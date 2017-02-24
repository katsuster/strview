package net.katsuster.strview.media.m2v;

import java.util.*;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;
import net.katsuster.strview.media.m2v.M2VConsts.*;

/**
 * <p>
 * MPEG2 Video picture display extension
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
public class M2VHeaderExtPictureDisplay extends M2VHeaderExt
        implements Cloneable {
    public SInt[] frame_centre_horizontal_offset;
    public UInt[] marker_bit0;
    public SInt[] frame_centre_vertical_offset;
    public UInt[] marker_bit1;

    private Map.Entry<Long, M2VHeaderExtSequence> entEseq;
    private M2VHeaderExtSequence eseq;
    private Map.Entry<Long, M2VHeaderExtPictureCoding> entEpic;
    private M2VHeaderExtPictureCoding epic;

    public M2VHeaderExtPictureDisplay(Map.Entry<Long, M2VHeaderExtSequence> es,
                                      Map.Entry<Long, M2VHeaderExtPictureCoding> epc) {
        frame_centre_horizontal_offset = new SInt[4];
        marker_bit0 = new UInt[4];
        frame_centre_vertical_offset = new SInt[4];
        marker_bit1 = new UInt[4];
        for (int i = 0; i < 4; i++) {
            frame_centre_horizontal_offset[i] = new SInt();
            marker_bit0[i] = new UInt();
            frame_centre_vertical_offset[i] = new SInt();
            marker_bit1[i] = new UInt();
        }

        entEseq = es;
        entEpic = epc;
        //Cannot set null
        eseq = entEseq.getValue();
        epic = entEpic.getValue();
    }

    @Override
    public M2VHeaderExtPictureDisplay clone()
            throws CloneNotSupportedException {
        M2VHeaderExtPictureDisplay obj = (M2VHeaderExtPictureDisplay) super.clone();

        obj.frame_centre_horizontal_offset = frame_centre_horizontal_offset.clone();
        obj.marker_bit0 = marker_bit0.clone();
        obj.frame_centre_vertical_offset = frame_centre_vertical_offset.clone();
        obj.marker_bit1 = marker_bit1.clone();
        for (int i = 0; i < obj.frame_centre_horizontal_offset.length; i++) {
            obj.frame_centre_horizontal_offset[i] = frame_centre_horizontal_offset[i].clone();
            obj.marker_bit0[i] = marker_bit0[i].clone();
            obj.frame_centre_vertical_offset[i] = frame_centre_vertical_offset[i].clone();
            obj.marker_bit1[i] = marker_bit1[i].clone();
        }

        return obj;
    }

    @Override
    public void read(PacketReader<?> c) {
        read(c, this);
    }

    public static void read(PacketReader<?> c,
                            M2VHeaderExtPictureDisplay d) {
        c.enterBlock("M2V picture_display_extension()");

        M2VHeaderExt.read(c, d);

        int n = d.getNumberOfFrameCentreOffsets();

        d.frame_centre_horizontal_offset = new SInt[n];
        d.marker_bit0 = new UInt[4];
        d.frame_centre_vertical_offset = new SInt[n];
        d.marker_bit1 = new UInt[n];
        for (int i = 0; i < n; i++) {
            d.frame_centre_horizontal_offset[i] = c.readSInt(16, d.frame_centre_horizontal_offset[i]);
            d.marker_bit0[i]                    = c.readUInt( 1, d.marker_bit0[i]                   );
            d.frame_centre_vertical_offset[i]   = c.readSInt(16, d.frame_centre_vertical_offset[i]  );
            d.marker_bit1[i]                    = c.readUInt( 1, d.marker_bit1[i]                   );
        }

        c.leaveBlock();
    }

    @Override
    public void write(PacketWriter<?> c) {
        write(c, this);
    }

    public static void write(PacketWriter<?> c,
                             M2VHeaderExtPictureDisplay d) {
        c.enterBlock("M2V picture_display_extension()");

        M2VHeaderExt.write(c, d);

        int n = d.getNumberOfFrameCentreOffsets();

        for (int i = 0; i < n; i++) {
            c.writeSInt(16, d.frame_centre_horizontal_offset[i], "frame_centre_horizontal_offset[" + i + "]");
            c.writeUInt( 1, d.marker_bit0[i]                   , "marker_bit0[" + i + "]"                   );
            c.writeSInt(16, d.frame_centre_vertical_offset[i]  , "frame_centre_vertical_offset[" + i + "]"  );
            c.writeUInt( 1, d.marker_bit1[i]                   , "marker_bit1[" + i + "]"                   );
        }

        c.leaveBlock();
    }

    public int getNumberOfFrameCentreOffsets() {
        return getNumberOfFrameCentreOffsets(eseq, epic);
    }

    /**
     * <p>
     * number_of_frame_centre_offsets
     * </p>
     *
     * @return number_of_frame_centre_offsets
     */
    public static int getNumberOfFrameCentreOffsets(M2VHeaderExtSequence eseq, M2VHeaderExtPictureCoding epic) {
        if (eseq.progressive_sequence.intValue() == 1) {
            if (epic.repeat_first_field.intValue() == 1) {
                if (epic.top_field_first.intValue() == 1) {
                    return 3;
                } else {
                    return 2;
                }
            } else {
                return 1;
            }
        } else {
            if (epic.picture_structure.intValue() == PICTURE_STRUCTURE.TOP
                    || epic.picture_structure.intValue() == PICTURE_STRUCTURE.BOTTOM) {
                return 1;
            } else {
                if (epic.repeat_first_field.intValue() == 1) {
                    return 3;
                } else {
                    return 2;
                }
            }
        }
    }
}
