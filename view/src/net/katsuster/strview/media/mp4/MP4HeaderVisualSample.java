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
    public void readBits(BitStreamReader c) {
        readBits(c, this);
    }

    public static void readBits(BitStreamReader c,
                                MP4HeaderVisualSample d) {
        c.enterBlock(d);

        MP4HeaderSample.readBits(c, d);

        c.readUInt(16, d.pre_defined1);
        c.readUInt(16, d.reserved2   );

        d.pre_defined2 = new UInt[3];
        for (int i = 0; i < d.pre_defined2.length; i++) {
            d.pre_defined2[i] = new UInt("pre_defined2[" + i + "]");
            d.pre_defined2[i] = c.readUInt(32, d.pre_defined2[i]);
        }

        d.width           = c.readUInt(16, d.width             );
        d.height          = c.readUInt(16, d.height            );
        d.horizresolution = c.readSF16_16(32, d.horizresolution);
        d.vertresolution  = c.readSF16_16(32, d.vertresolution );
        d.reserved3       = c.readUInt(32, d.reserved3         );
        d.frame_count     = c.readUInt(16, d.frame_count       );
        d.compressorname  = c.readBitList(256, d.compressorname);
        d.depth           = c.readUInt(16, d.depth             );
        d.pre_defined3    = c.readSInt(16, d.pre_defined3      );

        c.leaveBlock();
    }

    @Override
    public void writeBits(BitStreamWriter b) {
        writeBits(b, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 MP4HeaderVisualSample d) {
        c.enterBlock(d);

        MP4HeaderSample.writeBits(c, d);

        c.writeUInt(16, d.pre_defined1);
        c.writeUInt(16, d.reserved2   );

        for (int i = 0; i < d.pre_defined2.length; i++) {
            c.writeUInt(32, d.pre_defined2[i]);
        }

        c.writeUInt(16, d.width             );
        c.writeUInt(16, d.height            );
        c.writeSF16_16(32, d.horizresolution);
        c.writeSF16_16(32, d.vertresolution );
        c.writeUInt(32, d.reserved3         );
        c.writeUInt(16, d.frame_count       );
        c.writeBitList(256, d.compressorname, d.getCompressornameName());
        c.writeUInt(16, d.depth             );
        c.writeSInt(16, d.pre_defined3      );

        c.leaveBlock();
    }

    public String getCompressornameName() {
        return getArrayName(compressorname, "US-ASCII");
    }
}