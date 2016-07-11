package net.katsuster.strview.util;

/**
 * <p>
 * 通常の byte 型配列と、位置、長さを格納するクラスです。
 * </p>
 *
 * @author katsuhiro
 */
public class ByteArray extends Range
        implements Cloneable {
    private byte[] array;

    public ByteArray() {
        this(new byte[0], 0, 0);
    }

    public ByteArray(byte[] a) {
        this(a, 0, 0);
    }

    public ByteArray(byte[] a, int l, long p) {
        super(p, p + l);
        setArray(a);
    }

    /**
     * <p>
     * バイト配列の長さ（ビット単位）と、
     * バイト配列が存在する位置（ビット単位）を、
     * コピーした新たなバイト配列を返します。
     * </p>
     *
     * @return コピーされたオブジェクト
     * @throws CloneNotSupportedException インスタンスを複製できない場合
     */
    public ByteArray clone() throws CloneNotSupportedException {
        ByteArray obj = (ByteArray)super.clone();

        obj.array = array.clone();

        return obj;
    }

    /**
     * <p>
     * バイト配列を取得する。
     * </p>
     *
     * @return バイト配列
     */
    public byte[] getArray() {
        return array;
    }

    /**
     * <p>
     * バイト配列を設定する。
     * </p>
     *
     * @param a バイト配列
     */
    public void setArray(byte[] a) {
        array = a;
    }
}
