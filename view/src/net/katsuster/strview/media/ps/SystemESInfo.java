package net.katsuster.strview.media.ps;

import net.katsuster.strview.util.bit.*;
import net.katsuster.strview.media.bit.*;

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
public class SystemESInfo
        extends BitBlockAdapter
        implements Cloneable {
    public UInt stream_id;
    public UInt reserved1; //must be '11'
    public UInt p_std_buffer_bound_scale;
    public UInt p_std_buffer_size_bound;

    public SystemESInfo() {
        this(null);
    }

    public SystemESInfo(String n) {
        super(n);

        stream_id = new UInt("stream_id");
        reserved1 = new UInt("reserved1");
        p_std_buffer_bound_scale = new UInt("P-STD_buffer_bound_scale");
        p_std_buffer_size_bound  = new UInt("P-STD_buffer_size_bound");
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
    public String getTypeName() {
        return "PS system_header es_info";
    }

    @Override
    protected void readBits(BitStreamReader c) {
        readBits(c, this);
    }

    public static void readBits(BitStreamReader c,
                                SystemESInfo d) {
        c.enterBlock(d);

        d.stream_id                = c.readUInt( 8, d.stream_id               );
        d.reserved1                = c.readUInt( 2, d.reserved1               );
        d.p_std_buffer_bound_scale = c.readUInt( 1, d.p_std_buffer_bound_scale);
        d.p_std_buffer_size_bound  = c.readUInt(13, d.p_std_buffer_size_bound );

        c.leaveBlock();
    }

    @Override
    protected void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 SystemESInfo d) {
        c.enterBlock(d);

        c.writeUInt( 8, d.stream_id               , d.getStreamIdNameSystemHeader());
        c.writeUInt( 2, d.reserved1               );
        c.writeUInt( 1, d.p_std_buffer_bound_scale);
        c.writeUInt(13, d.p_std_buffer_size_bound );
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
