package net.katsuster.strview.media.flv;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * AUDIODATA
 * </p>
 */
public class FLVHeaderAudio<T extends LargeList<?>>
        extends FLVHeaderES<T>
        implements Cloneable {
    public UInt sound_format;
    public UInt sound_rate;
    public UInt sound_size;
    public UInt sound_type;

    public FLVHeaderAudio() {
        sound_format = new UInt("SoundFormat");
        sound_rate   = new UInt("SoundRate");
        sound_size   = new UInt("SoundSize");
        sound_type   = new UInt("SoundType");
    }

    @Override
    public FLVHeaderAudio<T> clone()
            throws CloneNotSupportedException {
        FLVHeaderAudio<T> obj = (FLVHeaderAudio<T>)super.clone();

        obj.sound_format = (UInt)sound_format.clone();
        obj.sound_rate = (UInt)sound_rate.clone();
        obj.sound_size = (UInt)sound_size.clone();
        obj.sound_type = (UInt)sound_type.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "FLV tag (Audio)";
    }

    @Override
    public void read(StreamReader<?> c) {
        read(c, this);
    }

    public static void read(StreamReader<?> c,
                            FLVHeaderAudio d) {
        c.enterBlock(d);

        FLVHeaderES.read(c, d);

        d.sound_format = c.readUInt( 4, d.sound_format);
        d.sound_rate   = c.readUInt( 2, d.sound_rate  );
        d.sound_size   = c.readUInt( 1, d.sound_size  );
        d.sound_type   = c.readUInt( 1, d.sound_type  );

        c.leaveBlock();
    }

    @Override
    public void write(StreamWriter<?> c) {
        write(c, this);
    }

    public static void write(StreamWriter<?> c,
                             FLVHeaderAudio d) {
        c.enterBlock(d);

        FLVHeaderES.write(c, d);

        c.writeUInt( 4, d.sound_format, d.getSoundFormatName());
        c.writeUInt( 2, d.sound_rate  , d.getSoundRateName());
        c.writeUInt( 1, d.sound_size  , d.getSoundSizeName());
        c.writeUInt( 1, d.sound_type  , d.getSoundTypeName());

        c.leaveBlock();
    }

    public String getSoundFormatName() {
        return FLVConsts.getSoundFormatName(sound_format.intValue());
    }

    public String getSoundRateName() {
        return FLVConsts.getSoundRateName(sound_rate.intValue());
    }

    public String getSoundSizeName() {
        return FLVConsts.getSoundSizeName(sound_size.intValue());
    }

    public String getSoundTypeName() {
        return FLVConsts.getSoundTypeName(sound_type.intValue());
    }
}
