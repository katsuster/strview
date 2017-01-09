package net.katsuster.strview.util;

/**
 * <p>
 * int 型で扱える長さを超えるリストの部分列を表します。
 * </p>
 *
 * @author katsuhiro
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
     * from から to まで（to は含まない）の部分列を作成します。
     * </p>
     *
     * @param buf  リスト
     * @param from 部分列の開始点
     * @param to   部分列の終了点
     */
    public SubLargeList(LargeList<T> buf, long from, long to) {
        super(from, to);

        if (to > buf.length()) {
            throw new IndexOutOfBoundsException("to:" + to
                    + " is too large.");
        }

        list = buf;
        offset = from;
    }

    @Override
    public SubLargeList<T> clone()
            throws CloneNotSupportedException {
        SubLargeList<T> obj = (SubLargeList<T>)super.clone();

        obj.list = list;
        obj.offset = offset;

        return obj;
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
    public LargeList<T> subLargeList(long from, long to) {
        return new SubLargeList<T>(this, from, to);
    }
}
