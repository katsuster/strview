package net.katsuster.strview.media.flv;

import java.util.*;

import net.katsuster.strview.media.*;
import net.katsuster.strview.util.*;

/**
 * <p>
 * SCRIPTDATAOBJECT
 * </p>
 */
public class FLVScriptDataObject<T extends LargeList<?>>
        extends FLVScriptData<T>
        implements Cloneable {
    public List<FLVScriptDataObjectProperty<T>> object_properties;
    //SCRIPTDATAOBJECT が Script タグの終端にある場合、省略されることがある
    public FLVScriptDataObjectEnd<T> list_terminator;

    public FLVScriptDataObject() {
        this(LIMIT_INVALID);
    }

    public FLVScriptDataObject(long l) {
        super(l);

        object_properties = new ArrayList<>();
        list_terminator = new FLVScriptDataObjectEnd<>();
    }

    @Override
    public FLVScriptDataObject<T> clone()
            throws CloneNotSupportedException {
        FLVScriptDataObject<T> obj = (FLVScriptDataObject<T>)super.clone();

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
    public void read(StreamReader<?, ?> c) {
        read(c, this);
    }

    public static void read(StreamReader<?, ?> c,
                            FLVScriptDataObject d) {
        c.enterBlock(d);

        FLVScriptData.read(c, d);

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

    protected static boolean isTerminated(StreamReader<?, ?> c,
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
    public void write(StreamWriter<?, ?> c) {
        write(c, this);
    }

    public static void write(StreamWriter<?, ?> c,
                             FLVScriptDataObject d) {
        c.enterBlock(d);

        FLVScriptData.write(c, d);

        writeObjectList(c, d.object_properties.size(),
                d.object_properties, "ObjectProperties");

        d.list_terminator.write(c);

        c.leaveBlock();
    }
}
