package net.katsuster.strview.util;

/**
 * <p>
 * 16bit.16bit の固定小数点数、位置、長さを格納するクラス。
 * 整数部分は符号付として扱います。
 * </p>
 *
 * @author katsuhiro
 */
public class SFixed16_16 extends Num {
    private int val;

    public SFixed16_16() {
        this(0, 0, 0);
    }

    public SFixed16_16(int v) {
        this(v, 0, 0);
    }

    public SFixed16_16(int v, int l, long p) {
        super(l, p);
        setBitsValue(v);
    }

    public SFixed16_16(SFixed16_16 obj) {
        super(obj);
        setBitsValue((int)obj.getBitsValue());
    }

    /**
     * <p>
     * オブジェクトを指定されたオブジェクトと比較します。
     * 結果が true になるのは、引数が null ではなく、
     * このオブジェクトと同じ int 値を含む
     * SFixed16_16 オブジェクトである場合だけです。
     * </p>
     *
     * @param o 比較対象のオブジェクト
     * @return オブジェクトが同じである場合は true、そうでない場合は false
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof SFixed16_16) {
            return (((SFixed16_16)o).val == val);
        } else {
            return false;
        }
    }

    /**
     * <p>
     * オブジェクトのハッシュコードを返します。
     * </p>
     *
     * @return オブジェクトが保持する値を int に変換した値に等しい
     */
    @Override
    public int hashCode() {
        return val;
    }

    @Override
    public byte byteValue() {
        return (byte)toFloat(val);
    }

    @Override
    public short shortValue() {
        return (short)toFloat(val);
    }

    @Override
    public int intValue() {
        return (int)toFloat(val);
    }

    @Override
    public long longValue() {
        return (long)toFloat(val);
    }

    @Override
    public float floatValue() {
        return toFloat(val);
    }

    @Override
    public double doubleValue() {
        return (double)toFloat(val);
    }

    @Override
    public long getBitsValue() {
        return val & 0xffffffffL;
    }

    /**
     * <p>
     * ビット列を設定する。
     * </p>
     *
     * @param v ビット列
     */
    public void setBitsValue(int v) {
        val = v;
    }

    /**
     * 値を表す String オブジェクトを返します。
     *
     * @return 10 進数 (基数 10) による文字列表現
     */
    @Override
    public String toString() {
        int upper, lower;

        upper = (val >> 16);
        lower = (val >>  0) & 0x0000ffff;

        return Integer.toString(upper) + "." + fractionToString(lower);
    }

    protected String fractionToString(int n) {
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

    /**
     * <p>
     * 指定されたビット表現を 16bit.16bit の固定小数点値とみなして、
     * float に変換した値を返します。
     * </p>
     *
     * @param bits 整数値
     * @return 同じビットパターンを持つ固定小数点値を、浮動小数点に変換した値
     */
    protected float toFloat(int bits) {
        int decimal;
        float fraction;

        decimal = (bits >> 16);
        fraction = (float)(bits & 0x0000ffff) / 65536;

        if (decimal < 0) {
            return decimal - fraction;
        } else {
            return decimal + fraction;
        }
    }
}
