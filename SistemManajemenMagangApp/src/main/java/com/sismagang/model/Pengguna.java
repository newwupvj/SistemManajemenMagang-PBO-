package com.sismagang.model;

/**
 * Pengguna - ABSTRACT CLASS (Pertemuan 11: Abstract Class & Interface)
 *
 * Class ini tidak bisa diinstansiasi langsung (abstract), tapi menjadi
 * induk/blueprint umum untuk semua jenis pengguna sistem: PesertaMagang,
 * Pembimbing, dan Admin. Ketiganya punya kemiripan (punya username,
 * password, perlu login) tapi punya perilaku method getRole() yang
 * berbeda-beda, sehingga cocok dibuat abstract class daripada interface
 * murni karena ada atribut & method yang sudah punya implementasi (konkrit).
 */
public abstract class Pengguna {

    // ===== ENCAPSULATION (Pertemuan 5): semua atribut private =====
    private String username;
    private String password;
    private String namaLengkap;

    // Constructor (Pertemuan 4)
    public Pengguna(String username, String password, String namaLengkap) {
        this.username = username;
        this.password = password;
        this.namaLengkap = namaLengkap;
    }

    // ===== ABSTRACT METHOD (Pertemuan 11) =====
    // Wajib diimplementasikan oleh setiap subclass dengan caranya masing-masing
    public abstract String getRole();

    // Method konkrit (non-void) yang sudah punya implementasi di induk,
    // bisa langsung dipakai/diwariskan oleh semua subclass (Pertemuan 6: Inheritance)
    public boolean login(String inputUsername, String inputPassword) {
        return this.username.equals(inputUsername) && this.password.equals(inputPassword);
    }

    // ===== GETTER & SETTER (Pertemuan 5: Encapsulation) =====
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username != null && !username.isBlank()) {
            this.username = username;
        }
    }

    // protected: hanya bisa diakses dari dalam package yang sama atau subclass
    // (Pertemuan 5: contoh access modifier 'protected')
    protected String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password != null && password.length() >= 4) {
            this.password = password;
        }
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    // Method yang nanti akan di-override oleh setiap subclass (Pertemuan 6)
    public void tampilkanInfo() {
        System.out.println("Username : " + username);
        System.out.println("Nama     : " + namaLengkap);
        System.out.println("Role     : " + getRole());
    }
}
