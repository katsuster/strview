package net.katsuster.strview.media.bit;

import net.katsuster.strview.util.*;
import net.katsuster.strview.util.bit.*;

/**
 * <p>
 * 何もしないリストへの書き込みクラスです。
 * </p>
 *
 * <p>
 * 下記の特徴を持ちます。
 * </p>
 *
 * <ul>
 *     <li>無限に書き込み可能（hasNext が常にtrue）</li>
 *     <li>マークは全て無視する</li>
 *     <li>何も書き込まないが、位置は進む</li>
 * </ul>
 */
public class BitStreamWriterAdapter extends AbstractBitStreamWriter
        implements BitStreamWriter {
    public BitStreamWriterAdapter() {
        //do nothing
    }

    @Override
    public void enterPacket(String name) {
        //do nothing
    }

    @Override
    public void leavePacket() {
        //do nothing
    }

    @Override
    public void enterBlock(String name) {
        //do nothing
    }

    @Override
    public void leaveBlock() {
        //do nothing
    }

    @Override
    public void mark(String name, String s, String desc) {
        //do nothing
    }

    @Override
    public void mark(String name, Number n, String desc) {
        //do nothing
    }

    @Override
    public boolean hasNext(long n) {
        return true;
    }

    @Override
    public void write(Boolean val, String desc) {
        position(position() + 1);
    }

    @Override
    public void writeList(long n, LargeList<Boolean> val, String desc) {
        position(position() + n);
    }

    @Override
    public void writeLong(int nbit, long val, String name, String desc) {
        position(position() + nbit);
    }

    @Override
    public void writeSInt(int nbit, SInt val, String desc) {
        position(position() + nbit);
    }

    @Override
    public void writeUInt(int nbit, UInt val, String desc) {
        position(position() + nbit);
    }

    @Override
    public void writeSIntR(int nbit, SIntR val, String desc) {
        position(position() + nbit);
    }

    @Override
    public void writeUIntR(int nbit, UIntR val, String desc) {
        position(position() + nbit);
    }

    @Override
    public void writeFloat32(int nbit, Float32 val, String desc) {
        position(position() + nbit);
    }

    @Override
    public void writeFloat64(int nbit, Float64 val, String desc) {
        position(position() + nbit);
    }

    @Override
    public void writeSF8_8(int nbit, SFixed8_8 val, String desc) {
        position(position() + nbit);
    }

    @Override
    public void writeSF16_16(int nbit, SFixed16_16 val, String desc) {
        position(position() + nbit);
    }

    @Override
    public void writeUF8_8(int nbit, UFixed8_8 val, String desc) {
        position(position() + nbit);
    }

    @Override
    public void writeUF16_16(int nbit, UFixed16_16 val, String desc) {
        position(position() + nbit);
    }

    @Override
    public void writeBitList(long nbit, LargeBitList val, String desc) {
        position(position() + nbit);
    }
}
