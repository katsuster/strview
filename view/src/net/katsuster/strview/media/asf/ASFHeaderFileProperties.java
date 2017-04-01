package net.katsuster.strview.media.asf;

import java.util.*;
import java.text.*;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * ASF (Advanced Systems Format) File Properties Object
 * </p>
 *
 * <p>
 * 参考規格
 * </p>
 * <ul>
 * <li>Advanced Systems Format (ASF) Specification: Revision 01.20.06</li>
 * </ul>
 *
 * @author katsuhiro
 */
public class ASFHeaderFileProperties extends ASFHeader
        implements Cloneable {
    public ASFGUID file_id;
    public UInt file_size;
    public UInt creation_date;
    public UInt data_packets_count;
    public UInt play_duration;
    public UInt send_duration;
    public UInt preroll;
    public UInt flags;

    public UInt minimum_data_packet_size;
    public UInt maximum_data_packet_size;
    public UInt maximum_bitrate;

    public ASFHeaderFileProperties() {
        file_id = new ASFGUID();
        file_size = new UInt();
        creation_date = new UInt();
        data_packets_count = new UInt();
        play_duration = new UInt();
        send_duration = new UInt();
        preroll = new UInt();
        flags = new UInt();

        minimum_data_packet_size = new UInt();
        maximum_data_packet_size = new UInt();
        maximum_bitrate = new UInt();
    }

    @Override
    public ASFHeaderFileProperties clone()
            throws CloneNotSupportedException {
        ASFHeaderFileProperties obj = (ASFHeaderFileProperties)super.clone();

        obj.file_id = file_id.clone();
        obj.file_size = (UInt)file_size.clone();
        obj.creation_date = (UInt)creation_date.clone();
        obj.data_packets_count = (UInt)data_packets_count.clone();
        obj.play_duration = (UInt)play_duration.clone();
        obj.send_duration = (UInt)send_duration.clone();
        obj.preroll = (UInt)preroll.clone();
        obj.flags = (UInt)flags.clone();
        ////Broadcast Flag 1
        ////Seekable Flag 1
        ////Reserved 30
        obj.minimum_data_packet_size = (UInt)minimum_data_packet_size.clone();
        obj.maximum_data_packet_size = (UInt)maximum_data_packet_size.clone();
        obj.maximum_bitrate = (UInt)maximum_bitrate.clone();

        return obj;
    }

    @Override
    public boolean isRecursive() {
        return false;
    }

    @Override
    public void read(PacketReader<?> c) {
        read(c, this);
    }

    public static void read(PacketReader<?> c,
                            ASFHeaderFileProperties d) {
        c.enterBlock("File Properties Object");

        ASFHeader.read(c, d);

        c.mark("File ID", "");
        d.file_id.read(c);
        d.file_size                = c.readUIntR(64, d.file_size               );
        d.creation_date            = c.readUIntR(64, d.creation_date           );
        d.data_packets_count       = c.readUIntR(64, d.data_packets_count      );
        d.play_duration            = c.readUIntR(64, d.play_duration           );
        d.send_duration            = c.readUIntR(64, d.send_duration           );
        d.preroll                  = c.readUIntR(64, d.preroll                 );
        d.flags                    = c.readUIntR(32, d.flags                   );
        ////Broadcast Flag 1
        ////Seekable Flag 1
        ////Reserved 30
        d.minimum_data_packet_size = c.readUIntR(32, d.minimum_data_packet_size);
        d.maximum_data_packet_size = c.readUIntR(32, d.maximum_data_packet_size);
        d.maximum_bitrate          = c.readUIntR(32, d.maximum_bitrate         );

        c.leaveBlock();
    }

    @Override
    public void write(PacketWriter<?> c) {
        write(c, this);
    }

    public static void write(PacketWriter<?> c,
                             ASFHeaderFileProperties d) {
        c.enterBlock("File Properties Object");

        ASFHeader.write(c, d);

        c.mark("File ID", "");
        d.file_id.write(c);
        c.writeUIntR(64, d.file_size               , "File Size"               );
        c.writeUIntR(64, d.creation_date           , "Creation Date"           , d.getCreationDateName());
        c.writeUIntR(64, d.data_packets_count      , "Data Packets Count"      );
        c.writeUIntR(64, d.play_duration           , "Play Duration"           , d.getPlayDurationName());
        c.writeUIntR(64, d.send_duration           , "Send Duration"           , d.getSendDurationName());
        c.writeUIntR(64, d.preroll                 , "Preroll"                 );
        c.writeUIntR(32, d.flags                   , "Flags"                   );
        ////Broadcast Flag 1
        ////Seekable Flag 1
        ////Reserved 30
        c.writeUIntR(32, d.minimum_data_packet_size, "Minimum Data Packet Size");
        c.writeUIntR(32, d.maximum_data_packet_size, "Maximum Data Packet Size");
        c.writeUIntR(32, d.maximum_bitrate         , "Maximum Bitrate"         );

        c.leaveBlock();
    }

    public String getCreationDateName() {
        return getCreationDateName(creation_date.longValue());
    }

    public static String getCreationDateName(long v) {
        GregorianCalendar date;
        DateFormat fmt;
        String name;
        int hour, min, sec;

        //100 ns
        sec  = (int)((v / 10000000) % 60);
        min  = (int)((v / 10000000 / 60) % 60);
        hour = (int)(v / 10000000 / 3600);

        //年月日、時分秒を文字列に変換する
        date = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
        date.set(1601, 0, 0, hour, min, sec);
        fmt = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
        name = fmt.format(date.getTime());

        return name;
    }

    public String getPlayDurationName() {
        return ASFHeader.get100nsDurationName(
                play_duration.longValue());
    }

    public String getSendDurationName() {
        return ASFHeader.get100nsDurationName(
                send_duration.longValue());
    }
}
