package net.katsuster.strview.media.test;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * 固定長パケットリスト。ビットリストから固定長パケットを読み出すテスト用。
 * </p>
 *
 * @author katsuhiro
 */
public class FixedPacketList extends AbstractPacketList<FixedPacket> {
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
    public String getShortName() {
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
    protected void seek(StreamReader<?> c, long index) {
        c.position(index * FixedPacket.PACKET_SIZE * 8);
    }

    @Override
    protected Packet readNextInner(StreamReader<?> c, PacketRange pr) {
        FixedPacket packet = new FixedPacket(new FixedHeader());
        packet.setRange(pr);
        packet.read(c);

        return packet;
    }

    @Override
    protected FixedPacket getInner(long index) {
        FromBitListConverter c = new FromBitListConverter(buf);

        seek(c, index);

        return (FixedPacket)readNext(c, index);
    }

    @Override
    protected void setInner(long index, FixedPacket data) {
        //TODO: not implemented yet
    }
}
