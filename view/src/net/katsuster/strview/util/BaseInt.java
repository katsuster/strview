package net.katsuster.strview.util;

/**
 * <p>
 * 64bit 整数の基底クラス。
 * </p>
 */
public abstract class BaseInt extends AbstractNum {
    public BaseInt() {
        super();
    }

    public BaseInt(LargeBitList b, long p, int l) {
        super(b, p, l);
    }

    public BaseInt(BaseInt obj) {
        super(obj);
    }

    /**
     * <p>
     * オブジェクトを指定されたオブジェクトと比較します。
     * 結果が true になるのは、引数が null ではなく、
     * このオブジェクトと同じ long 値を含む
     * Sint64 オブジェクトである場合だけです。
     * </p>
     *
     * @param o 比較対象のオブジェクト
     * @return オブジェクトが同じである場合は true、そうでない場合は false
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof BaseInt) {
            return (((BaseInt)o).getBitsValue() == getBitsValue());
        } else {
            return false;
        }
    }

    /**
     * <p>
     * オブジェクトのハッシュコードを返します。
     * </p>
     *
     * @return オブジェクトの値 val を、
     * 変換式 (val ^ (val &gt;&gt;&gt; 32)) にて int に変換した値に等しい
     */
    @Override
    public int hashCode() {
        return (int)(getBitsValue() ^ (getBitsValue() >>> 32));
    }

    @Override
    public byte byteValue() {
        return (byte) getBitsValue();
    }

    @Override
    public short shortValue() {
        return (short) getBitsValue();
    }

    @Override
    public int intValue() {
        return (int) getBitsValue();
    }

    @Override
    public long longValue() {
        return getBitsValue();
    }

    @Override
    public float floatValue() {
        return (float) getBitsValue();
    }

    @Override
    public double doubleValue() {
        return (double) getBitsValue();
    }

    @Override
    public long getBitsValue() {
        return getPackedLong(getRange());
    }

    /**
     * <p>
     * ビット列を設定する。
     * </p>
     *
     * @param v ビット列
     */
    public void setBitsValue(long v) {
        setPackedLong(getRange(), v);
    }

    protected long getReverseBitsValue() {
        int nbit = (int) getRange().getLength();

        return reverseNum(getBitsValue(), nbit);
    }

    protected void setReverseBitsValue(long v) {
        int nbit = (int) getRange().getLength();

        setBitsValue(reverseNum(v, nbit));
    }
}
