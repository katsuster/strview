package net.katsuster.strview.media.rmff;

import net.katsuster.strview.util.bit.*;
import net.katsuster.strview.media.bit.*;

/**
 * <p>
 * MDPR チャンクヘッダ。
 * </p>
 */
public class RMFFHeaderMDPR
        extends RMFFHeader
        implements Cloneable {
    public UInt stream_number;
    public UInt max_bit_rate;
    public UInt avg_bit_rate;
    public UInt max_packet_size;
    public UInt avg_packet_size;
    public UInt start_time;
    public UInt preroll;
    public UInt duration;
    public UInt stream_name_size;
    public LargeBitList stream_name;
    public UInt mime_type_size;
    public LargeBitList mime_type;

    public RMFFHeaderMDPR() {
        stream_number    = new UInt("stream_number"   );
        max_bit_rate     = new UInt("max_bit_rate"    );
        avg_bit_rate     = new UInt("avg_bit_rate"    );
        max_packet_size  = new UInt("max_packet_size" );
        avg_packet_size  = new UInt("avg_packet_size" );
        start_time       = new UInt("start_time"      );
        preroll          = new UInt("preroll"         );
        duration         = new UInt("duration"        );
        stream_name_size = new UInt("stream_name_size");
        stream_name      = new SubLargeBitList("stream_name");
        mime_type_size   = new UInt("mime_type_size"  );
        mime_type        = new SubLargeBitList("mime_type");
    }

    @Override
    public RMFFHeaderMDPR clone()
            throws CloneNotSupportedException {
        RMFFHeaderMDPR obj = (RMFFHeaderMDPR)super.clone();

        obj.stream_number = (UInt)stream_number.clone();
        obj.max_bit_rate = (UInt)max_bit_rate.clone();
        obj.avg_bit_rate = (UInt)avg_bit_rate.clone();
        obj.max_packet_size = (UInt)max_packet_size.clone();
        obj.avg_packet_size = (UInt)avg_packet_size.clone();
        obj.start_time = (UInt)start_time.clone();
        obj.preroll = (UInt)preroll.clone();
        obj.duration = (UInt)duration.clone();
        obj.stream_name_size = (UInt)stream_name_size.clone();
        obj.stream_name = (LargeBitList)stream_name.clone();
        obj.mime_type_size = (UInt)mime_type_size.clone();
        obj.mime_type = (LargeBitList)mime_type.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "MDPR chunk";
    }

    @Override
    protected void readBits(BitStreamReader c) {
        readBits(c, this);
    }

    public static void readBits(BitStreamReader c,
                                RMFFHeaderMDPR d) {
        c.enterBlock(d);

        RMFFHeader.readBits(c, d);

        if (d.object_version.intValue() == 0) {
            d.stream_number    = c.readUInt(16, d.stream_number   );
            d.max_bit_rate     = c.readUInt(32, d.max_bit_rate    );
            d.avg_bit_rate     = c.readUInt(32, d.avg_bit_rate    );
            d.max_packet_size  = c.readUInt(32, d.max_packet_size );
            d.avg_packet_size  = c.readUInt(32, d.avg_packet_size );
            d.start_time       = c.readUInt(32, d.start_time      );
            d.preroll          = c.readUInt(32, d.preroll         );
            d.duration         = c.readUInt(32, d.duration        );

            d.stream_name_size = c.readUInt( 8, d.stream_name_size);
            checkNegative(d.stream_name_size);
            d.stream_name = c.readBitList(d.stream_name_size.intValue() << 3, d.stream_name);

            d.mime_type_size   = c.readUInt( 8, d.mime_type_size  );
            checkNegative(d.mime_type_size);
            d.mime_type = c.readBitList(d.mime_type_size.intValue() << 3, d.mime_type);
        }

        c.leaveBlock();
    }

    @Override
    protected void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 RMFFHeaderMDPR d) {
        c.enterBlock(d);

        RMFFHeader.writeBits(c, d);

        if (d.object_version.intValue() == 0) {
            c.writeUInt(16, d.stream_number  );
            c.writeUInt(32, d.max_bit_rate   );
            c.writeUInt(32, d.avg_bit_rate   );
            c.writeUInt(32, d.max_packet_size);
            c.writeUInt(32, d.avg_packet_size);
            c.writeUInt(32, d.start_time     );
            c.writeUInt(32, d.preroll        );
            c.writeUInt(32, d.duration       );

            c.writeUInt( 8, d.stream_name_size);
            c.writeBitList(d.stream_name_size.intValue() << 3, d.stream_name, d.getStreamNameName());

            c.writeUInt( 8, d.mime_type_size);
            c.writeBitList(d.mime_type_size.intValue() << 3, d.mime_type, d.getMimeTypeName());
        }

        c.leaveBlock();
    }

    public String getStreamNameName() {
        return getArrayName(stream_name, "US-ASCII");
    }

    public String getMimeTypeName() {
        return getArrayName(mime_type, "US-ASCII");
    }
}
