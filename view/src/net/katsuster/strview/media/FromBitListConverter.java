package net.katsuster.strview.media;

import net.katsuster.strview.io.LargeBitList;
import net.katsuster.strview.util.SInt;
import net.katsuster.strview.util.UInt;

/**
 * <p>
 * ビット列から読み込むコンバータクラスです。
 * </p>
 *
 * @author katsuhiro
 */
public class FromBitListConverter extends PacketConverterAdapter<LargeBitList> {
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
    public SInt convSInt(int nbit, SInt val, String name, String desc) {
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
    public UInt convUInt(int nbit, UInt val, String name, String desc) {
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
    public LargeBitList convBitList(long nbit, LargeBitList val, String name, String desc) {
        val = buf.subLargeList(pos, pos + nbit);
        pos += nbit;

        return val;
    }

    @Override
    public LargeBitList getResult() {
        return buf;
    }
}
