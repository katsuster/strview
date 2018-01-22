package net.katsuster.strview.media.mkv;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * EBML の lacing サイズクラス。
 * </p>
 *
 * <p>
 * 可変長整数の場合、長さは 8バイトまで。
 * 値は、先頭の値が固定されているビットを含ま「ない」値から、
 * 「値域の半分」を引いた値が value メンバに格納される。
 * </p>
 *
 * <p>
 * 1byte なら、-(2^6 - 1) ～ 2^6 - 1 まで、
 * （計算方法は unsigned の値から 2^6 - 1 を引く）
 * 2byte なら、-(2^13 - 1) ～ 2^13 - 1 まで、
 * 3byte なら、-(2^20 - 1) ～ 2^20 - 1 まで、
 * を表現できる。
 * </p>
 *
 * <pre>
 * 1byte: 0b1xxx-xxxx
 *          ~
 *        minus: 2^6 - 1
 * 2byte: 0b01xx-xxxx xxxx-xxxx
 *          ~~
 *        minus: 2^13 - 1
 * 3byte: 0b001x-xxxx xxxx-xxxx xxxx-xxxx
 *          ~~~
 *        minus: 2^20 - 1
 * 4byte: 0b0001-xxxx xxxx-xxxx xxxx-xxxx xxxx-xxxx
 *          ~~~~
 *        minus: 2^27 - 1
 * 5byte: 0b0000-1xxx
 *          ~~~~~~
 *          xxxx-xxxx
 *        minus: 2^34 - 1
 * 6byte: 0b0000-01xx xxxx-xxxx xxxx-xxxx xxxx-xxxx
 *          ~~~~~~~
 *          xxxx-xxxx xxxx-xxxx
 *        minus: 2^31 - 1
 * 7byte: 0b0000-001x xxxx-xxxx xxxx-xxxx xxxx-xxxx
 *          ~~~~~~~~
 *          xxxx-xxxx xxxx-xxxx xxxx-xxxx
 *        minus: 2^48 - 1
 * 8byte: 0b0000-0001 xxxx-xxxx xxxx-xxxx xxxx-xxxx
 *          ~~~~~~~~~
 *          xxxx-xxxx xxxx-xxxx xxxx-xxxx xxxx-xxxx
 *        minus: 2^55 - 1
 * </pre>
 *
 * <p>
 * ~~~~ 部分が固定ビット。
 * 可変値の場合、value メンバはこのビットを含まない値が格納される。
 * </p>
 *
 * @see EBMLvid
 */
public class EBMLlacing<T extends LargeList<?>>
        extends EBMLvint<T>
        implements Cloneable {
    public UInt vint_head;
    public UInt vint_val;

    //符号付きに変換した値
    private long lacing_val;

    public EBMLlacing() {
        this(null);
    }

    public EBMLlacing(String n) {
        super(n);

        vint_head = new UInt("vint_head");
        vint_val = new UInt("vint_val");
    }

    @Override
    public EBMLlacing<T> clone()
            throws CloneNotSupportedException {
        EBMLlacing<T> obj = (EBMLlacing<T>)super.clone();

        obj.vint_head = (UInt)vint_head.clone();
        obj.vint_val = (UInt)vint_val.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "EBML lacing";
    }

    @Override
    public long getValue() {
        return lacing_val;
    }

    @Override
    protected void setValue(long v) {
        //TODO: not implemented
        lacing_val = v;

        throw new UnsupportedOperationException(
                "setValue() is not implemented.");
    }

    @Override
    public void read(StreamReader<?> c) {
        read(c, this);
    }

    public static void read(StreamReader<?> c,
                            EBMLlacing d) {
        int f, size_all, size_c;
        long minus;

        c.enterBlock(d);

        //可変長整数全体の長さと、
        //可変長整数のうち値が占める部分の長さを得る
        f = (int)c.peekLong( 8);
        size_all = getVintSize(f);
        size_c = getVintContentSize(f);
        d.setSizeAll(size_all);
        d.setSizeContent(size_c);

        d.vint_head = c.readUInt(size_all - size_c, d.vint_head);
        d.vint_val = c.readUInt(size_c, d.vint_val);

        //マイナス値を作る
        minus = ((long)1 << (size_c - 1)) - 1;
        d.lacing_val = d.vint_val.getRaw() - minus;

        c.leaveBlock();
    }

    @Override
    public void write(StreamWriter<?> c) {
        write(c, this);
    }

    public static void write(StreamWriter<?> c,
                             EBMLlacing d) {
        c.enterBlock(d);

        c.writeUInt(d.getSizeAll() - d.getSizeContent(), d.vint_head);
        c.writeUInt(d.getSizeContent(), d.vint_val);
        c.mark("val", d.getValue());

        c.leaveBlock();
    }
}
