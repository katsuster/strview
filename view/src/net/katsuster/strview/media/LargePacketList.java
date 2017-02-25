package net.katsuster.strview.media;

import net.katsuster.strview.util.*;

/**
 * <p>
 * int 型で扱える長さを超えるパケットリストのインタフェースです。
 * </p>
 *
 * @author katsuhiro
 */
public interface LargePacketList<T extends Packet> extends LargeList<T> {
    /**
     * <p>
     * パケットリストの短い名前を取得します。
     * </p>
     *
     * @return パケットの短い名前
     */
    public String getShortName();

    /**
     * <p>
     * パケットが木構造を持つかどうかを返します。
     * </p>
     *
     * @return パケットが木構造を持つなら true、持たないなら false
     */
    public boolean hasTreeStructure();
}
