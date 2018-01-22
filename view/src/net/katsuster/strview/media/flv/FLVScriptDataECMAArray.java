package net.katsuster.strview.media.flv;

import java.util.*;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * SCRIPTDATAECMAARRAY
 * </p>
 */
public class FLVScriptDataECMAArray<T extends LargeList<?>>
        extends FLVScriptData<T>
        implements Cloneable {
    public UInt ecma_array_length;
    public List<FLVScriptDataObjectProperty<T>> variables;
    //SCRIPTDATAECMAARRAY が Script タグの終端にある場合、省略されることがある
    public FLVScriptDataObjectEnd<T> list_terminator;

    public FLVScriptDataECMAArray() {
        ecma_array_length = new UInt();
        variables = new ArrayList<>();
        list_terminator = new FLVScriptDataObjectEnd<>("List Terminator");
    }

    @Override
    public FLVScriptDataECMAArray<T> clone()
            throws CloneNotSupportedException {
        FLVScriptDataECMAArray<T> obj =
                (FLVScriptDataECMAArray<T>)super.clone();

        obj.ecma_array_length = (UInt)ecma_array_length.clone();

        obj.variables = new ArrayList<>();
        for (FLVScriptDataObjectProperty<T> v : variables) {
            obj.variables.add(v.clone());
        }

        obj.list_terminator = list_terminator.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "SCRIPTDATAECMAARRAY";
    }

    @Override
    public void read(StreamReader<?> c) {
        read(c, this);
    }

    public static void read(StreamReader<?> c,
                            FLVScriptDataECMAArray d) {
        c.enterBlock(d);

        FLVScriptData.read(c, d);

        d.ecma_array_length = c.readUInt(32, d.ecma_array_length);

        d.variables.clear();
        for (int i = 0; i < d.ecma_array_length.intValue(); i++) {
            FLVScriptDataObjectProperty v = new FLVScriptDataObjectProperty("Variables[" + i + "]");
            v.setLimit(d.getLimit());
            v.read(c);
            d.variables.add(v);
        }
        if (c.peekLong(24) == 0x000009) {
            d.list_terminator.read(c);
        }

        c.leaveBlock();
    }

    @Override
    public void write(StreamWriter<?> c) {
        write(c, this);
    }

    public static void write(StreamWriter<?> c,
                             FLVScriptDataECMAArray d) {
        c.enterBlock(d);

        FLVScriptData.write(c, d);

        c.writeUInt(32, d.ecma_array_length, "ECMAArrayLength");

        writeObjectList(c, d.variables.size() /*d.ecma_array_length.intValue()*/,
                d.variables, "Variables");

        d.list_terminator.write(c);

        c.leaveBlock();
    }
}
