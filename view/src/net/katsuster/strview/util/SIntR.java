package net.katsuster.strview.util;

/**
 * <p>
 * 64bit の符号あり整数、リトルエンディアン
 * </p>
 *
 * <p>
 * 8, 16, 32, 64bit のみ有効です。
 * </p>
 */
public class SIntR extends AbstractNum
        implements Comparable<SIntR> {
    public SIntR() {
        this(0, 64);
    }

    public SIntR(long v, int l) {
        super(l);
        setValue(v);
    }

    public SIntR(LargeBitList b, long p, int l) {
        super(b, p, l);
    }

    public SIntR(SIntR obj) {
        super(obj);
    }

    @Override
    public int compareTo(SIntR obj) {
        return compareAsSInt(getValue(), obj.getValue());
    }

    @Override
    public long getValue() {
        int nbit = (int) getRange().getLength();

        return signext(reverseNum(getRaw(), nbit), nbit);
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
        return Long.toString(getValue());
    }
}
