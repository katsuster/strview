package net.katsuster.strview.media.mp4;

import net.katsuster.strview.media.bit.*;
import net.katsuster.strview.util.bit.*;

/**
 * <p>
 * MPEG4 media file format の dref ボックス。
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
public class MP4HeaderDref extends MP4HeaderFull
        implements Cloneable {
    public UInt entry_count;

    public MP4HeaderDref() {
        entry_count = new UInt("entry_count");
    }

    @Override
    public MP4HeaderDref clone()
            throws CloneNotSupportedException {
        MP4HeaderDref obj = (MP4HeaderDref)super.clone();

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
                                MP4HeaderDref d) {
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
                                 MP4HeaderDref d) {
        c.enterBlock(d);

        MP4HeaderFull.writeBits(c, d);

        c.writeUInt(32, d.entry_count);

        c.leaveBlock();
    }
}
