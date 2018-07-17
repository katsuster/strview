package net.katsuster.strview.media;

import net.katsuster.strview.util.*;

/**
 * <p>
 * リストのインデックスです。
 * </p>
 *
 * <p>
 * Java のイテレータと似ていますが、
 * 現在位置が「要素の間」ではなく「要素自体」を指している点が異なります。
 * </p>
 *
 * <p>
 * ほとんどのメソッドが空の実装となる場合は、
 * PacketConverterAdaptor を継承し必要な関数のみをオーバライドすると便利です。
 * </p>
 *
 * <p>
 * 想定する使用例を下記に示します。
 * </p>
 *
 * <pre>
 * Packet p;
 * SomeObject r;
 * StreamConverter&lt;SomeObject&gt; c = new SomeConverter(new SomeObject());
 *
 * //add members of packet
 * p.convert(c);
 *
 * r = c.getResult();
 * </pre>
 *
 * @see AbstractStreamConverter
 */
public interface StreamConverter<T> {
    /**
     * <p>
     * リストを取得します。
     * </p>
     *
     * @return リスト
     */
    public LargeList<T> getList();

    /**
     * <p>
     * リストを設定します。
     * </p>
     *
     * <p>
     * リストを設定する前に保持していたインデックスは無効になります。
     * </p>
     *
     * @param l リスト
     */
    public void setList(LargeList<T> l);

    /**
     * <p>
     * パケットの変換を開始します。
     * </p>
     *
     * <p>
     * パケットが入れ子状になっている場合、
     * enter を呼んだ順と逆順に leave を呼び出します。
     * </p>
     *
     * <pre>
     * 例えば変換対象が、
     *
     * packet A
     *  `--- packet B
     *       packet C
     *        `--- packet D
     *
     * という構造を持つ場合、
     *
     * enter(packet A)
     *  enter(packet B)
     *  leave(packet B)
     *  enter(packet C)
     *   enter(packet D)
     *   leave(packet D)
     *  leave(packet C)
     * leave(packet A)
     *
     * の順で呼び出します。
     * </pre>
     *
     * @param name パケットの名前
     */
    public void enterPacket(String name);

    /**
     * <p>
     * パケットの変換を開始します。
     * </p>
     *
     * <p>
     * パケットが入れ子状になっている場合、
     * enter を呼んだ順と逆順に leave を呼び出します。
     * </p>
     *
     * <pre>
     * 例えば変換対象が、
     *
     * packet A
     *  `--- packet B
     *       packet C
     *        `--- packet D
     *
     * という構造を持つ場合、
     *
     * enter(packet A)
     *  enter(packet B)
     *  leave(packet B)
     *  enter(packet C)
     *   enter(packet D)
     *   leave(packet D)
     *  leave(packet C)
     * leave(packet A)
     *
     * の順で呼び出します。
     * </pre>
     *
     * @param p パケット
     */
    public void enterPacket(Packet p);

    /**
     * <p>
     * パケットの変換を終了します。
     * </p>
     *
     * <p>
     * パケットが入れ子状になっている場合、
     * enter を呼んだ順と逆順に leave を呼びます。
     * </p>
     */
    public void leavePacket();

    /**
     * <p>
     * 意味のある一続きのデータ（ブロック）の変換を開始します。
     * パケットのヘッダ、フッタなどに使用します。
     * </p>
     *
     * <p>
     * ブロックが入れ子状になっている場合、
     * enter を呼んだ順と逆順に leave を呼び出します。
     * </p>
     *
     * <pre>
     * 例えば変換対象が、
     *
     * block A
     *  `--- block B
     *       block C
     *        `--- block D
     *
     * という構造を持つ場合、
     *
     * enter(block A)
     *  enter(block B)
     *  leave(block B)
     *  enter(block C)
     *   enter(block D)
     *   leave(block D)
     *  leave(block C)
     * leave(block A)
     *
     * の順で呼び出します。
     * </pre>
     *
     * @param name ブロックの名前
     */
    public void enterBlock(String name);

    /**
     * <p>
     * 意味のある一続きのデータ（ブロック）の変換を開始します。
     * パケットのヘッダ、フッタなどに使用します。
     * </p>
     *
     * <p>
     * ブロックが入れ子状になっている場合、
     * enter を呼んだ順と逆順に leave を呼び出します。
     * </p>
     *
     * <pre>
     * 例えば変換対象が、
     *
     * block A
     *  `--- block B
     *       block C
     *        `--- block D
     *
     * という構造を持つ場合、
     *
     * enter(block A)
     *  enter(block B)
     *  leave(block B)
     *  enter(block C)
     *   enter(block D)
     *   leave(block D)
     *  leave(block C)
     * leave(block A)
     *
     * の順で呼び出します。
     * </pre>
     *
     * @param b ブロック
     */
    public void enterBlock(Block b);

    /**
     * <p>
     * 意味のある一続きのデータ（ブロック）の変換を終了します。
     * </p>
     *
     * <p>
     * ブロックが入れ子状になっている場合、
     * enter を呼んだ順と逆順に leave を呼びます。
     * </p>
     */
    public void leaveBlock();

    /**
     * <p>
     * 変換対象とならない、データ以外の情報を追加します。
     * </p>
     *
     * @param name マークの名前
     * @param s    マークの内容
     */
    public void mark(String name, String s);

    /**
     * <p>
     * 変換対象とならない、データ以外の情報を追加します。
     * </p>
     *
     * @param name マークの名前
     * @param s    マークの内容
     * @param desc マークの意味、説明など
     */
    public void mark(String name, String s, String desc);

    /**
     * <p>
     * 変換対象とならない、データ以外の情報を追加します。
     * </p>
     *
     * @param name マークの名前
     * @param n    マークの内容
     */
    public void mark(String name, Number n);

    /**
     * <p>
     * 変換対象とならない、データ以外の情報を追加します。
     * </p>
     *
     * @param name マークの名前
     * @param n    マークの内容
     * @param desc マークの意味、説明など
     */
    public void mark(String name, Number n, String desc);

    /**
     * <p>
     * 現在の位置を取得します。
     * </p>
     *
     * @return 現在の位置
     */
    public long position();

    /**
     * <p>
     * 位置を設定します。
     * </p>
     *
     * @param p 新たな位置
     */
    public void position(long p);

    /**
     * <p>
     * 現在位置から指定された数の要素を読み出しあるいは、
     * 書き込みできるかどうかを取得します。
     * </p>
     *
     * @param n 読み出すまたは書き込む予定の要素数
     * @return 読み出しまたは書き込みが可能ならば true、不可能ならば false
     */
    public boolean hasNext(long n);

    /**
     * <p>
     * 変換中の位置が指定された要素数の境界かどうか取得します。
     * </p>
     *
     * <p>
     * 現在の位置を p とし、指定した要素数を n とすると、
     * p が n の整数倍であれば境界と判断されます。
     * </p>
     *
     * <p>
     * 例えば要素数に 4 を指定した場合、
     * 現在位置が 1, 2, 3, 5, 6, 7 ならば、false であり、
     * 現在位置が 0, 4, 8 ならば、true となります。
     * </p>
     *
     * @param n 境界となる要素数
     * @return 境界なら true、そうでなければ false
     */
    public boolean isAlign(int n);

    /**
     * <p>
     * 変換中の位置を指定された要素数の境界に設定します。
     * </p>
     *
     * <p>
     * 現在の位置を p とし、指定した要素数を n とすると、
     * p 以上の最も近い n の整数倍に位置が設定されます。
     * </p>
     *
     * <p>
     * 例えば要素数に 4 を指定した場合、
     * 現在位置が 0, 1, 2, 3, 4 ならば、新たな位置は 4
     * 5, 6, 7, 8 ならば、新たな位置は 8 となります。
     * </p>
     *
     * @param n 境界となる要素数
     */
    public void align(int n);
}
