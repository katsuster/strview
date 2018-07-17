package net.katsuster.strview.media.test;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * ソースパケット。
 * </p>
 */
public class SrcPacket extends BitPacketAdapter {
    public SrcPacket() {
        this(new SrcHeader(""));
    }

    public SrcPacket(SrcHeader h) {
        super(h);
    }

    @Override
    public String getTypeName() {
        return "Source Packet(name:" + getHeader().getName() + ")";
    }

    @Override
    public SrcHeader getHeader() {
        return (SrcHeader)super.getHeader();
    }

    @Override
    protected void readHeaderBits(BitStreamReader c) {
        getHeader().read(c);
    }

    @Override
    protected void readBodyBits(BitStreamReader c) {
        long size_f;

        //FIXME: サイズは固定の長さ
        size_f = 1;

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

        //FIXME: サイズは固定の長さ
        size_f = 1;

        //ヘッダ以降の本体を書き込む
        size_f -= getHeaderLength();
        c.writeBitList(size_f, (LargeBitList) getBody(), "body");
    }
}
