package net.katsuster.strview.gui;

import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;

import net.katsuster.strview.util.*;
import net.katsuster.strview.io.*;
import net.katsuster.strview.media.*;
import net.katsuster.strview.media.mkv.*;

/**
 * <p>
 * ストリームの構造を表示するクラス。
 * </p>
 *
 * <p>
 * 下記の情報を表示します。
 * </p>
 *
 * <ul>
 * <li>ストリームを構成するパケットの一覧</li>
 * <li>各パケットを構成するデータの一覧</li>
 * </ul>
 *
 * @author katsuhiro
 */
public class PacketTreeViewerWindow extends JFrame {
    private static final long serialVersionUID = 1L;

    private String filename;
    //private PacketNode pn_root;

    private BinaryViewer binaryViewer;
    private MemberTreeViewer memberTreeViewer;
    private MemberTreeSelListener memberTreeListener;
    private JTextArea memberTextViewer;

    public PacketTreeViewerWindow(String fn) {
        super();

        //表示するファイルを保持する
        filename = fn;
        //setRootPacketNode(pn);

        setTitle(fn);
        setResizable(true);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTransferHandler(new FileTransferHandler());

        //メニューを作成する
        JMenuBar topMenuBar = new JMenuBar();
        JMenu menuFile = new JMenu("ファイル");

        Action actionClose = new MenuActionClose(this, "閉じる(C)");
        actionClose.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
        menuFile.add(actionClose);

        topMenuBar.add(menuFile);
        setJMenuBar(topMenuBar);

        //ビューアペインを作成し追加する
        JSplitPane strViewer = createStreamViewer();
        getContentPane().add(strViewer);









        //FIXME: test code
        PacketWriter<MemberTreeNode> c = new ToMemberTreeNodeConverter();
        LargeBitList blist = new ByteToBitList(new FileByteList(fn));
        MKVTagList mkvlist = new MKVTagList(blist);
        Packet p = mkvlist.get(10);

        //ノードのツリー表現を表示する
        p.write(c);
        memberTreeViewer.setRootTreeNode(c.getResult());
        for (int row = 0; row < memberTreeViewer.getTreePane().getRowCount(); row++) {
            memberTreeViewer.getTreePane().expandRow(row);
        }
        memberTreeViewer.repaint();

        //ノードの文字列表現を表示する
        memberTextViewer.setText(p.toString());
        memberTextViewer.repaint();
    }

    protected JSplitPane createStreamViewer() {
        JSplitPane splitStreamViewer;
        JSplitPane splitTreeViewer;

        //ビューアを左右に分割表示する
        splitStreamViewer = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitStreamViewer.setDividerSize(3);
        splitStreamViewer.setDividerLocation(500);

        //左側、ツリービューア
        splitTreeViewer = createTreeViewer();

        splitStreamViewer.setLeftComponent(splitTreeViewer);

        //右側、バイナリビューア
        binaryViewer = new BinaryViewer(getFilename());
        binaryViewer.setFont(new Font(Font.MONOSPACED, 0, 12));

        splitStreamViewer.setRightComponent(binaryViewer);

        return splitStreamViewer;
    }

    protected JSplitPane createTreeViewer() {
        JSplitPane splitTreeViewer;
        JSplitPane splitNodeViewer;
        JScrollPane scrTreeViewer;
        JScrollPane scrNodeViewer;
        JScrollPane scrTreeNodeViewer;

        //ビューアを上下に 3分割表示する
        //真ん中、下
        splitNodeViewer = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitNodeViewer.setDividerSize(3);
        splitNodeViewer.setDividerLocation(400);

        //上、(真ん中、下）
        splitTreeViewer = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitTreeViewer.setDividerSize(3);
        splitTreeViewer.setDividerLocation(200);
        splitTreeViewer.setRightComponent(splitNodeViewer);

        //1番目（上側）、ストリームビューア
        //TODO: not implemented
        /*packetTreeViewer = new PacketTreeViewer();
        packetTreeViewer.setRootDataNode(getRootPacketNode());
        packetTreeListener = new PacketTreeSelListener();
        JTree m_pt = memberTreeViewer.getTreePane();
        m_pt.addTreeSelectionListener(packetTreeListener);
        m_pt.addMouseListener(packetTreeListener);

        scrTreeViewer = new JScrollPane(packetTreeViewer);*/
        scrTreeViewer = new JScrollPane(new JPanel());
        scrTreeViewer.getVerticalScrollBar().setUnitIncrement(10);
        splitTreeViewer.setLeftComponent(scrTreeViewer);

        //2番目（下側の上側）、パケットビューア
        memberTreeViewer = new MemberTreeViewer();
        memberTreeListener = new MemberTreeSelListener();
        JTree m_jt = memberTreeViewer.getTreePane();
        m_jt.addTreeSelectionListener(memberTreeListener);
        m_jt.addMouseListener(memberTreeListener);
        m_jt.setFont(new Font(Font.MONOSPACED, 0, 12));

        scrTreeNodeViewer = new JScrollPane(memberTreeViewer);
        scrTreeNodeViewer.getVerticalScrollBar().setUnitIncrement(10);
        splitNodeViewer.setLeftComponent(scrTreeNodeViewer);

        //3番目（下側の下側）、パケットビューア（文字列表現）
        memberTextViewer = new JTextArea();
        memberTextViewer.setFont(new Font(Font.MONOSPACED, 0, 12));

        scrNodeViewer = new JScrollPane(memberTextViewer);
        splitNodeViewer.setRightComponent(scrNodeViewer);

        return splitTreeViewer;
    }

    public String getFilename() {
        return filename;
    }

    public class MemberTreeSelListener extends MouseAdapter
            implements TreeSelectionListener {
        @Override
        public void valueChanged(TreeSelectionEvent e) {
            onLeftSingleClick(0, e.getPath());
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            int selRow = memberTreeViewer.getTreePane().getRowForLocation(
                    e.getX(), e.getY());
            TreePath selPath = memberTreeViewer.getTreePane().getPathForLocation(
                    e.getX(), e.getY());

            if (selRow != -1) {
                if (e.getClickCount() == 1
                        && e.getButton() == MouseEvent.BUTTON1) {
                    //左シングルのとき
                    //onLeftSingleClick(selRow, selPath);
                } else if (e.getClickCount() == 1
                        && e.getButton() == MouseEvent.BUTTON3) {
                    //右シングルのとき
                    //onRightSingleClick(selRow, selPath);
                } else if (e.getClickCount() == 2
                        && e.getButton() == MouseEvent.BUTTON1) {
                    //左ダブルのとき
                    onLeftDoubleClick(selRow, selPath);
                }
            }
        }

        public void onLeftSingleClick(int selRow, TreePath selPath) {
            MemberTreeNode ptn = getPacketTreeNodeFromEvent(selPath);

            if (ptn == null) {
                return;
            }

            //メンバが指すデータ範囲をハイライトする
            if (ptn.hasNumData() || ptn.hasArrayData()) {
                long len = ptn.getDataLength();
                if ((len != 0) && (len % 8 != 0)) {
                    //bit | bit+7 | byte
                    //----+-------+-----
                    //0   | 除外  | 0
                    //1   | 8     | 1
                    //2   | 9     | 1
                    //7   | 14    | 1
                    //8   | 15    | 1
                    //9   | 16    | 2
                    len += 7;
                }

                binaryViewer.setHighlightMemberRange(
                        ptn.getDataPosition() >>> 3, len >>> 3);
            } else {
                binaryViewer.setHighlightMemberRange(0, 0);
            }
            binaryViewer.repaint();
        }

        public void onLeftDoubleClick(int selRow, TreePath selPath) {
            MemberTreeNode ptn = getPacketTreeNodeFromEvent(selPath);

            if (ptn == null) {
                return;
            }

            //ノードが指すデータの先頭にジャンプする
            if (ptn.hasNumData() || ptn.hasArrayData()) {
                binaryViewer.setRaw(
                        (ptn.getDataPosition() >>> 3) / binaryViewer.getLengthOfRaw());

                binaryViewer.repaint();
            }
        }

        protected MemberTreeNode getPacketTreeNodeFromEvent(TreePath selPath) {
            if (!(selPath.getLastPathComponent() instanceof MemberTreeNode)) {
                return null;
            }

            return (MemberTreeNode)selPath.getLastPathComponent();
        }
    }
}
