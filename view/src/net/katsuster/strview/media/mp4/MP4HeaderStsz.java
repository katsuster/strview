package net.katsuster.strview.media.mp4;

import java.util.*;

import net.katsuster.strview.media.bit.*;
import net.katsuster.strview.util.bit.*;

/**
 * <p>
 * MPEG4 media file format の stsz ボックス。
 * </p>
 *
 * <p>
 * 参考規格
 * </p>
 * <ul>
 * <li>ISO/IEC 14496-12: ISO base media file format</li>
 * </ul>
 */
public class MP4HeaderStsz extends MP4HeaderFull
        implements Cloneable {
    public UInt sample_size;
    public UInt sample_count;
    public List<UInt> entry_size;

    public MP4HeaderStsz() {
        sample_size = new UInt("sample_size");
        sample_count = new UInt("sample_count");
        entry_size = new ArrayList<>();
    }

    @Override
    public MP4HeaderStsz clone()
            throws CloneNotSupportedException {
        MP4HeaderStsz obj =
                (MP4HeaderStsz)super.clone();

        obj.sample_size = (UInt)sample_size.clone();
        obj.sample_count = (UInt)sample_count.clone();

        obj.entry_size = new ArrayList<>();
        for (UInt v : entry_size) {
            obj.entry_size.add((UInt)v.clone());
        }

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
                                MP4HeaderStsz d) {
        c.enterBlock(d);

        MP4HeaderFull.readBits(c, d);

        d.sample_size  = c.readUInt(32, d.sample_size );
        d.sample_count = c.readUInt(32, d.sample_count);

        if (d.sample_size.intValue() == 0) {
            d.entry_size.clear();
            for (int i = 0; i < d.sample_count.intValue(); i++) {
                UInt data = new UInt("entry_size[" + i + "]");
                data = c.readUInt(32, data);
                d.entry_size.add(data);
            }
        }

        c.leaveBlock();
    }

    @Override
    public void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 MP4HeaderStsz d) {
        c.enterBlock(d);

        MP4HeaderFull.writeBits(c, d);

        c.writeUInt(32, d.sample_size );
        c.writeUInt(32, d.sample_count);

        if (d.sample_size.intValue() == 0) {
            for (int i = 0; i < d.sample_count.intValue(); i++) {
                c.writeUInt(32, d.entry_size.get(i));
            }
        }

        c.leaveBlock();
    }
}
