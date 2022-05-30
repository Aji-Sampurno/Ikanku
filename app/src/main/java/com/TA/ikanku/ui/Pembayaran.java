package com.TA.ikanku.ui;

import androidx.appcompat.app.AppCompatActivity;

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

import com.TA.ikanku.ui.penjual.PenjualEditProduk;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.TA.ikanku.R;
import com.TA.ikanku.ui.pembeli.PembeliMain;
import com.TA.ikanku.ui.penjual.PenjualMain;
import com.TA.ikanku.util.FormatCurrency;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Pembayaran extends AppCompatActivity {

    ImageButton     editalamat,savealamat,savetelp,edittelp;
    Button          bayar;
    EditText        bayaralamat,bayartelp;
    ImageView       gambarproduk;
    TextView        tvidproduk, tvidpenjual, tvnamaproduk, tvstok, tvhargaproduk,tvdeskripsi,tvidkeranjang,tvidpengguna,tvjumlah,tvtotalbayar,bayarpengiriman;
    ProgressDialog  pd;
    String          getAlamat,getId,getTelp,getStatus;
    SessionManager  sessionManager;

//    private static String URL_DETAILKERANJANG="https://jualanikan.000webhostapp.com/Penjual/DetailKeranjang?idkeranjang=";
    private static String URL_HAPUSKERANJANG="https://jualanikan.000webhostapp.com/Penjual/HapusKeranjang?idkeranjang=";
    private static String URL_BUATPESANAN="https://jualanikan.000webhostapp.com/Penjual/TambahPesanan";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran);

        Intent data = getIntent();
        final int update = data.getIntExtra("update",0);
        String intentidkeranjang = data.getStringExtra("idkeranjang");
        String intentidproduk = data.getStringExtra("idproduk");
        String intentidpenjual = data.getStringExtra("idpenjual");
        String intentnamaproduk = data.getStringExtra("namaproduk");
        String intentstok = data.getStringExtra("stok");
        String intenthargaproduk = data.getStringExtra("hargaproduk");
        String intentdeskripsi = data.getStringExtra("deskripsi");
        String intentidpengguna = data.getStringExtra("idpengguna");
        String intentjumlah      = data.getStringExtra("jumlah");
        String intentgambar = data.getStringExtra("gambarproduk");

        sessionManager  = new SessionManager(this);

        HashMap<String, String> user = sessionManager.getUserDetail();
        getId           = user.get(sessionManager.ID_PENGGUNA);
        getAlamat       = user.get(sessionManager.ALAMAT);
        getTelp         = user.get(sessionManager.TELP);
        getStatus       = user.get(sessionManager.STATUS);

        tvidproduk      = findViewById(R.id.ppidproduk);
        tvidpenjual     = findViewById(R.id.ppidpenjual);
        tvnamaproduk    = findViewById(R.id.ppnamaproduk);
        tvstok          = findViewById(R.id.ppstok);
        tvhargaproduk   = findViewById(R.id.ppharga);
        tvdeskripsi     = findViewById(R.id.ppdeskripsi);
        tvidkeranjang   = findViewById(R.id.ppidkeranjang);
        tvidpengguna    = findViewById(R.id.ppidpengguna);
        tvjumlah        = findViewById(R.id.ppjumlah);
        bayar           = findViewById(R.id.btnbayar);
        gambarproduk    = findViewById(R.id.ppgambarproduk);
        bayaralamat     = findViewById(R.id.bayaralamat);
        bayarpengiriman = findViewById(R.id.bayarpengiriman);
        tvtotalbayar    = findViewById(R.id.totalbayar);
        bayartelp       = findViewById(R.id.bayartelp);
        editalamat      = findViewById(R.id.bayaredit);
        savealamat      = findViewById(R.id.bayarsave);
        edittelp        = findViewById(R.id.bayaredittelp);
        savetelp        = findViewById(R.id.bayarsavetelp);
        pd              = new ProgressDialog(Pembayaran.this);

        if(update == 1)
        {
            FormatCurrency currency = new FormatCurrency();
            tvidproduk.setText(intentidproduk);
            tvidpenjual.setText(intentidpenjual);
            tvnamaproduk.setText(intentnamaproduk);
            tvstok.setText(intentstok);
            tvhargaproduk.setText(currency.formatRupiah(intenthargaproduk));
            tvdeskripsi.setText(intentdeskripsi);
            tvidkeranjang.setText(intentidkeranjang);
            tvidpengguna.setText(intentidpengguna);
            tvjumlah.setText(intentjumlah);
            bayaralamat.setText(getAlamat);
            bayartelp.setText(getTelp);

            String harga    = intenthargaproduk;
            String jumlah   = intentjumlah;

            Glide.with(Pembayaran.this).load(intentgambar).centerCrop().into(gambarproduk);

            int totalbayar  = Integer.parseInt(harga) * Integer.parseInt(jumlah);

            tvtotalbayar.setText(currency.formatRupiah(String.valueOf(totalbayar)));
            tvtotalbayar.setContentDescription(String.valueOf(totalbayar));
        }

//        loadJson(intentidkeranjang);

        bayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { buat_pesanan();}
        });

        editalamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bayaralamat.setFocusable(true);
                bayaralamat.setFocusableInTouchMode(true);
                savealamat.setVisibility(View.VISIBLE);
                editalamat.setVisibility(View.GONE);
            }
        });

        savealamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bayaralamat.setFocusable(false);
                bayaralamat.setFocusableInTouchMode(false);
                savealamat.setVisibility(View.GONE);
                editalamat.setVisibility(View.VISIBLE);
            }
        });

        edittelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bayartelp.setFocusable(true);
                bayartelp.setFocusableInTouchMode(true);
                savetelp.setVisibility(View.VISIBLE);
                edittelp.setVisibility(View.GONE);
            }
        });

        savetelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bayartelp.setFocusable(false);
                bayartelp.setFocusableInTouchMode(false);
                savetelp.setVisibility(View.GONE);
                edittelp.setVisibility(View.VISIBLE);
            }
        });
    }

    private void buat_pesanan() {
        pd.setMessage("Menyimpan Data");
        pd.setCancelable(false);
        pd.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_BUATPESANAN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.cancel();
                        try {
                            JSONObject res = new JSONObject(response);

                            AlertDialog.Builder builder = new AlertDialog.Builder(Pembayaran.this);
                            builder.setTitle("Pembayaran Sukses").
                                    setIcon(R.mipmap.ic_sukses_foreground).
                                    setMessage("Pesanan anda berhasil dibayar");
                            builder.setPositiveButton("Lanjut Belanja",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            if (getStatus.equals("1")){
                                                HapusKeranjang();
                                                Intent i = new Intent(Pembayaran.this, PembeliMain.class);
                                                startActivity(i);
                                                finish();
                                            }else {
                                                HapusKeranjang();
                                                Intent i = new Intent(Pembayaran.this, PenjualMain.class);
                                                startActivity(i);
                                                finish();
                                            }
                                        }
                                    });
                            builder.setNegativeButton("Lihat Keranjang",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            if (getStatus.equals("1")){
                                                HapusKeranjang();
                                                Intent i = new Intent(Pembayaran.this, PembeliMain.class);
                                                startActivity(i);
                                                finish();
                                            }else {
                                                HapusKeranjang();
                                                Intent i = new Intent(Pembayaran.this, Keranjang.class);
                                                startActivity(i);
                                                finish();
                                            }
                                        }
                                    });
                            AlertDialog alert11 = builder.create();
                            alert11.show();

                        } catch (JSONException e) {
                            e.printStackTrace();

                            AlertDialog.Builder builder = new AlertDialog.Builder(Pembayaran.this);
                            builder.setTitle("Kesalahan Memuat").
                                    setIcon(R.mipmap.ic_warning_foreground).
                                    setMessage("Terdapat Kesalahan saat memuat data");
                            builder.setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            HapusKeranjang();
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

                        AlertDialog.Builder builder = new AlertDialog.Builder(Pembayaran.this);
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
                map.put("idproduk",tvidproduk.getText().toString());
                map.put("jumlah",tvjumlah.getText().toString());
                map.put("bayar",tvtotalbayar.getContentDescription().toString());
                map.put("pengiriman",bayarpengiriman.getText().toString());
                map.put("alamat",bayaralamat.getText().toString());
                map.put("telp",bayartelp.getText().toString());
                System.out.println(map);

                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

//    private void loadJson(String intentidkeranjang)
//    {
//        pd.setMessage("Memuat...");
//        pd.setCancelable(false);
//        pd.show();
//
//        RequestQueue queue = Volley.newRequestQueue(this);
//        FormatCurrency currency = new FormatCurrency();
//        StringRequest updateReq = new StringRequest(Request.Method.GET, URL_DETAILKERANJANG + intentidkeranjang,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        pd.cancel();
//                        try {
//                            JSONArray jArray = new JSONArray(response);
//                            for(int i=0;i<jArray.length();i++){
//                                JSONObject res = jArray.getJSONObject(i);
//                                tvidproduk.setText(res.getString("idproduk").trim());
//                                tvidpenjual.setText(res.getString("idpenjual").trim());
//                                tvnamaproduk.setText(res.getString("namaproduk").trim());
//                                tvstok.setText(res.getString("stok").trim());
//                                tvhargaproduk.setText(currency.formatRupiah(res.getString("hargaproduk").trim()));
//                                tvdeskripsi.setText(res.getString("deskripsi").trim());
//                                tvidkeranjang.setText(res.getString("idkeranjang").trim());
//                                tvidpengguna.setText(res.getString("idpengguna").trim());
//                                tvjumlah.setText(res.getString("jumlah").trim());
//                                bayaralamat.setText(getAlamat);
//                                bayartelp.setText(getTelp);
//
//                                String harga    = res.getString("hargaproduk").trim();
//                                String jumlah   = res.getString("jumlah").trim();
//
//                                Glide.with(Pembayaran.this).load(res.getString("gambarproduk")).centerCrop().into(gambarproduk);
//
//                                int totalbayar  = Integer.parseInt(harga) * Integer.parseInt(jumlah);
//
//                                tvtotalbayar.setText(currency.formatRupiah(String.valueOf(totalbayar)));
//                                tvtotalbayar.setContentDescription(String.valueOf(totalbayar));
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//
//                            AlertDialog.Builder builder = new AlertDialog.Builder(Pembayaran.this);
//                            builder.setTitle("Kesalahan Memuat").
//                                    setIcon(R.mipmap.ic_warning_foreground).
//                                    setMessage("Terdapat Kesalahan saat memuat data");
//                            builder.setPositiveButton("OK",
//                                    new DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog, int id) {
//                                            dialog.cancel();
//                                        }
//                                    });
//                            AlertDialog alert11 = builder.create();
//                            alert11.show();
//
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        pd.cancel();
//
//                        AlertDialog.Builder builder = new AlertDialog.Builder(Pembayaran.this);
//                        builder.setTitle("Kesalahan Jaringan").
//                                setIcon(R.mipmap.ic_kesalahan_jaringan_foreground).
//                                setMessage("Terdapat kesalahan jaringan saat memuat data");
//                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//                            }
//                        });
//                        AlertDialog alert11 = builder.create();
//                        alert11.show();
//
//                    }
//                }){
//        };
//
//        queue.add(updateReq);
//    }

    private void HapusKeranjang()
    {
        pd.setMessage("Menghapus Data");
        pd.setCancelable(false);
        pd.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_HAPUSKERANJANG,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.cancel();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.cancel();

                        AlertDialog.Builder builder = new AlertDialog.Builder(Pembayaran.this);
                        builder.setTitle("Kesalahan Jaringan").
                                setIcon(R.mipmap.ic_kesalahan_jaringan_foreground).
                                setMessage("Terdapat kesalahan jaringan saat melakukan update");
                        builder.setPositiveButton("Ulangi",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        builder.setNegativeButton("Batal",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Intent i = new Intent(Pembayaran.this, PenjualMain.class);
                                        startActivity(i);
                                        finish();
                                    }
                                });
                        AlertDialog alert11 = builder.create();
                        alert11.show();

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("idkeranjang",tvidkeranjang.getText().toString());
                System.out.println(map);

                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

}