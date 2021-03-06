package net.katsuster.strview.media.flv;

import net.katsuster.strview.util.bit.*;
import net.katsuster.strview.media.bit.*;

/**
 * <p>
 * Flash Video ビデオ、オーディオ、スクリプトタグの共通ヘッダ。
 * </p>
 */
public class FLVHeaderES
        extends FLVHeader
        implements Cloneable {
    public UInt previous_tag_size;

    public UInt reserved;
    public UInt filter;
    public UInt tag_type;
    public UInt data_size;
    public UInt timestamp;
    public UInt timestamp_extended;
    public UInt stream_id;

    public FLVHeaderES() {
        previous_tag_size = new UInt("PreviousTagSize");

        reserved           = new UInt("Reserved");
        filter             = new UInt("Filter");
        tag_type           = new UInt("TagType");
        data_size          = new UInt("DataSize");
        timestamp          = new UInt("Timestamp");
        timestamp_extended = new UInt("TimestampExtended");
        stream_id          = new UInt("StreamID");
    }

    @Override
    public FLVHeaderES clone()
            throws CloneNotSupportedException {
        FLVHeaderES obj = (FLVHeaderES)super.clone();

        obj.previous_tag_size = (UInt)previous_tag_size.clone();

        obj.reserved = (UInt)reserved.clone();
        obj.filter = (UInt)filter.clone();
        obj.tag_type = (UInt)tag_type.clone();
        obj.data_size = (UInt)data_size.clone();
        obj.timestamp = (UInt)timestamp.clone();
        obj.timestamp_extended = (UInt)timestamp_extended.clone();
        obj.stream_id = (UInt)stream_id.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "FLV tag (ES)";
    }

    @Override
    protected void readBits(BitStreamReader c) {
        readBits(c, this);
    }

    public static void readBits(BitStreamReader c,
                                FLVHeaderES d) {
        c.enterBlock(d);

        FLVHeader.readBits(c, d);

        d.previous_tag_size  = c.readUInt(32, d.previous_tag_size );

        d.reserved           = c.readUInt( 2, d.reserved          );
        d.filter             = c.readUInt( 1, d.filter            );
        d.tag_type           = c.readUInt( 5, d.tag_type          );
        d.data_size          = c.readUInt(24, d.data_size         );
        d.timestamp          = c.readUInt(24, d.timestamp         );
        d.timestamp_extended = c.readUInt( 8, d.timestamp_extended);
        d.stream_id          = c.readUInt(24, d.stream_id         );

        c.leaveBlock();
    }

    @Override
    protected void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 FLVHeaderES d) {
        c.enterBlock(d);

        FLVHeader.writeBits(c, d);

        c.writeUInt(32, d.previous_tag_size);

        c.writeUInt( 2, d.reserved          );
        c.writeUInt( 1, d.filter            );
        c.writeUInt( 5, d.tag_type          , d.getTagTypeName());
        c.writeUInt(24, d.data_size         );
        c.writeUInt(24, d.timestamp         );
        c.writeUInt( 8, d.timestamp_extended);
        c.writeUInt(24, d.stream_id         );

        c.leaveBlock();
    }

    public String getTagTypeName() {
        return FLVConsts.getTagTypeName(tag_type.intValue());
    }
}
