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
public interface Block extends Cloneable {
    /**
     * オブジェクトのコピーを作成し、返します。
     *
     * @return このブロックのコピー
     * @throws CloneNotSupportedException clone をサポートしていない場合にスローされます。
     */
    public Block clone()
            throws CloneNotSupportedException;

    /**
     * <p>
     * 現在位置を更新せずに、ブロックを読み込みます。
     * </p>
     *
     * @param c 各メンバの変換を実施するオブジェクト
     */
    public void peek(PacketReader<?> c);

    /**
     * <p>
     * ブロックを読み込みます。
     * </p>
     *
     * @param c 各メンバの変換を実施するオブジェクト
     */
    public void read(PacketReader<?> c);

    /**
     * <p>
     * 現在位置を更新せずに、ブロックを書き込みます。
     * </p>
     *
     * @param c 各メンバの変換を実施するオブジェクト
     */
    public void poke(PacketWriter<?> c);

    /**
     * <p>
     * ブロックを書き込みます。
     * </p>
     *
     * @param c 各メンバの変換を実施するオブジェクト
     */
    public void write(PacketWriter<?> c);
}
