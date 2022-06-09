package com.TugasAkhir.ikanku.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.TugasAkhir.ikanku.util.ServerApi;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.TugasAkhir.ikanku.R;
import com.TugasAkhir.ikanku.ui.pembeli.PembeliMain;
import com.TugasAkhir.ikanku.ui.penjual.PenjualMain;
import com.TugasAkhir.ikanku.util.FormatCurrency;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetailProduk extends AppCompatActivity {

    TextView idproduk,namaproduk,stok,hargaproduk,deskripsi;
    EditText jumlah;
    Button btnbeli,btnlaporan;
    ImageView gambarproduk,ivlaporan;
    SessionManager sessionManager;
    ProgressDialog pd;
    String getId,getStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_produk);

        Intent data = getIntent();
        final int update = data.getIntExtra("update",0);
        String intentidproduk = data.getStringExtra("idproduk");
        String intentidpenjual = data.getStringExtra("idpenjual");
        String intentnamaproduk = data.getStringExtra("namaproduk");
        String intentstok = data.getStringExtra("stok");
        String intenthargaproduk = data.getStringExtra("hargaproduk");
        String intentdeskripsi = data.getStringExtra("deskripsi");
        String intentgambar = data.getStringExtra("gambarproduk");

        sessionManager = new SessionManager(this);

        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(sessionManager.ID_PENGGUNA);
        getStatus       = user.get(sessionManager.STATUS);

        idproduk     = findViewById(R.id.editidproduk);
        namaproduk   = findViewById(R.id.editnamaproduk);
        stok         = findViewById(R.id.editstok);
        hargaproduk  = findViewById(R.id.edithargaproduk);
        deskripsi    = findViewById(R.id.editdeskripsi);
        jumlah       = findViewById(R.id.jumlah);
        gambarproduk = findViewById(R.id.ivproduk);
        ivlaporan    = findViewById(R.id.ivlaporan);
        btnbeli      = findViewById(R.id.btnbeli);
        btnlaporan   = findViewById(R.id.btnlaporan);
        pd           = new ProgressDialog(DetailProduk.this);

        FormatCurrency currency = new FormatCurrency();

        if(update == 1)
        {
            idproduk.setText(intentidproduk);
            namaproduk.setText(intentnamaproduk);
            stok.setText(intentstok);
            hargaproduk.setText(currency.formatRupiah(intenthargaproduk));
            deskripsi.setText(intentdeskripsi);
            Glide.with(DetailProduk.this).load(intentgambar).centerCrop().into(gambarproduk);
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        btnbeli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String beli = jumlah.getText().toString();
                Integer jumlahbeli = Integer.valueOf(beli);
                Integer stokbarang = Integer.valueOf(intentstok);
                if (jumlahbeli>stokbarang){
                    AlertDialog.Builder builder = new AlertDialog.Builder(DetailProduk.this);
                    builder.setTitle("Gagal").
                            setIcon(R.mipmap.ic_cancel_foreground).
                            setMessage("Jumlah Melebihi Stok");
                    builder.setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert11 = builder.create();
                    alert11.show();
                }else {
                    simpanData();
                }
            }
        });

        ivlaporan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                laporkan();
            }
        });
    }

    private void simpanData()
    {
        pd.setMessage("Menyimpan Data");
        pd.setCancelable(false);
        pd.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_TAMBAHKERANJANG,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.cancel();
                        try {
                            JSONObject res = new JSONObject(response);

                            AlertDialog.Builder builder = new AlertDialog.Builder(DetailProduk.this);
                            builder.setTitle("Tambah Keranjang").
                                    setIcon(R.mipmap.ic_sukses_foreground).
                                    setMessage("Produk berhasil ditambahkan ke keranjang");
                            builder.setPositiveButton("Lanjut Belanja",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            if (getStatus.equals("1")){
                                                Intent i = new Intent(DetailProduk.this, PembeliMain.class);
                                                startActivity(i);
                                                finish();
                                            }else {
                                                Intent i = new Intent(DetailProduk.this, PenjualMain.class);
                                                startActivity(i);
                                                finish();
                                            }
                                        }
                                    });
                            builder.setNegativeButton("Lihat Keranjang",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });
                            AlertDialog alert11 = builder.create();
                            alert11.show();

                        } catch (JSONException e) {
                            e.printStackTrace();

                            AlertDialog.Builder builder = new AlertDialog.Builder(DetailProduk.this);
                            builder.setTitle("Kesalahan Memuat").
                                    setIcon(R.mipmap.ic_warning_foreground).
                                    setMessage("Terdapat Kesalahan saat memuat data");
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

                        AlertDialog.Builder builder = new AlertDialog.Builder(DetailProduk.this);
                        builder.setTitle("Kesalahan Jaringan").
                                setIcon(R.mipmap.ic_kesalahan_jaringan_foreground).
                                setMessage("Terdapat Kesalahan jaringan saat memuat data");
                        builder.setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
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
                map.put("idpengguna",getId);
                map.put("idproduk",idproduk.getText().toString());
                map.put("jumlah",jumlah.getText().toString());
                System.out.println(map);

                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void laporkan()
    {
        pd.setMessage("Melaporkan Produk");
        pd.setCancelable(false);
        pd.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_LAPORKANPRODUK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.cancel();
                        try {
                            JSONObject res = new JSONObject(response);

                            AlertDialog.Builder builder = new AlertDialog.Builder(DetailProduk.this);
                            builder.setTitle("Laporkan Produk").
                                    setIcon(R.mipmap.ic_sukses_foreground).
                                    setMessage("Produk berhasil dilaporkan");
                            builder.setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });
                            AlertDialog alert11 = builder.create();
                            alert11.show();

                        } catch (JSONException e) {
                            e.printStackTrace();

                            AlertDialog.Builder builder = new AlertDialog.Builder(DetailProduk.this);
                            builder.setTitle("Update Produk").
                                    setIcon(R.mipmap.ic_cancel_foreground).
                                    setMessage("Produk anda gagal diupdate");
                            builder.setPositiveButton("Ulangi",
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

                        AlertDialog.Builder builder = new AlertDialog.Builder(DetailProduk.this);
                        builder.setTitle("Kesalahan Jaringan").
                                setIcon(R.mipmap.ic_kesalahan_jaringan_foreground).
                                setMessage("Terdapat kesalahan jaringan saat melakukan update");
                        builder.setPositiveButton("Ulangi",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        builder.setNegativeButton("Batal",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        if (getStatus.equals("1")){
                                            Intent intent = new Intent(DetailProduk.this, PembeliMain.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Intent intent = new Intent(DetailProduk.this, PenjualMain.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                });
                        AlertDialog alert11 = builder.create();
                        alert11.show();

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("idproduk",idproduk.getText().toString());
                System.out.println(map);

                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}