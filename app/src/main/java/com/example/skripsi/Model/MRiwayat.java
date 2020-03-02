package com.example.skripsi.Model;

public class MRiwayat {
    String nama;
    String penyakitku;
    String tgl;
    String umur;

    public String getUmur() {
        return umur;
    }

    public void setUmur(String umur) {
        this.umur = umur;
    }

    public String getJk() {
        return jk;
    }

    public void setJk(String jk) {
        this.jk = jk;
    }

    String jk;

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getPenyakitku() {
        return penyakitku;
    }

    public void setPenyakitku(String penyakitku) {
        this.penyakitku = penyakitku;
    }

    public String getTgl() {
        return tgl;
    }

    public void setTgl(String tgl) {
        this.tgl = tgl;
    }

    public MRiwayat(String nama, String penyakitku, String tgl, String umur, String jk){
        this.nama=nama;
        this.penyakitku=penyakitku;
        this.tgl = tgl;
        this.umur = umur;
        this.jk = jk;
    }

    public MRiwayat(){ }

}
