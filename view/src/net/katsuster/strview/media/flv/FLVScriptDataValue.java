package net.katsuster.strview.media.flv;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * SCRIPTDATAVALUE
 * </p>
 */
public class FLVScriptDataValue extends FLVScriptData
        implements Cloneable {
    public UInt type;
    public FLVScriptData val;

    public FLVScriptDataValue() {
        this("", LIMIT_INVALID);
    }

    public FLVScriptDataValue(String n) {
        this(n, LIMIT_INVALID);
    }

    public FLVScriptDataValue(String n, long l) {
        super(n, l);

        type = new UInt();
        val = new FLVScriptData();
    }

    @Override
    public FLVScriptDataValue clone()
            throws CloneNotSupportedException {
        FLVScriptDataValue obj = (FLVScriptDataValue)super.clone();

        obj.type = (UInt)type.clone();
        obj.val = val.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "SCRIPTDATAVALUE";
    }

    @Override
    public void read(StreamReader<?> c) {
        read(c, this);
    }

    public static void read(StreamReader<?> c,
                            FLVScriptDataValue d) {
        c.enterBlock(d);

        d.type = c.readUInt( 8, d.type);

        d.val = FLVConsts.flvDatFactory.createPacketHeader(
                d.type.intValue());
        if (d.val == null) {
            throw new IllegalArgumentException("unknown FLVSCRIPTDATAVALUE "
                    + "type: " + d.type.intValue()
                    + "(" + d.getScriptDataTypeName() + ").");
        }
        d.val.setName("ScriptDataValue");
        d.val.setLimit(d.getLimit());
        d.val.read(c);

        c.leaveBlock();
    }

    @Override
    public void write(StreamWriter<?> c) {
        write(c, this);
    }

    public static void write(StreamWriter<?> c,
                             FLVScriptDataValue d) {
        c.enterBlock(d);

        c.writeUInt( 8, d.type, "Type", d.getScriptDataTypeName());
        d.val.write(c);

        c.leaveBlock();
    }

    public String getScriptDataTypeName() {
        return FLVConsts.getScriptDataTypeName(type.intValue());
    }
}
