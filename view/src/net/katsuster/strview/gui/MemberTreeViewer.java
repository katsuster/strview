package net.katsuster.strview.gui;

import java.awt.BorderLayout;
import javax.swing.*;
import javax.swing.tree.*;

/**
 * <p>
 * パケットを構成するデータをツリー状に表示するビューアです。
 * </p>
 *
 * @author katsuhiro
 */
public class MemberTreeViewer extends JComponent {
    private static final long serialVersionUID = 1L;

    private JTree w_tree;
    private DefaultMutableTreeNode hidden_root, root;

    public MemberTreeViewer() {
        setLayout(new BorderLayout());

        //ルートのみを持つツリービューアを作成する
        hidden_root = new DefaultMutableTreeNode();
        w_tree = new JTree(hidden_root);
        w_tree.setRootVisible(false);

        //ツリービューアを登録、表示する
        add(w_tree);
        setVisible(true);
    }

    public JTree getTreePane() {
        return w_tree;
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
        DefaultTreeModel model = (DefaultTreeModel)w_tree.getModel();
        model.reload();

        //選択を解除し、ルートノードを展開する
        w_tree.clearSelection();
        w_tree.expandRow(0);

        root = n;
    }
}
