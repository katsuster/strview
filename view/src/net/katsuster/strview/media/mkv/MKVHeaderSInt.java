package net.katsuster.strview.media.mkv;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * Matroska signed integer
 * </p>
 */
public class MKVHeaderSInt<T extends LargeList<?>>
        extends MKVHeader<T> {
    public SInt sint_val;

    public MKVHeaderSInt() {
        sint_val = new SInt("sint_val");
    }

    @Override
    public MKVHeaderSInt<T> clone()
            throws CloneNotSupportedException {
        MKVHeaderSInt<T> obj = (MKVHeaderSInt<T>)super.clone();

        obj.sint_val = (SInt)sint_val.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "Matroska int";
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
                            MKVHeaderSInt d) {
        c.enterBlock(d);

        MKVHeader.read(c, d);

        d.sint_val = c.readSInt((int)d.tag_len.getValue() << 3, d.sint_val);

        c.leaveBlock();
    }

    @Override
    public void write(StreamWriter<?, ?> c) {
        write(c, this);
    }

    public static void write(StreamWriter<?, ?> c,
                             MKVHeaderSInt d) {
        c.enterBlock(d);

        MKVHeader.write(c, d);

        c.writeSInt((int)d.tag_len.getValue() << 3, d.sint_val);

        c.leaveBlock();
    }
}
