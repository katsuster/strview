package net.katsuster.strview.media.flv;

import net.katsuster.strview.media.bit.*;

/**
 * <p>
 * SCRIPTDATA
 * </p>
 */
public class FLVHeaderScript
        extends FLVHeaderES
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
    protected void readBits(BitStreamReader c) {
        readBits(c, this);
    }

    public static void readBits(BitStreamReader c,
                                FLVHeaderScript d) {
        c.enterBlock(d);

        FLVHeaderES.readBits(c, d);

        long limit = c.position() + (d.data_size.intValue() << 3);

        d.name.setLimit(limit);
        d.name.read(c);
        d.value.setLimit(limit);
        d.value.read(c);

        c.leaveBlock();
    }

    @Override
    protected void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 FLVHeaderScript d) {
        c.enterBlock(d);

        FLVHeaderES.writeBits(c, d);

        c.mark("Name", "");
        d.name.write(c);
        c.mark("Value", "");
        d.value.write(c);

        c.leaveBlock();
    }
}
