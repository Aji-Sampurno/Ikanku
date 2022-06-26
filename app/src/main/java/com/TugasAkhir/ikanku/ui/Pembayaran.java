package com.TugasAkhir.ikanku.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.TugasAkhir.ikanku.Preferences;
import com.TugasAkhir.ikanku.util.ServerApi;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.TugasAkhir.ikanku.R;
import com.TugasAkhir.ikanku.ui.pembeli.PembeliMain;
import com.TugasAkhir.ikanku.ui.penjual.PenjualMain;
import com.TugasAkhir.ikanku.util.FormatCurrency;

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
    TextView        tvidproduk, tvidpenjual, tvnamaproduk, tvkategoriproduk, tvstok, tvhargaproduk,tvdeskripsi,tvidkeranjang,tvidpengguna,tvjumlah,tvtotalbayar,bayarpengiriman;
    ProgressDialog  pd;
    String          getAlamat,getId,getTelp,getStatus;
    SessionManager  sessionManager;
    MenuItem        menuItem;
    Menu            action;

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
        String intentkategoriproduk = data.getStringExtra("kategoriproduk");
        String intentstok = data.getStringExtra("stok");
        String intenthargaproduk = data.getStringExtra("hargaproduk");
        String intentdeskripsi = data.getStringExtra("deskripsi");
        String intentidpengguna = data.getStringExtra("idpengguna");
        String intentjumlah      = data.getStringExtra("jumlah");
        String intentgambar = data.getStringExtra("gambarproduk");

        sessionManager   = new SessionManager(this);

        HashMap<String, String> user = sessionManager.getUserDetail();
        getId            = user.get(sessionManager.ID_PENGGUNA);
        getAlamat        = user.get(sessionManager.ALAMAT);
        getTelp          = user.get(sessionManager.TELP);
        getStatus        = user.get(sessionManager.STATUS);

        tvidproduk       = findViewById(R.id.ppidproduk);
        tvidpenjual      = findViewById(R.id.ppidpenjual);
        tvnamaproduk     = findViewById(R.id.ppnamaproduk);
        tvkategoriproduk = findViewById(R.id.ppkategoriproduk);
        tvstok           = findViewById(R.id.ppstok);
        tvhargaproduk    = findViewById(R.id.ppharga);
        tvdeskripsi      = findViewById(R.id.ppdeskripsi);
        tvidkeranjang    = findViewById(R.id.ppidkeranjang);
        tvidpengguna     = findViewById(R.id.ppidpengguna);
        tvjumlah         = findViewById(R.id.ppjumlah);
        bayar            = findViewById(R.id.btnbayar);
        gambarproduk     = findViewById(R.id.ppgambarproduk);
        bayaralamat      = findViewById(R.id.bayaralamat);
        bayarpengiriman  = findViewById(R.id.bayarpengiriman);
        tvtotalbayar     = findViewById(R.id.totalbayar);
        bayartelp        = findViewById(R.id.bayartelp);
        editalamat       = findViewById(R.id.bayaredit);
        savealamat       = findViewById(R.id.bayarsave);
        edittelp         = findViewById(R.id.bayaredittelp);
        savetelp         = findViewById(R.id.bayarsavetelp);
        pd               = new ProgressDialog(Pembayaran.this);

        if(update == 1) {
            FormatCurrency currency = new FormatCurrency();
            tvidproduk.setText(intentidproduk);
            tvidpenjual.setText(intentidpenjual);
            tvnamaproduk.setText(intentnamaproduk);
            tvkategoriproduk.setText(intentkategoriproduk);
            tvstok.setText(intentstok);
            tvhargaproduk.setText(currency.formatRupiah(intenthargaproduk));
            tvdeskripsi.setText(intentdeskripsi);
            tvidkeranjang.setText(intentidkeranjang);
            tvidpengguna.setText(intentidpengguna);
            tvjumlah.setText(intentjumlah);
            bayaralamat.setText(getAlamat);
            bayartelp.setText(getTelp);

            String kategori = tvkategoriproduk.getText().toString().trim();
            String harga    = intenthargaproduk;
            String jumlah   = intentjumlah;
            String ongkir   = "5000";

            int jum = Integer.parseInt(intentjumlah);
            int batas = 10;

            Glide.with(Pembayaran.this).load(intentgambar).centerCrop().into(gambarproduk);

            if (kategori.equals("Ikan")){

                if (jum >= batas ){

                    int totalbayar  = Integer.parseInt(harga) * Integer.parseInt(jumlah);

                    tvtotalbayar.setText(currency.formatRupiah(String.valueOf(totalbayar)));
                    tvtotalbayar.setContentDescription(String.valueOf(totalbayar));

                } else {

                    int totalbayar  = Integer.parseInt(harga) * Integer.parseInt(jumlah) + Integer.parseInt(ongkir);

                    tvtotalbayar.setText(currency.formatRupiah(String.valueOf(totalbayar)));
                    tvtotalbayar.setContentDescription(String.valueOf(totalbayar));

                }
            }else {

                int totalbayar  = Integer.parseInt(harga) * Integer.parseInt(jumlah);

                tvtotalbayar.setText(currency.formatRupiah(String.valueOf(totalbayar)));
                tvtotalbayar.setContentDescription(String.valueOf(totalbayar));

            }
        }

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

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_BUATPESANAN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.cancel();
                        try {
                            JSONObject res = new JSONObject(response);

                            AlertDialog.Builder builder = new AlertDialog.Builder(Pembayaran.this);
                            builder.setTitle("Berhasil").
                                    setIcon(R.mipmap.ic_sukses_foreground).
                                    setMessage("Pesanan berhasil dibuat");
                            builder.setPositiveButton("Checkout",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            if (getStatus.equals("1")){
                                                HapusKeranjang();
                                                Intent i = new Intent(Pembayaran.this, Checkout.class);
                                                startActivity(i);
                                                finish();
                                            }else {
                                                HapusKeranjang();
                                                Intent i = new Intent(Pembayaran.this, Checkout.class);
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

    private void HapusKeranjang() {
        pd.setMessage("Menghapus Data");
        pd.setCancelable(false);
        pd.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_HAPUSKERANJANG,
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

    private void Hapus() {
        pd.setMessage("Menghapus Data");
        pd.setCancelable(false);
        pd.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_HAPUSKERANJANG,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.cancel();
                        AlertDialog.Builder builder = new AlertDialog.Builder(Pembayaran.this);
                        builder.setTitle("Berhasil").
                                setIcon(R.mipmap.ic_confirm_foreground).
                                setMessage("Produk berhasil dihapus");
                        builder.setPositiveButton("ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        finish();
                                    }
                                });
                        AlertDialog alert11 = builder.create();
                        alert11.show();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.pembayaran_menu,menu);

        menuItem = menu.findItem(R.id.menuhapus);
        action = menu;

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuhapus:

                AlertDialog.Builder builder = new AlertDialog.Builder(Pembayaran.this);
                builder.setTitle("Konfirmasi").
                        setIcon(R.mipmap.ic_confirm_foreground).
                        setMessage("apakah kamu yakin untuk menghapus produk dari keranjang ?");
                builder.setPositiveButton("Ya",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Hapus();
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

                return true;

            default:

                return super.onOptionsItemSelected(item);
        }
    }
}