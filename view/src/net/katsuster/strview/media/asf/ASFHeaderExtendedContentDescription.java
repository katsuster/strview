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
 */
public class ASFHeaderExtendedContentDescription<T extends LargeList<?>>
        extends ASFHeader<T>
        implements Cloneable {
    public UIntR content_descriptors_count;
    public List<ContentDescriptor<T>> content_descriptors;

    public ASFHeaderExtendedContentDescription() {
        content_descriptors_count = new UIntR("Content Descriptors Count");
        content_descriptors = new ArrayList<>();
    }

    @Override
    public ASFHeaderExtendedContentDescription<T> clone()
            throws CloneNotSupportedException {
        ASFHeaderExtendedContentDescription<T> obj = (ASFHeaderExtendedContentDescription<T>)super.clone();

        obj.content_descriptors_count = (UIntR)content_descriptors_count.clone();
        obj.content_descriptors = new ArrayList<>();
        for (ContentDescriptor<T> i : content_descriptors) {
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
    public void read(StreamReader<?> c) {
        read(c, this);
    }

    public static void read(StreamReader<?> c,
                            ASFHeaderExtendedContentDescription d) {
        c.enterBlock(d);

        ASFHeader.read(c, d);

        d.content_descriptors_count = c.readUIntR(16, d.content_descriptors_count);
        checkNegative(d.content_descriptors_count);

        d.content_descriptors = readObjectList(c, d.content_descriptors_count.intValue(),
                d.content_descriptors, ContentDescriptor.class);

        c.leaveBlock();
    }

    @Override
    public void write(StreamWriter<?> c) {
        write(c, this);
    }

    public static void write(StreamWriter<?> c,
                             ASFHeaderExtendedContentDescription d) {
        c.enterBlock(d);

        ASFHeader.write(c, d);

        c.writeUIntR(16, d.content_descriptors_count);

        writeObjectList(c, d.content_descriptors_count.intValue(),
                d.content_descriptors, "Content Descriptors");

        c.leaveBlock();
    }
}
