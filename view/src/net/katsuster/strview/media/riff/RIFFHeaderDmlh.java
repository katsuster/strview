package net.katsuster.strview.media.riff;

import net.katsuster.strview.util.bit.*;
import net.katsuster.strview.media.bit.*;

/**
 * <p>
 * dmlh (Extended AVI Header)
 * </p>
 *
 * <p>
 * 参考規格
 * </p>
 * <ul>
 * <li>Microsoft Multimedia Standards Update: Revision 1.0.97</li>
 * <li>OpenDML AVI File Format Extensions: Version 1.02</li>
 * </ul>
 */
public class RIFFHeaderDmlh
        extends RIFFHeader
        implements Cloneable {
    public UIntR dwTotalFrames;

    public RIFFHeaderDmlh() {
        dwTotalFrames = new UIntR("dwTotalFrames");
    }

    @Override
    public RIFFHeaderDmlh clone()
            throws CloneNotSupportedException {
        RIFFHeaderDmlh obj = (RIFFHeaderDmlh)super.clone();

        obj.dwTotalFrames = (UIntR)dwTotalFrames.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "dmlh chunk";
    }

    @Override
    protected void readBits(BitStreamReader c) {
        readBits(c, this);
    }

    public static void readBits(BitStreamReader c,
                                RIFFHeaderDmlh d) {
        c.enterBlock(d);

        RIFFHeader.readBits(c, d);

        d.dwTotalFrames = c.readUIntR(32, d.dwTotalFrames);

        c.leaveBlock();
    }

    @Override
    protected void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 RIFFHeaderDmlh d) {
        c.enterBlock(d);

        RIFFHeader.writeBits(c, d);

        c.writeUIntR(32, d.dwTotalFrames);

        c.leaveBlock();
    }
}
