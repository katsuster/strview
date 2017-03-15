package net.katsuster.strview.media.flv;

import java.util.*;

import net.katsuster.strview.util.*;
import net.katsuster.strview.media.*;

/**
 * <p>
 * SCRIPTDATAECMAARRAY
 * </p>
 *
 * @author katsuhiro
 */
public class FLVScriptDataECMAArray extends FLVScriptData
        implements Cloneable {
    public UInt ecma_array_length;
    public List<FLVScriptDataObjectProperty> variables;
    //TODO: 仕様書の意味がわからない…。
    //list_terminator を読み出すと位置がずれてしまい、後続データが読めない。
    //FLVTagHeaderES の data_size で数えられていないし、
    //ストリーム中にデータも入っていない。
    //仕様書も 0, 0, '9' と書いてあるが、そんなデータはないし（間違っている？）
    //意味がわからん…。
    //YouTube のストリームがおかしいだけかもしれないので、
    //とりあえずコメントで残しておく。
    //public FLVScriptDataObjectEnd list_terminator;

    public FLVScriptDataECMAArray() {
        ecma_array_length = new UInt();
        variables = new ArrayList<>();
        //list_terminator = new FLVScriptDataObjectEnd();
    }

    @Override
    public FLVScriptDataECMAArray clone()
            throws CloneNotSupportedException {
        FLVScriptDataECMAArray obj =
                (FLVScriptDataECMAArray)super.clone();

        obj.ecma_array_length = (UInt)ecma_array_length.clone();

        obj.variables = new ArrayList<>();
        for (FLVScriptDataObjectProperty v : variables) {
            obj.variables.add(v.clone());
        }

        //obj.list_terminator = list_terminator.clone();

        return obj;
    }

    @Override
    public void read(PacketReader<?> c) {
        read(c, this);
    }

    public static void read(PacketReader<?> c,
                            FLVScriptDataECMAArray d) {
        c.enterBlock("SCRIPTDATAECMAARRAY");

        FLVScriptData.read(c, d);

        d.ecma_array_length = c.readUInt(32, d.ecma_array_length);

        d.variables = readObjectList(c, d.ecma_array_length.intValue(),
                d.variables, FLVScriptDataObjectProperty.class);

        //d.list_terminator.read(b);

        c.leaveBlock();
    }

    @Override
    public void write(PacketWriter<?> c) {
        write(c, this);
    }

    public static void write(PacketWriter<?> c,
                             FLVScriptDataECMAArray d) {
        c.enterBlock("SCRIPTDATAECMAARRAY");

        FLVScriptData.write(c, d);

        c.writeUInt(32, d.ecma_array_length, "ECMAArrayLength");

        writeObjectList(c, d.ecma_array_length.intValue(),
                d.variables, "Variables");

        //d.list_terminator.write(b);

        c.leaveBlock();
    }
}
