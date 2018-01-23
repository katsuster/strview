package net.katsuster.strview.media.mp4;

import net.katsuster.strview.media.*;
import net.katsuster.strview.util.*;

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
public class MP4HeaderRecursive<T extends LargeList<?>>
        extends MP4Header<T>
        implements Cloneable {
    public MP4HeaderRecursive() {
    }

    @Override
    public MP4HeaderRecursive<T> clone()
            throws CloneNotSupportedException {
        MP4HeaderRecursive<T> obj = (MP4HeaderRecursive<T>)super.clone();

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
                            MP4HeaderRecursive d) {
        c.enterBlock(d);

        MP4Header.read(c, d);

        c.leaveBlock();
    }

    @Override
    public void write(StreamWriter<?> c) {
        write(c, this);
    }

    public static void write(StreamWriter<?> c,
                             MP4HeaderRecursive d) {
        c.enterBlock(d);

        MP4Header.write(c, d);

        c.leaveBlock();
    }
}
