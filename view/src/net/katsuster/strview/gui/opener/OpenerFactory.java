package net.katsuster.strview.gui.opener;

import java.util.*;
import javax.swing.*;

import net.katsuster.strview.gui.opener.Opener.*;
import net.katsuster.strview.gui.view.*;
import net.katsuster.strview.media.*;
import net.katsuster.strview.media.ts.*;
import net.katsuster.strview.util.*;
import net.katsuster.strview.util.bit.*;

public class OpenerFactory {
    private OpenerFactory() {

    }

    public static List<Opener> createOpener(LargeList<?> l, ViewerWindow w) {
    List<Opener> items = new ArrayList<>();

        if (l instanceof LargeBitList) {
            items.add(new Opener("Auto", FILE_TYPE.FT_AUTO, new ActionOpenAsBit(w)));
            items.add(new Opener("FLV", FILE_TYPE.FT_FLV, new ActionOpenAsBit(w)));
            items.add(new Opener("Matroska", FILE_TYPE.FT_MATROSKA, new ActionOpenAsBit(w)));
            items.add(new Opener("MPEG2 TS", FILE_TYPE.FT_MPEG2TS, new ActionOpenAsBit(w)));
            items.add(new Opener("MPEG2 PS", FILE_TYPE.FT_MPEG2PS, new ActionOpenAsBit(w)));
            items.add(new Opener("MPEG4", FILE_TYPE.FT_MPEG4, new ActionOpenAsBit(w)));
            items.add(new Opener("RIFF", FILE_TYPE.FT_RIFF, new ActionOpenAsBit(w)));
            items.add(new Opener("RealMedia", FILE_TYPE.FT_RMFF, new ActionOpenAsBit(w)));
            items.add(new Opener("MPEG2 Visual", FILE_TYPE.FT_MPEG2VIDEO, new ActionOpenAsBit(w)));
            items.add(new Opener("MPEG4 Part2 Visual", FILE_TYPE.FT_MPEG4VISUAL, new ActionOpenAsBit(w)));
            items.add(new Opener("TEST: Source Packet", FILE_TYPE.FT_TEST_SRC, new ActionOpenAsBit(w)));
            items.add(new Opener("TEST: Fixed Size Packet", FILE_TYPE.FT_TEST_FIXED, new ActionOpenAsBit(w)));
            items.add(new Opener("TEST: Marked Packet", FILE_TYPE.FT_TEST_MARKED, new ActionOpenAsBit(w)));

            for (Opener i : items) {
                i.getAction().putValue(Action.NAME, "Open");
            }
        }

        if (l instanceof LargePacketList && l.get(0) instanceof TSPacket) {
            items.add(new Opener("MPEG2 TS", FILE_TYPE.FT_MPEG2TS, new ActionOpenAsTS(w)));

            for (Opener i : items) {
                i.getAction().putValue(Action.NAME, "Open");
            }
        }

        return items;
    }
}
