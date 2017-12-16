package net.katsuster.strview.gui;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * ツリービューでパケットを表示するためのクラスです。
 * </p>
 *
 * <p>
 * toString をオーバーライドして、パケットの名前を返します。
 * </p>
 */
public class PacketTreeNode {
    private LargeList<? extends Packet> list;
    private PacketRange range;

    public PacketTreeNode(LargeList<? extends Packet> l, PacketRange pr) {
        list = l;
        range = pr;
    }

    public LargeList<? extends Packet> getPacketList() {
        return list;
    }

    public PacketRange getRange() {
        return range;
    }

    @Override
    public String toString() {
        if (range != null) {
            Packet packet = list.get(range.getNumber());

            return packet.getTypeName();
        }

        return "????";
    }
}
