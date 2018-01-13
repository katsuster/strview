package net.katsuster.strview.media;

import net.katsuster.strview.util.*;

/**
 * <p>
 * Num クラスの文字列表現を生成するためのユーティリティクラスです。
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

    /**
     * <p>
     * 16進数で表現した時の最低桁数を文字列として返します。
     * </p>
     *
     * <p>
     * String.format メソッドなどのフォーマット文字列として、
     * 使うことを想定しています。
     * </p>
     *
     * @param v 数値
     * @return 16進数で表現した時の最低桁数
     */
    public static String numToDigits(Num v) {
        if (v.length() == 0) {
            return "1";
        }

		/*   | return | (length() - 1) >> 2 | length() >> 2
		 * --+--------+---------------------+---------------
		 * 0 | "1"    | (error case)        | 0
		 * 1 | "1"    | 0                   | 0
		 * 2 | "1"    | 0                   | 0
		 * 3 | "1"    | 0                   | 0
		 * 4 | "1"    | 0                   | 1
		 * 5 | "2"    | 1                   | 1
		 * 6 | "2"    | 1                   | 1
		 * 7 | "2"    | 1                   | 1
		 * 8 | "2"    | 1                   | 2
		 * 9 | "3"    | 2                   | 2
		 * 10| "3"    | 2                   | 2
		 */
        return Integer.toString(((int)(v.length() - 1) >> 2) + 1);
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

    public static String numToDecHexCaption(String name, Num v, String caption) {
        String digits = numToDigits(v);
        StringBuilder sb = new StringBuilder();

        //TODO: 参照先が非連続の場合の対応
        sb.append(addressAndName(new SimpleRange(v.getSourceStart(), v.length()),
                v.getTypeName(), name));
        sb.append(String.format("0x%0" + digits + "x(0x%0" + digits + "x, %s)",
                v.getRaw(), v.getValue(), v.toString()));
        if (caption == null) {
            sb.append("\n");
        } else {
            sb.append(String.format("(%s)\n", caption));
        }

        return sb.toString();
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

    public static String bitListToHex(String name, LargeBitList v) {
        return bitListToHexCaption(name, v, null);
    }

    public static String bitListToHexCaption(String name, LargeBitList v,
                                             String caption) {
        StringBuilder sb = new StringBuilder();
        long i, t, w = 16;
        long pos, len_bit, len_byte, len_show;

        if (v == null) {
            pos = 0;
            len_bit = 0;
        } else {
            pos = v.getSourceStart();
            len_bit = v.length();
        }
        len_byte = len_bit >>> 3;
        len_show = Math.min(len_byte, 32);

        sb.append(addressAndName(new SimpleRange(pos, len_bit),
                v.getTypeName(), name));
        if (caption == null) {
            sb.append(String.format("%d[bytes]\n",
                    len_byte));
        } else {
            sb.append(String.format("%d[bytes](%s)\n",
                    len_byte, caption));
        }

        if (len_show == 0) {
            sb.append(FORMAT_INDENT);
            sb.append("(empty)\n");
        }

        i = 0;
        while (i < len_show) {
            sb.append(FORMAT_INDENT);
            sb.append(String.format("%04x: ", i));

            t = Math.min(i + w, len_show);
            for (; i < t; i++) {
                if (isCenter(i, 8, w)) {
                    sb.append(String.format("%02x-", v.getPackedLong(i * 8, 8)));
                } else {
                    sb.append(String.format("%02x ", v.getPackedLong(i * 8, 8)));
                }
            }

            sb.append("\n");
        }
        if (len_byte != len_show) {
            sb.append(FORMAT_INDENT);
            sb.append("...\n");
        }

        return sb.toString();
    }
}
