package net.katsuster.strview.media.mkv;

import net.katsuster.strview.util.bit.*;
import net.katsuster.strview.media.bit.*;

/**
 * <p>
 * Matroska float
 * </p>
 */
public class MKVHeaderFloat
        extends MKVHeader {
    public Float32 float_bits;
    public Float64 double_bits;

    public MKVHeaderFloat() {
        float_bits = new Float32("float_bits");
        double_bits = new Float64("double_bits");
    }

    @Override
    public MKVHeaderFloat clone()
            throws CloneNotSupportedException {
        MKVHeaderFloat obj = (MKVHeaderFloat)super.clone();

        obj.float_bits = (Float32)float_bits.clone();
        obj.double_bits = (Float64)double_bits.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "Matroska float";
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
                                MKVHeaderFloat d) {
        c.enterBlock(d);

        MKVHeader.readBits(c, d);

        int val = (int)d.tag_len.getValue();
        switch (val) {
        case 4:
            d.float_bits = c.readFloat32(val << 3, d.float_bits);
            break;
        case 8:
            d.double_bits = c.readFloat64(val << 3, d.double_bits);
            break;
        default:
            throw new IllegalStateException("float tag has illegal length"
                    + "(" + val + "bytes).");
        }

        c.leaveBlock();
    }

    @Override
    protected void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 MKVHeaderFloat d) {
        c.enterBlock(d);

        MKVHeader.writeBits(c, d);

        int val = (int)d.tag_len.getValue();
        switch (val) {
        case 4:
            c.writeFloat32(val << 3, d.float_bits );
            break;
        case 8:
            c.writeFloat64(val << 3, d.double_bits);
            break;
        default:
            throw new IllegalStateException("float tag has illegal length"
                    + "(" + val + "bytes).");
        }

        c.leaveBlock();
    }
}
