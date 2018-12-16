package net.katsuster.strview.media.flv;

import net.katsuster.strview.media.*;

/**
 * <p>
 * SCRIPTDATAOBJECTPROPERTY
 * </p>
 */
public class FLVScriptDataObjectProperty extends FLVScriptData
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
    public void read(StreamReader<?> c) {
        read(c, this);
    }

    public static void read(StreamReader<?> c,
                            FLVScriptDataObjectProperty d) {
        c.enterBlock(d);

        FLVScriptData.read(c, d);

        d.property_name.setLimit(d.getLimit());
        d.property_name.read(c);
        d.property_data.setLimit(d.getLimit());
        d.property_data.read(c);

        c.leaveBlock();
    }

    @Override
    public void write(StreamWriter<?> c) {
        write(c, this);
    }

    public static void write(StreamWriter<?> c,
                             FLVScriptDataObjectProperty d) {
        c.enterBlock(d);

        FLVScriptData.write(c, d);

        d.property_name.write(c);
        d.property_data.write(c);

        c.leaveBlock();
    }
}
