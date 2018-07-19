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
 */
public abstract class AbstractBlock<T>
        implements Block<T>, Range<LargeList<T>> {
    private String name;
    private Range<LargeList<T>> pos;

    public AbstractBlock() {
        this("");
    }

    /**
     * <p>
     * 名前付きのブロックを作成します。
     * </p>
     *
     * @param n 名前
     */
    public AbstractBlock(String n) {
        name = n;
        pos = new SimpleRange<>();
    }

    @Override
    public Object clone()
            throws CloneNotSupportedException {
        AbstractBlock obj = (AbstractBlock)super.clone();

        obj.pos = (Range)pos.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return getClass().getName();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String n) {
        name = n;
    }

    @Override
    public LargeList<T> getBuffer() {
        return pos.getBuffer();
    }

    @Override
    public void setBuffer(LargeList<T> b) {
        pos.setBuffer(b);
    }

    @Override
    public long getStart() {
        return pos.getStart();
    }

    @Override
    public void setStart(long addr) {
        pos.setStart(addr);
    }

    @Override
    public long getEnd() {
        return pos.getEnd();
    }

    @Override
    public void setEnd(long addr) {
        pos.setEnd(addr);
    }

    @Override
    public long getLength() {
        return pos.getLength();
    }

    @Override
    public void setLength(long len) {
        pos.setLength(len);
    }

    @Override
    public boolean isHit(long i) {
        return pos.isHit(i);
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
    public void peek(StreamReader<T> c) {
        long orgpos = c.position();
        read(c);
        c.position(orgpos);
    }

    @Override
    public void poke(StreamWriter<T> c) {
        long orgpos = c.position();
        write(c);
        c.position(orgpos);
    }

    @Override
    public String toString() {
        ToStringConverter<T> c = new ToStringConverter<>(new StringBuilder());

        write(c);

        return c.getResult().toString();
    }
}
