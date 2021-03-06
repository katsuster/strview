package net.katsuster.strview.media;

import net.katsuster.strview.util.*;

/**
 * <p>
 * 意味のあるデータのまとまりです。
 * バッファからの読み込み、書き込み、
 * データの整合性の検証、他の形式への変換を行います。
 * </p>
 *
 * <p>
 * 実装する際は BlockAdapter クラスを継承すると便利です。
 * </p>
 *
 * @see BlockAdapter
 */
public interface Block<T>
        extends Cloneable, Named {
    /**
     * オブジェクトのコピーを作成し、返します。
     *
     * @return このブロックのコピー
     * @throws CloneNotSupportedException clone をサポートしていない場合にスローされます。
     */
    public Object clone()
            throws CloneNotSupportedException;

    /**
     * <p>
     * データの存在する範囲を取得します。
     * </p>
     *
     * @return データの範囲
     */
    public Range<LargeList<T>> getRange();

    /**
     * <p>
     * データの存在する位置を設定します。
     * </p>
     *
     * @param p データの範囲
     */
    public void setRange(Range<LargeList<T>> p);

    /**
     * <p>
     * 現在位置を更新せずに、ブロックを読み込みます。
     * </p>
     *
     * @param c 各メンバの変換を実施するオブジェクト
     */
    public void peek(StreamReader<T> c);

    /**
     * <p>
     * ブロックを読み込みます。
     * </p>
     *
     * @param c 各メンバの変換を実施するオブジェクト
     */
    public void read(StreamReader<T> c);

    /**
     * <p>
     * 現在位置を更新せずに、ブロックを書き込みます。
     * </p>
     *
     * @param c 各メンバの変換を実施するオブジェクト
     */
    public void poke(StreamWriter<T> c);

    /**
     * <p>
     * ブロックを書き込みます。
     * </p>
     *
     * @param c 各メンバの変換を実施するオブジェクト
     */
    public void write(StreamWriter<T> c);
}
