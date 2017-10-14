package net.katsuster.strview.media.flv;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * Created by katsuhiro on 2017/03/15.
 */
public class FLVTagList extends AbstractPacketList<FLVTag> {
    private LargeBitList buf;

    public FLVTagList() {
        super(LENGTH_UNKNOWN);
    }

    public FLVTagList(LargeBitList l) {
        super(LENGTH_UNKNOWN);

        buf = l;
    }

    @Override
    public String getShortName() {
        return "Flash Video";
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
    protected FLVTag readNextInner(StreamReader<?> c, PacketRange pr) {
        FLVHeader tagh = createHeader(c, pr);

        FLVTag packet = new FLVTag(tagh);
        packet.setRange(pr);
        packet.read(c);

        return packet;
    }

    @Override
    protected FLVTag getInner(long index) {
        FromBitListConverter c = new FromBitListConverter(buf);

        seek(c, index);

        return (FLVTag)readNext(c, index);
    }

    @Override
    protected void setInner(long index, FLVTag data) {
        //TODO: not implemented yet
    }

    protected FLVHeader createHeader(StreamReader<?> c, PacketRange pr) {
        FLVHeader tagh;

        if (pr.getNumber() == 0) {
            tagh = new FLVHeaderFile();
        } else {
            FLVHeaderES tmph = new FLVHeaderES();
            tmph.peek(c);

            tagh = FLVConsts.flvFactory.createPacketHeader(
                    tmph.tag_type.intValue());
            if (tagh == null) {
                //unknown
                tagh = tmph;
            }
        }

        return tagh;
    }
}
