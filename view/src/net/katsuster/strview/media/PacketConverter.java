package net.katsuster.strview.media;

import net.katsuster.strview.util.*;

/**
 * <p>
 * パケットを別の形式に変換、または別の形式からパケットに変換するインタフェースです。
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
 * PacketConverter&lt;SomeObject&gt; c = new SomeConverter(new SomeObject());
 *
 * //add members of packet
 * p.convert(c);
 *
 * r = c.getResult();
 * </pre>
 *
 * @see AbstractPacketConverter
 * @author katsuhiro
 */
public interface PacketConverter<T> {
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
     * 変換中の位置を取得します。
     * </p>
     *
     * @return 現在の位置（ビット単位）
     */
    public long position();

    /**
     * <p>
     * 変換中の位置を設定します（ビット単位）。
     * </p>
     *
     * @param p 新たな位置（ビット単位）
     */
    public void position(long p);

    /**
     * <p>
     * 変換中の位置が 1バイト境界かどうか取得します。
     * </p>
     *
     * @return 1バイト境界なら true、そうでなければ false
     */
    public boolean isAlignByte();

    /**
     * <p>
     * 変換中の位置を 1バイト境界に設定します。
     * </p>
     */
    public void alignByte();

    /**
     * <p>
     * 変換中の位置が 2バイト境界かどうか取得します。
     * </p>
     *
     * @return 2バイト境界なら true、そうでなければ false
     */
    public boolean isAlignShort();

    /**
     * <p>
     * 変換中の位置を 2バイト境界に設定します。
     * </p>
     */
    public void alignShort();

    /**
     * <p>
     * 変換中の位置が 4バイト境界かどうか取得します。
     * </p>
     *
     * @return 4バイト境界なら true、そうでなければ false
     */
    public boolean isAlignInt();

    /**
     * <p>
     * 変換中の位置を 4バイト境界に設定します。
     * </p>
     */
    public void alignInt();

    /**
     * <p>
     * 変換中の位置が 8バイト境界かどうか取得します。
     * </p>
     *
     * @return 8バイト境界なら true、そうでなければ false
     */
    public boolean isAlignLong();

    /**
     * <p>
     * 変換中の位置を 8バイト境界に設定します。
     * </p>
     */
    public void alignLong();

    /**
     * <p>
     * 変換結果を取得します。
     * </p>
     *
     * <p>
     * 変換結果が取得できない場合は null を返します。
     * 例外をスローしても構いません。
     * スローする例外の種類は、
     * メソッドの実装クラスにて決定してください。
     * </p>
     *
     * @return 変換結果、変換結果が取得できない場合は null
     */
    public T getResult();
}
