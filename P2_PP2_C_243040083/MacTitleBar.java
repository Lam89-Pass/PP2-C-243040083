import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class MacTitleBar extends JPanel {

    private final Main mainFrame;
    private Point initialClick;
    private boolean hoverTraffic = false;

    private final TrafficButton btnClose;
    private final TrafficButton btnMinimize;
    private final TrafficButton btnMaximize;

    public MacTitleBar(Main mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        setOpaque(false);
        setPreferredSize(new Dimension(UITheme.px(900), UITheme.px(44)));
        setBorder(new EmptyBorder(0, UITheme.px(16), 0, UITheme.px(16)));

        JPanel pnlTraffic = new JPanel(new FlowLayout(FlowLayout.LEFT, UITheme.px(9), UITheme.px(13)));
        pnlTraffic.setOpaque(false);

        btnClose = new TrafficButton(
                new Color(0xFF, 0x5F, 0x56),
                new Color(0xE0, 0x44, 0x3E),
                TrafficType.CLOSE,
                e -> mainFrame.keluarAplikasi()
        );
        btnClose.setToolTipText("Tutup");

        btnMinimize = new TrafficButton(
                new Color(0xFF, 0xBD, 0x2E),
                new Color(0xDE, 0xA1, 0x23),
                TrafficType.MINIMIZE,
                e -> mainFrame.setState(JFrame.ICONIFIED)
        );
        btnMinimize.setToolTipText("Minimalkan");

        btnMaximize = new TrafficButton(
                new Color(0x27, 0xC9, 0x3F),
                new Color(0x1A, 0xAB, 0x29),
                TrafficType.MAXIMIZE,
                e -> mainFrame.toggleMaximize()
        );
        btnMaximize.setToolTipText("Perbesar / Pulihkan");

        pnlTraffic.add(btnClose);
        pnlTraffic.add(btnMinimize);
        pnlTraffic.add(btnMaximize);

        MouseAdapter hoverAdapter = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hoverTraffic = true;
                repaintTraffic();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                Point p = e.getPoint();
                if (!pnlTraffic.contains(p)) {
                    hoverTraffic = false;
                    repaintTraffic();
                }
            }
        };
        pnlTraffic.addMouseListener(hoverAdapter);
        btnClose.addMouseListener(hoverAdapter);
        btnMinimize.addMouseListener(hoverAdapter);
        btnMaximize.addMouseListener(hoverAdapter);

        JPanel pnlTitle = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, UITheme.px(12)));
        pnlTitle.setOpaque(false);

        JLabel lblTitle = new JLabel("Aplikasi Pendaftaran Kegiatan");
        lblTitle.setFont(UITheme.fnt(14, Font.BOLD));
        lblTitle.setForeground(Color.WHITE);

        pnlTitle.add(lblTitle);

        JPanel pnlRightSpacer = new JPanel();
        pnlRightSpacer.setOpaque(false);
        pnlRightSpacer.setPreferredSize(new Dimension(UITheme.px(80), UITheme.px(30)));

        add(pnlTraffic, BorderLayout.WEST);
        add(pnlTitle, BorderLayout.CENTER);
        add(pnlRightSpacer, BorderLayout.EAST);

        MouseAdapter dragAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    mainFrame.toggleMaximize();
                }
            }
        };

        MouseMotionAdapter motionAdapter = new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (!mainFrame.isMaximized()) {
                    int thisX = mainFrame.getLocation().x;
                    int thisY = mainFrame.getLocation().y;
                    int xMoved = e.getX() - initialClick.x;
                    int yMoved = e.getY() - initialClick.y;
                    mainFrame.setLocation(thisX + xMoved, thisY + yMoved);
                }
            }
        };

        addMouseListener(dragAdapter);
        addMouseMotionListener(motionAdapter);
        pnlTitle.addMouseListener(dragAdapter);
        pnlTitle.addMouseMotionListener(motionAdapter);
    }

    private void repaintTraffic() {
        btnClose.repaint();
        btnMinimize.repaint();
        btnMaximize.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        UITheme.aktifkanAntialias(g2);

        g2.setPaint(new GradientPaint(
                0, 0, new Color(0x0C, 0x22, 0x4D),
                0, getHeight(), new Color(0x10, 0x2D, 0x66)
        ));
        g2.fillRect(0, 0, getWidth(), getHeight());

        g2.setColor(new Color(0x2B, 0x63, 0xC4, 120));
        g2.setStroke(new BasicStroke(1f));
        g2.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);

        g2.dispose();
        super.paintComponent(g);
    }

    private enum TrafficType {
        CLOSE, MINIMIZE, MAXIMIZE
    }

    private class TrafficButton extends JButton {
        private final Color baseColor;
        private final Color borderColor;
        private final TrafficType type;

        public TrafficButton(Color baseColor, Color borderColor, TrafficType type, ActionListener action) {
            this.baseColor = baseColor;
            this.borderColor = borderColor;
            this.type = type;

            int size = UITheme.px(17);
            setPreferredSize(new Dimension(size, size));
            setMinimumSize(new Dimension(size, size));
            setMaximumSize(new Dimension(size, size));
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            addActionListener(action);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            UITheme.aktifkanAntialias(g2);
            int d = UITheme.px(16);
            int x = (getWidth() - d) / 2;
            int y = (getHeight() - d) / 2;

            g2.setColor(baseColor);
            g2.fillOval(x, y, d, d);

            g2.setColor(borderColor);
            g2.setStroke(new BasicStroke(1f));
            g2.drawOval(x, y, d - 1, d - 1);

            if (hoverTraffic) {
                g2.setColor(new Color(0, 0, 0, 160));
                g2.setStroke(new BasicStroke(UITheme.fpx(1.8f), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                int pad = UITheme.px(4);

                switch (type) {
                    case CLOSE:
                        g2.drawLine(x + pad, y + pad, x + d - pad, y + d - pad);
                        g2.drawLine(x + d - pad, y + pad, x + pad, y + d - pad);
                        break;
                    case MINIMIZE:
                        g2.drawLine(x + pad, y + d / 2, x + d - pad, y + d / 2);
                        break;
                    case MAXIMIZE:
                        g2.drawLine(x + pad, y + d / 2, x + d - pad, y + d / 2);
                        g2.drawLine(x + d / 2, y + pad, x + d / 2, y + d - pad);
                        break;
                }
            }

            g2.dispose();
        }
    }
}
