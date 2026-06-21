Sistem Informasi Manajemen Magang
Project Java OOP untuk tugas mata kuliah Pemrograman Berorientasi Objek (PBO), mengimplementasikan seluruh konsep dari Pertemuan 1 sampai 14 dalam satu studi kasus terpadu: sistem manajemen magang dengan 2 role (Admin & Mahasiswa).

Cara Import ke IntelliJ IDEA
Buka IntelliJ IDEA.
Pilih File > Open, lalu arahkan ke folder SistemManajemenMagangApp (folder yang berisi pom.xml).
IntelliJ akan otomatis mendeteksi ini sebagai project Maven. Tunggu proses "Importing Maven project" selesai di pojok kanan bawah (IntelliJ akan otomatis mengunduh dependency MySQL Connector/J yang tercantum di pom.xml).
Jika belum otomatis ter-download, klik ikon Maven di sisi kanan IDE, lalu klik tombol refresh (reload all maven projects).
Cara Menjalankan
Buka file src/main/java/com/sismagang/app/Main.java.
Klik kanan pada file tersebut, lalu pilih Run 'Main.main()' (atau klik ikon ▶ di sebelah method main).
Akan muncul output di console (demo polimorfisme), lalu jendela Login akan terbuka.
Klik "Masuk sebagai Admin" untuk melihat dashboard kelola data peserta, atau "Masuk sebagai Mahasiswa" untuk mendaftar magang lewat form.
Catatan: GUI (login, dashboard admin, dashboard mahasiswa) tetap bisa dicoba tanpa setup database untuk eksplorasi cepat (fallback ke data di memori lewat MagangService), tapi jika MySQL sudah aktif dan database.sql sudah dijalankan, aksi-aksi penting (pendaftaran, penempatan) akan otomatis tersinkron ke MySQL lewat PesertaMagangDAO di package db.

Setup Database (untuk Pertemuan 14 - JDBC)
GUI Admin dan Mahasiswa sudah tersambung langsung ke MySQL, bukan cuma data di memori. Supaya bisa dicoba:

Pastikan MySQL sudah jalan (bisa pakai XAMPP, Laragon, atau MySQL Server biasa).

Buka MySQL client (phpMyAdmin / MySQL Workbench / terminal mysql), lalu jalankan seluruh isi file database.sql yang ada di root project ini. Script ini akan:

Membuat database db_sismagang
Membuat 3 tabel: perusahaan_divisi, pembimbing, peserta_magang (lengkap dengan foreign key)
Mengisi beberapa data awal (2 divisi, 2 pembimbing)
Buka file src/main/java/com/sismagang/db/DatabaseConnection.java, sesuaikan USER dan PASSWORD dengan kredensial MySQL di komputer Anda (default XAMPP biasanya root dengan password kosong, sudah sesuai default di file ini).

Jalankan Main.java seperti biasa, lalu:

Dashboard Mahasiswa → isi form "Daftar Magang" → data otomatis ter-INSERT ke tabel peserta_magang di MySQL (selain juga tersimpan di memori untuk pencarian cepat).
Dashboard Admin → klik "Muat dari Database" untuk membaca (SELECT) langsung dari MySQL ke tabel di GUI, lalu pilih satu baris dan klik "Verifikasi & Tempatkan" untuk meng-UPDATE kolom id_divisi, id_pembimbing, dan sudah_ditempatkan di database.
Jika MySQL belum aktif/salah konfigurasi, GUI tetap bisa dipakai (operasi tetap jalan di memori), tapi akan muncul pesan peringatan bahwa data gagal disimpan ke database.

Untuk mencoba CRUD lebih bebas/manual di luar GUI (termasuk UPDATE nilai & DELETE), buka file src/main/java/com/sismagang/app/TestKoneksiDatabase.java, lalu klik kanan pada file tersebut dan pilih Run 'TestKoneksiDatabase.main()'.

Isi singkatnya seperti ini (sudah lengkap di file aslinya):

PesertaMagangDAO dao = new PesertaMagangDAO();
dao.tambah(pesertaBaru, 1);           // CREATE
List<PesertaMagang> semua = dao.ambilSemua(); // READ
dao.updateNilai("2410501099", 95.0);  // UPDATE
dao.hapus("2410501099");              // DELETE
Setiap kali dijalankan, akan muncul log di console seperti [CREATE] Berhasil menambah peserta: ..., [READ] Total data di database: ..., dst, supaya gampang dilihat hasilnya.

Untuk Pembimbing dan PerusahaanDivisi, gunakan PembimbingDAO dan PerusahaanDivisiDAO dengan cara yang sama (method tambah(), ambilSemua(), update...(), hapus()), bisa ditambahkan ke TestKoneksiDatabase.java jika ingin dites juga.

Struktur Package
Package	Isi
com.sismagang.model	Pengguna (abstract), Dapatdinilai (interface), PesertaMagang, PesertaMagangMBKM, Pembimbing, Admin, PerusahaanDivisi
com.sismagang.exception	KuotaMagangPenuhException, PesertaTidakDitemukanException
com.sismagang.service	MagangService (logic pendaftaran, penempatan, pencarian)
com.sismagang.db	DatabaseConnection, PesertaMagangDAO, PembimbingDAO, PerusahaanDivisiDAO (JDBC)
com.sismagang.gui	LoginFrame, DashboardAdminFrame, DashboardMahasiswaFrame (Swing, sudah tersambung ke MySQL)
com.sismagang.app	Main (entry point), TestKoneksiDatabase (test CRUD JDBC terpisah)
Pemetaan Konsep PBO ke Kode
Pertemuan	Konsep	Lokasi di Kode
1	Tipe Data & Casting	PesertaMagang (atribut primitif, implicit/explicit casting di getNilaiAkhirPembulatan(), hitungRataRataDenganBobot())
2	Class, Object, Atribut, Method	PesertaMagang sebagai blueprint utama
3	Inner Class, Static, Final	PesertaMagang.RiwayatBimbingan (inner class), totalPesertaTerdaftar (static), MAX_DURASI_MAGANG_BULAN (final), static block
4	Method, Return Type, Constructor	Constructor overloading di PesertaMagang
5	Encapsulation & Access Modifier	Semua atribut private + getter/setter; contoh protected, default di Pengguna/PesertaMagang
6	Inheritance & Overriding	PesertaMagangMBKM extends PesertaMagang, Pembimbing/Admin extends Pengguna, @Override tampilkanInfo()
7	Studi Kasus Gabungan	Pembimbing & PerusahaanDivisi berelasi dengan PesertaMagang
9	Polimorfisme	Overload cariPeserta() di MagangService (statis); upcasting di Main.java (dinamis)
10	Exception Handling	KuotaMagangPenuhException, PesertaTidakDitemukanException, try-catch-finally di MagangService.cobaTempatkanPeserta()
11	Abstract Class & Interface	Pengguna (abstract class), Dapatdinilai (interface)
12	Collection Framework & Package	ArrayList, HashMap, HashSet di MagangService; pemisahan package
13	GUI Java Swing	LoginFrame, DashboardAdminFrame, DashboardMahasiswaFrame
14	JDBC Database Connectivity	DatabaseConnection, PesertaMagangDAO, PembimbingDAO, PerusahaanDivisiDAO (PreparedStatement, ResultSet); tersambung langsung dari DashboardMahasiswaFrame & DashboardAdminFrame
