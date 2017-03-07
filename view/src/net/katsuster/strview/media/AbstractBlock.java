package net.katsuster.strview.media;

import net.katsuster.strview.util.*;

/**
 * <p>
 * 意味のあるデータのまとまりを実装する際に利用するクラスです。
 * </p>
 *
 * <p>
 * ほとんどのメソッドが空の実装となる場合は、
 * BlockAdapter を継承し必要な関数のみをオーバライドすると便利です。
 * </p>
 *
 * <p>
 * このクラスを継承するには、下記のメソッドを実装する必要があります。
 * </p>
 *
 * <dl>
 * <dt>convert() メソッド</dt>
 * <dd>ブロックが保持するデータを、
 * 別の形式に変換します。</dd>
 * </dl>
 *
 * <p>
 * また、下記のメソッドを実装することが推奨されます。
 * </p>
 *
 * <dl>
 * <dt>コンストラクタ</dt>
 * <dd>クラス特有の方法でオブジェクトを初期化します。</dd>
 * </dl>
 *
 * @see BlockAdapter
 * @author katsuhiro
 */
public abstract class AbstractBlock implements Block {
    private Range pos;

    public AbstractBlock() {
        pos = new SimpleRange();
    }

    @Override
    public Object clone()
            throws CloneNotSupportedException {
        AbstractBlock obj = (AbstractBlock)super.clone();

        obj.setRange(new SimpleRange(getRange()));

        return obj;
    }

    public long getStart() {
        return getRange().getStart();
    }

    public void setStart(long addr) {
        getRange().setStart(addr);
    }

    public long getEnd() {
        return getRange().getEnd();
    }

    public void setEnd(long addr) {
        getRange().setEnd(addr);
    }

    public long getLength() {
        return getRange().getLength();
    }

    public void setLength(long len) {
        getRange().setLength(len);
    }

    public boolean isHit(long i) {
        return getRange().isHit(i);
    }

    @Override
    public Range getRange() {
        return pos;
    }

    @Override
    public void setRange(Range p) {
        pos = p;
    }

    @Override
    public void peek(PacketReader<?> c) {
        long orgpos = c.position();
        read(c);
        c.position(orgpos);
    }

    @Override
    public void poke(PacketWriter<?> c) {
        long orgpos = c.position();
        write(c);
        c.position(orgpos);
    }

    @Override
    public String toString() {
        ToStringConverter c = new ToStringConverter(new StringBuilder());

        write(c);

        return c.getResult().toString();
    }
}
