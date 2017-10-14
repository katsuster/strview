package net.katsuster.strview.media.flv;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * DOUBLE
 * </p>
 */
public class FLVScriptDataDouble extends FLVScriptData
        implements Cloneable {
    public Float64 double_bits;

    public FLVScriptDataDouble() {
        double_bits = new Float64();
    }

    @Override
    public FLVScriptDataDouble clone()
            throws CloneNotSupportedException {
        FLVScriptDataDouble obj = (FLVScriptDataDouble)super.clone();

        obj.double_bits = (Float64)double_bits.clone();

        return obj;
    }

    @Override
    public void read(StreamReader<?> c) {
        read(c, this);
    }

    public static void read(StreamReader<?> c,
                            FLVScriptDataDouble d) {
        c.enterBlock("DOUBLE");

        FLVScriptData.read(c, d);

        d.double_bits = c.readFloat64(64, d.double_bits);

        c.leaveBlock();
    }

    @Override
    public void write(StreamWriter<?> c) {
        write(c, this);
    }

    public static void write(StreamWriter<?> c,
                             FLVScriptDataDouble d) {
        c.enterBlock("DOUBLE");

        FLVScriptData.write(c, d);

        c.writeFloat64(64, d.double_bits, "double_bits");

        c.leaveBlock();
    }
}
