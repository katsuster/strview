package net.katsuster.strview.io;

import net.katsuster.strview.util.*;
import net.katsuster.strview.util.bit.*;

/**
 * <p>
 * バイト列を受け取り、ビット列を返すクラスです。
 * </p>
 */
public class ByteToBitList extends AbstractLargeBitList {
    //ビット列を格納するバッファの 1要素が 2 の何乗ビット格納するか？を表す値
    public static final int ELEM_SHIFT = 3;
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
    private LargeList<Byte> buf;

    /**
     * <p>
     * 長さ 0 のビット列を作成します。
     * </p>
     */
    public ByteToBitList() {
        this(new MemoryByteList(0));
    }

    /**
     * <p>
     * 既存のバイト列をラップした、ビット列を作成します。
     * </p>
     *
     * <p>
     * バイト列はコピーされません。従ってバイト列を変更すると、
     * ラップしたビット列も変化します。
     * また、ビット列を変更すると、ラップされているバイト列も変化します。
     * </p>
     *
     * @param array 利用するバイト列
     * @throws IllegalArgumentException null を指定した場合
     */
    public ByteToBitList(LargeList<Byte> array) {
        super(0);

        if (array == null) {
            throw new IllegalArgumentException(
                    "array is null.");
        }

        buf = array;
        length(array.length() * ELEM_BITS);
    }

    @Override
    public Object clone()
            throws CloneNotSupportedException {
        ByteToBitList obj = (ByteToBitList)super.clone();

        obj.buf = (LargeList<Byte>)buf.clone();

        return obj;
    }

    @Override
    protected Boolean getInner(long index) {
        long b;
        int p, shifts;

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
        long b;
        int p, shifts;
        byte t;

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

    @Override
    protected long getPackedLongInner(long index, int n) {
        long epos;
        int remain;
        byte elem;
        long result = 0;

        epos = getBufferElementPosition(index);
        remain = ELEM_BITS - getElementBitPosition(index);
        elem = getElement(epos);

        while (n > remain) {
            //バッファに残っているビットを全部追加する
            n -= remain;
            result |= (getRightBits64(remain, elem) << n);
            //次の要素を読み出す
            epos += 1;
            elem = getElement(epos);
            //残りは ELEM_BITS ビット
            remain = ELEM_BITS;
        }

        if (n > 0) {
            //現在位置から n ビット読む
            result |= getRightBits64(n, elem >>> (remain - n));
        }

        return result;
    }

    @Override
    protected void setPackedLongInner(long index, int n, long val) {
        long epos;
        int remain;
        byte elem;

        epos = getBufferElementPosition(index);
        remain = ELEM_BITS - getElementBitPosition(index);
        elem = getElement(epos);

        while (n > remain) {
            //バッファの空きビット全てに書く
            n -= remain;
            elem &= ~(getRightBits64(remain, 0xffffffffffffffffL));
            elem |= getRightBits64(remain, val >>> n);
            setElement(epos, elem);
            //次の要素を読み出す
            epos += 1;
            elem = getElement(epos);
            //残りは ELEM_BITS ビット
            remain = ELEM_BITS;
        }

        if (n > 0) {
            //現在位置から n ビット書く
            elem &= ~(getRightBits64(n, 0xffffffffffffffffL) << (remain - n));
            elem |= getRightBits64(n, val) << (remain - n);
            setElement(epos, elem);
        }
    }

    /**
     * <p>
     * n ビット目のビットがバッファの何要素目に格納されているか取得する。
     * </p>
     *
     * @param n ビット位置
     * @return バッファの位置
     */
    private long getBufferElementPosition(long n) {
        return (n >>> ELEM_SHIFT);
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
    private byte getElement(long n) {
        return (byte)buf.get(n);
    }

    /**
     * <p>
     * 指定された位置の要素に値を設定します。
     * </p>
     *
     * @param n 要素の位置
     * @param v 要素に設定する値
     */
    private void setElement(long n, byte v) {
        buf.set(n, v);
    }
}
