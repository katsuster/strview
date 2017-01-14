package net.katsuster.strview.media;

import net.katsuster.strview.util.*;

/**
 * <p>
 * 何もしないコンバータクラスです。
 * </p>
 *
 * <p>
 * 下記の特徴を持ちます。
 * </p>
 *
 * <ul>
 *     <li>位置は常に 0</li>
 *     <li>マークは全て無視する</li>
 *     <li>変換対象は全て無視する</li>
 *     <li>結果は常に null を返す</li>
 * </ul>
 *
 * @author katsuhiro
 */
public class PacketConverterAdapter<T> implements PacketConverter<T> {
    public PacketConverterAdapter() {
        //do nothing
    }

    @Override
    public void doInit() {
        //do nothing
    }

    @Override
    public void doFinal() {
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
    public void mark(String name, String s) {
        mark(name, s, null);
    }

    @Override
    public void mark(String name, String s, String desc) {
        //do nothing
    }

    @Override
    public void mark(String name, Number n) {
        mark(name, n, null);
    }

    @Override
    public void mark(String name, Number n, String desc) {
        //do nothing
    }

    @Override
    public long position() {
        return 0;
    }

    @Override
    public void position(long p) {
        //do nothing
    }

    @Override
    public SInt convSInt(int nbit, SInt val, String name) {
        return convSInt(nbit, val, name, null);
    }

    @Override
    public SInt convSInt(int nbit, SInt val, String name, String desc) {
        return val;
    }

    @Override
    public UInt convUInt(int nbit, UInt val, String name) {
        return convUInt(nbit, val, name, null);
    }

    @Override
    public UInt convUInt(int nbit, UInt val, String name, String desc) {
        return val;
    }

    @Override
    public LargeBitList convBitList(long nbit, LargeBitList val, String name) {
        return convBitList(nbit, val, name, null);
    }

    @Override
    public LargeBitList convBitList(long nbit, LargeBitList val, String name, String desc) {
        return val;
    }

    @Override
    public T getResult() {
        return null;
    }
}
