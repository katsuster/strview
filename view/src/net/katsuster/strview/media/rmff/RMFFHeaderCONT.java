package net.katsuster.strview.media.rmff;

import net.katsuster.strview.util.*;
import net.katsuster.strview.io.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * CONT チャンクヘッダ。
 * </p>
 *
 * @author katsuhiro
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
        super();

        title_len = new UInt();
        title = new MemoryBitList();
        author_len = new UInt();
        author = new MemoryBitList();
        copyright_len = new UInt();
        copyright = new MemoryBitList();
        comment_len = new UInt();
        comment = new MemoryBitList();
    }

    @Override
    public RMFFHeaderCONT clone()
            throws CloneNotSupportedException {
        RMFFHeaderCONT obj =
                (RMFFHeaderCONT)super.clone();

        obj.title_len = title_len.clone();
        obj.title = title;
        obj.author_len = author_len.clone();
        obj.author = author;
        obj.copyright_len = copyright_len.clone();
        obj.copyright = copyright;
        obj.comment_len = comment_len.clone();
        obj.comment = comment;

        return obj;
    }

    @Override
    public void read(PacketReader<?> c) {
        read(c, this);
    }

    public static void read(PacketReader<?> c,
                            RMFFHeaderCONT d) {
        c.enterBlock("CONT chunk");

        RMFFHeader.read(c, d);

        if (d.object_version.intValue() == 0) {
            d.title_len = c.readUInt(16, d.title_len);
            checkNegative("title_len", d.title_len);
            d.title     = c.readBitList(d.title_len.intValue() << 3, d.title);
            c.mark("title_val", d.getTitleName());

            d.author_len = c.readUInt(16, d.author_len);
            checkNegative("author_len", d.author_len);
            d.author     = c.readBitList(d.author_len.intValue() << 3, d.author);
            c.mark("author_val", d.getAuthorName());

            d.copyright_len = c.readUInt(16, d.copyright_len);
            checkNegative("copyright_len", d.copyright_len);
            d.copyright     = c.readBitList(d.copyright_len.intValue() << 3, d.copyright);
            c.mark("copyright_val", d.getCopyrightName());

            d.comment_len = c.readUInt(16, d.comment_len);
            checkNegative("comment_len", d.comment_len);
            d.comment     = c.readBitList(d.comment_len.intValue() << 3, d.comment);
            c.mark("comment_val", d.getCommentName());
        }

        c.leaveBlock();
    }

    @Override
    public void write(PacketWriter<?> c) {
        write(c, this);
    }

    public static void write(PacketWriter<?> c,
                             RMFFHeaderCONT d) {
        c.enterBlock("CONT chunk");

        RMFFHeader.write(c, d);

        if (d.object_version.intValue() == 0) {
            c.writeUInt(16, d.title_len    , "title_len"    );
            c.writeBitList(d.title_len.intValue() << 3    , d.title    , "title"    );
            c.mark("title_val", d.getTitleName());

            c.writeUInt(16, d.author_len   , "author_len"   );
            c.writeBitList(d.author_len.intValue() << 3   , d.author   , "author"   );
            c.mark("author_val", d.getAuthorName());

            c.writeUInt(16, d.copyright_len, "copyright_len");
            c.writeBitList(d.copyright_len.intValue() << 3, d.copyright, "copyright");
            c.mark("copyright_val", d.getCopyrightName());

            c.writeUInt(16, d.comment_len  , "comment_len"  );
            c.writeBitList(d.comment_len.intValue() << 3  , d.comment  , "comment"  );
            c.mark("comment_val", d.getCommentName());
        }

        c.leaveBlock();
    }

    public String getTitleName() {
        return getArrayName(title);
    }

    public static String getTitleName(RMFFHeaderCONT d) {
        return getArrayName(d.title);
    }

    public String getAuthorName() {
        return getArrayName(author);
    }

    public static String getAuthorName(RMFFHeaderCONT d) {
        return getArrayName(d.author);
    }

    public String getCopyrightName() {
        return getArrayName(copyright);
    }

    public static String getCopyrightName(RMFFHeaderCONT d) {
        return getArrayName(d.copyright);
    }

    public String getCommentName() {
        return getArrayName(comment);
    }

    public static String getCommentName(RMFFHeaderCONT d) {
        return getArrayName(d.comment);
    }

}
