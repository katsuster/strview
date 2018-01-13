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
 */
public class M2VHeader<T extends LargeList<?>>
        extends BlockAdapter<T>
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
    public String getTypeName() {
        return "header";
    }

    @Override
    public void read(StreamReader<?> c) {
        read(c, this);
    }

    public static void read(StreamReader<?> c,
                            M2VHeader d) {
        c.enterBlock(d);

        d.start_code = c.readUInt(32, d.start_code);

        c.leaveBlock();
    }

    @Override
    public void write(StreamWriter<?> c) {
        write(c, this);
    }

    public static void write(StreamWriter<?> c,
                             M2VHeader d) {
        c.enterBlock(d);

        c.writeUInt(32, d.start_code, "start_code", d.getStartCodeName());

        c.leaveBlock();
    }

    public String getStartCodeName() {
        return M2VConsts.getStartCodeName(start_code.intValue());
    }
}
