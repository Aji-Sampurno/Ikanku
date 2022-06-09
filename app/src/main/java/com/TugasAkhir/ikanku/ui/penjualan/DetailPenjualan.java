package com.TugasAkhir.ikanku.ui.penjualan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.TugasAkhir.ikanku.R;
import com.TugasAkhir.ikanku.ui.Penjualan;
import com.TugasAkhir.ikanku.ui.SessionManager;
import com.TugasAkhir.ikanku.util.FormatCurrency;
import com.TugasAkhir.ikanku.util.ServerApi;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetailPenjualan extends AppCompatActivity {

    ImageView gambarproduk;
    Button btnkirim;
    TextView tvidproduk, tvidpenjual, tvnamaproduk, tvstok, tvhargaproduk,tvdeskripsi,tvidpesanan,tvidpengguna,tvjumlah,tvtotalbayar,tvalamat,tvpengiriman,tvtelp,tvstatus;
    ProgressDialog pd;
    String         getStatus;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_penjualan);
        Intent data = getIntent();
        final int update = data.getIntExtra("update",0);
        String intentidpesanan = data.getStringExtra("idpesanan");
        String intentidproduk = data.getStringExtra("idproduk");
        String intentidpenjual = data.getStringExtra("idpenjual");
        String intentnamaproduk = data.getStringExtra("namaproduk");
        String intentstok = data.getStringExtra("stok");
        String intenthargaproduk = data.getStringExtra("hargaproduk");
        String intentdeskripsi = data.getStringExtra("deskripsi");
        String intentgambarproduk = data.getStringExtra("gambarproduk");
        String intentinvoice = data.getStringExtra("invoice");
        String intentpembeli = data.getStringExtra("pembeli");
        String intentjumlah = data.getStringExtra("jumlah");
        String intentbayar = data.getStringExtra("bayar");
        String intentpengiriman = data.getStringExtra("pengiriman");
        String intentalamat = data.getStringExtra("alamat");
        String intenttelp = data.getStringExtra("telp");
        String intentstatus = data.getStringExtra("status");

        sessionManager  = new SessionManager(this);

        HashMap<String, String> user = sessionManager.getUserDetail();
        getStatus       = user.get(sessionManager.STATUS);

        tvidproduk      = findViewById(R.id.ppidproduk);
        tvidpenjual     = findViewById(R.id.ppidpenjual);
        tvnamaproduk    = findViewById(R.id.ppnamaproduk);
        tvstok          = findViewById(R.id.ppstok);
        tvhargaproduk   = findViewById(R.id.ppharga);
        tvdeskripsi     = findViewById(R.id.ppdeskripsi);
        tvidpesanan     = findViewById(R.id.ppidpesanan);
        tvidpengguna    = findViewById(R.id.ppidpengguna);
        tvjumlah        = findViewById(R.id.ppjumlah);
        gambarproduk    = findViewById(R.id.ppgambarproduk);
        tvalamat        = findViewById(R.id.pesananalamat);
        tvpengiriman    = findViewById(R.id.pesananpengiriman);
        tvtelp          = findViewById(R.id.pesanantelp);
        tvtotalbayar    = findViewById(R.id.totalbayar);
        tvstatus        = findViewById(R.id.ppstatus);
        btnkirim       = findViewById(R.id.btnkirim);
        pd              = new ProgressDialog(DetailPenjualan.this);

        FormatCurrency currency = new FormatCurrency();

        if(update == 1){
            tvidproduk.setText(intentidproduk);
            tvidpenjual.setText(intentidpenjual);
            tvnamaproduk.setText(intentnamaproduk);
            tvstok.setText(intentstok);
            tvhargaproduk.setText(currency.formatRupiah(intenthargaproduk));
            tvdeskripsi.setText(intentdeskripsi);
            tvidpesanan.setText(intentidpesanan);
            tvidpengguna.setText(intentpembeli);
            tvjumlah.setText(intentjumlah);
            tvalamat.setText(intentalamat);
            tvpengiriman.setText(intentpengiriman);
            tvtelp.setText(intenttelp);
            tvstatus.setText(intentstatus);
            tvtotalbayar.setText(currency.formatRupiah(intentbayar));
            Glide.with(DetailPenjualan.this).load(intentgambarproduk).centerCrop().into(gambarproduk);
        }

        if (intentstatus.equals("baru")){
            btnkirim.setVisibility(View.VISIBLE);
        }

        btnkirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailPenjualan.this);
                builder.setTitle("Kirim Pesanan").
                        setIcon(R.mipmap.ic_confirm_foreground).
                        setMessage("Yakin ingin mengirim pesanan ?");
                builder.setPositiveButton("Ya",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                kirim_pesanan();
                            }
                        });
                builder.setNegativeButton("Tidak",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert11 = builder.create();
                alert11.show();
            }
        });

    }

    private void kirim_pesanan() {
        pd.setMessage("Menyimpan Data");
        pd.setCancelable(false);
        pd.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_KIRIMPESANAN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.cancel();
                        try {
                            JSONObject res = new JSONObject(response);

                            AlertDialog.Builder builder = new AlertDialog.Builder(DetailPenjualan.this);
                            builder.setTitle("Kirim Pesanan").
                                    setIcon(R.mipmap.ic_sukses_foreground).
                                    setMessage("Produk siap dikirim");
                            builder.setNegativeButton("Lihat Keranjang",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                                Intent i = new Intent(DetailPenjualan.this, Penjualan.class);
                                                startActivity(i);
                                                finish();
                                        }
                                    });
                            AlertDialog alert11 = builder.create();
                            alert11.show();

                        } catch (JSONException e) {
                            e.printStackTrace();

                            AlertDialog.Builder builder = new AlertDialog.Builder(DetailPenjualan.this);
                            builder.setTitle("Kesalahan").
                                    setIcon(R.mipmap.ic_warning_foreground).
                                    setMessage("Terdapat Kesalahan");
                            builder.setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });
                            AlertDialog alert11 = builder.create();
                            alert11.show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.cancel();

                        AlertDialog.Builder builder = new AlertDialog.Builder(DetailPenjualan.this);
                        builder.setTitle("Kesalahan Jaringan").
                                setIcon(R.mipmap.ic_kesalahan_jaringan_foreground).
                                setMessage("Terdapat kesalahan jaringan");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                        AlertDialog alert11 = builder.create();
                        alert11.show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("idpesanan",tvidpesanan.getText().toString());
                System.out.println(map);

                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}