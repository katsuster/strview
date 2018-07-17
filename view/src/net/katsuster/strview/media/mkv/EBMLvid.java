package net.katsuster.strview.media.mkv;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * EBML のタグ ID を示す可変長整数クラス。
 * </p>
 *
 * <p>
 * タグの場合、長さは 4バイトまで。
 * 値は、先頭の値が固定されているビットを含んだ値が、
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
 * </pre>
 *
 * <p>
 * ~~~~ 部分が固定ビット。
 * タグの場合、value メンバはこのビットを含んだ値が格納される。
 * </p>
 *
 * @see EBMLvalue
 */
public class EBMLvid
        extends EBMLvint
        implements Cloneable {
    public UInt vint_tag;

    public EBMLvid() {
        this(null);
    }

    public EBMLvid(String n) {
        super(n);

        vint_tag = new UInt("vint_tag");
    }

    @Override
    public EBMLvid clone()
            throws CloneNotSupportedException {
        EBMLvid obj = (EBMLvid)super.clone();

        obj.vint_tag = (UInt)vint_tag.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "EBML vid";
    }

    @Override
    public long getValue() {
        return vint_tag.getRaw();
    }

    @Override
    protected void setValue(long v) {
        //TODO: not implemented
        vint_tag.setRaw(v);

        throw new UnsupportedOperationException(
                "setValue() is not implemented.");
    }

    @Override
    protected void readBits(BitStreamReader c) {
        readBits(c, this);
    }

    public static void readBits(BitStreamReader c,
                                EBMLvid d) {
        c.enterBlock(d);

        //可変長整数全体の長さを得る
        int f = (int)c.peekLong( 8);
        int size = getVintSize(f);
        d.setSizeAll(size);
        d.setSizeContent(size);

        d.vint_tag = c.readUInt(size, d.vint_tag);

        c.leaveBlock();
    }

    @Override
    protected void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 EBMLvid d) {
        c.enterBlock(d);

        c.writeUInt(d.getSizeAll(), d.vint_tag);

        c.leaveBlock();
    }
}
