package net.katsuster.strview.media;

import net.katsuster.strview.util.*;

/**
 * <p>
 * 別の形式からパケットに変換するインタフェースです。
 * </p>
 *
 * <p>
 * ほとんどのメソッドが空の実装となる場合は、
 * PacketReaderAdaptor を継承し必要な関数のみをオーバライドすると便利です。
 * </p>
 *
 * <p>
 * 想定する使用例を下記に示します。
 * </p>
 *
 * <pre>
 * Packet p;
 * SomeObject r;
 * PacketReader&lt;SomeObject&gt; c = new SomeReader(new SomeObject());
 *
 * //add members of packet
 * p.read(c);
 *
 * r = c.getResult();
 * </pre>
 *
 * @see PacketReaderAdapter
 * @author katsuhiro
 */
public interface PacketReader<T> extends PacketConverter<T> {
    /**
     * <p>
     * 符号付き数値を読み出します。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の符号付き数値オブジェクト
     * @return 変換対象の符号付き数値オブジェクト
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public SInt readSInt(int nbit, SInt val);

    /**
     * <p>
     * 符号付き数値を読み出します。
     * データに加えて、データの意味、説明などを渡すことができます。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の符号付き数値オブジェクト
     * @param desc 変換対象の符号付き数値の意味、説明など
     * @return 変換対象の符号付き数値オブジェクト
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public SInt readSInt(int nbit, SInt val, String desc);

    /**
     * <p>
     * 符号無し数値を読み出します。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の符号無し数値オブジェクト
     * @return 変換対象の符号無し数値オブジェクト
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public UInt readUInt(int nbit, UInt val);

    /**
     * <p>
     * 符号無し数値を読み出します。
     * データに加えて、データの意味、説明などを渡すことができます。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の符号無し数値オブジェクト
     * @param desc 変換対象の符号無し数値の意味、説明など
     * @return 変換対象の符号無し数値オブジェクト
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public UInt readUInt(int nbit, UInt val, String desc);

    /**
     * <p>
     * ビットリストを読み出します。
     * データに加えて、データの意味、説明などを渡すことができます。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象のビットリスト
     * @return 変換対象のビットリスト
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public LargeBitList readBitList(long nbit, LargeBitList val);

    /**
     * <p>
     * ビットリストを読み出します。
     * データに加えて、データの意味、説明などを渡すことができます。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象のビットリスト
     * @param desc 変換対象のビットリストの意味、説明など
     * @return 変換対象のビットリスト
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public LargeBitList readBitList(long nbit, LargeBitList val, String desc);
}
