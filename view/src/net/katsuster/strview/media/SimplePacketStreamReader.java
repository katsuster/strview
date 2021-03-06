package net.katsuster.strview.media;

import net.katsuster.strview.util.*;

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
public class SimplePacketStreamReader<T extends Packet> extends StreamReaderAdapter<T>
        implements StreamReader<T> {
    private LargePacketList<T> buf;

    public SimplePacketStreamReader(LargePacketList<T> l) {
        buf = l;
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
    public T read(T val, String desc) {
        T v = buf.get(position());
        position(position() + 1);
        return v;
    }

    @Override
    public LargeList<T> readList(long n, LargeList<T> val, String desc) {
        for (long i = 0; i < n; i++) {
            val.set(i, buf.get(position() + i));
        }
        position(position() + n);
        return val;
    }
}
