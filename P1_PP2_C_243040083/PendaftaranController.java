public class PendaftaranController {

    private final FormPendaftaran view;

    public PendaftaranController(FormPendaftaran view) {
        this.view = view;
    }

    public void handleDaftar() {
        ModelPendaftaran data = view.ambilDataForm();
        System.out.println("[INFO] Tombol Daftar diklik. (Sesuai ketentuan tugas 1: data belum disimpan).");
        System.out.println("[DEBUG ISIAN FORMULIR]");
        System.out.println(data.getRingkasan());
    }

    public void handleReset() {
        view.resetForm();
        System.out.println("[INFO] Formulir berhasil dikosongkan (reset).");
    }
}
