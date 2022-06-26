package com.TugasAkhir.ikanku.model;

public class ModelDataTemp {String namaproduk, hargaproduk, jumlah, bayar, gambarproduk;
    public ModelDataTemp(){}

    public ModelDataTemp(String namaproduk, String hargaproduk, String jumlah, String bayar, String gambarproduk) {
        this.namaproduk = namaproduk;
        this.hargaproduk = hargaproduk;
        this.jumlah = jumlah;
        this.bayar = bayar;
        this.gambarproduk = gambarproduk;
    }

    public String getNamaproduk() {
        return namaproduk;
    }

    public void setNamaproduk(String namaproduk) {
        this.namaproduk = namaproduk;
    }

    public String getHargaproduk() {
        return hargaproduk;
    }

    public void setHargaproduk(String hargaproduk) {
        this.hargaproduk = hargaproduk;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getBayar() {
        return bayar;
    }

    public void setBayar(String bayar) {
        this.bayar = bayar;
    }

    public String getGambarproduk() {
        return gambarproduk;
    }

    public void setGambarproduk(String gambarproduk) {
        this.gambarproduk = gambarproduk;
    }
}
