package net.katsuster.strview.util;

/**
 * <p>
 * バッファ上の半開区間 [start, end) を表すクラスです。
 * </p>
 *
 * <p>
 * end = start + length です。
 * </p>
 */
public class SimpleRange
        implements Range {
    private LargeBitList buffer;
    private long start;
    private long length;

    /**
     * <p>
     * バッファ、開始点、終了点を指定せず半開区間を構築します。
     * </p>
     */
    public SimpleRange() {
        this(null, 0, 0);
    }

    /**
     * <p>
     * バッファを指定せず、開始点と終了点を指定して半開区間を構築します。
     * </p>
     *
     * @param s 区間の開始地点
     * @param l 区間の長さ
     */
    public SimpleRange(long s, long l) {
        this(null, s, l);
    }

    /**
     * <p>
     * バッファ、開始点、終了点を指定して半開区間を構築します。
     * </p>
     *
     * @param b バッファ
     * @param s 区間の開始地点
     * @param l 区間の長さ
     */
    public SimpleRange(LargeBitList b, long s, long l) {
        if (s < 0) {
            throw new IllegalArgumentException("start:" + s + " is negative.");
        }
        if (l != Range.LENGTH_UNKNOWN && l < 0) {
            throw new NegativeArraySizeException("length:" + l + " is negative.");
        }
        buffer = b;
        start = s;
        length = l;
    }

    /**
     * <p>
     * 半開区間を構築します。
     * </p>
     *
     * @param obj 区間
     */
    public SimpleRange(Range obj) {
        this(obj.getBuffer(), obj.getStart(), obj.getLength());
    }

    /**
     * <p>
     * 区間の位置と長さを、
     * コピーした新たな区間を返します。
     * </p>
     *
     * @return コピーされたオブジェクト
     * @throws CloneNotSupportedException インスタンスを複製できない場合
     */
    public Object clone()
            throws CloneNotSupportedException {
        SimpleRange obj = (SimpleRange)super.clone();

        return obj;
    }

    /**
     * <p>
     * オブジェクトを指定されたオブジェクトと比較します。
     * 結果が true になるのは、引数が null ではなく、
     * このオブジェクトと同じ生成元、開始位置、
     * 長さを持つオブジェクトである場合だけです。
     * </p>
     *
     * @param obj 比較対象のオブジェクト
     * @return オブジェクトが同じである場合は true、そうでない場合は false
     */
    @Override
    public boolean equals(Object obj) {
        SimpleRange objp;

        if (!(obj instanceof SimpleRange)) {
            return false;
        }
        objp = (SimpleRange)obj;

        if ((buffer != objp.buffer)
                || (start != objp.start)
                || (length != objp.length)) {
            return false;
        }

        return true;
    }

    /**
     * <p>
     * オブジェクトのハッシュコードを返します。
     * </p>
     *
     * @return オブジェクトが保持する buffer のハッシュコードと、
     * start, length を変換式 (val ^ (val &gt;&gt; 32)) にて int に変換した値を
     * 全て xor した値
     */
    @Override
    public int hashCode() {
        int h;

        if (buffer == null) {
            h = 0;
        } else {
            h = buffer.hashCode();
        }

        return (int)(h
                ^ (start ^ (start >> 32))
                ^ (length ^ (length >> 32)));
    }

    @Override
    public LargeBitList getBuffer() {
        return buffer;
    }

    @Override
    public void setBuffer(LargeBitList b) {
        buffer = b;
    }

    @Override
    public long getStart() {
        return start;
    }

    @Override
    public void setStart(long p) {
        if (p < 0) {
            throw new IllegalArgumentException("new start:" + p + " is negative.");
        }
        start = p;
    }

    @Override
    public long getEnd() {
        if (length == Range.LENGTH_UNKNOWN) {
            return Range.LENGTH_UNKNOWN;
        }
        return start + length;
    }

    @Override
    public void setEnd(long p) {
        if (p < start) {
            throw new NegativeArraySizeException("new end:" + p + " is less than start:" + start + ".");
        }
        length = p - start;
    }

    @Override
    public long getLength() {
        return length;
    }

    @Override
    public void setLength(long l) {
        if (l != Range.LENGTH_UNKNOWN && l < 0) {
            throw new NegativeArraySizeException("new length:" + l + " is negative.");
        }
        length = l;
    }

    @Override
    public boolean isHit(long i) {
        if (getStart() <= i && i < getEnd()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return String.format("addr:%d-%d(len:%d)",
                getStart(), getEnd(), getLength());
    }
}
