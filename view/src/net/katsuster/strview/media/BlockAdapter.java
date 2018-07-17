package net.katsuster.strview.media;

/**
 * <p>
 * 何もメンバを持たないブロックを表すためのユーティリティクラスです。
 * </p>
 *
 * <p>
 * 下記の特徴を持ちます。
 * </p>
 *
 * <ul>
 *     <li>read 関数は何も読み込みません。</li>
 *     <li>write 関数は何も書き込みません。</li>
 * </ul>
 */
public class BlockAdapter<T> extends AbstractBlock<T> {
    public BlockAdapter() {
        super();
    }

    public BlockAdapter(String n) {
        super(n);
    }

    @Override
    public void read(StreamReader<T> c) {
        //do nothing
    }

    @Override
    public void write(StreamWriter<T> c) {
        //do nothing
    }
}
