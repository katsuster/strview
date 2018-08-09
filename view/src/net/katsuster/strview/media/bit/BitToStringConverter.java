package net.katsuster.strview.media.bit;

import net.katsuster.strview.media.*;
import net.katsuster.strview.util.bit.*;

/**
 * <p>
 * パケットの各メンバを文字列に変換するコンバータクラスです。
 * </p>
 */
public class BitToStringConverter extends BitStreamWriterAdapter {
    private StringBuilder sb;

    public BitToStringConverter() {
        this(new StringBuilder());
    }

    public BitToStringConverter(StringBuilder s) {
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
    public void writeLong(int nbit, long val, String name, String desc) {
        sb.append(NumFormatter.longToDecHexCaption(
                name, val, desc));
        position(position() + nbit);
    }

    @Override
    public void writeSInt(int nbit, SInt val, String desc) {
        sb.append(BitNumFormatter.numToDecHexCaption(
                val.getName(), val, desc));
        position(position() + nbit);
    }

    @Override
    public void writeUInt(int nbit, UInt val, String desc) {
        sb.append(BitNumFormatter.numToDecHexCaption(
                val.getName(), val, desc));
        position(position() + nbit);
    }

    @Override
    public void writeSIntR(int nbit, SIntR val, String desc) {
        sb.append(BitNumFormatter.numToDecHexCaption(
                val.getName(), val, desc));
        position(position() + nbit);
    }

    @Override
    public void writeUIntR(int nbit, UIntR val, String desc) {
        sb.append(BitNumFormatter.numToDecHexCaption(
                val.getName(), val, desc));
        position(position() + nbit);
    }

    @Override
    public void writeFloat32(int nbit, Float32 val, String desc) {
        sb.append(BitNumFormatter.numToDecHexCaption(
                val.getName(), val, desc));
        position(position() + nbit);
    }

    @Override
    public void writeFloat64(int nbit, Float64 val, String desc) {
        sb.append(BitNumFormatter.numToDecHexCaption(
                val.getName(), val, desc));
        position(position() + nbit);
    }

    @Override
    public void writeSF8_8(int nbit, SFixed8_8 val, String desc) {
        sb.append(BitNumFormatter.numToDecHexCaption(
                val.getName(), val, desc));
        position(position() + nbit);
    }

    @Override
    public void writeSF16_16(int nbit, SFixed16_16 val, String desc) {
        sb.append(BitNumFormatter.numToDecHexCaption(
                val.getName(), val, desc));
        position(position() + nbit);
    }

    @Override
    public void writeUF8_8(int nbit, UFixed8_8 val, String desc) {
        sb.append(BitNumFormatter.numToDecHexCaption(
                val.getName(), val, desc));
        position(position() + nbit);
    }

    @Override
    public void writeUF16_16(int nbit, UFixed16_16 val, String desc) {
        sb.append(BitNumFormatter.numToDecHexCaption(
                val.getName(), val, desc));
        position(position() + nbit);
    }

    @Override
    public void writeBitList(long nbit, LargeBitList val, String desc) {
        sb.append(BitNumFormatter.bitListToHexCaption(
                val.getName(), val, desc));
        position(position() + nbit);
    }

    public StringBuilder getResult() {
        return sb;
    }
}
