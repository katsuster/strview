package net.katsuster.strview.media.flv;

import net.katsuster.strview.media.bit.*;

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
public class FLVHeader
        extends BitBlockAdapter
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
    public String getTypeName() {
        return "FLV tag";
    }

    @Override
    protected void readBits(BitStreamReader c) {
        readBits(c, this);
    }

    public static void readBits(BitStreamReader c,
                            FLVHeader d) {
    }

    @Override
    protected void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                             FLVHeader d) {
    }
}
