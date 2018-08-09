package net.katsuster.strview.media.flv;

import java.util.*;
import java.text.*;

import net.katsuster.strview.util.bit.*;
import net.katsuster.strview.media.bit.*;

/**
 * <p>
 * SCRIPTDATADATE
 * </p>
 */
public class FLVScriptDataDate
        extends FLVScriptData
        implements Cloneable {
    public Float64 date_time;
    public SInt local_date_time_offset;

    public FLVScriptDataDate() {
        date_time = new Float64("DateTime");
        local_date_time_offset = new SInt("LocalDateTimeOffset");
    }

    @Override
    public FLVScriptDataDate clone()
            throws CloneNotSupportedException {
        FLVScriptDataDate obj = (FLVScriptDataDate)super.clone();

        obj.date_time = (Float64)date_time.clone();
        obj.local_date_time_offset = (SInt)local_date_time_offset.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "SCRIPTDATADATE";
    }

    @Override
    protected void readBits(BitStreamReader c) {
        readBits(c, this);
    }

    public static void readBits(BitStreamReader c,
                                FLVScriptDataDate d) {
        c.enterBlock(d);

        FLVScriptData.readBits(c, d);

        d.date_time              = c.readFloat64(64, d.date_time          );
        d.local_date_time_offset = c.readSInt(16, d.local_date_time_offset);

        c.leaveBlock();
    }

    @Override
    protected void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 FLVScriptDataDate d) {
        c.enterBlock(d);

        FLVScriptData.writeBits(c, d);

        c.writeFloat64(64, d.date_time          , d.getDateTimeName());
        c.writeSInt(16, d.local_date_time_offset, d.getLocalDateTimeOffsetName());

        c.leaveBlock();
    }

    public String getDateTimeName() {
        return getDateTimeName(date_time.longValue());
    }

    public static String getDateTimeName(long millis) {
        String name;
        Date d;

        d = new Date(millis);
        name = DateFormat.getDateTimeInstance().format(d);

        return name;
    }

    public String getLocalDateTimeOffsetName() {
        return getLocalDateTimeOffsetName(local_date_time_offset.intValue());
    }

    public static String getLocalDateTimeOffsetName(int minutes) {
        String name;
        int h, m;

        h = minutes / 60;
        m = minutes % 60;
        name = String.format("UTC %+03d:%02d", h, m);

        return name;
    }
}
