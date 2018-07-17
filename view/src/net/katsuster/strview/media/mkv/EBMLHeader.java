package net.katsuster.strview.media.mkv;

import net.katsuster.strview.media.*;
import net.katsuster.strview.util.*;

/**
 * <p>
 * EBML(Extensible Binary Meta Language) タグヘッダ。
 * </p>
 */
public class EBMLHeader
        extends BitBlockAdapter {
    public EBMLvid tag_id;
    public EBMLvalue tag_len;

    public EBMLHeader() {
        tag_id = new EBMLvid("tag_id");
        tag_len = new EBMLvalue("tag_len");
    }

    @Override
    public EBMLHeader clone()
            throws CloneNotSupportedException {
        EBMLHeader obj = (EBMLHeader)super.clone();

        obj.tag_id = tag_id.clone();
        obj.tag_len = tag_len.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "EBML tag header";
    }

    @Override
    protected void readBits(BitStreamReader c) {
        readBits(c, this);
    }

    public static void readBits(BitStreamReader c,
                                EBMLHeader d) {
        c.enterBlock(d);

        d.tag_id.read(c);
        d.tag_len.read(c);

        c.leaveBlock();
    }

    @Override
    protected void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 EBMLHeader d) {
        c.enterBlock(d);

        c.mark("tag_id", "");
        d.tag_id.write(c);
        c.mark("tag_length", "");
        d.tag_len.write(c);

        c.leaveBlock();
    }
}
