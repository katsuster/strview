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
 *     <li>無限に書き込み可能（hasNext が常にtrue）</li>
 *     <li>マークは全て無視する</li>
 *     <li>変換対象は全て無視する</li>
 *     <li>結果は常に null を返す</li>
 * </ul>
 */
public class StreamWriterAdapter<T> extends AbstractStreamWriter<T>
        implements StreamWriter<T> {
    public StreamWriterAdapter() {
        //do nothing
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
    public long position() {
        return 0;
    }

    @Override
    public void position(long p) {
        //do nothing
    }

    @Override
    public boolean hasNext(long n) {
        return true;
    }

    @Override
    public void writeLong(int nbit, long val, String name, String desc) {
        //do nothing
    }

    @Override
    public void writeSInt(int nbit, SInt val, String name, String desc) {
        //do nothing
    }

    @Override
    public void writeUInt(int nbit, UInt val, String name, String desc) {
        //do nothing
    }

    @Override
    public void writeSIntR(int nbit, SIntR val, String name, String desc) {
        //do nothing
    }

    @Override
    public void writeUIntR(int nbit, UIntR val, String name, String desc) {
        //do nothing
    }

    @Override
    public void writeFloat32(int nbit, Float32 val, String name, String desc) {
        //do nothing
    }

    @Override
    public void writeFloat64(int nbit, Float64 val, String name, String desc) {
        //do nothing
    }

    @Override
    public void writeBitList(long nbit, LargeBitList val, String name, String desc) {
        //do nothing
    }

    @Override
    public T getResult() {
        return null;
    }
}
