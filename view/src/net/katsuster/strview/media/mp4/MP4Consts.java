package net.katsuster.strview.media.mp4;

import java.io.*;

import net.katsuster.strview.media.*;

/**
 * <p>
 * MPEG4 media file format 規格由来の定数、定数名定義用のクラス。
 * </p>
 */
public class MP4Consts {
    //MP4 ボックスのファクトリ
    public static final PacketFactory<MP4Header, Integer> mp4Factory =
            new PacketFactory<>(MP4Header.class);
    static {
        //Container
        //mp4Factory.put(BOX_TYPE.AVCC, MP4HeaderAvcc.class);
        //mp4Factory.put(BOX_TYPE.BTRT, MP4HeaderBtrt.class);
        //mp4Factory.put(BOX_TYPE.CO64, MP4HeaderCo64.class);
        //mp4Factory.put(BOX_TYPE.CTTS, MP4HeaderCtts.class);
        mp4Factory.put(BOX_TYPE.DINF, MP4HeaderRecursive.class);
        mp4Factory.put(BOX_TYPE.EDTS, MP4HeaderRecursive.class);
        //mp4Factory.put(BOX_TYPE.ESDS, MP4HeaderEsds.class);
        //mp4Factory.put(BOX_TYPE.FTYP, MP4HeaderFtyp.class);
        mp4Factory.put(BOX_TYPE.HDLR, MP4HeaderHdlr.class);
        mp4Factory.put(BOX_TYPE.ILST, MP4HeaderRecursive.class);
        //mp4Factory.put(BOX_TYPE.IODS, MP4HeaderIods.class);
        mp4Factory.put(BOX_TYPE.MDHD, MP4HeaderMdhd.class);
        mp4Factory.put(BOX_TYPE.MDIA, MP4HeaderRecursive.class);
        mp4Factory.put(BOX_TYPE.META, MP4HeaderRecursiveFull.class);
        //mp4Factory.put(BOX_TYPE.MFHD, MP4HeaderMfhd.class);
        mp4Factory.put(BOX_TYPE.MFRA, MP4HeaderRecursive.class);
        //mp4Factory.put(BOX_TYPE.MFRO, MP4HeaderMfro.class);
        mp4Factory.put(BOX_TYPE.MINF, MP4HeaderRecursive.class);
        mp4Factory.put(BOX_TYPE.MOOF, MP4HeaderRecursive.class);
        mp4Factory.put(BOX_TYPE.MOOV, MP4HeaderRecursive.class);
        mp4Factory.put(BOX_TYPE.MVHD, MP4HeaderMvhd.class);
        mp4Factory.put(BOX_TYPE.NMHD, MP4HeaderFull.class);
        mp4Factory.put(BOX_TYPE.SMHD, MP4HeaderSmhd.class);
        mp4Factory.put(BOX_TYPE.STBL, MP4HeaderRecursive.class);
        //mp4Factory.put(BOX_TYPE.STCO, MP4HeaderStco.class);
        //mp4Factory.put(BOX_TYPE.STSC, MP4HeaderStsc.class);
        mp4Factory.put(BOX_TYPE.STSD, MP4HeaderStsd.class);
        //mp4Factory.put(BOX_TYPE.STSS, MP4HeaderStss.class);
        //mp4Factory.put(BOX_TYPE.STSZ, MP4HeaderStsz.class);
        //mp4Factory.put(BOX_TYPE.STTS, MP4HeaderStts.class);
        //mp4Factory.put(BOX_TYPE.TFHD, MP4HeaderTfhd.class);
        mp4Factory.put(BOX_TYPE.TKHD, MP4HeaderTkhd.class);
        mp4Factory.put(BOX_TYPE.TRAF, MP4HeaderRecursive.class);
        mp4Factory.put(BOX_TYPE.TRAK, MP4HeaderRecursive.class);
        mp4Factory.put(BOX_TYPE.UDTA, MP4HeaderRecursive.class);
        mp4Factory.put(BOX_TYPE.UUID, MP4Header.class);
        mp4Factory.put(BOX_TYPE.VMHD, MP4HeaderVmhd.class);

        //Video
        mp4Factory.put(BOX_TYPE.AVC1, MP4HeaderVisualSample.class);
        mp4Factory.put(BOX_TYPE.MP4V, MP4HeaderVisualSample.class);

        //Audio
        mp4Factory.put(BOX_TYPE.MP4A, MP4HeaderAudioSample.class);
    }

    protected MP4Consts() {
        //do nothing
    }

    public static String getTypeName(int id) {
        String name = "..unknown..";
        byte[] b = new byte[4];

        try {
            b[0] = (byte)((id >>> 24) & 0xff);
            b[1] = (byte)((id >>> 16) & 0xff);
            b[2] = (byte)((id >>>  8) & 0xff);
            b[3] = (byte)((id >>>  0) & 0xff);
            name = new String(b, "US-ASCII");
        } catch (UnsupportedEncodingException e) {
            //do nothing
        }

        return name;
    }

    public static class BOX_TYPE {
        public static final int AVCC = 0x61766343; //avcC
        public static final int BTRT = 0x62747274;
        public static final int CO64 = 0x636f3634;
        public static final int CTTS = 0x63747473;
        public static final int DINF = 0x64696e66;
        public static final int EDTS = 0x65647473;
        public static final int ESDS = 0x65736473;
        public static final int FTYP = 0x66747970;
        public static final int HDLR = 0x68646c72;
        public static final int ILST = 0x696c7374;
        public static final int IODS = 0x696f6473;
        public static final int MDHD = 0x6d646864;
        public static final int MDIA = 0x6d646961;
        public static final int META = 0x6d657461;
        public static final int MFHD = 0x6d666864;
        public static final int MFRA = 0x6d667261;
        public static final int MFRO = 0x6d66726f;
        public static final int MINF = 0x6d696e66;
        public static final int MOOF = 0x6d6f6f66;
        public static final int MOOV = 0x6d6f6f76;
        public static final int MVHD = 0x6d766864;
        public static final int NMHD = 0x6e6d6864;
        public static final int SMHD = 0x736d6864;
        public static final int STBL = 0x7374626c;
        public static final int STCO = 0x7374636f;
        public static final int STSC = 0x73747363;
        public static final int STSD = 0x73747364;
        public static final int STSS = 0x73747373;
        public static final int STSZ = 0x7374737a;
        public static final int STTS = 0x73747473;
        public static final int TFHD = 0x74666864;
        public static final int TKHD = 0x746b6864;
        public static final int TRAF = 0x74726166;
        public static final int TRAK = 0x7472616b;
        public static final int UDTA = 0x75647461;
        public static final int UUID = 0x75756964;
        public static final int VMHD = 0x766d6864;

        //Video
        public static final int AVC1 = 0x61766331;
        public static final int MP4V = 0x6d703476;

        //Audio
        public static final int MP4A = 0x6d703461;
    }
}
