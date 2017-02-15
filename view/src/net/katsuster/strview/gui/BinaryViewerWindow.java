package net.katsuster.strview.gui;

import java.awt.Font;
import java.awt.event.*;

import javax.swing.*;

/**
 * <p>
 * バイナリデータを表示するウインドウです。
 * </p>
 *
 * @author katsuhiro
 */
public class BinaryViewerWindow extends JFrame {
    private static final long serialVersionUID = 1L;

    private String filename;

    private BinaryViewer binaryViewer;

    public BinaryViewerWindow(String fn) {
        super();

        //表示するファイルを保持する
        filename = fn;

        setTitle(fn);
        setResizable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTransferHandler(new FileTransferHandler());

        //メニューを作成する
        JMenuBar topMenuBar = new JMenuBar();
        JMenu menuFile = new JMenu("ファイル(F)");
        menuFile.setMnemonic('f');

        Action actionClose = new MenuActionClose(this, "閉じる(C)");
        actionClose.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
        menuFile.add(actionClose);

        topMenuBar.add(menuFile);
        setJMenuBar(topMenuBar);

        //バイナリビューア
        binaryViewer = new BinaryViewer(fn);
        binaryViewer.setFont(new Font(Font.MONOSPACED, 0, 12));
        getContentPane().add(binaryViewer);
    }

    public String getFilename() {
        return filename;
    }
}
