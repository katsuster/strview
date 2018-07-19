package net.katsuster.strview.media.rmff;

import net.katsuster.strview.util.*;
import net.katsuster.strview.util.bit.*;
import net.katsuster.strview.media.*;
import net.katsuster.strview.media.bit.*;

/**
 * <p>
 * RMFF(RealMedia File Format) チャンクリスト。
 * </p>
 */
public class RMFFChunkList
        extends AbstractPacketList<RMFFChunk, Boolean> {
    private LargeBitList buf;

    public RMFFChunkList() {
        super(LENGTH_UNKNOWN);
    }

    public RMFFChunkList(LargeBitList l) {
        super(LENGTH_UNKNOWN);

        buf = l;
    }

    @Override
    public String getTypeName() {
        return "RMFF (RealMedia File Format)";
    }

    @Override
    public void count() {
        FromBitListConverter c = new FromBitListConverter(buf);

        countSlow(c);
    }

    @Override
    protected RMFFChunk readNextInner(StreamReader<Boolean> c, PacketRange<LargeList<Boolean>> pr) {
        RMFFHeader tagh = createHeader(c, pr);

        RMFFChunk packet = new RMFFChunk(tagh);
        packet.setRange(pr);
        packet.read(c);

        return packet;
    }

    @Override
    protected RMFFChunk getInner(long index) {
        FromBitListConverter c = new FromBitListConverter(buf);

        seek(c, index);

        return readNext(c, index);
    }

    @Override
    protected void setInner(long index, RMFFChunk data) {
        //TODO: not implemented yet
    }

    protected RMFFHeader createHeader(StreamReader<Boolean> c, PacketRange<LargeList<Boolean>> pr) {
        RMFFHeader tmph = new RMFFHeader();
        tmph.peek(c);

        RMFFHeader tagh;
        int id = tmph.object_id.intValue();

        if (id == RMFFConsts.OBJECT.MDPR) {
            RMFFHeaderMDPR tmph_mdpr = new RMFFHeaderMDPRAny();
            tmph_mdpr.peek(c);

            tagh = RMFFConsts.mdprFactory.createPacketHeader(tmph_mdpr.getMimeTypeName());
            if (tagh == null) {
                tagh = tmph_mdpr;
            }
        } else {
            tagh = RMFFConsts.rmffFactory.createPacketHeader(id);
        }
        if (tagh == null) {
            //unknown
            tagh = tmph;
        }

        return tagh;
    }
}
