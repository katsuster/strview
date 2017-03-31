package net.katsuster.strview.media.asf;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * ASF (Advanced Systems Format) Header Extension Object
 * </p>
 *
 * <p>
 * 参考規格
 * </p>
 * <ul>
 * <li>Advanced Systems Format (ASF) Specification: Revision 01.20.06</li>
 * </ul>
 *
 * @author katsuhiro
 */
public class ASFHeaderHeaderExtension extends ASFHeader
        implements Cloneable {
    public ASFGUID reserved_field1;
    public UInt reserved_field2;
    public UInt header_extension_data_size;
    //下記はパケットの body として扱う
    //public ByteArray header_extension_data;

    public ASFHeaderHeaderExtension() {
        reserved_field1 = new ASFGUID();
        reserved_field2 = new UInt();
        header_extension_data_size = new UInt();
    }

    @Override
    public ASFHeaderHeaderExtension clone()
            throws CloneNotSupportedException {
        ASFHeaderHeaderExtension obj = (ASFHeaderHeaderExtension)super.clone();

        obj.reserved_field1 = reserved_field1.clone();
        obj.reserved_field2 = (UInt)reserved_field2.clone();
        obj.header_extension_data_size = (UInt)header_extension_data_size.clone();

        return obj;
    }

    @Override
    public boolean isRecursive() {
        return true;
    }

    @Override
    public void read(PacketReader<?> c) {
        read(c, this);
    }

    public static void read(PacketReader<?> c,
                            ASFHeaderHeaderExtension d) {
        ASFHeader.read(c, d);

        c.mark("Reserved Field 1", "");
        d.reserved_field1.read(c);
        d.reserved_field2            = c.readUIntR(16, d.reserved_field2           );
        d.header_extension_data_size = c.readUIntR(32, d.header_extension_data_size);
        checkNegative("Header Extension Data Size", d.header_extension_data_size);
    }

    @Override
    public void write(PacketWriter<?> c) {
        write(c, this);
    }

    public static void write(PacketWriter<?> c,
                             ASFHeaderHeaderExtension d) {
        ASFHeader.write(c, d);

        c.mark("Reserved Field 1", "");
        d.reserved_field1.write(c);
        c.writeUIntR(16, d.reserved_field2           , "Reserved Field 2");
        c.writeUIntR(32, d.header_extension_data_size, "Header Extension Data Size");
    }
}
