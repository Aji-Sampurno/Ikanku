package com.TugasAkhir.ikanku.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.TugasAkhir.ikanku.util.ServerApi;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.TugasAkhir.ikanku.Preferences;
import com.TugasAkhir.ikanku.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Akun extends AppCompatActivity {

    private LinearLayout dikemas,dikirim,diterima;
    private TextView riwayatpesanan,pengaturanakun,pusatbantuan,badgedikemas,badgedikirim,badgediterima;
    private Button btnlogout;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akun);

        sessionManager  = new SessionManager(this);

        dikemas         = findViewById(R.id.dikemas);
        dikirim         = findViewById(R.id.dikirim);
        diterima        = findViewById(R.id.diterima);
        riwayatpesanan  = findViewById(R.id.riwayatpesanan);
        pengaturanakun  = findViewById(R.id.pengaturanakun);
        pusatbantuan    = findViewById(R.id.pusatbantuan);
        badgedikemas    = findViewById(R.id.badgedikemas);
        badgedikirim    = findViewById(R.id.badgedikirim);
        badgediterima   = findViewById(R.id.badgediterima);
        btnlogout       = findViewById(R.id.btnlogout);

        riwayatpesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Akun.this, Pesanan.class);
                Akun.this.startActivity(in);
            }
        });

        dikirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Akun.this, Pesanan.class);
                Akun.this.startActivity(in);
            }
        });

        dikemas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Akun.this, Pesanan.class);
                Akun.this.startActivity(in);
            }
        });

        diterima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Akun.this, Pesanan.class);
                Akun.this.startActivity(in);
            }
        });

        pengaturanakun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Akun.this, PengaturanAkun.class);
                Akun.this.startActivity(in);
            }
        });

        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Akun.this);
                builder.setTitle("Konfirmasi Logout").
                        setMessage("apakah kamu yakin untuk logout?");
                builder.setPositiveButton("Ya",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                sessionManager.logout();
                                Intent i = new Intent(Akun.this, Login.class);
                                startActivity(i);
                                finish();
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

    private void pesanandikemas()
    {
        final ProgressDialog progressDialog = new ProgressDialog(Akun.this);

        RequestQueue queue = Volley.newRequestQueue(Akun.this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_PESANANDIKEMAS +  Preferences.getKeyIdPengguna(Akun.this),null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);

                                String strTotal = data.getString("total");

                                if (strTotal.equals("0")){
                                    badgedikemas.setVisibility(View.GONE);
                                }else {
                                    badgedikemas.setVisibility(View.VISIBLE);
                                    badgedikemas.setText(strTotal);
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

    private void pesanandikirim()
    {
        final ProgressDialog progressDialog = new ProgressDialog(Akun.this);

        RequestQueue queue = Volley.newRequestQueue(Akun.this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_PESANANDIKIRIM +  Preferences.getKeyIdPengguna(Akun.this),null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);

                                String strTotal = data.getString("total");

                                if (strTotal.equals("0")){
                                    badgedikirim.setVisibility(View.GONE);
                                }else {
                                    badgedikirim.setVisibility(View.VISIBLE);
                                    badgedikirim.setText(strTotal);
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

    private void pesananditerima()
    {
        final ProgressDialog progressDialog = new ProgressDialog(Akun.this);

        RequestQueue queue = Volley.newRequestQueue(Akun.this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_PESANANDITERIMA +  Preferences.getKeyIdPengguna(Akun.this),null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);

                                String strTotal = data.getString("total");

                                if (strTotal.equals("0")){
                                    badgediterima.setVisibility(View.GONE);
                                }else {
                                    badgediterima.setVisibility(View.VISIBLE);
                                    badgediterima.setText(strTotal);
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

    @Override
    public void onResume() {
        super.onResume();
        pesanandikemas();
        pesanandikirim();
        pesananditerima();
    }
}