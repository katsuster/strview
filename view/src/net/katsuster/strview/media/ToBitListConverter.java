package net.katsuster.strview.media;

import net.katsuster.strview.io.LargeBitList;
import net.katsuster.strview.util.SInt;
import net.katsuster.strview.util.UInt;

/**
 * <p>
 * ビット列に書き込むコンバータクラスです。
 * </p>
 *
 * @author katsuhiro
 */
public class ToBitListConverter extends PacketConverterAdapter<LargeBitList> {
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
    public SInt convSInt(int nbit, SInt val, String name, String desc) {
        buf.setPackedLong(pos, nbit, val.getBitsValue());
        pos += nbit;

        return val;
    }

    @Override
    public UInt convUInt(int nbit, UInt val, String name, String desc) {
        buf.setPackedLong(pos, nbit, val.getBitsValue());
        pos += nbit;

        return val;
    }

    @Override
    public LargeBitList convBitList(long nbit, LargeBitList val, String name, String desc) {
        buf.set(pos, val, 0, (int)nbit);
        pos += nbit;

        return val;
    }

    @Override
    public LargeBitList getResult() {
        return buf;
    }
}
