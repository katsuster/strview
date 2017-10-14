package net.katsuster.strview.media.rmff;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * CONT チャンクヘッダ。
 * </p>
 */
public class RMFFHeaderCONT extends RMFFHeader
        implements Cloneable {
    public UInt title_len;
    public LargeBitList title;
    public UInt author_len;
    public LargeBitList author;
    public UInt copyright_len;
    public LargeBitList copyright;
    public UInt comment_len;
    public LargeBitList comment;

    public RMFFHeaderCONT() {
        title_len = new UInt();
        title = new SubLargeBitList();
        author_len = new UInt();
        author = new SubLargeBitList();
        copyright_len = new UInt();
        copyright = new SubLargeBitList();
        comment_len = new UInt();
        comment = new SubLargeBitList();
    }

    @Override
    public RMFFHeaderCONT clone()
            throws CloneNotSupportedException {
        RMFFHeaderCONT obj =
                (RMFFHeaderCONT)super.clone();

        obj.title_len = (UInt)title_len.clone();
        obj.title = (LargeBitList)title.clone();
        obj.author_len = (UInt)author_len.clone();
        obj.author = (LargeBitList)author.clone();
        obj.copyright_len = (UInt)copyright_len.clone();
        obj.copyright = (LargeBitList)copyright.clone();
        obj.comment_len = (UInt)comment_len.clone();
        obj.comment = (LargeBitList)comment.clone();

        return obj;
    }

    @Override
    public void read(StreamReader<?> c) {
        read(c, this);
    }

    public static void read(StreamReader<?> c,
                            RMFFHeaderCONT d) {
        c.enterBlock("CONT chunk");

        RMFFHeader.read(c, d);

        if (d.object_version.intValue() == 0) {
            d.title_len = c.readUInt(16, d.title_len);
            checkNegative("title_len", d.title_len);
            d.title     = c.readBitList(d.title_len.intValue() << 3, d.title);

            d.author_len = c.readUInt(16, d.author_len);
            checkNegative("author_len", d.author_len);
            d.author     = c.readBitList(d.author_len.intValue() << 3, d.author);

            d.copyright_len = c.readUInt(16, d.copyright_len);
            checkNegative("copyright_len", d.copyright_len);
            d.copyright     = c.readBitList(d.copyright_len.intValue() << 3, d.copyright);

            d.comment_len = c.readUInt(16, d.comment_len);
            checkNegative("comment_len", d.comment_len);
            d.comment     = c.readBitList(d.comment_len.intValue() << 3, d.comment);
        }

        c.leaveBlock();
    }

    @Override
    public void write(StreamWriter<?> c) {
        write(c, this);
    }

    public static void write(StreamWriter<?> c,
                             RMFFHeaderCONT d) {
        c.enterBlock("CONT chunk");

        RMFFHeader.write(c, d);

        if (d.object_version.intValue() == 0) {
            c.writeUInt(16, d.title_len    , "title_len"    );
            c.writeBitList(d.title_len.intValue() << 3    , d.title,
                    "title", d.getTitleName());

            c.writeUInt(16, d.author_len   , "author_len"   );
            c.writeBitList(d.author_len.intValue() << 3   , d.author,
                    "author", d.getAuthorName());

            c.writeUInt(16, d.copyright_len, "copyright_len");
            c.writeBitList(d.copyright_len.intValue() << 3, d.copyright,
                    "copyright", d.getCopyrightName());

            c.writeUInt(16, d.comment_len  , "comment_len"  );
            c.writeBitList(d.comment_len.intValue() << 3  , d.comment,
                    "comment", d.getCommentName());
        }

        c.leaveBlock();
    }

    public String getTitleName() {
        return getArrayName(title, "US-ASCII");
    }

    public String getAuthorName() {
        return getArrayName(author, "US-ASCII");
    }

    public String getCopyrightName() {
        return getArrayName(copyright, "US-ASCII");
    }

    public String getCommentName() {
        return getArrayName(comment, "US-ASCII");
    }
}
