package net.katsuster.strview.media.mkv;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * Matroska タグリスト。
 * </p>
 *
 * <p>
 * タグの種類は下記の通り。
 * </p>
 *
 * <dl>
 * <dt>Master-Element タグ</dt>
 * <dd>他のタグを内包するタグ。</dd>
 * <dt>Unsigned Integer タグ</dt>
 * <dd>Big-endian の符号なし整数を含むタグ。</dd>
 * <dt>Signed Integer タグ</dt>
 * <dd>Big-endian の符号付き整数を含むタグ。</dd>
 * <dt>String タグ</dt>
 * <dd>ASCII(0x20 ... 0x7e) 文字列を含むタグ。</dd>
 * <dt>UTF-8 タグ</dt>
 * <dd>UTF-8 文字列を含むタグ。</dd>
 * <dt>Binary タグ</dt>
 * <dd>任意のバイナリデータを含むタグ。</dd>
 * <dt>Float タグ</dt>
 * <dd>Big-endian の 32bit あるいは 64bit 浮動小数点数を含むタグ。</dd>
 * <dt>Date タグ</dt>
 * <dd>2001-01-01T00:00:00,000000000 UTC からの経過時間を、
 * ナノ秒で表した符号付き整数を含むタグ。</dd>
 * </dl>
 *
 * @author katsuhiro
 */
public class MKVTagList extends AbstractPacketList<MKVTag> {
    private LargeBitList buf;

    public MKVTagList() {
        super(LENGTH_UNKNOWN);
    }

    public MKVTagList(LargeBitList l) {
        super(LENGTH_UNKNOWN);

        buf = l;
    }

    @Override
    public void count() {
        FromBitListConverter c = new FromBitListConverter(buf);

        countSlow(c);
    }

    @Override
    protected MKVTag readNextInner(PacketReader<?> c, long index) {
        MKVTag packet;

        MKVHeader tmph = new MKVHeader();
        tmph.peek(c);

        MKVHeader tagh = MKVConsts.mkvFactory.createPacketHeader(
                (int)tmph.tag_id.getValue());

        if (tagh != null) {
            //対応しているタグ
            packet = new MKVTag(tagh);
        } else {
            //未対応のタグなので、タグの仕様定義にあるデータ型から類推する
            long id = tmph.tag_id.getValue();
            MKVTagSpec spec = MKVConsts.getTagSpec(id);
            packet = MKVConsts.mkvDataFactory.createPacket(
                    spec.type, null);
        }

        packet.read(c);
        if (packet.getHeader().isRecursive()) {
            //入れ子にできるなら、本体に別のパケットが含まれている
            //かもしれないので、パケット本体を解析する
            c.position(packet.getBodyAddress());
        }

        return packet;
    }

    @Override
    protected MKVTag getInner(long index) {
        FromBitListConverter c = new FromBitListConverter(buf);

        seek(c, index);

        return (MKVTag)readNext(c, index);
    }

    @Override
    protected void setInner(long index, MKVTag data) {

    }
}