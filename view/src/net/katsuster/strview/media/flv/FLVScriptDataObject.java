package net.katsuster.strview.media.flv;

import java.util.*;

import net.katsuster.strview.media.*;
import net.katsuster.strview.util.*;

/**
 * <p>
 * SCRIPTDATAOBJECT
 * </p>
 */
public class FLVScriptDataObject
        extends FLVScriptData
        implements Cloneable {
    public List<FLVScriptDataObjectProperty> object_properties;
    //SCRIPTDATAOBJECT が Script タグの終端にある場合、省略されることがある
    public FLVScriptDataObjectEnd list_terminator;

    public FLVScriptDataObject() {
        this(LIMIT_INVALID);
    }

    public FLVScriptDataObject(long l) {
        super(l);

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
    public String getTypeName() {
        return "SCRIPTDATAOBJECT";
    }

    @Override
    protected void readBits(BitStreamReader c) {
        readBits(c, this);
    }

    public static void readBits(BitStreamReader c,
                                FLVScriptDataObject d) {
        c.enterBlock(d);

        FLVScriptData.readBits(c, d);

        d.object_properties.clear();
        while (!isTerminated(c, d)) {
            FLVScriptDataObjectProperty prop = new FLVScriptDataObjectProperty();
            prop.setLimit(d.getLimit());
            prop.read(c);
            d.object_properties.add(prop);
        }
        if (c.peekLong(24) == 0x000009) {
            d.list_terminator.read(c);
        }

        c.leaveBlock();
    }

    protected static boolean isTerminated(BitStreamReader c,
                                          FLVScriptDataObject d) {
        if (c.peekLong(24) == 0x000009) {
            return true;
        }
        if (d.getLimit() != LIMIT_INVALID && d.getLimit() <= c.position()) {
            return true;
        }

        return false;
    }

    @Override
    protected void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 FLVScriptDataObject d) {
        c.enterBlock(d);

        FLVScriptData.writeBits(c, d);

        writeObjectList(c, d.object_properties.size(),
                d.object_properties, "ObjectProperties");

        d.list_terminator.write(c);

        c.leaveBlock();
    }
}
