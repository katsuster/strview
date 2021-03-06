package net.katsuster.strview.media.riff;

import net.katsuster.strview.util.bit.*;
import net.katsuster.strview.media.bit.*;

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
public class RIFFHeaderStrh
        extends RIFFHeader
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
        fccType         = new UIntR("fccType"        );
        fccHandler      = new UIntR("fccHandler"     );
        dwFlags         = new UIntR("dwFlags"        );
        wPriority       = new UIntR("wPriority"      );
        wLanguage       = new UIntR("wLanguage"      );
        dwInitialFrames = new UIntR("dwInitialFrames");
        dwScale         = new UIntR("dwScale"        );
        dwRate          = new UIntR("dwRate"         );
        dwStart         = new UIntR("dwStart"        );
        dwLength        = new UIntR("dwLength"       );
        dwSuggestedBufferSize = new UIntR("dwSuggestedBufferSize");
        dwQuality       = new UIntR("dwQuality"      );
        dwSampleSize    = new UIntR("dwSampleSize"   );
        left            = new SIntR("left"           );
        top             = new SIntR("top"            );
        right           = new SIntR("right"          );
        bottom          = new SIntR("bottom"         );
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
    public String getTypeName() {
        return "strh chunk";
    }

    @Override
    protected void readBits(BitStreamReader c) {
        readBits(c, this);
    }

    public static void readBits(BitStreamReader c,
                                RIFFHeaderStrh d) {
        c.enterBlock(d);

        RIFFHeader.readBits(c, d);

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
    protected void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 RIFFHeaderStrh d) {
        c.enterBlock(d);

        RIFFHeader.writeBits(c, d);

        c.writeUIntR(32, d.fccType              , d.getFccTypeName());
        c.writeUIntR(32, d.fccHandler           , d.getFccHandlerName());
        c.writeUIntR(32, d.dwFlags              );
        c.writeUIntR(16, d.wPriority            );
        c.writeUIntR(16, d.wLanguage            );
        c.writeUIntR(32, d.dwInitialFrames      );
        c.writeUIntR(32, d.dwScale              );
        c.writeUIntR(32, d.dwRate               );
        c.writeUIntR(32, d.dwStart              );
        c.writeUIntR(32, d.dwLength             );
        c.writeUIntR(32, d.dwSuggestedBufferSize);
        c.writeUIntR(32, d.dwQuality            );
        c.writeUIntR(32, d.dwSampleSize         );
        c.writeSIntR(16, d.left                 );
        c.writeSIntR(16, d.top                  );
        c.writeSIntR(16, d.right                );
        c.writeSIntR(16, d.bottom               );

        c.leaveBlock();
    }

    public String getFccTypeName() {
        return RIFFConsts.getChunkIdName(fccType.intValue());
    }

    public String getFccHandlerName() {
        return RIFFConsts.getChunkIdName(fccHandler.intValue());
    }
}
