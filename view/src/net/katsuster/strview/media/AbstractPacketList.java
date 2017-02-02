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

    public AbstractPacketList() {
        this(LENGTH_UNKNOWN);
    }

    public AbstractPacketList(long l) {
        super(l);

        stack_packet = new ArrayDeque<Packet>();
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
     * ツリー構造をなすストリームについて、
     * 1つの枝の処理を開始します。
     * </p>
     *
     * <p>
     * パケットスタックの頂上にパケットを積み、
     * パケット開始イベントを通知します。
     * </p>
     *
     * @param current 処理対象となるパケット
     */
    protected void enterParentPacket(Packet current) {
        //ツリー深さレベル、親パケットを設定する
        if (getPacketStack().peek() != null) {
            getPacketStack().peek().appendChild(current);
        }

        //スタックに積む
        getPacketStack().push(current);
    }

    /**
     * <p>
     * ツリー構造をなすストリームについて、
     * 1つの枝の処理を終了します。
     * </p>
     *
     * <p>
     * パケットスタックの頂上にあるパケットを見て、
     * パケットの終端に達していれば（※）パケット終了イベントを通知し、
     * パケットスタックから取り払います。
     * </p>
     *
     * <p>
     * （※）一番最後に読み込んだパケットの終端位置が、
     * パケットスタックの頂上にあるパケットの終端位置を超えていた場合、
     * 終端に達したと判断します。
     * </p>
     *
     * @param current 一番最後に読み込んだパケット
     */
    protected void leaveParentPacket(Packet current) {
        Packet p;

        while (true) {
            p = getPacketStack().peek();
            if (p == null) {
                //パケットがなければ何もする必要はない
                break;
            }

            /*if (p instanceof RootPacket) {
                //ルートパケットは終了させない（＝長さ無限と同じ扱い）
                break;
            }*/

            if (current.getAddress() + current.getLength() <= p.getAddress() + p.getLength()) {
                //親パケットはまだ続いているので、
                //終了イベントは送らない
                break;
            }

            //スタックから外す
            getPacketStack().pop();
        }
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

        try {
            while (true) {
                Packet p = readNext(c, cnt);
                leaveParentPacket(p);
                enterParentPacket(p);
                cnt++;
            }
        } catch (IndexOutOfBoundsException ex) {
            //End
            length(cnt);
        }
    }

    /**
     * <p>
     * 先頭からシークを行います。
     * </p>
     *
     * <p>
     * 先頭から、指定された位置まで順にパケットを読み出すため、
     * 正確にシークできますが、非常に低速です。
     * </p>
     *
     * @param c 各メンバの変換を実施するオブジェクト
     * @param index シークする位置のパケット ID
     */
    protected void seekSlow(PacketReader<?> c, long index) {
        getPacketStack().clear();
        seekSlow(c, 0, index);
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
            Packet p = readNext(c, i);
            leaveParentPacket(p);
            enterParentPacket(p);
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
    protected abstract Packet readNext(PacketReader<?> c, long index);
}
