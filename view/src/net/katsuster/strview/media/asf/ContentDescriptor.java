package net.katsuster.strview.media.asf;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * ASF (Advanced Systems Format) Extended Content Description Object
 * Content Descriptor
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
public class ContentDescriptor extends BlockAdapter
        implements Cloneable {
    public UIntR descriptor_name_length;
    public LargeBitList descriptor_name;
    public UIntR descriptor_value_data_type;
    public UIntR descriptor_value_length;
    public LargeBitList descriptor_value;

    public ContentDescriptor() {
        descriptor_name_length = new UIntR();
        descriptor_name = new SubLargeBitList();
        descriptor_value_data_type = new UIntR();
        descriptor_value_length = new UIntR();
        descriptor_value = new SubLargeBitList();
    }

    @Override
    public ContentDescriptor clone()
            throws CloneNotSupportedException {
        ContentDescriptor obj = (ContentDescriptor)super.clone();

        obj.descriptor_name_length = (UIntR)descriptor_name_length.clone();
        obj.descriptor_name = (LargeBitList)descriptor_name.clone();
        obj.descriptor_value_data_type = (UIntR)descriptor_value_data_type.clone();
        obj.descriptor_value_length = (UIntR)descriptor_value_length.clone();
        obj.descriptor_value = (LargeBitList)descriptor_value.clone();

        return obj;
    }

    @Override
    public void read(PacketReader<?> c) {
        read(c, this);
    }

    public static void read(PacketReader<?> c,
                            ContentDescriptor d) {
        c.enterBlock("Content Descriptor");

        d.descriptor_name_length     = c.readUIntR(16, d.descriptor_name_length    );
        checkNegative("Descriptor Name Length", d.descriptor_name_length);
        d.descriptor_name = c.readBitList(d.descriptor_name_length.intValue() << 3, d.descriptor_name);

        d.descriptor_value_data_type = c.readUIntR(16, d.descriptor_value_data_type);
        d.descriptor_value_length    = c.readUIntR(16, d.descriptor_value_length   );
        checkNegative("Descriptor Value Length", d.descriptor_value_length);
        d.descriptor_value = c.readBitList(d.descriptor_value_length.intValue() << 3, d.descriptor_value);

        c.leaveBlock();
    }

    @Override
    public void write(PacketWriter<?> c) {
        write(c, this);
    }

    public static void write(PacketWriter<?> c,
                             ContentDescriptor d) {
        c.enterBlock("Content Descriptor");

        c.writeUIntR(16, d.descriptor_name_length    , "Descriptor Name Length");
        c.writeBitList(d.descriptor_name_length.intValue() << 3, d.descriptor_name,
                "Descriptor Name", d.getDescriptorName());

        c.writeUIntR(16, d.descriptor_value_data_type, "Descriptor Value Data Type", d.getDescriptorDataTypeName());
        c.writeUIntR(16, d.descriptor_value_length   , "Descriptor Value Length");
        c.writeBitList(d.descriptor_value_length.intValue() << 3, d.descriptor_value,
                "");

        c.leaveBlock();
    }

    public String getDescriptorName() {
        return ASFHeader.getWcharName(descriptor_name);
    }

    public String getDescriptorDataTypeName() {
        return getDescriptorDataTypeName(descriptor_value_data_type.intValue());
    }

    public static String getDescriptorDataTypeName(int id) {
        String name = "..unknown..";

        //Microsoft ASF Specification
        switch (id) {
        case DESCRIPTOR_DATA_TYPE.UNICODE:
            name = "Unicode string";
            break;
        case DESCRIPTOR_DATA_TYPE.BYTE_ARRAY:
            name = "BYTE array";
            break;
        case DESCRIPTOR_DATA_TYPE.BOOL:
            name = "BOOL";
            break;
        case DESCRIPTOR_DATA_TYPE.DWORD:
            name = "DWORD";
            break;
        case DESCRIPTOR_DATA_TYPE.QWORD:
            name = "QWORD";
            break;
        case DESCRIPTOR_DATA_TYPE.WORD:
            name = "WORD";
            break;
        default:
            //do nothing
            break;
        }

        return name;
    }

    /**
     * 3.11 Extended Content Description Object (optional, one only)
     */
    public static class DESCRIPTOR_DATA_TYPE {
        //[microsoft] Unicode string
        public static final int UNICODE = 0x0000;
        //[microsoft] BYTE array
        public static final int BYTE_ARRAY = 0x0001;
        //[microsoft] BOOL
        public static final int BOOL = 0x0002;
        //[microsoft] DWORD
        public static final int DWORD = 0x0003;
        //[microsoft] QWORD
        public static final int QWORD = 0x0004;
        //[microsoft] WORD
        public static final int WORD = 0x005;
    }
}
