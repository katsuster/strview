package net.katsuster.strview.media.riff;

import net.katsuster.strview.util.bit.*;
import net.katsuster.strview.media.bit.*;

/**
 * <p>
 * RIFF (Resource Interchange File Format) チャンクヘッダ。
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
public class RIFFHeader
        extends BitBlockAdapter {
    public UIntR ckID;
    public UIntR ckSize;

    public RIFFHeader() {
        ckID = new UIntR("ckID");
        ckSize = new UIntR("ckSize");
    }

    @Override
    public RIFFHeader clone()
            throws CloneNotSupportedException {
        RIFFHeader obj = (RIFFHeader)super.clone();

        obj.ckID = (UIntR)ckID.clone();
        obj.ckSize = (UIntR)ckSize.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "RIFF chunk";
    }

    /**
     * <p>
     * チャンク本体に別のチャンクを含められるかどうかを返します。
     * </p>
     *
     * @return チャンク本体に別のチャンクを含められる場合は true、
     * 含められない場合は false
     */
    public boolean isRecursive() {
        return false;
    }

    @Override
    protected void readBits(BitStreamReader c) {
        readBits(c, this);
    }

    public static void readBits(BitStreamReader c,
                                RIFFHeader d) {
        c.enterBlock(d);

        d.ckID   = c.readUIntR(32, d.ckID);
        d.ckSize = c.readUIntR(32, d.ckSize);

        c.leaveBlock();
    }

    @Override
    protected void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 RIFFHeader d) {
        c.enterBlock(d);

        c.writeUIntR(32, d.ckID  , d.getChunkIdName());
        c.writeUIntR(32, d.ckSize);

        c.leaveBlock();
    }

    public String getChunkIdName() {
        return RIFFConsts.getChunkIdName(ckID.intValue());
    }
}
