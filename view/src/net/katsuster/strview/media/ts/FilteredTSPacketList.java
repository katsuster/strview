package net.katsuster.strview.media.ts;

import net.katsuster.strview.media.*;
import net.katsuster.strview.util.*;

/**
 * <p>
 * MPEG2-TS(Transport Stream) パケットリスト。
 * </p>
 */
public class FilteredTSPacketList
        extends AbstractPacketList<TSPacket, TSPacket> {
    private LargePacketList<TSPacket> buf;
    private int pid;

    public FilteredTSPacketList() {
        super(LENGTH_UNKNOWN);
    }

    public FilteredTSPacketList(LargePacketList<TSPacket> l, int p) {
        super(LENGTH_UNKNOWN);

        buf = l;
        pid = p;
    }

    @Override
    public String getTypeName() {
        return "MPEG2 TS (Transport Stream) PID:" + pid;
    }

    @Override
    public boolean hasTreeStructure() {
        return false;
    }

    @Override
    public void count() {
        SimplePacketStreamReader<TSPacket> c = new SimplePacketStreamReader<>(buf);

        countSlow(c);
    }

    /*@Override
    protected void seek(StreamReader<TSPacket> c, long index) {
        //c.read()
        c.position(index * 188 * 8);
    }*/

    @Override
    protected TSPacket readNextInner(StreamReader<TSPacket> c, PacketRange<LargeList<TSPacket>> pr) {
        TSPacket packet;

        do {
            long p = c.position();

            packet = c.read(null);
            packet.setRange(pr);
            packet.getRange().setStart(p);
            packet.getRange().setLength(1);
        } while (packet.getHeader().pid.intValue() != pid);

        return packet;
    }

    @Override
    protected TSPacket getInner(long index) {
        SimplePacketStreamReader<TSPacket> c = new SimplePacketStreamReader<>(buf);

        seek(c, index);

        return readNext(c, index);
    }

    @Override
    protected void setInner(long index, TSPacket data) {
        //TODO: not implemented yet
    }
}
