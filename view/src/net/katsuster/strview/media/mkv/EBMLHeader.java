package net.katsuster.strview.media.mkv;

import net.katsuster.strview.media.*;
import net.katsuster.strview.util.*;

/**
 * <p>
 * EBML(Extensible Binary Meta Language) タグヘッダ。
 * </p>
 */
public class EBMLHeader<T extends LargeList<?>>
        extends BlockAdapter<T> {
    public EBMLvid<T> tag_id;
    public EBMLvalue<T> tag_len;

    public EBMLHeader() {
        tag_id = new EBMLvid<>("tag_id");
        tag_len = new EBMLvalue<>("tag_len");
    }

    @Override
    public EBMLHeader<T> clone()
            throws CloneNotSupportedException {
        EBMLHeader<T> obj = (EBMLHeader<T>)super.clone();

        obj.tag_id = tag_id.clone();
        obj.tag_len = tag_len.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "EBML tag header";
    }

    @Override
    public void read(StreamReader<?, ?> c) {
        read(c, this);
    }

    public static void read(StreamReader<?, ?> c,
                            EBMLHeader d) {
        c.enterBlock(d);

        d.tag_id.read(c);
        d.tag_len.read(c);

        c.leaveBlock();
    }

    @Override
    public void write(StreamWriter<?, ?> c) {
        write(c, this);
    }

    public static void write(StreamWriter<?, ?> c,
                             EBMLHeader d) {
        c.enterBlock(d);

        c.mark("tag_id", "");
        d.tag_id.write(c);
        c.mark("tag_length", "");
        d.tag_len.write(c);

        c.leaveBlock();
    }
}
