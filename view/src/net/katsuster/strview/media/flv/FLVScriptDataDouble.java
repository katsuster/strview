package net.katsuster.strview.media.flv;

import net.katsuster.strview.util.bit.*;
import net.katsuster.strview.media.bit.*;

/**
 * <p>
 * DOUBLE
 * </p>
 */
public class FLVScriptDataDouble
        extends FLVScriptData
        implements Cloneable {
    public Float64 double_bits;

    public FLVScriptDataDouble() {
        double_bits = new Float64("double_bits");
    }

    @Override
    public FLVScriptDataDouble clone()
            throws CloneNotSupportedException {
        FLVScriptDataDouble obj = (FLVScriptDataDouble)super.clone();

        obj.double_bits = (Float64)double_bits.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "DOUBLE";
    }

    @Override
    protected void readBits(BitStreamReader c) {
        readBits(c, this);
    }

    public static void readBits(BitStreamReader c,
                                FLVScriptDataDouble d) {
        c.enterBlock(d);

        FLVScriptData.readBits(c, d);

        d.double_bits = c.readFloat64(64, d.double_bits);

        c.leaveBlock();
    }

    @Override
    protected void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 FLVScriptDataDouble d) {
        c.enterBlock(d);

        FLVScriptData.writeBits(c, d);

        c.writeFloat64(64, d.double_bits);

        c.leaveBlock();
    }
}
