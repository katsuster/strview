package net.katsuster.strview.media.test;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * 先頭にマーカーのあるパケットヘッダ。
 * </p>
 *
 * @author katsuhiro
 */
public class MarkedHeader extends BlockAdapter
        implements Cloneable {
    public UInt start_code;

    public MarkedHeader() {
        start_code = new UInt();
    }

    @Override
    public MarkedHeader clone()
            throws CloneNotSupportedException {
        MarkedHeader obj = (MarkedHeader)super.clone();

        obj.start_code = (UInt)start_code.clone();

        return obj;
    }

    @Override
    public void read(PacketReader<?> c) {
        read(c, this);
    }

    public static void read(PacketReader<?> c,
                            MarkedHeader d) {
        c.enterBlock("Marked header");

        d.start_code = c.readUInt(32, d.start_code);

        c.leaveBlock();
    }

    @Override
    public void write(PacketWriter<?> c) {
        write(c, this);
    }

    public static void write(PacketWriter<?> c,
                             MarkedHeader d) {
        c.enterBlock("Marked header");

        c.writeUInt(32, d.start_code, "start_code");

        c.leaveBlock();
    }
}
