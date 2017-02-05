package net.katsuster.strview.media.m2v;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;
import net.katsuster.strview.media.m2v.M2VConsts.*;

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
public class M2VDataList extends AbstractPacketList<M2VData> {
    private LargeBitList buf;

    public M2VDataList() {
        super(LENGTH_UNKNOWN);
    }

    public M2VDataList(LargeBitList l) {
        super(LENGTH_UNKNOWN);

        buf = l;
    }

    @Override
    public void count() {
        FromBitListConverter c = new FromBitListConverter(buf);

        countSlow(c);
    }

    @Override
    protected M2VData readNextInner(PacketReader<?> c, long index) {
        M2VHeader tagh = null;
        M2VData packet;

        M2VHeader tmph = new M2VHeader();
        tmph.peek(c);

        if (tmph.start_code.intValue() == START_CODE.EXTENSION) {
            M2VHeaderExt tmphext = new M2VHeaderExt();
            tmphext.peek(c);

            tagh = M2VConsts.m2vExtFactory.createPacketHeader(
                    tmphext.extension_start_code_identifier.intValue());
        }
        if (tagh == null) {
            tagh = M2VConsts.m2vFactory.createPacketHeader(
                    tmph.start_code.intValue());
        }
        if (tagh == null) {
            //未対応のタグ
            tagh = new M2VHeader();
        }

        packet = new M2VData(tagh);

        packet.read(c);
        if (packet.getHeader().isRecursive()) {
            //入れ子にできるなら、本体に別のパケットが含まれている
            //かもしれないので、パケット本体を解析する
            c.position(packet.getBodyAddress());
        }

        return packet;
    }

    @Override
    protected M2VData getInner(long index) {
        FromBitListConverter c = new FromBitListConverter(buf);

        seek(c, index);

        return (M2VData)readNext(c, index);
    }

    @Override
    protected void setInner(long index, M2VData data) {

    }
}
