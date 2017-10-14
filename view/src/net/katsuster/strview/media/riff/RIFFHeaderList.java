package net.katsuster.strview.media.riff;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * 入れ子にできる RIFF (Resource Interchange File Format) チャンクのヘッダ。
 * </p>
 *
 * <p>
 * 参考規格
 * </p>
 * <ul>
 * <li>Microsoft Multimedia Standards Update: Revision 1.0.97</li>
 * <li>OpenDML AVI File Format Extensions: Version 1.02</li>
 * </ul>
 *
 * @author katsuhiro
 */
public class RIFFHeaderList extends RIFFHeader
        implements Cloneable {
    public UIntR listType;

    public RIFFHeaderList() {
        listType = new UIntR();
    }

    @Override
    public RIFFHeaderList clone()
            throws CloneNotSupportedException {
        RIFFHeaderList obj = (RIFFHeaderList)super.clone();

        obj.listType = (UIntR)listType.clone();

        return obj;
    }

    @Override
    public boolean isRecursive() {
        return true;
    }

    @Override
    public void read(StreamReader<?> c) {
        read(c, this);
    }

    public static void read(StreamReader<?> c,
                            RIFFHeaderList d) {
        c.enterBlock("LIST chunk");

        RIFFHeader.read(c, d);

        d.listType = c.readUIntR(32, d.listType);

        c.leaveBlock();
    }

    @Override
    public void write(StreamWriter<?> c) {
        write(c, this);
    }

    public static void write(StreamWriter<?> c,
                             RIFFHeaderList d) {
        c.enterBlock("LIST chunk");

        RIFFHeader.write(c, d);

        c.writeUIntR(32, d.listType, "listType", d.getChunkTypeName());

        c.leaveBlock();
    }

    public String getChunkTypeName() {
        return RIFFConsts.getChunkIdName(listType.intValue());
    }
}
