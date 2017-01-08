package net.katsuster.strview.media;

import net.katsuster.strview.util.*;
import net.katsuster.strview.io.*;

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
 * PacketConverter&lt;SomeObject&gt; c = new SomeConverter();
 * SomeObject r;
 *
 * //initialize to start converting
 * c.doInit();
 * //add members of packet
 * p.convert(c);
 * //finish the converting
 * c.doFinish();
 *
 * r = c.getResult();
 * </pre>
 *
 * @see PacketConverterAdapter
 * @author katsuhiro
 */
public interface PacketConverter<T> {
    /**
     * <p>
     * 変換を開始します。
     * </p>
     *
     * <p>
     * 前回の変換の結果は削除され、参照できなくなります。
     * </p>
     */
    public void doInit();

    /**
     * <p>
     * 変換を終了します。
     * </p>
     *
     * <p>
     * 変換の結果が getResult() にて取得できるようになります。
     * </p>
     */
    public void doFinal();

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
     * 変換対象となる符号付き数値を追加します。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の符号付き数値オブジェクト
     * @param name 変換対象の名前
     * @return 変換対象の符号付き数値オブジェクト
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public SInt convSInt(int nbit, SInt val, String name);

    /**
     * <p>
     * 変換対象となる符号付き数値を追加します。
     * データに加えて、データの意味、説明などを渡すことができます。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の符号付き数値オブジェクト
     * @param name 変換対象の名前
     * @param desc 変換対象の符号付き数値の意味、説明など
     * @return 変換対象の符号付き数値オブジェクト
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public SInt convSInt(int nbit, SInt val, String name, String desc);

    /**
     * <p>
     * 変換対象となる符号無し数値を追加します。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の符号無し数値オブジェクト
     * @param name 変換対象の名前
     * @return 変換対象の符号無し数値オブジェクト
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public UInt convUInt(int nbit, UInt val, String name);

    /**
     * <p>
     * 変換対象となる符号無し数値を追加します。
     * データに加えて、データの意味、説明などを渡すことができます。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の符号無し数値オブジェクト
     * @param name 変換対象の名前
     * @param desc 変換対象の符号無し数値の意味、説明など
     * @return 変換対象の符号無し数値オブジェクト
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public UInt convUInt(int nbit, UInt val, String name, String desc);

    /**
     * <p>
     * 変換対象となるビットリストを追加します。
     * データに加えて、データの意味、説明などを渡すことができます。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象のビットリスト
     * @param name 変換対象の名前
     * @return 変換対象のビットリスト
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public LargeBitList convBitList(long nbit, LargeBitList val, String name);

    /**
     * <p>
     * 変換対象となるビットリストを追加します。
     * データに加えて、データの意味、説明などを渡すことができます。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象のビットリスト
     * @param name 変換対象の名前
     * @param desc 変換対象のビットリストの意味、説明など
     * @return 変換対象のビットリスト
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public LargeBitList convBitList(long nbit, LargeBitList val, String name, String desc);

    /**
     * <p>
     * 変換結果を取得します。
     * </p>
     *
     * <p>
     * doFinal() により変換を終了するまでは、変換結果は不定です。
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
