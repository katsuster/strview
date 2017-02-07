package net.katsuster.strview.media;

/**
 * <p>
 * パケットの位置。
 * </p>
 *
 * @author katsuhiro
 */
public class SimpleBlockPosition
        implements BlockPosition, Cloneable {
    //パケットの通し番号
    private long number;

    //パケットの位置
    private long address;

    //パケットの長さ
    private long length;

    /**
     * <p>
     * 番号 0、位置 0、長さ 0 のパケット位置を作成します。
     * </p>
     */
    public SimpleBlockPosition() {
        this(0, 0, 0);
    }

    /**
     * <p>
     * 位置 0、長さ 0 のパケット位置を作成します。
     * </p>
     *
     * @param num パケットの番号
     */
    public SimpleBlockPosition(long num) {
        this(num, 0, 0);
    }

    /**
     * <p>
     * 長さ 0 のパケット位置を作成します。
     * </p>
     *
     * @param num パケットの番号
     * @param addr パケットの位置
     */
    public SimpleBlockPosition(long num, long addr) {
        this(num, addr, 0);
    }

    /**
     * <p>
     * パケット位置を作成します。
     * </p>
     *
     * @param num パケットの番号
     * @param addr パケットの位置
     * @param len パケットのサイズ
     */
    public SimpleBlockPosition(long num, long addr, long len) {
        number = num;
        address = addr;
        length = len;
    }

    /**
     * <p>
     * パケット位置を作成します。
     * </p>
     *
     * @param obj パケット位置
     */
    public SimpleBlockPosition(BlockPosition obj) {
        this(obj.getNumber(), obj.getAddress(), obj.getLength());
    }

    @Override
    public SimpleBlockPosition clone()
            throws CloneNotSupportedException {
        SimpleBlockPosition obj = (SimpleBlockPosition)super.clone();

        return obj;
    }

    @Override
    public long getNumber() {
        return number;
    }

    @Override
    public void setNumber(long num) {
        number = num;
    }

    @Override
    public long getAddress() {
        return address;
    }

    @Override
    public void setAddress(long addr) {
        address = addr;
    }

    @Override
    public long getLength() {
        return length;
    }

    @Override
    public void setLength(long len) {
        length = len;
    }
}
