package net.katsuster.strview.media.mkv;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * EBML の可変長整数値クラス。
 * </p>
 *
 * <p>
 * 可変長整数の場合、長さは 8バイトまで。
 * 値は、先頭の値が固定されているビットを含ま「ない」値が、
 * value メンバに格納される。
 * </p>
 *
 * <pre>
 * 1byte: 0b1xxx-xxxx
 *          ~
 * 2byte: 0b01xx-xxxx xxxx-xxxx
 *          ~~
 * 3byte: 0b001x-xxxx xxxx-xxxx xxxx-xxxx
 *          ~~~
 * 4byte: 0b0001-xxxx xxxx-xxxx xxxx-xxxx xxxx-xxxx
 *          ~~~~
 * 5byte: 0b0000-1xxx
 *          ~~~~~~
 *          xxxx-xxxx
 * 6byte: 0b0000-01xx xxxx-xxxx xxxx-xxxx xxxx-xxxx
 *          ~~~~~~~
 *          xxxx-xxxx xxxx-xxxx
 * 7byte: 0b0000-001x xxxx-xxxx xxxx-xxxx xxxx-xxxx
 *          ~~~~~~~~
 *          xxxx-xxxx xxxx-xxxx xxxx-xxxx
 * 8byte: 0b0000-0001 xxxx-xxxx xxxx-xxxx xxxx-xxxx
 *          ~~~~~~~~~
 *          xxxx-xxxx xxxx-xxxx xxxx-xxxx xxxx-xxxx
 * </pre>
 *
 * <p>
 * ~~~~ 部分が固定ビット。
 * 可変値の場合、value メンバはこのビットを含まない値が格納される。
 * </p>
 *
 * @author katsuhiro
 * @see EBMLvid
 */
public class EBMLvalue extends EBMLvint
        implements Cloneable {
    public UInt vint_head;
    public UInt vint_val;

    public EBMLvalue() {
        super();

        vint_head = new UInt();
        vint_val = new UInt();
    }

    @Override
    public EBMLvalue clone()
            throws CloneNotSupportedException {
        EBMLvalue obj = (EBMLvalue)super.clone();

        obj.vint_head = vint_head.clone();
        obj.vint_val = vint_val.clone();

        return obj;
    }

    @Override
    public long getValue() {
        return vint_val.getBitsValue();
    }

    @Override
    protected void setValue(long v) {
        //TODO: not implemented
        vint_val.setBitsValue(v);

        throw new UnsupportedOperationException(
                "setValue() is not implemented.");
    }

    @Override
    public void read(PacketReader<?> c) {
        read(c, this);
    }

    public static void read(PacketReader<?> c,
                            EBMLvalue d) {
        int f, size_all, size_c;

        c.enterBlock("EBML value");

        //可変長整数全体の長さと、
        //可変長整数のうち値が占める部分の長さを得る
        f = (int)c.peekLong( 8);
        size_all = getVintSize(f);
        size_c = getVintContentSize(f);
        d.setSizeAll(size_all);
        d.setSizeContent(size_c);

        d.vint_head = c.readUInt(size_all - size_c, d.vint_head);
        d.vint_val = c.readUInt(size_c, d.vint_val);

        c.leaveBlock();
    }

    @Override
    public void write(PacketWriter<?> c) {
        write(c, this);
    }

    public static void write(PacketWriter<?> c,
                             EBMLvalue d) {
        c.enterBlock("EBML value");

        c.writeUInt(d.getSizeAll() - d.getSizeContent(), d.vint_head, "vint_head");
        c.writeUInt(d.getSizeContent(), d.vint_val, "vint_val");

        c.leaveBlock();
    }
}
