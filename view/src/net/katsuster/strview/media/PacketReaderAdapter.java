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
public class PacketReaderAdapter<T> extends PacketConverterAdapter<T>
        implements PacketReader<T> {
    public PacketReaderAdapter() {
        //do nothing
    }

    @Override
    public SInt readSInt(int nbit, SInt val) {
        return readSInt(nbit, val, null);
    }

    @Override
    public SInt readSInt(int nbit, SInt val, String desc) {
        return val;
    }

    @Override
    public UInt readUInt(int nbit, UInt val) {
        return readUInt(nbit, val, null);
    }

    @Override
    public UInt readUInt(int nbit, UInt val, String desc) {
        return val;
    }

    @Override
    public LargeBitList readBitList(long nbit, LargeBitList val) {
        return readBitList(nbit, val, null);
    }

    @Override
    public LargeBitList readBitList(long nbit, LargeBitList val, String desc) {
        return val;
    }
}
