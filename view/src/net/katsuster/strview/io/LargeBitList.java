package net.katsuster.strview.io;

import java.util.List;

/**
 * <p>
 * int 型で扱える長さを超えるビット列のインタフェースです。
 * </p>
 *
 * @author katsuhiro
 */
public interface LargeBitList extends LargeList<Boolean> {
    /**
     * <p>
     * ビット列の長さを返します。
     * </p>
     *
     * @return ビット列の長さ（ビット単位）
     */
    public long length();

    /**
     * <p>
     * 指定された位置のビット値を取得します。
     * ビット値が 1 ならば true、0 ならば false が返されます。
     * </p>
     *
     * @param index 要素の位置（ビット単位）
     * @return 指定された位置のビット値が 1 なら true、0 なら false
     * @throws IndexOutOfBoundsException 読み出し位置が負、ビット列の範囲外の場合
     */
    public Boolean get(long index);

    /**
     * <p>
     * 指定された位置から、length の長さだけビット値を取得し、
     * boolean 型配列に格納します。
     * ビット値が 1 ならば true、0 ならば false が返されます。
     * </p>
     *
     * @param index  読み出し開始位置（ビット単位）
     * @param dest   結果を格納するboolean 型配列
     * @param offset 結果の格納を開始する位置（ビット単位）
     * @param length 読みだすビット数
     * @return 実際に読みだしたビット数
     * @throws IndexOutOfBoundsException 読み出し位置が負、ビット列の範囲外の場合
     */
    public int get(long index, boolean[] dest, int offset, int length);

    /**
     * <p>
     * 指定された位置から、length の長さだけビット値を取得し、
     * ビット列に格納します。
     * </p>
     *
     * @param index  読み出し開始位置（ビット単位）
     * @param dest   結果を格納するビット列
     * @param offset 結果の格納を開始する位置（ビット単位）
     * @param length 読みだすビット数
     * @return 実際に読みだしたビット数
     * @throws IndexOutOfBoundsException 読み出し位置が負、ビット列の範囲外の場合
     */
    public int get(long index, LargeList<Boolean> dest, int offset, int length);

    /**
     * <p>
     * 指定された位置にビット値を設定します。
     * ブール値が true ならば 1 が設定され、false ならば 0 が設定されます。
     * </p>
     *
     * @param index 要素の位置（ビット単位）
     * @param data  指定された位置に設定する値、
     * 1 を設定するならば true、0 を設定するならば false
     * @throws IndexOutOfBoundsException 書き込み位置が負、ビット列の範囲外の場合
     */
    public void set(long index, Boolean data);

    /**
     * <p>
     * 指定された位置から、length の長さだけビット値を設定します。
     * ブール値が true ならば 1 が設定され、false ならば 0 が設定されます。
     * </p>
     *
     * @param index  バッファの書き込み開始位置（ビット単位）
     * @param src    バッファに書きこむ boolean 型配列
     * @param offset データの書きこみを開始する位置（ビット単位）
     * @param length 書きこむビット数
     * @return 実際に書き込んだビット数
     * @throws IndexOutOfBoundsException 書き込み位置が負、ビット列の範囲外の場合
     */
    public int set(long index, boolean[] src, int offset, int length);

    /**
     * <p>
     * 指定された位置から、length の長さだけビット値を設定します。
     * </p>
     *
     * @param index  バッファの書き込み開始位置（ビット単位）
     * @param src    バッファに書きこむビット列
     * @param offset データの書きこみを開始する位置（ビット単位）
     * @param length 書きこむビット数
     * @return 実際に書き込んだビット数
     * @throws IndexOutOfBoundsException 書き込み位置が負、ビット列の範囲外の場合
     */
    public int set(long index, LargeList<Boolean> src, int offset, int length);

    /**
     * <p>
     * 指定された位置から 0 ～ 8 ビットまでの任意のビット数を読み出し、
     * byte 型の LSB 側に詰めた値を取得します。
     * </p>
     *
     * @param index 読み出しを開始する位置（ビット単位）
     * @param n     取得するビット数（8 ビットまで）
     * @return ビット列から取得した n ビットの数値
     * @throws IllegalArgumentException 読み出すビット数が不適切だった
     * @throws IndexOutOfBoundsException ビット列の範囲外を読みだそうとした
     */
    public byte getPackedByte(long index, int n);

    /**
     * <p>
     * 指定された位置から 0 ～ 16 ビットまでの任意のビット数を読み出し、
     * short 型の LSB 側に詰めた値を取得します。
     * </p>
     *
     * @param index 読み出しを開始する位置（ビット単位）
     * @param n     取得するビット数（16 ビットまで）
     * @return ビット列から取得した n ビットの数値
     * @throws IllegalArgumentException 読み出すビット数が不適切だった
     * @throws IndexOutOfBoundsException ビット列の範囲外を読みだそうとした
     */
    public short getPackedShort(long index, int n);

    /**
     * <p>
     * 指定された位置から 0 ～ 32 ビットまでの任意のビット数を読み出し、
     * int 型の LSB 側に詰めた値を取得します。
     * </p>
     *
     * <pre>
     * (例)
     *
     * bit position | 0  1  2  3  4  5  6  7| 8  9 10 11 12 13 14 15|16
     * -------------+-----------------------+-----------------------+--
     * value        | 1  0  1  0  1  0  0  1| 1  0  0  0  0  1  0  1| 1
     *
     * 1. n =  5, index =  0 : return   21(0b10101)
     * 2. n =  5, index =  2 : return   20(0b10100)
     * 3. n =  5, index =  5 : return    6(0b00110)
     * 4. n = 12, index =  2 : return 2657(0b101001100001)
     * </pre>
     *
     * @param index 読み出しを開始する位置（ビット単位）
     * @param n     取得するビット数（32 ビットまで）
     * @return ビット列から取得した n ビットの数値
     * @throws IllegalArgumentException 読み出すビット数が不適切だった
     * @throws IndexOutOfBoundsException ビット列の範囲外を読みだそうとした
     */
    public int getPackedInt(long index, int n);

    /**
     * <p>
     * 指定された位置から 0 ～ 64 ビットまでの任意のビット数を読み出し、
     * long 型の LSB 側に詰めた値を取得します。
     * </p>
     *
     * @param index 読み出しを開始する位置（ビット単位）
     * @param n     取得するビット数（64 ビットまで）
     * @return ビット列から取得した n ビットの数値
     * @throws IllegalArgumentException 読み出すビット数が不適切だった
     * @throws IndexOutOfBoundsException ビット列の範囲外を読みだそうとした
     */
    public long getPackedLong(long index, int n);

    /**
     * <p>
     * 指定された位置から任意のビット数を読み出し、
     * byte 型の LSB 側に詰めた値を取得します。
     * </p>
     *
     * @param index 読み出しを開始する位置（ビット単位）
     * @param dst   取得したビットを格納する byte 型配列
     * @param off   読み出したビットを書きこむビット位置（配列内の位置）
     * @param n     取得するビット数（ビット単位）
     * @throws IllegalArgumentException 読み出すビット数が不適切だった
     * @throws IndexOutOfBoundsException ビット列の範囲外を読みだそうとした
     */
    public void getPackedByteArray(long index, byte[] dst, int off, int n);

    /**
     * <p>
     * 指定された位置から val の LSB 側から 0 ～ 8 ビットまでの、
     * 任意のビット数をビット列に書き込みます。
     * </p>
     *
     * @param index 書き込みを開始する位置（ビット単位）
     * @param n     書き込むビット数（8 ビットまで）
     * @param val   書き込むビットを含んだ整数値
     * @throws IllegalArgumentException 書き込むビット数が不適切だった
     * @throws IndexOutOfBoundsException ビット列のの範囲外に書き込もうとした
     */
    public void setPackedByte(long index, int n, byte val);

    /**
     * <p>
     * 指定された位置から val の LSB 側から 0 ～ 16 ビットまでの、
     * 任意のビット数をビット列に書き込みます。
     * </p>
     *
     * @param index 書き込みを開始する位置（ビット単位）
     * @param n     書き込むビット数（16 ビットまで）
     * @param val   書き込むビットを含んだ整数値
     * @throws IllegalArgumentException 書き込むビット数が不適切だった
     * @throws IndexOutOfBoundsException ビット列のの範囲外に書き込もうとした
     */
    public void setPackedShort(long index, int n, short val);

    /**
     * <p>
     * 指定された位置から val の LSB 側から 0 ～ 32 ビットまでの、
     * 任意のビット数をビット列に書き込みます。
     * </p>
     *
     * @param index 書き込みを開始する位置（ビット単位）
     * @param n     書き込むビット数（32 ビットまで）
     * @param val   書き込むビットを含んだ整数値
     * @throws IllegalArgumentException 書き込むビット数が不適切だった
     * @throws IndexOutOfBoundsException ビット列のの範囲外に書き込もうとした
     */
    public void setPackedInt(long index, int n, int val);

    /**
     * <p>
     * 指定された位置から val の LSB 側から 0 ～ 64 ビットまでの、
     * 任意のビット数をビット列に書き込みます。
     * </p>
     *
     * @param index 書き込みを開始する位置（ビット単位）
     * @param n     書き込むビット数（64 ビットまで）
     * @param val   書き込むビットを含んだ整数値
     * @throws IllegalArgumentException 書き込むビット数が不適切だった
     * @throws IndexOutOfBoundsException ビット列のの範囲外に書き込もうとした
     */
    public void setPackedLong(long index, int n, long val);

    /**
     * <p>
     * 指定された位置に任意のビット数を書き込みます。
     * </p>
     *
     * @param index 書き込みを開始する位置（ビット単位）
     * @param src   バッファに書き出す byte 型配列
     * @param off   書き込むビットを読み出すビット位置（配列内の位置）
     * @param n     書き込むビット数（ビット単位）
     * @throws IllegalArgumentException 読み出すビット数が不適切だった
     * @throws IndexOutOfBoundsException ビット列の範囲外を読みだそうとした
     */
    public void setPackedByteArray(long index, byte[] src, int off, int n);

    /**
     * <p>
     * ビット列の from（この要素を含む）から to（この要素を含まない）までの、
     * 部分列を返します。返された部分列への変更は、元のビット列と連動します。
     * </p>
     *
     * @param from 部分列の開始位置（ビット単位）
     * @param to   部分列の終了位置（ビット単位）
     * @return ビット列の部分列（ビット単位）
     */
    public LargeBitList subArray(long from, long to);
}
