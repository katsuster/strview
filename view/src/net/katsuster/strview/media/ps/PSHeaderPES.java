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
 */
public class PSHeaderPES<T extends LargeList<?>>
        extends PSHeader<T>
        implements Cloneable {
    public UInt pes_packet_length;

    public PSHeaderPES() {
        pes_packet_length = new UInt("PES_packet_length");
    }

    @Override
    public PSHeaderPES<T> clone()
            throws CloneNotSupportedException {
        PSHeaderPES<T> obj = (PSHeaderPES<T>)super.clone();

        obj.pes_packet_length = (UInt)pes_packet_length.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "PES common header";
    }

    @Override
    public void read(StreamReader<?> c) {
        read(c, this);
    }

    public static void read(StreamReader<?> c,
                            PSHeaderPES d) {
        c.enterBlock(d);

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
        c.enterBlock(d);

        PSHeader.write(c, d);

        c.writeUInt(16, d.pes_packet_length);

        c.leaveBlock();
    }

    public String getStreamIdName() {
        return PSConsts.getStreamIdName(stream_id.intValue());
    }
}
