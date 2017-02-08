package net.katsuster.strview.media;

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
    private BlockRange pos;

    public AbstractBlock() {
        pos = new SimpleBlockRange();
    }

    @Override
    public AbstractBlock clone()
            throws CloneNotSupportedException {
        AbstractBlock obj = (AbstractBlock)super.clone();

        obj.setRange(new SimpleBlockRange(getRange()));

        return obj;
    }

    @Override
    public long getNumber() {
        return getRange().getNumber();
    }

    @Override
    public void setNumber(long num) {
        getRange().setNumber(num);
    }

    @Override
    public long getStart() {
        return getRange().getStart();
    }

    @Override
    public void setStart(long addr) {
        getRange().setStart(addr);
    }

    @Override
    public long getLength() {
        return getRange().getLength();
    }

    @Override
    public void setLength(long len) {
        getRange().setLength(len);
    }

    @Override
    public BlockRange getRange() {
        return pos;
    }

    @Override
    public void setRange(BlockRange p) {
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
