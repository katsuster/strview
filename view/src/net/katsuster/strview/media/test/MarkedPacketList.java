package net.katsuster.strview.media.test;

import net.katsuster.strview.util.*;
import net.katsuster.strview.util.bit.*;
import net.katsuster.strview.media.*;
import net.katsuster.strview.media.bit.*;

/**
 * <p>
 * 先頭にマーカーのあるパケットリスト。
 * ビットリストからマーカーのあるパケットを読み出すテスト用。
 * </p>
 */
public class MarkedPacketList
        extends AbstractPacketList<MarkedPacket, Boolean> {
    private LargeBitList buf;

    public MarkedPacketList() {
        super(LENGTH_UNKNOWN);
    }

    public MarkedPacketList(LargeBitList l) {
        super(LENGTH_UNKNOWN);

        buf = l;
    }

    @Override
    public String getTypeName() {
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
    protected MarkedPacket readNextInner(StreamReader<Boolean> c, PacketRange<LargeList<Boolean>> pr) {
        MarkedPacket packet = new MarkedPacket(new MarkedHeader());
        packet.setRange(pr);
        packet.read(c);

        return packet;
    }

    @Override
    protected MarkedPacket getInner(long index) {
        FromBitListConverter c = new FromBitListConverter(buf);

        seek(c, index);

        return readNext(c, index);
    }

    @Override
    protected void setInner(long index, MarkedPacket data) {
        //TODO: not implemented yet
    }
}
