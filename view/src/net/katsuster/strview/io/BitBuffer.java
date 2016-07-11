package net.katsuster.strview.io;

import net.katsuster.strview.util.*;

/**
 * <p>
 * ビット単位でアクセス可能なバッファです。
 * </p>
 *
 * <p>
 * ビットはバイトの MSB 側から LSB 側に向かって配置されます。
 * </p>
 *
 * <pre>
 * position | 0  1  2  3  4  5  6  7| 8  9 10 11 12 13 14 15|16 17
 * ---------+-----------------------+-----------------------+------
 * byte     | 0                     | 1                     | 2
 * bit      | 0  1  2  3  4  5  6  7| 0  1  2  3  4  5  6  7| 0  1
 * </pre>
 *
 * <p>
 * オブジェクト生成時にメモリに配列を確保します。
 * または既存の LargeByteList オブジェクトをラップすることもできます。
 * 下記に例を示します。
 * </p>
 *
 * <pre>
 * LargeByteList a;
 * BitBuffer b;
 *
 * //100バイトのメモリ上の配列を確保し、配列にビット単位でアクセスする
 * b = new BitBuffer(100 &lt;&lt; 3);
 *
 * //ファイルにビット単位でアクセスする
 * a = new FileArray("/path/to/file");
 * b = BitBuffer.wrap(a);
 * </pre>
 *
 * @author katsuhiro
 */
public class BitBuffer {
    //バッファの現在位置（ビット単位）
    private long position = 0;
    //リミット（ビット単位）
    private long limit;
    //バッファの容量（ビット単位、バッファ生成時に設定され変更不可）
    private long capacity;

    //バッファが用いるビット列
    protected LargeBitList buf;

    /**
     * <p>
     * 不変条件は position &lt;= limit &lt;= capacity です。
     * </p>
     *
     * @param pos  現在位置（ビット単位）
     * @param lim  リミット（ビット単位）
     * @param cap  バッファの容量（ビット単位）
     */
    protected BitBuffer(long pos, long lim, long cap) {
        this(pos, lim, cap, null);
    }

    /**
     * <p>
     * 不変条件は position &lt;= limit &lt;= capacity です。
     * </p>
     *
     * @param pos  現在位置（ビット単位）
     * @param lim  リミット（ビット単位）
     * @param cap  バッファの容量（ビット単位）
     * @param b    バッファと連動するビット列
     */
    protected BitBuffer(long pos, long lim, long cap, LargeBitList b) {
        super();

        if (cap < 0) {
            throw new IllegalArgumentException(
                    "capacity is negative(" + cap + ").");
        }
        if (b.length() < (cap)) {
            throw new IndexOutOfBoundsException(
                    "capacity(" + cap + ") is too large"
                            + "(b.length: " + b.length() + ").");
        }

        capacity = cap;
        limit(lim);
        position(pos);

        buf = b;
    }

    /**
     * <p>
     * 新しいビットバッファを割り当てます。
     * </p>
     *
     * @param capacity バッファの容量（ビット単位）
     * @return 新しく割り当てたビットバッファ
     */
    public static BitBuffer allocate(long capacity) {
        BitBuffer b;

        if (capacity < 0) {
            throw new IllegalArgumentException(
                    "capacity is negative(" + capacity + ").");
        }
        if (capacity > Integer.MAX_VALUE) {
            throw new IndexOutOfBoundsException(
                    "capacity is too large(" + capacity + ").");
        }

        b = new BitBuffer(0, capacity, capacity,
                new MemoryBitList(capacity));

        return b;
    }

    /**
     * <p>
     * ビット列をバッファにラップします。
     * </p>
     *
     * @param array ラップしたいビット列
     * @param length バッファのオフセット（ビット単位）
     * @return 新しく割り当てたビットバッファ
     */
    public static BitBuffer wrap(LargeBitList array, long length) {
        return new BitBuffer(0, length, length,
                array);
    }

    /**
     * <p>
     * ビット列をバッファにラップします。
     * </p>
     *
     * @param array ラップしたいビット列
     * @return 新しく割り当てたビットバッファ
     */
    public static BitBuffer wrap(LargeBitList array) {
        return wrap(array, array.length());
    }

    /**
     * <p>
     * バイト列をバッファにラップします。
     * </p>
     *
     * @param array ラップしたいバイト列
     * @param length バッファのオフセット（ビット単位）
     * @return 新しく割り当てたビットバッファ
     */
    public static BitBuffer wrap(LargeByteList array, long length) {
        return new BitBuffer(0, length, length,
                new ByteToBitList(array));
    }

    /**
     * <p>
     * バイト列をバッファにラップします。
     * </p>
     *
     * @param array ラップしたいバイト配列
     * @return 新しく割り当てたビットバッファ
     */
    public static BitBuffer wrap(LargeByteList array) {
        return wrap(array, (array.length() << 3));
    }

    /**
     * <p>
     * byte 配列をバッファにラップします。
     * </p>
     *
     * @param array ラップしたい byte 配列
     * @param length バッファのオフセット（ビット単位）
     * @return 新しく割り当てたビットバッファ
     */
    public static BitBuffer wrap(byte[] array, long length) {
        return new BitBuffer(0, length, length,
                new ByteToBitList(new MemoryByteList(array)));
    }

    /**
     * <p>
     * byte 配列をバッファにラップします。
     * </p>
     *
     * @param array ラップしたい byte 配列
     * @return 新しく割り当てたビットバッファ
     */
    public static BitBuffer wrap(byte[] array) {
        return wrap(new MemoryByteList(array), (array.length << 3));
    }

    /**
     * <p>
     * 同じビット列を用いる新しいバッファを作成します。
     * </p>
     *
     * <p>
     * 現在のバッファを変更すると、新しいバッファにも変更が反映されます。
     * 逆に新しいバッファを変更すると、現在のバッファにも変更が反映されます。
     * 2つのバッファの現在位置、リミットはそれぞれ異なります。
     * </p>
     *
     * <p>
     * 新しいバッファの現在位置、容量、リミットは、
     * 現在のバッファと同じ値になります。
     * </p>
     *
     * @return 新しく割り当てたビットバッファ
     */
    public BitBuffer duplicate() {
        return new BitBuffer(position, limit, capacity,
                buf);
    }

    /**
     * <p>
     * インデックスから 1ビットアクセスできるかチェックして返します。
     * </p>
     *
     * @param i インデックス
     * @return インデックス
     * @throws IllegalArgumentException インデックスが負の値の場合
     * @throws IndexOutOfBoundsException インデックスがリミットを超えている場合
     */
    protected long getIndex(long i) {
        if ((0 <= i) && (i < limit)) {
            return i;
        } else {
            throw new IndexOutOfBoundsException(
                    "index(" + i + ") is exceeded "
                            + "limit(" + limit + ").");
        }
    }

    /**
     * <p>
     * インデックスから n ビットアクセスできるかチェックして返します。
     * </p>
     *
     * @param i インデックス
     * @param n アクセスするビット数
     * @return インデックス
     * @throws IllegalArgumentException インデックスが負の値の場合
     * @throws IndexOutOfBoundsException インデックスがリミットを超えている場合
     */
    protected long getIndex(long i, int n) {
        if ((0 <= i) || (n + i <= limit)) {
            return i;
        } else {
            throw new IndexOutOfBoundsException(
                    "index(" + i + " - " + (i + n) + ") is exceeded "
                            + "limit(" + limit + ").");
        }
    }

    /**
     * <p>
     * 現在の不変条件の状態を文字列表現を取得します。
     * デバッグ用です。
     * </p>
     *
     * @return バッファの現在の不変条件の文字列表現
     */
    protected String invariantToString() {
        return "position(" + position + ")"
                + "<= limit(" + limit + ")"
                + "<= capacity(" + capacity + ")";
    }

    /**
     * <p>
     * バッファの容量を取得します。
     * </p>
     *
     * @return バッファの容量
     */
    public long capacity() {
        return capacity;
    }

    /**
     * <p>
     * バッファの現在位置を取得します。
     * </p>
     *
     * @return バッファの現在位置
     */
    public long position() {
        return position;
    }

    /**
     * <p>
     * 新たなバッファの現在位置を設定します。
     * </p>
     *
     * <p>
     * 不変条件は position &lt;= limit &lt;= capacity です。
     * </p>
     *
     * @param newPosition 新たなバッファの現在位置
     * @return バッファ
     * @throws IllegalArgumentException 新たな位置が負の値の場合
     * @throws IndexOutOfBoundsException 不変条件を満たさない場合
     */
    public BitBuffer position(long newPosition) {
        if (newPosition < 0) {
            throw new IllegalArgumentException(
                    "new position is negative(" + newPosition + ").");
        }
        if (newPosition > limit) {
            throw new IndexOutOfBoundsException(
                    "new position does not hold invariant. "
                            + invariantToString());
        }

        position = newPosition;

        return this;
    }

    /**
     * <p>
     * バッファのリミット値を取得します。
     * </p>
     *
     * @return バッファのリミット値
     */
    public long limit() {
        return limit;
    }

    /**
     * <p>
     * 新たなバッファのリミットを設定します。
     * 新たなリミットより現在の現在位置が大きい場合、
     * 現在の現在位置はリミットと同じ位置に変更されます。
     * </p>
     *
     * <p>
     * 不変条件は position &lt;= limit &lt;= capacity です。
     * </p>
     *
     * @param newLimit 新たなバッファのリミット値
     * @return バッファ
     * @throws IllegalArgumentException 新たなリミット値が負の値の場合
     * @throws IndexOutOfBoundsException 不変条件を満たさない場合
     */
    public BitBuffer limit(long newLimit) {
        if (newLimit < 0) {
            throw new IllegalArgumentException(
                    "new limit is negative(" + newLimit + ").");
        }
        if (newLimit > capacity) {
            throw new IndexOutOfBoundsException(
                    "new limit does not hold invariant."
                            + invariantToString());
        }

        limit = newLimit;

        if (position > limit) {
            position = limit;
        }

        return this;
    }

    /**
     * <p>
     * バッファをクリアします。
     * リミットは容量と同じに設定され、現在位置は 0 に設定されます。
     * </p>
     *
     * <p>
     * 使用方法、サンプルコードについては、
     * java.nio.Buffer の clear() メソッドのドキュメントを参照してください。
     * </p>
     *
     * @return バッファ
     * @see java.nio.Buffer#clear()
     */
    public BitBuffer clear() {
        limit = capacity;
        position = 0;

        return this;
    }

    /**
     * <p>
     * バッファを反転させます。
     * リミットは現在の現在位置に、
     * 現在位置は 0 に設定されます。
     * </p>
     *
     * <p>
     * 使用方法、サンプルコードについては、
     * java.nio.Buffer の flip() メソッドのドキュメントを参照してください。
     * </p>
     *
     * @return バッファ
     * @see java.nio.Buffer#flip()
     */
    public BitBuffer flip() {
        limit = position;
        position = 0;

        return this;
    }

    /**
     * <p>
     * バッファを巻き戻します。
     * 現在位置は 0 に設定されます。
     * </p>
     *
     * <p>
     * 使用方法、サンプルコードについては、
     * java.nio.Buffer の rewind() メソッドのドキュメントを参照してください。
     * </p>
     *
     * @return バッファ
     * @see java.nio.Buffer#rewind()
     */
    public BitBuffer rewind() {
        position = 0;

        return this;
    }

    /**
     * <p>
     * 現在位置からリミットまでの要素数を取得します。
     * </p>
     *
     * @return 現在位置からリミットまでの要素数
     */
    public long remaining() {
        return (limit - position);
    }

    /**
     * <p>
     * 現在位置からリミットまでに要素が 1つでもあるかどうかを取得します。
     * </p>
     *
     * @return 現在位置からリミットまでに要素があれば true、
     * 要素が 1つもなければ false
     */
    public boolean hasRemaining() {
        return (position < limit);
    }

    /**
     * <p>
     * このバッファが読み取り専用であるかどうかを取得します。
     * </p>
     *
     * @return バッファが読み取り専用であれば true、書き込み可能なら false
     */
    public boolean isReadOnly() {
        return false;
    }

    /**
     * <p>
     * 現在のバッファが、
     * Java からアクセス可能なビット列と連動しているかどうかを取得します。
     * </p>
     *
     * <p>
     * このメソッドの戻り値が true であれば array メソッドおよび、
     * arrayOffset メソッドを安全に呼び出すことができます。
     * </p>
     *
     * @return Java からアクセス可能なビット列と連動していて、
     * なおかつ読み取り専用でなければれば true、
     * 読み取り専用または、ビット列と連動していなければ false
     */
    public boolean hasArray() {
        return true;
    }

    /**
     * <p>
     * 現在のバッファが連動しているビット列を返します。
     * </p>
     *
     * <p>
     * 現在のバッファのコンテンツに変更を加えると、
     * 返されるビット列のコンテンツも変更されます。その逆も同様です。
     * </p>
     *
     * <p>
     * このメソッドを呼び出す前に hasArray メソッドを呼び出し、
     * 現在のバッファが連動しているビット列を持つことをお勧めします。
     * </p>
     *
     * @return 現在のバッファが連動しているビット列
     * @throws UnsupportedOperationException 現在のバッファが
     * ビット列と連動していない場合
     */
    public LargeBitList array() {
        return buf;
    }

    /**
     * <p>
     * 現在のバッファがダイレクトバッファかどうかを取得します。
     * </p>
     *
     * @return 現在のバッファがダイレクトバッファであれば true、
     * ダイレクトバッファでなければ false
     */
    public boolean isDirect() {
        return false;
    }

    /**
     * <p>
     * バッファ上の位置 p がバイト境界に揃っているかどうかを返します。
     * </p>
     *
     * @return バイト境界に揃っていれば true、揃っていなければ false
     */
    public boolean isAlignedByte() {
        return ((position & 0x07) == 0);
    }

    /**
     * <p>
     * バッファ上の位置 p を次のバイト境界に揃えます。
     * </p>
     *
     * @return バッファ
     */
    public BitBuffer alignByte() {
        return position((position + 0x7) & ~0x7);
    }

    /**
     * <p>
     * バッファ上の位置 p が 2バイト境界に揃っているかどうかを返します。
     * </p>
     *
     * @return 2 バイト境界に揃っていれば true、揃っていなければ false
     */
    public boolean isAlignedShort() {
        return ((position & 0x0f) == 0);
    }

    /**
     * <p>
     * バッファ上の位置 p を次の 2バイト境界に揃えます。
     * </p>
     *
     * @return バッファ
     */
    public BitBuffer alignShort() {
        return position((position + 0xf) & ~0xf);
    }

    /**
     * <p>
     * バッファ上の位置 p が 4バイト境界に揃っているかどうかを返します。
     * </p>
     *
     * @return 4 バイト境界に揃っていれば true、揃っていなければ false
     */
    public boolean isAlignedInt() {
        return ((position & 0x1f) == 0);
    }

    /**
     * <p>
     * バッファ上の位置 p を次の 4バイト境界に揃えます。
     * </p>
     *
     * @return バッファ
     */
    public BitBuffer alignInt() {
        return position((position + 0x1f) & ~0x1f);
    }

    /**
     * <p>
     * バッファ上の位置 p が 8バイト境界に揃っているかどうかを返します。
     * </p>
     *
     * @return 8 バイト境界に揃っていれば true、揃っていなければ false
     */
    public boolean isAlignedLong() {
        return ((position & 0x3f) == 0);
    }

    /**
     * <p>
     * バッファ上の位置 p を次の 8バイト境界に揃えます。
     * </p>
     *
     * @return バッファ
     */
    public BitBuffer alignLong() {
        return position((position + 0x3f) & ~0x3f);
    }

    /**
     * <p>
     * 現在位置から 1 ビットを読み出し、現在位置を進めます。
     * </p>
     *
     * @return バッファから取得したビット
     * @throws IllegalArgumentException 読み出すビット数が不適切であるとき
     */
    public int get() {
        int result;

        result = peek32Inner(1, getIndex(position));
        position += 1;

        return result;
    }

    /**
     * <p>
     * 指定された位置から 1 ビットを読み出します。
     * </p>
     *
     * @param index 取得するビットの位置
     * @return バッファから取得したビット
     * @throws IllegalArgumentException 読み出すビット数が不適切であるとき
     */
    public int get(long index) {
        int result;

        result = peek32Inner(1, getIndex(index));

        return result;
    }

    /**
     * <p>
     * 指定した位置から配列内の任意のビット数を読み出し、
     * 現在位置を進めます。
     * </p>
     *
     * @param dst    取得したビットを格納する配列
     * @param off    読み出したビットを書きこむビット位置（配列内の位置）
     * @param length 取得するビット数（ビット単位）
     * @return バッファ
     * @throws IllegalArgumentException 読み出すビット数が不適切であるとき
     * @throws IndexOutOfBoundsException バッファの残量が足りない場合
     */
    public BitBuffer get(byte[] dst, int off, int length) {
        //bits
        long index;
        int len, n;
        //bytes
        int dst_off;

        if (off < 0) {
            throw new IllegalArgumentException(
                    "offset is negative"
                            + "(offset: " + off + ").");
        }
        if (length < 0) {
            throw new IllegalArgumentException(
                    "length is negative"
                            + "(length: " + length + ").");
        }
        if ((off + length) > (dst.length << 3)) {
            throw new IndexOutOfBoundsException(
                    "offset + length is too large"
                            + "(offset: " + off + ", "
                            + "(length: " + length + ", "
                            + "(dst.length: " + (dst.length << 3) + ")");
        }
        if (length > remaining()) {
            throw new IndexOutOfBoundsException(
                    "buffer is underflow, "
                            + "required(" + length + ") is exceeded "
                            + "remaining(" + remaining() + ").");
        }

        index = getIndex(position, length);
        len = length;
        dst_off = (off >>> 3);

        if (len > 8) {
            //use for-loop
            n = 8 - (off & 0x07);
            off = 0;

            //1st byte
            dst[dst_off] = (byte)peek32Inner(n, index);
            index += n;
            len -= n;
            dst_off += 1;

            //mid bytes
            while (len >= 8) {
                dst[dst_off] = (byte)peek32Inner(8, index);
                index += 8;
                len -= 8;
                dst_off += 1;
            }
        }

        //last byte
        if (len > 0) {
            dst[dst_off] =
                    (byte)(peek32Inner(len, index) << (8 - len - (off & 0x07)));
        }

        position += length;

        return this;
    }

    /**
     * <p>
     * 指定した位置から配列のビット長だけビットを読み出し、
     * 現在位置を進めます。
     * </p>
     *
     * <p>
     * このメソッドを src.get(a) の形式で呼び出すと、
     * 次の呼び出しと同じ結果になります。
     * </p>
     *
     * <pre>
     * src.get(a, 0, (a.length &lt;&lt; 3))
     * </pre>
     *
     * @param dst 取得したビットを格納する配列
     * @return バッファ
     * @throws IllegalArgumentException 読み出すビット数が不適切であるとき
     * @throws IndexOutOfBoundsException バッファの残量が足りない場合
     */
    public BitBuffer get(byte[] dst) {
        return get(dst, 0, (dst.length << 3));
    }

    /**
     * <p>
     * 指定した位置から配列内の任意のビット数を読み出し、
     * 現在位置を進めます。
     * </p>
     *
     * @param dst    取得したビットを格納する配列
     * @param off    読み出したビットを書きこむビット位置（配列内の位置）
     * @param length 取得するビット数（ビット単位）
     * @return バッファ
     * @throws IllegalArgumentException 読み出すビット数が不適切であるとき
     * @throws IndexOutOfBoundsException バッファの残量が足りない場合
     */
    public BitBuffer get(ByteArray dst, int off, int length) {
        return get(dst.getArray(), off, length);
    }

    /**
     * <p>
     * 指定した位置から配列のビット長だけビットを読み出し、
     * 現在位置を進めます。
     * </p>
     *
     * <p>
     * このメソッドを src.get(a) の形式で呼び出すと、
     * 次の呼び出しと同じ結果になります。
     * </p>
     *
     * <pre>
     * src.get(a, 0, a.length())
     * </pre>
     *
     * @param dst 取得したビットを格納する配列
     * @return バッファ
     * @throws IllegalArgumentException 読み出すビット数が不適切であるとき
     * @throws IndexOutOfBoundsException バッファの残量が足りない場合
     */
    public BitBuffer get(ByteArray dst) {
        return get(dst, 0, (int)dst.getLength());
    }

    /**
     * <p>
     * 現在位置から 1 ～ 32 ビットまでの任意のビット数を読み出し、
     * 現在位置を進めます。
     * </p>
     *
     * @param n 取得するビット数（32 ビットまで）
     * @param val 取得した n ビットの浮動小数値を書き込むオブジェクト
     * @return val が null 以外の場合は val、
     * val に null を指定した場合は、
     * 新たに生成したバッファから取得した n ビットの浮動小数値オブジェクト
     * @throws IllegalArgumentException 読み出すビット数が不適切であるとき
     * @throws IndexOutOfBoundsException 現在位置がリミット以上である場合
     */
    public Float32 getF32(int n, Float32 val) {
        long p = position();

        if (val == null) {
            val = new Float32();
        }

        val.setStart(p);
        val.setLength(n);
        val.setBitsValue(get32(n));

        return val;
    }

    /**
     * <p>
     * 現在位置から 1 ～ 16 ビットまでの任意のビット数を読み出し、
     * 現在位置を進めます。
     * </p>
     *
     * @param n 取得するビット数（16 ビットまで）
     * @param val 取得した n ビットの符号付き固定小数点値を書き込むオブジェクト
     * @return val が null 以外の場合は val、
     * val に null を指定した場合は、
     * 新たにバッファから取得した n ビットの符号付き固定小数点値オブジェクト
     * @throws IllegalArgumentException 読み出すビット数が不適切であるとき
     * @throws IndexOutOfBoundsException 現在位置がリミット以上である場合
     */
    public SFixed8_8 getSF8_8(int n, SFixed8_8 val) {
        long p = position();

        if (n != 16) {
            throw new IllegalArgumentException(
                    "getSF8_8() cannot get not equal 16 bits."
                            + "(" + n + "bits)");
        }

        if (val == null) {
            val = new SFixed8_8();
        }

        val.setStart(p);
        val.setLength(n);
        val.setBitsValue((short)(get32(n)));

        return val;
    }

    /**
     * <p>
     * 現在位置から 1 ～ 16 ビットまでの任意のビット数を読み出し、
     * 現在位置を進めます。
     * </p>
     *
     * @param n 取得するビット数（16 ビットまで）
     * @param val 取得した n ビットの符号無し固定小数点値を書き込むオブジェクト
     * @return val が null 以外の場合は val、
     * val に null を指定した場合は、
     * 新たにバッファから取得した n ビットの符号無し固定小数点値オブジェクト
     * @throws IllegalArgumentException 読み出すビット数が不適切であるとき
     * @throws IndexOutOfBoundsException 現在位置がリミット以上である場合
     */
    public UFixed8_8 getUF8_8(int n, UFixed8_8 val) {
        long p = position();

        if (n != 16) {
            throw new IllegalArgumentException(
                    "getUF8_8() cannot get not equal 16 bits."
                            + "(" + n + "bits)");
        }

        if (val == null) {
            val = new UFixed8_8();
        }

        val.setStart(p);
        val.setLength(n);
        val.setBitsValue((short)(get32(n)));

        return val;
    }

    /**
     * <p>
     * 現在位置から 1 ～ 32 ビットまでの任意のビット数を読み出し、
     * 現在位置を進めます。
     * </p>
     *
     * @param n 取得するビット数（32 ビットまで）
     * @param val 取得した n ビットの符号付き固定小数点値を書き込むオブジェクト
     * @return val が null 以外の場合は val、
     * val に null を指定した場合は、
     * 新たにバッファから取得した n ビットの符号付き固定小数点値オブジェクト
     * @throws IllegalArgumentException 読み出すビット数が不適切であるとき
     * @throws IndexOutOfBoundsException 現在位置がリミット以上である場合
     */
    public SFixed16_16 getSF16_16(int n, SFixed16_16 val) {
        long p = position();

        if (n != 32) {
            throw new IllegalArgumentException(
                    "getSF16_16() cannot get not equal 32 bits."
                            + "(" + n + "bits)");
        }

        if (val == null) {
            val = new SFixed16_16();
        }

        val.setStart(p);
        val.setLength(n);
        val.setBitsValue(get32(n));

        return val;
    }

    /**
     * <p>
     * 現在位置から 1 ～ 32 ビットまでの任意のビット数を読み出し、
     * 現在位置を進めます。
     * </p>
     *
     * @param n 取得するビット数（32 ビットまで）
     * @param val 取得した n ビットの符号無し固定小数点値を書き込むオブジェクト
     * @return val が null 以外の場合は val、
     * val に null を指定した場合は、
     * 新たにバッファから取得した n ビットの符号無し固定小数点値オブジェクト
     * @throws IllegalArgumentException 読み出すビット数が不適切であるとき
     * @throws IndexOutOfBoundsException 現在位置がリミット以上である場合
     */
    public UFixed16_16 getUF16_16(int n, UFixed16_16 val) {
        long p = position();

        if (n != 32) {
            throw new IllegalArgumentException(
                    "getUF16_16() cannot get not equal 32 bits."
                            + "(" + n + "bits)");
        }

        if (val == null) {
            val = new UFixed16_16();
        }

        val.setStart(p);
        val.setLength(n);
        val.setBitsValue(get32(n));

        return val;
    }

    /**
     * <p>
     * 現在位置から 16 ビットを読み出し、
     * 現在位置を進めます。
     * </p>
     *
     * <p>
     * バイト順序を逆順に並べ替えた値を返します。
     * </p>
     *
     * @param n 取得するビット数（16 ビット）
     * @param val 取得した n ビットの符号付き数値を書き込むオブジェクト
     * @return val が null 以外の場合は val、
     * val に null を指定した場合は、
     * 新たにバッファから取得した n ビットの符号付き数値を
     * バイト順序を逆順に並べ替えた数値オブジェクト
     * @throws IllegalArgumentException 読み出すビット数が不適切であるとき
     * @throws IndexOutOfBoundsException 現在位置がリミット以上である場合
     */
    public SInt getS16r(int n, SInt val) {
        long p = position();

        if (n != 16) {
            throw new IllegalArgumentException(
                    "getS16r() cannot get not equal 16 bits."
                            + "(" + n + "bits)");
        }

        if (val == null) {
            val = new SInt();
        }

        val.setStart(p);
        val.setLength(n);
        val.setBitsValue(Short.reverseBytes((short)(get32(n))));

        return val;
    }

    /**
     * <p>
     * 現在位置から 32 ビットを読み出し、
     * 現在位置を進めます。
     * </p>
     *
     * <p>
     * バイト順序を逆順に並べ替えた値を返します。
     * </p>
     *
     * @param n 取得するビット数（32 ビット）
     * @param val 取得した n ビットの符号付き数値を書き込むオブジェクト
     * @return val が null 以外の場合は val、
     * val に null を指定した場合は、
     * 新たにバッファから取得した n ビットの符号付き数値を
     * バイト順序を逆順に並べ替えた数値オブジェクト
     * @throws IllegalArgumentException 読み出すビット数が不適切であるとき
     * @throws IndexOutOfBoundsException 現在位置がリミット以上である場合
     */
    public SInt getS32r(int n, SInt val) {
        long p = position();

        if (n != 32) {
            throw new IllegalArgumentException(
                    "getS32r() cannot get not equal 32 bits."
                            + "(" + n + "bits)");
        }

        if (val == null) {
            val = new SInt();
        }

        val.setStart(p);
        val.setLength(n);
        val.setBitsValue(Integer.reverseBytes(get32(n)));

        return val;
    }

    /**
     * <p>
     * 現在位置から 16 ビットを読み出し、
     * 現在位置を進めます。
     * </p>
     *
     * <p>
     * バイト順序を逆順に並べ替えた値を返します。
     * </p>
     *
     * @param n 取得するビット数（16 ビット）
     * @param val 取得した n ビットの符号無し数値を書き込むオブジェクト
     * @return val が null 以外の場合は val、
     * val に null を指定した場合は、
     * 新たに生成したバッファから取得した n ビットの符号無し数値を
     * バイト順序を逆順に並べ替えた数値オブジェクト
     * @throws IllegalArgumentException 読み出すビット数が不適切であるとき
     * @throws IndexOutOfBoundsException 現在位置がリミット以上である場合
     */
    public UInt getU16r(int n, UInt val) {
        long p = position();

        if (n != 16) {
            throw new IllegalArgumentException(
                    "getU16r() cannot get not equal 16 bits."
                            + "(" + n + "bits)");
        }

        if (val == null) {
            val = new UInt();
        }

        val.setStart(p);
        val.setLength(n);
        val.setBitsValue(Short.reverseBytes((short)(get32(n))) & 0xffffL);

        return val;
    }

    /**
     * <p>
     * 現在位置から 32 ビットを読み出し、
     * 現在位置を進めます。
     * </p>
     *
     * <p>
     * バイト順序を逆順に並べ替えた値を返します。
     * </p>
     *
     * @param n 取得するビット数（32 ビット）
     * @param val 取得した n ビットの符号無し数値を書き込むオブジェクト
     * @return val が null 以外の場合は val、
     * val に null を指定した場合は、
     * 新たに生成したバッファから取得した n ビットの符号無し数値を
     * バイト順序を逆順に並べ替えた数値オブジェクト
     * @throws IllegalArgumentException 読み出すビット数が不適切であるとき
     * @throws IndexOutOfBoundsException 現在位置がリミット以上である場合
     */
    public UInt getU32r(int n, UInt val) {
        long p = position();

        if (n != 32) {
            throw new IllegalArgumentException(
                    "getU32r() cannot get not equal 32 bits."
                            + "(" + n + "bits)");
        }

        if (val == null) {
            val = new UInt();
        }

        val.setStart(p);
        val.setLength(n);
        val.setBitsValue(Integer.reverseBytes(get32(n)) & 0xffffffffL);

        return val;
    }

    /**
     * <p>
     * 現在位置から 1 ～ 32 ビットまでの任意のビット数を読み出し、
     * 現在位置を進めます。
     * </p>
     *
     * <p>
     * バイト順序を逆順に並べ替えた値を返します。
     * </p>
     *
     * @param n 取得するビット数（32 ビットまで）
     * @param val 取得した n ビットの浮動小数値を書き込むオブジェクト
     * @return val が null 以外の場合は val、
     * val に null を指定した場合は、
     * 新たにバッファから取得した n ビットの浮動小数値オブジェクト
     * @throws IllegalArgumentException 読み出すビット数が不適切であるとき
     * @throws IndexOutOfBoundsException 現在位置がリミット以上である場合
     */
    public Float32 getF32r(int n, Float32 val) {
        long p = position();

        if (n != 32) {
            throw new IllegalArgumentException(
                    "getF32r() cannot get not equal 32 bits."
                            + "(" + n + "bits)");
        }

        if (val == null) {
            val = new Float32();
        }

        val.setStart(p);
        val.setLength(n);
        val.setBitsValue(Integer.reverseBytes(get32(n)));

        return val;
    }

    /**
     * <p>
     * 現在位置から 1 ～ 32 ビットまでの任意のビット数を読み出します。
     * 現在位置は変更しません。
     * </p>
     *
     * @param n 取得するビット数（32 ビットまで）
     * @return バッファから取得した n ビットの数値
     * @throws IllegalArgumentException 読み出すビット数が不適切であるとき
     * @throws IndexOutOfBoundsException 現在位置がリミット以上である場合
     */
    public int peek32(int n) {
        int result;

        if (n < 0 || 32 < n) {
            throw new IllegalArgumentException(
                    "peek32() cannot get more than 32 bits."
                            + "(" + n + "bits)");
        }
        if (n > remaining()) {
            throw new IndexOutOfBoundsException(
                    "buffer is underflow, "
                            + "required(" + n + ") is exceeded "
                            + "remaining(" + remaining() + ").");
        }

        result = peek32Inner(n, getIndex(position, n));

        return result;
    }

    /**
     * <p>
     * 現在位置から 1 ～ 32 ビットまでの任意のビット数を読み出し、
     * 現在位置を進めます。
     * </p>
     *
     * @param n 取得するビット数（32 ビットまで）
     * @return バッファから取得した n ビットの数値
     * @throws IllegalArgumentException 読み出すビット数が不適切であるとき
     * @throws IndexOutOfBoundsException 現在位置がリミット以上である場合
     */
    public int get32(int n) {
        int result;

        if (n < 0 || 32 < n) {
            throw new IllegalArgumentException(
                    "get32() cannot get more than 32 bits."
                            + "(" + n + "bits)");
        }
        if (n > remaining()) {
            throw new IndexOutOfBoundsException(
                    "buffer is underflow, "
                            + "required(" + n + ") is exceeded "
                            + "remaining(" + remaining() + ").");
        }

        result = peek32Inner(n, getIndex(position, n));
        position += n;

        return result;
    }

    /**
     * <p>
     * 指定した位置から 1 ～ 32 ビットまでの任意のビット数を読み出します。
     * </p>
     *
     * @param n     取得するビット数（32 ビットまで）
     * @param index 読み出しを開始する位置
     * @return バッファから取得した n ビットの数値
     */
    protected int peek32Inner(int n, long index) {
        return buf.getPackedInt(index, n);
    }

    /**
     * <p>
     * 現在位置から 1 ～ 64 ビットまでの任意のビット数を読み出し、
     * 現在位置を進めます。
     * </p>
     *
     * @param n 取得するビット数（64 ビットまで）
     * @param val 取得した n ビットの符号付き数値を書き込むオブジェクト
     * @return val が null 以外の場合は val、
     * val に null を指定した場合は、
     * 新たに生成したバッファから取得した n ビットの符号付き数値オブジェクト
     * @throws IllegalArgumentException 読み出すビット数が不適切であるとき
     * @throws IndexOutOfBoundsException 現在位置がリミット以上である場合
     */
    public SInt getSInt(int n, SInt val) {
        long p = position();

        if (val == null) {
            val = new SInt();
        }

        val.setStart(p);
        val.setLength(n);
        val.setBitsValue(get64(n));

        return val;
    }

    /**
     * <p>
     * 現在位置から 1 ～ 64 ビットまでの任意のビット数を読み出し、
     * 現在位置を進めます。
     * </p>
     *
     * @param n 取得するビット数（64 ビットまで）
     * @param val 取得した n ビットの符号無し数値を書き込むオブジェクト
     * @return val が null 以外の場合は val、
     * val に null を指定した場合は、
     * 新たに生成したバッファから取得した n ビットの符号無し数値オブジェクト
     * @throws IllegalArgumentException 読み出すビット数が不適切であるとき
     * @throws IndexOutOfBoundsException 現在位置がリミット以上である場合
     */
    public UInt getUInt(int n, UInt val) {
        long p = position();

        if (val == null) {
            val = new UInt();
        }

        val.setStart(p);
        val.setLength(n);
        val.setBitsValue(get64(n));

        return val;
    }

    /**
     * <p>
     * 現在位置から 1 ～ 64 ビットまでの任意のビット数を読み出し、
     * 現在位置を進めます。
     * </p>
     *
     * @param n 取得するビット数（64 ビットまで）
     * @param val 取得した n ビットの浮動小数値を書き込むオブジェクト
     * @return val が null 以外の場合は val、
     * val に null を指定した場合は、
     * 新たに生成したバッファから取得した n ビットの浮動小数値オブジェクト
     * @throws IllegalArgumentException 読み出すビット数が不適切であるとき
     * @throws IndexOutOfBoundsException 現在位置がリミット以上である場合
     */
    public Float64 getF64(int n, Float64 val) {
        long p = position();

        if (val == null) {
            val = new Float64();
        }

        val.setStart(p);
        val.setLength(n);
        val.setBitsValue(get64(n));

        return val;
    }

    /**
     * <p>
     * 現在位置から 64 ビットを読み出し、
     * 現在位置を進めます。
     * </p>
     *
     * <p>
     * バイト順序を逆順に並べ替えた値を返します。
     * </p>
     *
     * @param n 取得するビット数（64 ビット）
     * @param val 取得した n ビットの符号付き数値を書き込むオブジェクト
     * @return val が null 以外の場合は val、
     * val に null を指定した場合は、
     * 新たにバッファから取得した n ビットの符号付き数値を
     * バイト順序を逆順に並べ替えた数値オブジェクト
     * @throws IllegalArgumentException 読み出すビット数が不適切であるとき
     * @throws IndexOutOfBoundsException 現在位置がリミット以上である場合
     */
    public SInt getS64r(int n, SInt val) {
        long p = position();

        if (n != 64) {
            throw new IllegalArgumentException(
                    "getS64r() cannot get not equal 64 bits."
                            + "(" + n + "bits)");
        }

        if (val == null) {
            val = new SInt();
        }

        val.setStart(p);
        val.setLength(n);
        val.setBitsValue(Long.reverseBytes(get64(n)));

        return val;
    }

    /**
     * <p>
     * 現在位置から 64 ビットを読み出し、
     * 現在位置を進めます。
     * </p>
     *
     * <p>
     * バイト順序を逆順に並べ替えた値を返します。
     * </p>
     *
     * @param n 取得するビット数（64 ビット）
     * @param val 取得した n ビットの符号無し数値を書き込むオブジェクト
     * @return val が null 以外の場合は val、
     * val に null を指定した場合は、
     * 新たに生成したバッファから取得した n ビットの符号無し数値を
     * バイト順序を逆順に並べ替えた数値オブジェクト
     * @throws IllegalArgumentException 読み出すビット数が不適切であるとき
     * @throws IndexOutOfBoundsException 現在位置がリミット以上である場合
     */
    public UInt getU64r(int n, UInt val) {
        long p = position();

        if (n != 64) {
            throw new IllegalArgumentException(
                    "getU64r() cannot get not equal 64 bits."
                            + "(" + n + "bits)");
        }

        if (val == null) {
            val = new UInt();
        }

        val.setStart(p);
        val.setLength(n);
        val.setBitsValue(Long.reverseBytes(get64(n)));

        return val;
    }

    /**
     * <p>
     * 現在位置から 1 ～ 64 ビットまでの任意のビット数を読み出し、
     * 現在位置を進めます。
     * </p>
     *
     * <p>
     * バイト順序を逆順に並べ替えた値を返します。
     * </p>
     *
     * @param n 取得するビット数（64 ビットまで）
     * @param val 取得した n ビットの浮動小数値を書き込むオブジェクト
     * @return val が null 以外の場合は val、
     * val に null を指定した場合は、
     * 新たにバッファから取得した n ビットの浮動小数値オブジェクト
     * @throws IllegalArgumentException 読み出すビット数が不適切であるとき
     * @throws IndexOutOfBoundsException 現在位置がリミット以上である場合
     */
    public Float64 getF64r(int n, Float64 val) {
        long p = position();

        if (n != 64) {
            throw new IllegalArgumentException(
                    "getF64r() cannot get not equal 64 bits."
                            + "(" + n + "bits)");
        }

        if (val == null) {
            val = new Float64();
        }

        val.setStart(p);
        val.setLength(n);
        val.setBitsValue(Long.reverseBytes(get64(n)));

        return val;
    }

    /**
     * <p>
     * 現在位置から任意のビット数を読み出し、
     * 現在位置を進めます。
     * </p>
     *
     * @param n 取得するビット数
     * @return バッファから取得した n ビットの数値を納めた配列オブジェクト
     * @throws IllegalArgumentException 読み出すビット数が不適切であるとき
     * @throws IndexOutOfBoundsException 現在位置がリミット以上である場合
     */
    public ByteArray getByteArray(int n) {
		/*
		 * n  | bytes | n >>> 3 | (n+7) >>> 3
		 * ---+-------+---------+------------
		 * 0  | 0     | 0       | 0
		 * 1  | 1     | 0       | 1
		 * 2  | 1     | 0       | 1
		 * ....
		 * 7  | 1     | 0       | 1
		 * 8  | 1     | 1       | 1
		 * 9  | 2     | 1       | 2
		 * 10 | 2     | 1       | 2
		 * ...
		 * 14 | 2     | 1       | 2
		 * 15 | 2     | 1       | 2
		 * 16 | 2     | 2       | 2
		 * 17 | 3     | 2       | 3
		 * ...
		 */
        ByteArray a = new ByteArray(
                new byte[(n + 7) >>> 3], n, position());

        get(a);

        return a;
    }

    /**
     * <p>
     * 現在位置から 1 ～ 64 ビットまでの任意のビット数を読み出します。
     * 現在位置は変更しません。
     * </p>
     *
     * @param n 取得するビット数（64 ビットまで）
     * @return バッファから取得した n ビットの数値
     * @throws IllegalArgumentException 読み出すビット数が不適切であるとき
     * @throws IndexOutOfBoundsException 現在位置がリミット以上である場合
     */
    public long peek64(int n) {
        long result;

        if (n < 0 || 64 < n) {
            throw new IllegalArgumentException(
                    "peek64() cannot get more than 64 bits."
                            + "(" + n + "bits)");
        }
        if (n > remaining()) {
            throw new IndexOutOfBoundsException(
                    "buffer is underflow, "
                            + "required(" + n + ") is exceeded "
                            + "remaining(" + remaining() + ").");
        }

        result = peek64Inner(n, getIndex(position, n));

        return result;
    }

    /**
     * <p>
     * 現在位置から 1 ～ 64 ビットまでの任意のビット数を読み出し、
     * 現在位置を進めます。
     * </p>
     *
     * @param n 取得するビット数（64 ビットまで）
     * @return バッファから取得した n ビットの数値
     * @throws IllegalArgumentException 読み出すビット数が不適切であるとき
     * @throws IndexOutOfBoundsException 現在位置がリミット以上である場合
     */
    public long get64(int n) {
        long result;

        if (n < 0 || 64 < n) {
            throw new IllegalArgumentException(
                    "get64() cannot get more than 64 bits."
                            + "(" + n + "bits)");
        }
        if (n > remaining()) {
            throw new IndexOutOfBoundsException(
                    "buffer is underflow, "
                            + "required(" + n + ") is exceeded "
                            + "remaining(" + remaining() + ").");
        }

        result = peek64Inner(n, getIndex(position, n));
        position += n;

        return result;
    }

    /**
     * <p>
     * 指定された位置から 1 ～ 64 ビットまでの任意のビット数を読み出します。
     * </p>
     *
     * @param n     取得するビット数（64 ビットまで）
     * @param index 読み出しを開始する位置
     * @return バッファから取得した n ビットの数値
     */
    protected long peek64Inner(int n, long index) {
        return buf.getPackedLong(index, n);
    }

    /**
     * <p>
     * 現在位置に 1 ビットを書き出し、現在位置を進めます。
     * </p>
     *
     * @param val 書き込むビット値（下位 1 ビットが使われます）
     * @return バッファ
     */
    public BitBuffer put(int val) {
        poke32Inner(1, val, getIndex(position));
        position += 1;

        return this;
    }

    /**
     * <p>
     * 指定した位置に 1 ビットを書き出します。
     * </p>
     *
     * @param index ビットを書き込む位置
     * @param val   書き込むビット値（下位 1 ビットが使われます）
     * @return バッファ
     */
    public BitBuffer put(long index, int val) {
        poke32Inner(1, val, getIndex(index));

        return this;
    }

    /**
     * <p>
     * 指定した位置に配列内の任意のビット数を書き出し、
     * 現在位置を進めます。
     * </p>
     *
     * @param src    バッファに書き出すビットが入った配列
     * @param off    書きこむビットを読み出すビット位置（配列内の位置）
     * @param length 書きだすビット数（ビット単位）
     * @return バッファ
     * @throws IllegalArgumentException 読み出すビット数が不適切であるとき
     * @throws IndexOutOfBoundsException バッファの残量が足りない場合
     */
    public BitBuffer put(byte[] src, int off, int length) {
        //bits
        long index;
        int len, n;
        //bytes
        int src_off;

        if (off < 0) {
            throw new IllegalArgumentException(
                    "offset is negative"
                            + "(offset: " + off + ").");
        }
        if (length < 0) {
            throw new IllegalArgumentException(
                    "length is negative"
                            + "(length: " + length + ").");
        }
        if ((off + length) > (src.length << 3)) {
            throw new IndexOutOfBoundsException(
                    "offset + length is too large"
                            + "(offset: " + off + ", "
                            + "(length: " + length + ", "
                            + "(src.length: " + (src.length << 3) + ")");
        }
        if (length > remaining()) {
            throw new IndexOutOfBoundsException(
                    "buffer is overflow, "
                            + "required(" + length + ") is exceeded "
                            + "remaining(" + remaining() + ").");
        }

        index = getIndex(position, length);
        len = length;
        src_off = (off >>> 3);

        if (len > 8) {
            //use for-loop
            n = 8 - (off & 0x07);
            off = 0;

            //1st byte
            poke32Inner(n, src[src_off], index);
            index += n;
            len -= n;
            src_off += 1;

            //mid bytes
            while (len >= 8) {
                poke32Inner(8, src[src_off], index);
                index += 8;
                len -= 8;
                src_off += 1;
            }
        }

        //last byte
        if (len > 0) {
            poke32Inner(len, src[src_off] >>> (8 - len - (off & 0x07)), index);
        }

        position += length;

        return this;
    }

    /**
     * <p>
     * 指定した位置に配列の長さだけビットを書き出し、
     * 現在位置を進めます。
     * </p>
     *
     * <p>
     * このメソッドを dst.put(a) の形式で呼び出すと、
     * 次の呼び出しと同じ結果になります。
     * </p>
     *
     * <pre>
     * dst.put(a, 0, a.length())
     * </pre>
     *
     * @param src バッファに書き出すビットが入った配列
     * @return バッファ
     * @throws IllegalArgumentException 読み出すビット数が不適切であるとき
     * @throws IndexOutOfBoundsException バッファの残量が足りない場合
     */
    public BitBuffer put(byte[] src) {
        return put(src, 0, (src.length << 3));
    }

    /**
     * <p>
     * 指定した位置に配列内の任意のビット数を書き出し、
     * 現在位置を進めます。
     * </p>
     *
     * @param src    バッファに書き出すビットが入った配列
     * @param off    書きこむビットを読み出すビット位置（配列内の位置）
     * @param length 書きだすビット数（ビット単位）
     * @return バッファ
     * @throws IllegalArgumentException 読み出すビット数が不適切であるとき
     * @throws IndexOutOfBoundsException バッファの残量が足りない場合
     */
    public BitBuffer put(ByteArray src, int off, int length) {
        return put(src.getArray(), off, length);
    }

    /**
     * <p>
     * 指定した位置に配列の長さだけビットを書き出し、
     * 現在位置を進めます。
     * </p>
     *
     * <p>
     * このメソッドを dst.put(a) の形式で呼び出すと、
     * 次の呼び出しと同じ結果になります。
     * </p>
     *
     * <pre>
     * dst.put(a, 0, (a.length &lt;&lt; 3))
     * </pre>
     *
     * @param src バッファに書き出すビットが入った配列
     * @return バッファ
     * @throws IllegalArgumentException 読み出すビット数が不適切であるとき
     * @throws IndexOutOfBoundsException バッファの残量が足りない場合
     */
    public BitBuffer put(ByteArray src) {
        return put(src, 0, (int)src.getLength());
    }

    /**
     * <p>
     * 現在位置に val の下位 1 ～ 32 ビットまでの、
     * 任意のビット数を書き出し、現在位置を進めます。
     * </p>
     *
     * @param n   書き込むビット数（32 ビットまで）
     * @param val 書き込むビットを含んだ浮動小数値オブジェクト
     * @return バッファ
     * @throws IllegalArgumentException 読み出すビット数が不適切であるとき
     * @throws IndexOutOfBoundsException 現在位置がリミット以上である場合
     */
    public BitBuffer putF32(int n, Float32 val) {
        return put32(n, (int)val.getBitsValue());
    }

    /**
     * <p>
     * 現在位置に val の下位 1 ～ 16 ビットまでの、
     * 任意のビット数を書き出し、現在位置を進めます。
     * </p>
     *
     * @param n   書き込むビット数（16 ビットまで）
     * @param val 書き込むビットを含んだ符号付き固定小数点値オブジェクト
     * @return バッファ
     * @throws IllegalArgumentException 読み出すビット数が不適切であるとき
     * @throws IndexOutOfBoundsException 現在位置がリミット以上である場合
     */
    public BitBuffer putSF8_8(int n, SFixed8_8 val) {
        return put32(n, (int)val.getBitsValue());
    }

    /**
     * <p>
     * 現在位置に val の下位 1 ～ 16 ビットまでの、
     * 任意のビット数を書き出し、現在位置を進めます。
     * </p>
     *
     * @param n   書き込むビット数（16 ビットまで）
     * @param val 書き込むビットを含んだ符号無し固定小数点値オブジェクト
     * @return バッファ
     * @throws IllegalArgumentException 読み出すビット数が不適切であるとき
     * @throws IndexOutOfBoundsException 現在位置がリミット以上である場合
     */
    public BitBuffer putUF8_8(int n, UFixed8_8 val) {
        return put32(n, (int)val.getBitsValue());
    }

    /**
     * <p>
     * 現在位置に val の下位 1 ～ 32 ビットまでの、
     * 任意のビット数を書き出し、現在位置を進めます。
     * </p>
     *
     * @param n   書き込むビット数（32 ビットまで）
     * @param val 書き込むビットを含んだ符号付き固定小数点値オブジェクト
     * @return バッファ
     * @throws IllegalArgumentException 読み出すビット数が不適切であるとき
     * @throws IndexOutOfBoundsException 現在位置がリミット以上である場合
     */
    public BitBuffer putSF16_16(int n, SFixed16_16 val) {
        return put32(n, (int)val.getBitsValue());
    }

    /**
     * <p>
     * 現在位置に val の下位 1 ～ 32 ビットまでの、
     * 任意のビット数を書き出し、現在位置を進めます。
     * </p>
     *
     * @param n   書き込むビット数（32 ビットまで）
     * @param val 書き込むビットを含んだ符号無し固定小数点値オブジェクト
     * @return バッファ
     * @throws IllegalArgumentException 読み出すビット数が不適切であるとき
     * @throws IndexOutOfBoundsException 現在位置がリミット以上である場合
     */
    public BitBuffer putUF16_16(int n, UFixed16_16 val) {
        return put32(n, (int)val.getBitsValue());
    }

    /**
     * <p>
     * 現在位置に val の下位 16 ビットを書き出し、
     * 現在位置を進めます。
     * </p>
     *
     * <p>
     * バイト順序を逆順に並べ替えた値が書き込まれます。
     * </p>
     *
     * @param n   書き込むビット数（16 ビット）
     * @param val 書き込むビットを含んだ符号付き整数値オブジェクト
     * @return バッファ
     * @throws IllegalArgumentException 読み出すビット数が不適切であるとき
     * @throws IndexOutOfBoundsException 現在位置がリミット以上である場合
     */
    public BitBuffer putS16r(int n, SInt val) {
        if (n != 16) {
            throw new IllegalArgumentException(
                    "putS16r() cannot put not equal 16 bits."
                            + "(" + n + "bits)");
        }
        return put32(n, Short.reverseBytes((short)val.getBitsValue()));
    }

    /**
     * <p>
     * 現在位置に val の下位 32 ビットを書き出し、
     * 現在位置を進めます。
     * </p>
     *
     * <p>
     * バイト順序を逆順に並べ替えた値が書き込まれます。
     * </p>
     *
     * @param n   書き込むビット数（32 ビット）
     * @param val 書き込むビットを含んだ符号付き整数値オブジェクト
     * @return バッファ
     * @throws IllegalArgumentException 読み出すビット数が不適切であるとき
     * @throws IndexOutOfBoundsException 現在位置がリミット以上である場合
     */
    public BitBuffer putS32r(int n, SInt val) {
        if (n != 32) {
            throw new IllegalArgumentException(
                    "putS32r() cannot put not equal 32 bits."
                            + "(" + n + "bits)");
        }
        return put32(n, Integer.reverseBytes((int)val.getBitsValue()));
    }

    /**
     * <p>
     * 現在位置に val の下位 16 ビットを書き出し、
     * 現在位置を進めます。
     * </p>
     *
     * <p>
     * バイト順序を逆順に並べ替えた値が書き込まれます。
     * </p>
     *
     * @param n   書き込むビット数（16 ビット）
     * @param val 書き込むビットを含んだ符号無し整数値オブジェクト
     * @return バッファ
     * @throws IllegalArgumentException 読み出すビット数が不適切であるとき
     * @throws IndexOutOfBoundsException 現在位置がリミット以上である場合
     */
    public BitBuffer putU16r(int n, UInt val) {
        if (n != 16) {
            throw new IllegalArgumentException(
                    "putU16r() cannot put not equal 16 bits."
                            + "(" + n + "bits)");
        }
        return put32(n, Short.reverseBytes((short)val.getBitsValue()));
    }

    /**
     * <p>
     * 現在位置に val の下位 32 ビットを書き出し、
     * 現在位置を進めます。
     * </p>
     *
     * <p>
     * バイト順序を逆順に並べ替えた値が書き込まれます。
     * </p>
     *
     * @param n   書き込むビット数（32 ビット）
     * @param val 書き込むビットを含んだ符号無し整数値オブジェクト
     * @return バッファ
     * @throws IllegalArgumentException 読み出すビット数が不適切であるとき
     * @throws IndexOutOfBoundsException 現在位置がリミット以上である場合
     */
    public BitBuffer putU32r(int n, UInt val) {
        if (n != 32) {
            throw new IllegalArgumentException(
                    "putU32r() cannot put not equal 32 bits."
                            + "(" + n + "bits)");
        }
        return put32(n, Integer.reverseBytes((int)val.getBitsValue()));
    }

    /**
     * <p>
     * 現在位置に val の下位 1 ～ 32 ビットまでの、
     * 任意のビット数を書き出し、現在位置を進めます。
     * </p>
     *
     * <p>
     * バイト順序を逆順に並べ替えた値が書き込まれます。
     * </p>
     *
     * @param n   書き込むビット数（32 ビットまで）
     * @param val 書き込むビットを含んだ浮動小数値オブジェクト
     * @return バッファ
     * @throws IllegalArgumentException 読み出すビット数が不適切であるとき
     * @throws IndexOutOfBoundsException 現在位置がリミット以上である場合
     */
    public BitBuffer putF32r(int n, Float32 val) {
        return put32(n, Integer.reverseBytes((int)val.getBitsValue()));
    }

    /**
     * <p>
     * 現在位置に val の下位 1 ～ 32 ビットまでの、
     * 任意のビット数を書き出します。
     * 現在位置は変更しません。
     * </p>
     *
     * @param n   書き込むビット数（32 ビットまで）
     * @param val 書き込むビットを含んだ整数値
     * @return バッファ
     * @throws IllegalArgumentException 読み出すビット数が不適切であるとき
     * @throws IndexOutOfBoundsException 現在位置がリミット以上である場合
     */
    public BitBuffer poke32(int n, int val) {
        if (n < 0 || 32 < n) {
            throw new IllegalArgumentException(
                    "poke32() cannot put more than 32 bits."
                            + "(" + n + "bits)");
        }
        if (n > remaining()) {
            throw new IndexOutOfBoundsException(
                    "buffer is overflow, "
                            + "required(" + n + ") is exceeded "
                            + "remaining(" + remaining() + ").");
        }

        poke32Inner(n, val, getIndex(position, n));

        return this;
    }

    /**
     * <p>
     * 現在位置に val の下位 1 ～ 32 ビットまでの、
     * 任意のビット数を書き出し、現在位置を進めます。
     * </p>
     *
     * @param n   書き込むビット数（32 ビットまで）
     * @param val 書き込むビットを含んだ整数値
     * @return バッファ
     * @throws IllegalArgumentException 読み出すビット数が不適切であるとき
     * @throws IndexOutOfBoundsException 現在位置がリミット以上である場合
     */
    public BitBuffer put32(int n, int val) {
        if (n < 0 || 32 < n) {
            throw new IllegalArgumentException(
                    "put32() cannot put more than 32 bits."
                            + "(" + n + "bits)");
        }
        if (n > remaining()) {
            throw new IndexOutOfBoundsException(
                    "buffer is overflow, "
                            + "required(" + n + ") is exceeded "
                            + "remaining(" + remaining() + ").");
        }

        poke32Inner(n, val, getIndex(position, n));
        position += n;

        return this;
    }

    /**
     * <p>
     * 指定した位置に val の下位 1 ～ 32 ビットまでの、
     * 任意のビット数を書き込みます。
     * </p>
     *
     * @param n     書き込むビット数（32 ビットまで）
     * @param val   書き込むビットを含んだ整数値
     * @param index 書き込む位置
     */
    protected void poke32Inner(int n, int val, long index) {
        buf.setPackedInt(index, n, val);
    }

    /**
     * <p>
     * 現在位置に val の下位 1 ～ 64 ビットまでの、
     * 任意のビット数を書き出し、現在位置を進めます。
     * </p>
     *
     * @param n   書き込むビット数（64 ビットまで）
     * @param val 書き込むビットを含んだ符号付き整数値オブジェクト
     * @return バッファ
     * @throws IllegalArgumentException 読み出すビット数が不適切であるとき
     * @throws IndexOutOfBoundsException 現在位置がリミット以上である場合
     */
    public BitBuffer putSInt(int n, SInt val) {
        return put64(n, val.getBitsValue());
    }

    /**
     * <p>
     * 現在位置に val の下位 1 ～ 64 ビットまでの、
     * 任意のビット数を書き出し、現在位置を進めます。
     * </p>
     *
     * @param n   書き込むビット数（64 ビットまで）
     * @param val 書き込むビットを含んだ符号無し整数値オブジェクト
     * @return バッファ
     * @throws IllegalArgumentException 読み出すビット数が不適切であるとき
     * @throws IndexOutOfBoundsException 現在位置がリミット以上である場合
     */
    public BitBuffer putUInt(int n, UInt val) {
        return put64(n, val.getBitsValue());
    }

    /**
     * <p>
     * 現在位置に val の下位 1 ～ 64 ビットまでの、
     * 任意のビット数を書き出し、現在位置を進めます。
     * </p>
     *
     * @param n   書き込むビット数（64 ビットまで）
     * @param val 書き込むビットを含んだ浮動小数値オブジェクト
     * @return バッファ
     * @throws IllegalArgumentException 読み出すビット数が不適切であるとき
     * @throws IndexOutOfBoundsException 現在位置がリミット以上である場合
     */
    public BitBuffer putF64(int n, Float64 val) {
        return put64(n, val.getBitsValue());
    }

    /**
     * <p>
     * 現在位置に val の下位 64 ビットを書き出し、
     * 現在位置を進めます。
     * </p>
     *
     * <p>
     * バイト順序を逆順に並べ替えた値が書き込まれます。
     * </p>
     *
     * @param n   書き込むビット数（64 ビット）
     * @param val 書き込むビットを含んだ符号付き整数値オブジェクト
     * @return バッファ
     * @throws IllegalArgumentException 読み出すビット数が不適切であるとき
     * @throws IndexOutOfBoundsException 現在位置がリミット以上である場合
     */
    public BitBuffer putS64r(int n, SInt val) {
        if (n != 64) {
            throw new IllegalArgumentException(
                    "putS64r() cannot put not equal 64 bits."
                            + "(" + n + "bits)");
        }
        return put64(n, Long.reverseBytes(val.getBitsValue()));
    }

    /**
     * <p>
     * 現在位置に val の下位 64 ビットを書き出し、
     * 現在位置を進めます。
     * </p>
     *
     * <p>
     * バイト順序を逆順に並べ替えた値が書き込まれます。
     * </p>
     *
     * @param n   書き込むビット数（64 ビット）
     * @param val 書き込むビットを含んだ符号無し整数値オブジェクト
     * @return バッファ
     * @throws IllegalArgumentException 読み出すビット数が不適切であるとき
     * @throws IndexOutOfBoundsException 現在位置がリミット以上である場合
     */
    public BitBuffer putU64r(int n, UInt val) {
        if (n != 64) {
            throw new IllegalArgumentException(
                    "putU64r() cannot put not equal 64 bits."
                            + "(" + n + "bits)");
        }
        return put64(n, Long.reverseBytes(val.getBitsValue()));
    }

    /**
     * <p>
     * 現在位置に val の下位 1 ～ 64 ビットまでの、
     * 任意のビット数を書き出し、現在位置を進めます。
     * </p>
     *
     * <p>
     * バイト順序を逆順に並べ替えた値が書き込まれます。
     * </p>
     *
     * @param n   書き込むビット数（64 ビットまで）
     * @param val 書き込むビットを含んだ浮動小数値オブジェクト
     * @return バッファ
     * @throws IllegalArgumentException 読み出すビット数が不適切であるとき
     * @throws IndexOutOfBoundsException 現在位置がリミット以上である場合
     */
    public BitBuffer putF64r(int n, Float64 val) {
        return put64(n, Long.reverseBytes(val.getBitsValue()));
    }

    /**
     * <p>
     * 現在位置に ary の先頭から、
     * 任意のビット数を書き出し、現在位置を進めます。
     * </p>
     *
     * @param n   書き込むビット数
     * @param ary 書き込むビットを含んだ配列オブジェクト
     * @return バッファ
     * @throws IllegalArgumentException 読み出すビット数が不適切であるとき
     * @throws IndexOutOfBoundsException 現在位置がリミット以上である場合
     */
    public BitBuffer putByteArray(int n, ByteArray ary) {
        return put(ary, 0, n);
    }

    /**
     * <p>
     * 現在位置に val の下位 1 ～ 64 ビットまでの、
     * 任意のビット数を書き出します。現在位置は変更しません。
     * </p>
     *
     * @param n   書き込むビット数（64 ビットまで）
     * @param val 書き込むビットを含んだ整数値
     * @return バッファ
     * @throws IllegalArgumentException 読み出すビット数が不適切であるとき
     * @throws IndexOutOfBoundsException 現在位置がリミット以上である場合
     */
    public BitBuffer poke64(int n, long val) {
        if (n < 0 || 64 < n) {
            throw new IllegalArgumentException(
                    "poke64() cannot put more than 64 bits."
                            + "(" + n + "bits)");
        }
        if (n > remaining()) {
            throw new IndexOutOfBoundsException(
                    "buffer is overflow, "
                            + "required(" + n + ") is exceeded "
                            + "remaining(" + remaining() + ").");
        }

        poke64Inner(n, val, getIndex(position, n));

        return this;
    }

    /**
     * <p>
     * 現在位置に val の下位 1 ～ 64 ビットまでの、
     * 任意のビット数を書き出し、現在位置を進めます。
     * </p>
     *
     * @param n   書き込むビット数（64 ビットまで）
     * @param val 書き込むビットを含んだ整数値
     * @return バッファ
     * @throws IllegalArgumentException 読み出すビット数が不適切であるとき
     * @throws IndexOutOfBoundsException 現在位置がリミット以上である場合
     */
    public BitBuffer put64(int n, long val) {
        if (n < 0 || 64 < n) {
            throw new IllegalArgumentException(
                    "put64() cannot put more than 64 bits."
                            + "(" + n + "bits)");
        }
        if (n > remaining()) {
            throw new IndexOutOfBoundsException(
                    "buffer is overflow, "
                            + "required(" + n + ") is exceeded "
                            + "remaining(" + remaining() + ").");
        }

        poke64Inner(n, val, getIndex(position, n));
        position += n;

        return this;
    }

    /**
     * <p>
     * 指定した位置に val の下位 1 ～ 64 ビットまでの、
     * 任意のビット数を書き込みます。
     * </p>
     *
     * @param n     書き込むビット数（64 ビットまで）
     * @param val   書き込むビットを含んだ整数値
     * @param index 書き込む位置
     */
    protected void poke64Inner(int n, long val, long index) {
        buf.setPackedLong(index, n, val);
    }
}
