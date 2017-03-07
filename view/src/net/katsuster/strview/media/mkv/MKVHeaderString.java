package net.katsuster.strview.media.mkv;

import java.io.*;

import net.katsuster.strview.io.*;
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
        string_bits = new MemoryBitList();
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
    public void read(PacketReader<?> c) {
        read(c, this);
    }

    public static void read(PacketReader<?> c,
                            MKVHeaderString d) {
        c.enterBlock("Matroska string");

        MKVHeader.read(c, d);

        d.string_bits = c.readBitList((int)d.tag_len.getValue() << 3, d.string_bits);

        c.leaveBlock();
    }

    @Override
    public void write(PacketWriter<?> c) {
        write(c, this);
    }

    public static void write(PacketWriter<?> c,
                             MKVHeaderString d) {
        c.enterBlock("Matroska string");

        MKVHeader.write(c, d);

        c.writeBitList((int)d.tag_len.getValue() << 3, d.string_bits,
                "string_bits", d.getStringName());

        c.leaveBlock();
    }

    public String getStringName() {
        return getStringName(string_bits);
    }

    public static String getStringName(LargeBitList dat) {
        String name;

        try {
            byte[] buf = new byte[(int)dat.length() >>> 3];
            dat.getPackedByteArray(0, buf, 0, (int)dat.length() & ~0x7);
            name = new String(buf, "US-ASCII");
        } catch (UnsupportedEncodingException e) {
            name = "..unknown..";
        }

        return name;
    }
}
