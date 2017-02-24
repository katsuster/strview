package net.katsuster.strview.media.mkv;

import net.katsuster.strview.media.*;

/**
 * <p>
 * EBML(Extensible Binary Meta Language) タグヘッダ。
 * </p>
 *
 * @author katsuhiro
 */
public class EBMLHeader extends BlockAdapter {
    public EBMLvid tag_id;
    public EBMLvalue tag_len;

    public EBMLHeader() {
        tag_id = new EBMLvid();
        tag_len = new EBMLvalue();
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
    public void read(PacketReader<?> c) {
        read(c, this);
    }

    public static void read(PacketReader<?> c,
                            EBMLHeader d) {
        c.enterBlock("EBML tag header");

        d.tag_id.read(c);
        d.tag_len.read(c);

        c.leaveBlock();
    }

    @Override
    public void write(PacketWriter<?> c) {
        write(c, this);
    }

    public static void write(PacketWriter<?> c,
                             EBMLHeader d) {
        c.enterBlock("EBML tag header");

        c.mark("tag_id", "");
        d.tag_id.write(c);
        c.mark("tag_length", "");
        d.tag_len.write(c);

        c.leaveBlock();
    }
}
