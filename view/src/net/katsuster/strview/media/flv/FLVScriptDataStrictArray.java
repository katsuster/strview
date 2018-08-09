package net.katsuster.strview.media.flv;

import java.util.*;

import net.katsuster.strview.util.bit.*;
import net.katsuster.strview.media.bit.*;

/**
 * <p>
 * SCRIPTDATASTRICTARRAY
 * </p>
 */
public class FLVScriptDataStrictArray
        extends FLVScriptData
        implements Cloneable {
    public UInt strict_array_length;
    public List<FLVScriptDataValue> strict_array_value;

    public FLVScriptDataStrictArray() {
        strict_array_length = new UInt("StrictArrayLength");
        strict_array_value = new ArrayList<>();
    }

    @Override
    public FLVScriptDataStrictArray clone()
            throws CloneNotSupportedException {
        FLVScriptDataStrictArray obj = (FLVScriptDataStrictArray)super.clone();

        obj.strict_array_length = (UInt)strict_array_length.clone();

        obj.strict_array_value = new ArrayList<>();
        for (FLVScriptDataValue v : strict_array_value) {
            obj.strict_array_value.add(v.clone());
        }

        return obj;
    }

    @Override
    public String getTypeName() {
        return "SCRIPTDATASTRICTARRAY";
    }

    @Override
    protected void readBits(BitStreamReader c) {
        readBits(c, this);
    }

    public static void readBits(BitStreamReader c,
                                FLVScriptDataStrictArray d) {
        c.enterBlock(d);

        FLVScriptData.readBits(c, d);

        d.strict_array_length = c.readUInt(32, d.strict_array_length);

        d.strict_array_value = readObjectList(c, d.strict_array_length.intValue(),
                d.strict_array_value, FLVScriptDataValue.class, "StrictArrayValue");

        c.leaveBlock();
    }

    @Override
    protected void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 FLVScriptDataStrictArray d) {
        c.enterBlock(d);

        FLVScriptData.writeBits(c, d);

        c.writeUInt(32, d.strict_array_length);

        writeObjectList(c, d.strict_array_length.intValue(),
                d.strict_array_value);

        c.leaveBlock();
    }
}
