package com.example.skripsi.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Penyakit implements Parcelable {

    int id;
    String kodePenyakit;
    String namaPenyakit;
    String prosedur;
    String peralatan;

    public String getProsedur() {
        return prosedur;
    }

    public void setProsedur(String prosedur) {
        this.prosedur = prosedur;
    }

    public String getPeralatan() {
        return peralatan;
    }

    public void setPeralatan(String peralatan) {
        this.peralatan = peralatan;
    }

    public String getLama_perawatan() {
        return lama_perawatan;
    }

    public void setLama_perawatan(String lama_perawatan) {
        this.lama_perawatan = lama_perawatan;
    }

    String lama_perawatan;

    protected Penyakit(Parcel in) {
        id = in.readInt();
        kodePenyakit = in.readString();
        namaPenyakit = in.readString();
        prosedur = in.readString();
        peralatan = in.readString();
        lama_perawatan = in.readString();
    }

    public Penyakit(int id, String kodePenyakit, String namaPenyakit, String prosedur, String peralatan, String lama_perawatan){
        this.id = id;
        this.kodePenyakit = kodePenyakit;
        this.namaPenyakit = namaPenyakit;
        this.prosedur = prosedur;
        this.peralatan = peralatan;
        this.lama_perawatan = lama_perawatan;
    }

    public Penyakit(){

    }

    public static final Creator<Penyakit> CREATOR = new Creator<Penyakit>() {
        @Override
        public Penyakit createFromParcel(Parcel in) {
            return new Penyakit(in);
        }

        @Override
        public Penyakit[] newArray(int size) {
            return new Penyakit[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKodePenyakit() {
        return kodePenyakit;
    }

    public void setKodePenyakit(String kodePenyakit) {
        this.kodePenyakit = kodePenyakit;
    }

    public String getNamaPenyakit() {
        return namaPenyakit;
    }

    public void setNamaPenyakit(String namaPenyakit) {
        this.namaPenyakit = namaPenyakit;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(kodePenyakit);
        dest.writeString(namaPenyakit);
        dest.writeString(prosedur);
        dest.writeString(peralatan);
        dest.writeString(lama_perawatan);
    }
}
