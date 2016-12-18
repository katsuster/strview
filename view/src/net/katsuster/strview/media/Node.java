package net.katsuster.strview.media;

import java.util.*;

/**
 * <p>
 * 木構造の要素を表すインタフェースです。
 * </p>
 *
 * @see Packet
 * @author katsuhiro
 */
public interface Node {
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
     * 木構造のストリームにおける、パケットの位置する深さを設定します。
     * </p>
     *
     * <p>
     * パケットの深さを別の値に上書きしたいときに使用します。
     * </p>
     *
     * @param l パケットの位置する深さ
     */
    public void setLevel(int l);

    /**
     * <p>
     * 子要素のリストの末尾に、新たに子要素を加えます。
     * </p>
     *
     * @param newChild リストの末尾に加える子要素
     * @return リストに加えられた子要素
     */
    public Node appendChild(Node newChild);

    /**
     * <p>
     * 子要素のリストから指定された要素を削除します。
     * </p>
     *
     * @param oldChild 削除したい子要素
     * @return リストから削除された子要素、
     * 削除する要素が見つからない場合は null
     */
    public Node removeChild(Node oldChild);

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
    public Node insertBefore(Node newChild, Node refChild);

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
    public Node replaceChild(Node newChild, Node oldChild);

    /**
     * <p>
     * 親要素を取得します。
     * </p>
     *
     * @return 親要素、親を持たない場合は null
     */
    public Node getParentNode();

    /**
     * <p>
     * 兄要素（親が持つ子要素のリストで自身の直前に位置する要素）
     * を取得します。
     * </p>
     *
     * @return 兄要素、兄を持たない場合は null
     */
    public Node getPreviousSibling();

    /**
     * <p>
     * 弟要素（親が持つ子要素のリストで自身の直後に位置する要素）
     * を取得します。
     * </p>
     *
     * @return 弟要素、弟を持たない場合は null
     */
    public Node getNextSibling();

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
    public List<Node> getChildNodes();

    /**
     * <p>
     * 最初の子要素を取得します。
     * </p>
     *
     * @return 最初の子要素、
     * 子要素がない場合は null
     */
    public Node getFirstChild();

    /**
     * <p>
     * 最後の子要素を取得します。
     * </p>
     *
     * @return 最後の子要素、
     * 子要素がない場合は null
     */
    public Node getLastChild();

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
    public Node getChild(int index);
}
