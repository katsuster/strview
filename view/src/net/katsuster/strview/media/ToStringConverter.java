package net.katsuster.strview.media;

import net.katsuster.strview.util.*;
import net.katsuster.strview.io.*;

/**
 * <p>
 * パケットの各メンバを文字列に変換するコンバータクラスです。
 * </p>
 *
 * @author katsuhiro
 */
public class ToStringConverter extends PacketConverterAdapter<String> {
    private StringBuilder sb;
    private String result;

    public ToStringConverter() {
        sb = new StringBuilder();
        result = sb.toString();
    }

    @Override
    public void doInit() {
        sb.delete(0, sb.length());
        result = sb.toString();
    }

    @Override
    public void doFinal() {
        result = sb.toString();
    }

    @Override
    public void enterPacket(String name) {
        // TODO Auto-generated method stub
    }

    @Override
    public void leavePacket() {
        // TODO Auto-generated method stub
    }

    @Override
    public void enterBlock(String name) {
        // TODO Auto-generated method stub
    }

    @Override
    public void leaveBlock() {
        // TODO Auto-generated method stub
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
    public SInt convSInt(int nbit, SInt val, String name, String desc) {
        sb.append(NumFormatter.numToDecHexCaption(
                name, val, desc));
        return val;
    }

    @Override
    public UInt convUInt(int nbit, UInt val, String name, String desc) {
        sb.append(NumFormatter.numToDecHexCaption(
                name, val, desc));
        return val;
    }

    @Override
    public LargeBitList convBitList(long nbit, LargeBitList val, String name, String desc) {
        sb.append(NumFormatter.bitListToHexCaption(
                name, val, desc));
        return val;
    }

    @Override
    public String getResult() {
        return result;
    }
}
