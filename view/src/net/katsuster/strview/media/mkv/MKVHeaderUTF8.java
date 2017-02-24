package net.katsuster.strview.media.mkv;

import java.io.*;

import net.katsuster.strview.io.*;
import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * Matroska UTF-8 string
 * </p>
 *
 * @author katsuhiro
 */
public class MKVHeaderUTF8 extends MKVHeader {
    public MemoryBitList utf8_bits;

    public MKVHeaderUTF8() {
        utf8_bits = new MemoryBitList();
    }

    @Override
    public MKVHeaderUTF8 clone()
            throws CloneNotSupportedException {
        MKVHeaderUTF8 obj = (MKVHeaderUTF8)super.clone();

        obj.utf8_bits = utf8_bits.clone();

        return obj;
    }

    @Override
    public boolean isMaster() {
        return false;
    }

    @Override
    public void read(PacketReader<?> c) {
        read(c, this);
    }

    public static void read(PacketReader<?> c,
                            MKVHeaderUTF8 d) {
        c.enterBlock("Matroska utf-8 string");

        MKVHeader.read(c, d);

        d.utf8_bits = (MemoryBitList)c.readBitList((int)d.tag_len.getValue() << 3, d.utf8_bits);

        c.leaveBlock();
    }

    @Override
    public void write(PacketWriter<?> c) {
        write(c, this);
    }

    public static void write(PacketWriter<?> c,
                             MKVHeaderUTF8 d) {
        c.enterBlock("Matroska utf-8 string");

        MKVHeader.write(c, d);

        c.writeBitList((int)d.tag_len.getValue() << 3, d.utf8_bits, "utf8_bits");
        c.mark("utf8_val", d.getUTF8Name());

        c.leaveBlock();
    }

    public String getUTF8Name() {
        return getUTF8Name(utf8_bits);
    }

    public static String getUTF8Name(LargeBitList dat) {
        String name;

        try {
            byte[] buf = new byte[(int)dat.length() >>> 3];
            dat.getPackedByteArray(0, buf, 0, (int)dat.length() & ~0x7);
            name = new String(buf, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            name = "..unknown..";
        }

        return name;
    }
}
