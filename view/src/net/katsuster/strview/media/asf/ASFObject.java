package net.katsuster.strview.media.asf;

import net.katsuster.strview.media.*;

/**
 * <p>
 * ASF (Advanced Systems Format) Object
 * </p>
 *
 * @author katsuhiro
 */
public class ASFObject extends PacketAdapter {
    //ASF Object 最小ヘッダサイズ（byte 単位、ID と size のみ）
    public static final int OBJECT_HEADER_SIZE = 24;

    public ASFObject() {
        this(new ASFHeader());
    }

    public ASFObject(ASFHeader header) {
        setHeader(header);
    }

    @Override
    public String getShortName() {
        return getHeader().object_id.getObjectIdName();
    }

    @Override
    public boolean isRecursive() {
        return getHeader().isRecursive();
    }

    /**
     * <p>
     * ASF オブジェクトヘッダを取得します。
     * </p>
     *
     * @return ASF オブジェクトヘッダ
     */
    @Override
    public ASFHeader getHeader() {
        return (ASFHeader)super.getHeader();
    }

    @Override
    protected void readHeader(PacketReader<?> c) {
        getHeader().read(c);
    }

    @Override
    protected void readBody(PacketReader<?> c) {
        ASFHeader head = getHeader();
        long size_f;

        //object_size は GUID, object_size を含んだ、
        //オブジェクト全体のサイズが格納されています
        size_f = (head.object_size.longValue() << 3);
        if (size_f <= 0) {
            //0 以下は異常値なので無視し、
            //ID, Size メンバのサイズに変更する
            //読み出し位置が進まず、無限ループする不具合回避も兼ねている
            size_f = (OBJECT_HEADER_SIZE << 3);
        }

        //ヘッダ以降を本体として読み込む
        size_f -= getHeaderLength();
        setBody(c.readBitList(size_f, getBody()));
    }

    @Override
    protected void writeHeader(PacketWriter<?> c) {
        getHeader().write(c);
    }

    @Override
    protected void writeBody(PacketWriter<?> c) {
        long size_f = getBody().length();

        //FIXME: tentative
        c.writeBitList(size_f, getBody(), "body");
    }
}
