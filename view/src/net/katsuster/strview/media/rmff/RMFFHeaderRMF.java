package net.katsuster.strview.media.rmff;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * .RMF チャンクヘッダ。
 * </p>
 */
public class RMFFHeaderRMF
        extends RMFFHeader
        implements Cloneable {
    public UInt file_version;
    public UInt num_headers;

    public RMFFHeaderRMF() {
        file_version = new UInt("file_version");
        num_headers = new UInt("num_headers");
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
    public String getTypeName() {
        return ".RMF chunk";
    }

    @Override
    protected void readBits(BitStreamReader c) {
        readBits(c, this);
    }

    public static void readBits(BitStreamReader c,
                                RMFFHeaderRMF d) {
        c.enterBlock(d);

        RMFFHeader.readBits(c, d);

        if ((d.object_version.intValue() == 0)
                || (d.object_version.intValue() == 1)) {
            d.file_version = c.readUInt(32, d.file_version);
            d.num_headers  = c.readUInt(32, d.num_headers );
        }

        c.leaveBlock();
    }

    @Override
    protected void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 RMFFHeaderRMF d) {
        c.enterBlock(d);

        RMFFHeader.writeBits(c, d);

        if ((d.object_version.intValue() == 0)
                || (d.object_version.intValue() == 1)) {
            c.writeUInt(32, d.file_version);
            c.writeUInt(32, d.num_headers );
        }

        c.leaveBlock();
    }
}
