package net.katsuster.strview.media.mp4;

import net.katsuster.strview.media.bit.*;
import net.katsuster.strview.util.bit.*;

/**
 * <p>
 * MPEG4 media file format の VisualSampleEntry ヘッダ。
 * </p>
 *
 * <p>
 * 参考規格
 * </p>
 * <ul>
 * <li>ISO/IEC 14496-12: ISO base media file format</li>
 * </ul>
 *
 * @author katsuhiro
 */
public class MP4HeaderVisualSample extends MP4HeaderSample
        implements Cloneable {
    public UInt pre_defined1;
    public UInt reserved2;
    public UInt[] pre_defined2;
    public UInt width;
    public UInt height;
    public SFixed16_16 horizresolution;
    public SFixed16_16 vertresolution;
    public UInt reserved3;
    public UInt frame_count;
    public LargeBitList compressorname;
    public UInt depth;
    public SInt pre_defined3;

    public MP4HeaderVisualSample() {
        pre_defined1 = new UInt("pre_defined1");
        reserved2 = new UInt("reserved2");

        pre_defined2 = new UInt[0];

        width = new UInt("width");
        height = new UInt("height");
        horizresolution = new SFixed16_16("horizresolution", 0);
        vertresolution = new SFixed16_16("vertresolution", 0);
        reserved3 = new UInt("reserved3");
        frame_count = new UInt("frame_count");
        compressorname = new SubLargeBitList("compressorname");
        depth = new UInt("depth");
        pre_defined3 = new SInt("pre_defined3");
    }

    @Override
    public MP4HeaderVisualSample clone()
            throws CloneNotSupportedException {
        MP4HeaderVisualSample obj =
                (MP4HeaderVisualSample)super.clone();
        int i;

        obj.pre_defined1 = (UInt)pre_defined1.clone();
        obj.reserved2 = (UInt)reserved2.clone();

        obj.pre_defined2 = pre_defined2.clone();
        for (i = 0; i < obj.pre_defined2.length; i++) {
            obj.pre_defined2[i] = (UInt)pre_defined2[i].clone();
        }

        obj.width = (UInt)width.clone();
        obj.height = (UInt)height.clone();
        obj.horizresolution = (SFixed16_16)horizresolution.clone();
        obj.vertresolution = (SFixed16_16)vertresolution.clone();
        obj.reserved3 = (UInt)reserved3.clone();
        obj.frame_count = (UInt)frame_count.clone();
        obj.compressorname = (LargeBitList)compressorname.clone();
        obj.depth = (UInt)depth.clone();
        obj.pre_defined3 = (SInt)pre_defined3.clone();

        return obj;
    }

    @Override
    public boolean isRecursive() {
        return true;
    }

    @Override
    public void readBits(BitStreamReader b) {
        readBits(b, this);
    }

    public static void readBits(BitStreamReader b,
                                MP4HeaderVisualSample d) {
        int i;

        MP4HeaderSample.readBits(b, d);

        b.readUInt(16, d.pre_defined1);
        b.readUInt(16, d.reserved2   );

        d.pre_defined2 = new UInt[3];
        for (i = 0; i < d.pre_defined2.length; i++) {
            d.pre_defined2[i] = new UInt("pre_defined2[" + i + "]");
            d.pre_defined2[i] = b.readUInt(32, d.pre_defined2[i]);
        }

        d.width           = b.readUInt(16, d.width             );
        d.height          = b.readUInt(16, d.height            );
        d.horizresolution = b.readSF16_16(32, d.horizresolution);
        d.vertresolution  = b.readSF16_16(32, d.vertresolution );
        d.reserved3       = b.readUInt(32, d.reserved3         );
        d.frame_count     = b.readUInt(16, d.frame_count       );
        d.compressorname  = b.readBitList(256, d.compressorname);
        d.depth           = b.readUInt(16, d.depth             );
        d.pre_defined3    = b.readSInt(16, d.pre_defined3      );
    }

    @Override
    public void writeBits(BitStreamWriter b) {
        writeBits(b, this);
    }

    public static void writeBits(BitStreamWriter b,
                                 MP4HeaderVisualSample d) {
        int i;

        MP4HeaderSample.writeBits(b, d);

        b.writeUInt(16, d.pre_defined1);
        b.writeUInt(16, d.reserved2   );

        for (i = 0; i < d.pre_defined2.length; i++) {
            b.writeUInt(32, d.pre_defined2[i]);
        }

        b.writeUInt(16, d.width             );
        b.writeUInt(16, d.height            );
        b.writeSF16_16(32, d.horizresolution);
        b.writeSF16_16(32, d.vertresolution );
        b.writeUInt(32, d.reserved3         );
        b.writeUInt(16, d.frame_count       );
        b.writeBitList(256, d.compressorname, d.getCompressornameName());
        b.writeUInt(16, d.depth             );
        b.writeSInt(16, d.pre_defined3      );
    }

    public String getCompressornameName() {
        return getArrayName(compressorname, "US-ASCII");
    }
}