package net.katsuster.strview.media.mkv;

import net.katsuster.strview.util.bit.*;
import net.katsuster.strview.media.bit.*;

/**
 * <p>
 * Matroska タグ。
 * </p>
 */
public class MKVTag extends BitPacketAdapter {
    //Matroska タグ最小ヘッダサイズ（byte 単位、id と size の最小値）
    public static final int TAG_HEADER_SIZE = 2;

    public MKVTag() {
        this(new MKVHeader());
    }

    public MKVTag(MKVHeader h) {
        super(h);
    }

    @Override
    public String getTypeName() {
        return getHeader().getTagIdName();
    }

    @Override
    public boolean isRecursive() {
        return getHeader().isMaster();
    }

    /**
     * <p>
     * Matroska タグヘッダを返す。
     * </p>
     *
     * @return Matroska タグヘッダ
     */
    @Override
    public MKVHeader getHeader() {
        return (MKVHeader)super.getHeader();
    }

    /**
     * <p>
     * タグ ID を返す。
     * </p>
     *
     * @return タグ ID
     */
    public long getTagID() {
        return getHeader().tag_id.getValue();
    }

    /**
     * <p>
     * タグの長さを返す。
     * </p>
     *
     * @return タグの長さ
     */
    public long getTagLength() {
        return getHeader().tag_len.getValue();
    }

    @Override
    protected void readHeaderBits(BitStreamReader c) {
        getHeader().read(c);
    }

    @Override
    protected void readBodyBits(BitStreamReader c) {
        MKVHeader head = getHeader();
        long size_f;

        //tag_len はタグ ID とタグサイズより後のデータサイズを表す
        size_f = head.tag_id.getSizeAll()
                + head.tag_len.getSizeAll()
                + (getTagLength() << 3);

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
