package net.katsuster.strview.media;

import net.katsuster.strview.util.*;

/**
 * <p>
 * リストのインデックスを持ち、
 * リストの要素を読み取るクラスを容易に実装するための抽象クラスです。
 * </p>
 */
public abstract class AbstractStreamReader<T> extends AbstractStreamConverter<T>
        implements StreamReader<T> {
    public AbstractStreamReader() {
        super(null);
    }

    public AbstractStreamReader(LargeList<T> l) {
        super(l);
    }

    public AbstractStreamReader(LargeList<T> l, long p) {
        super(l, p);
    }

    @Override
    public T peek(T val) {
        long orgpos = position();
        T res = read(val);
        position(orgpos);
        return res;
    }

    @Override
    public T read(T val) {
        return read(val, null);
    }

    @Override
    public T peek(T val, String desc) {
        long orgpos = position();
        T res = read(val, desc);
        position(orgpos);
        return res;
    }

    @Override
    public LargeList<T> peekList(long n, LargeList<T> val) {
        long orgpos = position();
        LargeList<T> res = readList(n, val);
        position(orgpos);
        return res;
    }

    @Override
    public LargeList<T> readList(long n, LargeList<T> val) {
        return readList(n, val, null);
    }

    @Override
    public LargeList<T> peekList(long n, LargeList<T> val, String desc) {
        long orgpos = position();
        LargeList<T> res = readList(n, val, desc);
        position(orgpos);
        return res;
    }
}
