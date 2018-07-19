package net.katsuster.strview.media.test;

import net.katsuster.strview.util.*;
import net.katsuster.strview.util.bit.*;
import net.katsuster.strview.media.*;
import net.katsuster.strview.media.bit.*;

/**
 * <p>
 * 固定長パケットリスト。ビットリストから固定長パケットを読み出すテスト用。
 * </p>
 */
public class FixedPacketList
        extends AbstractPacketList<FixedPacket, Boolean> {
    public static int PACKET_SIZE = FixedPacket.PACKET_SIZE;

    private LargeBitList buf;

    public FixedPacketList() {
        super(LENGTH_UNKNOWN);
    }

    public FixedPacketList(LargeBitList l) {
        super(LENGTH_UNKNOWN);

        buf = l;
    }

    @Override
    public String getTypeName() {
        return "Fixed Size Packet List";
    }

    @Override
    public boolean hasTreeStructure() {
        return false;
    }

    @Override
    public void count() {
        length(buf.length() / FixedPacket.PACKET_SIZE / 8);
    }

    @Override
    protected void seek(StreamReader<Boolean> c, long index) {
        c.position(index * FixedPacket.PACKET_SIZE * 8);
    }

    @Override
    protected FixedPacket readNextInner(StreamReader<Boolean> c, PacketRange<LargeList<Boolean>> pr) {
        FixedPacket packet = new FixedPacket(new FixedHeader());
        packet.setRange(pr);
        packet.read(c);

        return packet;
    }

    @Override
    protected FixedPacket getInner(long index) {
        FromBitListConverter c = new FromBitListConverter(buf);

        seek(c, index);

        return readNext(c, index);
    }

    @Override
    protected void setInner(long index, FixedPacket data) {
        //TODO: not implemented yet
    }
}
