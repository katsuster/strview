package net.katsuster.strview.media.flv;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * VIDEODATA
 * </p>
 */
public class FLVHeaderVideo<T extends LargeList<?>>
        extends FLVHeaderES<T>
        implements Cloneable {
    public UInt frame_type;
    public UInt codec_id;

    public FLVHeaderVideo() {
        frame_type = new UInt("FrameType");
        codec_id = new UInt("CodecID");
    }

    @Override
    public FLVHeaderVideo<T> clone()
            throws CloneNotSupportedException {
        FLVHeaderVideo<T> obj = (FLVHeaderVideo<T>)super.clone();

        obj.frame_type = (UInt)frame_type.clone();
        obj.codec_id = (UInt)codec_id.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "FLV tag (Video)";
    }

    @Override
    public void read(StreamReader<?, ?> c) {
        read(c, this);
    }

    public static void read(StreamReader<?, ?> c,
                            FLVHeaderVideo d) {
        c.enterBlock(d);

        FLVHeaderES.read(c, d);

        d.frame_type = c.readUInt( 4, d.frame_type);
        d.codec_id   = c.readUInt( 4, d.codec_id  );

        c.leaveBlock();
    }

    @Override
    public void write(StreamWriter<?, ?> c) {
        write(c, this);
    }

    public static void write(StreamWriter<?, ?> c,
                             FLVHeaderVideo d) {
        c.enterBlock(d);

        FLVHeaderES.write(c, d);

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
