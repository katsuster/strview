package net.katsuster.strview.media.ps;

import net.katsuster.strview.media.*;

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
 *
 * @author katsuhiro
 */
public class PSHeaderPESPadding extends PSHeaderPES
        implements Cloneable {
    public PSHeaderPESPadding() {
        //Do nothing
    }

    @Override
    public PSHeaderPESPadding clone()
            throws CloneNotSupportedException {
        PSHeaderPESPadding obj = (PSHeaderPESPadding)super.clone();

        return obj;
    }

    @Override
    public void read(PacketReader<?> c) {
        read(c, this);
    }

    public static void read(PacketReader<?> c,
                            PSHeaderPESPadding d) {
        c.enterBlock("PES padding header");

        PSHeaderPES.read(c, d);

        c.leaveBlock();
    }

    @Override
    public void write(PacketWriter<?> c) {
        write(c, this);
    }

    public static void write(PacketWriter<?> c,
                             PSHeaderPESPadding d) {
        c.enterBlock("PES padding header");

        PSHeaderPES.write(c, d);

        c.leaveBlock();
    }
}
