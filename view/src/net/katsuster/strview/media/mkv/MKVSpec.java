package net.katsuster.strview.media.mkv;

import java.util.*;

import net.katsuster.strview.media.mkv.MKVConsts.*;

/**
 * <p>
 * Matroska 規格由来のタグ定義用のクラス。
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
public class MKVSpec {
    private Map<Long, MKVTagSpec> specs;

    public MKVSpec() {
        specs = new TreeMap<Long, MKVTagSpec>();

        //ex.                  new MKVTagSpec(   EBML ID,               T, "Element Name",                 L,  Over,    Ma,    Mu,     1,     2,     3,     W));

        //EBML Header
        specs.put(0x1A45DFA3L, new MKVTagSpec(0x1A45DFA3, TAG_TYPE.MASTER, "EBML",                         0, false,  true,  true,  true,  true,  true,  true));
        specs.put(0x00004286L, new MKVTagSpec(0x00004286, TAG_TYPE.UINT,   "EBMLVersion",                  1, false,  true, false,  true,  true,  true,  true));
        specs.put(0x000042F7L, new MKVTagSpec(0x000042F7, TAG_TYPE.UINT,   "EBMLReadVersion",              1, false,  true, false,  true,  true,  true,  true));
        specs.put(0x000042F2L, new MKVTagSpec(0x000042F2, TAG_TYPE.UINT,   "EBMLMaxIDLength",              1, false,  true, false,  true,  true,  true,  true));
        specs.put(0x000042F3L, new MKVTagSpec(0x000042F3, TAG_TYPE.UINT,   "EBMLMaxSizeLength",            1, false,  true, false,  true,  true,  true,  true));
        specs.put(0x00004282L, new MKVTagSpec(0x00004282, TAG_TYPE.STRING, "DocType",                      1, false,  true, false,  true,  true,  true,  true));
        specs.put(0x00004287L, new MKVTagSpec(0x00004287, TAG_TYPE.UINT,   "DocTypeVersion",               1, false,  true, false,  true,  true,  true,  true));
        specs.put(0x00004285L, new MKVTagSpec(0x00004285, TAG_TYPE.UINT,   "DocTypeReadVersion",           1, false,  true, false,  true,  true,  true,  true));

        //Global elements
        specs.put(0x000000ECL, new MKVTagSpec(0x000000EC, TAG_TYPE.BINARY, "Void",                         0,  true, false, false,  true,  true,  true,  true));
        specs.put(0x000000BFL, new MKVTagSpec(0x000000BF, TAG_TYPE.BINARY, "CRC-32",                       0,  true, false, false,  true,  true,  true, false));
        specs.put(0x1B538667L, new MKVTagSpec(0x1B538667, TAG_TYPE.MASTER, "SignatureSlot",                0,  true, false,  true, false, false, false, false));
        specs.put(0x00007E8AL, new MKVTagSpec(0x00007E8A, TAG_TYPE.UINT,   "SignatureAlgo",                1, false, false, false, false, false, false, false));
        specs.put(0x00007E9AL, new MKVTagSpec(0x00007E9A, TAG_TYPE.UINT,   "SignatureHash",                1, false, false, false, false, false, false, false));
        specs.put(0x00007EA5L, new MKVTagSpec(0x00007EA5, TAG_TYPE.BINARY, "SignaturePublicKey",           1, false, false, false, false, false, false, false));
        specs.put(0x00007EB5L, new MKVTagSpec(0x00007EB5, TAG_TYPE.BINARY, "Signature",                    1, false, false, false, false, false, false, false));
        specs.put(0x00007E5BL, new MKVTagSpec(0x00007E5B, TAG_TYPE.MASTER, "SignatureElements",            1, false, false, false, false, false, false, false));
        specs.put(0x00007E7BL, new MKVTagSpec(0x00007E7B, TAG_TYPE.MASTER, "SignatureElementList",         2, false, false,  true, false, false, false, false));
        specs.put(0x00006532L, new MKVTagSpec(0x00006532, TAG_TYPE.BINARY, "SignedElement",                3, false, false,  true, false, false, false, false));

        //Segment
        specs.put(0x18538067L, new MKVTagSpec(0x18538067, TAG_TYPE.MASTER, "Segment",                      0, false,  true,  true,  true,  true,  true,  true));

        //Meta Seek Information
        specs.put(0x114D9B74L, new MKVTagSpec(0x114D9B74, TAG_TYPE.MASTER, "SeekHead",                     1, false, false,  true,  true,  true,  true,  true));
        specs.put(0x00004DBBL, new MKVTagSpec(0x00004DBB, TAG_TYPE.MASTER, "Seek",                         2, false,  true,  true,  true,  true,  true,  true));
        specs.put(0x000053ABL, new MKVTagSpec(0x000053AB, TAG_TYPE.BINARY, "SeekID",                       3, false,  true, false,  true,  true,  true,  true));
        specs.put(0x000053ACL, new MKVTagSpec(0x000053AC, TAG_TYPE.UINT,   "SeekPosition",                 3, false,  true, false,  true,  true,  true,  true));

        //Segment Information
        specs.put(0x1549A966L, new MKVTagSpec(0x1549A966, TAG_TYPE.MASTER, "Info",                         1, false,  true,  true,  true,  true,  true,  true));
        specs.put(0x000073A4L, new MKVTagSpec(0x000073A4, TAG_TYPE.BINARY, "SegmentUID",                   2, false, false, false,  true,  true,  true, false));
        specs.put(0x00007384L, new MKVTagSpec(0x00007384, TAG_TYPE.UTF8,   "SegmentFilename",              2, false, false, false,  true,  true,  true, false));
        specs.put(0x003CB923L, new MKVTagSpec(0x003CB923, TAG_TYPE.BINARY, "PrevUID",                      2, false, false, false,  true,  true,  true, false));
        specs.put(0x003C83ABL, new MKVTagSpec(0x003C83AB, TAG_TYPE.UTF8,   "PrevFilename",                 2, false, false, false,  true,  true,  true, false));
        specs.put(0x003EB923L, new MKVTagSpec(0x003EB923, TAG_TYPE.BINARY, "NextUID",                      2, false, false, false,  true,  true,  true, false));
        specs.put(0x003E83BBL, new MKVTagSpec(0x003E83BB, TAG_TYPE.UTF8,   "NextFilename",                 2, false, false, false,  true,  true,  true, false));
        specs.put(0x00004444L, new MKVTagSpec(0x00004444, TAG_TYPE.BINARY, "SegmentFamily",                2, false, false,  true,  true,  true,  true, false));
        specs.put(0x00006924L, new MKVTagSpec(0x00006924, TAG_TYPE.MASTER, "ChapterTranslate",             2, false, false,  true,  true,  true,  true, false));
        specs.put(0x000069FCL, new MKVTagSpec(0x000069FC, TAG_TYPE.UINT,   "ChapterTranslateEditionUID",   3, false, false,  true,  true,  true,  true, false));
        specs.put(0x000069BFL, new MKVTagSpec(0x000069BF, TAG_TYPE.UINT,   "ChapterTranslateCodec",        3, false,  true, false,  true,  true,  true, false));
        specs.put(0x000069A5L, new MKVTagSpec(0x000069A5, TAG_TYPE.BINARY, "ChapterTranslateID",           3, false,  true, false,  true,  true,  true, false));
        specs.put(0x002AD7B1L, new MKVTagSpec(0x002AD7B1, TAG_TYPE.UINT,   "TimecodeScale",                2, false,  true, false,  true,  true,  true,  true));
        specs.put(0x00004489L, new MKVTagSpec(0x00004489, TAG_TYPE.FLOAT,  "Duration",                     2, false, false, false,  true,  true,  true,  true));
        specs.put(0x00004461L, new MKVTagSpec(0x00004461, TAG_TYPE.DATE,   "DateUTC",                      2, false, false, false,  true,  true,  true,  true));
        specs.put(0x00007BA9L, new MKVTagSpec(0x00007BA9, TAG_TYPE.UTF8,   "Title",                        2, false, false, false,  true,  true,  true, false));
        specs.put(0x00004D80L, new MKVTagSpec(0x00004D80, TAG_TYPE.UTF8,   "MuxingApp",                    2, false,  true, false,  true,  true,  true,  true));
        specs.put(0x00005741L, new MKVTagSpec(0x00005741, TAG_TYPE.UTF8,   "WritingApp",                   2, false,  true, false,  true,  true,  true,  true));

        //Cluster
        specs.put(0x1F43B675L, new MKVTagSpec(0x1F43B675, TAG_TYPE.MASTER, "Cluster",                      1, false, false,  true,  true,  true,  true,  true));
        specs.put(0x000000E7L, new MKVTagSpec(0x000000E7, TAG_TYPE.UINT,   "Timecode",                     2, false,  true, false,  true,  true,  true,  true));
        specs.put(0x00005854L, new MKVTagSpec(0x00005854, TAG_TYPE.MASTER, "SilentTracks",                 2, false, false, false,  true,  true,  true, false));
        specs.put(0x000058D7L, new MKVTagSpec(0x000058D7, TAG_TYPE.UINT,   "SilentTrackNumber",            3, false, false,  true,  true,  true,  true, false));
        specs.put(0x000000A7L, new MKVTagSpec(0x000000A7, TAG_TYPE.UINT,   "Position",                     2, false, false, false,  true,  true,  true, false));
        specs.put(0x000000ABL, new MKVTagSpec(0x000000AB, TAG_TYPE.UINT,   "PrevSize",                     2, false, false, false,  true,  true,  true,  true));
        specs.put(0x000000A3L, new MKVTagSpec(0x000000A3, TAG_TYPE.BINARY, "SimpleBlock",                  2, false, false,  true, false,  true,  true,  true));
        specs.put(0x000000A0L, new MKVTagSpec(0x000000A0, TAG_TYPE.MASTER, "BlockGroup",                   2, false, false,  true,  true,  true,  true,  true));
        specs.put(0x000000A1L, new MKVTagSpec(0x000000A1, TAG_TYPE.BINARY, "Block",                        3, false,  true, false,  true,  true,  true,  true));
        specs.put(0x000000A2L, new MKVTagSpec(0x000000A2, TAG_TYPE.BINARY, "BlockVirtual",                 3, false, false,  true, false, false, false, false));
        specs.put(0x000075A1L, new MKVTagSpec(0x000075A1, TAG_TYPE.MASTER, "BlockAdditions",               3, false, false, false,  true,  true,  true, false));
        specs.put(0x000000A6L, new MKVTagSpec(0x000000A6, TAG_TYPE.MASTER, "BlockMore",                    4, false,  true,  true,  true,  true,  true, false));
        specs.put(0x000000EEL, new MKVTagSpec(0x000000EE, TAG_TYPE.UINT,   "BlockAddID",                   5, false,  true, false,  true,  true,  true, false));
        specs.put(0x000000A5L, new MKVTagSpec(0x000000A5, TAG_TYPE.BINARY, "BlockAdditional",              5, false,  true, false,  true,  true,  true, false));
        specs.put(0x0000009BL, new MKVTagSpec(0x0000009B, TAG_TYPE.UINT,   "BlockDuration",                3, false, false, false,  true,  true,  true,  true));
        specs.put(0x000000FAL, new MKVTagSpec(0x000000FA, TAG_TYPE.UINT,   "ReferencePriority",            3, false,  true, false,  true,  true,  true, false));
        specs.put(0x000000FBL, new MKVTagSpec(0x000000FB, TAG_TYPE.INT,    "ReferenceBlock",               3, false, false,  true,  true,  true,  true,  true));
        specs.put(0x000000FDL, new MKVTagSpec(0x000000FD, TAG_TYPE.INT,    "ReferenceVirtual",             3, false, false, false, false, false, false, false));
        specs.put(0x000000A4L, new MKVTagSpec(0x000000A4, TAG_TYPE.BINARY, "CodecState",                   3, false, false, false, false,  true,  true, false));
        specs.put(0x0000008EL, new MKVTagSpec(0x0000008E, TAG_TYPE.MASTER, "Slices",                       3, false, false,  true,  true,  true,  true,  true));
        specs.put(0x000000E8L, new MKVTagSpec(0x000000E8, TAG_TYPE.MASTER, "TimeSlice",                    4, false, false,  true,  true,  true,  true,  true));
        specs.put(0x000000CCL, new MKVTagSpec(0x000000CC, TAG_TYPE.UINT,   "LaceNumber",                   5, false, false, false,  true,  true,  true,  true));
        specs.put(0x000000CDL, new MKVTagSpec(0x000000CD, TAG_TYPE.UINT,   "FrameNumber",                  5, false, false, false, false, false, false, false));
        specs.put(0x000000CBL, new MKVTagSpec(0x000000CB, TAG_TYPE.UINT,   "BlockAdditionID",              5, false, false, false, false, false, false, false));
        specs.put(0x000000CEL, new MKVTagSpec(0x000000CE, TAG_TYPE.UINT,   "Delay",                        5, false, false, false, false, false, false, false));
        specs.put(0x000000CFL, new MKVTagSpec(0x000000CF, TAG_TYPE.UINT,   "SliceDuration",                5, false, false, false, false, false, false, false));
        specs.put(0x000000C8L, new MKVTagSpec(0x000000C8, TAG_TYPE.MASTER, "ReferenceFrame",               3, false, false, false, false, false, false, false));
        specs.put(0x000000C9L, new MKVTagSpec(0x000000C9, TAG_TYPE.UINT,   "ReferenceOffset",              4, false,  true, false, false, false, false, false));
        specs.put(0x000000CAL, new MKVTagSpec(0x000000CA, TAG_TYPE.UINT,   "ReferenceTimeCode",            4, false,  true, false, false, false, false, false));
        specs.put(0x000000AFL, new MKVTagSpec(0x000000AF, TAG_TYPE.BINARY, "EncryptedBlock",               2, false, false,  true, false, false, false, false));

        //Track
        specs.put(0x1654AE6BL, new MKVTagSpec(0x1654AE6B, TAG_TYPE.MASTER, "Tracks",                       1, false, false,  true,  true,  true,  true,  true));
        specs.put(0x000000AEL, new MKVTagSpec(0x000000AE, TAG_TYPE.MASTER, "TrackEntry",                   2, false,  true,  true,  true,  true,  true,  true));
        specs.put(0x000000D7L, new MKVTagSpec(0x000000D7, TAG_TYPE.UINT,   "TrackNumber",                  3, false,  true, false,  true,  true,  true,  true));
        specs.put(0x000073C5L, new MKVTagSpec(0x000073C5, TAG_TYPE.UINT,   "TrackUID",                     3, false,  true, false,  true,  true,  true,  true));
        specs.put(0x00000083L, new MKVTagSpec(0x00000083, TAG_TYPE.UINT,   "TrackType",                    3, false,  true, false,  true,  true,  true,  true));
        specs.put(0x000000B9L, new MKVTagSpec(0x000000B9, TAG_TYPE.UINT,   "FlagEnabled",                  3, false,  true, false, false,  true,  true,  true));
        specs.put(0x00000088L, new MKVTagSpec(0x00000088, TAG_TYPE.UINT,   "FlagDefault",                  3, false,  true, false,  true,  true,  true,  true));
        specs.put(0x000055AAL, new MKVTagSpec(0x000055AA, TAG_TYPE.UINT,   "FlagForced",                   3, false,  true,  true,  true,  true,  true,  true));
        specs.put(0x0000009CL, new MKVTagSpec(0x0000009C, TAG_TYPE.UINT,   "FlagLacing",                   3, false,  true, false,  true,  true,  true,  true));
        specs.put(0x00006DE7L, new MKVTagSpec(0x00006DE7, TAG_TYPE.UINT,   "MinCache",                     3, false,  true, false,  true,  true,  true, false));
        specs.put(0x00006DF8L, new MKVTagSpec(0x00006DF8, TAG_TYPE.UINT,   "MaxCache",                     3, false, false, false,  true,  true,  true, false));
        specs.put(0x0023E383L, new MKVTagSpec(0x0023E383, TAG_TYPE.UINT,   "DefaultDuration",              3, false, false, false,  true,  true,  true,  true));
        specs.put(0x0023314FL, new MKVTagSpec(0x0023314F, TAG_TYPE.FLOAT,  "TrackTimecodeScale",           3, false,  true, false,  true,  true,  true, false));
        specs.put(0x0000537FL, new MKVTagSpec(0x0000537F, TAG_TYPE.INT,    "TrackOffset",                  3, false, false, false, false, false, false, false));
        specs.put(0x000055EEL, new MKVTagSpec(0x000055EE, TAG_TYPE.UINT,   "MaxBlockAdditionID",           3, false,  true, false,  true,  true,  true, false));
        specs.put(0x0000536EL, new MKVTagSpec(0x0000536E, TAG_TYPE.UTF8,   "Name",                         3, false, false, false,  true,  true,  true,  true));
        specs.put(0x0022B59CL, new MKVTagSpec(0x0022B59C, TAG_TYPE.STRING, "Language",                     3, false, false, false,  true,  true,  true,  true));
        specs.put(0x00000086L, new MKVTagSpec(0x00000086, TAG_TYPE.STRING, "CodecID",                      3, false,  true, false,  true,  true,  true,  true));
        specs.put(0x000063A2L, new MKVTagSpec(0x000063A2, TAG_TYPE.BINARY, "CodecPrivate",                 3, false, false, false,  true,  true,  true,  true));
        specs.put(0x00258688L, new MKVTagSpec(0x00258688, TAG_TYPE.UTF8,   "CodecName",                    3, false, false, false,  true,  true,  true,  true));
        specs.put(0x00007446L, new MKVTagSpec(0x00007446, TAG_TYPE.UINT,   "AttachmentLink",               3, false, false, false,  true,  true,  true, false));
        specs.put(0x003A9697L, new MKVTagSpec(0x003A9697, TAG_TYPE.UTF8,   "CodecSettings",                3, false, false, false, false, false, false, false));
        specs.put(0x003B4040L, new MKVTagSpec(0x003B4040, TAG_TYPE.STRING, "CodecInfoURL",                 3, false, false,  true, false, false, false, false));
        specs.put(0x0026B240L, new MKVTagSpec(0x0026B240, TAG_TYPE.STRING, "CodecDownloadURL",             3, false, false,  true, false, false, false, false));
        specs.put(0x000000AAL, new MKVTagSpec(0x000000AA, TAG_TYPE.UINT,   "CodecDecodeAll",               3, false,  true, false, false,  true,  true, false));
        specs.put(0x00006FABL, new MKVTagSpec(0x00006FAB, TAG_TYPE.UINT,   "TrackOverlay",                 3, false, false,  true,  true,  true,  true, false));
        specs.put(0x00006624L, new MKVTagSpec(0x00006624, TAG_TYPE.MASTER, "TrackTranslate",               3, false, false,  true,  true,  true,  true, false));
        specs.put(0x000066FCL, new MKVTagSpec(0x000066FC, TAG_TYPE.UINT,   "TrackTranslateEditionUID",     4, false, false,  true,  true,  true,  true, false));
        specs.put(0x000066BFL, new MKVTagSpec(0x000066BF, TAG_TYPE.UINT,   "TrackTranslateCodec",          4, false,  true, false,  true,  true,  true, false));
        specs.put(0x000066A5L, new MKVTagSpec(0x000066A5, TAG_TYPE.BINARY, "TrackTranslateTrackID",        4, false,  true, false,  true,  true,  true, false));
        specs.put(0x000000E0L, new MKVTagSpec(0x000000E0, TAG_TYPE.MASTER, "Video",                        3, false, false, false,  true,  true,  true,  true));
        specs.put(0x0000009AL, new MKVTagSpec(0x0000009A, TAG_TYPE.UINT,   "FlagInterlaced",               4, false,  true, false, false,  true,  true,  true));
        specs.put(0x000053B8L, new MKVTagSpec(0x000053B8, TAG_TYPE.UINT,   "StereoMode",                   4, false, false, false, false, false,  true,  true));
        specs.put(0x000000B0L, new MKVTagSpec(0x000000B0, TAG_TYPE.UINT,   "PixelWidth",                   4, false,  true, false,  true,  true,  true,  true));
        specs.put(0x000000BAL, new MKVTagSpec(0x000000BA, TAG_TYPE.UINT,   "PixelHeight",                  4, false,  true, false,  true,  true,  true,  true));
        specs.put(0x000054AAL, new MKVTagSpec(0x000054AA, TAG_TYPE.UINT,   "PixelCropBottom",              4, false, false, false,  true,  true,  true,  true));
        specs.put(0x000054BBL, new MKVTagSpec(0x000054BB, TAG_TYPE.UINT,   "PixelCropTop",                 4, false, false, false,  true,  true,  true,  true));
        specs.put(0x000054CCL, new MKVTagSpec(0x000054CC, TAG_TYPE.UINT,   "PixelCropLeft",                4, false, false, false,  true,  true,  true,  true));
        specs.put(0x000054DDL, new MKVTagSpec(0x000054DD, TAG_TYPE.UINT,   "PixelCropRight",               4, false, false, false,  true,  true,  true,  true));
        specs.put(0x000054B0L, new MKVTagSpec(0x000054B0, TAG_TYPE.UINT,   "DisplayWidth",                 4, false, false, false,  true,  true,  true,  true));
        specs.put(0x000054BAL, new MKVTagSpec(0x000054BA, TAG_TYPE.UINT,   "DisplayHeight",                4, false, false, false,  true,  true,  true,  true));
        specs.put(0x000054B2L, new MKVTagSpec(0x000054B2, TAG_TYPE.UINT,   "DisplayUnit",                  4, false, false, false,  true,  true,  true,  true));
        specs.put(0x000054B3L, new MKVTagSpec(0x000054B3, TAG_TYPE.UINT,   "AspectRatioType",              4, false, false, false,  true,  true,  true,  true));
        specs.put(0x002EB524L, new MKVTagSpec(0x002EB524, TAG_TYPE.BINARY, "ColourSpace",                  4, false, false, false,  true,  true,  true, false));
        specs.put(0x002FB523L, new MKVTagSpec(0x002FB523, TAG_TYPE.FLOAT,  "GammaValue",                   4, false, false, false, false, false, false, false));
        specs.put(0x002383E3L, new MKVTagSpec(0x002383E3, TAG_TYPE.FLOAT,  "FrameRate",                    4, false, false, false,  true,  true,  true,  true));
        specs.put(0x000000E1L, new MKVTagSpec(0x000000E1, TAG_TYPE.MASTER, "Audio",                        3, false, false, false,  true,  true,  true,  true));
        specs.put(0x000000B5L, new MKVTagSpec(0x000000B5, TAG_TYPE.FLOAT,  "SamplingFrequency",            4, false,  true, false,  true,  true,  true,  true));
        specs.put(0x000078B5L, new MKVTagSpec(0x000078B5, TAG_TYPE.FLOAT,  "OutputSamplingFrequency",      4, false, false, false,  true,  true,  true,  true));
        specs.put(0x0000009FL, new MKVTagSpec(0x0000009F, TAG_TYPE.UINT,   "Channels",                     4, false,  true, false,  true,  true,  true,  true));
        specs.put(0x00007D7BL, new MKVTagSpec(0x00007D7B, TAG_TYPE.BINARY, "ChannelPositions",             4, false, false, false, false, false, false, false));
        specs.put(0x00006264L, new MKVTagSpec(0x00006264, TAG_TYPE.UINT,   "BitDepth",                     4, false, false, false,  true,  true,  true,  true));
        specs.put(0x000000E2L, new MKVTagSpec(0x000000E2, TAG_TYPE.MASTER, "TrackOperation",               3, false, false, false, false, false,  true, false));
        specs.put(0x000000E3L, new MKVTagSpec(0x000000E3, TAG_TYPE.MASTER, "TrackCombinePlanes",           4, false, false, false, false, false,  true, false));
        specs.put(0x000000E4L, new MKVTagSpec(0x000000E4, TAG_TYPE.MASTER, "TrackPlane",                   5, false,  true,  true, false, false,  true, false));
        specs.put(0x000000E5L, new MKVTagSpec(0x000000E5, TAG_TYPE.UINT,   "TrackPlaneUID",                6, false,  true, false, false, false,  true, false));
        specs.put(0x000000E6L, new MKVTagSpec(0x000000E6, TAG_TYPE.UINT,   "TrackPlaneType",               6, false,  true, false, false, false,  true, false));
        specs.put(0x000000E9L, new MKVTagSpec(0x000000E9, TAG_TYPE.MASTER, "TrackJoinBlocks",              4, false, false, false, false, false,  true, false));
        specs.put(0x000000EDL, new MKVTagSpec(0x000000ED, TAG_TYPE.UINT,   "TrackJoinUID",                 5, false,  true,  true, false, false,  true, false));
        specs.put(0x000000C0L, new MKVTagSpec(0x000000C0, TAG_TYPE.UINT,   "TrickTrackUID",                3, false, false, false, false, false, false, false));
        specs.put(0x000000C1L, new MKVTagSpec(0x000000C1, TAG_TYPE.BINARY, "TrickTrackSegmentUID",         3, false, false, false, false, false, false, false));
        specs.put(0x000000C6L, new MKVTagSpec(0x000000C6, TAG_TYPE.UINT,   "TrickTrackFlag",               3, false, false, false, false, false, false, false));
        specs.put(0x000000C7L, new MKVTagSpec(0x000000C7, TAG_TYPE.UINT,   "TrickMasterTrackUID",          3, false, false, false, false, false, false, false));
        specs.put(0x000000C4L, new MKVTagSpec(0x000000C4, TAG_TYPE.BINARY, "TrickMasterTrackSegmentUID",   3, false, false, false, false, false, false, false));
        specs.put(0x00006D80L, new MKVTagSpec(0x00006D80, TAG_TYPE.MASTER, "ContentEncodings",             3, false, false, false,  true,  true,  true, false));
        specs.put(0x00006240L, new MKVTagSpec(0x00006240, TAG_TYPE.MASTER, "ContentEncoding",              4, false,  true,  true,  true,  true,  true, false));
        specs.put(0x00005031L, new MKVTagSpec(0x00005031, TAG_TYPE.UINT,   "ContentEncodingOrder",         5, false,  true, false,  true,  true,  true, false));
        specs.put(0x00005032L, new MKVTagSpec(0x00005032, TAG_TYPE.UINT,   "ContentEncodingScope",         5, false,  true, false,  true,  true,  true, false));
        specs.put(0x00005033L, new MKVTagSpec(0x00005033, TAG_TYPE.UINT,   "ContentEncodingType",          5, false,  true, false,  true,  true,  true, false));
        specs.put(0x00005034L, new MKVTagSpec(0x00005034, TAG_TYPE.MASTER, "ContentCompression",           5, false, false, false,  true,  true,  true, false));
        specs.put(0x00004254L, new MKVTagSpec(0x00004254, TAG_TYPE.UINT,   "ContentCompAlgo",              6, false,  true, false,  true,  true,  true, false));
        specs.put(0x00004255L, new MKVTagSpec(0x00004255, TAG_TYPE.BINARY, "ContentCompSettings",          6, false, false, false,  true,  true,  true, false));
        specs.put(0x00005035L, new MKVTagSpec(0x00005035, TAG_TYPE.MASTER, "ContentEncryption",            5, false, false, false,  true,  true,  true, false));
        specs.put(0x000047E1L, new MKVTagSpec(0x000047E1, TAG_TYPE.UINT,   "ContentEncAlgo",               6, false, false, false,  true,  true,  true, false));
        specs.put(0x000047E2L, new MKVTagSpec(0x000047E2, TAG_TYPE.BINARY, "ContentEncKeyID",              6, false, false, false,  true,  true,  true, false));
        specs.put(0x000047E3L, new MKVTagSpec(0x000047E3, TAG_TYPE.BINARY, "ContentSignature",             6, false, false, false,  true,  true,  true, false));
        specs.put(0x000047E4L, new MKVTagSpec(0x000047E4, TAG_TYPE.BINARY, "ContentSigKeyID",              6, false, false, false,  true,  true,  true, false));
        specs.put(0x000047E5L, new MKVTagSpec(0x000047E5, TAG_TYPE.UINT,   "ContentSigAlgo",               6, false, false, false,  true,  true,  true, false));
        specs.put(0x000047E6L, new MKVTagSpec(0x000047E6, TAG_TYPE.UINT,   "ContentSigHashAlgo",           6, false, false, false,  true,  true,  true, false));

        //Cueing Data
        specs.put(0x1C53BB6BL, new MKVTagSpec(0x1C53BB6B, TAG_TYPE.MASTER, "Cues",                         1, false, false, false,  true,  true,  true,  true));
        specs.put(0x000000BBL, new MKVTagSpec(0x000000BB, TAG_TYPE.MASTER, "CuePoint",                     2, false,  true,  true,  true,  true,  true,  true));
        specs.put(0x000000B3L, new MKVTagSpec(0x000000B3, TAG_TYPE.UINT,   "CueTime",                      3, false,  true, false,  true,  true,  true,  true));
        specs.put(0x000000B7L, new MKVTagSpec(0x000000B7, TAG_TYPE.MASTER, "CueTrackPositions",            3, false,  true,  true,  true,  true,  true,  true));
        specs.put(0x000000F7L, new MKVTagSpec(0x000000F7, TAG_TYPE.UINT,   "CueTrack",                     4, false,  true, false,  true,  true,  true,  true));
        specs.put(0x000000F1L, new MKVTagSpec(0x000000F1, TAG_TYPE.UINT,   "CueClusterPosition",           4, false,  true, false,  true,  true,  true,  true));
        specs.put(0x00005378L, new MKVTagSpec(0x00005378, TAG_TYPE.UINT,   "CueBlockNumber",               4, false, false, false,  true,  true,  true,  true));
        specs.put(0x000000EAL, new MKVTagSpec(0x000000EA, TAG_TYPE.UINT,   "CueCodecState",                4, false, false, false, false,  true,  true, false));
        specs.put(0x000000DBL, new MKVTagSpec(0x000000DB, TAG_TYPE.MASTER, "CueReference",                 4, false, false,  true, false,  true,  true, false));
        specs.put(0x00000096L, new MKVTagSpec(0x00000096, TAG_TYPE.UINT,   "CueRefTime",                   5, false,  true, false, false,  true,  true, false));
        specs.put(0x00000097L, new MKVTagSpec(0x00000097, TAG_TYPE.UINT,   "CueRefCluster",                5, false,  true, false, false, false, false, false));
        specs.put(0x0000535FL, new MKVTagSpec(0x0000535F, TAG_TYPE.UINT,   "CueRefNumber",                 5, false, false, false, false, false, false, false));
        specs.put(0x000000EBL, new MKVTagSpec(0x000000EB, TAG_TYPE.UINT,   "CueRefCodecState",             5, false, false, false, false, false, false, false));

        //Attachment
        specs.put(0x1941A469L, new MKVTagSpec(0x1941A469, TAG_TYPE.MASTER, "Attachments",                  1, false, false, false,  true,  true,  true, false));
        specs.put(0x000061A7L, new MKVTagSpec(0x000061A7, TAG_TYPE.MASTER, "AttachedFile",                 2, false,  true,  true,  true,  true,  true, false));
        specs.put(0x0000467EL, new MKVTagSpec(0x0000467E, TAG_TYPE.UTF8,   "FileDescription",              3, false, false, false,  true,  true,  true, false));
        specs.put(0x0000466EL, new MKVTagSpec(0x0000466E, TAG_TYPE.UTF8,   "FileName",                     3, false,  true, false,  true,  true,  true, false));
        specs.put(0x00004660L, new MKVTagSpec(0x00004660, TAG_TYPE.STRING, "FileMimeType",                 3, false,  true, false,  true,  true,  true, false));
        specs.put(0x0000465CL, new MKVTagSpec(0x0000465C, TAG_TYPE.BINARY, "FileData",                     3, false,  true, false,  true,  true,  true, false));
        specs.put(0x000046AEL, new MKVTagSpec(0x000046AE, TAG_TYPE.UINT,   "FileUID",                      3, false,  true, false,  true,  true,  true, false));
        specs.put(0x00004675L, new MKVTagSpec(0x00004675, TAG_TYPE.BINARY, "FileReferral",                 3, false, false, false, false, false, false, false));
        specs.put(0x00004661L, new MKVTagSpec(0x00004661, TAG_TYPE.UINT,   "FileUsedStartTime",            3, false, false, false, false, false, false, false));
        specs.put(0x00004662L, new MKVTagSpec(0x00004662, TAG_TYPE.UINT,   "FileUsedEndTime",              3, false, false, false, false, false, false, false));
        specs.put(0x1043A770L, new MKVTagSpec(0x1043A770, TAG_TYPE.MASTER, "Chapters",                     1, false, false, false,  true,  true,  true, false));
        specs.put(0x000045B9L, new MKVTagSpec(0x000045B9, TAG_TYPE.MASTER, "EditionEntry",                 2, false,  true,  true,  true,  true,  true, false));
        specs.put(0x000045BCL, new MKVTagSpec(0x000045BC, TAG_TYPE.UINT,   "EditionUID",                   3, false, false, false,  true,  true,  true, false));
        specs.put(0x000045BDL, new MKVTagSpec(0x000045BD, TAG_TYPE.UINT,   "EditionFlagHidden",            3, false,  true, false,  true,  true,  true, false));
        specs.put(0x000045DBL, new MKVTagSpec(0x000045DB, TAG_TYPE.UINT,   "EditionFlagDefault",           3, false,  true, false,  true,  true,  true, false));
        specs.put(0x000045DDL, new MKVTagSpec(0x000045DD, TAG_TYPE.UINT,   "EditionFlagOrdered",           3, false, false, false,  true,  true,  true, false));
        specs.put(0x000000B6L, new MKVTagSpec(0x000000B6, TAG_TYPE.MASTER, "ChapterAtom",                  3,  true,  true,  true,  true,  true,  true, false));
        specs.put(0x000073C4L, new MKVTagSpec(0x000073C4, TAG_TYPE.UINT,   "ChapterUID",                   4, false,  true, false,  true,  true,  true, false));
        specs.put(0x00000091L, new MKVTagSpec(0x00000091, TAG_TYPE.UINT,   "ChapterTimeStart",             4, false,  true, false,  true,  true,  true, false));
        specs.put(0x00000092L, new MKVTagSpec(0x00000092, TAG_TYPE.UINT,   "ChapterTimeEnd",               4, false, false, false,  true,  true,  true, false));
        specs.put(0x00000098L, new MKVTagSpec(0x00000098, TAG_TYPE.UINT,   "ChapterFlagHidden",            4, false,  true, false,  true,  true,  true, false));
        specs.put(0x00004598L, new MKVTagSpec(0x00004598, TAG_TYPE.UINT,   "ChapterFlagEnabled",           4, false,  true, false,  true,  true,  true, false));
        specs.put(0x00006E67L, new MKVTagSpec(0x00006E67, TAG_TYPE.BINARY, "ChapterSegmentUID",            4, false, false, false,  true,  true,  true, false));
        specs.put(0x00006EBCL, new MKVTagSpec(0x00006EBC, TAG_TYPE.BINARY, "ChapterSegmentEditionUID",     4, false, false, false,  true,  true,  true, false));
        specs.put(0x000063C3L, new MKVTagSpec(0x000063C3, TAG_TYPE.UINT,   "ChapterPhysicalEquiv",         4, false, false, false,  true,  true,  true, false));
        specs.put(0x0000008FL, new MKVTagSpec(0x0000008F, TAG_TYPE.MASTER, "ChapterTrack",                 4, false, false, false,  true,  true,  true, false));
        specs.put(0x00000089L, new MKVTagSpec(0x00000089, TAG_TYPE.UINT,   "ChapterTrackNumber",           5, false,  true,  true,  true,  true,  true, false));
        specs.put(0x00000080L, new MKVTagSpec(0x00000080, TAG_TYPE.MASTER, "ChapterDisplay",               4, false, false,  true,  true,  true,  true, false));
        specs.put(0x00000085L, new MKVTagSpec(0x00000085, TAG_TYPE.UTF8,   "ChapString",                   5, false,  true, false,  true,  true,  true, false));
        specs.put(0x0000437CL, new MKVTagSpec(0x0000437C, TAG_TYPE.STRING, "ChapLanguage",                 5, false,  true,  true,  true,  true,  true, false));
        specs.put(0x0000437EL, new MKVTagSpec(0x0000437E, TAG_TYPE.STRING, "ChapCountry",                  5, false, false,  true,  true,  true,  true, false));
        specs.put(0x00006944L, new MKVTagSpec(0x00006944, TAG_TYPE.MASTER, "ChapProcess",                  4, false, false,  true,  true,  true,  true, false));
        specs.put(0x00006955L, new MKVTagSpec(0x00006955, TAG_TYPE.UINT,   "ChapProcessCodecID",           5, false,  true, false,  true,  true,  true, false));
        specs.put(0x0000450DL, new MKVTagSpec(0x0000450D, TAG_TYPE.BINARY, "ChapProcessPrivate",           5, false, false, false,  true,  true,  true, false));
        specs.put(0x00006911L, new MKVTagSpec(0x00006911, TAG_TYPE.MASTER, "ChapProcessCommand",           5, false, false,  true,  true,  true,  true, false));
        specs.put(0x00006922L, new MKVTagSpec(0x00006922, TAG_TYPE.UINT,   "ChapProcessTime",              6, false,  true, false,  true,  true,  true, false));
        specs.put(0x00006933L, new MKVTagSpec(0x00006933, TAG_TYPE.BINARY, "ChapProcessData",              6, false,  true, false,  true,  true,  true, false));

        //Tagging
        specs.put(0x1254C367L, new MKVTagSpec(0x1254C367, TAG_TYPE.MASTER, "Tags",                         1, false, false,  true,  true,  true,  true, false));
        specs.put(0x00007373L, new MKVTagSpec(0x00007373, TAG_TYPE.MASTER, "Tag",                          2, false,  true,  true,  true,  true,  true, false));
        specs.put(0x000063C0L, new MKVTagSpec(0x000063C0, TAG_TYPE.MASTER, "Targets",                      3, false,  true, false,  true,  true,  true, false));
        specs.put(0x000068CAL, new MKVTagSpec(0x000068CA, TAG_TYPE.UINT,   "TargetTypeValue",              4, false, false, false,  true,  true,  true, false));
        specs.put(0x000063CAL, new MKVTagSpec(0x000063CA, TAG_TYPE.STRING, "TargetType",                   4, false, false, false,  true,  true,  true, false));
        specs.put(0x000063C5L, new MKVTagSpec(0x000063C5, TAG_TYPE.UINT,   "TagTrackUID",                  4, false, false,  true,  true,  true,  true, false));
        specs.put(0x000063C9L, new MKVTagSpec(0x000063C9, TAG_TYPE.UINT,   "TagEditionUID",                4, false, false,  true,  true,  true,  true, false));
        specs.put(0x000063C4L, new MKVTagSpec(0x000063C4, TAG_TYPE.UINT,   "TagChapterUID",                4, false, false,  true,  true,  true,  true, false));
        specs.put(0x000063C6L, new MKVTagSpec(0x000063C6, TAG_TYPE.UINT,   "TagAttachmentUID",             4, false, false,  true,  true,  true,  true, false));
        specs.put(0x000067C8L, new MKVTagSpec(0x000067C8, TAG_TYPE.MASTER, "SimpleTag",                    3, false,  true,  true,  true,  true,  true, false));
        specs.put(0x000045A3L, new MKVTagSpec(0x000045A3, TAG_TYPE.UTF8,   "TagName",                      4, false,  true, false,  true,  true,  true, false));
        specs.put(0x0000447AL, new MKVTagSpec(0x0000447A, TAG_TYPE.STRING, "TagLanguage",                  4, false,  true, false,  true,  true,  true, false));
        specs.put(0x00004484L, new MKVTagSpec(0x00004484, TAG_TYPE.UINT,   "MatroskaTagSpecault",                   4, false,  true, false,  true,  true,  true, false));
        specs.put(0x00004487L, new MKVTagSpec(0x00004487, TAG_TYPE.UTF8,   "TagString",                    4, false, false, false,  true,  true,  true, false));
        specs.put(0x00004485L, new MKVTagSpec(0x00004485, TAG_TYPE.BINARY, "TagBinary",                    4, false, false, false,  true,  true,  true, false));
    }

    public MKVTagSpec getTagSpec(long id) {
        return specs.get(id);
    }

    public MKVTagSpec getUnknownTagSpec() {
        return new MKVTagSpec(0x00000000, TAG_TYPE.BINARY, "(unknown)", 0,  true,  true,  true,  true,  true,  true,  true);
    }
}
