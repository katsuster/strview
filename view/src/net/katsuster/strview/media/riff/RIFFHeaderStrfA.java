package net.katsuster.strview.media.riff;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * strf (AVI Stream Format), struct WAVEFORMATEX
 * </p>
 *
 * <p>
 * 参考規格
 * </p>
 * <ul>
 * <li>Microsoft Multimedia Standards Update: Revision 1.0.97</li>
 * <li>OpenDML AVI File Format Extensions: Version 1.02</li>
 * <li>MSDN: AVISTREAMHEADER struct</li>
 * </ul>
 */
public class RIFFHeaderStrfA<T extends LargeList<?>>
        extends RIFFHeader<T>
        implements Cloneable {
    public UIntR wFormatTag;
    public UIntR nChannels;
    public UIntR nSamplesPerSec;
    public UIntR nAvgBytesPerSec;
    public UIntR nBlockAlign;
    public UIntR wBitsPerSample;
    public UIntR cbSize;

    public RIFFHeaderStrfA() {
        wFormatTag      = new UIntR("wFormatTag"     );
        nChannels       = new UIntR("nChannels"      );
        nSamplesPerSec  = new UIntR("nSamplesPerSec" );
        nAvgBytesPerSec = new UIntR("nAvgBytesPerSec");
        nBlockAlign     = new UIntR("nBlockAlign"    );
        wBitsPerSample  = new UIntR("wBitsPerSample" );
        cbSize          = new UIntR("cbSize"         );
    }

    public RIFFHeaderStrfA<T> clone()
            throws CloneNotSupportedException {
        RIFFHeaderStrfA<T> obj = (RIFFHeaderStrfA<T>)super.clone();

        obj.wFormatTag = (UIntR)wFormatTag.clone();
        obj.nChannels = (UIntR)nChannels.clone();
        obj.nSamplesPerSec = (UIntR)nSamplesPerSec.clone();
        obj.nAvgBytesPerSec = (UIntR)nAvgBytesPerSec.clone();
        obj.nBlockAlign = (UIntR)nBlockAlign.clone();
        obj.wBitsPerSample = (UIntR)wBitsPerSample.clone();
        obj.cbSize = (UIntR)cbSize.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "strf chunk (audio)";
    }

    @Override
    public void read(StreamReader<?> c) {
        read(c, this);
    }

    public static void read(StreamReader<?> c,
                            RIFFHeaderStrfA d) {
        c.enterBlock(d);

        RIFFHeader.read(c, d);

        d.wFormatTag      = c.readUIntR(16, d.wFormatTag     );
        d.nChannels       = c.readUIntR(16, d.nChannels      );
        d.nSamplesPerSec  = c.readUIntR(32, d.nSamplesPerSec );
        d.nAvgBytesPerSec = c.readUIntR(32, d.nAvgBytesPerSec);
        d.nBlockAlign     = c.readUIntR(16, d.nBlockAlign    );
        d.wBitsPerSample  = c.readUIntR(16, d.wBitsPerSample );
        d.cbSize          = c.readUIntR(16, d.cbSize         );

        c.leaveBlock();
    }

    @Override
    public void write(StreamWriter<?> c) {
        write(c, this);
    }

    public static void write(StreamWriter<?> c,
                             RIFFHeaderStrfA d) {
        c.enterBlock(d);

        RIFFHeader.write(c, d);

        c.writeUIntR(16, d.wFormatTag     );
        c.writeUIntR(16, d.nChannels      );
        c.writeUIntR(32, d.nSamplesPerSec );
        c.writeUIntR(32, d.nAvgBytesPerSec);
        c.writeUIntR(16, d.nBlockAlign    );
        c.writeUIntR(16, d.wBitsPerSample );
        c.writeUIntR(16, d.cbSize         );

        c.leaveBlock();
    }
}
