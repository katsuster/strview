package net.katsuster.strview.media.rmff;

import java.io.*;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * RMFF (RealMedia File Format) チャンクヘッダ。
 * </p>
 *
 * <p>
 * 参考規格
 * </p>
 * <ul>
 * <li>HELIX CLIENT AND SERVER SOFTWARE DEVELOPER’S GUIDE Volume 2
 * : VERSION R5
 * - Appendix E: RealMedia File Format (RMFF) Reference
 * (https://www.helixcommunity.org/projects/common/2003/HCS_SDK_r5/htmfiles/rmff.htm)</li>
 * </ul>
 *
 * @author katsuhiro
 */
public class RMFFHeader extends BlockAdapter
        implements Cloneable {
    public UInt object_id;
    public UInt size;
    public UInt object_version;

    public RMFFHeader() {
        super();

        object_id = new UInt();
        size = new UInt();
        object_version = new UInt();
    }

    @Override
    public RMFFHeader clone()
            throws CloneNotSupportedException {
        RMFFHeader obj = (RMFFHeader)super.clone();

        obj.object_id = (UInt)object_id.clone();
        obj.size = (UInt)size.clone();
        obj.object_version = (UInt)object_version.clone();

        return obj;
    }

    /**
     * <p>
     * チャンク本体に別のチャンクを含められるかどうかを返します。
     * </p>
     *
     * @return チャンク本体に別のチャンクを含められる場合は true、
     * 含められない場合は false
     */
    public boolean isRecursive() {
        return false;
    }

    @Override
    public void read(PacketReader<?> c) {
        read(c, this);
    }

    public static void read(PacketReader<?> c,
                            RMFFHeader d) {
        c.enterBlock("RMFF chunk header");

        d.object_id      = c.readUInt(32, d.object_id     );
        d.size           = c.readUInt(32, d.size          );
        d.object_version = c.readUInt(16, d.object_version);

        c.leaveBlock();
    }

    @Override
    public void write(PacketWriter<?> c) {
        write(c, this);
    }

    public static void write(PacketWriter<?> c,
                             RMFFHeader d) {
        c.enterBlock("RMFF chunk header");

        c.writeUInt(32, d.object_id     , "object_id"     , d.getObjectIdName());
        c.writeUInt(32, d.size          , "size"          );
        c.writeUInt(16, d.object_version, "object_version");

        c.leaveBlock();
    }

    public String getObjectIdName() {
        return RMFFConsts.getObjectIdName(object_id.intValue());
    }

    /**
     * <p>
     * 値が負の値だった場合、例外をスローします。
     * </p>
     *
     * @param name 名前
     * @param v    値
     */
    protected static void checkNegative(String name, UInt v) {
        if (v.intValue() < 0) {
            throw new IllegalStateException(
                    name + " has negative size"
                            + "(len:" + v + ")");
        }
    }

    /**
     * <p>
     * ビットリストを ASCII 文字列と解釈して、変換します。
     * </p>
     *
     * @param v ビットリスト
     * @return 文字列
     */
    protected static String getArrayName(LargeBitList v) {
        String name;

        try {
            byte[] buf = new byte[(int)v.length() >>> 3];
            v.getPackedByteArray(0, buf, 0, (int)v.length() & ~0x7);
            name = new String(buf, "US-ASCII");
        } catch (UnsupportedEncodingException e) {
            name = "..unknown..";
        }

        return name;
    }
}
