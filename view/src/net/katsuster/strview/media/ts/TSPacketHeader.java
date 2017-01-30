package net.katsuster.strview.media.ts;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * MPEG2-TS(Transport Stream) パケットヘッダ。
 * </p>
 *
 * <p>
 * 参考規格
 * </p>
 * <ul>
 * <li>ISO/IEC 13818-1: MPEG2 Systems</li>
 * </ul>
 *
 * @author katsuhiro
 */
public class TSPacketHeader extends BlockAdapter
        implements Cloneable {
    public UInt sync_byte;
    public UInt transport_error_indicator;
    public UInt payload_unit_start_indicator;
    public UInt transport_priority;
    public UInt pid;
    public UInt transport_scrambling_control;
    public UInt adaptation_field_control;
    public UInt continuity_counter;

    public TSPacketHeaderAdaptation adapt;

    public TSPacketHeader() {
        super();

        sync_byte = new UInt();
        transport_error_indicator = new UInt();
        payload_unit_start_indicator = new UInt();
        transport_priority = new UInt();
        pid = new UInt();
        transport_scrambling_control = new UInt();
        adaptation_field_control = new UInt();
        continuity_counter = new UInt();

        adapt = new TSPacketHeaderAdaptation();
    }

    @Override
    public TSPacketHeader clone()
            throws CloneNotSupportedException {
        TSPacketHeader obj = (TSPacketHeader)super.clone();

        obj.sync_byte = sync_byte.clone();
        obj.transport_error_indicator = transport_error_indicator.clone();
        obj.payload_unit_start_indicator = payload_unit_start_indicator.clone();
        obj.transport_priority = transport_priority.clone();
        obj.pid = pid.clone();
        obj.transport_scrambling_control = transport_scrambling_control.clone();
        obj.adaptation_field_control = adaptation_field_control.clone();
        obj.continuity_counter = continuity_counter.clone();

        obj.adapt = adapt.clone();

        return obj;
    }

    @Override
    public void read(PacketReader<?> c) {
        read(c, this);
    }

    public static void read(PacketReader<?> c,
                            TSPacketHeader d) {
        c.enterBlock("TS packet header");

        d.sync_byte                    = c.readUInt( 8, d.sync_byte                   );
        d.transport_error_indicator    = c.readUInt( 1, d.transport_error_indicator   );
        d.payload_unit_start_indicator = c.readUInt( 1, d.payload_unit_start_indicator);
        d.transport_priority           = c.readUInt( 1, d.transport_priority          );
        d.pid                          = c.readUInt(13, d.pid                         , d.getPIDName());
        d.transport_scrambling_control = c.readUInt( 2, d.transport_scrambling_control, d.getScramblingName());
        d.adaptation_field_control     = c.readUInt( 2, d.adaptation_field_control    , d.getAdaptationFieldName());
        d.continuity_counter           = c.readUInt( 4, d.continuity_counter          );

        if (d.adaptation_field_control.intValue() == 2
                || d.adaptation_field_control.intValue() == 3) {
            d.adapt.read(c);
        }

        c.leaveBlock();
    }

    @Override
    public void write(PacketWriter<?> c) {
        write(c, this);
    }

    public static void write(PacketWriter<?> c,
                            TSPacketHeader d) {
        c.enterBlock("TS packet header");

        c.writeUInt( 8, d.sync_byte                   , "sync_byte"                   );
        c.writeUInt( 1, d.transport_error_indicator   , "transport_error_indicator"   );
        c.writeUInt( 1, d.payload_unit_start_indicator, "payload_unit_start_indicator");
        c.writeUInt( 1, d.transport_priority          , "transport_priority"          );
        c.writeUInt(13, d.pid                         , "pid"                         , d.getPIDName());
        c.writeUInt( 2, d.transport_scrambling_control, "transport_scrambling_control", d.getScramblingName());
        c.writeUInt( 2, d.adaptation_field_control    , "adaptation_field_control"    , d.getAdaptationFieldName());
        c.writeUInt( 4, d.continuity_counter          , "continuity_counter"          );

        if (d.adaptation_field_control.intValue() == 2
                || d.adaptation_field_control.intValue() == 3) {
            d.adapt.write(c);
        }

        c.leaveBlock();
    }

    public String getPIDName() {
        return getPIDName(pid.intValue());
    }

    public static String getPIDName(int id) {
        String name = "..unknown..";

        //ISO 13818-1
        switch (id) {
        case PID.PAT:
            name = "Program Association Table";
            break;
        case PID.CAT:
            name = "Conditional Access Table";
            break;
        case PID.TSDT:
            name = "Transport Stream Description Table";
            break;
        case PID.IPMP_CIT:
            name = "IPMP Control Information Table";
            break;
        case PID.NULL:
            name = "Null packet";
            break;
        default:
            //do nothing
            break;
        }

        if (PID.ISO_13818_1_RES_START <= id
                && id <= PID.ISO_13818_1_RES_END) {
            //[ISO 13818-1] reserved
            name = "ISO 13818-1 reserved";
        } else if (PID.ISO_13818_1_OTHER_START <= id
                && id <= PID.ISO_13818_1_OTHER_END) {
            //[ISO 13818-1] user private
            name = "ISO 13818-1 other";
        }

        return name;
    }

    public static class PID {
        //[ISO 13818-1] Program Association Table
        public static final int PAT = 0x0000;
        //[ISO 13818-1] Conditional Access Table
        public static final int CAT = 0x0001;
        //[ISO 13818-1] Transport Stream Description Table
        public static final int TSDT = 0x0002;
        //[ISO 13818-1] IPMP Control Information Table
        public static final int IPMP_CIT = 0x0003;
        //[ISO 13818-1] reserved
        public static final int ISO_13818_1_RES_START = 0x0004;
        public static final int ISO_13818_1_RES_END   = 0x000f;
        //[ISO 13818-1] other
        public static final int ISO_13818_1_OTHER_START = 0x0010;
        public static final int ISO_13818_1_OTHER_END   = 0x1ffe;
        //[ISO 13818-1] Null packet
        public static final int NULL = 0x1fff;
    }

    public String getScramblingName() {
        return getScramblingName(transport_scrambling_control.intValue());
    }

    public static String getScramblingName(int id) {
        String name = "..unknown..";

        //ISO 13818-1
        switch (id) {
        case SCRAMBLING.NOT:
            name = "Not scrambled";
            break;
        default:
            //do nothing
            break;
        }

        if (SCRAMBLING.USER_START <= id
                && id <= SCRAMBLING.USER_END) {
            //[ISO 13818-1] User defined
            name = "ISO 13818-1 User defined";
        }

        return name;
    }

    public static class SCRAMBLING {
        //[ISO 13818-1] Not scrambled
        public static final int NOT        = 0; //0b00
        //[ISO 13818-1] User defined
        public static final int USER_START = 1; //0b01
        public static final int USER_END   = 3; //0b11
    }

    public String getAdaptationFieldName() {
        return getAdaptationFieldName(adaptation_field_control.intValue());
    }

    public static String getAdaptationFieldName(int id) {
        String name = "..unknown..";

        //ISO 13818-1
        switch (id) {
        case ADAPTATION_FIELD.RESERVED:
            name = "Reserved for future";
            break;
        case ADAPTATION_FIELD.PAYLOAD:
            name = "No adaptation, payload only";
            break;
        case ADAPTATION_FIELD.ADAPTATION:
            name = "Adaptation only, no payload";
            break;
        case ADAPTATION_FIELD.BOTH:
            name = "Adaptation, followed by payload";
            break;
        default:
            //do nothing
            break;
        }

        return name;
    }

    public static class ADAPTATION_FIELD {
        //[ISO 13818-1] Reserved for future
        public static final int RESERVED   = 0; //0b00
        //[ISO 13818-1] No adaptation, payload only
        public static final int PAYLOAD    = 1; //0b01
        //[ISO 13818-1] Adaptation only, no payload
        public static final int ADAPTATION = 2; //0b10
        //[ISO 13818-1] Adaptation, followed by payload
        public static final int BOTH       = 3; //0b11
    }
}
