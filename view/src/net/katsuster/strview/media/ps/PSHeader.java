package net.katsuster.strview.media.ps;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

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
 *
 * @author katsuhiro
 */
public class PSHeader extends BlockAdapter
        implements Cloneable {
    public UInt packet_start_code_prefix;
    public UInt stream_id;

    public PSHeader() {
        super();

        packet_start_code_prefix = new UInt();
        stream_id = new UInt();
    }

    @Override
    public PSHeader clone()
            throws CloneNotSupportedException {
        PSHeader obj = (PSHeader)super.clone();

        obj.packet_start_code_prefix = packet_start_code_prefix.clone();
        obj.stream_id = stream_id.clone();

        return obj;
    }

    @Override
    public void read(PacketReader<?> c) {
        read(c, this);
    }

    public static void read(PacketReader<?> c,
                            PSHeader d) {
        c.enterBlock("PS header");

        d.packet_start_code_prefix = c.readUInt(24, d.packet_start_code_prefix);
        d.stream_id                = c.readUInt( 8, d.stream_id               );

        c.leaveBlock();
    }

    @Override
    public void write(PacketWriter<?> c) {
        write(c, this);
    }

    public static void write(PacketWriter<?> c,
                             PSHeader d) {
        c.enterBlock("PS header");

        c.writeUInt(24, d.packet_start_code_prefix, "packet_start_code_prefix");
        c.writeUInt( 8, d.stream_id               , "stream_id"               , d.getStreamIdName());

        c.leaveBlock();
    }

    public String getStreamIdName() {
        return PSConsts.getStreamIdName(stream_id.intValue());
    }
}
