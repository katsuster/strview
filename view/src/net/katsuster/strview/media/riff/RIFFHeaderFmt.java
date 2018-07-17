package net.katsuster.strview.media.riff;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * fmt chunk
 * </p>
 *
 * <p>
 * 参考規格
 * </p>
 * <ul>
 * <li>MSDN: WAVEFORMATEX struct</li>
 * </ul>
 */
public class RIFFHeaderFmt
        extends RIFFHeader
        implements Cloneable {
    public UIntR wFormatTag;
    public UIntR nChannels;
    public UIntR nSamplesPerSec;
    public UIntR nAvgBytesPerSec;
    public UIntR nBlockAlign;
    public UIntR wBitsPerSample;

    public RIFFHeaderFmt() {
        wFormatTag      = new UIntR("wFormatTag");
        nChannels       = new UIntR("nChannels");
        nSamplesPerSec  = new UIntR("nSamplesPerSec");
        nAvgBytesPerSec = new UIntR("nAvgBytesPerSec");
        nBlockAlign     = new UIntR("nBlockAlign");
        wBitsPerSample  = new UIntR("wBitsPerSample");
    }

    @Override
    public RIFFHeaderFmt clone()
            throws CloneNotSupportedException {
        RIFFHeaderFmt obj = (RIFFHeaderFmt)super.clone();

        obj.wFormatTag = (UIntR)wFormatTag.clone();
        obj.nChannels = (UIntR)nChannels.clone();
        obj.nSamplesPerSec = (UIntR)nSamplesPerSec.clone();
        obj.nAvgBytesPerSec = (UIntR)nAvgBytesPerSec.clone();
        obj.nBlockAlign = (UIntR)nBlockAlign.clone();
        obj.wBitsPerSample = (UIntR)wBitsPerSample.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "fmt chunk";
    }

    @Override
    protected void readBits(BitStreamReader c) {
        readBits(c, this);
    }

    public static void readBits(BitStreamReader c,
                                RIFFHeaderFmt d) {
        c.enterBlock(d);

        RIFFHeader.readBits(c, d);

        d.wFormatTag      = c.readUIntR(16, d.wFormatTag     );
        d.nChannels       = c.readUIntR(16, d.nChannels      );
        d.nSamplesPerSec  = c.readUIntR(32, d.nSamplesPerSec );
        d.nAvgBytesPerSec = c.readUIntR(32, d.nAvgBytesPerSec);
        d.nBlockAlign     = c.readUIntR(16, d.nBlockAlign    );
        d.wBitsPerSample  = c.readUIntR(16, d.wBitsPerSample );

        c.leaveBlock();
    }

    @Override
    protected void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 RIFFHeaderFmt d) {
        c.enterBlock(d);

        RIFFHeader.writeBits(c, d);

        c.writeUIntR(16, d.wFormatTag     );
        c.writeUIntR(16, d.nChannels      );
        c.writeUIntR(32, d.nSamplesPerSec );
        c.writeUIntR(32, d.nAvgBytesPerSec);
        c.writeUIntR(16, d.nBlockAlign    );
        c.writeUIntR(16, d.wBitsPerSample );

        c.leaveBlock();
    }
}
