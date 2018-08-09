package net.katsuster.strview.media.bit;

import net.katsuster.strview.media.*;
import net.katsuster.strview.util.bit.*;

/**
 * <p>
 * ビットリストを書き込むことに特化したインタフェースです。
 * </p>
 */
public interface BitStreamWriter extends StreamWriter<Boolean> {
    /**
     * <p>
     * 現在位置を更新せずに、数値を書き込みます。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の数値
     * @param name 変換対象の名前
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public void pokeLong(int nbit, long val, String name);

    /**
     * <p>
     * 数値を書き込みます。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の数値
     * @param name 変換対象の名前
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public void writeLong(int nbit, long val, String name);

    /**
     * <p>
     * 数値を書き込みます。
     * データに加えて、データの意味、説明などを渡すことができます。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の数値
     * @param name 変換対象の名前
     * @param desc 変換対象の符号付き数値の意味、説明など
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public void writeLong(int nbit, long val, String name, String desc);

    /**
     * <p>
     * 現在位置を更新せずに、符号付き数値を書き込みます。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の符号付き数値オブジェクト
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public void pokeSInt(int nbit, SInt val);

    /**
     * <p>
     * 符号付き数値を書き込みます。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の符号付き数値オブジェクト
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public void writeSInt(int nbit, SInt val);

    /**
     * <p>
     * 符号付き数値を書き込みます。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の符号付き数値オブジェクト
     * @param desc 変換対象の符号無し数値の意味、説明など
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public void writeSInt(int nbit, SInt val, String desc);

    /**
     * <p>
     * 現在位置を更新せずに、符号無し数値を書き込みます。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の符号無し数値オブジェクト
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public void pokeUInt(int nbit, UInt val);

    /**
     * <p>
     * 符号無し数値を書き込みます。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の符号無し数値オブジェクト
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public void writeUInt(int nbit, UInt val);

    /**
     * <p>
     * 符号無し数値を書き込みます。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の符号無し数値オブジェクト
     * @param desc 変換対象の符号無し数値の意味、説明など
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public void writeUInt(int nbit, UInt val, String desc);

    /**
     * <p>
     * 現在位置を更新せずに、符号付き数値を書き込みます。
     * </p>
     *
     * <p>
     * バイト順序を逆順に並べ替えた値を書き込みます。
     * 指定できるサイズは 16, 32, 64 ビットのいずれかです。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の符号付き数値オブジェクト
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public void pokeSIntR(int nbit, SIntR val);

    /**
     * <p>
     * 符号付き数値を書き込みます。
     * </p>
     *
     * <p>
     * バイト順序を逆順に並べ替えた値を書き込みます。
     * 指定できるサイズは 16, 32, 64 ビットのいずれかです。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の符号付き数値オブジェクト
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public void writeSIntR(int nbit, SIntR val);

    /**
     * <p>
     * 符号付き数値を書き込みます。
     * </p>
     *
     * <p>
     * バイト順序を逆順に並べ替えた値を書き込みます。
     * 指定できるサイズは 16, 32, 64 ビットのいずれかです。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の符号付き数値オブジェクト
     * @param desc 変換対象の符号無し数値の意味、説明など
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public void writeSIntR(int nbit, SIntR val, String desc);

    /**
     * <p>
     * 現在位置を更新せずに、符号無し数値を書き込みます。
     * </p>
     *
     * <p>
     * バイト順序を逆順に並べ替えた値を書き込みます。
     * 指定できるサイズは 16, 32, 64 ビットのいずれかです。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の符号無し数値オブジェクト
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public void pokeUIntR(int nbit, UIntR val);

    /**
     * <p>
     * 符号無し数値を書き込みます。
     * </p>
     *
     * <p>
     * バイト順序を逆順に並べ替えた値を書き込みます。
     * 指定できるサイズは 16, 32, 64 ビットのいずれかです。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の符号無し数値オブジェクト
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public void writeUIntR(int nbit, UIntR val);

    /**
     * <p>
     * 符号無し数値を書き込みます。
     * </p>
     *
     * <p>
     * バイト順序を逆順に並べ替えた値を書き込みます。
     * 指定できるサイズは 16, 32, 64 ビットのいずれかです。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の符号無し数値オブジェクト
     * @param desc 変換対象の符号無し数値の意味、説明など
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public void writeUIntR(int nbit, UIntR val, String desc);

    /**
     * <p>
     * 現在位置を更新せずに、32ビット浮動小数値を書き込みます。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の浮動小数値オブジェクト
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public void pokeFloat32(int nbit, Float32 val);

    /**
     * <p>
     * 32ビット浮動小数値を書き込みます。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の浮動小数値オブジェクト
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public void writeFloat32(int nbit, Float32 val);

    /**
     * <p>
     * 32ビット浮動小数値を書き込みます。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の浮動小数値オブジェクト
     * @param desc 変換対象の符号無し数値の意味、説明など
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public void writeFloat32(int nbit, Float32 val, String desc);

    /**
     * <p>
     * 現在位置を更新せずに、64ビット浮動小数値を書き込みます。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の浮動小数値オブジェクト
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public void pokeFloat64(int nbit, Float64 val);

    /**
     * <p>
     * 64ビット浮動小数値を書き込みます。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の浮動小数値オブジェクト
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public void writeFloat64(int nbit, Float64 val);

    /**
     * <p>
     * 64ビット浮動小数値を書き込みます。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の浮動小数値オブジェクト
     * @param desc 変換対象の符号無し数値の意味、説明など
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public void writeFloat64(int nbit, Float64 val, String desc);

    /**
     * <p>
     * 現在位置を更新せずに、16ビット符号付き固定小数値を書き込みます。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の固定小数値オブジェクト
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public void pokeSF8_8(int nbit, SFixed8_8 val);

    /**
     * <p>
     * 16ビット符号付き固定小数値を書き込みます。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の固定小数値オブジェクト
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public void writeSF8_8(int nbit, SFixed8_8 val);

    /**
     * <p>
     * 16ビット符号付き固定小数値を書き込みます。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の固定小数値オブジェクト
     * @param desc 変換対象の固定小数数値の意味、説明など
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public void writeSF8_8(int nbit, SFixed8_8 val, String desc);

    /**
     * <p>
     * 現在位置を更新せずに、32ビット符号付き固定小数値を書き込みます。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の固定小数値オブジェクト
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public void pokeSF16_16(int nbit, SFixed16_16 val);

    /**
     * <p>
     * 32ビット符号付き固定小数値を書き込みます。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の固定小数値オブジェクト
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public void writeSF16_16(int nbit, SFixed16_16 val);

    /**
     * <p>
     * 32ビット符号付き固定小数値を書き込みます。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の固定小数値オブジェクト
     * @param desc 変換対象の固定小数数値の意味、説明など
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public void writeSF16_16(int nbit, SFixed16_16 val, String desc);

    /**
     * <p>
     * 現在位置を更新せずに、16ビット符号なし固定小数値を書き込みます。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の固定小数値オブジェクト
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public void pokeUF8_8(int nbit, UFixed8_8 val);

    /**
     * <p>
     * 16ビット符号なし固定小数値を書き込みます。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の固定小数値オブジェクト
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public void writeUF8_8(int nbit, UFixed8_8 val);

    /**
     * <p>
     * 16ビット符号なし固定小数値を書き込みます。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の固定小数値オブジェクト
     * @param desc 変換対象の固定小数数値の意味、説明など
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public void writeUF8_8(int nbit, UFixed8_8 val, String desc);

    /**
     * <p>
     * 現在位置を更新せずに、32ビット符号なし固定小数値を書き込みます。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の固定小数値オブジェクト
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public void pokeUF16_16(int nbit, UFixed16_16 val);

    /**
     * <p>
     * 32ビット符号なし固定小数値を書き込みます。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の固定小数値オブジェクト
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public void writeUF16_16(int nbit, UFixed16_16 val);

    /**
     * <p>
     * 32ビット符号なし固定小数値を書き込みます。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象の固定小数値オブジェクト
     * @param desc 変換対象の固定小数数値の意味、説明など
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public void writeUF16_16(int nbit, UFixed16_16 val, String desc);

    /**
     * <p>
     * 現在位置を更新せずに、ビットリストを書き込みます。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象のビットリスト
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public void pokeBitList(long nbit, LargeBitList val);

    /**
     * <p>
     * ビットリストを書き込みます。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象のビットリスト
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public void writeBitList(long nbit, LargeBitList val);

    /**
     * <p>
     * ビットリストを書き込みます。
     * </p>
     *
     * @param nbit 変換対象のサイズ（ビット単位）
     * @param val  変換対象のビットリスト
     * @param desc 変換対象のビットリストの意味、説明など
     * @throws IllegalArgumentException 無効なパラメータや null を渡した場合
     */
    public void writeBitList(long nbit, LargeBitList val, String desc);
}
