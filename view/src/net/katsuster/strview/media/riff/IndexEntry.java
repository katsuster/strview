package net.katsuster.strview.media.riff;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * entry of idx1 chunk
 * </p>
 *
 * <p>
 * 参考規格
 * </p>
 * <ul>
 * <li>MSDN: AVIOLDINDEX struct</li>
 * </ul>
 *
 * @author katsuhiro
 */
public class IndexEntry extends BlockAdapter {
    public UInt dwChunkId;
    public UInt dwFlags;
    public UInt dwOffset;
    public UInt dwSize;

    public IndexEntry() {
        dwChunkId = new UInt();
        dwFlags = new UInt();
        dwOffset = new UInt();
        dwSize = new UInt();
    }

    @Override
    public IndexEntry clone()
            throws CloneNotSupportedException {
        IndexEntry obj = (IndexEntry)super.clone();

        obj.dwChunkId = (UInt)dwChunkId.clone();
        obj.dwFlags = (UInt)dwFlags.clone();
        obj.dwOffset = (UInt)dwOffset.clone();
        obj.dwSize = (UInt)dwSize.clone();

        return obj;
    }

    @Override
    public void read(PacketReader<?> c) {
        read(c, this);
    }

    public static void read(PacketReader<?> c,
                            IndexEntry d) {
        c.enterBlock("entry of idx1 chunk");

        d.dwChunkId = c.readUIntR(32, d.dwChunkId);
        d.dwFlags   = c.readUIntR(32, d.dwFlags  );
        d.dwOffset  = c.readUIntR(32, d.dwOffset );
        d.dwSize    = c.readUIntR(32, d.dwSize   );

        c.leaveBlock();
    }

    @Override
    public void write(PacketWriter<?> c) {
        write(c, this);
    }

    public static void write(PacketWriter<?> c,
                             IndexEntry d) {
        c.enterBlock("entry of idx1 chunk");

        c.writeUIntR(32, d.dwChunkId, "dwChunkId", d.getChunkIdName());
        c.writeUIntR(32, d.dwFlags  , "dwFlags"  );
        c.writeUIntR(32, d.dwOffset , "dwOffset" );
        c.writeUIntR(32, d.dwSize   , "dwSize"   );

        c.leaveBlock();
    }

    public String getChunkIdName() {
        return RIFFConsts.getChunkIdName(dwChunkId.intValue());
    }
}
