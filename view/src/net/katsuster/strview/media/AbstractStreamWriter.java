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
 */
public abstract class AbstractStreamWriter<T> extends AbstractStreamConverter<T>
        implements StreamWriter<T> {
    public AbstractStreamWriter() {
        //do nothing
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
        writeSInt(nbit, val, val.getName(), null);
    }

    @Override
    public void writeSInt(int nbit, SInt val, String desc) {
        writeSInt(nbit, val, val.getName(), desc);
    }

    @Override
    public void pokeUInt(int nbit, UInt val) {
        long orgpos = position();
        writeUInt(nbit, val, null);
        position(orgpos);
    }

    @Override
    public void writeUInt(int nbit, UInt val) {
        writeUInt(nbit, val, val.getName(), null);
    }

    @Override
    public void writeUInt(int nbit, UInt val, String desc) {
        writeUInt(nbit, val, val.getName(), desc);
    }

    @Override
    public void pokeSIntR(int nbit, SIntR val) {
        long orgpos = position();
        writeSIntR(nbit, val, null);
        position(orgpos);
    }

    @Override
    public void writeSIntR(int nbit, SIntR val) {
        writeSIntR(nbit, val, val.getName(), null);
    }

    @Override
    public void writeSIntR(int nbit, SIntR val, String desc) {
        writeSIntR(nbit, val, val.getName(), desc);
    }

    @Override
    public void pokeUIntR(int nbit, UIntR val) {
        long orgpos = position();
        writeUIntR(nbit, val, null);
        position(orgpos);
    }

    @Override
    public void writeUIntR(int nbit, UIntR val) {
        writeUIntR(nbit, val, val.getName(), null);
    }

    @Override
    public void writeUIntR(int nbit, UIntR val, String desc) {
        writeUIntR(nbit, val, val.getName(), desc);
    }

    @Override
    public void pokeFloat32(int nbit, Float32 val) {
        long orgpos = position();
        writeFloat32(nbit, val, null);
        position(orgpos);
    }

    @Override
    public void writeFloat32(int nbit, Float32 val) {
        writeFloat32(nbit, val, val.getName(), null);
    }

    @Override
    public void writeFloat32(int nbit, Float32 val, String desc) {
        writeFloat32(nbit, val, val.getName(), desc);
    }

    @Override
    public void pokeFloat64(int nbit, Float64 val) {
        long orgpos = position();
        writeFloat64(nbit, val, null);
        position(orgpos);
    }

    @Override
    public void writeFloat64(int nbit, Float64 val) {
        writeFloat64(nbit, val, val.getName(), null);
    }

    @Override
    public void writeFloat64(int nbit, Float64 val, String desc) {
        writeFloat64(nbit, val, val.getName(), desc);
    }

    @Override
    public void pokeBitList(long nbit, LargeBitList val) {
        long orgpos = position();
        writeBitList(nbit, val, null);
        position(orgpos);
    }

    @Override
    public void writeBitList(long nbit, LargeBitList val) {
        writeBitList(nbit, val, val.getName(), null);
    }

    @Override
    public void writeBitList(long nbit, LargeBitList val, String desc) {
        writeBitList(nbit, val, val.getName(), desc);
    }
}
