package net.katsuster.strview.gui;

import java.io.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import net.katsuster.strview.util.*;

/**
 * <p>
 * バイナリデータを表示するパネルです。
 * </p>
 *
 * @author katsuhiro
 */
public class BinaryViewerWindow extends ViewerPanel {
    private static final long serialVersionUID = 1L;

    private File file;

    private FlatTextField binaryToolText;
    private BinaryViewer binaryViewer;

    public BinaryViewerWindow(File f) {
        //表示するファイルを保持する
        file = f;

        setLayout(new BorderLayout());
        setTransferHandler(new FileTransferHandler());

        //バイナリビューア
        JPanel binaryTool = new JPanel();
        binaryTool.setLayout(new FlowLayout(FlowLayout.LEFT));
        //binaryTool.add(new JLabel("Find: "));

        binaryToolText = new FlatTextField();
        binaryToolText.setPreferredSize(new Dimension(150, 22));
        binaryToolText.getTextField().addKeyListener(new ActionGoto(""));
        binaryTool.add(binaryToolText);

        JButton btnGo = new JButton(new ActionGoto("Go"));
        binaryTool.add(btnGo);

        binaryViewer = new BinaryViewer(f);
        binaryViewer.setFont(new Font(Font.MONOSPACED, 0, 12));

        JPanel binaryPanel = new JPanel();
        binaryPanel.setLayout(new BorderLayout());
        binaryPanel.add(binaryTool, BorderLayout.NORTH);
        binaryPanel.add(binaryViewer, BorderLayout.CENTER);

        add(binaryPanel, BorderLayout.CENTER);
    }

    @Override
    public void setFont(Font f) {
        super.setFont(f);

        if (binaryViewer != null) {
            binaryViewer.setFont(f);
        }
    }

    @Override
    public String getShortName() {
        return file.getAbsolutePath();
    }

    @Override
    protected void processLinkEvent(LinkEvent e) {
        Range r = e.getRange();

        if (e.getEventType() == LinkEvent.Type.NODE) {
            binaryViewer.setHighlightMemberRange(0, 0);
            binaryViewer.setHighlightRange(r.getStart() >>> 3, r.getLength() >>> 3);
        } else if (e.getEventType() == LinkEvent.Type.MEMBER) {
            binaryViewer.setHighlightMemberRange(
                    r.getStart() >>> 3, r.getLength() >>> 3);
        } else if (e.getEventType() == LinkEvent.Type.JUMP) {
            binaryViewer.setAddress(r.getStart() >>> 3);
        }

        binaryViewer.repaint();
    }

    public File getFile() {
        return file;
    }

    public class ActionGoto extends AbstractAction
            implements KeyListener {
        private static final long serialVersionUID = 1L;

        public ActionGoto(String name) {
            super(name);
        }

        public ActionGoto(String name, Icon icon) {
            super(name, icon);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            go();
        }

        @Override
        public void keyTyped(KeyEvent e) {
            //Do nothing
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                go();
                e.consume();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            //Do nothing
        }

        public void go() {
            binaryToolText.setForeground(Color.BLACK);

            try {
                long addr = Long.parseLong(
                        binaryToolText.getTextField().getText(), 16);

                binaryViewer.setAddress(addr);
                binaryViewer.repaint();
            } catch (NumberFormatException ex) {
                binaryToolText.setForeground(Color.RED);
            }
        }
    }
}
