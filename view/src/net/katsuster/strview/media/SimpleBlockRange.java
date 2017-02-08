package net.katsuster.strview.media;

import net.katsuster.strview.util.*;

/**
 * <p>
 * パケットの位置。
 * </p>
 *
 * @author katsuhiro
 */
public class SimpleBlockRange extends SimpleRange
        implements BlockRange, Cloneable {
    //パケットの通し番号
    private long number;

    /**
     * <p>
     * 番号 0、位置 0、長さ 0 のパケット位置を作成します。
     * </p>
     */
    public SimpleBlockRange() {
        this(0, 0, 0);
    }

    /**
     * <p>
     * 位置 0、長さ 0 のパケット位置を作成します。
     * </p>
     *
     * @param num パケットの番号
     */
    public SimpleBlockRange(long num) {
        this(num, 0, 0);
    }

    /**
     * <p>
     * 長さ 0 のパケット位置を作成します。
     * </p>
     *
     * @param num パケットの番号
     * @param addr パケットの位置
     */
    public SimpleBlockRange(long num, long addr) {
        this(num, addr, 0);
    }

    /**
     * <p>
     * パケット位置を作成します。
     * </p>
     *
     * @param num パケットの番号
     * @param addr パケットの位置
     * @param len パケットのサイズ
     */
    public SimpleBlockRange(long num, long addr, long len) {
        super(addr, addr + len);
        number = num;
    }

    /**
     * <p>
     * パケット位置を作成します。
     * </p>
     *
     * @param obj パケット位置
     */
    public SimpleBlockRange(BlockRange obj) {
        this(obj.getNumber(), obj.getStart(), obj.getLength());
    }

    @Override
    public SimpleBlockRange clone()
            throws CloneNotSupportedException {
        SimpleBlockRange obj = (SimpleBlockRange)super.clone();

        return obj;
    }

    @Override
    public long getNumber() {
        return number;
    }

    @Override
    public void setNumber(long num) {
        number = num;
    }
}
