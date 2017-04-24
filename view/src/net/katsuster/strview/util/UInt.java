package net.katsuster.strview.util;

import java.util.*;

/**
 * <p>
 * 64bit の符号無し整数、位置、長さを格納するクラス。
 * </p>
 *
 * @author katsuhiro
 */
public class UInt extends AbstractNum
        implements Comparable<UInt> {
    private long val;

    public UInt() {
        this(0, 0, 0);
    }

    public UInt(long v) {
        this(v, 0, 0);
    }

    public UInt(long v, long p, int l) {
        super(p, l);
        setBitsValue(v);
    }

    public UInt(UInt obj) {
        super(obj);
        setBitsValue(obj.getBitsValue());
    }

    /**
     * <p>
     * オブジェクトを指定されたオブジェクトと比較します。
     * 結果が true になるのは、引数が null ではなく、
     * このオブジェクトと同じ long 値を含む
     * UInt オブジェクトである場合だけです。
     * </p>
     *
     * @param o 比較対象のオブジェクト
     * @return オブジェクトが同じである場合は true、そうでない場合は false
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof UInt) {
            return (((UInt)o).val == val);
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
    public int compareTo(UInt obj) {
        long upper, obj_upper;
        long lower, obj_lower;

        //上位 63ビットを比べる
        upper = val >>> 1;
        obj_upper = obj.val >>> 1;

        if (upper - obj_upper < 0) {
            return -1;
        } else if (upper - obj_upper > 0) {
            return 1;
        }

        //下位 1ビットを比べる
        lower = val & 1;
        obj_lower = obj.val & 1;

        if (lower - obj_lower < 0) {
            return -1;
        } else if (lower - obj_lower > 0) {
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
     * 変換式 (val ^ (val &gt;&gt;&gt; 32)) にて int に変換した値に等しい
     */
    @Override
    public int hashCode() {
        return (int)(val ^ (val >>> 32));
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
        if ((val & 0x8000000000000000L) != 0) {
            return (float)(val & ~0x8000000000000000L)
                    + 0x7fffffffffffffffL + 0x1L;
        } else {
            return (float)val;
        }
    }

    @Override
    public double doubleValue() {
        if ((val & 0x8000000000000000L) != 0) {
            return (double)(val & ~0x8000000000000000L)
                    + 0x7fffffffffffffffL + 0x1L;
        } else {
            return (double)val;
        }
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
        return uint64ToString(val);
    }
}
