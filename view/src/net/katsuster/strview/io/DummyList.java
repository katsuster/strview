package net.katsuster.strview.io;

import net.katsuster.strview.util.*;

/**
 * <p>
 * 何もしない LargeList クラスです。
 * </p>
 */
public class DummyList<T> extends AbstractLargeList<T>
        implements Cloneable {
    /**
     * <p>
     * 指定された名前、長さ 0 のリストを作成します。
     * </p>
     *
     * @param name リストの名前
     */
    public DummyList(String name) {
        super(name, 0);
    }

    /**
     * <p>
     * 名前無し、指定された長さのリストを作成します。
     * </p>
     *
     * @param l リストの長さ
     */
    public DummyList(long l) {
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
    public DummyList(String name, long l) {
        super(name, l);
    }

    @Override
    public DummyList<T> clone()
            throws CloneNotSupportedException {
        DummyList<T> obj = (DummyList<T>)super.clone();

        return obj;
    }

    @Override
    public T getInner(long index) {
        return null;
    }

    @Override
    public void setInner(long index, T data) {
        //do nothing
    }
}
