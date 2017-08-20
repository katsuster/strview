package net.katsuster.strview.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * <p>
 * ファイルを開くためのウインドウです。ドラッグ＆ドロップを受け付けます。
 * </p>
 *
 * @author katsuhiro
 */
public class FileDropWindow extends JFrame {
    private static final long serialVersionUID = 1L;

    private FileTransferHandler trHandler;
    private JComboBox<String> cmbType;
    private JLabel lblHeapWatcher;
    private JButton btnGC;
    private javax.swing.Timer timHeapWatcher;

    public FileDropWindow() {
        setResizable(true);
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        trHandler = new FileTransferHandler();
        setTransferHandler(trHandler);

        //メニューを作成する
        JMenuBar topMenuBar = new JMenuBar();
        setJMenuBar(topMenuBar);

        JMenu menuFile = new JMenu("ファイル(F)");
        menuFile.setMnemonic('f');
        topMenuBar.add(menuFile);

        Action actionOpen = new ActionFileOpen(this, "開く(O)...");
        actionOpen.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
        menuFile.add(actionOpen);
        menuFile.addSeparator();
        Action actionExit = new ActionExit("終了(X)");
        actionExit.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
        menuFile.add(actionExit);

        //ファイル種別ボックス
        cmbType = new JComboBox<>();
        cmbType.setPreferredSize(new Dimension(100, 20));
        cmbType.addItem("Auto");
        cmbType.addItem("FLV");
        cmbType.addItem("Matroska");
        cmbType.addItem("MPEG2 TS");
        cmbType.addItem("MPEG2 PS");
        cmbType.addItem("MPEG4");
        cmbType.addItem("RIFF");
        cmbType.addItem("RealMedia");
        cmbType.addItem("MPEG2 Visual");
        cmbType.addItem("MPEG4 Part2 Visual");
        cmbType.addItem("TEST: Fixed Size Packet");
        cmbType.addItem("TEST: Marked Packet");
        cmbType.addItemListener(new FileTypeChanged());
        getContentPane().add(cmbType);

        //ヒープ使用量と GC ボタンを追加する
        lblHeapWatcher = new JLabel();
        getContentPane().add(lblHeapWatcher);
        Action actHeapWatcher = new ActionWatchHeap(lblHeapWatcher);
        timHeapWatcher = new javax.swing.Timer(1000, actHeapWatcher);
        timHeapWatcher.start();

        Action actionGC = new ActionGC("GC");
        btnGC = new JButton(actionGC);
        getContentPane().add(btnGC);
    }

    public class ActionExit extends AbstractAction {
        private static final long serialVersionUID = 1L;

        public ActionExit(String name) {
            super(name);
        }

        public ActionExit(String name, Icon icon) {
            super(name, icon);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    public class FileTypeChanged implements ItemListener {
        private static final long serialVersionUID = 1L;

        public void itemStateChanged(ItemEvent e) {
            String s = (String)e.getItem();

            trHandler.setFileType(s);
        }
    }

    public class ActionGC extends AbstractAction {
        private static final long serialVersionUID = 1L;

        public ActionGC(String name) {
            super(name);
        }

        public ActionGC(String name, Icon icon) {
            super(name, icon);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.gc();
        }
    }

    public class ActionWatchHeap extends AbstractAction {
        private static final long serialVersionUID = 1L;
        private JLabel lbl;

        public ActionWatchHeap(JLabel l) {
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
