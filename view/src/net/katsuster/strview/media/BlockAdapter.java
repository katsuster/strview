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
 *     <li>タグ本体に別のタグを含められません。</li>
 *     <li>read 関数は何も読み込みません。</li>
 *     <li>write 関数は何も書き込みません。</li>
 * </ul>
 *
 * @author katsuhiro
 */
public class BlockAdapter extends AbstractBlock {
    @Override
    public BlockAdapter clone() throws CloneNotSupportedException {
        BlockAdapter obj = (BlockAdapter)super.clone();

        return obj;
    }

    @Override
    public void read(PacketReader<?> c) {
        //do nothing
    }

    @Override
    public void write(PacketWriter<?> c) {
        //do nothing
    }
}
