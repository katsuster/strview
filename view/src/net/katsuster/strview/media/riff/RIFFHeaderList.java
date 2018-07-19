package net.katsuster.strview.media.riff;

import net.katsuster.strview.util.bit.*;
import net.katsuster.strview.media.bit.*;

/**
 * <p>
 * 入れ子にできる RIFF (Resource Interchange File Format) チャンクのヘッダ。
 * </p>
 *
 * <p>
 * 参考規格
 * </p>
 * <ul>
 * <li>Microsoft Multimedia Standards Update: Revision 1.0.97</li>
 * <li>OpenDML AVI File Format Extensions: Version 1.02</li>
 * </ul>
 */
public class RIFFHeaderList
        extends RIFFHeader
        implements Cloneable {
    public UIntR listType;

    public RIFFHeaderList() {
        listType = new UIntR("listType");
    }

    @Override
    public RIFFHeaderList clone()
            throws CloneNotSupportedException {
        RIFFHeaderList obj = (RIFFHeaderList)super.clone();

        obj.listType = (UIntR)listType.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "LIST chunk";
    }

    @Override
    public boolean isRecursive() {
        return true;
    }

    @Override
    protected void readBits(BitStreamReader c) {
        readBits(c, this);
    }

    public static void readBits(BitStreamReader c,
                                RIFFHeaderList d) {
        c.enterBlock(d);

        RIFFHeader.readBits(c, d);

        d.listType = c.readUIntR(32, d.listType);

        c.leaveBlock();
    }

    @Override
    protected void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 RIFFHeaderList d) {
        c.enterBlock(d);

        RIFFHeader.writeBits(c, d);

        c.writeUIntR(32, d.listType, d.getChunkTypeName());

        c.leaveBlock();
    }

    public String getChunkTypeName() {
        return RIFFConsts.getChunkIdName(listType.intValue());
    }
}
