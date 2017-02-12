package net.katsuster.strview.util;

/**
 * <p>
 * 半開区間 [start, end) を表すクラスです。
 * </p>
 *
 * <p>
 * end = start + length です。
 * </p>
 *
 * @author katsuhiro
 */
public class SimpleRange
        implements Range, Cloneable {
    private long start;
    private long length;

    /**
     * <p>
     * 開始点と終了点を指定せず半開区間を構築します。
     * </p>
     */
    public SimpleRange() {
        this(0, 0);
    }

    /**
     * <p>
     * 開始点と終了点を指定して半開区間を構築します。
     * </p>
     *
     * @param s 区間の開始地点
     * @param l 区間の長さ
     */
    public SimpleRange(long s, long l) {
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
        this(obj.getStart(), obj.getLength());
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
    public SimpleRange clone() throws CloneNotSupportedException {
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

        if ((start != objp.start)
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
     * @return オブジェクトが保持する start, length を、
     * 変換式 (val ^ (val &gt;&gt; 32)) にて int に変換した値を
     * 全て xor した値
     */
    @Override
    public int hashCode() {
        return (int)((start ^ (start >> 32))
                ^ (length ^ (length >> 32)));
    }

    @Override
    public long getStart() {
        return start;
    }

    @Override
    public void setStart(long p) {
        start = p;
    }

    @Override
    public long getEnd() {
        return start + length;
    }

    @Override
    public void setEnd(long p) {
        length = p - start;
    }

    @Override
    public long getLength() {
        return length;
    }

    @Override
    public void setLength(long l) {
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
