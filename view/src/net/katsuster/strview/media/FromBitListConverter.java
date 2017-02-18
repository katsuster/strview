package net.katsuster.strview.media;

import net.katsuster.strview.io.*;
import net.katsuster.strview.util.*;

/**
 * <p>
 * ビット列から読み込むコンバータクラスです。
 * </p>
 *
 * @author katsuhiro
 */
public class FromBitListConverter extends PacketReaderAdapter<LargeBitList> {
    private LargeBitList buf;
    private long pos;

    /**
     * <p>
     * 初期位置 0 のコンバータを作成します。
     * </p>
     *
     * @param b ビット列
     */
    public FromBitListConverter(LargeBitList b) {
        this(b, 0);
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
        buf = b;
        pos = p;
    }

    @Override
    public long position() {
        return pos;
    }

    @Override
    public void position(long p) {
        pos = p;
    }

    @Override
    public boolean hasNext(long n) {
        if (buf.length() == LargeList.LENGTH_UNKNOWN) {
            return true;
        }
        return pos + n <= buf.length();
    }

    @Override
    public long readLong(int nbit, String desc) {
        long res = buf.getPackedLong(pos, nbit);
        pos += nbit;

        return res;
    }

    @Override
    public SInt readSInt(int nbit, SInt val, String desc) {
        if (val == null) {
            val = new SInt();
        }

        val.getRange().setStart(pos);
        val.getRange().setLength(nbit);
        val.setBitsValue(buf.getPackedLong(pos, nbit));
        pos += nbit;

        return val;
    }

    @Override
    public UInt readUInt(int nbit, UInt val, String desc) {
        if (val == null) {
            val = new UInt();
        }

        val.getRange().setStart(pos);
        val.getRange().setLength(nbit);
        val.setBitsValue(buf.getPackedLong(pos, nbit));
        pos += nbit;

        return val;
    }

    @Override
    public SInt readSIntR(int nbit, SInt val, String desc) {
        long rawval;

        if (val == null) {
            val = new SInt();
        }

        switch (nbit) {
        case 16:
            rawval = Short.reverseBytes((short)buf.getPackedLong(pos, nbit));
            break;
        case 32:
            rawval = Integer.reverseBytes((int)buf.getPackedLong(pos, nbit));
            break;
        case 64:
            rawval = Long.reverseBytes(buf.getPackedLong(pos, nbit));
            break;
        default:
            throw new IllegalArgumentException(
                    "readSIntR() not support " + nbit + "bits.");
        }

        val.getRange().setStart(pos);
        val.getRange().setLength(nbit);
        val.setBitsValue(rawval);
        pos += nbit;

        return val;
    }

    @Override
    public UInt readUIntR(int nbit, UInt val, String desc) {
        long rawval;

        if (val == null) {
            val = new UInt();
        }

        switch (nbit) {
        case 16:
            rawval = Short.reverseBytes((short)buf.getPackedLong(pos, nbit)) & 0xffffL;
            break;
        case 32:
            rawval = Integer.reverseBytes((int)buf.getPackedLong(pos, nbit)) & 0xffffffffL;
            break;
        case 64:
            rawval = Long.reverseBytes(buf.getPackedLong(pos, nbit));
            break;
        default:
            throw new IllegalArgumentException(
                    "readUIntR() not support " + nbit + "bits.");
        }

        val.getRange().setStart(pos);
        val.getRange().setLength(nbit);
        val.setBitsValue(rawval);
        pos += nbit;

        return val;
    }

    @Override
    public Float32 readFloat32(int nbit, Float32 val, String desc) {
        if (val == null) {
            val = new Float32();
        }

        val.getRange().setStart(pos);
        val.getRange().setLength(nbit);
        val.setBitsValue(buf.getPackedInt(pos, nbit));
        pos += nbit;

        return val;
    }

    @Override
    public Float64 readFloat64(int nbit, Float64 val, String desc) {
        if (val == null) {
            val = new Float64();
        }

        val.getRange().setStart(pos);
        val.getRange().setLength(nbit);
        val.setBitsValue(buf.getPackedLong(pos, nbit));
        pos += nbit;

        return val;
    }

    @Override
    public LargeBitList readBitList(int nbit, LargeBitList val, String desc) {
        if (val == null || val.length() < nbit) {
            val = new MemoryBitList(nbit);
        }
        val.setOffsetHint(pos);
        buf.get(pos, val, 0, nbit);
        pos += nbit;

        return val;
    }

    @Override
    public LargeBitList readSubList(long nbit, LargeBitList val, String desc) {
        val = buf.subLargeList(pos, nbit);
        pos += nbit;

        return val;
    }

    @Override
    public LargeBitList getResult() {
        return buf;
    }
}
