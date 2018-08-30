package net.katsuster.strview.media.mp4;

import net.katsuster.strview.media.bit.*;
import net.katsuster.strview.util.bit.*;

/**
 * <p>
 * MPEG4 media file format の stss ボックスのエントリ。
 * </p>
 *
 * <p>
 * 参考規格
 * </p>
 * <ul>
 * <li>ISO/IEC 14496-12: ISO base media file format</li>
 * </ul>
 */
public class MP4StssEntry extends BitBlockAdapter
        implements Cloneable {
    public UInt sample_number;

    public MP4StssEntry() {
        sample_number = new UInt("sample_number");
    }

    @Override
    public MP4StssEntry clone()
            throws CloneNotSupportedException {
        MP4StssEntry obj = (MP4StssEntry)super.clone();

        obj.sample_number = (UInt) sample_number.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "STSS Entry";
    }

    @Override
    public void readBits(BitStreamReader c) {
        readBits(c, this);
    }

    public static void readBits(BitStreamReader c,
                                MP4StssEntry d) {
        c.enterBlock(d);

        d.sample_number = c.readUInt(32, d.sample_number);

        c.leaveBlock();
    }

    @Override
    public void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 MP4StssEntry d) {
        c.enterBlock(d);

        c.writeUInt(32, d.sample_number);

        c.leaveBlock();
    }
}
