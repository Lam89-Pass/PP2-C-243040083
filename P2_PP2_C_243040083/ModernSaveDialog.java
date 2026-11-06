import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ModernSaveDialog extends JDialog {

    private File currentDir;
    private File selectedFile;
    private final RoundedTextField txtNamaFile;
    private final DefaultListModel<FileItem> listModel;
    private final JList<FileItem> fileList;
    private final JLabel lblCurrentPath;
    private Point initialClick;

    public ModernSaveDialog(Window parent, String defaultFileName) {
        super(parent, ModalityType.APPLICATION_MODAL);
        setUndecorated(true);

        try {
            setBackground(new Color(0, 0, 0, 0));
        } catch (Exception ignored) {
        }

        int w = UITheme.px(780);
        int h = UITheme.px(520);
        setSize(w, h);
        setLocationRelativeTo(parent);

        File userHome = new File(System.getProperty("user.home"));
        File desktop = new File(userHome, "Desktop");
        this.currentDir = desktop.exists() ? desktop : userHome;

        JPanel root = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                UITheme.aktifkanAntialias(g2);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, UITheme.px(20), UITheme.px(20));
                g2.setColor(UITheme.NAVY);
                g2.setStroke(new BasicStroke(UITheme.fpx(1.8f)));
                g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, UITheme.px(20), UITheme.px(20));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        root.setOpaque(false);

        JPanel titleBar = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                UITheme.aktifkanAntialias(g2);
                g2.setPaint(new GradientPaint(
                        0, 0, new Color(0x0C, 0x22, 0x4D),
                        0, getHeight(), new Color(0x10, 0x2D, 0x66)
                ));
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() + UITheme.px(10), UITheme.px(20), UITheme.px(20));
                g2.fillRect(0, getHeight() - UITheme.px(10), getWidth(), UITheme.px(10));
                g2.setColor(new Color(0x2B, 0x63, 0xC4, 120));
                g2.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        titleBar.setOpaque(false);
        titleBar.setPreferredSize(new Dimension(w, UITheme.px(42)));
        titleBar.setBorder(new EmptyBorder(0, UITheme.px(16), 0, UITheme.px(16)));

        JPanel pnlTitle = new JPanel(new FlowLayout(FlowLayout.LEFT, UITheme.px(10), UITheme.px(10)));
        pnlTitle.setOpaque(false);
        JLabel lblIco = new JLabel(new VectorIcon(Glyph.DOC, UITheme.px(20), Color.WHITE, UITheme.NAVY));
        JLabel lblTitle = new JLabel("Simpan E-Tiket Kegiatan");
        lblTitle.setFont(UITheme.fnt(15, Font.BOLD));
        lblTitle.setForeground(Color.WHITE);
        pnlTitle.add(lblIco);
        pnlTitle.add(lblTitle);

        JButton btnCloseX = new JButton("✕");
        btnCloseX.setFont(new Font("SansSerif", Font.BOLD, UITheme.px(14)));
        btnCloseX.setForeground(new Color(220, 230, 245));
        btnCloseX.setContentAreaFilled(false);
        btnCloseX.setBorderPainted(false);
        btnCloseX.setFocusPainted(false);
        btnCloseX.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCloseX.addActionListener(e -> dispose());

        titleBar.add(pnlTitle, BorderLayout.WEST);
        titleBar.add(btnCloseX, BorderLayout.EAST);

        MouseAdapter dragAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
            }
        };
        MouseMotionAdapter motionAdapter = new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int thisX = getLocation().x;
                int thisY = getLocation().y;
                int xMoved = e.getX() - initialClick.x;
                int yMoved = e.getY() - initialClick.y;
                setLocation(thisX + xMoved, thisY + yMoved);
            }
        };
        titleBar.addMouseListener(dragAdapter);
        titleBar.addMouseMotionListener(motionAdapter);

        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(UITheme.px(180), 100));
        sidebar.setBackground(new Color(0xF4, 0xF7, 0xFC));
        sidebar.setBorder(new EmptyBorder(UITheme.px(14), UITheme.px(12), UITheme.px(14), UITheme.px(12)));

        JLabel lblLokasi = new JLabel("LOKASI CEPAT");
        lblLokasi.setFont(UITheme.fnt(11, Font.BOLD));
        lblLokasi.setForeground(new Color(0x6A, 0x7E, 0x9B));
        lblLokasi.setAlignmentX(Component.LEFT_ALIGNMENT);

        sidebar.add(lblLokasi);
        sidebar.add(Box.createVerticalStrut(UITheme.px(10)));
        sidebar.add(buatTombolLokasi("Desktop", new File(userHome, "Desktop"), Glyph.DOC));
        sidebar.add(Box.createVerticalStrut(UITheme.px(6)));
        sidebar.add(buatTombolLokasi("Dokumen", new File(userHome, "Documents"), Glyph.BOOK));
        sidebar.add(Box.createVerticalStrut(UITheme.px(6)));
        sidebar.add(buatTombolLokasi("Unduhan", new File(userHome, "Downloads"), Glyph.SEND));
        sidebar.add(Box.createVerticalStrut(UITheme.px(6)));
        sidebar.add(buatTombolLokasi("Gambar", new File(userHome, "Pictures"), Glyph.STAR));
        sidebar.add(Box.createVerticalStrut(UITheme.px(6)));
        sidebar.add(buatTombolLokasi("Beranda", userHome, Glyph.BUILDING));

        JPanel center = new JPanel(new BorderLayout(0, UITheme.px(8)));
        center.setOpaque(false);
        center.setBorder(new EmptyBorder(UITheme.px(10), UITheme.px(12), UITheme.px(10), UITheme.px(14)));

        JPanel pnlPath = new JPanel(new BorderLayout(UITheme.px(8), 0));
        pnlPath.setOpaque(false);

        JButton btnUp = new JButton("⬆ Naik");
        btnUp.setFont(UITheme.fnt(12, Font.BOLD));
        btnUp.setForeground(UITheme.NAVY);
        btnUp.setBackground(Color.WHITE);
        btnUp.setBorder(new LineBorder(UITheme.BORDER_BLUE, 1, true));
        btnUp.setFocusPainted(false);
        btnUp.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnUp.setPreferredSize(new Dimension(UITheme.px(80), UITheme.px(28)));
        btnUp.addActionListener(e -> {
            File parentDir = currentDir.getParentFile();
            if (parentDir != null && parentDir.exists() && parentDir.canRead()) {
                currentDir = parentDir;
                refreshList();
            }
        });

        lblCurrentPath = new JLabel(currentDir.getAbsolutePath());
        lblCurrentPath.setFont(UITheme.fnt(12, Font.PLAIN));
        lblCurrentPath.setForeground(UITheme.TEXT_DARK);
        lblCurrentPath.setBorder(new EmptyBorder(UITheme.px(4), UITheme.px(8), UITheme.px(4), UITheme.px(8)));

        pnlPath.add(btnUp, BorderLayout.WEST);
        pnlPath.add(lblCurrentPath, BorderLayout.CENTER);

        listModel = new DefaultListModel<>();
        fileList = new JList<>(listModel);
        fileList.setFont(UITheme.fnt(13, Font.PLAIN));
        fileList.setFixedCellHeight(UITheme.px(32));
        fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fileList.setCellRenderer(new FileListRenderer());

        fileList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                FileItem item = fileList.getSelectedValue();
                if (item == null) return;
                if (e.getClickCount() == 2) {
                    if (item.file.isDirectory()) {
                        currentDir = item.file;
                        refreshList();
                    }
                } else if (e.getClickCount() == 1) {
                    if (!item.file.isDirectory()) {
                        txtNamaFile.setText(item.file.getName());
                    }
                }
            }
        });

        JScrollPane scrollList = new JScrollPane(fileList);
        scrollList.setBorder(new LineBorder(UITheme.BORDER_BLUE, 1, true));
        scrollList.getViewport().setBackground(Color.WHITE);
        scrollList.getVerticalScrollBar().setUnitIncrement(16);

        center.add(pnlPath, BorderLayout.NORTH);
        center.add(scrollList, BorderLayout.CENTER);

        JPanel splitMiddle = new JPanel(new BorderLayout());
        splitMiddle.setOpaque(false);
        splitMiddle.add(sidebar, BorderLayout.WEST);
        splitMiddle.add(center, BorderLayout.CENTER);

        JPanel footer = new JPanel(new BorderLayout(0, UITheme.px(10)));
        footer.setOpaque(false);
        footer.setBorder(new EmptyBorder(UITheme.px(10), UITheme.px(16), UITheme.px(14), UITheme.px(16)));

        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(0xDB, 0xE7, 0xF7));
        footer.add(sep, BorderLayout.NORTH);

        JPanel pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setOpaque(false);
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(UITheme.px(4), UITheme.px(4), UITheme.px(4), UITheme.px(8));

        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.EAST;
        JLabel lblNama = new JLabel("Nama Berkas :");
        lblNama.setFont(UITheme.fnt(13, Font.BOLD));
        lblNama.setForeground(UITheme.NAVY);
        pnlForm.add(lblNama, c);

        c.gridx = 1;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        txtNamaFile = new RoundedTextField("Nama berkas tiket...");
        txtNamaFile.setText(defaultFileName);
        txtNamaFile.setPreferredSize(new Dimension(UITheme.px(320), UITheme.px(38)));
        pnlForm.add(txtNamaFile, c);

        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.EAST;
        JLabel lblTipe = new JLabel("Tipe Berkas :");
        lblTipe.setFont(UITheme.fnt(13, Font.BOLD));
        lblTipe.setForeground(UITheme.NAVY);
        pnlForm.add(lblTipe, c);

        c.gridx = 1;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        JLabel lblTipeVal = new JLabel("Gambar PNG (*.png)");
        lblTipeVal.setFont(UITheme.fnt(13, Font.PLAIN));
        lblTipeVal.setForeground(UITheme.TEXT_DARK);
        pnlForm.add(lblTipeVal, c);

        JPanel pnlTombol = new JPanel(new FlowLayout(FlowLayout.RIGHT, UITheme.px(12), 0));
        pnlTombol.setOpaque(false);

        RoundedButton btnSimpan = new RoundedButton("Simpan Tiket", Glyph.DOC, true);
        btnSimpan.setFont(UITheme.fnt(15, Font.BOLD));
        btnSimpan.setPreferredSize(new Dimension(UITheme.px(150), UITheme.px(40)));
        btnSimpan.addActionListener(e -> {
            String name = txtNamaFile.getText().trim();
            if (name.isEmpty()) {
                ModernDialog.tampilkanPesan(this, "Perhatian", "Nama berkas tidak boleh kosong!", true);
                return;
            }
            if (!name.toLowerCase().endsWith(".png")) {
                name = name + ".png";
            }
            selectedFile = new File(currentDir, name);
            dispose();
        });

        RoundedButton btnBatal = new RoundedButton("Batal", Glyph.REFRESH, false);
        btnBatal.setFont(UITheme.fnt(15, Font.BOLD));
        btnBatal.setPreferredSize(new Dimension(UITheme.px(120), UITheme.px(40)));
        btnBatal.addActionListener(e -> {
            selectedFile = null;
            dispose();
        });

        pnlTombol.add(btnSimpan);
        pnlTombol.add(btnBatal);

        JPanel footerBottom = new JPanel(new BorderLayout());
        footerBottom.setOpaque(false);
        footerBottom.add(pnlForm, BorderLayout.WEST);
        footerBottom.add(pnlTombol, BorderLayout.EAST);

        footer.add(footerBottom, BorderLayout.CENTER);

        root.add(titleBar, BorderLayout.NORTH);
        root.add(splitMiddle, BorderLayout.CENTER);
        root.add(footer, BorderLayout.SOUTH);

        setContentPane(root);
        try {
            setShape(new RoundRectangle2D.Double(0, 0, w, h, UITheme.px(20), UITheme.px(20)));
        } catch (Exception ignored) {
        }

        refreshList();
    }

    private JButton buatTombolLokasi(String label, File path, Glyph glyph) {
        JButton btn = new JButton(label);
        btn.setIcon(new VectorIcon(glyph, UITheme.px(16), UITheme.NAVY, Color.WHITE));
        btn.setFont(UITheme.fnt(13, Font.PLAIN));
        btn.setForeground(UITheme.NAVY);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setIconTextGap(UITheme.px(8));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setMaximumSize(new Dimension(UITheme.px(160), UITheme.px(32)));
        btn.addActionListener(e -> {
            if (path.exists() && path.isDirectory() && path.canRead()) {
                currentDir = path;
                refreshList();
            }
        });
        return btn;
    }

    private void refreshList() {
        lblCurrentPath.setText(currentDir.getAbsolutePath());
        listModel.clear();

        File[] files = currentDir.listFiles();
        if (files == null) return;

        List<File> dirs = new ArrayList<>();
        List<File> fileItems = new ArrayList<>();

        for (File f : files) {
            if (f.isHidden()) continue;
            if (f.isDirectory()) {
                dirs.add(f);
            } else if (f.getName().toLowerCase().endsWith(".png")) {
                fileItems.add(f);
            }
        }

        dirs.sort((a, b) -> a.getName().compareToIgnoreCase(b.getName()));
        fileItems.sort((a, b) -> a.getName().compareToIgnoreCase(b.getName()));

        for (File d : dirs) {
            listModel.addElement(new FileItem(d, true));
        }
        for (File f : fileItems) {
            listModel.addElement(new FileItem(f, false));
        }
    }

    public static File pilihLokasiSimpan(Window parent, String defaultFileName) {
        ModernSaveDialog dlg = new ModernSaveDialog(parent, defaultFileName);
        dlg.setVisible(true);
        return dlg.selectedFile;
    }

    private static class FileItem {
        final File file;
        final boolean isDir;

        FileItem(File file, boolean isDir) {
            this.file = file;
            this.isDir = isDir;
        }

        @Override
        public String toString() {
            return file.getName();
        }
    }

    private static class FileListRenderer extends DefaultListCellRenderer {
        private final SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy, HH:mm");

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            JPanel p = new JPanel(new BorderLayout(UITheme.px(8), 0));
            p.setBorder(new EmptyBorder(UITheme.px(4), UITheme.px(10), UITheme.px(4), UITheme.px(10)));
            p.setOpaque(true);

            if (isSelected) {
                p.setBackground(new Color(0xDC, 0xE9, 0xFB));
            } else {
                p.setBackground(index % 2 == 0 ? Color.WHITE : new Color(0xF9, 0xFB, 0xFE));
            }

            if (value instanceof FileItem) {
                FileItem item = (FileItem) value;
                String prefix = item.isDir ? "📁  " : "🖼️  ";
                JLabel lblName = new JLabel(prefix + item.file.getName());
                lblName.setFont(UITheme.fnt(13, item.isDir ? Font.BOLD : Font.PLAIN));
                lblName.setForeground(UITheme.TEXT_DARK);

                String dateStr = df.format(new Date(item.file.lastModified()));
                JLabel lblDate = new JLabel(dateStr);
                lblDate.setFont(UITheme.fnt(11, Font.PLAIN));
                lblDate.setForeground(new Color(0x7A, 0x8C, 0xA5));

                p.add(lblName, BorderLayout.WEST);
                p.add(lblDate, BorderLayout.EAST);
            }

            return p;
        }
    }
}
