package com.example.skripsi.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Gejala implements Parcelable {

    int id;
    String kode, nama;

    public Gejala(int id, String kode, String nama){
        this.id = id;
        this.kode = kode;
        this.nama = nama;
    }

    public Gejala(){}

    protected Gejala(Parcel in) {
        id = in.readInt();
        kode = in.readString();
        nama = in.readString();
    }

    public static final Creator<Gejala> CREATOR = new Creator<Gejala>() {
        @Override
        public Gejala createFromParcel(Parcel in) {
            return new Gejala(in);
        }

        @Override
        public Gejala[] newArray(int size) {
            return new Gejala[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(kode);
        dest.writeString(nama);
    }
}
