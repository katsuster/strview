package net.katsuster.strview.media;

import net.katsuster.strview.util.*;

/**
 * <p>
 * データの存在する範囲を表します。
 * </p>
 *
 * @author katsuhiro
 */
public interface BlockRange extends Range, Cloneable {
    /**
     * <p>
     * パケットの通し番号を返します。
     * </p>
     *
     * <p>
     * パーサによって番号の付け方が異なりますが、
     * 通常はストリーム先頭を 0 とした番号が設定されます。
     * </p>
     *
     * @return パケット番号
     */
    public long getNumber();

    /**
     * <p>
     * パケットの通し番号を設定します。
     * </p>
     *
     * <p>
     * パーサによって番号の付け方が異なりますが、
     * 通常はストリーム先頭を 0 とした番号が設定されます。
     * </p>
     *
     * @param num パケット番号
     */
    public void setNumber(long num);
}
