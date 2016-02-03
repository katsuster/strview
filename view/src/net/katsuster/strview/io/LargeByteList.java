package net.katsuster.strview.io;

import java.util.List;

/**
 * <p>
 * int 型で扱える長さを超えるバイト列のインタフェースです。
 * </p>
 *
 * @author katsuhiro
 */
public interface LargeByteList extends List<Byte> {
    /**
     * <p>
     * バイト列の長さを返します。
     * </p>
     *
     * @return バイト列の長さ
     */
    public long length();

    /**
     * <p>
     * バイト列の from（この要素を含む）から to（この要素を含まない）までの、
     * 部分列を返します。返された部分列への変更は、元のバイト列と連動します。
     * </p>
     *
     * @param from 部分列の開始位置
     * @param to   部分列の終了位置
     * @return バイト列の部分列
     */
    //public LargeArray subArray(long from, long to);

    /**
     * <p>
     * 指定された位置の要素を取得します。
     * </p>
     *
     * @param index 要素の位置
     * @return 指定された位置の値
     * @throws IndexOutOfBoundsException 読み出し位置が負、バイト列の範囲外の場合
     */
    public int get(long index);

    /**
     * <p>
     * 指定された位置から、length の長さだけ要素を取得します。
     * </p>
     *
     * @param index  バッファの読み出し開始位置
     * @param dest   結果を格納するバイト列
     * @param offset 結果の格納を開始する位置（バイト単位）
     * @param length 読みだすバイト数
     * @return 実際に読みだしたバイト数
     * @throws IndexOutOfBoundsException 読み出し位置が負、バイト列の範囲外の場合
     */
    public int get(long index, byte[] dest, int offset, int length);

    /**
     * <p>
     * 指定された位置に要素を設定します。
     * </p>
     *
     * @param index 要素の位置
     * @param data  指定された位置に設定する値
     * @throws IndexOutOfBoundsException 書き込み位置が負、バイト列の範囲外の場合
     */
    public void set(long index, byte data);

    /**
     * <p>
     * 指定された位置から、length の長さだけ要素を設定します。
     * </p>
     *
     * @param index  バッファの書き込み開始位置
     * @param src    バッファに書きこむバイト列
     * @param offset データの書きこみを開始する位置（バイト単位）
     * @param length 書きこむバイト数
     * @return 実際に書き込んだバイト数
     * @throws IndexOutOfBoundsException 書き込み位置が負、バイト列の範囲外の場合
     */
    public int set(long index, byte[] src, int offset, int length);
}
