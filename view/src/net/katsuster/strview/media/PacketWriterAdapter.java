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
public class PacketWriterAdapter<T> extends PacketConverterAdapter<T>
        implements PacketWriter<T> {
    public PacketWriterAdapter() {
        //do nothing
    }

    @Override
    public void writeSInt(int nbit, SInt val, String name) {
        writeSInt(nbit, val, name, null);
    }

    @Override
    public void writeSInt(int nbit, SInt val, String name, String desc) {
        //do nothing
    }

    @Override
    public void writeUInt(int nbit, UInt val, String name) {
        writeUInt(nbit, val, name, null);
    }

    @Override
    public void writeUInt(int nbit, UInt val, String name, String desc) {
        //do nothing
    }

    @Override
    public void writeBitList(long nbit, LargeBitList val, String name) {
        writeBitList(nbit, val, name, null);
    }

    @Override
    public void writeBitList(long nbit, LargeBitList val, String name, String desc) {
        //do nothing
    }
}
