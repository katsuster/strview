package net.katsuster.strview.media.flv;

import net.katsuster.strview.media.*;

/**
 * <p>
 * SCRIPTDATA
 * </p>
 *
 * @author katsuhiro
 */
public class FLVHeaderScript extends FLVHeaderES
        implements Cloneable {
    public FLVScriptDataValue name;
    public FLVScriptDataValue value;

    public FLVHeaderScript() {
        name = new FLVScriptDataValue();
        value = new FLVScriptDataValue();
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
    public void read(PacketReader<?> c) {
        read(c, this);
    }

    public static void read(PacketReader<?> c,
                            FLVHeaderScript d) {
        c.enterBlock("FLV tag (Script)");

        FLVHeaderES.read(c, d);

        d.name.read(c);
        d.value.read(c);

        c.leaveBlock();
    }

    @Override
    public void write(PacketWriter<?> c) {
        write(c, this);
    }

    public static void write(PacketWriter<?> c,
                             FLVHeaderScript d) {
        c.enterBlock("FLV tag (Script)");

        FLVHeaderES.write(c, d);

        c.mark("Name", "");
        d.name.write(c);
        c.mark("Value", "");
        d.value.write(c);

        c.leaveBlock();
    }
}
