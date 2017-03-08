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
 *
 * @author katsuhiro
 */
public class RIFFHeaderStrfA extends RIFFHeader
        implements Cloneable {
    public UInt wFormatTag;
    public UInt nChannels;
    public UInt nSamplesPerSec;
    public UInt nAvgBytesPerSec;
    public UInt nBlockAlign;
    public UInt wBitsPerSample;
    public UInt cbSize;

    public RIFFHeaderStrfA() {
        wFormatTag = new UInt();
        nChannels = new UInt();
        nSamplesPerSec = new UInt();
        nAvgBytesPerSec = new UInt();
        nBlockAlign = new UInt();
        wBitsPerSample = new UInt();
        cbSize = new UInt();
    }

    public RIFFHeaderStrfA clone()
            throws CloneNotSupportedException {
        RIFFHeaderStrfA obj = (RIFFHeaderStrfA)super.clone();

        obj.wFormatTag = (UInt)wFormatTag.clone();
        obj.nChannels = (UInt)nChannels.clone();
        obj.nSamplesPerSec = (UInt)nSamplesPerSec.clone();
        obj.nAvgBytesPerSec = (UInt)nAvgBytesPerSec.clone();
        obj.nBlockAlign = (UInt)nBlockAlign.clone();
        obj.wBitsPerSample = (UInt)wBitsPerSample.clone();
        obj.cbSize = (UInt)cbSize.clone();

        return obj;
    }

    @Override
    public void read(PacketReader<?> c) {
        read(c, this);
    }

    public static void read(PacketReader<?> c,
                            RIFFHeaderStrfA d) {
        c.enterBlock("strf chunk (audio)");

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
    public void write(PacketWriter<?> c) {
        write(c, this);
    }

    public static void write(PacketWriter<?> c,
                             RIFFHeaderStrfA d) {
        c.enterBlock("strf chunk (audio)");

        RIFFHeader.write(c, d);

        c.writeUIntR(16, d.wFormatTag     , "wFormatTag"     );
        c.writeUIntR(16, d.nChannels      , "nChannels"      );
        c.writeUIntR(32, d.nSamplesPerSec , "nSamplesPerSec" );
        c.writeUIntR(32, d.nAvgBytesPerSec, "nAvgBytesPerSec");
        c.writeUIntR(16, d.nBlockAlign    , "nBlockAlign"    );
        c.writeUIntR(16, d.wBitsPerSample , "wBitsPerSample" );
        c.writeUIntR(16, d.cbSize         , "cbSize"         );

        c.leaveBlock();
    }
}
