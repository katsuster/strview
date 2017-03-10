package net.katsuster.strview.autogen;

import java.util.*;

/**
 * <p>
 * クラスのスケルトンコードを定義、出力するクラスです。
 * </p>
 *
 * @author katsuhiro
 */
public class SkeltonCode {
    //継承元のクラス名です
    protected String name_baseclass;
    //クラス名です
    protected String name_class;
    //クラスを構成するメンバ、コメントなどのリストです
    protected List<Generator> list_skeltons;

    /**
     * <p>
     * スケルトンコードを生成します。
     * クラス名は "??????"、リストは空のリストで初期化されます。
     * </p>
     */
    public SkeltonCode() {
        this("??????", "??????");
    }

    /**
     * <p>
     * スケルトンコードを生成します。
     * 指定したクラス名と、空のリストで初期化されます。
     * </p>
     *
     * @param base 継承元のクラス名
     * @param name クラス名
     */
    public SkeltonCode(String base, String name) {
        name_baseclass = base;
        name_class = name;
        list_skeltons = new ArrayList<>();
    }

    /**
     * <p>
     * 継承元クラスのクラス名を取得します。
     * </p>
     *
     * @return 継承元クラス名
     */
    public String getBaseClassName() {
        return name_baseclass;
    }

    /**
     * <p>
     * 継承元クラスのクラス名を設定します。
     * </p>
     *
     * @param name 継承元クラス名
     */
    public void setBaseClassName(String name) {
        name_baseclass = name;
    }

    /**
     * <p>
     * クラス名を取得します。
     * </p>
     *
     * @return クラス名
     */
    public String getClassName() {
        return name_class;
    }

    /**
     * <p>
     * クラス名を設定します。
     * </p>
     *
     * @param name クラス名
     */
    public void setClassName(String name) {
        name_class = name;
    }

    /**
     * <p>
     * Java の命名規則に従った形式に変換した、
     * クラス名を取得します。
     * </p>
     *
     * @return Java の命名規則に従ったクラス名
     */
    public String getJavaClassName() {
        StringBuilder s = new StringBuilder();
        String[] split;
        int i;

        //基本的な戦略
        //・先頭の文字を大文字にする
        //・'_' の後にある文字を大文字にする
        split = name_class.split("_");

        for (i = 0; i < split.length; i++) {
            if (split[i].length() <= 0) {
                //空のトークンは無視する
                continue;
            }

            //先頭だけ大文字に変更する
            s.append(split[i].toUpperCase().substring(0, 1));
            s.append(split[i].substring(1, split[i].length()));
        }

        return s.toString();
    }

    /**
     * <p>
     * クラスの構成要素（メンバやコメントなど）を生成するための、
     * スケルトンコード生成用オブジェクトを追加します。
     * </p>
     *
     * @param m
     */
    public void addSkelton(Generator m) {
        list_skeltons.add(m);
    }

    /**
     * <p>
     * スケルトンコードを取得します。
     * </p>
     *
     * @return クラスのスケルトンコード
     */
    public String toCode() {
        StringBuilder s = new StringBuilder();
        String code;
        boolean f_cont_empty;
        int i;

        s.append("public class " + getJavaClassName() + " "
                + "extends " + getBaseClassName() + " {\n");

        //変数宣言
        f_cont_empty = true;
        for (i = 0; i < list_skeltons.size(); i++) {
            //空白の連続を圧縮する
            if (list_skeltons.get(i).toDefineCode().equals("")) {
                if (f_cont_empty) {
                    continue;
                } else {
                    f_cont_empty = true;
                }
            } else {
                f_cont_empty = false;
            }
            code = list_skeltons.get(i).toDefineCode();
            s.append("\t" + code.replace("\n", "\n\t\t") + "\n");
        }

        //コンストラクタ
        s.append("\t" + "public " + getJavaClassName() + "() {\n"
                + "\t\tsuper();\n"
                + "\t\t\n");
        f_cont_empty = true;
        for (i = 0; i < list_skeltons.size(); i++) {
            //空白の連続を圧縮する
            if (list_skeltons.get(i).toConstructorCode().equals("")) {
                if (f_cont_empty) {
                    continue;
                } else {
                    f_cont_empty = true;
                }
            } else {
                f_cont_empty = false;
            }
            code = list_skeltons.get(i).toConstructorCode();
            s.append("\t\t" + code.replace("\n", "\n\t\t") + "\n");
        }
        s.append("\t" + "}\n\n");

        //clone 関数
        s.append(String.format("\t" + "public %s clone() \n"
                        + "\t\t\t" + "throws CloneNotSupportedException {\n"
                        + "\t\t" + "%s obj = \n"
                        + "\t\t\t" + "(%s)super.clone();\n\n",
                getJavaClassName(),
                getJavaClassName(),
                getJavaClassName()));
        for (i = 0; i < list_skeltons.size(); i++) {
            code = list_skeltons.get(i).toCloneCode();
            s.append("\t\t" + code + "\n");
        }
        s.append("\t\t" + "\n"
                + "\t\t" + "return obj;\n"
                + "\t" + "}\n\n");

        //read 関数
        s.append("\t" + "@Override\n"
                + "\t" + "public void read(PacketReader<?> c) {\n"
                + "\t\t" + "read(c, this);\n"
                + "\t" + "}\n\n");

        s.append("\t" + "public static void read("
                + "PacketReader<?> c, \n"
                + "\t\t\t" + getJavaClassName() + " d) {\n"
                + "\t\t" + "c.enterBlock(\"" + getJavaClassName() + "\");\n"
                + "\t\t" + "\n"
                + "\t\t" + getBaseClassName() + ".read(c, d);\n"
                + "\t\t\n");
        for (i = 0; i < list_skeltons.size(); i++) {
            code = list_skeltons.get(i).toReaderCode();
            s.append("\t\t" + code.replace("\n", "\n\t\t") + "\n");
        }
        s.append("\t\t" + "\n"
                + "\t\t" + "c.leaveBlock();\n"
                + "\t" + "}\n\n");

        //write 関数
        s.append("\t" + "@Override\n"
                + "\t" + "public void write(PacketWriter<?> c) {\n"
                + "\t\t" + "write(c, this);\n"
                + "\t" + "}\n\n");

        s.append("\t" + "public static void write("
                + "PacketWriter<?> c, \n"
                + "\t\t\t" + getJavaClassName() + " d) {\n"
                + "\t\t" + "c.enterBlock(\"" + getJavaClassName() + "\");\n"
                + "\t\t" + "\n"
                + "\t\t" + getBaseClassName() + ".write(c, d);\n"
                + "\t\t\n");
        for (i = 0; i < list_skeltons.size(); i++) {
            code = list_skeltons.get(i).toWriterCode();
            s.append("\t\t" + code.replace("\n", "\n\t\t") + "\n");
        }
        s.append("\t\t" + "\n"
                + "\t\t" + "c.leaveBlock();\n"
                + "\t" + "}\n\n");

        s.append("}\n");

        return s.toString();
    }
}
