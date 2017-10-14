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
 */
public class RIFFHeaderStrh extends RIFFHeader
        implements Cloneable {
    public UIntR fccType;
    public UIntR fccHandler;
    public UIntR dwFlags;
    public UIntR wPriority;
    public UIntR wLanguage;
    public UIntR dwInitialFrames;
    public UIntR dwScale;
    public UIntR dwRate;
    public UIntR dwStart;
    public UIntR dwLength;
    public UIntR dwSuggestedBufferSize;
    public UIntR dwQuality;
    public UIntR dwSampleSize;
    public SIntR left;
    public SIntR top;
    public SIntR right;
    public SIntR bottom;

    public RIFFHeaderStrh() {
        fccType = new UIntR();
        fccHandler = new UIntR();
        dwFlags = new UIntR();
        wPriority = new UIntR();
        wLanguage = new UIntR();
        dwInitialFrames = new UIntR();
        dwScale = new UIntR();
        dwRate = new UIntR();
        dwStart = new UIntR();
        dwLength = new UIntR();
        dwSuggestedBufferSize = new UIntR();
        dwQuality = new UIntR();
        dwSampleSize = new UIntR();
        left = new SIntR();
        top = new SIntR();
        right = new SIntR();
        bottom = new SIntR();
    }

    @Override
    public RIFFHeaderStrh clone()
            throws CloneNotSupportedException {
        RIFFHeaderStrh obj = (RIFFHeaderStrh)super.clone();

        obj.fccType = (UIntR)fccType.clone();
        obj.fccHandler = (UIntR)fccHandler.clone();
        obj.dwFlags = (UIntR)dwFlags.clone();
        obj.wPriority = (UIntR)wPriority.clone();
        obj.wLanguage = (UIntR)wLanguage.clone();
        obj.dwInitialFrames = (UIntR)dwInitialFrames.clone();
        obj.dwScale = (UIntR)dwScale.clone();
        obj.dwRate = (UIntR)dwRate.clone();
        obj.dwStart = (UIntR)dwStart.clone();
        obj.dwLength = (UIntR)dwLength.clone();
        obj.dwSuggestedBufferSize = (UIntR)dwSuggestedBufferSize.clone();
        obj.dwQuality = (UIntR)dwQuality.clone();
        obj.dwSampleSize = (UIntR)dwSampleSize.clone();
        obj.left = (SIntR)left.clone();
        obj.top = (SIntR)top.clone();
        obj.right = (SIntR)right.clone();
        obj.bottom = (SIntR)bottom.clone();

        return obj;
    }

    @Override
    public void read(StreamReader<?> c) {
        read(c, this);
    }

    public static void read(StreamReader<?> c,
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
    public void write(StreamWriter<?> c) {
        write(c, this);
    }

    public static void write(StreamWriter<?> c,
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
