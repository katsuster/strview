package net.katsuster.strview.gui;

import java.awt.*;

import javax.swing.*;
import javax.swing.tree.*;

/**
 * <p>
 * パケットを構成するデータをツリー状に表示するビューアです。
 * </p>
 *
 * @author katsuhiro
 */
public class MemberTreeViewer extends JPanel {
    private static final long serialVersionUID = 1L;

    private JTree viewer;
    private DefaultMutableTreeNode hidden_root, root;

    public MemberTreeViewer() {
        setLayout(new BorderLayout());

        //ルートのみを持つツリービューアを作成する
        hidden_root = new DefaultMutableTreeNode();
        viewer = new JTree(hidden_root);
        viewer.setRootVisible(false);

        //ツリービューアを登録、表示する
        add(viewer);
        setVisible(true);
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
     * ルートの子ノードとして表示するデータを設定します。
     * </p>
     *
     * @param n ルートの子ノードとして表示するデータ
     */
    public void setRootTreeNode(DefaultMutableTreeNode n) {
        //子ノードを再帰的に追加する
        hidden_root.removeAllChildren();
        hidden_root.add(n);

        //リロード
        DefaultTreeModel model = (DefaultTreeModel)viewer.getModel();
        model.reload();

        //選択を解除し、ルートノードを展開する
        viewer.clearSelection();
        viewer.expandRow(0);

        root = n;
    }
}
