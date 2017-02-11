package net.katsuster.strview.util;

/**
 * <p>
 * 数値、位置、長さを格納するクラスの基底クラス。
 * </p>
 *
 * @author katsuhiro
 */
public abstract class Num
        implements Cloneable {
    //リストが存在する範囲
    private Range r;

    private static final char[] DIGITS = {
            '0', '1', '2', '3', '4',
            '5', '6', '7', '8', '9',
    };

    /**
     * <p>
     * 数値の長さ（ビット単位）と、
     * 数値が存在する位置（ビット単位）を 0 に設定した、
     * 新たな数値を構築します。
     * </p>
     */
    public Num() {
        r = new SimpleRange(0, 0);
    }

    /**
     * <p>
     * 数値の長さ（ビット単位）を設定し、
     * 数値が存在する位置（ビット単位）を 0 に設定した、
     * 新たな数値を構築します。
     * </p>
     *
     * @param l 数値の長さ（ビット単位）
     */
    public Num(int l) {
        r = new SimpleRange(0, l);
    }

    /**
     * <p>
     * 数値の長さ（ビット単位）と、
     * 数値が存在する位置（ビット単位）を設定して、
     * 新たな数値を構築します。
     * </p>
     *
     * @param l 数値の長さ（ビット単位）
     * @param p 数値の存在する位置（ビット単位）
     */
    public Num(int l, long p) {
        r = new SimpleRange(p, l);
    }

    /**
     * <p>
     * 新たな数値を構築します。
     * </p>
     *
     * @param obj 数値
     */
    public Num(Num obj) {
        r = new SimpleRange(obj.r);
    }

    /**
     * <p>
     * 数値の長さ（ビット単位）と、
     * 数値が存在する位置（ビット単位）を、
     * コピーした新たな数値を返します。
     * </p>
     *
     * @return コピーされたオブジェクト
     * @throws CloneNotSupportedException インスタンスを複製できない場合
     */
    public Num clone() throws CloneNotSupportedException {
        Num obj = (Num)super.clone();

        obj.r = r.clone();

        return obj;
    }

    /**
     * <p>
     * 数値が存在する範囲を取得します。
     * </p>
     *
     * <p>
     * 範囲の単位は数値によって意味が異なります。
     * またその範囲から完全に数値を再現できるとは限りません。
     * </p>
     *
     * <p>
     * 例えば、この数値が別のビット列 A から生成された場合、
     * リスト A の何ビット目から生成されたかを示します。
     * </p>
     *
     * @return 数値が存在する範囲
     */
    public Range getRange() {
        return r;
    }

    /**
     * <p>
     * 数値が存在する範囲を設定します。
     * </p>
     *
     * <p>
     * 範囲の単位は数値によって意味が異なります。
     * またその範囲から完全に数値を再現できるとは限りません。
     * </p>
     *
     * @param range 数値が存在する範囲
     */
    public void setRange(Range range) {
        r = range;
    }

    /**
     * <p>
     * 指定された数値を byte 型として返します。
     * 値を丸めたり切り詰めたりすることもあります。
     * </p>
     *
     * @return このオブジェクトが表す数値を byte 型に変換した値
     */
    public abstract byte byteValue();

    /**
     * <p>
     * 指定された数値を short 型として返します。
     * 値を丸めたり切り詰めたりすることもあります。
     * </p>
     *
     * @return このオブジェクトが表す数値を short 型に変換した値
     */
    public abstract short shortValue();

    /**
     * <p>
     * 指定された数値を int 型として返します。
     * 値を丸めたり切り詰めたりすることもあります。
     * </p>
     *
     * @return このオブジェクトが表す数値を int 型に変換した値
     */
    public abstract int intValue();

    /**
     * <p>
     * 指定された数値を long 型として返します。
     * 値を丸めたり切り詰めたりすることもあります。
     * </p>
     *
     * @return このオブジェクトが表す数値を long 型に変換した値
     */
    public abstract long longValue();

    /**
     * <p>
     * 指定された数値を float 型として返します。
     * 値を丸めたり切り詰めたりすることもあります。
     * </p>
     *
     * @return このオブジェクトが表す数値を float 型に変換した値
     */
    public abstract float floatValue();

    /**
     * <p>
     * 指定された数値を double 型として返します。
     * 値を丸めたり切り詰めたりすることもあります。
     * </p>
     *
     * @return このオブジェクトが表す数値を double 型に変換した値
     */
    public abstract double doubleValue();

    /**
     * <p>
     * 数値を表すビットデータを long 型として返します。
     * 符号拡張はしません。
     * </p>
     *
     * @return このオブジェクトが表すビットデータを long 型に変換した値
     */
    public abstract long getBitsValue();

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
