package net.katsuster.strview.media.m2v;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * MPEG2 Video sequence display extension
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
public class M2VHeaderExtSequenceDisplay extends M2VHeaderExt
        implements Cloneable {
    public UInt video_format;
    public UInt colour_description;

    public UInt colour_primaries;
    public UInt transfer_characteristics;
    public UInt matrix_coefficients;

    public UInt display_horizontal_size;
    public UInt marker_bit;
    public UInt display_vertical_size;

    public M2VHeaderExtSequenceDisplay() {
        video_format = new UInt();
        colour_description = new UInt();

        colour_primaries = new UInt();
        transfer_characteristics = new UInt();
        matrix_coefficients = new UInt();

        display_horizontal_size = new UInt();
        marker_bit = new UInt();
        display_vertical_size = new UInt();
    }

    @Override
    public M2VHeaderExtSequenceDisplay clone()
            throws CloneNotSupportedException {
        M2VHeaderExtSequenceDisplay obj = (M2VHeaderExtSequenceDisplay)super.clone();

        obj.video_format = (UInt)video_format.clone();
        obj.colour_description = (UInt)colour_description.clone();

        obj.colour_primaries = (UInt)colour_primaries.clone();
        obj.transfer_characteristics = (UInt)transfer_characteristics.clone();
        obj.matrix_coefficients = (UInt)matrix_coefficients.clone();

        obj.display_horizontal_size = (UInt)display_horizontal_size.clone();
        obj.marker_bit = (UInt)marker_bit.clone();
        obj.display_vertical_size = (UInt)display_vertical_size.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "sequence_display_extension";
    }

    @Override
    public void read(StreamReader<?> c) {
        read(c, this);
    }

    public static void read(StreamReader<?> c,
                            M2VHeaderExtSequenceDisplay d) {
        c.enterBlock(d);

        M2VHeaderExt.read(c, d);

        d.video_format             = c.readUInt( 3, d.video_format            );
        d.colour_description       = c.readUInt( 1, d.colour_description      );

        if (d.colour_description.intValue() == 1) {
            d.colour_primaries         = c.readUInt( 8, d.colour_primaries        );
            d.transfer_characteristics = c.readUInt( 8, d.transfer_characteristics);
            d.matrix_coefficients      = c.readUInt( 8, d.matrix_coefficients     );
        }

        d.display_horizontal_size  = c.readUInt(14, d.display_horizontal_size );
        d.marker_bit               = c.readUInt( 1, d.marker_bit              );
        d.display_vertical_size    = c.readUInt(14, d.display_vertical_size   );

        c.leaveBlock();
    }

    @Override
    public void write(StreamWriter<?> c) {
        write(c, this);
    }

    public static void write(StreamWriter<?> c,
                             M2VHeaderExtSequenceDisplay d) {
        c.enterBlock(d);

        M2VHeaderExt.write(c, d);

        c.writeUInt( 3, d.video_format            , "video_format"            , d.getVideoFormatName());
        c.writeUInt( 1, d.colour_description      , "colour_description"      );

        if (d.colour_description.intValue() == 1) {
            c.writeUInt( 8, d.colour_primaries        , "colour_primaries"        , d.getColourPrimariesName());
            c.writeUInt( 8, d.transfer_characteristics, "transfer_characteristics", d.getTransferCharacteristicsName());
            c.writeUInt( 8, d.matrix_coefficients     , "matrix_coefficients"     , d.getMatrixCoefficientsName());
        }

        c.writeUInt(14, d.display_horizontal_size , "display_horizontal_size" );
        c.writeUInt( 1, d.marker_bit              , "marker_bit"              );
        c.writeUInt(14, d.display_vertical_size   , "display_vertical_size"   );

        c.leaveBlock();
    }

    public String getVideoFormatName() {
        return M2VConsts.getVideoFormatName(video_format.intValue());
    }

    public String getColourPrimariesName() {
        return M2VConsts.getColourPrimariesName(colour_primaries.intValue());
    }

    public String getTransferCharacteristicsName() {
        return M2VConsts.getTransferCharacteristicsName(transfer_characteristics.intValue());
    }

    public String getMatrixCoefficientsName() {
        return M2VConsts.getMatrixCoefficientsName(matrix_coefficients.intValue());
    }
}
