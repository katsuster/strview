package net.katsuster.strview.media;

import net.katsuster.strview.util.*;

import java.util.List;

/**
 * <p>
 * パケットの存在する範囲と木構造を表すインタフェースです。
 * </p>
 *
 * @author katsuhiro
 */
public interface PacketRange extends Range {
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

    /**
     * <p>
     * 本体に子データを含められるかどうかを返します。
     * </p>
     *
     * @return 本体に子データを含められる場合は true、
     * 含められない場合は false
     */
    public boolean getRecursive();

    /**
     * <p>
     * 本体に子データを含められるかどうかを設定します。
     * </p>
     *
     * @param r 本体に子データを含められる場合は true、
     * 含められない場合は false
     */
    public void setRecursive(boolean r);

    /**
     * <p>
     * 木構造のストリームにおける、パケットの位置する深さを取得します。
     * </p>
     *
     * <p>
     * ノードの深さは親パケットを設定した際に、自動的に更新されます。
     * </p>
     *
     * @return パケットの位置する深さ
     */
    public int getLevel();

    /**
     * <p>
     * 子要素のリストの末尾に、新たに子要素を加えます。
     * </p>
     *
     * @param newChild リストの末尾に加える子要素
     * @return リストに加えられた子要素
     */
    public PacketRange appendChild(PacketRange newChild);

    /**
     * <p>
     * 子要素のリストから指定された要素を削除します。
     * </p>
     *
     * @param oldChild 削除したい子要素
     * @return リストから削除された子要素、
     * 削除する要素が見つからない場合は null
     */
    public PacketRange removeChild(PacketRange oldChild);

    /**
     * <p>
     * 子要素のリストに含まれる refChild の直前に、
     * newChild を追加します。
     * </p>
     *
     * @param newChild 追加したい子要素
     * @param refChild 追加する位置を表す子要素
     * @return リストに追加された子要素、
     * 追加できなかった場合は null
     */
    public PacketRange insertBefore(PacketRange newChild, PacketRange refChild);

    /**
     * <p>
     * 子要素のリストに含まれる oldChild を newChild に置き換えます。
     * </p>
     *
     * @param newChild 置き換え後の子要素
     * @param oldChild 置き換える対象となる子要素
     * @return リストの置き換えられた子要素、
     * 置き換えられなかった場合は null
     */
    public PacketRange replaceChild(PacketRange newChild, PacketRange oldChild);

    /**
     * <p>
     * 親要素を取得します。
     * </p>
     *
     * @return 親要素、親を持たない場合は null
     */
    public PacketRange getParentNode();

    /**
     * <p>
     * 親パケットを設定します。
     * </p>
     *
     * <p>
     * ノードの深さは親パケットの深さ + 1 に設定されます。
     * </p>
     *
     * @param p パケットの親パケット
     */
    public void setParentNode(PacketRange p);

    /**
     * <p>
     * 兄要素（親が持つ子要素のリストで自身の直前に位置する要素）
     * を取得します。
     * </p>
     *
     * @return 兄要素、兄を持たない場合は null
     */
    public PacketRange getPreviousSibling();

    /**
     * <p>
     * 弟要素（親が持つ子要素のリストで自身の直後に位置する要素）
     * を取得します。
     * </p>
     *
     * @return 弟要素、弟を持たない場合は null
     */
    public PacketRange getNextSibling();

    /**
     * <p>
     * 子要素を持つかどうかを取得します。
     * </p>
     *
     * @return 子要素を持つ場合は true、子要素を持たない場合は false
     */
    public boolean hasChildNodes();

    /**
     * <p>
     * 子要素のリストを取得します。
     * </p>
     *
     * @return 子要素のリスト
     */
    public List<PacketRange> getChildNodes();

    /**
     * <p>
     * 最初の子要素を取得します。
     * </p>
     *
     * @return 最初の子要素、
     * 子要素がない場合は null
     */
    public PacketRange getFirstChild();

    /**
     * <p>
     * 最後の子要素を取得します。
     * </p>
     *
     * @return 最後の子要素、
     * 子要素がない場合は null
     */
    public PacketRange getLastChild();

    /**
     * <p>
     * 子要素の数を取得します。
     * </p>
     *
     * <p>
     * この関数は getChildNodes().size() と同じ結果を返します。
     * </p>
     *
     * @return 子要素の数
     */
    public int getChildCounts();

    /**
     * <p>
     * 指定された位置の子要素を取得します。
     * </p>
     *
     * @param index 取得したい子要素の位置
     * @return 指定された位置にある子要素
     * @throws IndexOutOfBoundsException 負の位置、
     * または子要素の数を超える位置を指定した場合
     */
    public PacketRange getChild(int index);
}
