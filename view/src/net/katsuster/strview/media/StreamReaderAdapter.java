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
 *     <li>無限に読み込み（hasNext が常にtrue）</li>
 *     <li>マークは全て無視する</li>
 *     <li>変換対象は全て無視する</li>
 *     <li>結果は常に null を返す</li>
 * </ul>
 *
 * @author katsuhiro
 */
public class StreamReaderAdapter<T> extends AbstractStreamReader<T>
        implements StreamReader<T> {
    public StreamReaderAdapter() {
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
    public long readLong(int nbit, String desc) {
        return 0;
    }

    @Override
    public SInt readSInt(int nbit, SInt val, String desc) {
        return val;
    }

    @Override
    public UInt readUInt(int nbit, UInt val, String desc) {
        return val;
    }

    @Override
    public SIntR readSIntR(int nbit, SIntR val, String desc) {
        return val;
    }

    @Override
    public UIntR readUIntR(int nbit, UIntR val, String desc) {
        return val;
    }

    @Override
    public Float32 readFloat32(int nbit, Float32 val, String desc) {
        return val;
    }

    @Override
    public Float64 readFloat64(int nbit, Float64 val, String desc) {
        return val;
    }

    @Override
    public LargeBitList readBitList(long nbit, LargeBitList val, String desc) {
        return val;
    }

    @Override
    public T getResult() {
        return null;
    }
}
