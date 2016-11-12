package net.katsuster.strview.util;

/**
 * <p>
 * 半開区間 [start, end) を表すインタフェースです。
 * </p>
 *
 * <p>
 * start は区間に入りますが、end は区間に入りません。
 * つまり start = 0, end = 5 であれば、
 * isHit(0) は true、isHit(4) も true ですが、
 * isHit(5) は false、isHit(6) も false です。
 * </p>
 *
 * @author katsuhiro
 */
public interface Range {
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
    public long getStart();

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
    public void setStart(long p);

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
    public long getEnd();

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
    public void setEnd(long p);

    /**
     * <p>
     * 区間の長さを取得します。
     * </p>
     *
     * @return 区間の長さ
     */
    public long getLength();

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
    public void setLength(long l);

    /**
     * <p>
     * 指定したインデックスが区間内に入るかどうかを判定します。
     * </p>
     *
     * @param i インデックス
     * @return インデックスが区間内なら true、区間外なら false
     */
    public boolean isHit(long i);
}
