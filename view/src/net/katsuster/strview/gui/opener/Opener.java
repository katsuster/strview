package net.katsuster.strview.gui.opener;

import javax.swing.*;

public class Opener {
    private String name;
    private FILE_TYPE type;
    private Action action;

    public Opener(String n, FILE_TYPE t, Action a) {
        name = n;
        type = t;
        action = a;
    }

    public String getName() {
        return name;
    }

    public FILE_TYPE getType() {
        return type;
    }

    public Action getAction() {
        return action;
    }

    @Override
    public String toString() {
        return name;
    }

    public enum FILE_TYPE {
        FT_UNKNOWN,
        FT_AUTO,

        FT_ASF,
        FT_FLV,
        FT_MATROSKA,
        FT_MPEG2PS,
        FT_MPEG2TS,
        FT_MPEG4,
        FT_RIFF,
        FT_RMFF,
        FT_MPEG2VIDEO,
        FT_MPEG4VISUAL,

        //For test
        FT_TEST_SRC,
        FT_TEST_FIXED,
        FT_TEST_MARKED,
    }
}