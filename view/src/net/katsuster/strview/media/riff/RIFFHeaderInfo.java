package net.katsuster.strview.media.riff;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * Children chunks of LIST(info)
 * </p>
 *
 * <p>
 * 参考規格
 * </p>
 * <ul>
 * <li>Microsoft Multimedia Standards Update: Revision 1.0.97</li>
 * <li>OpenDML AVI File Format Extensions: Version 1.02</li>
 * <li>MSDN: AVISTREAMHEADER struct</li>
 * </ul>
 */
public class RIFFHeaderInfo
        extends RIFFHeader
        implements Cloneable {
    public LargeBitList strz;

    public RIFFHeaderInfo() {
        strz = new SubLargeBitList("strz");
    }

    @Override
    public RIFFHeaderInfo clone()
            throws CloneNotSupportedException {
        RIFFHeaderInfo obj = (RIFFHeaderInfo)super.clone();

        obj.strz = (LargeBitList)strz.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "info chunk";
    }

    @Override
    protected void readBits(BitStreamReader c) {
        readBits(c, this);
    }

    public static void readBits(BitStreamReader c,
                                RIFFHeaderInfo d) {
        c.enterBlock(d);

        RIFFHeader.readBits(c, d);

        checkNegative(d.ckSize);
        d.strz = c.readBitList(d.ckSize.intValue() << 3, d.strz);

        c.leaveBlock();
    }

    @Override
    protected void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 RIFFHeaderInfo d) {
        c.enterBlock(d);

        RIFFHeader.writeBits(c, d);

        c.writeBitList(32, d.strz, d.getStringName());

        c.leaveBlock();
    }

    public String getStringName() {
        return getArrayName(strz, "US-ASCII");
    }
}
