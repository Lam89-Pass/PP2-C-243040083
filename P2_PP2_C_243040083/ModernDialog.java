import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class ModernDialog extends JDialog {

    public enum DialogType {
        WARNING, ERROR, INFO, SUCCESS
    }

    private int selectedOption = -1;

    public ModernDialog(Window parent, String judul, String pesan, DialogType type, String[] options) {
        super(parent, ModalityType.APPLICATION_MODAL);
        setUndecorated(true);

        try {
            setBackground(new Color(0, 0, 0, 0));
        } catch (Exception ignored) {
        }

        int w = UITheme.px(500);
        int h = UITheme.px(280);

        JPanel contentPane = new JPanel(new BorderLayout(0, UITheme.px(16))) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                UITheme.aktifkanAntialias(g2);

                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, UITheme.px(22), UITheme.px(22));

                Color borderColor = (type == DialogType.ERROR || type == DialogType.WARNING)
                        ? new Color(0xDC, 0x35, 0x45)
                        : UITheme.BORDER_BLUE;
                g2.setColor(borderColor);
                g2.setStroke(new BasicStroke(UITheme.fpx(1.8f)));
                g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, UITheme.px(22), UITheme.px(22));

                g2.dispose();
                super.paintComponent(g);
            }
        };
        contentPane.setOpaque(false);
        contentPane.setBorder(new EmptyBorder(UITheme.px(24), UITheme.px(28), UITheme.px(20), UITheme.px(28)));

        JPanel pnlHeader = new JPanel(new FlowLayout(FlowLayout.CENTER, UITheme.px(12), 0));
        pnlHeader.setOpaque(false);

        JLabel lblIcon = new JLabel() {
            @Override
            public Dimension getPreferredSize() {
                int s = UITheme.px(42);
                return new Dimension(s, s);
            }

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                UITheme.aktifkanAntialias(g2);
                int s = getWidth();

                Color circleColor;
                if (type == DialogType.ERROR) circleColor = new Color(0xDC, 0x35, 0x45);
                else if (type == DialogType.WARNING) circleColor = new Color(0xFD, 0x7E, 0x14);
                else if (type == DialogType.SUCCESS) circleColor = new Color(0x28, 0xA7, 0x45);
                else circleColor = UITheme.BLUE;

                g2.setColor(circleColor);
                g2.fillOval(0, 0, s - 1, s - 1);

                g2.setColor(Color.WHITE);
                if (type == DialogType.ERROR || type == DialogType.WARNING) {
                    g2.setStroke(new BasicStroke(UITheme.fpx(3.5f), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                    g2.drawLine(s / 2, UITheme.px(10), s / 2, s - UITheme.px(16));
                    g2.fillOval(s / 2 - UITheme.px(2), s - UITheme.px(11), UITheme.px(4), UITheme.px(4));
                } else if (type == DialogType.SUCCESS) {
                    g2.setStroke(new BasicStroke(UITheme.fpx(3.5f), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                    g2.drawLine(UITheme.px(11), s / 2, s / 2 - UITheme.px(2), s - UITheme.px(12));
                    g2.drawLine(s / 2 - UITheme.px(2), s - UITheme.px(12), s - UITheme.px(11), UITheme.px(12));
                } else {
                    g2.fillOval(s / 2 - UITheme.px(2), UITheme.px(9), UITheme.px(4), UITheme.px(4));
                    g2.setStroke(new BasicStroke(UITheme.fpx(3.5f), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                    g2.drawLine(s / 2, UITheme.px(18), s / 2, s - UITheme.px(10));
                }

                g2.dispose();
            }
        };

        JLabel lblJudul = new JLabel(judul);
        lblJudul.setFont(UITheme.fnt(20, Font.BOLD));
        lblJudul.setForeground(UITheme.NAVY);

        pnlHeader.add(lblIcon);
        pnlHeader.add(lblJudul);

        JTextArea txtPesan = new JTextArea(pesan);
        txtPesan.setFont(UITheme.fnt(15, Font.PLAIN));
        txtPesan.setForeground(UITheme.TEXT_DARK);
        txtPesan.setWrapStyleWord(true);
        txtPesan.setLineWrap(true);
        txtPesan.setOpaque(false);
        txtPesan.setEditable(false);
        txtPesan.setFocusable(false);
        txtPesan.setMargin(new Insets(UITheme.px(8), UITheme.px(12), UITheme.px(8), UITheme.px(12)));

        JPanel pnlTombol = new JPanel(new FlowLayout(FlowLayout.CENTER, UITheme.px(14), 0));
        pnlTombol.setOpaque(false);

        if (options == null || options.length == 0) {
            options = new String[]{"Mengerti"};
        }

        for (int i = 0; i < options.length; i++) {
            final int index = i;
            boolean isUtama = (i == 0);
            RoundedButton btn = new RoundedButton(options[i], isUtama ? Glyph.DOC : Glyph.REFRESH, isUtama);
            btn.setFont(UITheme.fnt(15, Font.BOLD));
            btn.setPreferredSize(new Dimension(UITheme.px(140), UITheme.px(42)));
            btn.addActionListener(e -> {
                selectedOption = index;
                dispose();
            });
            pnlTombol.add(btn);
        }

        contentPane.add(pnlHeader, BorderLayout.NORTH);
        contentPane.add(txtPesan, BorderLayout.CENTER);
        contentPane.add(pnlTombol, BorderLayout.SOUTH);

        setContentPane(contentPane);
        setSize(w, h);
        setLocationRelativeTo(parent);

        try {
            setShape(new RoundRectangle2D.Double(0, 0, w, h, UITheme.px(22), UITheme.px(22)));
        } catch (Exception ignored) {
        }

        getRootPane().registerKeyboardAction(e -> dispose(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    public int getSelectedOption() {
        return selectedOption;
    }

    public static void tampilkanPesan(Component parent, String judul, String pesan, boolean isError) {
        Window win = SwingUtilities.getWindowAncestor(parent);
        DialogType type = isError ? DialogType.ERROR : DialogType.INFO;
        ModernDialog dlg = new ModernDialog(win, judul, pesan, type, new String[]{"Mengerti"});
        dlg.setVisible(true);
    }

    public static void tampilkanSukses(Component parent, String judul, String pesan) {
        Window win = SwingUtilities.getWindowAncestor(parent);
        ModernDialog dlg = new ModernDialog(win, judul, pesan, DialogType.SUCCESS, new String[]{"OK"});
        dlg.setVisible(true);
    }

    public static boolean tampilkanKonfirmasi(Component parent, String judul, String pesan, String tombolYa, String tombolBatal) {
        Window win = SwingUtilities.getWindowAncestor(parent);
        ModernDialog dlg = new ModernDialog(win, judul, pesan, DialogType.WARNING, new String[]{tombolYa, tombolBatal});
        dlg.setVisible(true);
        return dlg.getSelectedOption() == 0;
    }

    public static int tampilkanOpsi(Component parent, String judul, String pesan, String[] opsi) {
        Window win = SwingUtilities.getWindowAncestor(parent);
        ModernDialog dlg = new ModernDialog(win, judul, pesan, DialogType.SUCCESS, opsi);
        dlg.setVisible(true);
        return dlg.getSelectedOption();
    }
}
