package net.katsuster.strview.media.ps;

import net.katsuster.strview.media.*;

/**
 * <p>
 * PS パケットの規格由来の定数、定数名定義用のクラス。
 * </p>
 */
public class PSConsts {
    //PS パケットのファクトリ
    public static final PacketFactory<PSHeader, Integer> psFactory =
            new PacketFactory<>(PSHeader.class);
    static {
        for (int i = 0; i <= 0xff; i++) {
            //0x00～0xff で既知ではない ID は全て
            //PES ヘッダ付きのエレメンタリストリームとする
            psFactory.put(i, PSHeaderPESStream.class);
        }

        psFactory.put(STREAM_ID.MPEG_PROGRAM_END, PSHeader.class);
        psFactory.put(STREAM_ID.PACK_START, PSHeaderPack.class);
        psFactory.put(STREAM_ID.SYSTEM_HEADER_START, PSHeaderSystem.class);
        psFactory.put(STREAM_ID.PROGRAM_STREAM_MAP, PSHeaderPESRaw.class);
        psFactory.put(STREAM_ID.PRIVATE_2, PSHeaderPESRaw.class);
        psFactory.put(STREAM_ID.ECM, PSHeaderPESRaw.class);
        psFactory.put(STREAM_ID.EMM, PSHeaderPESRaw.class);
        psFactory.put(STREAM_ID.PROGRAM_STREAM_DIR, PSHeaderPESRaw.class);
        psFactory.put(STREAM_ID.DSMCC, PSHeaderPESRaw.class);
        psFactory.put(STREAM_ID.H222_1_E, PSHeaderPESRaw.class);
        psFactory.put(STREAM_ID.PADDING, PSHeaderPESPadding.class);
    }

    protected PSConsts() {
        //do nothing
    }

    public static String getStreamIdName(int id) {
        String name = "..unknown..";

        //ISO 13818-1
        switch (id) {
        case STREAM_ID.MPEG_PROGRAM_END:
            name = "MPEG_program_end_code";
            break;
        case STREAM_ID.PACK_START:
            name = "pack_start_code";
            break;
        case STREAM_ID.SYSTEM_HEADER_START:
            name = "system_header_start_code";
            break;

        case STREAM_ID.PROGRAM_STREAM_MAP:
            name = "program_map_stream";
            break;
        case STREAM_ID.PRIVATE_1:
            name = "private_stream_1";
            break;
        case STREAM_ID.PADDING:
            name = "padding_stream";
            break;
        case STREAM_ID.PRIVATE_2:
            name = "private_stream_2";
            break;

        case STREAM_ID.ECM:
            name = "ECM_stream";
            break;
        case STREAM_ID.EMM:
            name = "EMM_stream";
            break;
        case STREAM_ID.DSMCC:
            name = "ISO 13818 Annex.A or "
                    + "13818-6 DSMCC_stream";
            break;
        case STREAM_ID.ISO_13522:
            name = "ISO 13522 stream";
            break;
        case STREAM_ID.H222_1_A:
            name = "H222.1 type A";
            break;
        case STREAM_ID.H222_1_B:
            name = "H222.1 type B";
            break;
        case STREAM_ID.H222_1_C:
            name = "H222.1 type C";
            break;
        case STREAM_ID.H222_1_D:
            name = "H222.1 type D";
            break;
        case STREAM_ID.H222_1_E:
            name = "H222.1 type E";
            break;
        case STREAM_ID.ANCILLARY:
            name = "ancillary_stream";
            break;
        case STREAM_ID.SL:
            name = "ISO 14496-1 SL-packetized_stream";
            break;
        case STREAM_ID.FLEXMUX:
            name = "ISO 14496-1 FlexMux_stream";
            break;
        case STREAM_ID.METADATA:
            name = "metadata stream";
            break;
        case STREAM_ID.EXTENDED:
            name = "extended_stream_id";
            break;
        case STREAM_ID.ISO_13818_RES1:
            name = "reserved data stream";
            break;
        case STREAM_ID.PROGRAM_STREAM_DIR:
            name = "program_stream_directory";
            break;
        default:
            //do nothing
            break;
        }

        if (STREAM_ID.AUDIO_START <= id
                && id <= STREAM_ID.AUDIO_END) {
            name = "ISO 13818-3, "
                    + "11172-3, 13818-7, 14496-3 audio";
        } else if (STREAM_ID.VIDEO_START <= id
                && id <= STREAM_ID.VIDEO_END) {
            name = "H.262 or "
                    + "ISO 13818-2, 11172-2, 13818-7, 14496-2 video";
        }

        return name;
    }

    public static String getStreamIdNameSystemHeader(int id) {
        String name = "..unknown..";

        if (id >= 0xbc) {
            return getStreamIdName(id);
        }

        switch (id) {
        case STREAM_ID.SYSH_FOR_AUDIO:
            name = "for all audio";
            break;
        case STREAM_ID.SYSH_FOR_VIDEO:
            name = "for all video";
            break;
        case STREAM_ID.SYSH_FOR_ALL:
            name = "for all elementary";
            break;
        case STREAM_ID.SYSH_EXTENSION:
            name = "stream_id_extension";
            break;
        }

        return name;
    }

    //Table 2-22: stream_id assignments
    public static class STREAM_ID {
        //system_header(): For all audio streams
        public static final int SYSH_FOR_AUDIO = 0xb8; //1011 1000
        //system_header(): For all video streams
        public static final int SYSH_FOR_VIDEO = 0xb9; //1011 1001
        //system_header(): For all elementary streams
        public static final int SYSH_FOR_ALL = 0xfd; //1111 1101
        //system_header(): stream_id_extension
        public static final int SYSH_EXTENSION = 0xb7; //1011 0111

        //[ISO 13818-1] MPEG_program_end_code
        public static final int MPEG_PROGRAM_END = 0xb9; //1011 1001
        //[ISO 13818-1] pack_start_code
        public static final int PACK_START = 0xba; //1011 1010
        //[ISO 13818-1] system_header_start_code
        public static final int SYSTEM_HEADER_START = 0xbb; //1011 1011

        //[ISO 13818-1] program_map_stream
        public static final int PROGRAM_STREAM_MAP = 0xbc; //1011 1100
        //[ISO 13818-1] private_stream_1
        public static final int PRIVATE_1 = 0xbd; //1011 1101
        //[ISO 13818-1] padding_stream
        public static final int PADDING = 0xbe; //1011 1110
        //[ISO 13818-1] private_stream_2
        public static final int PRIVATE_2 = 0xbf; //1011 1111

        //[ISO 13818-1] ISO 13818-3, 11172-3, 13818-7, 14496-3 audio
        public static final int AUDIO_START = 0xc0; //110x xxxx
        //[ISO 13818-1] ISO 13818-3, 11172-3, 13818-7, 14496-3 audio
        public static final int AUDIO_END   = 0xdf; //110x xxxx

        //[ISO 13818-1] H.262 or ISO 13818-2, 11172-2, 13818-7, 14496-2 video
        public static final int VIDEO_START = 0xe0; //1110 xxxx
        //[ISO 13818-1] H.262 or ISO 13818-2, 11172-2, 13818-7, 14496-2 video
        public static final int VIDEO_END   = 0xef; //1110 xxxx

        //[ISO 13818-1] ECM_stream
        public static final int ECM = 0xf0; //1111 0000
        //[ISO 13818-1] EMM_stream
        public static final int EMM = 0xf1; //1111 0001
        //[ISO 13818-1] ISO 13818 Annex.A or 13818-6 DSMCC_stream
        public static final int DSMCC = 0xf2; //1111 0010
        //[ISO 13818-1] ISO 13522 stream
        public static final int ISO_13522 = 0xf3; //1111 0011
        //[ISO 13818-1] H222.1 type A
        public static final int H222_1_A = 0xf4; //1111 0100
        //[ISO 13818-1] H222.1 type B
        public static final int H222_1_B = 0xf5; //1111 0101
        //[ISO 13818-1] H222.1 type C
        public static final int H222_1_C = 0xf6; //1111 0110
        //[ISO 13818-1] H222.1 type D
        public static final int H222_1_D = 0xf7; //1111 0111
        //[ISO 13818-1] H222.1 type E
        public static final int H222_1_E = 0xf8; //1111 1000
        //[ISO 13818-1] ancillary_stream
        public static final int ANCILLARY = 0xf9; //1111 1001
        //[ISO 13818-1] ISO 14496-1 SL-packetized_stream
        public static final int SL = 0xfa; //1111 1010
        //[ISO 13818-1] ISO 14496-1 FlexMux_stream
        public static final int FLEXMUX = 0xfb; //1111 1011
        //[ISO 13818-1] metadata stream
        public static final int METADATA = 0xfc; //1111 1100
        //[ISO 13818-1] extended_stream_id
        public static final int EXTENDED = 0xfd; //1111 1101
        //[ISO 13818-1] reserved data stream
        public static final int ISO_13818_RES1 = 0xfe; //1111 1110

        //[ISO 13818-1] program_stream_directory
        public static final int PROGRAM_STREAM_DIR = 0xff; //1111 1111
    }
}
