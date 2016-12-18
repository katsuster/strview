package net.katsuster.strview.media;

/**
 * <p>
 * 意味のあるデータのまとまりです。
 * バッファからの読み込み、書き込み、
 * データの整合性の検証、他の形式への変換を行います。
 * </p>
 *
 * @see Packet
 * @author katsuhiro
 */
public interface Block {
    /**
     * <p>
     * ブロックを読み込みます。
     * </p>
     *
     * @param c 各メンバの変換を実施するオブジェクト
     */
    public void read(PacketConverter<?> c);

    /**
     * <p>
     * ブロックを書き込みます。
     * </p>
     *
     * @param c 各メンバの変換を実施するオブジェクト
     */
    public void write(PacketConverter<?> c);

    /**
     * <p>
     * ブロックを別の形式に変換します。
     * </p>
     *
     * <p>
     * 変換結果は PacketConverter オブジェクトに保持されます。
     * </p>
     *
     * @param c 各メンバの変換を実施するオブジェクト
     */
    public void convert(PacketConverter<?> c);
}
