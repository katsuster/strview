package net.katsuster.strview.media.mkv;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * Matroska unsigned integer
 * </p>
 *
 * @author katsuhiro
 */
public class MKVHeaderUInt extends MKVHeader {
    public UInt uint_val;

    public MKVHeaderUInt() {
        uint_val = new UInt();
    }

    @Override
    public MKVHeaderUInt clone()
            throws CloneNotSupportedException {
        MKVHeaderUInt obj = (MKVHeaderUInt)super.clone();

        obj.uint_val = uint_val.clone();

        return obj;
    }

    @Override
    public boolean isMaster() {
        return false;
    }

    @Override
    public void read(PacketReader<?> c) {
        read(c, this);
    }

    public static void read(PacketReader<?> c,
                            MKVHeaderUInt d) {
        c.enterBlock("Matroska uint");

        MKVHeader.read(c, d);

        d.uint_val = c.readUInt((int)d.tag_len.getValue() << 3, d.uint_val);

        c.leaveBlock();
    }

    @Override
    public void write(PacketWriter<?> c) {
        write(c, this);
    }

    public static void write(PacketWriter<?> c,
                             MKVHeaderUInt d) {
        c.enterBlock("Matroska uint");

        MKVHeader.write(c, d);

        c.writeUInt((int)d.tag_len.getValue() << 3, d.uint_val, "uint_val");

        c.leaveBlock();
    }
}
