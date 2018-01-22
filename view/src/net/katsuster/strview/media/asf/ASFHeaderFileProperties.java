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
 */
public class ASFHeaderFileProperties<T extends LargeList<?>>
        extends ASFHeader<T>
        implements Cloneable {
    public ASFGUID<T> file_id;
    public UIntR file_size;
    public UIntR creation_date;
    public UIntR data_packets_count;
    public UIntR play_duration;
    public UIntR send_duration;
    public UIntR preroll;
    public UIntR flags;

    public UIntR minimum_data_packet_size;
    public UIntR maximum_data_packet_size;
    public UIntR maximum_bitrate;

    public ASFHeaderFileProperties() {
        file_id            = new ASFGUID<>("File ID");
        file_size          = new UIntR("File Size"         );
        creation_date      = new UIntR("Creation Date"     );
        data_packets_count = new UIntR("Data Packets Count");
        play_duration      = new UIntR("Play Duration"     );
        send_duration      = new UIntR("Send Duration"     );
        preroll            = new UIntR("Preroll"           );
        flags              = new UIntR("Flags"             );

        minimum_data_packet_size = new UIntR("Minimum Data Packet Size");
        maximum_data_packet_size = new UIntR("Maximum Data Packet Size");
        maximum_bitrate          = new UIntR("Maximum Bitrate"         );
    }

    @Override
    public ASFHeaderFileProperties clone()
            throws CloneNotSupportedException {
        ASFHeaderFileProperties obj = (ASFHeaderFileProperties)super.clone();

        obj.file_id = file_id.clone();
        obj.file_size = (UIntR)file_size.clone();
        obj.creation_date = (UIntR)creation_date.clone();
        obj.data_packets_count = (UIntR)data_packets_count.clone();
        obj.play_duration = (UIntR)play_duration.clone();
        obj.send_duration = (UIntR)send_duration.clone();
        obj.preroll = (UIntR)preroll.clone();
        obj.flags = (UIntR)flags.clone();
        ////Broadcast Flag 1
        ////Seekable Flag 1
        ////Reserved 30
        obj.minimum_data_packet_size = (UIntR)minimum_data_packet_size.clone();
        obj.maximum_data_packet_size = (UIntR)maximum_data_packet_size.clone();
        obj.maximum_bitrate = (UIntR)maximum_bitrate.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "File Properties Object";
    }

    @Override
    public boolean isRecursive() {
        return false;
    }

    @Override
    public void read(StreamReader<?> c) {
        read(c, this);
    }

    public static void read(StreamReader<?> c,
                            ASFHeaderFileProperties d) {
        c.enterBlock(d);

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
    public void write(StreamWriter<?> c) {
        write(c, this);
    }

    public static void write(StreamWriter<?> c,
                             ASFHeaderFileProperties d) {
        c.enterBlock(d);

        ASFHeader.write(c, d);

        c.mark("File ID", "");
        d.file_id.write(c);
        c.writeUIntR(64, d.file_size         );
        c.writeUIntR(64, d.creation_date     , d.getCreationDateName());
        c.writeUIntR(64, d.data_packets_count);
        c.writeUIntR(64, d.play_duration     , d.getPlayDurationName());
        c.writeUIntR(64, d.send_duration     , d.getSendDurationName());
        c.writeUIntR(64, d.preroll           );
        c.writeUIntR(32, d.flags             );
        ////Broadcast Flag 1
        ////Seekable Flag 1
        ////Reserved 30
        c.writeUIntR(32, d.minimum_data_packet_size);
        c.writeUIntR(32, d.maximum_data_packet_size);
        c.writeUIntR(32, d.maximum_bitrate         );

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
