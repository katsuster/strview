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
public class RIFFHeaderInfo<T extends LargeList<?>>
        extends RIFFHeader<T>
        implements Cloneable {
    public LargeBitList strz;

    public RIFFHeaderInfo() {
        strz = new SubLargeBitList("strz");
    }

    @Override
    public RIFFHeaderInfo<T> clone()
            throws CloneNotSupportedException {
        RIFFHeaderInfo<T> obj = (RIFFHeaderInfo<T>)super.clone();

        obj.strz = (LargeBitList)strz.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "info chunk";
    }

    @Override
    public void read(StreamReader<?> c) {
        read(c, this);
    }

    public static void read(StreamReader<?> c,
                            RIFFHeaderInfo d) {
        c.enterBlock(d);

        RIFFHeader.read(c, d);

        checkNegative(d.ckSize);
        d.strz = c.readBitList(d.ckSize.intValue() << 3, d.strz);

        c.leaveBlock();
    }

    @Override
    public void write(StreamWriter<?> c) {
        write(c, this);
    }

    public static void write(StreamWriter<?> c,
                             RIFFHeaderInfo d) {
        c.enterBlock(d);

        RIFFHeader.write(c, d);

        c.writeBitList(32, d.strz, d.getStringName());

        c.leaveBlock();
    }

    public String getStringName() {
        return getArrayName(strz, "US-ASCII");
    }
}
