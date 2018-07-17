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
 *     <li>パケット本体に別のパケットを含められません。</li>
 *     <li>read 関数は何も読み込みません。</li>
 *     <li>write 関数は何も書き込みません。</li>
 * </ul>
 */
public class BitPacketAdapter extends AbstractBitPacket {
    /**
     * <p>
     * 空のヘッダを持ち、存在する範囲が未定義のパケットを作成します。
     * </p>
     */
    public BitPacketAdapter() {
        //Do nothing
    }

    /**
     * <p>
     * 空のヘッダを持ち、存在する範囲が定義されたパケットを作成します。
     * </p>
     *
     * @param pr パケットが存在する範囲
     */
    public BitPacketAdapter(PacketRange pr) {
        super(pr);
    }

    /**
     * <p>
     * 指定されたヘッダを持ち、存在する範囲が未定義のパケットを作成します。
     * </p>
     *
     * @param h パケットヘッダ
     */
    public BitPacketAdapter(Block h) {
        super(h);
    }

    /**
     * <p>
     * 指定されたヘッダを持ち、存在する範囲が定義されたパケットを作成します。
     * </p>
     *
     * @param pr パケットが存在する範囲
     * @param h パケットヘッダ
     */
    public BitPacketAdapter(PacketRange pr, Block h) {
        super(pr, h);
    }

    @Override
    public boolean isRecursive() {
        return false;
    }

    @Override
    protected void readHeaderBits(BitStreamReader c) {
        //do nothing
    }

    @Override
    protected void readBodyBits(BitStreamReader c) {
        //do nothing
    }

    @Override
    protected void readFooterBits(BitStreamReader c) {
        //do nothing
    }

    @Override
    protected void writeHeaderBits(BitStreamWriter c) {
        //do nothing
    }

    @Override
    protected void writeBodyBits(BitStreamWriter c) {
        //do nothing
    }

    @Override
    protected void writeFooterBits(BitStreamWriter c) {
        //do nothing
    }
}
