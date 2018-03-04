package net.katsuster.strview.media.rmff;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * RMFF(RealMedia File Format) チャンクリスト。
 * </p>
 */
public class RMFFChunkList<U extends LargeList<?>>
        extends AbstractPacketList<RMFFChunk<U>, U> {
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
    protected RMFFChunk<U> readNextInner(StreamReader<?, ?> c, PacketRange<U> pr) {
        RMFFHeader<U> tagh = createHeader(c, pr);

        RMFFChunk<U> packet = new RMFFChunk<>(tagh);
        packet.setRange(pr);
        packet.read(c);

        return packet;
    }

    @Override
    protected RMFFChunk<U> getInner(long index) {
        FromBitListConverter c = new FromBitListConverter(buf);

        seek(c, index);

        return (RMFFChunk<U>)readNext(c, index);
    }

    @Override
    protected void setInner(long index, RMFFChunk data) {
        //TODO: not implemented yet
    }

    protected RMFFHeader<U> createHeader(StreamReader<?, ?> c, PacketRange<U> pr) {
        RMFFHeader<U> tmph = new RMFFHeader<>();
        tmph.peek(c);

        RMFFHeader<U> tagh;
        int id = tmph.object_id.intValue();

        if (id == RMFFConsts.OBJECT.MDPR) {
            RMFFHeaderMDPR<U> tmph_mdpr = new RMFFHeaderMDPRAny<>();
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
