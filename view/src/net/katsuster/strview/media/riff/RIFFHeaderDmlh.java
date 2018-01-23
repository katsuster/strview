package net.katsuster.strview.media.riff;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

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
public class RIFFHeaderDmlh<T extends LargeList<?>>
        extends RIFFHeader<T>
        implements Cloneable {
    public UIntR dwTotalFrames;

    public RIFFHeaderDmlh() {
        dwTotalFrames = new UIntR("dwTotalFrames");
    }

    @Override
    public RIFFHeaderDmlh<T> clone()
            throws CloneNotSupportedException {
        RIFFHeaderDmlh<T> obj = (RIFFHeaderDmlh<T>)super.clone();

        obj.dwTotalFrames = (UIntR)dwTotalFrames.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "dmlh chunk";
    }

    @Override
    public void read(StreamReader<?> c) {
        read(c, this);
    }

    public static void read(StreamReader<?> c,
                            RIFFHeaderDmlh d) {
        c.enterBlock(d);

        RIFFHeader.read(c, d);

        d.dwTotalFrames = c.readUIntR(32, d.dwTotalFrames);

        c.leaveBlock();
    }

    @Override
    public void write(StreamWriter<?> c) {
        write(c, this);
    }

    public static void write(StreamWriter<?> c,
                             RIFFHeaderDmlh d) {
        c.enterBlock(d);

        RIFFHeader.write(c, d);

        c.writeUIntR(32, d.dwTotalFrames);

        c.leaveBlock();
    }
}
