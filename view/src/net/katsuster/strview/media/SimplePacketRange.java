package net.katsuster.strview.media;

import java.util.*;

import net.katsuster.strview.util.*;

/**
 * <p>
 * パケットの位置。
 * </p>
 *
 * @author katsuhiro
 */
public class SimplePacketRange extends SimpleRange
        implements PacketRange, Cloneable {
    //パケットの通し番号
    private long number;
    //パケットのヘッダの長さ（ビット単位）
    private long len_header;
    //パケットのフッタの長さ（ビット単位）
    private long len_footer;
    //パケット本体に別のパケットを含められるか否か
    private boolean f_recursive;

    //パケットの親
    private PacketRange parent;
    //パケットの子供
    private List<PacketRange> children;

    /**
     * <p>
     * 番号 0、位置 0、長さ 0 のパケット位置を作成します。
     * </p>
     */
    public SimplePacketRange() {
        this(0, 0, 0, 0, 0);
    }

    /**
     * <p>
     * 長さ 0 のパケット位置を作成します。
     * </p>
     *
     * @param num パケットの番号
     * @param addr パケットの位置
     */
    public SimplePacketRange(long num, long addr) {
        this(num, addr, 0, 0, 0);
    }

    public SimplePacketRange(long num, long addr, long len_h, long len_b, long len_f) {
        this(num, addr, len_h, len_b, len_f, null);
    }

    /**
     * <p>
     * パケット位置を作成します。
     * </p>
     *
     * @param num パケットの番号
     * @param addr パケットの位置
     * @param len_h パケットヘッダのサイズ
     * @param len_b パケットペイロードのサイズ
     * @param len_f パケットフッタのサイズ
     * @param pp 親パケット
     */
    public SimplePacketRange(long num, long addr, long len_h, long len_b, long len_f, PacketRange pp) {
        super(addr, len_h + len_b + len_f);

        len_header = len_h;
        len_footer = len_f;
        parent = pp;
        children = new ArrayList<>();
    }

    /**
     * <p>
     * パケット位置を作成します。
     * </p>
     *
     * @param obj パケットの位置
     */
    public SimplePacketRange(PacketRange obj) {
        this(obj.getNumber(), obj.getStart(),
                obj.getHeaderLength(), obj.getBodyLength(), obj.getFooterLength(),
                obj.getParentNode());
    }

    @Override
    public SimplePacketRange clone()
            throws CloneNotSupportedException {
        SimplePacketRange obj = (SimplePacketRange)super.clone();

        obj.children = new ArrayList<>();
        for (int i = 0; i < children.size(); i++) {
            obj.children.add(children.get(i));
        }

        return obj;
    }

    @Override
    public long getNumber() {
        return number;
    }

    @Override
    public void setNumber(long num) {
        number = num;
    }

    @Override
    public long getBodyAddress() {
        return getStart() + getHeaderLength();
    }

    @Override
    public long getFooterAddress() {
        return getBodyAddress() + getBodyLength();
    }

    @Override
    public long getHeaderLength() {
        return len_header;
    }

    @Override
    public void setHeaderLength(long len) {
        len_header = len;
    }

    @Override
    public long getBodyLength() {
        return getLength() - getHeaderLength() - getFooterLength();
    }

    @Override
    public void setBodyLength(long len) {
        setLength(len + getHeaderLength() + getFooterLength());
    }

    @Override
    public long getFooterLength() {
        return len_footer;
    }

    @Override
    public void setFooterLength(long len) {
        len_footer = len;
    }

    @Override
    public boolean getRecursive() {
        return f_recursive;
    }

    @Override
    public void setRecursive(boolean r) {
        f_recursive = r;
    }

    @Override
    public int getLevel() {
        int level;
        PacketRange p = getParentNode();

        for (level = 0; p != null; level++) {
            p = p.getParentNode();
        }

        return level;
    }

    @Override
    public PacketRange appendChild(PacketRange newChild) {
        if (newChild == null) {
            throw new IllegalArgumentException(
                    "newChild is null.");
        }

        for (int i = 0; i < children.size(); i++) {
            if (children.get(i).getNumber() == newChild.getNumber()) {
                newChild.setParentNode(this);
                return null;
            }
        }

        children.add(newChild);
        newChild.setParentNode(this);

        return newChild;
    }

    @Override
    public PacketRange removeChild(PacketRange oldChild) {
        boolean result;

        if (oldChild == null) {
            throw new IllegalArgumentException(
                    "oldChild is null.");
        }

        result = children.remove(oldChild);
        if (result) {
            oldChild.setParentNode(null);

            return oldChild;
        } else {
            return null;
        }
    }

    @Override
    public PacketRange insertBefore(PacketRange newChild, PacketRange refChild) {
        int index;

        if (newChild == null || refChild == null) {
            throw new IllegalArgumentException(
                    "newChild or refChild is null.");
        }

        index = children.indexOf(refChild);
        if (index == -1) {
            return null;
        }

        children.add(index, newChild);
        newChild.setParentNode(this);

        return newChild;
    }

    @Override
    public PacketRange replaceChild(PacketRange newChild, PacketRange oldChild) {
        int index;

        if (newChild == null || oldChild == null) {
            throw new IllegalArgumentException(
                    "newChild or oldChild is null.");
        }

        index = children.indexOf(oldChild);
        if (index == -1) {
            return null;
        }

        children.set(index, newChild);
        oldChild.setParentNode(null);
        newChild.setParentNode(this);

        return newChild;
    }

    @Override
    public PacketRange getParentNode() {
        return parent;
    }

    @Override
    public void setParentNode(PacketRange p) {
        if (p == this) {
            return;
        }

        parent = p;
    }

    @Override
    public PacketRange getPreviousSibling() {
        List<PacketRange> l;
        int index;

        if (getParentNode() == null) {
            return null;
        }

        l = getParentNode().getChildNodes();
        index = l.indexOf(this);
        if (index == -1 || index <= 0) {
            return null;
        }

        return l.get(index - 1);
    }

    @Override
    public PacketRange getNextSibling() {
        List<PacketRange> l;
        int index;

        if (getParentNode() == null) {
            return null;
        }

        l = getParentNode().getChildNodes();
        index = getParentNode().getChildNodes().indexOf(this);
        if ((index == -1) || (l.size() - 1 <= index)) {
            return null;
        }

        return getParentNode().getChildNodes().get(index + 1);
    }

    @Override
    public boolean hasChildNodes() {
        return !children.isEmpty();
    }

    @Override
    public List<PacketRange> getChildNodes() {
        return children;
    }

    @Override
    public PacketRange getFirstChild() {
        if (children.isEmpty()) {
            return null;
        }

        return children.get(0);
    }

    @Override
    public PacketRange getLastChild() {
        if (children.isEmpty()) {
            return null;
        }

        return children.get(children.size() - 1);
    }

    @Override
    public int getChildCounts() {
        return children.size();
    }

    @Override
    public PacketRange getChild(int index) {
        return children.get(index);
    }

    @Override
    public String toString() {
        return String.format("num:%d, %s",
                getNumber(), super.toString());
    }
}
