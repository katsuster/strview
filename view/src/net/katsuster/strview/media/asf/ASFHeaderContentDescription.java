package net.katsuster.strview.media.asf;

import net.katsuster.strview.util.*;
import net.katsuster.strview.io.*;
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
 *
 * @author katsuhiro
 */
public class ASFHeaderContentDescription extends ASFHeader
        implements Cloneable {
    public UInt title_length;
    public UInt author_length;
    public UInt copyright_length;
    public UInt description_length;
    public UInt rating_length;
    public LargeBitList title;
    public LargeBitList author;
    public LargeBitList copyright;
    public LargeBitList description;
    public LargeBitList rating;

    public ASFHeaderContentDescription() {
        title_length = new UInt();
        author_length = new UInt();
        copyright_length = new UInt();
        description_length = new UInt();
        rating_length = new UInt();
        title = new MemoryBitList();
        author = new MemoryBitList();
        copyright = new MemoryBitList();
        description = new MemoryBitList();
        rating = new MemoryBitList();
    }

    @Override
    public ASFHeaderContentDescription clone()
            throws CloneNotSupportedException {
        ASFHeaderContentDescription obj = (ASFHeaderContentDescription)super.clone();

        obj.title_length = (UInt)title_length.clone();
        obj.author_length = (UInt)author_length.clone();
        obj.copyright_length = (UInt)copyright_length.clone();
        obj.description_length = (UInt)description_length.clone();
        obj.rating_length = (UInt)rating_length.clone();
        obj.title = (LargeBitList)title.clone();
        obj.author = (LargeBitList)author.clone();
        obj.copyright = (LargeBitList)copyright.clone();
        obj.description = (LargeBitList)description.clone();
        obj.rating = (LargeBitList)rating.clone();

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
                            ASFHeaderContentDescription d) {
        c.enterBlock("Content Description Object");

        ASFHeader.read(c, d);

        d.title_length       = c.readUIntR(16, d.title_length      );
        checkNegative("Title Length", d.title_length);
        d.author_length      = c.readUIntR(16, d.author_length     );
        checkNegative("Author Length", d.author_length);
        d.copyright_length   = c.readUIntR(16, d.copyright_length  );
        checkNegative("Copyright Length", d.copyright_length);
        d.description_length = c.readUIntR(16, d.description_length);
        checkNegative("Description Length", d.description_length);
        d.rating_length      = c.readUIntR(16, d.rating_length     );
        checkNegative("Rating Length", d.rating_length);

        d.title       = c.readBitList(d.title_length.intValue() << 3      , d.title);
        d.author      = c.readBitList(d.author_length.intValue() << 3     , d.author);
        d.copyright   = c.readBitList(d.copyright_length.intValue() << 3  , d.copyright);
        d.description = c.readBitList(d.description_length.intValue() << 3, d.description);
        d.rating      = c.readBitList(d.rating_length.intValue() << 3     , d.rating);

        c.leaveBlock();
    }

    @Override
    public void write(PacketWriter<?> c) {
        write(c, this);
    }

    public static void write(PacketWriter<?> c,
                             ASFHeaderContentDescription d) {
        c.enterBlock("Content Description Object");

        ASFHeader.write(c, d);

        c.writeUIntR(16, d.title_length      , "Title Length"      );
        c.writeUIntR(16, d.author_length     , "Author Length"     );
        c.writeUIntR(16, d.copyright_length  , "Copyright Length"  );
        c.writeUIntR(16, d.description_length, "Description Length");
        c.writeUIntR(16, d.rating_length     , "Rating Length"     );

        c.writeBitList(d.title_length.intValue() << 3      , d.title      ,
                "Title"      , d.getTitleName());
        c.writeBitList(d.author_length.intValue() << 3     , d.author     ,
                "Author"     , d.getAuthorName());
        c.writeBitList(d.copyright_length.intValue() << 3  , d.copyright  ,
                "Copyright"  , d.getCopyrightName());
        c.writeBitList(d.description_length.intValue() << 3, d.description,
                "Description", d.getDescriptionName());
        c.writeBitList(d.rating_length.intValue() << 3     , d.rating     ,
                "Rating"     , d.getRatingName());

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
