package net.katsuster.strview.gui;

import java.awt.event.*;

import javax.swing.*;

/**
 * <p>
 * 閉じるメニューの動作を表すクラスです。
 * </p>
 *
 * @author katsuhiro
 */
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
