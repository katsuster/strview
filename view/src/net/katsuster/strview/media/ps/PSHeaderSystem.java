package net.katsuster.strview.media.ps;

import java.util.*;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * MPEG2 PS (Program Stream) system_header
 * </p>
 *
 * <p>
 * 参考規格
 * </p>
 * <ul>
 * <li>ITU-T H.222, ISO/IEC 13818-1:
 * Information technology – Generic coding of moving pictures and
 * associated audio information: Systems</li>
 * </ul>
 *
 * @author katsuhiro
 */
public class PSHeaderSystem extends PSHeader {
    public UInt header_length;
    public UInt marker_bit1;
    public UInt rate_bound;
    public UInt marker_bit2;
    public UInt audio_bound;
    public UInt fixed_flag;
    public UInt csps_flag;
    public UInt system_audio_lock_flag;
    public UInt system_video_lock_flag;
    public UInt marker_bit3;
    public UInt video_bound;
    public UInt packet_rate_restriction_flag;
    public UInt reserved_bits;

    public List<SystemESInfo> es_info;

    public PSHeaderSystem() {
        header_length = new UInt();
        marker_bit1 = new UInt();
        rate_bound = new UInt();
        marker_bit2 = new UInt();
        audio_bound = new UInt();
        fixed_flag = new UInt();
        csps_flag = new UInt();
        system_audio_lock_flag = new UInt();
        system_video_lock_flag = new UInt();
        marker_bit3 = new UInt();
        video_bound = new UInt();
        packet_rate_restriction_flag = new UInt();
        reserved_bits = new UInt();

        es_info = new ArrayList<SystemESInfo>();
    }

    @Override
    public PSHeaderSystem clone()
            throws CloneNotSupportedException {
        PSHeaderSystem obj = (PSHeaderSystem)super.clone();
        int i;

        obj.header_length = (UInt)header_length.clone();
        obj.marker_bit1 = (UInt)marker_bit1.clone();
        obj.rate_bound = (UInt)rate_bound.clone();
        obj.marker_bit2 = (UInt)marker_bit2.clone();
        obj.audio_bound = (UInt)audio_bound.clone();
        obj.fixed_flag = (UInt)fixed_flag.clone();
        obj.csps_flag = (UInt)csps_flag.clone();
        obj.system_audio_lock_flag = (UInt)system_audio_lock_flag.clone();
        obj.system_video_lock_flag = (UInt)system_video_lock_flag.clone();
        obj.marker_bit3 = (UInt)marker_bit3.clone();
        obj.video_bound = (UInt)video_bound.clone();
        obj.packet_rate_restriction_flag = (UInt)packet_rate_restriction_flag.clone();
        obj.reserved_bits = (UInt)reserved_bits.clone();

        obj.es_info = new ArrayList<SystemESInfo>();
        for (i = 0; i < es_info.size(); i++) {
            obj.es_info.add(es_info.get(i).clone());
        }

        return obj;
    }

    @Override
    public void read(PacketReader<?> c) {
        read(c, this);
    }

    public static void read(PacketReader<?> c,
                            PSHeaderSystem d) {
        c.enterBlock("PS system header");

        PSHeader.read(c, d);

        d.header_length                = c.readUInt(16, d.header_length               );
        d.marker_bit1                  = c.readUInt( 1, d.marker_bit1                 );
        d.rate_bound                   = c.readUInt(22, d.rate_bound                  );
        d.marker_bit2                  = c.readUInt( 1, d.marker_bit2                 );
        d.audio_bound                  = c.readUInt( 6, d.audio_bound                 );
        d.fixed_flag                   = c.readUInt( 1, d.fixed_flag                  );
        d.csps_flag                    = c.readUInt( 1, d.csps_flag                   );
        d.system_audio_lock_flag       = c.readUInt( 1, d.system_audio_lock_flag      );
        d.system_video_lock_flag       = c.readUInt( 1, d.system_video_lock_flag      );
        d.marker_bit3                  = c.readUInt( 1, d.marker_bit3                 );
        d.video_bound                  = c.readUInt( 5, d.video_bound                 );
        d.packet_rate_restriction_flag = c.readUInt( 1, d.packet_rate_restriction_flag);
        d.reserved_bits                = c.readUInt( 7, d.reserved_bits               );

        d.es_info.clear();
        while (c.peekLong(1) == 1) {
            SystemESInfo info = new SystemESInfo();
            info.read(c);
            d.es_info.add(info);
        }

        c.leaveBlock();
    }

    @Override
    public void write(PacketWriter<?> c) {
        write(c, this);
    }

    public static void write(PacketWriter<?> c,
                             PSHeaderSystem d) {
        c.enterBlock("PS system header");

        PSHeader.write(c, d);

        c.writeUInt(16, d.header_length               , "header_length"               );
        c.writeUInt( 1, d.marker_bit1                 , "marker_bit1"                 );
        c.writeUInt(22, d.rate_bound                  , "rate_bound"                  );
        c.writeUInt( 1, d.marker_bit2                 , "marker_bit2"                 );
        c.writeUInt( 6, d.audio_bound                 , "audio_bound"                 );
        c.writeUInt( 1, d.fixed_flag                  , "fixed_flag"                  );
        c.writeUInt( 1, d.csps_flag                   , "CSPS_flag"                   );
        c.writeUInt( 1, d.system_audio_lock_flag      , "system_audio_lock_flag"      );
        c.writeUInt( 1, d.system_video_lock_flag      , "system_video_lock_flag"      );
        c.writeUInt( 1, d.marker_bit3                 , "marker_bit3"                 );
        c.writeUInt( 5, d.video_bound                 , "video_bound"                 );
        c.writeUInt( 1, d.packet_rate_restriction_flag, "packet_rate_restriction_flag");
        c.writeUInt( 7, d.reserved_bits               , "reserved_bits"               );

        for (int i = 0; i < d.es_info.size(); i++) {
            d.es_info.get(i).write(c);
        }

        c.leaveBlock();
    }
}
