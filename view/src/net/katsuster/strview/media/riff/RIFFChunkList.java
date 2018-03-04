package net.katsuster.strview.media.riff;

import java.util.*;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;
import net.katsuster.strview.media.riff.RIFFConsts.*;

/**
 * <p>
 * RIFF (Resource Interchange File Format) チャンクのリスト。
 * </p>
 */
public class RIFFChunkList<U extends LargeList<?>>
        extends AbstractPacketList<RIFFChunk<U>, U> {
    private LargeBitList buf;

    private NavigableMap<Long, RIFFHeaderStrh<U>> cacheStrh;

    public RIFFChunkList() {
        super(LENGTH_UNKNOWN);
    }

    public RIFFChunkList(LargeBitList l) {
        super(LENGTH_UNKNOWN);

        buf = l;

        cacheStrh = new TreeMap<>();
    }

    @Override
    public String getShortName() {
        return "RIFF (Resource Interchange File Format)";
    }

    @Override
    public void count() {
        FromBitListConverter c = new FromBitListConverter(buf);

        countSlow(c);
    }

    @Override
    protected RIFFChunk<U> readNextInner(StreamReader<?, ?> c, PacketRange<U> pr) {
        RIFFHeader<U> tagh = createHeader(c, pr);

        RIFFChunk<U> packet = new RIFFChunk<>(tagh);
        packet.setRange(pr);
        packet.read(c);

        //終端は必ず 2バイト境界なので、2バイト境界まで読み飛ばす
        c.align(16);

        cachePacket(packet);

        return packet;
    }

    @Override
    protected RIFFChunk<U> getInner(long index) {
        FromBitListConverter c = new FromBitListConverter(buf);

        seek(c, index);

        return (RIFFChunk<U>)readNext(c, index);
    }

    @Override
    protected void setInner(long index, RIFFChunk data) {
        //TODO: not implemented yet
    }

    protected RIFFHeader createHeader(StreamReader<?, ?> c, PacketRange pr) {
        RIFFHeader<U> tagh;

        RIFFHeader<U> tmph = new RIFFHeader<>();
        tmph.peek(c);

        int id = tmph.ckID.intValue();

        if (id == CHUNK_ID.STRF) {
            tagh = createHeaderStrf(c, pr);
        } else {
            tagh = RIFFConsts.riffFactory.createPacketHeader(id);
        }
        if (tagh == null) {
            //unknown
            tagh = tmph;
        }

        return tagh;
    }

    protected RIFFHeader<U> createHeaderStrf(StreamReader<?, ?> c, PacketRange pr) {
        Map.Entry<Long, RIFFHeaderStrh<U>> entStrh = cacheStrh.floorEntry(pr.getNumber());
        if (entStrh == null) {
            return null;
        }

        int fccType = entStrh.getValue().fccType.intValue();
        RIFFHeader<U> tagh = null;

        switch (fccType) {
        case FCC_TYPE.AUDS:
            tagh = new RIFFHeaderStrfA<>();
            break;
        case FCC_TYPE.VIDS:
            tagh = new RIFFHeaderStrfV<>();
            break;
        }

        return tagh;
    }

    protected void cachePacket(RIFFChunk<U> packet) {
        RIFFHeader<U> h = packet.getHeader();
        PacketRange<U> pr = packet.getRange();

        if (h instanceof RIFFHeaderStrh) {
            cacheStrh.put(pr.getNumber(), (RIFFHeaderStrh<U>)h);
        }
    }
}
