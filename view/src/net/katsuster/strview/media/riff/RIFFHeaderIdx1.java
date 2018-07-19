package net.katsuster.strview.media.riff;

import java.util.*;

import net.katsuster.strview.media.bit.*;

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
public class RIFFHeaderIdx1
        extends RIFFHeader
        implements Cloneable {
    public List<IndexEntry> aIndex;

    public RIFFHeaderIdx1() {
        aIndex = new ArrayList<>();
    }

    @Override
    public RIFFHeaderIdx1 clone()
            throws CloneNotSupportedException {
        RIFFHeaderIdx1 obj = (RIFFHeaderIdx1)super.clone();

        obj.aIndex = new ArrayList<>();
        for (IndexEntry e : aIndex) {
            obj.aIndex.add(e.clone());
        }

        return obj;
    }

    @Override
    public String getTypeName() {
        return "idx1 chunk";
    }

    @Override
    protected void readBits(BitStreamReader c) {
        readBits(c, this);
    }

    public static void readBits(BitStreamReader c,
                                RIFFHeaderIdx1 d) {
        c.enterBlock(d);

        RIFFHeader.readBits(c, d);

        //16 is size of IndexEntry
        int cnt = d.ckSize.intValue() / 16;
        d.aIndex = readObjectList(c, cnt, d.aIndex,
                IndexEntry.class);

        c.leaveBlock();
    }

    @Override
    protected void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 RIFFHeaderIdx1 d) {
        c.enterBlock(d);

        RIFFHeader.writeBits(c, d);

        writeObjectList(c, d.aIndex.size(), d.aIndex,
                "aIndex");

        c.leaveBlock();
    }
}
