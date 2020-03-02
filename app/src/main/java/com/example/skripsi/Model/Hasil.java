package com.example.skripsi.Model;

public class Hasil {
    double jumlah;
    String penyakit;

    public Hasil(String penyakit, double jumlah){
        this.penyakit =penyakit;
        this.jumlah = jumlah;
    }

    public Hasil(){}

    public double getJumlah() {
        return jumlah;
    }

    public void setJumlah(double jumlah) {
        this.jumlah = jumlah;
    }

    public String getPenyakit() {
        return penyakit;
    }

    public void setPenyakit(String penyakit) {
        this.penyakit = penyakit;
    }
}
