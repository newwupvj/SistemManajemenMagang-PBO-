package com.sismagang.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Pembimbing - SUBCLASS dari Pengguna (Pertemuan 6 & 7)
 *
 * Pembimbing adalah salah satu jenis Pengguna (sama seperti PesertaMagang
 * dan Admin), tetapi perannya berbeda: membimbing & menilai peserta magang
 * yang ditempatkan di divisinya.
 */
public class Pembimbing extends Pengguna {

    private String nip;
    private PerusahaanDivisi divisi;
    private List<PesertaMagang> daftarBimbingan;

    // idPembimbing menyimpan id_pembimbing dari tabel database (Pertemuan 14: JDBC).
    // Default 0 berarti pembimbing ini belum tersinkron dengan baris di database.
    private int idPembimbing = 0;

    public Pembimbing(String username, String password, String namaLengkap,
                       String nip, PerusahaanDivisi divisi) {
        super(username, password, namaLengkap);
        this.nip = nip;
        this.divisi = divisi;
        this.daftarBimbingan = new ArrayList<>();
    }

    // Method untuk menilai peserta magang yang dibimbingnya.
    // Memanfaatkan interface Dapatdinilai yang sudah diimplementasikan PesertaMagang
    // (Pertemuan 11: Interface) - menunjukkan bagaimana interface dipakai sebagai
    // "kontrak" sehingga Pembimbing tidak perlu tahu detail PesertaMagang,
    // cukup tahu bahwa objeknya "Dapatdinilai".
    public void nilaiPeserta(Dapatdinilai peserta, double nilai) {
        peserta.beriNilai(nilai);
    }

    public void tambahBimbingan(PesertaMagang peserta) {
        daftarBimbingan.add(peserta);
    }

    public List<PesertaMagang> getDaftarBimbingan() {
        return daftarBimbingan;
    }

    public String getNip() {
        return nip;
    }

    public PerusahaanDivisi getDivisi() {
        return divisi;
    }

    public int getIdPembimbing() {
        return idPembimbing;
    }

    public void setIdPembimbing(int idPembimbing) {
        this.idPembimbing = idPembimbing;
    }

    // ===== Implementasi abstract method dari Pengguna (Pertemuan 11) =====
    @Override
    public String getRole() {
        return "Pembimbing";
    }

    // ===== Method overriding (Pertemuan 6) =====
    @Override
    public void tampilkanInfo() {
        super.tampilkanInfo();
        System.out.println("NIP    : " + nip);
        System.out.println("Divisi : " + divisi);
        System.out.println("Jumlah Bimbingan: " + daftarBimbingan.size() + " peserta");
    }
}
