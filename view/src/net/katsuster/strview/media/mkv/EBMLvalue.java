package net.katsuster.strview.media.mkv;

import net.katsuster.strview.media.bit.*;
import net.katsuster.strview.util.bit.*;

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
 * @see EBMLvid
 */
public class EBMLvalue
        extends EBMLvint
        implements Cloneable {
    public UInt vint_head;
    public UInt vint_val;

    public EBMLvalue() {
        this(null);
    }

    public EBMLvalue(String n) {
        super(n);

        vint_head = new UInt("vint_head");
        vint_val = new UInt("vint_val");
    }

    @Override
    public EBMLvalue clone()
            throws CloneNotSupportedException {
        EBMLvalue obj = (EBMLvalue)super.clone();

        obj.vint_head = (UInt)vint_head.clone();
        obj.vint_val = (UInt)vint_val.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "EBML value";
    }

    @Override
    public long getValue() {
        return vint_val.getRaw();
    }

    @Override
    protected void setValue(long v) {
        //TODO: not implemented
        vint_val.setRaw(v);

        throw new UnsupportedOperationException(
                "setValue() is not implemented.");
    }

    @Override
    protected void readBits(BitStreamReader c) {
        readBits(c, this);
    }

    public static void readBits(BitStreamReader c,
                                EBMLvalue d) {
        c.enterBlock(d);

        //可変長整数全体の長さと、
        //可変長整数のうち値が占める部分の長さを得る
        int f = (int)c.peekLong( 8);
        int size_all = getVintSize(f);
        int size_c = getVintContentSize(f);
        d.setSizeAll(size_all);
        d.setSizeContent(size_c);

        d.vint_head = c.readUInt(size_all - size_c, d.vint_head);
        d.vint_val = c.readUInt(size_c, d.vint_val);

        c.leaveBlock();
    }

    @Override
    protected void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 EBMLvalue d) {
        c.enterBlock(d);

        c.writeUInt(d.getSizeAll() - d.getSizeContent(), d.vint_head);
        c.writeUInt(d.getSizeContent(), d.vint_val);

        c.leaveBlock();
    }
}
