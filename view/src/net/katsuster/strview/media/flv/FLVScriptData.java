package net.katsuster.strview.media.flv;

import net.katsuster.strview.media.*;

/**
 * <p>
 * Base class of SCRIPTDATAxxxx
 * </p>
 *
 * @author katsuhiro
 */
public class FLVScriptData extends BlockAdapter
        implements Cloneable {
    public FLVScriptData() {
    }

    @Override
    public FLVScriptData clone()
            throws CloneNotSupportedException {
        FLVScriptData obj = (FLVScriptData)super.clone();

        return obj;
    }

    @Override
    public void read(PacketReader<?> c) {
        read(c, this);
    }

    public static void read(PacketReader<?> c,
                            FLVScriptData d) {
    }

    @Override
    public void write(PacketWriter<?> c) {
        write(c, this);
    }

    public static void write(PacketWriter<?> c,
                             FLVScriptData d) {
    }
}
