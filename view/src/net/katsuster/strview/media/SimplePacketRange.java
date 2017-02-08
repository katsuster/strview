package net.katsuster.strview.media;

/**
 * <p>
 * パケットの位置。
 * </p>
 *
 * @author katsuhiro
 */
public class SimplePacketRange extends SimpleBlockRange
        implements PacketRange, Cloneable {
    //パケットのヘッダの長さ（ビット単位）
    private long len_header;
    //パケットのフッタの長さ（ビット単位）
    private long len_footer;

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
     * 位置 0、長さ 0 のパケット位置を作成します。
     * </p>
     *
     * @param num パケットの番号
     */
    public SimplePacketRange(long num) {
        this(num, 0, 0, 0, 0);
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
     */
    public SimplePacketRange(long num, long addr, long len_h, long len_b, long len_f) {
        super(num, addr, len_h + len_b + len_f);

        len_header = len_h;
        len_footer = len_f;
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
                obj.getHeaderLength(), obj.getBodyLength(), obj.getFooterLength());
    }

    @Override
    public SimplePacketRange clone()
            throws CloneNotSupportedException {
        SimplePacketRange obj = (SimplePacketRange)super.clone();

        return obj;
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
}
