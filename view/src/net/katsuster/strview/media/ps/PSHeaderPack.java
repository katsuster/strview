package net.katsuster.strview.media.ps;

import net.katsuster.strview.util.*;
import net.katsuster.strview.io.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * MPEG2-PS (Program Stream) pack header
 * </p>
 *
 * <p>
 * 参考規格
 * </p>
 * <ul>
 * <li>ITU-T H.222, ISO/IEC 13818-1:
 * Information technology – Generic coding of moving pictures and
 * associated audio information: Systems</li>
 * </ul>
 *
 * @author katsuhiro
 */
public class PSHeaderPack extends PSHeader
        implements Cloneable {
    public UInt reserved1; //must be '01'
    public UInt system_clock_reference_base_high;
    public UInt marker_bit1;
    public UInt system_clock_reference_base_mid;
    public UInt marker_bit2;
    public UInt system_clock_reference_base_low;
    public UInt marker_bit3;
    public UInt system_clock_reference_extension;
    public UInt marker_bit4;
    public UInt program_mux_rate;
    public UInt marker_bit5;
    public UInt marker_bit6;
    public UInt reserved2;
    public UInt pack_stuffing_length;
    public LargeBitList stuffing_byte;

    public PSHeaderPack() {
        reserved1 = new UInt();
        system_clock_reference_base_high = new UInt();
        marker_bit1 = new UInt();
        system_clock_reference_base_mid = new UInt();
        marker_bit2 = new UInt();
        system_clock_reference_base_low = new UInt();
        marker_bit3 = new UInt();
        system_clock_reference_extension = new UInt();
        marker_bit4 = new UInt();
        program_mux_rate = new UInt();
        marker_bit5 = new UInt();
        marker_bit6 = new UInt();
        reserved2 = new UInt();
        pack_stuffing_length = new UInt();
        stuffing_byte = new MemoryBitList();
    }

    @Override
    public PSHeaderPack clone()
            throws CloneNotSupportedException {
        PSHeaderPack obj = (PSHeaderPack)super.clone();

        obj.reserved1 = reserved1.clone();
        obj.system_clock_reference_base_high = system_clock_reference_base_high.clone();
        obj.marker_bit1 = marker_bit1.clone();
        obj.system_clock_reference_base_mid = system_clock_reference_base_mid.clone();
        obj.marker_bit2 = marker_bit2.clone();
        obj.system_clock_reference_base_low = system_clock_reference_base_low.clone();
        obj.marker_bit3 = marker_bit3.clone();
        obj.system_clock_reference_extension = system_clock_reference_extension.clone();
        obj.marker_bit4 = marker_bit4.clone();
        obj.program_mux_rate = program_mux_rate.clone();
        obj.marker_bit5 = marker_bit5.clone();
        obj.marker_bit6 = marker_bit6.clone();
        obj.reserved2 = reserved2.clone();
        obj.pack_stuffing_length = pack_stuffing_length.clone();
        obj.stuffing_byte = stuffing_byte;

        return obj;
    }

    @Override
    public void read(PacketReader<?> c) {
        read(c, this);
    }

    public static void read(PacketReader<?> c,
                            PSHeaderPack d) {
        int size_st;

        c.enterBlock("PS pack header");

        PSHeader.read(c, d);

        d.reserved1                        = c.readUInt( 2, d.reserved1                       );
        d.system_clock_reference_base_high = c.readUInt( 3, d.system_clock_reference_base_high);
        d.marker_bit1                      = c.readUInt( 1, d.marker_bit1                     );
        d.system_clock_reference_base_mid  = c.readUInt(15, d.system_clock_reference_base_mid );
        d.marker_bit2                      = c.readUInt( 1, d.marker_bit2                     );
        d.system_clock_reference_base_low  = c.readUInt(15, d.system_clock_reference_base_low );
        d.marker_bit3                      = c.readUInt( 1, d.marker_bit3                     );
        d.system_clock_reference_extension = c.readUInt( 9, d.system_clock_reference_extension);
        d.marker_bit4                      = c.readUInt( 1, d.marker_bit4                     );
        d.program_mux_rate                 = c.readUInt(22, d.program_mux_rate                );
        d.marker_bit5                      = c.readUInt( 1, d.marker_bit5                     );
        d.marker_bit6                      = c.readUInt( 1, d.marker_bit6                     );
        d.reserved2                        = c.readUInt( 5, d.reserved2                       );
        d.pack_stuffing_length             = c.readUInt( 3, d.pack_stuffing_length            );

        //残りを埋めているバイト列を読む
        size_st = d.pack_stuffing_length.intValue();
        if (size_st < 0) {
            throw new IllegalStateException("stuffing bytes have negative size"
                    + "(size: " + size_st + ")");
        }
        d.stuffing_byte = c.readBitList(size_st, d.stuffing_byte);

        c.leaveBlock();
    }

    @Override
    public void write(PacketWriter<?> c) {
        write(c, this);
    }

    public static void write(PacketWriter<?> c,
                             PSHeaderPack d) {
        c.enterBlock("PS pack header");

        PSHeader.write(c, d);

        c.writeUInt( 2, d.reserved1                       , "reserved1"                       );
        c.writeUInt( 3, d.system_clock_reference_base_high, "system_clock_reference_base_high");
        c.writeUInt( 1, d.marker_bit1                     , "marker_bit1"                     );
        c.writeUInt(15, d.system_clock_reference_base_mid , "system_clock_reference_base_mid" );
        c.writeUInt( 1, d.marker_bit2                     , "marker_bit2"                     );
        c.writeUInt(15, d.system_clock_reference_base_low , "system_clock_reference_base_low" );
        c.writeUInt( 1, d.marker_bit3                     , "marker_bit3"                     );
        c.mark("scr", d.getSCRBase());
        c.writeUInt( 9, d.system_clock_reference_extension, "system_clock_reference_extension");
        c.writeUInt( 1, d.marker_bit4                     , "marker_bit4"                     );
        c.writeUInt(22, d.program_mux_rate                , "program_mux_rate"                );
        c.writeUInt( 1, d.marker_bit5                     , "marker_bit5"                     );
        c.writeUInt( 1, d.marker_bit6                     , "marker_bit6"                     );
        c.writeUInt( 5, d.reserved2                       , "reserved2"                       );
        c.writeUInt( 3, d.pack_stuffing_length            , "pack_stuffing_length"            );

        //残りを埋める
        c.writeBitList(d.pack_stuffing_length.intValue() << 3, d.stuffing_byte, "stuffing_byte");

        c.leaveBlock();
    }

    public long getSCRBase() {
        return getSCRValue(
                system_clock_reference_base_high.longValue(),
                system_clock_reference_base_mid.longValue(),
                system_clock_reference_base_low.longValue());
    }

    public static long getSCRValue(long high, long mid, long low) {
        return ((high << 30) | (mid << 15) | (low << 0));
    }
}
