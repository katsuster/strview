package net.katsuster.strview.media.mp4;

import net.katsuster.strview.media.bit.*;
import net.katsuster.strview.util.bit.*;

/**
 * <p>
 * MPEG4 media file format の stsc ボックスのエントリ。
 * </p>
 *
 * <p>
 * 参考規格
 * </p>
 * <ul>
 * <li>ISO/IEC 14496-12: ISO base media file format</li>
 * </ul>
 */
public class MP4StscEntry extends BitBlockAdapter
        implements Cloneable {
    public UInt first_chunk;
    public UInt samples_per_chunk;
    public UInt sample_description_index;

    public MP4StscEntry() {
        first_chunk = new UInt("first_chunk");
        samples_per_chunk = new UInt("samples_per_chunk");
        sample_description_index = new UInt("sample_description_index");
    }

    @Override
    public MP4StscEntry clone()
            throws CloneNotSupportedException {
        MP4StscEntry obj = (MP4StscEntry)super.clone();

        obj.first_chunk = (UInt) first_chunk.clone();
        obj.samples_per_chunk = (UInt) samples_per_chunk.clone();
        obj.sample_description_index = (UInt) sample_description_index.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "STSC Entry";
    }

    @Override
    public void readBits(BitStreamReader c) {
        readBits(c, this);
    }

    public static void readBits(BitStreamReader c,
                                MP4StscEntry d) {
        c.enterBlock(d);

        d.first_chunk              = c.readUInt(32, d.first_chunk             );
        d.samples_per_chunk        = c.readUInt(32, d.samples_per_chunk       );
        d.sample_description_index = c.readUInt(32, d.sample_description_index);

        c.leaveBlock();
    }

    @Override
    public void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 MP4StscEntry d) {
        c.enterBlock(d);

        c.writeUInt(32, d.first_chunk             );
        c.writeUInt(32, d.samples_per_chunk       );
        c.writeUInt(32, d.sample_description_index);

        c.leaveBlock();
    }
}
