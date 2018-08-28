package net.katsuster.strview.media.mp4;

import net.katsuster.strview.media.bit.*;
import net.katsuster.strview.util.bit.*;

/**
 * <p>
 * MPEG4 media file format の tkhd ボックス。
 * </p>
 *
 * <p>
 * 参考規格
 * </p>
 * <ul>
 * <li>ISO/IEC 14496-12: ISO base media file format</li>
 * </ul>
 */
public class MP4HeaderTkhd extends MP4HeaderFull
        implements Cloneable {
    public UInt creation_time;
    public UInt modification_time;
    public UInt track_ID;
    public UInt reserved1;
    public UInt duration;

    public UInt reserved2;
    public UInt reserved3;
    public SInt layer;
    public SInt alternate_group;
    public SFixed8_8 volume;
    public UInt reserved4;
    public UInt[] matrix;
    public SFixed16_16 width;
    public SFixed16_16 height;

    public MP4HeaderTkhd() {
        creation_time = new UInt("creation_time");
        modification_time = new UInt("modification_time");
        track_ID = new UInt("track_ID");
        reserved1 = new UInt("reserved1");
        duration = new UInt("duration");

        reserved2 = new UInt("reserved2");
        reserved3 = new UInt("reserved3");
        layer = new SInt("layer");
        alternate_group = new SInt("alternate_group");
        volume = new SFixed8_8("volume", (short)0);
        reserved4 = new UInt("reserved4");
        matrix = new UInt[0];
        width = new SFixed16_16("width", 0);
        height = new SFixed16_16("height", 0);
    }

    @Override
    public MP4HeaderTkhd clone()
            throws CloneNotSupportedException {
        MP4HeaderTkhd obj =
                (MP4HeaderTkhd)super.clone();
        int i;

        if (version.intValue() == 1) {
            obj.creation_time = (UInt)creation_time.clone();
            obj.modification_time = (UInt)modification_time.clone();
            obj.track_ID = (UInt)track_ID.clone();
            obj.reserved1 = (UInt)reserved1.clone();
            obj.duration = (UInt)duration.clone();
        } else {
            obj.creation_time = (UInt)creation_time.clone();
            obj.modification_time = (UInt)modification_time.clone();
            obj.track_ID = (UInt)track_ID.clone();
            obj.reserved1 = (UInt)reserved1.clone();
            obj.duration = (UInt)duration.clone();
        }

        obj.reserved2 = (UInt)reserved2.clone();
        obj.reserved3 = (UInt)reserved3.clone();
        obj.layer = (SInt)layer.clone();
        obj.alternate_group = (SInt)alternate_group.clone();
        obj.volume = (SFixed8_8)volume.clone();
        obj.reserved4 = (UInt)reserved4.clone();
        obj.matrix = matrix.clone();
        for (i = 0; i < obj.matrix.length; i++) {
            obj.matrix[i] = (UInt)matrix[i].clone();
        }
        obj.width = (SFixed16_16)width.clone();
        obj.height = (SFixed16_16)height.clone();

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
                                MP4HeaderTkhd d) {
        c.enterBlock(d);

        MP4HeaderFull.readBits(c, d);

        if (d.version.intValue() == 1) {
            d.creation_time     = c.readUInt(64, d.creation_time    );
            d.modification_time = c.readUInt(64, d.modification_time);
            d.track_ID          = c.readUInt(32, d.track_ID         );
            d.reserved1         = c.readUInt(32, d.reserved1        );
            d.duration          = c.readUInt(64, d.duration         );
        } else {
            d.creation_time     = c.readUInt(32, d.creation_time    );
            d.modification_time = c.readUInt(32, d.modification_time);
            d.track_ID          = c.readUInt(32, d.track_ID         );
            d.reserved1         = c.readUInt(32, d.reserved1        );
            d.duration          = c.readUInt(32, d.duration         );
        }
        d.reserved2       = c.readUInt(32, d.reserved2      );
        d.reserved3       = c.readUInt(32, d.reserved3      );
        d.layer           = c.readSInt(16, d.layer          );
        d.alternate_group = c.readSInt(16, d.alternate_group);
        d.volume          = c.readSF8_8(16, d.volume        );
        d.reserved4       = c.readUInt(16, d.reserved4      );
        d.matrix = new UInt[9];
        for (int i = 0; i < d.matrix.length; i++) {
            d.matrix[i] = new UInt("matrix[" + i + "]");
            d.matrix[i] = c.readUInt(32, d.matrix[i]);
        }
        d.width  = c.readSF16_16(32, d.width );
        d.height = c.readSF16_16(32, d.height);

        c.leaveBlock();
    }

    @Override
    public void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 MP4HeaderTkhd d) {
        c.enterBlock(d);

        MP4HeaderFull.writeBits(c, d);

        if (d.version.intValue() == 1) {
            c.writeUInt(64, d.creation_time    , d.getCreationTimeName());
            c.writeUInt(64, d.modification_time, d.getModificationTimeName());
            c.writeUInt(32, d.track_ID         );
            c.writeUInt(32, d.reserved1        );
            c.writeUInt(64, d.duration         );
        } else {
            c.writeUInt(32, d.creation_time    , d.getCreationTimeName());
            c.writeUInt(32, d.modification_time, d.getModificationTimeName());
            c.writeUInt(32, d.track_ID         );
            c.writeUInt(32, d.reserved1        );
            c.writeUInt(32, d.duration         );
        }
        c.writeUInt(32, d.reserved2      );
        c.writeUInt(32, d.reserved3      );
        c.writeSInt(16, d.layer          );
        c.writeSInt(16, d.alternate_group);
        c.writeSF8_8(16, d.volume        );
        c.writeUInt(16, d.reserved4      );
        for (int i = 0; i < d.matrix.length; i++) {
            c.writeUInt(32, d.matrix[i]);
        }
        c.writeSF16_16(32, d.width );
        c.writeSF16_16(32, d.height);

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
