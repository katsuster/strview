package net.katsuster.strview.util;

import java.util.*;

/**
 * <p>
 * 64bit の符号あり整数、位置、長さを格納するクラス。
 * </p>
 *
 * @author katsuhiro
 */
public class SInt extends Num
        implements Comparable<SInt> {
    private long val;

    public SInt() {
        this(0, 0, 0);
    }

    public SInt(long v) {
        this(v, 0, 0);
    }

    public SInt(long v, int l, long p) {
        super(l, p);
        setBitsValue(v);
    }

    public SInt(SInt obj) {
        super(obj);
        setBitsValue(obj.getBitsValue());
    }

    /**
     * <p>
     * オブジェクトを指定されたオブジェクトと比較します。
     * 結果が true になるのは、引数が null ではなく、
     * このオブジェクトと同じ long 値を含む
     * Sint64 オブジェクトである場合だけです。
     * </p>
     *
     * @param o 比較対象のオブジェクト
     * @return オブジェクトが同じである場合は true、そうでない場合は false
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof SInt) {
            return (((SInt)o).val == val);
        } else {
            return false;
        }
    }

    /**
     * <p>
     * 2つのオブジェクトを数値的に比較します。
     * </p>
     *
     * @param obj 比較対象となるオブジェクト
     * @return このオブジェクトと比較対象が等しければ 0、
     * このオブジェクトが比較対象より小さい数値ならば負の値、
     * このオブジェクトが比較対象より大きい数値ならば正の値
     */
    @Override
    public int compareTo(SInt obj) {
        if (val < obj.val) {
            return -1;
        } else if (val > obj.val) {
            return 1;
        }

        return 0;
    }

    /**
     * <p>
     * オブジェクトのハッシュコードを返します。
     * </p>
     *
     * @return オブジェクトが保持する値 val を、
     * 変換式 (val ^ (val &gt;&gt; 32)) にて int に変換した値に等しい
     */
    @Override
    public int hashCode() {
        return (int)(val ^ (val >> 32));
    }

    @Override
    public byte byteValue() {
        return (byte)val;
    }

    @Override
    public short shortValue() {
        return (short)val;
    }

    @Override
    public int intValue() {
        return (int)val;
    }

    @Override
    public long longValue() {
        return val;
    }

    @Override
    public float floatValue() {
        return (float)val;
    }

    @Override
    public double doubleValue() {
        return (double)val;
    }

    @Override
    public long getBitsValue() {
        return val;
    }

    /**
     * <p>
     * ビット列を設定する。
     * </p>
     *
     * @param v ビット列
     */
    public void setBitsValue(long v) {
        val = v;
    }

    /**
     * 値を表す String オブジェクトを返します。
     *
     * @return 10 進数 (基数 10) による文字列表現
     */
    @Override
    public String toString() {
        return Long.toString(val);
    }
}
