package net.katsuster.strview.media.m2v;

import net.katsuster.strview.util.bit.*;
import net.katsuster.strview.media.bit.*;

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
public class M2VHeaderExtSequenceDisplay
        extends M2VHeaderExt
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
        video_format       = new UInt("video_format"      );
        colour_description = new UInt("colour_description");

        colour_primaries         = new UInt("colour_primaries"        );
        transfer_characteristics = new UInt("transfer_characteristics");
        matrix_coefficients      = new UInt("matrix_coefficients"     );

        display_horizontal_size = new UInt("display_horizontal_size");
        marker_bit              = new UInt("marker_bit"             );
        display_vertical_size   = new UInt("display_vertical_size"  );
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
    protected void readBits(BitStreamReader c) {
        readBits(c, this);
    }

    public static void readBits(BitStreamReader c,
                                M2VHeaderExtSequenceDisplay d) {
        c.enterBlock(d);

        M2VHeaderExt.readBits(c, d);

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
    protected void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 M2VHeaderExtSequenceDisplay d) {
        c.enterBlock(d);

        M2VHeaderExt.writeBits(c, d);

        c.writeUInt( 3, d.video_format            , d.getVideoFormatName());
        c.writeUInt( 1, d.colour_description      );

        if (d.colour_description.intValue() == 1) {
            c.writeUInt( 8, d.colour_primaries        , d.getColourPrimariesName());
            c.writeUInt( 8, d.transfer_characteristics, d.getTransferCharacteristicsName());
            c.writeUInt( 8, d.matrix_coefficients     , d.getMatrixCoefficientsName());
        }

        c.writeUInt(14, d.display_horizontal_size );
        c.writeUInt( 1, d.marker_bit              );
        c.writeUInt(14, d.display_vertical_size   );

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
