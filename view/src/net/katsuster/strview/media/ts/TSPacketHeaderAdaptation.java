package net.katsuster.strview.media.ts;

import net.katsuster.strview.util.*;
import net.katsuster.strview.io.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * MPEG2-TS(Transport Stream) パケットヘッダのアダプテーションフィールド。
 * </p>
 *
 * <p>
 * 参考規格
 * </p>
 * <ul>
 * <li>ISO/IEC 13818-1: MPEG2 Systems</li>
 * </ul>
 *
 * @author katsuhiro
 */
public class TSPacketHeaderAdaptation extends BlockAdapter
        implements Cloneable {
    public UInt adaptation_field_length;

    public UInt discontinuity_indicator;
    public UInt random_access_indicator;
    public UInt elementary_stream_priority_indicator;
    public UInt pcr_flag;
    public UInt opcr_flag;
    public UInt splicing_point_flag;
    public UInt transport_private_data_flag;
    public UInt adaptation_field_extension_flag;

    public UInt program_clock_reference_base;
    public UInt reserved;
    public UInt program_clock_reference_extension;

    public UInt original_program_clock_reference_base;
    public UInt reserved2;
    public UInt original_program_clock_reference_extension;

    public UInt splice_countdown;

    public UInt transport_private_data_length;
    public LargeBitList private_data_byte;

    public UInt adaptation_field_extension_length;
    public UInt ltw_flag;
    public UInt piecewise_rate_flag;
    public UInt seamless_splice_flag;
    public UInt reserved3;

    public UInt ltw_valid_flag;
    public UInt ltw_offset;

    public UInt reserved4;
    public UInt piecewise_rate;

    public UInt splice_type;
    public UInt dts_next_au_high;
    public UInt marker_bit;
    public UInt dts_next_au_mid;
    public UInt marker_bit2;
    public UInt dts_next_au_low;
    public UInt marker_bit3;

    public LargeBitList reserved_byte;
    public LargeBitList stuffing_byte;

    public TSPacketHeaderAdaptation() {
        super();

        adaptation_field_length = new UInt();

        discontinuity_indicator = new UInt();
        random_access_indicator = new UInt();
        elementary_stream_priority_indicator = new UInt();
        pcr_flag = new UInt();
        opcr_flag = new UInt();
        splicing_point_flag = new UInt();
        transport_private_data_flag = new UInt();
        adaptation_field_extension_flag = new UInt();

        program_clock_reference_base = new UInt();
        reserved = new UInt();
        program_clock_reference_extension = new UInt();

        original_program_clock_reference_base = new UInt();
        reserved2 = new UInt();
        original_program_clock_reference_extension = new UInt();

        splice_countdown = new UInt();

        transport_private_data_length = new UInt();
        private_data_byte = new MemoryBitList();

        adaptation_field_extension_length = new UInt();
        ltw_flag = new UInt();
        piecewise_rate_flag = new UInt();
        seamless_splice_flag = new UInt();
        reserved3 = new UInt();

        ltw_valid_flag = new UInt();
        ltw_offset = new UInt();

        reserved4 = new UInt();
        piecewise_rate = new UInt();

        splice_type = new UInt();
        dts_next_au_high = new UInt();
        marker_bit = new UInt();
        dts_next_au_mid = new UInt();
        marker_bit2 = new UInt();
        dts_next_au_low = new UInt();
        marker_bit3 = new UInt();

        reserved_byte = new MemoryBitList();
        stuffing_byte = new MemoryBitList();
    }

    @Override
    public TSPacketHeaderAdaptation clone()
            throws CloneNotSupportedException {
        TSPacketHeaderAdaptation obj = (TSPacketHeaderAdaptation)super.clone();

        obj.adaptation_field_length = adaptation_field_length.clone();

        obj.discontinuity_indicator = discontinuity_indicator.clone();
        obj.random_access_indicator = random_access_indicator.clone();
        obj.elementary_stream_priority_indicator = elementary_stream_priority_indicator.clone();
        obj.pcr_flag = pcr_flag.clone();
        obj.opcr_flag = opcr_flag.clone();
        obj.splicing_point_flag = splicing_point_flag.clone();
        obj.transport_private_data_flag = transport_private_data_flag.clone();
        obj.adaptation_field_extension_flag = adaptation_field_extension_flag.clone();

        obj.program_clock_reference_base = program_clock_reference_base.clone();
        obj.reserved = reserved.clone();
        obj.program_clock_reference_extension = program_clock_reference_extension.clone();

        obj.original_program_clock_reference_base = original_program_clock_reference_base.clone();
        obj.reserved2 = reserved2.clone();
        obj.original_program_clock_reference_extension = original_program_clock_reference_extension.clone();

        obj.splice_countdown = splice_countdown.clone();

        obj.transport_private_data_length = transport_private_data_length.clone();
        obj.private_data_byte = private_data_byte;

        obj.adaptation_field_extension_length = adaptation_field_extension_length.clone();
        obj.ltw_flag = ltw_flag.clone();
        obj.piecewise_rate_flag = piecewise_rate_flag.clone();
        obj.seamless_splice_flag = seamless_splice_flag.clone();
        obj.reserved3 = reserved3.clone();

        obj.ltw_valid_flag = ltw_valid_flag.clone();
        obj.ltw_offset = ltw_offset.clone();

        obj.reserved4 = reserved4.clone();
        obj.piecewise_rate = piecewise_rate.clone();

        obj.splice_type = splice_type.clone();
        obj.dts_next_au_high = dts_next_au_high.clone();
        obj.marker_bit = marker_bit.clone();
        obj.dts_next_au_mid = dts_next_au_mid.clone();
        obj.marker_bit2 = marker_bit2.clone();
        obj.dts_next_au_low = dts_next_au_low.clone();
        obj.marker_bit3 = marker_bit3.clone();

        obj.reserved_byte = reserved_byte;
        obj.stuffing_byte = stuffing_byte;

        return obj;
    }

    @Override
    public void read(PacketReader<?> c) {
        read(c, this);
    }

    public static void read(PacketReader<?> c,
                            TSPacketHeaderAdaptation d) {
        c.enterBlock("TS packet header(adaptation field)");

        long pos_byte;
        int size_st;

        pos_byte = (c.position() >>> 3);

        //アダプテーションフィールドの長さを得る
        d.adaptation_field_length = c.readUInt( 8, d.adaptation_field_length);
        if (d.adaptation_field_length.intValue() < 0
                || TSPacket.PACKET_SIZE < d.adaptation_field_length.intValue()) {
            throw new IllegalStateException("adaptation_field is too long"
                    + "(size: " + d.adaptation_field_length + ")");
        }

        if (d.adaptation_field_length.intValue() == 0) {
            //アダプテーションフィールドが存在しない
            return;
        }

        //各種フラグ
        d.discontinuity_indicator              = c.readUInt( 1, d.discontinuity_indicator             );
        d.random_access_indicator              = c.readUInt( 1, d.random_access_indicator             );
        d.elementary_stream_priority_indicator = c.readUInt( 1, d.elementary_stream_priority_indicator);
        d.pcr_flag                             = c.readUInt( 1, d.pcr_flag                            );
        d.opcr_flag                            = c.readUInt( 1, d.opcr_flag                           );
        d.splicing_point_flag                  = c.readUInt( 1, d.splicing_point_flag                 );
        d.transport_private_data_flag          = c.readUInt( 1, d.transport_private_data_flag         );
        d.adaptation_field_extension_flag      = c.readUInt( 1, d.adaptation_field_extension_flag     );

        //フラグに対応するフィールド
        if (d.pcr_flag.intValue() == 1) {
            d.program_clock_reference_base      = c.readUInt(33, d.program_clock_reference_base     );
            d.reserved                          = c.readUInt( 6, d.reserved                         );
            d.program_clock_reference_extension = c.readUInt( 9, d.program_clock_reference_extension);
            c.mark("pcr", d.getPCRValue());
        }

        if (d.opcr_flag.intValue() == 1) {
            d.original_program_clock_reference_base      = c.readUInt(33, d.original_program_clock_reference_base     );
            d.reserved2                                  = c.readUInt( 6, d.reserved2                                 );
            d.original_program_clock_reference_extension = c.readUInt( 9, d.original_program_clock_reference_extension);
            c.mark("opcr", d.getOPCRValue());
        }

        if (d.splicing_point_flag.intValue() == 1) {
            d.splice_countdown = c.readUInt( 8, d.splice_countdown);
        }

        if (d.transport_private_data_flag.intValue() == 1) {
            //プライベートデータの長さ
            d.transport_private_data_length = c.readUInt( 8, d.transport_private_data_length);

            //プライベートデータ
            if (d.transport_private_data_length.intValue() < 0
                    || TSPacket.PACKET_SIZE < d.transport_private_data_length.intValue()) {
                throw new IllegalStateException("private_data are too long"
                        + "(size: " + d.transport_private_data_length + ")");
            }
            d.private_data_byte = c.readBitList(d.transport_private_data_length.intValue() << 3,
                    d.private_data_byte);
        }

        if (d.adaptation_field_extension_flag.intValue() == 1) {
            //拡張ヘッダ
            readExtensions(c, d);
        }

        //スタッフィングバイト
        //adaptation_field_length は
        //adaptation_field_length 自身（1バイト）の
        //長さを含んでいないため、1バイト加えて計算する
        size_st = d.adaptation_field_length.intValue() + 1
                - (int)((c.position() >>> 3) - pos_byte);
        if (size_st < 0) {
            throw new IllegalStateException("stuffing bytes have negative size"
                    + "(size: " + size_st  + ")");
        }
        d.stuffing_byte = c.readBitList(size_st << 3, d.stuffing_byte);

        c.leaveBlock();
    }

    protected static void readExtensions(PacketReader<?> c,
                                         TSPacketHeaderAdaptation d) {
        long pos_byte;
        int size_rs;

        pos_byte = (c.position() >>> 3);
        size_rs = 0;

        //フィールドの長さ
        d.adaptation_field_extension_length = c.readUInt( 8, d.adaptation_field_extension_length);
        if (d.adaptation_field_extension_length.intValue() < 0
                || TSPacket.PACKET_SIZE < d.adaptation_field_extension_length.intValue()) {
            throw new IllegalStateException("adapt header extension is too long"
                    + "(size: " + d.adaptation_field_extension_length + ")");
        }

        //各種フラグ
        d.ltw_flag             = c.readUInt( 1, d.ltw_flag            );
        d.piecewise_rate_flag  = c.readUInt( 1, d.piecewise_rate_flag );
        d.seamless_splice_flag = c.readUInt( 1, d.seamless_splice_flag);
        d.reserved3            = c.readUInt( 5, d.reserved3           );

        //フラグに対応するフィールド
        if (d.ltw_flag.intValue() == 1) {
            d.ltw_valid_flag   = c.readUInt( 1, d.ltw_valid_flag);
            d.ltw_offset       = c.readUInt(15, d.ltw_offset    );
        }

        if (d.piecewise_rate_flag.intValue() == 1) {
            d.reserved4        = c.readUInt( 2, d.reserved4     );
            d.piecewise_rate   = c.readUInt(22, d.piecewise_rate);
        }

        if (d.seamless_splice_flag.intValue() == 1) {
            d.splice_type      = c.readUInt( 4, d.splice_type     );
            d.dts_next_au_high = c.readUInt( 3, d.dts_next_au_high);
            d.marker_bit       = c.readUInt( 1, d.marker_bit      );
            d.dts_next_au_mid  = c.readUInt(15, d.dts_next_au_mid );
            d.marker_bit2      = c.readUInt( 1, d.marker_bit2     );
            d.dts_next_au_low  = c.readUInt(15, d.dts_next_au_low );
            d.marker_bit3      = c.readUInt( 1, d.marker_bit3     );
            c.mark("dts_next_au", d.getDTSNextAUValue());
        }

        //残りを埋めているバイト列
        //adaptation_field_extension_length は
        //adaptation_field_extension_length 自身（1バイト）の
        //長さを含んでいないため、1バイト加えて計算する
        size_rs = d.adaptation_field_extension_length.intValue() + 1
                - (int)((c.position() >>> 3) - pos_byte);
        if (size_rs < 0) {
            throw new IllegalStateException("reserved bytes(extension) have negative size"
                    + "(size: " + size_rs + ")");
        }
        d.reserved_byte = c.readBitList(size_rs << 3, d.reserved_byte);
    }

    @Override
    public void write(PacketWriter<?> c) {
        write(c, this);
    }

    public static void write(PacketWriter<?> c,
                             TSPacketHeaderAdaptation d) {
        c.enterBlock("TS packet header(adaptation field)");

        long pos_byte;
        int size_st;

        pos_byte = (c.position() >>> 3);

        //アダプテーションフィールドの長さを得る
        c.writeUInt( 8, d.adaptation_field_length, "adaptation_field_length");
        if (d.adaptation_field_length.intValue() < 0
                || TSPacket.PACKET_SIZE < d.adaptation_field_length.intValue()) {
            throw new IllegalStateException("adaptation_field is too long"
                    + "(size: " + d.adaptation_field_length + ")");
        }

        if (d.adaptation_field_length.intValue() == 0) {
            //アダプテーションフィールドが存在しない
            return;
        }

        //各種フラグ
        c.writeUInt( 1, d.discontinuity_indicator             , "discontinuity_indicator"             );
        c.writeUInt( 1, d.random_access_indicator             , "random_access_indicator"             );
        c.writeUInt( 1, d.elementary_stream_priority_indicator, "elementary_stream_priority_indicator");
        c.writeUInt( 1, d.pcr_flag                            , "pcr_flag"                            );
        c.writeUInt( 1, d.opcr_flag                           , "opcr_flag"                           );
        c.writeUInt( 1, d.splicing_point_flag                 , "splicing_point_flag"                 );
        c.writeUInt( 1, d.transport_private_data_flag         , "transport_private_data_flag"         );
        c.writeUInt( 1, d.adaptation_field_extension_flag     , "adaptation_field_extension_flag"     );

        //フラグに対応するフィールド
        if (d.pcr_flag.intValue() == 1) {
            c.writeUInt(33, d.program_clock_reference_base     , "program_clock_reference_base"     );
            c.writeUInt( 6, d.reserved                         , "reserved"                         );
            c.writeUInt( 9, d.program_clock_reference_extension, "program_clock_reference_extension");
            c.mark("pcr", d.getPCRValue());
        }

        if (d.opcr_flag.intValue() == 1) {
            c.writeUInt(33, d.original_program_clock_reference_base     , "original_program_clock_reference_base"     );
            c.writeUInt( 6, d.reserved2                                 , "reserved2"                                 );
            c.writeUInt( 9, d.original_program_clock_reference_extension, "original_program_clock_reference_extension");
            c.mark("opcr", d.getOPCRValue());
        }

        if (d.splicing_point_flag.intValue() == 1) {
            c.writeUInt( 8, d.splice_countdown, "splice_countdown");
        }

        if (d.transport_private_data_flag.intValue() == 1) {
            //プライベートデータの長さ
            c.writeUInt( 8, d.transport_private_data_length, "transport_private_data_length");

            //プライベートデータ
            if (d.transport_private_data_length.intValue() < 0
                    || TSPacket.PACKET_SIZE < d.transport_private_data_length.intValue()) {
                throw new IllegalStateException("private_data are too long"
                        + "(size: " + d.transport_private_data_length + ")");
            }
            c.writeBitList(d.transport_private_data_length.intValue() << 3,
                    d.private_data_byte, "transport_private_data_length");
        }

        if (d.adaptation_field_extension_flag.intValue() == 1) {
            //拡張ヘッダ
            writeExtensions(c, d);
        }

        //スタッフィングバイト
        //adaptation_field_length は
        //adaptation_field_length 自身（1バイト）の
        //長さを含んでいないため、1バイト加えて計算する
        size_st = d.adaptation_field_length.intValue() + 1
                - (int)((c.position() >>> 3) - pos_byte);
        if (size_st < 0) {
            throw new IllegalStateException("stuffing bytes have negative size"
                    + "(size: " + size_st  + ")");
        }
        c.writeBitList(size_st << 3, d.stuffing_byte, "stuffing_byte");

        c.leaveBlock();
    }

    protected static void writeExtensions(PacketWriter<?> c,
                                          TSPacketHeaderAdaptation d) {
        long pos_byte;
        int size_rs;

        pos_byte = (c.position() >>> 3);
        size_rs = 0;

        //フィールドの長さ
        c.writeUInt( 8, d.adaptation_field_extension_length, "adaptation_field_extension_length");
        if (d.adaptation_field_extension_length.intValue() < 0
                || TSPacket.PACKET_SIZE < d.adaptation_field_extension_length.intValue()) {
            throw new IllegalStateException("adapt header extension is too long"
                    + "(size: " + d.adaptation_field_extension_length + ")");
        }

        //各種フラグ
        c.writeUInt( 1, d.ltw_flag            , "ltw_flag"            );
        c.writeUInt( 1, d.piecewise_rate_flag , "piecewise_rate_flag" );
        c.writeUInt( 1, d.seamless_splice_flag, "seamless_splice_flag");
        c.writeUInt( 5, d.reserved3           , "reserved3"           );

        //フラグに対応するフィールド
        if (d.ltw_flag.intValue() == 1) {
            c.writeUInt( 1, d.ltw_valid_flag  , "ltw_valid_flag"  );
            c.writeUInt(15, d.ltw_offset      , "ltw_offset"      );
        }

        if (d.piecewise_rate_flag.intValue() == 1) {
            c.writeUInt( 2, d.reserved4       , "reserved4"       );
            c.writeUInt(22, d.piecewise_rate  , "piecewise_rate"  );
        }

        if (d.seamless_splice_flag.intValue() == 1) {
            c.writeUInt( 4, d.splice_type     , "splice_type"     );
            c.writeUInt( 3, d.dts_next_au_high, "dts_next_au_high");
            c.writeUInt( 1, d.marker_bit      , "marker_bit"      );
            c.writeUInt(15, d.dts_next_au_mid , "dts_next_au_mid" );
            c.writeUInt( 1, d.marker_bit2     , "marker_bit2"     );
            c.writeUInt(15, d.dts_next_au_low , "dts_next_au_low" );
            c.writeUInt( 1, d.marker_bit3     , "marker_bit3"     );
            c.mark("dts_next_au", d.getDTSNextAUValue());
        }

        //残りを埋めているバイト列
        //adaptation_field_extension_length は
        //adaptation_field_extension_length 自身（1バイト）の
        //長さを含んでいないため、1バイト加えて計算する
        size_rs = d.adaptation_field_extension_length.intValue() + 1
                - (int)((c.position() >>> 3) - pos_byte);
        if (size_rs < 0) {
            throw new IllegalStateException("reserved bytes(extension) have negative size"
                    + "(size: " + size_rs + ")");
        }
        c.writeBitList(size_rs << 3, d.reserved_byte, "reserved_byte");
    }

    public long getPCRValue() {
        return getPCRValue(
                program_clock_reference_base.longValue(),
                program_clock_reference_extension.longValue());
    }

    public long getOPCRValue() {
        return getPCRValue(
                original_program_clock_reference_base.longValue(),
                original_program_clock_reference_extension.longValue());
    }

    public long getDTSNextAUValue() {
        return getPTSValue(
                dts_next_au_high.longValue(),
                dts_next_au_mid.longValue(),
                dts_next_au_low.longValue());
    }

    public static long getPTSValue(long high, long mid, long low) {
        return ((high << 30) | (mid << 15) | (low << 0));
    }

    public static long getPCRValue(long base, long ext) {
        return (base * 300 + ext);
    }
}
