package net.katsuster.strview.media.flv;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * SCRIPTDATAVALUE
 * </p>
 *
 * @author katsuhiro
 */
public class FLVScriptDataValue extends FLVScriptData
        implements Cloneable {
    public UInt type;
    public FLVScriptData val;

    public FLVScriptDataValue() {
        this(LIMIT_INVALID);
    }

    public FLVScriptDataValue(long l) {
        super(l);

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
    public void read(PacketReader<?> c) {
        read(c, this);
    }

    public static void read(PacketReader<?> c,
                            FLVScriptDataValue d) {
        c.enterBlock("SCRIPTDATAVALUE");

        d.type = c.readUInt( 8, d.type);

        d.val = FLVConsts.flvDatFactory.createPacketHeader(
                d.type.intValue());
        if (d.val == null) {
            throw new IllegalArgumentException("unknown FLVSCRIPTDATAVALUE "
                    + "type: " + d.type.intValue()
                    + "(" + d.getScriptDataTypeName() + ").");
        }
        d.val.setLimit(d.getLimit());
        d.val.read(c);

        c.leaveBlock();
    }

    @Override
    public void write(PacketWriter<?> c) {
        write(c, this);
    }

    public static void write(PacketWriter<?> c,
                             FLVScriptDataValue d) {
        c.enterBlock("SCRIPTDATAVALUE");

        c.writeUInt( 8, d.type, "Type", d.getScriptDataTypeName());
        d.val.write(c);

        c.leaveBlock();
    }

    public String getScriptDataTypeName() {
        return FLVConsts.getScriptDataTypeName(type.intValue());
    }
}
