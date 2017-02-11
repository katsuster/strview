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
public abstract class AbstractPacketList<T> extends AbstractLargeList<T> {
    //木構造を管理するためのスタック
    private Deque<Packet> stack_packet;
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
     * パケットのスタックを取得します。
     * </p>
     *
     * @return パケットスタック
     */
    protected Deque<Packet> getPacketStack() {
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
     * このため current がスタックに残るとは限りません。
     * </p>
     *
     * @param current スタックに追加するパケット
     */
    protected void stackPacket(Packet current) {
        Packet p;
        PacketRange cr = current.getRange(), pr;

        while (true) {
            p = getPacketStack().peek();
            if (p == null) {
                //パケットがない
                break;
            }
            pr = p.getRange();

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

        p = getPacketStack().peek();
        if (p != null) {
            pr = p.getRange();
            pr.appendChild(cr);
            cr.setParentNode(pr);
        }

        getPacketStack().push(current);
    }

    /**
     * <p>
     * パケットをキャッシュに追加します。
     * </p>
     *
     * <p>
     * 次回以降のシークが速くなる可能性があります。
     * </p>
     *
     * @param p キャッシュするパケット
     */
    protected void cachePacket(Packet p) {
        PacketRange pr = p.getRange();

        if (cache_packet.containsKey(pr.getNumber())) {
            return;
        }

        cache_packet.put(pr.getNumber(), pr);
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
    protected void countSlow(PacketReader<?> c) {
        long cnt = 0;

        getPacketStack().clear();
        try {
            while (true) {
                readNext(c, cnt);
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
     * パケットスタックをクリアし、適切に設定し直します。
     * </p>
     *
     * @param c 各メンバの変換を実施するオブジェクト
     * @param index シークする位置のパケット ID
     */
    protected void seek(PacketReader<?> c, long index) {
        getPacketStack().clear();
        seekInner(c, index);
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
    protected void seekInner(PacketReader<?> c, long index) {
        long start;

        start = seekNearest(c, index, cache_packet);
        seekSlow(c, start, index);
    }

    /**
     * <p>
     * 指定された位置の近くまでシークします。
     * </p>
     *
     * @param c 各メンバの変換を実施するオブジェクト
     * @param index シークする位置のパケット ID
     * @param cache パケット位置のキャッシュマップ
     * @return シークした位置
     */
    protected long seekNearest(PacketReader<?> c, long index, NavigableMap<Long, PacketRange> cache) {
        Map.Entry<Long, PacketRange> ent = cache.floorEntry(index);

        if (ent == null) {
            return 0;
        }

        c.position(ent.getValue().getStart());
        return ent.getKey();
    }

    /**
     * <p>
     * シークを行います。
     * </p>
     *
     * <p>
     * パケットスタックおよび変換オブジェクトの現在位置を適切に設定してから、
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
    protected void seekSlow(PacketReader<?> c, long start, long index) {
        for (long i = start; i < index; i++) {
            readNext(c, i);
        }
    }

    /**
     * <p>
     * 現在位置からパケットを読み出し、インデックス、レベルを設定します。
     * </p>
     *
     * @param c 各メンバの変換を実施するオブジェクト
     * @param index パケットに付与する ID
     * @return パケット
     */
    protected Packet readNext(PacketReader<?> c, long index) {
        Packet p;
        PacketRange pr;

        p = readNextInner(c, index);
        pr = p.getRange();
        stackPacket(p);
        pr.setNumber(index);

        cachePacket(p);

        return p;
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
    protected abstract Packet readNextInner(PacketReader<?> c, long index);
}
