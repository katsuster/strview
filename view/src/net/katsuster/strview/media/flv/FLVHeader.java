package net.katsuster.strview.media.flv;

import net.katsuster.strview.util.*;
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
public class FLVHeader<T extends LargeList<?>>
        extends BlockAdapter<T>
        implements Cloneable {
    public FLVHeader() {
    }

    @Override
    public FLVHeader<T> clone()
            throws CloneNotSupportedException {
        FLVHeader<T> obj = (FLVHeader<T>)super.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "FLV tag";
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
