package net.katsuster.strview.media;

import net.katsuster.strview.util.*;

/**
 * <p>
 * ビット列に書き込むコンバータクラスです。
 * </p>
 *
 * @author katsuhiro
 */
public class ToBitListConverter extends PacketWriterAdapter<LargeBitList> {
    private LargeBitList buf;
    private long pos;

    /**
     * <p>
     * 初期位置を指定してコンバータを作成します。
     * </p>
     *
     * @param b ビット列
     */
    public ToBitListConverter(LargeBitList b) {
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
    public ToBitListConverter(LargeBitList b, long p) {
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
    public void writeLong(int nbit, long val, String name, String desc) {
        buf.setPackedLong(pos, nbit, val);
        pos += nbit;
    }

    @Override
    public void writeSInt(int nbit, SInt val, String name, String desc) {
        buf.setPackedLong(pos, nbit, val.getBitsValue());
        pos += nbit;
    }

    @Override
    public void writeUInt(int nbit, UInt val, String name, String desc) {
        buf.setPackedLong(pos, nbit, val.getBitsValue());
        pos += nbit;
    }

    @Override
    public void writeSIntR(int nbit, SInt val, String name, String desc) {
        long rawval = val.getBitsValue();

        switch (nbit) {
        case 16:
            rawval = Short.reverseBytes((short)rawval);
            break;
        case 32:
            rawval = Integer.reverseBytes((int)rawval);
            break;
        case 64:
            rawval = Long.reverseBytes(rawval);
            break;
        default:
            throw new IllegalArgumentException(
                    "writeSIntR() not support " + nbit + "bits.");
        }

        buf.setPackedLong(pos, nbit, rawval);
        pos += nbit;
    }

    @Override
    public void writeUIntR(int nbit, UInt val, String name, String desc) {
        long rawval = val.getBitsValue();

        switch (nbit) {
        case 16:
            rawval = Short.reverseBytes((short)rawval);
            break;
        case 32:
            rawval = Integer.reverseBytes((int)rawval);
            break;
        case 64:
            rawval = Long.reverseBytes(rawval);
            break;
        default:
            throw new IllegalArgumentException(
                    "writeUIntR() not support " + nbit + "bits.");
        }

        buf.setPackedLong(pos, nbit, rawval);
        pos += nbit;
    }

    @Override
    public void writeFloat32(int nbit, Float32 val, String name, String desc) {
        buf.setPackedLong(pos, nbit, val.getBitsValue());
        pos += nbit;
    }

    @Override
    public void writeFloat64(int nbit, Float64 val, String name, String desc) {
        buf.setPackedLong(pos, nbit, val.getBitsValue());
        pos += nbit;
    }

    @Override
    public void writeBitList(int nbit, LargeBitList val, String name, String desc) {
        buf.set(pos, val, 0, nbit);
        pos += nbit;
    }

    @Override
    public void writeSubList(long nbit, LargeBitList val, String name, String desc) {
        pos += nbit;
    }

    @Override
    public LargeBitList getResult() {
        return buf;
    }
}
