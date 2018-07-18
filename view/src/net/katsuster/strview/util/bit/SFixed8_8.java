package net.katsuster.strview.util.bit;

/**
 * <p>
 * 8bit.8bit の固定小数点数、位置、長さを格納するクラス。
 * 整数部分は符号付として扱います。
 * </p>
 */
public class SFixed8_8 extends AbstractNum {
    public SFixed8_8() {
        this(null, (short) 0);
    }

    public SFixed8_8(String n) {
        this(n, (short) 0);
    }

    public SFixed8_8(String n, short v) {
        super(n, 16);
        setValue(v);
    }

    public SFixed8_8(String n, LargeBitList b, long p, int l) {
        super(n, b, p, l);
    }

    public SFixed8_8(SFixed8_8 obj) {
        super(obj);
    }

    @Override
    public String getTypeName() {
        return "SFix8_8";
    }

    @Override
    public byte byteValue() {
        return (byte) sfixed8_8ToFloat((short) getValue());
    }

    @Override
    public short shortValue() {
        return (short) sfixed8_8ToFloat((short) getValue());
    }

    @Override
    public int intValue() {
        return (int) sfixed8_8ToFloat((short) getValue());
    }

    @Override
    public long longValue() {
        return (long) sfixed8_8ToFloat((short) getValue());
    }

    @Override
    public float floatValue() {
        return sfixed8_8ToFloat((short) getValue());
    }

    @Override
    public double doubleValue() {
        return (double) sfixed8_8ToFloat((short) getValue());
    }

    @Override
    public long getValue() {
        //Need sign extension
        return (short)getRaw();
    }

    @Override
    public void setValue(long v) {
        setRaw(v & 0xffffL);
    }

    /**
     * 値を表す String オブジェクトを返します。
     *
     * @return 10 進数 (基数 10) による文字列表現
     */
    @Override
    public String toString() {
        int upper, lower;

        upper = (int)(getValue() >> 8);
        lower = (int)(getValue() >> 0) & 0x00ff;

        return Integer.toString(upper) + "." + fraction8ToString(lower);
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
    protected static float sfixed8_8ToFloat(short bits) {
        int decimal;
        float fraction;

        decimal = (bits >> 8);
        fraction = (float)(bits & 0x00ff) / 256;

        if (decimal < 0) {
            return decimal - fraction;
        } else {
            return decimal + fraction;
        }
    }
}
