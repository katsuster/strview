package net.katsuster.strview.media.rmff;

import net.katsuster.strview.util.bit.*;
import net.katsuster.strview.media.bit.*;

/**
 * <p>
 * RMFF(RealMedia File Format) チャンク。
 * </p>
 */
public class RMFFChunk extends BitPacketAdapter {
    public RMFFChunk() {
        this(new RMFFHeader());
    }

    public RMFFChunk(RMFFHeader h) {
        super(h);
    }

    @Override
    public String getTypeName() {
        return getHeader().getObjectIdName();
    }

    @Override
    public boolean isRecursive() {
        return getHeader().isRecursive();
    }

    /**
     * <p>
     * RMFF チャンクヘッダを取得します。
     * </p>
     *
     * @return RMFF チャンクヘッダ
     */
    @Override
    public RMFFHeader getHeader() {
        return (RMFFHeader)super.getHeader();
    }

    @Override
    protected void readHeaderBits(BitStreamReader c) {
        getHeader().read(c);
    }

    @Override
    protected void readBodyBits(BitStreamReader c) {
        RMFFHeader head = getHeader();
        long size_f;

        //h_chunk.size にはチャンクヘッダも含むサイズが格納されている
        size_f = (head.size.longValue() << 3);

        //ヘッダ以降を本体として読み込む
        size_f -= getHeaderLength();
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
