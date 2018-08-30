package net.katsuster.strview.media.mp4;

import java.util.*;

import net.katsuster.strview.media.bit.*;
import net.katsuster.strview.util.bit.*;

/**
 * <p>
 * MPEG4 media file format の stss ボックス。
 * </p>
 *
 * <p>
 * 参考規格
 * </p>
 * <ul>
 * <li>ISO/IEC 14496-12: ISO base media file format</li>
 * </ul>
 */
public class MP4HeaderStss extends MP4HeaderFull
        implements Cloneable {
    public UInt entry_count;
    public List<MP4StssEntry> sample_number;

    public MP4HeaderStss() {
        entry_count = new UInt("entry_count");
        sample_number = new ArrayList<>();
    }

    @Override
    public MP4HeaderStss clone()
            throws CloneNotSupportedException {
        MP4HeaderStss obj =
                (MP4HeaderStss)super.clone();

        obj.entry_count = (UInt)entry_count.clone();

        obj.sample_number = new ArrayList<>();
        for (MP4StssEntry v : sample_number) {
            obj.sample_number.add(v.clone());
        }

        return obj;
    }

    @Override
    public boolean isRecursive() {
        return false;
    }

    @Override
    public void readBits(BitStreamReader c) {
        readBits(c, this);
    }

    public static void readBits(BitStreamReader c,
                                MP4HeaderStss d) {
        c.enterBlock(d);

        MP4HeaderFull.readBits(c, d);

        d.entry_count = c.readUInt(32, d.entry_count);

        d.sample_number = readObjectList(c, d.entry_count.intValue(), d.sample_number,
                MP4StssEntry.class, "sample_number");

        c.leaveBlock();
    }

    @Override
    public void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 MP4HeaderStss d) {
        c.enterBlock(d);

        MP4HeaderFull.writeBits(c, d);

        c.writeUInt(32, d.entry_count);

        writeObjectList(c, d.entry_count.intValue(), d.sample_number);

        c.leaveBlock();
    }
}
