package com.sismagang.db;

import com.sismagang.model.PerusahaanDivisi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * PerusahaanDivisiDAO - (Pertemuan 14: JDBC Database Connectivity)
 *
 * DAO untuk tabel perusahaan_divisi. Pola dan struktur method sama persis
 * dengan PesertaMagangDAO, supaya konsisten: semua pakai PreparedStatement
 * dan try-with-resources.
 */
public class PerusahaanDivisiDAO {

    // ===== CREATE =====
    public void tambah(PerusahaanDivisi divisi) throws SQLException {
        String sql = "INSERT INTO perusahaan_divisi (nama_perusahaan, nama_divisi, kuota_maksimal) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, divisi.getNamaPerusahaan());
            stmt.setString(2, divisi.getNamaDivisi());
            stmt.setInt(3, divisi.getKuotaMaksimal());

            stmt.executeUpdate();
        }
    }

    // ===== READ (semua data) =====
    public List<PerusahaanDivisi> ambilSemua() throws SQLException {
        List<PerusahaanDivisi> hasil = new ArrayList<>();
        String sql = "SELECT * FROM perusahaan_divisi";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                PerusahaanDivisi d = new PerusahaanDivisi(
                        rs.getInt("id_divisi"),
                        rs.getString("nama_perusahaan"),
                        rs.getString("nama_divisi"),
                        rs.getInt("kuota_maksimal")
                );
                hasil.add(d);
            }
        }
        return hasil;
    }

    // ===== UPDATE =====
    public void updateKuota(int idDivisi, int kuotaBaru) throws SQLException {
        String sql = "UPDATE perusahaan_divisi SET kuota_maksimal = ? WHERE id_divisi = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, kuotaBaru);
            stmt.setInt(2, idDivisi);
            stmt.executeUpdate();
        }
    }

    // ===== DELETE =====
    public void hapus(int idDivisi) throws SQLException {
        String sql = "DELETE FROM perusahaan_divisi WHERE id_divisi = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idDivisi);
            stmt.executeUpdate();
        }
    }
}
