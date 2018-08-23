package net.katsuster.strview.media.mp4;

import net.katsuster.strview.media.bit.*;
import net.katsuster.strview.util.bit.*;

/**
 * <p>
 * MPEG4 media file format の smhd ボックス。
 * </p>
 *
 * <p>
 * 参考規格
 * </p>
 * <ul>
 * <li>ISO/IEC 14496-12: ISO base media file format</li>
 * </ul>
 */
public class MP4HeaderSmhd extends MP4HeaderFull
        implements Cloneable {
    public SFixed8_8 balance;
    public UInt reserved;

    public MP4HeaderSmhd() {
        balance = new SFixed8_8("balance", (short)0);
        reserved = new UInt("reserved");
    }

    @Override
    public MP4HeaderSmhd clone()
            throws CloneNotSupportedException {
        MP4HeaderSmhd obj = (MP4HeaderSmhd)super.clone();

        obj.balance = (SFixed8_8)balance.clone();
        obj.reserved = (UInt)reserved.clone();

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
                                MP4HeaderSmhd d) {
        MP4HeaderFull.readBits(b, d);

        d.balance  = b.readSF8_8(16, d.balance);
        d.reserved = b.readUInt(16, d.reserved);
    }

    @Override
    public void writeBits(BitStreamWriter b) {
        writeBits(b, this);
    }

    public static void writeBits(BitStreamWriter b,
                                 MP4HeaderSmhd d) {
        MP4HeaderFull.writeBits(b, d);

        b.writeSF8_8(16, d.balance);
        b.writeUInt(16, d.reserved);
    }
}
