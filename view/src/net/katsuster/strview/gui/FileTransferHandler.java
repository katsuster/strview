package net.katsuster.strview.gui;

import java.io.*;
import java.util.*;

import java.awt.HeadlessException;
import java.awt.datatransfer.*;

import javax.swing.*;

import net.katsuster.strview.util.*;
import net.katsuster.strview.io.*;
import net.katsuster.strview.media.*;
import net.katsuster.strview.media.asf.*;
import net.katsuster.strview.media.flv.*;
import net.katsuster.strview.media.mkv.*;
import net.katsuster.strview.media.mp4.*;
import net.katsuster.strview.media.ps.*;
import net.katsuster.strview.media.ts.*;
import net.katsuster.strview.media.riff.*;
import net.katsuster.strview.media.rmff.*;
import net.katsuster.strview.media.m2v.*;
import net.katsuster.strview.media.m4v.*;
import net.katsuster.strview.media.test.*;

/**
 * <p>
 * ファイルのドラッグ＆ドロップイベントを処理するクラスです。
 * </p>
 *
 * @author katsuhiro
 */
public class FileTransferHandler extends TransferHandler {
    private static final long serialVersionUID = 1L;

    private String fileType = "Auto";

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
     * ファイル形式を取得します。
     * </p>
     *
     * <p>
     * ファイル名から判定する場合は "Auto" を指定します。
     * </p>
     *
     * @return ファイル形式
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * <p>
     * ファイル形式を設定します。
     * </p>
     *
     * <p>
     * ファイル名から判定する場合は "Auto" を指定します。
     * </p>
     *
     * @param t ファイル形式
     */
    public void setFileType(String t) {
        fileType = t;
    }

    /**
     * <p>
     * ファイルを開きます。
     * </p>
     *
     * <p>
     * 解析が可能なファイルだと判断した場合は、
     * ストリームの構造を表示するウインドウが開きます。
     * 解析が不可能なファイルだと判断した場合は、
     * バイナリデータを表示するウインドウが開きます。
     * </p>
     *
     * @param tfile ファイル
     * @return ファイルを開けた場合は true、ファイルを開けなかった場合は false
     */
    public boolean openFile(File tfile) {
        System.out.println(tfile);

        try {
            LargeBitList blist = new ByteToBitList(new FileByteList(tfile.getAbsolutePath()));
            LargePacketList<?> list = getPacketList(getFileType(fileType, tfile), blist);
            JFrame w;

            if (list != null) {
                PacketTreeViewerWindow pw = new PacketTreeViewerWindow(tfile, list);
                w = pw;
            } else {
                BinaryViewerWindow bw = new BinaryViewerWindow(tfile);
                w = bw;
            }

            w.setSize(1024, 720);
            w.setVisible(true);
        } catch (HeadlessException ex) {

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    enum FILE_TYPE {
        FT_UNKNOWN,
        FT_ASF,
        FT_FLV,
        FT_MATROSKA,
        FT_MPEG2PS,
        FT_MPEG2TS,
        FT_MPEG4,
        FT_RIFF,
        FT_RMFF,
        FT_MPEG2VIDEO,
        FT_MPEG4VISUAL,

        //For test
        FT_TEST_FIXED,
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
        case FT_TEST_FIXED:
            list = new FixedPacketList(l);
            break;
        case FT_UNKNOWN:
            list = null;
            break;
        }

        return list;
    }

    /**
     * <p>
     * ファイル形式名からファイル形式を取得します。
     * </p>
     *
     * <p>
     * 自動 (Auto) を指定した場合は、ファイル名から類推します。
     * </p>
     *
     * @param type ファイル形式
     * @param file ファイル
     * @return ファイル形式
     */
    public static FILE_TYPE getFileType(String type, File file) {
        if (type.equalsIgnoreCase("ASF")) {
            return FILE_TYPE.FT_ASF;
        }
        if (type.equalsIgnoreCase("FLV")) {
            return FILE_TYPE.FT_FLV;
        }
        if (type.equalsIgnoreCase("Matroska")) {
            return FILE_TYPE.FT_MATROSKA;
        }
        if (type.equalsIgnoreCase("MPEG2 PS")) {
            return FILE_TYPE.FT_MPEG2PS;
        }
        if (type.equalsIgnoreCase("MPEG2 TS")) {
            return FILE_TYPE.FT_MPEG2TS;
        }
        if (type.equalsIgnoreCase("MPEG4")) {
            return FILE_TYPE.FT_MPEG4;
        }
        if (type.equalsIgnoreCase("RIFF")) {
            return FILE_TYPE.FT_RIFF;
        }
        if (type.equalsIgnoreCase("RealMedia")) {
            return FILE_TYPE.FT_RMFF;
        }
        if (type.equalsIgnoreCase("MPEG2 Visual")) {
            return FILE_TYPE.FT_MPEG2VIDEO;
        }
        if (type.equalsIgnoreCase("MPEG4 Part2 Visual")) {
            return FILE_TYPE.FT_MPEG4VISUAL;
        }
        if (type.equalsIgnoreCase("Auto")) {
            return getFileTypeAuto(file);
        }

        if (type.equalsIgnoreCase("TEST: Fixed Size Packet")) {
            return FILE_TYPE.FT_TEST_FIXED;
        }

        return FILE_TYPE.FT_UNKNOWN;
    }

    /**
     * <p>
     * ファイル形式を類推します。
     * </p>
     *
     * @param tfile ファイル
     * @return ファイル形式
     */
    public static FILE_TYPE getFileTypeAuto(File tfile) {
        String ext = getSuffix(tfile.getPath());

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
