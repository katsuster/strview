package net.katsuster.strview.media.mkv;

import net.katsuster.strview.util.bit.*;
import net.katsuster.strview.media.bit.*;

/**
 * <p>
 * Matroska unsigned integer
 * </p>
 */
public class MKVHeaderUInt
        extends MKVHeader {
    public UInt uint_val;

    public MKVHeaderUInt() {
        uint_val = new UInt("uint_val");
    }

    @Override
    public MKVHeaderUInt clone()
            throws CloneNotSupportedException {
        MKVHeaderUInt obj = (MKVHeaderUInt)super.clone();

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
    protected void readBits(BitStreamReader c) {
        readBits(c, this);
    }

    public static void readBits(BitStreamReader c,
                                MKVHeaderUInt d) {
        c.enterBlock(d);

        MKVHeader.readBits(c, d);

        d.uint_val = c.readUInt((int)d.tag_len.getValue() << 3, d.uint_val);

        c.leaveBlock();
    }

    @Override
    protected void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 MKVHeaderUInt d) {
        c.enterBlock(d);

        MKVHeader.writeBits(c, d);

        c.writeUInt((int)d.tag_len.getValue() << 3, d.uint_val);

        c.leaveBlock();
    }
}
