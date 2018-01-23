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
 */
public class RIFFHeaderList<T extends LargeList<?>>
        extends RIFFHeader<T>
        implements Cloneable {
    public UIntR listType;

    public RIFFHeaderList() {
        listType = new UIntR("listType");
    }

    @Override
    public RIFFHeaderList<T> clone()
            throws CloneNotSupportedException {
        RIFFHeaderList<T> obj = (RIFFHeaderList<T>)super.clone();

        obj.listType = (UIntR)listType.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "LIST chunk";
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
        c.enterBlock(d);

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
        c.enterBlock(d);

        RIFFHeader.write(c, d);

        c.writeUIntR(32, d.listType, d.getChunkTypeName());

        c.leaveBlock();
    }

    public String getChunkTypeName() {
        return RIFFConsts.getChunkIdName(listType.intValue());
    }
}
