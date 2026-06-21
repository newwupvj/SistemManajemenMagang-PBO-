package com.sismagang.exception;

/**
 * PesertaTidakDitemukanException - CUSTOM EXCEPTION KEDUA (Pertemuan 10)
 *
 * Dilempar saat proses pencarian peserta magang (berdasarkan NIM, misalnya)
 * tidak menemukan data yang dicari di dalam sistem.
 */
public class PesertaTidakDitemukanException extends Exception {

    public PesertaTidakDitemukanException(String message) {
        super(message);
    }
}
