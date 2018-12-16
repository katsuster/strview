package net.katsuster.strview.media.flv;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * SCRIPTDATAOBJECTEND
 * </p>
 */
public class FLVScriptDataObjectEnd extends FLVScriptData
        implements Cloneable {
    //shall be 0x00, 0x00, 0x09
    public UInt object_end_marker;

    public FLVScriptDataObjectEnd() {
        this("");
    }

    public FLVScriptDataObjectEnd(String n) {
        super(n);

        object_end_marker = new UInt();
    }

    @Override
    public FLVScriptDataObjectEnd clone()
            throws CloneNotSupportedException {
        FLVScriptDataObjectEnd obj = (FLVScriptDataObjectEnd)super.clone();

        obj.object_end_marker = (UInt)object_end_marker.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "SCRIPTDATAOBJECTEND";
    }

    @Override
    public void read(StreamReader<?> c) {
        read(c, this);
    }

    public static void read(StreamReader<?> c,
                            FLVScriptDataObjectEnd d) {
        c.enterBlock(d);

        FLVScriptData.read(c, d);

        d.object_end_marker = c.readUInt(24, d.object_end_marker);

        c.leaveBlock();
    }

    @Override
    public void write(StreamWriter<?> c) {
        write(c, this);
    }

    public static void write(StreamWriter<?> c,
                             FLVScriptDataObjectEnd d) {
        c.enterBlock(d);

        FLVScriptData.write(c, d);

        c.writeUInt(24, d.object_end_marker, "ObjectEndMarker");

        c.leaveBlock();
    }
}
