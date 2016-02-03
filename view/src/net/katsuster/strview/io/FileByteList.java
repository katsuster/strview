package net.katsuster.strview.io;

import java.io.*;

/**
 * <p>
 * RandomAccessFile クラスを LargeArray インタフェースでラップしたクラスです。
 * </p>
 *
 * @author katsuhiro
 */
public class FileByteList extends AbstractLargeByteList
        implements LargeByteList {
    private RandomAccessFile buf;
    private long filepointer;

    /**
     * <p>
     * 指定された長さを持つテンポラリファイルを返します。
     * </p>
     *
     * @param size ファイルの長さ
     * @return テンポラリファイル
     */
    protected static RandomAccessFile createTempFile(long size) {
        File temp_file;
        RandomAccessFile raf;

        if (size < 0) {
            throw new NegativeArraySizeException(
                    "size:" + size + " is negative.");
        }

        try {
            temp_file = File.createTempFile("bintmp", ".tmp");
            temp_file.deleteOnExit();
            raf = new RandomAccessFile(temp_file.getAbsolutePath(), "rw");
            raf.setLength(size);
            raf.seek(0);
        } catch (IOException ex) {
            throw new IllegalArgumentException(
                    "cannot create the temporary file.");
        }

        return raf;
    }

    /**
     * <p>
     * 長さ 0 の一時ファイルを利用してバイト列を作成します。
     * </p>
     */
    public FileByteList() {
        this(0);
    }

    /**
     * <p>
     * 指定された長さの一時ファイルを利用してバイト列を作成します。
     * </p>
     *
     * @param size 作成するバイト列の長さ
     * @throws NegativeArraySizeException 負のサイズを指定した場合
     */
    public FileByteList(long size) {
        this(createTempFile(size));
    }

    /**
     * <p>
     * 既存のファイルを利用してバイト列を作成します。
     * </p>
     *
     * @param path 利用するファイルへのパス
     */
    public FileByteList(String path) {
        super(0);

        File f;

        if (path == null) {
            throw new IllegalArgumentException(
                    "path is null.");
        }

        f = new File(path);

        try {
            buf = new RandomAccessFile(f.getAbsolutePath(), "rw");
            buf.seek(0);
            filepointer = 0;
        } catch (IOException ex) {
            throw new IllegalArgumentException(
                    "cannot open the file '" + path + "'.");
        }
    }

    /**
     * <p>
     * 既存のファイルを利用してバイト列を作成します。
     * </p>
     *
     * @param file 利用するファイル
     */
    public FileByteList(RandomAccessFile file) {
        super(0);

        if (file == null) {
            throw new IllegalArgumentException(
                    "file is null.");
        }

        try {
            buf = file;
            buf.seek(0);
            filepointer = 0;
        } catch (IOException ex) {
            throw new IllegalStateException(
                    "cannot rewind the file.");
        }
    }

    @Override
    protected void finalize() {
        try {
            buf.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public long length() {
        try {
            synchronized (buf) {
                return buf.length();
            }
        } catch (IOException ex) {
            throw new IllegalStateException(
                    "cannot get the length.");
        }
    }

    /**
     * <p>
     * get/set のパラメータをチェックし、
     * 異常があれば例外をスローされます。
     * </p>
     *
     * <p>
     * 下記に当てはまるときは IndexOutOfBoundsException がスローされます。
     * </p>
     *
     * <ul>
     * <li>index が負の場合</li>
     * <li>index が Integer.MAX_VALUE より大きい場合</li>
     * </ul>
     *
     * @param index  転送元（get 時）、転送先（set 時）の転送開始位置
     * @throws IndexOutOfBoundsException index が負の場合
     */
    protected void checkIndex(long index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException(
                    "index:" + index + " is negative.");
        }
    }

    /**
     * <p>
     * get/set のパラメータをチェックし、
     * 異常があれば例外をスローされます。
     * </p>
     *
     * <p>
     * 下記に当てはまるときは IndexOutOfBoundsException がスローされます。
     * </p>
     *
     * <ul>
     * <li>checkIndex() が例外をスローした場合</li>
     * <li>offset, length が負の場合</li>
     * </ul>
     *
     * @param index  転送元（get 時）、転送先（set 時）の転送開始位置
     * @param offset 転送先（get 時）、転送元（set 時）の転送開始位置
     * @param length 転送する量
     * @throws IndexOutOfBoundsException offset, length が負の場合、
     * checkIndex() が例外をスローした場合
     * @see checkIndex()
     */
    protected void checkIndexOffsetLength(
            long index, int offset, int length) {
        checkIndex(index);

        if (offset < 0) {
            throw new IndexOutOfBoundsException(
                    "offset:" + offset + " is negative.");
        }
        if (length < 0) {
            throw new IndexOutOfBoundsException(
                    "length:" + length + " is negative.");
        }
        if (index + length > length()) {
            throw new IndexOutOfBoundsException(
                    "index:" + index + " + "
                            + "length:" + length + " is larger than "
                            + "this.length:" + length() + ".");
        }
    }

    @Override
    public int get(long index) {
        int result;

        checkIndex(index);

        try {
            synchronized (buf) {
                if (index != filepointer) {
                    buf.seek(index);
                    filepointer = index;
                }

                result = buf.read();
                if (result == -1) {
                    //EOF
                    throw new IndexOutOfBoundsException(
                            "cannot read from " + index + "(reached EOF).");
                }
                filepointer += 1;
            }

            return result;
        } catch (IOException ex) {
            throw new IndexOutOfBoundsException(
                    "cannot read from " + index + ".");
        }
    }

    @Override
    public int get(long index, byte[] dest, int offset, int length) {
        int res = 0;

        checkIndexOffsetLength(index, offset, length);

        if (offset + length > dest.length) {
            throw new IndexOutOfBoundsException(
                    "offset:" + offset + " + "
                            + "length:" + length + " is larger than "
                            + "dest.length:" + dest.length + ".");
        }


        try {
            synchronized (buf) {
                if (index != filepointer) {
                    buf.seek(index);
                    filepointer = index;
                }

                buf.readFully(dest, offset, length);
                filepointer += length;
            }
        } catch (IOException ex) {
            throw new IndexOutOfBoundsException(
                    "cannot read from " + index
                            + " to " + (index + length) + ".");
        }

        return res;
    }

    @Override
    public void set(long index, byte data) {
        checkIndex(index);

        try {
            synchronized (buf) {
                if (index != filepointer) {
                    buf.seek(index);
                    filepointer = index;
                }

                buf.write(data);
                filepointer += 1;
            }
        } catch (IOException ex) {
            throw new IndexOutOfBoundsException(
                    "cannot write at " + index + ".");
        }
    }

    @Override
    public int set(long index, byte[] src, int offset, int length) {
        checkIndexOffsetLength(index, offset, length);

        if (offset + length > src.length) {
            throw new IndexOutOfBoundsException(
                    "offset:" + offset + " + "
                            + "length:" + length + " is larger than "
                            + "src.length:" + src.length + ".");
        }

        try {
            synchronized (buf) {
                if (index != filepointer) {
                    buf.seek(index);
                    filepointer = index;
                }

                buf.write(src, offset, length);
                filepointer += length;
            }
        } catch (IOException ex) {
            throw new IndexOutOfBoundsException(
                    "cannot write at " + index + ".");
        }

        return length;
    }
}
