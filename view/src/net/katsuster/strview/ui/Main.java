package net.katsuster.strview.ui;

import java.awt.HeadlessException;

import javax.swing.*;

import net.katsuster.strview.io.*;
import net.katsuster.strview.gui.*;

/**
 * メインクラス。
 */
public class Main {
    protected Main() {
        //do nothing
    }

    public static void main(String[] args) {
        FileDropWindow w = null;

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
