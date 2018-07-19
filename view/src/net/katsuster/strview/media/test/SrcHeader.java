package net.katsuster.strview.media.test;

import net.katsuster.strview.media.bit.*;

/**
 * <p>
 * ソースパケットヘッダ。
 * </p>
 */
public class SrcHeader
        extends BitBlockAdapter
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
    public String getTypeName() {
        return "Source Packet";
    }

    @Override
    protected void readBits(BitStreamReader c) {
        readBits(c, this);
    }

    public static void readBits(BitStreamReader c,
                                SrcHeader d) {
        c.enterBlock(d);

        c.leaveBlock();
    }

    @Override
    protected void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
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
