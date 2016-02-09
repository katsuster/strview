package net.katsuster.strview.util;

import java.util.*;

/**
 * <p>
 * バッファの半開区間 [start, end) を表すクラスです。
 * </p>
 *
 * <p>
 * start は区間に入りますが、end は区間に入りません。
 * つまり start = 0, end = 5 であれば、
 * isHit(0) は true、isHit(4) も true ですが、
 * isHit(5) は false、isHit(6) も false です。
 * </p>
 *
 * @author katsuhiro
 */
public class Range {
    private long start;
    private long end;
    private List<?> buf;

    /**
     * <p>
     * バッファを指定せず開始点と終了点を指定して半開区間を構築します。
     * </p>
     *
     * @param s 区間の開始地点
     * @param e 区間の終了地点
     */
    public Range(long s, long e) {
        this(s, e, Collections.emptyList());
    }

    /**
     * <p>
     * バッファと開始点と終了点を指定して半開区間を構築します。
     * </p>
     *
     * @param s 区間の開始地点
     * @param e 区間の終了地点
     * @param b この区間を含むバッファ
     */
    public Range(long s, long e, List<?> b) {
        if (e < s) {
            throw new IllegalArgumentException(
                    "start(" + s + ")" + "is larger than "
                            + "end(" + e + ").");
        }
        if (b != Collections.emptyList() && b.size() <= e) {
            throw new IllegalArgumentException(
                    "end(" + e + ")" + "is larger than "
                            + "b.size(" + b.size() + ").");
        }
        buf = b;
        start = s;
        end = e;
    }

    /**
     * <p>
     * 区間の開始地点を取得します。
     * </p>
     *
     * <p>
     * getStart() の点は区間に入りますが、getEnd() の点は区間に入りません。
     * </p>
     *
     * @return 区間の開始地点
     */
    public long getStart() {
        return start;
    }

    /**
     * <p>
     * 区間の開始地点を設定します。
     * </p>
     *
     * <p>
     * getStart() の点は区間に入りますが、getEnd() の点は区間に入りません。
     * </p>
     *
     * @param p 区間の開始地点
     */
    public void setStart(long p) {
        start = p;
    }

    /**
     * <p>
     * 区間の終了地点を取得します。
     * </p>
     *
     * <p>
     * getStart() の点は区間に入りますが、getEnd() の点は区間に入りません。
     * </p>
     *
     * @return 区間の終了地点
     */
    public long getEnd() {
        return end;
    }

    /**
     * <p>
     * 区間の終了地点を設定します。
     * </p>
     *
     * <p>
     * getStart() の点は区間に入りますが、getEnd() の点は区間に入りません。
     * </p>
     *
     * @param p 区間の終了地点
     */
    public void setEnd(long p) {
        end = p;
    }

    /**
     * <p>
     * 区間を含むバッファを取得します。
     * </p>
     *
     * @return 区間を含むバッファ
     */
    public List<?> getBuffer() {
        return buf;
    }

    /**
     * <p>
     * 区間の長さを取得します。
     * </p>
     *
     * @return 区間の長さ
     */
    public long getLength() {
        return (end - start);
    }

    /**
     * <p>
     * 区間の長さを設定します。
     * </p>
     *
     * <p>
     * 長さを設定すると、開始点は変更されず、
     * 終了点が適切な位置に変更されます。
     * </p>
     *
     * @param l 区間の長さ
     */
    public void setLength(long l) {
        end = start + l;
    }

    /**
     * <p>
     * 指定したインデックスが区間内に入るかどうかを判定します。
     * </p>
     *
     * @param i インデックス
     * @return インデックスが区間内なら true、区間外なら false
     */
    public boolean isHit(long i) {
        if (getStart() <= i && i < getEnd()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return String.format("start:%d - end:%d(length:%d)",
                getStart(), getEnd(), getLength());
    }
}
