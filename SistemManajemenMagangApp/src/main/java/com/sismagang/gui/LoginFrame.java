package com.sismagang.gui;

import com.sismagang.model.Pembimbing;
import com.sismagang.model.PerusahaanDivisi;
import com.sismagang.service.MagangService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * LoginFrame - (Pertemuan 13: GUI dengan Java Swing)
 *
 * Tampilan pembuka aplikasi: pengguna memilih akan masuk sebagai
 * Admin atau Mahasiswa (multi-role), lalu sistem membuka dashboard
 * yang sesuai.
 */
public class LoginFrame extends JFrame {

    public LoginFrame(MagangService magangService, List<PerusahaanDivisi> daftarDivisi,
                       List<Pembimbing> daftarPembimbing) {

        setTitle("Login - Sistem Informasi Manajemen Magang");
        setSize(400, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel labelJudul = new JLabel("SISTEM INFORMASI MANAJEMEN MAGANG", SwingConstants.CENTER);
        labelJudul.setFont(new Font("SansSerif", Font.BOLD, 14));
        add(labelJudul, BorderLayout.NORTH);

        JPanel panelTombol = new JPanel(new GridLayout(2, 1, 10, 10));
        panelTombol.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JButton btnAdmin = new JButton("Masuk sebagai Admin");
        btnAdmin.addActionListener(e -> {
            new DashboardAdminFrame(magangService, daftarDivisi, daftarPembimbing).setVisible(true);
        });

        JButton btnMahasiswa = new JButton("Masuk sebagai Mahasiswa");
        btnMahasiswa.addActionListener(e -> {
            new DashboardMahasiswaFrame(magangService).setVisible(true);
        });

        panelTombol.add(btnAdmin);
        panelTombol.add(btnMahasiswa);
        add(panelTombol, BorderLayout.CENTER);
    }
}
