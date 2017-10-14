package net.katsuster.strview.media.m2v;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * MPEG2 Video quant matrix extension
 * </p>
 *
 * <p>
 * 参考規格
 * </p>
 * <ul>
 * <li>ITU-T H.262, ISO/IEC 13818-2:
 * Information technology - Generic coding of moving pictures and
 * associated audio information: Video</li>
 * </ul>
 *
 * @author katsuhiro
 */
public class M2VHeaderExtQuantMatrix extends M2VHeaderExt
        implements Cloneable {
    public UInt load_intra_quantiser_matrix;
    public LargeBitList intra_quantiser_matrix;
    public UInt load_non_intra_quantiser_matrix;
    public LargeBitList non_intra_quantiser_matrix;
    public UInt load_chroma_intra_quantiser_matrix;
    public LargeBitList chroma_intra_quantiser_matrix;
    public UInt load_chroma_non_intra_quantiser_matrix;
    public LargeBitList chroma_non_intra_quantiser_matrix;

    public M2VHeaderExtQuantMatrix() {
        load_intra_quantiser_matrix = new UInt();
        intra_quantiser_matrix = new SubLargeBitList();
        load_non_intra_quantiser_matrix = new UInt();
        non_intra_quantiser_matrix = new SubLargeBitList();
        load_chroma_intra_quantiser_matrix = new UInt();
        chroma_intra_quantiser_matrix = new SubLargeBitList();
        load_chroma_non_intra_quantiser_matrix = new UInt();
        chroma_non_intra_quantiser_matrix = new SubLargeBitList();
    }

    @Override
    public M2VHeaderExtQuantMatrix clone()
            throws CloneNotSupportedException {
        M2VHeaderExtQuantMatrix obj = (M2VHeaderExtQuantMatrix) super.clone();

        obj.load_intra_quantiser_matrix = (UInt)load_intra_quantiser_matrix.clone();
        obj.intra_quantiser_matrix = (LargeBitList)intra_quantiser_matrix.clone();
        obj.load_non_intra_quantiser_matrix = (UInt)load_non_intra_quantiser_matrix.clone();
        obj.non_intra_quantiser_matrix = (LargeBitList)non_intra_quantiser_matrix.clone();
        obj.load_chroma_intra_quantiser_matrix = (UInt)load_chroma_intra_quantiser_matrix.clone();
        obj.chroma_intra_quantiser_matrix = (LargeBitList)chroma_intra_quantiser_matrix.clone();
        obj.load_chroma_non_intra_quantiser_matrix = (UInt)load_chroma_non_intra_quantiser_matrix.clone();
        obj.chroma_non_intra_quantiser_matrix = (LargeBitList)chroma_non_intra_quantiser_matrix.clone();

        return obj;
    }

    @Override
    public void read(StreamReader<?> c) {
        read(c, this);
    }

    public static void read(StreamReader<?> c,
                            M2VHeaderExtQuantMatrix d) {
        c.enterBlock("M2V quant_matrix_extension()");

        M2VHeaderExt.read(c, d);

        d.load_intra_quantiser_matrix = c.readUInt( 1, d.load_intra_quantiser_matrix);
        if (d.load_intra_quantiser_matrix.intValue() == 1) {
            d.intra_quantiser_matrix = c.readBitList(64 << 3, d.intra_quantiser_matrix);
        }

        d.load_non_intra_quantiser_matrix = c.readUInt( 1, d.load_non_intra_quantiser_matrix);
        if (d.load_non_intra_quantiser_matrix.intValue() == 1) {
            d.non_intra_quantiser_matrix = c.readBitList(64 << 3, d.non_intra_quantiser_matrix);
        }

        d.load_chroma_intra_quantiser_matrix = c.readUInt( 1, d.load_chroma_intra_quantiser_matrix);
        if (d.load_chroma_intra_quantiser_matrix.intValue() == 1) {
            d.chroma_intra_quantiser_matrix = c.readBitList(64 << 3, d.chroma_intra_quantiser_matrix);
        }

        d.load_chroma_non_intra_quantiser_matrix = c.readUInt( 1, d.load_chroma_non_intra_quantiser_matrix);
        if (d.load_chroma_non_intra_quantiser_matrix.intValue() == 1) {
            d.chroma_non_intra_quantiser_matrix = c.readBitList(64 << 3, d.chroma_non_intra_quantiser_matrix);
        }

        c.leaveBlock();
    }

    @Override
    public void write(StreamWriter<?> c) {
        write(c, this);
    }

    public static void write(StreamWriter<?> c,
                             M2VHeaderExtQuantMatrix d) {
        c.enterBlock("M2V quant_matrix_extension()");

        M2VHeaderExt.write(c, d);

        c.writeUInt( 1, d.load_intra_quantiser_matrix, "load_intra_quantiser_matrix");
        if (d.load_intra_quantiser_matrix.intValue() == 1) {
            c.writeBitList(64 << 3, d.intra_quantiser_matrix, "intra_quantiser_matrix");
        }

        c.writeUInt( 1, d.load_non_intra_quantiser_matrix, "load_non_intra_quantiser_matrix");
        if (d.load_non_intra_quantiser_matrix.intValue() == 1) {
            c.writeBitList(64 << 3, d.non_intra_quantiser_matrix, "non_intra_quantiser_matrix");
        }

        c.writeUInt( 1, d.load_chroma_intra_quantiser_matrix, "load_chroma_intra_quantiser_matrix");
        if (d.load_chroma_intra_quantiser_matrix.intValue() == 1) {
            c.writeBitList(64 << 3, d.chroma_intra_quantiser_matrix, "chroma_intra_quantiser_matrix");
        }

        c.writeUInt( 1, d.load_chroma_non_intra_quantiser_matrix, "load_chroma_non_intra_quantiser_matrix");
        if (d.load_chroma_non_intra_quantiser_matrix.intValue() == 1) {
            c.writeBitList(64 << 3, d.chroma_non_intra_quantiser_matrix, "chroma_non_intra_quantiser_matrix");
        }

        c.leaveBlock();
    }
}
