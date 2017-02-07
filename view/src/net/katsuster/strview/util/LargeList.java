package net.katsuster.strview.util;

import java.util.List;

/**
 * <p>
 * int 型で扱える長さを超えるリストのインタフェースです。
 * </p>
 *
 * @author katsuhiro
 */
public interface LargeList<T> extends List<T> {
    //size() および length() メソッドで長さが分からないときに返される値です
    public static long LENGTH_UNKNOWN = -1;

    /**
     * <p>
     * リストの長さを調べます。処理には長い時間が掛かることがあります。
     * </p>
     */
    public void count();

    /**
     * <p>
     * リストの長さを返します。
     * </p>
     *
     * @return リストの長さ
     */
    public long length();

    /**
     * <p>
     * リストの長さを設定します。
     * </p>
     *
     * @param l リストの長さ
     */
    public void length(long l);

    /**
     * <p>
     * リストの位置のヒントを返します。
     * </p>
     *
     * <p>
     * リストが別のリストの一部に含まれる場合などに使用します。
     * </p>
     *
     * @return リストの位置のヒント
     */
    public long getOffsetHint();

    /**
     * <p>
     * リストの位置のヒントを設定します。
     * </p>
     *
     * <p>
     * リストが別のリストの一部に含まれる場合などに使用します。
     * </p>
     *
     * @param h リストの位置のヒント
     */
    public void setOffsetHint(long h);

    /**
     * <p>
     * 指定された位置の要素を取得します。
     * </p>
     *
     * @param index 要素の位置
     * @return 指定された位置の要素
     * @throws IndexOutOfBoundsException 読み出し位置が負、リストの範囲外の場合
     */
    public T get(long index);

    /**
     * <p>
     * 指定された位置から、length の長さだけ要素を取得し、
     * 配列に格納します。
     * </p>
     *
     * @param index  読み出し開始位置
     * @param dest   結果を格納する配列
     * @param offset 結果の格納を開始する位置
     * @param length 読みだす要素数
     * @return 実際に読みだした要素数
     * @throws IndexOutOfBoundsException 読み出し位置が負、リストの範囲外の場合
     */
    //public int get(long index, T[] dest, int offset, int length);

    /**
     * <p>
     * 指定された位置から、length の長さだけ要素を取得し、
     * リストに格納します。
     * </p>
     *
     * @param index  読み出し開始位置
     * @param dest   結果を格納するリスト
     * @param offset 結果の格納を開始する位置
     * @param length 読みだす要素数
     * @return 実際に読みだした要素数
     * @throws IndexOutOfBoundsException 読み出し位置が負、リストの範囲外の場合
     */
    public int get(long index, LargeList<T> dest, int offset, int length);

    /**
     * <p>
     * 指定された位置に要素を設定します。
     * </p>
     *
     * @param index 要素の位置
     * @param data  指定された位置に設定する要素
     * @throws IndexOutOfBoundsException 書き込み位置が負、リストの範囲外の場合
     */
    public void set(long index, T data);

    /**
     * <p>
     * 指定された位置から、length の長さだけ要素を設定します。
     * </p>
     *
     * @param index  書き込み開始位置
     * @param src    書きこむ配列
     * @param offset 書きこむ配列の先頭要素の位置
     * @param length 書きこむ要素数
     * @return 実際に書き込んだ要素数
     * @throws IndexOutOfBoundsException 書き込み位置が負、リストの範囲外の場合
     */
    //public int set(long index, T[] src, int offset, int length);

    /**
     * <p>
     * 指定された位置から、length の長さだけ要素を設定します。
     * </p>
     *
     * @param index  書き込み開始位置
     * @param src    書きこむリスト
     * @param offset 書きこむリストの先頭要素の位置
     * @param length 書きこむ要素数
     * @return 実際に書き込んだ要素数
     * @throws IndexOutOfBoundsException 書き込み位置が負、リストの範囲外の場合
     */
    public int set(long index, LargeList<T> src, int offset, int length);

    /**
     * <p>
     * リストの from（この要素を含む）から to（この要素を含まない）までの、
     * 部分列を返します。返された部分列への変更は、元のリストと連動します。
     * </p>
     *
     * @param from 部分列の開始位置
     * @param to   部分列の終了位置
     * @return リストの部分列
     */
    public LargeList<T> subLargeList(long from, long to);

    /**
     * <p>
     * リストが存在する範囲を取得します。
     * </p>
     *
     * <p>
     * 範囲の単位はリストによって意味が異なります。
     * またその範囲から完全にリストを再現できるとは限りません。
     * </p>
     *
     * <p>
     * 例えば、このリストが別のビット列 A から生成された場合、
     * リスト A の何ビット目から生成されたかを示します。
     * </p>
     *
     * @return リストが存在する範囲
     */
    public Range getRange();

    /**
     * <p>
     * リストが存在する範囲を設定します。
     * </p>
     *
     * <p>
     * 範囲の単位はリストによって意味が異なります。
     * またその範囲から完全にリストを再現できるとは限りません。
     * </p>
     *
     * @param range リストが存在する範囲
     */
    public void setRange(Range range);
}
