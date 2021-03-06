package net.katsuster.strview.media;

import net.katsuster.strview.util.*;

/**
 * <p>
 * ヘッダ、本体、フッタの 3つの要素から構成されるデータの塊
 * （パケット）を表すインタフェースです。
 * </p>
 *
 * <p>
 * 実装する際は PacketAdapter クラスを継承すると便利です。
 * </p>
 *
 * @see PacketAdapter
 */
public interface Packet<T>
        extends Block<T> {
    /**
     * <p>
     * パケットの存在する範囲を取得します。
     * </p>
     *
     * @return データの範囲
     */
    @Override
    public PacketRange<LargeList<T>> getRange();

    /**
     * <p>
     * パケット本体に別のパケットを含められるかどうかを返します。
     * </p>
     *
     * @return パケット本体に別のパケットを含められる場合は true、
     * 含められない場合は false
     */
    public boolean isRecursive();

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
     * パケット本体を表すリストを取得します。
     * </p>
     *
     * @return パケット本体を表すリスト
     */
    public LargeList<T> getBody();

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
     * パケット全体を表すリストを取得します。
     * </p>
     *
     * @return パケット全体を表すリスト
     */
    public LargeList<T> getRawPacket();
}
