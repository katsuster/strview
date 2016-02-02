package net.katsuster.strview.io;

import java.util.AbstractList;

/**
 * <p>
 * int 型で扱える長さを超える配列の共通動作を定義します。
 * </p>
 *
 * @author katsuhiro
 */
public abstract class AbstractLargeByteList extends AbstractList<Byte>
        implements LargeByteList, Cloneable {
    //配列の長さ（バイト単位）
    private long len;

    /**
     * <p>
     * 指定された長さのバイト列を作成します。
     * </p>
     *
     * @param from バイト列の開始点
     * @param to   バイト列の終了点
     */
    public AbstractLargeByteList(long from, long to) {
        if (from < 0) {
            throw new IndexOutOfBoundsException("from:" + from
                    + " is negative.");
        }
        if (from > to) {
            throw new IllegalArgumentException("'from' is larger than 'to'"
                    + "(from:" + from + " > to:" + to + ").");
        }
        len = to - from;
    }

    /**
     * <p>
     * 指定された長さのバイト列を作成します。
     * </p>
     *
     * @param l バイト列の長さ
     */
    public AbstractLargeByteList(long l) {
        if (l < 0) {
            throw new NegativeArraySizeException("len:" + l
                    + " is negative.");
        }
        len = l;
    }

    @Override
    public AbstractLargeByteList clone() throws CloneNotSupportedException {
        AbstractLargeByteList obj = (AbstractLargeByteList)super.clone();

        obj.len = len;

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
    public Byte get(int index) {
        return (byte)get((long)index);
    }

    @Override
    public Byte set(int index, Byte element) {
        Byte before = get(index);
        set((long)index, element);
        return before;
    }

    @Override
    public long length() {
        return len;
    }

    /**
     * <p>
     * 配列の長さを設定します。
     * </p>
     *
     * @param l バイト配列の長さ（バイト単位）
     */
    protected void setLength(long l) {
        len = l;
    }

    /**
     * <p>
     * 指定された位置がバイト配列の範囲内か確認します。
     * </p>
     *
     * <p>
     * 指定された位置がバイト配列の範囲内であれば何もしません。
     * 範囲外であれば例外をスローします。
     * </p>
     *
     * @throws IndexOutOfBoundsException 指定された位置がバイト列の範囲外の場合
     */
    protected void checkIndex(long index) {
        if (index < 0 || length() <= index) {
            throw new IndexOutOfBoundsException("index:" + index
                    + " is negative or too large.");
        }
    }

    /**
     * <p>
     * 指定された位置から終端までのバイト数を取得します。
     * </p>
     *
     * @param index バイト位置
     * @return 指定された位置から終端までのバイト数
     */
    protected long remaining(long index) {
        return length() - index;
    }

    /**
     * <p>
     * 指定された位置から n バイト読み出せるかどうか確認します。
     * </p>
     *
     * <p>
     * n バイト読み出し可能であれば何もしません。
     * 読みだし不可能であれば例外をスローします。
     * </p>
     *
     * @param index 読み出しを開始するバイト位置
     * @param n 読み出すバイト数
     * @throws IndexOutOfBoundsException n バイト読み出したとき、バイト配列の
     * 範囲外へのアクセスが発生する場合
     */
    protected void checkRemaining(long index, int n) {
        if (n > remaining(index)) {
            throw new IndexOutOfBoundsException("buffer is underflow, "
                    + "required(" + n + ") is exceeded "
                    + "remaining(" + remaining(index) + ").");
        }
    }
}
