import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.Icon;

enum Glyph {
    PERSON, ID, MAIL, CALENDAR, CAP, CLOCK, STAR, DOC, PEOPLE, SEND, REFRESH, BUILDING, BOOK
}

public class VectorIcon implements Icon {
    private final Glyph glyph;
    private final int size;
    private final Color main;
    private final Color detail;

    public VectorIcon(Glyph glyph, int size, Color main, Color detail) {
        this.glyph = glyph;
        this.size = size;
        this.main = main;
        this.detail = detail;
    }

    @Override
    public int getIconWidth() {
        return size;
    }

    @Override
    public int getIconHeight() {
        return size;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2 = (Graphics2D) g.create();
        UITheme.aktifkanAntialias(g2);
        g2.translate(x, y);
        draw(g2, glyph, size, main, detail);
        g2.dispose();
    }

    private static Path2D poligon(double... xy) {
        Path2D p = new Path2D.Double();
        p.moveTo(xy[0], xy[1]);
        for (int i = 2; i < xy.length; i += 2) {
            p.lineTo(xy[i], xy[i + 1]);
        }
        p.closePath();
        return p;
    }

    private static void orang(Graphics2D g, double cx, double top, double size) {
        double d = size * 0.42;
        g.fill(new Ellipse2D.Double(cx - d / 2, top, d, d));
        g.fill(new Arc2D.Double(cx - size * 0.42, top + size * 0.52, size * 0.84, size * 0.9,
                0, 180, Arc2D.PIE));
    }

    public static void draw(Graphics2D g, Glyph t, double s, Color main, Color detail) {
        g.setColor(main);
        float sw = (float) Math.max(1.5, s * 0.09);
        BasicStroke garis = new BasicStroke(sw, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        switch (t) {
            case PERSON:
                orang(g, s * 0.5, s * 0.02, s * 0.96);
                break;
            case PEOPLE:
                orang(g, s * 0.2, s * 0.28, s * 0.5);
                orang(g, s * 0.8, s * 0.28, s * 0.5);
                orang(g, s * 0.5, s * 0.08, s * 0.8);
                break;
            case ID:
                g.fill(new RoundRectangle2D.Double(s * 0.04, s * 0.16, s * 0.92, s * 0.68, s * 0.14, s * 0.14));
                g.setColor(detail);
                g.fill(new Ellipse2D.Double(s * 0.14, s * 0.3, s * 0.2, s * 0.2));
                g.fill(new Arc2D.Double(s * 0.1, s * 0.54, s * 0.28, s * 0.3, 0, 180, Arc2D.PIE));
                for (int i = 0; i < 3; i++) {
                    g.fill(new RoundRectangle2D.Double(s * 0.48, s * (0.33 + 0.14 * i), s * 0.38, s * 0.07,
                            s * 0.04, s * 0.04));
                }
                break;
            case MAIL: {
                g.fill(new RoundRectangle2D.Double(s * 0.04, s * 0.2, s * 0.92, s * 0.62, s * 0.1, s * 0.1));
                g.setColor(detail);
                g.setStroke(garis);
                Path2D v = new Path2D.Double();
                v.moveTo(s * 0.1, s * 0.3);
                v.lineTo(s * 0.5, s * 0.58);
                v.lineTo(s * 0.9, s * 0.3);
                g.draw(v);
                break;
            }
            case CALENDAR:
                g.fill(new RoundRectangle2D.Double(s * 0.08, s * 0.16, s * 0.84, s * 0.76, s * 0.16, s * 0.16));
                g.fill(new RoundRectangle2D.Double(s * 0.26, s * 0.04, s * 0.11, s * 0.2, s * 0.06, s * 0.06));
                g.fill(new RoundRectangle2D.Double(s * 0.63, s * 0.04, s * 0.11, s * 0.2, s * 0.06, s * 0.06));
                g.setColor(detail);
                for (int r = 0; r < 2; r++) {
                    for (int c = 0; c < 3; c++) {
                        g.fill(new RoundRectangle2D.Double(s * (0.2 + 0.22 * c), s * (0.42 + 0.2 * r),
                                s * 0.14, s * 0.12, s * 0.03, s * 0.03));
                    }
                }
                break;
            case CAP: {
                Path2D atas = poligon(s * 0.5, s * 0.18, s * 0.98, s * 0.42, s * 0.5, s * 0.66, s * 0.02, s * 0.42);
                Path2D dasar = poligon(s * 0.24, s * 0.56, s * 0.5, s * 0.7, s * 0.76, s * 0.56,
                        s * 0.76, s * 0.76, s * 0.5, s * 0.88, s * 0.24, s * 0.76);
                g.fill(dasar);
                g.fill(atas);
                g.setColor(detail);
                g.setStroke(new BasicStroke(Math.max(1f, sw * 0.5f)));
                g.draw(atas);
                break;
            }
            case CLOCK:
                g.setStroke(garis);
                g.draw(new Ellipse2D.Double(s * 0.1, s * 0.1, s * 0.8, s * 0.8));
                g.draw(new Line2D.Double(s * 0.5, s * 0.5, s * 0.5, s * 0.28));
                g.draw(new Line2D.Double(s * 0.5, s * 0.5, s * 0.68, s * 0.6));
                break;
            case STAR: {
                Path2D p = new Path2D.Double();
                for (int i = 0; i < 10; i++) {
                    double rad = (i % 2 == 0) ? s * 0.5 : s * 0.21;
                    double ang = Math.toRadians(-90 + i * 36);
                    double px = s * 0.5 + rad * Math.cos(ang);
                    double py = s * 0.55 + rad * Math.sin(ang);
                    if (i == 0) p.moveTo(px, py); else p.lineTo(px, py);
                }
                p.closePath();
                g.fill(p);
                break;
            }
            case DOC:
                g.fill(poligon(s * 0.2, s * 0.06, s * 0.62, s * 0.06, s * 0.82, s * 0.26,
                        s * 0.82, s * 0.94, s * 0.2, s * 0.94));
                g.setColor(detail);
                for (int i = 0; i < 3; i++) {
                    g.fill(new RoundRectangle2D.Double(s * 0.31, s * (0.42 + 0.15 * i), s * 0.4, s * 0.06,
                            s * 0.03, s * 0.03));
                }
                break;
            case SEND:
                g.fill(poligon(s * 0.04, s * 0.46, s * 0.96, s * 0.06, s * 0.62, s * 0.94, s * 0.46, s * 0.6));
                g.setColor(detail);
                g.setStroke(new BasicStroke(Math.max(1f, sw * 0.45f), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g.draw(new Line2D.Double(s * 0.46, s * 0.6, s * 0.96, s * 0.06));
                break;
            case REFRESH: {
                g.setStroke(garis);
                double cx = s * 0.5, cy = s * 0.5, r = s * 0.34;
                g.draw(new Arc2D.Double(cx - r, cy - r, 2 * r, 2 * r, 60, 270, Arc2D.OPEN));
                double th = Math.toRadians(330);
                double ex = cx + r * Math.cos(th), ey = cy - r * Math.sin(th);
                double tx = -Math.sin(th), ty = -Math.cos(th); 
                double nx = Math.cos(th), ny = -Math.sin(th);  
                double a = s * 0.2;
                g.fill(poligon(ex + tx * a, ey + ty * a,
                        ex + nx * a * 0.9, ey + ny * a * 0.9,
                        ex - nx * a * 0.9, ey - ny * a * 0.9));
                break;
            }
            case BUILDING: {
                Path2D atap = poligon(s * 0.1, s * 0.32, s * 0.5, s * 0.08, s * 0.9, s * 0.32);
                g.fill(atap);
                g.fill(new RoundRectangle2D.Double(s * 0.08, s * 0.32, s * 0.84, s * 0.08, s * 0.02, s * 0.02));
                g.fill(new RoundRectangle2D.Double(s * 0.06, s * 0.82, s * 0.88, s * 0.10, s * 0.02, s * 0.02));
                for (int i = 0; i < 4; i++) {
                    g.fill(new RoundRectangle2D.Double(s * (0.16 + 0.20 * i), s * 0.40, s * 0.08, s * 0.42, s * 0.02, s * 0.02));
                }
                break;
            }
            case BOOK: {
                Path2D b = new Path2D.Double();
                b.moveTo(s * 0.5, s * 0.28);
                b.curveTo(s * 0.35, s * 0.20, s * 0.2, s * 0.22, s * 0.08, s * 0.25);
                b.lineTo(s * 0.08, s * 0.78);
                b.curveTo(s * 0.2, s * 0.75, s * 0.35, s * 0.74, s * 0.5, s * 0.82);
                b.curveTo(s * 0.65, s * 0.74, s * 0.8, s * 0.75, s * 0.92, s * 0.78);
                b.lineTo(s * 0.92, s * 0.25);
                b.curveTo(s * 0.8, s * 0.22, s * 0.65, s * 0.20, s * 0.5, s * 0.28);
                b.closePath();
                g.fill(b);
                g.setColor(detail);
                g.setStroke(new BasicStroke(Math.max(1f, sw * 0.5f)));
                g.draw(new Line2D.Double(s * 0.5, s * 0.28, s * 0.5, s * 0.82));
                break;
            }
            default:
                break;
        }
    }
}

class PanahIcon implements Icon {
    @Override
    public int getIconWidth() {
        return UITheme.px(14);
    }

    @Override
    public int getIconHeight() {
        return UITheme.px(14);
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        int size = getIconWidth();
        Graphics2D g2 = (Graphics2D) g.create();
        UITheme.aktifkanAntialias(g2);
        g2.translate(x, y);
        g2.setColor(UITheme.NAVY);
        Path2D p = new Path2D.Double();
        p.moveTo(size * 0.1, size * 0.3);
        p.lineTo(size * 0.9, size * 0.3);
        p.lineTo(size * 0.5, size * 0.78);
        p.closePath();
        g2.fill(p);
        g2.dispose();
    }
}

class CheckIcon implements Icon {
    @Override
    public int getIconWidth() {
        return UITheme.px(22);
    }

    @Override
    public int getIconHeight() {
        return UITheme.px(22);
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        int size = getIconWidth();
        AbstractButton b = (AbstractButton) c;
        ButtonModel m = b.getModel();
        Graphics2D g2 = (Graphics2D) g.create();
        UITheme.aktifkanAntialias(g2);
        g2.translate(x, y);
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(1, 1, size - 3, size - 3, UITheme.px(6), UITheme.px(6));
        g2.setColor(m.isSelected() || m.isRollover() || b.hasFocus() ? UITheme.BLUE : UITheme.BORDER_BLUE);
        g2.setStroke(new BasicStroke(UITheme.fpx(1.8)));
        g2.drawRoundRect(1, 1, size - 3, size - 3, UITheme.px(6), UITheme.px(6));
        if (m.isSelected()) {
            g2.setColor(UITheme.BLUE);
            g2.setStroke(new BasicStroke(UITheme.fpx(2.6), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            Path2D cek = new Path2D.Double();
            cek.moveTo(size * 0.24, size * 0.52);
            cek.lineTo(size * 0.43, size * 0.70);
            cek.lineTo(size * 0.78, size * 0.30);
            g2.draw(cek);
        }
        g2.dispose();
    }
}

class RadioIcon implements Icon {
    @Override
    public int getIconWidth() {
        return UITheme.px(22);
    }

    @Override
    public int getIconHeight() {
        return UITheme.px(22);
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        int size = getIconWidth();
        AbstractButton b = (AbstractButton) c;
        ButtonModel m = b.getModel();
        Graphics2D g2 = (Graphics2D) g.create();
        UITheme.aktifkanAntialias(g2);
        g2.translate(x, y);
        g2.setColor(Color.WHITE);
        g2.fillOval(1, 1, size - 3, size - 3);
        g2.setColor(m.isSelected() || m.isRollover() || b.hasFocus() ? UITheme.BLUE : UITheme.BORDER_BLUE);
        g2.setStroke(new BasicStroke(UITheme.fpx(1.8)));
        g2.drawOval(1, 1, size - 3, size - 3);
        if (m.isSelected()) {
            g2.setColor(UITheme.BLUE);
            double d = size * 0.5;
            g2.fill(new Ellipse2D.Double((size - d) / 2.0, (size - d) / 2.0, d, d));
        }
        g2.dispose();
    }
}
