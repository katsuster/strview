package net.katsuster.strview.media.rmff;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * RMFF (RealMedia File Format) チャンクヘッダ。
 * </p>
 *
 * <p>
 * 参考規格
 * </p>
 * <ul>
 * <li>HELIX CLIENT AND SERVER SOFTWARE DEVELOPER’S GUIDE Volume 2
 * : VERSION R5
 * - Appendix E: RealMedia File Format (RMFF) Reference
 * (https://www.helixcommunity.org/projects/common/2003/HCS_SDK_r5/htmfiles/rmff.htm)</li>
 * </ul>
 */
public class RMFFHeader
        extends BitBlockAdapter
        implements Cloneable {
    public UInt object_id;
    public UInt size;
    public UInt object_version;

    public RMFFHeader() {
        object_id = new UInt("object_id");
        size      = new UInt("size");
        object_version = new UInt("object_version");
    }

    @Override
    public RMFFHeader clone()
            throws CloneNotSupportedException {
        RMFFHeader obj = (RMFFHeader)super.clone();

        obj.object_id = (UInt)object_id.clone();
        obj.size = (UInt)size.clone();
        obj.object_version = (UInt)object_version.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "RMFF chunk";
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
                                RMFFHeader d) {
        c.enterBlock(d);

        d.object_id      = c.readUInt(32, d.object_id     );
        d.size           = c.readUInt(32, d.size          );
        d.object_version = c.readUInt(16, d.object_version);

        c.leaveBlock();
    }

    @Override
    protected void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 RMFFHeader d) {
        c.enterBlock(d);

        c.writeUInt(32, d.object_id     , d.getObjectIdName());
        c.writeUInt(32, d.size          );
        c.writeUInt(16, d.object_version);

        c.leaveBlock();
    }

    public String getObjectIdName() {
        return RMFFConsts.getObjectIdName(object_id.intValue());
    }
}
