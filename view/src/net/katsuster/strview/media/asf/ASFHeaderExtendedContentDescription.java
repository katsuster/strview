package net.katsuster.strview.media.asf;

import java.util.*;

import net.katsuster.strview.util.bit.*;
import net.katsuster.strview.media.bit.*;

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
 */
public class ASFHeaderExtendedContentDescription
        extends ASFHeader
        implements Cloneable {
    public UIntR content_descriptors_count;
    public List<ContentDescriptor> content_descriptors;

    public ASFHeaderExtendedContentDescription() {
        content_descriptors_count = new UIntR("Content Descriptors Count");
        content_descriptors = new ArrayList<>();
    }

    @Override
    public ASFHeaderExtendedContentDescription clone()
            throws CloneNotSupportedException {
        ASFHeaderExtendedContentDescription obj = (ASFHeaderExtendedContentDescription)super.clone();

        obj.content_descriptors_count = (UIntR)content_descriptors_count.clone();
        obj.content_descriptors = new ArrayList<>();
        for (ContentDescriptor i : content_descriptors) {
            obj.content_descriptors.add(i.clone());
        }

        return obj;
    }

    @Override
    public String getTypeName() {
        return "Extended Content Description Object";
    }

    @Override
    public boolean isRecursive() {
        return false;
    }

    @Override
    protected void readBits(BitStreamReader c) {
        readBits(c, this);
    }

    public static void readBits(BitStreamReader c,
                                ASFHeaderExtendedContentDescription d) {
        c.enterBlock(d);

        ASFHeader.readBits(c, d);

        d.content_descriptors_count = c.readUIntR(16, d.content_descriptors_count);
        checkNegative(d.content_descriptors_count);

        d.content_descriptors = readObjectList(c, d.content_descriptors_count.intValue(),
                d.content_descriptors, ContentDescriptor.class);

        c.leaveBlock();
    }

    @Override
    protected void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 ASFHeaderExtendedContentDescription d) {
        c.enterBlock(d);

        ASFHeader.writeBits(c, d);

        c.writeUIntR(16, d.content_descriptors_count);

        writeObjectList(c, d.content_descriptors_count.intValue(),
                d.content_descriptors, "Content Descriptors");

        c.leaveBlock();
    }
}
