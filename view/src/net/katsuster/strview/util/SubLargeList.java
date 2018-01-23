package net.katsuster.strview.util;

/**
 * <p>
 * int 型で扱える長さを超えるリストの部分列を表します。
 * </p>
 */
public class SubLargeList<T> extends AbstractLargeListBase<T>
        implements LargeList<T>, Cloneable {
    //元となるリスト
    private LargeList<T> list;
    //部分列の開始位置
    private long offset;

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
        super(len);

        if (from + len > buf.length()) {
            throw new IndexOutOfBoundsException("from:" + from
                    + ", len:" + len + " is too large.");
        }

        list = buf;
        offset = from;
    }

    @Override
    public Object clone()
            throws CloneNotSupportedException {
        SubLargeList<T> obj = (SubLargeList<T>)super.clone();

        obj.list = (SubLargeList<T>)list.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "SubList";
    }

    @Override
    public T get(long index) {
        checkIndex(index);

        return list.get(index + offset);
    }

    @Override
    public int get(long index, LargeList<T> dest, int offset, int length) {
        checkRemaining(index, length);

        return list.get(index + offset, dest, offset, length);
    }

    @Override
    public void set(long index, T data) {
        checkIndex(index);

        list.set(index + offset, data);
    }

    @Override
    public int set(long index, LargeList<T> src, int offset, int length) {
        checkRemaining(index, length);

        return list.set(index + offset, src, offset, length);
    }

    @Override
    public LargeList<T> subLargeList(long from, long len) {
        return new SubLargeList<T>(this, from, len);
    }
}
