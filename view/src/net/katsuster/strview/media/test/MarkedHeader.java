package net.katsuster.strview.media.test;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * 先頭にマーカーのあるパケットヘッダ。
 * </p>
 */
public class MarkedHeader<T extends LargeList<?>>
        extends BlockAdapter<T>
        implements Cloneable {
    public UInt start_code;

    public MarkedHeader() {
        start_code = new UInt("start_code");
    }

    @Override
    public MarkedHeader<T> clone()
            throws CloneNotSupportedException {
        MarkedHeader<T> obj = (MarkedHeader<T>)super.clone();

        obj.start_code = (UInt)start_code.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "Marked Packet";
    }

    @Override
    public void read(StreamReader<?, ?> c) {
        read(c, this);
    }

    public static void read(StreamReader<?, ?> c,
                            MarkedHeader d) {
        c.enterBlock(d);

        d.start_code = c.readUInt(32, d.start_code);

        c.leaveBlock();
    }

    @Override
    public void write(StreamWriter<?, ?> c) {
        write(c, this);
    }

    public static void write(StreamWriter<?, ?> c,
                             MarkedHeader d) {
        c.enterBlock(d);

        c.writeUInt(32, d.start_code);

        c.leaveBlock();
    }
}
