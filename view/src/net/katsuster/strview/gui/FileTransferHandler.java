package net.katsuster.strview.gui;

import java.io.*;
import java.util.*;

import java.awt.HeadlessException;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.awt.datatransfer.*;

import javax.swing.*;

import net.katsuster.strview.util.*;

/**
 * <p>
 * ファイルのドラッグ＆ドロップイベントを処理するクラスです。
 * </p>
 *
 * @author katsuhiro
 */
public class FileTransferHandler extends TransferHandler {
    private static final long serialVersionUID = 1L;

    @Override
    public boolean canImport(TransferSupport support) {
        return support.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean importData(TransferSupport support) {
        Transferable trans = support.getTransferable();
        Object tdata;
        List<Object> tlist;

        try {
            //ファイルリスト以外を渡された場合はドロップを拒否します
            if (!canImport(support)) {
                return false;
            }

            //ファイルリストの取得ができなければドロップを拒否します
            tdata = trans.getTransferData(DataFlavor.javaFileListFlavor);
            tlist = (List<Object>)tdata;
        } catch (Exception ex) {
            DebugInfo.printFunctionName(this);
            ex.printStackTrace();

            return false;
        }

        for (Object o: tlist) {
            //ファイル以外は無視します
            if (!(o instanceof File)) {
                continue;
            }
            File tfile = (File)o;
            if (!(tfile.isFile())) {
                continue;
            }

            //ファイルを解析します
            boolean result = openFile(tfile);
            if (!result) {
                System.err.println("open '" + tfile + "' is failed.");
                continue;
            }
        }

        return true;
    }

    public boolean openFile(File tfile) {
        System.out.println(tfile);

        try {
            BinaryViewerWindow bw = new BinaryViewerWindow(tfile.getAbsolutePath());
            bw.setSize(1024, 720);
            bw.setVisible(true);

            PacketTreeViewerWindow pw = new PacketTreeViewerWindow(tfile.getAbsolutePath());
            pw.setSize(1024, 720);
            pw.setVisible(true);
        } catch (HeadlessException ex) {

        }

        return true;
    }
}
