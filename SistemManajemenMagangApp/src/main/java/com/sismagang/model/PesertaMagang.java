package com.sismagang.model;

import java.util.ArrayList;
import java.util.List;

/**
 * PesertaMagang - CLASS UTAMA (Pertemuan 2: Class, Object, Atribut, Method)
 *
 * Merupakan subclass dari Pengguna (Pertemuan 6: Inheritance) dan
 * mengimplementasikan interface Dapatdinilai (Pertemuan 11: Interface).
 */
public class PesertaMagang extends Pengguna implements Dapatdinilai {

    // ===== STATIC MEMBER (Pertemuan 3) =====
    // Counter ini dimiliki oleh CLASS, bukan oleh masing-masing objek,
    // sehingga nilainya tetap "nyambung" walau diakses dari objek manapun.
    private static int totalPesertaTerdaftar;

    // ===== FINAL / CONSTANT (Pertemuan 3) =====
    // Nilai tidak bisa diubah setelah didefinisikan (konstanta durasi maksimal magang)
    public static final int MAX_DURASI_MAGANG_BULAN = 6;

    // ===== STATIC BLOCK (Pertemuan 3) =====
    // Dijalankan SEKALI saja saat class pertama kali dimuat oleh JVM,
    // dipakai untuk inisialisasi awal nilai static.
    static {
        totalPesertaTerdaftar = 0;
        System.out.println("[STATIC BLOCK] Modul PesertaMagang dimuat. Counter direset ke 0.");
    }

    // ===== ATRIBUT (private -> Pertemuan 5: Encapsulation) =====
    private String nim;
    private String programStudi;

    // Tipe data PRIMITIF bervariasi (Pertemuan 1: Tipe Data & Casting)
    private int durasiMagangBulan; // int
    private double nilaiAkhir;     // double
    private boolean sudahDitempatkan; // boolean
    private char statusKehadiran;  // char ('H'=Hadir, 'I'=Izin, 'A'=Alpha)

    // Default access modifier (tanpa keyword) -> hanya bisa diakses
    // dari dalam package yang sama (Pertemuan 5: contoh access modifier 'default')
    String catatanInternal;

    // Relasi ke entitas lain
    private Pembimbing pembimbing;
    private PerusahaanDivisi divisiPenempatan;

    // Riwayat bimbingan yang dicatat lewat inner class di bawah
    private List<RiwayatBimbingan> riwayatBimbinganList = new ArrayList<>();

    // ====================================================================
    // INNER CLASS (NON-STATIC) - Pertemuan 3
    // RiwayatBimbingan hanya punya makna jika "menempel" pada objek
    // PesertaMagang tertentu (non-static), karena setiap catatan bimbingan
    // pasti milik satu peserta magang yang spesifik.
    // ====================================================================
    public class RiwayatBimbingan {
        private String tanggal;
        private String catatan;

        public RiwayatBimbingan(String tanggal, String catatan) {
            this.tanggal = tanggal;
            this.catatan = catatan;
        }

        public String getTanggal() {
            return tanggal;
        }

        public String getCatatan() {
            return catatan;
        }

        // Inner class non-static otomatis bisa mengakses atribut
        // objek luar (outer class), contoh: nim milik PesertaMagang ini.
        public String ringkasan() {
            return "[" + tanggal + "] " + nim + " - " + catatan;
        }
    }

    // ===== CONSTRUCTOR OVERLOADING (Pertemuan 4) =====

    // Constructor lengkap
    public PesertaMagang(String username, String password, String namaLengkap,
                          String nim, String programStudi, int durasiMagangBulan) {
        super(username, password, namaLengkap); // panggil constructor induk (Pertemuan 6)
        this.nim = nim;
        this.programStudi = programStudi;

        // Validasi durasi memakai konstanta final MAX_DURASI_MAGANG_BULAN
        this.durasiMagangBulan = (durasiMagangBulan > MAX_DURASI_MAGANG_BULAN)
                ? MAX_DURASI_MAGANG_BULAN
                : durasiMagangBulan;

        this.nilaiAkhir = 0.0;
        this.sudahDitempatkan = false;
        this.statusKehadiran = 'H';

        // Setiap kali objek baru dibuat, counter static bertambah otomatis
        totalPesertaTerdaftar++;
    }

    // Constructor singkat (overload kedua): dipakai saat durasi belum diketahui,
    // default durasi diisi 3 bulan
    public PesertaMagang(String username, String password, String namaLengkap,
                          String nim, String programStudi) {
        this(username, password, namaLengkap, nim, programStudi, 3); // constructor chaining
    }

    // ===== METHOD (Pertemuan 4: kombinasi void & non-void) =====

    // Contoh EXPLICIT CASTING (Pertemuan 1): double -> int dengan pembulatan manual
    public int getNilaiAkhirPembulatan() {
        // (int) adalah explicit casting, akan memotong angka di belakang koma,
        // sehingga kita tambahkan 0.5 dulu sebagai teknik pembulatan sederhana
        return (int) (this.nilaiAkhir + 0.5);
    }

    // Contoh IMPLICIT CASTING / PROMOTION (Pertemuan 1): int otomatis menjadi double
    public double hitungRataRataDenganBobot(int nilaiTugas) {
        // nilaiTugas (int) otomatis di-promote menjadi double saat dioperasikan
        // dengan nilaiAkhir (double) -> tidak perlu casting manual
        double rata = (nilaiTugas + this.nilaiAkhir) / 2;
        return rata;
    }

    // void method
    public void tambahRiwayatBimbingan(String tanggal, String catatan) {
        RiwayatBimbingan riwayat = new RiwayatBimbingan(tanggal, catatan);
        riwayatBimbinganList.add(riwayat);
    }

    public List<RiwayatBimbingan> getRiwayatBimbinganList() {
        return riwayatBimbinganList;
    }

    public void tempatkanKe(PerusahaanDivisi divisi, Pembimbing pembimbing) {
        this.divisiPenempatan = divisi;
        this.pembimbing = pembimbing;
        this.sudahDitempatkan = true;
    }

    // ===== IMPLEMENTASI METHOD DARI INTERFACE Dapatdinilai (Pertemuan 11) =====
    @Override
    public void beriNilai(double nilai) {
        if (nilai >= 0 && nilai <= 100) {
            this.nilaiAkhir = nilai;
        } else {
            System.out.println("Nilai harus di antara 0 - 100.");
        }
    }

    @Override
    public String lihatLaporanAkhir() {
        return "Laporan Akhir Magang\n" +
                "NIM        : " + nim + "\n" +
                "Nama       : " + getNamaLengkap() + "\n" +
                "Durasi     : " + durasiMagangBulan + " bulan\n" +
                "Nilai Akhir: " + nilaiAkhir;
    }

    // ===== IMPLEMENTASI ABSTRACT METHOD DARI Pengguna (Pertemuan 11) =====
    @Override
    public String getRole() {
        return "Peserta Magang";
    }

    // ===== METHOD OVERRIDING (Pertemuan 6) =====
    @Override
    public void tampilkanInfo() {
        super.tampilkanInfo(); // tetap memanggil versi induk dulu
        System.out.println("NIM            : " + nim);
        System.out.println("Program Studi  : " + programStudi);
        System.out.println("Durasi Magang  : " + durasiMagangBulan + " bulan");
        System.out.println("Status         : " + (sudahDitempatkan ? "Sudah ditempatkan" : "Belum ditempatkan"));
    }

    // ===== GETTER & SETTER dengan VALIDASI (Pertemuan 5: Encapsulation) =====
    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getProgramStudi() {
        return programStudi;
    }

    public void setProgramStudi(String programStudi) {
        this.programStudi = programStudi;
    }

    public int getDurasiMagangBulan() {
        return durasiMagangBulan;
    }

    public void setDurasiMagangBulan(int durasiMagangBulan) {
        // Validasi: tidak boleh negatif dan tidak boleh melebihi batas maksimal
        if (durasiMagangBulan >= 0 && durasiMagangBulan <= MAX_DURASI_MAGANG_BULAN) {
            this.durasiMagangBulan = durasiMagangBulan;
        } else {
            System.out.println("Durasi magang tidak valid (0 - " + MAX_DURASI_MAGANG_BULAN + " bulan).");
        }
    }

    public double getNilaiAkhir() {
        return nilaiAkhir;
    }

    public boolean isSudahDitempatkan() {
        return sudahDitempatkan;
    }

    public char getStatusKehadiran() {
        return statusKehadiran;
    }

    public void setStatusKehadiran(char statusKehadiran) {
        this.statusKehadiran = statusKehadiran;
    }

    public Pembimbing getPembimbing() {
        return pembimbing;
    }

    public PerusahaanDivisi getDivisiPenempatan() {
        return divisiPenempatan;
    }

    // ===== Getter untuk static counter (Pertemuan 3) =====
    public static int getTotalPesertaTerdaftar() {
        return totalPesertaTerdaftar;
    }
}
