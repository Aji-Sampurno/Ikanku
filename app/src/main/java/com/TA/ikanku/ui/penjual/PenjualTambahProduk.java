package com.TA.ikanku.ui.penjual;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.TA.ikanku.R;
import com.TA.ikanku.ui.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class PenjualTambahProduk extends AppCompatActivity {

    private static final String TAG = PenjualTambahProduk.class.getSimpleName();
    EditText namaproduk,stok,hargaproduk,deskripsi;
    Button btnsimpan,tambahgambar;
    ImageView gambarproduk;
    private ProgressBar loading;
    ProgressDialog pd;
    SessionManager sessionManager;
    String getId;
    Bitmap bitmap;
    final int CODE_GALLERY_REQUEST = 999;
    int bitmap_size = 60;
    private static String URL_TAMBAHPRODUK="https://jualanikan.000webhostapp.com/Penjual/TambahProduk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjual_tambah_produk);

        sessionManager = new SessionManager(this);

        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(sessionManager.ID_PENGGUNA);

        loading = findViewById(R.id.loading);
        namaproduk = (EditText) findViewById(R.id.inp_namaproduk);
        stok = (EditText) findViewById(R.id.inp_stok);
        hargaproduk = (EditText) findViewById(R.id.inp_hargaproduk);
        deskripsi = (EditText) findViewById(R.id.inp_deskripsi);
        gambarproduk = (ImageView) findViewById(R.id.inp_gambarproduk);
        tambahgambar = (Button) findViewById(R.id.btntambahgambar);
        btnsimpan = (Button) findViewById(R.id.btn_tambah);
        pd = new ProgressDialog(PenjualTambahProduk.this);

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

    private void simpanData()
    {
        pd.setMessage("Menyimpan Data");
        pd.setCancelable(false);
        pd.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_TAMBAHPRODUK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            Toast.makeText(PenjualTambahProduk.this, response, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        startActivity( new Intent(PenjualTambahProduk.this,PenjualMain.class));
                        finish();
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