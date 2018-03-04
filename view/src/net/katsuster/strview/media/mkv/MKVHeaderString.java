package net.katsuster.strview.media.mkv;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * Matroska string
 * </p>
 */
public class MKVHeaderString<T extends LargeList<?>>
        extends MKVHeader<T> {
    public LargeBitList string_bits;

    public MKVHeaderString() {
        string_bits = new SubLargeBitList("string_bits");
    }

    @Override
    public MKVHeaderString<T> clone()
            throws CloneNotSupportedException {
        MKVHeaderString<T> obj = (MKVHeaderString<T>)super.clone();

        obj.string_bits = (LargeBitList)string_bits.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "Matroska string";
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
                            MKVHeaderString d) {
        c.enterBlock(d);

        MKVHeader.read(c, d);

        d.string_bits = c.readBitList(d.tag_len.getValue() << 3, d.string_bits);

        c.leaveBlock();
    }

    @Override
    public void write(StreamWriter<?, ?> c) {
        write(c, this);
    }

    public static void write(StreamWriter<?, ?> c,
                             MKVHeaderString d) {
        c.enterBlock(d);

        MKVHeader.write(c, d);

        c.writeBitList(d.tag_len.getValue() << 3, d.string_bits, d.getStringName());

        c.leaveBlock();
    }

    public String getStringName() {
        return getArrayName(string_bits, "US-ASCII");
    }
}
