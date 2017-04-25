package net.katsuster.strview.util;

/**
 * <p>
 * 64bit の符号あり整数、リトルエンディアン
 * </p>
 *
 * <p>
 * 8, 16, 32, 64bit のみ有効です。
 * </p>
 *
 * @author katsuhiro
 */
public class SIntR extends BaseInt
        implements Comparable<SIntR> {
    public SIntR() {
        this(0);
    }

    public SIntR(long v) {
        super();

        setReverseBitsValue(v);
    }

    public SIntR(LargeBitList b, long p, int l) {
        super(b, p, l);
    }

    public SIntR(SIntR obj) {
        super(obj);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof SIntR) {
            return (((SIntR)o).getReverseBitsValue() == getReverseBitsValue());
        } else {
            return false;
        }
    }

    @Override
    public int compareTo(SIntR obj) {
        return compareAsSInt(getReverseBitsValue(), obj.getReverseBitsValue());
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
        return (float) getReverseBitsValue();
    }

    @Override
    public double doubleValue() {
        return (double) getReverseBitsValue();
    }

    /**
     * 値を表す String オブジェクトを返します。
     *
     * @return 10 進数 (基数 10) による文字列表現
     */
    @Override
    public String toString() {
        return Long.toString(getReverseBitsValue());
    }
}
