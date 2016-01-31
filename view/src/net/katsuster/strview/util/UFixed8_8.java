package net.katsuster.strview.util;

/**
 * <p>
 * 8bit.8bit の固定小数点数、位置、長さを格納するクラス。
 * 整数部分は符号無しとして扱います。
 * </p>
 *
 * @author katsuhiro
 */
public class UFixed8_8 extends Num {
    private short val;

    public UFixed8_8() {
        this((short)0, 0, 0);
    }

    public UFixed8_8(short v) {
        this(v, 0, 0);
    }

    public UFixed8_8(short v, int l, long p) {
        super(l, p);
        setBitsValue(v);
    }

    /**
     * <p>
     * 値をコピーした新たなオブジェクトを返します。
     * </p>
     *
     * @return コピーされたオブジェクト
     * @throws CloneNotSupportedException インスタンスを複製できない場合
     */
    @Override
    public UFixed8_8 clone() throws CloneNotSupportedException {
        UFixed8_8 obj = (UFixed8_8)super.clone();

        return obj;
    }

    /**
     * <p>
     * オブジェクトを指定されたオブジェクトと比較します。
     * 結果が true になるのは、引数が null ではなく、
     * このオブジェクトと同じ short 値を含む
     * UFixed8_8 オブジェクトである場合だけです。
     * </p>
     *
     * @param o 比較対象のオブジェクト
     * @return オブジェクトが同じである場合は true、そうでない場合は false
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof UFixed8_8) {
            return (((UFixed8_8)o).val == val);
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
        return val & 0xffffL;
    }

    /**
     * <p>
     * ビット列を設定する。
     * </p>
     *
     * @param v ビット列
     */
    public void setBitsValue(short v) {
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

        upper = (val >> 8) & 0x00ff;
        lower = (val >> 0) & 0x00ff;

        return uint8ToString((byte)upper) + "." + fractionToString(lower);
    }

    protected String fractionToString(int n) {
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
     * 指定されたビット表現を 8bit.8bit の固定小数点値とみなして、
     * float に変換した値を返します。
     * </p>
     *
     * @param bits 整数値
     * @return 同じビットパターンを持つ固定小数点値を、浮動小数点に変換した値
     */
    protected static float toFloat(short bits) {
        int decimal;
        float fraction;

        decimal = (bits >> 8) & 0x00ff;
        fraction = (float)(bits & 0x00ff) / 256;

        if (decimal < 0) {
            return decimal - fraction;
        } else {
            return decimal + fraction;
        }
    }
}
