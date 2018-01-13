package net.katsuster.strview.media.m2v;

import java.util.*;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;
import net.katsuster.strview.media.m2v.M2VConsts.*;

/**
 * <p>
 * MPEG2 Video データリスト。
 * </p>
 */
public class M2VDataList<T extends LargeList<?>>
        extends AbstractPacketList<M2VData<T>, T> {
    private LargeBitList buf;

    private NavigableMap<Long, M2VHeaderSequence<T>> cacheSeq;
    private NavigableMap<Long, M2VHeaderExtSequence<T>> cacheExtSeq;
    private NavigableMap<Long, M2VHeaderExtSequenceScalable<T>> cacheExtSeqSca;
    private NavigableMap<Long, M2VHeaderExtPictureCoding<T>> cacheExtPicCod;

    public M2VDataList() {
        super(LENGTH_UNKNOWN);
    }

    public M2VDataList(LargeBitList l) {
        super(LENGTH_UNKNOWN);

        buf = l;

        cacheSeq = new TreeMap<>();
        cacheExtSeq = new TreeMap<>();
        cacheExtSeqSca = new TreeMap<>();
        cacheExtPicCod = new TreeMap<>();
    }

    @Override
    public String getShortName() {
        return "MPEG2 Video";
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
    protected M2VData<T> readNextInner(StreamReader<?> c, PacketRange<T> pr) {
        M2VHeader<T> tagh = createHeader(c, pr);

        M2VData<T> packet = new M2VData<>(tagh);
        packet.setRange(pr);
        packet.read(c);

        cachePacket(packet);

        return packet;
    }

    @Override
    protected M2VData<T> getInner(long index) {
        FromBitListConverter c = new FromBitListConverter(buf);

        seek(c, index);

        return (M2VData<T>)readNext(c, index);
    }

    @Override
    protected void setInner(long index, M2VData data) {
        //TODO: not implemented yet
    }

    protected M2VHeader<T> createHeader(StreamReader<?> c, PacketRange<T> pr) {
        M2VHeader<T> tagh;

        M2VHeader<T> tmph = new M2VHeader<>();
        tmph.peek(c);

        int sc = tmph.start_code.intValue();

        if (sc == START_CODE.EXTENSION) {
            M2VHeaderExt<T> tmphext = new M2VHeaderExt<>();
            tmphext.peek(c);

            int ec = tmphext.extension_start_code_identifier.intValue();
            if (ec == EXTENSION_START_CODE.PICTURE_DISPLAY) {
                tagh = createHeaderExtPictureDisplay(c, pr);
            } else {
                tagh = M2VConsts.m2vExtFactory.createPacketHeader(
                        tmphext.extension_start_code_identifier.intValue());
            }
            if (tagh == null) {
                tagh = tmphext;
            }
        } else if (START_CODE.SLICE_START <= sc
                && sc <= START_CODE.SLICE_END) {
            tagh = createHeaderSlice(c, pr);
        } else {
            tagh = M2VConsts.m2vFactory.createPacketHeader(
                    tmph.start_code.intValue());
        }
        if (tagh == null) {
            //unknown
            tagh = tmph;
        }

        return tagh;
    }

    protected M2VHeader<T> createHeaderSlice(StreamReader<?> c, PacketRange<T> pr) {
        Map.Entry<Long, M2VHeaderSequence<T>> entSeq = cacheSeq.floorEntry(pr.getNumber());
        Map.Entry<Long, M2VHeaderExtSequence<T>> entExtSeq = cacheExtSeq.floorEntry(pr.getNumber());
        Map.Entry<Long, M2VHeaderExtSequenceScalable<T>> entExtSeqSca = cacheExtSeqSca.floorEntry(pr.getNumber());
        if (entSeq == null) {
            return null;
        }

        return new M2VHeaderSlice<T>(entSeq, entExtSeq, entExtSeqSca);
    }

    protected M2VHeader<T> createHeaderExtPictureDisplay(StreamReader<?> c, PacketRange<T> pr) {
        Map.Entry<Long, M2VHeaderExtSequence<T>> entExtSeq = cacheExtSeq.floorEntry(pr.getNumber());
        Map.Entry<Long, M2VHeaderExtPictureCoding<T>> entExtPicCod = cacheExtPicCod.floorEntry(pr.getNumber());
        if (entExtSeq == null || entExtPicCod == null) {
            return null;
        }

        return new M2VHeaderExtPictureDisplay<T>(entExtSeq, entExtPicCod);
    }

    protected void cachePacket(M2VData<T> packet) {
        M2VHeader<T> h = packet.getHeader();
        PacketRange<T> pr = packet.getRange();

        if (h instanceof M2VHeaderSequence) {
            cacheSeq.put(pr.getNumber(), (M2VHeaderSequence<T>)h);
        }
        if (h instanceof M2VHeaderExtSequence) {
            cacheExtSeq.put(pr.getNumber(), (M2VHeaderExtSequence<T>)h);
        }
        if (h instanceof M2VHeaderExtSequenceScalable) {
            cacheExtSeqSca.put(pr.getNumber(), (M2VHeaderExtSequenceScalable<T>)h);
        }
        if (h instanceof M2VHeaderExtPictureCoding) {
            cacheExtPicCod.put(pr.getNumber(), (M2VHeaderExtPictureCoding<T>)h);
        }
    }
}
