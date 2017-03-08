package net.katsuster.strview.gui;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.*;

public class ActionFileOpen extends AbstractAction {
    private static final long serialVersionUID = 1L;
    private JFrame parent;

    public ActionFileOpen(JFrame f, String name) {
        super(name);
        parent = f;
    }

    public ActionFileOpen(JFrame f, String name, Icon icon) {
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
