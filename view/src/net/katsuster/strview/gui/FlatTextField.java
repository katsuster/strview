package net.katsuster.strview.gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

import javax.swing.*;
import javax.swing.border.*;

/**
 * <p>
 * クリアボタンの付いたテキストフィールドです。
 * </p>
 *
 * @author katsuhiro
 */
public class FlatTextField extends JPanel {
    private static final long serialVersionUID = 1L;

    private FlatClearButton btnL;
    private FlatClearButton btnR;
    private InnerJTextField fld;

    private Color borderColor = Color.GRAY;
    private Color fillColor = Color.WHITE;
    private Color faceColor = Color.GRAY;

    public FlatTextField() {
        this("");
    }

    public FlatTextField(String text) {
        fld = new InnerJTextField(text);
        btnL = new FlatClearButton(new ClearButtonAction("L", fld));
        btnR = new FlatClearButton(new ClearButtonAction("R", fld));

        btnL.setEnabled(false);
        btnR.setIsLeft(false);

        setLayout(new BorderLayout());
        add(btnL, BorderLayout.WEST);
        add(fld, BorderLayout.CENTER);
        add(btnR, BorderLayout.EAST);

        setForeground(getForeground());
        setBackground(getBackground());
        setBorderColor(getBorderColor());
        setFillColor(getFillColor());
        setFaceColor(getFaceColor());
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (fld == null) {
            return;
        }

        fld.setEnabled(enabled);
        btnL.setEnabled(enabled);
        btnR.setEnabled(enabled);
    }

    @Override
    public void setForeground(Color fg) {
        super.setForeground(fg);
        if (fld == null) {
            return ;
        }

        fld.setForeground(fg);
    }

    @Override
    public void setBackground(Color bg) {
        super.setBackground(bg);
        if (fld == null) {
            return ;
        }

        btnL.setBackground(bg);
        btnR.setBackground(bg);
    }

    public JTextField getTextField() {
        return fld;
    }

    /**
     * <p>
     * 枠の色を取得します。
     * </p>
     *
     * @return 枠の色
     */
    public Color getBorderColor() {
        return borderColor;
    }

    /**
     * <p>
     * 枠の色を設定します。
     * </p>
     *
     * @param bc 枠の色
     */
    public void setBorderColor(Color bc) {
        borderColor = bc;
        if (fld == null) {
            return ;
        }

        fld.setBorderColor(bc);
        btnL.setBorderColor(bc);
        btnR.setBorderColor(bc);
    }

    /**
     * <p>
     * テキストボックスの背景色を取得します。
     * </p>
     *
     * @return テキストボックスの背景色
     */
    public Color getFillColor() {
        return fillColor;
    }

    /**
     * <p>
     * テキストボックスの背景色を設定します。
     * </p>
     *
     * @param fc テキストボックスの背景色
     */
    public void setFillColor(Color fc) {
        fillColor = fc;
        if (fld == null) {
            return ;
        }

        fld.setBackground(fc);
        btnL.setFillColor(fc);
        btnR.setFillColor(fc);
    }

    /**
     * <p>
     * テキストクリアボタンの色を取得します。
     * </p>
     *
     * @return テキストクリアボタンの色
     */
    public Color getFaceColor() {
        return faceColor;
    }

    /**
     * <p>
     * テキストクリアボタンの色を設定します。
     * </p>
     *
     * @param fc テキストクリアボタンの色
     */
    public void setFaceColor(Color fc) {
        faceColor = fc;
        if (fld == null) {
            return ;
        }

        btnL.setFaceColor(fc);
        btnR.setFaceColor(fc);
    }

    public class ClearButtonAction extends AbstractAction {
        private static final long serialVersionUID = 1L;

        private JTextField target;

        public ClearButtonAction(String name, JTextField t) {
            super(name);
            target = t;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            target.setText("");
        }
    }

    public class FlatClearButton extends JPanel {
        private static final long serialVersionUID = 1L;

        private boolean isLeft = true;
        private int ellipseSize;
        private Color borderColor = Color.GRAY;
        private Color fillColor = Color.WHITE;
        private Color faceColor = Color.LIGHT_GRAY;
        private int strokeSize = 1;

        public FlatClearButton(Action act) {
            super();

            addMouseListener(new ClickListener(this, act));
            addMouseMotionListener(new CursorChanger(this));
            setEllipseSize(14);
        }

        public boolean getIsLeft() {
            return isLeft;
        }

        public void setIsLeft(boolean b) {
            isLeft = b;
        }

        public int getEllipseSize() {
            return ellipseSize;
        }

        public void setEllipseSize(int s) {
            ellipseSize = s;

            setPreferredSize(new Dimension(ellipseSize * 3 / 2, ellipseSize * 2));
        }

        public Color getBorderColor() {
            return borderColor;
        }

        public void setBorderColor(Color bc) {
            borderColor = bc;
        }

        public Color getFillColor() {
            return fillColor;
        }

        public void setFillColor(Color fc) {
            fillColor = fc;
        }

        public Color getFaceColor() {
            return faceColor;
        }

        public void setFaceColor(Color fc) {
            faceColor = fc;
        }

        public int getStrokeSize() {
            return strokeSize;
        }

        public void setStrokeSize(int s) {
            strokeSize = s;
        }

        public Rectangle getFaceRectangle() {
            Rectangle bound = getBounds(null);
            int ellipse_size = getEllipseSize();

            return new Rectangle((bound.width - ellipse_size) / 2, (bound.height - ellipse_size) / 2,
                    ellipse_size, ellipse_size);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D)g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            AffineTransform savedAT = g2.getTransform();
            Rectangle bound = getBounds(null);

            g2.setColor(getBackground());
            g2.fillRect(bound.x, bound.y, bound.width, bound.height);

            int sz = getStrokeSize();
            g2.setColor(getFillColor());
            if (getIsLeft()) {
                g2.fillRect(getWidth() / 2, 0, getWidth() / 2 + sz, getHeight() - sz);
                g2.fillArc(0, 0, getWidth() - sz, getHeight() - sz, 90, 180);
            } else {
                g2.fillRect(0, 0, getWidth() / 2 + sz, getHeight() - sz);
                g2.fillArc(0, 0, getWidth() - sz, getHeight() - sz, -90, 180);
            }

            if (!isEnabled()) {
                return;
            }

            double ellipse_size = getEllipseSize();
            Ellipse2D ellipse_c = new Ellipse2D.Double(0, 0, ellipse_size, ellipse_size);
            g2.translate((bound.width - ellipse_size) / 2, (bound.height - ellipse_size) / 2);
            g2.setColor(getFaceColor());
            g2.fill(ellipse_c);
            g2.setTransform(savedAT);

            double line_size = ellipse_size / 2.5;
            Line2D line_lr = new Line2D.Double(0, 0, line_size, line_size);
            Line2D line_rl = new Line2D.Double(line_size, 0, 0, line_size);
            g2.translate((bound.width - line_size) / 2, (bound.height - line_size) / 2);
            g2.setStroke(new BasicStroke(2f));
            g2.setColor(getFillColor());
            g2.draw(line_lr);
            g2.draw(line_rl);
            g2.setTransform(savedAT);
        }

        @Override
        protected void paintBorder(Graphics g) {
            super.paintBorder(g);

            int sz = getStrokeSize();
            Graphics2D g2 = (Graphics2D)g.create();;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setStroke(new BasicStroke(sz));
            g2.setColor(getBorderColor());

            if (getIsLeft()) {
                g2.drawLine(getWidth() / 2, 0, getWidth(), 0);
                g2.drawLine(getWidth() / 2, getHeight() - sz, getWidth(), getHeight() - sz);
                g2.drawArc(0, 0, getWidth() - sz, getHeight() - sz, 90, 180);
            } else {
                g2.drawLine(0, 0, getWidth() / 2, 0);
                g2.drawLine(0, getHeight() - sz, getWidth() / 2, getHeight() - sz);
                g2.drawArc(0, 0, getWidth() - sz, getHeight() - sz, -90, 180);
            }
        }

        public class ClickListener extends MouseAdapter {
            FlatClearButton parent;
            Action act;

            public ClickListener(FlatClearButton p, Action a) {
                parent = p;
                act = a;
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (!parent.isEnabled() || e.getButton() != MouseEvent.BUTTON1) {
                    return;
                }

                Rectangle bParent = parent.getBounds(null);
                Rectangle b = new Rectangle(
                        (bParent.width - parent.getEllipseSize()) / 2,
                        (bParent.height - parent.getEllipseSize()) / 2,
                        parent.getEllipseSize(), parent.getEllipseSize());
                if (!b.contains(e.getPoint())) {
                    return;
                }

                act.actionPerformed(new ActionEvent(parent, ActionEvent.ACTION_PERFORMED,
                        (String)act.getValue(Action.NAME)));
            }
        }

        public class CursorChanger extends MouseMotionAdapter {
            FlatClearButton parent;

            public CursorChanger(FlatClearButton p) {
                parent = p;
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                Rectangle r = parent.getFaceRectangle();

                if (parent.isEnabled() && r.contains(e.getPoint())) {
                    Cursor hand_cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
                    parent.setCursor(hand_cursor);
                } else {
                    Cursor default_cursor = Cursor.getDefaultCursor();
                    parent.setCursor(default_cursor);
                }
            }
        }
    }

    public class InnerJTextField extends JTextField {
        private Color borderColor = Color.GRAY;
        private int strokeSize = 1;

        public InnerJTextField() {
            this("");
        }

        public InnerJTextField(String text) {
            super(text);

            setBorder(new EmptyBorder(0, 0, 0, 0));
        }

        public Color getBorderColor() {
            return borderColor;
        }

        public void setBorderColor(Color bc) {
            borderColor = bc;
        }

        public int getStrokeSize() {
            return strokeSize;
        }

        public void setStrokeSize(int s) {
            strokeSize = s;
        }

        @Override
        protected void paintBorder(Graphics g) {
            super.paintBorder(g);

            int sz = getStrokeSize();
            Graphics2D g2 = (Graphics2D)g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setStroke(new BasicStroke(sz));
            g2.setColor(getBorderColor());
            g2.drawLine(0, 0, getWidth(), 0);
            g2.drawLine(0, getHeight() - sz, getWidth(), getHeight() - sz);
        }
    }
}
