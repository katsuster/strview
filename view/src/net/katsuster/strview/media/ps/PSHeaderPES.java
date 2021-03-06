package net.katsuster.strview.media.ps;

import net.katsuster.strview.util.bit.*;
import net.katsuster.strview.media.bit.*;

/**
 * <p>
 * MPEG2 PES (Packetized Elementary Stream) common header
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
public class PSHeaderPES
        extends PSHeader
        implements Cloneable {
    public UInt pes_packet_length;

    public PSHeaderPES() {
        pes_packet_length = new UInt("PES_packet_length");
    }

    @Override
    public PSHeaderPES clone()
            throws CloneNotSupportedException {
        PSHeaderPES obj = (PSHeaderPES)super.clone();

        obj.pes_packet_length = (UInt)pes_packet_length.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "PES common header";
    }

    @Override
    protected void readBits(BitStreamReader c) {
        readBits(c, this);
    }

    public static void readBits(BitStreamReader c,
                                PSHeaderPES d) {
        c.enterBlock(d);

        PSHeader.readBits(c, d);

        d.pes_packet_length = c.readUInt(16, d.pes_packet_length);

        c.leaveBlock();
    }

    @Override
    protected void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 PSHeaderPES d) {
        c.enterBlock(d);

        PSHeader.writeBits(c, d);

        c.writeUInt(16, d.pes_packet_length);

        c.leaveBlock();
    }

    public String getStreamIdName() {
        return PSConsts.getStreamIdName(stream_id.intValue());
    }
}
