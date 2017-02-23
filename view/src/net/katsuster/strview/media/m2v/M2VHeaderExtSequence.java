package net.katsuster.strview.media.m2v;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * MPEG2 Video sequence extension
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
public class M2VHeaderExtSequence extends M2VHeaderExt
        implements Cloneable {
    public UInt profile_and_level_indication;
    public UInt progressive_sequence;
    public UInt chroma_format;
    public UInt horizontal_size_extension;
    public UInt vertical_size_extension;
    public UInt bit_rate_extension;
    public UInt marker_bit;
    public UInt vbv_buffer_size_extension;
    public UInt low_delay;
    public UInt frame_rate_extension_n;
    public UInt frame_rate_extension_d;

    private int profile;
    private int level;

    public M2VHeaderExtSequence() {
        super();

        profile_and_level_indication = new UInt();
        progressive_sequence = new UInt();
        chroma_format = new UInt();
        horizontal_size_extension = new UInt();
        vertical_size_extension = new UInt();
        bit_rate_extension = new UInt();
        marker_bit = new UInt();
        vbv_buffer_size_extension = new UInt();
        low_delay = new UInt();
        frame_rate_extension_n = new UInt();
        frame_rate_extension_d = new UInt();
    }

    @Override
    public M2VHeaderExtSequence clone()
            throws CloneNotSupportedException {
        M2VHeaderExtSequence obj = (M2VHeaderExtSequence)super.clone();

        obj.profile_and_level_indication = profile_and_level_indication.clone();
        obj.progressive_sequence = progressive_sequence.clone();
        obj.chroma_format = chroma_format.clone();
        obj.horizontal_size_extension = horizontal_size_extension.clone();
        obj.vertical_size_extension = vertical_size_extension.clone();
        obj.bit_rate_extension = bit_rate_extension.clone();
        obj.marker_bit = marker_bit.clone();
        obj.vbv_buffer_size_extension = vbv_buffer_size_extension.clone();
        obj.low_delay = low_delay.clone();
        obj.frame_rate_extension_n = frame_rate_extension_n.clone();
        obj.frame_rate_extension_d = frame_rate_extension_d.clone();

        return obj;
    }

    @Override
    public void read(PacketReader<?> c) {
        read(c, this);
    }

    public static void read(PacketReader<?> c,
                            M2VHeaderExtSequence d) {
        c.enterBlock("M2V sequence_extension()");

        M2VHeaderExt.read(c, d);

        d.profile_and_level_indication = c.readUInt( 8, d.profile_and_level_indication);
        d.profile = getProfileValue(d);
        d.level   = getLevelValue(d);

        d.progressive_sequence         = c.readUInt( 1, d.progressive_sequence        );
        d.chroma_format                = c.readUInt( 2, d.chroma_format               );
        d.horizontal_size_extension    = c.readUInt( 2, d.horizontal_size_extension   );
        d.vertical_size_extension      = c.readUInt( 2, d.vertical_size_extension     );
        d.bit_rate_extension           = c.readUInt(12, d.bit_rate_extension          );
        d.marker_bit                   = c.readUInt( 1, d.marker_bit                  );
        d.vbv_buffer_size_extension    = c.readUInt( 8, d.vbv_buffer_size_extension   );
        d.low_delay                    = c.readUInt( 1, d.low_delay                   );
        d.frame_rate_extension_n       = c.readUInt( 2, d.frame_rate_extension_n      );
        d.frame_rate_extension_d       = c.readUInt( 5, d.frame_rate_extension_d      );

        c.leaveBlock();
    }

    @Override
    public void write(PacketWriter<?> c) {
        write(c, this);
    }

    public static void write(PacketWriter<?> c,
                             M2VHeaderExtSequence d) {
        c.enterBlock("M2V sequence_extension()");

        M2VHeaderExt.write(c, d);

        c.writeUInt( 8, d.profile_and_level_indication, "profile_and_level_indication");
        c.mark("profile", d.getProfileName());
        c.mark("level", d.getLevelName());

        c.writeUInt( 1, d.progressive_sequence        , "progressive_sequence"        );
        c.writeUInt( 2, d.chroma_format               , "chroma_format"               , d.getChromaFormatName());
        c.writeUInt( 2, d.horizontal_size_extension   , "horizontal_size_extension"   );
        c.writeUInt( 2, d.vertical_size_extension     , "vertical_size_extension"     );
        c.writeUInt(12, d.bit_rate_extension          , "bit_rate_extension"          );
        c.writeUInt( 1, d.marker_bit                  , "marker_bit"                  );
        c.writeUInt( 8, d.vbv_buffer_size_extension   , "vbv_buffer_size_extension"   );
        c.writeUInt( 1, d.low_delay                   , "low_delay"                   );
        c.writeUInt( 2, d.frame_rate_extension_n      , "frame_rate_extension_n"      );
        c.writeUInt( 5, d.frame_rate_extension_d      , "frame_rate_extension_d"      );

        c.leaveBlock();
    }

    public String getProfileName() {
        return M2VConsts.getProfileName(getProfileValue());
    }

    public int getProfileValue() {
        return profile;
    }

    public static int getProfileValue(M2VHeaderExtSequence d) {
        return (d.profile_and_level_indication.intValue() >>> 4) & 0x7;
    }

    public String getLevelName() {
        return M2VConsts.getLevelName(getLevelValue());
    }

    public int getLevelValue() {
        return level;
    }

    public static int getLevelValue(M2VHeaderExtSequence d) {
        return (d.profile_and_level_indication.intValue() >>> 0) & 0xf;
    }

    public String getChromaFormatName() {
        return M2VConsts.getChromaFormatName(chroma_format.intValue());
    }
}
