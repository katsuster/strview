package net.katsuster.strview.media.rmff;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * LogicalStream Structure
 * </p>
 *
 * @author katsuhiro
 */
public class LogicalStream extends BlockAdapter
        implements Cloneable {
    public UInt size;
    public UInt object_version;
    public UInt num_physical_streams;
    public UInt[] physical_stream_numbers;
    public UInt[] data_offsets;
    public UInt num_rules;
    public UInt[] rule_to_physical_stream_number_map;
    public UInt num_properties;
    public NameValueProperty[] properties;

    public LogicalStream() {
        size = new UInt();
        object_version = new UInt();
        num_physical_streams = new UInt();
        physical_stream_numbers = new UInt[0];
        data_offsets = new UInt[0];
        num_rules = new UInt();
        rule_to_physical_stream_number_map = new UInt[0];
        num_properties = new UInt();
        properties = new NameValueProperty[0];
    }

    @Override
    public LogicalStream clone()
            throws CloneNotSupportedException {
        LogicalStream obj = (LogicalStream)super.clone();
        int i;

        obj.size = (UInt)size.clone();
        obj.object_version = (UInt)object_version.clone();

        obj.num_physical_streams = (UInt)num_physical_streams.clone();
        obj.physical_stream_numbers = physical_stream_numbers.clone();
        for (i = 0; i < obj.physical_stream_numbers.length; i++) {
            obj.physical_stream_numbers[i] = (UInt)physical_stream_numbers[i].clone();
        }
        obj.data_offsets = data_offsets.clone();
        for (i = 0; i < obj.data_offsets.length; i++) {
            obj.data_offsets[i] = (UInt)data_offsets[i].clone();
        }
        obj.rule_to_physical_stream_number_map = rule_to_physical_stream_number_map.clone();
        for (i = 0; i < obj.rule_to_physical_stream_number_map.length; i++) {
            obj.rule_to_physical_stream_number_map[i] = (UInt)rule_to_physical_stream_number_map[i].clone();
        }

        obj.num_properties = (UInt)num_properties.clone();
        obj.properties = properties.clone();
        for (i = 0; i < obj.properties.length; i++) {
            obj.properties[i] = properties[i].clone();
        }

        return obj;
    }

    @Override
    public void read(StreamReader<?> c) {
        read(c, this);
    }

    public static void read(StreamReader<?> c,
                            LogicalStream d) {
        c.enterBlock("LogicalStream");

        int i;

        d.size           = c.readUInt(32, d.size          );
        d.object_version = c.readUInt(16, d.object_version);

        if (d.object_version.intValue() == 0) {
            d.num_physical_streams = c.readUInt(16, d.num_physical_streams);
            checkNegative("num_physical_streams", d.num_physical_streams);
            d.physical_stream_numbers = new UInt[d.num_physical_streams.intValue()];
            d.data_offsets            = new UInt[d.num_physical_streams.intValue()];
            for (i = 0; i < d.num_physical_streams.intValue(); i++) {
                d.physical_stream_numbers[i] = c.readUInt(16, d.physical_stream_numbers[i]);
                d.data_offsets[i]            = c.readUInt(32, d.data_offsets[i]           );
            }

            d.num_rules = c.readUInt(16, d.num_rules);
            checkNegative("num_rules", d.num_rules);
            d.rule_to_physical_stream_number_map = new UInt[d.num_rules.intValue()];
            for (i = 0; i < d.num_rules.intValue(); i++) {
                d.rule_to_physical_stream_number_map[i] = c.readUInt(16, d.rule_to_physical_stream_number_map[i]);
            }

            d.num_properties = c.readUInt(16, d.num_properties);
            checkNegative("num_properties", d.num_properties);
            d.properties = new NameValueProperty[d.num_properties.intValue()];
            for (i = 0; i < d.num_properties.intValue(); i++) {
                d.properties[i] = new NameValueProperty();
                d.properties[i].read(c);
            }
        }

        c.leaveBlock();
    }

    @Override
    public void write(StreamWriter<?> c) {
        write(c, this);
    }

    public static void write(StreamWriter<?> c,
                             LogicalStream d) {
        c.enterBlock("LogicalStream");

        int i;

        c.writeUInt(32, d.size          , "size"          );
        c.writeUInt(16, d.object_version, "object_version");

        if (d.object_version.intValue() == 0) {
            c.writeUInt(16, d.num_physical_streams, "num_physical_streams");
            for (i = 0; i < d.num_physical_streams.intValue(); i++) {
                c.writeUInt(16, d.physical_stream_numbers[i], "physical_stream_numbers[" + i + "]");
                c.writeUInt(32, d.data_offsets[i]           , "data_offsets[" + i + "]"           );
            }

            c.writeUInt(16, d.num_rules     , "num_rules"     );
            for (i = 0; i < d.num_rules.intValue(); i++) {
                c.writeUInt(16, d.rule_to_physical_stream_number_map[i], "rule_to_physical_stream_number_map[" + i + "]");
            }

            c.writeUInt(16, d.num_properties, "num_properties");
            for (i = 0; i < d.num_properties.intValue(); i++) {
                d.properties[i].write(c);
            }
        }

        c.leaveBlock();
    }
}
