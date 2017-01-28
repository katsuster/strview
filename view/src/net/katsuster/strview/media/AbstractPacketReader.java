package net.katsuster.strview.media;

import net.katsuster.strview.util.*;

/**
 * <p>
 * 何もしないコンバータクラスです。
 * </p>
 *
 * <p>
 * 下記の特徴を持ちます。
 * </p>
 *
 * <ul>
 *     <li>位置は常に 0</li>
 *     <li>マークは全て無視する</li>
 *     <li>変換対象は全て無視する</li>
 *     <li>結果は常に null を返す</li>
 * </ul>
 *
 * @author katsuhiro
 */
public abstract class AbstractPacketReader<T> extends AbstractPacketConverter<T>
        implements PacketReader<T> {
    public AbstractPacketReader() {
        //do nothing
    }

    @Override
    public long peekLong(int nbit) {
        return peekLong(nbit, null);
    }

    @Override
    public long peekLong(int nbit, String desc) {
        long orgpos = position();
        long res = readLong(nbit, desc);
        position(orgpos);
        return res;
    }

    @Override
    public long readLong(int nbit) {
        return readLong(nbit, null);
    }

    @Override
    public SInt peekSInt(int nbit, SInt val) {
        return peekSInt(nbit, val, null);
    }

    @Override
    public SInt peekSInt(int nbit, SInt val, String desc) {
        long orgpos = position();
        SInt res = readSInt(nbit, val, desc);
        position(orgpos);
        return res;
    }

    @Override
    public SInt readSInt(int nbit, SInt val) {
        return readSInt(nbit, val, null);
    }

    @Override
    public UInt peekUInt(int nbit, UInt val) {
        return peekUInt(nbit, val, null);
    }

    @Override
    public UInt peekUInt(int nbit, UInt val, String desc) {
        long orgpos = position();
        UInt res = readUInt(nbit, val, desc);
        position(orgpos);
        return res;
    }

    @Override
    public UInt readUInt(int nbit, UInt val) {
        return readUInt(nbit, val, null);
    }

    @Override
    public LargeBitList peekBitList(int nbit, LargeBitList val) {
        return peekBitList(nbit, val, null);
    }

    @Override
    public LargeBitList peekBitList(int nbit, LargeBitList val, String desc) {
        long orgpos = position();
        LargeBitList res = readBitList(nbit, val, desc);
        position(orgpos);
        return res;
    }

    @Override
    public LargeBitList readBitList(int nbit, LargeBitList val) {
        return readBitList(nbit, val, null);
    }
}
