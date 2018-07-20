package net.katsuster.strview.gui.view;

import java.io.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import net.katsuster.strview.util.*;
import net.katsuster.strview.util.bit.*;
import net.katsuster.strview.util.gui.*;
import net.katsuster.strview.io.*;

/**
 * <p>
 * バイナリデータを表示するビューアです。
 * </p>
 */
public class BinaryViewer extends JPanel {
    private static final long serialVersionUID = 1L;

    //バイナリデータ表示パネル
    private BinaryViewerInner viewer;
    //右端のスクロールバー
    private JScrollBar scr;

    //表示させるビットデータ
    private LargeBitList buf = null;

    //表示開始するアドレス（バイト単位）
    private long drawAddress = 0;
    //1列に表示させるバイト数
    private int length_raw = 16;
    //1列の高さ（ピクセル）
    private int height_raw = 16;

    //表示するデータ範囲
    private Range[] ranges;
    //アドレスの色
    private Color color_addr;
    //フォーカスを示す色
    private Color color_focus;
    //表示する色
    private Color[] colors;

    //表示領域
    private ContentBox box_all;
    //アドレス表示領域
    private ContentBox box_addr_area;
    //アドレス（1列）表示領域
    private ContentBox box_addr;
    //データ表示領域
    private ContentBox box_data_area;
    //データ（1列）表示領域
    private ContentBox box_data_raw;
    //データ（1バイト）表示領域
    private ContentBox box_data;
    //文字列表示領域
    private ContentBox box_ascii_area;
    //データ（1列）表示領域
    private ContentBox box_ascii_raw;
    //文字列（1文字）表示領域
    private ContentBox box_ascii;

    protected BinaryViewer() {
        //do nothing
    }

    public BinaryViewer(File f) {
        this(new ByteToBitList(new FileByteList(f.getAbsolutePath())));
    }

    public BinaryViewer(LargeList<Byte> l) {
        this(new ByteToBitList(l));
    }

    public BinaryViewer(LargeBitList l) {
        //表示する範囲を初期化する
        ranges = new Range[PRIORITY.MAX];
        for (int i = 0; i < PRIORITY.MAX; i++) {
            ranges[i] = new SimpleRange(0, 0);
        }
        setDataRange(new SimpleRange(0, 0));
        setHighlightRange(new SimpleRange(0, 0));
        setHighlightMemberRange(new SimpleRange(0, 0));

        //フォントの色を初期化する
        color_addr = Color.BLACK;
        color_focus = Color.GRAY;
        colors = new Color[PRIORITY.MAX];
        for (int i = 0; i < PRIORITY.MAX; i++) {
            colors[i] = Color.BLACK;
        }
        setDataColor(Color.BLACK);
        setHighlightColor(Color.RED);
        setHighlightMemberColor(Color.BLUE);
        setBackground(Color.WHITE);

        setColorsFromUI();

        //表示領域を初期化する
        box_all = new ContentBox();
        box_addr_area = new ContentBox();
        box_addr = new ContentBox();
        box_data_area = new ContentBox();
        box_data_raw = new ContentBox();
        box_data = new ContentBox();
        box_ascii_area = new ContentBox();
        box_ascii_raw = new ContentBox();
        box_ascii = new ContentBox();

        box_all.setMargin(5, 0, 5, 0);
        box_addr_area.setPadding(5, 0, 5, 0);
        box_data_area.setPadding(5, 0, 5, 0);
        box_ascii_area.setPadding(5, 0, 5, 0);

        setFocusable(true);
        setLayout(new BorderLayout());

        addKeyListener(new BinaryViewerKeyListener());
        addMouseListener(new BinaryViewerMouseListener());
        addMouseWheelListener(new BinaryViewerWheelListener());
        addComponentListener(new BinaryViewerComponentListener());
        addFocusListener(new BinaryViewerFocusListener());

        //中央にバイナリデータ表示パネルを配置する
        viewer = new BinaryViewerInner(this);
        add(viewer, BorderLayout.CENTER);

        //右端にスクロールバーを配置する
        scr = new JScrollBar();
        scr.getModel().addChangeListener(viewer);
        add(scr, BorderLayout.EAST);

        //表示するファイルを設定する
        //スクロールバーの最大値をいじるので、GUI 作成の後でないとだめ
        setBinary(l);
    }

    /**
     * <p>
     * UI の Look & feel が使用する色に合わせます。
     * </p>
     */
    public void setColorsFromUI() {
        Color c;

        //"EditorPane.background"
        //"EditorPane.caretForeground"
        //"EditorPane.foreground"
        //"EditorPane.inactiveBackground"
        //"EditorPane.inactiveForeground"
        //"EditorPane.selectionBackground"
        //"EditorPane.selectionForeground"

        c = UIManager.getColor("EditorPane.background");
        if (c != null) {
            setBackground(c);
        }

        c = UIManager.getColor("EditorPane.foreground");
        if (c != null) {
            color_addr = c;
            setDataColor(c);
        }

        c = UIManager.getColor("EditorPane.selectionBackground");
        if (c != null) {
            color_focus = c;
        }
    }

    /**
     * <p>
     * バイナリデータの読み出し元のビット列を取得します。
     * </p>
     *
     * @return データの格納されているビット列
     */
    public LargeBitList getBinary() {
        return buf;
    }

    /**
     * <p>
     * バイナリデータの読み出し元のビット列を設定します。
     * </p>
     *
     * @param l データの格納されているビット列
     */
    public void setBinary(LargeBitList l) {
        long lines;

        buf = l;

        //全域を通常のデータ表示領域とする
        setDataRange(new SimpleRange(0, getLength()));

        //1行（getLengthOfRaw() バイト）ずつスクロールできるように、
        //スクロールバー最大値はファイルの行数とする
        lines = getLength() / getLengthOfRaw() + 1;
        if (Integer.MAX_VALUE < lines) {
            scr.setMaximum(Integer.MAX_VALUE);
        } else {
            scr.setMaximum((int)lines);
        }
    }

    /**
     * <p>
     * バイナリデータのサイズを取得します。
     * </p>
     *
     * @return サイズ（バイト単位）
     */
    protected long getLength() {
        if (buf == null) {
            return 0;
        }

        return (buf.length() >>> 3);
    }

    /**
     * <p>
     * 描画開始するアドレスを取得します。
     * </p>
     *
     * @return 描画開始するアドレス（バイト単位）
     */
    protected long getDrawAddress() {
        return drawAddress;
    }

    /**
     * <p>
     * 描画開始するアドレスを設定します。
     * </p>
     *
     * @param addr 描画開始するアドレス（バイト単位）
     */
    protected void setDrawAddress(long addr) {
        if (getLength() < addr) {
            addr = getLength();
        }

        drawAddress = addr;
    }

    /**
     * <p>
     * 列から描画開始アドレス（バイト単位）を取得します。
     * </p>
     *
     * @param raw 列
     * @param max 列の最大値
     * @return 列の先頭アドレス
     */
    protected long rawToAddress(long raw, long max) {
        long lines = getLength() / getLengthOfRaw() + 1;
        double pos = (double)lines * raw / max;

        return (long)(pos * getLengthOfRaw());
    }

    /**
     * <p>
     * 表示開始するアドレスを取得します。
     * </p>
     *
     * @return 表示開始するアドレス
     */
    public long getAddress() {
        return drawAddress;
    }

    /**
     * <p>
     * 表示開始するアドレスを設定します。
     * </p>
     *
     * @param addr 表示開始するアドレス
     */
    public void setAddress(long addr) {
        scr.setValue((int)addr / getLengthOfRaw());
    }

    /**
     * <p>
     * 一列に表示させるバイト数を取得します。
     * </p>
     *
     * @return 一列に表示させるバイト数
     */
    public int getLengthOfRaw() {
        return length_raw;
    }

    /**
     * <p>
     * 一列に表示させるバイト数を設定します。
     * </p>
     *
     * @param w 一列に表示させるバイト数
     */
    public void setLengthOfRaw(int w) {
        length_raw = w;
    }

    /**
     * <p>
     * 一列の高さ（ピクセル数）を取得します。
     * </p>
     *
     * @return 一列の高さ（ピクセル数）
     */
    public int getHeightOfRaw() {
        return height_raw;
    }

    /**
     * <p>
     * 一列の高さ（ピクセル数）を取得します。
     * </p>
     *
     * @param h 一列の高さ（ピクセル数）
     */
    public void setHeightOfRaw(int h) {
        height_raw = h;
    }

    /**
     * <p>
     * 指定したアドレスを描画する色を取得します。
     * </p>
     *
     * @param a アドレス
     * @return 色
     */
    public Color getAddressColor(long a) {
        return color_addr;
    }

    /**
     * <p>
     * 指定したアドレス範囲を描画する色を設定します。
     * </p>
     *
     * @param r アドレス範囲
     * @param c 色
     */
    public void setAddressColor(Range r, Color c) {
        color_addr = c;
    }

    /**
     * <p>
     * バイナリデータを通常描画する際の色を取得します。
     * </p>
     *
     * @return バイナリデータを通常描画する際の色
     */
    public Color getDataColor() {
        return colors[PRIORITY.NORMAL];
    }

    /**
     * <p>
     * バイナリデータを通常描画する際の色を設定します。
     * </p>
     *
     * @param c バイナリデータを通常描画する際の色
     */
    public void setDataColor(Color c) {
        colors[PRIORITY.NORMAL] = c;
    }

    /**
     * <p>
     * バイナリデータを通常描画する範囲を設定します。
     * </p>
     *
     * @return バイナリデータを通常描画する範囲
     */
    public Range getDataRange() {
        return ranges[PRIORITY.NORMAL];
    }

    /**
     * <p>
     * バイナリデータを通常描画する範囲を設定します。
     * </p>
     *
     * @param r バイナリデータを通常描画する範囲
     */
    public void setDataRange(Range r) {
        ranges[PRIORITY.NORMAL] = r;
    }

    /**
     * <p>
     * バイナリデータを通常描画する範囲を設定します。
     * </p>
     *
     * @param s バイナリデータを通常描画する範囲の開始
     * @param l バイナリデータを通常描画する範囲の長さ
     */
    public void setDataRange(long s, long l) {
        setDataRange(new SimpleRange(s, l));
    }

    /**
     * <p>
     * バイナリデータを強調して描画する際の色を取得します。
     * </p>
     *
     * @return バイナリデータを強調して描画する際の色
     */
    public Color getHighlightColor() {
        return colors[PRIORITY.HIGHLIGHT_NODE];
    }

    /**
     * <p>
     * バイナリデータを強調して描画する際の色を設定します。
     * </p>
     *
     * @param c バイナリデータを強調して描画する際の色
     */
    public void setHighlightColor(Color c) {
        colors[PRIORITY.HIGHLIGHT_NODE] = c;
    }

    /**
     * <p>
     * バイナリデータを強調して描画する範囲を取得します。
     * </p>
     *
     * @return バイナリデータを強調して描画する範囲
     */
    public Range getHighlightRange() {
        return ranges[PRIORITY.HIGHLIGHT_NODE];
    }

    /**
     * <p>
     * バイナリデータを強調して描画する範囲を設定します。
     * </p>
     *
     * @param r バイナリデータを強調して描画する範囲
     */
    public void setHighlightRange(Range r) {
        ranges[PRIORITY.HIGHLIGHT_NODE] = r;
    }

    /**
     * <p>
     * バイナリデータを強調して描画する範囲を設定します。
     * </p>
     *
     * @param s バイナリデータを強調して描画する範囲の開始
     * @param l バイナリデータを強調して描画する範囲の長さ
     */
    public void setHighlightRange(long s, long l) {
        setHighlightRange(new SimpleRange(s, l));
    }

    /**
     * <p>
     * バイナリデータをさらに強調して描画する際の色を取得します。
     * </p>
     *
     * @return バイナリデータをさらに強調して描画する際の色
     */
    public Color getHighlightMemberColor() {
        return colors[PRIORITY.HIGHLIGHT_MEMBER];
    }

    /**
     * <p>
     * バイナリデータをさらに強調して描画する際の色を設定します。
     * </p>
     *
     * @param c バイナリデータをさらに強調して描画する際の色
     */
    public void setHighlightMemberColor(Color c) {
        colors[PRIORITY.HIGHLIGHT_MEMBER] = c;
    }

    /**
     * <p>
     * バイナリデータをさらに強調して描画する範囲を取得します。
     * </p>
     *
     * @return バイナリデータをさらに強調して描画する範囲
     */
    public Range getHighlightMemberRange() {
        return ranges[PRIORITY.HIGHLIGHT_MEMBER];
    }

    /**
     * <p>
     * バイナリデータをさらに強調して描画する範囲を設定します。
     * </p>
     *
     * @param r バイナリデータをさらに強調して描画する範囲
     */
    public void setHighlightMemberRange(Range r) {
        ranges[PRIORITY.HIGHLIGHT_MEMBER] = r;
    }

    /**
     * <p>
     * バイナリデータをさらに強調して描画する範囲を設定します。
     * </p>
     *
     * @param s バイナリデータをさらに強調して描画する範囲の開始
     * @param l バイナリデータをさらに強調して描画する範囲の長さ
     */
    public void setHighlightMemberRange(long s, long l) {
        setHighlightMemberRange(new SimpleRange(s, l));
    }

    protected class BinaryViewerKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int lines = Math.max(1, viewer.getDataLines() - 3);

            switch (e.getKeyCode()) {
            case KeyEvent.VK_HOME:
                scr.setValue(scr.getMinimum());
                break;
            case KeyEvent.VK_PAGE_UP:
                scr.setValue(scr.getValue() - lines);
                break;
            case KeyEvent.VK_PAGE_DOWN:
                scr.setValue(scr.getValue() + lines);
                break;
            case KeyEvent.VK_UP:
                scr.setValue(scr.getValue() - 1);
                break;
            case KeyEvent.VK_DOWN:
                scr.setValue(scr.getValue() + 1);
                break;
            case KeyEvent.VK_END:
                scr.setValue(scr.getMaximum());
                break;
            }
        }
    }

    protected class BinaryViewerMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            requestFocus();
        }
    }

    protected class BinaryViewerWheelListener
            implements MouseWheelListener {
        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            scr.setValue(scr.getValue() + e.getWheelRotation() * 3);
        }
    }

    protected class BinaryViewerComponentListener extends ComponentAdapter {
        @Override
        public void componentResized(ComponentEvent e) {
            //表示領域を更新する
            box_all.setWidth(viewer.getWidth());
            box_all.setHeight(viewer.getHeight());

            Rectangle all_contents = box_all.getContents();
            box_addr_area.setHeight(all_contents.height);
            box_data_area.setHeight(all_contents.height);
            box_ascii_area.setHeight(all_contents.height);

            //スクロールバーの extent = 一画面に入る行数とする
            scr.getModel().setExtent(
                    all_contents.height / box_addr_area.getHeight());

            viewer.repaint();
        }
    }

    protected class BinaryViewerFocusListener
            implements FocusListener {
        @Override
        public void focusGained(FocusEvent e) {
            viewer.repaint();
        }

        @Override
        public void focusLost(FocusEvent e) {
            viewer.repaint();
        }
    }

    /**
     * <p>
     * バイナリデータを描画するパネルです。
     * </p>
     */
    protected class BinaryViewerInner extends JComponent
            implements ChangeListener {
        private static final long serialVersionUID = 1L;

        private BinaryViewer parent;

        public BinaryViewerInner(BinaryViewer p) {
            parent = p;
        }

        public int getDataLines() {
            Rectangle all_data = box_data_area.getContents();
            int dy = all_data.y;
            int lines = 0;

            while (dy < all_data.height) {
                dy += box_data_raw.getHeight();
                lines++;
            }

            return lines;
        }

        protected void drawAll(Graphics2D g, long addr) {
            int width_f, height_f, dx, dy;

            //フォントメトリックから表示領域の大きさを計算する
            FontMetrics met = g.getFontMetrics();

            //表示領域
            width_f = met.stringWidth("F");
            height_f = Math.max(met.getHeight(), getHeightOfRaw());
            box_addr_area.setWidth(width_f * 10);
            box_addr.setWidth(width_f * 10);
            box_addr.setHeight(height_f);

            box_data_area.setWidth(width_f * 3 * (getLengthOfRaw() + 1));
            box_data_raw.setWidth(width_f * 3 * (getLengthOfRaw() + 1));
            box_data_raw.setHeight(height_f);
            box_data.setWidth(width_f * 3);
            box_data.setHeight(height_f);

            box_ascii_area.setWidth(width_f * 1 * (getLengthOfRaw() + 1));
            box_ascii_raw.setWidth(width_f * 1 * (getLengthOfRaw() + 1));
            box_ascii_raw.setHeight(height_f);
            box_ascii.setWidth(width_f * 1);
            box_ascii.setHeight(height_f);


            //マージンを除いた領域を描画に使う
            Rectangle all_contents = box_all.getContents();
            dx = all_contents.x;
            dy = all_contents.y;

            //アドレス、データ、文字列表現を描画する
            box_addr_area.setX(dx);
            box_addr_area.setY(dy);
            drawAddressAll(g, addr);
            dx += box_addr_area.getWidth();

            box_data_area.setX(dx);
            box_data_area.setY(dy);
            drawDataAll(g, addr);
            dx += box_data_area.getWidth();

            box_ascii_area.setX(dx);
            box_ascii_area.setY(dy);
            drawAsciiAll(g, addr);
            dx += box_ascii_area.getWidth();
        }

        protected void drawAddressAll(Graphics2D g, long addr) {
            //マージンを除いた領域を描画に使う
            Rectangle all_addr = box_addr_area.getContents();
            int dx = all_addr.x;
            int dy = all_addr.y;

            while (dy < all_addr.height) {
                box_addr.setX(dx);
                box_addr.setY(dy);
                drawAddress(g, addr);

                addr += getLengthOfRaw();
                dy += box_addr.getHeight();
            }

            //枠を描画
            //Rectangle borders_addr = box_addr_area.getBorder();
            //g.drawRoundRect(borders_addr.x, borders_addr.y,
            //		borders_addr.width, borders_addr.height,
            //		14, 14);
        }

        /**
         * <p>
         * 適切な色を選択しながら指定されたアドレスを描画します。
         * </p>
         *
         * <p>
         * アドレスを描画するサイズ、マージンの設定については、
         * TODO: (to be determined)
         * を使用してください。
         * </p>
         *
         * @param g    描画を行うグラフィクス
         * @param addr アドレス（バイト単位）
         */
        protected void drawAddress(Graphics2D g, long addr) {
            //マージンを除いた領域を描画に使う
            Rectangle raw_addr = box_addr.getContents();

            //文字列の色に設定する
            Color c = g.getColor();
            g.setColor(getAddressColor(addr));

            //描画する
            //drawString の Y 座標は文字のベースラインの位置のため、
            //本来ベースラインから文字の頭までの高さを足すべきだが、
            //簡易処理として描画領域の高さを足している
            String str = String.format("%08x", addr);
            g.drawString(str, raw_addr.x, raw_addr.y + raw_addr.height);

            //元の色に戻す
            g.setColor(c);
        }

        protected void drawDataAll(Graphics2D g, long addr) {
            //マージンを除いた領域を描画に使う
            Rectangle all_data = box_data_area.getContents();
            int dx = all_data.x;
            int dy = all_data.y;

            while (dy < all_data.height) {
                box_data_raw.setX(dx);
                box_data_raw.setY(dy);
                drawDataRaw(g, addr);

                addr += getLengthOfRaw();
                dy += box_data_raw.getHeight();
            }

            //枠を描画
            //Rectangle borders_data = box_data_area.getBorder();
            //g.drawRoundRect(borders_data.x, borders_data.y,
            //		borders_data.width, borders_data.height,
            //		14, 14);
        }

        protected void drawDataRaw(Graphics2D g, long addr) {
            byte[] buf_data;
            int buflen;
            int i;

            //1列分のデータリード
            buflen = (int)Math.min(getLengthOfRaw(), getLength() - addr);
            buflen = Math.max(buflen, 0);
            buf_data = new byte[getLengthOfRaw()];
            if (buflen > 0) {
                buf.getPackedByteArray(addr << 3, buf_data, 0, buflen << 3);
                //for (j = 0; j < buflen; j++) {
                //	buf_data[j] = buf.getPackedByte((addr + j) << 3, 8);
                //}
            }

            //マージンを除いた領域を描画に使う
            Rectangle raw_data = box_data_raw.getContents();
            int dx = raw_data.x;
            int dy = raw_data.y;

            //元の色を覚えておく
            Color c = g.getColor();

            //1列分のデータ描画
            //1列分に満たない部分は ".." で埋める
            for (i = 0; i < getLengthOfRaw(); i++) {
                box_data.setX(dx);
                box_data.setY(dy);

                if (i < buflen) {
                    drawData(g, addr + i, buf_data[i]);
                } else {
                    drawDataOther(g, "..");
                }
                dx += box_data.getWidth();

                //8バイトごとに区切り文字を入れる
                if ((i & 7) == 7 && (i + 1 < getLengthOfRaw())) {
                    box_data.setX(dx);
                    box_data.setY(dy);
                    drawDataOther(g, "-");

                    dx += box_data.getWidth() / 2;
                }
            }

            //元の色に戻す
            g.setColor(c);
        }

        /**
         * <p>
         * 適切な色を選択しながら指定されたデータを描画します。
         * </p>
         *
         * <p>
         * データを描画するサイズ、マージンの設定については、
         * TODO: (to be determined)
         * を使用してください。
         * </p>
         *
         * @param g    描画を行うグラフィクス
         * @param addr アドレス
         * @param data 描画するデータ
         */
        protected void drawData(Graphics2D g, long addr, byte data) {
            //マージンを除いた領域を描画に使う
            Rectangle one_data = box_data.getContents();

            //文字列の色を設定する
            g.setColor(getRangeColor(addr));

            //描画する
            //drawString の Y 座標は文字のベースラインの位置のため、
            //本来ベースラインから文字の頭までの高さを足すべきだが、
            //簡易処理として描画領域の高さを足している
            String str = String.format("%02x", data & 0xff);
            g.drawString(str, one_data.x, one_data.y + one_data.height);
        }

        protected void drawDataOther(Graphics2D g, String s) {
            //マージンを除いた領域を描画に使う
            Rectangle one_data = box_data.getContents();

            //その他のデータの色に設定する
            g.setColor(color_addr);

            //描画する
            //drawString の Y 座標は文字のベースラインの位置のため、
            //本来ベースラインから文字の頭までの高さを足すべきだが、
            //簡易処理として描画領域の高さを足している
            g.drawString(s, one_data.x, one_data.y + one_data.height);
        }

        protected void drawAsciiAll(Graphics2D g, long addr) {
            //マージンを除いた領域を描画に使う
            Rectangle all_ascii = box_ascii_area.getContents();
            int dx = all_ascii.x;
            int dy = all_ascii.y;

            while (dy < all_ascii.height) {
                box_ascii_raw.setX(dx);
                box_ascii_raw.setY(dy);
                drawAsciiRaw(g, addr);

                addr += getLengthOfRaw();
                dy += box_ascii_raw.getHeight();
            }

            //枠を描画
            //Rectangle borders_ascii = box_ascii_area.getBorder();
            //g.drawRoundRect(borders_ascii.x, borders_ascii.y,
            //		borders_ascii.width, borders_ascii.height,
            //		14, 14);
        }

        protected void drawAsciiRaw(Graphics2D g, long addr) {
            byte[] buf_data, buf_char;
            String str_char;
            int buflen;
            int i;

            //1列分のデータを文字列に変換
            buflen = (int)Math.min(getLengthOfRaw(), getLength() - addr);
            buflen = Math.max(buflen, 0);
            buf_data = new byte[getLengthOfRaw()];
            buf_char = new byte[getLengthOfRaw()];
            str_char = "";
            try {
                if (buflen > 0) {
                    buf.getPackedByteArray(addr << 3, buf_data, 0, buflen << 3);
                    //for (j = 0; j < buflen; j++) {
                    //	buf_data[j] = buf.getPackedByte((addr + j) << 3, 8);
                    //}
                }

                for (i = 0; i < buf_data.length; i++) {
                    if (0x20 <= (buf_data[i] & 0xff)
                            && (buf_data[i] & 0xff) <= 0x7e) {
                        buf_char[i] = buf_data[i];
                    } else {
                        buf_char[i] = 0x2e;
                    }
                }
                str_char = new String(buf_char, "US-ASCII");
            } catch (IOException ex) {
                //do nothing
            }

            //マージンを除いた領域を描画に使う
            Rectangle raw_ascii = box_ascii_raw.getContents();
            int dx = raw_ascii.x;
            int dy = raw_ascii.y;

            //元の色を記憶しておく
            Color c = g.getColor();

            //1列分の文字列を描画する
            //1列分に満たない部分は空白とする
            for (i = 0; i < getLengthOfRaw(); i++) {
                box_ascii.setX(dx);
                box_ascii.setY(dy);
                if (i < buflen) {
                    drawAscii(g, addr + i, str_char.substring(i, i + 1));
                }

                dx += box_ascii.getWidth();
            }

            //元の色に戻す
            g.setColor(c);
        }

        /**
         * <p>
         * 適切な色を選択しながら指定されたデータの文字列表現を描画します。
         * </p>
         *
         * <p>
         * データの文字列表現を描画するサイズ、マージンの設定については、
         * TODO: (to be determined)
         * を使用してください。
         * </p>
         *
         * @param g    描画を行うグラフィクス
         * @param addr アドレス
         * @param s    描画するデータの文字列表現
         */
        protected void drawAscii(Graphics2D g, long addr, String s) {
            //マージンを除いた領域を描画に使う
            Rectangle one_ascii = box_ascii.getContents();

            //文字列の色を設定する
            g.setColor(getRangeColor(addr));

            //描画する
            //drawString の Y 座標は文字のベースラインの位置のため、
            //本来ベースラインから文字の頭までの高さを足すべきだが、
            //簡易処理として描画領域の高さを足している
            g.drawString(s, one_ascii.x, one_ascii.y + one_ascii.height);
        }

        /**
         * <p>
         * 指定したアドレス、データを描画する色を取得します。
         * </p>
         *
         * <p>
         * 見つからなければ黒を返します。
         * </p>
         *
         * @param addr 描画するアドレス
         * @return 描画に使う色
         */
        private Color getRangeColor(long addr) {
            Color c = Color.BLACK;
            for (int i = 0; i < PRIORITY.MAX; i++) {
                if (ranges[i].isHit(addr)) {
                    c = colors[i];
                    break;
                }
            }

            return c;
        }

        @Override
        public void stateChanged(ChangeEvent e) {
            setDrawAddress(rawToAddress(scr.getValue(), scr.getMaximum()));

            repaint();
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);

            if (buf == null) {
                //do nothing
                return;
            }

            Graphics2D g2 = (Graphics2D)g.create();
            if (parent.isFocusOwner()) {
                int w = 2;
                g2.setColor(color_focus);
                Rectangle r = box_all.getBounds();
                g2.drawRoundRect(w, w, r.width - w * 2, r.height - w * 2, 0, 0);
            }

            g2 = (Graphics2D)g.create();
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            drawAll(g2, getDrawAddress());
        }
    }

    /**
     * <p>
     * 範囲の優先度の定義です。小さい番号ほど優先度が高くなります。
     * </p>
     */
    public static class PRIORITY {
        public static final int HIGHLIGHT_MEMBER = 0;
        public static final int HIGHLIGHT_NODE   = 1;
        public static final int NORMAL           = 2;
        public static final int MAX              = 3;
    }
}
