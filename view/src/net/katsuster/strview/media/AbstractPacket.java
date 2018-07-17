package net.katsuster.strview.media;

import java.util.List;

import net.katsuster.strview.io.*;
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
 */
public abstract class AbstractPacket<T>
        extends AbstractBlock<T>
        implements Packet<T>, PacketRange<LargeList<T>>, Cloneable {
    //ヘッダ
    private Block<T> head;
    //本体を表すビット列
    private LargeList<T> body;
    //フッタ
    private Block<T> foot;

    //パケット全体を表すビット列（参照のみ）
    private LargeList<T> raw_packet;

    /**
     * <p>
     * 空のヘッダを持ち、存在する範囲が未定義のパケットを作成します。
     * </p>
     */
    public AbstractPacket() {
        this(null, new BlockAdapter<>("head"));
    }

    /**
     * <p>
     * 空のヘッダを持ち、存在する範囲が定義されたパケットを作成します。
     * </p>
     *
     * @param pr パケットが存在する範囲
     */
    public AbstractPacket(PacketRange<LargeList<T>> pr) {
        this(pr, new BlockAdapter<>("head"));
    }

    /**
     * <p>
     * 指定されたヘッダを持ち、存在する範囲が未定義のパケットを作成します。
     * </p>
     *
     * @param h パケットヘッダ
     */
    public AbstractPacket(Block<T> h) {
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
    public AbstractPacket(PacketRange<LargeList<T>> pr, Block<T> h) {
        setRange(pr);
        head = h;
        body = new DummyList<>("body");
        foot = new BlockAdapter<>("foot");
        raw_packet = new DummyList<>("raw_packet");
    }

    @Override
    public Object clone()
            throws CloneNotSupportedException {
        AbstractPacket obj = (AbstractPacket)super.clone();

        obj.head = (Block<T>)head.clone();
        obj.body = (LargeList<T>)body.clone();
        obj.foot = (Block<T>)foot.clone();
        obj.raw_packet = (LargeList<T>)raw_packet.clone();

        return obj;
    }

    @Override
    public long getNumber() {
        return getRange().getNumber();
    }

    @Override
    public void setNumber(long num) {
        getRange().setNumber(num);
    }

    @Override
    public long getBodyAddress() {
        return getRange().getBodyAddress();
    }

    @Override
    public long getFooterAddress() {
        return getRange().getFooterAddress();
    }

    @Override
    public long getHeaderLength() {
        return getRange().getHeaderLength();
    }

    @Override
    public void setHeaderLength(long s) {
        getRange().setHeaderLength(s);
    }

    @Override
    public long getBodyLength() {
        return getRange().getBodyLength();
    }

    @Override
    public void setBodyLength(long s) {
        getRange().setBodyLength(s);
    }

    @Override
    public long getFooterLength() {
        return getRange().getFooterLength();
    }

    @Override
    public void setFooterLength(long s) {
        getRange().setFooterLength(s);
    }

    @Override
    public boolean getRecursive() {
        return getRange().getRecursive();
    }

    @Override
    public void setRecursive(boolean r) {
        getRange().setRecursive(r);
    }

    @Override
    public int getLevel() {
        return getRange().getLevel();
    }

    @Override
    public PacketRange<LargeList<T>> appendChild(PacketRange<LargeList<T>> newChild) {
        return getRange().appendChild(newChild);
    }

    @Override
    public PacketRange<LargeList<T>> removeChild(PacketRange<LargeList<T>> oldChild) {
        return getRange().removeChild(oldChild);
    }

    @Override
    public PacketRange<LargeList<T>> insertBefore(PacketRange<LargeList<T>> newChild, PacketRange<LargeList<T>> refChild) {
        return getRange().insertBefore(newChild, refChild);
    }

    @Override
    public PacketRange<LargeList<T>> replaceChild(PacketRange<LargeList<T>> newChild, PacketRange<LargeList<T>> oldChild) {
        return getRange().replaceChild(newChild, oldChild);
    }

    @Override
    public PacketRange<LargeList<T>> getParentNode() {
        return getRange().getParentNode();
    }

    @Override
    public void setParentNode(PacketRange<LargeList<T>> p) {
        getRange().setParentNode(p);
    }

    @Override
    public PacketRange<LargeList<T>> getPreviousSibling() {
        return getRange().getPreviousSibling();
    }

    @Override
    public PacketRange<LargeList<T>> getNextSibling() {
        return getRange().getNextSibling();
    }

    @Override
    public boolean hasChildNodes() {
        return getRange().hasChildNodes();
    }

    @Override
    public List<PacketRange<LargeList<T>>> getChildNodes() {
        return getRange().getChildNodes();
    }

    @Override
    public PacketRange<LargeList<T>> getFirstChild() {
        return getRange().getFirstChild();
    }

    @Override
    public PacketRange<LargeList<T>> getLastChild() {
        return getRange().getLastChild();
    }

    @Override
    public int getChildCounts() {
        return getRange().getChildCounts();
    }

    @Override
    public PacketRange<LargeList<T>> getChild(int index) {
        return getRange().getChild(index);
    }

    @Override
    public PacketRange<LargeList<T>> getRange() {
        return (PacketRange<LargeList<T>>)super.getRange();
    }

    @Override
    public Block<T> getHeader() {
        return head;
    }

    /**
     * <p>
     * パケットのヘッダを設定します。
     * </p>
     *
     * @param h パケットのヘッダ
     */
    protected void setHeader(Block<T> h) {
        head = h;
    }

    @Override
    public LargeList<T> getBody() {
        return body;
    }

    /**
     * <p>
     * パケット本体のビット列を設定します。
     * </p>
     *
     * @param b パケット本体のビット列
     */
    protected void setBody(LargeList<T> b) {
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
    protected void setBody(LargeList<T> b, long from, long len) {
        if (len < 0) {
            //長さが負の場合、長さを 0 にする
            System.err.printf("Warning: body length is negative."
                            + "(from:0x%08x, len:0x%08x)\n",
                    from, len);

            len = 0;
        }

        body = new SubLargeList<>(b, from, len);
    }

    @Override
    public Block<T> getFooter() {
        return foot;
    }

    /**
     * <p>
     * パケットのフッタを設定します。
     * </p>
     *
     * @param f パケットのフッタ
     */
    protected void setFooter(Block<T> f) {
        foot = f;
    }

    @Override
    public LargeList<T> getRawPacket() {
        return raw_packet;
    }

    /**
     * <p>
     * パケット全体のビット列を設定します。
     * </p>
     *
     * @param b パケット全体のビット列
     */
    protected void setRawPacket(LargeList<T> b) {
        raw_packet = b;
    }

    /**
     * <p>
     * パケットのヘッダを読み出します。
     * </p>
     *
     * @param c リストの読み出しオブジェクト
     */
    protected abstract void readHeader(StreamReader<T> c);

    /**
     * <p>
     * パケットの本体を読み出します。
     * </p>
     *
     * @param c リストの読み出しオブジェクト
     */
    protected abstract void readBody(StreamReader<T> c);

    /**
     * <p>
     * パケットのフッタを読み出します。
     * </p>
     *
     * @param c リストの読み出しオブジェクト
     */
    protected abstract void readFooter(StreamReader<T> c);

    /**
     * <p>
     * パケットのヘッダを書き込みます。
     * </p>
     *
     * @param c リストの書き込みオブジェクト
     */
    protected abstract void writeHeader(StreamWriter<T> c);

    /**
     * <p>
     * パケットの本体を書き込みます。
     * </p>
     *
     * @param c リストの書き込みオブジェクト
     */
    protected abstract void writeBody(StreamWriter<T> c);

    /**
     * <p>
     * パケットのフッタを書き込みます。
     * </p>
     *
     * @param c リストの書き込みオブジェクト
     */
    protected abstract void writeFooter(StreamWriter<T> c);

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
    protected void readRawPacket(StreamReader<T> c) {
        long org_pos;

        org_pos = c.position();
        c.position(getStart());
        raw_packet = c.readList(getLength(), raw_packet);
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
    protected void writeRawPacket(StreamWriter<T> c) {
        c.writeList(getLength(), raw_packet);
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
    public void read(StreamReader<T> c) {
        c.enterPacket(this);

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
    public void write(StreamWriter<T> c) {
        c.enterPacket(this);

        convHeader(c, this);

        writeHeader(c);
        writeBody(c);
        writeFooter(c);
        writeRawPacket(c);

        c.leavePacket();
    }

    private static void convHeader(StreamConverter<?> c,
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
