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
 *
 * @author katsuhiro
 */
public class RIFFHeaderFmt extends RIFFHeader
        implements Cloneable {
    public UInt wFormatTag;
    public UInt nChannels;
    public UInt nSamplesPerSec;
    public UInt nAvgBytesPerSec;
    public UInt nBlockAlign;
    public UInt wBitsPerSample;

    public RIFFHeaderFmt() {
        wFormatTag = new UInt();
        nChannels = new UInt();
        nSamplesPerSec = new UInt();
        nAvgBytesPerSec = new UInt();
        nBlockAlign = new UInt();
        wBitsPerSample = new UInt();
    }

    @Override
    public RIFFHeaderFmt clone()
            throws CloneNotSupportedException {
        RIFFHeaderFmt obj = (RIFFHeaderFmt)super.clone();

        obj.wFormatTag = wFormatTag.clone();
        obj.nChannels = nChannels.clone();
        obj.nSamplesPerSec = nSamplesPerSec.clone();
        obj.nAvgBytesPerSec = nAvgBytesPerSec.clone();
        obj.nBlockAlign = nBlockAlign.clone();
        obj.wBitsPerSample = wBitsPerSample.clone();

        return obj;
    }

    @Override
    public void read(PacketReader<?> c) {
        read(c, this);
    }

    public static void read(PacketReader<?> c,
                            RIFFHeaderFmt d) {
        c.enterBlock("fmt chunk");

        RIFFHeader.read(c, d);

        d.wFormatTag      = c.readUIntR(16, d.wFormatTag     );
        d.nChannels       = c.readUIntR(16, d.nChannels      );
        d.nSamplesPerSec  = c.readUIntR(32, d.nSamplesPerSec );
        d.nAvgBytesPerSec = c.readUIntR(32, d.nAvgBytesPerSec);
        d.nBlockAlign     = c.readUIntR(16, d.nBlockAlign    );
        d.wBitsPerSample  = c.readUIntR(16, d.wBitsPerSample );

        c.leaveBlock();
    }

    @Override
    public void write(PacketWriter<?> c) {
        write(c, this);
    }

    public static void write(PacketWriter<?> c,
                             RIFFHeaderFmt d) {
        c.enterBlock("fmt chunk");

        RIFFHeader.write(c, d);

        c.writeUIntR(16, d.wFormatTag     , "wFormatTag"     );
        c.writeUIntR(16, d.nChannels      , "nChannels"      );
        c.writeUIntR(32, d.nSamplesPerSec , "nSamplesPerSec" );
        c.writeUIntR(32, d.nAvgBytesPerSec, "nAvgBytesPerSec");
        c.writeUIntR(16, d.nBlockAlign    , "nBlockAlign"    );
        c.writeUIntR(16, d.wBitsPerSample , "wBitsPerSample" );

        c.leaveBlock();
    }
}
