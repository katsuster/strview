package net.katsuster.strview.gui.opener;

import java.awt.event.*;
import javax.swing.*;

import net.katsuster.strview.gui.view.PacketTreeViewerPanel;
import net.katsuster.strview.gui.view.ViewerWindow;
import net.katsuster.strview.media.*;
import net.katsuster.strview.media.asf.*;
import net.katsuster.strview.media.flv.*;
import net.katsuster.strview.media.m2v.*;
import net.katsuster.strview.media.m4v.*;
import net.katsuster.strview.media.mkv.*;
import net.katsuster.strview.media.mp4.*;
import net.katsuster.strview.media.ps.*;
import net.katsuster.strview.media.riff.*;
import net.katsuster.strview.media.rmff.*;
import net.katsuster.strview.media.test.*;
import net.katsuster.strview.media.ts.*;
import net.katsuster.strview.util.*;
import net.katsuster.strview.util.bit.*;
import net.katsuster.strview.gui.opener.Opener.*;

/**
 * 指定されたリストをビットデータとみなして開きます。
 */
public class ActionOpenAsBit extends ActionOpenAs {
    private static final long serialVersionUID = 1L;

    public ActionOpenAsBit(ViewerWindow w) {
        super(w);
    }

    public ActionOpenAsBit(String name, ViewerWindow w) {
        super(name, w);
    }

    public ActionOpenAsBit(String name, Icon icon, ViewerWindow w) {
        super(name, icon, w);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        LargeList<?> l = getParent().getViewer().getList();

        if (l instanceof LargeBitList) {
            LargeBitList lb = (LargeBitList) l;
            LargePacketList<?> lp = getPacketList(getFileTypeByFileName(l.getName()), lb);
            PacketTreeViewerPanel pp = new PacketTreeViewerPanel(lp);
            ViewerWindow vpw = new ViewerWindow(pp);
            vpw.addHelperViewer(getParent());
            vpw.setSize(800, 720);
            vpw.setVisible(true);
        }
    }

    /**
     * <p>
     * ファイル名をヒントにファイル形式を取得します。
     * </p>
     *
     * <p>
     * 自動 (Auto) を指定した場合は、ファイル名から類推します。
     * </p>
     *
     * @param name ファイル名
     * @return ファイル形式
     */
    public FILE_TYPE getFileTypeByFileName(String name) {
        Opener f = getParent().getSelectedOpener();
        FILE_TYPE ft = FILE_TYPE.FT_AUTO;

        if (f != null) {
            ft = f.getType();
        }

        if (ft == FILE_TYPE.FT_AUTO) {
            return guessFileType(name);
        }

        return ft;
    }

    /**
     * <p>
     * 指定されたファイル形式に適したパケットリストを生成します。
     * </p>
     *
     * @param t ファイル形式
     * @param l パケットリストの元データとなるビットリスト
     * @return パケットリスト
     */
    public static LargePacketList<?> getPacketList(FILE_TYPE t, LargeBitList l) {
        LargePacketList<?> list = null;

        switch (t) {
        case FT_ASF:
            list = new ASFObjectList(l);
            break;
        case FT_FLV:
            list = new FLVTagList(l);
            break;
        case FT_MATROSKA:
            list = new MKVTagList(l);
            break;
        case FT_MPEG2PS:
            list = new PSPackList(l);
            break;
        case FT_MPEG2TS:
            list = new TSPacketList(l);
            break;
        case FT_MPEG4:
            list = new MP4BoxList(l);
            break;
        case FT_RIFF:
            list = new RIFFChunkList(l);
            break;
        case FT_RMFF:
            list = new RMFFChunkList(l);
            break;
        case FT_MPEG2VIDEO:
            list = new M2VDataList(l);
            break;
        case FT_MPEG4VISUAL:
            list = new M4VObjectList(l);
            break;
        case FT_TEST_SRC:
            //Cannot create from BitList
            list = null;
            break;
        case FT_TEST_FIXED:
            list = new FixedPacketList(l);
            break;
        case FT_TEST_MARKED:
            list = new MarkedPacketList(l);
            break;
        case FT_UNKNOWN:
            list = null;
            break;
        }

        return list;
    }

    /**
     * <p>
     * ファイル形式を類推します。
     * </p>
     *
     * @param name ファイル名
     * @return ファイル形式
     */
    public static FILE_TYPE guessFileType(String name) {
        String ext = getSuffix(name);

        if (ext.equals("asf") || ext.equals("wma") || ext.equals("wmv")) {
            return FILE_TYPE.FT_ASF;
        }
        if (ext.equals("flv")) {
            return FILE_TYPE.FT_FLV;
        }
        if (ext.equals("mkv") || ext.equals("webm")) {
            return FILE_TYPE.FT_MATROSKA;
        }
        if (ext.equals("mpg") || ext.equals("ps") || ext.equals("vob")) {
            return FILE_TYPE.FT_MPEG2PS;
        }
        if (ext.equals("ts")) {
            return FILE_TYPE.FT_MPEG2TS;
        }
        if (ext.equals("mp4") || ext.equals("mov")) {
            return FILE_TYPE.FT_MPEG4;
        }
        if (ext.equals("avi") || ext.equals("cur") || ext.equals("ico") || ext.equals("wav")) {
            return FILE_TYPE.FT_RIFF;
        }
        if (ext.equals("rm") || ext.equals("rmvb")) {
            return FILE_TYPE.FT_RMFF;
        }
        if (ext.equals("m2v")) {
            return FILE_TYPE.FT_MPEG2VIDEO;
        }
        if (ext.equals("m4v")) {
            return FILE_TYPE.FT_MPEG4VISUAL;
        }

        return FILE_TYPE.FT_UNKNOWN;
    }

    /**
     * <p>
     * ファイル名の拡張子を取得します。
     * </p>
     *
     * @param n ファイル名
     * @return ファイルの拡張子、なければ空文字列
     */
    public static String getSuffix(String n) {
        if (n == null) {
            return null;
        }

        int dot = n.lastIndexOf(".");
        if (dot != -1) {
            return n.substring(dot + 1);
        }
        return "";
    }
}