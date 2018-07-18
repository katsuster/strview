package net.katsuster.strview.media;

import net.katsuster.strview.util.*;

/**
 * <p>
 * 数値の文字列表現を生成するためのユーティリティクラスです。
 * </p>
 */
public class NumFormatter {
    public static final String FORMAT_ADDRESS = "%6x.%d-%6x.%d" + ": ";
    public static final String FORMAT_ADDRESS_EMPTY = "                 " + "  ";
    public static final String FORMAT_NAME    = "%-32s" + ": ";
    public static final String FORMAT_TYPE    = "%-10s" + ": ";
    public static final String FORMAT_INDENT  = "  ";

    protected NumFormatter() {
        //do nothing
    }

    public static String indentDown(String name, int depth) {
        String tmp;
        int i;

        tmp = name;
        for (i = 0; i < depth; i++) {
            tmp = FORMAT_INDENT
                    + tmp.replaceAll("\n(.+)", "\n" + FORMAT_INDENT + "$1");
        }

        return tmp;
    }

    protected static String addressAndName(Range r, String type, String name) {
        if (r == null || r.getLength() == 0) {
            return String.format(FORMAT_ADDRESS_EMPTY + FORMAT_NAME + FORMAT_TYPE,
                    name, type);
        } else {
            return String.format(FORMAT_ADDRESS + FORMAT_NAME + FORMAT_TYPE,
                    r.getStart() >>> 3, r.getStart() & 7,
                    (r.getEnd() - 1) >>> 3, (r.getEnd() - 1) & 7,
                    name, type);
        }
    }

    public static String longToDecHexCaption(String name, long v, String caption) {
        StringBuilder sb = new StringBuilder();

        sb.append(addressAndName(null, "long", name));
        sb.append(String.format("0x%x(%d)", v, v));
        if (caption == null) {
            sb.append("\n");
        } else {
            sb.append(String.format("(%s)\n", caption));
        }

        return sb.toString();
    }

    public static String doubleToDecHexCaption(String name, double v, String caption) {
        StringBuilder sb = new StringBuilder();

        sb.append(addressAndName(null, "double", name));
        sb.append(String.format("%f", v));
        if (caption == null) {
            sb.append("\n");
        } else {
            sb.append(String.format("(%s)\n", caption));
        }

        return sb.toString();
    }

    public static String stringToDecHexCaption(String name, String v, String caption) {
        StringBuilder sb = new StringBuilder();

        sb.append(addressAndName(null, "String", name));
        sb.append(String.format("'%s'", v));
        if (caption == null) {
            sb.append("\n");
        } else {
            sb.append(String.format("(%s)\n", caption));
        }

        return sb.toString();
    }

    protected static boolean isCenter(long index, long interval, long width) {
        return (index % interval == (interval - 1)) &&
                (index % width != (width - 1));
    }
}
