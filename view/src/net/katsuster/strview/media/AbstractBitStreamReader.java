package net.katsuster.strview.media;

import net.katsuster.strview.util.*;

/**
 * <p>
 * ビットリストを読み取ることに特化したクラスを、
 * 容易に実装するための抽象クラスです。
 * </p>
 */
public abstract class AbstractBitStreamReader extends AbstractStreamReader<Boolean>
        implements BitStreamReader {
    public AbstractBitStreamReader() {
        super(null);
    }

    public AbstractBitStreamReader(LargeList<Boolean> l) {
        super(l);
    }

    public AbstractBitStreamReader(LargeList<Boolean> l, long p) {
        super(l, p);
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
