package net.katsuster.strview.media.rmff;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * NameValueProperty Structure
 * </p>
 *
 * <p>
 * 名前、値のタイプ、値、の 3つで構成される。
 * </p>
 */
public class NameValueProperty<T extends LargeList<?>>
        extends BlockAdapter<T>
        implements Cloneable {
    public UInt size;
    public UInt object_version;
    public UInt name_length;
    public LargeBitList name;
    public SInt type;
    public UInt value_length;
    public LargeBitList value_data;

    public NameValueProperty() {
        this(null);
    }

    public NameValueProperty(String n) {
        super(n);

        size           = new UInt("size"          );
        object_version = new UInt("object_version");
        name_length    = new UInt("name_length"   );
        name           = new SubLargeBitList("name");
        type           = new SInt("type"          );
        value_length   = new UInt("value_length"  );
        value_data     = new SubLargeBitList("value_data");
    }

    @Override
    public NameValueProperty<T> clone()
            throws CloneNotSupportedException {
        NameValueProperty<T> obj = (NameValueProperty<T>)super.clone();

        obj.size = (UInt)size.clone();
        obj.object_version = (UInt)object_version.clone();
        obj.name_length = (UInt)name_length.clone();
        obj.name = (LargeBitList)name.clone();
        obj.type = (SInt)type.clone();
        obj.value_length = (UInt)value_length.clone();
        obj.value_data = (LargeBitList)value_data.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return getTypeName(type.intValue());
    }

    public static String getTypeName(int id) {
        String name = "..unknown..";

        switch (id) {
        case TYPE.UINT:
            name = "32bit unsigned";
            break;
        case TYPE.BUFFER:
            name = "buffer";
            break;
        case TYPE.STRING:
            name = "string";
            break;
        default:
            //do nothing
            break;
        }

        return name;
    }

    @Override
    public void read(StreamReader<?, ?> c) {
        read(c, this);
    }

    public static void read(StreamReader<?, ?> c,
                            NameValueProperty d) {
        c.enterBlock(d);

        d.size           = c.readUInt(32, d.size          );
        d.object_version = c.readUInt(16, d.object_version);

        if (d.object_version.intValue() == 0) {
            d.name_length = c.readUInt( 8, d.name_length);
            checkNegative(d.name_length);
            d.name = c.readBitList(d.name_length.intValue() << 3, d.name);

            d.type = c.readSInt(32, d.type);

            d.value_length = c.readUInt(16, d.value_length);
            checkNegative(d.value_length);
            d.value_data = c.readBitList(d.value_length.intValue() << 3, d.value_data);
        }

        c.leaveBlock();
    }

    @Override
    public void write(StreamWriter<?, ?> c) {
        write(c, this);
    }

    public static void write(StreamWriter<?, ?> c,
                             NameValueProperty d) {
        c.enterBlock(d);

        c.writeUInt(32, d.size          );
        c.writeUInt(16, d.object_version);

        if (d.object_version.intValue() == 0) {
            c.writeUInt( 8, d.name_length);
            c.writeBitList(d.name_length.intValue() << 3, d.name, d.getNameName());

            c.writeSInt(32, d.type, d.getTypeName());

            c.writeUInt(16, d.value_length);
            if (d.type.intValue() == TYPE.STRING) {
                c.writeBitList(d.value_length.intValue() << 3, d.value_data, d.getValueDataName());
            } else {
                c.writeBitList(d.value_length.intValue() << 3, d.value_data);
            }
        }

        c.leaveBlock();
    }

    public String getNameName() {
        return getArrayName(name, "US-ASCII");
    }

    public static class TYPE {
        //[real] 32bit unsigned integer property
        public static final int UINT   = 0;
        //[real] buffer
        public static final int BUFFER = 1;
        //[real] string
        public static final int STRING = 2;
    }

    public String getValueDataName() {
        return getArrayName(value_data, "US-ASCII");
    }
}
