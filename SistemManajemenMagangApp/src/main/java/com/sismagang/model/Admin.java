package com.sismagang.model;

/**
 * Admin - SUBCLASS dari Pengguna (Pertemuan 6: Inheritance)
 *
 * Admin bertugas mengelola data Perusahaan/Divisi, Pembimbing, serta
 * memverifikasi dan menempatkan Peserta Magang. Tidak memiliki nilai
 * akhir magang sehingga TIDAK mengimplementasikan interface Dapatdinilai
 * (berbeda dengan PesertaMagang) - ini menunjukkan kegunaan interface:
 * hanya diterapkan pada class yang memang relevan.
 */
public class Admin extends Pengguna {

    private String idAdmin;

    public Admin(String username, String password, String namaLengkap, String idAdmin) {
        super(username, password, namaLengkap);
        this.idAdmin = idAdmin;
    }

    public String getIdAdmin() {
        return idAdmin;
    }

    // ===== Implementasi abstract method dari Pengguna (Pertemuan 11) =====
    @Override
    public String getRole() {
        return "Admin";
    }

    @Override
    public void tampilkanInfo() {
        super.tampilkanInfo();
        System.out.println("ID Admin: " + idAdmin);
    }
}
