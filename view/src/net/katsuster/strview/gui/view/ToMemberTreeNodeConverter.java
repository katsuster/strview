package net.katsuster.strview.gui.view;

import java.util.*;

import net.katsuster.strview.util.bit.*;
import net.katsuster.strview.media.bit.*;

/**
 * <p>
 * パケットを構成するデータをツリービューのノードに変換するコンバータです。
 * </p>
 */
public class ToMemberTreeNodeConverter extends BitStreamWriterAdapter {
    private Deque<MemberTreeNode> stack_node;
    private MemberTreeNode root;

    public ToMemberTreeNodeConverter() {
        this(new MemberTreeNode());
    }

    public ToMemberTreeNodeConverter(MemberTreeNode n) {
        stack_node = new ArrayDeque<>();
        root = n;
        stack_node.push(root);
    }

    @Override
    public void enterPacket(String name) {
        MemberTreeNode top = stack_node.peek();
        MemberTreeNode node = new MemberTreeNode(name);

        top.add(node);
        stack_node.push(node);
    }

    @Override
    public void leavePacket() {
        stack_node.pop();
    }

    @Override
    public void enterBlock(String name) {
        MemberTreeNode top = stack_node.peek();
        MemberTreeNode node = new MemberTreeNode(name);

        top.add(node);
        stack_node.push(node);
    }

    @Override
    public void leaveBlock() {
        stack_node.pop();
    }

    @Override
    public void mark(String name, String s, String desc) {
        MemberTreeNode top = stack_node.peek();

        top.add(new MemberTreeNode(s, name, desc));
    }

    @Override
    public void mark(String name, Number n, String desc) {
        MemberTreeNode top = stack_node.peek();

        top.add(new MemberTreeNode(n.toString(), name, desc));
    }

    @Override
    public void writeLong(int nbit, long val, String name, String desc) {
        MemberTreeNode top = stack_node.peek();

        top.add(new MemberTreeNode(Long.toString(val), name, desc));
    }

    @Override
    public void writeSInt(int nbit, SInt val, String desc) {
        MemberTreeNode top = stack_node.peek();

        top.add(new MemberTreeNode((Num)val, val.getName(), desc));
    }

    @Override
    public void writeUInt(int nbit, UInt val, String desc) {
        MemberTreeNode top = stack_node.peek();

        top.add(new MemberTreeNode((Num)val, val.getName(), desc));
    }

    @Override
    public void writeSIntR(int nbit, SIntR val, String desc) {
        MemberTreeNode top = stack_node.peek();

        top.add(new MemberTreeNode((Num)val, val.getName(), desc));
    }

    @Override
    public void writeUIntR(int nbit, UIntR val, String desc) {
        MemberTreeNode top = stack_node.peek();

        top.add(new MemberTreeNode((Num)val, val.getName(), desc));
    }

    @Override
    public void writeFloat32(int nbit, Float32 val, String desc) {
        MemberTreeNode top = stack_node.peek();

        top.add(new MemberTreeNode((Num)val, val.getName(), desc));
    }

    @Override
    public void writeFloat64(int nbit, Float64 val, String desc) {
        MemberTreeNode top = stack_node.peek();

        top.add(new MemberTreeNode((Num)val, val.getName(), desc));
    }

    @Override
    public void writeSF8_8(int nbit, SFixed8_8 val, String desc) {
        MemberTreeNode top = stack_node.peek();

        top.add(new MemberTreeNode((Num)val, val.getName(), desc));
    }

    @Override
    public void writeSF16_16(int nbit, SFixed16_16 val, String desc) {
        MemberTreeNode top = stack_node.peek();

        top.add(new MemberTreeNode((Num)val, val.getName(), desc));
    }

    @Override
    public void writeUF8_8(int nbit, UFixed8_8 val, String desc) {
        MemberTreeNode top = stack_node.peek();

        top.add(new MemberTreeNode((Num)val, val.getName(), desc));
    }

    @Override
    public void writeUF16_16(int nbit, UFixed16_16 val, String desc) {
        MemberTreeNode top = stack_node.peek();

        top.add(new MemberTreeNode((Num)val, val.getName(), desc));
    }

    @Override
    public void writeBitList(long nbit, LargeBitList val, String desc) {
        MemberTreeNode top = stack_node.peek();

        top.add(new MemberTreeNode(val, val.getName(), desc));
    }

    public MemberTreeNode getResult() {
        return root;
    }
}
