package com.TugasAkhir.ikanku.model;

public class ModelDataKategori {String idkategori, namakategori, gambarkategori;

    public ModelDataKategori(){}

    public ModelDataKategori(String idkategori, String namakategori, String gambarkategori) {
        this.idkategori = idkategori;
        this.namakategori = namakategori;
        this.gambarkategori = gambarkategori;
    }

    public String getIdkategori() {
        return idkategori;
    }

    public void setIdkategori(String idkategori) {
        this.idkategori = idkategori;
    }

    public String getNamakategori() {
        return namakategori;
    }

    public void setNamakategori(String namakategori) {
        this.namakategori = namakategori;
    }

    public String getGambarkategori() {
        return gambarkategori;
    }

    public void setGambarkategori(String gambarkategori) {
        this.gambarkategori = gambarkategori;
    }
}
