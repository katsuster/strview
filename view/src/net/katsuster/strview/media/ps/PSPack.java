package net.katsuster.strview.media.ps;

import net.katsuster.strview.media.*;

/**
 * <p>
 * MPEG2-PS (Program Stream) パック。
 * </p>
 *
 * @author katsuhiro
 */
public class PSPack extends PacketAdapter {
    //PS パケットのヘッダサイズ（byte 単位）
    public static final int PACKET_HEADER_SIZE = 6;

    public PSPack() {
        this(new PSHeader());
    }

    public PSPack(PSHeader h) {
        super(h);
    }

    @Override
    public String getShortName() {
        return getHeader().getStreamIdName();
    }

    /**
     * <p>
     * MPEG2-PS パックヘッダを取得します。
     * </p>
     *
     * @return MPEG2-PS パックヘッダ
     */
    @Override
    public PSHeader getHeader() {
        return (PSHeader)super.getHeader();
    }

    @Override
    protected void readHeader(StreamReader<?> c) {
        getHeader().read(c);
    }

    @Override
    protected void readBody(StreamReader<?> c) {
        long orgpos;
        long size_f = 0;
        boolean search = false;
        int stepback = 0;
        int acc = 0xffffff;

        if (getHeader() instanceof PSHeaderPES) {
            PSHeaderPES h = (PSHeaderPES)getHeader();

            if (h.pes_packet_length.intValue() == 0) {
                search = true;
            } else {
                size_f = (h.pes_packet_length.intValue() << 3)
                        - (getHeaderLength() - (PACKET_HEADER_SIZE << 3));
                //search = true;
            }
        } else {
            search = true;
        }

        if (search) {
            //次のパックヘッダを探す
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
            size_f = (int) (c.position() - orgpos - stepback);
            c.position(orgpos);
        }

        setBody(c.readBitList(size_f, getBody()));
    }

    @Override
    protected void writeHeader(StreamWriter<?> c) {
        getHeader().write(c);
    }

    @Override
    protected void writeBody(StreamWriter<?> c) {
        long size_f = getBody().length();

        //FIXME: tentative
        c.writeBitList(size_f, getBody(), "body");
    }
}
