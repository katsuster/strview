package net.katsuster.strview.media.flv;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * SCRIPTDATASTRING
 * </p>
 *
 * @author katsuhiro
 */
public class FLVScriptDataString extends FLVScriptData
        implements Cloneable {
    public UInt string_length;
    public LargeBitList string_data;

    public FLVScriptDataString() {
        string_length = new UInt();
        string_data = new SubLargeBitList();
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
    public void read(PacketReader<?> c) {
        read(c, this);
    }

    public static void read(PacketReader<?> c,
                            FLVScriptDataString d) {
        c.enterBlock("SCRIPTDATASTRING");

        FLVScriptData.read(c, d);

        d.string_length = c.readUInt(16, d.string_length);
        d.string_data = c.readBitList(d.string_length.intValue() << 3, d.string_data);

        c.leaveBlock();
    }

    @Override
    public void write(PacketWriter<?> c) {
        write(c, this);
    }

    public static void write(PacketWriter<?> c,
                             FLVScriptDataString d) {
        c.enterBlock("SCRIPTDATASTRING");

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
