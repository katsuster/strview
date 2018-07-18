package net.katsuster.strview.util.bit;

import net.katsuster.strview.io.*;

/**
 * <p>
 * 数値、位置、長さを格納するクラスの基底クラス。
 * </p>
 */
public abstract class AbstractNum extends SubLargeBitList
        implements Num, Cloneable {
    private static final char[] DIGITS = {
            '0', '1', '2', '3', '4',
            '5', '6', '7', '8', '9',
    };

    /**
     * <p>
     * 指定された数値の長さ（ビット単位）を持ち、
     * 数値が存在する位置（ビット単位）を 0 に設定した、
     * 新たな数値を構築します。
     * </p>
     *
     * @param n 名前
     * @param l 数値の長さ（ビット単位）
     */
    public AbstractNum(String n, int l) {
        super(n, new MemoryBitList(64), 0, l);
    }

    /**
     * <p>
     * バッファと、数値の長さ（ビット単位）と、
     * 数値が存在する位置（ビット単位）を設定して、
     * 新たな数値を構築します。
     * </p>
     *
     * @param n 名前
     * @param b バッファ
     * @param p 数値の存在する位置（ビット単位）
     * @param l 数値の長さ（ビット単位）
     */
    public AbstractNum(String n, LargeBitList b, long p, int l) {
        super(n, b, p, l);
    }

    /**
     * <p>
     * 新たな数値を構築します。
     * </p>
     *
     * @param obj 数値
     */
    public AbstractNum(AbstractNum obj) {
        super(obj.getRange());
    }

    @Override
    public Object clone()
            throws CloneNotSupportedException {
        AbstractNum obj = (AbstractNum)super.clone();

        return obj;
    }

    /**
     * <p>
     * オブジェクトを指定されたオブジェクトと比較します。
     * 結果が true になるのは、引数が null ではなく、
     * このオブジェクトと同じ long 値を含む
     * オブジェクトである場合だけです。
     * </p>
     *
     * @param o 比較対象のオブジェクト
     * @return オブジェクトが同じである場合は true、そうでない場合は false
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof AbstractNum) {
            return (((AbstractNum)o).getValue() == getValue());
        } else {
            return false;
        }
    }

    /**
     * <p>
     * オブジェクトのハッシュコードを返します。
     * </p>
     *
     * @return オブジェクトの値 val を、
     * 変換式 (val ^ (val &gt;&gt;&gt; 32)) にて int に変換した値に等しい
     */
    @Override
    public int hashCode() {
        return (int)(getValue() ^ (getValue() >>> 32));
    }

    @Override
    public byte byteValue() {
        return (byte) getValue();
    }

    @Override
    public short shortValue() {
        return (short) getValue();
    }

    @Override
    public int intValue() {
        return (int) getValue();
    }

    @Override
    public long longValue() {
        return getValue();
    }

    @Override
    public float floatValue() {
        return (float) getValue();
    }

    @Override
    public double doubleValue() {
        return (double) getValue();
    }

    @Override
    public long getValue() {
        return getRaw();
    }

    @Override
    public void setValue(long v) {
        setRaw(v);
    }

    @Override
    public long getRaw() {
        return getPackedLong(getRange());
    }

    @Override
    public void setRaw(long v) {
        setPackedLong(getRange(), v);
    }

    /**
     * <p>
     * 指定された値をバイトごとに反転させます。
     * </p>
     *
     * <p>
     * 8, 16, 32, 64bit のみ有効です。
     * </p>
     *
     * @param v    値
     * @param nbit 値の有効なビット数
     * @return 反転後の値
     */
    protected static long reverseNum(long v, int nbit) {
        switch (nbit) {
        case 8:
            return v & 0xffL;
        case 16:
            return Short.reverseBytes((short) v) & 0xffffL;
        case 32:
            return Integer.reverseBytes((int) v) & 0xffffffffL;
        case 64:
            return Long.reverseBytes(v);
        default:
            throw new IllegalArgumentException(
                    "reverseNum() not support " + nbit + "bits.");
        }
    }

    /**
     * <p>
     * 指定されたビット数の数値とみなし、64bit に符号拡張を行います。
     * </p>
     *
     * @param v 値
     * @param n ビット数
     */
    protected static long signext(long v, int n) {
        long sb, mb;

        if (n == 0) {
            return 0;
        }

        sb = 1L << (n - 1);
        mb = (-1L << (n - 1)) << 1;
        v &= ~mb;
        if ((v & sb) != 0) {
            v = mb + v;
        }

        return v;
    }

    /**
     * <p>
     * 2つの値を符号無し整数とみなして数値的に比較します。
     * </p>
     *
     * @param v    値
     * @param objv 比較対象となる値
     * @return 値と比較対象が等しければ 0、
     * 値が比較対象より小さい数値ならば負の値、
     * 値が比較対象より大きい数値ならば正の値
     */
    protected static int compareAsUInt(long v, long objv) {
        long upper, obj_upper;
        long lower, obj_lower;

        //上位 63ビットを比べる
        upper = v >>> 1;
        obj_upper = objv >>> 1;

        if (upper - obj_upper < 0) {
            return -1;
        } else if (upper - obj_upper > 0) {
            return 1;
        }

        //下位 1ビットを比べる
        lower = v & 1;
        obj_lower = objv & 1;

        if (lower - obj_lower < 0) {
            return -1;
        } else if (lower - obj_lower > 0) {
            return 1;
        }

        return 0;
    }

    /**
     * <p>
     * 2つの値を符号付き整数とみなして数値的に比較します。
     * </p>
     *
     * @param v    値
     * @param objv 比較対象となる値
     * @return 値と比較対象が等しければ 0、
     * 値が比較対象より小さい数値ならば負の値、
     * 値が比較対象より大きい数値ならば正の値
     */
    protected static int compareAsSInt(long v, long objv) {
        if (v < objv) {
            return -1;
        } else if (v > objv) {
            return 1;
        }

        return 0;
    }

    /**
     * <p>
     * 数値を 64ビットの符号無し整数とみなして、float に変換します。
     * </p>
     *
     * @param v 数値
     * @return 数値を float に変換した値
     */
    protected static float uint64ToFloat(long v) {
        if ((v & 0x8000000000000000L) != 0) {
            return (float)(v & ~0x8000000000000000L)
                    + 0x7fffffffffffffffL + 0x1L;
        } else {
            return (float) v;
        }
    }

    /**
     * <p>
     * 数値を 64ビットの符号無し整数とみなして、double に変換します。
     * </p>
     *
     * @param v 数値
     * @return 数値を double に変換した値
     */
    protected static double uint64ToDouble(long v) {
        if ((v & 0x8000000000000000L) != 0) {
            return (double)(v & ~0x8000000000000000L)
                    + 0x7fffffffffffffffL + 0x1L;
        } else {
            return (double) v;
        }
    }

    /**
     * <p>
     * 数値を 8ビットの符号無し整数とみなして、文字列に変換します。
     * </p>
     *
     * @param v 数値
     * @return 数値の文字列表記
     */
    protected static String uint8ToString(byte v) {
        char[] buf = new char[3];
        int n, i = buf.length - 1;

        if ((v & 0x80) != 0) {
            v = (byte)(v & ~0x80);
            n = (int)(v % 10) + 8;
            v = (byte)(v / 10 + 12);
            if (n >= 10) {
                n -= 10;
                v += 1;
            }
        } else {
            n = (int)(v % 10);
            v = (byte)(v / 10);
        }
        buf[i--] = DIGITS[n];

        while (v != 0) {
            n = (int)(v % 10);
            v = (byte)(v / 10);
            buf[i--] = DIGITS[n];
        }

        return new String(buf, i + 1, buf.length - i - 1);
    }

    /**
     * <p>
     * 数値を 16ビットの符号無し整数とみなして、文字列に変換します。
     * </p>
     *
     * @param v 数値
     * @return 数値の文字列表記
     */
    protected static String uint16ToString(short v) {
        char[] buf = new char[5];
        int n, i = buf.length - 1;

        if ((v & 0x8000) != 0) {
            v = (short)(v & ~0x8000);
            n = (int)(v % 10) + 8;
            v = (short)(v / 10 + 3276);
            if (n >= 10) {
                n -= 10;
                v += 1;
            }
        } else {
            n = (int)(v % 10);
            v = (short)(v / 10);
        }
        buf[i--] = DIGITS[n];

        while (v != 0) {
            n = (int)(v % 10);
            v = (short)(v / 10);
            buf[i--] = DIGITS[n];
        }

        return new String(buf, i + 1, buf.length - i - 1);
    }

    /**
     * <p>
     * 数値を 32ビットの符号無し整数とみなして、文字列に変換します。
     * </p>
     *
     * @param v 数値
     * @return 数値の文字列表記
     */
    protected static String uint32ToString(int v) {
        char[] buf = new char[10];
        int n, i = buf.length - 1;

        if ((v & 0x80000000) != 0) {
            v = (int)(v & ~0x80000000);
            n = (int)(v % 10) + 8;
            v = (int)(v / 10 + 214748364);
            if (n >= 10) {
                n -= 10;
                v += 1;
            }
        } else {
            n = (int)(v % 10);
            v = (int)(v / 10);
        }
        buf[i--] = DIGITS[n];

        while (v != 0) {
            n = (int)(v % 10);
            v = (int)(v / 10);
            buf[i--] = DIGITS[n];
        }

        return new String(buf, i + 1, buf.length - i - 1);
    }

    /**
     * <p>
     * 数値を 64ビットの符号無し整数とみなして、文字列に変換します。
     * </p>
     *
     * @param v 数値
     * @return 数値の文字列表記
     */
    protected static String uint64ToString(long v) {
        char[] buf = new char[20];
        int n, i = buf.length - 1;

        if ((v & 0x8000000000000000L) != 0) {
            v = (long)(v & ~0x8000000000000000L);
            n = (int)(v % 10) + 8;
            v = (long)(v / 10 + 922337203685477580L);
            if (n >= 10) {
                n -= 10;
                v += 1;
            }
        } else {
            n = (int)(v % 10);
            v = (long)(v / 10);
        }
        buf[i--] = DIGITS[n];

        while (v != 0) {
            n = (int)(v % 10);
            v = (long)(v / 10);
            buf[i--] = DIGITS[n];
        }

        return new String(buf, i + 1, buf.length - i - 1);
    }

    /**
     * <p>
     * 数値を 8ビットの浮動小数点の小数点部とみなして、文字列に変換します。
     * </p>
     *
     * @param n 数値
     * @return 数値の文字列表記
     */
    protected static String fraction8ToString(int n) {
        long dec[] = {
                390625L,
                781250L,
                1562500L,
                3125000L,
                6250000L,
                12500000L,
                25000000L,
                50000000L,
        };
        long result;
        int dig;

        result = 0;
        for (dig = 0; dig < dec.length; dig++) {
            if (((n >> dig) & 1) == 1) {
                result += dec[dig];
            }
        }
        while ((result != 0) && (result % 1000 == 0)) {
            result /= 1000;
        }
        while ((result != 0) && (result % 10 == 0)) {
            result /= 10;
        }

        return Long.toString(result);
    }

    /**
     * <p>
     * 数値を 16ビットの浮動小数点の小数点部とみなして、文字列に変換します。
     * </p>
     *
     * @param n 数値
     * @return 数値の文字列表記
     */
    protected static String fraction16ToString(int n) {
        long dec[] = {
                152587890625L,
                305175781250L,
                610351562500L,
                1220703125000L,
                2441406250000L,
                4882812500000L,
                9765625000000L,
                19531250000000L,
                39062500000000L,
                78125000000000L,
                156250000000000L,
                312500000000000L,
                625000000000000L,
                1250000000000000L,
                2500000000000000L,
                5000000000000000L,
        };
        long result;
        int dig;

        result = 0;
        for (dig = 0; dig < dec.length; dig++) {
            if (((n >> dig) & 1) == 1) {
                result += dec[dig];
            }
        }
        while ((result != 0) && (result % 10000000 == 0)) {
            result /= 10000000;
        }
        while ((result != 0) && (result % 1000 == 0)) {
            result /= 1000;
        }
        while ((result != 0) && (result % 10 == 0)) {
            result /= 10;
        }

        return Long.toString(result);
    }
}
