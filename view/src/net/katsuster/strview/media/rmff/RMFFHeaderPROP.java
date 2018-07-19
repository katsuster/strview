package net.katsuster.strview.media.rmff;

import net.katsuster.strview.util.bit.*;
import net.katsuster.strview.media.bit.*;

/**
 * <p>
 * PROP チャンクヘッダ。
 * </p>
 */
public class RMFFHeaderPROP
        extends RMFFHeader
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
        max_bit_rate    = new UInt("max_bit_rate"   );
        avg_bit_rate    = new UInt("avg_bit_rate"   );
        max_packet_size = new UInt("max_packet_size");
        avg_packet_size = new UInt("avg_packet_size");
        num_packets     = new UInt("num_packets"    );
        duration        = new UInt("duration"       );
        preroll         = new UInt("preroll"        );
        index_offset    = new UInt("index_offset"   );
        data_offset     = new UInt("data_offset"    );
        num_streams     = new UInt("num_streams"    );
        flags           = new UInt("flags"          );
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
    public String getTypeName() {
        return "PROP chunk";
    }

    @Override
    protected void readBits(BitStreamReader c) {
        readBits(c, this);
    }

    public static void readBits(BitStreamReader c,
                                RMFFHeaderPROP d) {
        c.enterBlock(d);

        RMFFHeader.readBits(c, d);

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
    protected void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 RMFFHeaderPROP d) {
        c.enterBlock(d);

        RMFFHeader.writeBits(c, d);

        if (d.object_version.intValue() == 0) {
            c.writeUInt(32, d.max_bit_rate   );
            c.writeUInt(32, d.avg_bit_rate   );
            c.writeUInt(32, d.max_packet_size);
            c.writeUInt(32, d.avg_packet_size);
            c.writeUInt(32, d.num_packets    );
            c.writeUInt(32, d.duration       );
            c.writeUInt(32, d.preroll        );
            c.writeUInt(32, d.index_offset   );
            c.writeUInt(32, d.data_offset    );
            c.writeUInt(16, d.num_streams    );
            c.writeUInt(16, d.flags          );
        }

        c.leaveBlock();
    }
}
