package net.katsuster.strview.media.rmff;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * PROP チャンクヘッダ。
 * </p>
 *
 * @author katsuhiro
 */
public class RMFFHeaderPROP extends RMFFHeader
        implements Cloneable {
    public UInt max_bit_rate;
    public UInt avg_bit_rate;
    public UInt max_packet_size;
    public UInt avg_packet_size;
    public UInt num_packets;
    public UInt duration;
    public UInt preroll;
    public UInt index_offset;
    public UInt data_offset;
    public UInt num_streams;
    public UInt flags;

    public RMFFHeaderPROP() {
        max_bit_rate = new UInt();
        avg_bit_rate = new UInt();
        max_packet_size = new UInt();
        avg_packet_size = new UInt();
        num_packets = new UInt();
        duration = new UInt();
        preroll = new UInt();
        index_offset = new UInt();
        data_offset = new UInt();
        num_streams = new UInt();
        flags = new UInt();
    }

    @Override
    public RMFFHeaderPROP clone()
            throws CloneNotSupportedException {
        RMFFHeaderPROP obj = (RMFFHeaderPROP)super.clone();

        obj.max_bit_rate = (UInt)max_bit_rate.clone();
        obj.avg_bit_rate = (UInt)avg_bit_rate.clone();
        obj.max_packet_size = (UInt)max_packet_size.clone();
        obj.avg_packet_size = (UInt)avg_packet_size.clone();
        obj.num_packets = (UInt)num_packets.clone();
        obj.duration = (UInt)duration.clone();
        obj.preroll = (UInt)preroll.clone();
        obj.index_offset = (UInt)index_offset.clone();
        obj.data_offset = (UInt)data_offset.clone();
        obj.num_streams = (UInt)num_streams.clone();
        obj.flags = (UInt)flags.clone();

        return obj;
    }

    @Override
    public void read(PacketReader<?> c) {
        read(c, this);
    }

    public static void read(PacketReader<?> c,
                            RMFFHeaderPROP d) {
        c.enterBlock("PROP chunk");

        RMFFHeader.read(c, d);

        if (d.object_version.intValue() == 0) {
            d.max_bit_rate    = c.readUInt(32, d.max_bit_rate   );
            d.avg_bit_rate    = c.readUInt(32, d.avg_bit_rate   );
            d.max_packet_size = c.readUInt(32, d.max_packet_size);
            d.avg_packet_size = c.readUInt(32, d.avg_packet_size);
            d.num_packets     = c.readUInt(32, d.num_packets    );
            d.duration        = c.readUInt(32, d.duration       );
            d.preroll         = c.readUInt(32, d.preroll        );
            d.index_offset    = c.readUInt(32, d.index_offset   );
            d.data_offset     = c.readUInt(32, d.data_offset    );
            d.num_streams     = c.readUInt(16, d.num_streams    );
            d.flags           = c.readUInt(16, d.flags          );
        }

        c.leaveBlock();
    }

    @Override
    public void write(PacketWriter<?> c) {
        write(c, this);
    }

    public static void write(PacketWriter<?> c,
                             RMFFHeaderPROP d) {
        c.enterBlock("PROP chunk");

        RMFFHeader.write(c, d);

        if (d.object_version.intValue() == 0) {
            c.writeUInt(32, d.max_bit_rate   , "max_bit_rate"   );
            c.writeUInt(32, d.avg_bit_rate   , "avg_bit_rate"   );
            c.writeUInt(32, d.max_packet_size, "max_packet_size");
            c.writeUInt(32, d.avg_packet_size, "avg_packet_size");
            c.writeUInt(32, d.num_packets    , "num_packets"    );
            c.writeUInt(32, d.duration       , "duration"       );
            c.writeUInt(32, d.preroll        , "preroll"        );
            c.writeUInt(32, d.index_offset   , "index_offset"   );
            c.writeUInt(32, d.data_offset    , "data_offset"    );
            c.writeUInt(16, d.num_streams    , "num_streams"    );
            c.writeUInt(16, d.flags          , "flags"          );
        }

        c.leaveBlock();
    }
}
