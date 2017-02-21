package net.katsuster.strview.media;

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
 * <dt>readHeader() メソッド</dt>
 * <dd>別の形式からパケットのヘッダに変換します。</dd>
 * <dt>readBody() メソッド</dt>
 * <dd>別の形式からパケットの本体に変換します。</dd>
 * <dt>readFooter() メソッド</dt>
 * <dd>別の形式から、パケットのフッタに変換します。</dd>
 *
 * <dt>writeHeader() メソッド</dt>
 * <dd>パケットのヘッダが保持するデータを、
 * 別の形式に変換します。</dd>
 * <dt>writeBody() メソッド</dt>
 * <dd>パケットの本体が保持するデータを、
 * 別の形式に変換します。</dd>
 * <dt>writeFooter() メソッド</dt>
 * <dd>パケットのフッタが保持するデータを、
 * 別の形式に変換します。</dd>
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
public abstract class AbstractPacket extends AbstractBlock
        implements Packet, Cloneable {
    //ヘッダ
    private Block head;
    //本体を表すビット列
    private LargeBitList body;
    //フッタ
    private Block foot;

    //パケット全体を表すビット列（参照のみ）
    private LargeBitList raw_packet;

    /**
     * <p>
     * 空のヘッダを持ち、存在する範囲が未定義のパケットを作成します。
     * </p>
     */
    public AbstractPacket() {
        this(null, new BlockAdapter());
    }

    /**
     * <p>
     * 空のヘッダを持ち、存在する範囲が定義されたパケットを作成します。
     * </p>
     *
     * @param pr パケットが存在する範囲
     */
    public AbstractPacket(PacketRange pr) {
        this(pr, new BlockAdapter());
    }

    /**
     * <p>
     * 指定されたヘッダを持ち、存在する範囲が未定義のパケットを作成します。
     * </p>
     *
     * @param h パケットヘッダ
     */
    public AbstractPacket(Block h) {
        this(null, h);
    }

    /**
     * <p>
     * 指定されたヘッダを持ち、存在する範囲が定義されたパケットを作成します。
     * </p>
     *
     * @param pr パケットが存在する範囲
     * @param h パケットヘッダ
     */
    public AbstractPacket(PacketRange pr, Block h) {
        super();

        setRange(pr);
        head = h;
        foot = new BlockAdapter();
    }

    @Override
    public AbstractPacket clone()
            throws CloneNotSupportedException {
        AbstractPacket obj = (AbstractPacket)super.clone();

        obj.setRange(getRange());
        obj.head = head.clone();
        obj.body = body;
        obj.foot = foot.clone();
        obj.raw_packet = raw_packet;

        return obj;
    }

    public long getNumber() {
        return getRange().getNumber();
    }

    public void setNumber(long num) {
        getRange().setNumber(num);
    }

    public long getBodyAddress() {
        return getRange().getBodyAddress();
    }

    public long getFooterAddress() {
        return getRange().getFooterAddress();
    }

    public long getHeaderLength() {
        return getRange().getHeaderLength();
    }

    public void setHeaderLength(long s) {
        getRange().setHeaderLength(s);
    }

    public long getBodyLength() {
        return getRange().getBodyLength();
    }

    public void setBodyLength(long s) {
        getRange().setBodyLength(s);
    }

    public long getFooterLength() {
        return getRange().getFooterLength();
    }

    public void setFooterLength(long s) {
        getRange().setFooterLength(s);
    }

    public boolean getRecursive() {
        return getRange().getRecursive();
    }

    public void setRecursive(boolean r) {
        getRange().setRecursive(r);
    }

    public int getLevel() {
        return getRange().getLevel();
    }

    public PacketRange appendChild(PacketRange newChild) {
        return getRange().appendChild(newChild);
    }

    public PacketRange removeChild(PacketRange oldChild) {
        return getRange().removeChild(oldChild);
    }

    public PacketRange insertBefore(PacketRange newChild, PacketRange refChild) {
        return getRange().insertBefore(newChild, refChild);
    }

    public PacketRange replaceChild(PacketRange newChild, PacketRange oldChild) {
        return getRange().replaceChild(newChild, oldChild);
    }

    public PacketRange getParentNode() {
        return getRange().getParentNode();
    }

    public void setParentNode(PacketRange p) {
        getRange().setParentNode(p);
    }

    @Override
    public PacketRange getRange() {
        return (PacketRange)super.getRange();
    }

    @Override
    public String getShortName() {
        return getClass().getName();
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
     * 部分ビット列をパケット本体として設定します。
     * </p>
     *
     * <p>
     * 長さに負の値を指定したとき、本体のビット列の長さは 0 となります。
     * </p>
     *
     * @param b パケット本体のビット列を含むビット列
     * @param from パケット本体の開始位置（ビット単位）
     * @param len  パケット本体の長さ（ビット単位）
     */
    protected void setBody(LargeBitList b, long from, long len) {
        if (len < 0) {
            //長さが負の場合、長さを 0 にする
            System.err.printf("Warning: body length is negative."
                            + "(from:0x%08x, len:0x%08x)\n",
                    from, len);

            len = 0;
        }

        body = b.subLargeList(from, len);
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
     * 別の形式からパケットのヘッダに変換します。
     * </p>
     *
     * @param c 各メンバの変換を実施するオブジェクト
     */
    protected abstract void readHeader(PacketReader<?> c);

    /**
     * <p>
     * 別の形式からパケットの本体に変換します。
     * </p>
     *
     * @param c 各メンバの変換を実施するオブジェクト
     */
    protected abstract void readBody(PacketReader<?> c);

    /**
     * <p>
     * 別の形式からパケットのフッタに変換します。
     * </p>
     *
     * @param c 各メンバの変換を実施するオブジェクト
     */
    protected abstract void readFooter(PacketReader<?> c);

    /**
     * <p>
     * パケットのヘッダを別の形式に変換します。
     * </p>
     *
     * @param c 各メンバの変換を実施するオブジェクト
     */
    protected abstract void writeHeader(PacketWriter<?> c);

    /**
     * <p>
     * パケットの本体を別の形式に変換します。
     * </p>
     *
     * @param c 各メンバの変換を実施するオブジェクト
     */
    protected abstract void writeBody(PacketWriter<?> c);

    /**
     * <p>
     * パケットのフッタを別の形式に変換します。
     * </p>
     *
     * @param c 各メンバの変換を実施するオブジェクト
     */
    protected abstract void writeFooter(PacketWriter<?> c);

    /**
     * <p>
     * パケット全体を表すビット列を取得します。
     * </p>
     *
     * <p>
     * パケット全体を表すビット列が存在しない場合もあります。
     * </p>
     *
     * @param c 各メンバの変換を実施するオブジェクト
     */
    protected void readRawPacket(PacketReader<?> c) {
        long org_pos;

        org_pos = c.position();
        c.position(getStart());
        raw_packet = c.readSubList(getLength(), raw_packet, "raw_packet");
        c.position(org_pos);
    }

    /**
     * <p>
     * パケット全体を表すビット列を書き込みます。
     * </p>
     *
     * <p>
     * パケット全体を表すビット列が存在しない場合もあります。
     * </p>
     *
     * @param c 各メンバの変換を実施するオブジェクト
     */
    protected void writeRawPacket(PacketWriter<?> c) {
        c.writeSubList(getLength(), raw_packet, "raw_packet");
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
    public void read(PacketReader<?> c) {
        c.enterPacket(getShortName());

        convHeader(c, this);

        setStart(c.position());
        readHeader(c);
        setHeaderLength(c.position() - getStart());
        readBody(c);
        setBodyLength(c.position() - getBodyAddress());
        readFooter(c);
        setFooterLength(c.position() - getFooterAddress());
        readRawPacket(c);

        if (isRecursive()) {
            //入れ子にできるなら、本体に別のパケットが含まれている
            //かもしれないので、パケット本体を解析する
            c.position(getBodyAddress());
        }
        setRecursive(isRecursive());

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
    public void write(PacketWriter<?> c) {
        c.enterPacket(getShortName());

        convHeader(c, this);

        writeHeader(c);
        writeBody(c);
        writeFooter(c);
        writeRawPacket(c);

        c.leavePacket();
    }

    private static void convHeader(PacketConverter<?> c,
                             AbstractPacket d) {
        c.enterBlock("common");

        c.mark("tag_num",
                d.getNumber());
        c.mark("addr(head,body,foot)(hex)",
                String.format("%x.%d(%x.%d, %x.%d, %x.%d)",
                        d.getStart() >>> 3,d.getStart() & 7,
                        d.getStart() >>> 3, d.getStart() & 7,
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
}
