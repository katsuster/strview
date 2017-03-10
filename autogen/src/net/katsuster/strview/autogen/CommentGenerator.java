package net.katsuster.strview.autogen;

import java.util.*;

/**
 * <p>
 * クラスの構成要素のうち、
 * コメントを生成するクラスです。
 * </p>
 *
 * @author katsuhiro
 */
public class CommentGenerator implements Generator {
    //クラス内に記述されるコメントです
    protected StringBuilder comment;

    /**
     * <p>
     * デフォルトコンストラクタです。
     * コメント行は空の文字列で初期化されます。
     * </p>
     */
    public CommentGenerator() {
        super();

        comment = new StringBuilder();
    }

    /**
     * <p>
     * コンストラクタです。
     * 指定した文字列をコメント行に設定します。
     * </p>
     *
     * @param s コメントに設定する文字列
     */
    public CommentGenerator(String s) {
        this();

        setComment(s);
    }

    /**
     * <p>
     * コンストラクタです。
     * トークンのリストを全て連結した文字列をコメント行に設定します。
     * </p>
     *
     * @param l コメント行を構成するトークンのリスト
     */
    public CommentGenerator(List<Token> l) {
        this();

        setComment(l);
    }

    /**
     * <p>
     * トークンのリストを全て連結したコメント行を取得します。
     * </p>
     *
     * @return コメント行を表す文字列
     */
    public String getComment() {
        return comment.toString();
    }

    /**
     * <p>
     * 指定した文字列をコメント行に設定します。
     * </p>
     *
     * @param s コメント
     */
    public void setComment(String s) {
        comment.append(s);
    }

    /**
     * <p>
     * トークンのリストを全て連結した文字列をコメント行に設定します。
     * </p>
     *
     * @param l コメント行を構成するトークンのリスト
     */
    public void setComment(List<Token> l) {
        int i;

        for (i = 0; i < l.size(); i++) {
            comment.append(l.get(i).sval + " ");
        }
    }

    @Override
    public String toDefineCode() {
        return "";
    }

    @Override
    public String toConstructorCode() {
        return "";
    }

    @Override
    public String toCloneCode() {
        return String.format(
                "//%s",
                comment.toString());
    }

    @Override
    public String toReaderCode() {
        return String.format(
                "//%s",
                comment.toString());
    }

    @Override
    public String toWriterCode() {
        return String.format(
                "//%s",
                comment.toString());
    }
}
