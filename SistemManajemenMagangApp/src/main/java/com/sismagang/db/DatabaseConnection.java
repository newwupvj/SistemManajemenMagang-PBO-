package com.sismagang.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DatabaseConnection - (Pertemuan 14: JDBC Database Connectivity)
 *
 * Menyediakan satu titik koneksi ke database MySQL menggunakan DriverManager.
 * Sesuaikan URL, USER, dan PASSWORD dengan konfigurasi MySQL di komputer Anda.
 */
public class DatabaseConnection {

    // Sesuaikan dengan database lokal Anda (XAMPP/Laragon/MySQL Workbench, dll)
    private static final String URL = "jdbc:mysql://localhost:3306/db_sismagang";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    /**
     * Membuka koneksi baru ke database menggunakan DriverManager.
     * Dipanggil setiap kali sebuah class DAO butuh akses ke database.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
