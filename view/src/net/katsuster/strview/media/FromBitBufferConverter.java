package net.katsuster.strview.media;

import net.katsuster.strview.util.*;
import net.katsuster.strview.io.*;

/**
 * <p>
 * BitBuffer から読み込むコンバータクラスです。
 * </p>
 *
 * @author katsuhiro
 */
public class FromBitBufferConverter extends PacketConverterAdapter<BitBuffer> {
    private BitBuffer b;

    public FromBitBufferConverter(BitBuffer buf) {
        b = buf;
    }

    @Override
    public long position() {
        return b.position();
    }

    @Override
    public void position(long pos) {
        b.position(pos);
    }

    @Override
    public SInt convSInt(int nbit, SInt val, String name, String desc) {
        b.getSInt(nbit, val);
        return val;
    }

    @Override
    public UInt convUInt(int nbit, UInt val, String name, String desc) {
        b.getUInt(nbit, val);
        return val;
    }

    @Override
    public LargeBitList convBitList(long nbit, LargeBitList val, String name, String desc) {
        return b.getBitList(nbit);
    }

    @Override
    public BitBuffer getResult() {
        return b;
    }
}
