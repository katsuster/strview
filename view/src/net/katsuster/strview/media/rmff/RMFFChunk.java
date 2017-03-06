package net.katsuster.strview.media.rmff;

import net.katsuster.strview.media.*;

/**
 * <p>
 * RMFF(RealMedia File Format) チャンク。
 * </p>
 *
 * @author katsuhiro
 */
public class RMFFChunk extends PacketAdapter {
    public RMFFChunk() {
        this(new RMFFHeader());
    }

    public RMFFChunk(RMFFHeader h) {
        super(h);
    }

    @Override
    public String getShortName() {
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
    protected void readHeader(PacketReader<?> c) {
        getHeader().read(c);
    }

    @Override
    protected void readBody(PacketReader<?> c) {
        RMFFHeader head = getHeader();
        long size_f;

        //h_chunk.size にはチャンクヘッダも含むサイズが格納されている
        size_f = (head.size.longValue() << 3);

        //ヘッダ以降を本体として読み込む
        size_f -= getHeaderLength();
        setBody(c.readSubList(size_f, getBody()));
    }

    @Override
    protected void writeHeader(PacketWriter<?> c) {
        getHeader().write(c);
    }

    @Override
    protected void writeBody(PacketWriter<?> c) {
        long size_f = getBody().length();

        //FIXME: tentative
        c.writeSubList(size_f, getBody(), "body");
    }
}
