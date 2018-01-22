package net.katsuster.strview.media.ps;

import net.katsuster.strview.media.*;
import net.katsuster.strview.util.*;

/**
 * <p>
 * MPEG2 PES (Packetized Elementary Stream) header (padding bytes)
 * </p>
 *
 * <p>
 * 参考規格
 * </p>
 * <ul>
 * <li>ITU-T H.222, ISO/IEC 13818-1:
 * Information technology – Generic coding of moving pictures and
 * associated audio information: Systems</li>
 * </ul>
 */
public class PSHeaderPESPadding<T extends LargeList<?>>
        extends PSHeaderPES<T>
        implements Cloneable {
    public PSHeaderPESPadding() {
        //Do nothing
    }

    @Override
    public PSHeaderPESPadding<T> clone()
            throws CloneNotSupportedException {
        PSHeaderPESPadding<T> obj = (PSHeaderPESPadding<T>)super.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "PES padding header";
    }

    @Override
    public void read(StreamReader<?> c) {
        read(c, this);
    }

    public static void read(StreamReader<?> c,
                            PSHeaderPESPadding d) {
        c.enterBlock(d);

        PSHeaderPES.read(c, d);

        c.leaveBlock();
    }

    @Override
    public void write(StreamWriter<?> c) {
        write(c, this);
    }

    public static void write(StreamWriter<?> c,
                             PSHeaderPESPadding d) {
        c.enterBlock(d);

        PSHeaderPES.write(c, d);

        c.leaveBlock();
    }
}
