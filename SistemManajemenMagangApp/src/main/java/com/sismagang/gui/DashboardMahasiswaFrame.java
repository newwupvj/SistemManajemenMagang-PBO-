package com.sismagang.gui;

import com.sismagang.db.PesertaMagangDAO;
import com.sismagang.model.PesertaMagang;
import com.sismagang.service.MagangService;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

/**
 * DashboardMahasiswaFrame - (Pertemuan 13: GUI dengan Java Swing)
 *
 * Tampilan untuk role Mahasiswa: mendaftar magang lewat form, lalu melihat
 * status penempatan & data pembimbing setelah diproses oleh Admin.
 *
 * Sejak terhubung ke JDBC (Pertemuan 14), setiap pendaftaran lewat form ini
 * langsung di-INSERT ke tabel peserta_magang di MySQL lewat PesertaMagangDAO,
 * selain disimpan juga ke MagangService (in-memory) supaya pencarian &
 * penempatan di Dashboard Admin tetap cepat tanpa query berulang-ulang.
 */
public class DashboardMahasiswaFrame extends JFrame {

    private MagangService magangService;
    private PesertaMagangDAO pesertaMagangDAO = new PesertaMagangDAO();

    private JTextField fieldNim, fieldNama, fieldProdi, fieldDurasi;
    private JTextArea areaStatus;

    public DashboardMahasiswaFrame(MagangService magangService) {
        this.magangService = magangService;

        setTitle("Dashboard Mahasiswa - Sistem Informasi Manajemen Magang");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        add(buatFormPendaftaran(), BorderLayout.NORTH);
        add(buatPanelStatus(), BorderLayout.CENTER);
    }

    private JPanel buatFormPendaftaran() {
        // GridLayout 5 baris x 2 kolom untuk form input (Pertemuan 13)
        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Form Pendaftaran Magang"));

        panel.add(new JLabel("NIM:"));
        fieldNim = new JTextField();
        panel.add(fieldNim);

        panel.add(new JLabel("Nama Lengkap:"));
        fieldNama = new JTextField();
        panel.add(fieldNama);

        panel.add(new JLabel("Program Studi:"));
        fieldProdi = new JTextField();
        panel.add(fieldProdi);

        panel.add(new JLabel("Durasi Magang (bulan):"));
        fieldDurasi = new JTextField();
        panel.add(fieldDurasi);

        JButton btnDaftar = new JButton("Daftar Magang");
        // Event handling (Pertemuan 13)
        btnDaftar.addActionListener(e -> daftarMagang());
        panel.add(btnDaftar);

        JButton btnCekStatus = new JButton("Cek Status Saya");
        btnCekStatus.addActionListener(e -> cekStatus());
        panel.add(btnCekStatus);

        return panel;
    }

    private JPanel buatPanelStatus() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));

        areaStatus = new JTextArea();
        areaStatus.setEditable(false);
        panel.add(new JScrollPane(areaStatus), BorderLayout.CENTER);

        return panel;
    }

    private void daftarMagang() {
        String nim = fieldNim.getText().trim();
        String nama = fieldNama.getText().trim();
        String prodi = fieldProdi.getText().trim();
        String durasiText = fieldDurasi.getText().trim();

        if (nim.isEmpty() || nama.isEmpty() || prodi.isEmpty() || durasiText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field wajib diisi.");
            return;
        }

        try {
            // Explicit casting String -> int (Pertemuan 1), dibungkus try-catch
            // untuk menangani NumberFormatException jika input bukan angka (Pertemuan 10)
            int durasi = Integer.parseInt(durasiText);

            PesertaMagang peserta = new PesertaMagang(
                    nim, "magang123", nama, nim, prodi, durasi
            );

            boolean berhasil = magangService.daftarkanPeserta(peserta);
            if (!berhasil) {
                areaStatus.setText("Pendaftaran gagal: NIM " + nim + " sudah pernah terdaftar.");
                return;
            }

            // ===== JDBC: simpan juga ke MySQL (Pertemuan 14) =====
            // id_divisi sementara diisi 1 (default) karena penempatan divisi
            // sesungguhnya baru ditentukan oleh Admin lewat proses verifikasi.
            try {
                pesertaMagangDAO.tambah(peserta, 1);
                areaStatus.setText("Pendaftaran berhasil & tersimpan ke database!\n\n" + peserta.lihatLaporanAkhir());
            } catch (SQLException dbEx) {
                // Data tetap masuk ke MagangService (in-memory) walau gagal ke DB,
                // supaya GUI tetap bisa dipakai meski koneksi database bermasalah.
                areaStatus.setText("Pendaftaran tersimpan sementara, TAPI gagal disimpan ke database.\n"
                        + "Error: " + dbEx.getMessage());
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Durasi magang harus berupa angka.");
        }
    }

    private void cekStatus() {
        String nim = fieldNim.getText().trim();
        try {
            PesertaMagang peserta = magangService.cariPeserta(nim);
            StringBuilder sb = new StringBuilder();
            sb.append(peserta.lihatLaporanAkhir()).append("\n\n");
            if (peserta.isSudahDitempatkan()) {
                sb.append("Divisi Penempatan : ").append(peserta.getDivisiPenempatan()).append("\n");
                sb.append("Pembimbing         : ").append(peserta.getPembimbing().getNamaLengkap());
            } else {
                sb.append("Status: Belum ditempatkan oleh Admin.");
            }
            areaStatus.setText(sb.toString());
        } catch (Exception ex) {
            areaStatus.setText("Data tidak ditemukan: " + ex.getMessage());
        }
    }
}
