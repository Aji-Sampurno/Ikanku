package com.TA.ikanku.model;

public class ModelDataKeranjang {String idproduk, idpenjual, namaproduk, stok, hargaproduk, deskripsi, idkeranjang, idpengguna, jumlah, gambarproduk;

    public ModelDataKeranjang(){}

    public ModelDataKeranjang(String idproduk, String idpenjual, String namaproduk, String stok, String hargaproduk, String deskripsi, String idkeranjang, String idpengguna, String jumlah, String gambarproduk) {
        this.idproduk = idproduk;
        this.idpenjual = idpenjual;
        this.namaproduk = namaproduk;
        this.stok = stok;
        this.hargaproduk = hargaproduk;
        this.deskripsi = deskripsi;
        this.idkeranjang = idkeranjang;
        this.idpengguna = idpengguna;
        this.jumlah = jumlah;
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

    public String getIdkeranjang() {
        return idkeranjang;
    }

    public void setIdkeranjang(String idkeranjang) { this.idkeranjang = idkeranjang; }

    public String getIdpengguna() {
        return idpengguna;
    }

    public void setIdpengguna(String idpengguna) { this.idpengguna = idpengguna; }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) { this.jumlah = jumlah; }

    public String getGambarproduk() { return gambarproduk; }

    public void setGambarproduk(String gambarproduk) { this.gambarproduk = gambarproduk; }
}