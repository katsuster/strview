package net.katsuster.strview.media;

/**
 * <p>
 * データの存在する範囲を表します。
 * </p>
 *
 * @author katsuhiro
 */
public interface BlockRange extends Cloneable {
    /**
     * <p>
     * パケットの通し番号を返します。
     * </p>
     *
     * <p>
     * パーサによって番号の付け方が異なりますが、
     * 通常はストリーム先頭を 0 とした番号が設定されます。
     * </p>
     *
     * @return パケット番号
     */
    public long getNumber();

    /**
     * <p>
     * パケットの通し番号を設定します。
     * </p>
     *
     * <p>
     * パーサによって番号の付け方が異なりますが、
     * 通常はストリーム先頭を 0 とした番号が設定されます。
     * </p>
     *
     * @param num パケット番号
     */
    public void setNumber(long num);

    /**
     * <p>
     * パケットが存在するストリーム中の位置を返します。
     * </p>
     *
     * <p>
     * パーサによって位置の付け方が異なりますが、
     * 通常はストリーム先頭を 0 とした位置（ビット単位）が設定されます。
     * </p>
     *
     * @return パケットが存在するストリーム中の位置
     */
    public long getStart();

    /**
     * <p>
     * パケットが存在するストリーム中の位置を設定します。
     * </p>
     *
     * <p>
     * パーサによって位置の付け方が異なりますが、
     * 通常はストリーム先頭を 0 とした位置（ビット単位）が設定されます。
     * </p>
     *
     * @param addr パケットが存在するストリーム中の位置
     */
    public void setStart(long addr);

    /**
     * <p>
     * パケットの長さを返します。
     * </p>
     *
     * <p>
     * パーサによって長さの付け方が異なりますが、
     * 通常はビット単位の長さです。
     * </p>
     *
     * @return パケットの長さ
     */
    public long getLength();

    /**
     * <p>
     * パケットの長さを返します。
     * </p>
     *
     * <p>
     * パーサによって長さの付け方が異なりますが、
     * 通常はビット単位の長さです。
     * </p>
     *
     * @param len パケットの長さ
     */
    public void setLength(long len);
}
