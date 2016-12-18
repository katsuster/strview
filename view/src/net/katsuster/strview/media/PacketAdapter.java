package net.katsuster.strview.media;

/**
 * <p>
 * 何もメンバを持たないパケットを表すためのユーティリティクラスです。
 * </p>
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

    /**
     * <p>
     * パケットのヘッダを別の形式に変換します。
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
    protected void convertHeader(PacketConverter<?> c) {
        //do nothing
    }

    /**
     * <p>
     * パケットの本体を別の形式に変換します。
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
    protected void convertBody(PacketConverter<?> c) {
        //do nothing
    }

    /**
     * <p>
     * パケットのフッタを別の形式に変換します。
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
    protected void convertFooter(PacketConverter<?> c) {
        //do nothing
    }
}
