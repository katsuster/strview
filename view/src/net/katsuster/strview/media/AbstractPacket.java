package net.katsuster.strview.media;

import java.util.*;

import net.katsuster.strview.util.*;

/**
 * <p>
 * ヘッダ、本体、フッタの 3要素からなる、
 * 小さな単位に分割されたデータの塊（パケットなど）を表す、
 * 抽象クラスです。
 * </p>
 *
 * <p>
 * パケットはヘッダ、本体、フッタで構成されます。
 * パケットの要素はどこに配置してもかまいません。
 * 特に決まりがなければ下記のガイドラインを目安に配置してください。
 * </p>
 *
 * <ul>
 * <li>他の要素に依存せず読み出し可能な要素は、ヘッダに配置します。</li>
 * <li>パケットと関連のないデータ、コンテナ内の可変長のペイロードなどは、
 * 本体に配置します。</li>
 * <li>ヘッダや本体より後に位置するものの、ヘッダや本体の要素により
 * 読み出し内容が変わらない場合、フッタに配置します。</li>
 * </ul>
 *
 * <p>
 * ほとんどのメソッドが空の実装となる場合は、
 * PacketAdapter を継承し必要な関数のみをオーバライドすると便利です。
 * </p>
 *
 * <p>
 * このクラスを継承するには、下記のメソッドを実装する必要があります。
 * </p>
 *
 * <dl>
 * <dt>convertHeader() メソッド</dt>
 * <dd>パケットのヘッダが保持するデータを、
 * 別の形式に変換します。</dd>
 * <dt>convertBody() メソッド</dt>
 * <dd>パケットの本体が保持するデータを、
 * 別の形式に変換します。</dd>
 * <dt>convertFooter() メソッド</dt>
 * <dd>パケットのフッタが保持するデータを、
 * 別の形式に変換します。</dd>
 *
 * <dt>setupBeforeNotifyStartPacket() メソッド</dt>
 * <dd>パケット開始イベントが通知される直前に、
 * パケットを設定するために呼び出されます。</dd>
 * <dt>setupAfterNotifyStartPacket() メソッド</dt>
 * <dd>パケット開始イベントが通知された直後に、
 * パケットを設定するために呼び出されます。</dd>
 * <dt>setupBeforeNotifyEndPacket() メソッド</dt>
 * <dd>パケット終端イベントが通知される直前に、
 * パケットを設定するために呼び出されます。</dd>
 * <dt>setupAfterNotifyEndPacket() メソッド</dt>
 * <dd>パケット終端イベントが通知された直後に、
 * パケットを設定するために呼び出されます。</dd>
 * </dl>
 *
 * <p>
 * また、下記のメソッドを実装することが推奨されます。
 * </p>
 *
 * <dl>
 * <dt>コンストラクタ</dt>
 * <dd>クラス特有の方法でオブジェクトを初期化します。</dd>
 *
 * <dt>toString() メソッド(オーバライド)</dt>
 * <dd>ヘッダ、本体、フッタが保持するデータを表す文字列を取得します。</dd>
 * </dl>
 *
 * @see PacketAdapter
 * @author katsuhiro
 */
public abstract class AbstractPacket
        implements Packet, Cloneable {
    //パケットの通し番号
    private long num;

    //パケットのヘッダのアドレス（ビット単位）
    private long address_header;

    //パケットのヘッダの長さ（ビット単位）
    private long len_header;
    //パケットの本体の長さ（ビット単位）
    private long len_body;
    //パケットのフッタの長さ（ビット単位）
    private long len_footer;

    //ヘッダ
    private Block head;
    //本体を表すビット列
    private LargeBitList body;
    //フッタ
    private Block foot;

    //パケット全体を表すビット列
    private LargeBitList raw_packet;

    //パケットの階層レベル
    private int level;
    //パケットの親
    private Node parent;
    //パケットの子供
    private List<Node> children;

    /**
     * <p>
     * 親も子も持たないパケットを作成します。
     * </p>
     */
    public AbstractPacket() {
        this(null);
    }

    /**
     * <p>
     * 指定された親を持ち、子要素を持たないパケットを作成します。
     * </p>
     *
     * @param pp 親パケット
     */
    public AbstractPacket(Packet pp) {
        super();

        num = 0;
        setParentNode(pp);
        children = new ArrayList<Node>();
        address_header = 0;
        len_header = 0;
        len_body = 0;
        len_footer = 0;
        head = new BlockAdapter();
        foot = new BlockAdapter();
    }

    @Override
    public AbstractPacket clone()
            throws CloneNotSupportedException {
        AbstractPacket obj = (AbstractPacket)super.clone();
        int i;

        obj.parent = parent;
        obj.children = new ArrayList<Node>();
        for (i = 0; i < children.size(); i++) {
            obj.children.add(children.get(i));
        }

        obj.head = head.clone();
        obj.body = body;
        obj.foot = foot.clone();
        obj.raw_packet = raw_packet;

        return obj;
    }

    /**
     * <p>
     * パケットの短い名前を取得します。
     * </p>
     *
     * <p>
     * このクラスではクラス名 Class.getCanonicalName() を返します。
     * 継承するクラスにおいてはこのメソッドをオーバライドして、
     * より適切な名前を返すことが推奨されます。
     * </p>
     *
     * @return パケットの短い名前
     */
    @Override
    public String getShortName() {
        return getClass().getName();
    }

    @Override
    public long getNumber() {
        return num;
    }

    @Override
    public void setNumber(long n) {
        num = n;
    }

    @Override
    public long getAddress() {
        return getHeaderAddress();
    }

    @Override
    public long getHeaderAddress() {
        return address_header;
    }

    /**
     * <p>
     * パケットのヘッダが存在するストリーム中の位置を設定します。
     * </p>
     *
     * <p>
     * パーサによって位置の付け方が異なりますが、
     * 通常はストリーム先頭を 0 とした位置（ビット単位）が設定されます。
     * </p>
     *
     * @param s パケットのヘッダが存在するストリーム中の位置（ビット単位）
     */
    public void setHeaderAddress(long s) {
        address_header = s;
    }

    @Override
    public long getBodyAddress() {
        return getHeaderAddress() + getHeaderLength();
    }

    @Override
    public long getFooterAddress() {
        return getBodyAddress() + getBodyLength();
    }

    @Override
    public long getLength() {
        return getHeaderLength() + getBodyLength() + getFooterLength();
    }

    @Override
    public long getHeaderLength() {
        return len_header;
    }

    /**
     * <p>
     * パケットのヘッダの長さを設定します。
     * </p>
     *
     * @param s パケットのヘッダの長さ（ビット単位）
     */
    public void setHeaderLength(long s) {
        len_header = s;
    }

    @Override
    public long getBodyLength() {
        return len_body;
    }

    /**
     * <p>
     * パケットの本体の長さを設定します。
     * </p>
     *
     * @param s パケットの本体の長さ（ビット単位）
     */
    public void setBodyLength(long s) {
        len_body = s;
    }

    @Override
    public long getFooterLength() {
        return len_footer;
    }

    /**
     * <p>
     * パケットのフッタの長さを設定します。
     * </p>
     *
     * @param s パケットのフッタの長さ（ビット単位）
     */
    public void setFooterLength(long s) {
        len_footer = s;
    }

    @Override
    public Block getHeader() {
        return head;
    }

    /**
     * <p>
     * パケットのヘッダを設定します。
     * </p>
     *
     * @param h パケットのヘッダ
     */
    protected void setHeader(Block h) {
        head = h;
    }

    @Override
    public LargeBitList getBody() {
        return body;
    }

    /**
     * <p>
     * パケット本体のビット列を設定します。
     * </p>
     *
     * @param b パケット本体のビット列
     */
    protected void setBody(LargeBitList b) {
        body = b;
    }

    /**
     * <p>
     * 指定したビット列の部分ビット列を、
     * パケット本体のビット列として設定します。
     * </p>
     *
     * <p>
     * from の位置は本体のビット列に含まれます。
     * to の位置のビットは本体のビット列には含まれません。
     * </p>
     *
     * <p>
     * from が to より小さい場合、本体のビット列の長さは 0 となります。
     * </p>
     *
     * @param b パケット本体のビット列を含むビット列
     * @param from パケット本体の開始位置（ビット単位）
     * @param to パケット本体の終了位置（ビット単位）
     */
    protected void setBody(LargeBitList b, long from, long to) {
        if (from > to) {
            //長さが負の場合
            System.err.printf("Warning: body length is negative."
                            + "(from:0x%08x, to:0x%08x, len:0x%08x)\n",
                    from, to, to - from);

            //長さを 0 にする
            to = from;
        }

        body = b.subLargeList(from, to);
    }

    @Override
    public Block getFooter() {
        return foot;
    }

    /**
     * <p>
     * パケットのフッタを設定します。
     * </p>
     *
     * @param f パケットのフッタ
     */
    protected void setFooter(Block f) {
        foot = f;
    }

    @Override
    public LargeBitList getRawPacket() {
        return raw_packet;
    }

    /**
     * <p>
     * パケット全体のビット列を設定します。
     * </p>
     *
     * @param b パケット全体のビット列
     */
    protected void setRawPacket(LargeBitList b) {
        raw_packet = b;
    }

    /**
     * <p>
     * パケットのヘッダを別の形式に変換します。
     * </p>
     *
     * <p>
     * 変換結果は PacketConverter オブジェクトに保持されます。
     * </p>
     *
     * @param c 各メンバの変換を実施するオブジェクト
     */
    protected abstract void convertHeader(PacketConverter<?> c);

    /**
     * <p>
     * パケットの本体を別の形式に変換します。
     * </p>
     *
     * <p>
     * 変換結果は PacketConverter オブジェクトに保持されます。
     * </p>
     *
     * @param c 各メンバの変換を実施するオブジェクト
     */
    protected abstract void convertBody(PacketConverter<?> c);

    /**
     * <p>
     * パケットのフッタを別の形式に変換します。
     * </p>
     *
     * <p>
     * 変換結果は PacketConverter オブジェクトに保持されます。
     * </p>
     *
     * @param c 各メンバの変換を実施するオブジェクト
     */
    protected abstract void convertFooter(PacketConverter<?> c);

    /**
     * <p>
     * パケット全体を別の形式に変換します。
     * </p>
     *
     * @param c 各メンバの変換を実施するオブジェクト
     */
    protected void convertRawPacket(PacketConverter<?> c) {
        long org_pos;

        org_pos = c.position();
        c.position(getHeaderAddress());
        raw_packet = c.convBitList(getLength(), raw_packet, "raw_packet");
        c.position(org_pos);
    }

    /**
     * <p>
     * パケットを読み込みます。
     * </p>
     *
     * <p>
     * パケットのアドレスやサイズが更新されます。
     * </p>
     *
     * @param c 各メンバの変換を実施するオブジェクト
     */
    @Override
    public void read(PacketConverter<?> c) {
        c.enterPacket(getShortName());

        setHeaderAddress(c.position());
        convertHeader(c);
        setHeaderLength(c.position() - getHeaderAddress());
        convertBody(c);
        setBodyLength(c.position() - getBodyAddress());
        convertFooter(c);
        setFooterLength(c.position() - getFooterAddress());
        convertRawPacket(c);

        c.leavePacket();
    }

    /**
     * <p>
     * パケットを書き込みます。
     * </p>
     *
     * <p>
     * パケットのアドレスやサイズが更新されます。
     * </p>
     *
     * @param c 各メンバの変換を実施するオブジェクト
     */
    @Override
    public void write(PacketConverter<?> c) {
        c.enterPacket(getShortName());

        setHeaderAddress(c.position());
        convertHeader(c);
        setHeaderLength(c.position() - getHeaderAddress());
        convertBody(c);
        setBodyLength(c.position() - getBodyAddress());
        convertFooter(c);
        setFooterLength(c.position() - getFooterAddress());
        convertRawPacket(c);

        c.leavePacket();
    }

    /**
     * <p>
     * パケットを別の形式に変換します。
     * </p>
     *
     * <p>
     * 変換結果は PacketConverter オブジェクトに保持されます。
     * </p>
     *
     * @param c 各メンバの変換を実施するオブジェクト
     */
    @Override
    public void convert(PacketConverter<?> c) {
        c.enterPacket(getShortName());

        convertHeader(c);
        convertBody(c);
        convertFooter(c);
        convertRawPacket(c);

        c.leavePacket();
    }

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
    @Override
    public int getLevel() {
        return level;
    }

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
    @Override
    public void setLevel(int l) {
        level = l;
    }

    /**
     * <p>
     * 子要素のリストの末尾に、新たに子要素を加えます。
     * </p>
     *
     * @param newChild リストの末尾に加える子要素
     * @return リストに加えられた子要素
     */
    @Override
    public Node appendChild(Node newChild) {
        if (newChild == null) {
            throw new IllegalArgumentException(
                    "newChild is null.");
        }

        children.add(newChild);
        if (newChild instanceof AbstractPacket) {
            ((AbstractPacket)newChild).setParentNode(this);
        }

        return newChild;
    }

    /**
     * <p>
     * 子要素のリストから指定された要素を削除します。
     * </p>
     *
     * @param oldChild 削除したい子要素
     * @return リストから削除された子要素、
     * 削除する要素が見つからない場合は null
     */
    @Override
    public Node removeChild(Node oldChild) {
        boolean result;

        if (oldChild == null) {
            throw new IllegalArgumentException(
                    "oldChild is null.");
        }

        result = children.remove(oldChild);
        if (result) {
            if (oldChild instanceof AbstractPacket) {
                ((AbstractPacket)oldChild).setParentNode(null);
            }

            return oldChild;
        } else {
            return null;
        }
    }

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
    @Override
    public Node insertBefore(Node newChild, Node refChild) {
        int index;

        if (newChild == null || refChild == null) {
            throw new IllegalArgumentException(
                    "newChild or refChild is null.");
        }

        index = children.indexOf(refChild);
        if (index == -1) {
            return null;
        }

        children.add(index, newChild);
        if (newChild instanceof AbstractPacket) {
            ((AbstractPacket)newChild).setParentNode(this);
        }

        return newChild;
    }

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
    @Override
    public Node replaceChild(Node newChild, Node oldChild) {
        int index;

        if (newChild == null || oldChild == null) {
            throw new IllegalArgumentException(
                    "newChild or oldChild is null.");
        }

        index = children.indexOf(oldChild);
        if (index == -1) {
            return null;
        }

        children.set(index, newChild);
        if (oldChild instanceof AbstractPacket) {
            ((AbstractPacket)oldChild).setParentNode(null);
        }
        if (newChild instanceof AbstractPacket) {
            ((AbstractPacket)newChild).setParentNode(this);
        }

        return newChild;
    }

    /**
     * <p>
     * 親要素を取得します。
     * </p>
     *
     * @return 親要素、親を持たない場合は null
     */
    @Override
    public Node getParentNode() {
        return parent;
    }

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
    protected void setParentNode(Node p) {
        parent = p;

        if (p != null) {
            setLevel(p.getLevel() + 1);
        } else {
            setLevel(0);
        }
    }

    /**
     * <p>
     * 兄要素（親が持つ子要素のリストで自身の直前に位置する要素）
     * を取得します。
     * </p>
     *
     * @return 兄要素、兄を持たない場合は null
     */
    @Override
    public Node getPreviousSibling() {
        List<Node> l;
        int index;

        if (getParentNode() == null) {
            return null;
        }

        l = getParentNode().getChildNodes();
        index = l.indexOf(this);
        if (index == -1 || index <= 0) {
            return null;
        }

        return l.get(index - 1);
    }

    /**
     * <p>
     * 弟要素（親が持つ子要素のリストで自身の直後に位置する要素）
     * を取得します。
     * </p>
     *
     * @return 弟要素、弟を持たない場合は null
     */
    @Override
    public Node getNextSibling() {
        List<Node> l;
        int index;

        if (getParentNode() == null) {
            return null;
        }

        l = getParentNode().getChildNodes();
        index = getParentNode().getChildNodes().indexOf(this);
        if ((index == -1) || (l.size() - 1 <= index)) {
            return null;
        }

        return getParentNode().getChildNodes().get(index + 1);
    }

    /**
     * <p>
     * 子要素を持つかどうかを取得します。
     * </p>
     *
     * @return 子要素を持つ場合は true、子要素を持たない場合は false
     */
    @Override
    public boolean hasChildNodes() {
        return !children.isEmpty();
    }

    /**
     * <p>
     * 子要素のリストを取得します。
     * </p>
     *
     * @return 子要素のリスト
     */
    @Override
    public List<Node> getChildNodes() {
        return children;
    }

    /**
     * <p>
     * 最初の子要素を取得します。
     * </p>
     *
     * @return 最初の子要素、
     * 子要素がない場合は null
     */
    @Override
    public Node getFirstChild() {
        if (children.isEmpty()) {
            return null;
        }

        return children.get(0);
    }

    /**
     * <p>
     * 最後の子要素を取得します。
     * </p>
     *
     * @return 最後の子要素、
     * 子要素がない場合は null
     */
    @Override
    public Node getLastChild() {
        if (children.isEmpty()) {
            return null;
        }

        return children.get(children.size() - 1);
    }

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
    @Override
    public int getChildCounts() {
        return children.size();
    }

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
    @Override
    public Node getChild(int index) {
        return children.get(index);
    }

    public static void convert(PacketConverter<?> c,
                               AbstractPacket d) {
        c.enterBlock("standard packet header");

        c.mark("tag_num",
                d.getNumber());
        c.mark("addr(head,body,foot)(hex)",
                String.format("%x.%d(%x.%d, %x.%d, %x.%d)",
                        d.getAddress() >>> 3,d. getAddress() & 7,
                        d.getHeaderAddress() >>> 3, d.getHeaderAddress() & 7,
                        d.getBodyAddress() >>> 3, d.getBodyAddress() & 7,
                        d.getFooterAddress() >>> 3, d.getFooterAddress() & 7));
        c.mark("len (head,body,foot)(dec)",
                String.format("%d.%d(%d.%d, %d.%d, %d.%d)",
                        d.getLength() >>> 3, d.getLength() & 7,
                        d.getHeaderLength() >>> 3, d.getHeaderLength() & 7,
                        d.getBodyLength() >>> 3, d.getBodyLength() & 7,
                        d.getFooterLength() >>> 3, d.getFooterLength() & 7));
        c.mark("level",
                d.getLevel());

        c.leaveBlock();
    }

    @Override
    public String toString() {
        PacketConverter<String> c = new ToStringConverter();

        c.doInit();
        convert(c);
        c.doFinal();

        return c.getResult();
    }
}
