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
public class FixedSizePacketList extends AbstractPacketList<FixedSizePacket> {
    public static int PACKET_SIZE = FixedSizePacket.PACKET_SIZE;

    private LargeBitList buf;

    public FixedSizePacketList() {
        super(LENGTH_UNKNOWN);
    }

    public FixedSizePacketList(LargeBitList l) {
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
        length(buf.length() / FixedSizePacket.PACKET_SIZE / 8);
    }

    @Override
    protected void seek(PacketReader<?> c, long index) {
        c.position(index * FixedSizePacket.PACKET_SIZE * 8);
    }

    @Override
    protected Packet readNextInner(PacketReader<?> c, PacketRange pr) {
        FixedSizePacket packet = new FixedSizePacket(new FixedHeader());
        packet.setRange(pr);
        packet.read(c);

        return packet;
    }

    @Override
    protected FixedSizePacket getInner(long index) {
        FromBitListConverter c = new FromBitListConverter(buf);

        seek(c, index);

        return (FixedSizePacket)readNext(c, index);
    }

    @Override
    protected void setInner(long index, FixedSizePacket data) {
        //TODO: not implemented yet
    }
}
