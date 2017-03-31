package net.katsuster.strview.media.asf;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;
import net.katsuster.strview.media.asf.*;

/**
 * <p>
 * ASF (Advanced Systems Format) Object リスト。
 * </p>
 *
 * @author katsuhiro
 */
public class ASFObjectList extends AbstractPacketList<ASFObject> {
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
    protected ASFObject readNextInner(PacketReader<?> c, PacketRange pr) {
        ASFHeader tagh = createHeader(c, pr);

        ASFObject packet = new ASFObject(tagh);
        packet.setRange(pr);
        packet.read(c);

        return packet;
    }

    @Override
    protected ASFObject getInner(long index) {
        FromBitListConverter c = new FromBitListConverter(buf);

        seek(c, index);

        return (ASFObject)readNext(c, index);
    }

    @Override
    protected void setInner(long index, ASFObject data) {
        //TODO: not implemented yet
    }

    protected ASFHeader createHeader(PacketReader<?> c, PacketRange pr) {
        ASFHeader tmph = new ASFHeader();
        tmph.peek(c);

        ASFHeader tagh = ASFConsts.asfFactory.createPacketHeader(tmph.object_id);
        if (tagh == null) {
            //unknown
            tagh = tmph;
        }

        return tagh;
    }
}
