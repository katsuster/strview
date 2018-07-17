package net.katsuster.strview.media.mkv;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * Matroska string
 * </p>
 */
public class MKVHeaderString
        extends MKVHeader {
    public LargeBitList string_bits;

    public MKVHeaderString() {
        string_bits = new SubLargeBitList("string_bits");
    }

    @Override
    public MKVHeaderString clone()
            throws CloneNotSupportedException {
        MKVHeaderString obj = (MKVHeaderString)super.clone();

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
    protected void readBits(BitStreamReader c) {
        readBits(c, this);
    }

    public static void readBits(BitStreamReader c,
                                MKVHeaderString d) {
        c.enterBlock(d);

        MKVHeader.readBits(c, d);

        d.string_bits = c.readBitList(d.tag_len.getValue() << 3, d.string_bits);

        c.leaveBlock();
    }

    @Override
    protected void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 MKVHeaderString d) {
        c.enterBlock(d);

        MKVHeader.writeBits(c, d);

        c.writeBitList(d.tag_len.getValue() << 3, d.string_bits, d.getStringName());

        c.leaveBlock();
    }

    public String getStringName() {
        return getArrayName(string_bits, "US-ASCII");
    }
}
