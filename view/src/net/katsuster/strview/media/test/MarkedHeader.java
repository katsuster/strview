package net.katsuster.strview.media.test;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * 先頭にマーカーのあるパケットヘッダ。
 * </p>
 */
public class MarkedHeader
        extends BitBlockAdapter
        implements Cloneable {
    public UInt start_code;

    public MarkedHeader() {
        start_code = new UInt("start_code");
    }

    @Override
    public MarkedHeader clone()
            throws CloneNotSupportedException {
        MarkedHeader obj = (MarkedHeader)super.clone();

        obj.start_code = (UInt)start_code.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "Marked Packet";
    }

    @Override
    protected void readBits(BitStreamReader c) {
        readBits(c, this);
    }

    public static void readBits(BitStreamReader c,
                                MarkedHeader d) {
        c.enterBlock(d);

        d.start_code = c.readUInt(32, d.start_code);

        c.leaveBlock();
    }

    @Override
    protected void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 MarkedHeader d) {
        c.enterBlock(d);

        c.writeUInt(32, d.start_code);

        c.leaveBlock();
    }
}
