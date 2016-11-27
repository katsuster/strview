package net.katsuster.strview.gui;

import java.io.*;
import java.util.*;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.datatransfer.*;
import java.awt.event.*;

import javax.swing.*;

import net.katsuster.strview.util.*;
import net.katsuster.strview.io.*;
import net.katsuster.strview.media.*;

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
    private Action actionExit;
    private JMenu menuFile;
    private Action actionGC;
    private JButton btnGC;
    private JLabel lblHeapWatcher;
    private Action actHeapWatcher;
    private javax.swing.Timer timHeapWatcher;

    public FileDropWindow() {
        super();

        //レイアウトを決める
        setLayout(new FlowLayout(FlowLayout.LEFT));

        //ウインドウを閉じられたらアプリケーションを終了する
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //メニューを作成する
        topMenuBar = new JMenuBar();
        menuFile = new JMenu("ファイル");

        actionExit = new MenuActionExit("終了(X)");
        actionExit.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
        menuFile.add(actionExit);

        //メニュー、リスナを追加する
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


        //ドラッグ＆ドロップを可能にする
        setTransferHandler(new FileDropWindow.FileTransferHandler());
    }

    public class MenuActionExit extends AbstractAction {
        private static final long serialVersionUID = 1L;

        public MenuActionExit() {
            this("");
        }

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

        public ButtonActionGC() {
            this("");
        }

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
            long free, total;

            free = Runtime.getRuntime().freeMemory();
            total = Runtime.getRuntime().totalMemory();

            lbl.setText("used/total: "
                    + ((total - free) / 1048576) + "MB"
                    + "/" + (total / 1048576) + "MB");
        }
    }

    public static class FileTransferHandler extends TransferHandler {
        private static final long serialVersionUID = 1L;

        @Override
        @SuppressWarnings("unchecked")
        public boolean importData(TransferSupport support) {
            Transferable trans = support.getTransferable();
            Object tdata;
            List<Object> tlist;
            File tfile;
            boolean result;

            try {
                //ファイルリスト以外を渡された場合はドロップを拒否します
                if (!canImport(support)) {
                    return false;
                }

                //ファイルリストの取得ができなければドロップを拒否します
                tdata = trans.getTransferData(DataFlavor.javaFileListFlavor);
                tlist = (List<Object>)tdata;
            } catch (Exception ex) {
                DebugInfo.printFunctionName(this);
                ex.printStackTrace();

                return false;
            }

            for (Object o: tlist) {
                //ファイル以外は無視します
                if (!(o instanceof File)) {
                    continue;
                }
                tfile = (File)o;
                if (!(tfile.isFile())) {
                    continue;
                }

                //ファイルを解析します
                result = openFile(tfile);
                if (!result) {
                    System.err.println("open '" + tfile + "' is failed.");
                    continue;
                }
            }

            return true;
        }

        private boolean openFile(File tfile) {
            BinaryViewerWindow bw;

            System.out.println(tfile);

            try {
                bw = new BinaryViewerWindow(tfile.getAbsolutePath());

                bw.setSize(1024, 720);
                bw.setVisible(true);
                bw.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            } catch (HeadlessException ex) {

            }

            return true;
        }

        @Override
        public boolean canImport(TransferSupport support) {
            return support.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
        }
    }
}
