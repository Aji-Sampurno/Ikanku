package com.TA.ikanku.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.TA.ikanku.R;
import com.TA.ikanku.ui.pembeli.PembeliMain;
import com.TA.ikanku.ui.penjual.PenjualMain;
import com.TA.ikanku.util.FormatCurrency;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetailProduk extends AppCompatActivity {

    private static final String TAG = DetailProduk.class.getSimpleName();
    TextView idproduk,namaproduk,stok,hargaproduk,deskripsi;
    EditText jumlah;
    Button btnbeli;
    ImageView gambarproduk;
    private ProgressBar loading;
    SessionManager sessionManager;
    ProgressDialog pd;
    String getId,getStatus;
    Bitmap bitmap;
    private Menu action;
    final int CODE_GALLERY_REQUEST = 999;
    private static String URL_DETAILPRODUK="https://jualanikan.000webhostapp.com/Penjual/DetailProduk?idproduk=";
    private static String URL_TAMBAHKERANJANG="https://jualanikan.000webhostapp.com/Penjual/TambahKeranjang";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_produk);

        Intent data = getIntent();
        String intentidproduk = data.getStringExtra("idproduk");

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
        btnbeli      = findViewById(R.id.btnbeli);
        pd           = new ProgressDialog(DetailProduk.this);

        loadJson(intentidproduk);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        btnbeli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpanData();
            }
        });
    }

    private void loadJson(String intentidproduk)
    {
        pd.setMessage("Memuat...");
        pd.setCancelable(false);
        pd.show();

        RequestQueue queue = Volley.newRequestQueue(this);
        FormatCurrency currency = new FormatCurrency();
        StringRequest updateReq = new StringRequest(Request.Method.GET, URL_DETAILPRODUK + intentidproduk,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.cancel();
                        try {
                            JSONArray jArray = new JSONArray(response);
                            for(int i=0;i<jArray.length();i++){
                                JSONObject res = jArray.getJSONObject(i);
                                idproduk.setText(res.getString("idproduk").trim());
                                namaproduk.setText(res.getString("namaproduk").trim());
                                stok.setText(res.getString("stok").trim());
                                hargaproduk.setText(currency.formatRupiah(res.getString("hargaproduk")));
                                deskripsi.setText(res.getString("deskripsi").trim());

                                Glide.with(DetailProduk.this).load(res.getString("gambarproduk")).centerCrop().into(gambarproduk);
                            }
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
        };

        queue.add(updateReq);
    }

    private void simpanData()
    {
        pd.setMessage("Menyimpan Data");
        pd.setCancelable(false);
        pd.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_TAMBAHKERANJANG,
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
}