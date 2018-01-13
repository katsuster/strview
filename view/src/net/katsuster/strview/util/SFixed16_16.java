package net.katsuster.strview.util;

/**
 * <p>
 * 16bit.16bit の固定小数点数、位置、長さを格納するクラス。
 * 整数部分は符号付として扱います。
 * </p>
 */
public class SFixed16_16 extends AbstractNum {
    public SFixed16_16() {
        this(null, 0);
    }

    public SFixed16_16(String n) {
        this(n, 0);
    }

    public SFixed16_16(String n, int v) {
        super(n, 32);
        setValue(v);
    }

    public SFixed16_16(String n, LargeBitList b, long p, int l) {
        super(n, b, p, l);
    }

    public SFixed16_16(SFixed16_16 obj) {
        super(obj);
    }

    @Override
    public String getTypeName() {
        return "SFix16_16";
    }

    @Override
    public byte byteValue() {
        return (byte) sfixed16_16ToFloat((int) getValue());
    }

    @Override
    public short shortValue() {
        return (short) sfixed16_16ToFloat((int) getValue());
    }

    @Override
    public int intValue() {
        return (int) sfixed16_16ToFloat((int) getValue());
    }

    @Override
    public long longValue() {
        return (long) sfixed16_16ToFloat((int) getValue());
    }

    @Override
    public float floatValue() {
        return sfixed16_16ToFloat((int) getValue());
    }

    @Override
    public double doubleValue() {
        return (double) sfixed16_16ToFloat((int) getValue());
    }

    @Override
    public long getValue() {
        //Need sign extension
        return (int)getRaw();
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

        upper = (int)(getValue() >> 16);
        lower = (int)(getValue() >>  0) & 0x0000ffff;

        return Integer.toString(upper) + "." + fraction16ToString(lower);
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
    protected float sfixed16_16ToFloat(int bits) {
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
