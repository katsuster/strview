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
    public void pokeSInt(int nbit, SInt val, String name) {
        long orgpos = position();
        writeSInt(nbit, val, name, null);
        position(orgpos);
    }

    @Override
    public void writeSInt(int nbit, SInt val, String name) {
        writeSInt(nbit, val, name, null);
    }

    @Override
    public void pokeUInt(int nbit, UInt val, String name) {
        long orgpos = position();
        writeUInt(nbit, val, name, null);
        position(orgpos);
    }

    @Override
    public void writeUInt(int nbit, UInt val, String name) {
        writeUInt(nbit, val, name, null);
    }

    @Override
    public void pokeSIntR(int nbit, SIntR val, String name) {
        long orgpos = position();
        writeSIntR(nbit, val, name, null);
        position(orgpos);
    }

    @Override
    public void writeSIntR(int nbit, SIntR val, String name) {
        writeSIntR(nbit, val, name, null);
    }

    @Override
    public void pokeUIntR(int nbit, UIntR val, String name) {
        long orgpos = position();
        writeUIntR(nbit, val, name, null);
        position(orgpos);
    }

    @Override
    public void writeUIntR(int nbit, UIntR val, String name) {
        writeUIntR(nbit, val, name, null);
    }

    @Override
    public void pokeFloat32(int nbit, Float32 val, String name) {
        long orgpos = position();
        writeFloat32(nbit, val, name, null);
        position(orgpos);
    }

    @Override
    public void writeFloat32(int nbit, Float32 val, String name) {
        writeFloat32(nbit, val, name, null);
    }

    @Override
    public void pokeFloat64(int nbit, Float64 val, String name) {
        long orgpos = position();
        writeFloat64(nbit, val, name, null);
        position(orgpos);
    }

    @Override
    public void writeFloat64(int nbit, Float64 val, String name) {
        writeFloat64(nbit, val, name, null);
    }

    @Override
    public void pokeBitList(long nbit, LargeBitList val, String name) {
        long orgpos = position();
        writeBitList(nbit, val, name, null);
        position(orgpos);
    }

    @Override
    public void writeBitList(long nbit, LargeBitList val, String name) {
        writeBitList(nbit, val, name, null);
    }
}
