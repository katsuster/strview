package net.katsuster.strview.media.rmff;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * .RMF チャンクヘッダ。
 * </p>
 *
 * @author katsuhiro
 */
public class RMFFHeaderRMF extends RMFFHeader
        implements Cloneable {
    public UInt file_version;
    public UInt num_headers;

    public RMFFHeaderRMF() {
        file_version = new UInt();
        num_headers = new UInt();
    }

    @Override
    public RMFFHeaderRMF clone()
            throws CloneNotSupportedException {
        RMFFHeaderRMF obj = (RMFFHeaderRMF)super.clone();

        obj.file_version = (UInt)file_version.clone();
        obj.num_headers = (UInt)num_headers.clone();

        return obj;
    }

    @Override
    public void read(StreamReader<?> c) {
        read(c, this);
    }

    public static void read(StreamReader<?> c,
                            RMFFHeaderRMF d) {
        c.enterBlock(".RMF chunk");

        RMFFHeader.read(c, d);

        if ((d.object_version.intValue() == 0)
                || (d.object_version.intValue() == 1)) {
            d.file_version = c.readUInt(32, d.file_version);
            d.num_headers  = c.readUInt(32, d.num_headers );
        }

        c.leaveBlock();
    }

    @Override
    public void write(StreamWriter<?> c) {
        write(c, this);
    }

    public static void write(StreamWriter<?> c,
                             RMFFHeaderRMF d) {
        c.enterBlock(".RMF chunk");

        RMFFHeader.write(c, d);

        if ((d.object_version.intValue() == 0)
                || (d.object_version.intValue() == 1)) {
            c.writeUInt(32, d.file_version, "file_version");
            c.writeUInt(32, d.num_headers , "num_headers" );
        }

        c.leaveBlock();
    }
}
