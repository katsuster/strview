package net.katsuster.strview.media.flv;

import net.katsuster.strview.media.*;

/**
 * <p>
 * Base class of SCRIPTDATAxxxx
 * </p>
 */
public class FLVScriptData extends BlockAdapter
        implements Cloneable {
    public static final long LIMIT_INVALID = -1;

    private long limit = -2;

    public FLVScriptData() {
        this(LIMIT_INVALID);
    }

    public FLVScriptData(long l) {
        limit = l;
    }

    @Override
    public FLVScriptData clone()
            throws CloneNotSupportedException {
        FLVScriptData obj = (FLVScriptData)super.clone();

        return obj;
    }

    @Override
    public void read(StreamReader<?> c) {
        read(c, this);
    }

    public static void read(StreamReader<?> c,
                            FLVScriptData d) {
    }

    @Override
    public void write(StreamWriter<?> c) {
        write(c, this);
    }

    public static void write(StreamWriter<?> c,
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
