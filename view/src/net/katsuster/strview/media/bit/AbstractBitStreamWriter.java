package net.katsuster.strview.media.bit;

import net.katsuster.strview.media.*;
import net.katsuster.strview.util.*;
import net.katsuster.strview.util.bit.*;

/**
 * <p>
 * ビットリストを書き込むことに特化したクラスを、
 * 容易に実装するための抽象クラスです。
 * </p>
 */
public abstract class AbstractBitStreamWriter extends AbstractStreamWriter<Boolean>
        implements BitStreamWriter {
    public AbstractBitStreamWriter() {
        super(null);
    }

    public AbstractBitStreamWriter(LargeList<Boolean> l) {
        super(l);
    }

    @Override
    public void pokeLong(int nbit, long val, String name) {
        long orgpos = position();
        writeLong(nbit, val, name, null);
        position(orgpos);
    }

    @Override
    public void writeLong(int nbit, long val, String name) {
        writeLong(nbit, val, name, null);
    }

    @Override
    public void pokeSInt(int nbit, SInt val) {
        long orgpos = position();
        writeSInt(nbit, val, null);
        position(orgpos);
    }

    @Override
    public void writeSInt(int nbit, SInt val) {
        writeSInt(nbit, val, null);
    }

    @Override
    public void pokeUInt(int nbit, UInt val) {
        long orgpos = position();
        writeUInt(nbit, val, null);
        position(orgpos);
    }

    @Override
    public void writeUInt(int nbit, UInt val) {
        writeUInt(nbit, val, null);
    }

    @Override
    public void pokeSIntR(int nbit, SIntR val) {
        long orgpos = position();
        writeSIntR(nbit, val, null);
        position(orgpos);
    }

    @Override
    public void writeSIntR(int nbit, SIntR val) {
        writeSIntR(nbit, val, null);
    }

    @Override
    public void pokeUIntR(int nbit, UIntR val) {
        long orgpos = position();
        writeUIntR(nbit, val, null);
        position(orgpos);
    }

    @Override
    public void writeUIntR(int nbit, UIntR val) {
        writeUIntR(nbit, val, null);
    }

    @Override
    public void pokeFloat32(int nbit, Float32 val) {
        long orgpos = position();
        writeFloat32(nbit, val, null);
        position(orgpos);
    }

    @Override
    public void writeFloat32(int nbit, Float32 val) {
        writeFloat32(nbit, val, null);
    }

    @Override
    public void pokeFloat64(int nbit, Float64 val) {
        long orgpos = position();
        writeFloat64(nbit, val, null);
        position(orgpos);
    }

    @Override
    public void writeFloat64(int nbit, Float64 val) {
        writeFloat64(nbit, val, null);
    }

    @Override
    public void pokeSF8_8(int nbit, SFixed8_8 val) {
        long orgpos = position();
        writeSF8_8(nbit, val, null);
        position(orgpos);
    }

    @Override
    public void writeSF8_8(int nbit, SFixed8_8 val) {
        writeSF8_8(nbit, val, null);
    }

    @Override
    public void pokeSF16_16(int nbit, SFixed16_16 val) {
        long orgpos = position();
        writeSF16_16(nbit, val, null);
        position(orgpos);
    }

    @Override
    public void writeSF16_16(int nbit, SFixed16_16 val) {
        writeSF16_16(nbit, val, null);
    }

    @Override
    public void pokeUF8_8(int nbit, UFixed8_8 val) {
        long orgpos = position();
        writeUF8_8(nbit, val, null);
        position(orgpos);
    }

    @Override
    public void writeUF8_8(int nbit, UFixed8_8 val) {
        writeUF8_8(nbit, val, null);
    }

    @Override
    public void pokeUF16_16(int nbit, UFixed16_16 val) {
        long orgpos = position();
        writeUF16_16(nbit, val, null);
        position(orgpos);
    }

    @Override
    public void writeUF16_16(int nbit, UFixed16_16 val) {
        writeUF16_16(nbit, val, null);
    }

    @Override
    public void pokeBitList(long nbit, LargeBitList val) {
        long orgpos = position();
        writeBitList(nbit, val, null);
        position(orgpos);
    }

    @Override
    public void writeBitList(long nbit, LargeBitList val) {
        writeBitList(nbit, val, null);
    }
}
