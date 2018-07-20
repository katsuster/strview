package net.katsuster.strview.gui;

import java.io.*;
import java.util.*;
import java.awt.datatransfer.*;
import javax.swing.*;

import net.katsuster.strview.gui.opener.*;
import net.katsuster.strview.gui.view.*;
import net.katsuster.strview.util.*;
import net.katsuster.strview.util.bit.*;
import net.katsuster.strview.io.*;

/**
 * <p>
 * ファイルのドラッグ＆ドロップイベントを処理するクラスです。
 * </p>
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
        List<?> tlist;

        try {
            //ファイルリスト以外を渡された場合はドロップを拒否します
            if (!canImport(support)) {
                return false;
            }

            //ファイルリストの取得ができなければドロップを拒否します
            tdata = trans.getTransferData(DataFlavor.javaFileListFlavor);
            tlist = (List<?>)tdata;
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

    /**
     * <p>
     * ファイルを開きます。
     * </p>
     *
     * <p>
     * バイナリデータを表示するウインドウが開きます。
     * </p>
     *
     * @param tfile ファイル
     * @return ファイルを開けた場合は true、ファイルを開けなかった場合は false
     */
    public boolean openFile(File tfile) {
        LargeBitList blist = new ByteToBitList(new FileByteList(tfile.getAbsolutePath()));
        blist.setName(tfile.getName());
        BinaryViewerPanel bp = new BinaryViewerPanel(blist);
        ViewerWindow vbw = new ViewerWindow(bp);
        List<Opener> openers = OpenerFactory.createOpener(blist, vbw);

        vbw.setSize(600, 720);
        vbw.setOpeners(openers);
        vbw.setVisible(true);

        return true;
    }
}
