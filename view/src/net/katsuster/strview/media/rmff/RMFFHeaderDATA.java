package net.katsuster.strview.media.rmff;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * DATA チャンクヘッダ。
 * </p>
 */
public class RMFFHeaderDATA<T extends LargeList<?>>
        extends RMFFHeader<T>
        implements Cloneable {
    public UInt num_packets;
    public UInt next_data_header;

    public RMFFHeaderDATA() {
        num_packets = new UInt("num_packets");
        next_data_header = new UInt("next_data_header");
    }

    @Override
    public RMFFHeaderDATA<T> clone()
            throws CloneNotSupportedException {
        RMFFHeaderDATA<T> obj = (RMFFHeaderDATA<T>)super.clone();

        obj.num_packets = (UInt)num_packets.clone();
        obj.next_data_header = (UInt)next_data_header.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "DATA chunk";
    }

    @Override
    public void read(StreamReader<?> c) {
        read(c, this);
    }

    public static void read(StreamReader<?> c,
                            RMFFHeaderDATA d) {
        c.enterBlock(d);

        RMFFHeader.read(c, d);

        if (d.object_version.intValue() == 0) {
            d.num_packets      = c.readUInt(32, d.num_packets     );
            d.next_data_header = c.readUInt(32, d.next_data_header);
        }

        c.leaveBlock();
    }

    @Override
    public void write(StreamWriter<?> c) {
        write(c, this);
    }

    public static void write(StreamWriter<?> c,
                             RMFFHeaderDATA d) {
        c.enterBlock(d);

        RMFFHeader.write(c, d);

        if (d.object_version.intValue() == 0) {
            c.writeUInt(32, d.num_packets     );
            c.writeUInt(32, d.next_data_header);
        }

        c.leaveBlock();
    }
}
