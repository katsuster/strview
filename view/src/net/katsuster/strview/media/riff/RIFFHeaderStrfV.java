package net.katsuster.strview.media.riff;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * strf (AVI Stream Format), struct BITMAPINFOHEADER
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
public class RIFFHeaderStrfV extends RIFFHeader
        implements Cloneable {
    public UIntR biSize;
    public SIntR biWidth;
    public SIntR biHeight;
    public UIntR biPlanes;
    public UIntR biBitCount;
    public UIntR biCompression;
    public UIntR biSizeImage;
    public SIntR biXPelsPerMeter;
    public SIntR biYPelsPerMeter;
    public UIntR biClrUsed;
    public UIntR biClrImportant;

    public RIFFHeaderStrfV() {
        biSize = new UIntR();
        biWidth = new SIntR();
        biHeight = new SIntR();
        biPlanes = new UIntR();
        biBitCount = new UIntR();
        biCompression = new UIntR();
        biSizeImage = new UIntR();
        biXPelsPerMeter = new SIntR();
        biYPelsPerMeter = new SIntR();
        biClrUsed = new UIntR();
        biClrImportant = new UIntR();
    }

    public RIFFHeaderStrfV clone()
            throws CloneNotSupportedException {
        RIFFHeaderStrfV obj = (RIFFHeaderStrfV)super.clone();

        obj.biSize = (UIntR)biSize.clone();
        obj.biWidth = (SIntR)biWidth.clone();
        obj.biHeight = (SIntR)biHeight.clone();
        obj.biPlanes = (UIntR)biPlanes.clone();
        obj.biBitCount = (UIntR)biBitCount.clone();
        obj.biCompression = (UIntR)biCompression.clone();
        obj.biSizeImage = (UIntR)biSizeImage.clone();
        obj.biXPelsPerMeter = (SIntR)biXPelsPerMeter.clone();
        obj.biYPelsPerMeter = (SIntR)biYPelsPerMeter.clone();
        obj.biClrUsed = (UIntR)biClrUsed.clone();
        obj.biClrImportant = (UIntR)biClrImportant.clone();

        return obj;
    }

    @Override
    public void read(PacketReader<?> c) {
        read(c, this);
    }

    public static void read(PacketReader<?> c,
                            RIFFHeaderStrfV d) {
        c.enterBlock("strf chunk (video)");

        RIFFHeader.read(c, d);

        d.biSize          = c.readUIntR(32, d.biSize         );
        d.biWidth         = c.readSIntR(32, d.biWidth        );
        d.biHeight        = c.readSIntR(32, d.biHeight       );
        d.biPlanes        = c.readUIntR(16, d.biPlanes       );
        d.biBitCount      = c.readUIntR(16, d.biBitCount     );
        d.biCompression   = c.readUIntR(32, d.biCompression  );
        d.biSizeImage     = c.readUIntR(32, d.biSizeImage    );
        d.biXPelsPerMeter = c.readSIntR(32, d.biXPelsPerMeter);
        d.biYPelsPerMeter = c.readSIntR(32, d.biYPelsPerMeter);
        d.biClrUsed       = c.readUIntR(32, d.biClrUsed      );
        d.biClrImportant  = c.readUIntR(32, d.biClrImportant );

        c.leaveBlock();
    }

    @Override
    public void write(PacketWriter<?> c) {
        write(c, this);
    }

    public static void write(PacketWriter<?> c,
                             RIFFHeaderStrfV d) {
        c.enterBlock("strf chunk (video)");

        RIFFHeader.write(c, d);

        c.writeUIntR(32, d.biSize         , "biSize"         );
        c.writeSIntR(32, d.biWidth        , "biWidth"        );
        c.writeSIntR(32, d.biHeight       , "biHeight"       );
        c.writeUIntR(16, d.biPlanes       , "biPlanes"       );
        c.writeUIntR(16, d.biBitCount     , "biBitCount"     );
        c.writeUIntR(32, d.biCompression  , "biCompression"  );
        c.writeUIntR(32, d.biSizeImage    , "biSizeImage"    );
        c.writeSIntR(32, d.biXPelsPerMeter, "biXPelsPerMeter");
        c.writeSIntR(32, d.biYPelsPerMeter, "biYPelsPerMeter");
        c.writeUIntR(32, d.biClrUsed      , "biClrUsed"      );
        c.writeUIntR(32, d.biClrImportant , "biClrImportant" );

        c.leaveBlock();
    }
}
