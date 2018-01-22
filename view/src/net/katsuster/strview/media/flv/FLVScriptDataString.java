package net.katsuster.strview.media.flv;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * SCRIPTDATASTRING
 * </p>
 */
public class FLVScriptDataString<T extends LargeList<?>>
        extends FLVScriptData<T>
        implements Cloneable {
    public UInt string_length;
    public LargeBitList string_data;

    public FLVScriptDataString() {
        this("");
    }

    public FLVScriptDataString(String n) {
        super(n);

        string_length = new UInt();
        string_data = new SubLargeBitList();
    }

    @Override
    public FLVScriptDataString<T> clone()
            throws CloneNotSupportedException {
        FLVScriptDataString<T> obj = (FLVScriptDataString<T>)super.clone();

        obj.string_length = (UInt)string_length.clone();
        obj.string_data = (LargeBitList)string_data.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "SCRIPTDATASTRING";
    }

    @Override
    public void read(StreamReader<?> c) {
        read(c, this);
    }

    public static void read(StreamReader<?> c,
                            FLVScriptDataString d) {
        c.enterBlock(d);

        FLVScriptData.read(c, d);

        d.string_length = c.readUInt(16, d.string_length);
        d.string_data = c.readBitList(d.string_length.intValue() << 3, d.string_data);

        c.leaveBlock();
    }

    @Override
    public void write(StreamWriter<?> c) {
        write(c, this);
    }

    public static void write(StreamWriter<?> c,
                             FLVScriptDataString d) {
        c.enterBlock(d);

        FLVScriptData.write(c, d);

        c.writeUInt(16, d.string_length, "StringLength");
        c.writeBitList(d.string_length.intValue() << 3, d.string_data,
                "StringData", d.getStringDataName());

        c.leaveBlock();
    }

    public String getStringDataName() {
        return getArrayName(string_data, "US-ASCII");
    }
}
