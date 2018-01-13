package net.katsuster.strview.media.flv;

import java.util.*;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * SCRIPTDATASTRICTARRAY
 * </p>
 */
public class FLVScriptDataStrictArray<T extends LargeList<?>>
        extends FLVScriptData<T>
        implements Cloneable {
    public UInt strict_array_length;
    public List<FLVScriptDataValue> strict_array_value;

    public FLVScriptDataStrictArray() {
        strict_array_length = new UInt();
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
    public void read(StreamReader<?> c) {
        read(c, this);
    }

    public static void read(StreamReader<?> c,
                            FLVScriptDataStrictArray d) {
        c.enterBlock(d);

        FLVScriptData.read(c, d);

        d.strict_array_length = c.readUInt(32, d.strict_array_length);

        d.strict_array_value = readObjectList(c, d.strict_array_length.intValue(),
                d.strict_array_value, FLVScriptDataValue.class);

        c.leaveBlock();
    }

    @Override
    public void write(StreamWriter<?> c) {
        write(c, this);
    }

    public static void write(StreamWriter<?> c,
                             FLVScriptDataStrictArray d) {
        c.enterBlock(d);

        FLVScriptData.write(c, d);

        c.writeUInt(32, d.strict_array_length, "StrictArrayLength");

        writeObjectList(c, d.strict_array_length.intValue(),
                d.strict_array_value, "StrictArrayValue");

        c.leaveBlock();
    }
}
