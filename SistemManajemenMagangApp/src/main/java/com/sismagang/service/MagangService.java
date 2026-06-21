package com.sismagang.service;

import com.sismagang.exception.KuotaMagangPenuhException;
import com.sismagang.exception.PesertaTidakDitemukanException;
import com.sismagang.model.PerusahaanDivisi;
import com.sismagang.model.Pembimbing;
import com.sismagang.model.PesertaMagang;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * MagangService - (Pertemuan 12: Collection Framework & Package)
 *
 * Berisi seluruh logic bisnis: pendaftaran, penempatan, dan pencarian
 * peserta magang. Memanfaatkan tiga jenis Collection Framework:
 *  - ArrayList  -> menyimpan daftar seluruh peserta magang (urut, boleh duplikat)
 *  - HashMap    -> memetakan idDivisi -> daftar peserta yang ditempatkan di sana
 *  - HashSet    -> menyimpan NIM unik supaya tidak ada peserta terdaftar dua kali
 */
public class MagangService {

    // ArrayList: daftar utama semua peserta magang yang terdaftar di sistem
    private List<PesertaMagang> daftarPeserta = new ArrayList<>();

    // HashMap: Key = idDivisi, Value = daftar peserta yang ditempatkan di divisi itu
    private Map<Integer, List<PesertaMagang>> petaPenempatan = new HashMap<>();

    // HashSet: menyimpan NIM yang sudah pernah terdaftar (unik, tanpa duplikat)
    private Set<String> nimTerdaftar = new HashSet<>();

    /**
     * Mendaftarkan peserta magang baru ke sistem.
     * Mengecek dulu ke HashSet supaya NIM yang sama tidak bisa daftar dua kali.
     */
    public boolean daftarkanPeserta(PesertaMagang peserta) {
        if (nimTerdaftar.contains(peserta.getNim())) {
            System.out.println("Pendaftaran gagal: NIM " + peserta.getNim() + " sudah terdaftar.");
            return false;
        }
        daftarPeserta.add(peserta);
        nimTerdaftar.add(peserta.getNim());
        System.out.println("Peserta " + peserta.getNamaLengkap() + " berhasil didaftarkan.");
        return true;
    }

    /**
     * Menempatkan peserta ke sebuah divisi & pembimbing.
     *
     * EXCEPTION HANDLING (Pertemuan 10): method ini mendeklarasikan 'throws'
     * karena bisa melempar KuotaMagangPenuhException jika kuota divisi penuh.
     */
    public void tempatkanPeserta(PesertaMagang peserta, PerusahaanDivisi divisi, Pembimbing pembimbing)
            throws KuotaMagangPenuhException {

        List<PesertaMagang> pesertaDiDivisi = petaPenempatan.getOrDefault(divisi.getIdDivisi(), new ArrayList<>());

        if (pesertaDiDivisi.size() >= divisi.getKuotaMaksimal()) {
            // throw: melempar exception secara manual ketika kuota sudah penuh
            throw new KuotaMagangPenuhException(
                    "Penempatan gagal: kuota divisi " + divisi.getNamaDivisi() + " sudah penuh ("
                            + divisi.getKuotaMaksimal() + " peserta)."
            );
        }

        peserta.tempatkanKe(divisi, pembimbing);
        pembimbing.tambahBimbingan(peserta);
        pesertaDiDivisi.add(peserta);
        petaPenempatan.put(divisi.getIdDivisi(), pesertaDiDivisi);
    }

    /**
     * Contoh pemanggilan method yang berpotensi melempar exception,
     * ditangani dengan try-catch-finally (Pertemuan 10).
     */
    public void cobaTempatkanPeserta(PesertaMagang peserta, PerusahaanDivisi divisi, Pembimbing pembimbing) {
        try {
            tempatkanPeserta(peserta, divisi, pembimbing);
            System.out.println("Penempatan " + peserta.getNamaLengkap() + " ke " + divisi + " berhasil.");
        } catch (KuotaMagangPenuhException e) {
            // catch: menangani exception supaya program tidak crash
            System.out.println("Gagal menempatkan peserta: " + e.getMessage());
        } finally {
            // finally: selalu dijalankan baik berhasil maupun gagal
            System.out.println("Proses penempatan untuk " + peserta.getNamaLengkap() + " selesai diproses.\n");
        }
    }

    // ===== POLIMORFISME STATIS (Pertemuan 9): Method Overloading =====
    // Tiga versi method cariPeserta() dengan parameter berbeda-beda.
    // Java memilih method yang dipanggil berdasarkan jumlah/tipe parameter
    // saat COMPILE TIME.

    // Versi 1: cari berdasarkan NIM
    public PesertaMagang cariPeserta(String nim) throws PesertaTidakDitemukanException {
        for (PesertaMagang p : daftarPeserta) {
            if (p.getNim().equalsIgnoreCase(nim)) {
                return p;
            }
        }
        throw new PesertaTidakDitemukanException("Peserta dengan NIM " + nim + " tidak ditemukan.");
    }

    // Versi 2 (overload): cari berdasarkan nama dan program studi sekaligus
    public PesertaMagang cariPeserta(String nama, String programStudi) throws PesertaTidakDitemukanException {
        for (PesertaMagang p : daftarPeserta) {
            if (p.getNamaLengkap().equalsIgnoreCase(nama) && p.getProgramStudi().equalsIgnoreCase(programStudi)) {
                return p;
            }
        }
        throw new PesertaTidakDitemukanException("Peserta atas nama " + nama + " di prodi " + programStudi + " tidak ditemukan.");
    }

    // Versi 3 (overload): cari semua peserta berdasarkan idDivisi (return List, bukan satu objek)
    public List<PesertaMagang> cariPeserta(int idDivisi) {
        return petaPenempatan.getOrDefault(idDivisi, new ArrayList<>());
    }

    public List<PesertaMagang> getDaftarPeserta() {
        return daftarPeserta;
    }

    public Set<String> getNimTerdaftar() {
        return nimTerdaftar;
    }

    public Map<Integer, List<PesertaMagang>> getPetaPenempatan() {
        return petaPenempatan;
    }
}
