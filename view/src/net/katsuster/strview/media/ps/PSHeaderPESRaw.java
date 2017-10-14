package net.katsuster.strview.media.ps;

import net.katsuster.strview.media.*;

/**
 * <p>
 * MPEG2 PES (Packetized Elementary Stream) header (has no header)
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
 *
 * @author katsuhiro
 */
public class PSHeaderPESRaw extends PSHeaderPES
        implements Cloneable {
    public PSHeaderPESRaw() {
        //Do nothing
    }

    @Override
    public PSHeaderPESRaw clone()
            throws CloneNotSupportedException {
        PSHeaderPESRaw obj = (PSHeaderPESRaw)super.clone();

        return obj;
    }

    @Override
    public void read(StreamReader<?> c) {
        read(c, this);
    }

    public static void read(StreamReader<?> c,
                            PSHeaderPESRaw d) {
        c.enterBlock("PES raw header");

        PSHeaderPES.read(c, d);

        c.leaveBlock();
    }

    @Override
    public void write(StreamWriter<?> c) {
        write(c, this);
    }

    public static void write(StreamWriter<?> c,
                             PSHeaderPESRaw d) {
        c.enterBlock("PES raw header");

        PSHeaderPES.write(c, d);

        c.leaveBlock();
    }
}
