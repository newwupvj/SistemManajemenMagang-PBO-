package com.sismagang.model;

import java.util.ArrayList;
import java.util.List;

/**
 * PerusahaanDivisi - (Pertemuan 7: Studi Kasus Gabungan)
 *
 * Merepresentasikan satu divisi di sebuah perusahaan tempat peserta magang
 * ditempatkan. Satu objek PerusahaanDivisi punya kuota maksimal peserta,
 * dan menyimpan daftar Pembimbing yang bertugas di divisi tersebut.
 */
public class PerusahaanDivisi {

    private int idDivisi;
    private String namaPerusahaan;
    private String namaDivisi;
    private int kuotaMaksimal;
    private List<Pembimbing> daftarPembimbing;

    public PerusahaanDivisi(int idDivisi, String namaPerusahaan, String namaDivisi, int kuotaMaksimal) {
        this.idDivisi = idDivisi;
        this.namaPerusahaan = namaPerusahaan;
        this.namaDivisi = namaDivisi;
        this.kuotaMaksimal = kuotaMaksimal;
        this.daftarPembimbing = new ArrayList<>();
    }

    public void tambahPembimbing(Pembimbing pembimbing) {
        daftarPembimbing.add(pembimbing);
    }

    public List<Pembimbing> getDaftarPembimbing() {
        return daftarPembimbing;
    }

    public int getIdDivisi() {
        return idDivisi;
    }

    public String getNamaPerusahaan() {
        return namaPerusahaan;
    }

    public void setNamaPerusahaan(String namaPerusahaan) {
        this.namaPerusahaan = namaPerusahaan;
    }

    public String getNamaDivisi() {
        return namaDivisi;
    }

    public void setNamaDivisi(String namaDivisi) {
        this.namaDivisi = namaDivisi;
    }

    public int getKuotaMaksimal() {
        return kuotaMaksimal;
    }

    public void setKuotaMaksimal(int kuotaMaksimal) {
        if (kuotaMaksimal >= 0) {
            this.kuotaMaksimal = kuotaMaksimal;
        }
    }

    @Override
    public String toString() {
        return namaPerusahaan + " - Divisi " + namaDivisi;
    }
}
