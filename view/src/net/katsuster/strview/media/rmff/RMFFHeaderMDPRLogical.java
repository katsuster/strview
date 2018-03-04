package net.katsuster.strview.media.rmff;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * MDPR LogicalStream Structure
 * </p>
 */
public class RMFFHeaderMDPRLogical<T extends LargeList<?>>
        extends RMFFHeaderMDPR<T>
        implements Cloneable {
    public UInt type_specific_len;
    public LargeBitList type_specific_data;

    public LogicalStream<T> logical_stream;

    public RMFFHeaderMDPRLogical() {
        type_specific_len = new UInt("type_specific_len");
        type_specific_data = new SubLargeBitList("type_specific_data");

        logical_stream = new LogicalStream<>("logical_stream");
    }

    @Override
    public RMFFHeaderMDPRLogical<T> clone()
            throws CloneNotSupportedException {
        RMFFHeaderMDPRLogical<T> obj = (RMFFHeaderMDPRLogical<T>)super.clone();

        obj.type_specific_len = (UInt)type_specific_len.clone();
        obj.type_specific_data = (LargeBitList)type_specific_data.clone();

        obj.logical_stream = logical_stream.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "MDPR (logical stream) chunk";
    }

    @Override
    public void read(StreamReader<?, ?> c) {
        read(c, this);
    }

    public static void read(StreamReader<?, ?> c,
                            RMFFHeaderMDPRLogical d) {
        c.enterBlock(d);

        RMFFHeaderMDPR.read(c, d);

        if (d.object_version.intValue() == 0) {
            d.type_specific_len = c.readUInt(32, d.type_specific_len);
            checkNegative(d.type_specific_len);

            long st = c.position();
            d.logical_stream.read(c);
            long ed = c.position();

            long remain = (d.type_specific_len.intValue() << 3) - (int)(ed - st);
            if (remain > 0) {
                d.type_specific_data = c.readBitList(remain, d.type_specific_data);
            }
        }

        c.leaveBlock();
    }

    @Override
    public void write(StreamWriter<?, ?> c) {
        write(c, this);
    }

    public static void write(StreamWriter<?, ?> c,
                             RMFFHeaderMDPRLogical d) {
        c.enterBlock(d);

        RMFFHeaderMDPR.write(c, d);

        if (d.object_version.intValue() == 0) {
            c.writeUInt(32, d.type_specific_len);

            d.logical_stream.write(c);

            c.writeBitList(d.type_specific_len.intValue() << 3, d.type_specific_data);
        }

        c.leaveBlock();
    }
}
