package net.katsuster.strview.gui;

import java.awt.*;

import javax.swing.*;
import javax.swing.text.*;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * パケットリストを表示するクラスです。
 * </p>
 */
public class PacketListViewer extends JPanel {
    private static final long serialVersionUID = 1L;

    private JList<String> viewer;
    private PacketListModel model;

    //表示させるパケットリスト
    private LargeList<? extends Packet> list;

    //今まで取得成功したパケット番号の最大値
    private long n_max = 0;

    public PacketListViewer(LargeList<? extends Packet> l) {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        //リストボックスを追加する
        model = new PacketListModel();
        viewer = new JList<String>() {
            @Override
            public int getNextMatch(String prefix, int startIndex, Position.Bias bias) {
                return -1;
            }
        };
        viewer.setFixedCellWidth(200);
        viewer.setFixedCellHeight(16);
        viewer.setModel(model);

        add(viewer, BorderLayout.CENTER);

        //表示するリストを設定する
        setPacketList(l);
    }

    protected class PacketListModel extends AbstractListModel<String> {
        @Override
        public int getSize() {
            return (int)getMax();
        }

        @Override
        public String getElementAt(int index) {
            return index + ": " + list.get(index).getShortName();
        }

        public void update(long before, long after) {
            fireContentsChanged(this, (int)before, (int)after);
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

        //とりあえず 100 要素取ってみる
        try {
            list.get(100);
            setMax(100);
        } catch (IndexOutOfBoundsException ex) {
            //短そうなので全カウント
            list.count();
            setMax(1);
        }
    }

    @Override
    public void setFont(Font f) {
        super.setFont(f);

        if (viewer != null) {
            viewer.setFont(f);
        }
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
        long before = n_max;

        n_max = n;

        if (model != null) {
            model.update(before, n_max);
        }
    }

    public JList getViewer() {
        return viewer;
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
        return viewer.locationToIndex(new Point(x, y));
    }
}
