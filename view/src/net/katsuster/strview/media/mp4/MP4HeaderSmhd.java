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
    public void readBits(BitStreamReader c) {
        readBits(c, this);
    }

    public static void readBits(BitStreamReader c,
                                MP4HeaderSmhd d) {
        c.enterBlock(d);

        MP4HeaderFull.readBits(c, d);

        d.balance  = c.readSF8_8(16, d.balance);
        d.reserved = c.readUInt(16, d.reserved);

        c.leaveBlock();
    }

    @Override
    public void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 MP4HeaderSmhd d) {
        c.enterBlock(d);

        MP4HeaderFull.writeBits(c, d);

        c.writeSF8_8(16, d.balance);
        c.writeUInt(16, d.reserved);

        c.leaveBlock();
    }
}
