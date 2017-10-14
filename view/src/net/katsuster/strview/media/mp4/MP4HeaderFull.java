package net.katsuster.strview.media.mp4;

import java.text.*;
import java.util.*;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * MPEG4 media file format の FullBox ヘッダ。
 * </p>
 *
 * <p>
 * 参考規格
 * </p>
 * <ul>
 * <li>ISO/IEC 14496-12: ISO base media file format</li>
 * </ul>
 *
 * @author katsuhiro
 */
public class MP4HeaderFull extends MP4Header
        implements Cloneable {
    public UInt version;
    public UInt flags;

    public MP4HeaderFull() {
        version = new UInt();
        flags = new UInt();
    }

    @Override
    public MP4HeaderFull clone()
            throws CloneNotSupportedException {
        MP4HeaderFull obj = (MP4HeaderFull)super.clone();

        obj.version = (UInt)version.clone();
        obj.flags = (UInt)flags.clone();

        return obj;
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
                            MP4HeaderFull d) {
        c.enterBlock("FullBox");

        MP4Header.read(c, d);

        d.version = c.readUInt( 8, d.version);
        d.flags   = c.readUInt(24, d.flags  );

        c.leaveBlock();
    }

    @Override
    public void write(StreamWriter<?> c) {
        write(c, this);
    }

    public static void write(StreamWriter<?> c,
                             MP4HeaderFull d) {
        c.enterBlock("FullBox");

        MP4Header.write(c, d);

        c.writeUInt( 8, d.version, "version");
        c.writeUInt(24, d.flags  , "flags");

        c.leaveBlock();
    }

    public static String getTrackTimeName(int hour, int sec) {
        GregorianCalendar date;
        DateFormat fmt;
        String name;

        date = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
        date.set(1904, 0, 1, hour, 0, sec);
        fmt = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
        name = fmt.format(date.getTime());

        return name;
    }
}
