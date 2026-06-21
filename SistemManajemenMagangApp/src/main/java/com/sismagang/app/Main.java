package com.sismagang.app;

import com.sismagang.db.PesertaMagangDAO;
import com.sismagang.gui.LoginFrame;
import com.sismagang.model.Pembimbing;
import com.sismagang.model.PerusahaanDivisi;
import com.sismagang.model.PesertaMagang;
import com.sismagang.model.PesertaMagangMBKM;
import com.sismagang.service.MagangService;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Main - ENTRY POINT aplikasi (gabungan seluruh Pertemuan 1-14)
 *
 * Class ini menyiapkan beberapa data contoh (dummy data) lalu membuka
 * tampilan GUI Swing (LoginFrame). Beberapa baris di bawah juga
 * mendemonstrasikan langsung konsep POLIMORFISME DINAMIS (Pertemuan 9)
 * lewat console sebelum GUI dibuka.
 */
public class Main {

    public static void main(String[] args) {

        // ===== Menyiapkan data contoh Perusahaan/Divisi & Pembimbing =====
        PerusahaanDivisi divisiIT = new PerusahaanDivisi(1, "PT Republika Media Mandiri", "IT Support", 3);
        PerusahaanDivisi divisiMarketing = new PerusahaanDivisi(2, "PT Republika Media Mandiri", "Marketing", 2);

        List<PerusahaanDivisi> daftarDivisi = new ArrayList<>();
        daftarDivisi.add(divisiIT);
        daftarDivisi.add(divisiMarketing);

        Pembimbing pembimbing1 = new Pembimbing("budi.pembimbing", "pass1234", "Budi Santoso", "NIP001", divisiIT);
        Pembimbing pembimbing2 = new Pembimbing("siti.pembimbing", "pass1234", "Siti Aminah", "NIP002", divisiMarketing);
        // id disamakan dengan data awal di database.sql (NIP001 -> id_pembimbing 1, NIP002 -> id_pembimbing 2)
        pembimbing1.setIdPembimbing(1);
        pembimbing2.setIdPembimbing(2);
        divisiIT.tambahPembimbing(pembimbing1);
        divisiMarketing.tambahPembimbing(pembimbing2);

        List<Pembimbing> daftarPembimbing = new ArrayList<>();
        daftarPembimbing.add(pembimbing1);
        daftarPembimbing.add(pembimbing2);

        // ===== Service utama yang dipakai bersama oleh seluruh GUI =====
        MagangService magangService = new MagangService();

        // ===== Demonstrasi POLIMORFISME DINAMIS (Pertemuan 9) di console =====
        System.out.println("=== Demo Polimorfisme Dinamis ===");
        PesertaMagang pesertaReguler = new PesertaMagang(
                "andi123", "pass123", "Andi Wijaya", "2410501001", "Sistem Informasi", 4);

        // Upcasting: variabel bertipe PesertaMagang (induk) menyimpan objek PesertaMagangMBKM (anak)
        PesertaMagang pesertaMbkm = new PesertaMagangMBKM(
                "rara456", "pass123", "Rara Putri", "2410501002", "Sistem Informasi", 5,
                12, "Praktik Kerja Lapangan");

        // Method yang sama dipanggil, tapi hasilnya berbeda tergantung tipe OBJEK
        // aslinya saat runtime, bukan tipe variabelnya saat compile time.
        pesertaReguler.tampilkanInfo();
        System.out.println("----------------------------------");
        pesertaMbkm.tampilkanInfo();
        System.out.println("===================================\n");

        // Daftarkan kedua peserta contoh ke service supaya bisa dicari lewat GUI
        magangService.daftarkanPeserta(pesertaReguler);
        magangService.daftarkanPeserta(pesertaMbkm);

        // ===== Membuka tampilan GUI Swing (Pertemuan 13) =====
        // Dijalankan lewat SwingUtilities.invokeLater agar GUI berjalan
        // di Event Dispatch Thread (EDT), sesuai praktik standar Swing.
        SwingUtilities.invokeLater(() -> {
            new LoginFrame(magangService, daftarDivisi, daftarPembimbing).setVisible(true);
        });
    }
}
