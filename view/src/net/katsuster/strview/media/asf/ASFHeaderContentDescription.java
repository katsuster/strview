package net.katsuster.strview.media.asf;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * ASF (Advanced Systems Format) Content Description Object
 * </p>
 *
 * <p>
 * 参考規格
 * </p>
 * <ul>
 * <li>Advanced Systems Format (ASF) Specification: Revision 01.20.06</li>
 * </ul>
 */
public class ASFHeaderContentDescription<T extends LargeList<?>>
        extends ASFHeader<T>
        implements Cloneable {
    public UIntR title_length;
    public UIntR author_length;
    public UIntR copyright_length;
    public UIntR description_length;
    public UIntR rating_length;
    public LargeBitList title;
    public LargeBitList author;
    public LargeBitList copyright;
    public LargeBitList description;
    public LargeBitList rating;

    public ASFHeaderContentDescription() {
        title_length       = new UIntR("Title Length"      );
        author_length      = new UIntR("Author Length"     );
        copyright_length   = new UIntR("Copyright Length"  );
        description_length = new UIntR("Description Length");
        rating_length      = new UIntR("Rating Length"     );
        title              = new SubLargeBitList("Title"      );
        author             = new SubLargeBitList("Author"     );
        copyright          = new SubLargeBitList("Copyright"  );
        description        = new SubLargeBitList("Description");
        rating             = new SubLargeBitList("Rating"     );
    }

    @Override
    public ASFHeaderContentDescription<T> clone()
            throws CloneNotSupportedException {
        ASFHeaderContentDescription<T> obj = (ASFHeaderContentDescription<T>)super.clone();

        obj.title_length = (UIntR)title_length.clone();
        obj.author_length = (UIntR)author_length.clone();
        obj.copyright_length = (UIntR)copyright_length.clone();
        obj.description_length = (UIntR)description_length.clone();
        obj.rating_length = (UIntR)rating_length.clone();
        obj.title = (LargeBitList)title.clone();
        obj.author = (LargeBitList)author.clone();
        obj.copyright = (LargeBitList)copyright.clone();
        obj.description = (LargeBitList)description.clone();
        obj.rating = (LargeBitList)rating.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "Content Description Object";
    }

    @Override
    public boolean isRecursive() {
        return false;
    }

    @Override
    public void read(StreamReader<?, ?> c) {
        read(c, this);
    }

    public static void read(StreamReader<?, ?> c,
                            ASFHeaderContentDescription d) {
        c.enterBlock(d);

        ASFHeader.read(c, d);

        d.title_length       = c.readUIntR(16, d.title_length      );
        checkNegative(d.title_length);
        d.author_length      = c.readUIntR(16, d.author_length     );
        checkNegative(d.author_length);
        d.copyright_length   = c.readUIntR(16, d.copyright_length  );
        checkNegative(d.copyright_length);
        d.description_length = c.readUIntR(16, d.description_length);
        checkNegative(d.description_length);
        d.rating_length      = c.readUIntR(16, d.rating_length     );
        checkNegative(d.rating_length);

        d.title       = c.readBitList(d.title_length.intValue() << 3      , d.title);
        d.author      = c.readBitList(d.author_length.intValue() << 3     , d.author);
        d.copyright   = c.readBitList(d.copyright_length.intValue() << 3  , d.copyright);
        d.description = c.readBitList(d.description_length.intValue() << 3, d.description);
        d.rating      = c.readBitList(d.rating_length.intValue() << 3     , d.rating);

        c.leaveBlock();
    }

    @Override
    public void write(StreamWriter<?, ?> c) {
        write(c, this);
    }

    public static void write(StreamWriter<?, ?> c,
                             ASFHeaderContentDescription d) {
        c.enterBlock(d);

        ASFHeader.write(c, d);

        c.writeUIntR(16, d.title_length      );
        c.writeUIntR(16, d.author_length     );
        c.writeUIntR(16, d.copyright_length  );
        c.writeUIntR(16, d.description_length);
        c.writeUIntR(16, d.rating_length     );

        c.writeBitList(d.title_length.intValue() << 3      , d.title      , d.getTitleName());
        c.writeBitList(d.author_length.intValue() << 3     , d.author     , d.getAuthorName());
        c.writeBitList(d.copyright_length.intValue() << 3  , d.copyright  , d.getCopyrightName());
        c.writeBitList(d.description_length.intValue() << 3, d.description, d.getDescriptionName());
        c.writeBitList(d.rating_length.intValue() << 3     , d.rating     , d.getRatingName());

        c.leaveBlock();
    }

    public String getTitleName() {
        return ASFHeader.getWcharName(title);
    }

    public String getAuthorName() {
        return ASFHeader.getWcharName(author);
    }

    public String getCopyrightName() {
        return ASFHeader.getWcharName(copyright);
    }

    public String getDescriptionName() {
        return ASFHeader.getWcharName(description);
    }

    public String getRatingName() {
        return ASFHeader.getWcharName(rating);
    }
}
