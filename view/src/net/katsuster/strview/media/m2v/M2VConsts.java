package net.katsuster.strview.media.m2v;

import net.katsuster.strview.media.*;

/**
 * <p>
 * MPEG2 Video ヘッダ。
 * </p>
 *
 * <p>
 * 参考規格
 * </p>
 * <ul>
 * <li>ITU-T H.262, ISO/IEC 13818-2:
 * Information technology - Generic coding of moving pictures and
 * associated audio information: Video</li>
 * </ul>
 */
public class M2VConsts {
    //MPEG2 Video データのファクトリ
    public static final PacketFactory<M2VHeader, Integer> m2vFactory =
            new PacketFactory<>(M2VHeader.class);
    static {
        m2vFactory.put(START_CODE.PICTURE, M2VHeaderPicture.class);
        m2vFactory.put(START_CODE.SEQUENCE_HEADER, M2VHeaderSequence.class);
        m2vFactory.put(START_CODE.EXTENSION, M2VHeaderExt.class);
        m2vFactory.put(START_CODE.GROUP, M2VHeaderGOP.class);
    }

    public static final PacketFactory<M2VHeaderExt, Integer> m2vExtFactory =
            new PacketFactory<>(M2VHeaderExt.class);
    static {
        m2vExtFactory.put(EXTENSION_START_CODE.SEQUENCE, M2VHeaderExtSequence.class);
        m2vExtFactory.put(EXTENSION_START_CODE.SEQUENCE_DISPLAY, M2VHeaderExtSequenceDisplay.class);
        m2vExtFactory.put(EXTENSION_START_CODE.QUANT_MATRIX, M2VHeaderExtQuantMatrix.class);
        m2vExtFactory.put(EXTENSION_START_CODE.SEQUENCE_SCALABLE, M2VHeaderExtSequenceScalable.class);
        m2vExtFactory.put(EXTENSION_START_CODE.PICTURE_CODING, M2VHeaderExtPictureCoding.class);
    }

    public static String getStartCodeName(int id) {
        String name = "..unknown..";

        switch (id) {
        case START_CODE.PICTURE:
            //[ISO 13818-2] picture_start_code
            name = "picture_start_code";
            break;
        case START_CODE.RES1:
            //[ISO 13818-2] reserved1
            name = "ISO 13818-2 reserved1";
            break;
        case START_CODE.RES2:
            //[ISO 13818-2] reserved2
            name = "ISO 13818-2 reserved2";
            break;
        case START_CODE.USER_DATA:
            //[ISO 13818-2] user_data_start_code
            name = "user_data_start_code";
            break;
        case START_CODE.SEQUENCE_HEADER:
            //[ISO 13818-2] sequence_header_code
            name = "sequence_header_code";
            break;
        case START_CODE.SEQUENCE_ERROR:
            //[ISO 13818-2] sequence_error_code
            name = "sequence_error_code";
            break;
        case START_CODE.EXTENSION:
            //[ISO 13818-2] extension_start_code
            name = "extension_start_code";
            break;
        case START_CODE.RES3:
            //[ISO 13818-2] reserved3
            name = "ISO 13818-2 reserved3";
            break;
        case START_CODE.SEQUENCE_END:
            //[ISO 13818-2] sequence_end_code
            name = "sequence_end_code";
            break;
        case START_CODE.GROUP:
            //[ISO 13818-2] group_start_code
            name = "group_start_code";
            break;
        default:
            //do nothing
            break;
        }

        if (START_CODE.SLICE_START <= id &&
                id <= START_CODE.SLICE_END) {
            //[ISO 13818-2] slice_start_code
            name = "slice_start_code";
        } else if (START_CODE.SYSTEM_START <= id &&
                id <= START_CODE.SYSTEM_END) {
            //[ISO 13818-2] system_start_code
            name = "system_start_code";
        }

        return name;
    }

    /**
     * Table 6-1: Start code values
     */
    public static class START_CODE {
        //[ISO 13818-2] picture_start_code
        public static final int PICTURE = 0x00000100;

        //[ISO 13818-2] slice_start_code
        public static final int SLICE_START = 0x00000101;
        //[ISO 13818-2] slice_start_code
        public static final int SLICE_END = 0x000001af;

        //[ISO 13818-2] reserved1
        public static final int RES1 = 0x000001b0;
        //[ISO 13818-2] reserved2
        public static final int RES2 = 0x000001b1;
        //[ISO 13818-2] user_data_start_code
        public static final int USER_DATA = 0x000001b2;
        //[ISO 13818-2] sequence_header_code
        public static final int SEQUENCE_HEADER = 0x000001b3;
        //[ISO 13818-2] sequence_error_code
        public static final int SEQUENCE_ERROR = 0x000001b4;
        //[ISO 13818-2] extension_start_code
        public static final int EXTENSION = 0x000001b5;
        //[ISO 13818-2] reserved3
        public static final int RES3 = 0x000001b6;
        //[ISO 13818-2] sequence_end_code
        public static final int SEQUENCE_END = 0x000001b7;
        //[ISO 13818-2] group_start_code
        public static final int GROUP = 0x000001b8;

        //[ISO 13818-2] system_start_code
        public static final int SYSTEM_START = 0x000001b9;
        //[ISO 13818-2] system_start_code
        public static final int SYSTEM_END = 0x000001ff;
    }

    public static String getExtensionStartCodeName(int id) {
        String name = "..unknown..";

        switch (id) {
        case EXTENSION_START_CODE.RESERVED1:
            //[ISO 13818-2] reserved1
            name = "reserved1";
            break;
        case EXTENSION_START_CODE.SEQUENCE:
            //[ISO 13818-2] Sequence Extension ID
            name = "Sequence Extension ID";
            break;
        case EXTENSION_START_CODE.SEQUENCE_DISPLAY:
            //[ISO 13818-2] Sequence Display Extension ID
            name = "Sequence Display Extension ID";
            break;
        case EXTENSION_START_CODE.QUANT_MATRIX:
            //[ISO 13818-2] Quant Matrix Extension ID
            name = "Quant Matrix Extension ID";
            break;
        case EXTENSION_START_CODE.COPYRIGHT:
            //[ISO 13818-2] Copyright Extension ID
            name = "Copyright Extension ID";
            break;
        case EXTENSION_START_CODE.SEQUENCE_SCALABLE:
            //[ISO 13818-2] Sequence Scalable Extension ID
            name = "Sequence Scalable Extension ID";
            break;
        case EXTENSION_START_CODE.RESERVED2:
            //[ISO 13818-2] reserved2
            name = "reserved2";
            break;
        case EXTENSION_START_CODE.PICTURE_DISPLAY:
            //[ISO 13818-2] Picture Display Extension ID
            name = "Picture Display Extension ID";
            break;
        case EXTENSION_START_CODE.PICTURE_CODING:
            //[ISO 13818-2] Picture Coding Extension ID
            name = "Picture Coding Extension ID";
            break;
        case EXTENSION_START_CODE.PICTURE_SPATIAL_SCALABLE:
            //[ISO 13818-2] Picture Spatial Scalable Extension ID
            name = "Picture Spatial Scalable Extension ID";
            break;
        case EXTENSION_START_CODE.PICTURE_TEMPORAL_SCALABLE:
            //[ISO 13818-2] Picture Temporal Scalable Extension ID
            name = "Picture Temporal Scalable Extension ID";
            break;
        default:
            //do nothing
            break;
        }

        if (EXTENSION_START_CODE.RESERVED_START <= id &&
                id <= EXTENSION_START_CODE.RESERVED_END) {
            //[ISO 13818-2] reserved
            name = "reserved";
        }

        return name;
    }

    /**
     * Table 6-2: extension_start_code_identifier codes
     */
    public static class EXTENSION_START_CODE {
        //[ISO 13818-2] reserved1
        public static final int RESERVED1 = 0x00;
        //[ISO 13818-2] Sequence Extension ID
        public static final int SEQUENCE = 0x01;
        //[ISO 13818-2] Sequence Display Extension ID
        public static final int SEQUENCE_DISPLAY = 0x02;
        //[ISO 13818-2] Quant Matrix Extension ID
        public static final int QUANT_MATRIX = 0x03;
        //[ISO 13818-2] Copyright Extension ID
        public static final int COPYRIGHT = 0x04;
        //[ISO 13818-2] Sequence Scalable Extension ID
        public static final int SEQUENCE_SCALABLE = 0x05;

        //[ISO 13818-2] reserved2
        public static final int RESERVED2 = 0x06;

        //[ISO 13818-2] Picture Display Extension ID
        public static final int PICTURE_DISPLAY = 0x07;
        //[ISO 13818-2] Picture Coding Extension ID
        public static final int PICTURE_CODING = 0x08;
        //[ISO 13818-2] Picture Spatial Scalable Extension ID
        public static final int PICTURE_SPATIAL_SCALABLE = 0x09;
        //[ISO 13818-2] Picture Temporal Scalable Extension ID
        public static final int PICTURE_TEMPORAL_SCALABLE = 0x0a;

        //[ISO 13818-2] reserved
        public static final int RESERVED_START = 0x0b;
        //[ISO 13818-2] reserved
        public static final int RESERVED_END = 0x0f;
    }

    public static String getAspectRatioName(int id) {
        String name = "..unknown..";

        switch (id) {
        case ASPECT_RATIO.FORBIDDEN:
            //[ISO 13818-2] forbidden
            name = "forbidden";
            break;
        case ASPECT_RATIO.SAR_SQUARE_SAMPLE:
            //[ISO 13818-2] SAR: 1/1 (square sample), DAR: none
            name = "SAR: 1/1(square sample)";
            break;
        case ASPECT_RATIO.DAR_3_4:
            //[ISO 13818-2] SAR: none, DAR: 3/4
            name = "DAR: 3/4";
            break;
        case ASPECT_RATIO.DAR_9_16:
            //[ISO 13818-2] SAR: none, DAR: 9/16
            name = "DAR: 9/16";
            break;
        case ASPECT_RATIO.DAR_1_221:
            //[ISO 11172-2] SAR: none, DAR: 1/2.21
            name = "DAR: 1/2.21";
            break;
        default:
            //do nothing
            break;
        }

        if (ASPECT_RATIO.RESERVED1_START <= id &&
                id <= ASPECT_RATIO.RESERVED1_END) {
            //[ISO 13818-2] reserved
            name = "reserved";
        }

        return name;
    }

    /**
     * Table 6-3: aspect_ratio_information
     */
    public static class ASPECT_RATIO {
        //[ISO 13818-2] forbidden
        public static final int FORBIDDEN         = 0x0; //0b0000
        //[ISO 13818-2] SAR: 1/1 (square sample), DAR: none
        public static final int SAR_SQUARE_SAMPLE = 0x1; //0b0001
        //[ISO 13818-2] SAR: none, DAR: 3/4
        public static final int DAR_3_4           = 0x2; //0b0010
        //[ISO 13818-2] SAR: none, DAR: 9/16
        public static final int DAR_9_16          = 0x3; //0b0011
        //[ISO 13818-2] SAR: none, DAR: 1/2.21
        public static final int DAR_1_221         = 0x4; //0b0100
        //[ISO 13818-2] reserved
        public static final int RESERVED1_START = 0x5; //0b0101
        public static final int RESERVED1_END   = 0xf; //0b1111
    }

    public static String getFrameRateValueName(int id) {
        String name = "..unknown..";

        switch (id) {
        case FRAME_RATE_VALUE.FORBIDDEN:
            //[ISO 13818-2] forbidden
            name = "forbidden";
            break;
        case FRAME_RATE_VALUE.RATE_23_976:
            name = "23.976(24/1001)";
            break;
        case FRAME_RATE_VALUE.RATE_24:
            name = "24";
            break;
        case FRAME_RATE_VALUE.RATE_25:
            name = "25";
            break;
        case FRAME_RATE_VALUE.RATE_29_97:
            name = "29.97(30/1001)";
            break;
        case FRAME_RATE_VALUE.RATE_30:
            name = "30";
            break;
        case FRAME_RATE_VALUE.RATE_50:
            name = "50";
            break;
        case FRAME_RATE_VALUE.RATE_59_94:
            name = "59_94(60/1001)";
            break;
        case FRAME_RATE_VALUE.RATE_60:
            name = "60";
            break;
        default:
            //do nothing
            break;
        }

        if (FRAME_RATE_VALUE.RESERVED_START <= id &&
                id <= FRAME_RATE_VALUE.RESERVED_END) {
            //[ISO 13818-2] reserved
            name = "reserved";
        }

        return name;
    }

    public static float getFrameRateValueValue(int id) {
        float[] v = {
                0, 24f/1001, 24, 25,
                30f/1001, 30, 50, 60/1001,
                60
        };

        if (FRAME_RATE_VALUE.RESERVED_START <= id &&
                id <= FRAME_RATE_VALUE.RESERVED_END) {
            //[ISO 13818-2] reserved
            return 0;
        }

        return v[id];
    }

    /**
     * Table 6-4: frame_rate_value
     */
    public static class FRAME_RATE_VALUE {
        //[ISO 13818-2]
        public static final int FORBIDDEN   = 0x0; //0b0000
        public static final int RATE_23_976 = 0x1; //0b0001
        public static final int RATE_24     = 0x2; //0b0010
        public static final int RATE_25     = 0x3; //0b0011
        public static final int RATE_29_97  = 0x4; //0b0100
        public static final int RATE_30     = 0x5; //0b0101
        public static final int RATE_50     = 0x6; //0b0110
        public static final int RATE_59_94  = 0x7; //0b0111
        public static final int RATE_60     = 0x8; //0b1000

        //[ISO 13818-2] reserved
        public static final int RESERVED_START = 0x9; //0b1001
        public static final int RESERVED_END   = 0xf; //0b1111
    }

    public static String getChromaFormatName(int id) {
        String name = "..unknown..";

        switch (id) {
        case CHROMA_FORMAT.RESERVED:
            //[ISO 13818-2] reserved
            name = "reserved";
            break;
        case CHROMA_FORMAT.CHROMA_4_2_0:
            //[ISO 13818-2] 4:2:0
            name = "4:2:0";
            break;
        case CHROMA_FORMAT.CHROMA_4_2_2:
            //[ISO 13818-2] 4:2:2
            name = "4:2:2";
            break;
        case CHROMA_FORMAT.CHROMA_4_4_4:
            //[ISO 13818-2] 4:4:4
            name = "4:4:4";
            break;
        default:
            //do nothing
            break;
        }

        return name;
    }

    /**
     * Table 6-5: Meaning of chroma_format
     */
    public static class CHROMA_FORMAT {
        //[ISO 13818-2] reserved
        public static final int RESERVED = 0x0;
        //[ISO 13818-2] 4:2:0
        public static final int CHROMA_4_2_0 = 0x1;
        //[ISO 13818-2] 4:2:2
        public static final int CHROMA_4_2_2 = 0x2;
        //[ISO 13818-2] 4:4:4
        public static final int CHROMA_4_4_4 = 0x3;
    }

    public static String getVideoFormatName(int id) {
        String name = "..unknown..";

        switch (id) {
        case VIDEO_FORMAT.COMPONENT:
            //[ISO 13818-2] component
            name = "component";
            break;
        case VIDEO_FORMAT.PAL:
            //[ISO 13818-2] PAL
            name = "PAL";
            break;
        case VIDEO_FORMAT.NTSC:
            //[ISO 13818-2] NTSC
            name = "NTSC";
            break;
        case VIDEO_FORMAT.SECAM:
            //[ISO 13818-2] SECAM
            name = "SECAM";
            break;
        case VIDEO_FORMAT.MAC:
            //[ISO 13818-2] MAC
            name = "MAC";
            break;
        case VIDEO_FORMAT.UNSPECIFIED:
            //[ISO 13818-2] Unspecified video format
            name = "Unspecified video format";
            break;
        case VIDEO_FORMAT.RESERVED1:
            //[ISO 13818-2] reserved
            name = "reserved1";
            break;
        case VIDEO_FORMAT.RESERVED2:
            //[ISO 13818-2] reserved
            name = "reserved2";
            break;
        default:
            //do nothing
            break;
        }

        return name;
    }

    /**
     * Table 6-6: Meaning of video_format
     */
    public static class VIDEO_FORMAT {
        //[ISO 13818-2] component
        public static final int COMPONENT = 0x0; //0b000
        //[ISO 13818-2] PAL
        public static final int PAL = 0x1; //0b001
        //[ISO 13818-2] NTSC
        public static final int NTSC = 0x2; //0b010
        //[ISO 13818-2] SECAM
        public static final int SECAM = 0x3; //0b011
        //[ISO 13818-2] MAC
        public static final int MAC = 0x4; //0b100
        //[ISO 13818-2] Unspecified video format
        public static final int UNSPECIFIED = 0x5; //0b101
        //[ISO 13818-2] reserved
        public static final int RESERVED1 = 0x6; //0b110
        //[ISO 13818-2] reserved
        public static final int RESERVED2 = 0x7; //0b111
    }

    public static String getColourPrimariesName(int id) {
        String name = "..unknown..";

        switch (id) {
        case COLOUR_PRIMARIES.FORBIDDEN:
            //[ISO 13818-2] forbidden
            name = "forbidden";
            break;
        case COLOUR_PRIMARIES.BT_709:
            //[ISO 13818-2] ITU-R BT.709
            name = "ITU-R BT.709";
            break;
        case COLOUR_PRIMARIES.UNSPECIFIED:
            //[ISO 13818-2] unspecified video
            name = "unspecified video";
            break;
        case COLOUR_PRIMARIES.RESERVED1:
            //[ISO 13818-2] reserved
            name = "reserved";
            break;
        case COLOUR_PRIMARIES.BT_470_2_M:
            //[ISO 13818-2] ITU-R BT.470-2 Syste M
            name = "ITU-R BT.470-2 Syste M";
            break;
        case COLOUR_PRIMARIES.BT_470_2_BG:
            //[ISO 13818-2] ITU-R BT.470-2 Syste B, G
            name = "ITU-R BT.470-2 Syste B, G";
            break;
        case COLOUR_PRIMARIES.SMPTE_170M:
            //[ISO 13818-2] SMPTE 170M
            name = "SMPTE 170M";
            break;
        case COLOUR_PRIMARIES.SMPTE_240M:
            //[ISO 13818-2] SMPTE 240M (1987)
            name = "SMPTE 240M (1987)";
            break;
        default:
            //do nothing
            break;
        }

        if (COLOUR_PRIMARIES.RESERVED2_START <= id &&
                id <= COLOUR_PRIMARIES.RESERVED2_END) {
            //[ISO 13818-2] reserved
            name = "reserved";
        }

        return name;
    }

    /**
     * Table 6-7: Colour Primaries
     */
    public static class COLOUR_PRIMARIES {
        //[ISO 13818-2] forbidden
        public static final int FORBIDDEN = 0x00;
        //[ISO 13818-2] ITU-R BT.709
        public static final int BT_709 = 0x01;
        //[ISO 13818-2] unspecified video
        public static final int UNSPECIFIED = 0x02;
        //[ISO 13818-2] reserved
        public static final int RESERVED1 = 0x03;
        //[ISO 13818-2] ITU-R BT.470-2 Syste M
        public static final int BT_470_2_M = 0x04;
        //[ISO 13818-2] ITU-R BT.470-2 Syste B, G
        public static final int BT_470_2_BG = 0x05;
        //[ISO 13818-2] SMPTE 170M
        public static final int SMPTE_170M = 0x06;
        //[ISO 13818-2] SMPTE 240M (1987)
        public static final int SMPTE_240M = 0x07;
        //[ISO 13818-2] reserved
        public static final int RESERVED2_START = 0x08;
        public static final int RESERVED2_END = 0xff;
    };

    public static String getTransferCharacteristicsName(int id) {
        String name = "..unknown..";

        switch (id) {
        case TRANSFER_CHARACTERISTICS.FORBIDDEN:
            //[ISO 13818-2] forbidden
            name = "forbidden";
            break;
        case TRANSFER_CHARACTERISTICS.BT_709:
            //[ISO 13818-2] ITU-R BT.709
            name = "ITU-R BT.709";
            break;
        case TRANSFER_CHARACTERISTICS.UNSPECIFIED:
            //[ISO 13818-2] unspecified video
            name = "unspecified video";
            break;
        case TRANSFER_CHARACTERISTICS.RESERVED1:
            //[ISO 13818-2] reserved
            name = "reserved";
            break;
        case TRANSFER_CHARACTERISTICS.BT_470_2_M:
            //[ISO 13818-2] ITU-R BT.470-2 Syste M
            name = "ITU-R BT.470-2 Syste M";
            break;
        case TRANSFER_CHARACTERISTICS.BT_470_2_BG:
            //[ISO 13818-2] ITU-R BT.470-2 Syste B, G
            name = "ITU-R BT.470-2 Syste B, G";
            break;
        case TRANSFER_CHARACTERISTICS.SMPTE_170M:
            //[ISO 13818-2] SMPTE 170M
            name = "SMPTE 170M";
            break;
        case TRANSFER_CHARACTERISTICS.SMPTE_240M:
            //[ISO 13818-2] SMPTE 240M (1987)
            name = "SMPTE 240M (1987)";
            break;
        case TRANSFER_CHARACTERISTICS.LINEAR:
            //[ISO 13818-2] linear transfer characteristics
            name = "linear transfer characteristics";
            break;
        default:
            //do nothing
            break;
        }

        if (TRANSFER_CHARACTERISTICS.RESERVED2_START <= id &&
                id <= TRANSFER_CHARACTERISTICS.RESERVED2_END) {
            //[ISO 13818-2] reserved
            name = "reserved";
        }

        return name;
    }

    /**
     * Table 6-8: Transfer Characteristics
     */
    public static class TRANSFER_CHARACTERISTICS {
        //[ISO 13818-2] forbidden
        public static final int FORBIDDEN = 0x00;
        //[ISO 13818-2] ITU-R BT.709
        public static final int BT_709 = 0x01;
        //[ISO 13818-2] unspecified video
        public static final int UNSPECIFIED = 0x02;
        //[ISO 13818-2] reserved
        public static final int RESERVED1 = 0x03;
        //[ISO 13818-2] ITU-R BT.470-2 Syste M
        public static final int BT_470_2_M = 0x04;
        //[ISO 13818-2] ITU-R BT.470-2 Syste B, G
        public static final int BT_470_2_BG = 0x05;
        //[ISO 13818-2] SMPTE 170M
        public static final int SMPTE_170M = 0x06;
        //[ISO 13818-2] SMPTE 240M (1987)
        public static final int SMPTE_240M = 0x07;
        //[ISO 13818-2] linear transfer characteristics
        public static final int LINEAR = 0x08;
        //[ISO 13818-2] reserved
        public static final int RESERVED2_START = 0x09;
        public static final int RESERVED2_END = 0xff;
    };

    public static String getMatrixCoefficientsName(int id) {
        String name = "..unknown..";

        switch (id) {
        case MATRIX_COEFFICIENTS.FORBIDDEN:
            //[ISO 13818-2] forbidden
            name = "forbidden";
            break;
        case MATRIX_COEFFICIENTS.BT_709:
            //[ISO 13818-2] ITU-R BT.709
            name = "ITU-R BT.709";
            break;
        case MATRIX_COEFFICIENTS.UNSPECIFIED:
            //[ISO 13818-2] unspecified video
            name = "unspecified video";
            break;
        case MATRIX_COEFFICIENTS.RESERVED1:
            //[ISO 13818-2] reserved
            name = "reserved";
            break;
        case MATRIX_COEFFICIENTS.FCC:
            //[ISO 13818-2] FCC
            name = "FCC";
            break;
        case MATRIX_COEFFICIENTS.BT_470_2_BG:
            //[ISO 13818-2] ITU-R BT.470-2 Syste B, G
            name = "ITU-R BT.470-2 Syste B, G";
            break;
        case MATRIX_COEFFICIENTS.SMPTE_170M:
            //[ISO 13818-2] SMPTE 170M
            name = "SMPTE 170M";
            break;
        case MATRIX_COEFFICIENTS.SMPTE_240M:
            //[ISO 13818-2] SMPTE 240M (1987)
            name = "SMPTE 240M (1987)";
            break;
        default:
            //do nothing
            break;
        }

        if (MATRIX_COEFFICIENTS.RESERVED2_START <= id &&
                id <= MATRIX_COEFFICIENTS.RESERVED2_END) {
            //[ISO 13818-2] reserved
            name = "reserved";
        }

        return name;
    }

    /**
     * Table 6-9: Matrix Coefficients
     */
    public static class MATRIX_COEFFICIENTS {
        //[ISO 13818-2] forbidden
        public static final int FORBIDDEN = 0x00;
        //[ISO 13818-2] ITU-R BT.709
        public static final int BT_709 = 0x01;
        //[ISO 13818-2] unspecified video
        public static final int UNSPECIFIED = 0x02;
        //[ISO 13818-2] reserved
        public static final int RESERVED1 = 0x03;
        //[ISO 13818-2] FCC
        public static final int FCC = 0x04;
        //[ISO 13818-2] ITU-R BT.470-2 Syste B, G
        public static final int BT_470_2_BG = 0x05;
        //[ISO 13818-2] SMPTE 170M
        public static final int SMPTE_170M = 0x06;
        //[ISO 13818-2] SMPTE 240M (1987)
        public static final int SMPTE_240M = 0x07;
        //[ISO 13818-2] reserved
        public static final int RESERVED2_START = 0x08;
        public static final int RESERVED2_END = 0xff;
    };

    public static String getScalableModeName(int id) {
        String name = "..unknown..";

        switch (id) {
        case SCALABLE_MODE.DATA_PARTITIONING:
            //[ISO 13818-2] data partitioning
            name = "data partitioning";
            break;
        case SCALABLE_MODE.SPATIAL:
            //[ISO 13818-2] spatial scalability
            name = "spatial scalability";
            break;
        case SCALABLE_MODE.SNR:
            //[ISO 13818-2] SNR scalability
            name = "SNR scalability";
            break;
        case SCALABLE_MODE.TEMPORAL:
            //[ISO 13818-2] temporal scalability
            name = "temporal scalability";
            break;
        default:
            //do nothing
            break;
        }

        return name;
    }

    /**
     * Table 6-10: Definition of scalable_mode
     */
    public static class SCALABLE_MODE {
        //[ISO 13818-2] data partitioning
        public static final int DATA_PARTITIONING = 0x0; //0b00
        //[ISO 13818-2] spatial scalability
        public static final int SPATIAL = 0x1; //0b01
        //[ISO 13818-2] SNR scalability
        public static final int SNR = 0x2; //0b10
        //[ISO 13818-2] temporal scalability
        public static final int TEMPORAL = 0x3; //0b11
    }

    public static String getPictureCodingTypeName(int id) {
        String name = "..unknown..";

        switch (id) {
        case PICTURE_CODING_TYPE.FORBIDDEN:
            //[ISO 13818-2] forbidden
            name = "forbidden";
            break;
        case PICTURE_CODING_TYPE.I:
            //[ISO 13818-2] intra(I)
            name = "intra(I)";
            break;
        case PICTURE_CODING_TYPE.P:
            //[ISO 13818-2] predictive(P)
            name = "predictive(P)";
            break;
        case PICTURE_CODING_TYPE.B:
            //[ISO 13818-2] bidirectionally(B)
            name = "bidirectionally(B)";
            break;
        case PICTURE_CODING_TYPE.RESERVED:
            //[ISO 11172-2] dc intra(D)
            name = "dc intra(D)";
            break;
        case PICTURE_CODING_TYPE.RESERVED1:
            //[ISO 13818-2] reserved1
            name = "reserved1";
            break;
        case PICTURE_CODING_TYPE.RESERVED2:
            //[ISO 13818-2] reserved2
            name = "reserved2";
            break;
        case PICTURE_CODING_TYPE.RESERVED3:
            //[ISO 13818-2] reserved3
            name = "reserved3";
            break;
        default:
            //do nothing
            break;
        }

        return name;
    }

    /**
     * Table 6-12: picture_coding_type
     */
    public static class PICTURE_CODING_TYPE {
        //[ISO 13818-2] forbidden
        public static final int FORBIDDEN = 0x0;
        //[ISO 13818-2] intra(I)
        public static final int I = 0x1;
        //[ISO 13818-2] predictive(P)
        public static final int P = 0x2;
        //[ISO 13818-2] bidirectionally(B)
        public static final int B = 0x3;
        //[ISO 11172-2] dc intra(D)
        public static final int RESERVED = 0x4;
        //[ISO 13818-2] reserved1
        public static final int RESERVED1 = 0x5;
        //[ISO 13818-2] reserved2
        public static final int RESERVED2 = 0x6;
        //[ISO 13818-2] reserved3
        public static final int RESERVED3 = 0x7;
    }

    public static String getIntraDCPrecisionName(int id) {
        String name = "..unknown..";

        switch (id) {
        case INTRA_DC_PRECISION.PREC_8:
            //[ISO 13818-2] 8bits
            name = "8bits";
            break;
        case INTRA_DC_PRECISION.PREC_9:
            //[ISO 13818-2] 9bits
            name = "9bits";
            break;
        case INTRA_DC_PRECISION.PREC_10:
            //[ISO 13818-2] 10bits
            name = "10bits";
            break;
        case INTRA_DC_PRECISION.PREC_11:
            //[ISO 13818-2] 11bits
            name = "11bits";
            break;
        default:
            //do nothing
            break;
        }

        return name;
    }

    /**
     * Table 6-13: Intra DC precision
     */
    public static class INTRA_DC_PRECISION {
        //[ISO 13818-2] 8bits
        public static final int PREC_8 = 0x00; //0b00
        //[ISO 13818-2] 9bits
        public static final int PREC_9 = 0x01; //0b01
        //[ISO 13818-2] 10bits
        public static final int PREC_10 = 0x02; //0b10
        //[ISO 13818-2] 11bits
        public static final int PREC_11 = 0x03; //0b11
    }

    public static String getPictureStructureName(int id) {
        String name = "..unknown..";

        switch (id) {
        case PICTURE_STRUCTURE.RESERVED:
            //[ISO 13818-2] reserved
            name = "reserved";
            break;
        case PICTURE_STRUCTURE.TOP:
            //[ISO 13818-2] Top Field
            name = "top";
            break;
        case PICTURE_STRUCTURE.BOTTOM:
            //[ISO 13818-2] Bottom Field
            name = "bottom";
            break;
        case PICTURE_STRUCTURE.FRAME:
            //[ISO 13818-2] Frame picture
            name = "frame";
            break;
        default:
            //do nothing
            break;
        }

        return name;
    }

    /**
     * Table 6-14: Meaning of picture_structure
     */
    public static class PICTURE_STRUCTURE {
        //[ISO 13818-2] reserved
        public static final int RESERVED = 0x00; //0b00
        //[ISO 13818-2] Top Field
        public static final int TOP = 0x01; //0b01
        //[ISO 13818-2] Bottom Field
        public static final int BOTTOM = 0x02; //0b10
        //[ISO 13818-2] Frame picture
        public static final int FRAME = 0x03; //0b11
    }

    public static String getFrameMotionTypeName(int id) {
        String name = "..unknown..";

        switch (id) {
        case FRAME_MOTION_TYPE.RESERVED:
            //[ISO 13818-2] reserved
            name = "reserved";
            break;
        case FRAME_MOTION_TYPE.FIELD:
            //[ISO 13818-2] Field-based prediction
            name = "Field-based prediction";
            break;
        case FRAME_MOTION_TYPE.FRAME:
            //[ISO 13818-2] Frame-based prediction
            name = "Frame-based prediction";
            break;
        case FRAME_MOTION_TYPE.DUAL:
            //[ISO 13818-2] Dual-prime
            name = "Dual-prime";
            break;
        default:
            //do nothing
            break;
        }

        return name;
    }

    /**
     * Table 6-17: Meaning of frame_motion_type
     */
    public static class FRAME_MOTION_TYPE {
        //[ISO 13818-2] reserved
        public static final int RESERVED = 0x0;
        //[ISO 13818-2] Field-based prediction
        public static final int FIELD = 0x1; //0b01
        //[ISO 13818-2] Frame-based prediction
        public static final int FRAME = 0x2; //0b10
        //[ISO 13818-2] Dual-prime
        public static final int DUAL = 0x3; //0b11
    }

    public static String getFieldMotionTypeName(int id) {
        String name = "..unknown..";

        switch (id) {
        case FIELD_MOTION_TYPE.RESERVED:
            //[ISO 13818-2] reserved
            name = "reserved";
            break;
        case FIELD_MOTION_TYPE.FIELD:
            //[ISO 13818-2] Field-based prediction
            name = "Field-based prediction";
            break;
        case FIELD_MOTION_TYPE.MC16_8:
            //[ISO 13818-2] Frame-based prediction
            name = "16x8 MC";
            break;
        case FIELD_MOTION_TYPE.DUAL:
            //[ISO 13818-2] Dual-prime
            name = "Dual-prime";
            break;
        default:
            //do nothing
            break;
        }

        return name;
    }

    /**
     * Table 6-18: Meaning of field_motion_type
     */
    public static class FIELD_MOTION_TYPE {
        //[ISO 13818-2] reserved
        public static final int RESERVED = 0x0;
        //[ISO 13818-2] Field-based prediction
        public static final int FIELD = 0x1; //0b01
        //[ISO 13818-2] 16x8 MC
        public static final int MC16_8 = 0x2; //0b10
        //[ISO 13818-2] Dual-prime
        public static final int DUAL = 0x3; //0b11
    }

    public static String getProfileName(int id) {
        String name = "..unknown..";

        switch (id) {
        case PROFILE.RESERVED1:
            //[ISO 13818-2] reserved
            name = "reserved1";
            break;
        case PROFILE.HIGH:
            //[ISO 13818-2] High
            name = "High";
            break;
        case PROFILE.SPATIALLY:
            //[ISO 13818-2] Spatially Scalable
            name = "Spatially Scalable";
            break;
        case PROFILE.SNR:
            //[ISO 13818-2] SNR Scalable
            name = "SNR Scalable";
            break;
        case PROFILE.MAIN:
            //[ISO 13818-2] Main
            name = "Main";
            break;
        case PROFILE.SIMPLE:
            //[ISO 13818-2] Simple
            name = "Simple";
            break;
        case PROFILE.RESERVED2:
            //[ISO 13818-2] reserved
            name = "reserved2";
            break;
        case PROFILE.RESERVED3:
            //[ISO 13818-2] reserved
            name = "reserved3";
            break;
        default:
            //do nothing
            break;
        }

        return name;
    }

    /**
     * Table 8-2: Profile identification
     */
    public static class PROFILE {
        //[ISO 13818-2] reserved
        public static final int RESERVED1 = 0x0;
        //[ISO 13818-2] High
        public static final int HIGH = 0x1;
        //[ISO 13818-2] Spatially Scalable
        public static final int SPATIALLY = 0x2;
        //[ISO 13818-2] SNR Scalable
        public static final int SNR = 0x3;
        //[ISO 13818-2] Main
        public static final int MAIN = 0x4;
        //[ISO 13818-2] Simple
        public static final int SIMPLE = 0x5;
        //[ISO 13818-2] reserved
        public static final int RESERVED2 = 0x6;
        //[ISO 13818-2] reserved
        public static final int RESERVED3 = 0x7;
    }

    public static String getLevelName(int id) {
        String name = "..unknown..";

        switch (id) {
        case LEVEL.HIGH:
            //[ISO 13818-2] High
            name = "High";
            break;
        case LEVEL.RESERVED2:
            //[ISO 13818-2] reserved
            name = "reserved2";
            break;
        case LEVEL.HIGH1440:
            //[ISO 13818-2] High 1440
            name = "High 1440";
            break;
        case LEVEL.RESERVED3:
            //[ISO 13818-2] reserved
            name = "reserved3";
            break;
        case LEVEL.MAIN:
            //[ISO 13818-2] Main
            name = "Main";
            break;
        case LEVEL.RESERVED4:
            //[ISO 13818-2] reserved
            name = "reserved4";
            break;
        case LEVEL.LOW:
            //[ISO 13818-2] Low
            name = "Low";
            break;
        default:
            //do nothing
            break;
        }

        if (LEVEL.RESERVED1_START <= id &&
                id <= LEVEL.RESERVED1_END) {
            //[ISO 13818-2] reserved
            name = "reserved";
        } else if (LEVEL.RESERVED5_START <= id &&
                id <= LEVEL.RESERVED5_END) {
            //[ISO 13818-2] reserved
            name = "reserved";
        }

        return name;
    }

    /**
     * Table 8-3: Level identification
     */
    public static class LEVEL {
        //[ISO 13818-2] reserved
        public static final int RESERVED1_START = 0x0; //0b0000
        public static final int RESERVED1_END = 0x3; //0b0011
        //[ISO 13818-2] High
        public static final int HIGH = 0x4;
        //[ISO 13818-2] reserved
        public static final int RESERVED2 = 0x5; //0b0101
        //[ISO 13818-2] High 1440
        public static final int HIGH1440 = 0x6;
        //[ISO 13818-2] reserved
        public static final int RESERVED3 = 0x7; //0b0111
        //[ISO 13818-2] Main
        public static final int MAIN = 0x8;
        //[ISO 13818-2] reserved
        public static final int RESERVED4 = 0x9; //0b1010
        //[ISO 13818-2] Low
        public static final int LOW = 0xa;
        //[ISO 13818-2] reserved
        public static final int RESERVED5_START = 0xb; //0b1011
        public static final int RESERVED5_END = 0xf; //0b1111
    }
}
