package net.katsuster.strview.media;

import net.katsuster.strview.util.*;

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
 */
public abstract class AbstractStreamConverter<T> implements StreamConverter<T> {
    LargeList<T> list;
    private long pos;

    public AbstractStreamConverter(LargeList<T> l) {
        this(l, 0);
    }

    public AbstractStreamConverter(LargeList<T> l, long p) {
        list = l;
        pos = p;
    }

    @Override
    public LargeList<T> getList() {
        return list;
    }

    @Override
    public void setList(LargeList<T> l) {
        list = l;
    }

    @Override
    public void enterPacket(Packet p) {
        if ("".equals(p.getName())) {
            enterPacket(p.getTypeName());
        } else {
            enterPacket(p.getTypeName() + ": " + p.getName());
        }
    }

    @Override
    public void enterBlock(Block b) {
        if ("".equals(b.getName())) {
            enterBlock(b.getTypeName());
        } else {
            enterBlock(b.getTypeName() + ": " + b.getName());
        }
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
    public long position() {
        return pos;
    }

    @Override
    public void position(long p) {
        pos = p;
    }

    @Override
    public boolean isAlign(int n) {
        return (position() % n) == 0;
    }

    @Override
    public void align(int n) {
        position(((position() + n - 1) / n) * n);
    }
}
