package net.katsuster.strview.media.test;

import net.katsuster.strview.media.*;
import net.katsuster.strview.util.*;

/**
 * <p>
 * ソースパケットヘッダ。
 * </p>
 */
public class SrcHeader<T extends LargeList<?>>
        extends BlockAdapter<T>
        implements Cloneable {
    private String name;

    public SrcHeader(String n) {
        name = n;
    }

    @Override
    public SrcHeader<T> clone()
            throws CloneNotSupportedException {
        SrcHeader<T> obj = (SrcHeader<T>)super.clone();

        obj.name = name;

        return obj;
    }

    @Override
    public String getTypeName() {
        return "Source Packet";
    }

    @Override
    public void read(StreamReader<?> c) {
        read(c, this);
    }

    public static void read(StreamReader<?> c,
                            SrcHeader d) {
        c.enterBlock(d);

        c.leaveBlock();
    }

    @Override
    public void write(StreamWriter<?> c) {
        write(c, this);
    }

    public static void write(StreamWriter<?> c,
                             SrcHeader d) {
        c.enterBlock(d);

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
