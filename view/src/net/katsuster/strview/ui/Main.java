package net.katsuster.strview.ui;

import java.util.*;
import java.io.*;
import java.awt.HeadlessException;

import javax.swing.*;

import net.katsuster.strview.util.*;
import net.katsuster.strview.io.*;
import net.katsuster.strview.gui.*;
import net.katsuster.strview.media.ts.*;

/**
 * メインクラス。
 */
public class Main {
    protected Main() {
        //do nothing
    }

    public static void main(String[] args) {
        FileDropWindow w = null;

        if (args.length > 0) {
            String fname = args[0];
            List<File> flist = new ArrayList<File>();

            flist.add(new File(fname));

            LargeBitList blist = new ByteToBitList(new FileByteList(fname));
            TSPacketList tslist = new TSPacketList(blist);

            tslist.count();

            for (TSPacket a : tslist) {
                System.out.println(a);
            }
        }

        try {
            //Look and Feel をシステム標準に変更する
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                ex.printStackTrace();
                return;
            }

            //結果表示用のフレームを作成する
            w = new FileDropWindow();
            w.setTitle("bined");
            w.setSize(300, 100);
            w.setVisible(true);
        } catch (HeadlessException ex) {

        }
    }
}
