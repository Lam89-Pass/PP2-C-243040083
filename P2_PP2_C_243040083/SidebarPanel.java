import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class SidebarPanel extends JPanel {
    private Main mainFrame;

    public SidebarPanel(Main mainFrame) {
        super(new BorderLayout());
        this.mainFrame = mainFrame;
        setPreferredSize(new Dimension(UITheme.px(260), 100));

        add(new KalenderPanel(), BorderLayout.NORTH);

        JPanel teks = new JPanel();
        teks.setLayout(new BoxLayout(teks, BoxLayout.Y_AXIS));
        teks.setOpaque(false);
        teks.setBorder(new EmptyBorder(0, UITheme.px(26), 0, UITheme.px(12)));

        JLabel judul = new JLabel("<html>Formulir<br>Pendaftaran<br>Kegiatan</html>");
        judul.setFont(UITheme.fnt(29, Font.BOLD));
        judul.setForeground(Color.WHITE);
        judul.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel garis = new JPanel();
        garis.setBackground(Color.WHITE);
        Dimension d = new Dimension(UITheme.px(72), Math.max(2, UITheme.px(3)));
        garis.setPreferredSize(d);
        garis.setMinimumSize(d);
        garis.setMaximumSize(d);
        garis.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel kutipan = new JLabel("<html><i>\u201C Bergabunglah dalam<br>kegiatan terbaik kami! \u201D</i></html>");
        kutipan.setFont(UITheme.fnt(16, Font.PLAIN));
        kutipan.setForeground(new Color(0xE1, 0xEB, 0xFA));
        kutipan.setAlignmentX(Component.LEFT_ALIGNMENT);

        teks.add(judul);
        teks.add(Box.createVerticalStrut(UITheme.px(16)));
        teks.add(garis);
        teks.add(Box.createVerticalStrut(UITheme.px(16)));
        teks.add(kutipan);

        teks.add(Box.createVerticalStrut(UITheme.px(30)));
        teks.add(buatTombolMenu("Isi Formulir", Glyph.DOC, e -> mainFrame.showCard("FORM")));
        teks.add(Box.createVerticalStrut(UITheme.px(10)));
        teks.add(buatTombolMenu("Pusat Bantuan", Glyph.STAR, e -> mainFrame.showCard("BANTUAN")));
        teks.add(Box.createVerticalStrut(UITheme.px(10)));
        teks.add(buatTombolMenu("Keluar Aplikasi", Glyph.PERSON, e -> mainFrame.keluarAplikasi()));

        add(teks, BorderLayout.CENTER);
        add(new IlustrasiPanel(), BorderLayout.SOUTH);
    }

    private JButton buatTombolMenu(String label, Glyph icon, ActionListener action) {
        JButton btn = new JButton(label);
        btn.setIcon(new VectorIcon(icon, UITheme.px(20), Color.WHITE, UITheme.NAVY));
        btn.setFont(UITheme.fnt(16, Font.BOLD));
        btn.setForeground(Color.WHITE);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setIconTextGap(UITheme.px(12));
        btn.setMargin(new Insets(0, 0, 0, 0));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.addActionListener(action);
        return btn;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        UITheme.aktifkanAntialias(g2);
        g2.setPaint(new GradientPaint(0, 0, new Color(0x1E, 0x52, 0xA6),
                0, getHeight(), new Color(0x0A, 0x28, 0x60)));
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.setColor(new Color(255, 255, 255, 18));
        g2.fill(new Ellipse2D.Double(-UITheme.px(120), -UITheme.px(140), UITheme.px(380), UITheme.px(330)));
        g2.dispose();
    }
}

class KalenderPanel extends JPanel {
    KalenderPanel() {
        setOpaque(false);
        setPreferredSize(new Dimension(UITheme.px(260), UITheme.px(190)));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        UITheme.aktifkanAntialias(g2);
        g2.translate(UITheme.px(26), UITheme.px(40));
        g2.scale(UITheme.getSkala(), UITheme.getSkala());
        VectorIcon.draw(g2, Glyph.CALENDAR, 112, Color.WHITE, UITheme.NAVY);
        g2.setColor(new Color(0xE6, 0xEF, 0xFC));
        g2.fill(new Ellipse2D.Double(70, 66, 58, 58));
        g2.setColor(UITheme.BLUE);
        g2.fill(new Ellipse2D.Double(75, 71, 48, 48));
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(6f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        Path2D cek = new Path2D.Double();
        cek.moveTo(86, 96);
        cek.lineTo(95, 105);
        cek.lineTo(112, 85);
        g2.draw(cek);
        g2.dispose();
    }
}

class IlustrasiPanel extends JPanel {
    IlustrasiPanel() {
        setOpaque(false);
        setPreferredSize(new Dimension(UITheme.px(260), UITheme.px(230)));
    }

    private void daun(Graphics2D g2, double cx, double cy, double w, double h, double sudut, Color c) {
        Graphics2D g = (Graphics2D) g2.create();
        g.translate(cx, cy);
        g.rotate(Math.toRadians(sudut));
        g.setColor(c);
        g.fill(new Ellipse2D.Double(-w / 2, -h / 2, w, h));
        g.dispose();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        UITheme.aktifkanAntialias(g2);
        float s = UITheme.getSkala();
        g2.scale(s, s);
        double w = getWidth() / s;
        double h = getHeight() / s;
        Color daun = new Color(0x3B, 0x72, 0xC9, 190);
        daun(g2, w * 0.12, h * 0.62, 34, 90, -25, daun);
        daun(g2, w * 0.2, h * 0.72, 30, 70, -50, daun);
        daun(g2, w * 0.78, h * 0.55, 34, 100, 20, daun);
        daun(g2, w * 0.9, h * 0.68, 30, 70, 50, daun);

        Graphics2D c = (Graphics2D) g2.create();
        c.translate(w * 0.4, h * 0.42);
        c.rotate(Math.toRadians(-12));
        c.setColor(new Color(0x2C, 0x5C, 0xB0));
        c.fill(new RoundRectangle2D.Double(-58, -78, 116, 156, 16, 16));
        c.setColor(new Color(0xE2, 0xEC, 0xFB));
        c.fill(new RoundRectangle2D.Double(-52, -72, 104, 144, 12, 12));
        c.setColor(new Color(0x46, 0x78, 0xC8));
        c.fill(new RoundRectangle2D.Double(-24, -90, 48, 24, 10, 10));
        c.setColor(new Color(0xE2, 0xEC, 0xFB));
        c.fill(new Ellipse2D.Double(-6, -83, 12, 10));
        for (int i = 0; i < 4; i++) {
            double y = -44 + i * 31;
            c.setColor(new Color(0x2C, 0x5C, 0xB0));
            c.setStroke(new BasicStroke(3.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            Path2D tanda = new Path2D.Double();
            tanda.moveTo(-40, y);
            tanda.lineTo(-33, y + 7);
            tanda.lineTo(-22, y - 6);
            c.draw(tanda);
            c.setColor(new Color(0x7A, 0xA2, 0xDC));
            c.fill(new RoundRectangle2D.Double(-14, y - 4, 52, 7, 6, 6));
        }
        c.dispose();

        Path2D gel = new Path2D.Double();
        gel.moveTo(0, h);
        gel.lineTo(0, h * 0.68);
        gel.curveTo(w * 0.3, h * 0.45, w * 0.65, h * 0.95, w, h * 0.62);
        gel.lineTo(w, h);
        gel.closePath();
        g2.setPaint(new GradientPaint(0, (float) (h * 0.5), new Color(0x8F, 0xB8, 0xF2, 230),
                0, (float) h, new Color(0x5B, 0x8E, 0xE0, 230)));
        g2.fill(gel);
        g2.dispose();
    }
}
