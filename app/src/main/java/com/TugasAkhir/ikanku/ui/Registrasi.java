package com.TugasAkhir.ikanku.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.TugasAkhir.ikanku.Preferences;
import com.TugasAkhir.ikanku.model.ModelDataProduk;
import com.TugasAkhir.ikanku.util.ServerApi;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.TugasAkhir.ikanku.R;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Registrasi extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText    username,nama,telp,password,ulangpassword,alamat;
    private AutoCompleteTextView autprov, autkab, autkec;
    private Button      btnregister;
    private ProgressBar loading;
    private TextView    login;
    SessionManager      sessionManager;

    ArrayList<String> provinsi  = new ArrayList<>();
    ArrayList<String> kabupaten = new ArrayList<>();
    ArrayList<String> kecamatan = new ArrayList<>();

    ArrayAdapter<String> provadater;
    ArrayAdapter<String> kabadater;
    ArrayAdapter<String> kecadater;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);

        sessionManager = new SessionManager(this);

        loading         = findViewById(R.id.loading);
        username        = findViewById(R.id.inp_username);
        nama            = findViewById(R.id.inp_nama);
        telp            = findViewById(R.id.inp_telp);
        alamat          = findViewById(R.id.inp_alamat);
        password        = findViewById(R.id.inp_password);
        ulangpassword   = findViewById(R.id.inp_ulangpassword);
        autprov         = findViewById(R.id.autoprov);
        autkab          = findViewById(R.id.autokab);
        autkec          = findViewById(R.id.autokec);
        btnregister     = findViewById(R.id.btnregister);
        login           = findViewById(R.id.login);

        requestQueue = Volley.newRequestQueue(this);

        dataprovinsi();
        datakabupaten();
        datakecamatan();

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String musername       = username.getText().toString().trim();
                String mnama           = nama.getText().toString().trim();
                String mtelp           = telp.getText().toString().trim();
                String malamat         = alamat.getText().toString().trim();
                String mpassword       = password.getText().toString().trim();
                String mulangpassword  = ulangpassword.getText().toString().trim();
                String mprov           = autprov.getText().toString();
                String mkab            = autkab.getText().toString();
                String mkec            = autkec.getText().toString();

                if (!musername.isEmpty() || !mnama.isEmpty() || !mtelp.isEmpty() || !malamat.isEmpty() || !mpassword.isEmpty() || !mulangpassword.isEmpty() || !mprov.isEmpty() || !mkab.isEmpty() || !mkec.isEmpty()){
                    Register(musername,mnama,mtelp,malamat,mpassword,mprov,mkab,mkec);
                } else {
                    username.setError("Mohon Masukkan Username");
                    nama.setError("Mohon Masukkan Nama");
                    telp.setError("Mohon Masukkan Telepon");
                    alamat.setError("Mohon Masukkan Alamat");
                    password.setError("Mohon Masukkan Password");
                    ulangpassword.setError("Mohon Masukkan Password");
                    autprov.setError("Mohon Pilih Provinsi");
                    autkab.setError("Mohon Pilih Kabupaten");
                    autkec.setError("Mohon Pilih Kecamatan");
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Registrasi.this, Login.class));
            }
        });
    }

    private void datakecamatan() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_DAFTARKECAMATAN,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        kecamatan.clear();
                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                String namakec = data.optString("namakecamatan");
                                kecamatan.add(namakec);
                                kecadater = new ArrayAdapter<>(Registrasi.this, R.layout.drop_down_item, kecamatan);
                                kecadater.setDropDownViewResource(R.layout.drop_down_item);
                                autkec.setAdapter(kecadater);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        kecadater.notifyDataSetChanged();
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

    private void datakabupaten() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_DAFTARKABUPATEN,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        kabupaten.clear();
                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                String namakab = data.optString("namakabupaten");
                                kabupaten.add(namakab);
                                kabadater = new ArrayAdapter<>(Registrasi.this, R.layout.drop_down_item, kabupaten);
                                kabadater.setDropDownViewResource(R.layout.drop_down_item);
                                autkab.setAdapter(kabadater);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        kabadater.notifyDataSetChanged();
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

    private void dataprovinsi() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_DAFTARPROVINSI,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        provinsi.clear();
                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                String namaprov = data.optString("namaprovinsi");
                                provinsi.add(namaprov);
                                provadater = new ArrayAdapter<>(Registrasi.this, R.layout.drop_down_item, provinsi);
                                provadater.setDropDownViewResource(R.layout.drop_down_item);
                                autprov.setAdapter(provadater);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        provadater.notifyDataSetChanged();
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

    private void Register(String musername, String mnama, String mtelp, String malamat, String mpassword, String mprov, String mkab, String mkec){
        loading.setVisibility(View.VISIBLE);
        btnregister.setVisibility(View.GONE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_DAFTARPENGGUNA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

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
            }, new Response.ErrorListener() {
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
                params.put("username",musername);
                params.put("nama",mnama);
                params.put("telp",mtelp);
                params.put("alamat",malamat);
                params.put("password",mpassword);
                params.put("provinsi",mprov);
                params.put("kabupaten",mkab);
                params.put("kecamatan",mkec);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}