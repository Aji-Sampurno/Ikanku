package com.TugasAkhir.ikanku.util;

public class ServerApi {

    private static final String BASE_URL                    = "https://jualikanku.my.id/Penjual";
    public static final String URL_LOGIN                    = BASE_URL + "/Login";
    public static final String URL_DAFTARPENGGUNA           = BASE_URL + "/Daftar_Pengguna";
    public static final String URL_DAFTARPENJUAL            = BASE_URL + "/Daftar_Penjual";
    public static final String URL_EDITAKUN                 = BASE_URL + "/Edit_Akun";
    public static final String URL_DAFTAREDUKASI            = BASE_URL + "/Edukasi";
    public static final String URL_TAMBAHPRODUK             = BASE_URL + "/TambahProduk";
    public static final String URL_EDITPRODUK               = BASE_URL + "/EditProduk";
    public static final String URL_DELETEPRODUK             = BASE_URL + "/DeleteProduk";
    public static final String URL_LAPORKANPRODUK           = BASE_URL + "/LaporkanProduk";
    public static final String URL_PENDAPATAN               = BASE_URL + "/Pendapatan?idpenjual=";
    public static final String URL_PENGELUARAN              = BASE_URL + "/Pengeluaran?idpenjual=";
    public static final String URL_DAFTARPROVINSI           = BASE_URL + "/Provinsi";
    public static final String URL_DAFTARKABUPATEN          = BASE_URL + "/Kabupaten";
    public static final String URL_DAFTARKECAMATAN          = BASE_URL + "/Kecamatan";
    public static final String URL_DAFTARKATEGORI           = BASE_URL + "/Kategori";
    public static final String URL_DAFTARBARANG             = BASE_URL + "/ListBarang";
    public static final String URL_DAFTARBARANGKATEGORI     = BASE_URL + "/ListProdukKategori?namakategori=";
    public static final String URL_DAFTARKERANJANG          = BASE_URL + "/ListKeranjang?idpengguna=";
    public static final String URL_DAFTARPRODUK             = BASE_URL + "/ListProduk?idpenjual=";
    public static final String URL_DAFTARPENDAPATAN         = BASE_URL + "/ListLaporanPendapatan";
    public static final String URL_DAFTARPENGELUARAN        = BASE_URL + "/ListLaporanPengeluaran";
    public static final String URL_DAFTARFILTERPENDAPATAN   = BASE_URL + "/ListFilterLaporanPendapatan?idpengguna=";
    public static final String URL_DAFTARFILTERPENGELUARAN  = BASE_URL + "/ListFilterLaporanPengeluaran?idpengguna=";
    public static final String URL_DAFTARPENJUALANBARU      = BASE_URL + "/ListPenjualanBaru?idpenjual=";
    public static final String URL_DAFTARPENJUALANDIKIRIM   = BASE_URL + "/ListPenjualanDikirim?idpenjual=";
    public static final String URL_DAFTARPENJUALANSELESAI   = BASE_URL + "/ListPenjualanSelesai?idpenjual=";
    public static final String URL_DAFTARTEMP               = BASE_URL + "/ListTemp?pembeli=";
    public static final String URL_DAFTARPESANANDIKEMAS     = BASE_URL + "/ListPesananDikemas?pembeli=";
    public static final String URL_DAFTARPESANANDIKIRIM     = BASE_URL + "/ListPesananDikirim?pembeli=";
    public static final String URL_DAFTARPESANANDITERIMA    = BASE_URL + "/ListPesananDiterima?pembeli=";
    public static final String URL_PENJUALANBARU            = BASE_URL + "/PenjualanBaru?idpenjual=";
    public static final String URL_PENJUALANDIKIRIM         = BASE_URL + "/PenjualanDikirim?idpenjual=";
    public static final String URL_PENJUALANSELESAI         = BASE_URL + "/PenjualanSelesai?idpenjual=";
    public static final String URL_PESANANDIKEMAS           = BASE_URL + "/PesananDikemas?pembeli=";
    public static final String URL_PESANANDIKIRIM           = BASE_URL + "/PesananDikirim?pembeli=";
    public static final String URL_PESANANDITERIMA          = BASE_URL + "/PesananDiterima?pembeli=";
    public static final String URL_BUATPESANAN              = BASE_URL + "/TambahPesanan";
    public static final String URL_CHECKOUT                 = BASE_URL + "/Checkout";
    public static final String URL_KIRIMPESANAN             = BASE_URL + "/KirimPesanan";
    public static final String URL_TERIMAPESANAN            = BASE_URL + "/TerimaPesanan";
    public static final String URL_TAMBAHKERANJANG          = BASE_URL + "/TambahKeranjang";
    public static final String URL_KERANJANG                = BASE_URL + "/Keranjang?idpengguna=";
    public static final String URL_TEMP                     = BASE_URL + "/Temp?idpengguna=";
    public static final String URL_TOTALBAYAR               = BASE_URL + "/TotalBayar?idpengguna=";
    public static final String URL_HAPUSKERANJANG           = BASE_URL + "/HapusKeranjang?idkeranjang=";
    public static final String URL_PRODUKTERJUAL            = BASE_URL + "/ProdukTerjual?idpengguna=";
    public static final String URL_PRODUKDIBELI             = BASE_URL + "/ProdukDibeli?idpengguna=";
    public static final String URL_PRODUKPENDAPATAN         = BASE_URL + "/ProdukPendapatan?idpengguna=";
    public static final String URL_PRODUKPENGELUARAN        = BASE_URL + "/ProdukPengeluaran?idpengguna=";
    public static final String URL_HAPUSTEMP                = BASE_URL + "/HapusTemp?pembeli=";

}
