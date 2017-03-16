package net.katsuster.strview.media.mp4;

import net.katsuster.strview.media.*;

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
 *
 * @author katsuhiro
 */
public class MP4HeaderRecursiveFull extends MP4HeaderFull
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
    public void read(PacketReader<?> c) {
        read(c, this);
    }

    public static void read(PacketReader<?> c,
                            MP4HeaderRecursiveFull d) {
        c.enterBlock("FullBox (recursive)");

        MP4HeaderFull.read(c, d);

        c.leaveBlock();
    }

    @Override
    public void write(PacketWriter<?> c) {
        write(c, this);
    }

    public static void write(PacketWriter<?> c,
                             MP4HeaderRecursiveFull d) {
        c.enterBlock("FullBox (recursive)");

        MP4HeaderFull.write(c, d);

        c.leaveBlock();

    }
}
