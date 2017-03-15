package net.katsuster.strview.media.rmff;

import net.katsuster.strview.util.*;
import net.katsuster.strview.io.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * MDPR LogicalStream Structure
 * </p>
 *
 * @author katsuhiro
 */
public class RMFFHeaderMDPRLogical extends RMFFHeaderMDPR
        implements Cloneable {
    public UInt type_specific_len;
    public LargeBitList type_specific_data;

    public LogicalStream logical_stream;

    public RMFFHeaderMDPRLogical() {
        type_specific_len = new UInt();
        type_specific_data = new MemoryBitList();

        logical_stream = new LogicalStream();
    }

    @Override
    public RMFFHeaderMDPRLogical clone()
            throws CloneNotSupportedException {
        RMFFHeaderMDPRLogical obj = (RMFFHeaderMDPRLogical)super.clone();

        obj.type_specific_len = (UInt)type_specific_len.clone();
        obj.type_specific_data = (LargeBitList)type_specific_data.clone();

        obj.logical_stream = logical_stream.clone();

        return obj;
    }

    @Override
    public void read(PacketReader<?> c) {
        read(c, this);
    }

    public static void read(PacketReader<?> c,
                            RMFFHeaderMDPRLogical d) {
        c.enterBlock("MDPR(logical stream) chunk");

        RMFFHeaderMDPR.read(c, d);

        if (d.object_version.intValue() == 0) {
            d.type_specific_len = c.readUInt(32, d.type_specific_len);
            checkNegative("type_specific_len", d.type_specific_len);

            long st = c.position();
            d.logical_stream.read(c);
            long ed = c.position();

            long remain = (d.type_specific_len.intValue() << 3) - (int)(ed - st);
            if (remain > 0) {
                d.type_specific_data = c.readSubList(remain, d.type_specific_data);
            }
        }

        c.leaveBlock();
    }

    @Override
    public void write(PacketWriter<?> c) {
        write(c, this);
    }

    public static void write(PacketWriter<?> c,
                             RMFFHeaderMDPRLogical d) {
        c.enterBlock("MDPR(logical stream) chunk");

        RMFFHeaderMDPR.write(c, d);

        if (d.object_version.intValue() == 0) {
            c.writeUInt(32, d.type_specific_len, "type_specific_len");

            d.logical_stream.write(c);

            c.writeSubList(d.type_specific_len.intValue() << 3, d.type_specific_data, "type_specific_data");
        }

        c.leaveBlock();
    }
}
