package net.katsuster.strview.media.ps;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

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
 *
 * @author katsuhiro
 */
public class PSHeaderPES extends PSHeader
        implements Cloneable {
    public UInt pes_packet_length;

    public PSHeaderPES() {
        pes_packet_length = new UInt();
    }

    @Override
    public PSHeaderPES clone()
            throws CloneNotSupportedException {
        PSHeaderPES obj = (PSHeaderPES)super.clone();

        obj.pes_packet_length = (UInt)pes_packet_length.clone();

        return obj;
    }

    @Override
    public void read(StreamReader<?> c) {
        read(c, this);
    }

    public static void read(StreamReader<?> c,
                            PSHeaderPES d) {
        c.enterBlock("PES common header");

        PSHeader.read(c, d);

        d.pes_packet_length = c.readUInt(16, d.pes_packet_length);

        c.leaveBlock();
    }

    @Override
    public void write(StreamWriter<?> c) {
        write(c, this);
    }

    public static void write(StreamWriter<?> c,
                             PSHeaderPES d) {
        c.enterBlock("PES common header");

        PSHeader.write(c, d);

        c.writeUInt(16, d.pes_packet_length, "PES_packet_length");

        c.leaveBlock();
    }

    public String getStreamIdName() {
        return PSConsts.getStreamIdName(stream_id.intValue());
    }
}
