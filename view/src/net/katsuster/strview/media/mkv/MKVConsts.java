package net.katsuster.strview.media.mkv;

import net.katsuster.strview.media.*;

/**
 * <p>
 * Matroska 規格由来の定数、
 * 定数名定義用のクラス。
 * </p>
 *
 * <p>
 * 参考規格
 * </p>
 * <ul>
 * <li>Matroska: http://www.matroska.org/technical/specs/index.html</li>
 * </ul>
 *
 * @author katsuhiro
 */
public class MKVConsts {
    //MKV タグのファクトリ
    public static final PacketFactory<MKVTag, MKVHeader, Integer> mkvFactory =
            new PacketFactory<>(MKVTag.class, MKVHeader.class);
    static {
        mkvFactory.put(TAG_ID.BLOCK, MKVHeaderBlock.class);
        mkvFactory.put(TAG_ID.SIMPLE_BLOCK, MKVHeaderSimpleBlock.class);
    }

    //MKV タグのファクトリ（タグが持つデータ型から作成）
    public static final PacketFactory<MKVTag, MKVHeader, Integer> mkvDataFactory =
            new PacketFactory<>(MKVTag.class, MKVHeader.class);
    static {
        mkvDataFactory.put(TAG_TYPE.UINT, MKVHeaderUInt.class);
        mkvDataFactory.put(TAG_TYPE.INT, MKVHeaderSInt.class);
        mkvDataFactory.put(TAG_TYPE.STRING, MKVHeaderString.class);
        mkvDataFactory.put(TAG_TYPE.UTF8, MKVHeaderUTF8.class);
        mkvDataFactory.put(TAG_TYPE.FLOAT, MKVHeaderFloat.class);
    }

    //MKV タグの仕様定義テーブル
    private static final MKVSpec SPECS = new MKVSpec();

    protected MKVConsts() {
        //do nothing
    }

    public static MKVTagSpec getTagSpec(long id) {
        MKVTagSpec spec;

        spec = SPECS.getTagSpec(id);
        if (spec == null) {
            spec = SPECS.getUnknownTagSpec();
        }

        return spec;
    }

    public static class TAG_ID {
        //dummy. this is not satisfied vint constraints...
        public static final int ROOT = 0x00000000;

        //EBML Header
        public static final int EBML                  = 0x1a45dfa3;
        public static final int EBML_VERSION          = 0x4286;
        public static final int EBML_READ_VERSION     = 0x42f7;
        public static final int EBML_MAX_ID_LENGTH    = 0x42f2;
        public static final int EBML_MAX_SIZE_LENGTH  = 0x42f3;
        public static final int DOC_TYPE              = 0x4282;
        public static final int DOC_TYPE_VERSION      = 0x4287;
        public static final int DOC_TYPE_READ_VERSION = 0x4285;

        //Global elements
        public static final int EBML_VOID  = 0xec;
        public static final int EBML_CRC32 = 0xbf;

        //Segment
        public static final int SEGMENT = 0x18538067;

        //Meta Seek Information
        public static final int SEEK_HEAD     = 0x114d9b74;
        public static final int SEEK          = 0x4dbb;
        public static final int SEEK_ID       = 0x53ab;
        public static final int SEEK_POSITION = 0x53ac;

        //Segment Information
        public static final int INFO                          = 0x1549a966;
        public static final int SEGMENT_UID                   = 0x73a4;
        public static final int SEGMENT_FILENAME              = 0x7384;
        public static final int PREV_UID                      = 0x3cb923;
        public static final int PREV_FILENAME                 = 0x3c83ab;
        public static final int NEXT_UID                      = 0x3eb923;
        public static final int NEXT_FILENAME                 = 0x3e83bb;
        public static final int SEGMENT_FAMILY                = 0x4444;
        public static final int CHAPTER_TRANSLATE             = 0x6924;
        public static final int CHAPTER_TRANSLATE_EDITION_UID = 0x69fc;
        public static final int CHAPTER_TRANSLATE_CODEC       = 0x69bf;
        public static final int CHAPTER_TRANSLATE_ID          = 0x69a5;
        public static final int TIMECODE_SCALE                = 0x2ad7b1;
        public static final int DURATION                      = 0x4489;
        public static final int DATE_UTC                      = 0x4461;
        public static final int TITLE                         = 0x7ba9;
        public static final int MUXING_APP                    = 0x4d80;
        public static final int WRITING_APP                   = 0x5741;

        //Cluster
        public static final int CLUSTER             = 0x1f43b675;
        public static final int TIMECODE            = 0xe7;
        public static final int SILENT_TRACKS       = 0x5854;
        public static final int SILENT_TRACK_NUMBER = 0x58d7;
        public static final int POSITION            = 0xa7;
        public static final int PREV_SIZE           = 0xab;
        public static final int SIMPLE_BLOCK        = 0xa3;
        public static final int BLOCK_GROUP         = 0xa0;
        public static final int BLOCK               = 0xa1;
        public static final int BLOCK_VIRTUAL       = 0xa2;
        public static final int BLOCK_ADDITIONS     = 0x75a1;
        public static final int BLOCK_MORE          = 0xa6;
        public static final int BLOCK_ADD_ID        = 0xee;
        public static final int BLOCK_ADDITIONAL    = 0xa5;
        public static final int BLOCK_DURATION      = 0x9b;
        public static final int REFERENCE_PRIORITY  = 0xfa;
        public static final int REFERENCE_BLOCK     = 0xfb;
        public static final int REFERENCE_VIRTUAL   = 0xfd;
        public static final int CODEC_STATE         = 0xa4;
        public static final int SLICES              = 0x8e;
        public static final int TIME_SLICE          = 0xe8;
        public static final int LACE_NUMBER         = 0xcc;
        public static final int FRAME_NUMBER        = 0xcd;
        public static final int BLOCK_ADDITION_ID   = 0xcb;
        public static final int DELAY               = 0xce;
        public static final int CLUSTER_DURATION    = 0xcf;
        public static final int REFERENCE_FRAME     = 0xc8;
        public static final int REFERENCE_OFFSET    = 0xc9;
        public static final int REFERENCE_TIME_CODE = 0xca;
        public static final int ENCRYPTED_BLOCK     = 0xaf;

        //Track
        public static final int TRACKS                      = 0x1654ae6b;
        public static final int TRACK_ENTRY                 = 0xae;
        public static final int TRACK_NUMBER                = 0xd7;
        public static final int TRACK_UID                   = 0x73c5;
        public static final int TRACK_TYPE                  = 0x83;
        public static final int FLAG_ENABLED                = 0xb9;
        public static final int FLAG_DEFAULT                = 0x88;
        public static final int FLAG_FORCED                 = 0x55aa;
        public static final int FLAG_LACING                 = 0x9c;

        public static final int MIN_CACHE                   = 0x6de7;
        public static final int MAX_CACHE                   = 0x6df8;
        public static final int DEFAULT_DURATION            = 0x23e383;
        public static final int TRACK_TIMECODE_SCALE        = 0x23314f;
        public static final int TRACK_OFFSET                = 0x537f;
        public static final int MAX_BLOCK_ADDITION_ID       = 0x55ee;
        public static final int NAME                        = 0x536e;
        public static final int LANGUAGE                    = 0x22b59c;
        public static final int CODEC_ID                    = 0x86;
        public static final int CODEC_PRIVATE               = 0x63a2;
        public static final int CODEC_NAME                  = 0x258688;
        public static final int ATTACHMENT_LINK             = 0x7446;
        public static final int CODEC_SETTINGS              = 0x3a9697;
        public static final int CODEC_INFO_URL              = 0x3b4040;
        public static final int CODEC_DOWNLOAD_URL          = 0x26b240;
        public static final int CODEC_DECODE_ALL            = 0xaa;
        public static final int TRACK_OVERLAY               = 0x6fab;
        public static final int TRACK_TRANSLATE             = 0x6624;
        public static final int TRACK_TRANSLATE_EDITION_UID = 0x66fc;
        public static final int TRACK_TRANSLATE_CODEC       = 0x66bf;
        public static final int TRACK_TRANSLATE_TRACK_ID    = 0x66a5;
        public static final int VIDEO                       = 0xe0;
        public static final int FLAG_INTERLACED             = 0x9a;
        public static final int STEREO_MODE                 = 0x53b8;
        public static final int PIXEL_WIDTH                 = 0xb0;
        public static final int PIXEL_HEIGHT                = 0xba;
        public static final int PIXEL_CROP_BOTTOM           = 0x54aa;
        public static final int PIXEL_CROP_TOP              = 0x54bb;
        public static final int PIXEL_CROP_LEFT             = 0x54cc;
        public static final int PIXEL_CROP_RIGHT            = 0x54dd;
        public static final int DISPLAY_WIDTH               = 0x54b0;
        public static final int DISPLAY_HEIGHT              = 0x54ba;
        public static final int DISPLAY_UNIT                = 0x54b2;
        public static final int ASPECT_RATIO_TYPE           = 0x54b3;
        public static final int COLOUR_SPACE                = 0x2eb524;
        public static final int GAMMA_VALUE                 = 0x2fb523;
        public static final int FRAME_RATE                  = 0x2383e3;

        public static final int AUDIO                       = 0xe1;
        public static final int SAMPLING_FREQUENCY          = 0xb5;
        public static final int OUTPUT_SAMPLING_FREQUENCY   = 0x78b5;
        public static final int CHANNELS                    = 0x9f;
        public static final int CHANNEL_POSITIONS           = 0x7d7b;
        public static final int BIT_DEPTH                   = 0x6264;

        public static final int TRACK_OPERATION             = 0xe2;
        public static final int TRACK_COMBINE_PLANES        = 0xe3;
        public static final int TRACK_PLANE                 = 0xe4;
        public static final int TRACK_PLANE_UID             = 0xe5;
        public static final int TRACK_PLANE_TYPE            = 0xe6;
        public static final int TRACK_JOIN_BLOCKS           = 0xe9;
        public static final int TRACK_JOIN_UID              = 0xed;
        public static final int CONTENT_ENCODINGS           = 0x6d80;
        public static final int CONTENT_ENCODING            = 0x6240;
        public static final int CONTENT_ENCODING_ORDER      = 0x5031;
        public static final int CONTENT_ENCODING_SCOPE      = 0x5032;
        public static final int CONTENT_ENCODING_TYPE       = 0x5033;
        public static final int CONTENT_COMPRESSION         = 0x5034;
        public static final int CONTENT_COMP_ALGO           = 0x4254;
        public static final int CONTENT_COMP_SETTINGS       = 0x4255;
        public static final int CONTENT_ENCRYPTION          = 0x5035;
        public static final int CONTENT_ENC_ALGO            = 0x47e1;
        public static final int CONTENT_ENC_KEY_ID          = 0x47e2;
        public static final int CONTENT_SIGNATURE           = 0x47e3;
        public static final int CONTENT_SIG_KEY_ID          = 0x47e4;
        public static final int CONTENT_SIG_ALGO            = 0x47e5;
        public static final int CONTENT_SIG_HASH_ALGO       = 0x47e6;

        //Cueing Data
        public static final int CUES                 = 0x1c53bb6b;
        public static final int CUE_POINT            = 0xbb;
        public static final int CUE_TIME             = 0xb3;
        public static final int CUE_TRACK_POSITIONS  = 0xb7;
        public static final int CUE_TRACK            = 0xf7;
        public static final int CUE_CLUSTER_POSITION = 0xf1;
        public static final int CUE_BLOCK_NUMBER     = 0x5378;
        public static final int CUE_CODEC_STATE      = 0xea;
        public static final int CUE_REFERENCE        = 0xdb;
        public static final int CUE_REF_TIME         = 0x96;
        public static final int CUE_REF_CLUSTER      = 0x97;
        public static final int CUE_REF_NUMBER       = 0x535f;
        public static final int CUE_REF_CODEC_STATE  = 0xeb;

        //Attachment
        public static final int ATTACHMENTS                 = 0x1941a469;
        public static final int ATTACHED_FILE               = 0x61a7;
        public static final int FILE_DESCRIPTION            = 0x467e;
        public static final int FILE_NAME                   = 0x466e;
        public static final int FILE_MIME_TYPE              = 0x4660;
        public static final int FILE_DATA                   = 0x465c;
        public static final int FILE_UID                    = 0x46ae;
        public static final int FILE_REFERRAL               = 0x4675;
        public static final int FILE_USED_START_TIME        = 0x4661;
        public static final int FILE_USED_END_TIME          = 0x4662;

        public static final int CHAPTERS                    = 0x1043a770;
        public static final int EDITION_ENTRY               = 0x45b9;
        public static final int EDITION_UID                 = 0x45bc;
        public static final int EDITION_FLAG_HIDDEN         = 0x45bd;
        public static final int EDITION_FLAG_DEFAULT        = 0x45db;
        public static final int EDITION_FLAG_ORDERED        = 0x45dd;
        public static final int CHAPTER_ATOM                = 0xb6;
        public static final int CHAPTER_UID                 = 0x73c4;
        public static final int CHAPTER_TIME_START          = 0x91;
        public static final int CHAPTER_TIME_END            = 0x92;
        public static final int CHAPTER_FLAG_HIDDEN         = 0x98;
        public static final int CHAPTER_FLAG_ENABLED        = 0x4598;
        public static final int CHAPTER_SEGMENT_UID         = 0x6e67;
        public static final int CHAPTER_SEGMENT_EDITION_UID = 0x6ebc;
        public static final int CHAPTER_PHYSICAL_EQUIV      = 0x63c3;
        public static final int CHAPTER_TRACK               = 0x8f;
        public static final int CHAPTER_TRACK_NUMBER        = 0x89;
        public static final int CHAPTER_DISPLAY             = 0x80;
        public static final int CHAP_STRING                 = 0x85;
        public static final int CHAP_LANGUAGE               = 0x437c;
        public static final int CHAP_COUNTRY                = 0x437e;
        public static final int CHAP_PROCESS                = 0x6944;
        public static final int CHAP_PROCESS_CODEC_ID       = 0x6955;
        public static final int CHAP_PROCESS_PRIVATE        = 0x450d;
        public static final int CHAP_PROCESS_COMMAND        = 0x6911;
        public static final int CHAP_PROCESS_TIME           = 0x6922;
        public static final int CHAP_PROCESS_DATA           = 0x6933;

        //Tagging
        public static final int TAGS               = 0x1254c367;
        public static final int TAG                = 0x7373;
        public static final int TARGETS            = 0x63c0;
        public static final int TARGET_TYPE_VALUE  = 0x68ca;
        public static final int TARGET_TYPE        = 0x63ca;
        public static final int TAG_TRACK_UID      = 0x63c5;
        public static final int TAG_EDITION_UID    = 0x63c9;
        public static final int TAG_CHAPTER_UID    = 0x63c4;
        public static final int TAG_ATTACHMENT_UID = 0x63c6;
        public static final int SIMPLE_TAG         = 0x67c8;
        public static final int TAG_NAME           = 0x45a3;
        public static final int TAG_LANGUAGE       = 0x447a;
        public static final int TAG_DEFAULT        = 0x4484;
        public static final int TAG_STRING         = 0x4487;
        public static final int TAG_BINARY         = 0x4485;
    }

    public static String getTagTypeName(int id) {
        String name = "..unknown..";

        switch (id) {
        case TAG_TYPE.MASTER:
            //m: Master,
            name = "Master";
            break;
        case TAG_TYPE.UINT:
            //u: unsigned int,
            name = "unsigned int";
            break;
        case TAG_TYPE.INT:
            //i: signed integer,
            name = "signed integer";
            break;
        case TAG_TYPE.STRING:
            //s: string,
            name = "string";
            break;
        case TAG_TYPE.UTF8:
            //8: UTF-8 string,
            name = "UTF-8 string";
            break;
        case TAG_TYPE.BINARY:
            //b: binary,
            name = "binary";
            break;
        case TAG_TYPE.FLOAT:
            //f: float,
            name = "float";
            break;
        case TAG_TYPE.DATE:
            //d: date
            name = "date";
            break;
        default:
            //do nothing
            break;
        }

        return name;
    }

    public static class TAG_TYPE {
        public static final int MASTER = 0x01; //m: Master,
        public static final int UINT   = 0x02; //u: unsigned int,
        public static final int INT    = 0x03; //i: signed integer,
        public static final int STRING = 0x04; //s: string,
        public static final int UTF8   = 0x05; //8: UTF-8 string,
        public static final int BINARY = 0x06; //b: binary,
        public static final int FLOAT  = 0x07; //f: float,
        public static final int DATE   = 0x08; //d: date
    }

    public static String getLacingName(int id) {
        String name = "..unknown..";

        switch (id) {
        case LACING.NO:
            //no lacing
            name = "no lacing";
            break;
        case LACING.XIPH:
            //Xiph lacing
            name = "Xiph lacing";
            break;
        case LACING.EBML:
            //EBML lacing
            name = "EBML lacing";
            break;
        case LACING.FIXED_SIZE:
            //fixed-size lacing
            name = "fixed-size lacing";
            break;
        default:
            //do nothing
            break;
        }

        return name;
    }

    public static class LACING {
        public static final int NO         = 0x00; //no lacing
        public static final int XIPH       = 0x01; //Xiph lacing
        public static final int EBML       = 0x03; //EBML lacing
        public static final int FIXED_SIZE = 0x02; //fixed-size lacing
    }
}
