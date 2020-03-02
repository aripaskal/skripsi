package com.example.skripsi.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Hubungan implements Parcelable {
    String gejala;

    public Hubungan(String gejala, String penyakit){
        this.gejala = gejala;
        this.penyakit=penyakit;
    }

    public Hubungan(){

    }

    protected Hubungan(Parcel in) {
        gejala = in.readString();
        penyakit = in.readString();
    }

    public static final Creator<Hubungan> CREATOR = new Creator<Hubungan>() {
        @Override
        public Hubungan createFromParcel(Parcel in) {
            return new Hubungan(in);
        }

        @Override
        public Hubungan[] newArray(int size) {
            return new Hubungan[size];
        }
    };

    public String getgejala() {
        return gejala;
    }

    public void setgejala(String gejala) {
        this.gejala = gejala;
    }

    public String getpenyakit() {
        return penyakit;
    }

    public void setpenyakit(String penyakit) {
        this.penyakit = penyakit;
    }

    String penyakit;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(gejala);
        dest.writeString(penyakit);
    }
}
