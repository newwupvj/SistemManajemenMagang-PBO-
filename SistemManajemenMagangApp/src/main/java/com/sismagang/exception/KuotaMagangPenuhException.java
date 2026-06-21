package com.sismagang.exception;

/**
 * KuotaMagangPenuhException - CUSTOM EXCEPTION (Pertemuan 10: Exception Handling)
 *
 * Checked exception (extends Exception, bukan RuntimeException) yang dilempar
 * ketika ada percobaan menempatkan peserta magang ke sebuah divisi yang
 * kuotanya sudah penuh. Karena checked exception, method yang melempar
 * exception ini WAJIB dideklarasikan dengan 'throws', dan pemanggilnya
 * WAJIB menangkap dengan try-catch atau melempar ulang.
 */
public class KuotaMagangPenuhException extends Exception {

    public KuotaMagangPenuhException(String message) {
        super(message); // meneruskan pesan error ke constructor Exception bawaan Java
    }
}
