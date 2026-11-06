import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;

public class TiketDialog extends JDialog {

    public TiketDialog(Main parent, ModelPendaftaran data) {
        super(parent, "E-Tiket", true);
        setUndecorated(true);
        
        try {
            setBackground(new Color(0, 0, 0, 0));
        } catch (Exception ignored) {
        }
        setSize(parent.getWidth(), parent.getHeight());
        setLocationRelativeTo(parent);

        setContentPane(new PanelTiket(this, parent, data));
    }

    private class PanelTiket extends JPanel {
        private final TiketDialog dialog;
        private final Main mainFrame;
        private final ModelPendaftaran data;
        private final String serialNumber;

        private final RoundedButton btnCetak;
        private final RoundedButton btnTutup;

        public PanelTiket(TiketDialog dialog, Main mainFrame, ModelPendaftaran data) {
            this.dialog = dialog;
            this.mainFrame = mainFrame;
            this.data = data;
            this.serialNumber = "TK-" + System.currentTimeMillis();
            
            setOpaque(false);
            setLayout(null);

            btnCetak = new RoundedButton("Cetak Tiket", Glyph.DOC, true);
            btnCetak.setFont(UITheme.fnt(16, Font.BOLD));
            btnCetak.addActionListener(e -> cetakDanSimpanTiket());

            btnTutup = new RoundedButton("Tutup", Glyph.REFRESH, false);
            btnTutup.setFont(UITheme.fnt(16, Font.BOLD));
            btnTutup.addActionListener(e -> {
                dialog.dispose();
                mainFrame.resetKeFormAwal();
            });
            
            add(btnCetak);
            add(btnTutup);
        }

        @Override
        public void doLayout() {
            super.doLayout();
            int w = Math.min(UITheme.px(820), getWidth() - UITheme.px(60));
            int h = Math.min(UITheme.px(310), getHeight() - UITheme.px(170));
            int yTicket = (getHeight() - h) / 2 - UITheme.px(30);

            int btnW = UITheme.px(210);
            int btnH = UITheme.px(46);
            int gap = UITheme.px(20);
            int totalW = (btnW * 2) + gap;

            int startX = (getWidth() - totalW) / 2;
            int btnY = yTicket + h + UITheme.px(25);

            btnCetak.setBounds(startX, btnY, btnW, btnH);
            btnTutup.setBounds(startX + btnW + gap, btnY, btnW, btnH);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            UITheme.aktifkanAntialias(g2);

            g2.setColor(new Color(0, 0, 0, 175));
            g2.fillRect(0, 0, getWidth(), getHeight());

            int w = Math.min(UITheme.px(820), getWidth() - UITheme.px(60));
            int h = Math.min(UITheme.px(310), getHeight() - UITheme.px(170));
            int x = (getWidth() - w) / 2;
            int y = (getHeight() - h) / 2 - UITheme.px(30);

            g2.setColor(new Color(0, 0, 0, 80));
            g2.fillRoundRect(x + 7, y + 7, w, h, UITheme.px(22), UITheme.px(22));

            gambarKontenTiket(g2, x, y, w, h, false);

            g2.dispose();
        }

        private void gambarKontenTiket(Graphics2D g2, int x, int y, int w, int h, boolean forExport) {
            UITheme.aktifkanAntialias(g2);

            g2.setColor(Color.WHITE);
            g2.fillRoundRect(x, y, w, h, UITheme.px(22), UITheme.px(22));

            Shape clipAsli = g2.getClip();
            g2.setClip(new RoundRectangle2D.Double(x, y, w, h, UITheme.px(22), UITheme.px(22)));
            g2.setColor(UITheme.BLUE);
            g2.fillRect(x, y, UITheme.px(18), h);
            g2.setClip(clipAsli);

            int splitX = x + (int) (w * 0.72);
            int r = UITheme.px(34);

            if (forExport) {
                Composite compLama = g2.getComposite();
                g2.setComposite(AlphaComposite.Clear);
                g2.fillOval(splitX - r / 2, y - r / 2, r, r);
                g2.fillOval(splitX - r / 2, y + h - r / 2, r, r);
                g2.setComposite(compLama);

                g2.setColor(new Color(210, 225, 245));
                g2.setStroke(new BasicStroke(UITheme.fpx(1.5f)));
                g2.drawArc(splitX - r / 2, y - r / 2, r, r, 180, 180);
                g2.drawArc(splitX - r / 2, y + h - r / 2, r, r, 0, 180);
            } else {
                g2.setColor(new Color(0, 0, 0, 175));
                g2.fillOval(splitX - r / 2, y - r / 2, r, r);
                g2.fillOval(splitX - r / 2, y + h - r / 2, r, r);
            }

            Stroke dashed = new BasicStroke(UITheme.fpx(2.5f), BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
            g2.setStroke(dashed);
            g2.setColor(new Color(205, 218, 235));
            g2.drawLine(splitX, y + r / 2 + 5, splitX, y + h - r / 2 - 5);

            g2.setColor(UITheme.NAVY);
            g2.setFont(UITheme.fnt(23, Font.BOLD));
            g2.drawString("E-TIKET MASUK RESMI", x + UITheme.px(42), y + UITheme.px(48));

            g2.setColor(UITheme.BLUE);
            g2.setFont(UITheme.fnt(16, Font.BOLD));
            String acara = (data != null && data.getKegiatan() != null) ? data.getKegiatan() : "-";
            if (acara.length() > 55) acara = acara.substring(0, 55) + "...";
            g2.drawString(acara, x + UITheme.px(42), y + UITheme.px(80));

            g2.setColor(new Color(0x60, 0x72, 0x8C));
            g2.setFont(UITheme.fnt(13, Font.PLAIN));
            int textY = y + UITheme.px(130);
            int gap = UITheme.px(30);

            g2.drawString("NAMA LENGKAP", x + UITheme.px(42), textY);
            g2.drawString("NIM / ID", x + UITheme.px(330), textY);
            
            g2.setColor(UITheme.TEXT_DARK);
            g2.setFont(UITheme.fnt(16, Font.BOLD));
            String nama = (data != null && data.getNama() != null) ? data.getNama().toUpperCase() : "-";
            String nim = (data != null && data.getNim() != null) ? data.getNim() : "-";
            g2.drawString(nama, x + UITheme.px(42), textY + UITheme.px(22));
            g2.drawString(nim, x + UITheme.px(330), textY + UITheme.px(22));

            g2.setColor(new Color(0x60, 0x72, 0x8C));
            g2.setFont(UITheme.fnt(13, Font.PLAIN));
            g2.drawString("ASAL INSTANSI", x + UITheme.px(42), textY + gap * 2);
            g2.drawString("STATUS KEHADIRAN", x + UITheme.px(330), textY + gap * 2);

            g2.setColor(UITheme.TEXT_DARK);
            g2.setFont(UITheme.fnt(16, Font.BOLD));
            String univ = (data != null && data.getUniversitas() != null) ? data.getUniversitas() : "-";
            if (univ.length() > 32) univ = univ.substring(0, 32) + "...";
            g2.drawString(univ, x + UITheme.px(42), textY + gap * 2 + UITheme.px(22));
            
            String mode = (data != null && data.getModeKehadiran() != null) ? data.getModeKehadiran() : "Online";
            boolean isOnline = mode.equalsIgnoreCase("Online");
            g2.setColor(isOnline ? new Color(40, 167, 69) : new Color(220, 53, 69));
            g2.fillRoundRect(x + UITheme.px(330), textY + gap * 2 + UITheme.px(6), UITheme.px(95), UITheme.px(24), UITheme.px(10), UITheme.px(10));
            g2.setColor(Color.WHITE);
            g2.setFont(UITheme.fnt(13, Font.BOLD));
            g2.drawString(mode.toUpperCase(), x + UITheme.px(346), textY + gap * 2 + UITheme.px(23));

            g2.setColor(new Color(110, 125, 145));
            g2.setFont(UITheme.fnt(12, Font.PLAIN));
            g2.drawString("* Tunjukkan E-Tiket ini kepada panitia saat registrasi ulang.", x + UITheme.px(42), y + h - UITheme.px(20));

            g2.setColor(UITheme.NAVY);
            g2.setFont(UITheme.fnt(19, Font.BOLD));
            String admit = "ADMIT ONE";
            FontMetrics fm = g2.getFontMetrics();
            int admitW = fm.stringWidth(admit);
            int rightPaneCenter = splitX + (w - (splitX - x)) / 2;
            g2.drawString(admit, rightPaneCenter - admitW / 2, y + UITheme.px(48));

            g2.setColor(UITheme.TEXT_DARK);
            int bcX = splitX + UITheme.px(28);
            int bcY = y + UITheme.px(76);
            int bcW = w - (splitX - x) - UITheme.px(56);
            int bcH = UITheme.px(110);
            
            int currX = bcX;
            int[] widths = {3, 1, 4, 2, 1, 5, 2, 2, 1, 4, 1, 2, 1, 3, 2, 5, 1, 2, 3, 2, 4, 1};
            for (int bw : widths) {
                int scaledW = UITheme.px(bw);
                if (currX + scaledW > bcX + bcW) break;
                g2.fillRect(currX, bcY, scaledW, bcH);
                currX += scaledW + UITheme.px(2);
            }

            g2.setFont(UITheme.fnt(13, Font.BOLD));
            g2.setColor(UITheme.NAVY);
            int serialW = g2.getFontMetrics().stringWidth(serialNumber);
            g2.drawString(serialNumber, rightPaneCenter - serialW / 2, bcY + bcH + UITheme.px(25));
        }

        private void cetakDanSimpanTiket() {
            int w = UITheme.px(820);
            int h = UITheme.px(310);
            int pad = UITheme.px(30);
            int scale = 2;

            int imgW = (w + pad * 2) * scale;
            int imgH = (h + pad * 2) * scale;

            BufferedImage img = new BufferedImage(imgW, imgH, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = img.createGraphics();
            UITheme.aktifkanAntialias(g2);
            g2.scale(scale, scale);

            g2.setColor(new Color(0, 0, 0, 50));
            g2.fillRoundRect(pad + 6, pad + 6, w, h, UITheme.px(22), UITheme.px(22));

            gambarKontenTiket(g2, pad, pad, w, h, true);
            g2.dispose();

            String namaPeserta = (data != null && data.getNama() != null)
                    ? data.getNama().trim().replaceAll("[^a-zA-Z0-9.-]", "_")
                    : "Peserta";
            String nimPeserta = (data != null && data.getNim() != null)
                    ? data.getNim().trim().replaceAll("[^a-zA-Z0-9.-]", "_")
                    : "";

            String defaultFilename = "E-Tiket_" + (nimPeserta.isEmpty() ? "" : nimPeserta + "_") + namaPeserta + ".png";

            File targetFile = ModernSaveDialog.pilihLokasiSimpan(dialog, defaultFilename);
            if (targetFile != null) {
                if (!targetFile.getName().toLowerCase().endsWith(".png")) {
                    targetFile = new File(targetFile.getParentFile(), targetFile.getName() + ".png");
                }

                try {
                    ImageIO.write(img, "png", targetFile);

                    int pilihan = ModernDialog.tampilkanOpsi(
                            dialog,
                            "Tiket Berhasil Disimpan",
                            "Berkas E-Tiket Anda telah tersimpan dengan aman di:\n" + targetFile.getAbsolutePath() +
                            "\n\nApakah Anda ingin membuka berkas gambar sekarang?",
                            new String[]{"Buka Tiket", "Selesai"}
                    );

                    if (pilihan == 0) {
                        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.OPEN)) {
                            Desktop.getDesktop().open(targetFile);
                        }
                    }

                } catch (Exception ex) {
                    ModernDialog.tampilkanPesan(dialog, "Error Penyimpanan", "Gagal menyimpan tiket: " + ex.getMessage(), true);
                }
            }
        }
    }
}
