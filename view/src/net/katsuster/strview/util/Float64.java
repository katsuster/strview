package net.katsuster.strview.util;

/**
 * <p>
 * 64bit の浮動小数点数、位置、長さを格納するクラス。
 * </p>
 *
 * @author katsuhiro
 */
public class Float64 extends AbstractNum {
    private long val;

    public Float64() {
        this(0, 0, 0);
    }

    public Float64(long v) {
        this(v, 0, 0);
    }

    public Float64(double v, long p, int l) {
        this(Double.doubleToRawLongBits(v), p, l);
    }

    public Float64(long v, long p, int l) {
        super(p, l);
        setBitsValue(v);
    }

    public Float64(Float64 obj) {
        super(obj);
        setBitsValue(obj.getBitsValue());
    }

    /**
     * <p>
     * オブジェクトを指定されたオブジェクトと比較します。
     * 結果が true になるのは、引数が null ではなく、
     * このオブジェクトと同じ long 値を含む
     * Float64 オブジェクトである場合だけです。
     * </p>
     *
     * @param o 比較対象のオブジェクト
     * @return オブジェクトが同じである場合は true、そうでない場合は false
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Float64) {
            return (((Float64)o).val == val);
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
        return (int)(val ^ (val >> 32));
    }

    @Override
    public byte byteValue() {
        return (byte)Double.longBitsToDouble(val);
    }

    @Override
    public short shortValue() {
        return (short)Double.longBitsToDouble(val);
    }

    @Override
    public int intValue() {
        return (int)Double.longBitsToDouble(val);
    }

    @Override
    public long longValue() {
        return (long)Double.longBitsToDouble(val);
    }

    @Override
    public float floatValue() {
        return (float)Double.longBitsToDouble(val);
    }

    @Override
    public double doubleValue() {
        return Double.longBitsToDouble(val);
    }

    @Override
    public long getBitsValue() {
        return val;
    }

    /**
     * <p>
     * ビット列を設定する。
     * </p>
     *
     * @param v ビット列
     */
    public void setBitsValue(long v) {
        val = v;
    }

    /**
     * 値を表す String オブジェクトを返します。
     *
     * @return 10 進数 (基数 10) による文字列表現
     */
    @Override
    public String toString() {
        return Double.toString(Double.longBitsToDouble(val));
    }
}
