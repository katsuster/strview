package net.katsuster.strview.gui.opener;

import net.katsuster.strview.gui.opener.Opener.*;
import net.katsuster.strview.gui.view.*;
import net.katsuster.strview.media.*;
import net.katsuster.strview.media.ts.*;
import net.katsuster.strview.util.*;
import net.katsuster.strview.util.bit.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * 特定の PID のみを選んだ TS パケットとして開きます。
 */
public class ActionOpenAsTS extends ActionOpenAs {
    private static final long serialVersionUID = 1L;

    public ActionOpenAsTS(ViewerWindow w) {
        super(w);
    }

    public ActionOpenAsTS(String name, ViewerWindow w) {
        super(name, w);
    }

    public ActionOpenAsTS(String name, Icon icon, ViewerWindow w) {
        super(name, icon, w);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        LargeList<?> l = getParent().getViewer().getList();

        if (l instanceof LargePacketList && l.get(0) instanceof TSPacket) {
            LargePacketList<TSPacket> lp = (LargePacketList<TSPacket>)l;
            LargePacketList<TSPacket> ln = new FilteredTSPacketList(lp, 0);
            PacketTreeViewerPanel pp = new PacketTreeViewerPanel(ln);
            ViewerWindow vpw = new ViewerWindow(pp);
            List<Opener> openers = OpenerFactory.createOpener(ln, vpw);

            vpw.setSize(800, 720);
            vpw.addHelperViewer(getParent());
            vpw.setOpeners(openers);
            vpw.setVisible(true);
        }
    }
}