package net.katsuster.strview.media.bit;

import net.katsuster.strview.util.*;
import net.katsuster.strview.util.bit.*;

/**
 * <p>
 * ビット列に書き込むコンバータクラスです。
 * </p>
 */
public class ToBitListConverter extends BitStreamWriterAdapter {
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
    public void writeSInt(int nbit, SInt val, String desc) {
        buf.setPackedLong(pos, nbit, val.getRaw());
        pos += nbit;
    }

    @Override
    public void writeUInt(int nbit, UInt val, String desc) {
        buf.setPackedLong(pos, nbit, val.getRaw());
        pos += nbit;
    }

    @Override
    public void writeSIntR(int nbit, SIntR val, String desc) {
        buf.setPackedLong(pos, nbit, val.getRaw());
        pos += nbit;
    }

    @Override
    public void writeUIntR(int nbit, UIntR val, String desc) {
        buf.setPackedLong(pos, nbit, val.getRaw());
        pos += nbit;
    }

    @Override
    public void writeFloat32(int nbit, Float32 val, String desc) {
        buf.setPackedLong(pos, nbit, val.getRaw());
        pos += nbit;
    }

    @Override
    public void writeFloat64(int nbit, Float64 val, String desc) {
        buf.setPackedLong(pos, nbit, val.getRaw());
        pos += nbit;
    }

    @Override
    public void writeBitList(long nbit, LargeBitList val, String desc) {
        //buf.set(pos, val, 0, nbit);
        pos += nbit;
    }
}
