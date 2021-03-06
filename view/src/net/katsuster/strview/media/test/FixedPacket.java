package net.katsuster.strview.media.test;

import net.katsuster.strview.util.bit.*;
import net.katsuster.strview.media.bit.*;

/**
 * <p>
 * 固定長パケット。
 * </p>
 */
public class FixedPacket extends BitPacketAdapter {
    //パケットのサイズ（byte 単位）
    public static final int PACKET_SIZE = 64;

    public FixedPacket() {
        this(new FixedHeader());
    }

    public FixedPacket(FixedHeader h) {
        super(h);
    }

    @Override
    public String getTypeName() {
        return "Fixed Size Packet(a:" + getHeader().data_a + ")";
    }

    @Override
    public FixedHeader getHeader() {
        return (FixedHeader)super.getHeader();
    }

    @Override
    protected void readHeaderBits(BitStreamReader c) {
        getHeader().read(c);
    }

    @Override
    protected void readBodyBits(BitStreamReader c) {
        long size_f;

        //サイズは固定の長さ
        size_f = (PACKET_SIZE << 3);

        //ヘッダ以降の本体を読み込む
        size_f -= getHeaderLength();
        setBody(c.readBitList(size_f, (LargeBitList) getBody()));
    }

    @Override
    protected void writeHeaderBits(BitStreamWriter c) {
        getHeader().write(c);
    }

    @Override
    protected void writeBodyBits(BitStreamWriter c) {
        long size_f;

        //サイズは固定の長さ
        size_f = (PACKET_SIZE << 3);

        //ヘッダ以降の本体を書き込む
        size_f -= getHeaderLength();
        c.writeBitList(size_f, (LargeBitList) getBody(), "body");
    }
}
