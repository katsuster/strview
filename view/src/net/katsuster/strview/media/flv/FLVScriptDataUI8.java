package net.katsuster.strview.media.flv;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * UI8
 * </p>
 *
 * @author katsuhiro
 */
public class FLVScriptDataUI8 extends FLVScriptData
        implements Cloneable {
    public UInt uint8_bits;

    public FLVScriptDataUI8() {
        uint8_bits = new UInt();
    }

    @Override
    public FLVScriptDataUI8 clone()
            throws CloneNotSupportedException {
        FLVScriptDataUI8 obj = (FLVScriptDataUI8)super.clone();

        obj.uint8_bits = (UInt)uint8_bits.clone();

        return obj;
    }

    @Override
    public void read(StreamReader<?> c) {
        read(c, this);
    }

    public static void read(StreamReader<?> c,
                            FLVScriptDataUI8 d) {
        c.enterBlock("UI8");

        FLVScriptData.read(c, d);

        d.uint8_bits = c.readUInt( 8, d.uint8_bits);

        c.leaveBlock();
    }

    @Override
    public void write(StreamWriter<?> c) {
        write(c, this);
    }

    public static void write(StreamWriter<?> c,
                             FLVScriptDataUI8 d) {
        c.enterBlock("UI8");

        FLVScriptData.write(c, d);

        c.writeUInt( 8, d.uint8_bits, "UI8");

        c.leaveBlock();
    }
}
