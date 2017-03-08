package net.katsuster.strview.autogen;

/**
 * <p>
 * クラスの構成要素のうち、
 * メンバを定義するためのクラスです。
 * </p>
 *
 * @author katsuhiro
 */
public class MemberGenerator implements Generator {
    //有効な型名
    static final String[] TABLE_VALID_TYPES = {
            //ISO/IEC 13818-1
            //signed
            "tcimsbf",
            //unsigned
            "bslbf", "uimsbf", "rpchof",

            //ISO/IEC 14496-10
            //unsigned
            "u",

            //ISO/IEC 14496-12
            //signed
            "int",
            "const int",
            "template int",
            //unsigned
            "unsigned int",
            "const unsigned int",
            "template unsigned int",
            "bit", "const bit",

            //ASF
            //signed
            "LONG",
            //unsigned
            "BYTE", "WCHAR", "WORD", "DWORD", "QWORD",

            //generic
            //big-endian
            "sint_b",
            "uint_b",
            //little-endian
            "sint_l",
            "uint_l",
    };

    //ビッグエンディアン扱いする型名
    static final String[] TABLE_BIG_ENDIAN_TYPES = {
            //ISO/IEC 13818-1
            //signed
            "tcimsbf",
            //unsigned
            "bslbf", "uimsbf", "rpchof",

            //ISO/IEC 14496-10
            //unsigned
            "u",

            //ISO/IEC 14496-12
            //signed
            "int",
            "const int",
            "template int",
            //unsigned
            "unsigned int",
            "const unsigned int",
            "template unsigned int",
            "bit", "const bit",

            //ASF
            //signed
            //"LONG",
            //unsigned
            //"BYTE", "WCHAR", "WORD", "DWORD", "QWORD",

            //generic
            //big-endian
            "sint_b",
            "uint_b",
            //little-endian
            //"sint_l",
            //"uint_l",
    };

    //符号無し整数扱いする型名
    static final String[] TABLE_UNSIGNED_TYPES = {
            //ISO/IEC 13818-1
            //signed
            //"tcimsbf",
            //unsigned
            "bslbf", "uimsbf", "rpchof",

            //ISO/IEC 14496-10
            //unsigned
            "u",

            //ISO/IEC 14496-12
            //signed
            //"int",
            //"const int",
            //"template int",
            //unsigned
            "unsigned int",
            "const unsigned int",
            "template unsigned int",
            "bit", "const bit",

            //ASF
            //signed
            //"LONG",
            //unsigned
            "BYTE", "WCHAR", "WORD", "DWORD", "QWORD",

            //generic
            //big-endian
            //"sint_b",
            "uint_b",
            //little-endian
            //"sint_l",
            "uint_l",
    };

    //メンバの型
    private String type;
    //メンバ名
    private String name;
    //フルネーム
    private String fullName;
    //メンバのビット長
    private int bits;

    /**
     * <p>
     * デフォルトコンストラクタです。
     * メンバの型、メンバ名は "????" に、
     * 長さは 1 ビットに、
     * ビッグエンディアンに初期化されます。
     * </p>
     */
    public MemberGenerator() {
        super();

        setType("????");
        setName("????");
        setFullName("????");
        setBits(1);
    }

    /**
     * <p>
     * コンストラクタです。
     * メンバの型、メンバ名、メンバのビット長を設定します。
     * </p>
     *
     * @param t メンバの型名
     * @param n メンバ名
     * @param b メンバのビット長
     */
    public MemberGenerator(String t, String n, int b) {
        this(t, n, n, b);
    }

    /**
     * <p>
     * コンストラクタです。
     * メンバの型、メンバ名、フルネーム、メンバのビット長を設定します。
     * </p>
     *
     * @param t メンバの型名
     * @param n メンバ名
     * @param f フルネーム
     * @param b メンバのビット長
     */
    public MemberGenerator(String t, String n, String f, int b) {
        super();

        setType(t);
        setName(n);
        setFullName(n);
        setBits(b);
    }

    /**
     * <p>
     * ビッグエンディアンとして処理すべき型かそうでないかを取得します。
     * </p>
     *
     * @param type 型名を表す文字列
     * @return ビッグエンディアンとして処理すべきであれば true、
     * そうでなければ false
     */
    protected static boolean isBigEndianType(String type) {
        String[] table_types = TABLE_BIG_ENDIAN_TYPES;
        int i;

        for (i = 0; i < table_types.length; i++) {
            if (table_types[i].equals(type)) {
                //ビッグエンディアンである
                return true;
            }
        }

        //リトルエンディアンである
        return false;
    }

    /**
     * <p>
     * ビッグエンディアンとして処理すべき型かそうでないかを取得します。
     * </p>
     *
     * @param type 型名を表す文字列
     * @return ビッグエンディアンとして処理すべきであれば true、
     * そうでなければ false
     */
    protected static boolean isUnsignedType(String type) {
        String[] table_types = TABLE_UNSIGNED_TYPES;
        int i;

        for (i = 0; i < table_types.length; i++) {
            if (table_types[i].equals(type)) {
                //符号無し整数値である
                return true;
            }
        }

        //符号有り整数値である
        return false;
    }

    /**
     * <p>
     * メンバの型を取得します。
     * </p>
     *
     * @return メンバの型
     */
    public String getType() {
        return type;
    }

    /**
     * <p>
     * メンバの型を設定します。
     * </p>
     *
     * @param t メンバの型名
     */
    public void setType(String t) {
        type = t;
    }

    /**
     * <p>
     * メンバ名を取得します。
     * </p>
     *
     * @return メンバ名
     */
    public String getName() {
        return name;
    }

    /**
     * <p>
     * メンバ名を設定します。
     * </p>
     *
     * @param n メンバ名
     */
    public void setName(String n) {
        name = n;
    }

    /**
     * <p>
     * フルネームを取得します。
     * </p>
     *
     * @return フルネーム
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * <p>
     * フルネームを設定します。
     * </p>
     *
     * @param f フルネーム
     */
    public void setFullName(String f) {
        fullName = f;
    }

    /**
     * <p>
     * メンバのビット長を取得します。
     * </p>
     *
     * @return メンバのビット長
     */
    public int getBits() {
        return bits;
    }

    /**
     * <p>
     * メンバのビット長を設定します。
     * </p>
     *
     * @param b メンバのビット長
     * @throws IllegalArgumentException ビット長が 0 以下、あるいは負の場合
     */
    public void setBits(int b) {
        if (b < 0) {
            throw new IllegalArgumentException(
                    "bits is negative or too large("
                            + b + ").");
        }

        bits = b;
    }

    public String toDefineCode() {
        String str_javatype;
        String str_javabits;

        if (isUnsignedType(getType())) {
            str_javatype = "UInt";
        } else {
            str_javatype = "SInt";
        }

        if (bits <= 64) {
            str_javabits = "";
        } else {
            str_javabits = "??";
        }

        return String.format(
                "public %-4s %s;",
                str_javatype + str_javabits, name);
    }

    public String toConstructorCode() {
        String str_javatype;

        if (isUnsignedType(getType())) {
            str_javatype = "UInt";
        } else {
            str_javatype = "SInt";
        }
        if (bits > 64) {
            str_javatype += "??";
        }

        return String.format(
                "%s = new %-4s();",
                name, str_javatype);
    }

    public String toCloneCode() {
        String str_javatype;

        if (isUnsignedType(getType())) {
            str_javatype = "UInt";
        } else {
            str_javatype = "SInt";
        }
        if (bits > 64) {
            str_javatype += "??";
        }

        return String.format(
                "obj.%s = (%s)%s.clone();",
                name, str_javatype, name);
    }

    public String toReaderCode() {
        String str_gettype;
        String str_reverse;

        if (isUnsignedType(getType())) {
            str_gettype = "U";
        } else {
            str_gettype = "S";
        }

        if (isBigEndianType(getType()) || bits <= 8) {
            str_reverse = "Int";
        } else {
            str_reverse = "IntR";
        }

        return String.format("d.%-24s = c.read%s%s(%2d, d.%-24s);",
                name, str_gettype, str_reverse,
                bits, name);
    }

    public String toWriterCode() {
        String str_puttype;
        String str_reverse;

        if (isUnsignedType(getType())) {
            str_puttype = "U";
        } else {
            str_puttype = "S";
        }

        if (isBigEndianType(getType()) || bits <= 8) {
            str_reverse = "Int";
        } else {
            str_reverse = "IntR";
        }

        return String.format("c.write%s%s(%2d, d.%-24s, %-24s);",
                str_puttype, str_reverse,
                bits, name, "\"" + fullName + "\"");
    }
}
