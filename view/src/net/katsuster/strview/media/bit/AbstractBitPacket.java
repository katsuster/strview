package net.katsuster.strview.media.bit;

import net.katsuster.strview.io.*;
import net.katsuster.strview.media.*;
import net.katsuster.strview.util.*;

/**
 * <p>
 * ヘッダ、本体、フッタの 3要素からなる、
 * 小さな単位に分割されたビットデータの塊（パケットなど）を表す、
 * 抽象クラスです。
 * </p>
 */
public abstract class AbstractBitPacket
        extends AbstractPacket<Boolean> {
    /**
     * <p>
     * 空のヘッダを持ち、存在する範囲が未定義のパケットを作成します。
     * </p>
     */
    public AbstractBitPacket() {
        this(null, null);
    }

    /**
     * <p>
     * 空のヘッダを持ち、存在する範囲が定義されたパケットを作成します。
     * </p>
     *
     * @param pr パケットが存在する範囲
     */
    public AbstractBitPacket(PacketRange<LargeList<Boolean>> pr) {
        this(pr, null);
    }

    /**
     * <p>
     * 指定されたヘッダを持ち、存在する範囲が未定義のパケットを作成します。
     * </p>
     *
     * @param h パケットヘッダ
     */
    public AbstractBitPacket(Block<Boolean> h) {
        this(null, h);
    }

    /**
     * <p>
     * 指定されたヘッダを持ち、存在する範囲が定義されたパケットを作成します。
     * </p>
     *
     * @param pr パケットが存在する範囲
     * @param h パケットヘッダ
     */
    public AbstractBitPacket(PacketRange<LargeList<Boolean>> pr, Block<Boolean> h) {
        super(pr, h);

        setBody(new DummyBitList("body"));
        setRawPacket(new DummyBitList("raw_packet"));
    }

    @Override
    public Object clone()
            throws CloneNotSupportedException {
        AbstractBitPacket obj = (AbstractBitPacket)super.clone();

        return obj;
    }

    @Override
    protected void readHeader(StreamReader<Boolean> c) {
        readHeaderBits((BitStreamReader)c);
    }

    /**
     * <p>
     * ビットリストからパケットのヘッダを読み出します。
     * </p>
     *
     * @param c リストの読み出しオブジェクト
     */
    protected abstract void readHeaderBits(BitStreamReader c);

    @Override
    protected void readBody(StreamReader<Boolean> c) {
        readBodyBits((BitStreamReader)c);
    }

    /**
     * <p>
     * ビットリストからパケットの本体を読み出します。
     * </p>
     *
     * @param c リストの読み出しオブジェクト
     */
    protected abstract void readBodyBits(BitStreamReader c);

    @Override
    protected void readFooter(StreamReader<Boolean> c) {
        readFooterBits((BitStreamReader)c);
    }

    /**
     * <p>
     * ビットリストからパケットのフッタを読み出します。
     * </p>
     *
     * @param c リストの読み出しオブジェクト
     */
    protected abstract void readFooterBits(BitStreamReader c);

    @Override
    protected void writeHeader(StreamWriter<Boolean> c) {
        writeHeaderBits((BitStreamWriter)c);
    }

    /**
     * <p>
     * パケットのヘッダを書き込みます。
     * </p>
     *
     * @param c リストの書き込みオブジェクト
     */
    protected abstract void writeHeaderBits(BitStreamWriter c);

    @Override
    protected void writeBody(StreamWriter<Boolean> c) {
        writeBodyBits((BitStreamWriter)c);
    }

    /**
     * <p>
     * パケットの本体を書き込みます。
     * </p>
     *
     * @param c リストの書き込みオブジェクト
     */
    protected abstract void writeBodyBits(BitStreamWriter c);

    @Override
    protected void writeFooter(StreamWriter<Boolean> c) {
        writeBodyBits((BitStreamWriter)c);
    }

    /**
     * <p>
     * パケットのフッタを書き込みます。
     * </p>
     *
     * @param c リストの書き込みオブジェクト
     */
    protected abstract void writeFooterBits(BitStreamWriter c);

    @Override
    public String toString() {
        BitToStringConverter c = new BitToStringConverter(new StringBuilder());

        write(c);

        return c.getResult().toString();
    }
}
