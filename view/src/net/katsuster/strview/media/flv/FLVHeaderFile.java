package net.katsuster.strview.media.flv;

import net.katsuster.strview.util.bit.*;
import net.katsuster.strview.media.bit.*;

/**
 * <p>
 * Flash Video ヘッダ。
 * </p>
 */
public class FLVHeaderFile
        extends FLVHeader
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
            signature[i] = new UInt("Signature[" + i + "]");
        }

        version              = new UInt("Version");
        type_flags_reserved1 = new UInt("TypeFlagsReserved1");
        type_flags_audio     = new UInt("TypeFlagsAudio");
        type_flags_reserved2 = new UInt("TypeFlagsReserved2");
        type_flags_video     = new UInt("TypeFlagsVideo");
        data_offset          = new UInt("DataOffset");
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
    public String getTypeName() {
        return "FLV header";
    }

    @Override
    protected void readBits(BitStreamReader c) {
        readBits(c, this);
    }

    public static void readBits(BitStreamReader c,
                                FLVHeaderFile d) {
        c.enterBlock(d);

        FLVHeader.readBits(c, d);

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
    protected void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 FLVHeaderFile d) {
        c.enterBlock(d);

        FLVHeader.writeBits(c, d);

        for (int i = 0; i < d.signature.length; i++) {
            c.writeUInt( 8, d.signature[i]);
        }
        c.writeUInt( 8, d.version             );
        c.writeUInt( 5, d.type_flags_reserved1);
        c.writeUInt( 1, d.type_flags_audio    );
        c.writeUInt( 1, d.type_flags_reserved2);
        c.writeUInt( 1, d.type_flags_video    );
        c.writeUInt(32, d.data_offset         );

        c.leaveBlock();
    }
}
