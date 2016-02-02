package net.katsuster.strview.io;

/**
 * <p>
 * 通常の byte 型配列を LargeByteList インタフェースでラップしたクラスです。
 * </p>
 *
 * @author katsuhiro
 */
public class MemoryByteList extends AbstractLargeByteList
        implements LargeByteList, Cloneable {
    private byte[] buf;

    /**
     * <p>
     * 長さ 0 の配列を作成します。
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
     * 指定された長さの配列を作成します。
     * </p>
     *
     * <p>
     * 指定できる最大サイズは Integer.MAX_VALUE までです。
     * </p>
     *
     * @param size 作成する配列の長さ
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
     * 既存のバイト配列を利用して、配列を作成します。
     * </p>
     *
     * @param array 利用するバイト配列
     * @throws IllegalArgumentException null を指定した場合
     */
    public MemoryByteList(byte[] array) {
        super(0);

        if (array == null) {
            throw new IllegalArgumentException(
                    "array is null.");
        }

        buf = array;
        setLength(buf.length);
    }

    @Override
    public long length() {
        return buf.length;
    }

    @Override
    public int get(long index) {
        return buf[(int)index];
    }

    @Override
    public int get(long index, byte[] dest, int offset, int length) {
        System.arraycopy(buf, (int)index, dest, offset, length);

        return length;
    }

    @Override
    public void set(long index, byte data) {
        buf[(int)index] = data;
    }

    @Override
    public int set(long index, byte[] src, int offset, int length) {
        System.arraycopy(src, offset, buf, (int)index, length);

        return length;
    }
}
