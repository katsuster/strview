package net.katsuster.strview.gui.view;

import java.util.List;
import java.util.*;
import java.awt.*;

import javax.swing.*;
import javax.swing.tree.*;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * ストリームを構成するパケットをツリー状に表示するクラスです。
 * </p>
 *
 * <ul>
 * <li>DataNode: データ保持用の MemberTreeNode オブジェクトのこと</li>
 * <li>TreeNode: ツリー表示用の DefaultMutableTreeNode のこと</li>
 * </ul>
 */
public class PacketTreeViewer extends JPanel {
    private static final long serialVersionUID = 1L;

    private JTree viewer;
    private DefaultMutableTreeNode root;

    //表示させるパケットリスト
    private LargePacketList<?> list;

    //今まで取得成功したパケット番号の最大値
    private long n_max = 0;

    public PacketTreeViewer(LargePacketList<?> l) {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        //ルートのみを持つツリービューアを作成する
        root = new DefaultMutableTreeNode();
        root.setUserObject(l.getTypeName());
        viewer = new JTree(root);
        viewer.setRootVisible(true);

        add(viewer, BorderLayout.CENTER);

        //表示するリストを設定する
        setPacketList(l);
    }

    @Override
    public void setFont(Font f) {
        super.setFont(f);

        if (viewer != null) {
            viewer.setFont(f);
        }
    }

    public JTree getViewer() {
        return viewer;
    }

    /**
     * <p>
     * ルートノードを取得します。
     * </p>
     *
     * @return ルートノード
     */
    public DefaultMutableTreeNode getRootTreeNode() {
        return root;
    }

    /**
     * <p>
     * 表示するパケットリストを取得します。
     * </p>
     *
     * @return 表示するパケットリスト
     */
    public LargePacketList<?> getPacketList() {
        return list;
    }

    /**
     * <p>
     * 表示するパケットリストを設定します。
     * </p>
     *
     * @param l 表示するパケットリスト
     */
    public void setPacketList(LargePacketList<?> l) {
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

        if (viewer != null) {
            update();
        }
    }

    /**
     * <p>
     * 全ノードを更新します。
     * </p>
     */
    public void update() {
        //選択を解除する
        viewer.clearSelection();
        root.removeAllChildren();

        for (long i = 0; i < getMax(); ) {
            Packet p = list.get(i);
            long cnt = addAllChildren(root, p.getRange());
            i += cnt;
        }

        //リロード
        DefaultTreeModel model = (DefaultTreeModel)viewer.getModel();
        model.reload();
    }

    /**
     * <p>
     * あるノードに指定したノードと、その子ノードを再帰的に追加します。
     * </p>
     *
     * @param pt 追加先のノード
     * @param pr 追加したいノード
     * @return 追加したノード数
     */
    protected long addAllChildren(DefaultMutableTreeNode pt, PacketRange pr) {
        long cnt = 0;

        //自身を登録する
        DefaultMutableTreeNode t_p = new DefaultMutableTreeNode(
                new PacketTreeNode(getPacketList(), pr));
        pt.add(t_p);
        cnt++;

        //子を登録する
        List<PacketRange> l = pr.getChildNodes();
        Iterator<PacketRange> i = l.iterator();
        while (i.hasNext()) {
            PacketRange pr_i = i.next();

            //孫、ひ孫ノードがあれば再帰的に追加する
            cnt += addAllChildren(t_p, pr_i);
        }

        return cnt;
    }
}
