package net.katsuster.strview.media;

/**
 * <p>
 * 何もメンバを持たないブロックを表すためのユーティリティクラスです。
 * </p>
 *
 * @author katsuhiro
 */
public class BlockAdapter extends AbstractBlock {
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
    public void read(PacketConverter<?> c) {
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
    public void write(PacketConverter<?> c) {
        //do nothing
    }

    /**
     * <p>
     * ブロックを別の形式に変換します。
     * </p>
     *
     * <p>
     * 変換結果は PacketConverter オブジェクトに保持されます。
     * </p>
     *
     * <p>
     * このクラスは何もメンバを持たないため、
     * convert 関数は何も変換しません。
     * </p>
     *
     * @param c 各メンバの変換を実施するオブジェクト
     */
    @Override
    public void convert(PacketConverter<?> c) {
        //do nothing
    }
}
