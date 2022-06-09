package com.TugasAkhir.ikanku.model;

public class ModelDataPesanan  {String idproduk, idpenjual, namaproduk, stok, hargaproduk, deskripsi, jumlah, gambarproduk, idpesanan, invoice, pembeli, bayar, pengiriman, alamat, telp, status;

    public ModelDataPesanan(){}

    public ModelDataPesanan(String idproduk, String idpenjual, String namaproduk, String stok, String hargaproduk, String deskripsi, String jumlah, String gambarproduk, String idpesanan, String invoice, String pembeli, String bayar, String pengiriman, String alamat, String telp, String status) {
        this.idproduk = idproduk;
        this.idpenjual = idpenjual;
        this.namaproduk = namaproduk;
        this.stok = stok;
        this.hargaproduk = hargaproduk;
        this.deskripsi = deskripsi;
        this.gambarproduk = gambarproduk;
        this.idpesanan = idpesanan;
        this.invoice = invoice;
        this.pembeli = pembeli;
        this.jumlah = jumlah;
        this.bayar = bayar;
        this.pengiriman = pengiriman;
        this.alamat = alamat;
        this.telp = telp;
        this.status = status;
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

    public String getIdpesanan() {
        return idpesanan;
    }

    public void setIdpesanan(String idpesanan) { this.idpesanan = idpesanan; }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) { this.invoice = invoice; }

    public String getPembeli() {
        return pembeli;
    }

    public void setPembeli(String pembeli) { this.pembeli = pembeli; }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) { this.jumlah = jumlah; }

    public String getBayar() {
        return bayar;
    }

    public void setBayar(String bayar) { this.bayar = bayar; }

    public String getPengiriman() {
        return pengiriman;
    }

    public void setPengiriman(String pengiriman) { this.pengiriman = pengiriman; }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) { this.alamat = alamat; }

    public String getTelp() {
        return telp;
    }

    public void setTelp(String telp) { this.telp = telp; }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) { this.status = status; }
}
