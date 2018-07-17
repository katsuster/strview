package net.katsuster.strview.util;

/**
 * <p>
 * int 型で扱える長さを超えるリストの部分列を表します。
 * </p>
 */
public class SubLargeList<T> extends AbstractLargeListBase<T>
        implements LargeList<T>, Cloneable {
    /**
     * <p>
     * 指定された名前を持った部分列を作成します。
     * </p>
     *
     * @param n 名前
     */
    public SubLargeList(String n) {
        this(n, null, 0, 0);
    }

    /**
     * <p>
     * 指定されたリストの
     * from から len の長さの部分列を作成します。
     * </p>
     *
     * @param buf  リスト
     * @param from 部分列の開始点
     * @param len  部分列の長さ
     */
    public SubLargeList(LargeList<T> buf, long from, long len) {
        this(null, buf, from, len);
    }

    /**
     * <p>
     * 指定されたリストの
     * from から len の長さの部分列を作成します。
     * </p>
     *
     * @param n    名前
     * @param buf  リスト
     * @param from 部分列の開始点
     * @param len  部分列の長さ
     */
    public SubLargeList(String n, LargeList<T> buf, long from, long len) {
        super(n, len);

        long bufLen = 0;
        if (buf != null) {
            bufLen = buf.length();
        }

        if (from < 0 || from + len > bufLen) {
            throw new IndexOutOfBoundsException("from:" + from
                    + ", len:" + len + " is too large.");
        }

        getRange().setBuffer(buf);
        getRange().setStart(from);
    }

    /**
     * <p>
     * 指定されたリストの範囲から部分列を作成します。
     * </p>
     *
     * @param r リストの範囲
     */
    public SubLargeList(Range<LargeList<T>> r) {
        this(r.getBuffer(), r.getStart(), r.getLength());
    }

    @Override
    public Object clone()
            throws CloneNotSupportedException {
        SubLargeList<T> obj = (SubLargeList<T>)super.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "SubList";
    }

    @Override
    public T get(long index) {
        Range<? extends LargeList<T>> r = getRange();

        checkIndex(index);

        return r.getBuffer().get(index + r.getStart());
    }

    @Override
    public int get(long index, T[] dest, int offset, int length) {
        Range<? extends LargeList<T>> r = getRange();

        checkRemaining(index, length);

        return r.getBuffer().get(index + r.getStart(), dest, offset, length);
    }

    @Override
    public int get(long index, LargeList<T> dest, int offset, int length) {
        Range<? extends LargeList<T>> r = getRange();

        checkRemaining(index, length);

        return r.getBuffer().get(index + r.getStart(), dest, offset, length);
    }

    @Override
    public void set(long index, T data) {
        Range<? extends LargeList<T>> r = getRange();

        checkIndex(index);

        r.getBuffer().set(index + r.getStart(), data);
    }

    @Override
    public int set(long index, T[] src, int offset, int length) {
        Range<? extends LargeList<T>> r = getRange();

        checkRemaining(index, length);

        return r.getBuffer().set(index + r.getStart(), src, offset, length);
    }

    @Override
    public int set(long index, LargeList<T> src, int offset, int length) {
        Range<? extends LargeList<T>> r = getRange();

        checkRemaining(index, length);

        return r.getBuffer().set(index + r.getStart(), src, offset, length);
    }

    @Override
    public LargeList<T> subLargeList(long from, long len) {
        return new SubLargeList<>(this, from, len);
    }
}
