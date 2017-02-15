package net.katsuster.strview.ui;

import java.awt.*;

import javax.swing.*;

import net.katsuster.strview.gui.*;

/**
 * メインクラス。
 */
public class Main {
    protected Main() {
        //do nothing
    }

    public static void main(String[] args) {
        try {
            //Look and Feel をシステム標準に変更する
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                ex.printStackTrace();
                return;
            }

            //結果表示用のフレームを作成する
            FileDropWindow w = new FileDropWindow();
            w.setTitle("strview");
            w.setSize(300, 100);
            w.setVisible(true);
        } catch (HeadlessException ex) {

        }
    }
}
