package net.katsuster.strview.media.flv;

import net.katsuster.strview.media.*;

/**
 * <p>
 * Flash Video 規格由来の定数、定数名定義用のクラス。
 * </p>
 *
 * @author katsuhiro
 */
public class FLVConsts {
    //FLV タグのファクトリ
    public static final PacketFactory<FLVHeaderES, Integer> flvFactory =
            new PacketFactory<>(FLVHeaderES.class);
    static {
        //Container
        //flvFactory.put(TAG_TYPE.AUDIO, FLVHeaderAudio.class);
        //flvFactory.put(TAG_TYPE.VIDEO, FLVHeaderVideo.class);
        //flvFactory.put(TAG_TYPE.SCRIPT_DATA, FLVHeaderScript.class);
    }

    //FLV Audio タグのファクトリ
    //public static final PacketFactory<FLVHeaderAudio, Integer> flvAudFactory =
    //        new PacketFactory<>(FLVHeaderAudio.class);
    //static {
        //Audio
        //flvAudFactory.put(SOUND_FORMAT.AAC, FLVHeaderAudioAAC.class);
    //}

    //FLV Video タグのファクトリ
    //public static final PacketFactory<FLVHeaderVideo, Integer> flvVidFactory =
    //        new PacketFactory<>(FLVHeaderVideo.class);
    //static {
        //Video
        //flvVidFactory.put(CODEC_ID.H263, FLVHeaderVideoH263.class);
        //flvVidFactory.put(CODEC_ID.VP6, FLVHeaderVideoVP6.class);
        //flvVidFactory.put(CODEC_ID.VP6_WITH_ALPHA, FLVHeaderVideoVP6A.class);
        //flvVidFactory.put(CODEC_ID.AVC, FLVHeaderVideoAVC.class);
    //}

    //FLV SCRIPTDATAxxxx 型のファクトリ
    //public static final PacketFactory<FLVScriptData, Integer> flvDatFactory =
    //        new PacketFactory<>(FLVScriptData.class);
    //static {
        //flvDatFactory.put(SCRIPT_DATA_TYPE.NUMBER, FLVScriptDataDouble.class);
        //flvDatFactory.put(SCRIPT_DATA_TYPE.BOOLEAN, FLVScriptDataUint8.class);
        //flvDatFactory.put(SCRIPT_DATA_TYPE.STRING, FLVScriptDataString.class);
        //flvDatFactory.put(SCRIPT_DATA_TYPE.OBJECT, FLVScriptDataObject.class);
        //flvDatFactory.put(SCRIPT_DATA_TYPE.MOVIECLIP, FLVScriptData.class);
        //flvDatFactory.put(SCRIPT_DATA_TYPE.NULL, FLVScriptData.class);
        //flvDatFactory.put(SCRIPT_DATA_TYPE.UNDEFINED, FLVScriptData.class);
        ////flvDatFactory.put(SCRIPT_DATA_TYPE.REFERENCE, );
        //flvDatFactory.put(SCRIPT_DATA_TYPE.ECMAARRAY, FLVScriptDataECMAArray.class);
        //flvDatFactory.put(SCRIPT_DATA_TYPE.OBJECTEND, FLVScriptData.class);
        //flvDatFactory.put(SCRIPT_DATA_TYPE.STRICTARRAY, FLVScriptDataStrictArray.class);
        //flvDatFactory.put(SCRIPT_DATA_TYPE.DATE, FLVScriptDataDate.class);
        ////flvDatFactory.put(SCRIPT_DATA_TYPE.LONGSTRING, );
    //}

    protected FLVConsts() {
        //do nothing
    }

    public static String getTagTypeName(int id) {
        String name = "..unknown..";

        //Adobe Flash Video File Format Specification
        switch (id) {
        case TAG_TYPE.AUDIO:
            name = "audio";
            break;
        case TAG_TYPE.VIDEO:
            name = "video";
            break;
        case TAG_TYPE.SCRIPT_DATA:
            name = "script data";
            break;
        default:
            //do nothing
            break;
        }

        return name;
    }

    public static class TAG_TYPE {
        //[adobe] audio
        public static final int AUDIO = 0x08;
        //[adobe] video
        public static final int VIDEO = 0x09;
        //[adobe] script data
        public static final int SCRIPT_DATA = 0x12;
    }

    public static String getSoundFormatName(int id) {
        String name = "..unknown..";

        //Adobe Flash Video File Format Specification
        switch (id) {
        case SOUND_FORMAT.LPCM:
            name = "Linear PCM, platform endian";
            break;
        case SOUND_FORMAT.ADPCM:
            name = "ADPCM";
            break;
        case SOUND_FORMAT.MP3:
            name = "MP3";
            break;
        case SOUND_FORMAT.LPCM_LITTLE:
            name = "Linear PCM, little endian";
            break;
        case SOUND_FORMAT.NELLYMOSER_16KHZ:
            name = "Nellymoser 16kHz mono";
            break;
        case SOUND_FORMAT.NELLYMOSER_8KHZ:
            name = "Nellymoser 8kHz mono";
            break;
        case SOUND_FORMAT.NELLYMOSER:
            name = "Nellymoser";
            break;
        case SOUND_FORMAT.G711_ALOW:
            name = "G.711 A-law logarithmic PCM(internal use)";
            break;
        case SOUND_FORMAT.G711_MULOW:
            name = "G.711 mu-law logarithmic PCM(internal use)";
            break;
        case SOUND_FORMAT.RESERVED:
            name = "reserved";
            break;
        case SOUND_FORMAT.AAC:
            name = "AAC";
            break;
        case SOUND_FORMAT.SPEEX:
            name = "Speex";
            break;
        case SOUND_FORMAT.MP3_8KHZ:
            name = "MP3 8kHz(internal use)";
            break;
        case SOUND_FORMAT.DEVICE_SPECIFIC:
            name = "Device specific sound";
            break;
        default:
            //do nothing
            break;
        }

        return name;
    }

    public static class SOUND_FORMAT {
        //[adobe] Linear PCM, platform endian
        public static final int LPCM = 0;
        //[adobe] ADPCM
        public static final int ADPCM = 1;
        //[adobe] MP3
        public static final int MP3 = 2;
        //[adobe] Linear PCM, little endian
        public static final int LPCM_LITTLE = 3;
        //[adobe] Nellymoser 16kHz mono
        public static final int NELLYMOSER_16KHZ = 4;
        //[adobe] Nellymoser 8kHz mono
        public static final int NELLYMOSER_8KHZ = 5;
        //[adobe] Nellymoser
        public static final int NELLYMOSER = 6;
        //[adobe] G.711 A-law logarithmic PCM(internal use)
        public static final int G711_ALOW = 7;
        //[adobe] G.711 mu-law logarithmic PCM(internal use)
        public static final int G711_MULOW = 8;
        //[adobe] reserved
        public static final int RESERVED = 9;
        //[adobe] AAC
        public static final int AAC = 10;
        //[adobe] Speex
        public static final int SPEEX = 11;
        //[adobe] MP3 8kHz(internal use)
        public static final int MP3_8KHZ = 14;
        //[adobe] Device specific sound
        public static final int DEVICE_SPECIFIC = 15;
    }

    public static String getCodecIDName(int id) {
        String name = "..unknown..";

        //Adobe Flash Video File Format Specification
        switch (id) {
        case CODEC_ID.JPEG:
            name = "JPEG";
            break;
        case CODEC_ID.H263:
            name = "Sorenson H.263";
            break;
        case CODEC_ID.SCREEN_V1:
            name = "Screen video";
            break;
        case CODEC_ID.VP6:
            name = "On2 VP6";
            break;
        case CODEC_ID.VP6_WITH_ALPHA:
            name = "On2 VP6 with alpha channel";
            break;
        case CODEC_ID.SCREEN_V2:
            name = "Screen video version 2";
            break;
        case CODEC_ID.AVC:
            name = "AVC";
            break;
        default:
            //do nothing
            break;
        }

        return name;
    }

    public static class CODEC_ID {
        //[adobe] JPEG(currently unused)
        public static final int JPEG = 1;
        //[adobe] Sorenson H.263
        public static final int H263 = 2;
        //[adobe] Screen video
        public static final int SCREEN_V1 = 3;
        //[adobe] On2 VP6
        public static final int VP6 = 4;
        //[adobe] On2 VP6 with alpha channel
        public static final int VP6_WITH_ALPHA = 5;
        //[adobe] Screen video version 2
        public static final int SCREEN_V2 = 6;
        //[adobe] AVC
        public static final int AVC = 7;
    }

    public static String getScriptDataTypeName(int id) {
        String name = "..unknown..";

        //Adobe Flash Video File Format Specification
        switch (id) {
        case SCRIPT_DATA_TYPE.NUMBER:
            name = "Number(DOUBLE)";
            break;
        case SCRIPT_DATA_TYPE.BOOLEAN:
            name = "Boolean(UI8)";
            break;
        case SCRIPT_DATA_TYPE.STRING:
            name = "String(SCRIPTDATASTRING)";
            break;
        case SCRIPT_DATA_TYPE.OBJECT:
            name = "Object(SCRIPTDATAOBJECT)";
            break;
        case SCRIPT_DATA_TYPE.MOVIECLIP:
            name = "MovieClip";
            break;
        case SCRIPT_DATA_TYPE.NULL:
            name = "Null";
            break;
        case SCRIPT_DATA_TYPE.UNDEFINED:
            name = "Undefined";
            break;
        case SCRIPT_DATA_TYPE.REFERENCE:
            name = "Reference(UI16)";
            break;
        case SCRIPT_DATA_TYPE.ECMAARRAY:
            name = "ECMA Array(SCRIPTDATAECMAARRAY)";
            break;
        case SCRIPT_DATA_TYPE.OBJECTEND:
            name = "Object end marker";
            break;
        case SCRIPT_DATA_TYPE.STRICTARRAY:
            name = "Strict Array(SCRIPTDATASTRICTARRAY)";
            break;
        case SCRIPT_DATA_TYPE.DATE:
            name = "Date(SCRIPTDATADATE)";
            break;
        case SCRIPT_DATA_TYPE.LONGSTRING:
            name = "Long string(SCRIPTDATAOLONGSTRING)";
            break;
        default:
            //do nothing
            break;
        }

        return name;
    }

    public static class SCRIPT_DATA_TYPE {
        //[adobe] Number
        public static final int NUMBER = 0;
        //[adobe] Boolean
        public static final int BOOLEAN = 1;
        //[adobe] SCRIPTDATASTRING
        public static final int STRING = 2;
        //[adobe] SCRIPTDATAOBJECT
        public static final int OBJECT = 3;
        //[adobe] MovieClip(reserved, not supported)
        public static final int MOVIECLIP = 4;
        //[adobe] Null
        public static final int NULL = 5;
        //[adobe] Undefined
        public static final int UNDEFINED = 6;
        //[adobe] Reference
        public static final int REFERENCE = 7;
        //[adobe] SCRIPTDATAECMAARRAY
        public static final int ECMAARRAY = 8;
        //[adobe] Object end marker
        public static final int OBJECTEND = 9;
        //[adobe] SCRIPTDATASTRICTARRAY
        public static final int STRICTARRAY = 10;
        //[adobe] SCRIPTDATADATE
        public static final int DATE = 11;
        //[adobe] SCRIPTDATAOLONGSTRING
        public static final int LONGSTRING = 12;
    }
}
