import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.KeyboardFocusManager;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;

public class FormPendaftaran extends JFrame {

    private static final String[] DAFTAR_UNIVERSITAS = {
        "-- Pilih Perguruan Tinggi --",
        "Institut Teknologi Bandung",
        "Universitas Pasundan",
        "Universitas Gadjah Mada",
        "Universitas Indonesia",
        "Telkom University",
        "Universitas Padjadjaran",
        "Universitas Pendidikan Indonesia",
        "Bina Nusantara (BINUS University)",
        "Universitas Komputer Indonesia",
        "Universitas Islam Bandung",
        "Universitas Airlangga",
        "Institut Teknologi Sepuluh Nopember",
        "Universitas Diponegoro",
        "Universitas Brawijaya",
        "Lainnya / Perguruan Tinggi Lain"
    };

    private static final String[] DAFTAR_PRODI = {
        "-- Pilih Program Studi --",
        // Fakultas Teknik
        "Teknik Informatika",
        "Teknik Industri",
        "Teknik Mesin",
        "Teknik Elektro",
        "Teknik Sipil",
        "Teknologi Pangan",
        "Teknik Komputer",
        "Teknik Nuklir",
        "Teknik Kimia",

        // Fakultas Lainnya
        "Manajemen",
        "Akuntansi",
        "Ekonomi Pembangunan",
        "Bisnis Digital",
        "Pendidikan Guru SD - PGSD",
        "Pendidikan Matematika",
        "Pendidikan Bahasa Indonesia",
        "Pendidikan Bahasa Inggris",
        "Pendidikan Biologi",
        "Pendidikan Ekonomi",
        "Ilmu Komunikasi",
        "Hubungan Internasional",
        "Ilmu Hukum",
        "Psikologi",
        "Desain Komunikasi Visual"
    };

    private static final String[] DAFTAR_KEGIATAN = {
        // Teknologi
        "Seminar Nasional: Tren AI & Generative Intelligence (Teknologi)",
        "Workshop Java: Modern Software Architecture & Spring Boot (Teknologi)",
        "Lomba Pemrograman & Algoritma Competitive Programming (Teknologi)",
        "Workshop Cyber Security & Ethical Hacking (Teknologi)",
        "Pelatihan Cloud Computing & Data Engineering (Teknologi)",

        // Bisnis
        "Seminar Bisnis: Strategi Scale-Up Startup & Digital Marketing (Bisnis)",
        "Workshop Financial Planning & Investment bagi Gen-Z (Bisnis)",
        "Lomba Business Plan & Pitching Competition (Bisnis)",
        "Seminar Akuntansi Forensik & Tata Kelola Keuangan Digital (Bisnis)",

        // Keguruan & Edukasi
        "Seminar Nasional Inovasi Model Pembelajaran Abad 21 (Keguruan)",
        "Workshop Implementasi Kurikulum Merdeka & Gamifikasi Edukasi (Keguruan)",
        "Pelatihan Pembuatan Media Pembelajaran Interaktif Berbasis AI (Keguruan)",
        "Seminar Psikologi Pendidikan & Karakter Generasi Emas (Keguruan)"
    };

    private RoundedTextField txtNama;
    private RoundedTextField txtNim;
    private RoundedTextField txtEmail;
    private JComboBox<String> cmbUniversitas;
    private JComboBox<String> cmbProdi;
    private JComboBox<String> cmbKegiatan;
    private JRadioButton rbOnline;
    private JRadioButton rbOffline;
    private ButtonGroup groupMode;
    private JCheckBox chkKonsumsi;
    private JCheckBox chkSertifikat;
    private JCheckBox chkMateri;
    private JCheckBox chkMerchandise;
    private JCheckBox chkRekaman;
    private JCheckBox chkDoorprize;
    private PlaceholderTextArea txtCatatan;
    private RoundedButton btnDaftar;
    private RoundedButton btnReset;

    private final Timer timerResize;
    private final PendaftaranController controller;

    public FormPendaftaran() {
        super("Formulir Pendaftaran Kegiatan");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.controller = new PendaftaranController(this);

        Dimension layar = Toolkit.getDefaultToolkit().getScreenSize();
        int w = Math.min(layar.width, Math.max(1150, (int) (layar.width * 0.60)));
        int h = Math.min(layar.height, Math.max(820, (int) (layar.height * 0.80)));
        UITheme.setSkala(UITheme.hitungSkala(w, h));

        initComponents();
        setSize(w, h);
        setMinimumSize(new Dimension(1050, 750));
        setLocationRelativeTo(null);

        timerResize = new Timer(150, e -> sesuaikanSkala());
        timerResize.setRepeats(false);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                timerResize.restart();
            }
        });
    }

    private void initComponents() {
        JPanel root = new JPanel(new BorderLayout());
        root.add(new SidebarPanel(), BorderLayout.WEST);
        root.add(buatPanelKonten(), BorderLayout.CENTER);
        setContentPane(root);
        getRootPane().setDefaultButton(btnDaftar);
    }

    private Component[] daftarKomponen() {
        return new Component[] {
            txtNama, txtNim, txtEmail, cmbUniversitas, cmbProdi,
            cmbKegiatan, rbOnline, rbOffline,
            chkKonsumsi, chkSertifikat, chkMateri, chkMerchandise, chkRekaman, chkDoorprize,
            txtCatatan, btnDaftar, btnReset
        };
    }

    private void sesuaikanSkala() {
        float baru = UITheme.hitungSkala(getWidth(), getHeight());
        if (baru == UITheme.getSkala()) {
            return;
        }

        ModelPendaftaran dataSementara = ambilDataForm();
        int idxUniv = cmbUniversitas.getSelectedIndex();
        int idxProdi = cmbProdi.getSelectedIndex();
        int idxKegiatan = cmbKegiatan.getSelectedIndex();

        Component fokusLama = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
        int idxFokus = -1;
        Component[] lama = daftarKomponen();
        for (int i = 0; i < lama.length; i++) {
            if (lama[i] == fokusLama) {
                idxFokus = i;
                break;
            }
        }

        UITheme.setSkala(baru);
        initComponents();

        txtNama.setText(dataSementara.getNama());
        txtNim.setText(dataSementara.getNim());
        txtEmail.setText(dataSementara.getEmail());
        cmbUniversitas.setSelectedIndex(idxUniv);
        cmbProdi.setSelectedIndex(idxProdi);
        cmbKegiatan.setSelectedIndex(idxKegiatan);
        txtCatatan.setText(dataSementara.getCatatan());

        boolean online = "Online".equalsIgnoreCase(dataSementara.getModeKehadiran());
        rbOnline.setSelected(online);
        rbOffline.setSelected(!online);

        List<String> fasilitas = dataSementara.getFasilitas();
        chkKonsumsi.setSelected(fasilitas.contains("Konsumsi"));
        chkSertifikat.setSelected(fasilitas.contains("Sertifikat"));
        chkMateri.setSelected(fasilitas.contains("Materi Digital"));
        chkMerchandise.setSelected(fasilitas.contains("Merchandise"));
        chkRekaman.setSelected(fasilitas.contains("Akses Rekaman Video"));
        chkDoorprize.setSelected(fasilitas.contains("Kupon Doorprize"));

        revalidate();
        repaint();

        if (idxFokus >= 0) {
            final Component target = daftarKomponen()[idxFokus];
            SwingUtilities.invokeLater(() -> target.requestFocusInWindow());
        }
    }

    // Panel Konten (Area Kanan)
    private JPanel buatPanelKonten() {
        GradientPanel konten = new GradientPanel(new BorderLayout(0, UITheme.px(10)));
        konten.setBorder(new EmptyBorder(UITheme.px(14), UITheme.px(18), UITheme.px(12), UITheme.px(18)));

        JPanel isi = new JPanel(new GridBagLayout());
        isi.setOpaque(false);
        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 0;
        g.weightx = 1.0;
        g.fill = GridBagConstraints.BOTH;
        g.insets = new Insets(0, 0, UITheme.px(10), 0);

        g.gridy = 0; g.weighty = 0;
        isi.add(buatPanelIdentitas(), g);

        g.gridy = 1; g.weighty = 0;
        isi.add(buatPanelKegiatan(), g);

        g.gridy = 2; g.weighty = 1.0;
        g.insets = new Insets(0, 0, 0, 0);
        isi.add(buatPanelCatatan(), g);

        konten.add(isi, BorderLayout.CENTER);
        konten.add(buatPanelFooter(), BorderLayout.SOUTH);
        return konten;
    }

    private JPanel buatPanelIdentitas() {
        SectionPanel p = new SectionPanel("Identitas Peserta", Glyph.PERSON);
        p.setLayout(new GridBagLayout());

        txtNama = new RoundedTextField("Masukkan nama lengkap...");
        txtNim = new RoundedTextField("Masukkan NIM...");
        txtEmail = new RoundedTextField("Masukkan email aktif...");
        cmbUniversitas = buatComboBox(DAFTAR_UNIVERSITAS);
        cmbProdi = buatComboBox(DAFTAR_PRODI);

        tambahBaris(p, 0, buatLabel(Glyph.PERSON, "Nama Lengkap :", KeyEvent.VK_N, txtNama), txtNama);
        tambahBaris(p, 1, buatLabel(Glyph.ID, "NIM :", KeyEvent.VK_I, txtNim), txtNim);
        tambahBaris(p, 2, buatLabel(Glyph.MAIL, "Email :", KeyEvent.VK_E, txtEmail), txtEmail);
        tambahBaris(p, 3, buatLabel(Glyph.BUILDING, "Universitas :", KeyEvent.VK_U, cmbUniversitas), cmbUniversitas);
        tambahBaris(p, 4, buatLabel(Glyph.BOOK, "Program Studi :", KeyEvent.VK_P, cmbProdi), cmbProdi);
        return p;
    }

    private JPanel buatPanelKegiatan() {
        SectionPanel p = new SectionPanel("Pilihan Kegiatan", Glyph.CALENDAR);
        p.setLayout(new GridBagLayout());

        cmbKegiatan = buatComboBox(DAFTAR_KEGIATAN);

        // Mode kehadiran
        rbOnline = buatRadio("Online", true);
        rbOffline = buatRadio("Offline", false);
        groupMode = new ButtonGroup();
        groupMode.add(rbOnline);
        groupMode.add(rbOffline);

        JPanel panelRadio = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, UITheme.px(4)));
        panelRadio.setOpaque(false);
        panelRadio.add(rbOnline);
        panelRadio.add(Box.createHorizontalStrut(UITheme.px(28)));
        panelRadio.add(rbOffline);

        // Fasilitas tambahan
        chkKonsumsi = buatCheck("Konsumsi");
        chkSertifikat = buatCheck("Sertifikat");
        chkMateri = buatCheck("Materi Digital");
        chkMerchandise = buatCheck("Merchandise");
        chkRekaman = buatCheck("Akses Rekaman");
        chkDoorprize = buatCheck("Doorprize");

        JPanel panelCek = new JPanel(new GridLayout(2, 3, UITheme.px(16), UITheme.px(6)));
        panelCek.setOpaque(false);
        panelCek.add(chkKonsumsi);
        panelCek.add(chkSertifikat);
        panelCek.add(chkMateri);
        panelCek.add(chkMerchandise);
        panelCek.add(chkRekaman);
        panelCek.add(chkDoorprize);

        tambahBaris(p, 0, buatLabel(Glyph.CAP, "Kegiatan :", KeyEvent.VK_K, cmbKegiatan), cmbKegiatan);
        tambahBaris(p, 1, buatLabel(Glyph.CLOCK, "Mode Kehadiran :", KeyEvent.VK_M, rbOnline), panelRadio);
        tambahBaris(p, 2, buatLabel(Glyph.STAR, "Fasilitas :", KeyEvent.VK_F, chkKonsumsi), panelCek);
        return p;
    }

    private JPanel buatPanelCatatan() {
        SectionPanel p = new SectionPanel("Catatan", Glyph.DOC);
        p.setLayout(new BorderLayout());

        txtCatatan = new PlaceholderTextArea("Tambahkan catatan jika ada...");
        txtCatatan.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,
                Collections.singleton(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0)));
        txtCatatan.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS,
                Collections.singleton(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, InputEvent.SHIFT_DOWN_MASK)));

        JScrollPane scroll = new JScrollPane(txtCatatan);
        scroll.setBorder(new LineBorder(UITheme.BORDER_BLUE, 1, true));
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setPreferredSize(new Dimension(100, UITheme.px(80)));
        p.add(scroll, BorderLayout.CENTER);
        return p;
    }

    private JPanel buatPanelFooter() {
        JPanel footer = new JPanel(new BorderLayout(0, UITheme.px(10)));
        footer.setOpaque(false);

        JSeparator garis = new JSeparator();
        garis.setForeground(UITheme.BORDER_BLUE);
        footer.add(garis, BorderLayout.NORTH);

        JLabel slogan = new JLabel("Mari wujudkan pengalaman terbaik bersama kami!",
                new VectorIcon(Glyph.PEOPLE, UITheme.px(28), UITheme.BLUE, Color.WHITE), SwingConstants.LEFT);
        slogan.setFont(UITheme.fnt(13, Font.PLAIN));
        slogan.setForeground(UITheme.TEXT_DARK);
        slogan.setIconTextGap(UITheme.px(12));

        btnDaftar = new RoundedButton("Daftar", Glyph.SEND, true);
        btnReset = new RoundedButton("Reset", Glyph.REFRESH, false);

        btnDaftar.addActionListener(e -> controller.handleDaftar());
        btnReset.addActionListener(e -> controller.handleReset());

        JPanel tombol = new JPanel(new FlowLayout(FlowLayout.RIGHT, UITheme.px(12), 0));
        tombol.setOpaque(false);
        tombol.add(btnDaftar);
        tombol.add(btnReset);

        JPanel bawah = new JPanel(new BorderLayout(UITheme.px(12), 0));
        bawah.setOpaque(false);
        bawah.add(slogan, BorderLayout.WEST);
        bawah.add(tombol, BorderLayout.EAST);
        footer.add(bawah, BorderLayout.CENTER);
        return footer;
    }

    public ModelPendaftaran ambilDataForm() {
        List<String> fasilitas = new ArrayList<>();
        if (chkKonsumsi.isSelected()) fasilitas.add("Konsumsi");
        if (chkSertifikat.isSelected()) fasilitas.add("Sertifikat");
        if (chkMateri.isSelected()) fasilitas.add("Materi Digital");
        if (chkMerchandise.isSelected()) fasilitas.add("Merchandise");
        if (chkRekaman.isSelected()) fasilitas.add("Akses Rekaman Video");
        if (chkDoorprize.isSelected()) fasilitas.add("Kupon Doorprize");

        String univ = cmbUniversitas.getSelectedItem() != null ? cmbUniversitas.getSelectedItem().toString() : "";
        String prodi = cmbProdi.getSelectedItem() != null ? cmbProdi.getSelectedItem().toString() : "";
        String kegiatan = cmbKegiatan.getSelectedItem() != null ? cmbKegiatan.getSelectedItem().toString() : "";
        String mode = rbOnline.isSelected() ? "Online" : "Offline";

        return new ModelPendaftaran(
            txtNama.getText(),
            txtNim.getText(),
            txtEmail.getText(),
            univ,
            prodi,
            kegiatan,
            mode,
            fasilitas,
            txtCatatan.getText()
        );
    }

    public void resetForm() {
        txtNama.setText("");
        txtNim.setText("");
        txtEmail.setText("");
        cmbUniversitas.setSelectedIndex(0);
        cmbProdi.setSelectedIndex(0);
        cmbKegiatan.setSelectedIndex(0);
        rbOnline.setSelected(true);
        chkKonsumsi.setSelected(false);
        chkSertifikat.setSelected(false);
        chkMateri.setSelected(false);
        chkMerchandise.setSelected(false);
        chkRekaman.setSelected(false);
        chkDoorprize.setSelected(false);
        txtCatatan.setText("");
        txtNama.requestFocusInWindow();
    }

    public RoundedTextField getTxtNama() { return txtNama; }
    public RoundedTextField getTxtNim() { return txtNim; }
    public RoundedTextField getTxtEmail() { return txtEmail; }
    public JComboBox<String> getCmbUniversitas() { return cmbUniversitas; }
    public JComboBox<String> getCmbProdi() { return cmbProdi; }
    public JComboBox<String> getCmbKegiatan() { return cmbKegiatan; }
    public JRadioButton getRbOnline() { return rbOnline; }
    public JRadioButton getRbOffline() { return rbOffline; }
    public JCheckBox getChkKonsumsi() { return chkKonsumsi; }
    public JCheckBox getChkSertifikat() { return chkSertifikat; }
    public JCheckBox getChkMateri() { return chkMateri; }
    public JCheckBox getChkMerchandise() { return chkMerchandise; }
    public JCheckBox getChkRekaman() { return chkRekaman; }
    public JCheckBox getChkDoorprize() { return chkDoorprize; }
    public PlaceholderTextArea getTxtCatatan() { return txtCatatan; }
    public RoundedButton getBtnDaftar() { return btnDaftar; }
    public RoundedButton getBtnReset() { return btnReset; }

    private JComboBox<String> buatComboBox(String[] data) {
        JComboBox<String> cmb = new JComboBox<>(data);
        cmb.setUI(new BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                JButton b = new JButton(new PanahIcon());
                b.setBorder(new EmptyBorder(0, UITheme.px(12), 0, UITheme.px(12)));
                b.setContentAreaFilled(false);
                b.setFocusPainted(false);
                return b;
            }

            @Override
            public void paintCurrentValueBackground(java.awt.Graphics g, Rectangle bounds, boolean hasFocus) {
                g.setColor(Color.WHITE);
                g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
            }
        });
        cmb.setFont(UITheme.fnt(15, Font.PLAIN));
        cmb.setBackground(Color.WHITE);
        cmb.setForeground(UITheme.TEXT_DARK);
        cmb.setBorder(new LineBorder(UITheme.BORDER_BLUE, 1, true));
        cmb.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                    boolean selected, boolean fokus) {
                JLabel l = (JLabel) super.getListCellRendererComponent(list, value, index, selected, fokus);
                l.setBorder(new EmptyBorder(UITheme.px(4), UITheme.px(10), UITheme.px(4), UITheme.px(10)));
                l.setFont(UITheme.fnt(15, Font.PLAIN));
                if (index < 0) {
                    l.setOpaque(true);
                    l.setBackground(Color.WHITE);
                    l.setForeground(UITheme.TEXT_DARK);
                }
                return l;
            }
        });
        cmb.setPreferredSize(new Dimension(UITheme.px(260), UITheme.px(40)));
        return cmb;
    }

    private JLabel buatLabel(Glyph glyph, String teks, int mnemonic, Component target) {
        JLabel l = new JLabel(teks, new VectorIcon(glyph, UITheme.px(24), UITheme.BLUE, Color.WHITE), SwingConstants.LEFT);
        l.setFont(UITheme.fnt(15, Font.PLAIN));
        l.setForeground(UITheme.TEXT_DARK);
        l.setIconTextGap(UITheme.px(12));
        l.setLabelFor(target);
        l.setDisplayedMnemonic(mnemonic);
        l.setPreferredSize(new Dimension(UITheme.px(205), UITheme.px(30)));
        return l;
    }

    private void tambahBaris(JPanel p, int baris, JLabel label, Component input) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridy = baris;
        c.insets = new Insets(UITheme.px(4), UITheme.px(4), UITheme.px(4), UITheme.px(10));
        c.anchor = GridBagConstraints.WEST;

        c.gridx = 0; c.weightx = 0; c.fill = GridBagConstraints.NONE;
        p.add(label, c);

        c.gridx = 1; c.weightx = 1.0; c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(UITheme.px(4), 0, UITheme.px(4), UITheme.px(4));
        p.add(input, c);
    }

    private JRadioButton buatRadio(String teks, boolean dipilih) {
        JRadioButton rb = new JRadioButton(teks, dipilih);
        rb.setOpaque(false);
        rb.setFont(UITheme.fnt(15, Font.PLAIN));
        rb.setForeground(UITheme.TEXT_DARK);
        rb.setIcon(new RadioIcon());
        rb.setIconTextGap(UITheme.px(8));
        return rb;
    }

    private JCheckBox buatCheck(String teks) {
        JCheckBox cb = new JCheckBox(teks);
        cb.setOpaque(false);
        cb.setFont(UITheme.fnt(15, Font.PLAIN));
        cb.setForeground(UITheme.TEXT_DARK);
        cb.setIcon(new CheckIcon());
        cb.setIconTextGap(UITheme.px(8));
        return cb;
    }
}