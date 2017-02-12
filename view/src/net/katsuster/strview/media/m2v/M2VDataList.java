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
        M2VHeader tagh = null;
        M2VData packet;

        M2VHeader tmph = new M2VHeader();
        tmph.peek(c);

        if (tmph.start_code.intValue() == START_CODE.EXTENSION) {
            M2VHeaderExt tmphext = new M2VHeaderExt();
            tmphext.peek(c);

            tagh = M2VConsts.m2vExtFactory.createPacketHeader(
                    tmphext.extension_start_code_identifier.intValue());
        }
        if (tagh == null) {
            tagh = M2VConsts.m2vFactory.createPacketHeader(
                    tmph.start_code.intValue());
        }
        if (tagh == null) {
            //未対応のタグ
            tagh = new M2VHeader();
        }

        packet = new M2VData(tagh);

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

    }
}
