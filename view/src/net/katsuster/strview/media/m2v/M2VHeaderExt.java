package net.katsuster.strview.media.m2v;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * MPEG2 Video extension and user data
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
 */
public class M2VHeaderExt<T extends LargeList<?>>
        extends M2VHeader<T>
        implements Cloneable {
    public UInt extension_start_code_identifier;

    public M2VHeaderExt() {
        extension_start_code_identifier = new UInt("extension_start_code_identifier");
    }

    @Override
    public M2VHeaderExt clone()
            throws CloneNotSupportedException {
        M2VHeaderExt obj = (M2VHeaderExt)super.clone();

        obj.extension_start_code_identifier = (UInt)extension_start_code_identifier.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "header_ext";
    }

    @Override
    public void read(StreamReader<?> c) {
        read(c, this);
    }

    public static void read(StreamReader<?> c,
                            M2VHeaderExt d) {
        c.enterBlock(d);

        M2VHeader.read(c, d);

        d.extension_start_code_identifier = c.readUInt( 4, d.extension_start_code_identifier);

        c.leaveBlock();
    }

    @Override
    public void write(StreamWriter<?> c) {
        write(c, this);
    }

    public static void write(StreamWriter<?> c,
                             M2VHeaderExt d) {
        c.enterBlock(d);

        M2VHeader.write(c, d);

        c.writeUInt( 4, d.extension_start_code_identifier, d.getExtensionStartCodeName());

        c.leaveBlock();
    }

    public String getExtensionStartCodeName() {
        return M2VConsts.getExtensionStartCodeName(extension_start_code_identifier.intValue());
    }
}
