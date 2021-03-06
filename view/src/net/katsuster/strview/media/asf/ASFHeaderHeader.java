package net.katsuster.strview.media.asf;

import net.katsuster.strview.util.bit.*;
import net.katsuster.strview.media.bit.*;

/**
 * <p>
 * ASF (Advanced Systems Format) Header Object
 * </p>
 *
 * <p>
 * 参考規格
 * </p>
 * <ul>
 * <li>Advanced Systems Format (ASF) Specification: Revision 01.20.06</li>
 * </ul>
 */
public class ASFHeaderHeader
        extends ASFHeader
        implements Cloneable {
    public UIntR number_of_header_objects;
    public UIntR reserved1;
    public UIntR reserved2;

    public ASFHeaderHeader() {
        number_of_header_objects = new UIntR("Number of Header Objects");
        reserved1 = new UIntR("Reserved 1");
        reserved2 = new UIntR("Reserved 2");
    }

    @Override
    public ASFHeaderHeader clone()
            throws CloneNotSupportedException {
        ASFHeaderHeader obj = (ASFHeaderHeader)super.clone();

        obj.number_of_header_objects = (UIntR)number_of_header_objects.clone();
        obj.reserved1 = (UIntR)reserved1.clone();
        obj.reserved2 = (UIntR)reserved2.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "Header Object";
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
                                ASFHeaderHeader d) {
        c.enterBlock(d);

        ASFHeader.readBits(c, d);

        d.number_of_header_objects = c.readUIntR(32, d.number_of_header_objects);
        d.reserved1                = c.readUIntR( 8, d.reserved1               );
        d.reserved2                = c.readUIntR( 8, d.reserved2               );

        c.leaveBlock();
    }

    @Override
    protected void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 ASFHeaderHeader d) {
        c.enterBlock(d);

        ASFHeader.writeBits(c, d);

        c.writeUIntR(32, d.number_of_header_objects);
        c.writeUIntR( 8, d.reserved1               );
        c.writeUIntR( 8, d.reserved2               );

        c.leaveBlock();
    }
}
