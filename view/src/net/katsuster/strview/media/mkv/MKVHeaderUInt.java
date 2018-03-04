package net.katsuster.strview.media.mkv;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * Matroska unsigned integer
 * </p>
 */
public class MKVHeaderUInt<T extends LargeList<?>>
        extends MKVHeader<T> {
    public UInt uint_val;

    public MKVHeaderUInt() {
        uint_val = new UInt("uint_val");
    }

    @Override
    public MKVHeaderUInt<T> clone()
            throws CloneNotSupportedException {
        MKVHeaderUInt<T> obj = (MKVHeaderUInt<T>)super.clone();

        obj.uint_val = (UInt)uint_val.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "Matroska uint";
    }

    @Override
    public boolean isMaster() {
        return false;
    }

    @Override
    public void read(StreamReader<?, ?> c) {
        read(c, this);
    }

    public static void read(StreamReader<?, ?> c,
                            MKVHeaderUInt d) {
        c.enterBlock(d);

        MKVHeader.read(c, d);

        d.uint_val = c.readUInt((int)d.tag_len.getValue() << 3, d.uint_val);

        c.leaveBlock();
    }

    @Override
    public void write(StreamWriter<?, ?> c) {
        write(c, this);
    }

    public static void write(StreamWriter<?, ?> c,
                             MKVHeaderUInt d) {
        c.enterBlock(d);

        MKVHeader.write(c, d);

        c.writeUInt((int)d.tag_len.getValue() << 3, d.uint_val);

        c.leaveBlock();
    }
}
