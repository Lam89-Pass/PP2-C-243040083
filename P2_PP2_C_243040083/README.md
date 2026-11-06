# 📋 Formulir Pendaftaran Kegiatan

> **Tugas Praktikum 1 — Pemrograman Lanjutan II (PP II)**  
> Kode Tugas: `P1_PP2_C_243040083`

Aplikasi desktop **Formulir Pendaftaran Kegiatan** berbasis Java Swing.  
Dibuat sebagai tugas praktikum untuk mendemonstrasikan penggunaan komponen GUI Swing dan layout manager.

---

## 📌 Deskripsi Singkat

Aplikasi ini menampilkan formulir pendaftaran kegiatan seminar/workshop dengan fitur:

- **Identitas Peserta** — Input nama lengkap, NIM, email, universitas (dropdown), dan program studi (dropdown).
- **Pilihan Kegiatan** — Pilihan kegiatan (JComboBox), mode kehadiran Online/Offline (JRadioButton + ButtonGroup), dan fasilitas (JCheckBox).
- **Catatan** — Area teks bebas untuk catatan tambahan (JTextArea).
- **Tombol Aksi** — Tombol **Daftar** (mencetak ringkasan ke console) dan **Reset** (mengosongkan formulir).

> ⚠️ Sesuai ketentuan Tugas 1: *"tombol belum wajib menyimpan data"*, sehingga tombol **Daftar** hanya mencetak log debug ke console (bukan menyimpan ke database/file).

---

## 🧰 Prasyarat (Requirements)

| Komponen        | Versi yang Digunakan                         | Catatan                                           |
|-----------------|----------------------------------------------|---------------------------------------------------|
| **JDK**         | OpenJDK 26.0.2 (atau JDK 21+)               | Minimal JDK 21; sudah diuji di JDK 26             |
| **IDE**         | Antigravity IDE / VS Code + Java Extension   | Opsional; bisa di-build via terminal              |
| **OS**          | Ubuntu Linux (diuji)                         | Cross-platform (Windows/macOS juga bisa)          |
| **Build Tool**  | `javac` + `java` (bawaan JDK)               | Tidak memerlukan Maven, Gradle, atau Ant          |

### Cek Versi Java

Pastikan Java sudah terinstall dengan menjalankan:

```bash
java --version
```

Contoh output yang diharapkan:

```
openjdk 26.0.2 2026-07-21
OpenJDK Runtime Environment (build 26.0.2+10-55)
OpenJDK 64-Bit Server VM (build 26.0.2+10-55, mixed mode)
```

Jika belum ada, install JDK terlebih dahulu:

```bash
# Ubuntu/Debian
sudo apt install openjdk-21-jdk

# Atau download dari https://jdk.java.net/
```

---

## 🚀 Langkah Build & Run

### Cara 1: Via Terminal (Direkomendasikan ✅)

```bash
# 1. Masuk ke folder proyek
cd "P1_PP2_C_243040083"

# 2. Hapus hasil kompilasi lama (opsional, untuk build bersih)
rm -rf out

# 3. Compile semua file Java ke folder out/
javac -d out *.java

# 4. Jalankan aplikasi
java -cp out Main
```

**Penjelasan tiap langkah:**
1. `cd` — Pindah ke direktori proyek.
2. `rm -rf out` — Menghapus folder output lama agar tidak ada class file basi.
3. `javac -d out *.java` — Meng-compile semua file `.java` di folder saat ini. Flag `-d out` menyimpan hasil `.class` ke folder `out/`.
4. `java -cp out Main` — Menjalankan class `Main` dari classpath `out/`.

### Cara 2: Via IDE (VS Code / Antigravity IDE)

1. Buka folder proyek di IDE.
2. Pastikan file `.vscode/settings.json` sudah ada (sudah disediakan), berisi:
   ```json
   {
       "java.project.outputPath": "out",
       "java.project.sourcePaths": ["."],
       "java.compile.target": "21",
       "java.compile.source": "21"
   }
   ```
3. Buka file `Main.java`.
4. Klik tombol **▶ Run** di atas method `main`, atau tekan **F5**.
5. Aplikasi akan terbuka.

### ⚠️ Troubleshooting: UnsupportedClassVersionError

Jika muncul error seperti:

```
java.lang.UnsupportedClassVersionError: Main has been compiled by a more recent
version of the Java Runtime (class file version 70.0), this version of the
Java Runtime only recognizes class file versions up to 69.0
```

**Penyebab:** File `.class` di-compile dengan JDK yang lebih baru dari JRE yang menjalankan.

**Solusi:**
- Gunakan **Cara 1 (Terminal)** untuk build & run — ini memastikan versi compiler dan runtime sama.
- Atau pastikan `settings.json` mengatur `java.compile.target` ke versi yang sesuai dengan JRE IDE Anda (misalnya `"21"`).

---

## 📁 Struktur Proyek

```
P1_PP2_C_243040083/
├── Main.java                  ← Entry point (EDT launcher)
├── FormPendaftaran.java       ← View: layout & komponen GUI
├── ModelPendaftaran.java      ← Model: data pendaftaran (POJO)
├── PendaftaranController.java ← Controller: logic tombol Daftar/Reset
├── UITheme.java               ← Design system: warna, font, skala responsif
├── CustomUI.java              ← Komponen kustom (RoundedTextField, dll.)
├── VectorIcon.java            ← Ikon vektor Java2D (bukan file gambar)
├── SidebarPanel.java          ← Panel sidebar kiri (ilustrasi & branding)
├── screenshots/               ← Tangkapan layar bukti uji
│   ├── tampilan_awal.png
│   ├── hasil_resize_besar.png
│   └── hasil_resize_kecil.png
├── .vscode/
│   ├── settings.json          ← Konfigurasi compiler & output path
│   └── launch.json            ← Konfigurasi debug/run di IDE
├── out/                       ← Folder output kompilasi (.class files)
└── README.md                  ← File ini
```

---

## 🏗️ Arsitektur (MVC Pattern)

Aplikasi ini menggunakan pola **Model–View–Controller (MVC)** untuk memisahkan tanggung jawab:

```
┌─────────────────┐     ┌──────────────────────┐     ┌──────────────────────┐
│   Main.java     │────▶│  FormPendaftaran.java │◀───▶│ PendaftaranController│
│  (Entry Point)  │     │      (VIEW)          │     │    (CONTROLLER)      │
│  EDT launcher   │     │  Layout & komponen   │     │  handleDaftar()      │
└─────────────────┘     │  GUI Swing           │     │  handleReset()       │
                        └──────────┬───────────┘     └──────────┬───────────┘
                                   │                            │
                                   │         ┌─────────────────┐│
                                   └────────▶│ ModelPendaftaran ││
                                             │    (MODEL)      ││
                                             │  Data POJO      │◀┘
                                             └─────────────────┘

  ┌──────────────┐   ┌────────────┐   ┌──────────────┐
  │ UITheme.java │   │ CustomUI   │   │ VectorIcon   │
  │ (warna/font) │   │ (komponen) │   │ (ikon Java2D)│
  └──────────────┘   └────────────┘   └──────────────┘
        └────────────────┴────────────────┘
              Helper / Utility classes
```

---

## 🧩 Komponen Swing yang Digunakan

| Komponen Swing     | Penggunaan dalam Aplikasi                          |
|--------------------|----------------------------------------------------|
| `JFrame`           | Window utama aplikasi                              |
| `JPanel`           | Container untuk setiap section (identitas, dll.)   |
| `JLabel`           | Label untuk setiap field input                     |
| `JTextField`       | Input nama lengkap, NIM, dan email                 |
| `JComboBox`        | Dropdown universitas, program studi, dan kegiatan  |
| `JRadioButton`     | Pilihan mode kehadiran (Online/Offline)             |
| `ButtonGroup`      | Mengelompokkan radio button agar saling eksklusif  |
| `JCheckBox`        | Pilihan fasilitas (Konsumsi, Sertifikat, dll.)     |
| `JTextArea`        | Area catatan tambahan                              |
| `JButton`          | Tombol Daftar dan Reset                            |
| `JScrollPane`      | Scroll wrapper untuk form dan text area            |

---

## 📐 Layout Manager yang Digunakan

Aplikasi ini menggunakan **5 layout manager** (tanpa absolute positioning / null layout):

| Layout Manager     | Digunakan di                                                    |
|--------------------|-----------------------------------------------------------------|
| `BorderLayout`     | Struktur utama JFrame (sidebar WEST, form CENTER, footer SOUTH) |
| `GridBagLayout`    | Tata letak field-field dalam setiap section (label + input)     |
| `GridLayout`       | Grid checkbox fasilitas (2 baris × 3 kolom)                     |
| `FlowLayout`       | Baris radio button (Online/Offline) dan tombol aksi             |
| `BoxLayout`        | Menyusun section-section secara vertikal dalam form             |

---

## 🎨 Fitur Desain

- **Tema Warna Navy-Blue** — Palet warna konsisten menggunakan `UITheme.java`.
- **Ikon Vektor (Java2D)** — Semua ikon digambar dengan kode Java2D murni (tanpa file gambar eksternal).
- **Responsive / Scalable** — Layout otomatis menyesuaikan saat jendela di-resize. Faktor skala dihitung berdasarkan rasio ukuran jendela terhadap base size (1150×820).
- **Komponen Kustom** — Text field dengan rounded corner, tombol dengan efek hover, placeholder text.
- **Sidebar Ilustrasi** — Panel kiri dengan gradient, kalender icon, dan ilustrasi clipboard.

---

## 📜 Lisensi

Proyek ini dibuat untuk keperluan akademik (tugas kuliah).
