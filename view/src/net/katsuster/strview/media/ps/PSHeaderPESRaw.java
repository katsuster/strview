package net.katsuster.strview.media.ps;

import net.katsuster.strview.media.*;
import net.katsuster.strview.util.*;

/**
 * <p>
 * MPEG2 PES (Packetized Elementary Stream) header (has no header)
 * </p>
 *
 * <p>
 * 参考規格
 * </p>
 * <ul>
 * <li>ITU-T H.222, ISO/IEC 13818-1:
 * Information technology – Generic coding of moving pictures and
 * associated audio information: Systems</li>
 * </ul>
 */
public class PSHeaderPESRaw
        extends PSHeaderPES
        implements Cloneable {
    public PSHeaderPESRaw() {
        //Do nothing
    }

    @Override
    public PSHeaderPESRaw clone()
            throws CloneNotSupportedException {
        PSHeaderPESRaw obj = (PSHeaderPESRaw)super.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "PES raw header";
    }

    @Override
    protected void readBits(BitStreamReader c) {
        readBits(c, this);
    }

    public static void readBits(BitStreamReader c,
                                PSHeaderPESRaw d) {
        c.enterBlock(d);

        PSHeaderPES.readBits(c, d);

        c.leaveBlock();
    }

    @Override
    protected void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 PSHeaderPESRaw d) {
        c.enterBlock(d);

        PSHeaderPES.writeBits(c, d);

        c.leaveBlock();
    }
}
