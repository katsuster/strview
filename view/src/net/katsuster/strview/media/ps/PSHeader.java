package net.katsuster.strview.media.ps;

import net.katsuster.strview.util.bit.*;
import net.katsuster.strview.media.bit.*;

/**
 * <p>
 * MPEG2-PS (Program Stream) common header
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
public class PSHeader
        extends BitBlockAdapter
        implements Cloneable {
    public UInt packet_start_code_prefix;
    public UInt stream_id;

    public PSHeader() {
        packet_start_code_prefix = new UInt("packet_start_code_prefix");
        stream_id = new UInt("stream_id");
    }

    @Override
    public PSHeader clone()
            throws CloneNotSupportedException {
        PSHeader obj = (PSHeader)super.clone();

        obj.packet_start_code_prefix = (UInt)packet_start_code_prefix.clone();
        obj.stream_id = (UInt)stream_id.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "PS header";
    }

    @Override
    protected void readBits(BitStreamReader c) {
        readBits(c, this);
    }

    public static void readBits(BitStreamReader c,
                                PSHeader d) {
        c.enterBlock(d);

        d.packet_start_code_prefix = c.readUInt(24, d.packet_start_code_prefix);
        d.stream_id                = c.readUInt( 8, d.stream_id               );

        c.leaveBlock();
    }

    @Override
    protected void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 PSHeader d) {
        c.enterBlock(d);

        c.writeUInt(24, d.packet_start_code_prefix);
        c.writeUInt( 8, d.stream_id               , d.getStreamIdName());

        c.leaveBlock();
    }

    public String getStreamIdName() {
        return PSConsts.getStreamIdName(stream_id.intValue());
    }
}
