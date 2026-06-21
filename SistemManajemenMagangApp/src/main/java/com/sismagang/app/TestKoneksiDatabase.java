package com.sismagang.app;

import com.sismagang.db.PesertaMagangDAO;
import com.sismagang.model.PesertaMagang;

import java.sql.SQLException;
import java.util.List;

/**
 * TestKoneksiDatabase - Class terpisah khusus untuk MENCOBA operasi CRUD
 * ke database lewat PesertaMagangDAO (Pertemuan 14: JDBC).
 *
 * Dipisah dari Main.java supaya:
 *  1. Main.java (GUI) tetap bisa dijalankan tanpa wajib MySQL nyala dulu.
 *  2. Operasi database bisa dites kapan saja secara independen, cukup
 *     klik kanan file ini -> Run, tanpa harus klik-klik tombol di GUI.
 *
 * CARA PAKAI:
 *  1. Pastikan MySQL sudah jalan dan database.sql sudah dijalankan.
 *  2. Sesuaikan USER/PASSWORD di DatabaseConnection.java jika perlu.
 *  3. Klik kanan file ini di IntelliJ -> Run 'TestKoneksiDatabase.main()'.
 */
public class TestKoneksiDatabase {

    public static void main(String[] args) {

        PesertaMagangDAO dao = new PesertaMagangDAO();

        // ===== CREATE: menambah peserta magang baru ke database =====
        try {
            PesertaMagang pesertaBaru = new PesertaMagang(
                    "2410501099", "magang123", "Citra Lestari",
                    "2410501099", "Sistem Informasi", 4
            );
            pesertaBaru.beriNilai(88.5);

            // id_divisi = 1 mengacu ke data awal di database.sql (IT Support)
            dao.tambah(pesertaBaru, 1);
            System.out.println("[CREATE] Berhasil menambah peserta: " + pesertaBaru.getNamaLengkap());

        } catch (SQLException e) {
            // try-catch menangani SQLException (Pertemuan 10: Exception Handling)
            System.out.println("[CREATE] Gagal: " + e.getMessage());
        }

        // ===== READ: menampilkan semua peserta magang dari database =====
        try {
            List<PesertaMagang> semuaPeserta = dao.ambilSemua();
            System.out.println("\n[READ] Total data di database: " + semuaPeserta.size());
            for (PesertaMagang p : semuaPeserta) {
                System.out.println(" - " + p.getNim() + " | " + p.getNamaLengkap()
                        + " | Nilai: " + p.getNilaiAkhir());
            }
        } catch (SQLException e) {
            System.out.println("[READ] Gagal: " + e.getMessage());
        }

        // ===== UPDATE: mengubah nilai akhir peserta tertentu =====
        try {
            dao.updateNilai("2410501099", 95.0);
            System.out.println("\n[UPDATE] Nilai peserta 2410501099 berhasil diubah menjadi 95.0");
        } catch (SQLException e) {
            System.out.println("[UPDATE] Gagal: " + e.getMessage());
        }

        // ===== DELETE: menghapus peserta dari database =====
        try {
            dao.hapus("2410501099");
            System.out.println("[DELETE] Peserta 2410501099 berhasil dihapus dari database.");
        } catch (SQLException e) {
            System.out.println("[DELETE] Gagal: " + e.getMessage());
        }
    }
}
