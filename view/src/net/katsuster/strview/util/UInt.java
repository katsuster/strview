package net.katsuster.strview.util;

/**
 * <p>
 * 64bit の符号無し整数
 * </p>
 *
 * @author katsuhiro
 */
public class UInt extends BaseInt
        implements Comparable<UInt> {
    public UInt() {
        this(0);
    }

    public UInt(long v) {
        super();

        setBitsValue(v);
    }

    public UInt(LargeBitList b, long p, int l) {
        super(b, p, l);
    }

    public UInt(UInt obj) {
        super(obj);
    }

    @Override
    public int compareTo(UInt obj) {
        return compareAsUInt(getBitsValue(), obj.getBitsValue());
    }

    @Override
    public float floatValue() {
        return uint64ToFloat(getBitsValue());
    }

    @Override
    public double doubleValue() {
        return uint64ToDouble(getBitsValue());
    }

    @Override
    protected long getV() {
        Range r = getRange();
        LargeBitList buf = r.getBuffer();

        if (r.getBuffer() == null) {
            return 0;
        }

        return buf.getPackedLong(r.getStart(), (int) r.getLength());
    }

    @Override
    protected void setV(long v) {
        Range r = getRange();
        LargeBitList buf = r.getBuffer();

        buf.setPackedLong(r.getStart(), (int) r.getLength(), v);
    }

    /**
     * 値を表す String オブジェクトを返します。
     *
     * @return 10 進数 (基数 10) による文字列表現
     */
    @Override
    public String toString() {
        return uint64ToString(getBitsValue());
    }
}
