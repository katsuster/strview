package net.katsuster.strview.media.m2v;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * MPEG2 Video ヘッダ。
 * </p>
 *
 * <p>
 * 参考規格
 * </p>
 * <ul>
 * <li>ITU-T H.262, ISO/IEC 13818-2:
 * Information technology - Generic coding of moving pictures and
 * associated audio information: Video</li>
 * </ul>
 *
 * @author katsuhiro
 */
public class M2VHeader extends BlockAdapter
        implements Cloneable {
    public UInt start_code;

    public M2VHeader() {
        start_code = new UInt();
    }

    @Override
    public M2VHeader clone()
            throws CloneNotSupportedException {
        M2VHeader obj = (M2VHeader)super.clone();

        obj.start_code = (UInt)start_code.clone();

        return obj;
    }

    @Override
    public void read(PacketReader<?> c) {
        read(c, this);
    }

    public static void read(PacketReader<?> c,
                            M2VHeader d) {
        c.enterBlock("M2V header");

        d.start_code = c.readUInt(32, d.start_code);

        c.leaveBlock();
    }

    @Override
    public void write(PacketWriter<?> c) {
        write(c, this);
    }

    public static void write(PacketWriter<?> c,
                             M2VHeader d) {
        c.enterBlock("M2V header");

        c.writeUInt(32, d.start_code, "start_code", d.getStartCodeName());

        c.leaveBlock();
    }

    public String getStartCodeName() {
        return M2VConsts.getStartCodeName(start_code.intValue());
    }
}
