package net.katsuster.strview.gui.view;

import javax.swing.tree.*;

import net.katsuster.strview.util.bit.*;
import net.katsuster.strview.media.bit.*;

/**
 * <p>
 * パケットを構成するデータを表すクラスです。
 * </p>
 *
 * <p>
 * ToMemberTreeNodeConverter が生成し、
 * MemberTreeViewer に設定し、表示します。
 * </p>
 */
public class MemberTreeNode extends DefaultMutableTreeNode {
    private static final long serialVersionUID = 1L;

    public static final String FORMAT_ADDRESS = "%6x.%d-%6x.%d";
    public static final String FORMAT_ADDRESS_EMPTY = "                 ";
    public static final String FORMAT_NAME    = "%-32s";
    public static final String FORMAT_INDENT  = "  ";

    private String data_string;
    private Num data_num;
    private LargeBitList data_array;
    private String nodename;
    private String description;

    /**
     * <p>
     * 名前、値が空文字列で、説明を持たないノードを作成します。
     * </p>
     */
    public MemberTreeNode() {
        this("", "", null);
    }

    /**
     * <p>
     * 指定された名前を持ち、値が空文字列で、説明を持たないノードを作成します。
     * </p>
     *
     * @param name ノードの名前
     */
    public MemberTreeNode(String name) {
        this("", name, null);
    }

    /**
     * <p>
     * 指定された名前、値（文字列）、説明を持つノードを作成します。
     * </p>
     *
     * @param s ノードの保持する値（文字列）
     * @param name ノードの名前
     * @param desc ノードの説明
     */
    public MemberTreeNode(String s, String name, String desc) {
        data_string = s;
        nodename = name;
        description = desc;
    }

    /**
     * <p>
     * 指定された名前、値（数値）、説明を持つノードを作成します。
     * </p>
     *
     * @param val  ノードの保持する値（数値）
     * @param name ノードの名前
     * @param desc ノードの説明
     */
    public MemberTreeNode(Num val, String name, String desc) {
        data_num = val;
        nodename = name;
        description = desc;
    }

    /**
     * <p>
     * 指定された名前、値（ビットリスト）、説明を持つノードを作成します。
     * </p>
     *
     * @param name ノードの名前
     * @param array ノードの保持する値（ビットリスト）
     * @param desc ノードの説明
     */
    public MemberTreeNode(LargeBitList array, String name, String desc) {
        data_array = array;
        nodename = name;
        description = desc;
    }

    /**
     * <p>
     * ノードの名前を取得します。
     * </p>
     *
     * @return ノードの名前
     */
    public String getName() {
        return nodename;
    }

    /**
     * <p>
     * ノードの名前を設定します。
     * </p>
     *
     * @param n ノードの名前
     */
    public void setName(String n) {
        nodename = n;
    }

    /**
     * <p>
     * ノードの値（文字列）を保持しているか否かを返します。
     * </p>
     *
     * @return ノードの値（文字列）を保持する場合は true、
     * そうでない場合は false
     */
    public boolean hasStringData() {
        //FIXME: empty string is illegal or not?
        return data_string != null && !data_string.equals("");
    }

    /**
     * <p>
     * ノードの値（文字列）を取得します。
     * </p>
     *
     * <p>
     * 値を持たない場合は IllegalStateException をスローします。
     * </p>
     *
     * @return ノードの値（文字列）
     */
    public String getStringData() {
        if (!hasStringData()) {
            throw new IllegalStateException("Node does not have "
                    + "String value.");
        }

        return data_string;
    }

    /**
     * <p>
     * ノードの値（数値）を保持しているか否かを返します。
     * </p>
     *
     * @return ノードの値（数値）を保持する場合は true、
     * そうでない場合は false
     */
    public boolean hasNumData() {
        return data_num != null;
    }

    /**
     * <p>
     * ノードの値（数値）を取得します。
     * </p>
     *
     * <p>
     * 値を持たない場合は IllegalStateException をスローします。
     * </p>
     *
     * @return ノードの値（数値）
     */
    public Num getNumData() {
        if (!hasNumData()) {
            throw new IllegalStateException("Node does not have "
                    + "Num value.");
        }

        return data_num;
    }

    /**
     * <p>
     * ノードの値（ビットリスト）を保持しているか否かを返します。
     * </p>
     *
     * @return ノードの値（ビットリスト）を保持する場合は true、
     * そうでない場合は false
     */
    public boolean hasArrayData() {
        return data_array != null;
    }

    /**
     * <p>
     * ノードの値（ビットリスト）を取得します。
     * </p>
     *
     * <p>
     * 値を持たない場合は IllegalStateException をスローします。
     * </p>
     *
     * @return ノードの値（ビットリスト）
     */
    public LargeBitList getArrayData() {
        if (!hasArrayData()) {
            throw new IllegalStateException("Node does not have "
                    + "ByteArray value.");
        }

        return data_array;
    }

    /**
     * <p>
     * 説明を保持しているか否かを返します。
     * </p>
     *
     * @return 説明を保持する場合は true、
     * そうでない場合は false
     */
    public boolean hasDescritpion() {
        return description != null;
    }

    /**
     * <p>
     * ノードの説明を取得します。
     * </p>
     *
     * @return ノードの説明
     */
    public String getDescription() {
        return description;
    }

    /**
     * <p>
     * ストリーム上での値（数値、またはビットリスト）の、
     * 開始位置（ビット単位）を取得します。
     * </p>
     *
     * <p>
     * 開始位置を持たない場合は IllegalStateException をスローします。
     * </p>
     *
     * @return ストリーム上での値の開始位置（ビット単位）
     */
    public long getDataStart() {
        long pos;

        if (hasNumData()) {
            pos = getNumData().getRange().getStart();
        } else if (hasArrayData()) {
            pos = getArrayData().getRange().getStart();
        } else {
            throw new IllegalStateException("Node does not have "
                    + "start position.");
        }

        return pos;
    }

    /**
     * <p>
     * ストリーム上での値（数値、またはビットリスト）の、
     * 終了位置（ビット単位）を取得します。
     * </p>
     *
     * <p>
     * 開始位置を持たない場合は IllegalStateException をスローします。
     * </p>
     *
     * @return ストリーム上での値の終了位置（ビット単位）
     */
    public long getDataEnd() {
        long pos;

        if (hasNumData()) {
            //TODO: 参照先が非連続の場合の対応
            pos = getNumData().getRange().getEnd();
        } else if (hasArrayData()) {
            //TODO: 参照先が非連続の場合の対応
            pos = getArrayData().getRange().getEnd();
        } else {
            throw new IllegalStateException("Node does not have "
                    + "start position.");
        }

        return pos;
    }

    /**
     * <p>
     * ストリーム上での値（数値、またはビットリスト）の、
     * 長さ（ビット単位）を取得します。
     * </p>
     *
     * <p>
     * 長さを持たない場合は IllegalStateException をスローします。
     * </p>
     *
     * @return ストリーム上での値の長さ（ビット単位）
     */
    public long getDataLength() {
        long len;

        if (hasNumData()) {
            len = getNumData().length();
        } else if (hasArrayData()) {
            len = getArrayData().length();
        } else {
            throw new IllegalStateException("Node does not have "
                    + "end position.");
        }

        return len;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String valuename;

        //長さ
        if (hasNumData() || hasArrayData()) {
            String len;

            if (getDataLength() == 0) {
                len = String.format(FORMAT_ADDRESS_EMPTY);
            } else {
                len = String.format(FORMAT_ADDRESS,
                        getDataStart() >>> 3, getDataStart() & 7,
                        (getDataEnd() - 1) >>> 3, (getDataEnd() - 1) & 7);
            }
            sb.append(len + ": ");
        }

        //名前
        if (hasStringData() || hasNumData()
                || hasArrayData() || hasDescritpion()) {
            String name = String.format(FORMAT_NAME, getName());
            sb.append(name);
        } else {
            sb.append(getName());
        }

        //値
        if (hasStringData()) {
            valuename = getStringData();
        } else if (hasNumData()) {
            Num v = getNumData();
            String digits = BitNumFormatter.numToDigits(v);

            valuename = String.format(
                    "0x%0" + digits + "x(0x%0" + digits + "x, %s)\n",
                    v.getRaw(), v.getValue(), v.toString());
        } else if (hasArrayData()) {
            valuename = "array";
        } else {
            valuename = null;
        }
        if (valuename != null) {
            sb.append(": " + valuename);
        }

        //説明
        if (hasDescritpion()) {
            String desc = getDescription();
            sb.append("(" + desc + ")");
        }

        return sb.toString();
    }
}
