package net.katsuster.strview.media.flv;

import java.util.*;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * SCRIPTDATAOBJECT
 * </p>
 *
 * @author katsuhiro
 */
public class FLVScriptDataObject extends FLVScriptData
        implements Cloneable {
    public List<FLVScriptDataObjectProperty> object_properties;
    public FLVScriptDataObjectEnd list_terminator;

    public FLVScriptDataObject() {
        object_properties = new ArrayList<>();
        list_terminator = new FLVScriptDataObjectEnd();
    }

    @Override
    public FLVScriptDataObject clone()
            throws CloneNotSupportedException {
        FLVScriptDataObject obj = (FLVScriptDataObject)super.clone();

        obj.object_properties = new ArrayList<>();
        for (FLVScriptDataObjectProperty v : object_properties) {
            obj.object_properties.add(v.clone());
        }

        obj.list_terminator = list_terminator.clone();

        return obj;
    }

    @Override
    public void read(PacketReader<?> c) {
        read(c, this);
    }

    public static void read(PacketReader<?> c,
                            FLVScriptDataObject d) {
        c.enterBlock("SCRIPTDATAOBJECT");

        FLVScriptData.read(c, d);

        d.object_properties = new ArrayList<>();
        while (!isTerminated(c)) {
            FLVScriptDataObjectProperty prop = new FLVScriptDataObjectProperty();
            prop.read(c);
            d.object_properties.add(prop);
        }

        d.list_terminator.read(c);

        c.leaveBlock();
    }

    protected static boolean isTerminated(PacketReader<?> c) {
        return c.peekLong(24) == 0x000009;
    }

    @Override
    public void write(PacketWriter<?> c) {
        write(c, this);
    }

    public static void write(PacketWriter<?> c,
                             FLVScriptDataObject d) {
        c.enterBlock("SCRIPTDATAOBJECT");

        FLVScriptData.write(c, d);

        writeObjectList(c, d.object_properties.size(),
                d.object_properties, "ObjectProperties");

        d.list_terminator.write(c);

        c.leaveBlock();
    }
}
