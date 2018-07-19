package net.katsuster.strview.media.flv;

import net.katsuster.strview.util.bit.*;
import net.katsuster.strview.media.bit.*;

/**
 * <p>
 * UI8
 * </p>
 */
public class FLVScriptDataUI8
        extends FLVScriptData
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
    public String getTypeName() {
        return "UI8";
    }

    @Override
    protected void readBits(BitStreamReader c) {
        readBits(c, this);
    }

    public static void readBits(BitStreamReader c,
                            FLVScriptDataUI8 d) {
        c.enterBlock(d);

        FLVScriptData.readBits(c, d);

        d.uint8_bits = c.readUInt( 8, d.uint8_bits);

        c.leaveBlock();
    }

    @Override
    protected void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                             FLVScriptDataUI8 d) {
        c.enterBlock(d);

        FLVScriptData.writeBits(c, d);

        c.writeUInt( 8, d.uint8_bits, "UI8");

        c.leaveBlock();
    }
}
