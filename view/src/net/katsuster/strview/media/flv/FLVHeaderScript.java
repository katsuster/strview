package net.katsuster.strview.media.flv;

import net.katsuster.strview.media.*;
import net.katsuster.strview.util.*;

/**
 * <p>
 * SCRIPTDATA
 * </p>
 */
public class FLVHeaderScript<T extends LargeList<?>>
        extends FLVHeaderES<T>
        implements Cloneable {
    public FLVScriptDataValue name;
    public FLVScriptDataValue value;

    public FLVHeaderScript() {
        name = new FLVScriptDataValue("Name");
        value = new FLVScriptDataValue("Value");
    }

    @Override
    public FLVHeaderScript clone()
            throws CloneNotSupportedException {
        FLVHeaderScript obj = (FLVHeaderScript)super.clone();

        obj.name = name.clone();
        obj.value = value.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "FLV tag (Script)";
    }

    @Override
    public void read(StreamReader<?> c) {
        read(c, this);
    }

    public static void read(StreamReader<?> c,
                            FLVHeaderScript d) {
        c.enterBlock(d);

        FLVHeaderES.read(c, d);

        long limit = c.position() + (d.data_size.intValue() << 3);

        d.name.setLimit(limit);
        d.name.read(c);
        d.value.setLimit(limit);
        d.value.read(c);

        c.leaveBlock();
    }

    @Override
    public void write(StreamWriter<?> c) {
        write(c, this);
    }

    public static void write(StreamWriter<?> c,
                             FLVHeaderScript d) {
        c.enterBlock(d);

        FLVHeaderES.write(c, d);

        c.mark("Name", "");
        d.name.write(c);
        c.mark("Value", "");
        d.value.write(c);

        c.leaveBlock();
    }
}
