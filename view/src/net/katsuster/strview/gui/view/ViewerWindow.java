package net.katsuster.strview.gui.view;

import java.util.List;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import net.katsuster.strview.gui.*;
import net.katsuster.strview.gui.opener.*;

/**
 * <p>
 * ストリームの構造を表示するウインドウです。
 * </p>
 *
 * <p>
 * ViewerPanel を 1つだけ表示できます。
 * </p>
 */
public class ViewerWindow extends JFrame {
    private static final long serialVersionUID = 1L;

    private ViewerPanel viewer;
    private JComboBox<Opener> cmbType;
    private JButton btnOpen;

    private Opener opener;

    public ViewerWindow(ViewerPanel p) {
        viewer = p;

        setTitle(p.getName());
        setResizable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //メニューを作成する
        JMenuBar topMenuBar = new JMenuBar();
        JMenu menuFile = new JMenu("ファイル(F)");
        menuFile.setMnemonic('f');

        Action actionOpen = new ActionFileOpen(this, "開く(O)...");
        actionOpen.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
        menuFile.add(actionOpen);
        menuFile.addSeparator();
        Action actionClose = new ActionClose(this, "閉じる(C)");
        actionClose.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
        menuFile.add(actionClose);

        JMenu menuSetting = new JMenu("設定(S)");
        menuSetting.setMnemonic('s');

        Action actionFont = new ActionFont(this, "フォント(F)...");
        actionFont.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_F);
        menuSetting.add(actionFont);

        topMenuBar.add(menuFile);
        topMenuBar.add(menuSetting);
        setJMenuBar(topMenuBar);

        //下側、ファイル種別選択ボックス
        JPanel typeTool = new JPanel();
        typeTool.setLayout(new FlowLayout(FlowLayout.RIGHT));

        typeTool.add(new JLabel("Open as:"));

        cmbType = new JComboBox<>();
        cmbType.addItemListener(new FileTypeChanged());
        typeTool.add(cmbType);

        btnOpen = new JButton("Open");
        btnOpen.setEnabled(false);
        typeTool.add(btnOpen);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(p, BorderLayout.CENTER);
        getContentPane().add(typeTool, BorderLayout.SOUTH);
    }

    public ViewerPanel getViewer() {
        return viewer;
    }

    public void addHelperViewer(ViewerWindow w) {
        viewer.addHelperViewer(w.getViewer());
    }

    public void removeHelperViewer(ViewerWindow w) {
        viewer.removeHelperViewer(w.getViewer());
    }

    public void setOpeners(List<Opener> ops) {
        cmbType.removeAllItems();
        for (Opener i : ops) {
            cmbType.addItem(i);
        }
    }

    public Opener getSelectedOpener() {
        return opener;
    }

    public class ActionFont extends AbstractAction {
        private static final long serialVersionUID = 1L;
        private JFrame parent;

        public ActionFont(JFrame f, String name) {
            super(name);
            parent = f;
        }

        public ActionFont(JFrame f, String name, Icon icon) {
            super(name, icon);
            parent = f;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JFontChooser chooser = new JFontChooser();

            chooser.setSelectedFont(viewer.getFont());
            int res = chooser.showDialog(parent);
            if (res == JFontChooser.OK_OPTION) {
                viewer.setFont(chooser.getSelectedFont());
            }
        }
    }

    public class FileTypeChanged implements ItemListener {
        public void itemStateChanged(ItemEvent e) {
            Opener o = (Opener)e.getItem();

            opener = o;

            if (o == null) {
                btnOpen.setAction(null);
                btnOpen.setEnabled(false);
            } else {
                btnOpen.setAction(o.getAction());
                btnOpen.setEnabled(true);
            }
        }
    }
}
