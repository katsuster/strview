package net.katsuster.strview.media.m2v;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * MPEG2 Video データ。
 * </p>
 */
public class M2VData<T extends LargeList<?>> extends PacketAdapter<T> {
    public M2VData() {
        this(new M2VHeader());
    }

    public M2VData(M2VHeader h) {
        super(h);
    }

    @Override
    public String getTypeName() {
        if (getHeader() instanceof M2VHeaderExt) {
            M2VHeaderExt h = (M2VHeaderExt)getHeader();
            return getHeader().getStartCodeName() + " (" + h.getExtensionStartCodeName() + ")";
        } else {
            return getHeader().getStartCodeName();
        }
    }

    /**
     * <p>
     * MPEG2 Video データのヘッダを取得します。
     * </p>
     *
     * @return MPEG2 Video データのヘッダ
     */
    @Override
    public M2VHeader<T> getHeader() {
        return (M2VHeader<T>)super.getHeader();
    }

    @Override
    protected void readHeader(StreamReader<?, ?> c) {
        getHeader().read(c);
    }

    @Override
    protected void readBody(StreamReader<?, ?> c) {
        long orgpos;
        int size_f = 0;
        int stepback = 0;
        int acc = 0xffffff;

        //次のスタートコードを探す
        c.align(8);
        orgpos = c.position();
        while (c.hasNext(8)) {
            acc <<= 8;
            acc |= c.readLong(8);
            if ((acc & 0xffffff) == 0x000001) {
                stepback = 24;
                break;
            }
        }
        size_f = (int)(c.position() - orgpos - stepback);
        c.position(orgpos);

        setBody(c.readBitList(size_f, getBody()));
    }

    @Override
    protected void writeHeader(StreamWriter<?, ?> c) {
        getHeader().write(c);
    }

    @Override
    protected void writeBody(StreamWriter<?, ?> c) {
        long size_f = getBody().length();

        //FIXME: tentative
        c.writeBitList(size_f, getBody(), "body");
    }
}
