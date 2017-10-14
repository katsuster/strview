package net.katsuster.strview.util.gui;

/**
 * <p>
 * 上下、左右の空白の大きさを表すクラスです。
 * </p>
 */
public class Space {
    //左側の空白の大きさ
    public int left;
    //上側の空白の大きさ
    public int top;
    //右側の空白の大きさ
    public int right;
    //下側の空白の大きさ
    public int bottom;

    /**
     * <p>
     * 空白の大きさをすべて 0 として新たなオブジェクトを生成します。
     * </p>
     */
    public Space() {
        this(0);
    }

    /**
     * <p>
     * 空白の大きさにすべて同じ値を指定して新たなオブジェクトを生成します。
     * </p>
     *
     * @param n 空白の大きさ
     */
    public Space(int n) {
        this(n, n, n, n);
    }

    /**
     * <p>
     * 指定された空白と同じ大きさの空白を指定して、
     * 新たなオブジェクトを生成します。
     * </p>
     *
     * @param s 元となる空白
     */
    public Space(Space s) {
        this(s.left, s.top, s.right, s.bottom);
    }

    /**
     * <p>
     * 左、上、右、下の空白の大きさを指定して新たなオブジェクトを生成します。
     * </p>
     *
     * @param l 左側の空白の大きさ
     * @param t 上側の空白の大きさ
     * @param r 右側の空白の大きさ
     * @param b 下側の空白の大きさ
     */
    public Space(int l, int t, int r, int b) {
        left = l;
        top = t;
        right = r;
        bottom = b;
    }
}
