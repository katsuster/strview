package net.katsuster.strview.media.test;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * 固定長パケットヘッダ。
 * </p>
 *
 * @author katsuhiro
 */
public class FixedHeader extends BlockAdapter
        implements Cloneable {
    public UInt data_a;
    public UInt data_b;

    public FixedHeader() {
        data_a = new UInt();
        data_b = new UInt();
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
    public void read(PacketReader<?> c) {
        read(c, this);
    }

    public static void read(PacketReader<?> c,
                            FixedHeader d) {
        c.enterBlock("Fixed Size Packet header");

        d.data_a = c.readUInt( 3, d.data_a);
        d.data_b = c.readUInt( 5, d.data_b);

        c.leaveBlock();
    }

    @Override
    public void write(PacketWriter<?> c) {
        write(c, this);
    }

    public static void write(PacketWriter<?> c,
                             FixedHeader d) {
        c.enterBlock("Fixed Size Packet header");

        c.writeUInt( 3, d.data_a, "data_a");
        c.writeUInt( 5, d.data_b, "data_b");

        c.leaveBlock();
    }
}
