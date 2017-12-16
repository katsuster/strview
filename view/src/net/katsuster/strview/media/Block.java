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
public interface Block extends Cloneable {
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
     * 型名を取得します。
     * </p>
     *
     * <p>
     * クラス名 Class.getCanonicalName() を返すか、
     * より適切な名前を返すことが推奨されます。
     * </p>
     *
     * @return 型名
     */
    public String getTypeName();

    /**
     * <p>
     * データの存在する範囲を取得します。
     * </p>
     *
     * @return データの範囲
     */
    public Range getRange();

    /**
     * <p>
     * データの存在する位置を設定します。
     * </p>
     *
     * @param p データの範囲
     */
    public void setRange(Range p);

    /**
     * <p>
     * 現在位置を更新せずに、ブロックを読み込みます。
     * </p>
     *
     * @param c 各メンバの変換を実施するオブジェクト
     */
    public void peek(StreamReader<?> c);

    /**
     * <p>
     * ブロックを読み込みます。
     * </p>
     *
     * @param c 各メンバの変換を実施するオブジェクト
     */
    public void read(StreamReader<?> c);

    /**
     * <p>
     * 現在位置を更新せずに、ブロックを書き込みます。
     * </p>
     *
     * @param c 各メンバの変換を実施するオブジェクト
     */
    public void poke(StreamWriter<?> c);

    /**
     * <p>
     * ブロックを書き込みます。
     * </p>
     *
     * @param c 各メンバの変換を実施するオブジェクト
     */
    public void write(StreamWriter<?> c);
}
