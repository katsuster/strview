package net.katsuster.strview.media.mkv;

import net.katsuster.strview.util.bit.*;
import net.katsuster.strview.media.bit.*;

/**
 * <p>
 * Matroska UTF-8 string
 * </p>
 */
public class MKVHeaderUTF8
        extends MKVHeader {
    public LargeBitList utf8_bits;

    public MKVHeaderUTF8() {
        utf8_bits = new SubLargeBitList("utf8_bits");
    }

    @Override
    public MKVHeaderUTF8 clone()
            throws CloneNotSupportedException {
        MKVHeaderUTF8 obj = (MKVHeaderUTF8)super.clone();

        obj.utf8_bits = (LargeBitList)utf8_bits.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "Matroska UTF-8 string";
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
                                MKVHeaderUTF8 d) {
        c.enterBlock(d);

        MKVHeader.readBits(c, d);

        d.utf8_bits = c.readBitList(d.tag_len.getValue() << 3, d.utf8_bits);

        c.leaveBlock();
    }

    @Override
    protected void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 MKVHeaderUTF8 d) {
        c.enterBlock(d);

        MKVHeader.writeBits(c, d);

        c.writeBitList(d.tag_len.getValue() << 3, d.utf8_bits, d.getUTF8Name());

        c.leaveBlock();
    }

    public String getUTF8Name() {
        return getArrayName(utf8_bits, "UTF-8");
    }
}
