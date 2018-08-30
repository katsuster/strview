package net.katsuster.strview.media.mp4;

import net.katsuster.strview.media.bit.*;
import net.katsuster.strview.util.bit.*;

/**
 * <p>
 * MPEG4 media file format の stsd ボックス。
 * </p>
 *
 * <p>
 * 参考規格
 * </p>
 * <ul>
 * <li>ISO/IEC 14496-12: ISO base media file format</li>
 * </ul>
 */
public class MP4HeaderStsd extends MP4HeaderFull
        implements Cloneable {
    public UInt entry_count;

    public MP4HeaderStsd() {
        entry_count = new UInt("entry_count");
    }

    @Override
    public MP4HeaderStsd clone()
            throws CloneNotSupportedException {
        MP4HeaderStsd obj = (MP4HeaderStsd)super.clone();

        obj.entry_count = (UInt)entry_count.clone();

        return obj;
    }

    @Override
    public boolean isRecursive() {
        return true;
    }

    @Override
    public void readBits(BitStreamReader c) {
        readBits(c, this);
    }

    public static void readBits(BitStreamReader c,
                                MP4HeaderStsd d) {
        c.enterBlock(d);

        MP4HeaderFull.readBits(c, d);

        d.entry_count = c.readUInt(32, d.entry_count);

        c.leaveBlock();
    }

    @Override
    public void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 MP4HeaderStsd d) {
        c.enterBlock(d);

        MP4HeaderFull.writeBits(c, d);

        c.writeUInt(32, d.entry_count);

        c.leaveBlock();
    }
}
