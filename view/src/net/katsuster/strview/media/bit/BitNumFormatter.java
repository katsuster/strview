package net.katsuster.strview.media.bit;

import net.katsuster.strview.media.*;
import net.katsuster.strview.util.*;
import net.katsuster.strview.util.bit.*;

/**
 * <p>
 * Num クラスの文字列表現を生成するためのユーティリティクラスです。
 * </p>
 */
public class BitNumFormatter extends NumFormatter {
    protected BitNumFormatter() {
        //do nothing
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

    public static String numToDecHexCaption(String name, Num v, String caption) {
        String digits = numToDigits(v);
        StringBuilder sb = new StringBuilder();

        //TODO: 参照先が非連続の場合の対応
        sb.append(addressAndName(new SimpleRange(v.getRange().getStart(), v.length()),
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
            pos = v.getRange().getStart();
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
