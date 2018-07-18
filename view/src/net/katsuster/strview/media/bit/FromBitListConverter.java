package net.katsuster.strview.media.bit;

import net.katsuster.strview.util.*;
import net.katsuster.strview.util.bit.*;

/**
 * <p>
 * ビット列から読み込むコンバータクラスです。
 * </p>
 */
public class FromBitListConverter extends BitStreamReaderAdapter {
    /**
     * <p>
     * 初期位置 0 のコンバータを作成します。
     * </p>
     *
     * @param b ビット列
     */
    public FromBitListConverter(LargeBitList b) {
        super(b, 0);
    }

    /**
     * <p>
     * 初期位置を指定してコンバータを作成します。
     * </p>
     *
     * @param b ビット列
     * @param p 初期位置
     */
    public FromBitListConverter(LargeBitList b, long p) {
        super(b, p);
    }

    @Override
    public boolean hasNext(long n) {
        if (getList().length() == LargeList.LENGTH_UNKNOWN) {
            return true;
        }
        return position() + n <= getList().length();
    }

    @Override
    public long readLong(int nbit, String desc) {
        LargeBitList buf = (LargeBitList)getList();
        long res = buf.getPackedLong(position(), nbit);
        position(position() + nbit);

        return res;
    }

    @Override
    public SInt readSInt(int nbit, SInt val, String desc) {
        if (val == null) {
            val = new SInt();
        }

        val.getRange().setBuffer(getList());
        val.getRange().setStart(position());
        val.length(nbit);
        position(position() + nbit);

        return val;
    }

    @Override
    public UInt readUInt(int nbit, UInt val, String desc) {
        if (val == null) {
            val = new UInt();
        }

        val.getRange().setBuffer(getList());
        val.getRange().setStart(position());
        val.length(nbit);
        position(position() + nbit);

        return val;
    }

    @Override
    public SIntR readSIntR(int nbit, SIntR val, String desc) {
        if (val == null) {
            val = new SIntR();
        }

        val.getRange().setBuffer(getList());
        val.getRange().setStart(position());
        val.length(nbit);
        position(position() + nbit);

        return val;
    }

    @Override
    public UIntR readUIntR(int nbit, UIntR val, String desc) {
        if (val == null) {
            val = new UIntR();
        }

        val.getRange().setBuffer(getList());
        val.getRange().setStart(position());
        val.length(nbit);
        position(position() + nbit);

        return val;
    }

    @Override
    public Float32 readFloat32(int nbit, Float32 val, String desc) {
        if (val == null) {
            val = new Float32();
        }

        val.getRange().setBuffer(getList());
        val.getRange().setStart(position());
        val.length(nbit);
        position(position() + nbit);

        return val;
    }

    @Override
    public Float64 readFloat64(int nbit, Float64 val, String desc) {
        if (val == null) {
            val = new Float64();
        }

        val.getRange().setBuffer(getList());
        val.getRange().setStart(position());
        val.length(nbit);
        position(position() + nbit);

        return val;
    }

    @Override
    public LargeBitList readBitList(long nbit, LargeBitList val, String desc) {
        val = new SubLargeBitList(val.getName());

        val.getRange().setBuffer(getList());
        val.getRange().setStart(position());
        val.length(nbit);
        position(position() + nbit);

        return val;
    }
}
