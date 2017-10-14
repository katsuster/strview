package net.katsuster.strview.media.rmff;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * INDX チャンクヘッダ。
 * </p>
 *
 * @author katsuhiro
 */
public class RMFFHeaderINDX extends RMFFHeader
        implements Cloneable {
    public UInt num_indices;
    public UInt stream_number;
    public UInt next_index_header;

    public RMFFHeaderINDX() {
        num_indices = new UInt();
        stream_number = new UInt();
        next_index_header = new UInt();
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
    public void read(StreamReader<?> c) {
        read(c, this);
    }

    public static void read(StreamReader<?> c,
                            RMFFHeaderINDX d) {
        c.enterBlock("INDX chunk");

        RMFFHeader.read(c, d);

        if (d.object_version.intValue() == 0) {
            d.num_indices       = c.readUInt(32, d.num_indices      );
            d.stream_number     = c.readUInt(16, d.stream_number    );
            d.next_index_header = c.readUInt(32, d.next_index_header);
        }

        c.leaveBlock();
    }

    @Override
    public void write(StreamWriter<?> c) {
        write(c, this);
    }

    public static void write(StreamWriter<?> c,
                             RMFFHeaderINDX d) {
        c.enterBlock("INDX chunk");

        RMFFHeader.write(c, d);

        if (d.object_version.intValue() == 0) {
            c.writeUInt(32, d.num_indices      , "num_indices"      );
            c.writeUInt(16, d.stream_number    , "stream_number"    );
            c.writeUInt(32, d.next_index_header, "next_index_header");
        }

        c.leaveBlock();
    }
}
