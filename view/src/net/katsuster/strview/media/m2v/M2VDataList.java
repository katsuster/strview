package net.katsuster.strview.media.m2v;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;
import net.katsuster.strview.media.m2v.M2VConsts.*;

/**
 * <p>
 * MPEG2 Video データリスト。
 * </p>
 *
 * @author katsuhiro
 */
public class M2VDataList extends AbstractPacketList<M2VData> {
    private LargeBitList buf;

    public M2VDataList() {
        super(LENGTH_UNKNOWN);
    }

    public M2VDataList(LargeBitList l) {
        super(LENGTH_UNKNOWN);

        buf = l;
    }

    @Override
    public void count() {
        FromBitListConverter c = new FromBitListConverter(buf);

        countSlow(c);
    }

    @Override
    protected M2VData readNextInner(PacketReader<?> c, PacketRange pr) {
        M2VHeader tagh = createHeader(c, pr);

        M2VData packet = new M2VData(tagh);
        packet.setRange(pr);
        packet.read(c);

        return packet;
    }

    @Override
    protected M2VData getInner(long index) {
        FromBitListConverter c = new FromBitListConverter(buf);

        seek(c, index);

        return (M2VData)readNext(c, index);
    }

    @Override
    protected void setInner(long index, M2VData data) {
        //TODO: not implemented yet
    }

    protected M2VHeader createHeader(PacketReader<?> c, PacketRange pr) {
        M2VHeader tagh;

        M2VHeader tmph = new M2VHeader();
        tmph.peek(c);

        switch (tmph.start_code.intValue()) {
        case START_CODE.EXTENSION:
            M2VHeaderExt tmphext = new M2VHeaderExt();
            tmphext.peek(c);

            tagh = M2VConsts.m2vExtFactory.createPacketHeader(
                    tmphext.extension_start_code_identifier.intValue());
            break;
        default:
            tagh = M2VConsts.m2vFactory.createPacketHeader(
                    tmph.start_code.intValue());
        }
        if (tagh == null) {
            //unknown
            tagh = tmph;
        }

        return tagh;
    }
}
