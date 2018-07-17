package net.katsuster.strview.media.flv;

import net.katsuster.strview.media.*;
import net.katsuster.strview.util.*;

/**
 * <p>
 * SCRIPTDATAOBJECTPROPERTY
 * </p>
 */
public class FLVScriptDataObjectProperty
        extends FLVScriptData
        implements Cloneable {
    public FLVScriptDataString property_name;
    public FLVScriptDataValue property_data;

    public FLVScriptDataObjectProperty() {
        this("");
    }

    public FLVScriptDataObjectProperty(String n) {
        super(n);

        property_name = new FLVScriptDataString("PropertyName");
        property_data = new FLVScriptDataValue("PropertyData");
    }

    @Override
    public FLVScriptDataObjectProperty clone()
            throws CloneNotSupportedException {
        FLVScriptDataObjectProperty obj = (FLVScriptDataObjectProperty)super.clone();

        obj.property_name = property_name.clone();
        obj.property_data = property_data.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "SCRIPTDATAOBJECTPROPERTY";
    }

    @Override
    protected void readBits(BitStreamReader c) {
        readBits(c, this);
    }

    public static void readBits(BitStreamReader c,
                                FLVScriptDataObjectProperty d) {
        c.enterBlock(d);

        FLVScriptData.readBits(c, d);

        d.property_name.setLimit(d.getLimit());
        d.property_name.read(c);
        d.property_data.setLimit(d.getLimit());
        d.property_data.read(c);

        c.leaveBlock();
    }

    @Override
    protected void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 FLVScriptDataObjectProperty d) {
        c.enterBlock(d);

        FLVScriptData.writeBits(c, d);

        d.property_name.write(c);
        d.property_data.write(c);

        c.leaveBlock();
    }
}
