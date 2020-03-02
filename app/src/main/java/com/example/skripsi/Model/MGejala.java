package com.example.skripsi.Model;

public class MGejala {
    private boolean check;
    int idgejala;
    String penyakit;

    public String getPenyakit() {
        return penyakit;
    }

    public void setPenyakit(String penyakit) {
        this.penyakit = penyakit;
    }

    public int getIdgejala() {
        return idgejala;
    }

    public void setIdgejala(int idgejala) {
        this.idgejala = idgejala;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public MGejala(String gejala, boolean check, int idgejala, String penyakit){
        this.check = check;
        this.gejala=gejala;
        this.idgejala=idgejala;
        this.penyakit=penyakit;
    }

    public MGejala(){

    }

    public String getGejala() {
        return gejala;
    }

    public void setGejala(String gejalaa) {
        gejala = gejalaa;
    }

    String gejala;
}
