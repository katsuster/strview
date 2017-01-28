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
    public void writeBitList(int nbit, LargeBitList val, String name, String desc) {
        buf.set(pos, val, 0, nbit);
        pos += nbit;
    }

    @Override
    public LargeBitList convSubList(long nbit, LargeBitList val, String name, String desc) {
        val = buf.subLargeList(pos, pos + nbit);
        pos += nbit;

        return val;
    }

    @Override
    public LargeBitList getResult() {
        return buf;
    }
}
