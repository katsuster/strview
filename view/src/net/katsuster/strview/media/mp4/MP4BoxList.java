package net.katsuster.strview.media.mp4;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * MP4 Box リスト。
 * </p>
 */
public class MP4BoxList<T extends LargeList<?>>
        extends AbstractPacketList<MP4Box<T>, T> {
    private LargeBitList buf;

    public MP4BoxList() {
        super(LENGTH_UNKNOWN);
    }

    public MP4BoxList(LargeBitList l) {
        super(LENGTH_UNKNOWN);

        buf = l;
    }

    @Override
    public String getShortName() {
        return "MP4";
    }

    @Override
    public void count() {
        FromBitListConverter c = new FromBitListConverter(buf);

        countSlow(c);
    }

    @Override
    protected MP4Box<T> readNextInner(StreamReader<?> c, PacketRange<T> pr) {
        MP4Header<T> tagh = createHeader(c, pr);

        MP4Box<T> packet = new MP4Box<>(tagh);
        packet.setRange(pr);
        packet.read(c);

        return packet;
    }

    @Override
    protected MP4Box<T> getInner(long index) {
        FromBitListConverter c = new FromBitListConverter(buf);

        seek(c, index);

        return (MP4Box<T>)readNext(c, index);
    }

    @Override
    protected void setInner(long index, MP4Box<T> data) {
        //TODO: not implemented yet
    }

    protected MP4Header<T> createHeader(StreamReader<?> c, PacketRange<T> pr) {
        MP4Header<T> tmph = new MP4Header<>();
        tmph.peek(c);

        MP4Header<T> tagh = MP4Consts.mp4Factory.createPacketHeader(tmph.type.intValue());
        if (tagh == null) {
            //unknown
            tagh = tmph;
        }

        return tagh;
    }
}
