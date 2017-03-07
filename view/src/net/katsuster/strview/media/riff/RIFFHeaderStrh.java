package net.katsuster.strview.media.riff;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * strh (AVI Stream Header)
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
public class RIFFHeaderStrh extends RIFFHeader
        implements Cloneable {
    public UInt fccType;
    public UInt fccHandler;
    public UInt dwFlags;
    public UInt wPriority;
    public UInt wLanguage;
    public UInt dwInitialFrames;
    public UInt dwScale;
    public UInt dwRate;
    public UInt dwStart;
    public UInt dwLength;
    public UInt dwSuggestedBufferSize;
    public UInt dwQuality;
    public UInt dwSampleSize;
    public SInt left;
    public SInt top;
    public SInt right;
    public SInt bottom;

    public RIFFHeaderStrh() {
        fccType = new UInt();
        fccHandler = new UInt();
        dwFlags = new UInt();
        wPriority = new UInt();
        wLanguage = new UInt();
        dwInitialFrames = new UInt();
        dwScale = new UInt();
        dwRate = new UInt();
        dwStart = new UInt();
        dwLength = new UInt();
        dwSuggestedBufferSize = new UInt();
        dwQuality = new UInt();
        dwSampleSize = new UInt();
        left = new SInt();
        top = new SInt();
        right = new SInt();
        bottom = new SInt();
    }

    @Override
    public RIFFHeaderStrh clone()
            throws CloneNotSupportedException {
        RIFFHeaderStrh obj = (RIFFHeaderStrh)super.clone();

        obj.fccType = (UInt)fccType.clone();
        obj.fccHandler = (UInt)fccHandler.clone();
        obj.dwFlags = (UInt)dwFlags.clone();
        obj.wPriority = (UInt)wPriority.clone();
        obj.wLanguage = (UInt)wLanguage.clone();
        obj.dwInitialFrames = (UInt)dwInitialFrames.clone();
        obj.dwScale = (UInt)dwScale.clone();
        obj.dwRate = (UInt)dwRate.clone();
        obj.dwStart = (UInt)dwStart.clone();
        obj.dwLength = (UInt)dwLength.clone();
        obj.dwSuggestedBufferSize = (UInt)dwSuggestedBufferSize.clone();
        obj.dwQuality = (UInt)dwQuality.clone();
        obj.dwSampleSize = (UInt)dwSampleSize.clone();
        obj.left = (SInt)left.clone();
        obj.top = (SInt)top.clone();
        obj.right = (SInt)right.clone();
        obj.bottom = (SInt)bottom.clone();

        return obj;
    }

    @Override
    public void read(PacketReader<?> c) {
        read(c, this);
    }

    public static void read(PacketReader<?> c,
                            RIFFHeaderStrh d) {
        c.enterBlock("strh chunk");

        RIFFHeader.read(c, d);

        d.fccType               = c.readUIntR(32, d.fccType              );
        d.fccHandler            = c.readUIntR(32, d.fccHandler           );
        d.dwFlags               = c.readUIntR(32, d.dwFlags              );
        d.wPriority             = c.readUIntR(16, d.wPriority            );
        d.wLanguage             = c.readUIntR(16, d.wLanguage            );
        d.dwInitialFrames       = c.readUIntR(32, d.dwInitialFrames      );
        d.dwScale               = c.readUIntR(32, d.dwScale              );
        d.dwRate                = c.readUIntR(32, d.dwRate               );
        d.dwStart               = c.readUIntR(32, d.dwStart              );
        d.dwLength              = c.readUIntR(32, d.dwLength             );
        d.dwSuggestedBufferSize = c.readUIntR(32, d.dwSuggestedBufferSize);
        d.dwQuality             = c.readUIntR(32, d.dwQuality            );
        d.dwSampleSize          = c.readUIntR(32, d.dwSampleSize         );
        d.left                  = c.readSIntR(16, d.left                 );
        d.top                   = c.readSIntR(16, d.top                  );
        d.right                 = c.readSIntR(16, d.right                );
        d.bottom                = c.readSIntR(16, d.bottom               );

        c.leaveBlock();
    }

    @Override
    public void write(PacketWriter<?> c) {
        write(c, this);
    }

    public static void write(PacketWriter<?> c,
                             RIFFHeaderStrh d) {
        c.enterBlock("strh chunk");

        RIFFHeader.write(c, d);

        c.writeUIntR(32, d.fccType              , "fccType"              , d.getFccTypeName());
        c.writeUIntR(32, d.fccHandler           , "fccHandler"           , d.getFccHandlerName());
        c.writeUIntR(32, d.dwFlags              , "dwFlags"              );
        c.writeUIntR(16, d.wPriority            , "wPriority"            );
        c.writeUIntR(16, d.wLanguage            , "wLanguage"            );
        c.writeUIntR(32, d.dwInitialFrames      , "dwInitialFrames"      );
        c.writeUIntR(32, d.dwScale              , "dwScale"              );
        c.writeUIntR(32, d.dwRate               , "dwRate"               );
        c.writeUIntR(32, d.dwStart              , "dwStart"              );
        c.writeUIntR(32, d.dwLength             , "dwLength"             );
        c.writeUIntR(32, d.dwSuggestedBufferSize, "dwSuggestedBufferSize");
        c.writeUIntR(32, d.dwQuality            , "dwQuality"            );
        c.writeUIntR(32, d.dwSampleSize         , "dwSampleSize"         );
        c.writeSIntR(16, d.left                 , "left"                 );
        c.writeSIntR(16, d.top                  , "top"                  );
        c.writeSIntR(16, d.right                , "right"                );
        c.writeSIntR(16, d.bottom               , "bottom"               );

        c.leaveBlock();
    }

    public String getFccTypeName() {
        return RIFFConsts.getChunkIdName(fccType.intValue());
    }

    public String getFccHandlerName() {
        return RIFFConsts.getChunkIdName(fccHandler.intValue());
    }
}
