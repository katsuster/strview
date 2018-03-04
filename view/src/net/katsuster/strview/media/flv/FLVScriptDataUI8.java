package net.katsuster.strview.media.flv;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * UI8
 * </p>
 */
public class FLVScriptDataUI8<T extends LargeList<?>>
        extends FLVScriptData<T>
        implements Cloneable {
    public UInt uint8_bits;

    public FLVScriptDataUI8() {
        uint8_bits = new UInt();
    }

    @Override
    public FLVScriptDataUI8<T> clone()
            throws CloneNotSupportedException {
        FLVScriptDataUI8<T> obj = (FLVScriptDataUI8<T>)super.clone();

        obj.uint8_bits = (UInt)uint8_bits.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "UI8";
    }

    @Override
    public void read(StreamReader<?, ?> c) {
        read(c, this);
    }

    public static void read(StreamReader<?, ?> c,
                            FLVScriptDataUI8 d) {
        c.enterBlock(d);

        FLVScriptData.read(c, d);

        d.uint8_bits = c.readUInt( 8, d.uint8_bits);

        c.leaveBlock();
    }

    @Override
    public void write(StreamWriter<?, ?> c) {
        write(c, this);
    }

    public static void write(StreamWriter<?, ?> c,
                             FLVScriptDataUI8 d) {
        c.enterBlock(d);

        FLVScriptData.write(c, d);

        c.writeUInt( 8, d.uint8_bits, "UI8");

        c.leaveBlock();
    }
}
