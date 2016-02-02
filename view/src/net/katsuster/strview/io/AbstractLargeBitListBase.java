package net.katsuster.strview.io;

import java.util.AbstractList;

/**
 * <p>
 * int 型で扱える長さを超えるビット配列の共通動作を定義します。
 * </p>
 *
 * @author katsuhiro
 */
public abstract class AbstractLargeBitListBase extends AbstractList<Boolean>
        implements LargeBitList, Cloneable {
    //配列の長さ（ビット単位）
    private long len;

    /**
     * <p>
     * 指定された長さのビット列を作成します。
     * </p>
     *
     * @param from ビット列の開始点
     * @param to   ビット列の終了点
     */
    public AbstractLargeBitListBase(long from, long to) {
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
     * 指定された長さのビット列を作成します。
     * </p>
     *
     * @param l ビット列の長さ
     */
    public AbstractLargeBitListBase(long l) {
        if (l < 0) {
            throw new NegativeArraySizeException("len:" + l
                    + " is negative.");
        }
        len = l;
    }

    @Override
    public AbstractLargeBitListBase clone() throws CloneNotSupportedException {
        AbstractLargeBitListBase obj = (AbstractLargeBitListBase)super.clone();

        obj.len = len;

        return obj;
    }

    /**
     * <p>
     * ブール値の配列を整数値に右詰め（LSB 側に詰める）した値を返します。
     * </p>
     *
     * <p>
     * ブール値の true が整数値の 1、false が 0 に対応します。
     * ブール値の配列長が 32 に満たない場合、残りのビットは 0 で埋められます。
     * </p>
     *
     * @param array ブール値の配列
     * @return ブール値の配列を整数値に右詰め（LSB 側に詰める）した値
     * @throws IllegalArgumentException 配列長が長すぎた
     */
    public static int packBitsInt(boolean[] array) {
        int result;
        int i;

        if (array.length > 32) {
            throw new IllegalArgumentException("packBitsInt() cannot get more than 32 bits."
                    + "(" + array.length + "bits)");
        }

        result = 0;
        for (i = 0; i < array.length; i++) {
            result <<= 1;

            if (array[i]) {
                result |= 1;
            }
        }

        return result;
    }

    /**
     * <p>
     * ブール値の配列を整数値に右詰め（LSB 側に詰める）した値を返します。
     * </p>
     *
     * <p>
     * ブール値の true が整数値の 1、false が 0 に対応します。
     * ブール値の配列長が 64 に満たない場合、残りのビットは 0 で埋められます。
     * </p>
     *
     * @param array ブール値の配列
     * @return ブール値の配列を整数値に右詰め（LSB 側に詰める）した値
     * @throws IllegalArgumentException 配列長が長すぎた
     */
    public static long packBitsLong(boolean[] array) {
        long result;
        int i;

        if (array.length > 64) {
            throw new IllegalArgumentException("packBitsLong() cannot get more than 64 bits."
                    + "(" + array.length + "bits)");
        }

        result = 0;
        for (i = 0; i < array.length; i++) {
            result <<= 1;

            if (array[i]) {
                result |= 1;
            }
        }

        return result;
    }

    /**
     * <p>
     * 右詰め（LSB 側に詰める）された整数値をブール値の配列に変換します。
     * </p>
     *
     * <p>
     * 整数値の 1 がブール値の true、0 が false に対応します。
     * ブール値の配列の要素分だけビットが変換され、他の値は無視されます。
     * </p>
     *
     * @param val   整数値
     * @param array ブール値の配列
     * @throws IllegalArgumentException 変換するビット数が長すぎた
     */
    public static void unpackBitsInt(int val, boolean[] array) {
        int i;

        if (array.length > 32) {
            throw new IllegalArgumentException("unpackBitsInt() cannot get more than 32 bits."
                    + "(" + array.length + "bits)");
        }

        for (i = array.length - 1; i >= 0; i--) {
            array[i] = ((val & 1) == 1);
            val >>>= 1;
        }
    }

    /**
     * <p>
     * 右詰め（LSB 側に詰める）された整数値をブール値の配列に変換します。
     * </p>
     *
     * <p>
     * 整数値の 1 がブール値の true、0 が false に対応します。
     * ブール値の配列の要素分だけビットが変換され、他の値は無視されます。
     * </p>
     *
     * @param val   整数値
     * @param array ブール値の配列
     * @throws IllegalArgumentException 変換するビット数が長すぎた
     */
    public static void unpackBitsLong(long val, boolean[] array) {
        int i;

        if (array.length > 64) {
            throw new IllegalArgumentException("unpackBitsInt() cannot get more than 64 bits."
                    + "(" + array.length + "bits)");
        }

        for (i = array.length - 1; i >= 0; i--) {
            array[i] = ((val & 1) == 1);
            val >>>= 1;
        }
    }

    /**
     * <p>
     * 指定された int 値の右 n ビットを取得する。
     * </p>
     *
     * <p>
     * 注意: ビット数に 0 を指定すると、0 ではなく値そのものを返す。
     * </p>
     *
     * @param n 取得するビット数（1 ～ 32 ビットまで）
     * @param val ビットを取得する整数値（32 ビットまで有効）
     * @return val の右 n ビットの数値、0 ビットの場合は val そのもの
     */
    public static int getRightBits32(int n, int val) {
        int s = 32 - n;

        return (val << s) >>> s;
    }

    /**
     * <p>
     * 指定された long 値の右 n ビットを取得する。
     * </p>
     *
     * <p>
     * 注意: ビット数に 0 を指定すると、0 ではなく値そのものを返す。
     * </p>
     *
     * @param n 取得するビット数（1 ～ 64 ビットまで）
     * @param val ビットを取得する整数値（64 ビットまで有効）
     * @return val の右 n ビットの数値、0 ビットの場合は val そのもの
     */
    public static long getRightBits64(int n, long val) {
        int s = 64 - n;

        return (val << s) >>> s;
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
    public Boolean get(int index) {
        return get((long)index);
    }

    @Override
    public Boolean set(int index, Boolean element) {
        Boolean before = get(index);
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
     * @param l ビット配列の長さ（ビット単位）
     */
    protected void setLength(long l) {
        len = l;
    }

    /**
     * <p>
     * 指定された位置がビット配列の範囲内か確認します。
     * </p>
     *
     * <p>
     * 指定された位置がビット配列の範囲内であれば何もしません。
     * 範囲外であれば例外をスローします。
     * </p>
     *
     * @throws IndexOutOfBoundsException 指定された位置がビット列の範囲外の場合
     */
    protected void checkIndex(long index) {
        if (index < 0 || length() <= index) {
            throw new IndexOutOfBoundsException("index:" + index
                    + " is negative or too large.");
        }
    }

    /**
     * <p>
     * 指定された位置から終端までのビット数を取得します。
     * </p>
     *
     * @param index ビット位置
     * @return 指定された位置から終端までのビット数
     */
    protected long remaining(long index) {
        return length() - index;
    }

    /**
     * <p>
     * 指定された位置から n ビット読み出せるかどうか確認します。
     * </p>
     *
     * <p>
     * n ビット読み出し可能であれば何もしません。
     * 読みだし不可能であれば例外をスローします。
     * </p>
     *
     * @param index 読み出しを開始するビット位置
     * @param n 読み出すビット数
     * @throws IndexOutOfBoundsException n ビット読み出したとき、ビット配列の
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
