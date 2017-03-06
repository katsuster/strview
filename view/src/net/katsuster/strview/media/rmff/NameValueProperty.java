package net.katsuster.strview.media.rmff;

import net.katsuster.strview.util.*;
import net.katsuster.strview.io.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * NameValueProperty Structure
 * </p>
 *
 * <p>
 * 名前、値のタイプ、値、の 3つで構成される。
 * </p>
 *
 * @author katsuhiro
 */
public class NameValueProperty extends BlockAdapter
        implements Cloneable {
    public UInt size;
    public UInt object_version;

    public UInt name_length;
    public LargeBitList name;
    public SInt type;
    public UInt value_length;
    public LargeBitList value_data;

    public NameValueProperty() {
        super();

        size = new UInt();
        object_version = new UInt();

        name_length = new UInt();
        name = new MemoryBitList();
        type = new SInt();
        value_length = new UInt();
        value_data = new MemoryBitList();
    }

    @Override
    public NameValueProperty clone()
            throws CloneNotSupportedException {
        NameValueProperty obj = (NameValueProperty)super.clone();

        obj.size = size.clone();
        obj.object_version = object_version.clone();

        obj.name_length = name_length.clone();
        obj.name = name;
        obj.type = type.clone();
        obj.value_length = value_length.clone();
        obj.value_data = value_data;

        return obj;
    }

    @Override
    public void read(PacketReader<?> c) {
        read(c, this);
    }

    public static void read(PacketReader<?> c,
                            NameValueProperty d) {
        c.enterBlock("NameValueProperty");

        d.size           = c.readUInt(32, d.size          );
        d.object_version = c.readUInt(16, d.object_version);

        if (d.object_version.intValue() == 0) {
            d.name_length = c.readUInt( 8, d.name_length);
            RMFFHeader.checkNegative("name_length", d.name_length);
            d.name = c.readBitList(d.name_length.intValue() << 3, d.name);

            d.type = c.readSInt(32, d.type);

            d.value_length = c.readUInt(16, d.value_length);
            RMFFHeader.checkNegative("value_length", d.value_length);
            d.value_data = c.readBitList(d.value_length.intValue() << 3, d.value_data);
        }

        c.leaveBlock();
    }

    @Override
    public void write(PacketWriter<?> c) {
        write(c, this);
    }

    public static void write(PacketWriter<?> c,
                             NameValueProperty d) {
        c.enterBlock("NameValueProperty");

        c.writeUInt(32, d.size          , "size"          );
        c.writeUInt(16, d.object_version, "object_version");

        if (d.object_version.intValue() == 0) {
            c.writeUInt( 8, d.name_length, "name_length");
            c.writeBitList(d.name_length.intValue() << 3, d.name,
                    "name", d.getNameName());

            c.writeSInt(32, d.type, "type", d.getTypeName());

            c.writeUInt(16, d.value_length, "value_length");
            if (d.type.intValue() == TYPE.STRING) {
                c.writeBitList(d.value_length.intValue() << 3, d.value_data,
                        "value_data", d.getValueDataName());
            } else {
                c.writeBitList(d.value_length.intValue() << 3, d.value_data,
                        "value_data");
            }
        }

        c.leaveBlock();
    }

    public String getNameName() {
        return RMFFHeader.getArrayName(name);
    }

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

    public static class TYPE {
        //[real] 32bit unsigned integer property
        public static final int UINT   = 0;
        //[real] buffer
        public static final int BUFFER = 1;
        //[real] string
        public static final int STRING = 2;
    }

    public String getValueDataName() {
        return RMFFHeader.getArrayName(value_data);
    }
}
