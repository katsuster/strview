package net.katsuster.strview.test.io;

import java.io.*;

/**
 * @author katsuhiro
 */
public class TempFile {
    protected TempFile() {
        //do nothing
    }

    /**
     * <p>
     * 指定されたバイト列でファイルの内容を初期化した、
     * テンポラリファイルを返します。
     * </p>
     *
     * @param array ファイルを初期化するためのバイト列
     * @return ファイルの内容が初期化されたテンポラリファイル
     */
    public static RandomAccessFile initTempFile(byte[] array) {
        File temp_file;
        RandomAccessFile raf;

        try {
            temp_file = File.createTempFile("braft", ".tmp");
            temp_file.deleteOnExit();
            raf = new RandomAccessFile(temp_file.getAbsolutePath(), "rw");
            raf.setLength(array.length);
            raf.seek(0);
            raf.write(array);
        } catch (IOException ex) {
            throw new IllegalArgumentException("cannot create the temporary file.");
        }

        return raf;
    }

    /**
     * <p>
     * ファイルの先頭にあるバイト列を読み出します。
     * </p>
     *
     * @param raf ファイル
     * @param array バイト列を格納するバッファ
     */
    public static void readTempFile(RandomAccessFile raf, byte[] array) {
        try {
            raf.seek(0);
            raf.readFully(array);
        } catch (IOException ex) {
            throw new RuntimeException("temp file read error.");
        }
    }

    /**
     * <p>
     * ファイルの先頭に指定されたバイト列を書き込みます。
     * </p>
     *
     * @param raf ファイル
     * @param array バイト列を格納するバッファ
     */
    public static void writeTempFile(RandomAccessFile raf, byte[] array) {
        try {
            raf.seek(0);
            raf.write(array);
        } catch (IOException ex) {
            throw new RuntimeException("temp file write error.");
        }
    }
}
