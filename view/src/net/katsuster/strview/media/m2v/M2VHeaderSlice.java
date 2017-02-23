package net.katsuster.strview.media.m2v;

import java.util.*;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;
import net.katsuster.strview.media.m2v.M2VConsts.*;

/**
 * <p>
 * MPEG2 Video slice
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
public class M2VHeaderSlice extends M2VHeader
        implements Cloneable {
    public UInt slice_vertical_position_extension;
    public UInt priority_breakpoint;
    public UInt quantiser_scale_code;
    public UInt slice_extension_flag;
    public UInt intra_slice;
    public UInt slice_picture_id_enable;
    public UInt slice_picture_id;
    public UInt extra_bit_slice_1;
    public UInt extra_information_slice;
    public UInt extra_bit_slice_0;

    private Map.Entry<Long, M2VHeaderSequence> entSeq;
    private M2VHeaderSequence seq = null;
    private Map.Entry<Long, M2VHeaderExtSequence> entEseq;
    private M2VHeaderExtSequence eseq = null;
    private Map.Entry<Long, M2VHeaderExtSequenceScalable> entEseqsca;
    private M2VHeaderExtSequenceScalable eseqsca = null;
    private int horizontal_size;
    private int vertical_size;

    public M2VHeaderSlice(Map.Entry<Long, M2VHeaderSequence> es) {
        this(es, null, null);
    }

    public M2VHeaderSlice(Map.Entry<Long, M2VHeaderSequence> es,
                          Map.Entry<Long, M2VHeaderExtSequence> ees) {
        this(es, ees, null);
    }

    public M2VHeaderSlice(Map.Entry<Long, M2VHeaderSequence> es,
                          Map.Entry<Long, M2VHeaderExtSequence> ees,
                          Map.Entry<Long, M2VHeaderExtSequenceScalable> eesc) {
        slice_vertical_position_extension = new UInt();
        priority_breakpoint = new UInt();
        quantiser_scale_code = new UInt();
        slice_extension_flag = new UInt();
        intra_slice = new UInt();
        slice_picture_id_enable = new UInt();
        slice_picture_id = new UInt();
        extra_bit_slice_1 = new UInt();
        extra_information_slice = new UInt();
        extra_bit_slice_0 = new UInt();

        entSeq = es;
        entEseq = ees;
        entEseqsca = eesc;
        //Cannot use null
        seq = entSeq.getValue();
        if (entEseq != null) {
            eseq = entEseq.getValue();
        }
        if (entEseqsca != null) {
            eseqsca = entEseqsca.getValue();
        }

        horizontal_size = getHorizontalSize(seq, eseq);
        vertical_size = getVerticalSize(seq, eseq);
    }

    @Override
    public M2VHeaderSlice clone()
            throws CloneNotSupportedException {
        M2VHeaderSlice obj = (M2VHeaderSlice)super.clone();

        obj.slice_vertical_position_extension = slice_vertical_position_extension.clone();
        obj.priority_breakpoint = priority_breakpoint.clone();
        obj.quantiser_scale_code = quantiser_scale_code.clone();
        obj.slice_extension_flag = slice_extension_flag.clone();
        obj.intra_slice = intra_slice.clone();
        obj.slice_picture_id_enable = slice_picture_id_enable.clone();
        obj.slice_picture_id = slice_picture_id.clone();
        obj.extra_bit_slice_1 = extra_bit_slice_1.clone();
        obj.extra_information_slice = extra_information_slice.clone();
        obj.extra_bit_slice_0 = extra_bit_slice_0.clone();

        return obj;
    }

    @Override
    public void read(PacketReader<?> c) {
        read(c, this);
    }

    public static void read(PacketReader<?> c,
                            M2VHeaderSlice d) {
        c.enterBlock("M2V slice()");

        M2VHeader.read(c, d);

        c.mark("sequence_header No", d.entSeq.getKey());
        if (d.eseq != null) {
            c.mark("sequence_extension No", d.entEseq.getKey());
        }
        if (d.eseqsca != null) {
            c.mark("sequence_scalable_extension No", d.entEseqsca.getKey());
        }

        if (d.getVerticalSize() > 2800) {
            d.slice_vertical_position_extension = c.readUInt( 3, d.slice_vertical_position_extension);
        }

        if (d.eseqsca != null) {
            if (d.eseqsca.scalable_mode.intValue() == SCALABLE_MODE.DATA_PARTITIONING) {
                d.priority_breakpoint = c.readUInt( 7, d.priority_breakpoint);
            }
        }

        d.quantiser_scale_code        = c.readUInt( 5, d.quantiser_scale_code   );
        if (c.peekLong(1) == 1) {
            d.slice_extension_flag    = c.readUInt( 1, d.slice_extension_flag   );
            d.intra_slice             = c.readUInt( 1, d.intra_slice            );
            d.slice_picture_id_enable = c.readUInt( 1, d.slice_picture_id_enable);
            d.slice_picture_id        = c.readUInt( 6, d.slice_picture_id       );
            while (c.peekLong(1) == 1) {
                //TODO: extra_information_slice is ignored
                d.extra_bit_slice_1       = c.readUInt( 1, d.extra_bit_slice_1      );
                d.extra_information_slice = c.readUInt( 8, d.extra_information_slice);
            }
        }
        d.extra_bit_slice_0 = c.readUInt( 1, d.extra_bit_slice_0);

        c.leaveBlock();
    }

    @Override
    public void write(PacketWriter<?> c) {
        write(c, this);
    }

    public static void write(PacketWriter<?> c,
                             M2VHeaderSlice d) {
        c.enterBlock("M2V slice()");

        M2VHeader.write(c, d);

        c.mark("sequence_header No", d.entSeq.getKey());
        if (d.eseq != null) {
            c.mark("sequence_extension No", d.entEseq.getKey());
        }
        if (d.eseqsca != null) {
            c.mark("sequence_scalable_extension No", d.entEseqsca.getKey());
        }

        if (d.getVerticalSize() > 2800) {
            c.writeUInt( 3, d.slice_vertical_position_extension, "slice_vertical_position_extension");
        }

        if (d.eseqsca != null) {
            if (d.eseqsca.scalable_mode.intValue() == SCALABLE_MODE.DATA_PARTITIONING) {
                c.writeUInt( 7, d.priority_breakpoint, "priority_breakpoint");
            }
        }

        c.writeUInt( 5, d.quantiser_scale_code, "quantiser_scale_code");
        if (d.slice_extension_flag.intValue() == 1) {
            c.writeUInt( 1, d.slice_extension_flag   , "slice_extension_flag"   );
            c.writeUInt( 1, d.intra_slice            , "intra_slice"            );
            c.writeUInt( 1, d.slice_picture_id_enable, "slice_picture_id_enable");
            c.writeUInt( 6, d.slice_picture_id       , "slice_picture_id"       );
            while (d.extra_bit_slice_1.intValue() == 1) {
                //TODO: extra_information_slice is ignored
                c.writeUInt( 1, d.extra_bit_slice_1      , "extra_bit_slice_1"      );
                c.writeUInt( 8, d.extra_information_slice, "extra_information_slice");
            }
        }
        c.writeUInt( 1, d.extra_bit_slice_0, "extra_bit_slice_0");

        c.leaveBlock();
    }

    public int getHorizontalSize() {
        return horizontal_size;
    }

    public static int getHorizontalSize(M2VHeaderSequence s, M2VHeaderExtSequence es) {
        int v = s.vertical_size_value.intValue();
        if (es != null) {
            v |= (es.vertical_size_extension.intValue() << 12);
        }
        return v;
    }

    public int getVerticalSize() {
        return vertical_size;
    }

    public static int getVerticalSize(M2VHeaderSequence s, M2VHeaderExtSequence es) {
        int v = s.vertical_size_value.intValue();
        if (es != null) {
            v |= (es.vertical_size_extension.intValue() << 12);
        }
        return v;
    }
}
