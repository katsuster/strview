package net.katsuster.strview.util;

/**
 * <p>
 * int 型で扱える長さを超えるビット列の部分列を表します。
 * </p>
 *
 * @author katsuhiro
 */
public class SubLargeBitList extends AbstractLargeListBase<Boolean>
        implements LargeBitList, Cloneable {
    //元となるリスト
    private LargeBitList list;
    //部分列の開始位置
    private long offsetList;

    /**
     * <p>
     * 指定されたビット列の
     * from から len の長さの部分列を作成します。
     * </p>
     *
     * @param bits ビット列
     * @param from 部分列の開始点
     * @param len  部分列の長さ
     */
    public SubLargeBitList(LargeBitList bits, long from, long len) {
        super(from, len);

        if (from + len > bits.length()) {
            throw new IndexOutOfBoundsException("from:" + from
                    + ", len:" + len + " is too large.");
        }

        list = bits;
        offsetList = from;
    }

    @Override
    public Object clone()
            throws CloneNotSupportedException {
        SubLargeBitList obj = (SubLargeBitList)super.clone();

        obj.list = (LargeBitList)list.clone();

        return obj;
    }

    @Override
    public Boolean get(long index) {
        checkRemaining(index, 1);

        return list.get(index + offsetList);
    }

    @Override
    public int get(long index, LargeList<Boolean> dest, int offset, int length) {
        checkRemaining(index, length);

        return list.get(index + offsetList, dest, offset, length);
    }

    @Override
    public int get(long index, boolean[] dest, int offset, int length) {
        return list.get(index + offsetList, dest, offset, length);
    }

    @Override
    public int set(long index, boolean[] src, int offset, int length) {
        return list.set(index + offsetList, src, offset, length);
    }

    @Override
    public void set(long index, Boolean data) {
        list.set(index + offsetList, data);
    }

    @Override
    public int set(long index, LargeList<Boolean> src, int offset, int length) {
        return set(index + offsetList, src, offset, length);
    }

    @Override
    public LargeBitList subLargeList(long from, long len) {
        return new SubLargeBitList(this, from, len);
    }

    @Override
    public byte getPackedByte(long index, int n) {
        checkRemaining(index, n);

        return list.getPackedByte(index + offsetList, n);
    }

    @Override
    public short getPackedShort(long index, int n) {
        checkRemaining(index, n);

        return list.getPackedShort(index + offsetList, n);
    }

    @Override
    public int getPackedInt(long index, int n) {
        checkRemaining(index, n);

        return list.getPackedInt(index + offsetList, n);
    }

    @Override
    public long getPackedLong(long index, int n) {
        checkRemaining(index, n);

        return list.getPackedLong(index + offsetList, n);
    }

    @Override
    public void getPackedByteArray(long index, byte[] dst, int off, int n) {
        checkRemaining(index, n);

        list.getPackedByteArray(index + offsetList, dst, off, n);
    }

    @Override
    public void setPackedByte(long index, int n, byte val) {
        checkRemaining(index, n);

        list.setPackedByte(index + offsetList, n, val);
    }

    @Override
    public void setPackedShort(long index, int n, short val) {
        checkRemaining(index, n);

        list.setPackedShort(index + offsetList, n, val);
    }

    @Override
    public void setPackedInt(long index, int n, int val) {
        checkRemaining(index, n);

        list.setPackedInt(index + offsetList, n, val);
    }

    @Override
    public void setPackedLong(long index, int n, long val) {
        checkRemaining(index, n);

        list.setPackedLong(index + offsetList, n, val);
    }

    @Override
    public void setPackedByteArray(long index, byte[] src, int off, int n) {
        checkRemaining(index, n);

        list.setPackedByteArray(index + offsetList, src, off, n);
    }

    @Override
    public ExtraInfo getExtraInfo(long index) {
        return list.getExtraInfo(index + offsetList);
    }

    @Override
    public void setExtraInfo(long index, ExtraInfo info) {
        list.setExtraInfo(index + offsetList, info);
    }
}
