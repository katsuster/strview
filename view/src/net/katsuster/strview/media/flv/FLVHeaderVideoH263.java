package net.katsuster.strview.media.flv;

import java.util.*;

import net.katsuster.strview.media.bit.*;
import net.katsuster.strview.util.bit.*;

/**
 * <p>
 * SWF Flash Video の H263VIDEOPACKET
 * </p>
 */
public class FLVHeaderVideoH263
        extends FLVHeaderVideo
        implements Cloneable {
    public UInt PictureStartCode;
    public UInt Version;
    public UInt TemporalReference;
    public UInt PictureSize;
    public UInt CustomWidth;
    public UInt CustomHeight;
    public UInt PictureType;
    public UInt DeblockingFlag;
    public UInt Quantizer;

    public ArrayList<UInt> ExtraInformationFlag;
    public ArrayList<UInt> ExtraInformation;

    public UInt ExtraInformationFlagLast;

    public FLVHeaderVideoH263() {
        PictureStartCode = new UInt("PictureStartCode");
        Version = new UInt("Version");
        TemporalReference = new UInt("TemporalReference");
        PictureSize = new UInt("PictureSize");
        CustomWidth = new UInt("CustomWidth");
        CustomHeight = new UInt("CustomHeight");
        PictureType = new UInt("PictureType");
        DeblockingFlag = new UInt("DeblockingFlag");
        Quantizer = new UInt("Quantizer");

        ExtraInformationFlag = new ArrayList<>();
        ExtraInformation = new ArrayList<>();

        ExtraInformationFlagLast = new UInt("ExtraInformationFlagLast");
    }

    @Override
    public FLVHeaderVideoH263 clone()
            throws CloneNotSupportedException {
        FLVHeaderVideoH263 obj = (FLVHeaderVideoH263)super.clone();

        obj.PictureStartCode = (UInt)PictureStartCode.clone();
        obj.Version = (UInt)Version.clone();
        obj.TemporalReference = (UInt)TemporalReference.clone();
        obj.PictureSize = (UInt)PictureSize.clone();
        obj.CustomWidth = (UInt)CustomWidth.clone();
        obj.CustomHeight = (UInt)CustomHeight.clone();
        obj.PictureType = (UInt)PictureType.clone();
        obj.DeblockingFlag = (UInt)DeblockingFlag.clone();
        obj.Quantizer = (UInt)Quantizer.clone();

        obj.ExtraInformationFlag = new ArrayList<>();
        for (UInt v : ExtraInformationFlag) {
            obj.ExtraInformationFlag.add((UInt)v.clone());
        }
        obj.ExtraInformation = new ArrayList<>();
        for (UInt v : ExtraInformation) {
            obj.ExtraInformation.add((UInt)v.clone());
        }
        obj.ExtraInformationFlagLast = (UInt)ExtraInformationFlagLast.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "FLV tag (Video, H.263)";
    }

    @Override
    protected void readBits(BitStreamReader c) {
        readBits(c, this);
    }

    public static void readBits(BitStreamReader c,
                                FLVHeaderVideoH263 d) {
        c.enterBlock(d);

        FLVHeaderVideo.readBits(c, d);

        d.PictureStartCode  = c.readUInt(17, d.PictureStartCode );
        d.Version           = c.readUInt( 5, d.Version          );
        d.TemporalReference = c.readUInt( 8, d.TemporalReference);
        d.PictureSize       = c.readUInt( 3, d.PictureSize      );
        if (d.PictureSize.intValue() == 0) {
            d.CustomWidth  = c.readUInt( 8, d.CustomWidth );
            d.CustomHeight = c.readUInt( 8, d.CustomHeight);
        } else if (d.PictureSize.intValue() == 1) {
            d.CustomWidth  = c.readUInt(16, d.CustomWidth );
            d.CustomHeight = c.readUInt(16, d.CustomHeight);
        }
        d.PictureType    = c.readUInt( 2, d.PictureType   );
        d.DeblockingFlag = c.readUInt( 1, d.DeblockingFlag);
        d.Quantizer      = c.readUInt( 5, d.Quantizer     );

        UInt v = new UInt("ExtraInformation");
        v = c.readUInt(1, v);
        while (v.intValue() == 1) {
            d.ExtraInformationFlag.add(v);

            v = new UInt("ExtraInformationFlag");
            v = c.readUInt(8, v);
            d.ExtraInformation.add(v);

            v = new UInt("ExtraInformation");
            v = c.readUInt(1, v);
        }
        d.ExtraInformationFlagLast = v;

        c.leaveBlock();
    }

    @Override
    protected void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 FLVHeaderVideoH263 d) {
        c.enterBlock(d);

        FLVHeaderVideo.writeBits(c, d);

        c.writeUInt(17, d.PictureStartCode );
        c.writeUInt( 5, d.Version          );
        c.writeUInt( 8, d.TemporalReference);
        c.writeUInt( 3, d.PictureSize      );
        if (d.PictureSize.intValue() == 0) {
            c.writeUInt( 8, d.CustomWidth );
            c.writeUInt( 8, d.CustomHeight);
        } else if (d.PictureSize.intValue() == 1) {
            c.writeUInt(16, d.CustomWidth );
            c.writeUInt(16, d.CustomHeight);
        }
        c.writeUInt( 2, d.PictureType   );
        c.writeUInt( 1, d.DeblockingFlag);
        c.writeUInt( 5, d.Quantizer     );

        for (int i = 0; i < d.ExtraInformationFlag.size(); i++) {
            c.writeUInt( 1, d.ExtraInformationFlag.get(i));
            c.writeUInt( 8, d.ExtraInformation.get(i)    );
        }
        c.writeUInt( 1, d.ExtraInformationFlagLast);

        c.leaveBlock();
    }
}
