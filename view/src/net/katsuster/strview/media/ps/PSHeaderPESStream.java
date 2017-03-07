package net.katsuster.strview.media.ps;

import net.katsuster.strview.util.*;
import net.katsuster.strview.io.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * MPEG2 PES (Packetized Elementary Stream) header (has header)
 * </p>
 *
 * <p>
 * 参考規格
 * </p>
 * <ul>
 * <li>ITU-T H.222, ISO/IEC 13818-1:
 * Information technology – Generic coding of moving pictures and
 * associated audio information: Systems</li>
 * </ul>
 *
 * @author katsuhiro
 */
public class PSHeaderPESStream extends PSHeaderPES
        implements Cloneable {
    //pes_stream

    public UInt const_bit; //must be '0b10'
    public UInt pes_scrambling_control;
    public UInt pes_priority;
    public UInt data_alignment_indicator;
    public UInt copyright;
    public UInt original_or_copy;
    public UInt pts_dts_flags;
    public UInt escr_flag;
    public UInt es_rate_flag;
    public UInt dsm_trick_mode_flag;
    public UInt additional_copy_info_flag;
    public UInt pes_crc_flag;
    public UInt pes_extension_flag;
    public UInt pes_header_data_length;

    public UInt const_bit2; //must be '0b00xx' (xx = pts_dts_flags)
    public UInt marker_bit1;
    public UInt pts_high;
    public UInt marker_bit2;
    public UInt pts_mid;
    public UInt marker_bit3;
    public UInt pts_low;
    public UInt const_bit3; //must be '0b0001'
    public UInt marker_bit4;
    public UInt dts_high;
    public UInt marker_bit5;
    public UInt dts_mid;
    public UInt marker_bit6;
    public UInt dts_low;

    public UInt reserved;
    public UInt escr_base_high;
    public UInt marker_bit7;
    public UInt escr_base_mid;
    public UInt marker_bit8;
    public UInt escr_base_low;
    public UInt marker_bit9;
    public UInt escr_extension;
    public UInt marker_bit10;

    public UInt marker_bit11;
    public UInt es_rate;
    public UInt marker_bit12;

    public UInt trick_mode_control;
    public UInt field_id;
    public UInt intra_slice_refresh;
    public UInt frequency_truncation;
    public UInt rep_cntrl;
    public UInt reserved2;

    public UInt marker_bit13;
    public UInt additional_copy_info;

    public UInt previous_pes_packet_crc;

    public LargeBitList stuffing_byte;

    //pes_stream_ext

    public UInt pes_private_data_flag;
    public UInt pack_header_field_flag;
    public UInt program_packet_sequence_counter_flag;
    public UInt p_std_buffer_flag;
    public UInt reserved3;
    public UInt pes_extension_flag_2;

    public LargeBitList pes_private_data;

    public UInt pack_field_length;

    public UInt marker_bit14;
    public UInt program_packet_sequence_counter;
    public UInt marker_bit15;
    public UInt mpeg1_mpeg2_identifier;
    public UInt original_stuff_length;

    public UInt const_bit4; //must be '0b01'
    public UInt p_std_buffer_scale;
    public UInt p_std_buffer_size;

    public UInt marker_bit16;
    public UInt pes_extension_field_length;
    public UInt stream_id_extension_flag;
    public UInt stream_id_extension;

    public LargeBitList extension_field_data_byte;

    public PSHeaderPESStream() {
        //pes_stream
        const_bit = new UInt();
        pes_scrambling_control = new UInt();
        pes_priority = new UInt();
        data_alignment_indicator = new UInt();
        copyright = new UInt();
        original_or_copy = new UInt();
        pts_dts_flags = new UInt();
        escr_flag = new UInt();
        es_rate_flag = new UInt();
        dsm_trick_mode_flag = new UInt();
        additional_copy_info_flag = new UInt();
        pes_crc_flag = new UInt();
        pes_extension_flag = new UInt();
        pes_header_data_length = new UInt();

        const_bit2 = new UInt();
        marker_bit1 = new UInt();
        pts_high = new UInt();
        marker_bit2 = new UInt();
        pts_mid = new UInt();
        marker_bit3 = new UInt();
        pts_low = new UInt();
        const_bit3 = new UInt();
        marker_bit4 = new UInt();
        dts_high = new UInt();
        marker_bit5 = new UInt();
        dts_mid = new UInt();
        marker_bit6 = new UInt();
        dts_low = new UInt();

        reserved = new UInt();
        escr_base_high = new UInt();
        marker_bit7 = new UInt();
        escr_base_mid = new UInt();
        marker_bit8 = new UInt();
        escr_base_low = new UInt();
        marker_bit9 = new UInt();
        escr_extension = new UInt();
        marker_bit10 = new UInt();

        marker_bit11 = new UInt();
        es_rate = new UInt();
        marker_bit12 = new UInt();

        trick_mode_control = new UInt();
        field_id = new UInt();
        intra_slice_refresh = new UInt();
        frequency_truncation = new UInt();
        rep_cntrl = new UInt();
        reserved2 = new UInt();

        marker_bit13 = new UInt();
        additional_copy_info = new UInt();

        previous_pes_packet_crc = new UInt();

        stuffing_byte = new MemoryBitList();

        //pes_stream_ext
        pes_private_data_flag = new UInt();
        pack_header_field_flag = new UInt();
        program_packet_sequence_counter_flag = new UInt();
        p_std_buffer_flag = new UInt();
        reserved3 = new UInt();
        pes_extension_flag_2 = new UInt();

        pes_private_data = new MemoryBitList();

        pack_field_length = new UInt();

        marker_bit14 = new UInt();
        program_packet_sequence_counter = new UInt();
        marker_bit15 = new UInt();
        mpeg1_mpeg2_identifier = new UInt();
        original_stuff_length = new UInt();

        const_bit4 = new UInt();
        p_std_buffer_scale = new UInt();
        p_std_buffer_size = new UInt();

        marker_bit16 = new UInt();
        pes_extension_field_length = new UInt();
        stream_id_extension_flag = new UInt();
        stream_id_extension = new UInt();

        extension_field_data_byte = new MemoryBitList();
    }

    @Override
    public PSHeaderPESStream clone()
            throws CloneNotSupportedException {
        PSHeaderPESStream obj = (PSHeaderPESStream)super.clone();

        obj.const_bit = const_bit.clone();
        obj.pes_scrambling_control = pes_scrambling_control.clone();
        obj.pes_priority = pes_priority.clone();
        obj.data_alignment_indicator = data_alignment_indicator.clone();
        obj.copyright = copyright.clone();
        obj.original_or_copy = original_or_copy.clone();
        obj.pts_dts_flags = pts_dts_flags.clone();
        obj.escr_flag = escr_flag.clone();
        obj.es_rate_flag = es_rate_flag.clone();
        obj.dsm_trick_mode_flag = dsm_trick_mode_flag.clone();
        obj.additional_copy_info_flag = additional_copy_info_flag.clone();
        obj.pes_crc_flag = pes_crc_flag.clone();
        obj.pes_extension_flag = pes_extension_flag.clone();
        obj.pes_header_data_length = pes_header_data_length.clone();

        obj.const_bit2 = const_bit2.clone();
        obj.marker_bit1 = marker_bit1.clone();
        obj.pts_high = pts_high.clone();
        obj.marker_bit2 = marker_bit2.clone();
        obj.pts_mid = pts_mid.clone();
        obj.marker_bit3 = marker_bit3.clone();
        obj.pts_low = pts_low.clone();
        obj.const_bit3 = const_bit3.clone();
        obj.marker_bit4 = marker_bit4.clone();
        obj.dts_high = dts_high.clone();
        obj.marker_bit5 = marker_bit5.clone();
        obj.dts_mid = dts_mid.clone();
        obj.marker_bit6 = marker_bit6.clone();
        obj.dts_low = dts_low.clone();

        obj.reserved = reserved.clone();
        obj.escr_base_high = escr_base_high.clone();
        obj.marker_bit7 = marker_bit7.clone();
        obj.escr_base_mid = escr_base_mid.clone();
        obj.marker_bit8 = marker_bit8.clone();
        obj.escr_base_low = escr_base_low.clone();
        obj.marker_bit9 = marker_bit9.clone();
        obj.escr_extension = escr_extension.clone();
        obj.marker_bit10 = marker_bit10.clone();

        obj.marker_bit11 = marker_bit11.clone();
        obj.es_rate = es_rate.clone();
        obj.marker_bit12 = marker_bit12.clone();

        obj.trick_mode_control = trick_mode_control.clone();
        obj.field_id = field_id.clone();
        obj.intra_slice_refresh = intra_slice_refresh.clone();
        obj.frequency_truncation = frequency_truncation.clone();
        obj.rep_cntrl = rep_cntrl.clone();
        obj.reserved2 = reserved2.clone();

        obj.marker_bit13 = marker_bit13.clone();
        obj.additional_copy_info = additional_copy_info.clone();

        obj.previous_pes_packet_crc = previous_pes_packet_crc.clone();

        obj.stuffing_byte = (LargeBitList)stuffing_byte.clone();

        //pes_stream_ext
        obj.pes_private_data_flag = pes_private_data_flag.clone();
        obj.pack_header_field_flag = pack_header_field_flag.clone();
        obj.program_packet_sequence_counter_flag = program_packet_sequence_counter_flag.clone();
        obj.p_std_buffer_flag = p_std_buffer_flag.clone();
        obj.reserved3 = reserved3.clone();
        obj.pes_extension_flag_2 = pes_extension_flag_2.clone();

        obj.pes_private_data = (LargeBitList)pes_private_data.clone();

        obj.pack_field_length = pack_field_length.clone();

        obj.marker_bit14 = marker_bit14.clone();
        obj.program_packet_sequence_counter = program_packet_sequence_counter.clone();
        obj.marker_bit15 = marker_bit15.clone();
        obj.mpeg1_mpeg2_identifier = mpeg1_mpeg2_identifier.clone();
        obj.original_stuff_length = original_stuff_length.clone();

        obj.const_bit4 = const_bit4.clone(); //must be '0b01'
        obj.p_std_buffer_scale = p_std_buffer_scale.clone();
        obj.p_std_buffer_size = p_std_buffer_size.clone();

        obj.marker_bit16 = marker_bit16.clone();
        obj.pes_extension_field_length = pes_extension_field_length.clone();
        obj.stream_id_extension_flag = stream_id_extension_flag.clone();
        obj.stream_id_extension = stream_id_extension.clone();

        obj.extension_field_data_byte = (LargeBitList)extension_field_data_byte.clone();

        return obj;
    }

    @Override
    public void read(PacketReader<?> c) {
        read(c, this);
    }

    public static void read(PacketReader<?> c,
                            PSHeaderPESStream d) {
        c.enterBlock("PES stream header");

        PSHeaderPES.read(c, d);

        //00, 01, 11: MPEG1, 10: MPEG2
        d.const_bit = c.peekUInt( 2, d.const_bit);
        if (d.const_bit.intValue() != 2) {
            readMPEG1(c, d);
        } else {
            readMPEG2(c, d);
        }

        c.leaveBlock();
    }

    public static void readMPEG1(PacketReader<?> c,
                                 PSHeaderPESStream d) {
        long orgpos = c.position();
        while (c.readLong(8) == 0xff) {
            //Do nothing
        }
        int size_st = (int)(c.position() - orgpos - 8);
        c.position(orgpos);
        d.stuffing_byte = c.readBitList(size_st << 3, d.stuffing_byte);

        if (d.const_bit.intValue() == 1) {
            d.const_bit          = c.readUInt( 2, d.const_bit         );
            d.p_std_buffer_scale = c.readUInt( 1, d.p_std_buffer_scale);
            d.p_std_buffer_size  = c.readUInt(13, d.p_std_buffer_size );
        }

        //else   : 0b0000
        //Unknwon: 0b0001
        //PTS    : 0b0010
        //PTS+DTS: 0b0011
        d.const_bit2  = c.readUInt( 4, d.const_bit2 );
        if ((d.const_bit2.intValue() & 2) == 2) {
            d.pts_high    = c.readUInt( 3, d.pts_high   );
            d.marker_bit1 = c.readUInt( 1, d.marker_bit1);
            d.pts_mid     = c.readUInt(15, d.pts_mid    );
            d.marker_bit2 = c.readUInt( 1, d.marker_bit2);
            d.pts_low     = c.readUInt(15, d.pts_low    );
            d.marker_bit3 = c.readUInt( 1, d.marker_bit3);
            c.mark("PTS", d.getPTS());
        }

        if ((d.const_bit2.intValue() & 3) == 3) {
            d.const_bit3  = c.readUInt( 4, d.const_bit3 );
            d.dts_high    = c.readUInt( 3, d.dts_high   );
            d.marker_bit4 = c.readUInt( 1, d.marker_bit4);
            d.dts_mid     = c.readUInt(15, d.dts_mid    );
            d.marker_bit5 = c.readUInt( 1, d.marker_bit5);
            d.dts_low     = c.readUInt(15, d.dts_low    );
            d.marker_bit6 = c.readUInt( 1, d.marker_bit6);
            c.mark("DTS", d.getDTS());
        }

        if ((d.const_bit2.intValue() & 3) == 0) {
            d.const_bit3  = c.readUInt( 4, d.const_bit3 );
        }
    }

    public static void readMPEG2(PacketReader<?> c,
                                 PSHeaderPESStream d) {
        d.const_bit                 = c.readUInt( 2, d.const_bit                );
        d.pes_scrambling_control    = c.readUInt( 2, d.pes_scrambling_control   );
        d.pes_priority              = c.readUInt( 1, d.pes_priority             );
        d.data_alignment_indicator  = c.readUInt( 1, d.data_alignment_indicator );
        d.copyright                 = c.readUInt( 1, d.copyright                );
        d.original_or_copy          = c.readUInt( 1, d.original_or_copy         );
        d.pts_dts_flags             = c.readUInt( 2, d.pts_dts_flags            );
        d.escr_flag                 = c.readUInt( 1, d.escr_flag                );
        d.es_rate_flag              = c.readUInt( 1, d.es_rate_flag             );
        d.dsm_trick_mode_flag       = c.readUInt( 1, d.dsm_trick_mode_flag      );
        d.additional_copy_info_flag = c.readUInt( 1, d.additional_copy_info_flag);
        d.pes_crc_flag              = c.readUInt( 1, d.pes_crc_flag             );
        d.pes_extension_flag        = c.readUInt( 1, d.pes_extension_flag       );
        d.pes_header_data_length    = c.readUInt( 8, d.pes_header_data_length   );

        long pos_byte = (c.position() >>> 3);

        if ((d.pts_dts_flags.intValue() & 2) == 2) {
            d.const_bit2  = c.readUInt( 4, d.const_bit2 );
            d.pts_high    = c.readUInt( 3, d.pts_high   );
            d.marker_bit1 = c.readUInt( 1, d.marker_bit1);
            d.pts_mid     = c.readUInt(15, d.pts_mid    );
            d.marker_bit2 = c.readUInt( 1, d.marker_bit2);
            d.pts_low     = c.readUInt(15, d.pts_low    );
            d.marker_bit3 = c.readUInt( 1, d.marker_bit3);
            c.mark("PTS", d.getPTS());
        }

        if ((d.pts_dts_flags.intValue() & 3) == 3) {
            d.const_bit3  = c.readUInt( 4, d.const_bit3 );
            d.dts_high    = c.readUInt( 3, d.dts_high   );
            d.marker_bit4 = c.readUInt( 1, d.marker_bit4);
            d.dts_mid     = c.readUInt(15, d.dts_mid    );
            d.marker_bit5 = c.readUInt( 1, d.marker_bit5);
            d.dts_low     = c.readUInt(15, d.dts_low    );
            d.marker_bit6 = c.readUInt( 1, d.marker_bit6);
            c.mark("DTS", d.getDTS());
        }

        if (d.escr_flag.intValue() == 1) {
            d.reserved       = c.readUInt( 2, d.reserved      );
            d.escr_base_high = c.readUInt( 3, d.escr_base_high);
            d.marker_bit10   = c.readUInt( 1, d.marker_bit10  );
            d.escr_base_mid  = c.readUInt(15, d.escr_base_mid );
            d.marker_bit11   = c.readUInt( 1, d.marker_bit11  );
            d.escr_base_low  = c.readUInt(15, d.escr_base_low );
            d.marker_bit12   = c.readUInt( 1, d.marker_bit12  );
            d.escr_extension = c.readUInt( 9, d.escr_extension);
            d.marker_bit13   = c.readUInt( 1, d.marker_bit13  );
            c.mark("ESCR", d.getESCR());
        }

        if (d.es_rate_flag.intValue() == 1) {
            d.marker_bit14 = c.readUInt( 1, d.marker_bit14);
            d.es_rate      = c.readUInt(22, d.es_rate     );
            d.marker_bit15 = c.readUInt( 1, d.marker_bit15);
        }

        if (d.dsm_trick_mode_flag.intValue() == 1) {
            d.trick_mode_control = c.readUInt( 3, d.trick_mode_control);

            switch(d.trick_mode_control.intValue()) {
            case TRICK_MODE.FAST_F:
                d.field_id             = c.readUInt( 2, d.field_id            );
                d.intra_slice_refresh  = c.readUInt( 1, d.intra_slice_refresh );
                d.frequency_truncation = c.readUInt( 2, d.frequency_truncation);
                break;
            case TRICK_MODE.SLOW_F:
                d.rep_cntrl            = c.readUInt( 5, d.rep_cntrl           );
                break;
            case TRICK_MODE.FREEZE:
                d.field_id             = c.readUInt( 2, d.field_id            );
                d.reserved             = c.readUInt( 3, d.reserved            );
                break;
            case TRICK_MODE.FAST_R:
                d.field_id             = c.readUInt( 2, d.field_id            );
                d.intra_slice_refresh  = c.readUInt( 1, d.intra_slice_refresh );
                d.frequency_truncation = c.readUInt( 2, d.frequency_truncation);
                break;
            case TRICK_MODE.SLOW_R:
                d.rep_cntrl            = c.readUInt( 5, d.rep_cntrl           );
                break;
            default:
                d.reserved3            = c.readUInt( 5, d.reserved3           );
                break;
            }
        }

        if (d.additional_copy_info_flag.intValue() == 1) {
            d.marker_bit16         = c.readUInt( 1, d.marker_bit16        );
            d.additional_copy_info = c.readUInt( 7, d.additional_copy_info);
        }

        if (d.pes_crc_flag.intValue() == 1) {
            d.previous_pes_packet_crc = c.readUInt(16, d.previous_pes_packet_crc);
        }

        //拡張ヘッダを読む
        if (d.pes_extension_flag.intValue() == 1) {
            readStreamExt(c, d);
        }

        //pes_header_data_length から stuffing bytes の長さを得る
        int size_st = d.pes_header_data_length.intValue()
                - (int)((c.position() >> 3) - pos_byte);
        if (size_st < 0) {
            throw new IllegalStateException("stuffing bytes have negative size"
                    + "(size: "  + size_st + "). "
                    + "pes_header: " + d.pes_header_data_length
                    + "read_bytes: "  + ((c.position() >> 3) - pos_byte)
                    + ".");
        }
        if (size_st > 32) {
            throw new IllegalStateException("stuffing bytes is too long"
                    + "(size: " + size_st + " > 32)");
        }
        d.stuffing_byte = c.readBitList(size_st << 3, d.stuffing_byte);
    }

    protected static void readStreamExt(PacketReader<?> c,
                                        PSHeaderPESStream d) {
        d.pes_private_data_flag      = c.readUInt( 1, d.pes_private_data_flag     );
        d.pack_header_field_flag     = c.readUInt( 1, d.pack_header_field_flag    );
        d.program_packet_sequence_counter_flag = c.readUInt( 1, d.program_packet_sequence_counter_flag);
        d.p_std_buffer_flag          = c.readUInt( 1, d.p_std_buffer_flag         );
        d.reserved3                  = c.readUInt( 3, d.reserved3                 );
        d.pes_extension_flag_2       = c.readUInt( 1, d.pes_extension_flag_2      );

        if (d.pes_private_data_flag.intValue() == 1) {
            d.pes_private_data = c.readBitList(128, d.pes_private_data);
        }

        if (d.pack_header_field_flag.intValue() == 1) {
            d.pack_field_length = c.readUInt( 8, d.pack_field_length);

            //TODO: implemented yet
            ////pack_header()
            throw new UnsupportedOperationException(
                    "read pack_header() is not implemented.");
        }

        if (d.program_packet_sequence_counter_flag.intValue() == 1) {
            d.marker_bit14           = c.readUInt( 1, d.marker_bit14          );
            d.program_packet_sequence_counter = c.readUInt( 7, d.program_packet_sequence_counter);
            d.marker_bit15           = c.readUInt( 1, d.marker_bit15          );
            d.mpeg1_mpeg2_identifier = c.readUInt( 1, d.mpeg1_mpeg2_identifier);
            d.original_stuff_length  = c.readUInt( 6, d.original_stuff_length );
        }

        if (d.p_std_buffer_flag.intValue() == 1) {
            d.const_bit4         = c.readUInt( 2, d.const_bit4        );
            d.p_std_buffer_scale = c.readUInt( 1, d.p_std_buffer_scale);
            d.p_std_buffer_size  = c.readUInt(13, d.p_std_buffer_size );
        }

        if (d.pes_extension_flag_2.intValue() == 1) {
            d.marker_bit16               = c.readUInt( 1, d.marker_bit16              );
            d.pes_extension_field_length = c.readUInt( 7, d.pes_extension_field_length);
            d.stream_id_extension_flag   = c.readUInt( 1, d.stream_id_extension_flag  );

            if (d.stream_id_extension_flag.intValue() == 0) {
                d.stream_id_extension = c.readUInt( 7, d.stream_id_extension);

                int size_ef = d.pes_extension_field_length.intValue();
                if (size_ef < 0) {
                    throw new IllegalStateException("pes_extension_field has "
                            + "negative size(size: " + size_ef + ").");
                }
                d.extension_field_data_byte = c.readBitList(size_ef << 3, d.extension_field_data_byte);
            }
        }
    }

    @Override
    public void write(PacketWriter<?> c) {
        write(c, this);
    }

    public static void write(PacketWriter<?> c,
                             PSHeaderPESStream d) {
        c.enterBlock("PES stream header");

        PSHeaderPES.write(c, d);

        //MPEG1  : 0b00, 0b01, 0b11
        //MPEG2  : 0b10
        if (d.const_bit.intValue() != 2) {
            writeMPEG1(c, d);
        } else {
            writeMPEG2(c, d);
        }

        c.leaveBlock();
    }

    public static void writeMPEG1(PacketWriter<?> c,
                                  PSHeaderPESStream d) {
        c.writeBitList((int)d.stuffing_byte.length(), d.stuffing_byte, "stuffing_byte");

        if (d.const_bit.intValue() == 1) {
            c.writeUInt( 2, d.const_bit         , "const_bit"       );
            c.writeUInt( 1, d.p_std_buffer_scale, "STD_buffer_scale");
            c.writeUInt(13, d.p_std_buffer_size , "STD_buffer_size" );
        }

        //else   : 0b0000
        //Unknwon: 0b0001
        //PTS    : 0b0010
        //PTS+DTS: 0b0011
        c.writeUInt( 4, d.const_bit2 , "const_bit2" );
        if ((d.const_bit2.intValue() & 2) == 2) {
            c.writeUInt( 3, d.pts_high   , "PTS_high"   );
            c.writeUInt( 1, d.marker_bit1, "marker_bit1");
            c.writeUInt(15, d.pts_mid    , "PTS_mid"    );
            c.writeUInt( 1, d.marker_bit2, "marker_bit2");
            c.writeUInt(15, d.pts_low    , "PTS_low"    );
            c.writeUInt( 1, d.marker_bit3, "marker_bit3");
            c.mark("PTS", d.getPTS());
        }

        if ((d.const_bit2.intValue() & 3) == 3) {
            c.writeUInt( 4, d.const_bit3 , "const_bit3" );
            c.writeUInt( 3, d.dts_high   , "DTS_high"   );
            c.writeUInt( 1, d.marker_bit4, "marker_bit4");
            c.writeUInt(15, d.dts_mid    , "DTS_mid"    );
            c.writeUInt( 1, d.marker_bit5, "marker_bit5");
            c.writeUInt(15, d.dts_low    , "DTS_low"    );
            c.writeUInt( 1, d.marker_bit6, "marker_bit6");
            c.mark("DTS", d.getDTS());
        }

        if ((d.const_bit2.intValue() & 3) == 0) {
            c.writeUInt( 4, d.const_bit3 , "const_bit3" );
        }
    }

    public static void writeMPEG2(PacketWriter<?> c,
                                  PSHeaderPESStream d) {
        c.writeUInt( 2, d.const_bit                , "const_bit"                );
        c.writeUInt( 2, d.pes_scrambling_control   , "PES_scrambling_control"   );
        c.writeUInt( 1, d.pes_priority             , "PES_priority"             );
        c.writeUInt( 1, d.data_alignment_indicator , "data_alignment_indicator" );
        c.writeUInt( 1, d.copyright                , "copyright"                );
        c.writeUInt( 1, d.original_or_copy         , "original_or_copy"         );
        c.writeUInt( 2, d.pts_dts_flags            , "PTS_DTS_flags"            );
        c.writeUInt( 1, d.escr_flag                , "ESCR_flag"                );
        c.writeUInt( 1, d.es_rate_flag             , "ES_rate_flag"             );
        c.writeUInt( 1, d.dsm_trick_mode_flag      , "DSM_trick_mode_flag"      );
        c.writeUInt( 1, d.additional_copy_info_flag, "additional_copy_info_flag");
        c.writeUInt( 1, d.pes_crc_flag             , "PES_CRC_flag"             );
        c.writeUInt( 1, d.pes_extension_flag       , "PES_extension_flag"       );
        c.writeUInt( 8, d.pes_header_data_length   , "PES_header_data_length"   );

        if ((d.pts_dts_flags.intValue() & 2) == 2) {
            c.writeUInt( 4, d.const_bit2 , "const_bit2" );
            c.writeUInt( 3, d.pts_high   , "PTS_high"   );
            c.writeUInt( 1, d.marker_bit1, "marker_bit1");
            c.writeUInt(15, d.pts_mid    , "PTS_mid"    );
            c.writeUInt( 1, d.marker_bit2, "marker_bit2");
            c.writeUInt(15, d.pts_low    , "PTS_low"    );
            c.writeUInt( 1, d.marker_bit3, "marker_bit3");
            c.mark("PTS", d.getPTS());
        }

        if ((d.pts_dts_flags.intValue() & 3) == 3) {
            c.writeUInt( 4, d.const_bit3 , "const_bit3" );
            c.writeUInt( 3, d.dts_high   , "DTS_high"   );
            c.writeUInt( 1, d.marker_bit4, "marker_bit4");
            c.writeUInt(15, d.dts_mid    , "DTS_mid"    );
            c.writeUInt( 1, d.marker_bit5, "marker_bit5");
            c.writeUInt(15, d.dts_low    , "DTS_low"    );
            c.writeUInt( 1, d.marker_bit6, "marker_bit6");
            c.mark("DTS", d.getDTS());
        }

        if (d.escr_flag.intValue() == 1) {
            c.writeUInt( 2, d.reserved      , "reserved"      );
            c.writeUInt( 3, d.escr_base_high, "ESCR_base_high");
            c.writeUInt( 1, d.marker_bit10  , "marker_bit10"  );
            c.writeUInt(15, d.escr_base_mid , "ESCR_base_mid" );
            c.writeUInt( 1, d.marker_bit11  , "marker_bit11"  );
            c.writeUInt(15, d.escr_base_low , "ESCR_base_low" );
            c.writeUInt( 1, d.marker_bit12  , "marker_bit12"  );
            c.writeUInt( 9, d.escr_extension, "ESCR_extension");
            c.writeUInt( 1, d.marker_bit13  , "marker_bit13"  );
            c.mark("ESCR", d.getESCR());
        }

        if (d.es_rate_flag.intValue() == 1) {
            c.writeUInt( 1, d.marker_bit14, "marker_bit14");
            c.writeUInt(22, d.es_rate     , "ES_rate"     );
            c.writeUInt( 1, d.marker_bit15, "marker_bit15");
        }

        if (d.dsm_trick_mode_flag.intValue() == 1) {
            c.writeUInt( 3, d.trick_mode_control, "trick_mode_control", d.getTrickModeName());

            switch(d.trick_mode_control.intValue()) {
            case TRICK_MODE.FAST_F:
                c.writeUInt( 2, d.field_id            , "field_id"            );
                c.writeUInt( 1, d.intra_slice_refresh , "intra_slice_refresh" );
                c.writeUInt( 2, d.frequency_truncation, "frequency_truncation");
                break;
            case TRICK_MODE.SLOW_F:
                c.writeUInt( 5, d.rep_cntrl           , "rep_cntrl"           );
                break;
            case TRICK_MODE.FREEZE:
                c.writeUInt( 2, d.field_id            , "field_id"            );
                c.writeUInt( 3, d.reserved            , "reserved"            );
                break;
            case TRICK_MODE.FAST_R:
                c.writeUInt( 2, d.field_id            , "field_id"            );
                c.writeUInt( 1, d.intra_slice_refresh , "intra_slice_refresh" );
                c.writeUInt( 2, d.frequency_truncation, "frequency_truncation");
                break;
            case TRICK_MODE.SLOW_R:
                c.writeUInt( 5, d.rep_cntrl           , "rep_cntrl"           );
                break;
            default:
                c.writeUInt( 5, d.reserved3           , "reserved3"           );
                break;
            }
        }

        if (d.additional_copy_info_flag.intValue() == 1) {
            c.writeUInt( 1, d.marker_bit16        , "marker_bit16"        );
            c.writeUInt( 7, d.additional_copy_info, "additional_copy_info");
        }

        if (d.pes_crc_flag.intValue() == 1) {
            c.writeUInt(16, d.previous_pes_packet_crc, "previous_PES_packet_CRC");
        }

        //拡張ヘッダを書く
        if (d.pes_extension_flag.intValue() == 1) {
            writeStreamExt(c, d);
        }

        c.writeBitList((int)d.stuffing_byte.length(), d.stuffing_byte, "stuffing_byte");
    }

    protected static void writeStreamExt(PacketWriter<?> c,
                                         PSHeaderPESStream d) {
        c.writeUInt( 1, d.pes_private_data_flag , "PES_private_data_flag" );
        c.writeUInt( 1, d.pack_header_field_flag, "pack_header_field_flag");
        c.writeUInt( 1, d.program_packet_sequence_counter_flag, "program_packet_sequence_counter_flag");
        c.writeUInt( 1, d.p_std_buffer_flag     , "P-STD_buffer_flag"     );
        c.writeUInt( 3, d.reserved3             , "reserved3"             );
        c.writeUInt( 1, d.pes_extension_flag_2  , "PES_extension_flag_2"  );

        if (d.pes_private_data_flag.intValue() == 1) {
            c.writeBitList(128, d.pes_private_data, "PES_private_data");
        }

        if (d.pack_header_field_flag.intValue() == 1) {
            c.writeUInt( 8, d.pack_field_length, "pack_field_length");

            //TODO: implemented yet
            ////pack_header()
            throw new UnsupportedOperationException(
                    "write pack_header() is not implemented.");
        }

        if (d.program_packet_sequence_counter_flag.intValue() == 1) {
            c.writeUInt( 1, d.marker_bit14          , "marker_bit14"          );
            c.writeUInt( 7, d.program_packet_sequence_counter, "program_packet_sequence_counter");
            c.writeUInt( 1, d.marker_bit15          , "marker_bit15"          );
            c.writeUInt( 1, d.mpeg1_mpeg2_identifier, "MPEG1_MPEG2_identifier");
            c.writeUInt( 6, d.original_stuff_length , "original_stuff_length" );
        }

        if (d.p_std_buffer_flag.intValue() == 1) {
            c.writeUInt( 2, d.const_bit4        , "const_bit4"        );
            c.writeUInt( 1, d.p_std_buffer_scale, "P-STD_buffer_scale");
            c.writeUInt(13, d.p_std_buffer_size , "P-STD_buffer_size" );
        }

        if (d.pes_extension_flag_2.intValue() == 1) {
            c.writeUInt( 1, d.marker_bit16              , "marker_bit16"              );
            c.writeUInt( 7, d.pes_extension_field_length, "PES_extension_field_length");
            c.writeUInt( 1, d.stream_id_extension_flag  , "stream_id_extension_flag"  );

            if (d.stream_id_extension_flag.intValue() == 0) {
                c.writeUInt( 7, d.stream_id_extension, "stream_id_extension");
                c.writeBitList(d.pes_extension_field_length.intValue() << 3,
                        d.extension_field_data_byte, "extension_field_data_byte");
            }
        }
    }

    public long getPTS() {
        return getPTSValue(
                pts_high.longValue(),
                pts_mid.longValue(),
                pts_low.longValue());
    }

    public long getDTS() {
        return getPTSValue(
                dts_high.longValue(),
                dts_mid.longValue(),
                dts_low.longValue());
    }

    public long getESCR() {
        long escr_base;

        escr_base = getPTSValue(
                escr_base_high.longValue(),
                escr_base_mid.longValue(),
                escr_base_low.longValue());

        return getPCRValue(
                escr_base,
                escr_extension.intValue());
    }

    public static long getPTSValue(long high, long mid, long low) {
        return ((high << 30) | (mid << 15) | (low << 0));
    }

    public static long getPCRValue(long base, long ext) {
        return (base * 300 + ext);
    }

    public String getTrickModeName() {
        return getTrickModeName(trick_mode_control.intValue());
    }

    public static String getTrickModeName(int id) {
        String name = "..unknown..";

        //ISO 13818-1
        switch (id) {
        case TRICK_MODE.FAST_F:
            name = "[ISO 13818-1] fast forward";
            break;
        case TRICK_MODE.SLOW_F:
            name = "[ISO 13818-1] slow motion";
            break;
        case TRICK_MODE.FREEZE:
            name = "[ISO 13818-1] freeze frame";
            break;
        case TRICK_MODE.FAST_R:
            name = "[ISO 13818-1] fast reverse";
            break;
        case TRICK_MODE.SLOW_R:
            name = "[ISO 13818-1] slow reverse";
            break;
        default:
            //do nothing
            break;
        }

        if (TRICK_MODE.RESERVED_START <= id
                && id <= TRICK_MODE.RESERVED_END) {
            name = "[ISO 13818-1] reserved";
        }

        return name;
    }

    public static class TRICK_MODE {
        //[ISO 13818-1] fast forward
        public static final int FAST_F = 0x0;
        //[ISO 13818-1] slow motion
        public static final int SLOW_F = 0x1;
        //[ISO 13818-1] freeze frame
        public static final int FREEZE = 0x2;
        //[ISO 13818-1] fast reverse
        public static final int FAST_R = 0x3;
        //[ISO 13818-1] slow reverse
        public static final int SLOW_R = 0x4;

        //[ISO 13818-1] reserved
        public static final int RESERVED_START = 0x5;
        //[ISO 13818-1] reserved
        public static final int RESERVED_END   = 0x7;
    }
}
