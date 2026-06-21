package com.sismagang.gui;

import com.sismagang.db.PesertaMagangDAO;
import com.sismagang.exception.PesertaTidakDitemukanException;
import com.sismagang.model.Pembimbing;
import com.sismagang.model.PerusahaanDivisi;
import com.sismagang.model.PesertaMagang;
import com.sismagang.service.MagangService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

/**
 * DashboardAdminFrame - (Pertemuan 13: GUI dengan Java Swing)
 *
 * Tampilan untuk role Admin: melihat seluruh peserta magang yang
 * terdaftar, mencari peserta berdasarkan NIM, dan melihat ringkasan
 * jumlah peserta terdaftar (memanfaatkan static counter Pertemuan 3).
 *
 * Sejak terhubung ke JDBC (Pertemuan 14): tombol "Muat dari Database"
 * melakukan READ langsung dari tabel peserta_magang di MySQL, dan tombol
 * "Verifikasi & Tempatkan" melakukan UPDATE ke kolom id_divisi,
 * id_pembimbing, dan sudah_ditempatkan di database.
 */
public class DashboardAdminFrame extends JFrame {

    private MagangService magangService;
    private List<PerusahaanDivisi> daftarDivisi;
    private List<Pembimbing> daftarPembimbing;
    private PesertaMagangDAO pesertaMagangDAO = new PesertaMagangDAO();

    private DefaultTableModel tableModel;
    private JTable tabelPeserta;
    private JTextField fieldCariNim;
    private JLabel labelTotalPeserta;

    public DashboardAdminFrame(MagangService magangService, List<PerusahaanDivisi> daftarDivisi,
                                List<Pembimbing> daftarPembimbing) {
        this.magangService = magangService;
        this.daftarDivisi = daftarDivisi;
        this.daftarPembimbing = daftarPembimbing;

        setTitle("Dashboard Admin - Sistem Informasi Manajemen Magang");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // BorderLayout sebagai layout utama frame (Pertemuan 13)
        setLayout(new BorderLayout(10, 10));

        add(buatPanelAtas(), BorderLayout.NORTH);
        add(buatPanelTabel(), BorderLayout.CENTER);
        add(buatPanelBawah(), BorderLayout.SOUTH);

        muatDataKeTabel();
    }

    private JPanel buatPanelAtas() {
        // GridLayout untuk menyusun komponen pencarian secara rapi (Pertemuan 13)
        JPanel panel = new JPanel(new GridLayout(1, 3, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        panel.add(new JLabel("Cari Peserta (NIM):"));

        fieldCariNim = new JTextField();
        panel.add(fieldCariNim);

        JButton btnCari = new JButton("Cari Peserta");
        // Event handling dengan addActionListener (Pertemuan 13)
        btnCari.addActionListener(e -> cariPeserta());
        panel.add(btnCari);

        return panel;
    }

    private JScrollPane buatPanelTabel() {
        String[] kolom = {"NIM", "Nama", "Program Studi", "Durasi (bulan)", "Nilai Akhir", "Status"};
        tableModel = new DefaultTableModel(kolom, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // tabel hanya untuk ditampilkan, bukan diedit langsung
            }
        };
        tabelPeserta = new JTable(tableModel);
        return new JScrollPane(tabelPeserta);
    }

    private JPanel buatPanelBawah() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));

        labelTotalPeserta = new JLabel();
        panel.add(labelTotalPeserta, BorderLayout.WEST);

        // Panel kanan menampung 2 tombol sekaligus (Pertemuan 13: GridLayout)
        JPanel panelTombolKanan = new JPanel(new GridLayout(1, 2, 5, 0));

        JButton btnMuatDariDB = new JButton("Muat dari Database");
        // READ dari MySQL (Pertemuan 14: JDBC)
        btnMuatDariDB.addActionListener(e -> muatDariDatabase());
        panelTombolKanan.add(btnMuatDariDB);

        JButton btnVerifikasiTempatkan = new JButton("Verifikasi & Tempatkan");
        btnVerifikasiTempatkan.addActionListener(e -> verifikasiDanTempatkan());
        panelTombolKanan.add(btnVerifikasiTempatkan);

        panel.add(panelTombolKanan, BorderLayout.EAST);

        return panel;
    }

    private void muatDataKeTabel() {
        tableModel.setRowCount(0); // bersihkan dulu isi tabel
        for (PesertaMagang p : magangService.getDaftarPeserta()) {
            tableModel.addRow(new Object[]{
                    p.getNim(),
                    p.getNamaLengkap(),
                    p.getProgramStudi(),
                    p.getDurasiMagangBulan(),
                    p.getNilaiAkhir(),
                    p.isSudahDitempatkan() ? "Sudah Ditempatkan" : "Belum Ditempatkan"
            });
        }
        // Menampilkan total peserta terdaftar memakai static counter (Pertemuan 3)
        labelTotalPeserta.setText("Total Peserta Terdaftar: " + PesertaMagang.getTotalPesertaTerdaftar());
    }

    private void cariPeserta() {
        String nim = fieldCariNim.getText().trim();
        if (nim.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Masukkan NIM terlebih dahulu.");
            return;
        }
        try {
            // try-catch menangani custom exception (Pertemuan 10) langsung dari GUI
            PesertaMagang hasil = magangService.cariPeserta(nim);
            JOptionPane.showMessageDialog(this, hasil.lihatLaporanAkhir());
        } catch (PesertaTidakDitemukanException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Tidak Ditemukan", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void muatDariDatabase() {
        try {
            // READ langsung dari tabel peserta_magang di MySQL (Pertemuan 14)
            List<PesertaMagang> dariDatabase = pesertaMagangDAO.ambilSemua();

            tableModel.setRowCount(0);
            for (PesertaMagang p : dariDatabase) {
                tableModel.addRow(new Object[]{
                        p.getNim(),
                        p.getNamaLengkap(),
                        p.getProgramStudi(),
                        p.getDurasiMagangBulan(),
                        p.getNilaiAkhir(),
                        p.isSudahDitempatkan() ? "Sudah Ditempatkan" : "Belum Ditempatkan"
                });
            }
            labelTotalPeserta.setText("Total dari Database: " + dariDatabase.size() + " peserta");
            JOptionPane.showMessageDialog(this, "Berhasil memuat " + dariDatabase.size() + " data dari database.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Gagal konek ke database: " + ex.getMessage(),
                    "Error Database", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void verifikasiDanTempatkan() {
        int row = tabelPeserta.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih peserta pada tabel terlebih dahulu.");
            return;
        }
        if (daftarDivisi.isEmpty() || daftarPembimbing.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Data divisi/pembimbing belum tersedia.");
            return;
        }

        String nim = (String) tableModel.getValueAt(row, 0);
        try {
            PesertaMagang peserta = magangService.cariPeserta(nim);
            PerusahaanDivisi divisi = daftarDivisi.get(0);
            Pembimbing pembimbing = daftarPembimbing.get(0);

            // cobaTempatkanPeserta sudah menangani try-catch-finally di dalam service-nya
            // (memperbarui status di memori / MagangService)
            magangService.cobaTempatkanPeserta(peserta, divisi, pembimbing);

            // ===== JDBC: sinkronkan penempatan ke MySQL juga (Pertemuan 14) =====
            try {
                pesertaMagangDAO.updatePenempatan(nim, divisi.getIdDivisi(), pembimbing.getIdPembimbing());
                JOptionPane.showMessageDialog(this, "Penempatan berhasil disimpan ke database.");
            } catch (SQLException dbEx) {
                JOptionPane.showMessageDialog(this,
                        "Penempatan tersimpan di sesi ini, TAPI gagal disimpan ke database.\nError: " + dbEx.getMessage(),
                        "Peringatan", JOptionPane.WARNING_MESSAGE);
            }

            muatDataKeTabel();
        } catch (PesertaTidakDitemukanException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
}
