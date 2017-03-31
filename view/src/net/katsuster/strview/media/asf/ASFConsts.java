package net.katsuster.strview.media.asf;

import net.katsuster.strview.media.*;

/**
 * <p>
 * ASF (Advanced Systems Format) 規格由来の定数、
 * 定数名定義用のクラス。
 * </p>
 *
 * <p>
 * 参考規格
 * </p>
 * <ul>
 * <li>Advanced Systems Format (ASF) Specification: Revision 01.20.05</li>
 * </ul>
 *
 * @author katsuhiro
 */
public class ASFConsts {
    //ASF オブジェクトのファクトリ
    public static final PacketFactory<ASFHeader, ASFGUID> asfFactory =
            new PacketFactory<>(ASFHeader.class);
    static {
        asfFactory.put(OBJECT_GUID.ASF_HEADER_OBJECT, ASFHeaderHeader.class);
        //asfFactory.put(OBJECT_GUID.ASF_FILE_PROPERTIES_OBJECT, ASFHeaderFileProperties.class);
        //asfFactory.put(OBJECT_GUID.ASF_STREAM_PROPERTIES_OBJECT, ASFHeaderStreamProperties.class);
        asfFactory.put(OBJECT_GUID.ASF_HEADER_EXTENSION_OBJECT, ASFHeaderHeaderExtension.class);
        //asfFactory.put(OBJECT_GUID.ASF_CODEC_LIST_OBJECT, ASFHeaderCodecList.class);
        //asfFactory.put(OBJECT_GUID.ASF_CONTENT_DESCRIPTION_OBJECT, ASFHeaderContentDescription.class);
        //asfFactory.put(OBJECT_GUID.ASF_EXTENDED_CONTENT_DESCRIPTION_OBJECT, ASFHeaderExtendedContentDescription.class);
        //asfFactory.put(OBJECT_GUID.ASF_STREAM_BITRATE_PROPERTIES_OBJECT, ASFHeaderStreamBitrateProperties.class);
        //asfFactory.put(OBJECT_GUID.ASF_PADDING_OBJECT, ASFHeaderPadding.class);
        //asfFactory.put(OBJECT_GUID.ASF_EXTENDED_STREAM_PROPERTIES_OBJECT, ASFHeaderExtendedStreamProperties.class);
        ////asfFactory.put(OBJECT_GUID.ASF_DATA_OBJECT, ASFHeaderData.class);
        //asfFactory.put(OBJECT_GUID.ASF_SIMPLE_INDEX_OBJECT, ASFHeaderSimpleIndex.class);
    }

    //ASF Stream Properties オブジェクトのファクトリ
    //public static final PacketFactory<ASFHeaderStreamProperties, ASFGUID> asfStrFactory =
    //        new PacketFactory<>(ASFHeaderStreamProperties.class);
    //static {
        //asfStrFactory.put(OBJECT_GUID.ASF_AUDIO_MEDIA, ASFHeaderStreamPropertiesAudio.class);
        //asfStrFactory.put(OBJECT_GUID.ASF_VIDEO_MEDIA, ASFHeaderStreamPropertiesVideo.class);
    //}

    protected ASFConsts() {
        //do nothing
    }

    public static String getObjectIdName(ASFGUID id) {
        String name = "..unknown..";

        //10.1 Top-level ASF object GUIDS
        if (id.equals(OBJECT_GUID.ASF_HEADER_OBJECT)) {
            //75B22630-668E-11CF-A6D9-00AA0062CE6C
            name = "ASF_Header_Object";
        } else if (id.equals(OBJECT_GUID.ASF_DATA_OBJECT)) {
            //75B22636-668E-11CF-A6D9-00AA0062CE6C
            name = "ASF_Data_Object";
        } else if (id.equals(OBJECT_GUID.ASF_SIMPLE_INDEX_OBJECT)) {
            //33000890-E5B1-11CF-89F4-00A0C90349CB
            name = "ASF_Simple_Index_Object";
        } else if (id.equals(OBJECT_GUID.ASF_INDEX_OBJECT)) {
            //D6E229D3-35DA-11D1-9034-00A0C90349BE
            name = "ASF_Index_Object";
        } else if (id.equals(OBJECT_GUID.ASF_MEDIA_OBJECT_INDEX_OBJECT)) {
            //FEB103F8-12AD-4C64-840F-2A1D2F7AD48C
            name = "ASF_Media_Object_Index_Object";
        } else if (id.equals(OBJECT_GUID.ASF_TIMECODE_INDEX_OBJECT)) {
            //3CB73FD0-0C4A-4803-953D-EDF7B6228F0C
            name = "ASF_Timecode_Index_Object";
        }

        //10.2 Header Object GUIDs
        if (id.equals(OBJECT_GUID.ASF_FILE_PROPERTIES_OBJECT)) {
            //8CABDCA1-A947-11CF-8EE4-00C00C205365
            name = "ASF_File_Properties_Object";
        } else if (id.equals(OBJECT_GUID.ASF_STREAM_PROPERTIES_OBJECT)) {
            //B7DC0791-A9B7-11CF-8EE6-00C00C205365
            name = "ASF_Stream_Properties_Object";
        } else if (id.equals(OBJECT_GUID.ASF_HEADER_EXTENSION_OBJECT)) {
            //5FBF03B5-A92E-11CF-8EE3-00C00C205365
            name = "ASF_Header_Extension_Object";
        } else if (id.equals(OBJECT_GUID.ASF_CODEC_LIST_OBJECT)) {
            //86D15240-311D-11D0-A3A4-00A0C90348F6
            name = "ASF_Codec_List_Object";
        } else if (id.equals(OBJECT_GUID.ASF_SCRIPT_COMMAND_OBJECT)) {
            //1EFB1A30-0B62-11D0-A39B-00A0C90348F6
            name = "ASF_Script_Command_Object";
        } else if (id.equals(OBJECT_GUID.ASF_MARKER_OBJECT)) {
            //F487CD01-A951-11CF-8EE6-00C00C205365
            name = "ASF_Marker_Object";
        } else if (id.equals(OBJECT_GUID.ASF_BITRATE_MUTUAL_EXCLUSION_OBJECT)) {
            //D6E229DC-35DA-11D1-9034-00A0C90349BE
            name = "ASF_Bitrate_Mutual_Exclusion_Object";
        } else if (id.equals(OBJECT_GUID.ASF_ERROR_CORRECTION_OBJECT)) {
            //75B22635-668E-11CF-A6D9-00AA0062CE6C
            name = "ASF_Error_Correction_Object";
        } else if (id.equals(OBJECT_GUID.ASF_CONTENT_DESCRIPTION_OBJECT)) {
            //75B22633-668E-11CF-A6D9-00AA0062CE6C
            name = "ASF_Content_Description_Object";
        } else if (id.equals(OBJECT_GUID.ASF_EXTENDED_CONTENT_DESCRIPTION_OBJECT)) {
            //D2D0A440-E307-11D2-97F0-00A0C95EA850
            name = "ASF_Extended_Content_Description_Object";
        } else if (id.equals(OBJECT_GUID.ASF_CONTENT_BRANDING_OBJECT)) {
            //2211B3FA-BD23-11D2-B4B7-00A0C955FC6E
            name = "ASF_Content_Branding_Object";
        } else if (id.equals(OBJECT_GUID.ASF_STREAM_BITRATE_PROPERTIES_OBJECT)) {
            //7BF875CE-468D-11D1-8D82-006097C9A2B2
            name = "ASF_Stream_Bitrate_Properties_Object";
        } else if (id.equals(OBJECT_GUID.ASF_CONTENT_ENCRYPTION_OBJECT)) {
            //2211B3FB-BD23-11D2-B4B7-00A0C955FC6E
            name = "ASF_Content_Encryption_Object";
        } else if (id.equals(OBJECT_GUID.ASF_EXTENDED_CONTENT_ENCRYPTION_OBJECT)) {
            //298AE614-2622-4C17-B935-DAE07EE9289C
            name = "ASF_Extended_Content_Encryption_Object";
        } else if (id.equals(OBJECT_GUID.ASF_DIGITAL_SIGNATURE_OBJECT)) {
            //2211B3FC-BD23-11D2-B4B7-00A0C955FC6E
            name = "ASF_Digital_Signature_Object";
        } else if (id.equals(OBJECT_GUID.ASF_PADDING_OBJECT)) {
            //1806D474-CADF-4509-A4BA-9AABCB96AAE8
            name = "ASF_Padding_Object";
        }

        //10.3 Header Extension Object GUIDs
        if (id.equals(OBJECT_GUID.ASF_EXTENDED_STREAM_PROPERTIES_OBJECT)) {
            //14E6A5CB-C672-4332-8399-A96952065B5A
            name = "ASF_Extended_Stream_Properties_Object";
        } else if (id.equals(OBJECT_GUID.ASF_ADVANCED_MUTUAL_EXCLUSION_OBJECT)) {
            //A08649CF-4775-4670-8A16-6E35357566CD
            name = "ASF_Advanced_Mutual_Exclusion_Object";
        } else if (id.equals(OBJECT_GUID.ASF_GROUP_MUTUAL_EXCLUSION_OBJECT)) {
            //D1465A40-5A79-4338-B71B-E36B8FD6C249
            name = "ASF_Group_Mutual_Exclusion_Object";
        } else if (id.equals(OBJECT_GUID.ASF_STREAM_PRIORITIZATION_OBJECT)) {
            //D4FED15B-88D3-454F-81F0-ED5C45999E24
            name = "ASF_Stream_Prioritization_Object";
        } else if (id.equals(OBJECT_GUID.ASF_BANDWIDTH_SHARING_OBJECT)) {
            //A69609E6-517B-11D2-B6AF-00C04FD908E9
            name = "ASF_Bandwidth_Sharing_Object";
        } else if (id.equals(OBJECT_GUID.ASF_LANGUAGE_LIST_OBJECT)) {
            //7C4346A9-EFE0-4BFC-B229-393EDE415C85
            name = "ASF_Language_List_Object";
        } else if (id.equals(OBJECT_GUID.ASF_METADATA_OBJECT)) {
            //C5F8CBEA-5BAF-4877-8467-AA8C44FA4CCA
            name = "ASF_Metadata_Object";
        } else if (id.equals(OBJECT_GUID.ASF_METADATA_LIBRARY_OBJECT)) {
            //44231C94-9498-49D1-A141-1D134E457054
            name = "ASF_Metadata_Library_Object";
        } else if (id.equals(OBJECT_GUID.ASF_INDEX_PARAMETERS_OBJECT)) {
            //D6E229DF-35DA-11D1-9034-00A0C90349BE
            name = "ASF_Index_Parameters_Object";
        } else if (id.equals(OBJECT_GUID.ASF_MEDIA_OBJECT_INDEX_PARAMETERS_OBJECT)) {
            //6B203BAD-3F11-48E4-ACA8-D7613DE2CFA7
            name = "ASF_Media_Object_Index_Parameters_Object";
        } else if (id.equals(OBJECT_GUID.ASF_TIMECODE_INDEX_PARAMETERS_OBJECT)) {
            //F55E496D-9797-4B5D-8C8B-604DFE9BFB24
            name = "ASF_Timecode_Index_Parameters_Object";
        } else if (id.equals(OBJECT_GUID.ASF_COMPATIBILITY_OBJECT)) {
            //26F18B5D-4584-47EC-9F5F-0E651F0452C9
            name = "ASF_Compatibility_Object";
        } else if (id.equals(OBJECT_GUID.ASF_ADVANCED_CONTENT_ENCRYPTION_OBJECT)) {
            //43058533-6981-49E6-9B74-AD12CB86D58C
            name = "ASF_Advanced_Content_Encryption_Object";
        }

        //10.4 Stream Properties Object Stream Type GUIDs
        if (id.equals(OBJECT_GUID.ASF_AUDIO_MEDIA)) {
            //F8699E40-5B4D-11CF-A8FD-00805F5C442B
            name = "ASF_Audio_Media";
        } else if (id.equals(OBJECT_GUID.ASF_VIDEO_MEDIA)) {
            //BC19EFC0-5B4D-11CF-A8FD-00805F5C442B
            name = "ASF_Video_Media";
        } else if (id.equals(OBJECT_GUID.ASF_COMMAND_MEDIA)) {
            //59DACFC0-59E6-11D0-A3AC-00A0C90348F6
            name = "ASF_Command_Media";
        } else if (id.equals(OBJECT_GUID.ASF_JFIF_MEDIA)) {
            //B61BE100-5B4E-11CF-A8FD-00805F5C442B
            name = "ASF_JFIF_Media";
        } else if (id.equals(OBJECT_GUID.ASF_DEGRADABLE_JPEG_MEDIA)) {
            //35907DE0-E415-11CF-A917-00805F5C442B
            name = "ASF_Degradable_JPEG_Media";
        } else if (id.equals(OBJECT_GUID.ASF_FILE_TRANSFER_MEDIA)) {
            //91BD222C-F21C-497A-8B6D-5AA86BFC0185
            name = "ASF_File_Transfer_Media";
        } else if (id.equals(OBJECT_GUID.ASF_BINARY_MEDIA)) {
            //3AFB65E2-47EF-40F2-AC2C-70A90D71D343
            name = "ASF_Binary_Media";
        }

        //10.4.1 Web stream Type-Specific Data GUIDs
        if (id.equals(OBJECT_GUID.ASF_WEB_STREAM_MEDIA_SUBTYPE)) {
            //776257D4-C627-41CB-8F81-7AC7FF1C40CC
            name = "ASF_Web_Stream_Media_Subtype";
        } else if (id.equals(OBJECT_GUID.ASF_WEB_STREAM_FORMAT)) {
            //DA1E6B13-8359-4050-B398-388E965BF00C
            name = "ASF_Web_Stream_Format";
        }

        //10.5 Stream Properties Object Error Correction Type GUIDs
        if (id.equals(OBJECT_GUID.ASF_NO_ERROR_CORRECTION)) {
            //20FB5700-5B55-11CF-A8FD-00805F5C442B
            name = "ASF_No_Error_Correction";
        } else if (id.equals(OBJECT_GUID.ASF_AUDIO_SPREAD)) {
            //BFC3CD50-618F-11CF-8BB2-00AA00B4E220
            name = "ASF_Audio_Spread";
        }

        //10.6 Header Extension Object GUIDs
        if (id.equals(OBJECT_GUID.ASF_RESERVED_1)) {
            //ABD3D211-A9BA-11cf-8EE6-00C00C205365
            name = "ASF_Reserved_1";
        }

        //10.7 Advanced Content Encryption Object System ID GUIDs
        if (id.equals(OBJECT_GUID.ASF_CONTENT_ENCRYPTION_SYSTEM_WINDOWS_MEDIA_DRM_NETWORK_DEVICES)) {
            //7A079BB6-DAA4-4e12-A5CA-91D38DC11A8D
            name = "ASF_Content_Encryption_System_Windows_Media_DRM_Network_Devices";
        }

        //10.8 Codec List Object GUIDs
        if (id.equals(OBJECT_GUID.ASF_RESERVED_2)) {
            //86D15241-311D-11D0-A3A4-00A0C90348F6
            name = "ASF_Reserved_2";
        }

        //10.9 Script Command Object GUIDs
        if (id.equals(OBJECT_GUID.ASF_RESERVED_3)) {
            //4B1ACBE3-100B-11D0-A39B-00A0C90348F6
            name = "ASF_Reserved_3";
        }

        //10.10 Marker Object GUIDs
        if (id.equals(OBJECT_GUID.ASF_RESERVED_4)) {
            //4CFEDB20-75F6-11CF-9C0F-00A0C90349CB
            name = "ASF_Reserved_4";
        }

        //10.11 Mutual Exclusion Object Exclusion Type GUIDs
        if (id.equals(OBJECT_GUID.ASF_MUTEX_LANGUAGE)) {
            //D6E22A00-35DA-11D1-9034-00A0C90349BE
            name = "ASF_Mutex_Language";
        } else if (id.equals(OBJECT_GUID.ASF_MUTEX_BITRATE)) {
            //D6E22A01-35DA-11D1-9034-00A0C90349BE
            name = "ASF_Mutex_Bitrate";
        } else if (id.equals(OBJECT_GUID.ASF_MUTEX_UNKNOWN)) {
            //D6E22A02-35DA-11D1-9034-00A0C90349BE
            name = "ASF_Mutex_Unknown";
        }

        //10.12 Bandwidth Sharing Object GUIDs
        if (id.equals(OBJECT_GUID.ASF_BANDWIDTH_SHARING_EXCLUSIVE)) {
            //AF6060AA-5197-11D2-B6AF-00C04FD908E9
            name = "ASF_Bandwidth_Sharing_Exclusive";
        } else if (id.equals(OBJECT_GUID.ASF_BANDWIDTH_SHARING_PARTIAL)) {
            //AF6060AB-5197-11D2-B6AF-00C04FD908E9
            name = "ASF_Bandwidth_Sharing_Partial";
        }

        //10.13 Standard Payload Extension System GUIDs
        if (id.equals(OBJECT_GUID.ASF_PAYLOAD_EXTENSION_SYSTEM_TIMECODE)) {
            //399595EC-8667-4E2D-8FDB-98814CE76C1E
            name = "ASF_Payload_Extension_System_Timecode";
        } else if (id.equals(OBJECT_GUID.ASF_PAYLOAD_EXTENSION_SYSTEM_FILE_NAME)) {
            //E165EC0E-19ED-45D7-B4A7-25CBD1E28E9B
            name = "ASF_Payload_Extension_System_File_Name";
        } else if (id.equals(OBJECT_GUID.ASF_PAYLOAD_EXTENSION_SYSTEM_CONTENT_TYPE)) {
            //D590DC20-07BC-436C-9CF7-F3BBFBF1A4DC
            name = "ASF_Payload_Extension_System_Content_Type";
        } else if (id.equals(OBJECT_GUID.ASF_PAYLOAD_EXTENSION_SYSTEM_PIXEL_ASPECT_RATIO)) {
            //1B1EE554-F9EA-4BC8-821A-376B74E4C4B8
            name = "ASF_Payload_Extension_System_Pixel_Aspect_Ratio";
        } else if (id.equals(OBJECT_GUID.ASF_PAYLOAD_EXTENSION_SYSTEM_SAMPLE_DURATION)) {
            //C6BD9450-867F-4907-83A3-C77921B733AD
            name = "ASF_Payload_Extension_System_Sample_Duration";
        } else if (id.equals(OBJECT_GUID.ASF_PAYLOAD_EXTENSION_SYSTEM_ENCRYPTION_SAMPLE_ID)) {
            //6698B84E-0AFA-4330-AEB2-1C0A98D7A44D
            name = "ASF_Payload_Extension_System_Encryption_Sample_ID";
        } else if (id.equals(OBJECT_GUID.ASF_PAYLOAD_EXTENSION_SYSTEM_DEGRADABLE_JPEG)) {
            //00E1AF06-7BEC-11D1-A582-00C04FC29CFB
            name = "ASF_Payload_Extension_System_Degradable_JPEG";
        }

        return name;
    }

    //Object GUID
    public static class OBJECT_GUID {
        //10.1 Top-level ASF object ASFGUIDS
        public static final ASFGUID ASF_HEADER_OBJECT =
                new ASFGUID(0x75B22630, 0x668E, 0x11CF, 0xA6D9, 0x00AA0062CE6CL);
        public static final ASFGUID ASF_DATA_OBJECT =
                new ASFGUID(0x75B22636, 0x668E, 0x11CF, 0xA6D9, 0x00AA0062CE6CL);
        public static final ASFGUID ASF_SIMPLE_INDEX_OBJECT =
                new ASFGUID(0x33000890, 0xE5B1, 0x11CF, 0x89F4, 0x00A0C90349CBL);
        public static final ASFGUID ASF_INDEX_OBJECT =
                new ASFGUID(0xD6E229D3, 0x35DA, 0x11D1, 0x9034, 0x00A0C90349BEL);
        public static final ASFGUID ASF_MEDIA_OBJECT_INDEX_OBJECT =
                new ASFGUID(0xFEB103F8, 0x12AD, 0x4C64, 0x840F, 0x2A1D2F7AD48CL);
        public static final ASFGUID ASF_TIMECODE_INDEX_OBJECT =
                new ASFGUID(0x3CB73FD0, 0x0C4A, 0x4803, 0x953D, 0xEDF7B6228F0CL);

        //10.2 Header Object ASFGUIDs
        public static final ASFGUID ASF_FILE_PROPERTIES_OBJECT =
                new ASFGUID(0x8CABDCA1, 0xA947, 0x11CF, 0x8EE4, 0x00C00C205365L);
        public static final ASFGUID ASF_STREAM_PROPERTIES_OBJECT =
                new ASFGUID(0xB7DC0791, 0xA9B7, 0x11CF, 0x8EE6, 0x00C00C205365L);
        public static final ASFGUID ASF_HEADER_EXTENSION_OBJECT =
                new ASFGUID(0x5FBF03B5, 0xA92E, 0x11CF, 0x8EE3, 0x00C00C205365L);
        public static final ASFGUID ASF_CODEC_LIST_OBJECT =
                new ASFGUID(0x86D15240, 0x311D, 0x11D0, 0xA3A4, 0x00A0C90348F6L);
        public static final ASFGUID ASF_SCRIPT_COMMAND_OBJECT =
                new ASFGUID(0x1EFB1A30, 0x0B62, 0x11D0, 0xA39B, 0x00A0C90348F6L);
        public static final ASFGUID ASF_MARKER_OBJECT =
                new ASFGUID(0xF487CD01, 0xA951, 0x11CF, 0x8EE6, 0x00C00C205365L);
        public static final ASFGUID ASF_BITRATE_MUTUAL_EXCLUSION_OBJECT =
                new ASFGUID(0xD6E229DC, 0x35DA, 0x11D1, 0x9034, 0x00A0C90349BEL);
        public static final ASFGUID ASF_ERROR_CORRECTION_OBJECT =
                new ASFGUID(0x75B22635, 0x668E, 0x11CF, 0xA6D9, 0x00AA0062CE6CL);
        public static final ASFGUID ASF_CONTENT_DESCRIPTION_OBJECT =
                new ASFGUID(0x75B22633, 0x668E, 0x11CF, 0xA6D9, 0x00AA0062CE6CL);
        public static final ASFGUID ASF_EXTENDED_CONTENT_DESCRIPTION_OBJECT =
                new ASFGUID(0xD2D0A440, 0xE307, 0x11D2, 0x97F0, 0x00A0C95EA850L);
        public static final ASFGUID ASF_CONTENT_BRANDING_OBJECT =
                new ASFGUID(0x2211B3FA, 0xBD23, 0x11D2, 0xB4B7, 0x00A0C955FC6EL);
        public static final ASFGUID ASF_STREAM_BITRATE_PROPERTIES_OBJECT =
                new ASFGUID(0x7BF875CE, 0x468D, 0x11D1, 0x8D82, 0x006097C9A2B2L);
        public static final ASFGUID ASF_CONTENT_ENCRYPTION_OBJECT =
                new ASFGUID(0x2211B3FB, 0xBD23, 0x11D2, 0xB4B7, 0x00A0C955FC6EL);
        public static final ASFGUID ASF_EXTENDED_CONTENT_ENCRYPTION_OBJECT =
                new ASFGUID(0x298AE614, 0x2622, 0x4C17, 0xB935, 0xDAE07EE9289CL);
        public static final ASFGUID ASF_DIGITAL_SIGNATURE_OBJECT =
                new ASFGUID(0x2211B3FC, 0xBD23, 0x11D2, 0xB4B7, 0x00A0C955FC6EL);
        public static final ASFGUID ASF_PADDING_OBJECT =
                new ASFGUID(0x1806D474, 0xCADF, 0x4509, 0xA4BA, 0x9AABCB96AAE8L);

        //10.3 Header Extension Object ASFGUIDs
        public static final ASFGUID ASF_EXTENDED_STREAM_PROPERTIES_OBJECT =
                new ASFGUID(0x14E6A5CB, 0xC672, 0x4332, 0x8399, 0xA96952065B5AL);
        public static final ASFGUID ASF_ADVANCED_MUTUAL_EXCLUSION_OBJECT =
                new ASFGUID(0xA08649CF, 0x4775, 0x4670, 0x8A16, 0x6E35357566CDL);
        public static final ASFGUID ASF_GROUP_MUTUAL_EXCLUSION_OBJECT =
                new ASFGUID(0xD1465A40, 0x5A79, 0x4338, 0xB71B, 0xE36B8FD6C249L);
        public static final ASFGUID ASF_STREAM_PRIORITIZATION_OBJECT =
                new ASFGUID(0xD4FED15B, 0x88D3, 0x454F, 0x81F0, 0xED5C45999E24L);
        public static final ASFGUID ASF_BANDWIDTH_SHARING_OBJECT =
                new ASFGUID(0xA69609E6, 0x517B, 0x11D2, 0xB6AF, 0x00C04FD908E9L);
        public static final ASFGUID ASF_LANGUAGE_LIST_OBJECT =
                new ASFGUID(0x7C4346A9, 0xEFE0, 0x4BFC, 0xB229, 0x393EDE415C85L);
        public static final ASFGUID ASF_METADATA_OBJECT =
                new ASFGUID(0xC5F8CBEA, 0x5BAF, 0x4877, 0x8467, 0xAA8C44FA4CCAL);
        public static final ASFGUID ASF_METADATA_LIBRARY_OBJECT =
                new ASFGUID(0x44231C94, 0x9498, 0x49D1, 0xA141, 0x1D134E457054L);
        public static final ASFGUID ASF_INDEX_PARAMETERS_OBJECT =
                new ASFGUID(0xD6E229DF, 0x35DA, 0x11D1, 0x9034, 0x00A0C90349BEL);
        public static final ASFGUID ASF_MEDIA_OBJECT_INDEX_PARAMETERS_OBJECT =
                new ASFGUID(0x6B203BAD, 0x3F11, 0x48E4, 0xACA8, 0xD7613DE2CFA7L);
        public static final ASFGUID ASF_TIMECODE_INDEX_PARAMETERS_OBJECT =
                new ASFGUID(0xF55E496D, 0x9797, 0x4B5D, 0x8C8B, 0x604DFE9BFB24L);
        public static final ASFGUID ASF_COMPATIBILITY_OBJECT =
                new ASFGUID(0x26F18B5D, 0x4584, 0x47EC, 0x9F5F, 0x0E651F0452C9L);
        public static final ASFGUID ASF_ADVANCED_CONTENT_ENCRYPTION_OBJECT =
                new ASFGUID(0x43058533, 0x6981, 0x49E6, 0x9B74, 0xAD12CB86D58CL);

        //10.4 Stream Properties Object Stream Type ASFGUIDs
        public static final ASFGUID ASF_AUDIO_MEDIA =
                new ASFGUID(0xF8699E40, 0x5B4D, 0x11CF, 0xA8FD, 0x00805F5C442BL);
        public static final ASFGUID ASF_VIDEO_MEDIA =
                new ASFGUID(0xBC19EFC0, 0x5B4D, 0x11CF, 0xA8FD, 0x00805F5C442BL);
        public static final ASFGUID ASF_COMMAND_MEDIA =
                new ASFGUID(0x59DACFC0, 0x59E6, 0x11D0, 0xA3AC, 0x00A0C90348F6L);
        public static final ASFGUID ASF_JFIF_MEDIA =
                new ASFGUID(0xB61BE100, 0x5B4E, 0x11CF, 0xA8FD, 0x00805F5C442BL);
        public static final ASFGUID ASF_DEGRADABLE_JPEG_MEDIA =
                new ASFGUID(0x35907DE0, 0xE415, 0x11CF, 0xA917, 0x00805F5C442BL);
        public static final ASFGUID ASF_FILE_TRANSFER_MEDIA =
                new ASFGUID(0x91BD222C, 0xF21C, 0x497A, 0x8B6D, 0x5AA86BFC0185L);
        public static final ASFGUID ASF_BINARY_MEDIA =
                new ASFGUID(0x3AFB65E2, 0x47EF, 0x40F2, 0xAC2C, 0x70A90D71D343L);

        //10.4.1 Web stream Type-Specific Data ASFGUIDs
        public static final ASFGUID ASF_WEB_STREAM_MEDIA_SUBTYPE =
                new ASFGUID(0x776257D4, 0xC627, 0x41CB, 0x8F81, 0x7AC7FF1C40CCL);
        public static final ASFGUID ASF_WEB_STREAM_FORMAT =
                new ASFGUID(0xDA1E6B13, 0x8359, 0x4050, 0xB398, 0x388E965BF00CL);

        //10.5 Stream Properties Object Error Correction Type ASFGUIDs
        public static final ASFGUID ASF_NO_ERROR_CORRECTION =
                new ASFGUID(0x20FB5700, 0x5B55, 0x11CF, 0xA8FD, 0x00805F5C442BL);
        public static final ASFGUID ASF_AUDIO_SPREAD =
                new ASFGUID(0xBFC3CD50, 0x618F, 0x11CF, 0x8BB2, 0x00AA00B4E220L);

        //10.6 Header Extension Object ASFGUIDs
        public static final ASFGUID ASF_RESERVED_1 =
                new ASFGUID(0xABD3D211, 0xA9BA, 0x11CF, 0x8EE6, 0x00C00C205365L);

        //10.7 Advanced Content Encryption Object System ID ASFGUIDs
        public static final ASFGUID ASF_CONTENT_ENCRYPTION_SYSTEM_WINDOWS_MEDIA_DRM_NETWORK_DEVICES =
                new ASFGUID(0x7A079BB6, 0xDAA4, 0x4E12, 0xA5CA, 0x91D38DC11A8DL);

        //10.8 Codec List Object ASFGUIDs
        public static final ASFGUID ASF_RESERVED_2 =
                new ASFGUID(0x86D15241, 0x311D, 0x11D0, 0xA3A4, 0x00A0C90348F6L);

        //10.9 Script Command Object ASFGUIDs
        public static final ASFGUID ASF_RESERVED_3 =
                new ASFGUID(0x4B1ACBE3, 0x100B, 0x11D0, 0xA39B, 0x00A0C90348F6L);

        //10.10 Marker Object ASFGUIDs
        public static final ASFGUID ASF_RESERVED_4 =
                new ASFGUID(0x4CFEDB20, 0x75F6, 0x11CF, 0x9C0F, 0x00A0C90349CBL);

        //10.11 Mutual Exclusion Object Exclusion Type ASFGUIDs
        public static final ASFGUID ASF_MUTEX_LANGUAGE =
                new ASFGUID(0xD6E22A00, 0x35DA, 0x11D1, 0x9034, 0x00A0C90349BEL);
        public static final ASFGUID ASF_MUTEX_BITRATE =
                new ASFGUID(0xD6E22A01, 0x35DA, 0x11D1, 0x9034, 0x00A0C90349BEL);
        public static final ASFGUID ASF_MUTEX_UNKNOWN =
                new ASFGUID(0xD6E22A02, 0x35DA, 0x11D1, 0x9034, 0x00A0C90349BEL);

        //10.12 Bandwidth Sharing Object ASFGUIDs
        public static final ASFGUID ASF_BANDWIDTH_SHARING_EXCLUSIVE =
                new ASFGUID(0xAF6060AA, 0x5197, 0x11D2, 0xB6AF, 0x00C04FD908E9L);
        public static final ASFGUID ASF_BANDWIDTH_SHARING_PARTIAL =
                new ASFGUID(0xAF6060AB, 0x5197, 0x11D2, 0xB6AF, 0x00C04FD908E9L);

        //10.13 Standard Payload Extension System ASFGUIDs
        public static final ASFGUID ASF_PAYLOAD_EXTENSION_SYSTEM_TIMECODE =
                new ASFGUID(0x399595EC, 0x8667, 0x4E2D, 0x8FDB, 0x98814CE76C1EL);
        public static final ASFGUID ASF_PAYLOAD_EXTENSION_SYSTEM_FILE_NAME =
                new ASFGUID(0xE165EC0E, 0x19ED, 0x45D7, 0xB4A7, 0x25CBD1E28E9BL);
        public static final ASFGUID ASF_PAYLOAD_EXTENSION_SYSTEM_CONTENT_TYPE =
                new ASFGUID(0xD590DC20, 0x07BC, 0x436C, 0x9CF7, 0xF3BBFBF1A4DCL);
        public static final ASFGUID ASF_PAYLOAD_EXTENSION_SYSTEM_PIXEL_ASPECT_RATIO =
                new ASFGUID(0x1B1EE554, 0xF9EA, 0x4BC8, 0x821A, 0x376B74E4C4B8L);
        public static final ASFGUID ASF_PAYLOAD_EXTENSION_SYSTEM_SAMPLE_DURATION =
                new ASFGUID(0xC6BD9450, 0x867F, 0x4907, 0x83A3, 0xC77921B733ADL);
        public static final ASFGUID ASF_PAYLOAD_EXTENSION_SYSTEM_ENCRYPTION_SAMPLE_ID =
                new ASFGUID(0x6698B84E, 0x0AFA, 0x4330, 0xAEB2, 0x1C0A98D7A44DL);
        public static final ASFGUID ASF_PAYLOAD_EXTENSION_SYSTEM_DEGRADABLE_JPEG =
                new ASFGUID(0x00E1AF06, 0x7BEC, 0x11D1, 0xA582, 0x00C04FC29CFBL);
    }
}
