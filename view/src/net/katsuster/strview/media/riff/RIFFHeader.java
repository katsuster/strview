package net.katsuster.strview.media.riff;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

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
 *
 * @author katsuhiro
 */
public class RIFFHeader extends BlockAdapter {
    public UInt ckID;
    public UInt ckSize;

    public RIFFHeader() {
        ckID = new UInt();
        ckSize = new UInt();
    }

    @Override
    public RIFFHeader clone()
            throws CloneNotSupportedException {
        RIFFHeader obj = (RIFFHeader)super.clone();

        obj.ckID = ckID.clone();
        obj.ckSize = ckSize.clone();

        return obj;
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
    public void read(PacketReader<?> c) {
        read(c, this);
    }

    public static void read(PacketReader<?> c,
                            RIFFHeader d) {
        c.enterBlock("RIFF chunk header");

        d.ckID   = c.readUIntR(32, d.ckID);
        d.ckSize = c.readUIntR(32, d.ckSize);

        c.leaveBlock();
    }

    @Override
    public void write(PacketWriter<?> c) {
        write(c, this);
    }

    public static void write(PacketWriter<?> c,
                             RIFFHeader d) {
        c.enterBlock("RIFF chunk header");

        c.writeUIntR(32, d.ckID  , "ckID"  , d.getChunkIdName());
        c.writeUIntR(32, d.ckSize, "ckSize");

        c.leaveBlock();
    }

    public String getChunkIdName() {
        return RIFFConsts.getChunkIdName(ckID.intValue());
    }
}
