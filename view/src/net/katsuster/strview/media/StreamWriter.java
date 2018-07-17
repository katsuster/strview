package net.katsuster.strview.media;

import net.katsuster.strview.util.*;

/**
 * <p>
 * リストのインデックスを持ち、リストの要素を書き込むインタフェースです。
 * </p>
 *
 * <p>
 * ほとんどのメソッドが空の実装となる場合は、
 * StreamWriterAdaptor を継承し必要な関数のみをオーバライドすると便利です。
 * </p>
 *
 * <p>
 * 想定する使用例を下記に示します。
 * </p>
 *
 * <pre>
 * Packet p;
 * SomeObject r;
 * StreamWriter&lt;SomeObject&gt; c = new SomeWriter(new SomeObject());
 *
 * //add members of packet
 * p.convert(c);
 *
 * r = c.getResult();
 * </pre>
 *
 * @see StreamWriterAdapter
 */
public interface StreamWriter<T> extends StreamConverter<T> {
    /**
     * <p>
     * 現在位置を更新せずに、要素を書き込みます。
     * </p>
     *
     * @param val 要素
     */
    public void poke(T val);

    /**
     * <p>
     * 要素を書き込み、現在位置を進めます。
     * </p>
     *
     * @param val 要素
     */
    public void write(T val);

    /**
     * <p>
     * 現在位置を更新せずに、要素を書き込みます。
     * 意味、説明などを渡すことができます。
     * </p>
     *
     * @param val  要素
     * @param desc 要素の意味、説明など
     */
    public void poke(T val, String desc);

    /**
     * <p>
     * 要素を書き込み、現在位置を進めます。
     * 意味、説明などを渡すことができます。
     * </p>
     *
     * @param val  要素
     * @param desc 要素の意味、説明など
     */
    public void write(T val, String desc);

    /**
     * <p>
     * 現在位置を更新せずに、リストを書き込みます。
     * </p>
     *
     * @param n   読み出す要素数
     * @param val リスト
     */
    public void pokeList(long n, LargeList<T> val);

    /**
     * <p>
     * リストを書き込み、現在位置を進めます。
     * </p>
     *
     * @param n   読み出す要素数
     * @param val リスト
     */
    public void writeList(long n, LargeList<T> val);

    /**
     * <p>
     * 現在位置を更新せずに、リストを書き込みます。
     * 意味、説明などを渡すことができます。
     * </p>
     *
     * @param n    読み出す要素数
     * @param val  リスト
     * @param desc リストの意味、説明など
     */
    public void pokeList(long n, LargeList<T> val, String desc);

    /**
     * <p>
     * リストを書き込み、現在位置を進めます。
     * 意味、説明などを渡すことができます。
     * </p>
     *
     * @param n    読み出す要素数
     * @param val  リスト
     * @param desc リストの意味、説明など
     */
    public void writeList(long n, LargeList<T> val, String desc);
}
