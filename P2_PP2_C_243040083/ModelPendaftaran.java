import java.util.ArrayList;
import java.util.List;

public class ModelPendaftaran {
    private String nama;
    private String nim;
    private String email;
    private int umur;
    private String universitas;
    private String programStudi;
    private String kegiatan;
    private String modeKehadiran;
    private List<String> fasilitas;
    private String catatan;

    public ModelPendaftaran() {
        this.fasilitas = new ArrayList<>();
    }

    public ModelPendaftaran(String nama, String nim, String email, int umur, String universitas, String programStudi,
            String kegiatan, String modeKehadiran, List<String> fasilitas, String catatan) {
        this.nama = nama;
        this.nim = nim;
        this.email = email;
        this.umur = umur;
        this.universitas = universitas;
        this.programStudi = programStudi;
        this.kegiatan = kegiatan;
        this.modeKehadiran = modeKehadiran;
        this.fasilitas = fasilitas != null ? fasilitas : new ArrayList<>();
        this.catatan = catatan;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUmur() {
        return umur;
    }

    public void setUmur(int umur) {
        this.umur = umur;
    }

    public String getUniversitas() {
        return universitas;
    }

    public void setUniversitas(String universitas) {
        this.universitas = universitas;
    }

    public String getProgramStudi() {
        return programStudi;
    }

    public void setProgramStudi(String programStudi) {
        this.programStudi = programStudi;
    }

    public String getKegiatan() {
        return kegiatan;
    }

    public void setKegiatan(String kegiatan) {
        this.kegiatan = kegiatan;
    }

    public String getModeKehadiran() {
        return modeKehadiran;
    }

    public void setModeKehadiran(String modeKehadiran) {
        this.modeKehadiran = modeKehadiran;
    }

    public List<String> getFasilitas() {
        return fasilitas;
    }

    public void setFasilitas(List<String> fasilitas) {
        this.fasilitas = fasilitas;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }

    public String getRingkasan() {
        String daftarFasilitas = (fasilitas == null || fasilitas.isEmpty()) ? "-" : String.join(", ", fasilitas);
        String txtCatatan = (catatan == null || catatan.trim().isEmpty()) ? "-" : catatan.trim();

        return "Nama Lengkap    : " + (nama != null && !nama.isEmpty() ? nama : "-") + "\n"
                + "NIM             : " + (nim != null && !nim.isEmpty() ? nim : "-") + "\n"
                + "Email           : " + (email != null && !email.isEmpty() ? email : "-") + "\n"
                + "Umur            : " + umur + " Tahun\n"
                + "Universitas     : " + (universitas != null && !universitas.isEmpty() ? universitas : "-") + "\n"
                + "Program Studi   : " + (programStudi != null && !programStudi.isEmpty() ? programStudi : "-") + "\n"
                + "Kegiatan        : " + (kegiatan != null && !kegiatan.isEmpty() ? kegiatan : "-") + "\n"
                + "Mode Kehadiran  : " + (modeKehadiran != null && !modeKehadiran.isEmpty() ? modeKehadiran : "-")
                + "\n"
                + "Fasilitas       : " + daftarFasilitas + "\n"
                + "Catatan         : " + txtCatatan;
    }

    @Override
    public String toString() {
        return "ModelPendaftaran{" +
                "nama='" + nama + '\'' +
                ", nim='" + nim + '\'' +
                ", email='" + email + '\'' +
                ", umur=" + umur +
                ", universitas='" + universitas + '\'' +
                ", programStudi='" + programStudi + '\'' +
                ", kegiatan='" + kegiatan + '\'' +
                ", modeKehadiran='" + modeKehadiran + '\'' +
                ", fasilitas=" + fasilitas +
                ", catatan='" + catatan + '\'' +
                '}';
    }
}
