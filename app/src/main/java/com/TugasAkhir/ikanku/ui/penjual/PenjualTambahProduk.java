package com.TugasAkhir.ikanku.ui.penjual;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.TugasAkhir.ikanku.ui.DetailProduk;
import com.TugasAkhir.ikanku.ui.Keranjang;
import com.TugasAkhir.ikanku.ui.Registrasi;
import com.TugasAkhir.ikanku.ui.pembeli.PembeliMain;
import com.TugasAkhir.ikanku.util.ServerApi;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.TugasAkhir.ikanku.R;
import com.TugasAkhir.ikanku.ui.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PenjualTambahProduk extends AppCompatActivity {

    private static final String TAG = PenjualTambahProduk.class.getSimpleName();
    private EditText                namaproduk,stok,hargaproduk,deskripsi;
    private Button                  btnsimpan,tambahgambar;
    private AutoCompleteTextView    autkategori;
    private ImageView               gambarproduk;
    private ProgressBar             loading;
    private ProgressDialog          pd;
    private SessionManager          sessionManager;
    private String                  getId;
    private Bitmap                  bitmap;

    final int CODE_GALLERY_REQUEST = 999;

    int bitmap_size = 60;

    ArrayList<String> kategori = new ArrayList<>();

    ArrayAdapter<String> katadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjual_tambah_produk);

        sessionManager = new SessionManager(this);

        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(sessionManager.ID_PENGGUNA);

        pd  = new ProgressDialog(PenjualTambahProduk.this);

        loading         = findViewById(R.id.loading);
        namaproduk      = findViewById(R.id.inp_namaproduk);
        stok            = findViewById(R.id.inp_stok);
        hargaproduk     = findViewById(R.id.inp_hargaproduk);
        deskripsi       = findViewById(R.id.inp_deskripsi);
        autkategori     = findViewById(R.id.autokategori);
        gambarproduk    = findViewById(R.id.inp_gambarproduk);
        tambahgambar    = findViewById(R.id.btntambahgambar);
        btnsimpan       = findViewById(R.id.btn_tambah);

        datakategori();

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        btnsimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { simpanData(); }
        });

        tambahgambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(PenjualTambahProduk.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},CODE_GALLERY_REQUEST
                );
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == CODE_GALLERY_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent,"Pilih Gambar"), CODE_GALLERY_REQUEST);
            } else {
                Toast.makeText(getApplicationContext(), "Tidak Mendapatkan Izin Untuk Mengakses Galeri", Toast.LENGTH_SHORT).show();
            }

            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CODE_GALLERY_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri filePath = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(filePath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                gambarproduk.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String imageToString (Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] imageBytes = outputStream.toByteArray();

        String encodeImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodeImage;
    }

    private void datakategori() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_DAFTARKATEGORI,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        kategori.clear();
                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                String namakategori = data.optString("namakategori");
                                kategori.add(namakategori);
                                katadapter = new ArrayAdapter<>(PenjualTambahProduk.this, R.layout.drop_down_item, kategori);
                                katadapter.setDropDownViewResource(R.layout.drop_down_item);
                                autkategori.setAdapter(katadapter);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        katadapter.notifyDataSetChanged();
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

    private void simpanData() {
        pd.setMessage("Menyimpan Data");
        pd.setCancelable(false);
        pd.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_TAMBAHPRODUK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            AlertDialog.Builder builder = new AlertDialog.Builder(PenjualTambahProduk.this);
                            builder.setTitle("Tambah Produk").
                                    setIcon(R.mipmap.ic_sukses_foreground).
                                    setMessage("Produk berhasil ditambahkan");
                            builder.setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            startActivity( new Intent(PenjualTambahProduk.this,PenjualMain.class));
                                            finish();
                                        }
                                    });
                            AlertDialog alert11 = builder.create();
                            alert11.show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.cancel();
                        Toast.makeText(PenjualTambahProduk.this, "Error : "+ error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("idpenjual",getId);
                map.put("namaproduk",namaproduk.getText().toString());
                map.put("kategoriproduk",autkategori.getText().toString());
                map.put("stok",stok.getText().toString());
                map.put("hargaproduk",hargaproduk.getText().toString());
                map.put("deskripsi",deskripsi.getText().toString());
                map.put("gambarproduk",imageToString(bitmap));
                System.out.println(map);

                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}