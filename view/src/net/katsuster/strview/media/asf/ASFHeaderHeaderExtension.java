package net.katsuster.strview.media.asf;

import net.katsuster.strview.util.bit.*;
import net.katsuster.strview.media.bit.*;

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
public class ASFHeaderHeaderExtension
        extends ASFHeader
        implements Cloneable {
    public ASFGUID reserved_field1;
    public UIntR reserved_field2;
    public UIntR header_extension_data_size;
    //下記はパケットの body として扱う
    //public ByteArray header_extension_data;

    public ASFHeaderHeaderExtension() {
        reserved_field1 = new ASFGUID("Reserved Field 1");
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
    protected void readBits(BitStreamReader c) {
        readBits(c, this);
    }

    public static void readBits(BitStreamReader c,
                                ASFHeaderHeaderExtension d) {
        c.enterBlock(d);

        ASFHeader.readBits(c, d);

        c.mark("Reserved Field 1", "");
        d.reserved_field1.read(c);
        d.reserved_field2            = c.readUIntR(16, d.reserved_field2           );
        d.header_extension_data_size = c.readUIntR(32, d.header_extension_data_size);
        checkNegative(d.header_extension_data_size);

        c.leaveBlock();
    }

    @Override
    protected void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 ASFHeaderHeaderExtension d) {
        c.enterBlock(d);

        ASFHeader.writeBits(c, d);

        c.mark("Reserved Field 1", "");
        d.reserved_field1.write(c);
        c.writeUIntR(16, d.reserved_field2           );
        c.writeUIntR(32, d.header_extension_data_size);

        c.leaveBlock();
    }
}
