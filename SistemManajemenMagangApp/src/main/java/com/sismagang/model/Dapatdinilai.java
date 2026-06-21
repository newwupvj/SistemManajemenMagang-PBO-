package com.sismagang.model;

/**
 * Dapatdinilai - INTERFACE (Pertemuan 11: Abstract Class & Interface)
 *
 * Interface mendefinisikan "kontrak" method yang WAJIB diimplementasikan
 * oleh class yang menerapkannya (implements). Semua method di interface
 * ini bersifat public abstract secara default (tidak perlu ditulis manual).
 *
 * Digunakan oleh PesertaMagang karena hanya peserta magang yang bisa
 * "dinilai" dan punya laporan akhir, beda dengan Admin atau Pembimbing.
 */
public interface Dapatdinilai {

    // Method abstrak: beri nilai akhir magang ke peserta
    void beriNilai(double nilai);

    // Method abstrak: menampilkan laporan akhir peserta magang
    String lihatLaporanAkhir();
}
