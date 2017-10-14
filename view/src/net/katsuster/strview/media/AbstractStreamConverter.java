package net.katsuster.strview.media;

/**
 * <p>
 * 何もしないコンバータクラスです。
 * </p>
 *
 * <p>
 * 下記の特徴を持ちます。
 * </p>
 *
 * <ul>
 *     <li>位置は常に 0</li>
 *     <li>マークは全て無視する</li>
 *     <li>変換対象は全て無視する</li>
 *     <li>結果は常に null を返す</li>
 * </ul>
 *
 * @author katsuhiro
 */
public abstract class AbstractStreamConverter<T> implements StreamConverter<T> {
    public AbstractStreamConverter() {
        //do nothing
    }

    @Override
    public void mark(String name, String s) {
        mark(name, s, null);
    }

    @Override
    public void mark(String name, Number n) {
        mark(name, n, null);
    }

    @Override
    public boolean isAlignByte() {
        return (position() & 0x7) == 0;
    }

    @Override
    public void alignByte() {
        position((position() + 0x7) & ~0x7);
    }

    @Override
    public boolean isAlignShort() {
        return (position() & 0xf) == 0;
    }

    @Override
    public void alignShort() {
        position((position() + 0xf) & ~0xf);
    }

    @Override
    public boolean isAlignInt() {
        return (position() & 0x1f) == 0;
    }

    @Override
    public void alignInt() {
        position((position() + 0x1f) & ~0x1f);
    }

    @Override
    public boolean isAlignLong() {
        return (position() & 0x3f) == 0;
    }

    @Override
    public void alignLong() {
        position((position() + 0x3f) & ~0x3f);
    }
}
