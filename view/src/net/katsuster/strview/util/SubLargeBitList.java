package net.katsuster.strview.util;

/**
 * <p>
 * int 型で扱える長さを超えるビット列の部分列を表します。
 * </p>
 */
public class SubLargeBitList extends AbstractLargeListBase<Boolean>
        implements LargeBitList, Cloneable {
    //ビット列の存在する範囲
    private Range<LargeBitList> range;

    public SubLargeBitList() {
        this(null, 0, 0);
    }

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

        long bitsLen = 0;
        if (bits != null) {
            bitsLen = bits.length();
        }

        if (from < 0 || from + len > bitsLen) {
            throw new IndexOutOfBoundsException("from:" + from
                    + ", len:" + len + " is too large.");
        }

        range = new SimpleRange<>(bits, from, len);
    }

    /**
     * <p>
     * 指定されたビット列の範囲から部分列を作成します。
     * </p>
     *
     * @param r ビット列の存在する範囲
     */
    public SubLargeBitList(Range<LargeBitList> r) {
        this(r.getBuffer(), r.getStart(), r.getLength());
    }

    @Override
    public Object clone()
            throws CloneNotSupportedException {
        SubLargeBitList obj = (SubLargeBitList)super.clone();

        obj.range = (Range<LargeBitList>)range.clone();

        return obj;
    }

    @Override
    public Boolean get(long index) {
        Range<LargeBitList> r = getRange();

        checkRemaining(index, 1);

        return r.getBuffer().get(index + r.getStart());
    }

    @Override
    public int get(long index, LargeList<Boolean> dest, int offset, int length) {
        Range<LargeBitList> r = getRange();

        checkRemaining(index, length);

        return r.getBuffer().get(index + r.getStart(), dest, offset, length);
    }

    @Override
    public int get(long index, boolean[] dest, int offset, int length) {
        Range<LargeBitList> r = getRange();

        checkRemaining(index, length);

        return r.getBuffer().get(index + r.getStart(), dest, offset, length);
    }

    @Override
    public int set(long index, boolean[] src, int offset, int length) {
        Range<LargeBitList> r = getRange();

        checkRemaining(index, length);

        return r.getBuffer().set(index + r.getStart(), src, offset, length);
    }

    @Override
    public void set(long index, Boolean data) {
        Range<LargeBitList> r = getRange();

        checkRemaining(index, 1);

        r.getBuffer().set(index + r.getStart(), data);
    }

    @Override
    public int set(long index, LargeList<Boolean> src, int offset, int length) {
        Range<LargeBitList> r = getRange();

        checkRemaining(index, 1);

        return r.getBuffer().set(index + r.getStart(), src, offset, length);
    }

    @Override
    public long getPackedLong(long index, int n) {
        Range<LargeBitList> r = getRange();

        checkRemaining(index, n);

        return r.getBuffer().getPackedLong(index + r.getStart(), n);
    }

    @Override
    public void getPackedByteArray(long index, byte[] dst, int off, int n) {
        Range<LargeBitList> r = getRange();

        checkRemaining(index, n);

        r.getBuffer().getPackedByteArray(index + r.getStart(), dst, off, n);
    }

    public static long getPackedLong(Range<LargeBitList> r) {
        return r.getBuffer().getPackedLong(r.getStart(), (int) r.getLength());
    }

    public static void getPackedByteArray(Range<LargeBitList> r, byte[] dst, int off) {
        r.getBuffer().getPackedByteArray(r.getStart(), dst, off, (int) r.getLength());
    }

    @Override
    public void setPackedLong(long index, int n, long val) {
        Range<LargeBitList> r = getRange();

        checkRemaining(index, n);

        r.getBuffer().setPackedLong(index + r.getStart(), n, val);
    }

    @Override
    public void setPackedByteArray(long index, byte[] src, int off, int n) {
        Range<LargeBitList> r = getRange();

        checkRemaining(index, n);

        r.getBuffer().setPackedByteArray(index + r.getStart(), src, off, n);
    }

    public static void setPackedLong(Range<LargeBitList> r, long val) {
        r.getBuffer().setPackedLong(r.getStart(), (int) r.getLength(), val);
    }

    public static void setPackedByteArray(Range<LargeBitList> r, byte[] src, int off) {
        r.getBuffer().setPackedByteArray(r.getStart(), src, off, (int) r.getLength());
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
    public LargeBitList getSourceBuffer() {
        return getRange().getBuffer();
    }

    @Override
    public void setSourceBuffer(LargeBitList buf) {
        getRange().setBuffer(buf);
    }

    @Override
    public long getSourceStart() {
        return getRange().getStart();
    }

    @Override
    public void setSourceStart(long index) {
        getRange().setStart(index);
    }

    @Override
    public long getSourceEnd() {
        return getRange().getEnd();
    }

    @Override
    public void setSourceEnd(long index) {
        getRange().setEnd(index);
    }

    protected Range<LargeBitList> getRange() {
        return range;
    }

    protected void setRange(Range<LargeBitList> r) {
        range = r;
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
