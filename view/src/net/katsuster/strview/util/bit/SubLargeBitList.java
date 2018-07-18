package net.katsuster.strview.util.bit;

import net.katsuster.strview.util.*;

/**
 * <p>
 * int 型で扱える長さを超えるビット列の部分列を表します。
 * </p>
 */
public class SubLargeBitList extends AbstractLargeListBase<Boolean>
        implements LargeBitList, Cloneable {
    public SubLargeBitList() {
        this(null, null, 0, 0);
    }

    /**
     * <p>
     * 指定された名前を持った部分列を作成します。
     * </p>
     *
     * @param n    名前
     */
    public SubLargeBitList(String n) {
        this(n, null, 0, 0);
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
    public SubLargeBitList(LargeList<Boolean> bits, long from, long len) {
        this(null, bits, from, len);
    }

    /**
     * <p>
     * 指定されたビット列の
     * from から len の長さの部分列を作成します。
     * </p>
     *
     * @param n    名前
     * @param bits ビット列
     * @param from 部分列の開始点
     * @param len  部分列の長さ
     */
    public SubLargeBitList(String n, LargeList<Boolean> bits, long from, long len) {
        super(n, len);

        long bitsLen = 0;
        if (bits != null) {
            bitsLen = bits.length();
        }

        if (from < 0 || from + len > bitsLen) {
            throw new IndexOutOfBoundsException("from:" + from
                    + ", len:" + len + " is too large.");
        }

        getRange().setBuffer(bits);
        getRange().setStart(from);
    }

    /**
     * <p>
     * 指定されたビット列の範囲から部分列を作成します。
     * </p>
     *
     * @param r ビット列の存在する範囲
     */
    public SubLargeBitList(Range<LargeList<Boolean>> r) {
        this(r.getBuffer(), r.getStart(), r.getLength());
    }

    @Override
    public Object clone()
            throws CloneNotSupportedException {
        SubLargeBitList obj = (SubLargeBitList)super.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "SubBits";
    }

    /*@Override
    public Range<LargeBitList> getRange() {
        return (Range<LargeBitList>)super.getRange();
    }*/

    @Override
    public Boolean get(long index) {
        Range<LargeList<Boolean>> r = getRange();

        checkRemaining(index, 1);

        return r.getBuffer().get(index + r.getStart());
    }

    @Override
    public int get(long index, Boolean[] dest, int offset, int length) {
        Range<LargeList<Boolean>> r = getRange();

        checkRemaining(index, length);

        return r.getBuffer().get(index + r.getStart(), dest, offset, length);
    }

    @Override
    public int get(long index, LargeList<Boolean> dest, int offset, int length) {
        Range<LargeList<Boolean>> r = getRange();

        checkRemaining(index, length);

        return r.getBuffer().get(index + r.getStart(), dest, offset, length);
    }

    @Override
    public void set(long index, Boolean data) {
        Range<LargeList<Boolean>> r = getRange();

        checkRemaining(index, 1);

        r.getBuffer().set(index + r.getStart(), data);
    }

    @Override
    public int set(long index, Boolean[] src, int offset, int length) {
        Range<LargeList<Boolean>> r = getRange();

        checkRemaining(index, length);

        return r.getBuffer().set(index + r.getStart(), src, offset, length);
    }

    @Override
    public int set(long index, LargeList<Boolean> src, int offset, int length) {
        Range<LargeList<Boolean>> r = getRange();

        checkRemaining(index, 1);

        return r.getBuffer().set(index + r.getStart(), src, offset, length);
    }

    @Override
    public int get(long index, boolean[] dest, int offset, int length) {
        Range<LargeList<Boolean>> r = getRange();
        LargeBitList b = (LargeBitList)r.getBuffer();

        checkRemaining(index, length);

        return b.get(index + r.getStart(), dest, offset, length);
    }

    @Override
    public int set(long index, boolean[] src, int offset, int length) {
        Range<LargeList<Boolean>> r = getRange();
        LargeBitList b = (LargeBitList)r.getBuffer();

        checkRemaining(index, 1);

        return b.set(index + r.getStart(), src, offset, length);
    }

    @Override
    public long getPackedLong(long index, int n) {
        Range<LargeList<Boolean>> r = getRange();
        LargeBitList b = (LargeBitList)r.getBuffer();

        checkRemaining(index, n);

        return b.getPackedLong(index + r.getStart(), n);
    }

    @Override
    public void getPackedByteArray(long index, byte[] dst, int off, int n) {
        Range<LargeList<Boolean>> r = getRange();
        LargeBitList b = (LargeBitList)r.getBuffer();

        checkRemaining(index, n);

        b.getPackedByteArray(index + r.getStart(), dst, off, n);
    }

    public static long getPackedLong(Range<LargeList<Boolean>> r) {
        LargeBitList b = (LargeBitList)r.getBuffer();

        return b.getPackedLong(r.getStart(), (int) r.getLength());
    }

    public static void getPackedByteArray(Range<LargeList<Boolean>> r, byte[] dst, int off) {
        LargeBitList b = (LargeBitList)r.getBuffer();

        b.getPackedByteArray(r.getStart(), dst, off, (int) r.getLength());
    }

    @Override
    public void setPackedLong(long index, int n, long val) {
        Range<LargeList<Boolean>> r = getRange();
        LargeBitList b = (LargeBitList)r.getBuffer();

        checkRemaining(index, n);

        b.setPackedLong(index + r.getStart(), n, val);
    }

    @Override
    public void setPackedByteArray(long index, byte[] src, int off, int n) {
        Range<LargeList<Boolean>> r = getRange();
        LargeBitList b = (LargeBitList)r.getBuffer();

        checkRemaining(index, n);

        b.setPackedByteArray(index + r.getStart(), src, off, n);
    }

    public static void setPackedLong(Range<LargeList<Boolean>> r, long val) {
        LargeBitList b = (LargeBitList)r.getBuffer();

        b.setPackedLong(r.getStart(), (int) r.getLength(), val);
    }

    public static void setPackedByteArray(Range<LargeList<Boolean>> r, byte[] src, int off) {
        LargeBitList b = (LargeBitList)r.getBuffer();

        b.setPackedByteArray(r.getStart(), src, off, (int) r.getLength());
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
}
