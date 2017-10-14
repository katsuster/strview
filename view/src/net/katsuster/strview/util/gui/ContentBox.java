package net.katsuster.strview.util.gui;

import java.awt.*;

/**
 * <p>
 * コンテンツの描画領域をあらわすクラスです。
 * </p>
 *
 * <p>
 * 下記の構成要素からなります。
 * </p>
 *
 * <ul>
 * <li>描画領域（Bounds）</li>
 * <li>描画領域と枠の間の余白（Margin）</li>
 * <li>枠（Border）</li>
 * <li>枠とコンテンツの間の空白（Padding）</li>
 * <li>コンテンツ領域（Contents）</li>
 * </ul>
 *
 * <pre>
 *  (X, Y)          width
 *        +---------------------------+ ___ Bounds
 *        |                           |/
 *        |  +--------------------+  ______ Margin
 *        |  |                    | / |
 *        |  |  +-------------+   | _______ Border
 *        |  |  |aaaaaaaaaaaaa|   |/  |
 *        |  |  |bbbbbbbbbbbbb|  __________ Padding
 * height |  |  |ccccccccccccc| / |   |
 *        |  |  |ddddddddddddd| ___________ Contents
 *        |  |  |eeeeeeeeeeeee|/  |   |
 *        |  |  |fffffffffffff|   |   |
 *        |  |  +-------------+   |   |
 *        |  |                    |   |
 *        |  +--------------------+   |
 *        +---------------------------+
 * </pre>
 *
 * <p>
 * このうち大きさや位置を設定可能なのは、
 * 描画領域（Bounds）、描画領域と枠の間の余白（Margin）、
 * 枠とコンテンツの間の空白（Padding）のみです。
 * <br>
 * 枠（Border）の大きさは、
 * Bounds と Margin から自動的に決まります。
 * また、コンテンツ領域（Contents）の大きさは、
 * Border と Padding から自動的に決まります。
 * </p>
 */
public class ContentBox {
    //描画領域
    private Rectangle bounds;
    //描画領域と枠の間の余白
    private Space margin;
    //枠とコンテンツの間の空白
    private Space padding;

    /**
     * <p>
     * 描画領域の位置 (0, 0)、
     * 大きさ（Bounds）なし、
     * 余白（Margin）なし、
     * 空白（Padding）なしのオブジェクトを生成します。
     * </p>
     */
    public ContentBox() {
        this(0, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 0);
    }

    /**
     * <p>
     * 描画領域の大きさ（Bounds）を指定して、
     * 余白（Margin）なし、
     * 空白（Padding）なしのオブジェクトを生成します。
     * </p>
     *
     * @param x  描画領域の X 位置
     * @param y  描画領域の Y 位置
     * @param w  描画領域の幅
     * @param h  描画領域の高さ
     */
    public ContentBox(int x, int y, int w, int h) {
        this(x, y, w, h,
                0, 0, 0, 0,
                0, 0, 0, 0);
    }

    /**
     * <p>
     * 描画領域の大きさ（Bounds）、
     * 余白（Margin）の大きさを指定して、
     * 空白（Padding）なしのオブジェクトを生成します。
     * </p>
     *
     * @param x  描画領域の X 位置
     * @param y  描画領域の Y 位置
     * @param w  描画領域の幅
     * @param h  描画領域の高さ
     * @param ml 左側の余白（Margin）の大きさ
     * @param mt 上側の余白（Margin）の大きさ
     * @param mr 右側の余白（Margin）の大きさ
     * @param mb 下側の余白（Margin）の大きさ
     */
    public ContentBox(int x, int y, int w, int h,
                      int ml, int mt, int mr, int mb) {
        this(x, y, w, h,
                ml, mt, mr, mb,
                0, 0, 0, 0);
    }

    /**
     * <p>
     * 描画領域の大きさ（Bounds）、余白（Margin）、空白（Padding）、
     * の大きさを指定してオブジェクトを生成します。
     * </p>
     *
     * @param x  描画領域の X 位置
     * @param y  描画領域の Y 位置
     * @param w  描画領域の幅
     * @param h  描画領域の高さ
     * @param ml 左側の余白（Margin）の大きさ
     * @param mt 上側の余白（Margin）の大きさ
     * @param mr 右側の余白（Margin）の大きさ
     * @param mb 下側の余白（Margin）の大きさ
     * @param pl 左側の空白（Padding）の大きさ
     * @param pt 上側の空白（Padding）の大きさ
     * @param pr 右側の空白（Padding）の大きさ
     * @param pb 下側の空白（Padding）の大きさ
     */
    public ContentBox(int x, int y, int w, int h,
                      int ml, int mt, int mr, int mb,
                      int pl, int pt, int pr, int pb) {
        bounds = new Rectangle(x, y, w, h);
        margin = new Space(ml, mt, mr, mb);
        padding = new Space(pl, pt, pr, pb);
    }

    /**
     * <p>
     * 描画領域の X 座標を取得します。
     * </p>
     *
     * @return 描画領域の X 座標
     */
    public int getX() {
        return bounds.x;
    }

    /**
     * <p>
     * 描画領域の X 座標を設定します。
     * </p>
     *
     * @param x 描画領域の X 座標
     */
    public void setX(int x) {
        bounds.x = x;
    }

    /**
     * <p>
     * 描画領域の Y 座標を取得します。
     * </p>
     *
     * @return 描画領域の Y 座標
     */
    public int getY() {
        return bounds.y;
    }

    /**
     * <p>
     * 描画領域の Y 座標を設定します。
     * </p>
     *
     * @param y 描画領域の Y 座標
     */
    public void setY(int y) {
        bounds.y = y;
    }

    /**
     * <p>
     * 描画領域の幅を取得します。
     * </p>
     *
     * @return 描画領域の幅
     */
    public int getWidth() {
        return bounds.width;
    }

    /**
     * <p>
     * 描画領域の幅を設定します。
     * </p>
     *
     * @param w 描画領域の幅
     */
    public void setWidth(int w) {
        bounds.width = w;
    }

    /**
     * <p>
     * 描画領域の高さを取得します。
     * </p>
     *
     * @return 描画領域の高さ
     */
    public int getHeight() {
        return bounds.height;
    }

    /**
     * <p>
     * 描画領域の高さを設定します。
     * </p>
     *
     * @param h 描画領域の高さ
     */
    public void setHeight(int h) {
        bounds.height = h;
    }

    /**
     * <p>
     * 描画領域（Bounds）を取得します。
     * </p>
     *
     * @return 描画領域
     */
    public Rectangle getBounds() {
        return new Rectangle(bounds);
    }

    /**
     * <p>
     * 描画領域（Bounds）を設定します。
     * </p>
     *
     * @param x  描画領域の X 位置
     * @param y  描画領域の Y 位置
     * @param w  描画領域の幅
     * @param h  描画領域の高さ
     */
    public void setBounds(int x, int y, int w, int h) {
        bounds = new Rectangle(x, y, w, h);
    }

    /**
     * <p>
     * 描画領域（Bounds）を設定します。
     * </p>
     *
     * @param r 描画領域
     */
    public void setBounds(Rectangle r) {
        bounds = new Rectangle(r);
    }

    /**
     * <p>
     * 描画領域と枠の間の余白（Margin）を取得します。
     * </p>
     *
     * @return 余白
     */
    public Space getMargin() {
        return new Space(margin);
    }

    /**
     * <p>
     * 描画領域と枠の間の余白（Margin）を設定します。
     * </p>
     *
     * @param l 左側の余白
     * @param t 上側の余白
     * @param r 右側の余白
     * @param b 下側の余白
     */
    public void setMargin(int l, int t, int r, int b) {
        margin = new Space(l, t, r, b);
    }

    /**
     * <p>
     * 描画領域と枠の間の余白（Margin）を設定します。
     * </p>
     *
     * @param s 余白
     */
    public void setMargin(Space s) {
        margin = new Space(s);
    }

    /**
     * <p>
     * 枠（Border）を取得します。
     * </p>
     *
     * @return 枠
     */
    public Rectangle getBorder() {
        return new Rectangle(
                bounds.x + margin.left,
                bounds.y + margin.top,
                bounds.width - margin.left - margin.right,
                bounds.height - margin.top - margin.bottom);
    }

    /**
     * <p>
     * 枠とコンテンツの間の空白（Padding）を取得します。
     * </p>
     *
     * @return 空白
     */
    public Space getPadding() {
        return new Space(padding);
    }

    /**
     * <p>
     * 枠とコンテンツの間の空白（Padding）を設定します。
     * </p>
     *
     * @param l 左側の空白
     * @param t 上側の空白
     * @param r 右側の空白
     * @param b 下側の空白
     */
    public void setPadding(int l, int t, int r, int b) {
        padding = new Space(l, t, r, b);
    }

    /**
     * <p>
     * 枠とコンテンツの間の空白（Padding）を設定します。
     * </p>
     *
     * @param s 空白
     */
    public void setPadding(Space s) {
        padding = new Space(s);
    }

    /**
     * <p>
     * コンテンツ領域（Contents）を取得します。
     * </p>
     *
     * @return コンテンツ領域
     */
    public Rectangle getContents() {
        Rectangle border = getBorder();

        return new Rectangle(
                border.x + padding.left,
                border.y + padding.top,
                border.width - padding.left - padding.right,
                border.height - padding.top - padding.bottom);
    }
}
