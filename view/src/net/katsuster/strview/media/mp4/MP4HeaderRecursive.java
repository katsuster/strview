package net.katsuster.strview.media.mp4;

import net.katsuster.strview.media.bit.*;

/**
 * <p>
 * MPEG4 media file format の親ボックス。
 * </p>
 *
 * <p>
 * 自身は何もメンバを持たず、子ボックスだけを持ちます。
 * </p>
 *
 * <p>
 * 参考規格
 * </p>
 * <ul>
 * <li>ISO/IEC 14496-12: ISO base media file format</li>
 * </ul>
 */
public class MP4HeaderRecursive
        extends MP4Header
        implements Cloneable {
    public MP4HeaderRecursive() {
    }

    @Override
    public MP4HeaderRecursive clone()
            throws CloneNotSupportedException {
        MP4HeaderRecursive obj = (MP4HeaderRecursive)super.clone();

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
                                MP4HeaderRecursive d) {
        c.enterBlock(d);

        MP4Header.readBits(c, d);

        c.leaveBlock();
    }

    @Override
    protected void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 MP4HeaderRecursive d) {
        c.enterBlock(d);

        MP4Header.writeBits(c, d);

        c.leaveBlock();
    }
}
