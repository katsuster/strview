package net.katsuster.strview.media.m4v;

import net.katsuster.strview.media.*;

/**
 * <p>
 * MPEG4 Part 2 Visual Object
 * </p>
 */
public class M4VObject extends PacketAdapter {
    public M4VObject() {
        this(new M4VHeader());
    }

    public M4VObject(M4VHeader h) {
        super(h);
    }

    @Override
    public String getShortName() {
        return getHeader().getStartCodeName();
    }

    /**
     * <p>
     * MPEG4 Part 2 Video オブジェクトのヘッダを取得します。
     * </p>
     *
     * @return MPEG4 Part 2 Video オブジェクトのヘッダ
     */
    @Override
    public M4VHeader getHeader() {
        return (M4VHeader)super.getHeader();
    }

    @Override
    protected void readHeader(StreamReader<?> c) {
        getHeader().read(c);
    }

    @Override
    protected void readBody(StreamReader<?> c) {
        long orgpos;
        int size_f = 0;
        int stepback = 0;
        int acc = 0xffffff;

        //次のスタートコードを探す
        c.alignByte();
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
    protected void writeHeader(StreamWriter<?> c) {
        getHeader().write(c);
    }

    @Override
    protected void writeBody(StreamWriter<?> c) {
        long size_f = getBody().length();

        //FIXME: tentative
        c.writeBitList(size_f, getBody(), "body");
    }
}
