package net.katsuster.strview.util;

/**
 * <p>
 * 半開区間 [start, end) を表すクラスです。
 * </p>
 *
 * @author katsuhiro
 */
public class SimpleRange
        implements Range, Cloneable {
    private long start;
    private long end;

    /**
     * <p>
     * 開始点と終了点を指定して半開区間を構築します。
     * </p>
     *
     * @param s 区間の開始地点
     * @param e 区間の終了地点
     */
    public SimpleRange(long s, long e) {
        start = s;
        end = e;
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
                || (end != objp.end)) {
            return false;
        }

        return true;
    }

    /**
     * <p>
     * オブジェクトのハッシュコードを返します。
     * </p>
     *
     * @return オブジェクトが保持する start, end を、
     * 変換式 (val ^ (val &gt;&gt; 32)) にて int に変換した値を
     * 全て xor した値
     */
    @Override
    public int hashCode() {
        return (int)((start ^ (start >> 32))
                ^ (end ^ (end >> 32)));
    }

    /**
     * <p>
     * 区間の開始地点を取得します。
     * </p>
     *
     * <p>
     * getStart() の点は区間に入りますが、getEnd() の点は区間に入りません。
     * </p>
     *
     * @return 区間の開始地点
     */
    public long getStart() {
        return start;
    }

    /**
     * <p>
     * 区間の開始地点を設定します。
     * </p>
     *
     * <p>
     * getStart() の点は区間に入りますが、getEnd() の点は区間に入りません。
     * </p>
     *
     * @param p 区間の開始地点
     */
    public void setStart(long p) {
        start = p;
    }

    /**
     * <p>
     * 区間の終了地点を取得します。
     * </p>
     *
     * <p>
     * getStart() の点は区間に入りますが、getEnd() の点は区間に入りません。
     * </p>
     *
     * @return 区間の終了地点
     */
    public long getEnd() {
        return end;
    }

    /**
     * <p>
     * 区間の終了地点を設定します。
     * </p>
     *
     * <p>
     * getStart() の点は区間に入りますが、getEnd() の点は区間に入りません。
     * </p>
     *
     * @param p 区間の終了地点
     */
    public void setEnd(long p) {
        end = p;
    }

    /**
     * <p>
     * 区間の長さを取得します。
     * </p>
     *
     * @return 区間の長さ
     */
    public long getLength() {
        return (end - start);
    }

    /**
     * <p>
     * 区間の長さを設定します。
     * </p>
     *
     * <p>
     * 長さを設定すると、開始点は変更されず、
     * 終了点が適切な位置に変更されます。
     * </p>
     *
     * @param l 区間の長さ
     */
    public void setLength(long l) {
        end = start + l;
    }

    /**
     * <p>
     * 指定したインデックスが区間内に入るかどうかを判定します。
     * </p>
     *
     * @param i インデックス
     * @return インデックスが区間内なら true、区間外なら false
     */
    public boolean isHit(long i) {
        if (getStart() <= i && i < getEnd()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return String.format("start:%d - end:%d(length:%d)",
                getStart(), getEnd(), getLength());
    }
}
