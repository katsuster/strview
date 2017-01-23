package net.katsuster.strview.media;

/**
 * <p>
 * 何もメンバを持たないブロックを表すためのユーティリティクラスです。
 * </p>
 *
 * @author katsuhiro
 */
public class BlockAdapter extends AbstractBlock {
    @Override
    public BlockAdapter clone() throws CloneNotSupportedException {
        BlockAdapter obj = (BlockAdapter)super.clone();

        return obj;
    }

    /**
     * <p>
     * ブロックを読み込みます。
     * </p>
     *
     * <p>
     * このクラスは何もメンバを持たないため、
     * read 関数は何も読み込みません。
     * </p>
     *
     * @param c 各メンバの変換を実施するオブジェクト
     */
    @Override
    public void read(PacketReader<?> c) {
        //do nothing
    }

    /**
     * <p>
     * ブロックを書き込みます。
     * </p>
     *
     * <p>
     * このクラスは何もメンバを持たないため、
     * write 関数は何も書き込みません。
     * </p>
     *
     * @param c 各メンバの変換を実施するオブジェクト
     */
    @Override
    public void write(PacketWriter<?> c) {
        //do nothing
    }
}
