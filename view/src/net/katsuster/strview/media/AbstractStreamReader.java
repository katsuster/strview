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
public abstract class AbstractStreamReader<T> extends AbstractStreamConverter<T>
        implements StreamReader<T> {
    public AbstractStreamReader() {
        //do nothing
    }

    @Override
    public long peekLong(int nbit) {
        long orgpos = position();
        long res = readLong(nbit, null);
        position(orgpos);
        return res;
    }

    @Override
    public long readLong(int nbit) {
        return readLong(nbit, null);
    }

    @Override
    public SInt peekSInt(int nbit, SInt val) {
        long orgpos = position();
        SInt res = readSInt(nbit, val, null);
        position(orgpos);
        return res;
    }

    @Override
    public SInt readSInt(int nbit, SInt val) {
        return readSInt(nbit, val, null);
    }

    @Override
    public UInt peekUInt(int nbit, UInt val) {
        long orgpos = position();
        UInt res = readUInt(nbit, val, null);
        position(orgpos);
        return res;
    }

    @Override
    public UInt readUInt(int nbit, UInt val) {
        return readUInt(nbit, val, null);
    }

    @Override
    public SIntR peekSIntR(int nbit, SIntR val) {
        long orgpos = position();
        SIntR res = readSIntR(nbit, val, null);
        position(orgpos);
        return res;
    }

    @Override
    public SIntR readSIntR(int nbit, SIntR val) {
        return readSIntR(nbit, val, null);
    }

    @Override
    public UIntR peekUIntR(int nbit, UIntR val) {
        long orgpos = position();
        UIntR res = readUIntR(nbit, val, null);
        position(orgpos);
        return res;
    }

    @Override
    public UIntR readUIntR(int nbit, UIntR val) {
        return readUIntR(nbit, val, null);
    }

    @Override
    public Float32 peekFloat32(int nbit, Float32 val) {
        long orgpos = position();
        Float32 res = readFloat32(nbit, val, null);
        position(orgpos);
        return res;
    }

    @Override
    public Float32 readFloat32(int nbit, Float32 val) {
        return readFloat32(nbit, val, null);
    }

    @Override
    public Float64 peekFloat64(int nbit, Float64 val) {
        long orgpos = position();
        Float64 res = readFloat64(nbit, val, null);
        position(orgpos);
        return res;
    }

    @Override
    public Float64 readFloat64(int nbit, Float64 val) {
        return readFloat64(nbit, val, null);
    }

    @Override
    public LargeBitList peekBitList(long nbit, LargeBitList val) {
        long orgpos = position();
        LargeBitList res = readBitList(nbit, val, null);
        position(orgpos);
        return res;
    }

    @Override
    public LargeBitList readBitList(long nbit, LargeBitList val) {
        return readBitList(nbit, val, null);
    }
}
