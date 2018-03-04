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
 */
public class IndexEntry<T extends LargeList<?>>
        extends BlockAdapter<T> {
    public UIntR dwChunkId;
    public UIntR dwFlags;
    public UIntR dwOffset;
    public UIntR dwSize;

    public IndexEntry() {
        this(null);
    }

    public IndexEntry(String n) {
        super(n);

        dwChunkId = new UIntR("dwChunkId");
        dwFlags   = new UIntR("dwFlags"  );
        dwOffset  = new UIntR("dwOffset" );
        dwSize    = new UIntR("dwSize"   );
    }

    @Override
    public IndexEntry<T> clone()
            throws CloneNotSupportedException {
        IndexEntry<T> obj = (IndexEntry<T>)super.clone();

        obj.dwChunkId = (UIntR)dwChunkId.clone();
        obj.dwFlags = (UIntR)dwFlags.clone();
        obj.dwOffset = (UIntR)dwOffset.clone();
        obj.dwSize = (UIntR)dwSize.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "entry of idx1 chunk";
    }

    @Override
    public void read(StreamReader<?, ?> c) {
        read(c, this);
    }

    public static void read(StreamReader<?, ?> c,
                            IndexEntry d) {
        c.enterBlock(d);

        d.dwChunkId = c.readUIntR(32, d.dwChunkId);
        d.dwFlags   = c.readUIntR(32, d.dwFlags  );
        d.dwOffset  = c.readUIntR(32, d.dwOffset );
        d.dwSize    = c.readUIntR(32, d.dwSize   );

        c.leaveBlock();
    }

    @Override
    public void write(StreamWriter<?, ?> c) {
        write(c, this);
    }

    public static void write(StreamWriter<?, ?> c,
                             IndexEntry d) {
        c.enterBlock(d);

        c.writeUIntR(32, d.dwChunkId, d.getChunkIdName());
        c.writeUIntR(32, d.dwFlags  );
        c.writeUIntR(32, d.dwOffset );
        c.writeUIntR(32, d.dwSize   );

        c.leaveBlock();
    }

    public String getChunkIdName() {
        return RIFFConsts.getChunkIdName(dwChunkId.intValue());
    }
}
