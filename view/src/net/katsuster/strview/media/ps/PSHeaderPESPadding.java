package net.katsuster.strview.media.ps;

import net.katsuster.strview.media.bit.*;

/**
 * <p>
 * MPEG2 PES (Packetized Elementary Stream) header (padding bytes)
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
public class PSHeaderPESPadding
        extends PSHeaderPES
        implements Cloneable {
    public PSHeaderPESPadding() {
        //Do nothing
    }

    @Override
    public PSHeaderPESPadding clone()
            throws CloneNotSupportedException {
        PSHeaderPESPadding obj = (PSHeaderPESPadding)super.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "PES padding header";
    }

    @Override
    protected void readBits(BitStreamReader c) {
        readBits(c, this);
    }

    public static void readBits(BitStreamReader c,
                                PSHeaderPESPadding d) {
        c.enterBlock(d);

        PSHeaderPES.readBits(c, d);

        c.leaveBlock();
    }

    @Override
    protected void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 PSHeaderPESPadding d) {
        c.enterBlock(d);

        PSHeaderPES.writeBits(c, d);

        c.leaveBlock();
    }
}
