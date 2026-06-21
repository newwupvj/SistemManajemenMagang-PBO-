package com.sismagang.db;

import com.sismagang.model.Pembimbing;
import com.sismagang.model.PerusahaanDivisi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * PembimbingDAO - (Pertemuan 14: JDBC Database Connectivity)
 *
 * DAO untuk tabel pembimbing. Karena tabel pembimbing punya foreign key
 * ke perusahaan_divisi (id_divisi), method ambilSemua() di sini melakukan
 * JOIN sederhana supaya nama perusahaan & divisi ikut terbawa, tanpa
 * perlu query terpisah untuk tiap pembimbing.
 */
public class PembimbingDAO {

    // ===== CREATE =====
    public void tambah(Pembimbing pembimbing, int idDivisi) throws SQLException {
        String sql = "INSERT INTO pembimbing (nip, nama_lengkap, username, password, id_divisi) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pembimbing.getNip());
            stmt.setString(2, pembimbing.getNamaLengkap());
            stmt.setString(3, pembimbing.getUsername());
            stmt.setString(4, "pass1234"); // password contoh, idealnya di-hash pada aplikasi produksi
            stmt.setInt(5, idDivisi);

            stmt.executeUpdate();
        }
    }

    // ===== READ (semua data, sekaligus JOIN ke perusahaan_divisi) =====
    public List<Pembimbing> ambilSemua() throws SQLException {
        List<Pembimbing> hasil = new ArrayList<>();
        String sql = "SELECT p.nip, p.nama_lengkap, p.username, p.password, "
                + "d.id_divisi, d.nama_perusahaan, d.nama_divisi, d.kuota_maksimal "
                + "FROM pembimbing p JOIN perusahaan_divisi d ON p.id_divisi = d.id_divisi";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                PerusahaanDivisi divisi = new PerusahaanDivisi(
                        rs.getInt("id_divisi"),
                        rs.getString("nama_perusahaan"),
                        rs.getString("nama_divisi"),
                        rs.getInt("kuota_maksimal")
                );
                Pembimbing p = new Pembimbing(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("nama_lengkap"),
                        rs.getString("nip"),
                        divisi
                );
                hasil.add(p);
            }
        }
        return hasil;
    }

    // ===== UPDATE =====
    public void updateNamaLengkap(String nip, String namaBaru) throws SQLException {
        String sql = "UPDATE pembimbing SET nama_lengkap = ? WHERE nip = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, namaBaru);
            stmt.setString(2, nip);
            stmt.executeUpdate();
        }
    }

    // ===== DELETE =====
    public void hapus(String nip) throws SQLException {
        String sql = "DELETE FROM pembimbing WHERE nip = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nip);
            stmt.executeUpdate();
        }
    }
}
