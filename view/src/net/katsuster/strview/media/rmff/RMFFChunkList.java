package net.katsuster.strview.media.rmff;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * RMFF(RealMedia File Format) チャンクリスト。
 * </p>
 */
public class RMFFChunkList extends AbstractPacketList<RMFFChunk> {
    private LargeBitList buf;

    public RMFFChunkList() {
        super(LENGTH_UNKNOWN);
    }

    public RMFFChunkList(LargeBitList l) {
        super(LENGTH_UNKNOWN);

        buf = l;
    }

    @Override
    public String getShortName() {
        return "RMFF (RealMedia File Format)";
    }

    @Override
    public void count() {
        FromBitListConverter c = new FromBitListConverter(buf);

        countSlow(c);
    }

    @Override
    protected RMFFChunk readNextInner(StreamReader<?> c, PacketRange pr) {
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

        return (RMFFChunk)readNext(c, index);
    }

    @Override
    protected void setInner(long index, RMFFChunk data) {
        //TODO: not implemented yet
    }

    protected RMFFHeader createHeader(StreamReader<?> c, PacketRange pr) {
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
