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
    public UIntR dwMicroSecPerFrame;
    public UIntR dwMaxBytesPerSec;
    public UIntR dwPaddingGranularity;
    public UIntR dwFlags;
    public UIntR dwTotalFrames;
    public UIntR dwInitialFrames;
    public UIntR dwStreams;
    public UIntR dwSuggestedBufferSize;
    public UIntR dwWidth;
    public UIntR dwHeight;
    public UIntR dwReserved0;
    public UIntR dwReserved1;
    public UIntR dwReserved2;
    public UIntR dwReserved3;

    public RIFFHeaderAvih() {
        dwMicroSecPerFrame = new UIntR();
        dwMaxBytesPerSec = new UIntR();
        dwPaddingGranularity = new UIntR();
        dwFlags = new UIntR();
        dwTotalFrames = new UIntR();
        dwInitialFrames = new UIntR();
        dwStreams = new UIntR();
        dwSuggestedBufferSize = new UIntR();
        dwWidth = new UIntR();
        dwHeight = new UIntR();
        dwReserved0 = new UIntR();
        dwReserved1 = new UIntR();
        dwReserved2 = new UIntR();
        dwReserved3 = new UIntR();
    }

    @Override
    public RIFFHeaderAvih clone()
            throws CloneNotSupportedException {
        RIFFHeaderAvih obj = (RIFFHeaderAvih)super.clone();

        obj.dwMicroSecPerFrame = (UIntR)dwMicroSecPerFrame.clone();
        obj.dwMaxBytesPerSec = (UIntR)dwMaxBytesPerSec.clone();
        obj.dwPaddingGranularity = (UIntR)dwPaddingGranularity.clone();
        obj.dwFlags = (UIntR)dwFlags.clone();
        obj.dwTotalFrames = (UIntR)dwTotalFrames.clone();
        obj.dwInitialFrames = (UIntR)dwInitialFrames.clone();
        obj.dwStreams = (UIntR)dwStreams.clone();
        obj.dwSuggestedBufferSize = (UIntR)dwSuggestedBufferSize.clone();
        obj.dwWidth = (UIntR)dwWidth.clone();
        obj.dwHeight = (UIntR)dwHeight.clone();
        obj.dwReserved0 = (UIntR)dwReserved0.clone();
        obj.dwReserved1 = (UIntR)dwReserved1.clone();
        obj.dwReserved2 = (UIntR)dwReserved2.clone();
        obj.dwReserved3 = (UIntR)dwReserved3.clone();

        return obj;
    }

    @Override
    public void read(StreamReader<?> c) {
        read(c, this);
    }

    public static void read(StreamReader<?> c,
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
    public void write(StreamWriter<?> c) {
        write(c, this);
    }

    public static void write(StreamWriter<?> c,
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
