package net.katsuster.strview.media.bit;

import net.katsuster.strview.media.*;
import net.katsuster.strview.util.bit.*;

/**
 * <p>
 * ビットリストを読み取ることに特化したインタフェースです。
 * </p>
 */
public interface BitStreamReader extends StreamReader<Boolean> {
    /**
     * <p>
     * 現在位置を更新せずに、数値を読み出します。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @return 変換対象の数値
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public long peekLong(int nbit);

    /**
     * <p>
     * 数値を読み出します。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @return 変換対象の数値
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public long readLong(int nbit);

    /**
     * <p>
     * 数値を読み出します。
     * データに加えて、データの意味、説明などを渡すことができます。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param desc 変換対象の符号付き数値の意味、説明など
     * @return 変換対象の符号付き数値オブジェクト
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public long readLong(int nbit, String desc);

    /**
     * <p>
     * 現在位置を更新せずに、符号付き数値を読み出します。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の符号付き数値オブジェクト
     * @return 変換対象の符号付き数値オブジェクト
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public SInt peekSInt(int nbit, SInt val);

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
     * 現在位置を更新せずに、符号無し数値を読み出します。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の符号無し数値オブジェクト
     * @return 変換対象の符号無し数値オブジェクト
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public UInt peekUInt(int nbit, UInt val);

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
     * 現在位置を更新せずに、符号付き数値を読み出します。
     * </p>
     *
     * <p>
     * バイト順序を逆順に並べ替えた値を読み込みます。
     * 指定できるサイズは 16, 32, 64 ビットのいずれかです。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の符号付き数値オブジェクト
     * @return 変換対象の符号付き数値オブジェクト
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public SIntR peekSIntR(int nbit, SIntR val);

    /**
     * <p>
     * 符号付き数値を読み出します。
     * </p>
     *
     * <p>
     * バイト順序を逆順に並べ替えた値を読み込みます。
     * 指定できるサイズは 16, 32, 64 ビットのいずれかです。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の符号付き数値オブジェクト
     * @return 変換対象の符号付き数値オブジェクト
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public SIntR readSIntR(int nbit, SIntR val);

    /**
     * <p>
     * 符号付き数値を読み出します。
     * データに加えて、データの意味、説明などを渡すことができます。
     * </p>
     *
     * <p>
     * バイト順序を逆順に並べ替えた値を読み込みます。
     * 指定できるサイズは 16, 32, 64 ビットのいずれかです。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の符号付き数値オブジェクト
     * @param desc 変換対象の符号付き数値の意味、説明など
     * @return 変換対象の符号付き数値オブジェクト
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public SIntR readSIntR(int nbit, SIntR val, String desc);

    /**
     * <p>
     * 現在位置を更新せずに、符号無し数値を読み出します。
     * </p>
     *
     * <p>
     * バイト順序を逆順に並べ替えた値を読み込みます。
     * 指定できるサイズは 16, 32, 64 ビットのいずれかです。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の符号無し数値オブジェクト
     * @return 変換対象の符号無し数値オブジェクト
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public UIntR peekUIntR(int nbit, UIntR val);

    /**
     * <p>
     * 符号無し数値を読み出します。
     * </p>
     *
     * <p>
     * バイト順序を逆順に並べ替えた値を読み込みます。
     * 指定できるサイズは 16, 32, 64 ビットのいずれかです。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の符号無し数値オブジェクト
     * @return 変換対象の符号無し数値オブジェクト
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public UIntR readUIntR(int nbit, UIntR val);

    /**
     * <p>
     * 符号無し数値を読み出します。
     * データに加えて、データの意味、説明などを渡すことができます。
     * </p>
     *
     * <p>
     * バイト順序を逆順に並べ替えた値を読み込みます。
     * 指定できるサイズは 16, 32, 64 ビットのいずれかです。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の符号無し数値オブジェクト
     * @param desc 変換対象の符号無し数値の意味、説明など
     * @return 変換対象の符号無し数値オブジェクト
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public UIntR readUIntR(int nbit, UIntR val, String desc);

    /**
     * <p>
     * 現在位置を更新せずに、32ビット浮動小数値を読み出します。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の浮動小数値オブジェクト
     * @return 変換対象の浮動小数値オブジェクト
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public Float32 peekFloat32(int nbit, Float32 val);

    /**
     * <p>
     * 32ビット浮動小数値を読み出します。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の浮動小数値オブジェクト
     * @return 変換対象の浮動小数値オブジェクト
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public Float32 readFloat32(int nbit, Float32 val);

    /**
     * <p>
     * 32ビット浮動小数値を読み出します。
     * データに加えて、データの意味、説明などを渡すことができます。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の浮動小数値オブジェクト
     * @param desc 変換対象の浮動小数値の意味、説明など
     * @return 変換対象の浮動小数値オブジェクト
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public Float32 readFloat32(int nbit, Float32 val, String desc);

    /**
     * <p>
     * 現在位置を更新せずに、64ビット浮動小数値を読み出します。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の浮動小数値オブジェクト
     * @return 変換対象の浮動小数値オブジェクト
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public Float64 peekFloat64(int nbit, Float64 val);

    /**
     * <p>
     * 64ビット浮動小数値を読み出します。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の浮動小数値オブジェクト
     * @return 変換対象の浮動小数値オブジェクト
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public Float64 readFloat64(int nbit, Float64 val);

    /**
     * <p>
     * 64ビット浮動小数値を読み出します。
     * データに加えて、データの意味、説明などを渡すことができます。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の浮動小数値オブジェクト
     * @param desc 変換対象の浮動小数値の意味、説明など
     * @return 変換対象の浮動小数値オブジェクト
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public Float64 readFloat64(int nbit, Float64 val, String desc);

    /**
     * <p>
     * 現在位置を更新せずに、16ビット符号付き固定小数値を読み出します。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の固定小数値オブジェクト
     * @return 変換対象の固定小数値オブジェクト
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public SFixed8_8 peekSF8_8(int nbit, SFixed8_8 val);

    /**
     * <p>
     * 16ビット符号付き固定小数値を読み出します。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の固定小数値オブジェクト
     * @return 変換対象の固定小数値オブジェクト
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public SFixed8_8 readSF8_8(int nbit, SFixed8_8 val);

    /**
     * <p>
     * 16ビット符号付き固定小数値を読み出します。
     * データに加えて、データの意味、説明などを渡すことができます。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の固定小数値オブジェクト
     * @param desc 変換対象の固定小数値の意味、説明など
     * @return 変換対象の固定小数値オブジェクト
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public SFixed8_8 readSF8_8(int nbit, SFixed8_8 val, String desc);

    /**
     * <p>
     * 現在位置を更新せずに、32ビット符号付き固定小数値を読み出します。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の固定小数値オブジェクト
     * @return 変換対象の固定小数値オブジェクト
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public SFixed16_16 peekSF16_16(int nbit, SFixed16_16 val);

    /**
     * <p>
     * 32ビット符号付き固定小数値を読み出します。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の固定小数値オブジェクト
     * @return 変換対象の固定小数値オブジェクト
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public SFixed16_16 readSF16_16(int nbit, SFixed16_16 val);

    /**
     * <p>
     * 32ビット符号付き固定小数値を読み出します。
     * データに加えて、データの意味、説明などを渡すことができます。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の固定小数値オブジェクト
     * @param desc 変換対象の固定小数値の意味、説明など
     * @return 変換対象の固定小数値オブジェクト
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public SFixed16_16 readSF16_16(int nbit, SFixed16_16 val, String desc);

    /**
     * <p>
     * 現在位置を更新せずに、16ビット符号なし固定小数値を読み出します。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の固定小数値オブジェクト
     * @return 変換対象の固定小数値オブジェクト
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public UFixed8_8 peekUF8_8(int nbit, UFixed8_8 val);

    /**
     * <p>
     * 16ビット符号なし固定小数値を読み出します。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の固定小数値オブジェクト
     * @return 変換対象の固定小数値オブジェクト
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public UFixed8_8 readUF8_8(int nbit, UFixed8_8 val);

    /**
     * <p>
     * 16ビット符号なし固定小数値を読み出します。
     * データに加えて、データの意味、説明などを渡すことができます。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の固定小数値オブジェクト
     * @param desc 変換対象の固定小数値の意味、説明など
     * @return 変換対象の固定小数値オブジェクト
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public UFixed8_8 readUF8_8(int nbit, UFixed8_8 val, String desc);

    /**
     * <p>
     * 現在位置を更新せずに、32ビット符号なし固定小数値を読み出します。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の固定小数値オブジェクト
     * @return 変換対象の固定小数値オブジェクト
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public UFixed16_16 peekUF16_16(int nbit, UFixed16_16 val);

    /**
     * <p>
     * 32ビット符号なし固定小数値を読み出します。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の固定小数値オブジェクト
     * @return 変換対象の固定小数値オブジェクト
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public UFixed16_16 readUF16_16(int nbit, UFixed16_16 val);

    /**
     * <p>
     * 32ビット符号なし固定小数値を読み出します。
     * データに加えて、データの意味、説明などを渡すことができます。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の固定小数値オブジェクト
     * @param desc 変換対象の固定小数値の意味、説明など
     * @return 変換対象の固定小数値オブジェクト
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public UFixed16_16 readUF16_16(int nbit, UFixed16_16 val, String desc);

    /**
     * <p>
     * 現在位置を更新せずに、ビットリストを読み出します。
     * </p>
     *
     * <p>
     * readBitList で取得したデータのコピーを変更しても、取得元のデータは変化しません。
     * readSubList で取得した部分列を変更すると、取得元のデータも変化します。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象のビットリスト
     * @return 変換対象のビットリスト
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public LargeBitList peekBitList(long nbit, LargeBitList val);

    /**
     * <p>
     * ビットリストを読み出します。
     * </p>
     *
     * <p>
     * readBitList で取得したデータのコピーを変更しても、取得元のデータは変化しません。
     * readSubList で取得した部分列を変更すると、取得元のデータも変化します。
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
     * <p>
     * readBitList で取得したデータのコピーを変更しても、取得元のデータは変化しません。
     * readSubList で取得した部分列を変更すると、取得元のデータも変化します。
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
