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

}
