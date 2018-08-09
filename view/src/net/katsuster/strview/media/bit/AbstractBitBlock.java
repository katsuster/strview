package net.katsuster.strview.media.bit;

import java.io.*;
import java.util.*;

import net.katsuster.strview.util.bit.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * 意味のあるビットデータのまとまりを実装する際に利用するクラスです。
 * </p>
 */
public abstract class AbstractBitBlock
        extends AbstractBlock<Boolean> {
    public AbstractBitBlock() {
        super("");
    }

    /**
     * <p>
     * 名前付きのブロックを作成します。
     * </p>
     *
     * @param n 名前
     */
    public AbstractBitBlock(String n) {
        super(n);
    }

    @Override
    public Object clone()
            throws CloneNotSupportedException {
        AbstractBitBlock obj = (AbstractBitBlock)super.clone();

        return obj;
    }

    @Override
    public void read(StreamReader<Boolean> c) {
        readBits((BitStreamReader)c);
    }

    protected abstract void readBits(BitStreamReader c);

    @Override
    public void write(StreamWriter<Boolean> c) {
        writeBits((BitStreamWriter)c);
    }

    protected abstract void writeBits(BitStreamWriter c);

    @Override
    public String toString() {
        BitToStringConverter c = new BitToStringConverter(new StringBuilder());

        write(c);

        return c.getResult().toString();
    }

    /**
     * <p>
     * 値が負の値だった場合、例外をスローします。
     * </p>
     *
     * @param v 値
     */
    protected static void checkNegative(Num v) {
        checkNegative(v.getName(), v);
    }

    /**
     * <p>
     * 値が負の値だった場合、例外をスローします。
     * </p>
     *
     * @param name 名前
     * @param v    値
     */
    protected static void checkNegative(String name, Num v) {
        if (v.intValue() < 0) {
            throw new IllegalStateException(
                    name + " has negative size"
                            + "(len:" + v + ")");
        }
    }

    /**
     * <p>
     * バッファから任意の数のオブジェクトを読み出し、
     * 現在位置を進めます。
     * </p>
     *
     * <p>
     * リストに格納されている要素は全て削除され、
     * 読み出したオブジェクトが格納されます。
     * </p>
     *
     * @param c   各メンバの変換を実施するオブジェクト
     * @param n   取得するオブジェクトの個数
     * @param lst オブジェクトのリスト、null を渡すと新たなリストを作成します
     * @param cls 取得するオブジェクトのクラス
     * @param name リストの名前
     * @return バッファから取得した n 個のオブジェクトを納めたリスト
     * @throws IllegalArgumentException 読み出すオブジェクト数が不適切であるとき
     * @throws IndexOutOfBoundsException 現在位置がリミット以上である場合
     */
    public static <T extends Block> List<T> readObjectList(BitStreamReader c, int n, List<T> lst, Class<? extends T> cls, String name) {
        try {
            if (lst == null) {
                lst = new ArrayList<>();
            } else {
                lst.clear();
            }
            for (int i = 0; i < n; i++) {
                T v = cls.newInstance();
                v.setName(name + "[" + i + "]");
                v.read(c);
                lst.add(v);
            }

            return lst;
        } catch (InstantiationException ex) {
            throw new IllegalStateException("cannot instantiation of the "
                    + "'" + cls.getCanonicalName() + "'.");
        } catch (IllegalAccessException ex) {
            throw new IllegalStateException("illegal access in the "
                    + "'" + cls.getCanonicalName() + "'.");
        }
    }

    /**
     * <p>
     * バッファへ任意の数のオブジェクトを書き込み、
     * 現在位置を進めます。
     * </p>
     *
     * @param c    各メンバの変換を実施するオブジェクト
     * @param n    書き込むオブジェクトの個数
     * @param lst  書き込むオブジェクトのリスト
     * @throws IllegalArgumentException ビット数が不適切であるとき
     * @throws IndexOutOfBoundsException 現在位置がリミット以上である場合
     */
    public static <T extends Block> void writeObjectList(BitStreamWriter c, int n, List<T> lst) {
        for (int i = 0; i < n; i++) {
            lst.get(i).write(c);
        }
    }

    /**
     * <p>
     * ビットリストを指定されたエンコードの文字列と解釈して、変換します。
     * </p>
     *
     * @param v ビットリスト
     * @param enc エンコード
     * @return 文字列
     */
    protected static String getArrayName(LargeBitList v, String enc) {
        String name;

        try {
            byte[] buf = new byte[(int)v.length() >>> 3];
            v.getPackedByteArray(0, buf, 0, (int)v.length() & ~0x7);
            name = new String(buf, enc);
        } catch (UnsupportedEncodingException e) {
            name = "..unknown..";
        }

        return name;
    }
}
