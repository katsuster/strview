package net.katsuster.strview.media;

/**
 * <p>
 * データの場所を表します。
 * </p>
 *
 * @author katsuhiro
 */
public interface PacketPosition extends BlockPosition {
    /**
     * <p>
     * パケットの本体が存在するストリーム中の位置を返します。
     * </p>
     *
     * <p>
     * パーサによって位置の付け方が異なりますが、
     * 通常はストリーム先頭を 0 とした位置（ビット単位）が設定されます。
     * </p>
     *
     * @return パケットの本体が存在するストリーム中の位置
     */
    public long getBodyAddress();

    /**
     * <p>
     * パケットのフッタが存在するストリーム中の位置を返します。
     * </p>
     *
     * <p>
     * パーサによって位置の付け方が異なりますが、
     * 通常はストリーム先頭を 0 とした位置（ビット単位）が設定されます。
     * </p>
     *
     * @return パケットのフッタが存在するストリーム中の位置
     */
    public long getFooterAddress();

    /**
     * <p>
     * パケットのヘッダの長さを返します。
     * </p>
     *
     * <p>
     * パーサによって長さの付け方が異なりますが、
     * 通常はビット単位の長さです。
     * </p>
     *
     * @return パケットのヘッダの長さ
     */
    public long getHeaderLength();

    /**
     * <p>
     * パケットのヘッダの長さを設定します。
     * </p>
     *
     * <p>
     * パーサによって長さの付け方が異なりますが、
     * 通常はビット単位の長さです。
     * </p>
     *
     * @param len パケットのヘッダの長さ
     */
    public void setHeaderLength(long len);

    /**
     * <p>
     * パケットの本体の長さを返します。
     * </p>
     *
     * <p>
     * パーサによって長さの付け方が異なりますが、
     * 通常はビット単位の長さです。
     * </p>
     *
     * @return パケットの本体の長さ
     */
    public long getBodyLength();

    /**
     * <p>
     * パケットの本体の長さを設定します。
     * </p>
     *
     * <p>
     * パーサによって長さの付け方が異なりますが、
     * 通常はビット単位の長さです。
     * </p>
     *
     * @param len パケットの本体の長さ
     */
    public void setBodyLength(long len);

    /**
     * <p>
     * パケットのフッタの長さを返します。
     * </p>
     *
     * <p>
     * パーサによって長さの付け方が異なりますが、
     * 通常はビット単位の長さです。
     * </p>
     *
     * @return パケットのフッタの長さ
     */
    public long getFooterLength();

    /**
     * <p>
     * パケットのフッタの長さを設定します。
     * </p>
     *
     * <p>
     * パーサによって長さの付け方が異なりますが、
     * 通常はビット単位の長さです。
     * </p>
     *
     * @param len パケットのフッタの長さ
     */
    public void setFooterLength(long len);
}
