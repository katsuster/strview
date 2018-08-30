package net.katsuster.strview.media.mp4;

import net.katsuster.strview.media.bit.*;
import net.katsuster.strview.util.bit.*;

/**
 * <p>
 * MPEG4 media file format の stts ボックスのエントリ。
 * </p>
 *
 * <p>
 * 参考規格
 * </p>
 * <ul>
 * <li>ISO/IEC 14496-12: ISO base media file format</li>
 * </ul>
 */
public class MP4SttsEntry extends BitBlockAdapter
        implements Cloneable {
    public UInt sample_count;
    public UInt sample_delta;

    public MP4SttsEntry() {
        sample_count = new UInt("sample_number");
        sample_delta = new UInt("sample_delta");
    }

    @Override
    public MP4SttsEntry clone()
            throws CloneNotSupportedException {
        MP4SttsEntry obj = (MP4SttsEntry)super.clone();

        obj.sample_count = (UInt)sample_count.clone();
        obj.sample_delta = (UInt)sample_delta.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "STTS Entry";
    }

    @Override
    public void readBits(BitStreamReader c) {
        readBits(c, this);
    }

    public static void readBits(BitStreamReader c,
                                MP4SttsEntry d) {
        c.enterBlock(d);

        d.sample_count = c.readUInt(32, d.sample_count);
        d.sample_delta = c.readUInt(32, d.sample_delta);

        c.leaveBlock();
    }

    @Override
    public void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 MP4SttsEntry d) {
        c.enterBlock(d);

        c.writeUInt(32, d.sample_count);
        c.writeUInt(32, d.sample_delta);

        c.leaveBlock();
    }
}
