package net.katsuster.strview.media.test;

import net.katsuster.strview.media.*;

/**
 * <p>
 * 固定長パケット。
 * </p>
 *
 * @author katsuhiro
 */
public class FixedSizePacket extends PacketAdapter {
    //パケットのサイズ（byte 単位）
    public static final int PACKET_SIZE = 64;

    public FixedSizePacket() {
        this(new FixedHeader());
    }

    public FixedSizePacket(FixedHeader h) {
        super(h);
    }

    @Override
    public String getShortName() {
        return "Fixed Size Packet(a:" + getHeader().data_a + ")";
    }

    /**
     * <p>
     * TS パケットヘッダを取得します。
     * </p>
     *
     * @return TS パケットヘッダ
     */
    @Override
    public FixedHeader getHeader() {
        return (FixedHeader)super.getHeader();
    }

    @Override
    protected void readHeader(PacketReader<?> c) {
        getHeader().read(c);
    }

    @Override
    protected void readBody(PacketReader<?> c) {
        long size_f;

        //サイズは固定の長さ
        size_f = (PACKET_SIZE << 3);

        //ヘッダ以降の本体を読み込む
        size_f -= getHeaderLength();
        setBody(c.readBitList(size_f, getBody()));
    }

    @Override
    protected void writeHeader(PacketWriter<?> c) {
        getHeader().write(c);
    }

    @Override
    protected void writeBody(PacketWriter<?> c) {
        long size_f;

        //サイズは固定の長さ
        size_f = (PACKET_SIZE << 3);

        //ヘッダ以降の本体を書き込む
        size_f -= getHeaderLength();
        c.writeBitList(size_f, getBody(), "body");
    }
}
