package net.katsuster.strview.media.test;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * 固定長パケットヘッダ。
 * </p>
 */
public class FixedHeader
        extends BitBlockAdapter
        implements Cloneable {
    public UInt data_a;
    public UInt data_b;

    public FixedHeader() {
        data_a = new UInt("data_a");
        data_b = new UInt("data_b");
    }

    @Override
    public FixedHeader clone()
            throws CloneNotSupportedException {
        FixedHeader obj = (FixedHeader)super.clone();

        obj.data_a = (UInt) data_a.clone();
        obj.data_b = (UInt) data_b.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "Fixed Size Packet";
    }

    @Override
    protected void readBits(BitStreamReader c) {
        readBits(c, this);
    }

    public static void readBits(BitStreamReader c,
                                FixedHeader d) {
        c.enterBlock(d);

        d.data_a = c.readUInt( 3, d.data_a);
        d.data_b = c.readUInt( 5, d.data_b);

        c.leaveBlock();
    }

    @Override
    protected void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 FixedHeader d) {
        c.enterBlock(d);

        c.writeUInt( 3, d.data_a);
        c.writeUInt( 5, d.data_b);

        c.leaveBlock();
    }
}
