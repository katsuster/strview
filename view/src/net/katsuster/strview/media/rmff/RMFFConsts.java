package net.katsuster.strview.media.rmff;

import java.io.*;

import net.katsuster.strview.media.*;

/**
 * <p>
 * RMFF (RealMedia File Format) 規格由来の定数、
 * 定数名定義用のクラス。
 * </p>
 *
 * <p>
 * 参考規格
 * </p>
 * <ul>
 * <li>HELIX CLIENT AND SERVER SOFTWARE DEVELOPER’S GUIDE Volume 2
 * : VERSION R5
 * - Appendix E: RealMedia File Format (RMFF) Reference
 * (https://www.helixcommunity.org/projects/common/2003/HCS_SDK_r5/htmfiles/rmff.htm)</li>
 * </ul>
 */
public class RMFFConsts {
    //RMFF チャンクのファクトリ
    public static final PacketFactory<RMFFHeader, Integer> rmffFactory =
            new PacketFactory<>(RMFFHeader.class);
    static {
        //Container
        rmffFactory.put(OBJECT.RMF, RMFFHeaderRMF.class);
        rmffFactory.put(OBJECT.PROP, RMFFHeaderPROP.class);
        rmffFactory.put(OBJECT.MDPR, RMFFHeaderMDPR.class);
        rmffFactory.put(OBJECT.CONT, RMFFHeaderCONT.class);
        rmffFactory.put(OBJECT.DATA, RMFFHeaderDATA.class);
        rmffFactory.put(OBJECT.INDX, RMFFHeaderINDX.class);
    }

    //MDPR チャンクのファクトリ
    public static final PacketFactory<RMFFHeaderMDPR, String> mdprFactory =
            new PacketFactory<>(RMFFHeaderMDPR.class);
    static {
        mdprFactory.put("logical-fileinfo", RMFFHeaderMDPRLogical.class);
        //mdprFactory.put("video/x-pn-realvideo", RMFFHeaderMDPRRV.class);
        //mdprFactory.put("audio/x-pn-realaudio", RMFFHeaderMDPRRA.class);
    }

    protected RMFFConsts() {
        //do nothing
    }

    public static String getObjectIdName(int id) {
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

    public static class OBJECT {
        //'.RMF': ReadMedia File Header
        public static final int RMF  = 0x2e524d46;
        //'PROP': Properties Header
        public static final int PROP = 0x50524f50;
        //'MDPR': Media Properties Header
        public static final int MDPR = 0x4d445052;
        //'CONT': Content Description Header
        public static final int CONT = 0x434f4e54;
        //'DATA': Data Chunk Header
        public static final int DATA = 0x44415441;
        //'INDX': Index Section Header
        public static final int INDX = 0x494e4458;
        //'RMMD': Metadata Section Header
        //TODO: don't have object_version...????
        public static final int RMMD = 0x524d4d44;
        //'RJMD': Metadata Tag
        //TODO: don't have size...????
        public static final int RJMD = 0x524a4d44;
        //'RJME': Metadata Section Footer
        //TODO: don't have size...????
        public static final int RJME = 0x524a4d45;
    }
}
