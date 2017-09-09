package net.katsuster.strview.media.test;

import net.katsuster.strview.media.*;
import net.katsuster.strview.util.*;

/**
 * <p>
 * ソースパケットヘッダ。
 * </p>
 *
 * @author katsuhiro
 */
public class SrcHeader extends BlockAdapter
        implements Cloneable {
    private String name;

    public SrcHeader(String n) {
        name = n;
    }

    @Override
    public SrcHeader clone()
            throws CloneNotSupportedException {
        SrcHeader obj = (SrcHeader)super.clone();

        obj.name = name;

        return obj;
    }

    @Override
    public void read(PacketReader<?> c) {
        read(c, this);
    }

    public static void read(PacketReader<?> c,
                            SrcHeader d) {
        c.enterBlock("Source Packet header");

        c.leaveBlock();
    }

    @Override
    public void write(PacketWriter<?> c) {
        write(c, this);
    }

    public static void write(PacketWriter<?> c,
                             SrcHeader d) {
        c.enterBlock("Source Packet header");

        c.mark("name", d.getName());

        c.leaveBlock();
    }

    public String getName() {
        return name;
    }

    public void setName(String n) {
        name = n;
    }
}
