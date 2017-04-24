package net.katsuster.strview.util;

/**
 * <p>
 * 32bit の浮動小数点数、位置、長さを格納するクラス。
 * </p>
 *
 * @author katsuhiro
 */
public class Float32 extends AbstractNum {
    private int val;

    public Float32() {
        this(0, 0, 0);
    }

    public Float32(int v) {
        this(v, 0, 0);
    }

    public Float32(float v, long p, int l) {
        this(Float.floatToRawIntBits(v), p, l);
    }

    public Float32(int v, long p, int l) {
        super(p, l);
        setBitsValue(v);
    }

    public Float32(Float32 obj) {
        super(obj);
        setBitsValue((int)obj.getBitsValue());
    }

    /**
     * <p>
     * オブジェクトを指定されたオブジェクトと比較します。
     * 結果が true になるのは、引数が null ではなく、
     * このオブジェクトと同じ int 値を含む
     * Float32 オブジェクトである場合だけです。
     * </p>
     *
     * @param o 比較対象のオブジェクト
     * @return オブジェクトが同じである場合は true、そうでない場合は false
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Float32) {
            return (((Float32)o).val == val);
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
        return (byte)Float.intBitsToFloat(val);
    }

    @Override
    public short shortValue() {
        return (short)Float.intBitsToFloat(val);
    }

    @Override
    public int intValue() {
        return (int)Float.intBitsToFloat(val);
    }

    @Override
    public long longValue() {
        return (long)Float.intBitsToFloat(val);
    }

    @Override
    public float floatValue() {
        return Float.intBitsToFloat(val);
    }

    @Override
    public double doubleValue() {
        return (double)Float.intBitsToFloat(val);
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
        return Float.toString(Float.intBitsToFloat(val));
    }
}
