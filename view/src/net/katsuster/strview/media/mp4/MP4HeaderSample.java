package net.katsuster.strview.media.mp4;

import net.katsuster.strview.media.bit.*;
import net.katsuster.strview.util.bit.*;

/**
 * <p>
 * MPEG4 media file format の SampleEntry ヘッダ。
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
public class MP4HeaderSample extends MP4Header
        implements Cloneable {
    public UInt[] reserved1;
    public UInt data_reference_index;

    public MP4HeaderSample() {
        reserved1 = new UInt[0];
        data_reference_index = new UInt("data_reference_index");
    }

    @Override
    public MP4HeaderSample clone()
            throws CloneNotSupportedException {
        MP4HeaderSample obj = (MP4HeaderSample)super.clone();
        int i;

        for (i = 0; i < obj.reserved1.length; i++) {
            obj.reserved1[i] = (UInt)reserved1[i].clone();
        }

        obj.data_reference_index = (UInt)data_reference_index.clone();

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
                                MP4HeaderSample d) {
        int i;

        MP4Header.readBits(b, d);

        d.reserved1 = new UInt[6];
        for (i = 0; i < d.reserved1.length; i++) {
            d.reserved1[i] = new UInt("reserved1[" + i + "]");
            d.reserved1[i] = b.readUInt( 8, d.reserved1[i]);
        }

        d.data_reference_index = b.readUInt(16, d.data_reference_index);
    }

    @Override
    public void writeBits(BitStreamWriter b) {
        writeBits(b, this);
    }

    public static void writeBits(BitStreamWriter b,
                                 MP4HeaderSample d) {
        int i;

        MP4Header.writeBits(b, d);

        for (i = 0; i < d.reserved1.length; i++) {
            b.writeUInt( 8, d.reserved1[i]);
        }

        b.writeUInt(16, d.data_reference_index);
    }
}
