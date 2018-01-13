package net.katsuster.strview.util;

/**
 * <p>
 * バッファ上の半開区間 [start, end) を表すインタフェースです。
 * </p>
 *
 * <p>
 * end = start + length です。
 * </p>
 *
 * <p>
 * start は区間に入りますが、end は区間に入りません。
 * つまり start = 1, length = 5 (end = 6) であれば、
 * isHit(1) は true、isHit(5) も true ですが、
 * isHit(6) は false、isHit(7) も false です。
 * </p>
 */
public interface Range<T extends LargeList<?>> extends Cloneable {
    //size() および length() メソッドで長さが分からないときに返される値です
    public static final long LENGTH_UNKNOWN = -1;

    /**
     * オブジェクトのコピーを作成し、返します。
     *
     * @return この範囲のコピー
     * @throws CloneNotSupportedException clone をサポートしていない場合にスローされます。
     */
    public Object clone()
            throws CloneNotSupportedException;

    /**
     * <p>
     * バッファを取得します。
     * </p>
     *
     * @return バッファ
     */
    public T getBuffer();

    /**
     * <p>
     * バッファを設定します。
     * </p>
     *
     * @param b バッファ
     */
    public void setBuffer(T b);

    /**
     * <p>
     * 区間の開始点を取得します。
     * </p>
     *
     * <p>
     * getStart() の点は区間に入りますが、getEnd() の点は区間に入りません。
     * </p>
     *
     * @return 区間の開始点
     */
    public long getStart();

    /**
     * <p>
     * 区間の開始点を設定します。
     * </p>
     *
     * <p>
     * getStart() の点は区間に入りますが、getEnd() の点は区間に入りません。
     * </p>
     *
     * <p>
     * 開始点を設定しても、長さは変更されず、
     * 終了点が適切な値に変更されます。
     * </p>
     *
     * @param p 区間の開始点
     */
    public void setStart(long p);

    /**
     * <p>
     * 区間の終了点を取得します。
     * </p>
     *
     * <p>
     * getStart() の点は区間に入りますが、getEnd() の点は区間に入りません。
     * </p>
     *
     * @return 区間の終了点
     */
    public long getEnd();

    /**
     * <p>
     * 区間の終了点を設定します。
     * </p>
     *
     * <p>
     * 終了点を設定しても、開始点は変更されず、
     * 長さが適切な値に変更されます。
     * </p>
     *
     * <p>
     * getStart() の点は区間に入りますが、getEnd() の点は区間に入りません。
     * </p>
     *
     * @param p 区間の終了点
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
