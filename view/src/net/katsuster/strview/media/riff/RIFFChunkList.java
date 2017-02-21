package net.katsuster.strview.media.riff;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * RIFF (Resource Interchange File Format) チャンクのリスト。
 * </p>
 *
 * @author katsuhiro
 */
public class RIFFChunkList extends AbstractPacketList<RIFFChunk> {
    private LargeBitList buf;

    public RIFFChunkList() {
        super(LENGTH_UNKNOWN);
    }

    public RIFFChunkList(LargeBitList l) {
        super(LENGTH_UNKNOWN);

        buf = l;
    }

    @Override
    public void count() {
        FromBitListConverter c = new FromBitListConverter(buf);

        countSlow(c);
    }

    @Override
    protected RIFFChunk readNextInner(PacketReader<?> c, PacketRange pr) {
        RIFFChunk packet;

        RIFFHeader tmph = new RIFFHeader();
        tmph.peek(c);

        RIFFHeader tagh = RIFFConsts.riffFactory.createPacketHeader(
                tmph.ckID.intValue());
        if (tagh == null) {
            //未対応のチャンク
            tagh = new RIFFHeader();
        }

        packet = new RIFFChunk(tagh);
        packet.setRange(pr);
        packet.read(c);

        //終端は必ず 2バイト境界なので、2バイト境界まで読み飛ばす
        c.alignShort();

        return packet;
    }

    @Override
    protected RIFFChunk getInner(long index) {
        FromBitListConverter c = new FromBitListConverter(buf);

        seek(c, index);

        return (RIFFChunk)readNext(c, index);
    }

    @Override
    protected void setInner(long index, RIFFChunk data) {
        //TODO: not implemented yet
    }
}
