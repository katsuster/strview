package net.katsuster.strview.util;

/**
 * <p>
 * デバッグ用ユーティリティ関数群を定義するためのクラスです。
 * </p>
 *
 * @author katsuhiro
 */
public class DebugInfo {
    protected DebugInfo() {
        //do nothing
    }

    public static void printFunctionName(Object o) {
        System.err.print("Exception in "
                + "'" + o.getClass().getName() + "."
                + DebugInfo.getFunctionName(1) + "()': ");
    }

    public static String getFunctionName(int i) {
        return Thread.currentThread().getStackTrace()[2 + i].getMethodName();
    }
}
