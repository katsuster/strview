package net.katsuster.strview.media.mp4;

import net.katsuster.strview.media.*;
import net.katsuster.strview.util.*;

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
public class MP4HeaderRecursiveFull<T extends LargeList<?>>
        extends MP4HeaderFull<T>
        implements Cloneable {
    public MP4HeaderRecursiveFull() {
    }

    @Override
    public MP4HeaderRecursiveFull<T> clone()
            throws CloneNotSupportedException {
        MP4HeaderRecursiveFull<T> obj = (MP4HeaderRecursiveFull<T>)super.clone();

        return obj;
    }

    @Override
    public boolean isRecursive() {
        return true;
    }

    @Override
    public void read(StreamReader<?> c) {
        read(c, this);
    }

    public static void read(StreamReader<?> c,
                            MP4HeaderRecursiveFull d) {
        c.enterBlock(d);

        MP4HeaderFull.read(c, d);

        c.leaveBlock();
    }

    @Override
    public void write(StreamWriter<?> c) {
        write(c, this);
    }

    public static void write(StreamWriter<?> c,
                             MP4HeaderRecursiveFull d) {
        c.enterBlock(d);

        MP4HeaderFull.write(c, d);

        c.leaveBlock();

    }
}
