package net.katsuster.strview.media.ts;

import net.katsuster.strview.util.*;
import net.katsuster.strview.util.bit.*;
import net.katsuster.strview.media.*;
import net.katsuster.strview.media.bit.*;

/**
 * <p>
 * MPEG2-TS(Transport Stream) パケットリスト。
 * </p>
 */
public class TSPacketList
        extends AbstractPacketList<TSPacket, Boolean> {
    private LargeBitList buf;

    public TSPacketList() {
        super(LENGTH_UNKNOWN);
    }

    public TSPacketList(LargeBitList l) {
        super(LENGTH_UNKNOWN);

        buf = l;
    }

    @Override
    public String getTypeName() {
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
    protected void seek(StreamReader<Boolean> c, long index) {
        c.position(index * 188 * 8);
    }

    @Override
    protected TSPacket readNextInner(StreamReader<Boolean> c, PacketRange<LargeList<Boolean>> pr) {
        TSPacket packet = new TSPacket(new TSHeader());
        packet.setRange(pr);
        packet.read(c);

        return packet;
    }

    @Override
    protected TSPacket getInner(long index) {
        FromBitListConverter c = new FromBitListConverter(buf);

        seek(c, index);

        return readNext(c, index);
    }

    @Override
    protected void setInner(long index, TSPacket data) {
        //TODO: not implemented yet
    }
}
