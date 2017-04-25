package net.katsuster.strview.util;

/**
 * <p>
 * 64bit の符号無し整数、リトルエンディアン
 * </p>
 *
 * <p>
 * 8, 16, 32, 64bit のみ有効です。
 * </p>
 *
 * @author katsuhiro
 */
public class UIntR extends BaseInt
        implements Comparable<UIntR> {
    public UIntR() {
        this(0);
    }

    public UIntR(long v) {
        super();

        setReverseBitsValue(v);
    }

    public UIntR(LargeBitList b, long p, int l) {
        super(b, p, l);
    }

    public UIntR(UIntR obj) {
        super(obj);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof UIntR) {
            return (((UIntR)o).getReverseBitsValue() == getReverseBitsValue());
        } else {
            return false;
        }
    }

    @Override
    public int compareTo(UIntR obj) {
        return compareAsUInt(getReverseBitsValue(), obj.getReverseBitsValue());
    }

    @Override
    public byte byteValue() {
        return (byte) getReverseBitsValue();
    }

    @Override
    public short shortValue() {
        return (short) getReverseBitsValue();
    }

    @Override
    public int intValue() {
        return (int) getReverseBitsValue();
    }

    @Override
    public long longValue() {
        return getReverseBitsValue();
    }

    @Override
    public float floatValue() {
        return uint64ToFloat(getReverseBitsValue());
    }

    @Override
    public double doubleValue() {
        return uint64ToDouble(getReverseBitsValue());
    }

    /**
     * 値を表す String オブジェクトを返します。
     *
     * @return 10 進数 (基数 10) による文字列表現
     */
    @Override
    public String toString() {
        return uint64ToString(getReverseBitsValue());
    }
}
