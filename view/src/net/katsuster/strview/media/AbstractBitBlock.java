package net.katsuster.strview.media;

/**
 * <p>
 * 意味のあるビットデータのまとまりを実装する際に利用するクラスです。
 * </p>
 */
public abstract class AbstractBitBlock
        extends AbstractBlock<Boolean> {
    public AbstractBitBlock() {
        super("");
    }

    /**
     * <p>
     * 名前付きのブロックを作成します。
     * </p>
     *
     * @param n 名前
     */
    public AbstractBitBlock(String n) {
        super(n);
    }

    @Override
    public Object clone()
            throws CloneNotSupportedException {
        AbstractBitBlock obj = (AbstractBitBlock)super.clone();

        return obj;
    }

    @Override
    public void read(StreamReader<Boolean> c) {
        readBits((BitStreamReader)c);
    }

    protected abstract void readBits(BitStreamReader c);

    @Override
    public void write(StreamWriter<Boolean> c) {
        writeBits((BitStreamWriter)c);
    }

    protected abstract void writeBits(BitStreamWriter c);

    @Override
    public String toString() {
        BitToStringConverter c = new BitToStringConverter(new StringBuilder());

        write(c);

        return c.getResult().toString();
    }
}
