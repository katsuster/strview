package net.katsuster.strview.media.m2v;

import net.katsuster.strview.util.bit.*;
import net.katsuster.strview.media.bit.*;

/**
 * <p>
 * MPEG2 Video ヘッダ。
 * </p>
 *
 * <p>
 * 参考規格
 * </p>
 * <ul>
 * <li>ITU-T H.262, ISO/IEC 13818-2:
 * Information technology - Generic coding of moving pictures and
 * associated audio information: Video</li>
 * </ul>
 */
public class M2VHeader
        extends BitBlockAdapter
        implements Cloneable {
    public UInt start_code;

    public M2VHeader() {
        start_code = new UInt("start_code");
    }

    @Override
    public M2VHeader clone()
            throws CloneNotSupportedException {
        M2VHeader obj = (M2VHeader)super.clone();

        obj.start_code = (UInt)start_code.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "header";
    }

    @Override
    protected void readBits(BitStreamReader c) {
        readBits(c, this);
    }

    public static void readBits(BitStreamReader c,
                                M2VHeader d) {
        c.enterBlock(d);

        d.start_code = c.readUInt(32, d.start_code);

        c.leaveBlock();
    }

    @Override
    protected void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 M2VHeader d) {
        c.enterBlock(d);

        c.writeUInt(32, d.start_code, d.getStartCodeName());

        c.leaveBlock();
    }

    public String getStartCodeName() {
        return M2VConsts.getStartCodeName(start_code.intValue());
    }
}
