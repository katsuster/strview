package net.katsuster.strview.media;

import net.katsuster.strview.util.*;

/**
 * <p>
 * ヘッダ、本体、フッタの 3つの要素から構成されるデータの塊
 * （パケット）を表すインタフェースです。
 * </p>
 *
 * <p>
 * 実装する際は AbstractPacket クラスを継承すると便利です。
 * </p>
 *
 * @see AbstractPacket
 * @author katsuhiro
 */
public interface Packet extends Block, Node {
    /**
     * <p>
     * パケットの短い名前を取得します。
     * </p>
     *
     * @return パケットの短い名前
     */
    public String getShortName();

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
     * @param n パケット番号
     */
    public void setNumber(long n);

    /**
     * <p>
     * パケットが存在するストリーム中の位置を返します。
     * デフォルトではパケットのヘッダが存在する位置と同じです。
     * </p>
     *
     * <p>
     * パーサによって位置の付け方が異なりますが、
     * 通常はストリーム先頭を 0 とした位置（ビット単位）が設定されます。
     * </p>
     *
     * @return パケットが存在するストリーム中の位置（ビット単位）
     */
    public long getAddress();

    /**
     * <p>
     * パケットのヘッダが存在するストリーム中の位置を返します。
     * </p>
     *
     * <p>
     * パーサによって位置の付け方が異なりますが、
     * 通常はストリーム先頭を 0 とした位置（ビット単位）が設定されます。
     * </p>
     *
     * @return パケットのヘッダが存在するストリーム中の位置（ビット単位）
     */
    public long getHeaderAddress();

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
     * @return パケットの本体が存在するストリーム中の位置（ビット単位）
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
     * @return パケットのフッタが存在するストリーム中の位置（ビット単位）
     */
    public long getFooterAddress();

    /**
     * <p>
     * パケットの長さを返します。
     * </p>
     *
     * <p>
     * デフォルトではヘッダ、本体、フッタの長さの合計値です。
     * </p>
     *
     * @return パケットの長さ（ビット単位）
     */
    public long getLength();

    /**
     * <p>
     * パケットのヘッダの長さを返します。
     * </p>
     *
     * @return パケットのヘッダの長さ（ビット単位）
     */
    public long getHeaderLength();

    /**
     * <p>
     * パケットの本体の長さを返します。
     * </p>
     *
     * @return パケットの本体の長さ（ビット単位）
     */
    public long getBodyLength();

    /**
     * <p>
     * パケットのフッタの長さを返します。
     * </p>
     *
     * @return パケットのフッタの長さ（ビット単位）
     */
    public long getFooterLength();

    /**
     * <p>
     * パケットのヘッダを取得します。
     * </p>
     *
     * @return パケットのヘッダ
     */
    public Block getHeader();

    /**
     * <p>
     * パケット本体のビット列を取得します。
     * </p>
     *
     * @return パケット本体のビット列
     */
    public LargeBitList getBody();

    /**
     * <p>
     * パケットのフッタを取得します。
     * </p>
     *
     * @return パケットのフッタ
     */
    public Block getFooter();

    /**
     * <p>
     * パケット全体を表すビット列を取得します。
     * </p>
     *
     * @return ビット列
     */
    public LargeBitList getRawPacket();
}
