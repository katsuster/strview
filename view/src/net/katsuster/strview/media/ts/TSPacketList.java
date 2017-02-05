package net.katsuster.strview.media.ts;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * MPEG2-TS(Transport Stream) パケットリスト。
 * </p>
 *
 * @author katsuhiro
 */
public class TSPacketList extends AbstractPacketList<TSPacket> {
    private LargeBitList buf;

    public TSPacketList() {
        super(LENGTH_UNKNOWN);
    }


    public TSPacketList(LargeBitList l) {
        super(LENGTH_UNKNOWN);

        buf = l;
    }

    @Override
    public void count() {
        length(buf.length() / 188 / 8);
    }

    @Override
    protected void seek(PacketReader<?> c, long index) {
        c.position(index * 188 * 8);
    }

    @Override
    protected Packet readNextInner(PacketReader<?> c, long index) {
        TSPacket packet = new TSPacket();

        packet.read(c);

        return packet;
    }

    @Override
    protected TSPacket getInner(long index) {
        FromBitListConverter c = new FromBitListConverter(buf);

        seek(c, index);

        return (TSPacket)readNext(c, index);
    }

    @Override
    protected void setInner(long index, TSPacket data) {

    }
}
