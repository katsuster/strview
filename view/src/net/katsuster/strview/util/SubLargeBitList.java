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
    //ビット列の存在する範囲
    private Range range;

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
        super(len);

        if (from < 0 || from + len > bits.length()) {
            throw new IndexOutOfBoundsException("from:" + from
                    + ", len:" + len + " is too large.");
        }

        range = new SimpleRange(bits, from, len);
    }

    /**
     * <p>
     * 指定されたビット列の範囲から部分列を作成します。
     * </p>
     *
     * @param r ビット列の存在する範囲
     */
    public SubLargeBitList(Range r) {
        this(r.getBuffer(), r.getStart(), r.getLength());
    }

    @Override
    public Object clone()
            throws CloneNotSupportedException {
        SubLargeBitList obj = (SubLargeBitList)super.clone();

        obj.range = (Range)range.clone();

        return obj;
    }

    @Override
    public Boolean get(long index) {
        Range r = getRange();

        checkRemaining(index, 1);

        return r.getBuffer().get(index + r.getStart());
    }

    @Override
    public int get(long index, LargeList<Boolean> dest, int offset, int length) {
        Range r = getRange();

        checkRemaining(index, length);

        return r.getBuffer().get(index + r.getStart(), dest, offset, length);
    }

    @Override
    public int get(long index, boolean[] dest, int offset, int length) {
        Range r = getRange();

        checkRemaining(index, length);

        return r.getBuffer().get(index + r.getStart(), dest, offset, length);
    }

    @Override
    public int set(long index, boolean[] src, int offset, int length) {
        Range r = getRange();

        checkRemaining(index, length);

        return r.getBuffer().set(index + r.getStart(), src, offset, length);
    }

    @Override
    public void set(long index, Boolean data) {
        Range r = getRange();

        checkRemaining(index, 1);

        r.getBuffer().set(index + r.getStart(), data);
    }

    @Override
    public int set(long index, LargeList<Boolean> src, int offset, int length) {
        Range r = getRange();

        checkRemaining(index, 1);

        return r.getBuffer().set(index + r.getStart(), src, offset, length);
    }

    @Override
    public long length() {
        return getRange().getLength();
    }

    @Override
    public void length(long l) {
        getRange().setLength(l);
    }

    @Override
    public LargeBitList subLargeList(long from, long len) {
        return new SubLargeBitList(this, from, len);
    }

    @Override
    public Range getRange() {
        return range;
    }

    @Override
    public void setRange(Range r) {
        range = r;
    }

    @Override
    public byte getPackedByte(long index, int n) {
        Range r = getRange();

        checkRemaining(index, n);

        return r.getBuffer().getPackedByte(index + r.getStart(), n);
    }

    @Override
    public short getPackedShort(long index, int n) {
        Range r = getRange();

        checkRemaining(index, n);

        return r.getBuffer().getPackedShort(index + r.getStart(), n);
    }

    @Override
    public int getPackedInt(long index, int n) {
        Range r = getRange();

        checkRemaining(index, n);

        return r.getBuffer().getPackedInt(index + r.getStart(), n);
    }

    @Override
    public long getPackedLong(long index, int n) {
        Range r = getRange();

        checkRemaining(index, n);

        return r.getBuffer().getPackedLong(index + r.getStart(), n);
    }

    @Override
    public void getPackedByteArray(long index, byte[] dst, int off, int n) {
        Range r = getRange();

        checkRemaining(index, n);

        r.getBuffer().getPackedByteArray(index + r.getStart(), dst, off, n);
    }

    @Override
    public void setPackedByte(long index, int n, byte val) {
        Range r = getRange();

        checkRemaining(index, n);

        r.getBuffer().setPackedByte(index + r.getStart(), n, val);
    }

    @Override
    public void setPackedShort(long index, int n, short val) {
        Range r = getRange();

        checkRemaining(index, n);

        r.getBuffer().setPackedShort(index + r.getStart(), n, val);
    }

    @Override
    public void setPackedInt(long index, int n, int val) {
        Range r = getRange();

        checkRemaining(index, n);

        r.getBuffer().setPackedInt(index + r.getStart(), n, val);
    }

    @Override
    public void setPackedLong(long index, int n, long val) {
        Range r = getRange();

        checkRemaining(index, n);

        r.getBuffer().setPackedLong(index + r.getStart(), n, val);
    }

    @Override
    public void setPackedByteArray(long index, byte[] src, int off, int n) {
        Range r = getRange();

        checkRemaining(index, n);

        r.getBuffer().setPackedByteArray(index + r.getStart(), src, off, n);
    }

    @Override
    public ExtraInfo getExtraInfo(long index) {
        Range r = getRange();

        return r.getBuffer().getExtraInfo(index + r.getStart());
    }

    @Override
    public void setExtraInfo(long index, ExtraInfo info) {
        Range r = getRange();

        r.getBuffer().setExtraInfo(index + r.getStart(), info);
    }
}
