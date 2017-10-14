package net.katsuster.strview.media.test;

import net.katsuster.strview.media.*;

/**
 * <p>
 * ソースパケットヘッダ。
 * </p>
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
    public void read(StreamReader<?> c) {
        read(c, this);
    }

    public static void read(StreamReader<?> c,
                            SrcHeader d) {
        c.enterBlock("Source Packet header");

        c.leaveBlock();
    }

    @Override
    public void write(StreamWriter<?> c) {
        write(c, this);
    }

    public static void write(StreamWriter<?> c,
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
