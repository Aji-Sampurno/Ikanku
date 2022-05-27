package com.TA.ikanku.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.TA.ikanku.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Registrasi extends AppCompatActivity {

    private EditText    username,nama,telp,password,ulangpassword,alamat;
    private Button      btnregister;
    private ProgressBar loading;
    private TextView    login;
    private static String URL_DAFTARPENGGUNA="https://pusatbelanjaikan.000webhostapp.com/register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);

        loading         = findViewById(R.id.loading);
        username        = findViewById(R.id.inp_username);
        nama            = findViewById(R.id.inp_nama);
        telp            = findViewById(R.id.inp_telp);
        alamat          = findViewById(R.id.inp_alamat);
        password        = findViewById(R.id.inp_password);
        ulangpassword   = findViewById(R.id.inp_ulangpassword);
        btnregister     = findViewById(R.id.btnregister);
        login           = findViewById(R.id.login);

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Registrasi.this, Login.class));
            }
        });
    }

    private void Register(){
        loading.setVisibility(View.VISIBLE);
        btnregister.setVisibility(View.GONE);

        final String username       = this.username.getText().toString().trim();
        final String nama           = this.nama.getText().toString().trim();
        final String telp           = this.telp.getText().toString().trim();
        final String alamat         = this.alamat.getText().toString().trim();
        final String password       = this.password.getText().toString().trim();
        final String ulangpassword  = this.ulangpassword.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_DAFTARPENGGUNA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")){

                                AlertDialog.Builder builder = new AlertDialog.Builder(Registrasi.this);
                                builder.setTitle("Registrasi Berhasil").
                                        setIcon(R.mipmap.ic_sukses_foreground).
                                        setMessage("Registrasi akun anda telah berhasil");
                                builder.setPositiveButton("Login",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                Intent intent = new Intent(Registrasi.this, Login.class);
                                                Registrasi.this.startActivity(intent);
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

                            AlertDialog.Builder builder = new AlertDialog.Builder(Registrasi.this);
                            builder.setTitle("Registrasi Gagal").
                                    setIcon(R.mipmap.ic_warning_foreground).
                                    setMessage("Terdapat kesalahan saat melakukan registrasi");
                            builder.setPositiveButton("Coba Lagi",
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

                        AlertDialog.Builder builder = new AlertDialog.Builder(Registrasi.this);
                        builder.setTitle("Kesalahan Jaringan").
                                setIcon(R.mipmap.ic_kesalahan_jaringan_foreground).
                                setMessage("Terdapat kesalahan jaringan saat memuat data");
                        builder.setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
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
                params.put("password",password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}