package net.katsuster.strview.media.m2v;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * MPEG2 Video picture header
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
public class M2VHeaderPicture<T extends LargeList<?>>
        extends M2VHeader<T>
        implements Cloneable {
    public UInt temporal_reference;
    public UInt picture_coding_type;
    public UInt vbv_delay;
    public UInt full_pel_forward_vector;
    public UInt forward_f_code;
    public UInt full_pel_backward_vector;
    public UInt backward_f_code;
    public UInt extra_bit_picture_1;
    public UInt extra_information_picture;
    public UInt extra_bit_picture_0;

    public M2VHeaderPicture() {
        temporal_reference        = new UInt("temporal_reference"       );
        picture_coding_type       = new UInt("picture_coding_type"      );
        vbv_delay                 = new UInt("vbv_delay"                );
        full_pel_forward_vector   = new UInt("full_pel_forward_vector"  );
        forward_f_code            = new UInt("forward_f_code"           );
        full_pel_backward_vector  = new UInt("full_pel_backward_vector" );
        backward_f_code           = new UInt("backward_f_code"          );
        extra_bit_picture_1       = new UInt("extra_bit_picture_1"      );
        extra_information_picture = new UInt("extra_information_picture");
        extra_bit_picture_0       = new UInt("extra_bit_picture_0"      );
    }

    @Override
    public M2VHeaderPicture<T> clone()
            throws CloneNotSupportedException {
        M2VHeaderPicture<T> obj = (M2VHeaderPicture<T>)super.clone();

        obj.temporal_reference = (UInt)temporal_reference.clone();
        obj.picture_coding_type = (UInt)picture_coding_type.clone();
        obj.vbv_delay = (UInt)vbv_delay.clone();
        obj.full_pel_forward_vector = (UInt)full_pel_forward_vector.clone();
        obj.forward_f_code = (UInt)forward_f_code.clone();
        obj.full_pel_backward_vector = (UInt)full_pel_backward_vector.clone();
        obj.backward_f_code = (UInt)backward_f_code.clone();
        obj.extra_bit_picture_1 = (UInt)extra_bit_picture_1.clone();
        obj.extra_information_picture = (UInt)extra_information_picture.clone();
        obj.extra_bit_picture_0 = (UInt)extra_bit_picture_0.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "picture_header";
    }

    @Override
    public void read(StreamReader<?> c) {
        read(c, this);
    }

    public static void read(StreamReader<?> c,
                            M2VHeaderPicture d) {
        c.enterBlock(d);

        M2VHeader.read(c, d);

        d.temporal_reference  = c.readUInt(10, d.temporal_reference );
        d.picture_coding_type = c.readUInt( 3, d.picture_coding_type);
        d.vbv_delay           = c.readUInt(16, d.vbv_delay          );

        if (d.picture_coding_type.intValue() == 2 ||
                d.picture_coding_type.intValue() == 3) {
            d.full_pel_forward_vector   = c.readUInt( 1, d.full_pel_forward_vector  );
            d.forward_f_code            = c.readUInt( 3, d.forward_f_code           );
        }

        if (d.picture_coding_type.intValue() == 3) {
            d.full_pel_backward_vector  = c.readUInt( 1, d.full_pel_backward_vector );
            d.backward_f_code           = c.readUInt( 3, d.backward_f_code          );
        }

        while (c.peekLong(1) == 1) {
            //TODO: extra_information_picture is ignored
            d.extra_bit_picture_1       = c.readUInt( 1, d.extra_bit_picture_1      );
            d.extra_information_picture = c.readUInt( 8, d.extra_information_picture);
        }
        d.extra_bit_picture_0 = c.readUInt( 1, d.extra_bit_picture_0);

        c.leaveBlock();
    }

    @Override
    public void write(StreamWriter<?> c) {
        write(c, this);
    }

    public static void write(StreamWriter<?> c,
                             M2VHeaderPicture d) {
        c.enterBlock(d);

        M2VHeader.write(c, d);

        c.writeUInt(10, d.temporal_reference );
        c.writeUInt( 3, d.picture_coding_type, d.getPictureCodingTypeName());
        c.writeUInt(16, d.vbv_delay          );

        if (d.picture_coding_type.intValue() == 2 ||
                d.picture_coding_type.intValue() == 3) {
            c.writeUInt( 1, d.full_pel_forward_vector  );
            c.writeUInt( 3, d.forward_f_code           );
        }

        if (d.picture_coding_type.intValue() == 3) {
            c.writeUInt( 1, d.full_pel_backward_vector );
            c.writeUInt( 3, d.backward_f_code          );
        }

        if (d.extra_bit_picture_1.intValue() == 1) {
            //TODO: extra_information_picture is ignored
            c.writeUInt( 1, d.extra_bit_picture_1      );
            c.writeUInt( 8, d.extra_information_picture);
        }
        c.writeUInt( 1, d.extra_bit_picture_0);

        c.leaveBlock();
    }

    public String getPictureCodingTypeName() {
        return M2VConsts.getPictureCodingTypeName(picture_coding_type.intValue());
    }
}
