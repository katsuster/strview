package net.katsuster.strview.media.riff;

import java.util.*;

import net.katsuster.strview.media.*;
import net.katsuster.strview.util.*;

/**
 * <p>
 * idx1 chunk
 * </p>
 *
 * <p>
 * 参考規格
 * </p>
 * <ul>
 * <li>MSDN: AVIOLDINDEX struct</li>
 * </ul>
 */
public class RIFFHeaderIdx1<T extends LargeList<?>>
        extends RIFFHeader<T>
        implements Cloneable {
    public List<IndexEntry<T>> aIndex;

    public RIFFHeaderIdx1() {
        aIndex = new ArrayList<>();
    }

    @Override
    public RIFFHeaderIdx1<T> clone()
            throws CloneNotSupportedException {
        RIFFHeaderIdx1<T> obj = (RIFFHeaderIdx1<T>)super.clone();

        obj.aIndex = new ArrayList<>();
        for (IndexEntry<T> e : aIndex) {
            obj.aIndex.add(e.clone());
        }

        return obj;
    }

    @Override
    public String getTypeName() {
        return "idx1 chunk";
    }

    @Override
    public void read(StreamReader<?> c) {
        read(c, this);
    }

    public static void read(StreamReader<?> c,
                            RIFFHeaderIdx1 d) {
        c.enterBlock(d);

        RIFFHeader.read(c, d);

        //16 is size of IndexEntry
        int cnt = d.ckSize.intValue() / 16;
        d.aIndex = readObjectList(c, cnt, d.aIndex,
                IndexEntry.class);

        c.leaveBlock();
    }

    @Override
    public void write(StreamWriter<?> c) {
        write(c, this);
    }

    public static void write(StreamWriter<?> c,
                             RIFFHeaderIdx1 d) {
        c.enterBlock(d);

        RIFFHeader.write(c, d);

        writeObjectList(c, d.aIndex.size(), d.aIndex,
                "aIndex");

        c.leaveBlock();
    }
}
