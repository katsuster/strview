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
public class PacketAdapter<T> extends AbstractPacket<T> {
    /**
     * <p>
     * 空のヘッダを持ち、存在する範囲が未定義のパケットを作成します。
     * </p>
     */
    public PacketAdapter() {
        //Do nothing
    }

    /**
     * <p>
     * 空のヘッダを持ち、存在する範囲が定義されたパケットを作成します。
     * </p>
     *
     * @param pr パケットが存在する範囲
     */
    public PacketAdapter(PacketRange pr) {
        super(pr);
    }

    /**
     * <p>
     * 指定されたヘッダを持ち、存在する範囲が未定義のパケットを作成します。
     * </p>
     *
     * @param h パケットヘッダ
     */
    public PacketAdapter(Block h) {
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
    public PacketAdapter(PacketRange pr, Block h) {
        super(pr, h);
    }

    @Override
    public boolean isRecursive() {
        return false;
    }

    @Override
    protected void readHeader(StreamReader<T> c) {
        //do nothing
    }

    @Override
    protected void readBody(StreamReader<T> c) {
        //do nothing
    }

    @Override
    protected void readFooter(StreamReader<T> c) {
        //do nothing
    }

    @Override
    protected void writeHeader(StreamWriter<T> c) {
        //do nothing
    }

    @Override
    protected void writeBody(StreamWriter<T> c) {
        //do nothing
    }

    @Override
    protected void writeFooter(StreamWriter<T> c) {
        //do nothing
    }
}
