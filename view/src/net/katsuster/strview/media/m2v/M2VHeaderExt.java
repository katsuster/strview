package net.katsuster.strview.media.m2v;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * MPEG2 Video extension header and user data
 * </p>
 *
 * <p>
 * 参考規格
 * </p>
 * <ul>
 * <li>ITU-T H.262, ISO/IEC 13818-2:
 * Information technology - Generic coding of moving pictures and
 * associated audio information: Video</li>
 * </ul>
 *
 * @author katsuhiro
 */
public class M2VHeaderExt extends M2VHeader
        implements Cloneable {
    public UInt extension_start_code_identifier;

    public M2VHeaderExt() {
        super();

        extension_start_code_identifier = new UInt();
    }

    @Override
    public M2VHeaderExt clone()
            throws CloneNotSupportedException {
        M2VHeaderExt obj = (M2VHeaderExt)super.clone();

        obj.extension_start_code_identifier = extension_start_code_identifier.clone();

        return obj;
    }

    /**
     * <p>
     * タグ本体に別のタグを含められるかどうかを返します。
     * </p>
     *
     * @return タグ本体に別のタグを含められる場合は true、
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
                            M2VHeaderExt d) {
        c.enterBlock("M2V ext header");

        M2VHeader.read(c, d);

        d.extension_start_code_identifier = c.readUInt( 4, d.extension_start_code_identifier);

        c.leaveBlock();
    }

    @Override
    public void write(PacketWriter<?> c) {
        write(c, this);
    }

    public static void write(PacketWriter<?> c,
                             M2VHeaderExt d) {
        c.enterBlock("M2V ext header");

        M2VHeader.write(c, d);

        c.writeUInt( 4, d.extension_start_code_identifier, "extension_start_code_identifier", d.getExtensionStartCodeName());

        c.leaveBlock();
    }

    public String getExtensionStartCodeName() {
        return M2VConsts.getExtensionStartCodeName(extension_start_code_identifier.intValue());
    }
}
