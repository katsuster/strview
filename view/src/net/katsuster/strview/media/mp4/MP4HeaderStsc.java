package net.katsuster.strview.media.mp4;

import java.util.*;

import net.katsuster.strview.media.bit.*;
import net.katsuster.strview.util.bit.*;

/**
 * <p>
 * MPEG4 media file format の stsc ボックス。
 * </p>
 *
 * <p>
 * 参考規格
 * </p>
 * <ul>
 * <li>ISO/IEC 14496-12: ISO base media file format</li>
 * </ul>
 */
public class MP4HeaderStsc extends MP4HeaderFull
        implements Cloneable {
    public UInt entry_count;
    public List<MP4StscEntry> entries;

    public MP4HeaderStsc() {
        entry_count = new UInt("entry_count");
        entries = new ArrayList<>();
    }

    @Override
    public MP4HeaderStsc clone()
            throws CloneNotSupportedException {
        MP4HeaderStsc obj =
                (MP4HeaderStsc)super.clone();

        obj.entry_count = (UInt)entry_count.clone();

        obj.entries = new ArrayList<>();
        for (MP4StscEntry v : entries) {
            obj.entries.add(v.clone());
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
                                MP4HeaderStsc d) {
        c.enterBlock(d);

        MP4HeaderFull.readBits(c, d);

        d.entry_count = c.readUInt(32, d.entry_count);

        d.entries = readObjectList(c, d.entry_count.intValue(), d.entries,
                MP4StscEntry.class, "entries");

        c.leaveBlock();
    }

    @Override
    public void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 MP4HeaderStsc d) {
        c.enterBlock(d);

        MP4HeaderFull.writeBits(c, d);

        c.writeUInt(32, d.entry_count);

        writeObjectList(c, d.entry_count.intValue(), d.entries);

        c.leaveBlock();
    }
}
