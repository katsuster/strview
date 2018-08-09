package net.katsuster.strview.media.flv;

import net.katsuster.strview.util.bit.*;
import net.katsuster.strview.media.bit.*;

/**
 * <p>
 * SCRIPTDATASTRING
 * </p>
 */
public class FLVScriptDataString
        extends FLVScriptData
        implements Cloneable {
    public UInt string_length;
    public LargeBitList string_data;

    public FLVScriptDataString() {
        this("");
    }

    public FLVScriptDataString(String n) {
        super(n);

        string_length = new UInt("StringLength");
        string_data = new SubLargeBitList("StringData");
    }

    @Override
    public FLVScriptDataString clone()
            throws CloneNotSupportedException {
        FLVScriptDataString obj = (FLVScriptDataString)super.clone();

        obj.string_length = (UInt)string_length.clone();
        obj.string_data = (LargeBitList)string_data.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "SCRIPTDATASTRING";
    }

    @Override
    protected void readBits(BitStreamReader c) {
        readBits(c, this);
    }

    public static void readBits(BitStreamReader c,
                                FLVScriptDataString d) {
        c.enterBlock(d);

        FLVScriptData.readBits(c, d);

        d.string_length = c.readUInt(16, d.string_length);
        d.string_data = c.readBitList(d.string_length.intValue() << 3, d.string_data);

        c.leaveBlock();
    }

    @Override
    protected void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 FLVScriptDataString d) {
        c.enterBlock(d);

        FLVScriptData.writeBits(c, d);

        c.writeUInt(16, d.string_length);
        c.writeBitList(d.string_length.intValue() << 3, d.string_data,
                d.getStringDataName());

        c.leaveBlock();
    }

    public String getStringDataName() {
        return getArrayName(string_data, "US-ASCII");
    }
}
