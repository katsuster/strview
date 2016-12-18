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
        PacketConverter<String> c = new ToStringConverter();

        c.doInit();
        convert(c);
        c.doFinal();

        return c.getResult();
    }
}
