package net.katsuster.strview.media;

/**
 * <p>
 * 何もメンバを持たないパケットを表すためのユーティリティクラスです。
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
public class PacketAdapter extends AbstractPacket {
    /**
     * <p>
     * 何もメンバを持たないパケットの構成要素を作成します。
     * </p>
     */
    public PacketAdapter() {
        super();
    }

    /**
     * <p>
     * パケットヘッダを指定して、
     * 何もメンバを持たないパケットの構成要素を作成します。
     * パケットヘッダは無視されます。
     * </p>
     *
     * @param h パケットヘッダ
     */
    public PacketAdapter(Block h) {
        super();
    }

    /**
     * <p>
     * 親パケットを持ったパケットの構成要素を作成します。
     * </p>
     *
     * @param pp 親パケット
     */
    public PacketAdapter(Packet pp) {
        super(pp);
    }

    @Override
    public boolean isRecursive() {
        return false;
    }

    @Override
    protected void readHeader(PacketReader<?> c) {
        //do nothing
    }

    @Override
    protected void readBody(PacketReader<?> c) {
        //do nothing
    }

    @Override
    protected void readFooter(PacketReader<?> c) {
        //do nothing
    }

    @Override
    protected void writeHeader(PacketWriter<?> c) {
        //do nothing
    }

    @Override
    protected void writeBody(PacketWriter<?> c) {
        //do nothing
    }

    @Override
    protected void writeFooter(PacketWriter<?> c) {
        //do nothing
    }
}
