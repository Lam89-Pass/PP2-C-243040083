import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class Main extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainContentPanel;
    private FormPendaftaran formPanel;
    private HalamanRingkasan ringkasanPanel;
    private HalamanBantuan bantuanPanel;
    private SidebarPanel sidebarPanel;
    private MacTitleBar titleBar;

    public Main() {
        super("Aplikasi Pendaftaran Kegiatan");
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        Dimension layar = Toolkit.getDefaultToolkit().getScreenSize();
        int w = Math.min(layar.width, Math.max(1150, (int) (layar.width * 0.60)));
        int h = Math.min(layar.height, Math.max(820, (int) (layar.height * 0.80)));
        UITheme.setSkala(UITheme.hitungSkala(w, h));

        setSize(w, h);
        setMinimumSize(new Dimension(1050, 750));
        setLocationRelativeTo(null);

        updateWindowShape();

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateWindowShape();
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                keluarAplikasi();
            }
        });

        JPanel rootPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (!isMaximized()) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    UITheme.aktifkanAntialias(g2);
                    g2.setColor(new Color(0x10, 0x2A, 0x5C));
                    g2.setStroke(new BasicStroke(UITheme.fpx(2f)));
                    g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, UITheme.px(20), UITheme.px(20));

                    g2.setColor(new Color(0x7A, 0x96, 0xBD, 150));
                    g2.setStroke(new BasicStroke(UITheme.fpx(1.5f), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                    int rx = getWidth() - UITheme.px(14);
                    int ry = getHeight() - UITheme.px(14);
                    g2.drawLine(rx + UITheme.px(6), ry + UITheme.px(2), rx + UITheme.px(2), ry + UITheme.px(6));
                    g2.drawLine(rx + UITheme.px(9), ry + UITheme.px(1), rx + UITheme.px(1), ry + UITheme.px(9));
                    g2.dispose();
                }
            }
        };

        titleBar = new MacTitleBar(this);

        cardLayout = new CardLayout();
        mainContentPanel = new JPanel(cardLayout);

        formPanel = new FormPendaftaran(this);
        ringkasanPanel = new HalamanRingkasan(this);
        bantuanPanel = new HalamanBantuan();
        sidebarPanel = new SidebarPanel(this);

        mainContentPanel.add(formPanel, "FORM");
        mainContentPanel.add(ringkasanPanel, "RINGKASAN");
        mainContentPanel.add(bantuanPanel, "BANTUAN");

        JPanel bodyPanel = new JPanel(new BorderLayout());
        bodyPanel.add(sidebarPanel, BorderLayout.WEST);
        bodyPanel.add(mainContentPanel, BorderLayout.CENTER);

        rootPanel.add(titleBar, BorderLayout.NORTH);
        rootPanel.add(bodyPanel, BorderLayout.CENTER);

        pasangWindowResizer(rootPanel);

        setContentPane(rootPanel);
    }

    private void pasangWindowResizer(JPanel panel) {
        MouseAdapter resizer = new MouseAdapter() {
            private final int BORDER_SZ = 8;
            private Point startPt;
            private Rectangle startBnds;
            private int resizeDir = 0;

            @Override
            public void mouseMoved(MouseEvent e) {
                if (isMaximized()) {
                    setCursor(Cursor.getDefaultCursor());
                    return;
                }
                int x = e.getX();
                int y = e.getY();
                int w = getWidth();
                int h = getHeight();

                boolean right = (x >= w - BORDER_SZ && x <= w);
                boolean bottom = (y >= h - BORDER_SZ && y <= h);

                if (right && bottom) {
                    resizeDir = 3;
                    setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
                } else if (right) {
                    resizeDir = 1;
                    setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
                } else if (bottom) {
                    resizeDir = 2;
                    setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
                } else {
                    resizeDir = 0;
                    setCursor(Cursor.getDefaultCursor());
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                startPt = e.getLocationOnScreen();
                startBnds = getBounds();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (resizeDir == 0 || startPt == null || startBnds == null || isMaximized())
                    return;
                Point curr = e.getLocationOnScreen();
                int dx = curr.x - startPt.x;
                int dy = curr.y - startPt.y;

                int newW = startBnds.width;
                int newH = startBnds.height;

                if (resizeDir == 1 || resizeDir == 3) {
                    newW = Math.max(getMinimumSize().width, startBnds.width + dx);
                }
                if (resizeDir == 2 || resizeDir == 3) {
                    newH = Math.max(getMinimumSize().height, startBnds.height + dy);
                }

                setSize(newW, newH);
                updateWindowShape();
            }
        };

        panel.addMouseListener(resizer);
        panel.addMouseMotionListener(resizer);
    }

    public boolean isMaximized() {
        return (getExtendedState() & JFrame.MAXIMIZED_BOTH) == JFrame.MAXIMIZED_BOTH;
    }

    public void toggleMaximize() {
        if (isMaximized()) {
            setExtendedState(JFrame.NORMAL);
        } else {
            setExtendedState(JFrame.MAXIMIZED_BOTH);
        }
        updateWindowShape();
    }

    public void updateWindowShape() {
        try {
            if (isMaximized()) {
                setShape(null);
            } else {
                setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), UITheme.px(20), UITheme.px(20)));
            }
        } catch (Exception ignored) {
        }
    }

    public void showCard(String cardName) {
        cardLayout.show(mainContentPanel, cardName);
    }

    public void tampilkanRingkasan(ModelPendaftaran data) {
        ringkasanPanel.tampilkanData(data);
        showCard("RINGKASAN");
    }

    public void pendaftaranBerhasil(ModelPendaftaran data) {
        TiketDialog tiket = new TiketDialog(this, data);
        tiket.setVisible(true);
    }

    public void resetKeFormAwal() {
        formPanel.resetForm();
        formPanel.setDirty(false);
        showCard("FORM");
    }

    public void keluarAplikasi() {
        if (formPanel.hasUnsavedChanges()) {
            boolean konfirmasi = ModernDialog.tampilkanKonfirmasi(
                    this,
                    "Konfirmasi Keluar",
                    "Formulir belum disubmit atau disimpan.\nApakah Anda yakin ingin keluar dan membuang data isian?",
                    "Keluar",
                    "Batal");
            if (!konfirmasi) {
                return;
            }
        }
        System.exit(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception e) {
            }
            new Main().setVisible(true);
        });
    }
}
