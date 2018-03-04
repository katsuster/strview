package net.katsuster.strview.media.m4v;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;


/**
 * <p>
 * MPEG4 Part 2 Visual ヘッダ。
 * </p>
 *
 * <p>
 * 参考規格
 * </p>
 * <ul>
 * <li>ISO/IEC 14496-2:
 * Information technology - Coding of audio-visual objects
 * Part 2: Video</li>
 * </ul>
 */
public class M4VHeader<T extends LargeList<?>>
        extends BlockAdapter<T>
        implements Cloneable {
    public UInt start_code;

    public M4VHeader() {
        start_code = new UInt("start_code");
    }

    @Override
    public M4VHeader<T> clone()
            throws CloneNotSupportedException {
        M4VHeader<T> obj = (M4VHeader<T>)super.clone();

        obj.start_code = (UInt)start_code.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "M4V header";
    }

    @Override
    public void read(StreamReader<?, ?> c) {
        read(c, this);
    }

    public static void read(StreamReader<?, ?> c,
                            M4VHeader d) {
        c.enterBlock(d);

        d.start_code = c.readUInt(32, d.start_code);

        c.leaveBlock();
    }

    @Override
    public void write(StreamWriter<?, ?> c) {
        write(c, this);
    }

    public static void write(StreamWriter<?, ?> c,
                             M4VHeader d) {
        c.enterBlock(d);

        c.writeUInt(32, d.start_code, d.getStartCodeName());

        c.leaveBlock();
    }

    public String getStartCodeName() {
        return M4VConsts.getStartCodeName(start_code.intValue());
    }
}
