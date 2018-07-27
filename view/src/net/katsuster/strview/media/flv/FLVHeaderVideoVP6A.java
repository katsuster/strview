package net.katsuster.strview.media.flv;

import net.katsuster.strview.media.bit.*;
import net.katsuster.strview.util.bit.*;

/**
 * <p>
 * SWF Flash Video の VP6FLVALPHAVIDEOPACKET
 * </p>
 */
public class FLVHeaderVideoVP6A
        extends FLVHeaderVideo
        implements Cloneable {
    private UInt HorizontalAdjustment;
    private UInt VerticalAdjustment;
    private UInt OffsetToAlpha;

    public FLVHeaderVideoVP6A() {
        HorizontalAdjustment = new UInt("HorizontalAdjustment");
        VerticalAdjustment = new UInt("VerticalAdjustment");
        OffsetToAlpha = new UInt("OffsetToAlpha");
    }

    @Override
    public FLVHeaderVideoVP6A clone()
            throws CloneNotSupportedException {
        FLVHeaderVideoVP6A obj = (FLVHeaderVideoVP6A)super.clone();

        obj.HorizontalAdjustment = (UInt)HorizontalAdjustment.clone();
        obj.VerticalAdjustment = (UInt)VerticalAdjustment.clone();
        obj.OffsetToAlpha = (UInt)OffsetToAlpha.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "FLV tag (Video, VP6A)";
    }

    @Override
    protected void readBits(BitStreamReader c) {
        readBits(c, this);
    }

    public static void readBits(BitStreamReader c,
                                FLVHeaderVideoVP6A d) {
        c.enterBlock(d);

        FLVHeaderVideo.readBits(c, d);

        d.HorizontalAdjustment = c.readUInt( 4, d.HorizontalAdjustment);
        d.VerticalAdjustment   = c.readUInt( 4, d.VerticalAdjustment  );
        d.OffsetToAlpha        = c.readUInt(24, d.OffsetToAlpha       );

        c.leaveBlock();
    }

    @Override
    protected void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 FLVHeaderVideoVP6A d) {
        c.enterBlock(d);

        FLVHeaderVideo.writeBits(c, d);

        c.writeUInt( 4, d.HorizontalAdjustment);
        c.writeUInt( 4, d.VerticalAdjustment  );
        c.writeUInt(24, d.OffsetToAlpha       );

        c.leaveBlock();
    }
}
