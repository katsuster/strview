package net.katsuster.strview.media.mkv;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * Matroska float
 * </p>
 *
 * @author katsuhiro
 */
public class MKVHeaderFloat extends MKVHeader {
    public Float32 float_bits;
    public Float64 double_bits;

    public MKVHeaderFloat() {
        super();

        float_bits = new Float32();
        double_bits = new Float64();
    }

    @Override
    public MKVHeaderFloat clone()
            throws CloneNotSupportedException {
        MKVHeaderFloat obj = (MKVHeaderFloat)super.clone();

        obj.float_bits = float_bits.clone();
        obj.double_bits = double_bits.clone();

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
                            MKVHeaderFloat d) {
        int val;

        c.enterBlock("Matroska float");

        MKVHeader.read(c, d);

        val = (int)d.tag_len.getValue();
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
    public void write(PacketWriter<?> c) {
        write(c, this);
    }

    public static void write(PacketWriter<?> c,
                             MKVHeaderFloat d) {
        int val;

        c.enterBlock("Matroska float");

        MKVHeader.write(c, d);

        val = (int)d.tag_len.getValue();
        switch (val) {
        case 4:
            c.writeFloat32(val << 3, d.float_bits , "float_bits");
            break;
        case 8:
            c.writeFloat64(val << 3, d.double_bits, "double_bits");
            break;
        default:
            throw new IllegalStateException("float tag has illegal length"
                    + "(" + val + "bytes).");
        }

        c.leaveBlock();
    }
}
