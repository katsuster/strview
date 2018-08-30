package net.katsuster.strview.media.mp4;

import net.katsuster.strview.media.bit.*;
import net.katsuster.strview.util.bit.*;

/**
 * <p>
 * MPEG4 media file format の ctts ボックスのエントリ。
 * </p>
 *
 * <p>
 * 参考規格
 * </p>
 * <ul>
 * <li>ISO/IEC 14496-12: ISO base media file format</li>
 * </ul>
 */
public class MP4CttsEntry extends BitBlockAdapter
        implements Cloneable {
    public UInt sample_count;
    public UInt sample_offset;

    public MP4CttsEntry() {
        sample_count = new UInt("sample_number");
        sample_offset = new UInt("sample_offset");
    }

    @Override
    public MP4CttsEntry clone()
            throws CloneNotSupportedException {
        MP4CttsEntry obj = (MP4CttsEntry)super.clone();

        obj.sample_count = (UInt)sample_count.clone();
        obj.sample_offset = (UInt)sample_offset.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "CTTS Entry";
    }

    @Override
    public void readBits(BitStreamReader c) {
        readBits(c, this);
    }

    public static void readBits(BitStreamReader c,
                                MP4CttsEntry d) {
        c.enterBlock(d);

        d.sample_count  = c.readUInt(32, d.sample_count );
        d.sample_offset = c.readUInt(32, d.sample_offset);

        c.leaveBlock();
    }

    @Override
    public void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 MP4CttsEntry d) {
        c.enterBlock(d);

        c.writeUInt(32, d.sample_count );
        c.writeUInt(32, d.sample_offset);

        c.leaveBlock();
    }
}
