package net.katsuster.strview.util;

import net.katsuster.strview.media.*;

/**
 * <p>
 * 名前を持ったオブジェクトを表します。
 * </p>
 */
public interface Named {
    /**
     * <p>
     * 型名を取得します。
     * </p>
     *
     * <p>
     * クラス名 Class.getCanonicalName() を返すか、
     * より適切な名前を返すことが推奨されます。
     * </p>
     *
     * @return 型名
     */
    public String getTypeName();

    /**
     * <p>
     * 名前を取得します。
     * </p>
     *
     * @return 名前
     */
    public String getName();

    /**
     * <p>
     * 名前を設定します。
     * </p>
     *
     * @param n 名前
     */
    public void setName(String n);
}
