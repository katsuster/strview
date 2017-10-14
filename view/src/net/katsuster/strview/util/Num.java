package net.katsuster.strview.util;

/**
 * <p>
 * 数値、位置、長さを格納するクラスの基底クラス。
 * </p>
 */
public interface Num extends LargeBitList {
    /**
     * <p>
     * 数値の長さ（ビット単位）と、
     * 数値が存在する位置（ビット単位）を、
     * コピーした新たな数値を返します。
     * </p>
     *
     * @return コピーされたオブジェクト
     * @throws CloneNotSupportedException インスタンスを複製できない場合
     */
    public Object clone()
            throws CloneNotSupportedException;

    /**
     * <p>
     * 指定された数値を byte 型として返します。
     * 値を丸めたり切り詰めたりすることもあります。
     * </p>
     *
     * @return このオブジェクトが表す数値を byte 型に変換した値
     */
    public byte byteValue();

    /**
     * <p>
     * 指定された数値を short 型として返します。
     * 値を丸めたり切り詰めたりすることもあります。
     * </p>
     *
     * @return このオブジェクトが表す数値を short 型に変換した値
     */
    public short shortValue();

    /**
     * <p>
     * 指定された数値を int 型として返します。
     * 値を丸めたり切り詰めたりすることもあります。
     * </p>
     *
     * @return このオブジェクトが表す数値を int 型に変換した値
     */
    public int intValue();

    /**
     * <p>
     * 指定された数値を long 型として返します。
     * 値を丸めたり切り詰めたりすることもあります。
     * </p>
     *
     * @return このオブジェクトが表す数値を long 型に変換した値
     */
    public long longValue();

    /**
     * <p>
     * 指定された数値を float 型として返します。
     * 値を丸めたり切り詰めたりすることもあります。
     * </p>
     *
     * @return このオブジェクトが表す数値を float 型に変換した値
     */
    public float floatValue();

    /**
     * <p>
     * 指定された数値を double 型として返します。
     * 値を丸めたり切り詰めたりすることもあります。
     * </p>
     *
     * @return このオブジェクトが表す数値を double 型に変換した値
     */
    public double doubleValue();

    /**
     * <p>
     * 数値を long 型として返します。
     * </p>
     *
     * @return このオブジェクトが表す数値を long 型に変換した値
     */
    public long getValue();

    /**
     * <p>
     * long 型の数値を設定します。
     * </p>
     *
     * @param v このオブジェクトに設定する long 型の値
     */
    public void setValue(long v);

    /**
     * <p>
     * 数値を表すビットデータを long 型として返します。
     * バイト順の並び替え、符号拡張はしません。
     * </p>
     *
     * @return このオブジェクトが表すビットデータを long 型に変換した値
     */
    public long getRaw();

    /**
     * <p>
     * 数値を表すビットデータを long 型として返します。
     * バイト順の並び替え、符号拡張はしません。
     * </p>
     *
     * @param v このオブジェクトが表すビットデータを long 型に変換した値
     */
    public void setRaw(long v);
}
