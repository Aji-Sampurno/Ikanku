package com.TugasAkhir.ikanku.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.TugasAkhir.ikanku.Preferences;
import com.TugasAkhir.ikanku.R;
import com.TugasAkhir.ikanku.adapter.AdapterKeranjang;
import com.TugasAkhir.ikanku.adapter.AdapterPenjualan;
import com.TugasAkhir.ikanku.adapter.AdapterTemp;
import com.TugasAkhir.ikanku.model.ModelDataKeranjang;
import com.TugasAkhir.ikanku.model.ModelDataTemp;
import com.TugasAkhir.ikanku.ui.pembeli.PembeliMain;
import com.TugasAkhir.ikanku.ui.penjual.PenjualMain;
import com.TugasAkhir.ikanku.util.FormatCurrency;
import com.TugasAkhir.ikanku.util.ServerApi;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Checkout extends AppCompatActivity {

    Button                      bayar;
    TextView                    tvtotalbayar;
    ProgressDialog              pd;
    String                      getAlamat,getId,getTelp,getStatus;
    SessionManager              sessionManager;
    RecyclerView                mRecyclerview;
    RecyclerView.Adapter        mAdapter;
    RecyclerView.LayoutManager  mManager;
    SwipeRefreshLayout          refreshLayout;
    List<ModelDataTemp>         mItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        mRecyclerview = findViewById(R.id.rvtemp);
        mItems = new ArrayList<>();

        loadJson(true);
        totalbayar();

        mManager = new LinearLayoutManager(Checkout.this,LinearLayoutManager.VERTICAL,false);
        mRecyclerview.setLayoutManager(mManager);
        mAdapter = new AdapterTemp(Checkout.this, mItems);
        mRecyclerview.setAdapter(mAdapter);

        sessionManager   = new SessionManager(this);

        HashMap<String, String> user = sessionManager.getUserDetail();
        getId            = user.get(sessionManager.ID_PENGGUNA);
        getAlamat        = user.get(sessionManager.ALAMAT);
        getTelp          = user.get(sessionManager.TELP);
        getStatus        = user.get(sessionManager.STATUS);

        bayar            = findViewById(R.id.btnbayar);
        tvtotalbayar     = findViewById(R.id.totalbayar);
        pd               = new ProgressDialog(Checkout.this);

        bayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { checckout();}
        });
    }

    private void loadJson(boolean showProgressDialog) {
        final ProgressDialog progressDialog = new ProgressDialog(Checkout.this);
        progressDialog.setMessage("Memuat Pesanan...");
        progressDialog.show();
        if (showProgressDialog) progressDialog.show();
        else progressDialog.cancel();

        RequestQueue queue = Volley.newRequestQueue(Checkout.this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_DAFTARTEMP +  Preferences.getKeyIdPengguna(Checkout.this),null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (showProgressDialog) progressDialog.cancel();
                        else refreshLayout.setRefreshing(false);
                        mItems.clear();
                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                ModelDataTemp md = new ModelDataTemp();
                                md.setNamaproduk(data.getString("namaproduk"));
                                md.setHargaproduk(data.getString("hargaproduk"));
                                md.setJumlah(data.getString("jumlah"));
                                md.setBayar(data.getString("bayar"));
                                md.setGambarproduk(data.getString("gambarproduk"));
                                mItems.add(md);
                            } catch (JSONException e) {
                                e.printStackTrace();

                                AlertDialog.Builder builder = new AlertDialog.Builder(Checkout.this);
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

                        mAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        AlertDialog.Builder builder = new AlertDialog.Builder(Checkout.this);
                        builder.setTitle("Kesalahan Jaringan").
                                setIcon(R.mipmap.ic_kesalahan_jaringan_foreground).
                                setMessage("Terdapat kesalahan jaringan saat memuat data");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                        AlertDialog alert11 = builder.create();
                        alert11.show();

                    }
                });

        queue.add(reqData);
    }

    private void checckout() {
        pd.setMessage("Proses Checkout...");
        pd.setCancelable(false);
        pd.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_CHECKOUT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.cancel();
                        try {
                            JSONObject res = new JSONObject(response);

                            AlertDialog.Builder builder = new AlertDialog.Builder(Checkout.this);
                            builder.setTitle("Berhasil").
                                    setIcon(R.mipmap.ic_sukses_foreground).
                                    setMessage("Pembayaran Berhasil");
                            builder.setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            if (getStatus.equals("1")){
                                                Intent i = new Intent(Checkout.this, PembeliMain.class);
                                                startActivity(i);
                                                finish();
                                            }else {
                                                Intent i = new Intent(Checkout.this, PenjualMain.class);
                                                startActivity(i);
                                                finish();
                                            }
                                        }
                                    });
                            builder.setNegativeButton("Lihat Keranjang",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            if (getStatus.equals("1")){
                                                Intent i = new Intent(Checkout.this, PembeliMain.class);
                                                startActivity(i);
                                                finish();
                                            }else {
                                                Intent i = new Intent(Checkout.this, Keranjang.class);
                                                startActivity(i);
                                                finish();
                                            }
                                        }
                                    });
                            AlertDialog alert11 = builder.create();
                            alert11.show();

                        } catch (JSONException e) {
                            e.printStackTrace();

                            AlertDialog.Builder builder = new AlertDialog.Builder(Checkout.this);
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

                        AlertDialog.Builder builder = new AlertDialog.Builder(Checkout.this);
                        builder.setTitle("Kesalahan Jaringan").
                                setIcon(R.mipmap.ic_kesalahan_jaringan_foreground).
                                setMessage("Terdapat kesalahan jaringan saat memuat data");
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
                map.put("idpengguna",getId);
                System.out.println(map);

                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void totalbayar() {
        final ProgressDialog progressDialog = new ProgressDialog(Checkout.this);

        RequestQueue queue = Volley.newRequestQueue(Checkout.this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_TOTALBAYAR + Preferences.getKeyIdPengguna(Checkout.this),null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        FormatCurrency currency = new FormatCurrency();
                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);

                                String strTotal = data.getString("total");
                                String strJual  = data.getString("beli");

                                if (strJual.equals("0")){
                                    tvtotalbayar.setText(currency.formatRupiah("000000"));
                                }else {
                                    tvtotalbayar.setText(currency.formatRupiah(strTotal));
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        queue.add(reqData);
    }
}