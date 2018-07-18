package net.katsuster.strview.io;

import net.katsuster.strview.util.bit.*;

/**
 * <p>
 * 何もしない LargeList クラスです。
 * </p>
 */
public class DummyBitList extends AbstractLargeBitList
        implements Cloneable {
    /**
     * <p>
     * 指定された名前、長さ 0 のリストを作成します。
     * </p>
     *
     * @param name リストの名前
     */
    public DummyBitList(String name) {
        super(name, 0);
    }

    /**
     * <p>
     * 名前無し、指定された長さのリストを作成します。
     * </p>
     *
     * @param l リストの長さ
     */
    public DummyBitList(long l) {
        super(null, l);
    }

    /**
     * <p>
     * 指定された長さ、名前のリストを作成します。
     * </p>
     *
     * @param name リストの名前
     * @param l リストの長さ
     */
    public DummyBitList(String name, long l) {
        super(name, l);
    }

    @Override
    public DummyBitList clone()
            throws CloneNotSupportedException {
        DummyBitList obj = (DummyBitList)super.clone();

        return obj;
    }

    @Override
    public Boolean getInner(long index) {
        return null;
    }

    @Override
    public void setInner(long index, Boolean data) {
        //do nothing
    }
}
