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
public class FLVScriptDataValue extends BlockAdapter
        implements Cloneable {
    public UInt type;
    public FLVScriptData val;

    public FLVScriptDataValue() {
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
        d.type = c.readUInt( 8, d.type);

        //FIXME: REFERENCE と LONGSTRING は未対応
        if (d.type.intValue() == FLVConsts.SCRIPT_DATA_TYPE.REFERENCE
                || d.type.intValue() == FLVConsts.SCRIPT_DATA_TYPE.LONGSTRING) {
            throw new IllegalArgumentException("unknown FLVSCRIPTDATAVALUE "
                    + "type: " + d.type.intValue() + ".");
        }

        d.val = FLVConsts.flvDatFactory.createPacketHeader(
                d.type.intValue());
        if (d.val == null) {
            d.val = new FLVScriptData();
        }
        d.val.read(c);
    }

    @Override
    public void write(PacketWriter<?> c) {
        write(c, this);
    }

    public static void write(PacketWriter<?> c,
                             FLVScriptDataValue d) {
        c.writeUInt( 8, d.type, "Type", d.getScriptDataTypeName());

        d.val.write(c);
    }

    public String getScriptDataTypeName() {
        return FLVConsts.getScriptDataTypeName(type.intValue());
    }
}
