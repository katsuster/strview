package net.katsuster.strview.media.asf;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * ASF (Advanced Systems Format) Object
 * </p>
 *
 * <p>
 * 参考規格
 * </p>
 * <ul>
 * <li>Advanced Systems Format (ASF) Specification: Revision 01.20.06</li>
 * </ul>
 */
public class ASFHeader<T extends LargeList<?>>
        extends BlockAdapter<T>
        implements Cloneable {
    public ASFGUID<T> object_id;
    public UIntR object_size;

    public ASFHeader() {
        object_id = new ASFGUID<>("Object ID");
        object_size = new UIntR("Object Size");
    }

    @Override
    public ASFHeader<T> clone()
            throws CloneNotSupportedException {
        ASFHeader<T> obj = (ASFHeader<T>)super.clone();

        obj.object_id = object_id.clone();
        obj.object_size = (UIntR)object_size.clone();

        return obj;
    }

    @Override
    public String getTypeName() {
        return "Header Object";
    }

    /**
     * <p>
     * オブジェクト本体に別のオブジェクトを含められるかどうかを返します。
     * </p>
     *
     * @return オブジェクト本体に別のオブジェクトを含められる場合は true、
     * 含められない場合は false
     */
    public boolean isRecursive() {
        return false;
    }

    @Override
    public void read(StreamReader<?> c) {
        read(c, this);
    }

    public static void read(StreamReader<?> c,
                            ASFHeader d) {
        c.enterBlock(d);

        c.mark("Object ID", "");
        d.object_id.read(c);
        d.object_size = c.readUIntR(64, d.object_size);

        c.leaveBlock();
    }

    @Override
    public void write(StreamWriter<?> c) {
        write(c, this);
    }

    public static void write(StreamWriter<?> c,
                             ASFHeader d) {
        c.enterBlock(d);

        c.mark("Object ID", "");
        d.object_id.write(c);
        c.writeUIntR(64, d.object_size);

        c.leaveBlock();
    }

    /**
     * <p>
     * 100ns 単位の時間を秒に変換した文字列を返します。
     * </p>
     *
     * @param v 100ns を 1 とした時間
     * @return 秒に変換した文字列
     */
    public static String get100nsDurationName(long v) {
        float sec;

        //100 ns
        sec  = (float)v / 10000000;

        return String.format("%3.3f[s]", sec);
    }

    /**
     * <p>
     * WCHAR 文字列（UTF-16LE）を文字列に変換します。
     * </p>
     *
     * @param v WCHAR 文字列を格納したビットリスト
     * @return 文字列
     */
    public static String getWcharName(LargeBitList v) {
        return AbstractBlock.getArrayName(v, "UTF-16LE");
    }
}
