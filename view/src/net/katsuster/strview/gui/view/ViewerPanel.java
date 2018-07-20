package net.katsuster.strview.gui.view;

import java.util.*;
import javax.swing.*;

import net.katsuster.strview.gui.*;
import net.katsuster.strview.util.*;

/**
 * <p>
 * ストリームの構造を表示するパネルです。
 * </p>
 */
public class ViewerPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private List<ViewerPanel> helpers;
    private LargeList<?> list;

    public ViewerPanel() {
        helpers = new ArrayList<>();
    }

    protected void processLinkEvent(LinkEvent e) {
        //do nothing
    }

    public void addHelperViewer(ViewerPanel p) {
        helpers.add(p);
    }

    public void removeHelperViewer(ViewerPanel p) {
        helpers.remove(p);
    }

    protected void fireLinkChange(LinkEvent e) {
        for (ViewerPanel elem : helpers) {
            elem.processLinkEvent(e);
        }
    }

    public LargeList<?> getList() {
        return list;
    }

    public void setList(LargeList<?> l) {
        list = l;
    }
}
