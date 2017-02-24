package net.katsuster.strview.media.mkv;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * Matroska signed integer
 * </p>
 *
 * @author katsuhiro
 */
public class MKVHeaderSInt extends MKVHeader {
    public SInt sint_val;

    public MKVHeaderSInt() {
        sint_val = new SInt();
    }

    @Override
    public MKVHeaderSInt clone()
            throws CloneNotSupportedException {
        MKVHeaderSInt obj = (MKVHeaderSInt)super.clone();

        obj.sint_val = sint_val.clone();

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
                            MKVHeaderSInt d) {
        c.enterBlock("Matroska int");

        MKVHeader.read(c, d);

        d.sint_val = c.readSInt((int)d.tag_len.getValue() << 3, d.sint_val);

        c.leaveBlock();
    }

    @Override
    public void write(PacketWriter<?> c) {
        write(c, this);
    }

    public static void write(PacketWriter<?> c,
                             MKVHeaderSInt d) {
        c.enterBlock("Matroska int");

        MKVHeader.write(c, d);

        c.writeSInt((int)d.tag_len.getValue() << 3, d.sint_val, "sint_val");

        c.leaveBlock();
    }
}
