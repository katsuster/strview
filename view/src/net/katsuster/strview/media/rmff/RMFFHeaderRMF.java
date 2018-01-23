package net.katsuster.strview.media.rmff;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * .RMF チャンクヘッダ。
 * </p>
 */
public class RMFFHeaderRMF<T extends LargeList<?>>
        extends RMFFHeader<T>
        implements Cloneable {
    public UInt file_version;
    public UInt num_headers;

    public RMFFHeaderRMF() {
        file_version = new UInt("file_version");
        num_headers = new UInt("num_headers");
    }

    @Override
    public RMFFHeaderRMF<T> clone()
            throws CloneNotSupportedException {
        RMFFHeaderRMF<T> obj = (RMFFHeaderRMF<T>)super.clone();

        obj.file_version = (UInt)file_version.clone();
        obj.num_headers = (UInt)num_headers.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return ".RMF chunk";
    }

    @Override
    public void read(StreamReader<?> c) {
        read(c, this);
    }

    public static void read(StreamReader<?> c,
                            RMFFHeaderRMF d) {
        c.enterBlock(d);

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
        c.enterBlock(d);

        RMFFHeader.write(c, d);

        if ((d.object_version.intValue() == 0)
                || (d.object_version.intValue() == 1)) {
            c.writeUInt(32, d.file_version);
            c.writeUInt(32, d.num_headers );
        }

        c.leaveBlock();
    }
}
