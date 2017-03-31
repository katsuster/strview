package net.katsuster.strview.media.asf;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * ASF (Advanced Systems Format) GUID
 * </p>
 *
 * <p>
 * A-B-C-D-E の 5つの部分からなる 128 ビット値です。
 * 各部分のビット長は下記の通り。
 * A(32 bits)-B(16 bits)-C(16 bits)-D(16 bits)-E(48 bits)
 * </p>
 *
 * <p>
 * バイトストリームに変換するときは、
 * A, B, C がリトルエンディアン、
 * D, E がビッグエンディアンであることに留意してください。
 * </p>
 *
 * <p>
 * 参考規格
 * </p>
 * <ul>
 * <li>Advanced Systems Format (ASF) Specification: Revision 01.20.06</li>
 * </ul>
 *
 * @author katsuhiro
 */
public class ASFGUID extends BlockAdapter
        implements Cloneable, Comparable<ASFGUID> {
    private UInt la;
    private UInt lb;
    private UInt lc;
    private UInt bd;
    private UInt be;

    /**
     * <p>
     * 全て 0 で初期化された GUID を生成します。
     * </p>
     */
    public ASFGUID() {
        this(0, 0, 0, 0, 0);
    }

    /**
     * <p>
     * 指定された値で初期化された GUID を生成します。
     * </p>
     *
     * @param a パート A、下位 32bit のみ使用されます。
     * @param b パート B、下位 16bit のみ使用されます。
     * @param c パート C、下位 16bit のみ使用されます。
     * @param d パート D、下位 16bit のみ使用されます。
     * @param e パート E、下位 48bit のみ使用されます。
     */
    public ASFGUID(int a, int b, int c, int d, long e) {
        la = new UInt(a & 0xffffffffL);
        lb = new UInt(b & 0xffffL);
        lc = new UInt(c & 0xffffL);
        bd = new UInt(d & 0xffffL);
        be = new UInt(e & 0xffffffffffffL);
    }

    @Override
    public ASFGUID clone() throws CloneNotSupportedException {
        ASFGUID obj = (ASFGUID)super.clone();

        obj.la = (UInt)la.clone();
        obj.lb = (UInt)lb.clone();
        obj.lc = (UInt)lc.clone();
        obj.bd = (UInt)bd.clone();
        obj.be = (UInt)be.clone();

        return obj;
    }

    @Override
    public boolean equals(Object obj) {
        ASFGUID o;

        if (obj instanceof ASFGUID) {
            o = (ASFGUID)obj;
        } else {
            return false;
        }

        if (la.equals(o.la) && lb.equals(o.lb) && lc.equals(o.lc)
                && bd.equals(o.bd) && be.equals(o.be)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int compareTo(ASFGUID obj) {
        if (la.compareTo(obj.la) != 0) {
            return la.compareTo(obj.la);
        }
        if (lb.compareTo(obj.lb) != 0) {
            return lb.compareTo(obj.lb);
        }
        if (lc.compareTo(obj.lc) != 0) {
            return lc.compareTo(obj.lc);
        }
        if (bd.compareTo(obj.bd) != 0) {
            return bd.compareTo(obj.bd);
        }
        if (be.compareTo(obj.be) != 0) {
            return be.compareTo(obj.be);
        }

        return 0;
    }

    @Override
    public int hashCode() {
        return la.intValue() + lb.intValue() + lc.intValue()
                + bd.intValue() + be.intValue();
    }

    @Override
    public void read(PacketReader<?> c) {
        read(c, this);
    }

    public static void read(PacketReader<?> c,
                            ASFGUID d) {
        c.enterBlock("GUID");

        d.la = c.readUIntR(32, d.la);
        d.lb = c.readUIntR(16, d.lb);
        d.lc = c.readUIntR(16, d.lc);
        d.bd = c.readUInt(16, d.bd);
        d.be = c.readUInt(48, d.be);

        c.leaveBlock();
    }

    @Override
    public void write(PacketWriter<?> c) {
        write(c, this);
    }

    public static void write(PacketWriter<?> c,
                             ASFGUID d) {
        c.enterBlock("GUID");

        c.writeUIntR(32, d.la, "la");
        c.writeUIntR(16, d.lb, "lb");
        c.writeUIntR(16, d.lc, "lc");
        c.writeUInt(16, d.bd, "bd");
        c.writeUInt(48, d.be, "be");

        c.mark("GUID",
                String.format("%08x-%04x-%04x-%04x-%012x",
                        d.la.intValue(), d.lb.intValue(), d.lc.intValue(),
                        d.bd.intValue(), d.be.longValue()),
                d.getObjectIdName());

        c.leaveBlock();
    }

    public String getObjectIdName() {
        return ASFConsts.getObjectIdName(this);
    }
}
