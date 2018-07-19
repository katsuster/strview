package net.katsuster.strview.media.flv;

import net.katsuster.strview.util.bit.*;
import net.katsuster.strview.media.bit.*;

/**
 * <p>
 * VIDEODATA
 * </p>
 */
public class FLVHeaderVideo
        extends FLVHeaderES
        implements Cloneable {
    public UInt frame_type;
    public UInt codec_id;

    public FLVHeaderVideo() {
        frame_type = new UInt("FrameType");
        codec_id = new UInt("CodecID");
    }

    @Override
    public FLVHeaderVideo clone()
            throws CloneNotSupportedException {
        FLVHeaderVideo obj = (FLVHeaderVideo)super.clone();

        obj.frame_type = (UInt)frame_type.clone();
        obj.codec_id = (UInt)codec_id.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "FLV tag (Video)";
    }

    @Override
    protected void readBits(BitStreamReader c) {
        readBits(c, this);
    }

    public static void readBits(BitStreamReader c,
                                FLVHeaderVideo d) {
        c.enterBlock(d);

        FLVHeaderES.readBits(c, d);

        d.frame_type = c.readUInt( 4, d.frame_type);
        d.codec_id   = c.readUInt( 4, d.codec_id  );

        c.leaveBlock();
    }

    @Override
    protected void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 FLVHeaderVideo d) {
        c.enterBlock(d);

        FLVHeaderES.writeBits(c, d);

        c.writeUInt( 4, d.frame_type, d.getFrameTypeName());
        c.writeUInt( 4, d.codec_id  , d.getCodecIDName());

        c.leaveBlock();
    }

    public String getFrameTypeName() {
        return FLVConsts.getFrameTypeName(frame_type.intValue());
    }

    public String getCodecIDName() {
        return FLVConsts.getCodecIDName(codec_id.intValue());
    }
}
