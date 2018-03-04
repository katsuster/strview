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
 */
public class M2VHeaderSlice<T extends LargeList<?>>
        extends M2VHeader<T>
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

    private Map.Entry<Long, M2VHeaderSequence<T>> entSeq;
    private M2VHeaderSequence<T> seq = null;
    private Map.Entry<Long, M2VHeaderExtSequence<T>> entEseq;
    private M2VHeaderExtSequence<T> eseq = null;
    private Map.Entry<Long, M2VHeaderExtSequenceScalable<T>> entEseqsca;
    private M2VHeaderExtSequenceScalable<T> eseqsca = null;
    private int horizontal_size;
    private int vertical_size;

    public M2VHeaderSlice(Map.Entry<Long, M2VHeaderSequence<T>> es) {
        this(es, null, null);
    }

    public M2VHeaderSlice(Map.Entry<Long, M2VHeaderSequence<T>> es,
                          Map.Entry<Long, M2VHeaderExtSequence<T>> ees) {
        this(es, ees, null);
    }

    public M2VHeaderSlice(Map.Entry<Long, M2VHeaderSequence<T>> es,
                          Map.Entry<Long, M2VHeaderExtSequence<T>> ees,
                          Map.Entry<Long, M2VHeaderExtSequenceScalable<T>> eesc) {
        slice_vertical_position_extension = new UInt("slice_vertical_position_extension");
        priority_breakpoint     = new UInt("priority_breakpoint"    );
        quantiser_scale_code    = new UInt("quantiser_scale_code"   );
        slice_extension_flag    = new UInt("slice_extension_flag"   );
        intra_slice             = new UInt("intra_slice"            );
        slice_picture_id_enable = new UInt("slice_picture_id_enable");
        slice_picture_id        = new UInt("slice_picture_id"       );
        extra_bit_slice_1       = new UInt("extra_bit_slice_1"      );
        extra_information_slice = new UInt("extra_information_slice");
        extra_bit_slice_0       = new UInt("extra_bit_slice_0"      );

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
    public String getTypeName() {
        return "slice";
    }

    @Override
    public M2VHeaderSlice<T> clone()
            throws CloneNotSupportedException {
        M2VHeaderSlice<T> obj = (M2VHeaderSlice<T>)super.clone();

        obj.slice_vertical_position_extension = (UInt)slice_vertical_position_extension.clone();
        obj.priority_breakpoint = (UInt)priority_breakpoint.clone();
        obj.quantiser_scale_code = (UInt)quantiser_scale_code.clone();
        obj.slice_extension_flag = (UInt)slice_extension_flag.clone();
        obj.intra_slice = (UInt)intra_slice.clone();
        obj.slice_picture_id_enable = (UInt)slice_picture_id_enable.clone();
        obj.slice_picture_id = (UInt)slice_picture_id.clone();
        obj.extra_bit_slice_1 = (UInt)extra_bit_slice_1.clone();
        obj.extra_information_slice = (UInt)extra_information_slice.clone();
        obj.extra_bit_slice_0 = (UInt)extra_bit_slice_0.clone();

        return obj;
    }

    @Override
    public void read(StreamReader<?, ?> c) {
        read(c, this);
    }

    public static void read(StreamReader<?, ?> c,
                            M2VHeaderSlice d) {
        c.enterBlock(d);

        M2VHeader.read(c, d);

        c.mark("sequence_header No", d.entSeq.getKey().toString());
        if (d.eseq != null) {
            c.mark("sequence_extension No", d.entEseq.getKey().toString());
        }
        if (d.eseqsca != null) {
            c.mark("sequence_scalable_extension No", d.entEseqsca.getKey().toString());
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
    public void write(StreamWriter<?, ?> c) {
        write(c, this);
    }

    public static void write(StreamWriter<?, ?> c,
                             M2VHeaderSlice d) {
        c.enterBlock(d);

        M2VHeader.write(c, d);

        c.mark("sequence_header No", d.entSeq.getKey().toString());
        if (d.eseq != null) {
            c.mark("sequence_extension No", d.entEseq.getKey().toString());
        }
        if (d.eseqsca != null) {
            c.mark("sequence_scalable_extension No", d.entEseqsca.getKey().toString());
        }

        if (d.getVerticalSize() > 2800) {
            c.writeUInt( 3, d.slice_vertical_position_extension);
        }

        if (d.eseqsca != null) {
            if (d.eseqsca.scalable_mode.intValue() == SCALABLE_MODE.DATA_PARTITIONING) {
                c.writeUInt( 7, d.priority_breakpoint);
            }
        }

        c.writeUInt( 5, d.quantiser_scale_code);
        if (d.slice_extension_flag.intValue() == 1) {
            c.writeUInt( 1, d.slice_extension_flag   );
            c.writeUInt( 1, d.intra_slice            );
            c.writeUInt( 1, d.slice_picture_id_enable);
            c.writeUInt( 6, d.slice_picture_id       );
            while (d.extra_bit_slice_1.intValue() == 1) {
                //TODO: extra_information_slice is ignored
                c.writeUInt( 1, d.extra_bit_slice_1      );
                c.writeUInt( 8, d.extra_information_slice);
            }
        }
        c.writeUInt( 1, d.extra_bit_slice_0);

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
