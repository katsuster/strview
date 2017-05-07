package net.katsuster.strview.media.mp4;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;
import net.katsuster.strview.media.mp4.MP4Consts.*;

/**
 * <p>
 * MPEG4 media file format の Box ヘッダ。
 * </p>
 *
 * <p>
 * 参考規格
 * </p>
 * <ul>
 * <li>ISO/IEC 14496-12: ISO base media file format</li>
 * </ul>
 *
 * @author katsuhiro
 */
public class MP4Header extends BlockAdapter
        implements Cloneable {
    public UInt size;
    public UInt type;
    public UInt largesize;
    public LargeBitList usertype;

    public MP4Header() {
        size = new UInt();
        type = new UInt();
        largesize = new UInt();
        usertype = new SubLargeBitList();
    }

    @Override
    public MP4Header clone()
            throws CloneNotSupportedException {
        MP4Header obj = (MP4Header)super.clone();

        obj.size = (UInt)size.clone();
        obj.type = (UInt)type.clone();
        obj.largesize = (UInt)largesize.clone();
        obj.usertype = (LargeBitList)usertype.clone();

        return obj;
    }

    /**
     * <p>
     * Box 本体に別の Box を含められるかどうかを返します。
     * </p>
     *
     * @return Box 本体に別の Box を含められる場合は true、
     * 含められない場合は false
     */
    public boolean isRecursive() {
        return false;
    }

    @Override
    public void read(PacketReader<?> c) {
        read(c, this);
    }

    public static void read(PacketReader<?> c,
                            MP4Header d) {
        c.enterBlock("Box");

        c.alignByte();

        d.size = c.readUInt(32, d.size);
        d.type = c.readUInt(32, d.type);

        if (d.size.intValue() == 1) {
            d.largesize = c.readUInt(64, d.largesize);
        } else if (d.size.intValue() == 0) {
            // box extends to end of file
        }

        if (d.type.intValue() == BOX_TYPE.UUID) {
            d.usertype = c.readBitList(16 << 3, d.usertype);
        }

        c.leaveBlock();
    }

    @Override
    public void write(PacketWriter<?> c) {
        write(c, this);
    }

    public static void write(PacketWriter<?> c,
                             MP4Header d) {
        c.enterBlock("Box");

        c.alignByte();

        c.writeUInt(32, d.size, "size");
        c.writeUInt(32, d.type, "type", d.getTypeName());

        if (d.size.intValue() == 1) {
            c.writeUInt(64, d.largesize, "largesize");
        } else if (d.size.intValue() == 0) {
            // box extends to end of file
        }

        if (d.type.intValue() == BOX_TYPE.UUID) {
            c.writeBitList(16 << 3, d.usertype, "usertype");
        }

        c.leaveBlock();
    }

    public String getTypeName() {
        return MP4Consts.getTypeName(type.intValue());
    }
}
