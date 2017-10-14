package net.katsuster.strview.media.flv;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * Flash Video ヘッダ。
 * </p>
 */
public class FLVHeaderFile extends FLVHeader
        implements Cloneable {
    public UInt[] signature;
    public UInt version;
    public UInt type_flags_reserved1;
    public UInt type_flags_audio;
    public UInt type_flags_reserved2;
    public UInt type_flags_video;
    public UInt data_offset;

    public FLVHeaderFile() {
        signature = new UInt[3];
        for (int i = 0; i < signature.length; i++) {
            signature[i] = new UInt();
        }

        version = new UInt();
        type_flags_reserved1 = new UInt();
        type_flags_audio = new UInt();
        type_flags_reserved2 = new UInt();
        type_flags_video = new UInt();
        data_offset = new UInt();
    }

    @Override
    public FLVHeaderFile clone()
            throws CloneNotSupportedException {
        FLVHeaderFile obj = (FLVHeaderFile)super.clone();

        obj.signature = signature.clone();
        for (int i = 0; i < signature.length; i++) {
            obj.signature[i] = (UInt)signature[i].clone();
        }
        obj.version = (UInt)version.clone();
        obj.type_flags_reserved1 = (UInt)type_flags_reserved1.clone();
        obj.type_flags_audio = (UInt)type_flags_audio.clone();
        obj.type_flags_reserved2 = (UInt)type_flags_reserved2.clone();
        obj.type_flags_video = (UInt)type_flags_video.clone();
        obj.data_offset = (UInt)data_offset.clone();

        return obj;
    }

    @Override
    public void read(StreamReader<?> c) {
        read(c, this);
    }

    public static void read(StreamReader<?> c,
                            FLVHeaderFile d) {
        c.enterBlock("FLV header");

        FLVHeader.read(c, d);

        for (int i = 0; i < d.signature.length; i++) {
            d.signature[i] = c.readUInt( 8, d.signature[i]);
        }
        d.version              = c.readUInt( 8, d.version             );
        d.type_flags_reserved1 = c.readUInt( 5, d.type_flags_reserved1);
        d.type_flags_audio     = c.readUInt( 1, d.type_flags_audio    );
        d.type_flags_reserved2 = c.readUInt( 1, d.type_flags_reserved2);
        d.type_flags_video     = c.readUInt( 1, d.type_flags_video    );
        d.data_offset          = c.readUInt(32, d.data_offset         );

        c.leaveBlock();
    }

    @Override
    public void write(StreamWriter<?> c) {
        write(c, this);
    }

    public static void write(StreamWriter<?> c,
                             FLVHeaderFile d) {
        c.enterBlock("FLV header");

        FLVHeader.write(c, d);

        for (int i = 0; i < d.signature.length; i++) {
            c.writeUInt( 8, d.signature[i], "Signature[" + i + "]");
        }
        c.writeUInt( 8, d.version             , "Version"           );
        c.writeUInt( 5, d.type_flags_reserved1, "TypeFlagsReserved1");
        c.writeUInt( 1, d.type_flags_audio    , "TypeFlagsAudio"    );
        c.writeUInt( 1, d.type_flags_reserved2, "TypeFlagsReserved2");
        c.writeUInt( 1, d.type_flags_video    , "TypeFlagsVideo"    );
        c.writeUInt(32, d.data_offset         , "DataOffset"        );

        c.leaveBlock();
    }
}
