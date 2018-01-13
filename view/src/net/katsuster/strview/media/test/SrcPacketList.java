package net.katsuster.strview.media.test;

import java.util.*;
import java.io.*;

import net.katsuster.strview.io.*;
import net.katsuster.strview.media.*;
import net.katsuster.strview.util.*;

/**
 * <p>
 * ソースパケットリスト。
 * ビットリストではないデータからビットリストを生成するテスト用。
 * </p>
 */
public class SrcPacketList<T extends LargeList<?>>
        extends AbstractPacketList<SrcPacket<T>, T> {
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
    protected Packet<T> readNextInner(StreamReader<?> c, PacketRange<T> pr) {
        //FIXME: This is not works correctly...
        SrcPacket<T> packet = new SrcPacket<>(new SrcHeader<>(""));
        packet.setRange(pr);
        packet.read(c);

        return packet;
    }

    @Override
    protected SrcPacket<T> getInner(long index) {
        FromBitListConverter c = new FromBitListConverter(new MemoryBitList(0));

        seek(c, index);

        return (SrcPacket<T>)readNext(c, index);
    }

    @Override
    protected void setInner(long index, SrcPacket<T> data) {
        //TODO: not implemented yet
    }
}
