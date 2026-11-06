# 📋 Sistem Pendaftaran Kegiatan & E-Tiket

> **Tugas Praktikum 2 — Pemrograman Lanjutan II (PP II)**  
> Program Studi: Teknik Informatika — Universitas Pasundan  

Aplikasi desktop **Sistem Pendaftaran Kegiatan & Penerbitan E-Tiket** berbasis Java Swing dengan arsitektur **Model-View-Controller (MVC)**, antarmuka modern bergaya **macOS Window**, sistem navigasi multi-halaman (**CardLayout**), pelacak perubahan data (**Dirty State Tracker**), serta fitur ekspor **E-Tiket grafis beresolusi tinggi (PNG)**.

---

## 📌 Fitur Utama Aplikasi

1. **Jendela Modern Bergaya macOS (Mac Window Frame)**
   - Desain tanpa border klasik (*undecorated*) dengan sudut melengkung halus (*rounded corners*).
   - Bilah judul kustom (*MacTitleBar*) lengkap dengan tombol kontrol lampu lalu lintas (*traffic lights*):
     - 🔴 **Tutup (Close):** Menutup aplikasi dengan proteksi konfirmasi *Dirty State*.
     - 🟡 **Minimalkan (Minimize):** Menyembunyikan jendela ke taskbar.
     - 🟢 **Maksimalkan / Pulihkan (Maximize/Restore):** Menyesuaikan ukuran layar penuh atau kembali ke ukuran normal.
     - Efek hover ikon interaktif (`✕`, `–`, `+`).
   - Fitur *Window Dragging* untuk memindahkan jendela dan *Edge Resizing* manual di tepi serta sudut jendela.

2. **Formulir Pendaftaran Interaktif (View: FormPendaftaran)**
   - **Identitas Peserta:** Input Nama Lengkap, NIM, Email aktif, dan Umur (15–100 tahun).
   - **Institusi Akademik:** Pilihan Perguruan Tinggi dan Program Studi melalui dropdown `JComboBox`.
   - **Kegiatan & Kehadiran:** Pilihan topik seminar/workshop, format kehadiran (*Online* / *Offline*), dan paket fasilitas pendukung (`JCheckBox`).
   - **Kolom Catatan:** Area teks bebas (`JTextArea`) dengan dukungan placeholder.

3. **Validasi Formulir & Dialog Kustom Modern (ModernDialog)**
   - Validasi ketat pada setiap field wajib (Nama, NIM, Email berformat regex, Umur integer, pilihan dropdown).
   - Seluruh notifikasi validasi dan peringatan menggunakan `ModernDialog` berukuran besar, jelas, dan kontras tinggi.

4. **Halaman Ringkasan Data (View: HalamanRingkasan)**
   - Menampilkan rekapitulasi lengkap isian formulir sebelum difinalisasi.
   - Tombol **"Kembali Edit Form"** untuk menyunting ulang isian tanpa menghapus data sebelumnya.
   - Tombol **"Daftar Sekarang"** untuk memproses registrasi dan menerbitkan E-Tiket.

5. **Pusat Bantuan & Panduan Sistem (View: HalamanBantuan)**
   - Panduan alur 5 tahapan pendaftaran.
   - Spesifikasi aturan validasi data.
   - Tanya jawab umum (*Frequently Asked Questions / FAQ*).
   - Layanan kontak narahubung panitia.

6. **Penerbitan & Ekspor E-Tiket (TiketDialog & ModernSaveDialog)**
   - Tampilan modal dialog E-Tiket virtual dengan efek backdrop dimmer, aksen perforasi sobekan tiket, barcode vertikal, dan nomor seri registrasi unik berbasis timestamp.
   - Tombol **"Cetak Tiket"** yang membuka `ModernSaveDialog` (jendela penyimpanan berkas modern berukuran besar dengan pintasan cepat Desktop, Dokumen, Unduhan, dan Beranda).
   - Penyimpanan gambar tiket dalam format **PNG resolusi tinggi (2x Retina scale)** yang tajam dan siap cetak.
   - Opsi langsung membuka berkas gambar setelah penyimpanan berhasil.

7. **Pengaman Draf (Dirty State Tracking)**
   - Mendeteksi pengetikan dan perubahan opsi formulir secara *real-time*.
   - Mencegah kehilangan data yang belum disubmit saat pengguna menutup jendela.

---

## 🧰 Prasyarat Sistem (Requirements)

| Komponen | Spesifikasi / Versi | Keterangan |
| :--- | :--- | :--- |
| **JDK (Java Development Kit)** | OpenJDK 21+ (Diuji pada OpenJDK 26.0.2) | Wajib JDK 21 ke atas |
| **IDE** | Antigravity IDE / VS Code / IntelliJ IDEA | Disertakan konfigurasi `.vscode/` |
| **Sistem Operasi** | Linux (Ubuntu diuji), macOS, atau Windows | Cross-platform (Java Swing murni) |
| **Build Tool** | `javac` & `java` (bawaan JDK) | Tanpa dependensi pihak ketiga (Zero-Dependency) |

### Pengecekan Versi Java

Buka terminal dan jalankan:
```bash
java -version
javac -version
```

Pastikan versi compiler dan runtime berada pada versi **21** atau yang lebih baru.

---

## 🚀 Panduan Build & Run

### Cara 1: Menjalankan via Terminal (Direkomendasikan)

1. Buka terminal dan masuk ke direktori proyek:
   ```bash
   cd "P2_PP2_C_243040083"
   ```

2. Bersihkan berkas kompilasi lama (opsional):
   ```bash
   rm -rf out
   ```

3. Kompilasi seluruh berkas Java ke folder `out/`:
   ```bash
   javac -d out *.java
   ```

4. Jalankan aplikasi:
   ```bash
   java -cp out Main
   ```

---

### Cara 2: Menjalankan via IDE (VS Code / Antigravity IDE)

1. Buka folder `P2_PP2_C_243040083` di IDE.
2. Konfigurasi build telah disiapkan otomatis pada `.vscode/settings.json`:
   ```json
   {
       "java.project.outputPath": "out",
       "java.project.sourcePaths": ["."],
       "java.compile.target": "21",
       "java.compile.source": "21"
   }
   ```
3. Buka berkas `Main.java`.
4. Klik tombol **▶ Run** yang muncul di atas method `public static void main(String[] args)`, atau tekan tombol **F5**.
5. Aplikasi akan terbuka.

---

## 📁 Struktur Berkas Proyek

```
P2_PP2_C_243040083/
├── Main.java                  ← Entry point aplikasi, inisialisasi JFrame & CardLayout
├── MacTitleBar.java           ← Komponen bilah judul macOS (traffic lights, drag, maximize)
├── FormPendaftaran.java       ← View: Formulir input pendaftaran kegiatan
├── HalamanRingkasan.java      ← View: Layar tinjauan ringkasan data sebelum submit
├── HalamanBantuan.java        ← View: Pusat panduan, alur, validasi, dan FAQ
├── TiketDialog.java           ← View: Dialog modal kartu E-Tiket & ekspor gambar
├── ModernDialog.java          ← Komponen popup notifikasi, validasi, dan konfirmasi modern
├── ModernSaveDialog.java      ← Komponen popup pemilih lokasi simpan berkas tiket
├── PendaftaranController.java ← Controller: Logika validasi input dan alur form
├── ModelPendaftaran.java      ← Model: POJO penyimpan data pendaftaran
├── SidebarPanel.java          ← Panel samping kiri (navigasi & identitas visual)
├── UITheme.java               ← Design system: Palet warna, font, dan penskalaan responsif
├── CustomUI.java              ← Komponen UI kustom (RoundedTextField, RoundedButton, dll.)
├── VectorIcon.java            ← Generator ikon vektor berbasis Java2D murni
├── .vscode/                   ← Pengaturan workspace IDE (settings.json, launch.json)
├── out/                       ← Direktori output hasil kompilasi bytecode (.class)
└── README.md                  ← Dokumentasi resmi proyek
```

---

## 🏗️ Pola Arsitektur Model-View-Controller (MVC)

```
┌──────────────────────────────────────────────────────────────────┐
│                           CONTROLLER                             │
│                  PendaftaranController & Main                    │
│           • Menangani aksi tombol Lanjutkan / Reset              │
│           • Memvalidasi kesesuaian data input                    │
│           • Mengatur navigasi halaman via CardLayout             │
└───────────────────────────────┬──────────────────────────────────┘
                                │
        ┌───────────────────────┴───────────────────────┐
        ▼                                               ▼
┌───────────────────────────────┐               ┌───────────────────────────────┐
│             VIEW              │               │             MODEL             │
│  • FormPendaftaran            │◀──────────────│       ModelPendaftaran        │
│  • HalamanRingkasan           │  Mengirimkan  │  Menyimpan state & atribut    │
│  • HalamanBantuan             │  data isian   │  peserta secara terenkapsulasi│
│  • TiketDialog                │               │  (Nama, NIM, Email, dll.)     │
│  • ModernDialog & SaveDialog  │               └───────────────────────────────┘
└───────────────────────────────┘
```

---

## 📐 Layout Manager yang Diterapkan

Aplikasi mengombinasikan berbagai layout manager standar Swing secara terstruktur:

| Layout Manager | Bagian yang Menggunakan |
| :--- | :--- |
| **BorderLayout** | Tata letak induk jendela (`Main`), `HalamanRingkasan`, `HalamanBantuan`, `ModernSaveDialog`, dan kartu section. |
| **CardLayout** | Mengelola transisi multi-halaman antara `FORM`, `RINGKASAN`, dan `BANTUAN`. |
| **GridBagLayout** | Tata letak presisi pada field identitas peserta dan form input file. |
| **GridLayout** | Tata letak kotak centang fasilitas (2 baris × 3 kolom) dan matriks info. |
| **BoxLayout** | Penyusunan elemen secara vertikal pada kartu bantuan dan menu samping. |
| **FlowLayout** | Penataan tombol aksi, baris radio button, dan grup tombol dialog. |

---

## 💡 Pemecahan Masalah (Troubleshooting)

- **Masalah: Font atau komponen terlihat kecil di layar resolusi tinggi (4K/Retina)**  
  Aplikasi memiliki kalkulasi skala otomatis pada `UITheme.hitungSkala(w, h)` yang mengalikan ukuran pixel secara proporsional berdasarkan resolusi layar perangkat.

- **Masalah: Layar tidak merespon saat resize**  
  Aplikasi dilengkapi `WindowResizer` manual pada panel utama. Anda dapat menyeret tepi kanan, bawah, atau sudut kanan bawah jendela untuk mengubah ukurannya kapan saja.

---

## 📜 Lisensi & Ketentuan

Proyek ini disusun dan dikembangkan sebagai tugas praktikum mata kuliah **Pemrograman Lanjutan II (PP II)**, Program Studi Teknik Informatika, Fakultas Teknik, Universitas Pasundan.
