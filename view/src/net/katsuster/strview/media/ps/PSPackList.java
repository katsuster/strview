package net.katsuster.strview.media.ps;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * MPEG2-PS (Program Stream) パックリスト。
 * </p>
 *
 * @author katsuhiro
 */
public class PSPackList extends AbstractPacketList<PSPack> {
    private LargeBitList buf;

    public PSPackList() {
        super(LENGTH_UNKNOWN);
    }

    public PSPackList(LargeBitList l) {
        super(LENGTH_UNKNOWN);

        buf = l;
    }

    @Override
    public String getShortName() {
        return "MPEG1 System / MPEG2 PS (Program Stream)";
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
    protected PSPack readNextInner(StreamReader<?> c, PacketRange pr) {
        PSHeader tagh = createHeader(c, pr);

        PSPack packet = new PSPack(tagh);
        packet.setRange(pr);
        packet.read(c);

        return packet;
    }

    @Override
    protected PSPack getInner(long index) {
        FromBitListConverter c = new FromBitListConverter(buf);

        seek(c, index);

        return (PSPack)readNext(c, index);
    }

    @Override
    protected void setInner(long index, PSPack data) {
        //TODO: not implemented yet
    }

    protected PSHeader createHeader(StreamReader<?> c, PacketRange pr) {
        PSHeader tagh;

        PSHeader tmph = new PSHeader();
        tmph.peek(c);

        tagh = PSConsts.psFactory.createPacketHeader(
                tmph.stream_id.intValue());
        if (tagh == null) {
            //unknown
            tagh = tmph;
        }

        return tagh;
    }
}
