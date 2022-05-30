package com.TA.ikanku.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.TA.ikanku.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegistrasiPenjual extends AppCompatActivity {

    private static final String TAG = RegistrasiPenjual.class.getSimpleName();
    private EditText    username,nama,telp,password,ulangpassword,alamat,namatoko;
    private Button      btnregister;
    private ProgressBar loading;
    SessionManager      sessionManager;
    String getId,getUsername,getNama,getTelp,getAlamat,getNamatoko,getStatus;
    private static String URL_DAFTARPENJUAL = "https://pusatbelanjaikan.000webhostapp.com/register_penjual.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi_penjual);

        sessionManager = new SessionManager(this);

        loading = findViewById(R.id.loading);
        username = findViewById(R.id.inp_username);
        nama = findViewById(R.id.inp_nama);
        telp = findViewById(R.id.inp_telp);
        alamat = findViewById(R.id.inp_alamat);
        namatoko = findViewById(R.id.inp_namatoko);
        password = findViewById(R.id.inp_password);
        ulangpassword = findViewById(R.id.inp_ulangpassword);
        btnregister = findViewById(R.id.btnregister);

        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(sessionManager.ID_PENGGUNA);
        getUsername = user.get(sessionManager.USERNAME);
        getNama = user.get(sessionManager.NAMA);
        getTelp = user.get(sessionManager.TELP);
        getAlamat = user.get(sessionManager.ALAMAT);
        getNamatoko = user.get(sessionManager.NAMATOKO);
        getStatus = user.get(sessionManager.STATUS);

        username.setText(getUsername);
        nama.setText(getUsername);
        telp.setText(getUsername);
        alamat.setText(getUsername);
        namatoko.setText(getUsername);

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveDetail();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void SaveDetail() {
        loading.setVisibility(View.VISIBLE);
        btnregister.setVisibility(View.GONE);

        final String username = this.username.getText().toString().trim();
        final String nama = this.nama.getText().toString().trim();
        final String telp = this.telp.getText().toString().trim();
        final String alamat = this.alamat.getText().toString().trim();
        final String namatoko = this.namatoko.getText().toString().trim();
        final String password = this.password.getText().toString().trim();
        final String id_pengguna = this.getId;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_DAFTARPENJUAL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")){

                                AlertDialog.Builder builder = new AlertDialog.Builder(RegistrasiPenjual.this);
                                builder.setTitle("Registrasi Berhasil").
                                        setIcon(R.mipmap.ic_sukses_foreground).
                                        setMessage("Registrasi akun anda telah berhasil");
                                builder.setPositiveButton("Login",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                Intent intent = new Intent(RegistrasiPenjual.this, Login.class);
                                                RegistrasiPenjual.this.startActivity(intent);
                                                finish();
                                            }
                                        });
                                AlertDialog alert11 = builder.create();
                                alert11.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            loading.setVisibility(View.GONE);
                            btnregister.setVisibility(View.VISIBLE);

                            AlertDialog.Builder builder = new AlertDialog.Builder(RegistrasiPenjual.this);
                            builder.setTitle("Registrasi Gagal").
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
                        loading.setVisibility(View.GONE);
                        btnregister.setVisibility(View.VISIBLE);

                        AlertDialog.Builder builder = new AlertDialog.Builder(RegistrasiPenjual.this);
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
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username",username);
                params.put("nama",nama);
                params.put("telp",telp);
                params.put("alamat",alamat);
                params.put("nama_toko",namatoko);
                params.put("password",password);
                params.put("id_pengguna",id_pengguna);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}