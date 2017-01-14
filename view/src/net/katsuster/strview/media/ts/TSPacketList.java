package net.katsuster.strview.media.ts;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * @author katsuhiro
 */
public class TSPacketList extends AbstractLargeList<TSPacket> {
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
    protected TSPacket getInner(long index) {
        TSPacket p = new TSPacket();
        FromBitListConverter c = new FromBitListConverter(buf);

        c.doInit();
        c.position(index * 188 * 8);
        p.setNumber(index);
        p.setLevel(0);
        p.read(c);
        c.doFinal();

        return p;
    }

    @Override
    protected void setInner(long index, TSPacket data) {

    }
}
