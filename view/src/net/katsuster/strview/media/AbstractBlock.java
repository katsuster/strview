package net.katsuster.strview.media;

import java.util.*;

/**
 * <p>
 * 意味のあるデータのまとまりを実装する際に利用するクラスです。
 * </p>
 *
 * <p>
 * ほとんどのメソッドが空の実装となる場合は、
 * BlockAdapter を継承し必要な関数のみをオーバライドすると便利です。
 * </p>
 *
 * <p>
 * このクラスを継承するには、下記のメソッドを実装する必要があります。
 * </p>
 *
 * <dl>
 * <dt>convert() メソッド</dt>
 * <dd>ブロックが保持するデータを、
 * 別の形式に変換します。</dd>
 * </dl>
 *
 * <p>
 * また、下記のメソッドを実装することが推奨されます。
 * </p>
 *
 * <dl>
 * <dt>コンストラクタ</dt>
 * <dd>クラス特有の方法でオブジェクトを初期化します。</dd>
 * </dl>
 *
 * @see BlockAdapter
 * @author katsuhiro
 */
public abstract class AbstractBlock implements Block {
    @Override
    public AbstractBlock clone()
            throws CloneNotSupportedException {
        AbstractBlock obj = (AbstractBlock)super.clone();

        return obj;
    }

    /**
     * <p>
     * ブロックを別の形式に変換します。
     * </p>
     *
     * <p>
     * 変換結果は PacketConverter オブジェクトに保持されます。
     * </p>
     *
     * @param c 各メンバの変換を実施するオブジェクト
     */
    @Override
    public abstract void convert(PacketConverter<?> c);

    @Override
    public String toString() {
        ToStringConverter c = new ToStringConverter(new StringBuilder());

        convert(c);

        return c.getResult().toString();
    }
}
