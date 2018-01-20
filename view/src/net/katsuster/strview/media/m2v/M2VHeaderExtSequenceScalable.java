package net.katsuster.strview.media.m2v;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;
import net.katsuster.strview.media.m2v.M2VConsts.*;

/**
 * <p>
 * MPEG2 Video sequence scalable extension
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
public class M2VHeaderExtSequenceScalable<T extends LargeList<?>>
        extends M2VHeaderExt<T>
        implements Cloneable {
    public UInt scalable_mode;
    public UInt layer_id;
    public UInt lower_layer_prediction_horizontal_size;
    public UInt marker_bit;
    public UInt lower_layer_prediction_vertical_size;
    public UInt horizontal_subsampling_factor_m;
    public UInt horizontal_subsampling_factor_n;
    public UInt vertical_subsampling_factor_m;
    public UInt vertical_subsampling_factor_n;
    public UInt picture_mux_enable;
    public UInt mux_to_progressive_sequence;
    public UInt picture_mux_order;
    public UInt picture_mux_factor;

    public M2VHeaderExtSequenceScalable() {
        scalable_mode                          = new UInt("scalable_mode"                         );
        layer_id                               = new UInt("layer_id"                              );
        lower_layer_prediction_horizontal_size = new UInt("lower_layer_prediction_horizontal_size");
        marker_bit                             = new UInt("marker_bit"                            );
        lower_layer_prediction_vertical_size   = new UInt("lower_layer_prediction_vertical_size"  );
        horizontal_subsampling_factor_m        = new UInt("horizontal_subsampling_factor_m"       );
        horizontal_subsampling_factor_n        = new UInt("horizontal_subsampling_factor_n"       );
        vertical_subsampling_factor_m          = new UInt("vertical_subsampling_factor_m"         );
        vertical_subsampling_factor_n          = new UInt("vertical_subsampling_factor_n"         );
        picture_mux_enable                     = new UInt("picture_mux_enable"                    );
        mux_to_progressive_sequence            = new UInt("mux_to_progressive_sequence"           );
        picture_mux_order                      = new UInt("picture_mux_order"                     );
        picture_mux_factor                     = new UInt("picture_mux_factor"                    );
    }

    @Override
    public M2VHeaderExtSequenceScalable clone()
            throws CloneNotSupportedException {
        M2VHeaderExtSequenceScalable obj = (M2VHeaderExtSequenceScalable)super.clone();

        obj.scalable_mode = (UInt)scalable_mode.clone();
        obj.layer_id = (UInt)layer_id.clone();
        obj.lower_layer_prediction_horizontal_size = (UInt)lower_layer_prediction_horizontal_size.clone();
        obj.marker_bit = (UInt)marker_bit.clone();
        obj.lower_layer_prediction_vertical_size = (UInt)lower_layer_prediction_vertical_size.clone();
        obj.horizontal_subsampling_factor_m = (UInt)horizontal_subsampling_factor_m.clone();
        obj.horizontal_subsampling_factor_n = (UInt)horizontal_subsampling_factor_n.clone();
        obj.vertical_subsampling_factor_m = (UInt)vertical_subsampling_factor_m.clone();
        obj.vertical_subsampling_factor_n = (UInt)vertical_subsampling_factor_n.clone();
        obj.picture_mux_enable = (UInt)picture_mux_enable.clone();
        obj.mux_to_progressive_sequence = (UInt)mux_to_progressive_sequence.clone();
        obj.picture_mux_order = (UInt)picture_mux_order.clone();
        obj.picture_mux_factor = (UInt)picture_mux_factor.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "sequence_scalable_extension";
    }

    @Override
    public void read(StreamReader<?> c) {
        read(c, this);
    }

    public static void read(StreamReader<?> c,
                            M2VHeaderExtSequenceScalable d) {
        c.enterBlock(d);

        M2VHeaderExt.read(c, d);

        d.scalable_mode = c.readUInt( 2, d.scalable_mode);
        d.layer_id      = c.readUInt( 4, d.layer_id     );

        if (d.scalable_mode.intValue() == SCALABLE_MODE.SPATIAL) {
            d.lower_layer_prediction_horizontal_size = c.readUInt(14, d.lower_layer_prediction_horizontal_size);
            d.marker_bit                             = c.readUInt( 1, d.marker_bit                            );
            d.lower_layer_prediction_vertical_size   = c.readUInt(14, d.lower_layer_prediction_vertical_size  );
            d.horizontal_subsampling_factor_m        = c.readUInt( 5, d.horizontal_subsampling_factor_m       );
            d.horizontal_subsampling_factor_n        = c.readUInt( 5, d.horizontal_subsampling_factor_n       );
            d.vertical_subsampling_factor_m          = c.readUInt( 5, d.vertical_subsampling_factor_m         );
            d.vertical_subsampling_factor_n          = c.readUInt( 5, d.vertical_subsampling_factor_n         );
        }

        if (d.scalable_mode.intValue() == SCALABLE_MODE.TEMPORAL) {
            d.picture_mux_enable = c.readUInt( 1, d.picture_mux_enable);
            if (d.picture_mux_enable.intValue() == 1) {
                d.mux_to_progressive_sequence = c.readUInt( 1, d.mux_to_progressive_sequence);
            }
            d.picture_mux_order  = c.readUInt( 3, d.picture_mux_order );
            d.picture_mux_factor = c.readUInt( 3, d.picture_mux_factor);
        }

        c.leaveBlock();
    }

    @Override
    public void write(StreamWriter<?> c) {
        write(c, this);
    }

    public static void write(StreamWriter<?> c,
                             M2VHeaderExtSequenceScalable d) {
        c.enterBlock(d);

        M2VHeaderExt.write(c, d);

        c.writeUInt( 2, d.scalable_mode, d.getScalableModeName());
        c.writeUInt( 4, d.layer_id     );

        if (d.scalable_mode.intValue() == SCALABLE_MODE.SPATIAL) {
            c.writeUInt(14, d.lower_layer_prediction_horizontal_size);
            c.writeUInt( 1, d.marker_bit                            );
            c.writeUInt(14, d.lower_layer_prediction_vertical_size  );
            c.writeUInt( 5, d.horizontal_subsampling_factor_m       );
            c.writeUInt( 5, d.horizontal_subsampling_factor_n       );
            c.writeUInt( 5, d.vertical_subsampling_factor_m         );
            c.writeUInt( 5, d.vertical_subsampling_factor_n         );
        }

        if (d.scalable_mode.intValue() == SCALABLE_MODE.TEMPORAL) {
            c.writeUInt( 1, d.picture_mux_enable);
            if (d.picture_mux_enable.intValue() == 1) {
                c.writeUInt( 1, d.mux_to_progressive_sequence);
            }
            c.writeUInt( 3, d.picture_mux_order );
            c.writeUInt( 3, d.picture_mux_factor);
        }

        c.leaveBlock();
    }

    public String getScalableModeName() {
        return M2VConsts.getScalableModeName(scalable_mode.intValue());
    }
}
