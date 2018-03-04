package net.katsuster.strview.media.test;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * 固定長パケットヘッダ。
 * </p>
 */
public class FixedHeader<T extends LargeList<?>>
        extends BlockAdapter<T>
        implements Cloneable {
    public UInt data_a;
    public UInt data_b;

    public FixedHeader() {
        data_a = new UInt("data_a");
        data_b = new UInt("data_b");
    }

    @Override
    public FixedHeader<T> clone()
            throws CloneNotSupportedException {
        FixedHeader<T> obj = (FixedHeader<T>)super.clone();

        obj.data_a = (UInt) data_a.clone();
        obj.data_b = (UInt) data_b.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "Fixed Size Packet";
    }

    @Override
    public void read(StreamReader<?, ?> c) {
        read(c, this);
    }

    public static void read(StreamReader<?, ?> c,
                            FixedHeader d) {
        c.enterBlock(d);

        d.data_a = c.readUInt( 3, d.data_a);
        d.data_b = c.readUInt( 5, d.data_b);

        c.leaveBlock();
    }

    @Override
    public void write(StreamWriter<?, ?> c) {
        write(c, this);
    }

    public static void write(StreamWriter<?, ?> c,
                             FixedHeader d) {
        c.enterBlock(d);

        c.writeUInt( 3, d.data_a);
        c.writeUInt( 5, d.data_b);

        c.leaveBlock();
    }
}
