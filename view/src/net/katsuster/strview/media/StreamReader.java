package net.katsuster.strview.media;

import net.katsuster.strview.util.*;

/**
 * <p>
 * リストのインデックスを持ち、リストの要素を読み取るインタフェースです。
 * </p>
 *
 * <p>
 * ほとんどのメソッドが空の実装となる場合は、
 * StreamReaderAdaptor を継承し必要な関数のみをオーバライドすると便利です。
 * </p>
 *
 * <p>
 * 想定する使用例を下記に示します。
 * </p>
 *
 * <pre>
 * Packet p;
 * SomeObject r;
 * StreamReader&lt;SomeObject&gt; c = new SomeReader(new SomeObject());
 *
 * //add members of packet
 * p.read(c);
 *
 * r = c.getResult();
 * </pre>
 *
 * @see StreamReaderAdapter
 */
public interface StreamReader<T> extends StreamConverter<T> {
    /**
     * <p>
     * 現在位置を更新せずに、要素を読み出します。
     * </p>
     *
     * <p>
     * 渡した要素を書き換えて返す実装と、
     * 渡した要素から必要な情報をコピーした新たな要素を返す実装、
     * どちらの実装も可能です。
     * </p>
     *
     * @param val 要素
     * @return 現在位置にある要素
     */
    public T peek(T val);

    /**
     * <p>
     * 要素を読み出し、現在位置を進めます。
     * </p>
     *
     * <p>
     * 渡した要素を書き換えて返す実装と、
     * 渡した要素から必要な情報をコピーした新たな要素を返す実装、
     * どちらの実装も可能です。
     * </p>
     *
     * @param val 要素
     * @return 現在位置にある要素
     */
    public T read(T val);

    /**
     * <p>
     * 現在位置を更新せずに、要素を読み出します。
     * 意味、説明などを渡すことができます。
     * </p>
     *
     * <p>
     * 渡した要素を書き換えて返す実装と、
     * 渡した要素から必要な情報をコピーした新たな要素を返す実装、
     * どちらの実装も可能です。
     * </p>
     *
     * @param val 要素
     * @param desc 要素の意味、説明など
     * @return 現在位置にある要素
     */
    public T peek(T val, String desc);

    /**
     * <p>
     * 要素を読み出し、現在位置を進めます。
     * 意味、説明などを渡すことができます。
     * </p>
     *
     * <p>
     * 渡した要素を書き換えて返す実装と、
     * 渡した要素から必要な情報をコピーした新たな要素を返す実装、
     * どちらの実装も可能です。
     * </p>
     *
     * @param val 要素
     * @param desc 要素の意味、説明など
     * @return 現在位置にある要素
     */
    public T read(T val, String desc);

    /**
     * <p>
     * 現在位置を更新せずに、複数の要素を読み出します。
     * </p>
     *
     * <p>
     * 渡したリストを書き換えて返す実装と、
     * 渡したリストから必要な情報をコピーした新たなリストを返す実装、
     * どちらの実装も可能です。
     * </p>
     *
     * @param n   読み出す要素数
     * @param val リスト
     * @return 現在位置から読み出したリスト
     */
    public LargeList<T> peekList(long n, LargeList<T> val);

    /**
     * <p>
     * 複数の要素を読み出し、現在位置を進めます。
     * </p>
     *
     * <p>
     * 渡したリストを書き換えて返す実装と、
     * 渡したリストから必要な情報をコピーした新たなリストを返す実装、
     * どちらの実装も可能です。
     * </p>
     *
     * @param n   読み出す要素数
     * @param val リスト
     * @return 現在位置から読み出したリスト
     */
    public LargeList<T> readList(long n, LargeList<T> val);

    /**
     * <p>
     * 現在位置を更新せずに、複数の要素を読み出します。
     * 意味、説明などを渡すことができます。
     * </p>
     *
     * <p>
     * 渡したリストを書き換えて返す実装と、
     * 渡したリストから必要な情報をコピーした新たなリストを返す実装、
     * どちらの実装も可能です。
     * </p>
     *
     * @param n    読み出す要素数
     * @param val  リスト
     * @param desc 要素の意味、説明など
     * @return 現在位置から読み出したリスト
     */
    public LargeList<T> peekList(long n, LargeList<T> val, String desc);

    /**
     * <p>
     * 複数の要素を読み出し、現在位置を進めます。
     * 意味、説明などを渡すことができます。
     * </p>
     *
     * <p>
     * 渡したリストを書き換えて返す実装と、
     * 渡したリストから必要な情報をコピーした新たなリストを返す実装、
     * どちらの実装も可能です。
     * </p>
     *
     * @param n    読み出す要素数
     * @param val  リスト
     * @param desc 要素の意味、説明など
     * @return 現在位置から読み出したリスト
     */
    public LargeList<T> readList(long n, LargeList<T> val, String desc);
}
