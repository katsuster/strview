package net.katsuster.strview.gui;

import java.awt.FlowLayout;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.filechooser.*;

/**
 * <p>
 * ファイルを受け付けるウインドウ。
 * </p>
 *
 * @author katsuhiro
 */
public class FileDropWindow extends JFrame {
    private static final long serialVersionUID = 1L;

    private JMenuBar topMenuBar;
    private JMenu menuFile;
    private Action actionOpen, actionExit;
    private Action actionGC;
    private JButton btnGC;
    private JLabel lblHeapWatcher;
    private Action actHeapWatcher;
    private javax.swing.Timer timHeapWatcher;

    public FileDropWindow() {
        super();

        setResizable(true);
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTransferHandler(new FileTransferHandler());

        //メニューを作成する
        topMenuBar = new JMenuBar();
        menuFile = new JMenu("ファイル");

        actionOpen = new MenuActionFileOpen(this, "開く(O)");
        actionOpen.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
        menuFile.add(actionOpen);
        actionExit = new MenuActionExit("終了(X)");
        actionExit.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
        menuFile.addSeparator();
        menuFile.add(actionExit);

        topMenuBar.add(menuFile);
        setJMenuBar(topMenuBar);


        //ラベルを追加する
        lblHeapWatcher = new JLabel();
        actHeapWatcher = new HeapWatcher(lblHeapWatcher);
        timHeapWatcher = new javax.swing.Timer(1000, actHeapWatcher);
        timHeapWatcher.start();
        getContentPane().add(lblHeapWatcher);

        //GC ボタンを追加する
        actionGC = new ButtonActionGC("GC");
        btnGC = new JButton(actionGC);
        getContentPane().add(btnGC);
    }

    public class MenuActionFileOpen extends AbstractAction {
        private static final long serialVersionUID = 1L;
        private JFrame parent;

        public MenuActionFileOpen(JFrame f, String name) {
            super(name);
            parent = f;
        }

        public MenuActionFileOpen(JFrame f, String name, Icon icon) {
            super(name, icon);
            parent = f;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "Matroska", "mkv");
            int res;

            //chooser.setFileFilter(filter);
            res = chooser.showOpenDialog(parent);
            if (res == JFileChooser.APPROVE_OPTION) {
                FileTransferHandler t = new FileTransferHandler();
                t.openFile(chooser.getSelectedFile());
            }
        }
    }

    public class MenuActionExit extends AbstractAction {
        private static final long serialVersionUID = 1L;

        public MenuActionExit(String name) {
            super(name);
        }

        public MenuActionExit(String name, Icon icon) {
            super(name, icon);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    public class ButtonActionGC extends AbstractAction {
        private static final long serialVersionUID = 1L;

        public ButtonActionGC(String name) {
            super(name);
        }

        public ButtonActionGC(String name, Icon icon) {
            super(name, icon);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.gc();
        }
    }

    public class HeapWatcher extends AbstractAction {
        private static final long serialVersionUID = 1L;
        private JLabel lbl;

        public HeapWatcher(JLabel l) {
            lbl = l;
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            long free = Runtime.getRuntime().freeMemory();
            long total = Runtime.getRuntime().totalMemory();

            lbl.setText("used/total: "
                    + ((total - free) / 1048576) + "MB"
                    + "/" + (total / 1048576) + "MB");
        }
    }
}
