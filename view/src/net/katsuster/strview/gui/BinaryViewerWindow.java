package net.katsuster.strview.gui;

import java.awt.Font;
import java.awt.event.*;

import javax.swing.*;

/**
 * <p>
 * バイナリデータを表示するウインドウ。
 * </p>
 *
 * @author katsuhiro
 */
public class BinaryViewerWindow extends JFrame {
    private static final long serialVersionUID = 1L;

    private String filename;

    private JMenuBar topMenuBar;
    private JMenu menuFile;
    private Action actionClose;
    private BinaryViewer binaryViewer;

    public BinaryViewerWindow(String fn) {
        super();

        setTitle(fn);
        setResizable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTransferHandler(new FileTransferHandler());

        //メニューを作成する
        topMenuBar = new JMenuBar();
        menuFile = new JMenu("ファイル");

        actionClose = new MenuActionClose(this, "閉じる(C)");
        actionClose.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
        menuFile.add(actionClose);

        topMenuBar.add(menuFile);
        setJMenuBar(topMenuBar);

        //バイナリビューア
        binaryViewer = new BinaryViewer(fn);
        binaryViewer.setFont(new Font(Font.MONOSPACED, 0, 12));
        getContentPane().add(binaryViewer);
    }

    public class MenuActionClose extends AbstractAction {
        private static final long serialVersionUID = 1L;
        private JFrame parent;

        public MenuActionClose(JFrame f, String name) {
            super(name);
            parent = f;
        }

        public MenuActionClose(JFrame f, String name, Icon icon) {
            super(name, icon);
            parent = f;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            parent.dispose();
        }
    }
}
