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
     * <p>
     * クラス名 Class.getCanonicalName() を返すか、
     * より適切な名前を返すことが推奨されます。
     * </p>
     *
     * @return パケットの短い名前
     */
    public String getShortName();
}
