package net.katsuster.strview.media.mkv;

import java.util.*;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * Matroska SimpleBlock タグヘッダ。
 * </p>
 *
 * @author katsuhiro
 */
public class MKVHeaderSimpleBlock extends MKVHeader {
    public EBMLvalue track_number;
    public UInt timecode;
    public UInt keyframe;
    public UInt reserved1;
    public UInt invisible;
    public UInt lacing;
    public UInt discardable;

    //EBML lacing
    public UInt lacing_head;
    public EBMLvalue lacing_size0;
    public ArrayList<EBMLlacing> lacing_diffs;

    //Lacing の各フレームのサイズ（バイト単位）
    private ArrayList<Long> lacing_sizes;

    public MKVHeaderSimpleBlock() {
        super();

        track_number = new EBMLvalue();
        timecode = new UInt();
        keyframe = new UInt();
        reserved1 = new UInt();
        invisible = new UInt();
        lacing = new UInt();
        discardable = new UInt();

        lacing_head = new UInt();
        lacing_size0 = new EBMLvalue();
        lacing_diffs = new ArrayList<EBMLlacing>();

        lacing_sizes = new ArrayList<Long>();
    }

    @Override
    public MKVHeaderSimpleBlock clone()
            throws CloneNotSupportedException {
        MKVHeaderSimpleBlock obj = (MKVHeaderSimpleBlock)super.clone();

        obj.track_number = track_number.clone();
        obj.timecode = timecode.clone();
        obj.keyframe = keyframe.clone();
        obj.reserved1 = reserved1.clone();
        obj.invisible = invisible.clone();
        obj.lacing = lacing.clone();
        obj.discardable = discardable.clone();

        obj.lacing_head = lacing_head.clone();
        obj.lacing_size0 = lacing_size0.clone();

        obj.lacing_diffs = new ArrayList<EBMLlacing>();
        for (EBMLlacing v : lacing_diffs) {
            obj.lacing_diffs.add(v.clone());
        }

        lacing_sizes = new ArrayList<Long>();
        for (Long v : lacing_sizes) {
            obj.lacing_sizes.add(new Long(v));
        }

        return obj;
    }

    /**
     * <p>
     * タグ本体に別のタグを含められるかどうかを返します。
     * </p>
     *
     * @return タグ本体に別のタグを含められる場合は true、
     * 含められない場合は false
     */
    @Override
    public boolean isRecursive() {
        return false;
    }

    @Override
    public void read(PacketReader<?> c) {
        read(c, this);
    }

    public static void read(PacketReader<?> c,
                            MKVHeaderSimpleBlock d) {
        EBMLlacing val;
        long pos;
        long frame_size, frame_sum;
        int i;

        c.enterBlock("Matroska SimpleBlock header");

        MKVHeader.read(c, d);

        pos = c.position();

        d.track_number.read(c);

        c.readUInt(16, d.timecode   );
        c.readUInt( 1, d.keyframe   );
        c.readUInt( 3, d.reserved1  );
        c.readUInt( 1, d.invisible  );
        c.readUInt( 2, d.lacing     );
        c.readUInt( 1, d.discardable);

        if (d.lacing.intValue() == LACING.EBML) {
            c.readUInt( 8, d.lacing_head);
            d.lacing_size0.read(c);

            //最初のフレームサイズは絶対値
            frame_size = d.lacing_size0.getValue();
            frame_sum = frame_size;
            d.lacing_sizes.add(frame_size);

            for (i = 0; i < d.lacing_head.intValue() - 1; i++) {
                val = new EBMLlacing();
                val.read(c);
                d.lacing_diffs.add(val);

                //2 ～ n-1 のフレームサイズは相対値
                frame_size += val.getValue();
                frame_sum += frame_size;
                d.lacing_sizes.add(frame_size);
            }

            //最後のフレームサイズはブロックサイズから計算
            frame_size = (d.tag_len.getValue() - frame_sum)
                    - ((c.position() - pos) >> 3);
            d.lacing_sizes.add(frame_size);
        } else if (d.lacing.intValue() == LACING.FIXED_SIZE) {
            c.readUInt( 8, d.lacing_head);
        }

        c.leaveBlock();
    }

    @Override
    public void write(PacketWriter<?> c) {
        write(c, this);
    }

    public static void write(PacketWriter<?> c,
                             MKVHeaderSimpleBlock d) {
        int i;

        c.enterBlock("Matroska SimpleBlock header");

        MKVHeader.write(c, d);

        c.mark("track_number", "");
        d.track_number.write(c);

        c.writeUInt(16, d.timecode   , "timecode"   );
        c.writeUInt( 1, d.keyframe   , "keyframe"   );
        c.writeUInt( 3, d.reserved1  , "reserved1"  );
        c.writeUInt( 1, d.invisible  , "invisible"  );
        c.writeUInt( 2, d.lacing     , "lacing"     , d.getLacingName());
        c.writeUInt( 1, d.discardable, "discardable");

        if (d.lacing.intValue() == LACING.EBML) {
            c.writeUInt( 8, d.lacing_head, "lacing_head");
            c.mark("lacing_size0", "");
            d.lacing_size0.write(c);

            for (i = 0; i < d.lacing_head.intValue() - 1; i++) {
                c.mark("lacing_diffs[" + i + "]", "");
                d.lacing_diffs.get(i).write(c);
            }
            for (i = 0; i < d.lacing_head.intValue() + 1; i++) {
                c.mark("lacing_sizes[" + i + "]",
                        d.lacing_sizes.get(i));
            }
        } else if (d.lacing.intValue() == LACING.FIXED_SIZE) {
            c.writeUInt( 8, d.lacing_head, "lacing_head");
        }

        c.leaveBlock();
    }

    public String getLacingName() {
        return getLacingName(lacing.intValue());
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
