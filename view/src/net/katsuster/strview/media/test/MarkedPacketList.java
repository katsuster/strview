package net.katsuster.strview.media.test;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * 先頭にマーカーのあるパケットリスト。
 * ビットリストからマーカーのあるパケットを読み出すテスト用。
 * </p>
 *
 * @author katsuhiro
 */
public class MarkedPacketList extends AbstractPacketList<MarkedPacket> {
    private LargeBitList buf;

    public MarkedPacketList() {
        super(LENGTH_UNKNOWN);
    }

    public MarkedPacketList(LargeBitList l) {
        super(LENGTH_UNKNOWN);

        buf = l;
    }

    @Override
    public String getShortName() {
        return "Marked Packet List";
    }

    @Override
    public boolean hasTreeStructure() {
        return false;
    }

    @Override
    public void count() {
        FromBitListConverter c = new FromBitListConverter(buf);

        countSlow(c);
    }

    @Override
    protected Packet readNextInner(PacketReader<?> c, PacketRange pr) {
        MarkedPacket packet = new MarkedPacket(new MarkedHeader());
        packet.setRange(pr);
        packet.read(c);

        return packet;
    }

    @Override
    protected MarkedPacket getInner(long index) {
        FromBitListConverter c = new FromBitListConverter(buf);

        seek(c, index);

        return (MarkedPacket)readNext(c, index);
    }

    @Override
    protected void setInner(long index, MarkedPacket data) {
        //TODO: not implemented yet
    }
}
