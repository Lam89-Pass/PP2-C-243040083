import java.awt.Component;
import java.util.regex.Pattern;

public class PendaftaranController {

    private final FormPendaftaran view;
    private final Main mainFrame;

    public PendaftaranController(FormPendaftaran view, Main mainFrame) {
        this.view = view;
        this.mainFrame = mainFrame;
    }

    public void handleLanjutkan() {
        String nama = view.getTxtNama().getText().trim();
        String nim = view.getTxtNim().getText().trim();
        String email = view.getTxtEmail().getText().trim();
        String umurStr = view.getTxtUmur().getText().trim();
        int idxUniv = view.getCmbUniversitas().getSelectedIndex();
        int idxProdi = view.getCmbProdi().getSelectedIndex();
        int idxKegiatan = view.getCmbKegiatan().getSelectedIndex();

        if (nama.isEmpty()) {
            tampilkanError("Nama Lengkap wajib diisi terlebih dahulu!", view.getTxtNama());
            return;
        }

        if (nim.isEmpty()) {
            tampilkanError("NIM (Nomor Induk Mahasiswa) wajib diisi!", view.getTxtNim());
            return;
        }

        String emailRegex = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+)$";
        if (email.isEmpty() || !Pattern.matches(emailRegex, email)) {
            tampilkanError("Format alamat email belum sesuai atau masih kosong!\nContoh format yang benar: nama@domain.com", view.getTxtEmail());
            return;
        }

        try {
            int umur = Integer.parseInt(umurStr);
            if (umur < 15 || umur > 100) {
                tampilkanError("Umur peserta harus berada dalam rentang 15 hingga 100 tahun!", view.getTxtUmur());
                return;
            }
        } catch (NumberFormatException ex) {
            tampilkanError("Umur peserta wajib berupa angka bilangan bulat!", view.getTxtUmur());
            return;
        }

        if (idxUniv <= 0) {
            tampilkanError("Silakan pilih Perguruan Tinggi / Universitas asal Anda!", view.getCmbUniversitas());
            return;
        }

        if (idxProdi <= 0) {
            tampilkanError("Silakan pilih Program Studi Anda!", view.getCmbProdi());
            return;
        }

        if (idxKegiatan <= 0) {
            tampilkanError("Silakan pilih Kegiatan yang ingin Anda ikuti!", view.getCmbKegiatan());
            return;
        }

        ModelPendaftaran data = view.ambilDataForm();
        view.setDirty(false);
        mainFrame.tampilkanRingkasan(data);
    }

    public void handleReset() {
        view.resetForm();
        view.setDirty(false);
    }

    private void tampilkanError(String pesan, Component kontrol) {
        ModernDialog.tampilkanPesan(view, "Perhatian", pesan, true);
        kontrol.requestFocusInWindow();
    }
}
