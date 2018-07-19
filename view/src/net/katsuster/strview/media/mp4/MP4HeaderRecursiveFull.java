package net.katsuster.strview.media.mp4;

import net.katsuster.strview.media.bit.*;

/**
 * <p>
 * MPEG4 media file format の親ボックス。
 * </p>
 *
 * <p>
 * FullBox のメンバ（バージョン、フラグ）以外は何もメンバを持たず、
 * 子ボックスだけを持ちます。
 * </p>
 *
 * <p>
 * 参考規格
 * </p>
 * <ul>
 * <li>ISO/IEC 14496-12: ISO base media file format</li>
 * </ul>
 */
public class MP4HeaderRecursiveFull
        extends MP4HeaderFull
        implements Cloneable {
    public MP4HeaderRecursiveFull() {
    }

    @Override
    public MP4HeaderRecursiveFull clone()
            throws CloneNotSupportedException {
        MP4HeaderRecursiveFull obj = (MP4HeaderRecursiveFull)super.clone();

        return obj;
    }

    @Override
    public boolean isRecursive() {
        return true;
    }

    @Override
    protected void readBits(BitStreamReader c) {
        readBits(c, this);
    }

    public static void readBits(BitStreamReader c,
                                MP4HeaderRecursiveFull d) {
        c.enterBlock(d);

        MP4HeaderFull.readBits(c, d);

        c.leaveBlock();
    }

    @Override
    protected void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 MP4HeaderRecursiveFull d) {
        c.enterBlock(d);

        MP4HeaderFull.writeBits(c, d);

        c.leaveBlock();

    }
}
