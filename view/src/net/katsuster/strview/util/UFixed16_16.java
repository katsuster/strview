package net.katsuster.strview.util;

/**
 * <p>
 * 16bit.16bit の固定小数点数、位置、長さを格納するクラス。
 * 整数部分は符号無しとして扱います。
 * </p>
 *
 * @author katsuhiro
 */
public class UFixed16_16 extends AbstractNum {
    public UFixed16_16() {
        this((short) 0);
    }

    public UFixed16_16(int v) {
        super(32);
        setValue(v);
    }

    public UFixed16_16(LargeBitList b, long p, int l) {
        super(b, p, l);
    }

    public UFixed16_16(UFixed16_16 obj) {
        super(obj);
    }

    @Override
    public byte byteValue() {
        return (byte) ufixed16_16ToFloat((int) getValue());
    }

    @Override
    public short shortValue() {
        return (short) ufixed16_16ToFloat((int) getValue());
    }

    @Override
    public int intValue() {
        return (int) ufixed16_16ToFloat((int) getValue());
    }

    @Override
    public long longValue() {
        return (long) ufixed16_16ToFloat((int) getValue());
    }

    @Override
    public float floatValue() {
        return ufixed16_16ToFloat((int) getValue());
    }

    @Override
    public double doubleValue() {
        return (double) ufixed16_16ToFloat((int) getValue());
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
        int upper, lower;

        upper = (int)(getValue() >> 16) & 0x0000ffff;
        lower = (int)(getValue() >>  0) & 0x0000ffff;

        return uint16ToString((short)upper) + "." + fraction16ToString(lower);
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
    protected float ufixed16_16ToFloat(int bits) {
        int decimal;
        float fraction;

        decimal = (bits >> 16) & 0x0000ffff;
        fraction = (float)(bits & 0x0000ffff) / 65536;

        if (decimal < 0) {
            return decimal - fraction;
        } else {
            return decimal + fraction;
        }
    }
}
