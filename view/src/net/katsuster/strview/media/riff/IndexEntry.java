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
    public UIntR dwChunkId;
    public UIntR dwFlags;
    public UIntR dwOffset;
    public UIntR dwSize;

    public IndexEntry() {
        dwChunkId = new UIntR();
        dwFlags = new UIntR();
        dwOffset = new UIntR();
        dwSize = new UIntR();
    }

    @Override
    public IndexEntry clone()
            throws CloneNotSupportedException {
        IndexEntry obj = (IndexEntry)super.clone();

        obj.dwChunkId = (UIntR)dwChunkId.clone();
        obj.dwFlags = (UIntR)dwFlags.clone();
        obj.dwOffset = (UIntR)dwOffset.clone();
        obj.dwSize = (UIntR)dwSize.clone();

        return obj;
    }

    @Override
    public void read(StreamReader<?> c) {
        read(c, this);
    }

    public static void read(StreamReader<?> c,
                            IndexEntry d) {
        c.enterBlock("entry of idx1 chunk");

        d.dwChunkId = c.readUIntR(32, d.dwChunkId);
        d.dwFlags   = c.readUIntR(32, d.dwFlags  );
        d.dwOffset  = c.readUIntR(32, d.dwOffset );
        d.dwSize    = c.readUIntR(32, d.dwSize   );

        c.leaveBlock();
    }

    @Override
    public void write(StreamWriter<?> c) {
        write(c, this);
    }

    public static void write(StreamWriter<?> c,
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
