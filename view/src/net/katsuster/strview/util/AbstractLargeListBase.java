package net.katsuster.strview.util;

import java.util.AbstractList;

/**
 * <p>
 * int 型で扱える長さを超えるリストの共通動作を定義します。
 * </p>
 *
 * @author katsuhiro
 */
public abstract class AbstractLargeListBase<T> extends AbstractList<T>
        implements LargeList<T>, Cloneable {
    //リストの長さ
    private long len;
    //リストの位置のヒント
    private long offsetHint;
    //リストが存在する範囲
    private Range r;

    /**
     * <p>
     * 指定された長さのリストを作成します。
     * </p>
     *
     * @param from リストの開始点
     * @param l    リストの長さ
     */
    public AbstractLargeListBase(long from, long l) {
        if (from < 0) {
            throw new IndexOutOfBoundsException("from:" + from
                    + " is negative.");
        }
        if (l < 0) {
            throw new IllegalArgumentException("len:" + l
                    + " is negative.");
        }
        len = l;
        r = new SimpleRange(from, l);
    }

    /**
     * <p>
     * 指定された長さのリストを作成します。
     * </p>
     *
     * @param l リストの長さ
     */
    public AbstractLargeListBase(long l) {
        if (l < 0 && l != LENGTH_UNKNOWN) {
            throw new NegativeArraySizeException("len:" + l
                    + " is negative.");
        }
        len = l;
        r = new SimpleRange(0, l);
    }

    @Override
    public Object clone()
            throws CloneNotSupportedException {
        AbstractLargeListBase obj = (AbstractLargeListBase)super.clone();

        obj.r = (Range)r.clone();

        return obj;
    }

    @Override
    public int size() {
        if (length() > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        } else {
            return (int) length();
        }
    }

    @Override
    public T get(int index) {
        return get((long)index);
    }

    @Override
    public T set(int index, T element) {
        T before = get(index);
        set((long)index, element);
        return before;
    }

    @Override
    public void count() {

    }

    @Override
    public long length() {
        return len;
    }

    @Override
    public void length(long l) {
        len = l;
    }

    @Override
    public long getOffsetHint() {
        return offsetHint;
    }

    @Override
    public void setOffsetHint(long h) {
        offsetHint = h;
    }

    @Override
    public LargeList<T> subLargeList(long from, long len) {
        return new SubLargeList<T>(this, from, len);
    }

    @Override
    public Range getRange() {
        return r;
    }

    @Override
    public void setRange(Range range) {
        r = range;
    }

    /**
     * <p>
     * 指定された位置がリストの範囲内か確認します。
     * </p>
     *
     * <p>
     * 指定された位置がリストの範囲内であれば何もしません。
     * 範囲外であれば例外をスローします。
     * </p>
     *
     * @param index 位置
     * @throws IndexOutOfBoundsException 指定された位置がリストの範囲外の場合
     */
    protected void checkIndex(long index) {
        if (index < 0 || (length() != LENGTH_UNKNOWN && length() <= index)) {
            throw new IndexOutOfBoundsException("index:" + index
                    + " is negative or too large.");
        }
    }

    /**
     * <p>
     * 指定された位置から終端までの要素数を取得します。
     * </p>
     *
     * @param index 位置
     * @return 指定された位置から終端までの要素数
     */
    protected long remaining(long index) {
        return length() - index;
    }

    /**
     * <p>
     * 指定された位置から n 要素読み出せるかどうか確認します。
     * </p>
     *
     * <p>
     * n 要素読み出し可能であれば何もしません。
     * 読みだし不可能であれば例外をスローします。
     * </p>
     *
     * @param index 読み出しを開始する位置
     * @param n 読み出す要素数
     * @throws IndexOutOfBoundsException n 要素読み出したとき、
     * リストの範囲外へのアクセスが発生する場合
     */
    protected void checkRemaining(long index, int n) {
        if (n > remaining(index)) {
            throw new IndexOutOfBoundsException("buffer is underflow, "
                    + "required(" + n + ") is exceeded "
                    + "remaining(" + remaining(index) + ").");
        }
    }
}
