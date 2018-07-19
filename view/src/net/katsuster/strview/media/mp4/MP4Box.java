package net.katsuster.strview.media.mp4;

import net.katsuster.strview.util.bit.*;
import net.katsuster.strview.media.bit.*;

/**
 * <p>
 * MPEG4 media file format の Box。
 * </p>
 */
public class MP4Box extends BitPacketAdapter {
    //MP4 Box 最小ヘッダサイズ（byte 単位、size と type のみ）
    public static final int BOX_HEADER_SIZE = 8;

    public MP4Box() {
        this(new MP4Header());
    }

    public MP4Box(MP4Header header) {
        setHeader(header);
    }

    @Override
    public String getTypeName() {
        return getHeader().getTypeName();
    }

    @Override
    public boolean isRecursive() {
        return getHeader().isRecursive();
    }

    /**
     * <p>
     * MP4 Box ヘッダを返す。
     * </p>
     *
     * @return MP4 Box ヘッダ
     */
    @Override
    public MP4Header getHeader() {
        return (MP4Header)super.getHeader();
    }

    @Override
    protected void readHeaderBits(BitStreamReader c) {
        getHeader().read(c);
    }

    @Override
    protected void readBodyBits(BitStreamReader c) {
        MP4Header head = getHeader();
        long size_f;

        //size は size を含むボックス全体のサイズを表す
        size_f = (head.size.longValue() << 3);
        if (size_f <= 0) {
            //0 以下は異常値なので無視し、
            //size, type メンバのサイズに変更する
            //読み出し位置が進まず、無限ループする不具合回避も兼ねている
            size_f = (BOX_HEADER_SIZE << 3);
        }

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
