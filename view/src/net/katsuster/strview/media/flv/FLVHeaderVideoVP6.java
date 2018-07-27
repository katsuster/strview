package net.katsuster.strview.media.flv;

import net.katsuster.strview.media.bit.*;
import net.katsuster.strview.util.bit.*;

/**
 * <p>
 * SWF Flash Video の VP6FLVVIDEOPACKET
 * </p>
 */
public class FLVHeaderVideoVP6
        extends FLVHeaderVideo
        implements Cloneable {
    private UInt HorizontalAdjustment;
    private UInt VerticalAdjustment;

    public FLVHeaderVideoVP6() {
        HorizontalAdjustment = new UInt("HorizontalAdjustment");
        VerticalAdjustment = new UInt("VerticalAdjustment");
    }

    @Override
    public FLVHeaderVideoVP6 clone()
            throws CloneNotSupportedException {
        FLVHeaderVideoVP6 obj = (FLVHeaderVideoVP6)super.clone();

        obj.HorizontalAdjustment = (UInt)HorizontalAdjustment.clone();
        obj.VerticalAdjustment = (UInt)VerticalAdjustment.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "FLV tag (Video, VP6)";
    }

    @Override
    protected void readBits(BitStreamReader c) {
        readBits(c, this);
    }

    public static void readBits(BitStreamReader c,
                                FLVHeaderVideoVP6 d) {
        c.enterBlock(d);

        FLVHeaderVideo.readBits(c, d);

        d.HorizontalAdjustment = c.readUInt( 4, d.HorizontalAdjustment);
        d.VerticalAdjustment   = c.readUInt( 4, d.VerticalAdjustment  );

        c.leaveBlock();
    }

    @Override
    protected void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 FLVHeaderVideoVP6 d) {
        c.enterBlock(d);

        FLVHeaderVideo.writeBits(c, d);

        c.writeUInt( 4, d.HorizontalAdjustment);
        c.writeUInt( 4, d.VerticalAdjustment  );

        c.leaveBlock();
    }
}
