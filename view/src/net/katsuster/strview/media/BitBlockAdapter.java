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
public class BitBlockAdapter extends AbstractBitBlock {
    public BitBlockAdapter() {
        super();
    }

    public BitBlockAdapter(String n) {
        super(n);
    }

    @Override
    protected void readBits(BitStreamReader c) {
        //do nothing
    }

    @Override
    protected void writeBits(BitStreamWriter c) {
        //do nothing
    }
}
