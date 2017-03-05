package net.katsuster.strview.media.riff;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * avih (Main AVI Header)
 * </p>
 *
 * <p>
 * 参考規格
 * </p>
 * <ul>
 * <li>Microsoft Multimedia Standards Update: Revision 1.0.97</li>
 * <li>OpenDML AVI File Format Extensions: Version 1.02</li>
 * <li>MSDN: AVIMAINHEADER struct</li>
 * </ul>
 *
 * @author katsuhiro
 */
public class RIFFHeaderAvih extends RIFFHeader
        implements Cloneable {
    public UInt dwMicroSecPerFrame;
    public UInt dwMaxBytesPerSec;
    public UInt dwPaddingGranularity;
    public UInt dwFlags;
    public UInt dwTotalFrames;
    public UInt dwInitialFrames;
    public UInt dwStreams;
    public UInt dwSuggestedBufferSize;
    public UInt dwWidth;
    public UInt dwHeight;
    public UInt dwReserved0;
    public UInt dwReserved1;
    public UInt dwReserved2;
    public UInt dwReserved3;

    public RIFFHeaderAvih() {
        dwMicroSecPerFrame = new UInt();
        dwMaxBytesPerSec = new UInt();
        dwPaddingGranularity = new UInt();
        dwFlags = new UInt();
        dwTotalFrames = new UInt();
        dwInitialFrames = new UInt();
        dwStreams = new UInt();
        dwSuggestedBufferSize = new UInt();
        dwWidth = new UInt();
        dwHeight = new UInt();
        dwReserved0 = new UInt();
        dwReserved1 = new UInt();
        dwReserved2 = new UInt();
        dwReserved3 = new UInt();
    }

    @Override
    public RIFFHeaderAvih clone()
            throws CloneNotSupportedException {
        RIFFHeaderAvih obj = (RIFFHeaderAvih)super.clone();

        obj.dwMicroSecPerFrame = dwMicroSecPerFrame.clone();
        obj.dwMaxBytesPerSec = dwMaxBytesPerSec.clone();
        obj.dwPaddingGranularity = dwPaddingGranularity.clone();
        obj.dwFlags = dwFlags.clone();
        obj.dwTotalFrames = dwTotalFrames.clone();
        obj.dwInitialFrames = dwInitialFrames.clone();
        obj.dwStreams = dwStreams.clone();
        obj.dwSuggestedBufferSize = dwSuggestedBufferSize.clone();
        obj.dwWidth = dwWidth.clone();
        obj.dwHeight = dwHeight.clone();
        obj.dwReserved0 = dwReserved0.clone();
        obj.dwReserved1 = dwReserved1.clone();
        obj.dwReserved2 = dwReserved2.clone();
        obj.dwReserved3 = dwReserved3.clone();

        return obj;
    }

    @Override
    public void read(PacketReader<?> c) {
        read(c, this);
    }

    public static void read(PacketReader<?> c,
                            RIFFHeaderAvih d) {
        c.enterBlock("avih chunk");

        RIFFHeader.read(c, d);

        d.dwMicroSecPerFrame    = c.readUIntR(32, d.dwMicroSecPerFrame   );
        d.dwMaxBytesPerSec      = c.readUIntR(32, d.dwMaxBytesPerSec     );
        d.dwPaddingGranularity  = c.readUIntR(32, d.dwPaddingGranularity );
        d.dwFlags               = c.readUIntR(32, d.dwFlags              );
        d.dwTotalFrames         = c.readUIntR(32, d.dwTotalFrames        );
        d.dwInitialFrames       = c.readUIntR(32, d.dwInitialFrames      );
        d.dwStreams             = c.readUIntR(32, d.dwStreams            );
        d.dwSuggestedBufferSize = c.readUIntR(32, d.dwSuggestedBufferSize);
        d.dwWidth               = c.readUIntR(32, d.dwWidth              );
        d.dwHeight              = c.readUIntR(32, d.dwHeight             );
        d.dwReserved0           = c.readUIntR(32, d.dwReserved0          );
        d.dwReserved1           = c.readUIntR(32, d.dwReserved1          );
        d.dwReserved2           = c.readUIntR(32, d.dwReserved2          );
        d.dwReserved3           = c.readUIntR(32, d.dwReserved3          );

        c.leaveBlock();
    }

    @Override
    public void write(PacketWriter<?> c) {
        write(c, this);
    }

    public static void write(PacketWriter<?> c,
                             RIFFHeaderAvih d) {
        c.enterBlock("avih chunk");

        RIFFHeader.write(c, d);

        c.writeUIntR(32, d.dwMicroSecPerFrame   , "dwMicroSecPerFrame"   );
        c.writeUIntR(32, d.dwMaxBytesPerSec     , "dwMaxBytesPerSec"     );
        c.writeUIntR(32, d.dwPaddingGranularity , "dwPaddingGranularity" );
        c.writeUIntR(32, d.dwFlags              , "dwFlags"              );
        c.writeUIntR(32, d.dwTotalFrames        , "dwTotalFrames"        );
        c.writeUIntR(32, d.dwInitialFrames      , "dwInitialFrames"      );
        c.writeUIntR(32, d.dwStreams            , "dwStreams"            );
        c.writeUIntR(32, d.dwSuggestedBufferSize, "dwSuggestedBufferSize");
        c.writeUIntR(32, d.dwWidth              , "dwWidth"              );
        c.writeUIntR(32, d.dwHeight             , "dwHeight"             );
        c.writeUIntR(32, d.dwReserved0          , "dwReserved0"          );
        c.writeUIntR(32, d.dwReserved1          , "dwReserved1"          );
        c.writeUIntR(32, d.dwReserved2          , "dwReserved2"          );
        c.writeUIntR(32, d.dwReserved3          , "dwReserved3"          );

        c.leaveBlock();
    }
}
