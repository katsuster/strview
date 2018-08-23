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
    public void readBits(BitStreamReader b) {
        readBits(b, this);
    }

    public static void readBits(BitStreamReader b,
                                MP4HeaderTkhd d) {
        int i;

        MP4HeaderFull.readBits(b, d);

        if (d.version.intValue() == 1) {
            d.creation_time     = b.readUInt(64, d.creation_time    );
            d.modification_time = b.readUInt(64, d.modification_time);
            d.track_ID          = b.readUInt(32, d.track_ID         );
            d.reserved1         = b.readUInt(32, d.reserved1        );
            d.duration          = b.readUInt(64, d.duration         );
        } else {
            d.creation_time     = b.readUInt(32, d.creation_time    );
            d.modification_time = b.readUInt(32, d.modification_time);
            d.track_ID          = b.readUInt(32, d.track_ID         );
            d.reserved1         = b.readUInt(32, d.reserved1        );
            d.duration          = b.readUInt(32, d.duration         );
        }
        d.reserved2       = b.readUInt(32, d.reserved2      );
        d.reserved3       = b.readUInt(32, d.reserved3      );
        d.layer           = b.readSInt(16, d.layer          );
        d.alternate_group = b.readSInt(16, d.alternate_group);
        d.volume          = b.readSF8_8(16, d.volume        );
        d.reserved4       = b.readUInt(16, d.reserved4      );
        d.matrix = new UInt[9];
        for (i = 0; i < d.matrix.length; i++) {
            d.matrix[i] = new UInt("matrix[" + i + "]");
            d.matrix[i] = b.readUInt(32, d.matrix[i]);
        }
        d.width  = b.readSF16_16(32, d.width );
        d.height = b.readSF16_16(32, d.height);
    }

    @Override
    public void writeBits(BitStreamWriter b) {
        writeBits(b, this);
    }

    public static void writeBits(BitStreamWriter b,
                                 MP4HeaderTkhd d) {
        int i;

        MP4HeaderFull.writeBits(b, d);

        if (d.version.intValue() == 1) {
            b.writeUInt(64, d.creation_time    , d.getCreationTimeName());
            b.writeUInt(64, d.modification_time, d.getModificationTimeName());
            b.writeUInt(32, d.track_ID         );
            b.writeUInt(32, d.reserved1        );
            b.writeUInt(64, d.duration         );
        } else {
            b.writeUInt(32, d.creation_time    , d.getCreationTimeName());
            b.writeUInt(32, d.modification_time, d.getModificationTimeName());
            b.writeUInt(32, d.track_ID         );
            b.writeUInt(32, d.reserved1        );
            b.writeUInt(32, d.duration         );
        }
        b.writeUInt(32, d.reserved2      );
        b.writeUInt(32, d.reserved3      );
        b.writeSInt(16, d.layer          );
        b.writeSInt(16, d.alternate_group);
        b.writeSF8_8(16, d.volume        );
        b.writeUInt(16, d.reserved4      );
        for (i = 0; i < d.matrix.length; i++) {
            b.writeUInt(32, d.matrix[i]);
        }
        b.writeSF16_16(32, d.width );
        b.writeSF16_16(32, d.height);
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
