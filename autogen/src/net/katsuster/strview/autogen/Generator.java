package net.katsuster.strview.autogen;

/**
 * <p>
 * クラスの構成要素を定義するためのインタフェースです。
 * </p>
 *
 * @author katsuhiro
 */
public interface Generator {
    /**
     * <p>
     * 変数宣言領域のコードを出力します。
     * </p>
     *
     * @return 変数宣言領域のコード
     */
    public String toDefineCode();

    /**
     * <p>
     * コンストラクタ領域のコードを出力します。
     * </p>
     *
     * @return 変数宣言領域のコード
     */
    public String toConstructorCode();

    /**
     * <p>
     * ディープコピーを行うためのコードを出力します。
     * </p>
     *
     * @return 構成要素をディープコピーするためのコード
     */
    public String toCloneCode();

    /**
     * <p>
     * 構成要素を read するためのコードを出力します。
     * </p>
     *
     * @return 構成要素を read するためのコード
     */
    public String toReaderCode();

    /**
     * <p>
     * 構成要素を write するためのコードを出力します。
     * </p>
     *
     * @return 構成要素を write するためのコード
     */
    public String toWriterCode();
}
