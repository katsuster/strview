package net.katsuster.strview.media.rmff;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * MDPR チャンクヘッダ。その他のデータ形式。
 * </p>
 */
public class RMFFHeaderMDPRAny
        extends RMFFHeaderMDPR
        implements Cloneable {
    public UInt type_specific_len;
    public LargeBitList type_specific_data;

    public RMFFHeaderMDPRAny() {
        type_specific_len = new UInt("type_specific_len");
        type_specific_data = new SubLargeBitList("type_specific_data");
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
    public String getTypeName() {
        return "MDPR (unknown) chunk";
    }

    @Override
    protected void readBits(BitStreamReader c) {
        readBits(c, this);
    }

    public static void readBits(BitStreamReader c,
                                RMFFHeaderMDPRAny d) {
        c.enterBlock(d);

        RMFFHeaderMDPR.readBits(c, d);

        if (d.object_version.intValue() == 0) {
            d.type_specific_len = c.readUInt(32, d.type_specific_len);
            checkNegative(d.type_specific_len);

            d.type_specific_data = c.readBitList(d.type_specific_len.intValue() << 3, d.type_specific_data);
        }

        c.leaveBlock();
    }

    @Override
    protected void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 RMFFHeaderMDPRAny d) {
        c.enterBlock(d);

        RMFFHeaderMDPR.writeBits(c, d);

        if (d.object_version.intValue() == 0) {
            c.writeUInt(32, d.type_specific_len);

            c.writeBitList(d.type_specific_len.intValue() << 3, d.type_specific_data);
        }

        c.leaveBlock();
    }
}
