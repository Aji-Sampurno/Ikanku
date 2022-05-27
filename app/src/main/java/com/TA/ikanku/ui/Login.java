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
import com.TA.ikanku.Preferences;
import com.TA.ikanku.R;
import com.TA.ikanku.ui.pembeli.PembeliMain;
import com.TA.ikanku.ui.penjual.PenjualMain;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    private EditText    username,password;
    private Button      btnlogin;
    private TextView    register;
    private ProgressBar loading;
    SessionManager      sessionManager;
    private static String URL_LOGIN="https://pusatbelanjaikan.000webhostapp.com/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(this);

        loading     = findViewById(R.id.loading);
        username    = findViewById(R.id.inp_username);
        password    = findViewById(R.id.inp_password);
        btnlogin    = findViewById(R.id.btnlogin);
        register    = findViewById(R.id.register);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mUsername = username.getText().toString().trim();
                String mPass     = password.getText().toString().trim();

                if (!mUsername.isEmpty() || !mPass.isEmpty()){
                    Login(mUsername,mPass);
                } else {
                    username.setError("Mohon Masukkan Username");
                    password.setError("Mohon Masukkan Password");
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Registrasi.class));
            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    private void Login(String username, String password){
        loading.setVisibility(View.VISIBLE);
        btnlogin.setVisibility(View.GONE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("login");

                            if (success.equals("1")){

                                for (int i = 0; i < jsonArray.length(); i++){

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    Preferences.setKeyIdPengguna(getBaseContext(), object.getString("id_pengguna"));
                                    Preferences.setRegisteredUser(getBaseContext(), object.getString("username"));
                                    Preferences.setKeyNama(getBaseContext(), object.getString("nama"));
                                    Preferences.setKeyAlamat(getBaseContext(), object.getString("alamat"));
                                    Preferences.setKeyTelepon(getBaseContext(), object.getString("telp"));

                                    String id_pengguna  = object.getString("id_pengguna").trim();
                                    String username     = object.getString("username").trim();
                                    String nama         = object.getString("nama").trim();
                                    String telp         = object.getString("telp").trim();
                                    String alamat       = object.getString("alamat").trim();
                                    String namatoko     = object.getString("namatoko").trim();
                                    String status       = object.getString("status").trim();

                                    sessionManager.createsession(id_pengguna, username, nama, telp, alamat, namatoko, status);

                                    if (status.equals("1")){
                                        Intent intent = new Intent(Login.this, PembeliMain.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Intent intent = new Intent(Login.this, PenjualMain.class);
                                        startActivity(intent);
                                        finish();
                                    }

                                    loading.setVisibility(View.GONE);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            loading.setVisibility(View.GONE);
                            btnlogin.setVisibility(View.VISIBLE);

                            AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                            builder.setTitle("Login Gagal").
                                    setIcon(R.mipmap.ic_warning_foreground).
                                    setMessage("Password Atau Username Salah");
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
                        btnlogin.setVisibility(View.VISIBLE);

                        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
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
                params.put("password",password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}