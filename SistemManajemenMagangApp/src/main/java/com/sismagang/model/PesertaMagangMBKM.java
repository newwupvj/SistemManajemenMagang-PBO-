package com.sismagang.model;

/**
 * PesertaMagangMBKM - SUBCLASS dari PesertaMagang (Pertemuan 6: Inheritance)
 *
 * Peserta program Merdeka Belajar Kampus Merdeka (MBKM) punya semua sifat
 * PesertaMagang biasa, ditambah atribut khusus yaitu konversi SKS.
 * Dipakai juga untuk mendemonstrasikan POLIMORFISME DINAMIS (Pertemuan 9):
 * objek bertipe PesertaMagang bisa menyimpan instance PesertaMagangMBKM,
 * dan saat method di-override dipanggil, versi subclass yang dijalankan
 * (ditentukan saat runtime, bukan saat compile).
 */
public class PesertaMagangMBKM extends PesertaMagang {

    // Atribut tambahan yang hanya dimiliki peserta MBKM
    private int jumlahSksKonversi;
    private String namaMataKuliahKonversi;

    // Constructor subclass, wajib memanggil super() (Pertemuan 6)
    public PesertaMagangMBKM(String username, String password, String namaLengkap,
                              String nim, String programStudi, int durasiMagangBulan,
                              int jumlahSksKonversi, String namaMataKuliahKonversi) {
        // super(...) memanggil constructor PesertaMagang (induk langsung)
        super(username, password, namaLengkap, nim, programStudi, durasiMagangBulan);
        this.jumlahSksKonversi = jumlahSksKonversi;
        this.namaMataKuliahKonversi = namaMataKuliahKonversi;
    }

    public int getJumlahSksKonversi() {
        return jumlahSksKonversi;
    }

    public void setJumlahSksKonversi(int jumlahSksKonversi) {
        if (jumlahSksKonversi >= 0 && jumlahSksKonversi <= 20) {
            this.jumlahSksKonversi = jumlahSksKonversi;
        }
    }

    public String getNamaMataKuliahKonversi() {
        return namaMataKuliahKonversi;
    }

    // ===== METHOD OVERRIDING dengan @Override (Pertemuan 6) =====
    // Method ini "menimpa" versi milik PesertaMagang, menambahkan
    // informasi konversi SKS yang khusus dimiliki peserta MBKM.
    @Override
    public void tampilkanInfo() {
        super.tampilkanInfo(); // tetap pakai info dasar dari PesertaMagang
        System.out.println("Program        : MBKM (Merdeka Belajar Kampus Merdeka)");
        System.out.println("Konversi SKS   : " + jumlahSksKonversi + " SKS (" + namaMataKuliahKonversi + ")");
    }

    // Override getRole() juga, supaya beda dengan peserta magang reguler
    @Override
    public String getRole() {
        return "Peserta Magang MBKM";
    }
}
