package net.katsuster.strview.media;

import net.katsuster.strview.util.*;

/**
 * リストのインデックスを持ち、
 * リストの要素を書き込むクラスを容易に実装するための抽象クラスです。
 * </p>
 */
public abstract class AbstractStreamWriter<T> extends AbstractStreamConverter<T>
        implements StreamWriter<T> {
    public AbstractStreamWriter() {
        super(null);
    }

    public AbstractStreamWriter(LargeList<T> l) {
        super(l);
    }

    public AbstractStreamWriter(LargeList<T> l, long p) {
        super(l, p);
    }

    @Override
    public void poke(T val) {
        long orgpos = position();
        write(val);
        position(orgpos);
    }

    @Override
    public void write(T val) {
        write(val, null);
    }

    @Override
    public void poke(T val, String desc) {
        long orgpos = position();
        write(val, desc);
        position(orgpos);
    }

    @Override
    public void pokeList(long n, LargeList<T> val) {
        long orgpos = position();
        writeList(n, val);
        position(orgpos);
    }

    @Override
    public void writeList(long n, LargeList<T> val) {
        writeList(n, val, null);
    }

    @Override
    public void pokeList(long n, LargeList<T> val, String desc) {
        long orgpos = position();
        writeList(n, val, desc);
        position(orgpos);
    }
}
