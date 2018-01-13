package net.katsuster.strview.media;

import net.katsuster.strview.util.*;

/**
 * <p>
 * int 型で扱える長さを超えるパケットリストのインタフェースです。
 * </p>
 */
public interface LargePacketList<T extends Packet>
        extends LargeList<T> {
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

    /**
     * <p>
     * ビット列に変換します。
     * </p>
     *
     * <p>
     * パケット列とメソッドが返すビット列は等価ではありません。
     * すなわちビット列はパケット列の全ての情報を表しているとは限りません。
     * </p>
     *
     * @return ビット列、ビット列への変換ができない場合は null
     */
    //public LargeBitList toBitList();
}
