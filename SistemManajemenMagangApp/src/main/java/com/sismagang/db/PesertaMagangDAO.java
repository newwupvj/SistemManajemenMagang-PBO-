package com.sismagang.db;

import com.sismagang.model.PesertaMagang;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * PesertaMagangDAO - (Pertemuan 14: JDBC Database Connectivity)
 *
 * DAO (Data Access Object) berisi operasi CRUD ke tabel peserta_magang.
 * Semua query memakai PreparedStatement (BUKAN Statement biasa) supaya
 * aman dari SQL Injection, karena parameter di-bind terpisah dari query.
 */
public class PesertaMagangDAO {

    // ===== CREATE =====
    public void tambah(PesertaMagang peserta, int idDivisi) throws SQLException {
        String sql = "INSERT INTO peserta_magang (nim, nama_lengkap, program_studi, durasi_bulan, nilai_akhir, id_divisi) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        // try-with-resources: Connection & PreparedStatement otomatis ditutup
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Parameter di-bind lewat tanda tanya (?), bukan digabung string -> aman dari SQL Injection
            stmt.setString(1, peserta.getNim());
            stmt.setString(2, peserta.getNamaLengkap());
            stmt.setString(3, peserta.getProgramStudi());
            stmt.setInt(4, peserta.getDurasiMagangBulan());
            stmt.setDouble(5, peserta.getNilaiAkhir());
            stmt.setInt(6, idDivisi);

            stmt.executeUpdate();
        }
    }

    // ===== READ (semua data) =====
    public List<PesertaMagang> ambilSemua() throws SQLException {
        List<PesertaMagang> hasil = new ArrayList<>();
        String sql = "SELECT * FROM peserta_magang";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // ResultSet dibaca baris per baris dengan rs.next()
            while (rs.next()) {
                PesertaMagang p = new PesertaMagang(
                        rs.getString("nim"),       // dipakai sementara sebagai username
                        "default123",               // password default, idealnya dari kolom terpisah
                        rs.getString("nama_lengkap"),
                        rs.getString("nim"),
                        rs.getString("program_studi"),
                        rs.getInt("durasi_bulan")
                );
                p.beriNilai(rs.getDouble("nilai_akhir"));
                hasil.add(p);
            }
        }
        return hasil;
    }

    // ===== UPDATE (khusus penempatan: id_divisi, id_pembimbing, status) =====
    public void updatePenempatan(String nim, int idDivisi, int idPembimbing) throws SQLException {
        String sql = "UPDATE peserta_magang SET id_divisi = ?, id_pembimbing = ?, sudah_ditempatkan = TRUE WHERE nim = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idDivisi);
            stmt.setInt(2, idPembimbing);
            stmt.setString(3, nim);
            stmt.executeUpdate();
        }
    }

    // ===== UPDATE =====
    public void updateNilai(String nim, double nilaiBaru) throws SQLException {
        String sql = "UPDATE peserta_magang SET nilai_akhir = ? WHERE nim = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, nilaiBaru);
            stmt.setString(2, nim);
            stmt.executeUpdate();
        }
    }

    // ===== DELETE =====
    public void hapus(String nim) throws SQLException {
        String sql = "DELETE FROM peserta_magang WHERE nim = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nim);
            stmt.executeUpdate();
        }
    }
}
