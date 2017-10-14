package net.katsuster.strview.media.test;

import java.util.*;
import java.io.*;

import net.katsuster.strview.io.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * ソースパケットリスト。
 * ビットリストではないデータからビットリストを生成するテスト用。
 * </p>
 *
 * @author katsuhiro
 */
public class SrcPacketList extends AbstractPacketList<SrcPacket> {
    private List<File> buf;

    public SrcPacketList() {
        super(0);
    }

    public SrcPacketList(List<File> l) {
        super(l.size());

        buf = l;
    }

    @Override
    public String getShortName() {
        return "Source Packet List";
    }

    @Override
    public boolean hasTreeStructure() {
        return false;
    }

    @Override
    public void count() {
        length(buf.size());
    }

    @Override
    protected void seek(StreamReader<?> c, long index) {
        c.position(index);
    }

    @Override
    protected Packet readNextInner(StreamReader<?> c, PacketRange pr) {
        //FIXME: This is not works correctly...
        SrcPacket packet = new SrcPacket(new SrcHeader(""));
        packet.setRange(pr);
        packet.read(c);

        return packet;
    }

    @Override
    protected SrcPacket getInner(long index) {
        FromBitListConverter c = new FromBitListConverter(new MemoryBitList(0));

        seek(c, index);

        return (SrcPacket)readNext(c, index);
    }

    @Override
    protected void setInner(long index, SrcPacket data) {
        //TODO: not implemented yet
    }
}
