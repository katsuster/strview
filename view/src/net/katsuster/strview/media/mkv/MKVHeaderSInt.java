package net.katsuster.strview.media.mkv;

import net.katsuster.strview.util.bit.*;
import net.katsuster.strview.media.bit.*;

/**
 * <p>
 * Matroska signed integer
 * </p>
 */
public class MKVHeaderSInt
        extends MKVHeader {
    public SInt sint_val;

    public MKVHeaderSInt() {
        sint_val = new SInt("sint_val");
    }

    @Override
    public MKVHeaderSInt clone()
            throws CloneNotSupportedException {
        MKVHeaderSInt obj = (MKVHeaderSInt)super.clone();

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
    protected void readBits(BitStreamReader c) {
        readBits(c, this);
    }

    public static void readBits(BitStreamReader c,
                                MKVHeaderSInt d) {
        c.enterBlock(d);

        MKVHeader.readBits(c, d);

        d.sint_val = c.readSInt((int)d.tag_len.getValue() << 3, d.sint_val);

        c.leaveBlock();
    }

    @Override
    protected void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 MKVHeaderSInt d) {
        c.enterBlock(d);

        MKVHeader.writeBits(c, d);

        c.writeSInt((int)d.tag_len.getValue() << 3, d.sint_val);

        c.leaveBlock();
    }
}
