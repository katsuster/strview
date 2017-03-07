package net.katsuster.strview.media.m2v;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * MPEG2 Video picture coding extension
 * </p>
 *
 * <p>
 * 参考規格
 * </p>
 * <ul>
 * <li>ITU-T H.262, ISO/IEC 13818-2:
 * Information technology - Generic coding of moving pictures and
 * associated audio information: Video</li>
 * </ul>
 *
 * @author katsuhiro
 */
public class M2VHeaderExtPictureCoding extends M2VHeaderExt
        implements Cloneable {
    public UInt[][] f_code;
    public UInt intra_dc_precision;
    public UInt picture_structure;
    public UInt top_field_first;
    public UInt frame_pred_frame_dct;
    public UInt concealment_motion_vectors;
    public UInt q_scale_type;
    public UInt intra_vlc_format;
    public UInt alternate_scan;
    public UInt repeat_first_field;
    public UInt chroma_420_type;
    public UInt progressive_frame;
    public UInt composite_display_flag;

    public UInt v_axis;
    public UInt field_sequence;
    public UInt sub_carrier;
    public UInt burst_amplitude;
    public UInt sub_carrier_phase;

    public M2VHeaderExtPictureCoding() {
        f_code = new UInt[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                f_code[i][j] = new UInt();
            }
        }
        intra_dc_precision = new UInt();
        picture_structure = new UInt();
        top_field_first = new UInt();
        frame_pred_frame_dct = new UInt();
        concealment_motion_vectors = new UInt();
        q_scale_type = new UInt();
        intra_vlc_format = new UInt();
        alternate_scan = new UInt();
        repeat_first_field = new UInt();
        chroma_420_type = new UInt();
        progressive_frame = new UInt();
        composite_display_flag = new UInt();

        v_axis = new UInt();
        field_sequence = new UInt();
        sub_carrier = new UInt();
        burst_amplitude = new UInt();
        sub_carrier_phase = new UInt();
    }

    @Override
    public M2VHeaderExtPictureCoding clone()
            throws CloneNotSupportedException {
        M2VHeaderExtPictureCoding obj = (M2VHeaderExtPictureCoding)super.clone();

        obj.f_code = f_code.clone();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                obj.f_code[i][j] = (UInt)f_code[i][j].clone();
            }
        }
        obj.intra_dc_precision = (UInt)intra_dc_precision.clone();
        obj.picture_structure = (UInt)picture_structure.clone();
        obj.top_field_first = (UInt)top_field_first.clone();
        obj.frame_pred_frame_dct = (UInt)frame_pred_frame_dct.clone();
        obj.concealment_motion_vectors = (UInt)concealment_motion_vectors.clone();
        obj.q_scale_type = (UInt)q_scale_type.clone();
        obj.intra_vlc_format = (UInt)intra_vlc_format.clone();
        obj.alternate_scan = (UInt)alternate_scan.clone();
        obj.repeat_first_field = (UInt)repeat_first_field.clone();
        obj.chroma_420_type = (UInt)chroma_420_type.clone();
        obj.progressive_frame = (UInt)progressive_frame.clone();
        obj.composite_display_flag = (UInt)composite_display_flag.clone();

        obj.v_axis = (UInt)v_axis.clone();
        obj.field_sequence = (UInt)field_sequence.clone();
        obj.sub_carrier = (UInt)sub_carrier.clone();
        obj.burst_amplitude = (UInt)burst_amplitude.clone();
        obj.sub_carrier_phase = (UInt)sub_carrier_phase.clone();

        return obj;
    }

    @Override
    public void read(PacketReader<?> c) {
        read(c, this);
    }

    public static void read(PacketReader<?> c,
                            M2VHeaderExtPictureCoding d) {
        c.enterBlock("M2V picture_coding_extension()");

        M2VHeaderExt.read(c, d);

        d.f_code[0][0]               = c.readUInt( 4, d.f_code[0][0]              );
        d.f_code[0][1]               = c.readUInt( 4, d.f_code[0][1]              );
        d.f_code[1][0]               = c.readUInt( 4, d.f_code[1][0]              );
        d.f_code[1][1]               = c.readUInt( 4, d.f_code[1][1]              );
        d.intra_dc_precision         = c.readUInt( 2, d.intra_dc_precision        );
        d.picture_structure          = c.readUInt( 2, d.picture_structure         );
        d.top_field_first            = c.readUInt( 1, d.top_field_first           );
        d.frame_pred_frame_dct       = c.readUInt( 1, d.frame_pred_frame_dct      );
        d.concealment_motion_vectors = c.readUInt( 1, d.concealment_motion_vectors);
        d.q_scale_type               = c.readUInt( 1, d.q_scale_type              );
        d.intra_vlc_format           = c.readUInt( 1, d.intra_vlc_format          );
        d.alternate_scan             = c.readUInt( 1, d.alternate_scan            );
        d.repeat_first_field         = c.readUInt( 1, d.repeat_first_field        );
        d.chroma_420_type            = c.readUInt( 1, d.chroma_420_type           );
        d.progressive_frame          = c.readUInt( 1, d.progressive_frame         );
        d.composite_display_flag     = c.readUInt( 1, d.composite_display_flag    );

        if (d.composite_display_flag.intValue() == 1) {
            d.v_axis            = c.readUInt( 1, d.v_axis           );
            d.field_sequence    = c.readUInt( 3, d.field_sequence   );
            d.sub_carrier       = c.readUInt( 1, d.sub_carrier      );
            d.burst_amplitude   = c.readUInt( 7, d.burst_amplitude  );
            d.sub_carrier_phase = c.readUInt( 8, d.sub_carrier_phase);
        }

        c.leaveBlock();
    }

    @Override
    public void write(PacketWriter<?> c) {
        write(c, this);
    }

    public static void write(PacketWriter<?> c,
                             M2VHeaderExtPictureCoding d) {
        c.enterBlock("M2V picture_coding_extension()");

        M2VHeaderExt.write(c, d);

        c.writeUInt( 4, d.f_code[0][0]              , "f_code[0][0]"              );
        c.writeUInt( 4, d.f_code[0][1]              , "f_code[0][1]"              );
        c.writeUInt( 4, d.f_code[1][0]              , "f_code[1][0]"              );
        c.writeUInt( 4, d.f_code[1][1]              , "f_code[1][1]"              );
        c.writeUInt( 2, d.intra_dc_precision        , "intra_dc_precision"        , d.getIntraDCPrecisionName());
        c.writeUInt( 2, d.picture_structure         , "picture_structure"         , d.getPictureStructureName());
        c.writeUInt( 1, d.top_field_first           , "top_field_first"           );
        c.writeUInt( 1, d.frame_pred_frame_dct      , "frame_pred_frame_dct"      );
        c.writeUInt( 1, d.concealment_motion_vectors, "concealment_motion_vectors");
        c.writeUInt( 1, d.q_scale_type              , "q_scale_type"              );
        c.writeUInt( 1, d.intra_vlc_format          , "intra_vlc_format"          );
        c.writeUInt( 1, d.alternate_scan            , "alternate_scan"            );
        c.writeUInt( 1, d.repeat_first_field        , "repeat_first_field"        );
        c.writeUInt( 1, d.chroma_420_type           , "chroma_420_type"           );
        c.writeUInt( 1, d.progressive_frame         , "progressive_frame"         );
        c.writeUInt( 1, d.composite_display_flag    , "composite_display_flag"    );

        if (d.composite_display_flag.intValue() == 1) {
            c.writeUInt( 1, d.v_axis                    , "v_axis"                    );
            c.writeUInt( 3, d.field_sequence            , "field_sequence"            );
            c.writeUInt( 1, d.sub_carrier               , "sub_carrier"               );
            c.writeUInt( 7, d.burst_amplitude           , "burst_amplitude"           );
            c.writeUInt( 8, d.sub_carrier_phase         , "sub_carrier_phase"         );
        }

        c.leaveBlock();
    }

    public String getIntraDCPrecisionName() {
        return M2VConsts.getIntraDCPrecisionName(intra_dc_precision.intValue());
    }

    public String getPictureStructureName() {
        return M2VConsts.getPictureStructureName(picture_structure.intValue());
    }
}
