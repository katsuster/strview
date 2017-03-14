package net.katsuster.strview.media.riff;

import java.io.*;

import net.katsuster.strview.media.*;

/**
 * <p>
 * RIFF(Resource Interchange File Format) 規格由来の定数、
 * 定数名定義用のクラス。
 * </p>
 *
 * <p>
 * 参考規格
 * </p>
 * <ul>
 * <li>Microsoft Multimedia Standards Update: Revision 1.0.97</li>
 * <li>OpenDML AVI File Format Extensions: Version 1.02</li>
 * </ul>
 *
 * @author katsuhiro
 */
public class RIFFConsts {
    //RIFF チャンクのファクトリ
    public static final PacketFactory<RIFFHeader, Integer> riffFactory =
            new PacketFactory<>(RIFFHeader.class);
    static {
        riffFactory.put(CHUNK_ID.RIFF, RIFFHeaderList.class);
        riffFactory.put(CHUNK_ID.LIST, RIFFHeaderList.class);
        riffFactory.put(CHUNK_ID.FMT, RIFFHeaderFmt.class);
        riffFactory.put(CHUNK_ID.AVIH, RIFFHeaderAvih.class);
        riffFactory.put(CHUNK_ID.STRH, RIFFHeaderStrh.class);
        riffFactory.put(CHUNK_ID.DMLH, RIFFHeaderDmlh.class);
        riffFactory.put(CHUNK_ID.IDX1, RIFFHeaderIdx1.class);

        riffFactory.put(CHUNK_ID.IARL, RIFFHeaderInfo.class);
        riffFactory.put(CHUNK_ID.IART, RIFFHeaderInfo.class);
        riffFactory.put(CHUNK_ID.ICMS, RIFFHeaderInfo.class);
        riffFactory.put(CHUNK_ID.ICMT, RIFFHeaderInfo.class);
        riffFactory.put(CHUNK_ID.ICOP, RIFFHeaderInfo.class);
        riffFactory.put(CHUNK_ID.ICRD, RIFFHeaderInfo.class);
        riffFactory.put(CHUNK_ID.ICRP, RIFFHeaderInfo.class);
        riffFactory.put(CHUNK_ID.IDIM, RIFFHeaderInfo.class);
        riffFactory.put(CHUNK_ID.IDPI, RIFFHeaderInfo.class);
        riffFactory.put(CHUNK_ID.IENG, RIFFHeaderInfo.class);
        riffFactory.put(CHUNK_ID.IGNR, RIFFHeaderInfo.class);
        riffFactory.put(CHUNK_ID.IKEY, RIFFHeaderInfo.class);
        riffFactory.put(CHUNK_ID.ILGT, RIFFHeaderInfo.class);
        riffFactory.put(CHUNK_ID.IMED, RIFFHeaderInfo.class);
        riffFactory.put(CHUNK_ID.INAM, RIFFHeaderInfo.class);
        riffFactory.put(CHUNK_ID.IPLT, RIFFHeaderInfo.class);
        riffFactory.put(CHUNK_ID.IPRD, RIFFHeaderInfo.class);
        riffFactory.put(CHUNK_ID.ISBJ, RIFFHeaderInfo.class);
        riffFactory.put(CHUNK_ID.ISFT, RIFFHeaderInfo.class);
        riffFactory.put(CHUNK_ID.ISHP, RIFFHeaderInfo.class);
        riffFactory.put(CHUNK_ID.ISRC, RIFFHeaderInfo.class);
        riffFactory.put(CHUNK_ID.ISRF, RIFFHeaderInfo.class);
        riffFactory.put(CHUNK_ID.ITCH, RIFFHeaderInfo.class);
    }

    protected RIFFConsts() {
        //do nothing
    }

    public static String getChunkIdName(int id) {
        String name = "..unknown..";
        byte[] b = new byte[4];

        try {
            b[0] = (byte)((id >>>  0) & 0xff);
            b[1] = (byte)((id >>>  8) & 0xff);
            b[2] = (byte)((id >>> 16) & 0xff);
            b[3] = (byte)((id >>> 24) & 0xff);
            name = new String(b, "US-ASCII");
        } catch (UnsupportedEncodingException e) {
            //do nothing
        }

        return name;
    }

    //RIFF chunk ID（32bit リトルエンディアン）
    public static class CHUNK_ID {
        //RIFF
        public static final int RIFF = 0x46464952;
        //LIST
        public static final int LIST = 0x5453494c;
        //fmt : Format
        public static final int FMT  = 0x20746d66;
        //avih: Main AVI Header
        public static final int AVIH = 0x68697661;
        //strh: AVI Stream Header
        public static final int STRH = 0x68727473;
        //strf: AVI Stream Format
        public static final int STRF = 0x66727473;
        //dmlh: Extended AVI Header
        public static final int DMLH = 0x686c6d64;
        //idx1: AVI 1.0 Index
        public static final int IDX1 = 0x31786469;

        //IARL: Archival Location
        public static final int IARL = 0x4c524149;
        //IART: Artist
        public static final int IART = 0x54524149;
        //ICMS: Commissioned
        public static final int ICMS = 0x534d4349;
        //ICMT: Comments
        public static final int ICMT = 0x544d4349;
        //ICOP: Copyright
        public static final int ICOP = 0x504f4349;
        //ICRD: Creation date
        public static final int ICRD = 0x44524349;
        //ICRP: Cropped
        public static final int ICRP = 0x50524349;
        //IDIM: Dimentions
        public static final int IDIM = 0x4d494449;
        //IDPI: Dots Per Inch
        public static final int IDPI = 0x49504449;
        //IENG: Engineer
        public static final int IENG = 0x474e4549;
        //IGNR: Genre
        public static final int IGNR = 0x524e4749;
        //IKEY: Keywords
        public static final int IKEY = 0x59454b49;
        //ILGT: Lightness
        public static final int ILGT = 0x54474c49;
        //IMED: Medium
        public static final int IMED = 0x44454d49;
        //INAM: Name
        public static final int INAM = 0x4d414e49;
        //IPLT: Palette Setting
        public static final int IPLT = 0x544c5049;
        //IPRD: Product
        public static final int IPRD = 0x44525049;
        //ISBJ: Subject
        public static final int ISBJ = 0x4a425349;
        //ISFT: Software
        public static final int ISFT = 0x54465349;
        //ISHP: Sharpness
        public static final int ISHP = 0x50485349;
        //ISRC: Source
        public static final int ISRC = 0x43525349;
        //ISRF: Source Form
        public static final int ISRF = 0x46525349;
        //ITCH: Technician
        public static final int ITCH = 0x48435449;
    }

    //strh チャンク fccType（32bit リトルエンディアン）
    public static class FCC_TYPE {
        //'auds'
        public static final int AUDS = 0x73647561;
        //'mids'
        public static final int MIDS = 0x7364696d;
        //'txts'
        public static final int TXTS = 0x73747874;
        //'vids'
        public static final int VIDS = 0x73646976;
    }
}
