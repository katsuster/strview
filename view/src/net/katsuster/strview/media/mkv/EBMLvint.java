package net.katsuster.strview.media.mkv;

import net.katsuster.strview.media.*;
import net.katsuster.strview.util.*;

/**
 * <p>
 * EBML の UTF-8 風可変長整数値の基底クラス。
 * </p>
 *
 * @see EBMLvid
 * @see EBMLvalue
 */
public abstract class EBMLvint<T extends LargeList<?>>
        extends BlockAdapter<T>
        implements Cloneable {
    //可変長整数値の全体サイズ（ビット単位）
    private int size_all;
    //可変長整数値のうち値が占める部分のサイズ（ビット単位）
    private int size_content;

    public EBMLvint() {
        //Do nothing
    }

    @Override
    public EBMLvint clone()
            throws CloneNotSupportedException {
        EBMLvint obj = (EBMLvint)super.clone();

        return obj;
    }

    /**
     * <p>
     * 可変長整数値のデコード後の値を取得する。
     * </p>
     *
     * @return 可変長整数値のデコード後の値
     */
    public abstract long getValue();

    /**
     * <p>
     * 可変長整数値のデコード後の値を設定する。
     * </p>
     *
     * @param v 可変長整数値のデコード後の値
     */
    protected abstract void setValue(long v);

    /**
     * <p>
     * 可変長整数値の全体サイズを取得します。
     * </p>
     *
     * @return 可変長整数値の全体サイズ（ビット単位）
     */
    public int getSizeAll() {
        return size_all;
    }

    /**
     * <p>
     * 可変長整数値の全体サイズを設定します。
     * </p>
     *
     * @param size 可変長整数値の全体サイズ（ビット単位）
     */
    public void setSizeAll(int size) {
        size_all = size;
    }

    /**
     * <p>
     * 可変長整数値のうち、値が占める部分のサイズを取得します。
     * </p>
     *
     * @return 可変長整数値のうち、値が占める部分のサイズ（ビット単位）
     */
    public int getSizeContent() {
        return size_content;
    }

    /**
     * <p>
     * 可変長整数値のうち、値が占める部分のサイズを設定します。
     * </p>
     *
     * @param size 可変長整数値のうち、値が占める部分のサイズ（ビット単位）
     */
    public void setSizeContent(int size) {
        size_content = size;
    }

    /**
     * <p>
     * 可変長整数の種類を求めます。
     * </p>
     *
     * <p>
     * 下記のいずれかの種類を返します。
     * </p>
     *
     * <dl>
     * <dt>0</dt>
     * <dd>8bit: 0b1xxx-xxxx</dd>
     * <dt>1</dt>
     * <dd>16bit: 0b01xx-xxxx xxxx-xxxx</dd>
     * <dt>2</dt>
     * <dd>24bit: 0b001x-xxxx xxxx-xxxx xxxx-xxxx</dd>
     * <dt>3</dt>
     * <dd>32bit: 0b0001-xxxx xxxx-xxxx xxxx-xxxx xxxx-xxxx</dd>
     * <dt>4</dt>
     * <dd>40bit: 0b0000-1xxx xxxx-xxxx xxxx-xxxx xxxx-xxxx
     * xxxx-xxxx</dd>
     * <dt>5</dt>
     * <dd>48bit: 0b0000-01xx xxxx-xxxx xxxx-xxxx xxxx-xxxx
     * xxxx-xxxx xxxx-xxxx</dd>
     * <dt>6</dt>
     * <dd>56bit: 0b0000-001x xxxx-xxxx xxxx-xxxx xxxx-xxxx
     * xxxx-xxxx xxxx-xxxx xxxx-xxxx</dd>
     * <dt>7</dt>
     * <dd>64bit: 0b0000-0001 xxxx-xxxx xxxx-xxxx xxxx-xxxx
     * xxxx-xxxx xxxx-xxxx xxxx-xxxx xxxx-xxxx</dd>
     * </dl>
     *
     * @param f 可変長整数の先頭 8bit の値
     * @return 可変長整数の符号全体の種類
     * @throws IllegalArgumentException どの種類にも当てはまらない場合
     */
    public static int getVintType(int f) {
        int i = 8 + Integer.numberOfLeadingZeros(f & 0xff) - 32;
        if (i < 8) {
            return i;
        }

        //over 64bit... not support
        throw new IllegalArgumentException(
                "EBMLvint is not supported over 64bits.");
    }

    /**
     * <p>
     * 可変長整数の符号全体の長さを求めます。
     * </p>
     *
     * @param f 可変長整数の先頭 8bit の値
     * @return 可変長整数の符号全体の長さ（ビット単位）
     */
    public static int getVintSize(int f) {
        int t = getVintType(f);
        int s[] = {
                8, 16, 24, 32,
                40, 48, 56, 64,
        };

        return s[t];
    }

    /**
     * <p>
     * 可変長整数のうち値が占める部分の長さを求めます。
     * </p>
     *
     * @param f 可変長整数の先頭 8bit の値
     * @return 可変長整数のうち値が占める長さ（ビット単位）
     */
    public static int getVintContentSize(int f) {
        int t = getVintType(f);
        int s[] = {
                7, 14, 21, 28,
                35, 42, 49, 56,
        };

        return s[t];
    }

    /**
     * <p>
     * 通常の整数を可変長整数に変換したときの種類を求めます。
     * </p>
     *
     * <p>
     * 下記のいずれかの種類を返します。
     * </p>
     *
     * <dl>
     * <dt>0</dt>
     * <dd>8bit: 0b1xxx-xxxx</dd>
     * <dt>1</dt>
     * <dd>16bit: 0b01xx-xxxx xxxx-xxxx</dd>
     * <dt>2</dt>
     * <dd>24bit: 0b001x-xxxx xxxx-xxxx xxxx-xxxx</dd>
     * <dt>3</dt>
     * <dd>32bit: 0b0001-xxxx xxxx-xxxx xxxx-xxxx xxxx-xxxx</dd>
     * <dt>4</dt>
     * <dd>40bit: 0b0000-1xxx xxxx-xxxx xxxx-xxxx xxxx-xxxx
     * xxxx-xxxx</dd>
     * <dt>5</dt>
     * <dd>48bit: 0b0000-01xx xxxx-xxxx xxxx-xxxx xxxx-xxxx
     * xxxx-xxxx xxxx-xxxx</dd>
     * <dt>6</dt>
     * <dd>56bit: 0b0000-001x xxxx-xxxx xxxx-xxxx xxxx-xxxx
     * xxxx-xxxx xxxx-xxxx xxxx-xxxx</dd>
     * <dt>7</dt>
     * <dd>64bit: 0b0000-0001 xxxx-xxxx xxxx-xxxx xxxx-xxxx
     * xxxx-xxxx xxxx-xxxx xxxx-xxxx xxxx-xxxx</dd>
     * </dl>
     *
     * @param f 整数
     * @return 可変長整数の符号全体の種類
     * @throws IllegalArgumentException どの種類にも当てはまらない場合
     */
    public static int parseVintType(long f) {
        if (f < 0) {
            //over 56bit... not support
            throw new IllegalArgumentException(
                    "EBMLvint is not supported over 56bits value.");
        }

        int i = (64 - Long.numberOfLeadingZeros(f + 1) - 1) / 7;
        if (i < 8) {
            return i;
        }

        //over 56bit... not support
        throw new IllegalArgumentException(
                "EBMLvint is not supported over 56bits value.");
    }

    /**
     * <p>
     * 整数を可変長整数に変換したとき、符号全体の長さを求めます。
     * </p>
     *
     * @param f 整数
     * @return 可変長整数の符号全体の長さ（ビット単位）
     */
    public static int parseVintSize(long f) {
        int t = parseVintType(f);
        int s[] = {
                8, 16, 24, 32,
                40, 48, 56, 64,
        };

        return s[t];
    }

    /**
     * <p>
     * 整数を可変長整数に変換したとき、値が占める部分の長さを求めます。
     * </p>
     *
     * @param f 整数
     * @return 可変長整数のうち値が占める長さ（ビット単位）
     */
    public static int parseVintContentSize(long f) {
        int t = parseVintType(f);
        int s[] = {
                7, 14, 21, 28,
                35, 42, 49, 56,
        };

        return s[t];
    }
}
