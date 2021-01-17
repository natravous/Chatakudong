package com.example.chatakudong.model;

public class Tab {
    private String ID, notelpon, nama, keterangan, tanggal;

    public Tab() {
    }

    public Tab(String ID, String notelpon, String nama, String keterangan, String tanggal) {
        this.ID = ID;
        this.notelpon = notelpon;
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

    public String getNotelpon() {
        return notelpon;
    }

    public void setNotelpon(String notelpon) {
        this.notelpon = notelpon;
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
