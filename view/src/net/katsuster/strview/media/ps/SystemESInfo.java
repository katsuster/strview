package net.katsuster.strview.media.ps;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * MPEG2 PS (Program Stream) system_header
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
public class SystemESInfo<T extends LargeList<?>>
        extends BlockAdapter<T>
        implements Cloneable {
    public UInt stream_id;
    public UInt reserved1; //must be '11'
    public UInt p_std_buffer_bound_scale;
    public UInt p_std_buffer_size_bound;

    public SystemESInfo() {
        stream_id = new UInt();
        reserved1 = new UInt();
        p_std_buffer_bound_scale = new UInt();
        p_std_buffer_size_bound = new UInt();
    }

    @Override
    public SystemESInfo clone()
            throws CloneNotSupportedException {
        SystemESInfo obj = (SystemESInfo)super.clone();

        stream_id = (UInt)stream_id.clone();
        reserved1 = (UInt)reserved1.clone();
        p_std_buffer_bound_scale = (UInt)p_std_buffer_bound_scale.clone();
        p_std_buffer_size_bound = (UInt)p_std_buffer_size_bound.clone();

        return obj;
    }

    @Override
    public void read(StreamReader<?> c) {
        read(c, this);
    }

    public static void read(StreamReader<?> c,
                            SystemESInfo d) {
        c.enterBlock("PS system_header es_info");

        d.stream_id                = c.readUInt( 8, d.stream_id               );
        d.reserved1                = c.readUInt( 2, d.reserved1               );
        d.p_std_buffer_bound_scale = c.readUInt( 1, d.p_std_buffer_bound_scale);
        d.p_std_buffer_size_bound  = c.readUInt(13, d.p_std_buffer_size_bound );

        c.leaveBlock();
    }

    @Override
    public void write(StreamWriter<?> c) {
        write(c, this);
    }

    public static void write(StreamWriter<?> c,
                             SystemESInfo d) {
        c.enterBlock("PS system_header es_info");

        c.writeUInt( 8, d.stream_id               , "stream_id"               , d.getStreamIdNameSystemHeader());
        c.writeUInt( 2, d.reserved1               , "reserved1"               );
        c.writeUInt( 1, d.p_std_buffer_bound_scale, "P-STD_buffer_bound_scale");
        c.writeUInt(13, d.p_std_buffer_size_bound , "P-STD_buffer_size_bound" );
        c.mark("P-STD_buffer_size", d.getStdBufferSize());

        c.leaveBlock();
    }

    public String getStreamIdNameSystemHeader() {
        return PSConsts.getStreamIdNameSystemHeader(stream_id.intValue());
    }

    public long getStdBufferSize() {
        return getStdBufferSize(p_std_buffer_bound_scale.intValue(),
                p_std_buffer_size_bound.intValue());
    }

    public static long getStdBufferSize(int scale, int bound) {
        if (scale == 0) {
            return bound * 128;
        } else {
            return bound * 1024;
        }
    }
}
