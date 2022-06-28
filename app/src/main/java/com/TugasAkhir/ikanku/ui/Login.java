package com.TugasAkhir.ikanku.ui;

import androidx.annotation.NonNull;
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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.TugasAkhir.ikanku.Preferences;
import com.TugasAkhir.ikanku.R;
import com.TugasAkhir.ikanku.ui.pembeli.PembeliMain;
import com.TugasAkhir.ikanku.ui.penjual.PenjualMain;
import com.TugasAkhir.ikanku.util.ServerApi;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Login extends AppCompatActivity {

    private EditText    username,password;
    private Button      btnlogin;
    private TextView    register;
    private ProgressBar loading;
    SessionManager      sessionManager;
    private static final int TIME_INTERVAL = 2000;
    private long mBackPressed;

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
                    proseslogin(mUsername,mPass);
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

    private void proseslogin(String username, String password) {
        loading.setVisibility(View.VISIBLE);
        btnlogin.setVisibility(View.GONE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_LOGIN, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String success = jsonObject.getString("success");
                JSONArray jsonArray = jsonObject.getJSONArray("login");

                if (success.equals("1")) {

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);

                        Preferences.setKeyIdPengguna(getBaseContext(), object.getString("id_pengguna"));
                        Preferences.setRegisteredUser(getBaseContext(), object.getString("username"));
                        Preferences.setKeyNama(getBaseContext(), object.getString("nama"));
                        Preferences.setKeyAlamat(getBaseContext(), object.getString("alamat"));
                        Preferences.setKeyTelepon(getBaseContext(), object.getString("telp"));

                        String id_pengguna = object.getString("id_pengguna").trim();
                        String username1 = object.getString("username").trim();
                        String nama = object.getString("nama").trim();
                        String telp = object.getString("telp").trim();
                        String alamat = object.getString("alamat").trim();
                        String namatoko = object.getString("namatoko").trim();
                        String status = object.getString("status").trim();

                        sessionManager.createsession(id_pengguna, username1, nama, telp, alamat, namatoko, status);
//                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                                "+62" + telp,
//                                60,
//                                TimeUnit.SECONDS,
//                                Login.this,
//                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//                                    @Override
//                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
//
//                                    }
//
//                                    @Override
//                                    public void onVerificationFailed(@NonNull FirebaseException e) {
//
//                                        Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                                    }
//
//                                    @Override
//                                    public void onCodeSent(@NonNull String backendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//;
//
//                                        Intent intent=new Intent(getApplicationContext(),VerifyOTP.class);
//                                        intent.putExtra("mobile",telp);
//                                        intent.putExtra("backendotp",backendotp);
//                                        startActivity(intent);
//                                        finish();
//
//                                    }
//                                }
//
//                        );

//                        if (status.equals("1")) {
//                            Intent intent = new Intent(Login.this, PembeliMain.class);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                            startActivity(intent);
//                        } else {
//                            Intent intent = new Intent(Login.this, PenjualMain.class);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                            startActivity(intent);
//                        }

                        Intent intent=new Intent(getApplicationContext(),SendOTP.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();


//                        loading.setVisibility(View.GONE);
//                        btnlogin.setVisibility(View.VISIBLE);
                        btnlogin.setVisibility(View.GONE);
                    }
                } else {
                    loading.setVisibility(View.GONE);
                    btnlogin.setVisibility(View.VISIBLE);

                    AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                    builder.setTitle("Login Gagal").
                            setIcon(R.mipmap.ic_warning_foreground).
                            setMessage("Username Dan Password Salah");
                    builder.setPositiveButton("Coba Lagi",
                            (dialog, id) -> dialog.cancel());
                    AlertDialog alert11 = builder.create();
                    alert11.show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                loading.setVisibility(View.GONE);
                btnlogin.setVisibility(View.VISIBLE);

                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                builder.setTitle("Login Gagal").
                        setIcon(R.mipmap.ic_warning_foreground).
                        setMessage("Username Dan Password Salah");
                builder.setPositiveButton("Coba Lagi",
                        (dialog, id) -> dialog.cancel());
                AlertDialog alert11 = builder.create();
                alert11.show();
            }
        },
                error -> {
                    loading.setVisibility(View.GONE);
                    btnlogin.setVisibility(View.VISIBLE);

                    AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                    builder.setTitle("Kesalahan Jaringan").
                            setIcon(R.mipmap.ic_kesalahan_jaringan_foreground).
                            setMessage("Terdapat kesalahan jaringan saat memuat data");
                    builder.setPositiveButton("OK",
                            (dialog, id) -> dialog.cancel());
                    AlertDialog alert11 = builder.create();
                    alert11.show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed()
    {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
        {
            sessionManager.logout();
            FirebaseAuth.getInstance().signOut();
            super.moveTaskToBack(true);
            return;
        }
        else {
            sessionManager.logout();
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(getBaseContext(), "Tekan Sekali lagi untuk Keluar", Toast.LENGTH_SHORT).show();
        }

        mBackPressed = System.currentTimeMillis();
        sessionManager.logout();
        finish();
        FirebaseAuth.getInstance().signOut();
    }
}