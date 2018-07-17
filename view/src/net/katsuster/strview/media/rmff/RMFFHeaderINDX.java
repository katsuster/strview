package net.katsuster.strview.media.rmff;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * INDX チャンクヘッダ。
 * </p>
 */
public class RMFFHeaderINDX
        extends RMFFHeader
        implements Cloneable {
    public UInt num_indices;
    public UInt stream_number;
    public UInt next_index_header;

    public RMFFHeaderINDX() {
        num_indices = new UInt("num_indices");
        stream_number = new UInt("stream_number");
        next_index_header = new UInt("next_index_header");
    }

    @Override
    public RMFFHeaderINDX clone()
            throws CloneNotSupportedException {
        RMFFHeaderINDX obj = (RMFFHeaderINDX)super.clone();

        obj.num_indices = (UInt)num_indices.clone();
        obj.stream_number = (UInt)stream_number.clone();
        obj.next_index_header = (UInt)next_index_header.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "INDX chunk";
    }

    @Override
    protected void readBits(BitStreamReader c) {
        readBits(c, this);
    }

    public static void readBits(BitStreamReader c,
                                RMFFHeaderINDX d) {
        c.enterBlock(d);

        RMFFHeader.readBits(c, d);

        if (d.object_version.intValue() == 0) {
            d.num_indices       = c.readUInt(32, d.num_indices      );
            d.stream_number     = c.readUInt(16, d.stream_number    );
            d.next_index_header = c.readUInt(32, d.next_index_header);
        }

        c.leaveBlock();
    }

    @Override
    protected void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 RMFFHeaderINDX d) {
        c.enterBlock(d);

        RMFFHeader.writeBits(c, d);

        if (d.object_version.intValue() == 0) {
            c.writeUInt(32, d.num_indices      );
            c.writeUInt(16, d.stream_number    );
            c.writeUInt(32, d.next_index_header);
        }

        c.leaveBlock();
    }
}
