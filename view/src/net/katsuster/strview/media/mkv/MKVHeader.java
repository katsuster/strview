package net.katsuster.strview.media.mkv;

import net.katsuster.strview.media.*;
import net.katsuster.strview.media.mkv.MKVConsts.*;

/**
 * <p>
 * Matroska タグヘッダ。
 * </p>
 *
 * <p>
 * 参考規格
 * </p>
 * <ul>
 * <li>Matroska: http://www.matroska.org/technical/specs/index.html</li>
 * </ul>
 *
 * @author katsuhiro
 */
public class MKVHeader extends EBMLHeader {
    //タグの定義
    protected MKVTagSpec tag_spec;

    public MKVHeader() {
        //Do nothing
    }

    @Override
    public MKVHeader clone()
            throws CloneNotSupportedException {
        MKVHeader obj = (MKVHeader)super.clone();

        return obj;
    }

    public boolean isMaster() {
        long id;

        id = tag_id.getValue();
        if (MKVConsts.getTagSpec(id).type == TAG_TYPE.MASTER) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void read(PacketReader<?> c) {
        read(c, this);
    }

    public static void read(PacketReader<?> c,
                            MKVHeader d) {
        c.enterBlock("Matroska tag header");

        EBMLHeader.read(c, d);

        //ID と一致するタグの定義を得る
        d.tag_spec = MKVConsts.getTagSpec(
                d.tag_id.getValue());

        c.leaveBlock();
    }

    @Override
    public void write(PacketWriter<?> c) {
        write(c, this);
    }

    public static void write(PacketWriter<?> c,
                             MKVHeader d) {
        c.enterBlock("Matroska tag header");

        EBMLHeader.write(c, d);

        c.mark("type", d.getTagType(), d.getTagTypeName());
        c.mark("name", d.getTagIdName());

        c.leaveBlock();
    }

    public int getTagType() {
        if (tag_spec == null) {
            return 0;
        } else {
            return tag_spec.type;
        }
    }

    public String getTagTypeName() {
        if (tag_spec == null) {
            return "(unknown)";
        } else {
            return MKVConsts.getTagTypeName(tag_spec.type);
        }
    }

    public String getTagIdName() {
        if (tag_spec == null) {
            return "(unknown)";
        } else {
            return tag_spec.name;
        }
    }
}
