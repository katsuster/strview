package net.katsuster.strview.media.mp4;

import net.katsuster.strview.media.bit.*;
import net.katsuster.strview.util.bit.*;

/**
 * <p>
 * MPEG4 media file format の vmhd ボックス。
 * </p>
 *
 * <p>
 * 参考規格
 * </p>
 * <ul>
 * <li>ISO/IEC 14496-12: ISO base media file format</li>
 * </ul>
 */
public class MP4HeaderVmhd extends MP4HeaderFull
        implements Cloneable {
    public UInt graphicsmode;
    public UInt opcolor0;
    public UInt opcolor1;
    public UInt opcolor2;

    public MP4HeaderVmhd() {
        graphicsmode = new UInt("graphicsmode");
        opcolor0 = new UInt("opcolor0");
        opcolor1 = new UInt("opcolor1");
        opcolor2 = new UInt("opcolor2");
    }

    @Override
    public MP4HeaderVmhd clone()
            throws CloneNotSupportedException {
        MP4HeaderVmhd obj =
                (MP4HeaderVmhd)super.clone();

        obj.graphicsmode = (UInt)graphicsmode.clone();
        obj.opcolor0 = (UInt)opcolor0.clone();
        obj.opcolor1 = (UInt)opcolor1.clone();
        obj.opcolor2 = (UInt)opcolor2.clone();

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
                                MP4HeaderVmhd d) {
        c.enterBlock(d);

        MP4HeaderFull.readBits(c, d);

        d.graphicsmode = c.readUInt(16, d.graphicsmode);
        d.opcolor0     = c.readUInt(16, d.opcolor0    );
        d.opcolor1     = c.readUInt(16, d.opcolor1    );
        d.opcolor2     = c.readUInt(16, d.opcolor2    );

        c.leaveBlock();
    }

    @Override
    public void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 MP4HeaderVmhd d) {
        c.enterBlock(d);

        MP4HeaderFull.writeBits(c, d);

        c.writeUInt(16, d.graphicsmode);
        c.writeUInt(16, d.opcolor0    );
        c.writeUInt(16, d.opcolor1    );
        c.writeUInt(16, d.opcolor2    );

        c.leaveBlock();
    }
}
