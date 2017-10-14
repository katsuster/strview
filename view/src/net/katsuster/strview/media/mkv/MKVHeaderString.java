package net.katsuster.strview.media.mkv;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * Matroska string
 * </p>
 *
 * @author katsuhiro
 */
public class MKVHeaderString extends MKVHeader {
    public LargeBitList string_bits;

    public MKVHeaderString() {
        string_bits = new SubLargeBitList();
    }

    @Override
    public MKVHeaderString clone()
            throws CloneNotSupportedException {
        MKVHeaderString obj = (MKVHeaderString)super.clone();

        obj.string_bits = (LargeBitList)string_bits.clone();

        return obj;
    }

    @Override
    public boolean isMaster() {
        return false;
    }

    @Override
    public void read(StreamReader<?> c) {
        read(c, this);
    }

    public static void read(StreamReader<?> c,
                            MKVHeaderString d) {
        c.enterBlock("Matroska string");

        MKVHeader.read(c, d);

        d.string_bits = c.readBitList(d.tag_len.getValue() << 3, d.string_bits);

        c.leaveBlock();
    }

    @Override
    public void write(StreamWriter<?> c) {
        write(c, this);
    }

    public static void write(StreamWriter<?> c,
                             MKVHeaderString d) {
        c.enterBlock("Matroska string");

        MKVHeader.write(c, d);

        c.writeBitList(d.tag_len.getValue() << 3, d.string_bits,
                "string_bits", d.getStringName());

        c.leaveBlock();
    }

    public String getStringName() {
        return getArrayName(string_bits, "US-ASCII");
    }
}
