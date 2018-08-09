package net.katsuster.strview.media.bit;

import net.katsuster.strview.util.*;
import net.katsuster.strview.util.bit.*;

/**
 * <p>
 * 何もしないリストからの読み込みクラスです。
 * </p>
 *
 * <p>
 * 下記の特徴を持ちます。
 * </p>
 *
 * <ul>
 *     <li>無限に読み込み（hasNext が常にtrue）</li>
 *     <li>マークは全て無視する</li>
 *     <li>何も読み込まず渡された値をそのまま返すが、位置は進む</li>
 * </ul>
 */
public class BitStreamReaderAdapter extends AbstractBitStreamReader
        implements BitStreamReader {
    public BitStreamReaderAdapter() {
        super(null);
    }

    public BitStreamReaderAdapter(LargeList<Boolean> l) {
        super(l);
    }

    public BitStreamReaderAdapter(LargeList<Boolean> l, long p) {
        super(l, p);
    }

    @Override
    public void enterPacket(String name) {
        //do nothing
    }

    @Override
    public void leavePacket() {
        //do nothing
    }

    @Override
    public void enterBlock(String name) {
        //do nothing
    }

    @Override
    public void leaveBlock() {
        //do nothing
    }

    @Override
    public void mark(String name, String s, String desc) {
        //do nothing
    }

    @Override
    public void mark(String name, Number n, String desc) {
        //do nothing
    }

    @Override
    public boolean hasNext(long n) {
        return true;
    }

    @Override
    public Boolean read(Boolean val, String desc) {
        position(position() + 1);
        return val;
    }

    @Override
    public LargeList<Boolean> readList(long n, LargeList<Boolean> val, String desc) {
        position(position() + n);
        return val;
    }
    @Override
    public long readLong(int nbit, String desc) {
        position(position() + nbit);
        return 0;
    }

    @Override
    public SInt readSInt(int nbit, SInt val, String desc) {
        position(position() + nbit);
        return val;
    }

    @Override
    public UInt readUInt(int nbit, UInt val, String desc) {
        position(position() + nbit);
        return val;
    }

    @Override
    public SIntR readSIntR(int nbit, SIntR val, String desc) {
        position(position() + nbit);
        return val;
    }

    @Override
    public UIntR readUIntR(int nbit, UIntR val, String desc) {
        position(position() + nbit);
        return val;
    }

    @Override
    public Float32 readFloat32(int nbit, Float32 val, String desc) {
        position(position() + nbit);
        return val;
    }

    @Override
    public Float64 readFloat64(int nbit, Float64 val, String desc) {
        position(position() + nbit);
        return val;
    }

    @Override
    public SFixed8_8 readSF8_8(int nbit, SFixed8_8 val, String desc) {
        position(position() + nbit);
        return val;
    }

    @Override
    public SFixed16_16 readSF16_16(int nbit, SFixed16_16 val, String desc) {
        position(position() + nbit);
        return val;
    }

    @Override
    public UFixed8_8 readUF8_8(int nbit, UFixed8_8 val, String desc) {
        position(position() + nbit);
        return val;
    }

    @Override
    public UFixed16_16 readUF16_16(int nbit, UFixed16_16 val, String desc) {
        position(position() + nbit);
        return val;
    }

    @Override
    public LargeBitList readBitList(long nbit, LargeBitList val, String desc) {
        position(position() + nbit);
        return val;
    }
}
