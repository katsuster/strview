package net.katsuster.strview.media;

import java.util.*;

import net.katsuster.strview.util.*;

/**
 * <p>
 * パケットリスト。
 * </p>
 *
 * @author katsuhiro
 */
public abstract class AbstractPacketList<T extends Packet> extends AbstractLargeList<T>
        implements LargePacketList<T> {
    //木構造を管理するためのスタック
    private Deque<PacketRange> stack_packet;
    //インデックスキャッシュ
    private NavigableMap<Long, PacketRange> cache_packet;

    public AbstractPacketList() {
        this(LENGTH_UNKNOWN);
    }

    public AbstractPacketList(long l) {
        super(l);

        stack_packet = new ArrayDeque<>();
        cache_packet = new TreeMap<>();
    }

    /**
     * <p>
     * パケットリストの短い名前を取得します。
     * </p>
     *
     * <p>
     * クラス名 Class.getCanonicalName() を返すか、
     * より適切な名前を返すことが推奨されます。
     * </p>
     *
     * @return パケットの短い名前
     */
    @Override
    public String getShortName() {
        return getClass().getName();
    }

    /**
     * <p>
     * パケットが木構造を持つかどうかを返します。
     * </p>
     *
     * <p>
     * デフォルトの実装では常に true を返します。
     * シーク時に木構造の管理が不要であれば、
     * このメソッドをオーバライドし false を返してください。
     * </p>
     *
     * @return パケットが木構造を持つなら true、持たないなら false
     */
    @Override
    public boolean hasTreeStructure() {
        return true;
    }

    /**
     * <p>
     * ビット列に変換します。
     * </p>
     *
     * <p>
     * パケット列とメソッドが返すビット列は等価ではありません。
     * すなわちビット列はパケット列の全ての情報を表しているとは限りません。
     * </p>
     *
     * <p>
     * デフォルトの実装では常に null を返します。
     * </p>
     *
     * @return ビット列、ビット列への変換ができない場合は null
     */
    public LargeBitList toBitList() {
        return null;
    }

    /**
     * <p>
     * パケットのスタックを取得します。
     * </p>
     *
     * @return パケットスタック
     */
    protected Deque<PacketRange> getPacketStack() {
        return stack_packet;
    }

    /**
     * <p>
     * パケットをスタックに追加します。
     * </p>
     *
     * <p>
     * 終端に到達している全てのパケットは、
     * スタックから削除されます。
     * </p>
     *
     * @param cr スタックに追加するパケット
     */
    protected void stackPacket(PacketRange cr) {
        PacketRange pr;

        if (!hasTreeStructure()) {
            return;
        }

        while (true) {
            pr = getPacketStack().peek();
            if (pr == null) {
                //パケットがない
                break;
            }

            /*if (p instanceof RootPacket) {
                //ルートパケットは終了させない（＝長さ無限と同じ扱い）
                break;
            }*/

            if (cr.getStart() + cr.getLength() <= pr.getStart() + pr.getLength()) {
                //パケットはまだ続いている
                break;
            }

            //パケットの終わりに到達したのでスタックから外す
            getPacketStack().pop();
        }

        pr = getPacketStack().peek();
        if (pr != null) {
            pr.appendChild(cr);
        }

        getPacketStack().push(cr);
    }

    /**
     * <p>
     * パケットの存在する範囲を取得します。
     * </p>
     *
     * <p>
     * 存在しなければ新たに生成します。
     * </p>
     *
     * @param index パケットの番号
     * @return パケットの存在する範囲
     */
    protected PacketRange getPacketRange(long index) {
        PacketRange pr;

        pr = cache_packet.get(index);
        if (pr == null) {
            pr = new SimplePacketRange(index, 0);
            cache_packet.put(index, pr);
        }

        return pr;
    }

    /**
     * <p>
     * パケットを数え、リストの長さを確定させます。
     * </p>
     *
     * <p>
     * 先頭から終端まで全パケットを読み出すため、
     * 正確に数えられますが、非常に低速です。
     * </p>
     *
     * @param c 各メンバの変換を実施するオブジェクト
     */
    protected void countSlow(StreamReader<?> c) {
        long cnt = 0;

        getPacketStack().clear();
        try {
            while (true) {
                Packet p = readNext(c, cnt);
                stackPacket(p.getRange());
                cnt++;
            }
        } catch (IndexOutOfBoundsException ex) {
            //End
            length(cnt);
        }
    }

    /**
     * <p>
     * シークを行います。
     * </p>
     *
     * <p>
     * デフォルトの実装では seekNearest(), seekSlow() を呼び出します。
     * </p>
     *
     * @param c 各メンバの変換を実施するオブジェクト
     * @param index シークする位置のパケット ID
     */
    protected void seek(StreamReader<?> c, long index) {
        long start;

        start = seekNearest(c, index);
        seekSlow(c, start, index);
    }

    /**
     * <p>
     * 指定された位置の近くまでシークします。
     * </p>
     *
     * @param c 各メンバの変換を実施するオブジェクト
     * @param index シークする位置のパケット ID
     * @return シークした位置
     */
    protected long seekNearest(StreamReader<?> c, long index) {
        Map.Entry<Long, PacketRange> ent = cache_packet.floorEntry(index);
        PacketRange pr;

        if (ent == null) {
            return 0;
        }
        pr = ent.getValue();

        if (pr.getNumber() == index) {
            c.position(pr.getStart());

            return ent.getKey();
        } else {
            if (pr.getRecursive()) {
                c.position(pr.getBodyAddress());
            } else {
                c.position(pr.getStart() + pr.getLength());
            }

            return ent.getKey() + 1;
        }
    }

    /**
     * <p>
     * シークを行います。
     * </p>
     *
     * <p>
     * 変換オブジェクトの現在位置を適切に設定してから、
     * 呼び出す必要があります。
     * </p>
     *
     * <p>
     * 開始位置から指定された位置まで順にパケットを読み出すため、
     * 確実にシークできますが、非常に低速です。
     * </p>
     *
     * @param c 各メンバの変換を実施するオブジェクト
     * @param start 開始位置のパケット ID
     * @param index シークする位置のパケット ID
     */
    protected void seekSlow(StreamReader<?> c, long start, long index) {
        for (long i = start; i < index; i++) {
            readNext(c, i);
        }
    }

    /**
     * <p>
     * 現在位置からパケットを読み出します。
     * </p>
     *
     * @param c 各メンバの変換を実施するオブジェクト
     * @param index パケットに付与する ID
     * @return パケット
     */
    protected Packet readNext(StreamReader<?> c, long index) {
        PacketRange pr;

        pr = getPacketRange(index);
        return readNextInner(c, pr);
    }

    /**
     * <p>
     * 現在位置からパケットを読み出します。
     * </p>
     *
     * @param c 各メンバの変換を実施するオブジェクト
     * @param pr パケットの存在する範囲
     * @return パケット
     */
    protected abstract Packet readNextInner(StreamReader<?> c, PacketRange pr);
}
