package net.katsuster.strview.media.mp4;

import java.io.*;

import net.katsuster.strview.media.bit.*;
import net.katsuster.strview.util.bit.*;

/**
 * <p>
 * MPEG4 media file format の mdhd ボックス。
 * </p>
 *
 * <p>
 * 参考規格
 * </p>
 * <ul>
 * <li>ISO/IEC 14496-12: ISO base media file format</li>
 * </ul>
 */
public class MP4HeaderMdhd extends MP4HeaderFull {
    public UInt creation_time;
    public UInt modification_time;
    public UInt timescale;
    public UInt duration;

    public UInt pad;
    public UInt[] language;
    public UInt pre_defined;

    public MP4HeaderMdhd() {
        creation_time = new UInt("creation_time");
        modification_time = new UInt("modification_time");
        timescale = new UInt("timescale");
        duration = new UInt("duration");

        pad = new UInt("pad");
        language = new UInt[0];
        pre_defined = new UInt("pre_defined");
    }

    @Override
    public MP4HeaderMdhd clone()
            throws CloneNotSupportedException {
        MP4HeaderMdhd obj = (MP4HeaderMdhd)super.clone();

        obj.creation_time = (UInt)creation_time.clone();
        obj.modification_time = (UInt)modification_time.clone();
        obj.timescale = (UInt)timescale.clone();
        obj.duration = (UInt)duration.clone();

        obj.pad = (UInt)pad.clone();
        obj.language = language.clone();
        for (int i = 0; i < obj.language.length; i++) {
            obj.language[i] = (UInt)language[i].clone();
        }
        obj.pre_defined = (UInt)pre_defined.clone();

        return obj;
    }

    @Override
    public boolean isRecursive() {
        return false;
    }

    @Override
    public void readBits(BitStreamReader c) {
        readBits(c, this);
    }

    public static void readBits(BitStreamReader c,
                                MP4HeaderMdhd d) {
        c.enterBlock(d);

        MP4HeaderFull.readBits(c, d);

        if (d.version.intValue() == 1) {
            d.creation_time     = c.readUInt(64, d.creation_time    );
            d.modification_time = c.readUInt(64, d.modification_time);
            d.timescale         = c.readUInt(32, d.timescale        );
            d.duration          = c.readUInt(64, d.duration         );
        } else {
            d.creation_time     = c.readUInt(32, d.creation_time    );
            d.modification_time = c.readUInt(32, d.modification_time);
            d.timescale         = c.readUInt(32, d.timescale        );
            d.duration          = c.readUInt(32, d.duration         );
        }

        d.pad = c.readUInt( 1, d.pad);
        d.language = new UInt[3];
        for (int i = 0; i < d.language.length; i++) {
            d.language[i] = new UInt("language[" + i + "]");
            d.language[i] = c.readUInt( 5, d.language[i]);
        }
        c.readUInt(16, d.pre_defined);

        c.leaveBlock();
    }

    @Override
    public void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 MP4HeaderMdhd d) {
        c.enterBlock(d);

        MP4HeaderFull.writeBits(c, d);

        if (d.version.intValue() == 1) {
            c.writeUInt(64, d.creation_time    , d.getCreationTimeName());
            c.writeUInt(64, d.modification_time, d.getModificationTimeName());
            c.writeUInt(32, d.timescale        );
            c.writeUInt(64, d.duration         );
        } else {
            c.writeUInt(32, d.creation_time    , d.getCreationTimeName());
            c.writeUInt(32, d.modification_time, d.getModificationTimeName());
            c.writeUInt(32, d.timescale        );
            c.writeUInt(32, d.duration         );
        }

        c.writeUInt( 1, d.pad);
        for (int i = 0; i < d.language.length; i++) {
            c.writeUInt( 5, d.language[i]);
        }
        c.mark("language", d.getLanguageName());
        c.writeUInt(16, d.pre_defined);

        c.leaveBlock();
    }

    public String getCreationTimeName() {
        return getTrackTimeName(
                (int)(creation_time.longValue() / 3600),
                (int)(creation_time.longValue() % 3600));
    }

    public String getModificationTimeName() {
        return getTrackTimeName(
                (int)(modification_time.longValue() / 3600),
                (int)(modification_time.longValue() % 3600));
    }

    public String getLanguageName() {
        if (language.length >= 3) {
            return getLanguageName(language[0].intValue(),
                    language[1].intValue(),
                    language[2].intValue());
        } else {
            return "???";
        }
    }

    public static String getLanguageName(int chr1, int chr2, int chr3) {
        String name = "..unknown..";
        byte[] b = new byte[3];

        try {
            //ISO 639-2/T for the set of three character codes.
            b[0] = (byte)((chr1 & 0x1f) + 0x60);
            b[1] = (byte)((chr2 & 0x1f) + 0x60);
            b[2] = (byte)((chr3 & 0x1f) + 0x60);
            name = new String(b, "US-ASCII");
        } catch (UnsupportedEncodingException e) {
            //do nothing
        }

        return name;
    }
}
