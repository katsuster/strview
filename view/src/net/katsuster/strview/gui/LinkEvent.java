package net.katsuster.strview.gui;

import java.util.*;

import net.katsuster.strview.util.*;

/**
 * <p>
 * データリンクの発生を表すイベントです。
 * </p>
 *
 * <p>
 * イベントの種類には、
 * </p>
 *
 * <ul>
 * <li>ノード選択</li>
 * <li>メンバ選択</li>
 * </ul>
 *
 * <p>
 * があります。
 * </p>
 */
public class LinkEvent extends EventObject {
    private Type eventType;
    private Range range;

    public LinkEvent(Object source, Type type, Range r) {
        super(source);

        eventType = type;
        range = r;
    }

    public Type getEventType() {
        return eventType;
    }

    public Range getRange() {
        return range;
    }

    public static class Type {
        public static LinkEvent.Type NODE   = new Type("NODE");
        public static LinkEvent.Type MEMBER = new Type("MEMBER");
        public static LinkEvent.Type JUMP   = new Type("JUMP");
        private String typeName;

        public Type(String name) {
            typeName = name;
        }

        public String toString() {
            return typeName;
        }
    }
}
