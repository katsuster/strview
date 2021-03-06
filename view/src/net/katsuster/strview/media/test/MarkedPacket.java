package net.katsuster.strview.media.test;

import net.katsuster.strview.util.bit.*;
import net.katsuster.strview.media.bit.*;

/**
 * <p>
 * 先頭にマーカーのあるパケット。
 * </p>
 */
public class MarkedPacket extends BitPacketAdapter {
    public MarkedPacket() {
        this(new MarkedHeader());
    }

    public MarkedPacket(MarkedHeader h) {
        super(h);
    }

    @Override
    public String getTypeName() {
        return "Marked Packet(a:" + getHeader().start_code + ")";
    }

    @Override
    public MarkedHeader getHeader() {
        return (MarkedHeader)super.getHeader();
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
