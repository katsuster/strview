package net.katsuster.strview.media;

import java.util.*;
import java.lang.reflect.*;

/**
 * <p>
 * パケットのファクトリクラス。
 * </p>
 *
 * <p>
 * このクラスは、
 * パケット Class オブジェクト（汎用型 P で示すクラス）と、
 * パケットのヘッダの Class オブジェクト（汎用型 H で示すクラス）と、
 * ID（汎用型 K で示すクラス）を、
 * パラメータとして取ります。
 * </p>
 *
 * <p>
 * このクラスのオブジェクトを生成する方法は 2通りあります。
 * </p>
 *
 * <p>
 * 一つは ID とパケットヘッダクラス H の対応表となる Map オブジェクトを、
 * コンストラクタに渡してファクトリオブジェクトを生成する方法です。
 * </p>
 *
 * <p>
 * もう一つは空の対応表を持ったファクトリオブジェクトを生成し、
 * put メソッドにより対応関係を後から追加する方法です。
 * </p>
 *
 *
 * <p>
 * パケットの生成メソッド createPacket() は、
 * ID とユニット Class オブジェクトをパラメータとして取り、
 * 下記の手順で新たなパケットのインスタンスを生成します。
 * </p>
 *
 * <ul>
 * <li>コンストラクタに渡したパケットクラスから、
 * パケットのインスタンスを生成します。</li>
 * <li>指定された ID に対応するユニットクラスを対応表
 * （コンストラクタに渡したもの）から探します。</li>
 * <li>もし対応するクラスがあれば、クラスのインスタンスを生成します。</li>
 * <li>もし対応するクラスがなければ、
 * パラメータに渡されたユニットクラスのインスタンスを生成します。</li>
 * <li>もしパラメータに渡されたユニットクラスもなければ、
 * コンストラクタに渡されたユニットクラスのインスタンスを生成します。</li>
 * <li>生成したユニットを、パケットのヘッダに設定します。</li>
 * </ul>
 *
 * <p>
 * ファクトリメソッドにより生成するパケットのクラス P は、
 * パケットヘッダのクラス H のオブジェクトを引数に取る
 * コンストラクタを実装する必要があります。
 * コンストラクタが実装されていない場合、
 * パケット生成時に例外がスローされます。
 * </p>
 *
 * @param <P> ファクトリが生成するパケットの型
 * @param <H> ファクトリが生成するパケットのヘッダの型
 * @param <K> パケットのヘッダを識別する ID の型
 *
 * @author katsuhiro
 */
public class PacketFactory<P extends Packet, H extends Block, K extends Object> {
    //このファクトリクラスで生成するパケットのクラス
    private Class<? extends P> pclass;
    //このファクトリクラスで生成するパケットのヘッダのクラス
    private Class<? extends H> hclass;
    //ID と、ID に対応するユニットクラスのペアを登録した表
    private Map<K, Class<? extends H>> table;

    /**
     * <p>
     * パケットのクラスと、パケットヘッダのクラスを指定して、
     * 空の表（ID とパケットヘッダの対応表）を持つ、
     * ファクトリオブジェクトを生成します。
     * </p>
     *
     * @param pcl ファクトリで生成するパケットのクラス
     * @param hcl パケットのヘッダのクラス
     */
    public PacketFactory(Class<? extends P> pcl, Class<? extends H> hcl) {
        this(pcl, hcl, new HashMap<K, Class<? extends H>>());
    }

    /**
     * <p>
     * パケットのクラスと、パケットヘッダのクラスと、
     * ID とパケットヘッダの対応表を指定して、
     * ファクトリオブジェクトを生成します。
     * </p>
     *
     * @param pcl ファクトリで生成するパケットのクラス
     * @param hcl パケットのヘッダのクラス
     * @param t ID とパケットヘッダの対応表
     */
    public PacketFactory(Class<? extends P> pcl, Class<? extends H> hcl, Map<K, Class<? extends H>> t) {
        setPacketClass(pcl);
        setPacketHeaderClass(hcl);
        setTable(t);
    }

    /**
     * <p>
     * このファクトリクラスで生成するパケットのクラスを取得します。
     * </p>
     *
     * @return パケットのクラス
     */
    protected Class<? extends P> getPacketClass() {
        return pclass;
    }

    /**
     * <p>
     * このファクトリクラスで生成するパケットのクラスを設定します。
     * </p>
     *
     * @param c パケットのクラス
     */
    protected void setPacketClass(Class<? extends P> c) {
        pclass = c;
    }

    /**
     * <p>
     * このファクトリクラスで生成する、
     * パケットのヘッダのクラスを取得します。
     * </p>
     *
     * @return パケットのヘッダのクラス
     */
    protected Class<? extends H> getPacketHeaderClass() {
        return hclass;
    }

    /**
     * <p>
     * このファクトリクラスで生成する、
     * パケットのヘッダのクラスを設定します。
     * </p>
     *
     * @param c パケットのヘッダのクラス
     */
    protected void setPacketHeaderClass(Class<? extends H> c) {
        hclass = c;
    }

    /**
     * <p>
     * ID と、ID に対応するユニットクラスのペアを登録した表を取得します。
     * </p>
     *
     * @return ID とユニットクラスの対応表
     */
    public Map<K, Class<? extends H>> getTable() {
        return table;
    }

    /**
     * <p>
     * ID と、ID に対応するユニットクラスのペアを登録した表を設定します。
     * </p>
     *
     * @param t ID とユニットクラスの対応表
     */
    protected void setTable(Map<K, Class<? extends H>> t) {
        table = t;
    }

    /**
     * <p>
     * ID とパケットヘッダの対応を追加します。
     * </p>
     *
     * @param key ID
     * @param val パケットヘッダのクラス
     * @return 追加したパケットヘッダのクラス
     */
    public Class<? extends H> put(K key, Class<? extends H> val) {
        return table.put(key, val);
    }

    /**
     * <p>
     * ID とパケットヘッダの対応表の全メンバを追加します。
     * </p>
     *
     * @param m ID とパケットヘッダの対応表
     */
    public void putAll(Map<K, Class<? extends H>> m) {
        table.putAll(m);
    }

    /**
     * <p>
     * 指定した ID に対応するパケットヘッダを生成します。
     * 対応する ID が存在しない場合は null を返します。
     * </p>
     * @param id パケットヘッダを識別する ID
     * @return 指定した ID に対応するパケットヘッダ、
     * 対応する ID がなければ null
     */
    public H createPacketHeader(K id) {
        Class<? extends H> c;

        c = table.get(id);
        if (c == null) {
            //見つからなかった
            return null;
        }

        try {
            return c.newInstance();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
            return null;
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * <p>
     * 指定した ID に対応したパケットヘッダを持つ、
     * パケットを生成します。
     * </p>
     *
     * @param id ヘッダを識別する ID
     * @param def デフォルトで生成するパケットヘッダのクラス、
     * 不要な場合は null
     * @return パケットのインスタンス
     */
    public P createPacket(K id, Class<? extends H> def) {
        Constructor<? extends P> con;
        H h;
        P p;
        String msg;

        try {
            h = createPacketHeader(id);

            if (h == null) {
                //ID に該当するヘッダがない
                if (def != null) {
                    //パラメータで指定されたデフォルトヘッダを生成する
                    h = def.newInstance();
                } else {
                    //コンストラクタで指定されたデフォルトヘッダを生成する
                    h = getPacketHeaderClass().newInstance();
                }
            }

            con = getPacketClass().getConstructor(getPacketHeaderClass());
            p = con.newInstance(h);

            return p;
        } catch (NoSuchMethodException ex) {
            ex.printStackTrace();
            msg = "cannot find constructor of packet.";
        } catch (InstantiationException e) {
            e.printStackTrace();
            msg = "cannot create new packet.";
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            msg = "cannot create new packet(illegal access).";
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            msg = "cannot create new packet(illegal argument).";
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            msg = "cannot create new packet(illegal invoking).";
        }

        throw new IllegalStateException(msg);
    }
}
