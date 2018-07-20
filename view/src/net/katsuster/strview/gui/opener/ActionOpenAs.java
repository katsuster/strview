package net.katsuster.strview.gui.opener;

import javax.swing.*;

import net.katsuster.strview.gui.view.*;

abstract public class ActionOpenAs extends AbstractAction {
    private static final long serialVersionUID = 1L;

    private ViewerWindow parent;

    public ActionOpenAs(ViewerWindow w) {
        super();
        parent = w;
    }

    public ActionOpenAs(String name, ViewerWindow w) {
        super(name);
        parent = w;
    }

    public ActionOpenAs(String name, Icon icon, ViewerWindow w) {
        super(name, icon);
        parent = w;
    }

    public ViewerWindow getParent() {
        return parent;
    }
}