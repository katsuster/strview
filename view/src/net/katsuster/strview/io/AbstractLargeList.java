package net.katsuster.strview.io;

/**
 * <p>
 * int 型で扱える長さを超えるリストの共通動作を定義します。
 * </p>
 *
 * @author katsuhiro
 */
public abstract class AbstractLargeList<T> extends AbstractLargeListBase<T>
        implements LargeList<T>, Cloneable {
    /**
     * <p>
     * 指定された長さのリストを作成します。
     * </p>
     *
     * @param from リストの開始点
     * @param to   リストの終了点
     */
    public AbstractLargeList(long from, long to) {
        super(from, to);
    }

    /**
     * <p>
     * 指定された長さのリストを作成します。
     * </p>
     *
     * @param l リストの長さ
     */
    public AbstractLargeList(long l) {
        super(l);
    }

    @Override
    public AbstractLargeList clone() throws CloneNotSupportedException {
        AbstractLargeList obj = (AbstractLargeList)super.clone();

        return obj;
    }

    @Override
    public T get(long index) {
        checkIndex(index);

        return getInner(index);
    }

    @Override
    public int get(long index, LargeList<T> dest, int offset, int length) {
        checkRemaining(index, length);

        return getInner(index, dest, offset, length);
    }

    @Override
    public void set(long index, T data) {
        checkIndex(index);

        setInner(index, data);
    }

    @Override
    public int set(long index, LargeList<T> src, int offset, int length) {
        checkRemaining(index, length);

        return setInner(index, src, offset, length);
    }

    /**
     * <p>
     * 指定された位置の要素を取得します。
     * </p>
     *
     * <p>
     * この関数は指定された位置をチェックしません。
     * リストの範囲内の位置を指定する必要があります。
     * 範囲外を指定した場合の動作は不定です。
     * </p>
     *
     * @param index 要素の位置
     * @return 指定された位置の要素
     */
    protected abstract T getInner(long index);

    /**
     * <p>
     * 指定された位置から、length の長さだけ要素を取得し、
     * リストに格納します。
     * </p>
     *
     * <p>
     * この関数は指定された位置をチェックしません。
     * リストの範囲内の位置を指定する必要があります。
     * 範囲外を指定した場合の動作は不定です。
     * </p>
     *
     * <p>
     * リストから getInner() メソッドにより、
     * 1つずつ要素を取得する実装になっています。
     * </p>
     *
     * <p>
     * 派生クラスにてより高速な実装が可能であれば、
     * オーバライドすることを推奨します。
     * </p>
     *
     * @param index  バッファの読み出し開始位置
     * @param dest   結果を格納するリスト
     * @param offset 結果の格納を開始する位置
     * @param length 読みだす要素数
     * @return 実際に読みだした要素数
     * @throws IndexOutOfBoundsException 読み出し位置が負、リストの範囲外の場合
     */
    protected int getInner(long index, LargeList<T> dest, int offset, int length) {
        int i;

        for (i = 0; i < length; i++) {
            dest.set(offset + i, getInner(index + i));
        }

        return i;
    }

    /**
     * <p>
     * 指定された位置に要素を設定します。
     * </p>
     *
     * <p>
     * この関数は指定された位置をチェックしません。
     * リストの範囲内の位置を指定する必要があります。
     * 範囲外を指定した場合の動作は不定です。
     * </p>
     *
     * @param index 要素の位置
     * @param data  指定された位置に設定する値
     */
    protected abstract void setInner(long index, T data);

    /**
     * <p>
     * 指定された位置から、length の長さだけ要素を設定します。
     * </p>
     *
     * <p>
     * この関数は指定された位置をチェックしません。
     * リストの範囲内の位置を指定する必要があります。
     * 範囲外を指定した場合の動作は不定です。
     * </p>
     *
     * <p>
     * リストへ setInner() メソッドにより、
     * 1つずつ要素を設定する実装になっています。
     * </p>
     *
     * <p>
     * 派生クラスにてより高速な実装が可能であれば、
     * オーバライドすることを推奨します。
     * </p>
     *
     * @param index  バッファの書き込み開始位置
     * @param src    バッファに書きこむリスト
     * @param offset データの書きこみを開始する位置
     * @param length 書きこむ要素数
     * @return 実際に書き込んだ要素数
     * @throws IndexOutOfBoundsException 書き込み位置が負、リストの範囲外の場合
     */
    protected int setInner(long index, LargeList<T> src, int offset, int length) {
        int i;

        for (i = 0; i < length; i++) {
            setInner(index + i, src.get(offset + i));
        }

        return i;
    }
}
