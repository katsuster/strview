package net.katsuster.strview.autogen;

/**
 * <p>
 * トークンです。
 * </p>
 *
 * @author katsuhiro
 */
public class Token {
    //トークンの種類です
    public int ttype = -4;
    //トークンです
    public String sval = "";
    //トークンを int に変換した数値です
    public int num = 0;
    //トークンが数値として有効なら true、無効なら false
    public boolean f_num = false;

    /**
     * <p>
     * デフォルトコンストラクタです。
     * トークンの種類は -4、
     * トークンは空の文字列、
     * int に変換した数値は 0、
     * トークンは数値として無効である状態で初期化されます。
     * </p>
     */
    public Token() {

    }

    /**
     * <p>
     * コンストラクタです。
     * トークンを設定し、
     * トークンが数値として解釈可能ならば数値に変換した値を保持します。
     * </p>
     *
     * @param sv トークン
     */
    public Token(String sv) {
        this(0, sv);
    }

    /**
     * <p>
     * コンストラクタです。
     * トークンの種類、トークンを設定し、
     * トークンが数値として解釈可能ならば数値に変換した値を保持します。
     * </p>
     *
     * @param tt トークンの種類
     * @param sv トークン
     */
    public Token(int tt, String sv) {
        ttype = tt;
        sval = sv;
        try {
            this.num = Integer.parseInt(sv);
            this.f_num = true;
        } catch (NumberFormatException ex) {
            this.num = 0;
            this.f_num = false;
        }
    }
}
