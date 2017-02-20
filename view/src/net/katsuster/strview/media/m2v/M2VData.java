package net.katsuster.strview.media.m2v;

import net.katsuster.strview.media.*;

/**
 * <p>
 * MPEG2 Video データ。
 * </p>
 *
 * @author katsuhiro
 */
public class M2VData extends PacketAdapter
        implements Cloneable {
    public M2VData() {
        this(new M2VHeader());
    }

    public M2VData(M2VHeader h) {
        super(h);
    }

    @Override
    public M2VData clone()
            throws CloneNotSupportedException {
        M2VData obj = (M2VData)super.clone();

        return obj;
    }

    @Override
    public String getShortName() {
        return "M2V(" + getHeader().getStartCodeName() + ")";
    }

    /**
     * <p>
     * MPEG2 Video データのヘッダを取得します。
     * </p>
     *
     * @return MPEG2 Video データのヘッダ
     */
    @Override
    public M2VHeader getHeader() {
        return (M2VHeader)super.getHeader();
    }

    @Override
    protected void readHeader(PacketReader<?> c) {
        AbstractPacket.read(c, this);

        getHeader().read(c);
    }

    @Override
    protected void readBody(PacketReader<?> c) {
        long orgpos;
        int size_f = 0;
        int stepback = 0;
        int acc = 0xffffff;

        //次のスタートコードを探す
        c.alignByte();
        orgpos = c.position();
        while (c.hasNext(8)) {
            acc <<= 8;
            acc |= c.readLong(8);
            if ((acc & 0xffffff) == 0x000001) {
                stepback = 24;
                break;
            }
        }
        size_f = (int)(c.position() - orgpos - stepback);
        c.position(orgpos);

        setBody(c.readSubList(size_f, getBody()));
    }

    @Override
    protected void writeHeader(PacketWriter<?> c) {
        AbstractPacket.write(c, this);

        getHeader().write(c);
    }

    @Override
    protected void writeBody(PacketWriter<?> c) {
        long size_f = getBody().length();

        //FIXME: tentative
        c.writeSubList(size_f, getBody(), "body");
    }
}
