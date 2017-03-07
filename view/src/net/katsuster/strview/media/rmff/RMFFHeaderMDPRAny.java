package net.katsuster.strview.media.rmff;

import net.katsuster.strview.util.*;
import net.katsuster.strview.io.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * MDPR チャンクヘッダ。その他のデータ形式。
 * </p>
 *
 * @author katsuhiro
 */
public class RMFFHeaderMDPRAny extends RMFFHeaderMDPR
        implements Cloneable {
    public UInt type_specific_len;
    public LargeBitList type_specific_data;

    public RMFFHeaderMDPRAny() {
        super();

        type_specific_len = new UInt();
        type_specific_data = new MemoryBitList();
    }

    @Override
    public RMFFHeaderMDPRAny clone()
            throws CloneNotSupportedException {
        RMFFHeaderMDPRAny obj = (RMFFHeaderMDPRAny)super.clone();

        obj.type_specific_len = (UInt)type_specific_len.clone();
        obj.type_specific_data = (LargeBitList)type_specific_data.clone();

        return obj;
    }

    @Override
    public void read(PacketReader<?> c) {
        read(c, this);
    }

    public static void read(PacketReader<?> c,
                            RMFFHeaderMDPRAny d) {
        c.enterBlock("MDPR(unknown type_specific_data) chunk");

        RMFFHeaderMDPR.read(c, d);

        if (d.object_version.intValue() == 0) {
            d.type_specific_len = c.readUInt(32, d.type_specific_len);
            checkNegative("type_specific_len", d.type_specific_len);

            d.type_specific_data = c.readSubList(d.type_specific_len.intValue() << 3, d.type_specific_data);
        }

        c.leaveBlock();
    }

    @Override
    public void write(PacketWriter<?> c) {
        write(c, this);
    }

    public static void write(PacketWriter<?> c,
                             RMFFHeaderMDPRAny d) {
        c.enterBlock("MDPR(unknown type_specific_data) chunk");

        RMFFHeaderMDPR.write(c, d);

        if (d.object_version.intValue() == 0) {
            c.writeUInt(32, d.type_specific_len, "type_specific_len");

            c.writeSubList(d.type_specific_len.intValue() << 3, d.type_specific_data, "type_specific_data");
        }

        c.leaveBlock();
    }
}
