package net.katsuster.strview.util;

/**
 * <p>
 * 数値、位置、長さを格納するクラスの基底クラス。
 * </p>
 *
 * @author katsuhiro
 */
public interface Num {
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
     * 数値が存在する範囲を取得します。
     * </p>
     *
     * <p>
     * 範囲の単位は数値によって意味が異なります。
     * またその範囲から完全に数値を再現できるとは限りません。
     * </p>
     *
     * <p>
     * 例えば、この数値が別のビット列 A から生成された場合、
     * リスト A の何ビット目から生成されたかを示します。
     * </p>
     *
     * @return 数値が存在する範囲
     */
    public Range getRange();

    /**
     * <p>
     * 数値が存在する範囲を設定します。
     * </p>
     *
     * <p>
     * 範囲の単位は数値によって意味が異なります。
     * またその範囲から完全に数値を再現できるとは限りません。
     * </p>
     *
     * @param range 数値が存在する範囲
     */
    public void setRange(Range range);

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
     * 数値を表すビットデータを long 型として返します。
     * 符号拡張はしません。
     * </p>
     *
     * @return このオブジェクトが表すビットデータを long 型に変換した値
     */
    public long getBitsValue();
}
