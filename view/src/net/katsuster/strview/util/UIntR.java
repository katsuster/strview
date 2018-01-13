package net.katsuster.strview.util;

/**
 * <p>
 * 64bit の符号無し整数、リトルエンディアン
 * </p>
 *
 * <p>
 * 8, 16, 32, 64bit のみ有効です。
 * </p>
 */
public class UIntR extends AbstractNum
        implements Comparable<UIntR> {
    public UIntR() {
        this(null, 0, 64);
    }

    public UIntR(String n) {
        this(n, 0, 64);
    }

    public UIntR(String n, long v, int l) {
        super(n, l);
        setValue(v);
    }

    public UIntR(String n, LargeBitList b, long p, int l) {
        super(n, b, p, l);
    }

    public UIntR(UIntR obj) {
        super(obj);
    }

    @Override
    public int compareTo(UIntR obj) {
        return compareAsUInt(getValue(), obj.getValue());
    }

    @Override
    public float floatValue() {
        return uint64ToFloat(getValue());
    }

    @Override
    public double doubleValue() {
        return uint64ToDouble(getValue());
    }

    @Override
    public long getValue() {
        int nbit = (int) getRange().getLength();

        return reverseNum(getRaw(), nbit);
    }

    @Override
    public void setValue(long v) {
        int nbit = (int) getRange().getLength();

        setRaw(reverseNum(v, nbit));
    }

    /**
     * 値を表す String オブジェクトを返します。
     *
     * @return 10 進数 (基数 10) による文字列表現
     */
    @Override
    public String toString() {
        return uint64ToString(getValue());
    }
}
