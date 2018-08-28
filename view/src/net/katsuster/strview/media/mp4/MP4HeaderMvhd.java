package net.katsuster.strview.media.mp4;

import java.io.*;

import net.katsuster.strview.media.bit.*;
import net.katsuster.strview.util.bit.*;

/**
 * <p>
 * MPEG4 media file format の mvhd ボックス。
 * </p>
 *
 * <p>
 * 参考規格
 * </p>
 * <ul>
 * <li>ISO/IEC 14496-12: ISO base media file format</li>
 * </ul>
 */
public class MP4HeaderMvhd extends MP4HeaderFull {
    public UInt creation_time;
    public UInt modification_time;
    public UInt timescale;
    public UInt duration;

    public SFixed16_16 rate;
    public SFixed8_8 volume;
    public UInt reserved1;
    public UInt reserved2;
    public UInt reserved3;
    public UInt[] matrix;
    public UInt[] pre_defined;
    public UInt next_track_ID;

    public MP4HeaderMvhd() {
        creation_time = new UInt("creation_time");
        modification_time = new UInt("modification_time");
        timescale = new UInt("timescale");
        duration = new UInt("duration");

        rate = new SFixed16_16("rate");
        volume = new SFixed8_8("volume");
        reserved1 = new UInt("reserved1");
        reserved2 = new UInt("reserved2");
        reserved3 = new UInt("reserved3");
        matrix = new UInt[0];
        pre_defined = new UInt[0];
        next_track_ID = new UInt("next_track_ID");
    }

    @Override
    public MP4HeaderMvhd clone()
            throws CloneNotSupportedException {
        MP4HeaderMvhd obj = (MP4HeaderMvhd)super.clone();
        int i;

        obj.creation_time = (UInt)creation_time.clone();
        obj.modification_time = (UInt)modification_time.clone();
        obj.timescale = (UInt)timescale.clone();
        obj.duration = (UInt)duration.clone();

        obj.rate = (SFixed16_16)rate.clone();
        obj.volume = (SFixed8_8)volume.clone();
        obj.reserved1 = (UInt)reserved1.clone();
        obj.reserved2 = (UInt)reserved2.clone();
        obj.reserved3 = (UInt)reserved3.clone();
        obj.matrix = matrix.clone();
        for (i = 0; i < obj.matrix.length; i++) {
            obj.matrix[i] = (UInt)matrix[i].clone();
        }
        obj.pre_defined = pre_defined.clone();
        for (i = 0; i < obj.pre_defined.length; i++) {
            obj.pre_defined[i] = (UInt)pre_defined[i].clone();
        }
        obj.next_track_ID = (UInt)next_track_ID.clone();

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
                                MP4HeaderMvhd d) {
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

        d.rate      = c.readSF16_16(32, d.rate  );
        d.volume    = c.readSF8_8(16, d.volume  );
        d.reserved1 = c.readUInt(16, d.reserved1);
        d.reserved2 = c.readUInt(32, d.reserved2);
        d.reserved3 = c.readUInt(32, d.reserved3);
        d.matrix = new UInt[9];
        for (int i = 0; i < d.matrix.length; i++) {
            d.matrix[i] = new UInt("matrix[" + i + "]");
            d.matrix[i] = c.readUInt(32, d.matrix[i]);
        }
        d.pre_defined = new UInt[6];
        for (int i = 0; i < d.pre_defined.length; i++) {
            d.pre_defined[i] = new UInt("pre_defined[" + i + "]");
            d.pre_defined[i] = c.readUInt(32, d.pre_defined[i]);
        }
        c.readUInt(32, d.next_track_ID);

        c.leaveBlock();
    }

    @Override
    public void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 MP4HeaderMvhd d) {
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

        c.writeSF16_16(32, d.rate  );
        c.writeSF8_8(16, d.volume  );
        c.writeUInt(16, d.reserved1);
        c.writeUInt(32, d.reserved2);
        c.writeUInt(32, d.reserved3);
        for (int i = 0; i < d.matrix.length; i++) {
            c.writeUInt(32, d.matrix[i]);
        }
        for (int i = 0; i < d.pre_defined.length; i++) {
            c.writeUInt(32, d.pre_defined[i]);
        }
        c.writeUInt(32, d.next_track_ID);

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
}
