package net.katsuster.strview.io;

import net.katsuster.strview.util.bit.*;

/**
 * <p>
 * メモリ上に確保されたビット列を表すクラスです。
 * </p>
 *
 * <p>
 * 複数ビットを一度に扱うメソッドの高速化を一切行いません。
 * 他のビット列クラスをテストする際に用います。
 * </p>
 */
public class SlowMemoryBitList extends AbstractLargeBitList {
    //ビット列を格納するバッファの 1要素が 2 の何乗ビット格納するか？を表す値
    public static final int ELEM_SHIFT = 5;
    //ビット列を格納するバッファの 1要素が何ビット格納するか？を表す値
    public static final int ELEM_BITS = (1 << ELEM_SHIFT);
    //ビット列を格納するバッファの 1要素内の位置を計算する際のビットマスク値
    public static final int ELEM_MASK = (1 << ELEM_SHIFT) - 1;

    /**
     * <p>
     * ビット列を格納するバッファ
     * </p>
     *
     * <p>
     * ビットはバッファの要素の MSB 側から LSB 側に向かって配置されます。
     * </p>
     *
     * <pre>
     * BITS = 8 の例
     *
     * bit position   | 0  1  2  3  4  5  6  7| 8  9 10 11 12 13 14 15|16
     * ---------------+-----------------------+-----------------------+--
     * buffer element | 0                     | 1                     | 2
     * element bit    | 0  1  2  3  4  5  6  7| 0  1  2  3  4  5  6  7| 0
     * </pre>
     */
    private int[] buf;

    /**
     * <p>
     * 長さ 0 のビット列を作成します。
     * </p>
     */
    public SlowMemoryBitList() {
        this(0);
    }

    /**
     * <p>
     * 長さ n のビット列を作成します。
     * </p>
     *
     * <p>
     * 指定できる最大サイズは Integer.MAX_VALUE * ELEM_BITS [bits] まで、
     * つまり (2^31 - 1) * 32 / 8 ≒ 約 8[GB] です。
     * </p>
     *
     * @param size 作成するビット列の長さ（ビット単位）
     * @throws NegativeArraySizeException 負のサイズを指定した場合
     * @throws IllegalArgumentException 最大サイズを超えていた場合
     */
    public SlowMemoryBitList(long size) {
        super(size);

        if (((long)Integer.MAX_VALUE * ELEM_BITS) < size) {
            throw new IllegalArgumentException(
                    "size:" + size + " is too large.");
        }

        buf = new int[getBufferElementPosition(size + ELEM_MASK)];
        length(size);
        getRange().setLength(size);
    }

    /**
     * <p>
     * 既存の boolean 型配列を利用して、ビット列を作成します。
     * </p>
     *
     * @param array 利用する boolean 型配列
     * @throws IllegalArgumentException null を指定した場合
     */
    public SlowMemoryBitList(boolean[] array) {
        super(0);

        if (array == null) {
            throw new IllegalArgumentException(
                    "array is null.");
        }

        buf = new int[getBufferElementPosition(array.length + ELEM_MASK)];
        length(array.length);
        getRange().setLength(array.length);

        set(0, array, 0, array.length);
    }

    @Override
    public SlowMemoryBitList clone()
            throws CloneNotSupportedException {
        SlowMemoryBitList obj = (SlowMemoryBitList)super.clone();

        obj.buf = buf.clone();

        return obj;
    }

    @Override
    protected Boolean getInner(long index) {
        int b, p, shifts;

		/*
		 * BITS = 8(MASK = 7) の例
		 *
		 * p        | 0  1  2  3  4  5  6  7|
		 * ---------+-----------------------+-
		 * shifts   | 7  6  5  4  3  2  1  0|
		 */
        b = getBufferElementPosition(index);
        p = getElementBitPosition(index);
        shifts = ELEM_MASK - p;

        if (((getElement(b) >>> shifts) & 1) == 1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void setInner(long index, Boolean data) {
        int b, p, shifts;
        int t;

		/*
		 * BITS = 8(MASK = 7) の例
		 *
		 * p        | 0  1  2  3  4  5  6  7|
		 * ---------+-----------------------+-
		 * shifts   | 7  6  5  4  3  2  1  0|
		 */
        b = getBufferElementPosition(index);
        p = getElementBitPosition(index);
        shifts = ELEM_MASK - p;

        t = getElement(b);
        t &= ~(1 << shifts);
        if (data) {
            t |= (1 << shifts);
        }
        setElement(b, t);
    }

    /**
     * <p>
     * n ビット目のビットがバッファの何要素目に格納されているか取得する。
     * </p>
     *
     * @param n ビット位置
     * @return バッファの位置
     */
    private int getBufferElementPosition(long n) {
        return (int)(n >>> ELEM_SHIFT);
    }

    /**
     * <p>
     * n ビット目のビットがバッファのある要素の、
     * 最上位ビット側から何ビット目に格納されているか取得する。
     * </p>
     *
     * @param n ビット位置
     * @return バッファの要素の、最上位ビット側から何ビット目かを表す位置
     */
    private int getElementBitPosition(long n) {
        return (int)(n & ELEM_MASK);
    }

    /**
     * <p>
     * 指定された位置の要素を取得します。
     * </p>
     *
     * @param n 要素の位置
     * @return 指定された要素
     */
    private int getElement(int n) {
        return buf[n];
    }

    /**
     * <p>
     * 指定された位置の要素に値を設定します。
     * </p>
     *
     * @param n 要素の位置
     * @param v 要素に設定する値
     */
    private void setElement(int n, int v) {
        buf[n] = v;
    }
}
