import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class HalamanBantuan extends GradientPanel {

    public HalamanBantuan() {
        super(new BorderLayout(0, UITheme.px(14)));
        setBorder(new EmptyBorder(UITheme.px(16), UITheme.px(20), UITheme.px(16), UITheme.px(20)));

        add(buatHeaderBanner(), BorderLayout.NORTH);

        JPanel pnlKartu = new JPanel();
        pnlKartu.setLayout(new BoxLayout(pnlKartu, BoxLayout.Y_AXIS));
        pnlKartu.setOpaque(false);
        pnlKartu.setBorder(new EmptyBorder(0, 0, UITheme.px(16), UITheme.px(6)));

        pnlKartu.add(buatKartuAlur());
        pnlKartu.add(Box.createVerticalStrut(UITheme.px(14)));
        pnlKartu.add(buatKartuValidasi());
        pnlKartu.add(Box.createVerticalStrut(UITheme.px(14)));
        pnlKartu.add(buatKartuFAQ());
        pnlKartu.add(Box.createVerticalStrut(UITheme.px(14)));
        pnlKartu.add(buatKartuKontak());

        JScrollPane scroll = new JScrollPane(pnlKartu);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        add(scroll, BorderLayout.CENTER);
    }

    private JPanel buatHeaderBanner() {
        JPanel hero = new JPanel(new BorderLayout(UITheme.px(14), 0)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                UITheme.aktifkanAntialias(g2);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, UITheme.px(16), UITheme.px(16));
                g2.setColor(UITheme.BORDER_BLUE);
                g2.setStroke(new BasicStroke(UITheme.fpx(1.5f)));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, UITheme.px(16), UITheme.px(16));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        hero.setOpaque(false);
        hero.setBorder(new EmptyBorder(UITheme.px(14), UITheme.px(18), UITheme.px(14), UITheme.px(18)));

        JLabel lblIcon = new JLabel(new VectorIcon(Glyph.STAR, UITheme.px(34), UITheme.NAVY, Color.WHITE));

        JPanel pnlTeks = new JPanel();
        pnlTeks.setLayout(new BoxLayout(pnlTeks, BoxLayout.Y_AXIS));
        pnlTeks.setOpaque(false);

        JLabel lblJudul = new JLabel("Pusat Bantuan & Panduan Sistem");
        lblJudul.setFont(UITheme.fnt(20, Font.BOLD));
        lblJudul.setForeground(UITheme.NAVY);

        JLabel lblSub = new JLabel("Petunjuk resmi penggunaan formulir pendaftaran, aturan validasi data, ringkasan, dan penerbitan tiket.");
        lblSub.setFont(UITheme.fnt(13, Font.PLAIN));
        lblSub.setForeground(UITheme.TEXT_DARK);

        pnlTeks.add(lblJudul);
        pnlTeks.add(Box.createVerticalStrut(UITheme.px(3)));
        pnlTeks.add(lblSub);

        hero.add(lblIcon, BorderLayout.WEST);
        hero.add(pnlTeks, BorderLayout.CENTER);
        return hero;
    }

    private JPanel buatKartuAlur() {
        JPanel isi = new JPanel();
        isi.setLayout(new BoxLayout(isi, BoxLayout.Y_AXIS));
        isi.setOpaque(false);

        isi.add(buatBarisPanduan("1", "Pengisian Data Identitas Peserta",
                "Lengkapi Nama Lengkap, Nomor Induk Mahasiswa (NIM), Alamat Email aktif, dan Umur peserta. Pilih Universitas dan Program Studi yang sesuai dari daftar pilihan."));
        isi.add(Box.createVerticalStrut(UITheme.px(10)));
        isi.add(buatBarisPanduan("2", "Pemilihan Kegiatan & Mode Kehadiran",
                "Pilih tema kegiatan seminar atau workshop yang ingin diikuti. Tentukan format kehadiran (Online via telekonferensi atau Offline di kampus)."));
        isi.add(Box.createVerticalStrut(UITheme.px(10)));
        isi.add(buatBarisPanduan("3", "Fasilitas & Catatan Khusus",
                "Tandai fasilitas pendukung yang diinginkan (Konsumsi, Sertifikat, Merchandise, Rekaman, Materi, Doorprize). Tambahkan catatan khusus bila diperlukan."));
        isi.add(Box.createVerticalStrut(UITheme.px(10)));
        isi.add(buatBarisPanduan("4", "Pemeriksaan pada Halaman Ringkasan",
                "Tekan tombol 'Lanjutkan' untuk memvalidasi isian. Anda akan diarahkan ke Halaman Ringkasan untuk memeriksa kembali ketepatan data sebelum difinalisasi."));
        isi.add(Box.createVerticalStrut(UITheme.px(10)));
        isi.add(buatBarisPanduan("5", "Penerbitan & Pencetakan E-Tiket",
                "Tekan tombol 'Daftar Sekarang'. Tiket digital resmi akan tampil di layar. Klik tombol 'Cetak Tiket' untuk menyimpannya sebagai berkas gambar PNG beresolusi tinggi."));

        return buatSectionCard("Panduan Alur Pendaftaran", Glyph.DOC, isi);
    }

    private JPanel buatKartuValidasi() {
        JPanel isi = new JPanel();
        isi.setLayout(new BoxLayout(isi, BoxLayout.Y_AXIS));
        isi.setOpaque(false);

        isi.add(buatBarisPoin("Nama Lengkap", "Wajib diisi dan tidak boleh dibiarkan kosong."));
        isi.add(Box.createVerticalStrut(UITheme.px(8)));
        isi.add(buatBarisPoin("Nomor Induk Mahasiswa (NIM)", "Wajib diisi sebagai identifikasi tunggal peserta."));
        isi.add(Box.createVerticalStrut(UITheme.px(8)));
        isi.add(buatBarisPoin("Format Email", "Wajib menggunakan format email yang valid (contoh: nama@domain.com)."));
        isi.add(Box.createVerticalStrut(UITheme.px(8)));
        isi.add(buatBarisPoin("Umur Peserta", "Wajib berupa angka bilangan bulat dalam rentang 15 hingga 100 tahun."));
        isi.add(Box.createVerticalStrut(UITheme.px(8)));
        isi.add(buatBarisPoin("Universitas & Program Studi", "Wajib memilih salah satu pilihan pada menu dropdown yang tersedia."));
        isi.add(Box.createVerticalStrut(UITheme.px(8)));
        isi.add(buatBarisPoin("Pilihan Kegiatan", "Wajib memilih salah satu tema seminar/workshop yang diselenggarakan."));

        return buatSectionCard("Ketentuan & Validasi Data", Glyph.CAP, isi);
    }

    private JPanel buatKartuFAQ() {
        JPanel isi = new JPanel();
        isi.setLayout(new BoxLayout(isi, BoxLayout.Y_AXIS));
        isi.setOpaque(false);

        isi.add(buatItemFAQ(
                "Bagaimana cara memperbaiki data jika ada kesalahan sebelum mendaftar?",
                "Pada Halaman Ringkasan, klik tombol 'Kembali Edit Form'. Semua data yang telah diisi sebelumnya akan tetap utuh dan siap disunting kembali."));
        isi.add(Box.createVerticalStrut(UITheme.px(8)));
        isi.add(buatItemFAQ(
                "Di mana berkas tiket tersimpan setelah menekan tombol Cetak Tiket?",
                "Anda bebas menentukan folder penyimpanan (misalnya folder Dokumen atau Unduhan) melalui jendela penyimpanan berkas yang muncul."));
        isi.add(Box.createVerticalStrut(UITheme.px(8)));
        isi.add(buatItemFAQ(
                "Apakah saya dapat mendaftarkan peserta lain setelah pendaftaran pertama selesai?",
                "Ya, setelah tiket peserta pertama berhasil dicetak atau disimpan, klik tombol 'Tutup' untuk kembali ke formulir dan mendaftarkan peserta baru."));
        isi.add(Box.createVerticalStrut(UITheme.px(8)));
        isi.add(buatItemFAQ(
                "Mengapa muncul peringatan saat menekan tombol Lanjutkan?",
                "Peringatan muncul jika terdapat kolom wajib yang belum diisi atau salah format (seperti Nama kosong, NIM kosong, email tidak valid, atau umur bukan angka)."));

        return buatSectionCard("Pertanyaan Umum (FAQ)", Glyph.PEOPLE, isi);
    }

    private JPanel buatKartuKontak() {
        JPanel isi = new JPanel();
        isi.setLayout(new BoxLayout(isi, BoxLayout.Y_AXIS));
        isi.setOpaque(false);

        isi.add(buatBarisPoin("Sekretariat Panitia", "Gedung Fakultas Teknik Universitas Pasundan, Jl. Dr. Setiabudhi No. 193, Bandung"));
        isi.add(Box.createVerticalStrut(UITheme.px(8)));
        isi.add(buatBarisPoin("Layanan Email", "helpdesk.pp2@unpas.ac.id / nur.243040083@mail.unpas.ac.id"));
        isi.add(Box.createVerticalStrut(UITheme.px(8)));
        isi.add(buatBarisPoin("Hotline WhatsApp", "+62 821-2430-4083 (Senin – Jumat, Pukul 08.00 – 16.00 WIB)"));

        return buatSectionCard("Layanan Bantuan & Narahubung", Glyph.MAIL, isi);
    }

    private JPanel buatSectionCard(String judul, Glyph glyph, JComponent content) {
        JPanel card = new JPanel(new BorderLayout(0, UITheme.px(10))) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                UITheme.aktifkanAntialias(g2);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, UITheme.px(16), UITheme.px(16));
                g2.setColor(new Color(0xD6, 0xE4, 0xF7));
                g2.setStroke(new BasicStroke(UITheme.fpx(1.5f)));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, UITheme.px(16), UITheme.px(16));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        card.setOpaque(false);
        card.setBorder(new EmptyBorder(UITheme.px(14), UITheme.px(18), UITheme.px(16), UITheme.px(18)));

        JPanel pnlHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, UITheme.px(10), 0));
        pnlHeader.setOpaque(false);

        JLabel lblIcon = new JLabel(new VectorIcon(glyph, UITheme.px(22), UITheme.NAVY, Color.WHITE));
        JLabel lblTitle = new JLabel(judul);
        lblTitle.setFont(UITheme.fnt(16, Font.BOLD));
        lblTitle.setForeground(UITheme.NAVY);

        pnlHeader.add(lblIcon);
        pnlHeader.add(lblTitle);

        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(0xEB, 0xF1, 0xFA));

        JPanel pnlTop = new JPanel(new BorderLayout(0, UITheme.px(8)));
        pnlTop.setOpaque(false);
        pnlTop.add(pnlHeader, BorderLayout.NORTH);
        pnlTop.add(sep, BorderLayout.SOUTH);

        card.add(pnlTop, BorderLayout.NORTH);
        card.add(content, BorderLayout.CENTER);
        return card;
    }

    private JPanel buatBarisPanduan(String nomor, String judul, String deskripsi) {
        JPanel p = new JPanel(new BorderLayout(UITheme.px(12), 0));
        p.setOpaque(false);

        JLabel lblNo = new JLabel(nomor, SwingConstants.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                UITheme.aktifkanAntialias(g2);
                g2.setColor(UITheme.BLUE);
                g2.fillOval(0, 0, getWidth() - 1, getHeight() - 1);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        lblNo.setFont(UITheme.fnt(13, Font.BOLD));
        lblNo.setForeground(Color.WHITE);
        int d = UITheme.px(26);
        lblNo.setPreferredSize(new Dimension(d, d));

        JPanel teks = new JPanel();
        teks.setLayout(new BoxLayout(teks, BoxLayout.Y_AXIS));
        teks.setOpaque(false);

        JLabel lJudul = new JLabel(judul);
        lJudul.setFont(UITheme.fnt(14, Font.BOLD));
        lJudul.setForeground(UITheme.TEXT_DARK);

        JLabel lDesk = new JLabel("<html><body style='margin:0; padding:0;'>" + deskripsi + "</body></html>");
        lDesk.setFont(UITheme.fnt(13, Font.PLAIN));
        lDesk.setForeground(new Color(0x40, 0x4D, 0x60));

        teks.add(lJudul);
        teks.add(Box.createVerticalStrut(UITheme.px(2)));
        teks.add(lDesk);

        p.add(lblNo, BorderLayout.WEST);
        p.add(teks, BorderLayout.CENTER);
        return p;
    }

    private JPanel buatBarisPoin(String judul, String isi) {
        JPanel p = new JPanel(new BorderLayout(UITheme.px(8), 0));
        p.setOpaque(false);

        JLabel lblBullet = new JLabel("•");
        lblBullet.setFont(UITheme.fnt(16, Font.BOLD));
        lblBullet.setForeground(UITheme.BLUE);

        JPanel teks = new JPanel(new FlowLayout(FlowLayout.LEFT, UITheme.px(4), 0));
        teks.setOpaque(false);

        JLabel lJudul = new JLabel(judul + " : ");
        lJudul.setFont(UITheme.fnt(13, Font.BOLD));
        lJudul.setForeground(UITheme.NAVY);

        JLabel lIsi = new JLabel("<html><body style='margin:0; padding:0;'>" + isi + "</body></html>");
        lIsi.setFont(UITheme.fnt(13, Font.PLAIN));
        lIsi.setForeground(new Color(0x40, 0x4D, 0x60));

        teks.add(lJudul);
        teks.add(lIsi);

        p.add(lblBullet, BorderLayout.WEST);
        p.add(teks, BorderLayout.CENTER);
        return p;
    }

    private JPanel buatItemFAQ(String tanya, String jawab) {
        JPanel p = new JPanel(new BorderLayout(0, UITheme.px(4))) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                UITheme.aktifkanAntialias(g2);
                g2.setColor(new Color(0xF6, 0xF9, 0xFD));
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, UITheme.px(10), UITheme.px(10));
                g2.setColor(new Color(0xDE, 0xEA, 0xF7));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, UITheme.px(10), UITheme.px(10));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        p.setOpaque(false);
        p.setBorder(new EmptyBorder(UITheme.px(10), UITheme.px(14), UITheme.px(10), UITheme.px(14)));

        JLabel lblTanya = new JLabel("T: " + tanya);
        lblTanya.setFont(UITheme.fnt(13, Font.BOLD));
        lblTanya.setForeground(UITheme.NAVY);

        JLabel lblJawab = new JLabel("<html><body style='margin:0; padding:0;'>J: " + jawab + "</body></html>");
        lblJawab.setFont(UITheme.fnt(13, Font.PLAIN));
        lblJawab.setForeground(new Color(0x3B, 0x47, 0x58));

        p.add(lblTanya, BorderLayout.NORTH);
        p.add(lblJawab, BorderLayout.CENTER);
        return p;
    }
}
