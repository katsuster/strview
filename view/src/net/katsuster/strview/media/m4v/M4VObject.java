package net.katsuster.strview.media.m4v;

import net.katsuster.strview.util.bit.*;
import net.katsuster.strview.media.bit.*;

/**
 * <p>
 * MPEG4 Part 2 Visual Object
 * </p>
 */
public class M4VObject
        extends BitPacketAdapter {
    public M4VObject() {
        this(new M4VHeader());
    }

    public M4VObject(M4VHeader h) {
        super(h);
    }

    @Override
    public String getTypeName() {
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
    protected void readHeaderBits(BitStreamReader c) {
        getHeader().read(c);
    }

    @Override
    protected void readBodyBits(BitStreamReader c) {
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

        setBody(c.readBitList(size_f, (LargeBitList) getBody()));
    }

    @Override
    protected void writeHeaderBits(BitStreamWriter c) {
        getHeader().write(c);
    }

    @Override
    protected void writeBodyBits(BitStreamWriter c) {
        long size_f = getBody().length();

        //FIXME: tentative
        c.writeBitList(size_f, (LargeBitList) getBody(), "body");
    }
}
