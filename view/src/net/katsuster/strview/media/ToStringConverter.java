package net.katsuster.strview.media;

import net.katsuster.strview.util.*;

/**
 * <p>
 * パケットの各メンバを文字列に変換するコンバータクラスです。
 * </p>
 *
 * @author katsuhiro
 */
public class ToStringConverter extends PacketWriterAdapter<StringBuilder> {
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
    public void writeSInt(int nbit, SInt val, String name, String desc) {
        sb.append(NumFormatter.numToDecHexCaption(
                name, val, desc));
    }

    @Override
    public void writeUInt(int nbit, UInt val, String name, String desc) {
        sb.append(NumFormatter.numToDecHexCaption(
                name, val, desc));
    }

    @Override
    public void writeBitList(long nbit, LargeBitList val, String name, String desc) {
        sb.append(NumFormatter.bitListToHexCaption(
                name, val, desc));
    }

    @Override
    public LargeBitList convSubList(long nbit, LargeBitList val, String name, String desc) {
        sb.append(NumFormatter.bitListToHexCaption(
                name, val, desc));
        return val;
    }

    @Override
    public StringBuilder getResult() {
        return sb;
    }
}
