package net.katsuster.strview.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import net.katsuster.strview.util.*;
import net.katsuster.strview.util.gui.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * パケットリストをツリー状に表示するクラスです。
 * </p>
 *
 * @author katsuhiro
 */
public class PacketTreeViewer extends JPanel {
    private static final long serialVersionUID = 1L;

    //バイナリデータ表示パネル
    private PacketTreeViewerInner viewer;
    //右端のスクロールバー
    private JScrollBar scr;

    //表示させるパケットリスト
    private LargeList<? extends Packet> list;

    //表示を開始するパケット番号
    private long n_start;
    //今まで取得成功したパケット番号の最大値
    private long n_max;
    //1列の高さ（ピクセル）
    private int height_raw;

    //アドレスの色
    private Color color_addr;

    //表示領域
    private ContentBox box_all;
    //アドレス表示領域
    private ContentBox box_number_area;
    //アドレス（1列）表示領域
    private ContentBox box_number;
    //データ表示領域
    private ContentBox box_name_area;
    //データ（1列）表示領域
    private ContentBox box_name_raw;

    public PacketTreeViewer(LargeList<? extends Packet> l) {
        //表示するパケット番号
        n_start = 0;
        n_max = 0;
        //列の表示領域の高さ
        height_raw = 20;

        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        //表示するリストを設定する
        setPacketList(l);

        //フォントの色を初期化する
        color_addr = Color.BLACK;

        //表示領域を初期化する
        box_all = new ContentBox();
        box_number_area = new ContentBox();
        box_number = new ContentBox();
        box_name_area = new ContentBox();
        box_name_raw = new ContentBox();

        box_all.setMargin(5, 0, 5, 0);
        box_number_area.setPadding(5, 0, 5, 0);
        box_name_area.setPadding(5, 0, 5, 0);

        //マウスホイール、リサイズイベント、リスナを追加する
        addMouseListener(new ViewerMouseListener());
        addMouseWheelListener(new ViewerWheelListener());
        addComponentListener(new ViewerComponentListener());

        //中央にバイナリデータ表示パネルを配置する
        viewer = new PacketTreeViewerInner();
        add(viewer, BorderLayout.CENTER);

        //右端にスクロールバーを配置する
        scr = new JScrollBar();
        scr.getModel().addChangeListener(viewer);
        add(scr, BorderLayout.EAST);

        //とりあえず 100 要素取ってみる
        try {
            l.get(100);
            setMax(100);
        } catch (IndexOutOfBoundsException ex) {
            //短そうなので全カウント
            l.count();
        }

        //スクロールバーの最大値を設定する、GUI 作成の後でないとだめ
        updateScrollbar();
    }

    protected void updateScrollbar() {
        //1行（getLengthOfRaw() バイト）ずつスクロールできるように、
        //スクロールバー最大値はファイルの行数とする
        long lines = getMax();
        if (Integer.MAX_VALUE < lines) {
            scr.setMaximum(Integer.MAX_VALUE);
        } else {
            scr.setMaximum((int)lines);
        }
    }

    /**
     * <p>
     * 表示するパケットリストを取得します。
     * </p>
     *
     * @return 表示するパケットリスト
     */
    public LargeList<? extends Packet> getPacketList() {
        return list;
    }

    /**
     * <p>
     * 表示するパケットリストを設定します。
     * </p>
     *
     * @param l 表示するパケットリスト
     */
    public void setPacketList(LargeList<? extends Packet> l) {
        list = l;
    }

    /**
     * <p>
     * 表示開始するパケット番号を取得します。
     * </p>
     *
     * @return 表示開始するパケット番号
     */
    public long getStart() {
        return n_start;
    }

    /**
     * <p>
     * 表示開始するパケット番号を設定します。
     * </p>
     *
     * @param n 表示開始するパケット番号
     */
    public void setStart(long n) {
        LargeList<?> l = getPacketList();
        long n_max = getPacketList().length();

        if (n_max == LargeList.LENGTH_UNKNOWN) {
            n_max = getMax();
        }
        if (n_max - 1 < n) {
            n = n_max - 1;
        }

        n_start = n;
    }

    /**
     * <p>
     * パケット番号の最大値を取得します。
     * </p>
     *
     * <p>
     * パケットリストの大きさが既知の場合は、
     * パケットリストの大きさを返します。
     * 大きさが未知の場合は、
     * 今までに取得成功したパケット番号の最大値を返します。
     * </p>
     *
     * @return パケット番号の最大値
     */
    public long getMax() {
        long l = getPacketList().length();

        if (l == LargeList.LENGTH_UNKNOWN) {
            return n_max;
        }

        return l;
    }

    /**
     * <p>
     * パケット番号の最大値を設定します。
     * </p>
     *
     * <p>
     * パケットリストの大きさが既知の場合は、
     * パケットリストの大きさを返します。
     * </p>
     *
     * @param n パケット番号の最大値
     */
    protected void setMax(long n) {
        n_max = n;

        updateScrollbar();
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
     * 指定された位置にある行を取得します。
     * </p>
     *
     * @param x 表示領域の左端からの距離
     * @param y 表示領域の上端からの距離
     * @return 指定された位置にある行、領域外の時は -1
     */
    public long getRowForLocation(int x, int y) {
        int offset = y / getHeightOfRaw();
        long row = getStart() + offset;

        if (row < 0 || getMax() <= row) {
            return -1;
        } else {
            return row;
        }
    }

    protected class ViewerMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println(e.getPoint());
            System.out.println("row:" + getRowForLocation(e.getX(), e.getY()));
        }
    }

    protected class ViewerWheelListener
            implements MouseWheelListener {
        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            scr.setValue(scr.getValue() + e.getWheelRotation() * 3);
        }
    }

    protected class ViewerComponentListener extends ComponentAdapter {
        @Override
        public void componentResized(ComponentEvent e) {
            Rectangle all_contents;

            //表示領域を更新する
            box_all.setWidth(viewer.getWidth());
            box_all.setHeight(viewer.getHeight());

            all_contents = box_all.getContents();
            box_number_area.setHeight(all_contents.height);
            box_name_area.setHeight(all_contents.height);
            //box_ascii_area.setHeight(all_contents.height);

            //スクロールバーの extent = 一画面に入る行数とする
            scr.getModel().setExtent(
                    all_contents.height / box_number_area.getHeight());

            viewer.repaint();
        }
    }

    /**
     * <p>
     * パケットリストをツリー状に描画するパネルです。
     * </p>
     *
     * @author katsuhiro
     */
    protected class PacketTreeViewerInner extends JComponent
            implements ChangeListener {
        private static final long serialVersionUID = 1L;

        public PacketTreeViewerInner() {

        }

        protected void drawAll(Graphics g, long number) {
            //フォントメトリックから表示領域の大きさを計算する
            FontMetrics met = g.getFontMetrics();

            //表示領域
            int width_f = met.stringWidth("F");
            int height_f = Math.max(met.getHeight(), getHeightOfRaw());
            box_number_area.setWidth(width_f * 10);
            box_number.setWidth(width_f * 10);
            box_number.setHeight(height_f);

            box_name_area.setWidth(width_f * 3 * (16 /*getLengthOfRaw()*/ + 1));
            box_name_raw.setWidth(width_f * 3 * (16 /*getLengthOfRaw()*/ + 1));
            box_name_raw.setHeight(height_f);

            //マージンを除いた領域を描画に使う
            Rectangle all_contents = box_all.getContents();
            int dx = all_contents.x;
            int dy = all_contents.y;

            //アドレス、データ、文字列表現を描画する
            box_number_area.setX(dx);
            box_number_area.setY(dy);
            drawNumberAll(g, number);
            dx += box_number_area.getWidth();

            box_name_area.setX(dx);
            box_name_area.setY(dy);
            drawDataAll(g, number);
            dx += box_name_area.getWidth();
        }

        protected void drawNumberAll(Graphics g, long number) {
            //マージンを除いた領域を描画に使う
            Rectangle all_number = box_number_area.getContents();
            int dx = all_number.x;
            int dy = all_number.y;

            while (dy < all_number.height && number < getMax()) {
                box_number.setX(dx);
                box_number.setY(dy);
                drawNumber(g, number);

                number += 1;
                dy += box_number.getHeight();
            }

            //枠を描画
            //Rectangle borders_number = box_number_area.getBorder();
            //g.drawRoundRect(borders_number.x, borders_number.y,
            //		borders_number.width, borders_number.height,
            //		14, 14);
        }

        /**
         * <p>
         * 指定されたパケット番号を描画します。
         * </p>
         *
         * @param g      描画を行うグラフィクス
         * @param number パケット番号
         */
        protected void drawNumber(Graphics g, long number) {
            Packet p = list.get(number);

            //マージンを除いた領域を描画に使う
            Rectangle raw_number = box_number.getContents();

            //文字列の色に設定する
            Color c = g.getColor();
            g.setColor(getAddressColor(number));

            //描画する
            //drawString の Y 座標は文字のベースラインの位置のため、
            //本来ベースラインから文字の頭までの高さを足すべきだが、
            //簡易処理として描画領域の高さを足している
            String str = String.format("%8d", number);
            g.drawString(str, p.getRange().getLevel() * 16 + raw_number.x, raw_number.y + raw_number.height);

            //元の色に戻す
            g.setColor(c);
        }

        protected void drawDataAll(Graphics g, long number) {
            //マージンを除いた領域を描画に使う
            Rectangle all_name = box_name_area.getContents();
            int dx = all_name.x;
            int dy = all_name.y;

            while (dy < all_name.height && number < getMax()) {
                box_name_raw.setX(dx);
                box_name_raw.setY(dy);
                drawShortName(g, number);

                number += 1;
                dy += box_name_raw.getHeight();
            }

            //枠を描画
            //Rectangle borders_data = box_name_area.getBorder();
            //g.drawRoundRect(borders_data.x, borders_data.y,
            //		borders_data.width, borders_data.height,
            //		14, 14);
        }

        /**
         * <p>
         * 指定されたパケット番号の短縮名を描画します。
         * </p>
         *
         * @param g      描画を行うグラフィクス
         * @param number パケット番号
         */
        protected void drawShortName(Graphics g, long number) {
            Packet p = list.get(number);

            //マージンを除いた領域を描画に使う
            Rectangle raw_name = box_name_raw.getContents();

            //文字列の色に設定する
            Color c = g.getColor();
            g.setColor(getAddressColor(number));

            //描画する
            //drawString の Y 座標は文字のベースラインの位置のため、
            //本来ベースラインから文字の頭までの高さを足すべきだが、
            //簡易処理として描画領域の高さを足している
            String str = String.format("%s", p.getShortName());
            g.drawString(str, p.getRange().getLevel() * 16 + raw_name.x, raw_name.y + raw_name.height);

            //Rectangle borders_data = box_name_raw.getBorder();
            //g.drawRoundRect(borders_data.x, borders_data.y,
            //        borders_data.width, borders_data.height,
            //        14, 14);

            //元の色に戻す
            g.setColor(c);
        }

        @Override
        public void stateChanged(ChangeEvent e) {
            setStart(scr.getValue());

            repaint();
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);

            if (getPacketList() == null) {
                //do nothing
                return;
            }

            drawAll(g, getStart());
        }
    }
}
