package net.katsuster.strview.media;

import net.katsuster.strview.io.*;

import net.katsuster.strview.util.*;

/**
 * <p>
 * Num クラスの文字列表現を生成するためのユーティリティクラスです。
 * </p>
 *
 * @author katsuhiro
 */
public class NumFormatter {
    public static final String FORMAT_ADDRESS = "%6x.%d-%6x.%d";
    public static final String FORMAT_NAME    = "%-32s";
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
        if (v.getLength() == 0) {
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
        return Integer.toString(((int)(v.getLength() - 1) >> 2) + 1);
    }

    public static String numToDecHex(String name, Num v) {
        String digits = numToDigits(v);

        return String.format(
                FORMAT_ADDRESS + ": " + FORMAT_NAME + ": "
                        + "0x%0" + digits + "x(%s)\n",
                v.getStart() >>> 3, v.getStart() & 7,
                (v.getEnd() - 1) >>> 3, (v.getEnd() - 1) & 7,
                name, v.getBitsValue(), v.toString());
    }

    public static String numToDecHexCaption(String name, Num v, String caption) {
        String digits = numToDigits(v);

        return String.format(
                FORMAT_ADDRESS + ": " + FORMAT_NAME + ": "
                        + "0x%0" + digits + "x(%s)(%s)\n",
                v.getStart() >>> 3, v.getStart() & 7,
                (v.getEnd() - 1) >>> 3, (v.getEnd() - 1) & 7,
                name, v.getBitsValue(), v.toString(), caption);
    }

    public static String longToDecHex(String name, long v) {
        return String.format(FORMAT_NAME + ": 0x%x(%d)\n",
                name, v, v);
    }

    public static String longToDecHexCaption(String name, long v, String caption) {
        return String.format(FORMAT_NAME + ": 0x%x(%d)(%s)\n",
                name, v, v, caption);
    }

    public static String intToDecHex(String name, int v) {
        return String.format(FORMAT_NAME + ": 0x%x(%d)\n",
                name, v, v);
    }

    public static String intToDecHexCaption(String name, int v, String caption) {
        return String.format(FORMAT_NAME + ": 0x%x(%d)(%s)\n",
                name, v, v, caption);
    }

    public static String stringToDecHex(String name, String v) {
        return String.format(FORMAT_NAME + ": '%s'\n",
                name, v);
    }

    public static String stringToDecHexCaption(String name, String v, String caption) {
        return String.format(FORMAT_NAME + ": '%s'(%s)\n",
                name, v, caption);
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
            pos = v.getStart();
            len_bit = v.getLength();
        }
        len_byte = len_bit >>> 3;
        len_show = Long.min(len_byte, 32);

        if (caption == null) {
            sb.append(String.format(
                    FORMAT_ADDRESS + ": " + FORMAT_NAME + ": %d[bytes]\n",
                    pos >>> 3, pos & 7,
                    (pos + len_bit - 1) >>> 3, (pos + len_bit - 1) & 7,
                    name, len_byte));
        } else {
            sb.append(String.format(
                    FORMAT_ADDRESS + ": " + FORMAT_NAME + ": %d[bytes](%s)\n",
                    pos >>> 3, pos & 7,
                    (pos + len_bit - 1) >>> 3, (pos + len_bit - 1) & 7,
                    name, len_byte, caption));
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
                    sb.append(String.format("%02x-", v.getPackedByte(i * 8, 8)));
                } else {
                    sb.append(String.format("%02x ", v.getPackedByte(i * 8, 8)));
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

    public static String byteListToHex(String name, LargeByteList v) {
        return byteListToHexCaption(name, v, null);
    }

    public static String byteListToHexCaption(String name, LargeByteList v,
                                             String caption) {
        StringBuilder sb = new StringBuilder();
        long i, t, w = 16;
        long pos, len_bit, len_byte, len_show;

        if (v == null) {
            pos = 0;
            len_bit = 0;
        } else {
            pos = v.getStart();
            len_bit = v.getLength() << 3;
        }
        len_byte = len_bit >>> 3;
        len_show = Long.min(len_byte, 32);

        if (caption == null) {
            sb.append(String.format(
                    FORMAT_ADDRESS + ": " + FORMAT_NAME + ": %d[bytes]\n",
                    pos >>> 3, pos & 7,
                    (pos + len_bit - 1) >>> 3, (pos + len_bit - 1) & 7,
                    name, len_byte));
        } else {
            sb.append(String.format(
                    FORMAT_ADDRESS + ": " + FORMAT_NAME + ": %d[bytes](%s)\n",
                    pos >>> 3, pos & 7,
                    (pos + len_bit - 1) >>> 3, (pos + len_bit - 1) & 7,
                    name, len_byte, caption));
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
                    sb.append(String.format("%02x-", v.get(i)));
                } else {
                    sb.append(String.format("%02x ", v.get(i)));
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
