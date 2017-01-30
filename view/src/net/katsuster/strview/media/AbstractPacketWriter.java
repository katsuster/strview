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
public abstract class AbstractPacketWriter<T> extends AbstractPacketConverter<T>
        implements PacketWriter<T> {
    public AbstractPacketWriter() {
        //do nothing
    }

    @Override
    public void pokeLong(int nbit, long val, String name) {
        pokeLong(nbit, val, name, null);
    }

    @Override
    public void pokeLong(int nbit, long val, String name, String desc) {
        long orgpos = position();
        writeLong(nbit, val, name, desc);
        position(orgpos);
    }

    @Override
    public void writeLong(int nbit, long val, String name) {
        writeLong(nbit, val, name, null);
    }

    @Override
    public void pokeSInt(int nbit, SInt val, String name) {
        pokeSInt(nbit, val, name, null);
    }

    @Override
    public void pokeSInt(int nbit, SInt val, String name, String desc) {
        long orgpos = position();
        writeSInt(nbit, val, name, desc);
        position(orgpos);
    }

    @Override
    public void writeSInt(int nbit, SInt val, String name) {
        writeSInt(nbit, val, name, null);
    }

    @Override
    public void pokeUInt(int nbit, UInt val, String name) {
        pokeUInt(nbit, val, name, null);
    }

    @Override
    public void pokeUInt(int nbit, UInt val, String name, String desc) {
        long orgpos = position();
        writeUInt(nbit, val, name, desc);
        position(orgpos);
    }

    @Override
    public void writeUInt(int nbit, UInt val, String name) {
        writeUInt(nbit, val, name, null);
    }

    @Override
    public void pokeFloat32(int nbit, Float32 val, String name) {
        pokeFloat32(nbit, val, name, null);
    }

    @Override
    public void pokeFloat32(int nbit, Float32 val, String name, String desc) {
        long orgpos = position();
        writeFloat32(nbit, val, name, desc);
        position(orgpos);
    }

    @Override
    public void writeFloat32(int nbit, Float32 val, String name) {
        writeFloat32(nbit, val, name, null);
    }

    @Override
    public void pokeFloat64(int nbit, Float64 val, String name) {
        pokeFloat64(nbit, val, name, null);
    }

    @Override
    public void pokeFloat64(int nbit, Float64 val, String name, String desc) {
        long orgpos = position();
        writeFloat64(nbit, val, name, desc);
        position(orgpos);
    }

    @Override
    public void writeFloat64(int nbit, Float64 val, String name) {
        writeFloat64(nbit, val, name, null);
    }

    @Override
    public void pokeBitList(int nbit, LargeBitList val, String name) {
        pokeBitList(nbit, val, name, null);
    }

    @Override
    public void pokeBitList(int nbit, LargeBitList val, String name, String desc) {
        long orgpos = position();
        writeBitList(nbit, val, name, desc);
        position(orgpos);
    }

    @Override
    public void writeBitList(int nbit, LargeBitList val, String name) {
        writeBitList(nbit, val, name, null);
    }

    @Override
    public void pokeSubList(long nbit, LargeBitList val, String name) {
        pokeSubList(nbit, val, name, null);
    }

    @Override
    public void pokeSubList(long nbit, LargeBitList val, String name, String desc) {
        long orgpos = position();
        writeSubList(nbit, val, name, desc);
        position(orgpos);
    }

    @Override
    public void writeSubList(long nbit, LargeBitList val, String name) {
        writeSubList(nbit, val, name, null);
    }
}
