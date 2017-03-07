package net.katsuster.strview.media.mkv;

import java.util.*;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;
import net.katsuster.strview.media.mkv.MKVConsts.*;

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
        obj.timecode = (UInt)timecode.clone();
        obj.keyframe = (UInt)keyframe.clone();
        obj.reserved1 = (UInt)reserved1.clone();
        obj.invisible = (UInt)invisible.clone();
        obj.lacing = (UInt)lacing.clone();
        obj.discardable = (UInt)discardable.clone();

        obj.lacing_head = (UInt)lacing_head.clone();
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

    @Override
    public boolean isMaster() {
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

        c.enterBlock("Matroska SimpleBlock");

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

        c.enterBlock("Matroska SimpleBlock");

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
        return MKVConsts.getLacingName(lacing.intValue());
    }
}
