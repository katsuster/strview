package net.katsuster.strview.media.mp4;

import net.katsuster.strview.media.*;

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
 *
 * @author katsuhiro
 */
public class MP4HeaderRecursive extends MP4Header
        implements Cloneable {
    public MP4HeaderRecursive() {
        super();
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
    public void read(PacketReader<?> c) {
        read(c, this);
    }

    public static void read(PacketReader<?> c,
                            MP4HeaderRecursive d) {
        c.enterBlock("Box (recursive)");

        MP4Header.read(c, d);

        c.leaveBlock();
    }

    @Override
    public void write(PacketWriter<?> c) {
        write(c, this);
    }

    public static void write(PacketWriter<?> c,
                             MP4HeaderRecursive d) {
        c.enterBlock("Box (recursive)");

        MP4Header.write(c, d);

        c.leaveBlock();
    }
}
