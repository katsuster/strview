package net.katsuster.strview.media.asf;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * ASF (Advanced Systems Format) Object リスト。
 * </p>
 */
public class ASFObjectList<T extends LargeList<?>>
        extends AbstractPacketList<ASFObject<T>, T> {
    private LargeBitList buf;

    public ASFObjectList() {
        super(LENGTH_UNKNOWN);
    }

    public ASFObjectList(LargeBitList l) {
        super(LENGTH_UNKNOWN);

        buf = l;
    }

    @Override
    public String getShortName() {
        return "ASF";
    }

    @Override
    public void count() {
        FromBitListConverter c = new FromBitListConverter(buf);

        countSlow(c);
    }

    @Override
    protected ASFObject<T> readNextInner(StreamReader<?, ?> c, PacketRange<T> pr) {
        ASFHeader<T> tagh = createHeader(c, pr);

        ASFObject<T> packet = new ASFObject<>(tagh);
        packet.setRange(pr);
        packet.read(c);

        return packet;
    }

    @Override
    protected ASFObject<T> getInner(long index) {
        FromBitListConverter c = new FromBitListConverter(buf);

        seek(c, index);

        return (ASFObject<T>)readNext(c, index);
    }

    @Override
    protected void setInner(long index, ASFObject<T> data) {
        //TODO: not implemented yet
    }

    protected ASFHeader<T> createHeader(StreamReader<?, ?> c, PacketRange<T> pr) {
        ASFHeader<T> tmph = new ASFHeader<>();
        tmph.peek(c);

        ASFHeader<T> tagh = ASFConsts.asfFactory.createPacketHeader(tmph.object_id);
        if (tagh == null) {
            //unknown
            tagh = tmph;
        }

        return tagh;
    }
}
