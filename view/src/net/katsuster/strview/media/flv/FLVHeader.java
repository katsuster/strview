package net.katsuster.strview.media.flv;

import net.katsuster.strview.media.*;

/**
 * <p>
 * Flash Video タグヘッダ。
 * </p>
 *
 * <p>
 * 参考規格
 * </p>
 * <ul>
 * <li>Adobe Flash Video File Format Specification</li>
 * <li>Adobe SWF File Format Specification</li>
 * </ul>
 */
public class FLVHeader extends BlockAdapter
        implements Cloneable {
    public FLVHeader() {
    }

    @Override
    public FLVHeader clone()
            throws CloneNotSupportedException {
        FLVHeader obj = (FLVHeader)super.clone();

        return obj;
    }

    @Override
    public void read(StreamReader<?> c) {
        read(c, this);
    }

    public static void read(StreamReader<?> c,
                            FLVHeader d) {
    }

    @Override
    public void write(StreamWriter<?> c) {
        write(c, this);
    }

    public static void write(StreamWriter<?> c,
                             FLVHeader d) {
    }
}
