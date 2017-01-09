package net.katsuster.strview.io;

/**
 * <p>
 * 通常の byte 型配列を LargeByteList インタフェースでラップしたクラスです。
 * </p>
 *
 * @author katsuhiro
 */
public class MemoryByteList extends AbstractLargeList<Byte>
        implements LargeByteList, Cloneable {
    private byte[] buf;

    /**
     * <p>
     * 長さ 0 のバイト列を作成します。
     * </p>
     */
    public MemoryByteList() {
        this(0);
    }

    @Override
    public MemoryByteList clone()
            throws CloneNotSupportedException {
        MemoryByteList obj = (MemoryByteList)super.clone();

        obj.buf = buf.clone();

        return obj;
    }

    /**
     * <p>
     * 指定された長さのバイト列を作成します。
     * </p>
     *
     * <p>
     * 指定できる最大サイズは Integer.MAX_VALUE までです。
     * </p>
     *
     * @param size 作成するバイト列の長さ
     * @throws IllegalArgumentException Integer.MAX_VALUE 以上のサイズを
     * 指定した場合
     * @throws NegativeArraySizeException 負のサイズを指定した場合
     */
    public MemoryByteList(long size) {
        super(size);

        if (Integer.MAX_VALUE < size) {
            throw new IllegalArgumentException(
                    "size:" + size + " is too large.");
        }

        buf = new byte[(int)size];
    }

    /**
     * <p>
     * 既存の byte 型配列を利用して、バイト列を作成します。
     * </p>
     *
     * @param array 利用する byte 型配列
     * @throws IllegalArgumentException null を指定した場合
     */
    public MemoryByteList(byte[] array) {
        super(0, 0);

        if (array == null) {
            throw new IllegalArgumentException(
                    "array is null.");
        }

        buf = array;
        length(array.length);
        getRange().setLength(array.length << 3);
    }

    /**
     * <p>
     * 既存の byte 型配列と位置を指定して、バイト列を作成します。
     * </p>
     *
     * @param array 利用する byte 型配列
     * @param st    開始位置
     * @param ed    終了位置
     * @throws IllegalArgumentException null を指定した場合
     */
    public MemoryByteList(byte[] array, long st, long ed) {
        super(st, ed);

        if (array == null) {
            throw new IllegalArgumentException(
                    "array is null.");
        }

        buf = array;
    }

    @Override
    public long length() {
        return buf.length;
    }

    @Override
    public int get(long index, byte[] dest, int offset, int length) {
        System.arraycopy(buf, (int)index, dest, offset, length);

        return length;
    }

    @Override
    public int set(long index, byte[] src, int offset, int length) {
        System.arraycopy(src, offset, buf, (int)index, length);

        return length;
    }

    @Override
    public Byte getInner(long index) {
        return buf[(int)index];
    }

    @Override
    public void setInner(long index, Byte data) {
        buf[(int)index] = data;
    }
}
