package com.TA.ikanku.model;

public class ModelDataEdukasi {String idedukasi, judul, kategoriedukasi, isi, gambaredukasi;

    public ModelDataEdukasi(){}

    public ModelDataEdukasi(String idedukasi, String judul, String kategoriedukasi, String isi, String gambaredukasi) {
        this.idedukasi = idedukasi;
        this.judul = judul;
        this.kategoriedukasi = kategoriedukasi;
        this.isi = isi;
        this.gambaredukasi = gambaredukasi;
    }
    public String getIdedukasi() {
        return idedukasi;
    }

    public void setIdedukasi(String idedukasi) {
        this.idedukasi = idedukasi;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) { this.judul = judul; }

    public String getKategoriedukasi() {
        return kategoriedukasi;
    }

    public void setKategoriedukasi(String kategoriedukasi) { this.kategoriedukasi = kategoriedukasi; }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }

    public String getGambaredukasi() {
        return gambaredukasi;
    }

    public void setGambaredukasi(String gambaredukasi) {
        this.gambaredukasi = gambaredukasi;
    }
}
