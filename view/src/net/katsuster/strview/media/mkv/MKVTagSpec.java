package net.katsuster.strview.media.mkv;

/**
 * <p>
 * Matroska 規格由来のタグ定義用のクラス。
 * </p>
 *
 * <p>
 * 参考規格
 * </p>
 * <ul>
 * <li>Matroska: http://www.matroska.org/technical/specs/index.html</li>
 * </ul>
 *
 * @author katsuhiro
 */
public class MKVTagSpec {
    public int id;
    public int type;
    public String name;
    public int level;
    public boolean over;
    public boolean mandatory;
    public boolean multiple;
    public boolean first;
    public boolean second;
    public boolean third;
    public boolean webm;

    public MKVTagSpec(int id, int type, String name,
                      int level, boolean over,
                      boolean mandatory, boolean multiple,
                      boolean first, boolean second, boolean third, boolean webm) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.level = level;
        this.over = over;
        this.mandatory = mandatory;
        this.multiple = multiple;
        this.first = first;
        this.second = second;
        this.third = third;
        this.webm = webm;
    }
}
