package net.katsuster.strview.util.bit;

/**
 * <p>
 * 64bit の符号無し整数
 * </p>
 */
public class UInt extends AbstractNum
        implements Comparable<UInt> {
    public UInt() {
        this(null, 0, 64);
    }

    public UInt(String n) {
        this(n, 0, 64);
    }

    public UInt(String n, long v, int l) {
        super(n, l);
        setValue(v);
    }

    public UInt(String n, LargeBitList b, long p, int l) {
        super(n, b, p, l);
    }

    public UInt(UInt obj) {
        super(obj);
    }

    @Override
    public int compareTo(UInt obj) {
        return compareAsUInt(getValue(), obj.getValue());
    }

    @Override
    public String getTypeName() {
        return "UInt";
    }

    @Override
    public float floatValue() {
        return uint64ToFloat(getValue());
    }

    @Override
    public double doubleValue() {
        return uint64ToDouble(getValue());
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
