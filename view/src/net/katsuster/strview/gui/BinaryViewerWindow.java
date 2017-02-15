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
        JMenu menuSetting = new JMenu("設定(S)");
        menuSetting.setMnemonic('s');

        Action actionClose = new MenuActionClose(this, "閉じる(C)");
        actionClose.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
        menuFile.add(actionClose);

        Action actionFont = new MenuActionFont(this, "フォント(F)...");
        actionFont.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_F);
        menuSetting.add(actionFont);

        topMenuBar.add(menuFile);
        topMenuBar.add(menuSetting);
        setJMenuBar(topMenuBar);

        //バイナリビューア
        binaryViewer = new BinaryViewer(fn);
        binaryViewer.setFont(new Font(Font.MONOSPACED, 0, 12));
        getContentPane().add(binaryViewer);
    }

    public String getFilename() {
        return filename;
    }

    public class MenuActionFont extends AbstractAction {
        private static final long serialVersionUID = 1L;
        private JFrame parent;

        public MenuActionFont(JFrame f, String name) {
            super(name);
            parent = f;
        }

        public MenuActionFont(JFrame f, String name, Icon icon) {
            super(name, icon);
            parent = f;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JFontChooser chooser = new JFontChooser();

            chooser.setSelectedFont(binaryViewer.getFont());
            int res = chooser.showDialog(parent);
            if (res == JFontChooser.OK_OPTION) {
                binaryViewer.setFont(chooser.getSelectedFont());
            }
        }
    }
}
