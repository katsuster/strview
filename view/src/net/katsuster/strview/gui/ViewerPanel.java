package net.katsuster.strview.gui;

import java.util.*;
import javax.swing.*;

/**
 * <p>
 * ストリームの構造を表示するパネルです。
 * </p>
 */
public class ViewerPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private List<ViewerPanel> helpers;

    public ViewerPanel() {
        helpers = new ArrayList<>();
    }

    public String getShortName() {
        return "";
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
}
