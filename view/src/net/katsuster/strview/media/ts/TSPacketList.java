package net.katsuster.strview.media.ts;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * MPEG2-TS(Transport Stream) パケットリスト。
 * </p>
 */
public class TSPacketList<T extends LargeList<?>>
        extends AbstractPacketList<TSPacket<T>, T> {
    private LargeBitList buf;

    public TSPacketList() {
        super(LENGTH_UNKNOWN);
    }

    public TSPacketList(LargeBitList l) {
        super(LENGTH_UNKNOWN);

        buf = l;
    }

    @Override
    public String getShortName() {
        return "MPEG2 TS (Transport Stream)";
    }

    @Override
    public boolean hasTreeStructure() {
        return false;
    }

    @Override
    public void count() {
        length(buf.length() / 188 / 8);
    }

    @Override
    protected void seek(StreamReader<?> c, long index) {
        c.position(index * 188 * 8);
    }

    @Override
    protected Packet<T> readNextInner(StreamReader<?> c, PacketRange<T> pr) {
        TSPacket<T> packet = new TSPacket<>(new TSHeader<>());
        packet.setRange(pr);
        packet.read(c);

        return packet;
    }

    @Override
    protected TSPacket<T> getInner(long index) {
        FromBitListConverter c = new FromBitListConverter(buf);

        seek(c, index);

        return (TSPacket<T>)readNext(c, index);
    }

    @Override
    protected void setInner(long index, TSPacket<T> data) {
        //TODO: not implemented yet
    }
}
