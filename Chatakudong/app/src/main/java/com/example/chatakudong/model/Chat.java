package com.example.chatakudong.model;

public class Chat {
    private String ID, nama, keterangan, tanggal;

    public Chat() {
    }

    public Chat(String ID, String nama, String keterangan, String tanggal) {
        this.ID = ID;
        this.nama = nama;
        this.keterangan = keterangan;
        this.tanggal = tanggal;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}
