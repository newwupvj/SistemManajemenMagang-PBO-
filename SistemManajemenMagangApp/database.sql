-- =====================================================================
-- database.sql
-- Sistem Informasi Manajemen Magang
-- Pertemuan 14: JDBC Database Connectivity
-- =====================================================================

CREATE DATABASE IF NOT EXISTS db_sismagang;
USE db_sismagang;

-- =====================================================================
-- Tabel: perusahaan_divisi
-- Menyimpan data perusahaan & divisi tempat magang
-- =====================================================================
CREATE TABLE IF NOT EXISTS perusahaan_divisi (
    id_divisi      INT AUTO_INCREMENT PRIMARY KEY,
    nama_perusahaan VARCHAR(100) NOT NULL,
    nama_divisi     VARCHAR(50)  NOT NULL,
    kuota_maksimal  INT NOT NULL DEFAULT 5
);

-- =====================================================================
-- Tabel: pembimbing
-- Setiap pembimbing terikat pada satu divisi (foreign key id_divisi)
-- =====================================================================
CREATE TABLE IF NOT EXISTS pembimbing (
    id_pembimbing  INT AUTO_INCREMENT PRIMARY KEY,
    nip            VARCHAR(20) NOT NULL UNIQUE,
    nama_lengkap   VARCHAR(100) NOT NULL,
    username       VARCHAR(50) NOT NULL UNIQUE,
    password       VARCHAR(100) NOT NULL,
    id_divisi      INT NOT NULL,
    CONSTRAINT fk_pembimbing_divisi
        FOREIGN KEY (id_divisi) REFERENCES perusahaan_divisi(id_divisi)
        ON DELETE CASCADE
);

-- =====================================================================
-- Tabel: peserta_magang
-- Setiap peserta magang terikat pada satu divisi & satu pembimbing
-- (foreign key id_divisi dan id_pembimbing, boleh NULL jika belum
-- ditempatkan)
-- =====================================================================
CREATE TABLE IF NOT EXISTS peserta_magang (
    id_peserta     INT AUTO_INCREMENT PRIMARY KEY,
    nim            VARCHAR(20) NOT NULL UNIQUE,
    nama_lengkap   VARCHAR(100) NOT NULL,
    program_studi  VARCHAR(50) NOT NULL,
    durasi_bulan   INT NOT NULL DEFAULT 3,
    nilai_akhir    DECIMAL(5,2) DEFAULT 0,
    sudah_ditempatkan BOOLEAN DEFAULT FALSE,
    id_divisi      INT NULL,
    id_pembimbing  INT NULL,
    CONSTRAINT fk_peserta_divisi
        FOREIGN KEY (id_divisi) REFERENCES perusahaan_divisi(id_divisi)
        ON DELETE SET NULL,
    CONSTRAINT fk_peserta_pembimbing
        FOREIGN KEY (id_pembimbing) REFERENCES pembimbing(id_pembimbing)
        ON DELETE SET NULL
);

-- =====================================================================
-- Contoh data awal (opsional, untuk testing)
-- =====================================================================
INSERT INTO perusahaan_divisi (nama_perusahaan, nama_divisi, kuota_maksimal)
VALUES
    ('PT Republika Media Mandiri', 'IT Support', 3),
    ('PT Republika Media Mandiri', 'Marketing', 2);

INSERT INTO pembimbing (nip, nama_lengkap, username, password, id_divisi)
VALUES
    ('NIP001', 'Budi Santoso', 'budi.pembimbing', 'pass1234', 1),
    ('NIP002', 'Siti Aminah', 'siti.pembimbing', 'pass1234', 2);
