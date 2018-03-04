package net.katsuster.strview.media.mkv;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * Matroska float
 * </p>
 */
public class MKVHeaderFloat<T extends LargeList<?>>
        extends MKVHeader<T> {
    public Float32 float_bits;
    public Float64 double_bits;

    public MKVHeaderFloat() {
        float_bits = new Float32("float_bits");
        double_bits = new Float64("double_bits");
    }

    @Override
    public MKVHeaderFloat<T> clone()
            throws CloneNotSupportedException {
        MKVHeaderFloat<T> obj = (MKVHeaderFloat<T>)super.clone();

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
    public void read(StreamReader<?, ?> c) {
        read(c, this);
    }

    public static void read(StreamReader<?, ?> c,
                            MKVHeaderFloat d) {
        c.enterBlock(d);

        MKVHeader.read(c, d);

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
    public void write(StreamWriter<?, ?> c) {
        write(c, this);
    }

    public static void write(StreamWriter<?, ?> c,
                             MKVHeaderFloat d) {
        c.enterBlock(d);

        MKVHeader.write(c, d);

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
