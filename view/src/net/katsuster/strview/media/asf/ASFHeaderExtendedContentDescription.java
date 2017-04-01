package net.katsuster.strview.media.asf;

import java.util.*;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * ASF (Advanced Systems Format) Extended Content Description Object
 * </p>
 *
 * <p>
 * 参考規格
 * </p>
 * <ul>
 * <li>Advanced Systems Format (ASF) Specification: Revision 01.20.06</li>
 * </ul>
 *
 * @author katsuhiro
 */
public class ASFHeaderExtendedContentDescription extends ASFHeader
        implements Cloneable {
    public UInt content_descriptors_count;
    public List<ContentDescriptor> content_descriptors;

    public ASFHeaderExtendedContentDescription() {
        content_descriptors_count = new UInt();
        content_descriptors = new ArrayList<>();
    }

    @Override
    public ASFHeaderExtendedContentDescription clone()
            throws CloneNotSupportedException {
        ASFHeaderExtendedContentDescription obj = (ASFHeaderExtendedContentDescription)super.clone();

        obj.content_descriptors_count = (UInt)content_descriptors_count.clone();
        obj.content_descriptors = new ArrayList<>();
        for (ContentDescriptor i : content_descriptors) {
            obj.content_descriptors.add(i.clone());
        }

        return obj;
    }

    @Override
    public boolean isRecursive() {
        return false;
    }

    @Override
    public void read(PacketReader<?> c) {
        read(c, this);
    }

    public static void read(PacketReader<?> c,
                            ASFHeaderExtendedContentDescription d) {
        c.enterBlock("Extended Content Description Object");

        ASFHeader.read(c, d);

        d.content_descriptors_count = c.readUIntR(16, d.content_descriptors_count);
        checkNegative("Content Descriptors Count", d.content_descriptors_count);

        d.content_descriptors = readObjectList(c, d.content_descriptors_count.intValue(),
                d.content_descriptors, ContentDescriptor.class);

        c.leaveBlock();
    }

    @Override
    public void write(PacketWriter<?> c) {
        write(c, this);
    }

    public static void write(PacketWriter<?> c,
                             ASFHeaderExtendedContentDescription d) {
        c.enterBlock("Extended Content Description Object");

        ASFHeader.write(c, d);

        c.writeUIntR(16, d.content_descriptors_count, "Content Descriptors Count");

        writeObjectList(c, d.content_descriptors_count.intValue(),
                d.content_descriptors, "Content Descriptors");

        c.leaveBlock();
    }
}
