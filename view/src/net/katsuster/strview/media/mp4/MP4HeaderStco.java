package net.katsuster.strview.media.mp4;

import java.util.*;

import net.katsuster.strview.media.bit.*;
import net.katsuster.strview.util.bit.*;

/**
 * <p>
 * MPEG4 media file format の stco ボックス。
 * </p>
 *
 * <p>
 * 参考規格
 * </p>
 * <ul>
 * <li>ISO/IEC 14496-12: ISO base media file format</li>
 * </ul>
 */
public class MP4HeaderStco extends MP4HeaderFull
        implements Cloneable {
    public UInt entry_count;
    public List<UInt> chunk_offset;

    public MP4HeaderStco() {
        entry_count = new UInt("entry_count");
        chunk_offset = new ArrayList<>();
    }

    @Override
    public MP4HeaderStco clone()
            throws CloneNotSupportedException {
        MP4HeaderStco obj =
                (MP4HeaderStco)super.clone();

        obj.entry_count = (UInt)entry_count.clone();

        obj.chunk_offset = new ArrayList<>();
        for (UInt v : chunk_offset) {
            obj.chunk_offset.add((UInt)v.clone());
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
                                MP4HeaderStco d) {
        c.enterBlock(d);

        MP4HeaderFull.readBits(c, d);

        d.entry_count = c.readUInt(32, d.entry_count);

        d.chunk_offset.clear();
        for (int i = 0; i < d.entry_count.intValue(); i++) {
            UInt data = new UInt("chunk_offset[" + i + "]");
            data = c.readUInt(32, data);
            d.chunk_offset.add(data);
        }

        c.leaveBlock();
    }

    @Override
    public void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 MP4HeaderStco d) {
        c.enterBlock(d);

        MP4HeaderFull.writeBits(c, d);

        c.writeUInt(32, d.entry_count);

        for (int i = 0; i < d.entry_count.intValue(); i++) {
            c.writeUInt(32, d.chunk_offset.get(i));
        }

        c.leaveBlock();
    }
}
