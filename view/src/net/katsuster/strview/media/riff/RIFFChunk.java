package net.katsuster.strview.media.riff;

import net.katsuster.strview.util.bit.*;
import net.katsuster.strview.media.bit.*;

/**
 * <p>
 * RIFF (Resource Interchange File Format) チャンク。
 * </p>
 */
public class RIFFChunk extends BitPacketAdapter {
    //RIFF チャンクのヘッダサイズ（byte 単位、ckSize を含まない）
    public static final int CHUNK_HEADER_SIZE = 8;

    public RIFFChunk() {
        this(new RIFFHeader());
    }

    public RIFFChunk(RIFFHeader h) {
        super(h);
    }

    @Override
    public String getTypeName() {
        if (getHeader() instanceof RIFFHeaderList) {
            RIFFHeaderList t = (RIFFHeaderList)getHeader();

            return t.getChunkIdName()
                    + "(" + t.getChunkTypeName() +")";
        } else {
            return getHeader().getChunkIdName();
        }
    }

    @Override
    public boolean isRecursive() {
        return getHeader().isRecursive();
    }

    /**
     * <p>
     * RIFF チャンクヘッダを取得します。
     * </p>
     *
     * @return RIFF チャンクヘッダ
     */
    @Override
    public RIFFHeader getHeader() {
        return (RIFFHeader)super.getHeader();
    }

    @Override
    protected void readHeaderBits(BitStreamReader c) {
        getHeader().read(c);
    }

    @Override
    protected void readBodyBits(BitStreamReader c) {
        RIFFHeader head = getHeader();
        long size_f;

        //ckSize は ckSize より後のデータサイズを表す
        size_f = (CHUNK_HEADER_SIZE << 3) + (head.ckSize.longValue() << 3);

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
