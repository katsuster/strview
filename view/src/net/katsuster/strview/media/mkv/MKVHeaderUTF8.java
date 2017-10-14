package net.katsuster.strview.media.mkv;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * Matroska UTF-8 string
 * </p>
 */
public class MKVHeaderUTF8 extends MKVHeader {
    public LargeBitList utf8_bits;

    public MKVHeaderUTF8() {
        utf8_bits = new SubLargeBitList();
    }

    @Override
    public MKVHeaderUTF8 clone()
            throws CloneNotSupportedException {
        MKVHeaderUTF8 obj = (MKVHeaderUTF8)super.clone();

        obj.utf8_bits = (LargeBitList)utf8_bits.clone();

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
                            MKVHeaderUTF8 d) {
        c.enterBlock("Matroska utf-8 string");

        MKVHeader.read(c, d);

        d.utf8_bits = c.readBitList(d.tag_len.getValue() << 3, d.utf8_bits);

        c.leaveBlock();
    }

    @Override
    public void write(StreamWriter<?> c) {
        write(c, this);
    }

    public static void write(StreamWriter<?> c,
                             MKVHeaderUTF8 d) {
        c.enterBlock("Matroska utf-8 string");

        MKVHeader.write(c, d);

        c.writeBitList(d.tag_len.getValue() << 3, d.utf8_bits,
                "utf8_bits", d.getUTF8Name());

        c.leaveBlock();
    }

    public String getUTF8Name() {
        return getArrayName(utf8_bits, "UTF-8");
    }
}
