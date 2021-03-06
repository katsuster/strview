package net.katsuster.strview.util.bit;

/**
 * <p>
 * 64bit の浮動小数点数、位置、長さを格納するクラス。
 * </p>
 */
public class Float64 extends AbstractNum {
    public Float64() {
        this(null, 0);
    }

    public Float64(String n) {
        this(n, 0);
    }

    public Float64(String n, long v) {
        super(n, 64);
        setValue(v);
    }

    public Float64(String n, double v) {
        super(n, 64);
        setValue(Double.doubleToRawLongBits(v));
    }

    public Float64(String n, LargeBitList b, long p, int l) {
        super(n, b, p, l);
    }

    public Float64(Float64 obj) {
        super(obj);
    }

    @Override
    public byte byteValue() {
        return (byte)Double.longBitsToDouble(getValue());
    }

    @Override
    public short shortValue() {
        return (short)Double.longBitsToDouble(getValue());
    }

    @Override
    public int intValue() {
        return (int)Double.longBitsToDouble(getValue());
    }

    @Override
    public long longValue() {
        return (long)Double.longBitsToDouble(getValue());
    }

    @Override
    public float floatValue() {
        return (float)Double.longBitsToDouble(getValue());
    }

    @Override
    public double doubleValue() {
        return Double.longBitsToDouble(getValue());
    }

    /**
     * 値を表す String オブジェクトを返します。
     *
     * @return 10 進数 (基数 10) による文字列表現
     */
    @Override
    public String toString() {
        return Double.toString(Double.longBitsToDouble(getValue()));
    }
}
