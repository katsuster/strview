package net.katsuster.strview.media.m4v;

import net.katsuster.strview.media.*;
import net.katsuster.strview.util.*;

/**
 * <p>
 * MPEG4 Part 2 Visual ヘッダ。
 * </p>
 *
 * <p>
 * 参考規格
 * </p>
 * <ul>
 * <li>ISO/IEC 14496-2:
 * Information technology - Coding of audio-visual objects
 * Part 2: Video</li>
 * </ul>
 */
public class M4VConsts {
    //MPEG4 Part2 Video データのファクトリ
    public static final PacketFactory<M4VHeader, Integer> m4vFactory =
            new PacketFactory<>(M4VHeader.class);
    static {
        //m4vFactory.put(START_CODE.PICTURE, M4VHeaderPicture.class);
    }

    public static String getStartCodeName(int id) {
        String name = "..unknown..";

        switch (id) {
        case START_CODE.VISUAL_OBJECT_SEQUENCE:
            //[ISO 14496-2] visual_object_sequence
            name = "visual_object_sequence";
            break;
        case START_CODE.VISUAL_OBJECT_SEQUENCE_END:
            //[ISO 14496-2] visual_object_sequence_end
            name = "visual_object_sequence_end";
            break;
        case START_CODE.USER_DATA:
            //[ISO 14496-2] user_data
            name = "user_data";
            break;
        case START_CODE.GROUP_OF_VOP:
            //[ISO 14496-2] group_of_vop
            name = "group_of_vop";
            break;
        case START_CODE.VIDEO_SESSION_ERROR:
            //[ISO 14496-2] video_session_error
            name = "video_session_error";
            break;
        case START_CODE.VISUAL_OBJECT:
            //[ISO 14496-2] visual_object
            name = "visual_object";
            break;
        case START_CODE.VOP:
            //[ISO 14496-2] vop
            name = "vop";
            break;
        case START_CODE.SLICE:
            //[ISO 14496-2] slice
            name = "slice";
            break;
        case START_CODE.EXTENSION:
            //[ISO 14496-2] extension
            name = "extension";
            break;
        case START_CODE.FGS_VOP:
            //[ISO 14496-2] fgs_vop
            name = "fgs_vop";
            break;
        case START_CODE.FBA_OBJECT:
            //[ISO 14496-2] fba_object
            name = "fba_object";
            break;
        case START_CODE.FBA_OBJECT_PLANE:
            //[ISO 14496-2] fba_object_plane
            name = "fba_object_plane";
            break;
        case START_CODE.MESH_OBJECT:
            //[ISO 14496-2] mesh_object
            name = "mesh_object";
            break;
        case START_CODE.MESH_OBJECT_PLANE:
            //[ISO 14496-2] mesh_object_plane
            name = "mesh_object_plane";
            break;
        case START_CODE.STILL_TEXTURE_OBJECT:
            //[ISO 14496-2] still_texture_object
            name = "still_texture_object";
            break;
        case START_CODE.TEXTURE_SPATIAL_LAYER:
            //[ISO 14496-2] texture_spatial_layer
            name = "texture_spatial_layer";
            break;
        case START_CODE.TEXTURE_SNR_LAYER:
            //[ISO 14496-2] texture_snr_layer
            name = "texture_snr_layer";
            break;
        case START_CODE.TEXTURE_TILE:
            //[ISO 14496-2] texture_tile
            name = "texture_tile";
            break;
        case START_CODE.TEXTURE_SHAPE_LAYER:
            //[ISO 14496-2] texture_shape_layer
            name = "texture_shape_layer";
            break;
        case START_CODE.STUFFING:
            //[ISO 14496-2] stuffing
            name = "stuffing";
            break;
        default:
            //do nothing
            break;
        }

        if (START_CODE.VIDEO_OBJECT__ST <= id &&
                id <= START_CODE.VIDEO_OBJECT__ED) {
            //[ISO 14496-2] video_object_start_code
            name = "video_object_start_code";
        } else if (START_CODE.VIDEO_OBJECT_LAYER__ST <= id &&
                id <= START_CODE.VIDEO_OBJECT_LAYER__ED) {
            //[ISO 14496-2] video_object_layer_start_code
            name = "video_object_layer_start_code";
        } else if (START_CODE.RESERVED1__ST <= id &&
                id <= START_CODE.RESERVED1__ED) {
            //[ISO 14496-2] reserved
            name = "reserved";
        } else if (START_CODE.FGS_BP__ST <= id &&
                id <= START_CODE.FGS_BP__ED) {
            //[ISO 14496-2] fgs_bp_start_code
            name = "fgs_bp_start_code";
        } else if (START_CODE.RESERVED2__ST <= id &&
                id <= START_CODE.RESERVED2__ED) {
            //[ISO 14496-2] reserved
            name = "reserved";
        } else if (START_CODE.RESERVED3__ST <= id &&
                id <= START_CODE.RESERVED3__ED) {
            //[ISO 14496-2] reserved
            name = "reserved";
        } else if (START_CODE.SYSTEM__ST <= id &&
                id <= START_CODE.SYSTEM__ED) {
            //[ISO 14496-2] System start codes
            name = "System start codes";
        }

        return name;
    }

    /**
     * Table 6-3: Start code values
     */
    public static class START_CODE {
        //[ISO 14496-2] video_object_start_code
        public static final int VIDEO_OBJECT__ST = 0x00000100;
        public static final int VIDEO_OBJECT__ED = 0x0000011f;
        //[ISO 14496-2] video_object_layer_start_code
        public static final int VIDEO_OBJECT_LAYER__ST = 0x00000120;
        public static final int VIDEO_OBJECT_LAYER__ED = 0x0000012f;
        //[ISO 14496-2] reserved
        public static final int RESERVED1__ST = 0x00000130;
        public static final int RESERVED1__ED = 0x0000013f;
        //[ISO 14496-2] fgs_bp_start_code
        public static final int FGS_BP__ST = 0x00000140;
        public static final int FGS_BP__ED = 0x0000015f;
        //[ISO 14496-2] reserved
        public static final int RESERVED2__ST = 0x00000160;
        public static final int RESERVED2__ED = 0x000001af;

        //[ISO 14496-2] visual_object_sequence_start_code
        public static final int VISUAL_OBJECT_SEQUENCE = 0x000001b0;
        //[ISO 14496-2] visual_object_sequence_end_code
        public static final int VISUAL_OBJECT_SEQUENCE_END = 0x000001b1;
        //[ISO 14496-2] user_data_start_code
        public static final int USER_DATA = 0x000001b2;
        //[ISO 14496-2] group_of_vop_start_code
        public static final int GROUP_OF_VOP = 0x000001b3;
        //[ISO 14496-2] video_session_error_code
        public static final int VIDEO_SESSION_ERROR = 0x000001b4;
        //[ISO 14496-2] visual_object_start_code
        public static final int VISUAL_OBJECT = 0x000001b5;
        //[ISO 14496-2] vop_start_code
        public static final int VOP = 0x000001b6;
        //[ISO 14496-2] slice_start_code
        public static final int SLICE = 0x000001b7;
        //[ISO 14496-2] extension_start_code
        public static final int EXTENSION = 0x000001b8;
        //[ISO 14496-2] fgs_vop_start_code
        public static final int FGS_VOP = 0x000001b9;
        //[ISO 14496-2] fba_object_start_code
        public static final int FBA_OBJECT = 0x000001ba;
        //[ISO 14496-2] fba_object_plane_start_code
        public static final int FBA_OBJECT_PLANE = 0x000001bb;
        //[ISO 14496-2] mesh_object_start_code
        public static final int MESH_OBJECT = 0x000001bc;
        //[ISO 14496-2] mesh_object_plane_start_code
        public static final int MESH_OBJECT_PLANE = 0x000001bd;
        //[ISO 14496-2] still_texture_object_start_code
        public static final int STILL_TEXTURE_OBJECT = 0x000001be;
        //[ISO 14496-2] texture_spatial_layer_start_code
        public static final int TEXTURE_SPATIAL_LAYER = 0x000001bf;
        //[ISO 14496-2] texture_snr_layer_start_code
        public static final int TEXTURE_SNR_LAYER = 0x000001c0;
        //[ISO 14496-2] texture_tile_start_code
        public static final int TEXTURE_TILE = 0x000001c1;
        //[ISO 14496-2] texture_shape_layer_start_code
        public static final int TEXTURE_SHAPE_LAYER = 0x000001c2;
        //[ISO 14496-2] stuffing_start_code
        public static final int STUFFING = 0x000001c3;

        //[ISO 14496-2] reserved
        public static final int RESERVED3__ST = 0x000001c4;
        public static final int RESERVED3__ED = 0x000001c5;
        //[ISO 14496-2] System start codes
        public static final int SYSTEM__ST = 0x000001c6;
        public static final int SYSTEM__ED = 0x000001ff;
    }
}
