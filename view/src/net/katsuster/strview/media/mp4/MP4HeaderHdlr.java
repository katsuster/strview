package net.katsuster.strview.media.mp4;

import java.io.*;

import net.katsuster.strview.media.bit.*;
import net.katsuster.strview.util.bit.*;

/**
 * <p>
 * MPEG4 media file format の hdlr ボックス。
 * </p>
 *
 * <p>
 * 参考規格
 * </p>
 * <ul>
 * <li>ISO/IEC 14496-12: ISO base media file format</li>
 * </ul>
 */
public class MP4HeaderHdlr extends MP4HeaderFull
        implements Cloneable {
    public UInt pre_defined;
    public UInt handler_type;
    public UInt[] reserved;
    public UInt namelen;
    public LargeBitList name;

    public MP4HeaderHdlr() {
        pre_defined = new UInt("pre_defined");
        handler_type = new UInt("handler_type");
        reserved = new UInt[3];
        namelen = new UInt("namelen");
        name = new SubLargeBitList("name");
    }

    @Override
    public MP4HeaderHdlr clone()
            throws CloneNotSupportedException {
        MP4HeaderHdlr obj =
                (MP4HeaderHdlr)super.clone();
        int i;

        obj.pre_defined = (UInt)pre_defined.clone();
        obj.handler_type = (UInt)handler_type.clone();
        obj.reserved = reserved.clone();
        for (i = 0; i < obj.reserved.length; i++) {
            obj.reserved[i] = (UInt)reserved[i].clone();
        }
        obj.namelen = (UInt)namelen.clone();
        obj.name = (LargeBitList) name.clone();

        return obj;
    }

    @Override
    public boolean isRecursive() {
        return false;
    }

    @Override
    public void readBits(BitStreamReader b) {
        readBits(b, this);
    }

    public static void readBits(BitStreamReader b,
                                MP4HeaderHdlr d) {
        int size;
        int i;

        MP4HeaderFull.readBits(b, d);

        d.pre_defined  = b.readUInt(32, d.pre_defined );
        d.handler_type = b.readUInt(32, d.handler_type);

        d.reserved = new UInt[3];
        for (i = 0; i < d.reserved.length; i++) {
            d.reserved[i] = new UInt("reserved[" + i + "]");
            d.reserved[i] = b.readUInt(32, d.reserved[i]);
        }

        //文字列の長さを求める
        size = ((d.size.intValue() - 4) << 3)
                - (int)(b.position() - d.type.getRange().getStart());

        d.name = b.readBitList(size, d.name);
    }

    @Override
    public void writeBits(BitStreamWriter b) {
        writeBits(b, this);
    }

    public static void writeBits(BitStreamWriter b,
                                 MP4HeaderHdlr d) {
        int i;

        MP4HeaderFull.writeBits(b, d);

        b.writeUInt(32, d.pre_defined );
        b.writeUInt(32, d.handler_type, d.getHandlerTypeName());

        for (i = 0; i < d.reserved.length; i++) {
            b.writeUInt(32, d.reserved[i]);
        }

        b.writeBitList((int)d.name.length(), d.name, d.getNameName());
    }

    public String getHandlerTypeName() {
        return MP4Consts.getTypeName(handler_type.intValue());
    }

    public String getNameName() {
        return getArrayName(name, "US-ASCII");
    }
}
