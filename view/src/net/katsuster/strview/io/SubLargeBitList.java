package net.katsuster.strview.io;

/**
 * <p>
 * int 型で扱える長さを超えるビット列の部分列を表します。
 * </p>
 *
 * @author katsuhiro
 */
public class SubLargeBitList extends AbstractLargeBitListBase
        implements LargeBitList, Cloneable {
    //元となるビット列
    private LargeBitList buf;
    //部分列の開始位置（ビット単位）
    private long offset;

    /**
     * <p>
     * 指定されたビット列の
     * from から to まで（to は含まない）の部分列を作成します。
     * </p>
     *
     * @param bits ビット列
     * @param from 部分列の開始点
     * @param to   部分列の終了点
     */
    public SubLargeBitList(LargeBitList bits, long from, long to) {
        super(from, to);

        if (to > bits.length()) {
            throw new IndexOutOfBoundsException("to:" + to
                    + " is too large.");
        }

        buf = bits;
        offset = from;
    }

    @Override
    public SubLargeBitList clone()
            throws CloneNotSupportedException {
        SubLargeBitList obj = (SubLargeBitList)super.clone();

        obj.buf = buf;
        obj.offset = offset;

        return obj;
    }

    @Override
    public LargeBitList subArray(long from, long to) {
        return new SubLargeBitList(this, from, to);
    }

    @Override
    public boolean get(long index) {
        checkIndex(index);

        return buf.get(index + offset);
    }

    @Override
    public int get(long index, boolean[] dest, int offset, int length) {
        checkRemaining(index, length);

        return buf.get(index + offset, dest, offset, length);
    }

    @Override
    public int get(long index, LargeBitList dest, int offset, int length) {
        checkRemaining(index, length);

        return buf.get(index + offset, dest, offset, length);
    }

    @Override
    public void set(long index, boolean data) {
        checkIndex(index);

        buf.set(index + offset, data);
    }

    @Override
    public int set(long index, boolean[] src, int offset, int length) {
        checkRemaining(index, length);

        return buf.set(index + offset, src, offset, length);
    }

    @Override
    public int set(long index, LargeBitList src, int offset, int length) {
        checkRemaining(index, length);

        return buf.set(index + offset, src, offset, length);
    }

    @Override
    public byte getPackedByte(long index, int n) {
        checkRemaining(index, n);

        return buf.getPackedByte(index + offset, n);
    }

    @Override
    public short getPackedShort(long index, int n) {
        checkRemaining(index, n);

        return buf.getPackedShort(index + offset, n);
    }

    @Override
    public int getPackedInt(long index, int n) {
        checkRemaining(index, n);

        return buf.getPackedInt(index + offset, n);
    }

    @Override
    public long getPackedLong(long index, int n) {
        checkRemaining(index, n);

        return buf.getPackedLong(index + offset, n);
    }

    @Override
    public void getPackedByteArray(long index, byte[] dst, int off, int n) {
        checkRemaining(index, n);

        buf.getPackedByteArray(index + offset, dst, off, n);
    }

    @Override
    public void setPackedByte(long index, int n, byte val) {
        checkRemaining(index, n);

        buf.setPackedByte(index + offset, n, val);
    }

    @Override
    public void setPackedShort(long index, int n, short val) {
        checkRemaining(index, n);

        buf.setPackedShort(index + offset, n, val);
    }

    @Override
    public void setPackedInt(long index, int n, int val) {
        checkRemaining(index, n);

        buf.setPackedInt(index + offset, n, val);
    }

    @Override
    public void setPackedLong(long index, int n, long val) {
        checkRemaining(index, n);

        buf.setPackedLong(index + offset, n, val);
    }

    @Override
    public void setPackedByteArray(long index, byte[] src, int off, int n) {
        checkRemaining(index, n);

        buf.setPackedByteArray(index + offset, src, off, n);
    }
}
