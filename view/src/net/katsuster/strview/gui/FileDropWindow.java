package net.katsuster.strview.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import net.katsuster.strview.gui.FileTransferHandler.*;

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
    private JComboBox<FileTypeItem> cmbType;
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
        cmbType.addItem(new FileTypeItem("Auto", FILE_TYPE.FT_AUTO));
        cmbType.addItem(new FileTypeItem("FLV", FILE_TYPE.FT_FLV));
        cmbType.addItem(new FileTypeItem("Matroska", FILE_TYPE.FT_MATROSKA));
        cmbType.addItem(new FileTypeItem("MPEG2 TS", FILE_TYPE.FT_MPEG2TS));
        cmbType.addItem(new FileTypeItem("MPEG2 PS", FILE_TYPE.FT_MPEG2PS));
        cmbType.addItem(new FileTypeItem("MPEG4", FILE_TYPE.FT_MPEG4));
        cmbType.addItem(new FileTypeItem("RIFF", FILE_TYPE.FT_RIFF));
        cmbType.addItem(new FileTypeItem("RealMedia", FILE_TYPE.FT_RMFF));
        cmbType.addItem(new FileTypeItem("MPEG2 Visual", FILE_TYPE.FT_MPEG2VIDEO));
        cmbType.addItem(new FileTypeItem("MPEG4 Part2 Visual", FILE_TYPE.FT_MPEG4VISUAL));
        cmbType.addItem(new FileTypeItem("TEST: Source Packet", FILE_TYPE.FT_TEST_SRC));
        cmbType.addItem(new FileTypeItem("TEST: Fixed Size Packet", FILE_TYPE.FT_TEST_FIXED));
        cmbType.addItem(new FileTypeItem("TEST: Marked Packet", FILE_TYPE.FT_TEST_MARKED));
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

    public class FileTypeItem {
        private String name;
        private FileTransferHandler.FILE_TYPE type;

        public FileTypeItem(String n, FileTransferHandler.FILE_TYPE t) {
            name = n;
            type = t;
        }

        public String getName() {
            return name;
        }

        public FileTransferHandler.FILE_TYPE getType() {
            return type;
        }

        @Override
        public String toString() {
            return name;
        }
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
            FileTypeItem s = (FileTypeItem)e.getItem();

            trHandler.setFileType(s.getType());
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
