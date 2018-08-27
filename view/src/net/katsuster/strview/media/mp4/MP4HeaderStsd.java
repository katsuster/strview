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
 *
 * @author katsuhiro
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
    public void readBits(BitStreamReader b) {
        readBits(b, this);
    }

    public static void readBits(BitStreamReader b,
                                MP4HeaderStsd d) {
        MP4HeaderFull.readBits(b, d);

        d.entry_count = b.readUInt(32, d.entry_count);
    }

    @Override
    public void writeBits(BitStreamWriter b) {
        writeBits(b, this);
    }

    public static void writeBits(BitStreamWriter b,
                                 MP4HeaderStsd d) {
        MP4HeaderFull.writeBits(b, d);

        b.writeUInt(32, d.entry_count);
    }
}
