package net.katsuster.strview.media;

import java.util.*;
import java.io.*;

import net.katsuster.strview.util.*;

/**
 * <p>
 * 意味のあるデータのまとまりを実装する際に利用するクラスです。
 * </p>
 *
 * <p>
 * ほとんどのメソッドが空の実装となる場合は、
 * BlockAdapter を継承し必要な関数のみをオーバライドすると便利です。
 * </p>
 *
 * <p>
 * このクラスを継承するには、下記のメソッドを実装する必要があります。
 * </p>
 *
 * <dl>
 * <dt>convert() メソッド</dt>
 * <dd>ブロックが保持するデータを、
 * 別の形式に変換します。</dd>
 * </dl>
 *
 * <p>
 * また、下記のメソッドを実装することが推奨されます。
 * </p>
 *
 * <dl>
 * <dt>コンストラクタ</dt>
 * <dd>クラス特有の方法でオブジェクトを初期化します。</dd>
 * </dl>
 *
 * @see BlockAdapter
 */
public abstract class AbstractBlock<T extends LargeList<?>>
        implements Block<T>, Range<T> {
    private String name;
    private Range<T> pos;

    public AbstractBlock() {
        this("");
    }

    public AbstractBlock(String n) {
        name = n;
        pos = new SimpleRange<>();
    }

    @Override
    public Object clone()
            throws CloneNotSupportedException {
        AbstractBlock obj = (AbstractBlock)super.clone();

        obj.pos = (Range)pos.clone();

        return obj;
    }

    @Override
    public T getBuffer() {
        return pos.getBuffer();
    }

    @Override
    public void setBuffer(T b) {
        pos.setBuffer(b);
    }

    @Override
    public long getStart() {
        return pos.getStart();
    }

    @Override
    public void setStart(long addr) {
        pos.setStart(addr);
    }

    @Override
    public long getEnd() {
        return pos.getEnd();
    }

    @Override
    public void setEnd(long addr) {
        pos.setEnd(addr);
    }

    @Override
    public long getLength() {
        return pos.getLength();
    }

    @Override
    public void setLength(long len) {
        pos.setLength(len);
    }

    @Override
    public boolean isHit(long i) {
        return pos.isHit(i);
    }

    @Override
    public String getTypeName() {
        return getClass().getName();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String n) {
        name = n;
    }

    @Override
    public Range getRange() {
        return pos;
    }

    @Override
    public void setRange(Range p) {
        pos = p;
    }

    @Override
    public void peek(StreamReader<?> c) {
        long orgpos = c.position();
        read(c);
        c.position(orgpos);
    }

    @Override
    public void poke(StreamWriter<?> c) {
        long orgpos = c.position();
        write(c);
        c.position(orgpos);
    }

    @Override
    public String toString() {
        ToStringConverter c = new ToStringConverter(new StringBuilder());

        write(c);

        return c.getResult().toString();
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
     * @return バッファから取得した n 個のオブジェクトを納めたリスト
     * @throws IllegalArgumentException 読み出すオブジェクト数が不適切であるとき
     * @throws IndexOutOfBoundsException 現在位置がリミット以上である場合
     */
    public static <T extends Block> List<T> readObjectList(StreamReader<?> c, int n, List<T> lst, Class<? extends T> cls) {
        try {
            if (lst == null) {
                lst = new ArrayList<>();
            } else {
                lst.clear();
            }
            for (int i = 0; i < n; i++) {
                T v = cls.newInstance();
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
     * @param name リストの名前
     * @throws IllegalArgumentException ビット数が不適切であるとき
     * @throws IndexOutOfBoundsException 現在位置がリミット以上である場合
     */
    public static <T extends Block> void writeObjectList(StreamWriter<?> c, int n, List<T> lst, String name) {
        for (int i = 0; i < n; i++) {
            c.mark(name, i);
            lst.get(i).write(c);
        }
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
