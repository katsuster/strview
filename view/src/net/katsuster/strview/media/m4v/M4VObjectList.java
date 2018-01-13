package net.katsuster.strview.media.m4v;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * MPEG4 Part 2 Visual オブジェクトリスト。
 * </p>
 */
public class M4VObjectList<U extends LargeList<?>>
        extends AbstractPacketList<M4VObject, U> {
    private LargeBitList buf;

    public M4VObjectList() {
        super(LENGTH_UNKNOWN);
    }

    public M4VObjectList(LargeBitList l) {
        super(LENGTH_UNKNOWN);

        buf = l;
    }

    @Override
    public String getShortName() {
        return "MPEG4 Part 2 Video";
    }

    @Override
    public boolean hasTreeStructure() {
        return false;
    }

    @Override
    public void count() {
        FromBitListConverter c = new FromBitListConverter(buf);

        countSlow(c);
    }

    @Override
    protected M4VObject<U> readNextInner(StreamReader<?> c, PacketRange<U> pr) {
        M4VHeader<U> tagh = createHeader(c, pr);

        M4VObject<U> packet = new M4VObject<>(tagh);
        packet.setRange(pr);
        packet.read(c);

        return packet;
    }

    @Override
    protected M4VObject getInner(long index) {
        FromBitListConverter c = new FromBitListConverter(buf);

        seek(c, index);

        return (M4VObject)readNext(c, index);
    }

    @Override
    protected void setInner(long index, M4VObject data) {
        //TODO: not implemented yet
    }

    protected M4VHeader<U> createHeader(StreamReader<?> c, PacketRange<U> pr) {
        M4VHeader<U> tagh;

        M4VHeader<U> tmph = new M4VHeader<>();
        tmph.peek(c);

        tagh = M4VConsts.m4vFactory.createPacketHeader(
                tmph.start_code.intValue());
        if (tagh == null) {
            //unknown
            tagh = tmph;
        }

        return tagh;
    }
}
