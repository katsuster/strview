package net.katsuster.strview.media.m2v;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * MPEG2 Video GOP header
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
public class M2VHeaderGOP
        extends M2VHeader
        implements Cloneable {
    public UInt drop_frame;
    public UInt time_code_hours;
    public UInt time_code_minutes;
    public UInt marker_bit;
    public UInt time_code_seconds;
    public UInt time_code_pictures;
    public UInt closed_gop;
    public UInt broken_link;

    public M2VHeaderGOP() {
        drop_frame         = new UInt("drop_frame"        );
        time_code_hours    = new UInt("time_code_hours"   );
        time_code_minutes  = new UInt("time_code_minutes" );
        marker_bit         = new UInt("marker_bit"        );
        time_code_seconds  = new UInt("time_code_seconds" );
        time_code_pictures = new UInt("time_code_pictures");
        closed_gop         = new UInt("closed_gop"        );
        broken_link        = new UInt("broken_link"       );
    }

    @Override
    public M2VHeaderGOP clone()
            throws CloneNotSupportedException {
        M2VHeaderGOP obj = (M2VHeaderGOP)super.clone();

        obj.drop_frame = (UInt)drop_frame.clone();
        obj.time_code_hours = (UInt)time_code_hours.clone();
        obj.time_code_minutes = (UInt)time_code_minutes.clone();
        obj.marker_bit = (UInt)marker_bit.clone();
        obj.time_code_seconds = (UInt)time_code_seconds.clone();
        obj.time_code_pictures = (UInt)time_code_pictures.clone();
        obj.closed_gop = (UInt)closed_gop.clone();
        obj.broken_link = (UInt)broken_link.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "group_of_pictures_header";
    }

    @Override
    protected void readBits(BitStreamReader c) {
        readBits(c, this);
    }

    public static void readBits(BitStreamReader c,
                                M2VHeaderGOP d) {
        c.enterBlock(d);

        M2VHeader.readBits(c, d);

        d.drop_frame         = c.readUInt( 1, d.drop_frame        );
        d.time_code_hours    = c.readUInt( 5, d.time_code_hours   );
        d.time_code_minutes  = c.readUInt( 6, d.time_code_minutes );
        d.marker_bit         = c.readUInt( 1, d.marker_bit        );
        d.time_code_seconds  = c.readUInt( 6, d.time_code_seconds );
        d.time_code_pictures = c.readUInt( 6, d.time_code_pictures);
        d.closed_gop         = c.readUInt( 1, d.closed_gop        );
        d.broken_link        = c.readUInt( 1, d.broken_link       );

        c.leaveBlock();
    }

    @Override
    protected void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 M2VHeaderGOP d) {
        c.enterBlock(d);

        M2VHeader.writeBits(c, d);

        c.writeUInt( 1, d.drop_frame        );
        c.writeUInt( 5, d.time_code_hours   );
        c.writeUInt( 6, d.time_code_minutes );
        c.writeUInt( 1, d.marker_bit        );
        c.writeUInt( 6, d.time_code_seconds );
        c.writeUInt( 6, d.time_code_pictures);
        c.writeUInt( 1, d.closed_gop        );
        c.writeUInt( 1, d.broken_link       );

        c.leaveBlock();
    }
}
