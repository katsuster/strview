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
    private BinaryViewer binaryViewer;

    public BinaryViewerWindow(String fn) {
        super();

        //タイトル設定、リサイズ可能にする
        setTitle(fn);
        setResizable(true);

        //ウインドウを閉じられたらウインドウを破棄する
        addWindowListener(new StreamViewerWindowListener());

        //ドラッグ＆ドロップを可能にする
        setTransferHandler(new FileDropWindow.FileTransferHandler());

        //表示するファイルを設定する
        setFilename(fn);

        //メニューを作成する
        topMenuBar = new JMenuBar();
        topMenuBar.add(new JMenu("ファイル"));

        setJMenuBar(topMenuBar);

        //バイナリビューア
        binaryViewer = new BinaryViewer(getFilename());
        binaryViewer.setFont(new Font(Font.MONOSPACED, 0, 12));
        getContentPane().add(binaryViewer);
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String fn) {
        filename = fn;
    }

    public static class StreamViewerWindowListener implements WindowListener {
        @Override
        public void windowActivated(WindowEvent ev) {
            //do nothing
        }

        @Override
        public void windowClosed(WindowEvent ev) {
            //do nothing
        }

        @Override
        public void windowClosing(WindowEvent ev) {
            ev.getWindow().dispose();
        }

        @Override
        public void windowDeactivated(WindowEvent ev) {
            //do nothing
        }

        @Override
        public void windowDeiconified(WindowEvent ev) {
            //do nothing
        }

        @Override
        public void windowIconified(WindowEvent ev) {
            //do nothing
        }

        @Override
        public void windowOpened(WindowEvent ev) {
            //do nothing
        }
    }
}
