package net.katsuster.strview.media.rmff;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * DATA チャンクヘッダ。
 * </p>
 */
public class RMFFHeaderDATA
        extends RMFFHeader
        implements Cloneable {
    public UInt num_packets;
    public UInt next_data_header;

    public RMFFHeaderDATA() {
        num_packets = new UInt("num_packets");
        next_data_header = new UInt("next_data_header");
    }

    @Override
    public RMFFHeaderDATA clone()
            throws CloneNotSupportedException {
        RMFFHeaderDATA obj = (RMFFHeaderDATA)super.clone();

        obj.num_packets = (UInt)num_packets.clone();
        obj.next_data_header = (UInt)next_data_header.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "DATA chunk";
    }

    @Override
    protected void readBits(BitStreamReader c) {
        readBits(c, this);
    }

    public static void readBits(BitStreamReader c,
                                RMFFHeaderDATA d) {
        c.enterBlock(d);

        RMFFHeader.readBits(c, d);

        if (d.object_version.intValue() == 0) {
            d.num_packets      = c.readUInt(32, d.num_packets     );
            d.next_data_header = c.readUInt(32, d.next_data_header);
        }

        c.leaveBlock();
    }

    @Override
    protected void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 RMFFHeaderDATA d) {
        c.enterBlock(d);

        RMFFHeader.writeBits(c, d);

        if (d.object_version.intValue() == 0) {
            c.writeUInt(32, d.num_packets     );
            c.writeUInt(32, d.next_data_header);
        }

        c.leaveBlock();
    }
}
