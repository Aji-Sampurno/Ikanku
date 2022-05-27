package com.TA.ikanku.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.TA.ikanku.R;
import com.TA.ikanku.ui.pembeli.PembeliMain;
import com.TA.ikanku.ui.penjual.PenjualMain;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PengaturanAkun extends AppCompatActivity {

    private static final String TAG = PengaturanAkun.class.getSimpleName();
    private EditText username,nama,telp,alamat,namatoko,status;
    private Button btnlogout;
    SessionManager sessionManager;
    String getId;
    private Menu action;
    private static String URL_AKUN = "https://pusatbelanjaikan.000webhostapp.com/read_detail.php";
    private static String URL_EDITAKUN = "https://pusatbelanjaikan.000webhostapp.com/edit_detail.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan_akun);

        sessionManager = new SessionManager(this);

        username = findViewById(R.id.username);
        nama = findViewById(R.id.nama);
        telp = findViewById(R.id.telp);
        alamat = findViewById(R.id.alamat);
        namatoko = findViewById(R.id.namatoko);
        status = findViewById(R.id.status);
        btnlogout = findViewById(R.id.btnlogout);

        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(sessionManager.ID_PENGGUNA);

        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PengaturanAkun.this);
                builder.setTitle("Konfirmasi Logout").
                        setIcon(R.mipmap.ic_confirm_foreground).
                        setMessage("apakah kamu yakin untuk logout?");
                builder.setPositiveButton("Ya",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                sessionManager.logout();
                                Intent i = new Intent(PengaturanAkun.this, Login.class);
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

    private void getUserDetail(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_AKUN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.i(TAG, response.toString());

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("read");

                            if (success.equals("1")){
                                for (int i = 0; i<jsonArray.length(); i++){
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String strUsername = object.getString("username").trim();
                                    String strNama = object.getString("nama").trim();
                                    String strTelp = object.getString("telp").trim();
                                    String strAlamat = object.getString("alamat").trim();
                                    String strNamatoko = object.getString("namatoko").trim();
                                    String strStatus = object.getString("status").trim();

                                    username.setText(strUsername);
                                    nama.setText(strNama);
                                    telp.setText(strTelp);
                                    alamat.setText(strAlamat);
                                    namatoko.setText(strNamatoko);
                                    status.setText(strStatus);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();

                            AlertDialog.Builder builder = new AlertDialog.Builder(PengaturanAkun.this);
                            builder.setTitle("Kesalahan Memuat").
                                    setIcon(R.mipmap.ic_warning_foreground).
                                    setMessage("Terdapat kesalahan jaringan saat memuat update");
                            builder.setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });
                            builder.setNegativeButton("Kembali",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            if (status.equals("1")){
                                                Intent intent = new Intent(PengaturanAkun.this, PembeliMain.class);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                Intent intent = new Intent(PengaturanAkun.this, PenjualMain.class);
                                                startActivity(intent);
                                                finish();
                                            }
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
                        progressDialog.dismiss();

                        AlertDialog.Builder builder = new AlertDialog.Builder(PengaturanAkun.this);
                        builder.setTitle("Kesalahan Jaringan").
                                setIcon(R.mipmap.ic_kesalahan_jaringan_foreground).
                                setMessage("Terdapat kesalahan jaringan saat melakukan update");
                        builder.setNegativeButton("Kembali",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        if (status.equals("1")){
                                            Intent intent = new Intent(PengaturanAkun.this, PembeliMain.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Intent intent = new Intent(PengaturanAkun.this, PenjualMain.class);
                                            startActivity(intent);
                                            finish();
                                        }
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
                params.put("id_pengguna",getId);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserDetail();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_action,menu);

        action = menu;
        action.findItem(R.id.menusave).setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuedit:

                username.setFocusableInTouchMode(true);
                nama.setFocusableInTouchMode(true);
                telp.setFocusableInTouchMode(true);

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(username, InputMethodManager.SHOW_IMPLICIT);

                action.findItem(R.id.menuedit).setVisible(false);
                action.findItem(R.id.menusave).setVisible(true);

                return true;

            case R.id.menusave:

                SaveDetail();

                action.findItem(R.id.menuedit).setVisible(true);
                action.findItem(R.id.menusave).setVisible(false);

                username.setFocusableInTouchMode(false);
                nama.setFocusableInTouchMode(false);
                telp.setFocusableInTouchMode(false);
                username.setFocusable(false);
                nama.setFocusable(false);
                telp.setFocusable(false);

                return true;

            default:

                return super.onOptionsItemSelected(item);
        }
    }

    private void SaveDetail() {
        final String username = this.username.getText().toString().trim();
        final String nama = this.nama.getText().toString().trim();
        final String telp = this.telp.getText().toString().trim();
        final String status = this.status.getText().toString().trim();
        final String alamat = this.alamat.getText().toString().trim();
        final String namatoko = this.namatoko.getText().toString().trim();
        final String id_pengguna = this.getId;

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Menyimpan...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_EDITAKUN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")){
                                sessionManager.createsession(id_pengguna, username, nama, telp, alamat, namatoko, status);

                                AlertDialog.Builder builder = new AlertDialog.Builder(PengaturanAkun.this);
                                builder.setTitle("Sukses").
                                        setIcon(R.mipmap.ic_sukses_foreground).
                                        setMessage("Akun berhasil di perbarui");
                                builder.setPositiveButton("OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });
                                AlertDialog alert11 = builder.create();
                                alert11.show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();

                            AlertDialog.Builder builder = new AlertDialog.Builder(PengaturanAkun.this);
                            builder.setTitle("Gagal").
                                    setIcon(R.mipmap.ic_warning_foreground).
                                    setMessage("Terdapat kesalahan saat memperbarui akun");
                            builder.setPositiveButton("Ulangi",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });
                            builder.setNegativeButton("Batal",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            if (status.equals("1")){
                                                Intent intent = new Intent(PengaturanAkun.this, PembeliMain.class);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                Intent intent = new Intent(PengaturanAkun.this, PenjualMain.class);
                                                startActivity(intent);
                                                finish();
                                            }
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
                        progressDialog.dismiss();

                        AlertDialog.Builder builder = new AlertDialog.Builder(PengaturanAkun.this);
                        builder.setTitle("Kesalahan Jaringan").
                                setIcon(R.mipmap.ic_kesalahan_jaringan_foreground).
                                setMessage("Terdapat kesalahan jaringan saat melakukan update");
                        builder.setNegativeButton("Kembali",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        if (status.equals("1")){
                                            Intent intent = new Intent(PengaturanAkun.this, PembeliMain.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Intent intent = new Intent(PengaturanAkun.this, PenjualMain.class);
                                            startActivity(intent);
                                            finish();
                                        }
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
                params.put("namatoko",namatoko);
                params.put("id_pengguna",id_pengguna);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}