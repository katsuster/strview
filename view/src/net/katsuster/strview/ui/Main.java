package net.katsuster.strview.ui;

import net.katsuster.strview.io.*;

/**
 * メインクラス。
 */
public class Main {
    public static void main(String[] args) {
        LargeBitList l = new ByteToBitList(new FileByteList());
    }
}
