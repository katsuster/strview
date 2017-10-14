package net.katsuster.strview.util;

/**
 * <p>
 * 32bit の浮動小数点数、位置、長さを格納するクラス。
 * </p>
 */
public class Float32 extends AbstractNum {
    public Float32() {
        this(0);
    }

    public Float32(int v) {
        super(32);
        setValue(v);
    }

    public Float32(float v) {
        super(32);
        setValue(Float.floatToRawIntBits(v));
    }

    public Float32(LargeBitList b, long p, int l) {
        super(b, p, l);
    }

    public Float32(Float32 obj) {
        super(obj);
    }

    @Override
    public byte byteValue() {
        return (byte)Float.intBitsToFloat((int) getValue());
    }

    @Override
    public short shortValue() {
        return (short)Float.intBitsToFloat((int) getValue());
    }

    @Override
    public int intValue() {
        return (int)Float.intBitsToFloat((int) getValue());
    }

    @Override
    public long longValue() {
        return (long)Float.intBitsToFloat((int) getValue());
    }

    @Override
    public float floatValue() {
        return Float.intBitsToFloat((int) getValue());
    }

    @Override
    public double doubleValue() {
        return (double)Float.intBitsToFloat((int) getValue());
    }

    @Override
    public long getValue() {
        return getRaw() & 0xffffffffL;
    }

    @Override
    public void setValue(long v) {
        setRaw(v & 0xffffffffL);
    }

    /**
     * 値を表す String オブジェクトを返します。
     *
     * @return 10 進数 (基数 10) による文字列表現
     */
    @Override
    public String toString() {
        return Float.toString(Float.intBitsToFloat((int) getValue()));
    }
}
