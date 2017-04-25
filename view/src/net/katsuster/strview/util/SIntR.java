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

        setBitsValue(v);
    }

    public SIntR(LargeBitList b, long p, int l) {
        super(b, p, l);
    }

    public SIntR(SIntR obj) {
        super(obj);
    }

    @Override
    public int compareTo(SIntR obj) {
        return compareAsSInt(getBitsValue(), obj.getBitsValue());
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
    protected long getV() {
        Range r = getRange();
        LargeBitList buf = r.getBuffer();
        int nbit = (int)r.getLength();

        if (r.getBuffer() == null) {
            return 0;
        }

        return reverseNum(buf.getPackedLong(r.getStart(), nbit), nbit);
    }

    @Override
    protected void setV(long v) {
        Range r = getRange();
        LargeBitList buf = r.getBuffer();
        int nbit = (int)r.getLength();

        buf.setPackedLong(r.getStart(), nbit, reverseNum(v, nbit));
    }

    /**
     * 値を表す String オブジェクトを返します。
     *
     * @return 10 進数 (基数 10) による文字列表現
     */
    @Override
    public String toString() {
        return Long.toString(getBitsValue());
    }
}
