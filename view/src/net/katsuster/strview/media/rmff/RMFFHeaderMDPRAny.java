package net.katsuster.strview.media.rmff;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * MDPR チャンクヘッダ。その他のデータ形式。
 * </p>
 */
public class RMFFHeaderMDPRAny<T extends LargeList<?>>
        extends RMFFHeaderMDPR<T>
        implements Cloneable {
    public UInt type_specific_len;
    public LargeBitList type_specific_data;

    public RMFFHeaderMDPRAny() {
        type_specific_len = new UInt();
        type_specific_data = new SubLargeBitList();
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
    public void read(StreamReader<?> c) {
        read(c, this);
    }

    public static void read(StreamReader<?> c,
                            RMFFHeaderMDPRAny d) {
        c.enterBlock("MDPR(unknown type_specific_data) chunk");

        RMFFHeaderMDPR.read(c, d);

        if (d.object_version.intValue() == 0) {
            d.type_specific_len = c.readUInt(32, d.type_specific_len);
            checkNegative("type_specific_len", d.type_specific_len);

            d.type_specific_data = c.readBitList(d.type_specific_len.intValue() << 3, d.type_specific_data);
        }

        c.leaveBlock();
    }

    @Override
    public void write(StreamWriter<?> c) {
        write(c, this);
    }

    public static void write(StreamWriter<?> c,
                             RMFFHeaderMDPRAny d) {
        c.enterBlock("MDPR(unknown type_specific_data) chunk");

        RMFFHeaderMDPR.write(c, d);

        if (d.object_version.intValue() == 0) {
            c.writeUInt(32, d.type_specific_len, "type_specific_len");

            c.writeBitList(d.type_specific_len.intValue() << 3, d.type_specific_data, "type_specific_data");
        }

        c.leaveBlock();
    }
}
