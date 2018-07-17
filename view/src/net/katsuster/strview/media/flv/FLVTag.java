package net.katsuster.strview.media.flv;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * Flash Video Tag
 * </p>
 */
public class FLVTag extends BitPacketAdapter {
    //FLV タグのヘッダサイズ（byte 単位）
    public static final int TAG_HEADER_SIZE = 15;

    public FLVTag() {
        this(new FLVHeader());
    }

    public FLVTag(FLVHeader h) {
        super(h);
    }

    @Override
    public String getTypeName() {
        if (getHeader() instanceof FLVHeaderAudio) {
            return "Audio";
        } else if (getHeader() instanceof FLVHeaderScript) {
            return "Script";
        } else if (getHeader() instanceof FLVHeaderVideo) {
            return "Video";
        } else if (getHeader() instanceof FLVHeaderFile) {
            return "File";
        }

        return "Other?";
    }

    /**
     * <p>
     * FLV タグヘッダを取得します。
     * </p>
     *
     * @return FLV タグヘッダ
     */
    @Override
    public FLVHeader getHeader() {
        return (FLVHeader)super.getHeader();
    }

    @Override
    protected void readHeaderBits(BitStreamReader c) {
        getHeader().read(c);
    }

    @Override
    protected void readBodyBits(BitStreamReader c) {
        FLVHeader head = getHeader();
        FLVHeaderES headt;
        long size_f;

        //パケット全体のサイズを得る
        if (head instanceof FLVHeaderES) {
            //data_size は stream_id より後のデータサイズを表す
            headt = (FLVHeaderES)head;

            size_f = (TAG_HEADER_SIZE << 3) + (headt.data_size.intValue() << 3);
        } else if (head instanceof FLVHeaderFile) {
            //ヘッダのみ、本体はない
            size_f = getHeaderLength();
        } else {
            throw new IllegalStateException("illegal FLV Tag header type.");
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
