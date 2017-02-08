package net.katsuster.strview.media;

/**
 * <p>
 * 意味のあるデータのまとまりです。
 * バッファからの読み込み、書き込み、
 * データの整合性の検証、他の形式への変換を行います。
 * </p>
 *
 * <p>
 * 実装する際は BlockAdapter クラスを継承すると便利です。
 * </p>
 *
 * @see BlockAdapter
 * @author katsuhiro
 */
public interface Block extends BlockRange, Cloneable {
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
     * データの存在する範囲を取得します。
     * </p>
     *
     * @return データの範囲
     */
    public BlockRange getRange();

    /**
     * <p>
     * データの存在する位置を設定します。
     * </p>
     *
     * @param p データの範囲
     */
    public void setRange(BlockRange p);

    /**
     * <p>
     * タグ本体に別のタグを含められるかどうかを返します。
     * </p>
     *
     * @return タグ本体に別のタグを含められる場合は true、
     * 含められない場合は false
     */
    public boolean isRecursive();

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
