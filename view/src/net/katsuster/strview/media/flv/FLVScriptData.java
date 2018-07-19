package net.katsuster.strview.media.flv;

import net.katsuster.strview.media.bit.*;

/**
 * <p>
 * Base class of SCRIPTDATAxxxx
 * </p>
 */
public class FLVScriptData
        extends BitBlockAdapter
        implements Cloneable {
    public static final long LIMIT_INVALID = -1;

    private long limit = -2;

    public FLVScriptData() {
        this("", LIMIT_INVALID);
    }

    public FLVScriptData(String n) {
        this(n, LIMIT_INVALID);
    }

    public FLVScriptData(long l) {
        this("", l);
    }

    public FLVScriptData(String n, long l) {
        super(n);
        limit = l;
    }

    @Override
    public FLVScriptData clone()
            throws CloneNotSupportedException {
        FLVScriptData obj = (FLVScriptData)super.clone();

        return obj;
    }

    @Override
    protected void readBits(BitStreamReader c) {
        readBits(c, this);
    }

    public static void readBits(BitStreamReader c,
                                FLVScriptData d) {
    }

    @Override
    protected void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 FLVScriptData d) {
    }

    /**
     * <p>
     * データを配置できる位置の上限を取得します。
     * </p>
     *
     * @return データを配置できる位置の上限（ビット単位）
     */
    public long getLimit() {
        return limit;
    }

    /**
     * <p>
     * データを配置できる位置の上限を設定します。
     * </p>
     *
     * @param l データを配置できる位置の上限（ビット単位）
     */
    public void setLimit(long l) {
        limit = l;
    }
}
