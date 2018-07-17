package net.katsuster.strview.media;

import net.katsuster.strview.util.*;

/**
 * <p>
 * パケットの各メンバを文字列に変換するコンバータクラスです。
 * </p>
 */
public class ToStringConverter<T> extends StreamWriterAdapter<T> {
    private StringBuilder sb;

    public ToStringConverter() {
        this(new StringBuilder());
    }

    public ToStringConverter(StringBuilder s) {
        sb = s;
    }

    @Override
    public void mark(String name, String s, String desc) {
        sb.append(NumFormatter.stringToDecHexCaption(
                name, s, desc));
    }

    @Override
    public void mark(String name, Number n, String desc) {
        sb.append(NumFormatter.stringToDecHexCaption(
                name, n.toString(), desc));
    }

    @Override
    public void write(T val, String desc) {
        sb.append(NumFormatter.stringToDecHexCaption(
                val.getClass().getTypeName(), val.toString(), desc));
        position(position() + 1);
    }

    @Override
    public void writeList(long n, LargeList<T> val, String desc) {
        sb.append(NumFormatter.stringToDecHexCaption(
                val.getClass().getTypeName(), val.toString(), desc));
        position(position() + n);
    }

    public StringBuilder getResult() {
        return sb;
    }
}
