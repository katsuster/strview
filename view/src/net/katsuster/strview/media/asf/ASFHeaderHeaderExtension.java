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
 */
public class ASFHeaderHeaderExtension<T extends LargeList<?>>
        extends ASFHeader<T>
        implements Cloneable {
    public ASFGUID<T> reserved_field1;
    public UIntR reserved_field2;
    public UIntR header_extension_data_size;
    //下記はパケットの body として扱う
    //public ByteArray header_extension_data;

    public ASFHeaderHeaderExtension() {
        reserved_field1 = new ASFGUID<>("Reserved Field 1");
        reserved_field2 = new UIntR("Reserved Field 2");
        header_extension_data_size = new UIntR("Header Extension Data Size");
    }

    @Override
    public ASFHeaderHeaderExtension clone()
            throws CloneNotSupportedException {
        ASFHeaderHeaderExtension obj = (ASFHeaderHeaderExtension)super.clone();

        obj.reserved_field1 = reserved_field1.clone();
        obj.reserved_field2 = (UIntR)reserved_field2.clone();
        obj.header_extension_data_size = (UIntR)header_extension_data_size.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "Header Extension Object";
    }

    @Override
    public boolean isRecursive() {
        return true;
    }

    @Override
    public void read(StreamReader<?> c) {
        read(c, this);
    }

    public static void read(StreamReader<?> c,
                            ASFHeaderHeaderExtension d) {
        c.enterBlock(d);

        ASFHeader.read(c, d);

        c.mark("Reserved Field 1", "");
        d.reserved_field1.read(c);
        d.reserved_field2            = c.readUIntR(16, d.reserved_field2           );
        d.header_extension_data_size = c.readUIntR(32, d.header_extension_data_size);
        checkNegative(d.header_extension_data_size);

        c.leaveBlock();
    }

    @Override
    public void write(StreamWriter<?> c) {
        write(c, this);
    }

    public static void write(StreamWriter<?> c,
                             ASFHeaderHeaderExtension d) {
        c.enterBlock(d);

        ASFHeader.write(c, d);

        c.mark("Reserved Field 1", "");
        d.reserved_field1.write(c);
        c.writeUIntR(16, d.reserved_field2           );
        c.writeUIntR(32, d.header_extension_data_size);

        c.leaveBlock();
    }
}
