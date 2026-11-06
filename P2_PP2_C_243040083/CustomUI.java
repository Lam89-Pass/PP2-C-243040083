import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


public class CustomUI {
}


class GradientPanel extends JPanel {
    GradientPanel(LayoutManager lm) {
        super(lm);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setPaint(new GradientPaint(0, 0, new Color(0xF6, 0xF9, 0xFF),
                0, getHeight(), new Color(0xDD, 0xE9, 0xFA)));
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.dispose();
    }
}


class SectionPanel extends JPanel {
    private final int pillH;
    private final String judul;
    private final VectorIcon ikon;

    SectionPanel(String judul, Glyph glyph) {
        this.judul = judul;
        this.pillH = UITheme.px(44);
        this.ikon = new VectorIcon(glyph, UITheme.px(22), Color.WHITE, UITheme.NAVY);
        setOpaque(false);
        setBorder(new EmptyBorder(pillH + UITheme.px(8), UITheme.px(16), UITheme.px(14), UITheme.px(16)));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        UITheme.aktifkanAntialias(g2);
        int w = getWidth();
        int h = getHeight();
        int top = pillH / 2;

        g2.setColor(new Color(255, 255, 255, 210));
        g2.fillRoundRect(0, top, w - 1, h - top - 1, UITheme.px(26), UITheme.px(26));
        g2.setColor(UITheme.BORDER_BLUE);
        g2.setStroke(new BasicStroke(UITheme.fpx(1.5)));
        g2.drawRoundRect(0, top, w - 1, h - top - 1, UITheme.px(26), UITheme.px(26));

        Font f = UITheme.fnt(19, Font.BOLD);
        FontMetrics fm = g2.getFontMetrics(f);
        int pillW = pillH + UITheme.px(14) + fm.stringWidth(judul) + UITheme.px(30);
        g2.setColor(UITheme.NAVY);
        g2.fillRoundRect(0, 0, pillW, pillH, pillH, pillH);
        g2.setColor(UITheme.BLUE);
        g2.fillOval(0, 0, pillH, pillH);
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(UITheme.fpx(2)));
        g2.drawOval(1, 1, pillH - 3, pillH - 3);
        int ik = ikon.getIconWidth();
        ikon.paintIcon(this, g2, (pillH - ik) / 2, (pillH - ik) / 2);

        g2.setFont(f);
        g2.setColor(Color.WHITE);
        g2.drawString(judul, pillH + UITheme.px(14), (pillH - fm.getHeight()) / 2 + fm.getAscent());
        g2.dispose();
        super.paintComponent(g);
    }
}

class RoundedTextField extends JTextField {
    private final String placeholder;

    RoundedTextField(String placeholder) {
        this.placeholder = placeholder;
        setOpaque(false);
        setFont(UITheme.fnt(16, Font.PLAIN));
        setForeground(UITheme.TEXT_DARK);
        setCaretColor(UITheme.TEXT_DARK);
        setBorder(new EmptyBorder(UITheme.px(6), UITheme.px(12), UITheme.px(6), UITheme.px(12)));
        setPreferredSize(new Dimension(UITheme.px(260), UITheme.px(42)));
        addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) { repaint(); }
            @Override public void focusLost(FocusEvent e) { repaint(); }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        int arc = UITheme.px(16);
        Graphics2D bg = (Graphics2D) g.create();
        UITheme.aktifkanAntialias(bg);
        bg.setColor(Color.WHITE);
        bg.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arc, arc);
        bg.dispose();

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        UITheme.aktifkanAntialias(g2);
        if (getText().isEmpty()) {
            g2.setFont(getFont());
            g2.setColor(UITheme.PLACEHOLDER);
            FontMetrics fm = g2.getFontMetrics();
            g2.drawString(placeholder, getInsets().left,
                    (getHeight() - fm.getHeight()) / 2 + fm.getAscent());
        }
        boolean fokus = isFocusOwner();
        g2.setColor(fokus ? UITheme.BLUE : UITheme.BORDER_BLUE);
        g2.setStroke(new BasicStroke(UITheme.fpx(fokus ? 2 : 1.5)));
        g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, arc, arc);
        g2.dispose();
    }
}


class PlaceholderTextArea extends JTextArea {
    private final String placeholder;

    PlaceholderTextArea(String placeholder) {
        this.placeholder = placeholder;
        setFont(UITheme.fnt(16, Font.PLAIN));
        setForeground(UITheme.TEXT_DARK);
        setCaretColor(UITheme.TEXT_DARK);
        setBackground(Color.WHITE);
        setLineWrap(true);
        setWrapStyleWord(true);
        setMargin(new Insets(UITheme.px(8), UITheme.px(12), UITheme.px(8), UITheme.px(12)));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (getText().isEmpty()) {
            Graphics2D g2 = (Graphics2D) g.create();
            UITheme.aktifkanAntialias(g2);
            g2.setFont(getFont());
            g2.setColor(UITheme.PLACEHOLDER);
            Insets in = getInsets();
            g2.drawString(placeholder, in.left, in.top + g2.getFontMetrics().getAscent());
            g2.dispose();
        }
    }
}


class RoundedButton extends JButton {
    private final boolean utama;

    RoundedButton(String teks, Glyph glyph, boolean utama) {
        super(teks);
        this.utama = utama;
        setIcon(new VectorIcon(glyph, UITheme.px(24), utama ? Color.WHITE : UITheme.NAVY, utama ? UITheme.NAVY : Color.WHITE));
        setFont(UITheme.fnt(17, Font.BOLD));
        setForeground(utama ? Color.WHITE : UITheme.NAVY);
        setIconTextGap(UITheme.px(10));
        setOpaque(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setRolloverEnabled(true);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setBorder(new EmptyBorder(UITheme.px(11), UITheme.px(26), UITheme.px(11) + UITheme.px(3), UITheme.px(26)));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        UITheme.aktifkanAntialias(g2);
        int arc = UITheme.px(18);
        int bayang = UITheme.px(3);
        int w = getWidth();
        int h = getHeight() - bayang; 
        ButtonModel m = getModel();

        if (utama) {
            g2.setColor(new Color(0, 0, 0, 50));
            g2.fillRoundRect(2, bayang, w - 4, h, arc, arc);
            Color c1 = new Color(0x2A, 0x5F, 0xBF);
            Color c2 = new Color(0x12, 0x35, 0x78);
            if (m.isPressed()) {
                c1 = new Color(0x10, 0x30, 0x70);
                c2 = new Color(0x0C, 0x24, 0x58);
            } else if (m.isRollover()) {
                c1 = new Color(0x3A, 0x72, 0xD6);
                c2 = new Color(0x1A, 0x42, 0x92);
            }
            g2.setPaint(new GradientPaint(0, 0, c1, 0, h, c2));
            g2.fillRoundRect(0, 0, w - 1, h - 1, arc, arc);
        } else {
            Color isi = Color.WHITE;
            if (m.isPressed()) isi = new Color(0xDC, 0xE8, 0xFA);
            else if (m.isRollover()) isi = new Color(0xEE, 0xF4, 0xFF);
            g2.setColor(isi);
            g2.fillRoundRect(0, 0, w - 1, h - 1, arc, arc);
            g2.setColor(UITheme.BORDER_BLUE);
            g2.setStroke(new BasicStroke(UITheme.fpx(1.5)));
            g2.drawRoundRect(1, 1, w - 3, h - 3, arc, arc);
        }

        if (isFocusOwner()) { 
            g2.setColor(new Color(0xFF, 0xB3, 0x2B));
            g2.setStroke(new BasicStroke(UITheme.fpx(2.5)));
            int in = UITheme.px(3);
            g2.drawRoundRect(in, in, w - 1 - 2 * in, h - 1 - 2 * in, arc - UITheme.px(4), arc - UITheme.px(4));
        }
        g2.dispose();
        super.paintComponent(g);
    }
}
