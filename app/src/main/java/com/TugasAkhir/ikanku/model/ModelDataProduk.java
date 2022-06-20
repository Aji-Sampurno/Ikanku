package com.TugasAkhir.ikanku.model;

import java.nio.charset.Charset;

public class ModelDataProduk {String idproduk, idpenjual, namaproduk, stok, hargaproduk, deskripsi, gambarproduk;

    public ModelDataProduk(){}

    public ModelDataProduk(String idproduk, String idpenjual, String namaproduk, String stok, String hargaproduk, String deskripsi, String gambarproduk) {
        this.idproduk = idproduk;
        this.idpenjual = idpenjual;
        this.namaproduk = namaproduk;
        this.stok = stok;
        this.hargaproduk = hargaproduk;
        this.deskripsi = deskripsi;
        this.gambarproduk = gambarproduk;
    }
    public String getIdProduk() {
        return idproduk;
    }

    public void setIdProduk(String idproduk) {
        this.idproduk = idproduk;
    }

    public String getIdPenjual() {
        return idpenjual;
    }

    public void setIdPenjual(String idpenjual) { this.idpenjual = idpenjual; }

    public String getNamaProduk() {
        return namaproduk;
    }

    public void setNamaProduk(String namaproduk) {
        this.namaproduk = namaproduk;
    }

    public String getStok() {
        return stok;
    }

    public void setStok(String stok) {
        this.stok = stok;
    }

    public String getHargaProduk() {
        return hargaproduk;
    }

    public void setHargaProduk(String hargaproduk) { this.hargaproduk = hargaproduk; }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) { this.deskripsi = deskripsi; }

    public String getGambarproduk() { return gambarproduk; }

    public void setGambarproduk(String gambarproduk) { this.gambarproduk = gambarproduk; }

    public Charset toLowerCase() {
        return toLowerCase();
    }
}