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
    public UInt biSize;
    public SInt biWidth;
    public SInt biHeight;
    public UInt biPlanes;
    public UInt biBitCount;
    public UInt biCompression;
    public UInt biSizeImage;
    public SInt biXPelsPerMeter;
    public SInt biYPelsPerMeter;
    public UInt biClrUsed;
    public UInt biClrImportant;

    public RIFFHeaderStrfV() {
        biSize = new UInt();
        biWidth = new SInt();
        biHeight = new SInt();
        biPlanes = new UInt();
        biBitCount = new UInt();
        biCompression = new UInt();
        biSizeImage = new UInt();
        biXPelsPerMeter = new SInt();
        biYPelsPerMeter = new SInt();
        biClrUsed = new UInt();
        biClrImportant = new UInt();
    }

    public RIFFHeaderStrfV clone()
            throws CloneNotSupportedException {
        RIFFHeaderStrfV obj = (RIFFHeaderStrfV)super.clone();

        obj.biSize = (UInt)biSize.clone();
        obj.biWidth = (SInt)biWidth.clone();
        obj.biHeight = (SInt)biHeight.clone();
        obj.biPlanes = (UInt)biPlanes.clone();
        obj.biBitCount = (UInt)biBitCount.clone();
        obj.biCompression = (UInt)biCompression.clone();
        obj.biSizeImage = (UInt)biSizeImage.clone();
        obj.biXPelsPerMeter = (SInt)biXPelsPerMeter.clone();
        obj.biYPelsPerMeter = (SInt)biYPelsPerMeter.clone();
        obj.biClrUsed = (UInt)biClrUsed.clone();
        obj.biClrImportant = (UInt)biClrImportant.clone();

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
